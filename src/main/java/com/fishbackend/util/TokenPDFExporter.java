package com.fishbackend.util;

import com.fishbackend.dto.TokenDTO;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/
public class TokenPDFExporter {
    private TokenDTO tokenDTO;

    public TokenPDFExporter(TokenDTO tokenDTO) {
        this.tokenDTO = tokenDTO;
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A7, 10, 10, 0, 0);
        PdfWriter.getInstance(document, response.getOutputStream());

        Image image = Image.getInstance("C:\\Users\\Udara\\Desktop\\fish-backend\\fish-backend\\src\\main\\java\\com\\fishbackend\\qr\\"+tokenDTO.getId()+".png");

        document.open();

        Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setSize(12F);
        font.setStyle(Font.BOLD);

        Font font2 = FontFactory.getFont(FontFactory.TIMES);
        font2.setSize(10F);
        font2.setStyle(Font.BOLD);

        Font tokenFont = FontFactory.getFont(FontFactory.TIMES);
        tokenFont.setSize(10F);
        tokenFont.setStyle(Font.BOLD);

        Paragraph companyName = new Paragraph("Ceylon Fish Marketing Group (pvt) ltd", font);
        Paragraph address = new Paragraph("No:69 , Gall Road, Colombo 03.", font2);
        Paragraph telephoneNumber = new Paragraph("Tel : 076 1234-567", font2);
        Paragraph line = new Paragraph("-------------------------------------------", font2);
        Paragraph tokenId = new Paragraph("Token ID : "+tokenDTO.getId(), tokenFont);
        Paragraph createDate = new Paragraph("Create Date : "+tokenDTO.getCreateDate(), tokenFont);
        Paragraph shopName = new Paragraph("Shop Name : "+tokenDTO.getCustomerName(), tokenFont);
        Paragraph line2 = new Paragraph("-------------------------------------------", font2);
        Paragraph thank = new Paragraph("Thank You", tokenFont);
        Paragraph powerBy = new Paragraph("Powered by : MetaLab", tokenFont);

        companyName.setAlignment(Element.ALIGN_CENTER);
        address.setAlignment(Element.ALIGN_CENTER);
        telephoneNumber.setAlignment(Element.ALIGN_CENTER);
        line.setAlignment(Element.ALIGN_CENTER);
        tokenId.setAlignment(Element.ALIGN_LEFT);
        createDate.setAlignment(Element.ALIGN_LEFT);
        shopName.setAlignment(Element.ALIGN_LEFT);
//        image.setAbsolutePosition(30, 40);
        image.setAlignment(Element.ALIGN_CENTER);
        image.scaleAbsolute(110, 110);
        line2.setAlignment(Element.ALIGN_CENTER);
        thank.setAlignment(Element.ALIGN_CENTER);
        powerBy.setAlignment(Element.ALIGN_CENTER);


        document.add(companyName);
        document.add(address);
        document.add(telephoneNumber);
        document.add(line);
        document.add(tokenId);
        document.add(createDate);
        document.add(shopName);
        document.add(image);
        document.add(line2);
        document.add(thank);
        document.add(powerBy);



        document.close();
    }
}
