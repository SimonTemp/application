package com.validator.validatorservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {


    private Long id;

    @NotNull(message = "Name cannot be null!")
    private String name;

    @NotNull(message = "Phone cannot be null!")
    private String phone;

    public CustomerDTO() {
    }

    public CustomerDTO(Long id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public static CustomerDTOBuilder newBuilder(){

        return new CustomerDTOBuilder();
    }

    public static class CustomerDTOBuilder {

        private Long id;
        private String name;
        private String phone;

        public CustomerDTOBuilder setId(Long id){
            this.id = id;
            return this;
        }
        public CustomerDTOBuilder setName(String name){
            this.name = name;
            return this;
        }
        public CustomerDTOBuilder setPhone(String phone){
            this.phone = phone;
            return this;
        }
        public CustomerDTO createCustomerDTO(){

            return new CustomerDTO(id,name,phone);
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
