package com.fishbackend.util.mapper;

import com.fishbackend.dto.DescriptionDTO;
import com.fishbackend.dto.InvoiceDTO;
import com.fishbackend.dto.TokenDTO;
import com.fishbackend.entity.Description;
import com.fishbackend.entity.Invoice;
import com.fishbackend.entity.Token;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@Mapper(componentModel = "spring")
public interface StockMapper {
    @Mapping(target = "createDate", dateFormat = "yyyy-MM-dd")
    Token toToken(TokenDTO tokenDTO);
    @IterableMapping(dateFormat = "yyyy-MM-dd")
    TokenDTO toTokenDto(Token token);
    @IterableMapping(dateFormat = "yyyy-MM-dd")
    List<TokenDTO> tokenDtoList(List<Token> tokens);

    Description toDescription(DescriptionDTO descriptionDTO);
    DescriptionDTO toDescriptionDto(Description description);
    List<DescriptionDTO> toDescriptionDtoList(List<Description> descriptions);
    List<Description> toDescriptions(List<DescriptionDTO> descriptionDTOS);

    @Mapping(target = "billIssueDate", dateFormat = "yyyy-MM-dd")
    Invoice toInvoice(InvoiceDTO invoiceDTO);
    @IterableMapping(dateFormat = "yyyy-MM-dd")
    InvoiceDTO toInvoiceDto(Invoice invoice);
    @IterableMapping(dateFormat = "yyyy-MM-dd")
    List<InvoiceDTO> toInvoiceDtoList(List<Invoice> invoices);
}
