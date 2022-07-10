package com.fishbackend.service;

import com.fishbackend.entity.Token;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import java.io.IOException;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

public interface QRCodeGenerateService {
    public void createTokenQRCode(Token token) throws IOException, WriterException;
    public BinaryBitmap getTokenQRCode(Long tokenId) throws NotFoundException, IOException;
    public void deleteTokenQRCode(Long tokenId);
}
