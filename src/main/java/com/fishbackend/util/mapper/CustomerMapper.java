package com.fishbackend.util.mapper;

import com.fishbackend.dto.CustomerDTO;
import com.fishbackend.entity.Customer;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author : Udara Deshan <udaradeshan.ud@gmail.com>
 * @since : 5/15/2022
 **/

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomer(CustomerDTO customerDTO);
    CustomerDTO toCustomerDto(Customer customer);
    List<CustomerDTO> toCustomerDtoList(List<Customer> customers);
}
