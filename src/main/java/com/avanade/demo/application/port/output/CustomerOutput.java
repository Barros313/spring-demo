package com.avanade.demo.application.port.output;

import com.avanade.demo.application.dto.CreateCustomerDTO;
import com.avanade.demo.application.dto.CustomerDTO;

import java.util.List;

public interface CustomerOutput {

    CustomerDTO getCustomerById(Long id);

    CustomerDTO getCustomerByName(String name);

    void createCustomer(CreateCustomerDTO createCustomerDTO);

    List<CustomerDTO> getAllCustomers(int pageNo, int pageSize);
}
