package com.validator.validatorservice.controller.mapper;

import com.validator.validatorservice.dto.CustomerDTO;
import com.validator.validatorservice.model.Customer;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {

    public static Customer makeCustomerFromDTO(CustomerDTO customerDTO){

        return new Customer(customerDTO.getName(),customerDTO.getPhone());
    }

    public static CustomerDTO makeCustomerDTO(Customer customer){

        CustomerDTO.CustomerDTOBuilder customerDTOBuilder =  CustomerDTO.newBuilder().
                setId(customer.getId()).setName(customer.getName()).setPhone(customer.getPhone());

        return customerDTOBuilder.createCustomerDTO();
    }

    public static List<CustomerDTO> makeCustomerDTOList(Collection<Customer> customers){

        return  customers.stream().map(CustomerMapper::makeCustomerDTO).collect(Collectors.toList());
    }
}
