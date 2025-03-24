package com.avanade.demo.application.controller;

import com.avanade.demo.application.dto.CreateCustomerDTO;
import com.avanade.demo.application.dto.CustomerDTO;
import com.avanade.demo.domain.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger logger = LogManager.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable long id) {
        CustomerDTO customer = customerService.getCustomerById(id);
        logger.info("Found customer with id: " + id);
        return customer;
    }

    @GetMapping("/name/{customerName}")
    public CustomerDTO getCustomerByName(@PathVariable String customerName) {
        CustomerDTO customer = customerService.getCustomerByName(customerName);
        logger.info("Found customer with name: " + customerName);
        return customer;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCustomer(@RequestBody CreateCustomerDTO createCustomerDTO) {
        customerService.createCustomer(createCustomerDTO);
        logger.info("Created customer with name: " + createCustomerDTO.name());
    }
}