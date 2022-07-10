package com.fishbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntryNotFoundException extends RuntimeException {
    public EntryNotFoundException(String message) {
        super(message);
    }
}
