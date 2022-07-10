package com.fishbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity(name = "token")
public class Token implements SupperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "who_issued", nullable = false)
    private String whoIssued;
    @Column(name = "create_date", nullable = false)
    private Date createDate;
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    @Column(name = "status", columnDefinition = "TINYINT", nullable = false)
    private int status;
}
