package com.fishbackend.service.impl;

import com.fishbackend.dto.CustomerDTO;
import com.fishbackend.entity.Customer;
import com.fishbackend.exception.EntryDuplicateException;
import com.fishbackend.exception.EntryNotFoundException;
import com.fishbackend.repository.CustomerRepo;
import com.fishbackend.service.CustomerService;
import com.fishbackend.util.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Long saveCustomer(CustomerDTO customerDTO) {
        if (!customerRepo.existsByShopNameIsAndNicIsAndStatusIs(customerDTO.getShopName(), customerDTO.getNic(), 1)) {
            return customerRepo.save(customerMapper.toCustomer(customerDTO)).getId();
        } else {
            throw new EntryDuplicateException("Customer is already exists");
        }
    }

    @Override
    public Long updateCustomer(CustomerDTO customerDTO) {
        System.out.println("customerDTO = " + customerDTO);
        if (customerRepo.existsByIdIs(customerDTO.getId())) {
            Customer customer = customerMapper.toCustomer(customerDTO);
            System.out.println("customer = " + customer);
            return customerRepo.save(customer).getId();
        } else {
            throw new EntryNotFoundException("Customer is not exists");
        }
    }

    @Override
    public Long deleteCustomer(Long customerId) {
        Customer customer = customerRepo.findById(customerId).orElseThrow(() -> (
                new EntryNotFoundException("Customer is not exists")
        ));
        customer.setStatus(0);
        return customerRepo.save(customer).getId();
    }

    @Override
    public CustomerDTO getByCustomerId(Long customerId) {
        return customerMapper.toCustomerDto(
                customerRepo.findById(customerId).orElseThrow(() -> (
                        new EntryNotFoundException("Customer is not exists")
                        )));
    }

    @Override
    public List<CustomerDTO> getAllCustomer(int page, int size) {
        return customerMapper.toCustomerDtoList(
                customerRepo.findAllByStatusIs(1, PageRequest.of(page, size)).getContent());
    }

    @Override
    public List<CustomerDTO> searchCustomer(String value, int page, int size) {
        return customerMapper.toCustomerDtoList(customerRepo.searchCustomer(value, PageRequest.of(page, size)));
    }
}
