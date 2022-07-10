package com.fishbackend.service;

import net.sf.jasperreports.engine.JRException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since 5/28/2022
 **/
public interface PDFGenerateService {
    public void mm54(HttpServletResponse response, Long tokenId) throws IOException, JRException;
    public void qrPrint57mm(HttpServletResponse response, Long tokenId, String pngName) throws IOException, JRException;
}
