package com.avanade.demo.infrastructure.adapter.output.repository;

import com.avanade.demo.domain.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testFindByName() {
        assertNotNull(customerRepository.findByName("Maria da Silva"));
    }

    @Test
    void testFindAllCustomers() {
        Pageable pageable = PageRequest.of(0, 3, Sort.by("id").ascending());
        Page<Customer> page = customerRepository.findAllCustomers(pageable);
        assertNotNull(page);
        assertEquals(0, page.getNumber());
        assertEquals(3, page.getSize());
        assertEquals(3, page.getContent().size());
        assertEquals(1L, page.getContent().getFirst().getId());
    }

}