package com.fishbackend.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 6/23/2022
 **/

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TestController {
    @GetMapping()
    public String test() {
        return "Success";
    }
}
