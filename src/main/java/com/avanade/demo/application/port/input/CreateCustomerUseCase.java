package com.avanade.demo.application.port.input;

import com.avanade.demo.application.dto.CreateCustomerDTO;

public interface CreateCustomerUseCase {
    void createCustomer(CreateCustomerDTO createCustomerDTO);
}
