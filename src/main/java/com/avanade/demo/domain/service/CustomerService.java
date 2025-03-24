package com.avanade.demo.domain.service;

import com.avanade.demo.application.dto.CreateCustomerDTO;
import com.avanade.demo.application.dto.CustomerDTO;
import com.avanade.demo.application.port.input.CreateCustomerUseCase;
import com.avanade.demo.application.port.input.GetCustomerUseCase;
import com.avanade.demo.application.port.output.CustomerOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerService implements GetCustomerUseCase, CreateCustomerUseCase {

    @Autowired
    private CustomerOutput customerOutput;

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerOutput.getCustomerById(id);
    }

    @Override
    public CustomerDTO getCustomerByName(String customerName) {
        return customerOutput.getCustomerByName(customerName);
    }

    @Override
    public void createCustomer(CreateCustomerDTO createCustomerDTO) {
        customerOutput.createCustomer(createCustomerDTO);
    }
}
