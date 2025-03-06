package com.avanade.demo.domain.service;

import com.avanade.demo.application.dto.AddCustomerDTO;
import com.avanade.demo.application.dto.CustomerDTO;
import com.avanade.demo.application.port.input.AddCustomerUseCase;
import com.avanade.demo.application.port.input.GetCustomerUseCase;
import com.avanade.demo.application.port.output.CustomerOutput;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerService implements GetCustomerUseCase, AddCustomerUseCase {

    private final CustomerOutput customerOutput;

    public CustomerService(CustomerOutput customerOutput) {
        this.customerOutput = customerOutput;
    }

    public List<CustomerDTO> getAllCustomers(int pageNo, int pageSize) {
        return customerOutput.getAllCustomers(pageNo, pageSize);
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
    public void addCustomer(AddCustomerDTO addCustomerDTO) {
        customerOutput.addCustomer(addCustomerDTO);
    }
}
