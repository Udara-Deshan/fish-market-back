package com.fishbackend.util.mapper;

import com.fishbackend.dto.UserDTO;
import com.fishbackend.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDTO userDTO);
    UserDTO toUserDto(User user);
    List<UserDTO> toUserDtoList(List<User> users);
}
