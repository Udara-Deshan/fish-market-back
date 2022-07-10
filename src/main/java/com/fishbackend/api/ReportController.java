package com.fishbackend.api;

import com.fishbackend.dto.ReportDTO;
import com.fishbackend.dto.TokenDTO;
import com.fishbackend.service.ReportService;
import com.fishbackend.service.StockService;
import com.fishbackend.util.StockPDFExporter;
import com.fishbackend.util.TokenPDFExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 6/4/2022
 **/

@RestController
@RequestMapping("/api/v1/report")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping(params = {"type"})
    public void exportPDF(@RequestParam String type, HttpServletResponse response) throws IOException {

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<ReportDTO> reportDTOS = null;
        switch (type) {
            case "3Day" :
                reportDTOS = reportService.getMoreThan3Days();
                break;
            case "7Day" :
                reportDTOS = reportService.getMoreThan7Days();
                break;
            case "daily-stock" :
                reportDTOS = reportService.getDailyStock();
                break;
            case "daily-paid" :
                reportDTOS = reportService.getDailyPaidStock();
                break;
            default:
                break;
        }
        StockPDFExporter stockPDFExporter = new StockPDFExporter(reportDTOS, type);
        stockPDFExporter.export(response);

    }
}
