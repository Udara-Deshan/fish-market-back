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
public class DescriptionDTO implements SupperDTO {
    private Long id;
    private String fishName;
    private double fishWeight;
    private Long coolingRoomId;
    private Long tokenId;
    private double price;
    private int status;
}
