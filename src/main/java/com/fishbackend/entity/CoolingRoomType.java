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

@Entity(name = "cooling_room_type")
public class CoolingRoomType implements SupperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "type_name", nullable = false)
    private String typeName;
    @Column(name = "type_price", nullable = false)
    private double typePrice;
    @Column(name = "status", columnDefinition = "TINYINT", nullable = false)
    private int status;
}
