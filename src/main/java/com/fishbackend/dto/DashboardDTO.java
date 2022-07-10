package com.fishbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 6/3/2022
 **/

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DashboardDTO implements SupperDTO {
    private long day3Count;
    private long day7Count;
    private long dailyStock;
    private String dailyIncome;
    private double available;
}
