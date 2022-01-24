package com.validator.validatorservice.service.customer;

import com.validator.validatorservice.exception.ConstraintsViolationException;
import com.validator.validatorservice.model.Customer;
import com.validator.validatorservice.model.domainvalue.Country;
import com.validator.validatorservice.model.domainvalue.Status;
import com.validator.validatorservice.repository.CustomerRepository;
import com.validator.validatorservice.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findCustomer(Long customerId) throws EntityNotFoundException {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + customerId));
    }

    @Override
    public Customer createCustomer(Customer customerDO) throws ConstraintsViolationException {

        Customer customer;
        try {
            customer = customerRepository.save(customerDO);
        }catch (DataIntegrityViolationException e) {
            LOG.warn("ConstraintsViolationException while creating a customer: {}", customerDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return customer;
    }

    @Override
    @Transactional
    public void deleteCustomer(Long customerId) throws EntityNotFoundException {

       Customer customer = findCustomer(customerId);
      //changed to deactivated

    }



    @Override
    public List<Customer> findAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add);
        return customers;
    }

    public List<Customer>  findAllPhoneNumbersByCountry(Country country){

       return customerRepository.findByPhoneStartsWith(country.getCode());
    }

    public List<Customer> findPhoneWithCriteria(Country country, Status status){

        List<Customer> customers = findAllPhoneNumbersByCountry(country);

        if(status.equals(Status.VALID)){
            //Valid phone
            return  customers.stream()
                    .filter( customer -> customer.getPhone().matches(country.getRegex().trim()))
                    .collect(Collectors.toList());
        }else {
           //invalid phone
            return  customers.stream()
                    .filter( customer -> !customer.getPhone().matches(country.getRegex().trim()))
                    .collect(Collectors.toList());
        }

    }



}
