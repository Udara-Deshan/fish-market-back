package com.fishbackend.advisor;

import com.fishbackend.exception.EntryDuplicateException;
import com.fishbackend.exception.EntryNotFoundException;
import com.fishbackend.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.UnexpectedTypeException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@RestControllerAdvice
public class AppWideExceptionHandler {

    @ExceptionHandler({EntryDuplicateException.class})
    public ResponseEntity<StandardResponse> entryDuplicateHandler(EntryDuplicateException e){
        return new ResponseEntity<>(
                new StandardResponse(404, "Entry Duplicate Exception", e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EntryNotFoundException.class})
    public ResponseEntity<StandardResponse> entryNotFoundHandler(EntryNotFoundException e){
        return new ResponseEntity<>(
                new StandardResponse(404, "Entry Not Found Exception", e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Object> badCredentialHandler(BadCredentialsException e){
        return new ResponseEntity<>(
                new StandardResponse(401, "Bad Credentials Exception", e.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({UnexpectedTypeException.class})
    protected ResponseEntity<StandardResponse> apiValidationHandler(UnexpectedTypeException e){
        return new ResponseEntity<>(
                new StandardResponse(400, "Validation Exception", e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        Map<String,String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) ->{
            errors.put(
                    ((FieldError) error).getField(),
                    error.getDefaultMessage()
            );
        });
        return new ResponseEntity<>(
                new StandardResponse(400, "Validation Exception", errors),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<StandardResponse> handleNullPointException(NullPointerException ex){
        ex.printStackTrace();
        return new ResponseEntity<>(
                new StandardResponse(404, "Null Point Error", ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<StandardResponse> handleSqlException(SQLException ex){
        return new ResponseEntity<>(
                new StandardResponse(404, "SQL Error", ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardResponse> handleGlobalException(Exception ex){
        ex.printStackTrace();
        return new ResponseEntity<>(
                new StandardResponse(500, "Server Error", ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

}
