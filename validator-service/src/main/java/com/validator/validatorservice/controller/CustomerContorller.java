package com.validator.validatorservice.controller;

import com.validator.validatorservice.controller.mapper.CustomerMapper;
import com.validator.validatorservice.dto.CustomerDTO;
import com.validator.validatorservice.exception.ConstraintsViolationException;
import com.validator.validatorservice.exception.EntityNotFoundException;
import com.validator.validatorservice.model.Customer;
import com.validator.validatorservice.model.domainvalue.Country;
import com.validator.validatorservice.model.domainvalue.Status;
import com.validator.validatorservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/customer")
@CrossOrigin(origins = "http://localhost:8080")
public class CustomerContorller {

    private final CustomerService customerService;

    @Autowired
    public CustomerContorller(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping(value = "/{customerId}")
    public CustomerDTO getCustomer(@PathVariable long customerId) throws EntityNotFoundException {

        return CustomerMapper.makeCustomerDTO(customerService.findCustomer(customerId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@Valid @RequestBody CustomerDTO customerDTO) throws ConstraintsViolationException {

        Customer customer = CustomerMapper.makeCustomerFromDTO(customerDTO);
        return CustomerMapper.makeCustomerDTO(customerService.createCustomer(customer));

    }
    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable long customerId){
        customerService.deleteCustomer(customerId);
    }
    @PutMapping
    public CustomerDTO updateCustomer(@Valid @RequestBody CustomerDTO customerDTO)
            throws ConstraintsViolationException, EntityNotFoundException {

        Customer customer = CustomerMapper.makeCustomerFromDTO(customerDTO);

        return CustomerMapper.makeCustomerDTO(customerService.createCustomer(customer));
    }

    @GetMapping("/customers")
    public List<CustomerDTO> findCustomers(){
        return CustomerMapper.makeCustomerDTOList(customerService.findAllCustomers());
    }

    @GetMapping("/customersFilter")
    public List<CustomerDTO> findCustomersPhoneByCountry(
            @RequestParam(value = "country", required = false) Country country,
            @RequestParam(value = "Status", required = false) Status status){


        System.out.println("Country" + country);
        System.out.println("Valid" + status);

        if(country== null && status == null){
            return CustomerMapper.makeCustomerDTOList(customerService.findAllCustomers());

        }else {
             //country chosen
            if(status != null){
                //status chosen
                return CustomerMapper.makeCustomerDTOList(customerService.findPhoneWithCriteria(country,status));
            }else{
                return CustomerMapper.makeCustomerDTOList(customerService.findAllPhoneNumbersByCountry(country));
            }

        }

    }



}
