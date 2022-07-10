package com.fishbackend.service.impl;

import com.fishbackend.dto.InvoiceDTO;
import com.fishbackend.dto.ReportDTO;
import com.fishbackend.dto.TokenDTO;
import com.fishbackend.entity.Invoice;
import com.fishbackend.entity.Token;
import com.fishbackend.repository.CustomerRepo;
import com.fishbackend.repository.InvoiceRepo;
import com.fishbackend.repository.TokenRepo;
import com.fishbackend.service.ReportService;
import com.fishbackend.util.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 6/4/2022
 **/

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private TokenRepo tokenRepo;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private InvoiceRepo invoiceRepo;

    DecimalFormat f = new DecimalFormat("##.00");

    @Override
    public List<ReportDTO> getMoreThan3Days() {
        List<Token> allTokenByMoreThanDay = tokenRepo.getAllTokenByMoreThanDay(3);
        List<ReportDTO> reportDTOS = new ArrayList<>();
        allTokenByMoreThanDay.forEach(token -> {
            reportDTOS.add(new ReportDTO(
                    token.getId(),
                    token.getWhoIssued(),
                    token.getCreateDate().toString(),
                    token.getCustomerId(),
                    customerRepo.findByIdIs(token.getCustomerId()).getShopName(),
                    "",
                    0
            ));
        });
        return reportDTOS;
    }

    @Override
    public List<ReportDTO> getMoreThan7Days() {
        List<Token> allTokenByMoreThanDay = tokenRepo.getAllTokenByMoreThanDay(7);
        List<ReportDTO> reportDTOS = new ArrayList<>();
        allTokenByMoreThanDay.forEach(token -> {
            reportDTOS.add(new ReportDTO(
                    token.getId(),
                    token.getWhoIssued(),
                    token.getCreateDate().toString(),
                    token.getCustomerId(),
                    customerRepo.findByIdIs(token.getCustomerId()).getShopName(),
                    "",
                    0
            ));
        });
        return reportDTOS;
    }

    @Override
    public List<ReportDTO> getDailyStock() {
        List<Token> dailyStock = tokenRepo.getDailyStock();
        List<ReportDTO> reportDTOS = new ArrayList<>();
        dailyStock.forEach(token -> {
            reportDTOS.add(new ReportDTO(
                    token.getId(),
                    token.getWhoIssued(),
                    token.getCreateDate().toString(),
                    token.getCustomerId(),
                    customerRepo.findByIdIs(token.getCustomerId()).getShopName(),
                    "",
                    0
            ));
        });
        return reportDTOS;
    }

    @Override
    public List<ReportDTO> getDailyPaidStock() {
        List<Invoice> dailyIncomeList = invoiceRepo.getDailyIncomeList();
        List<InvoiceDTO> invoiceDTOS = stockMapper.toInvoiceDtoList(dailyIncomeList);
        List<ReportDTO> reportDTOS = new ArrayList<>();
        invoiceDTOS.forEach(invoiceDTO -> {
            Token token = tokenRepo.getTokenById(invoiceDTO.getId());
            reportDTOS.add(new ReportDTO(
                    token.getId(),
                    token.getWhoIssued(),
                    token.getCreateDate().toString(),
                    token.getCustomerId(),
                    customerRepo.findByIdIs(token.getCustomerId()).getShopName(),
                    f.format(invoiceDTO.getTotalPrice()),
                    invoiceDTO.getNumberOfDays()
            ));
        });
        return reportDTOS;
    }
}
