package com.avanade.demo.application.port.input;

import com.avanade.demo.application.dto.CustomerDTO;

import java.util.List;

public interface GetCustomerUseCase {

    CustomerDTO getCustomerById(Long id);

    CustomerDTO getCustomerByName(String username);

    List<CustomerDTO> getAllCustomers(int pageNo, int pageSize);

    List<CustomerDTO> getAllCustomersBySegment(String segmentName);
}