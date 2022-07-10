package com.fishbackend.service.impl;

import com.fishbackend.dto.UserDTO;
import com.fishbackend.entity.User;
import com.fishbackend.exception.EntryDuplicateException;
import com.fishbackend.exception.EntryNotFoundException;
import com.fishbackend.repository.UserRepo;
import com.fishbackend.service.UserService;
import com.fishbackend.util.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Long saveUser(UserDTO userDTO) {
        if (!userRepo.existsByUsernameIsAndStatusIs(userDTO.getUsername(), 1)) {
            return userRepo.save(userMapper.toUser(userDTO)).getId();
        } else {
            throw new EntryDuplicateException("User is already exists");
        }
    }

    @Override
    public Long updateUser(UserDTO userDTO) {
        if (userRepo.existsByIdIs(userDTO.getId())) {
            return userRepo.save(userMapper.toUser(userDTO)).getId();
        } else {
            throw new EntryNotFoundException("User is not exists");
        }
    }

    @Override
    public Long deleteUser(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> (
                new EntryNotFoundException("User is not exists")
        ));
        user.setStatus(0);
        return userRepo.save(user).getId();
    }

    @Override
    public UserDTO getByUserId(Long userId) {
        return userMapper.toUserDto(userRepo.findById(userId).orElseThrow(() -> (
                new EntryNotFoundException("User is not exists")
                )));
    }

    @Override
    public List<UserDTO> getAllUser(int page, int size) {
        Page<User> allUser = userRepo.findAllByStatusIs(1, PageRequest.of(page, size));
        return userMapper.toUserDtoList(allUser.getContent());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userRepo.existsByUsernameIsAndStatusIs(username, 1)) {
            User user = userRepo.getUserByUsername(username);
            return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), new ArrayList<>());
        } else {
            throw new EntryNotFoundException("User is not exists");
        }
    }

    public UserDTO getUserByUserName(String username) {
        return userMapper.toUserDto(userRepo.getUserByUsername(username));
    }
}
