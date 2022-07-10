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

@Entity(name = "description")
public class Description implements SupperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "fish_name", nullable = false)
    private String fishName;
    @Column(name = "fish_weight", nullable = false)
    private double fishWeight;
    @Column(name = "cooling_room_id", nullable = false)
    private Long coolingRoomId;
    @Column(name = "token_id", nullable = false)
    private Long tokenId;
    @Column(name = "price")
    private double price;
    @Column(name = "status", columnDefinition = "TINYINT", nullable = false)
    private int status;
}
