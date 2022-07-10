package com.fishbackend.api;

import com.fishbackend.dto.CustomerDTO;
import com.fishbackend.service.CustomerService;
import com.fishbackend.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@RestController
@RequestMapping("/api/v1/customers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StandardResponse> saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Long id = customerService.saveCustomer(customerDTO);
        return new ResponseEntity<>(
                new StandardResponse(201, "success", id),
                HttpStatus.CREATED
        );
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StandardResponse> updateCustomer(@RequestBody CustomerDTO customerDTO) {
        Long id = customerService.updateCustomer(customerDTO);
        return new ResponseEntity<>(
                new StandardResponse(204, "success", id),
                HttpStatus.OK
        );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<StandardResponse> deleteCustomer(@PathVariable Long id) {
        Long customerId = customerService.deleteCustomer(id);
        return new ResponseEntity<>(
                new StandardResponse(204, "success", customerId),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<StandardResponse> getByCustomerId(@PathVariable Long id) {
        CustomerDTO customerDTO = customerService.getByCustomerId(id);
        return new ResponseEntity<>(
                new StandardResponse(200, "success", customerDTO),
                HttpStatus.OK
        );
    }

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<StandardResponse> getAllCustomers(
            @RequestParam int page,
            @RequestParam int size) {
        List<CustomerDTO> allCustomer = customerService.getAllCustomer(page, size);
        return new ResponseEntity<>(
                new StandardResponse(200, "success", allCustomer),
                HttpStatus.OK
        );
    }

    @GetMapping(params = {"value", "page", "size"})
    public ResponseEntity<StandardResponse> searchCustomers(
            @RequestParam String value,
            @RequestParam int page,
            @RequestParam int size) {
        List<CustomerDTO> allCustomer = customerService.searchCustomer(value, page, size);
        return new ResponseEntity<>(
                new StandardResponse(200, "success", allCustomer),
                HttpStatus.OK
        );
    }
}
