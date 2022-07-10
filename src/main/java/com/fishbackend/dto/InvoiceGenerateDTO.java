package com.fishbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since 5/28/2022
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceGenerateDTO {
    private String fishName;
    private Double fishWeight;
    private Double price;
    private Double totalPrice;
    private Long invoiceId;
    private int numberOfDays;
    private String roomNumber;
    private String typeName;
}
