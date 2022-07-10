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
public class CoolingRoomDTO implements SupperDTO {
    private Long id;
    private String roomNumber;
    private Long roomTypeId;
    private String typeName;
    private int status;
}
