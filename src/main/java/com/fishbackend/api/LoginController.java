package com.fishbackend.api;

import com.fishbackend.dto.LoginResponseDTO;
import com.fishbackend.dto.UserDTO;
import com.fishbackend.service.UserService;
import com.fishbackend.service.impl.UserServiceImpl;
import com.fishbackend.util.JwtUtil;
import com.fishbackend.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@CrossOrigin
@RestController
public class LoginController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/authenticate", method = RequestMethod.GET)
    public ResponseEntity<Object> createAuthenticationToken(
            @RequestHeader("username") String username,
            @RequestHeader("password") String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,
                            password)
            );
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Incorrect userName and password", e);
        }
        final UserDetails userDetails = userService.loadUserByUsername(username);
        UserDTO userByEmail = userService.getUserByUserName(userDetails.getUsername());

        if (userByEmail.getStatus() != 1){
            throw new RuntimeException("Inactivated user");
        } else {
            String jwt = jwtUtil.generateToken(userDetails);
            return new ResponseEntity<>(
                    new StandardResponse(
                            200,
                            "success",
                            new LoginResponseDTO(jwt,userByEmail.getRole())),
                    HttpStatus.OK
            );
        }
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<Object> CreateNewUser(@RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(),
                            userDTO.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Incorrect userName and password", e);
        }
        final UserDetails userDetails = userService.loadUserByUsername(userDTO.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        UserDTO userByEmail = userService.getUserByUserName(userDetails.getUsername());
        return new ResponseEntity<>(
                new StandardResponse(
                        200,
                        "success",
                        new LoginResponseDTO(jwt,userByEmail.getRole())),
                HttpStatus.OK
        );
    }
}
