package com.fishbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InvoiceDTO implements SupperDTO {
    private Long id;
    private String billIssueDate;
    private int numberOfDays;
    private double totalPrice;
    private Long tokenId;
    private int status;
}
