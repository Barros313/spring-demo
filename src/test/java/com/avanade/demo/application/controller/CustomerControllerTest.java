package com.avanade.demo.application.controller;

import com.avanade.demo.application.dto.CustomerContactDTO;
import com.avanade.demo.application.dto.CustomerDTO;
import com.avanade.demo.application.dto.CustomerDocumentDTO;
import com.avanade.demo.application.port.output.CustomerOutput;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerControllerTest {

    private CustomerController customerController;

    @Test
    void testGetAllCustomersWithPageNoZeroed() {
        CustomerOutput customerOutput = Mockito.mock(CustomerOutput.class);
        customerController = new CustomerController(customerOutput);
        assertThrows(IllegalArgumentException.class, () -> customerController.getAllCustomers(0, 10));
    }

    @Test
    void testGetCustomerById() {
        CustomerOutput customerOutput = Mockito.mock(CustomerOutput.class);
        CustomerDocumentDTO customerDocumentDTO = new CustomerDocumentDTO("CPF",
                "12345678901");
        CustomerContactDTO customerContactDTO = new CustomerContactDTO("celular", "123456789");
        CustomerDTO customerDTO = new CustomerDTO(1L, "Teste", "Black",
                List.of((customerDocumentDTO)), List.of(customerContactDTO));
        Mockito.when(customerOutput.getCustomerById(1L)).thenReturn(customerDTO);

        customerController = new CustomerController(customerOutput);
        CustomerDTO testCustomer = customerController.getCustomerById(1L);
        assertEquals(customerDTO.id(), testCustomer.id());
        assertEquals(customerDTO.name(), testCustomer.name());
        assertEquals(customerDTO.segmentName(), testCustomer.segmentName());
        assertEquals(customerDTO.documents().getFirst().documentType(),
                testCustomer.documents().getFirst().documentType());
        assertEquals(customerDTO.documents().getFirst().documentNumber(), testCustomer.documents().getFirst().
                documentNumber());
        assertEquals(customerDTO.contacts().getFirst().contactType(), testCustomer.contacts().getFirst().contactType());
        assertEquals(customerDTO.contacts().getFirst().contactValue(), testCustomer.contacts().getFirst().contactValue());
    }

}
