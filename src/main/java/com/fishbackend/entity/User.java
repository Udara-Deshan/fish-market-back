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

@Entity(name = "user")
public class User implements SupperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "user_name", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "role")
    private String role;
    @Column(name = "status", columnDefinition = "TINYINT", nullable = false)
    private int status;
}
