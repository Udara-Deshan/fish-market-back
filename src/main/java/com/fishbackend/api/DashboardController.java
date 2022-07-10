package com.fishbackend.api;

import com.fishbackend.dto.DashboardDTO;
import com.fishbackend.service.DashboardService;
import com.fishbackend.service.StockService;
import com.fishbackend.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 6/3/2022
 **/

@RestController
@RequestMapping("/Dashboard")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<StandardResponse> getDashboardData() {
        DashboardDTO dashboardData = dashboardService.getDashboardData();
        return new ResponseEntity<>(
                new StandardResponse(204, "success", dashboardData),
                HttpStatus.OK
        );
    }
}
