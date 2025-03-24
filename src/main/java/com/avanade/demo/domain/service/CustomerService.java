package com.avanade.demo.domain.service;

import com.avanade.demo.application.dto.CreateCustomerDTO;
import com.avanade.demo.application.dto.CustomerDTO;
import com.avanade.demo.application.port.input.CreateCustomerUseCase;
import com.avanade.demo.application.port.input.GetCustomerUseCase;
import com.avanade.demo.application.port.output.CustomerOutput;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerService implements GetCustomerUseCase, CreateCustomerUseCase {

    private final CustomerOutput customerOutput;

    public CustomerService(CustomerOutput customerOutput) {
        this.customerOutput = customerOutput;
    }

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

    @Override
    public List<CustomerDTO> getAllCustomers(int pageNo, int pageSize) {
        return customerOutput.getAllCustomers(pageNo, pageSize);
    }
}
