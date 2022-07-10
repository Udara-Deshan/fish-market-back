package com.fishbackend.util.mapper;

import com.fishbackend.dto.CoolingRoomDTO;
import com.fishbackend.dto.CoolingRoomTypeDTO;
import com.fishbackend.entity.CoolingRoom;
import com.fishbackend.entity.CoolingRoomType;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@Mapper(componentModel = "spring")
public interface CoolingRoomMapper {
    CoolingRoom toCoolingRoom(CoolingRoomDTO coolingRoomDTO);
    CoolingRoomDTO toCoolingRoomDto(CoolingRoom coolingRoom);
    List<CoolingRoomDTO> toCoolingRoomDtoList(List<CoolingRoom> coolingRooms);

    CoolingRoomType toCoolingRoomType(CoolingRoomTypeDTO coolingRoomTypeDTO);
    CoolingRoomTypeDTO toCoolingRoomTypeDto(CoolingRoomType coolingRoomType);
    List<CoolingRoomTypeDTO> toCoolingRoomTypeDtoList(List<CoolingRoomType> coolingRoomTypes);
}
