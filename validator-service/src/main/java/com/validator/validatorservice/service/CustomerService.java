package com.validator.validatorservice.service;

import com.validator.validatorservice.exception.ConstraintsViolationException;
import com.validator.validatorservice.model.Customer;
import com.validator.validatorservice.model.domainvalue.Country;
import com.validator.validatorservice.model.domainvalue.Status;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface CustomerService {

    Customer findCustomer(Long customerId) throws EntityNotFoundException;

    Customer createCustomer(Customer customer) throws ConstraintsViolationException;

    void deleteCustomer(Long customerId) throws  EntityNotFoundException;

    public List<Customer> findAllPhoneNumbersByCountry(Country country);

    public List<Customer> findAllCustomers();

    public List<Customer> findPhoneWithCriteria(Country country, Status status);

}
