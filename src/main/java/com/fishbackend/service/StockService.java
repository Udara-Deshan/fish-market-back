package com.fishbackend.service;


import com.fishbackend.dto.StockDTO;
import com.fishbackend.dto.TokenDTO;

import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

public interface StockService {
    public Long placeStock(StockDTO stockDTO);
    public Long updateStock(StockDTO stockDTO);
    public Long deleteStock(Long stockId);
    public StockDTO getStockById(Long stockId);
    public List<TokenDTO> getAllStock(int page, int size);
    public List<TokenDTO> searchStock(Long value, int page, int size);
    public TokenDTO getTokenById(Long tokenId);
    public void payStock(Long stockId);
    public Long changeTokenStatus(Long tokenId, int status);

    public long getMoreThanDayTokenCount(int day);
    public List<TokenDTO> getMoreThanDayToken(int day);
}
