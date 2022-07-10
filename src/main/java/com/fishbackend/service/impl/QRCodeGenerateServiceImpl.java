package com.fishbackend.service.impl;

import com.fishbackend.entity.Token;
import com.fishbackend.service.QRCodeGenerateService;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@Service
public class QRCodeGenerateServiceImpl implements QRCodeGenerateService {

    private static String QRCODE_PATH = "src\\main\\java\\com\\fishbackend\\qr\\";

    @Override
    public void createTokenQRCode(Token token) throws IOException, WriterException {
        String qrcode = QRCODE_PATH+token.getId()+".png";
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(
                token.getId().toString(),
                BarcodeFormat.QR_CODE,
                150,
                150);
        Path path = FileSystems.getDefault().getPath(qrcode);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        try {
            getTokenQRCode(token.getId());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BinaryBitmap getTokenQRCode(Long tokenId) throws NotFoundException, IOException {
        String qrCodePath = QRCODE_PATH+tokenId+".png";
        BufferedImage bufferedImage = ImageIO.read(new File(qrCodePath));
        LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
        return binaryBitmap;
    }

    @Override
    public void deleteTokenQRCode(Long tokenId) {
        String qrCodePath = QRCODE_PATH+tokenId+".png";
        File file = new File(qrCodePath);
        if (file.exists()) {
            file.delete();
        }
    }
}
