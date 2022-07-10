package com.fishbackend.service.impl;

import com.fishbackend.dto.InvoiceGenerateDTO;
import com.fishbackend.dto.QRPrintDTO;
import com.fishbackend.repository.InvoiceRepo;
import com.fishbackend.repository.TokenRepo;
import com.fishbackend.service.PDFGenerateService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since 5/28/2022
 **/
@Service
public class PDFGenerateServiceImpl implements PDFGenerateService {

    @Autowired
    private InvoiceRepo repo;

    @Autowired
    private TokenRepo tokenRepo;

    private List<InvoiceGenerateDTO> objectToInvoicePrintDTOList(Long tokenId){
        List<Object[]> detailsByTokenId = repo.getDetailsByTokenId(tokenId);
        ArrayList<InvoiceGenerateDTO> invoiceGenerateDTOS = new ArrayList<>();

        for (Object[] id :detailsByTokenId) {
            invoiceGenerateDTOS.add(new InvoiceGenerateDTO(
                    id[0].toString(),
                    Double.parseDouble(String.valueOf(id[1])),
                    Double.parseDouble(String.valueOf(id[2])),
                    Double.parseDouble(String.valueOf(id[3])),
                    Long.valueOf(String.valueOf(id[4])),
                    Integer.parseInt(String.valueOf(id[5])),
                    id[6].toString(),
                    id[7].toString()
            ));
        }
        return invoiceGenerateDTOS;
    }

    private List<QRPrintDTO> objectToQrPrintListDTO(Long tokenId){
        List<Object[]> detailsByTokenId = tokenRepo.getDetailsByTokenId(tokenId);
        ArrayList<QRPrintDTO> qrPrintDTOS = new ArrayList<>();
        for (Object[] id:detailsByTokenId) {
            qrPrintDTOS.add(new QRPrintDTO(
               Long.valueOf(id[0].toString()),
               Timestamp.valueOf(id[1].toString()),
               id[2].toString()
            ));
        }
        return qrPrintDTOS;
    }

    @Override
    public void mm54(HttpServletResponse response, Long tokenId) throws IOException, JRException {
        List<InvoiceGenerateDTO> invoiceGenerateDTOS = objectToInvoicePrintDTOList(tokenId);

        InputStream stream = new ClassPathResource("jasper/57mm.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(stream);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(invoiceGenerateDTOS);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("companyName", "Ceylon Fish Marketing Group (pvt) ltd");
        parameters.put("address", "No:69 , Gall Road, Colombo 03.");
        parameters.put("phoneNumber", "Tel : 076 1234-567");
        parameters.put("poweredBy", "poweredBy MetaLab");
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
    }

    @Override
    public void qrPrint57mm(HttpServletResponse response, Long tokenId, String pngName) throws IOException, JRException {
        List<QRPrintDTO> qrPrintDTOS = objectToQrPrintListDTO(tokenId);

        InputStream stream = new ClassPathResource("jasper/57mm_qrprint.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(stream);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(qrPrintDTOS);

        String path = new ClassPathResource("image\\12.png").getPath();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("companyName", "Ceylon Fish Marketing Group (pvt) ltd");
        parameters.put("address", "No:69 , Gall Road, Colombo 03.");
        parameters.put("phoneNumber", "Tel : 076 1234-567");
        parameters.put("poweredBy", "poweredBy MetaLab");
        parameters.put("img", path);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
    }
}
