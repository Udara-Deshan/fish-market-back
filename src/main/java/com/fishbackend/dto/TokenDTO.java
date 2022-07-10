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
public class TokenDTO implements SupperDTO {
    private Long id;
    private String whoIssued;
    private String createDate;
    private Long customerId;
    private String customerName;
    private int status;
}
