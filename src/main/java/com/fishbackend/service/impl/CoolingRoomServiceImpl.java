package com.fishbackend.service.impl;

import com.fishbackend.dto.CoolingRoomDTO;
import com.fishbackend.dto.CoolingRoomTypeDTO;
import com.fishbackend.entity.CoolingRoom;
import com.fishbackend.entity.CoolingRoomType;
import com.fishbackend.exception.EntryDuplicateException;
import com.fishbackend.exception.EntryNotFoundException;
import com.fishbackend.repository.CoolingRoomRepo;
import com.fishbackend.repository.CoolingRoomTypeRepo;
import com.fishbackend.service.CoolingRoomService;
import com.fishbackend.util.mapper.CoolingRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@Service
public class CoolingRoomServiceImpl implements CoolingRoomService {

    @Autowired
    private CoolingRoomRepo coolingRoomRepo;

    @Autowired
    private CoolingRoomTypeRepo coolingRoomTypeRepo;

    @Autowired
    private CoolingRoomMapper coolingRoomMapper;

    @Override
    public Long saveCoolingRoom(CoolingRoomDTO coolingRoomDTO) {
        if (!coolingRoomRepo.existsByRoomNumberIsAndRoomTypeIdIsAndStatusIs(coolingRoomDTO.getRoomNumber(), coolingRoomDTO.getRoomTypeId(), 1)) {
            return coolingRoomRepo.save(coolingRoomMapper.toCoolingRoom(coolingRoomDTO)).getId();
        } else {
            throw new EntryDuplicateException("Cooling room is already exists");
        }
    }

    @Override
    public Long updateCoolingRoom(CoolingRoomDTO coolingRoomDTO) {
        if (coolingRoomRepo.existsByIdIsAndStatusIs(coolingRoomDTO.getId(), 1)) {
            return coolingRoomRepo.save(coolingRoomMapper.toCoolingRoom(coolingRoomDTO)).getId();
        } else {
            throw new EntryNotFoundException("Cooling room is not exists");
        }
    }

    @Override
    public Long deleteCoolingRoom(Long coolingRoomId) {
        CoolingRoom coolingRoom = coolingRoomRepo.findById(coolingRoomId).orElseThrow(() -> (
                new EntryNotFoundException("Cooling room is not exists")
        ));
        coolingRoom.setStatus(0);
        return coolingRoomRepo.save(coolingRoom).getId();
    }

    @Override
    public CoolingRoomDTO getByCoolingRoomId(Long coolingRoomId) {
        CoolingRoom coolingRoom = coolingRoomRepo.findById(coolingRoomId).orElseThrow(() -> (
                new EntryNotFoundException("Cooling room is not exists")
        ));
        CoolingRoomDTO coolingRoomDTO = coolingRoomMapper.toCoolingRoomDto(coolingRoom);
        CoolingRoomType coolingRoomType = coolingRoomTypeRepo.findById(coolingRoomDTO.getRoomTypeId()).orElseThrow(() -> (new EntryNotFoundException("Cooling room is not exists")));
        coolingRoomDTO.setTypeName(coolingRoomType.getTypeName());
        return coolingRoomDTO;
    }

    @Override
    public List<CoolingRoomDTO> getAllCoolingRoom(int page, int size, int status) {
        Page<CoolingRoom> coolingRooms = coolingRoomRepo.findAllByStatusIs(status, PageRequest.of(page, size));
        List<CoolingRoomDTO> coolingRoomDTOS = coolingRoomMapper.toCoolingRoomDtoList(coolingRooms.getContent());
        coolingRoomDTOS.forEach(coolingRoomDTO -> {
            CoolingRoomType coolingRoomType = coolingRoomTypeRepo.findById(coolingRoomDTO.getRoomTypeId()).orElseThrow(() -> (new EntryNotFoundException("Cooling room is not exists")));
            coolingRoomDTO.setTypeName(coolingRoomType.getTypeName());
        });
        return coolingRoomDTOS;
    }

    @Override
    public Long saveCoolingRoomType(CoolingRoomTypeDTO coolingRoomTypeDTO) {
        if (!coolingRoomTypeRepo.existsByTypeNameIsAndStatusIs(coolingRoomTypeDTO.getTypeName(), 1)) {
            return coolingRoomTypeRepo.save(coolingRoomMapper.toCoolingRoomType(coolingRoomTypeDTO)).getId();
        } else {
            throw new EntryDuplicateException("Cooling room type is already exists");
        }
    }

    @Override
    public Long updateCoolingRoomType(CoolingRoomTypeDTO coolingRoomTypeDTO) {
        if (coolingRoomTypeRepo.existsByIdIsAndStatusIs(coolingRoomTypeDTO.getId(), 1)) {
            return coolingRoomTypeRepo.save(coolingRoomMapper.toCoolingRoomType(coolingRoomTypeDTO)).getId();
        } else {
            throw new EntryNotFoundException("Cooling room type is not exists");
        }
    }

    @Override
    public Long deleteCoolingRoomType(Long coolingRoomTypeId) {
        CoolingRoomType roomType = coolingRoomTypeRepo.findById(coolingRoomTypeId).orElseThrow(() -> (
                new EntryNotFoundException("Cooling room type is not exists")));
        roomType.setStatus(0);
        return coolingRoomTypeRepo.save(roomType).getId();
    }

    @Override
    public CoolingRoomTypeDTO getByCoolingRoomTypeId(Long coolingRoomTypeId) {
        return coolingRoomMapper.toCoolingRoomTypeDto(
                coolingRoomTypeRepo.findById(coolingRoomTypeId).orElseThrow(() -> (
                        new EntryNotFoundException("Cooling room type is not exists")))
        );
    }

    @Override
    public List<CoolingRoomTypeDTO> getAllCoolingRoomType(int page, int size) {
        Page<CoolingRoomType> roomTypes = coolingRoomTypeRepo.findAllByStatusIs(1, PageRequest.of(page, size));
        return coolingRoomMapper.toCoolingRoomTypeDtoList(roomTypes.getContent());
    }

    @Override
    public List<CoolingRoomDTO> getAllAvailableCoolingRooms(Long typeId, int page, int size) {
        List<CoolingRoom> content = coolingRoomRepo.getAllAvailableCoolingRooms(typeId, PageRequest.of(page, size)).getContent();
        List<CoolingRoomDTO> coolingRoomDTOS = coolingRoomMapper.toCoolingRoomDtoList(content);
        coolingRoomDTOS.forEach(coolingRoomDTO -> {
            CoolingRoomType coolingRoomType = coolingRoomTypeRepo.findById(coolingRoomDTO.getRoomTypeId()).orElseThrow(() -> (new EntryNotFoundException("Cooling room is not exists")));
            coolingRoomDTO.setTypeName(coolingRoomType.getTypeName());
        });
        return coolingRoomDTOS;
    }
}
