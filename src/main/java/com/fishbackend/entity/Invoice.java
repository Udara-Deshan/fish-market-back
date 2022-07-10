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

@Entity(name = "invoice")
public class Invoice implements SupperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "bill_issue_date", nullable = false)
    private Date billIssueDate;
    @Column(name = "number_of_days", nullable = false)
    private int numberOfDays;
    @Column(name = "total_price", nullable = false)
    private double totalPrice;
    @Column(name = "token_id", nullable = false)
    private Long tokenId;
    @Column(name = "status", columnDefinition = "TINYINT", nullable = false)
    private int status;
}
