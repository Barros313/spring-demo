package com.avanade.demo.application.port.output;

import com.avanade.demo.application.dto.AddCustomerDTO;
import com.avanade.demo.application.dto.CustomerDTO;

import java.util.List;

public interface CustomerOutput {

    CustomerDTO getCustomerById(Long id);

    CustomerDTO getCustomerByName(String name);

    List<CustomerDTO> getAllCustomers(int pageNo, int pageSize);

    void addCustomer(AddCustomerDTO addCustomerDTO);
}
