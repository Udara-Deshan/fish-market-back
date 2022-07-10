package com.fishbackend.service.impl;

import com.fishbackend.dto.DashboardDTO;
import com.fishbackend.repository.CoolingRoomRepo;
import com.fishbackend.repository.InvoiceRepo;
import com.fishbackend.repository.TokenRepo;
import com.fishbackend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 6/3/2022
 **/

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private CoolingRoomRepo coolingRoomRepo;

    @Autowired
    private TokenRepo tokenRepo;

    @Autowired
    private InvoiceRepo invoiceRepo;


    @Override
    public DashboardDTO getDashboardData() {
        long allCount = coolingRoomRepo.countAllCoolingRoomCount();
        long availableCount = coolingRoomRepo.countAllAvailableCoolingRoomCount();
        double percentage = Double.parseDouble(availableCount+"") / Double.parseDouble(allCount+"") * 100;

        DecimalFormat f = new DecimalFormat("##.00");
        String income;
        try {
            income = f.format(invoiceRepo.getDailyIncome());
        } catch (Exception e) {
            income = "0.00";
        }


        return new DashboardDTO(
                tokenRepo.getAllTokenByMoreThanDayCount(3),
                tokenRepo.getAllTokenByMoreThanDayCount(7),
                tokenRepo.getDailyStockCount(),
                income,
                percentage
        );
    }
}
