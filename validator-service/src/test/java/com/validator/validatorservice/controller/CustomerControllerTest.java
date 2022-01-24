package com.validator.validatorservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.validator.validatorservice.controller.mapper.CustomerMapper;
import com.validator.validatorservice.dto.CustomerDTO;
import com.validator.validatorservice.exception.ConstraintsViolationException;
import com.validator.validatorservice.model.Customer;
import com.validator.validatorservice.model.domainvalue.Country;
import com.validator.validatorservice.model.domainvalue.Status;
import com.validator.validatorservice.repository.CustomerRepository;
import com.validator.validatorservice.service.CustomerService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {


    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    private Customer customer;

    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerContorller customerContorller;


    @BeforeClass
    public static void setup() {
        MockitoAnnotations.initMocks(CustomerContorller.class);
    }


    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(customerContorller).build();
    }

    private final List<Customer> customers = new ArrayList<>();


    @Test
    public void test_findAllCustomers() throws Exception {

        Customer customer_one = new Customer("Simon", "(256) 714661221");
        Customer customer_two = new Customer("Dan", "(251) 988200000");

        customers.add(customer_one);
        customers.add(customer_two);

        when(customerService.findAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/v1/customer/customers").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andDo(print()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void test_createCustomer()throws Exception {

        Customer customer_one = new Customer("Simon", "(256) 714661221");
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer_one);

        CustomerDTO customerDTO = CustomerMapper.makeCustomerDTO(customer_one);
        String customerJsonInString = mapper.writeValueAsString(customerDTO);

        mockMvc.perform(post("/v1/customer").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(customerJsonInString))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.name", is("Simon")))
              ;

    }

    @Test
    public void test_findValidPhoneNumbersByCountry() throws Exception{

        Customer customer_one = new Customer("Simon", "(256) 714661221");
        customers.add(customer_one);

        when(customerRepository.findByPhoneStartsWith("(256)")).thenReturn(customers);
        when(customerService.findAllPhoneNumbersByCountry(Country.UGANDA)).thenReturn(customers);
        when(customerService.findPhoneWithCriteria(Country.UGANDA, Status.VALID)).thenReturn(customers);

        mockMvc.perform(get("/v1/customer/customersFilter?Status=VALID&country=UGANDA").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andDo(print()).andExpect(jsonPath("$", hasSize(1)));


    }


    @Test
    public void test_findInValidPhoneNumbersByCountry() throws Exception{

        Customer customer_one = new Customer("Simon", "(256) 714661221XXXXXXX");
        customers.add(customer_one);

        when(customerRepository.findByPhoneStartsWith("(256)")).thenReturn(customers);
        when(customerService.findAllPhoneNumbersByCountry(Country.UGANDA)).thenReturn(customers);
        when(customerService.findPhoneWithCriteria(Country.UGANDA, Status.INVALID)).thenReturn(customers);

        mockMvc.perform(get("/v1/customer/customersFilter?Status=INVALID&country=UGANDA").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andDo(print()).andExpect(jsonPath("$", hasSize(1)));


    }
}
