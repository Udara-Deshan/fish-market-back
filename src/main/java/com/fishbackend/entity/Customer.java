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

@Entity(name = "customer")
public class Customer implements SupperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "shop_name", nullable = false)
    private String shopName;
    @Column(name = "shop_owner_name", nullable = false)
    private String shopOwnerName;
    @Column(name = "nic", nullable = false)
    private String nic;
    @Column(name = "contact_number", nullable = false)
    private String contactNo;
    @Column(name = "status", columnDefinition = "TINYINT", nullable = false)
    private int status;
}
