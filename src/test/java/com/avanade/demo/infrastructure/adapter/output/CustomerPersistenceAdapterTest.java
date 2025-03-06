package com.avanade.demo.infrastructure.adapter.output;

import com.avanade.demo.domain.exception.EntityNotFoundException;
import com.avanade.demo.domain.model.Customer;
import com.avanade.demo.domain.model.Segment;
import com.avanade.demo.infrastructure.adapter.output.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerPersistenceAdapterTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerPersistenceAdapter customerPersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCustomerById_NullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerPersistenceAdapter.getCustomerById(null);
        });
    }

    @Test
    void testGetCustomerById_CustomerFound() {
        Segment segment = new Segment();
        segment.setName("Black");

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Teste");
        customer.setSegment(segment);
        Optional<Customer> customerOptional = Optional.of(customer);
        Mockito.when(customerRepository.findById(1L)).thenReturn(customerOptional);

        assertNotNull(customerPersistenceAdapter.getCustomerById(1L));
        assertEquals(1L, customerPersistenceAdapter.getCustomerById(1L).id());
        assertEquals("Teste", customerPersistenceAdapter.getCustomerById(1L).name());
        assertEquals("Black", customerPersistenceAdapter.getCustomerById(1L).segmentName());
    }

    @Test
    void testGetCustomerById_CustomerNotFound() {
        Optional<Customer> customerOptional = Optional.empty();
        Mockito.when(customerRepository.findById(1L)).thenReturn(customerOptional);

        try {
            customerPersistenceAdapter.getCustomerById(1L);
            fail("Expected EntityNotFoundException");
        } catch (EntityNotFoundException ignored) {
        }
    }



}