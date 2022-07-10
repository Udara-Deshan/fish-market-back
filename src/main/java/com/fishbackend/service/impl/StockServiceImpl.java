package com.fishbackend.service.impl;

import com.fishbackend.dto.DescriptionDTO;
import com.fishbackend.dto.StockDTO;
import com.fishbackend.dto.TokenDTO;
import com.fishbackend.entity.*;
import com.fishbackend.exception.EntryNotFoundException;
import com.fishbackend.repository.*;
import com.fishbackend.service.QRCodeGenerateService;
import com.fishbackend.service.StockService;
import com.fishbackend.util.mapper.StockMapper;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private TokenRepo tokenRepo;

    @Autowired
    private DescriptionRepo descriptionRepo;

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private CoolingRoomRepo coolingRoomRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private QRCodeGenerateService qrCodeGenerateService;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy MM dd");

    @Transactional
    @Override
    public Long placeStock(StockDTO stockDTO) {
        Token token = stockMapper.toToken(stockDTO.getTokenDTO());
        token.setCreateDate(new Date(System.currentTimeMillis()));
        token.setStatus(1);
        Token save = tokenRepo.save(token);
        List<DescriptionDTO> descriptionDTOS = stockDTO.getDescriptionDTOS();
        descriptionDTOS.forEach(descriptionDTO -> descriptionDTO.setId(null));
        List<Description> descriptions = stockMapper.toDescriptions(descriptionDTOS);
        descriptions.forEach(description -> {
            CoolingRoom coolingRoom = coolingRoomRepo.findByIdIs(description.getCoolingRoomId());
            description.setTokenId(save.getId());
            coolingRoom.setStatus(2);
            coolingRoomRepo.save(coolingRoom);
            Description save1 = descriptionRepo.save(description);
        });
        try {
            qrCodeGenerateService.createTokenQRCode(save);
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
        return save.getId();
    }

    @Transactional
    @Override
    public Long updateStock(StockDTO stockDTO) {
        Token token = tokenRepo.findById(stockDTO.getTokenDTO().getId()).orElseThrow(() -> (
                new EntryNotFoundException("Stock is not exists")
        ));
        deleteStock(token.getId());
        return placeStock(stockDTO);
    }

    @Transactional
    @Override
    public Long deleteStock(Long stockId) {
        Token token = tokenRepo.findById(stockId).orElseThrow(() -> (
                new EntryNotFoundException("Stock is not exists")
        ));
        token.setStatus(0);
        List<Description> allDescription = descriptionRepo.findAllByStatusIsAndTokenIdIs(1, token.getId());
        allDescription.forEach(description -> {
            CoolingRoom room = coolingRoomRepo.findByIdIs(description.getCoolingRoomId());
            room.setStatus(1);
            description.setStatus(0);
            coolingRoomRepo.save(room);
        });
        qrCodeGenerateService.deleteTokenQRCode(stockId);
        tokenRepo.save(token);
        descriptionRepo.saveAll(allDescription);
        return stockId;
    }

    @Override
    public StockDTO getStockById(Long stockId) {
        Token token = tokenRepo.findById(stockId).orElseThrow(() -> (
                new EntryNotFoundException("Stock is not exists")
        ));
        TokenDTO tokenDTO = stockMapper.toTokenDto(token);
        Customer customer = customerRepo.findByIdIs(token.getCustomerId());
        tokenDTO.setCustomerName(customer.getShopName());
        List<Description> descriptions = descriptionRepo.findAllByStatusIsAndTokenIdIs(1, stockId);
        return new StockDTO(
                tokenDTO,
                stockMapper.toDescriptionDtoList(descriptions)
        );
    }

    @Override
    public List<TokenDTO> getAllStock(int page, int size) {
        Page<Token> tokens = tokenRepo.findAllByStatusIs(1, PageRequest.of(page, size));
        List<TokenDTO> tokenDTOS = stockMapper.tokenDtoList(tokens.getContent());
        tokenDTOS.forEach(tokenDTO -> {
            tokenDTO.setCustomerName(customerRepo.findByIdIs(tokenDTO.getCustomerId()).getShopName());
        });
        return tokenDTOS;
    }

    @Override
    public List<TokenDTO> searchStock(Long value, int page, int size) {
        Page<Token> tokens = tokenRepo.searchStock(value, PageRequest.of(page, size));
        List<TokenDTO> tokenDTOS = stockMapper.tokenDtoList(tokens.getContent());
        tokenDTOS.forEach(tokenDTO -> {
            tokenDTO.setCustomerName(customerRepo.findByIdIs(tokenDTO.getCustomerId()).getShopName());
        });

        return tokenDTOS;
    }

    @Override
    public TokenDTO getTokenById(Long tokenId) {
        Token token = tokenRepo.getTokenById(tokenId);
        TokenDTO tokenDTO = stockMapper.toTokenDto(token);
        tokenDTO.setCustomerName(customerRepo.findByIdIs(tokenDTO.getCustomerId()).getShopName());
        if (token.getStatus() == 1) {
            return tokenDTO;
        } else {
            throw new EntryNotFoundException("Token id is not valid");
        }
    }

    @Transactional
    @Override
    public void payStock(Long stockId) {
        Token token = tokenRepo.findById(stockId).orElseThrow(() -> (
                new EntryNotFoundException("Stock is not exists")
        ));
        if (token.getStatus() == 1) {
            TokenDTO tokenDTO = stockMapper.toTokenDto(token);
            tokenDTO.setCustomerName(customerRepo.findByIdIs(token.getCustomerId()).getShopName());
            List<Description> descriptions = descriptionRepo.findAllByStatusIsAndTokenIdIs(1, token.getId());
//            long noOfDay = Duration.between(token.getCreateDate().toInstant(), new Date(System.currentTimeMillis()).toInstant()).toDays();
            int days = calculateNoOfDays(token.getCreateDate());
            System.out.println("days = " + days);
            descriptions.forEach(description -> {
                double priceByRoom = coolingRoomRepo.getCoolingRoomPriceByRoomId(description.getCoolingRoomId());
                description.setPrice(priceByRoom*days*description.getFishWeight());
                coolingRoomRepo.changeCoolingRoomStatus(1, description.getCoolingRoomId());
                description.setStatus(2);
            });
            List<DescriptionDTO> descriptionDTOS = stockMapper.toDescriptionDtoList(descriptions);
            double totalPrice = descriptionDTOS.stream().mapToDouble(DescriptionDTO::getPrice).sum();
            descriptionRepo.saveAll(descriptions);
            invoiceRepo.save(new Invoice(null, new Date(System.currentTimeMillis()), Integer.parseInt(days + ""), totalPrice, stockId, 1));
            qrCodeGenerateService.deleteTokenQRCode(stockId);
        } else {
            throw new EntryNotFoundException("Token is not valid");
        }
    }

    @Override
    public Long changeTokenStatus(Long tokenId, int status) {
        Token token = tokenRepo.getTokenById(tokenId);
        token.setStatus(2);
        return tokenRepo.save(token).getId();
    }

    @Override
    public long getMoreThanDayTokenCount(int day) {
        long count = tokenRepo.getAllTokenByMoreThanDayCount(day);
        return count;
    }

    @Override
    public List<TokenDTO> getMoreThanDayToken(int day) {
        List<Token> thanDay = tokenRepo.getAllTokenByMoreThanDay(day);
        List<TokenDTO> tokenDTOS = stockMapper.tokenDtoList(thanDay);
        tokenDTOS.forEach(tokenDTO -> {
            tokenDTO.setCustomerName(customerRepo.findByIdIs(tokenDTO.getCustomerId()).getShopName());
        });
        return tokenDTOS;
    }

    public int calculateNoOfDays(Date tokenDate) {
        Instant instant = new Date(System.currentTimeMillis()).toInstant();
        String e = instant.toString().substring(0, 10);
        String s = tokenDate.toString().substring(0, 10);
        LocalDate start = LocalDate.parse(s);
        LocalDate end = LocalDate.parse(e);
        int days = 0;
        while (!start.isAfter(end)) {
            start = start.plusDays(1);
            days++;
        }
        if (days == 1) {
            return days;
        } else {
            return days -1;
        }
    }
}
