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
public class CustomerDTO implements SupperDTO {
    private Long id;
    private String shopName;
    private String shopOwnerName;
    private String nic;
    private String contactNo;
    private int status;
}
