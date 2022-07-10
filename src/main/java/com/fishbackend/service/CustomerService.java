package com.fishbackend.service;

import com.fishbackend.dto.CustomerDTO;

import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

public interface CustomerService {
    public Long saveCustomer(CustomerDTO customerDTO);
    public Long updateCustomer(CustomerDTO customerDTO);
    public Long deleteCustomer(Long customerId);
    public CustomerDTO getByCustomerId(Long customerId);
    public List<CustomerDTO> getAllCustomer(int page, int size);
    public List<CustomerDTO> searchCustomer(String value, int page, int size);
}
