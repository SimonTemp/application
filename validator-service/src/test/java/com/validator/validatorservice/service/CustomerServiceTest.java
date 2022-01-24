package com.validator.validatorservice.service;

import com.validator.validatorservice.ValidatorServiceApplicationTests;
import com.validator.validatorservice.exception.ConstraintsViolationException;
import com.validator.validatorservice.model.Customer;
import com.validator.validatorservice.model.domainvalue.Country;
import com.validator.validatorservice.model.domainvalue.Status;
import com.validator.validatorservice.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest{

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    private final List<Customer> customers = new ArrayList<>();

    @Test
    public void test_findAllCustomers() {

        Customer customer_one = new Customer("Simon", "(256) 714661221");
        Customer customer_two = new Customer("Dan", "(251) 988200000");

        customers.add(customer_one);
        customers.add(customer_two);

        when(customerRepository.findAll()).thenReturn(customers);
        assertNotNull(customerService.findAllCustomers());
        assertEquals(2, customerService.findAllCustomers().size());

    }

    @Test
    public void test_createCustomer()throws ConstraintsViolationException {

        Customer customer_one = new Customer("Simon", "(256) 714661221");
        when(customerRepository.save(customer_one)).thenReturn(customer_one);

        Customer customer = customerService.createCustomer(customer_one);
        assertNotNull(customer);
        assertEquals(customer.getPhone(),customer.getPhone());
    }


    @Test
    public void test_findCarByID() {

        Customer customer_one = new Customer("Simon", "(256) 714661221");
        when(customerRepository.findById(customer_one.getId())).thenReturn(Optional.of(customer_one));

        Customer customer = customerService.findCustomer(customer_one.getId());
        assertNotNull(customer);
        assertEquals(customer.getPhone(),customer.getPhone());
    }

    @Test
    public void test_findAllPhoneNumbersByCountry() {

        Customer customer_one = new Customer("Simon", "(256) 714661221");
        customers.add(customer_one);
        when(customerRepository.findByPhoneStartsWith("(256)")).thenReturn(customers);

        List<Customer> customer_s = customerService.findAllPhoneNumbersByCountry(Country.UGANDA);
        assertNotNull(customer_s);
        assertEquals(1,customer_s.size());
    }


    @Test
    public void test_findValidPhone() {

        Customer customer_one = new Customer("Simon", "(256) 714661221");
        customers.add(customer_one);

        when(customerRepository.findByPhoneStartsWith("(256)")).thenReturn(customers);
        when(customerService.findAllPhoneNumbersByCountry(Country.UGANDA)).thenReturn(customers);

        List<Customer> customer_s = customerService.findPhoneWithCriteria(Country.UGANDA, Status.VALID);
        assertNotNull(customer_s);
        assertEquals(1,customer_s.size());
    }

    @Test
    public void test_findInValidPhone() {

        Customer customer_one = new Customer("Dan", "(256) 714661221");
        customers.add(customer_one);

        when(customerRepository.findByPhoneStartsWith("(256)")).thenReturn(customers);
        when(customerService.findAllPhoneNumbersByCountry(Country.UGANDA)).thenReturn(customers);

        List<Customer> customer_s = customerService.findPhoneWithCriteria(Country.UGANDA, Status.VALID);
        assertNotNull(customer_s);
        assertEquals(1,customer_s.size());
    }


}