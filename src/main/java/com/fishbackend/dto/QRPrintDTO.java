package com.fishbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since 5/28/2022
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QRPrintDTO {
    Long tokenId;
    Timestamp createDate;
    String shopName;
}
