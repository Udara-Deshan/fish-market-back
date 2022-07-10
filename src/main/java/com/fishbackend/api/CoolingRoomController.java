package com.fishbackend.api;

import com.fishbackend.dto.CoolingRoomDTO;
import com.fishbackend.dto.CoolingRoomTypeDTO;
import com.fishbackend.service.CoolingRoomService;
import com.fishbackend.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CoolingRoomController {

    @Autowired
    private CoolingRoomService coolingRoomService;

    @PostMapping(path = "rooms", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StandardResponse> saveCoolingRooms(@RequestBody CoolingRoomDTO roomDTO) {
        Long id = coolingRoomService.saveCoolingRoom(roomDTO);
        return new ResponseEntity<>(
                new StandardResponse(201, "success", id),
                HttpStatus.CREATED
        );
    }

    @PostMapping(path = "types", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StandardResponse> saveCoolingRoomsType(@RequestBody CoolingRoomTypeDTO typeDTO) {
        Long id = coolingRoomService.saveCoolingRoomType(typeDTO);
        return new ResponseEntity<>(
                new StandardResponse(201, "success", id),
                HttpStatus.CREATED
        );
    }

    @PutMapping(path = "rooms", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StandardResponse> updateCoolingRooms(@RequestBody CoolingRoomDTO roomDTO) {
        Long id = coolingRoomService.updateCoolingRoom(roomDTO);
        return new ResponseEntity<>(
                new StandardResponse(204, "success", id),
                HttpStatus.OK
        );
    }

    @PutMapping(path = "types", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StandardResponse> updateCoolingRoomsType(@RequestBody CoolingRoomTypeDTO typeDTO) {
        Long id = coolingRoomService.updateCoolingRoomType(typeDTO);
        return new ResponseEntity<>(
                new StandardResponse(204, "success", id),
                HttpStatus.OK
        );
    }

    @DeleteMapping(path = "rooms/{id}")
    public ResponseEntity<StandardResponse> deleteCoolingRooms(@PathVariable Long id) {
        Long roomId = coolingRoomService.deleteCoolingRoom(id);
        return new ResponseEntity<>(
                new StandardResponse(204, "success", roomId),
                HttpStatus.OK
        );
    }

    @DeleteMapping(path = "types/{id}")
    public ResponseEntity<StandardResponse> deleteCoolingRoomsType(@PathVariable Long id) {
        Long roomId = coolingRoomService.deleteCoolingRoomType(id);
        return new ResponseEntity<>(
                new StandardResponse(204, "success", roomId),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "rooms/{id}")
    public ResponseEntity<StandardResponse> getByCoolingRooms(@PathVariable Long id) {
        CoolingRoomDTO roomDTO = coolingRoomService.getByCoolingRoomId(id);
        return new ResponseEntity<>(
                new StandardResponse(200, "success", roomDTO),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "types/{id}")
    public ResponseEntity<StandardResponse> getByCoolingRoomsType(@PathVariable Long id) {
        CoolingRoomTypeDTO typeDTO = coolingRoomService.getByCoolingRoomTypeId(id);
        return new ResponseEntity<>(
                new StandardResponse(200, "success", typeDTO),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "rooms", params = {"page", "size", "status"})
    public ResponseEntity<StandardResponse> getAllCoolingRooms(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam int status) {
        List<CoolingRoomDTO> coolingRoom = coolingRoomService.getAllCoolingRoom(page, size, status);
        return new ResponseEntity<>(
                new StandardResponse(200, "success", coolingRoom),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "types", params = {"page", "size"})
    public ResponseEntity<StandardResponse> getAllCoolingRooms(
            @RequestParam int page,
            @RequestParam int size) {
        List<CoolingRoomTypeDTO> typeDTOList = coolingRoomService.getAllCoolingRoomType(page, size);
        return new ResponseEntity<>(
                new StandardResponse(200, "success", typeDTOList),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "rooms/{typeId}", params = {"page", "size"})
    public ResponseEntity<StandardResponse> getAllAvailableCoolingRoomsByType(
            @PathVariable Long typeId,
            @RequestParam int page,
            @RequestParam int size) {
        List<CoolingRoomDTO> allAvailableCoolingRooms = coolingRoomService.getAllAvailableCoolingRooms(typeId, page, size);
        return new ResponseEntity<>(
                new StandardResponse(200, "success", allAvailableCoolingRooms),
                HttpStatus.OK
        );
    }
}
