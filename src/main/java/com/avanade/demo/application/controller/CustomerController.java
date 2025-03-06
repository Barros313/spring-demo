package com.avanade.demo.application.controller;

import com.avanade.demo.application.dto.AddCustomerDTO;
import com.avanade.demo.application.dto.CustomerDTO;
import com.avanade.demo.application.port.output.CustomerOutput;
import com.avanade.demo.domain.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger logger = LogManager.getLogger(CustomerController.class);

    private final CustomerOutput customerOutput;

    public CustomerController(CustomerOutput customerOutput) {
        this.customerOutput = customerOutput;
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers(@RequestParam(defaultValue = "1") int pageNo,
                                             @RequestParam(defaultValue = "10") int pageSize) {
        return customerOutput.getAllCustomers(pageNo, pageSize);
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable long id) {
        CustomerDTO customer = customerOutput.getCustomerById(id);
        logger.info("Found customer with id: " + id);
        return customer;
    }

    @GetMapping("/name/{customerName}")
    public CustomerDTO getCustomerByName(@PathVariable String customerName) {
        CustomerDTO customer = customerOutput.getCustomerByName(customerName);
        logger.info("Found customer with name: " + customerName);
        return customer;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCustomer(@RequestBody AddCustomerDTO addCustomerDTO) {
        customerOutput.addCustomer(addCustomerDTO);
    }
}