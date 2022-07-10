package com.fishbackend.repository;

import com.fishbackend.entity.CoolingRoom;
import com.fishbackend.entity.CoolingRoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@Repository
public interface CoolingRoomTypeRepo extends JpaRepository<CoolingRoomType, Long> {
    boolean existsByTypeNameIsAndStatusIs(String name, int status);
    boolean existsByIdIsAndStatusIs(Long id, int status);
    Page<CoolingRoomType> findAllByStatusIs(int status, Pageable page);

    @Query(value = "select * from cooling_room_type where id = ?1", nativeQuery = true)
    CoolingRoomType findByIdIs(Long id);
}
