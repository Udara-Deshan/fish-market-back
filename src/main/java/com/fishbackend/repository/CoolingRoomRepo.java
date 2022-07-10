package com.fishbackend.repository;

import com.fishbackend.entity.CoolingRoom;
import com.fishbackend.entity.CoolingRoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@Repository
public interface CoolingRoomRepo extends JpaRepository<CoolingRoom, Long> {
    boolean existsByRoomNumberIsAndRoomTypeIdIsAndStatusIs(String roomNumber, Long typeId, int status);
    boolean existsByIdIsAndStatusIs(Long id, int status);
    Page<CoolingRoom> findAllByStatusIs(int status, Pageable page);
    CoolingRoom findByIdIs(Long coolingRoomId);

    @Query(value = "select crt.type_price from cooling_room left join cooling_room_type crt on cooling_room.room_type_id = crt.id where cooling_room.id = ?1", nativeQuery = true)
    double getCoolingRoomPriceByRoomId(Long roomId);

    @Transactional
    @Modifying
    @Query(value = "update cooling_room set status = ?1 where id = ?2", nativeQuery = true)
    void changeCoolingRoomStatus(int status, Long id);

    @Query(value = "select * from cooling_room where room_type_id = ?1 and status  = 1", nativeQuery = true)
    Page<CoolingRoom> getAllAvailableCoolingRooms(Long typeId, Pageable page);

    @Query(value = "select count(id) from cooling_room where status not in (0)", nativeQuery = true)
    long countAllCoolingRoomCount();

    @Query(value = "select count(id) from cooling_room where status = 1", nativeQuery = true)
    long countAllAvailableCoolingRoomCount();
}
