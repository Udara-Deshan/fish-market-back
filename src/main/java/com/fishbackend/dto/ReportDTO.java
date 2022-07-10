package com.fishbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 6/4/2022
 **/

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReportDTO implements SupperDTO{
    private Long id;
    private String whoIssued;
    private String createDate;
    private Long customerId;
    private String customerName;
    private String price;
    private int noOfDay;
}
