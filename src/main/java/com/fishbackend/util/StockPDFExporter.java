package com.fishbackend.util;

import com.fishbackend.dto.ReportDTO;
import com.fishbackend.dto.TokenDTO;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 6/4/2022
 **/


public class StockPDFExporter {
    private List<ReportDTO> reportDTOS;
    private String type;

    public StockPDFExporter(List<ReportDTO> reportDTOS, String type) {
        this.reportDTOS = reportDTOS;
        this.type = type;
    }

    private void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.WHITE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.TIMES_BOLD);
        font.setSize(10F);
        font.setColor(Color.black);

        cell.setPhrase(new Phrase("Token ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Who issued", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Customer ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Customer Name", font));
        table.addCell(cell);

        if (type.equals("daily-paid")) {
            cell.setPhrase(new Phrase("Price (LKR)", font));
            table.addCell(cell);

            cell.setPhrase(new Phrase("No Of Days", font));
            table.addCell(cell);
        }
    }

    private void writeTableData(PdfPTable table) {
        Font font = FontFactory.getFont(FontFactory.TIMES_BOLD);
        font.setSize(10F);
        font.setColor(Color.black);
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.WHITE);
        cell.setPadding(5);
        for (ReportDTO reportDTO : reportDTOS) {
            cell.setPhrase(new Phrase(reportDTO.getId().toString(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(reportDTO.getWhoIssued(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(reportDTO.getCreateDate(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(reportDTO.getCustomerId().toString(), font));
            table.addCell(cell);

            cell.setPhrase(new Phrase(reportDTO.getCustomerName(), font));
            table.addCell(cell);

            if (type.equals("daily-paid")) {
                cell.setPhrase(new Phrase(reportDTO.getPrice()+"", font));
                table.addCell(cell);
                cell.setPhrase(new Phrase(reportDTO.getNoOfDay()+"", font));
                table.addCell(cell);
            }
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setSize(20F);
        font.setStyle(Font.BOLD);

        Font font2 = FontFactory.getFont(FontFactory.TIMES);
        font2.setSize(15F);
        font2.setStyle(Font.BOLD);

        document.open();

        Paragraph companyName = new Paragraph("Ceylon Fish Marketing Group (pvt) ltd", font);
        Paragraph address = new Paragraph("No:69 , Gall Road, Colombo 03.", font2);
        Paragraph telephoneNumber = new Paragraph("Tel : 076 1234-567", font2);
        Paragraph line = new Paragraph("----------------------------------------------------------------------", font2);
        String name = "";
        switch (type) {
            case "3Day" :
                name = "3 Day More Than Stock Report.";
                break;
            case "7Day" :
                name = "7 Day More Than Stock Report.";
                break;
            case "daily-stock" :
                name = "Daily Stock Report.";
                break;
            case "daily-paid" :
                name = "Daily paid Stock Report.";
                break;
            default:
                break;
        }

        Paragraph reportName = new Paragraph(name, font);
        Paragraph aa = new Paragraph("         ", font);

        Paragraph date = new Paragraph("Date : "+ new Date(System.currentTimeMillis()).toString(), font2);
        Paragraph aaa = new Paragraph("         ", font);

        PdfPTable table;
        if (type.equals("daily-paid")) {
            table = new PdfPTable(7);
            table.setWidthPercentage(100f);
            table.setWidths(new float[] {1.5f, 2.5f, 1.5f, 2.0f, 2.5f, 1.5f,2.5f});
        } else {
            table = new PdfPTable(5);
            table.setWidthPercentage(100f);
            table.setWidths(new float[] {1.5f, 2.5f, 1.5f, 2.0f, 1.5f});
        }
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table);


        companyName.setAlignment(Element.ALIGN_CENTER);
        address.setAlignment(Element.ALIGN_CENTER);
        telephoneNumber.setAlignment(Element.ALIGN_CENTER);
        line.setAlignment(Element.ALIGN_CENTER);
        reportName.setAlignment(Element.ALIGN_CENTER);
        date.setAlignment(Element.ALIGN_LEFT);


        document.add(companyName);
        document.add(address);
        document.add(telephoneNumber);
        document.add(line);
        document.add(reportName);
        document.add(aa);
        document.add(date);
        document.add(aaa);
        document.add(table);


        document.close();

    }

}
