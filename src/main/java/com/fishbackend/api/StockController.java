package com.fishbackend.api;

import com.fishbackend.dto.StockDTO;
import com.fishbackend.dto.TokenDTO;
import com.fishbackend.service.PDFGenerateService;
import com.fishbackend.service.StockService;
import com.fishbackend.util.StandardResponse;
import com.fishbackend.util.TokenPDFExporter;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/16/2022
 **/

@RestController
@RequestMapping("/api/v1/stocks")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private PDFGenerateService pdfGenerateService;

    @GetMapping(path = "/pdf/57test")
    public void test(HttpServletResponse response) throws IOException, JRException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey,headerValue);
        pdfGenerateService.mm54(response,1L);

    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void saveStock(
            @RequestBody StockDTO stockDTO, HttpServletResponse response) throws IOException, JRException {

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        Long tokenId = stockService.placeStock(stockDTO);
        TokenDTO tokenById = stockService.getTokenById(tokenId);
        TokenPDFExporter exporter = new TokenPDFExporter(tokenById);
        exporter.export(response);
//        pdfGenerateService.qrPrint57mm(response,tokenId, tokenId+".png");
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StandardResponse> updateStock(@RequestBody StockDTO stockDTO) {
        Long id = stockService.updateStock(stockDTO);
        return new ResponseEntity<>(
                new StandardResponse(204, "success", id),
                HttpStatus.OK
        );
    }

    @PatchMapping(path = "/{id}")
    public void payStock(@PathVariable Long id, HttpServletResponse response) throws IOException, JRException {

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        stockService.payStock(id);
        pdfGenerateService.mm54(response,id);
        stockService.changeTokenStatus(id, 2);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<StandardResponse> deleteStock(@PathVariable Long id) {
        Long stockId = stockService.deleteStock(id);
        return new ResponseEntity<>(
                new StandardResponse(204, "success", stockId),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<StandardResponse> getByStockId(@PathVariable Long id) {
        StockDTO stockDTO = stockService.getStockById(id);
        return new ResponseEntity<>(
                new StandardResponse(200, "success", stockDTO),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/search", params = {"value", "page", "size"})
    public ResponseEntity<StandardResponse> searchStockId(
            @RequestParam Long value,
            @RequestParam int page,
            @RequestParam int size) {
        List<TokenDTO> tokenDTOS = stockService.searchStock(value, page, size);
        return new ResponseEntity<>(
                new StandardResponse(200, "success", tokenDTOS),
                HttpStatus.OK
        );
    }

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<StandardResponse> getAllStock(
            @RequestParam int page,
            @RequestParam int size) {
        List<TokenDTO> allStock = stockService.getAllStock(page, size);
        return new ResponseEntity<>(
                new StandardResponse(200, "success", allStock),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "more/count", params = {"day"})
    public ResponseEntity<StandardResponse> getAllStockCount(@RequestParam int day) {
        long tokenCount = stockService.getMoreThanDayTokenCount(day);
        return new ResponseEntity<>(
                new StandardResponse(200, "success", tokenCount),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "more/token", params = {"day"})
    public ResponseEntity<StandardResponse> getAllStockToken(
            @RequestParam int day) {
        List<TokenDTO> allStock = stockService.getMoreThanDayToken(day);
        return new ResponseEntity<>(
                new StandardResponse(200, "success", allStock),
                HttpStatus.OK
        );
    }
}
