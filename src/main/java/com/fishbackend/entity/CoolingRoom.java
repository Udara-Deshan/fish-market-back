package com.fishbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity(name = "cooling_room")
public class CoolingRoom implements SupperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "room_number", nullable = false)
    private String roomNumber;
    @Column(name = "room_type_id", nullable = false)
    private Long roomTypeId;
    @Column(name = "status", columnDefinition = "TINYINT", nullable = false)
    private int status;

}
