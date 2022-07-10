package com.fishbackend.service;

import com.fishbackend.dto.UserDTO;

import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/


public interface UserService {
    public Long saveUser(UserDTO userDTO);
    public Long updateUser(UserDTO userDTO);
    public Long deleteUser(Long userId);
    public UserDTO getByUserId(Long userId);
    public List<UserDTO> getAllUser(int page, int size);
}
