package com.validator.validatorservice.repository;

import com.validator.validatorservice.model.Customer;
import com.validator.validatorservice.model.domainvalue.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Long> {

   public List<Customer> findByPhoneStartsWith(String countryCode);
}
