package com.fishbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginResponseDTO implements SupperDTO {
    private String token;
    private String userType;
}
