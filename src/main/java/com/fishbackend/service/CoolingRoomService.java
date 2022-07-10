package com.fishbackend.service;

import com.fishbackend.dto.CoolingRoomDTO;
import com.fishbackend.dto.CoolingRoomTypeDTO;

import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

public interface CoolingRoomService {
    public Long saveCoolingRoom(CoolingRoomDTO coolingRoomDTO);
    public Long updateCoolingRoom(CoolingRoomDTO coolingRoomDTO);
    public Long deleteCoolingRoom(Long coolingRoomId);
    public CoolingRoomDTO getByCoolingRoomId(Long coolingRoomId);
    public List<CoolingRoomDTO> getAllCoolingRoom(int page, int size, int status);

    public Long saveCoolingRoomType(CoolingRoomTypeDTO coolingRoomTypeDTO);
    public Long updateCoolingRoomType(CoolingRoomTypeDTO coolingRoomTypeDTO);
    public Long deleteCoolingRoomType(Long coolingRoomTypeId);
    public CoolingRoomTypeDTO getByCoolingRoomTypeId(Long coolingRoomTypeId);
    public List<CoolingRoomTypeDTO> getAllCoolingRoomType(int page, int size);

    public List<CoolingRoomDTO> getAllAvailableCoolingRooms(Long typeId, int page, int size);
}
