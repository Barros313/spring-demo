package com.avanade.demo.infrastructure.adapter.output.repository;

import com.avanade.demo.domain.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByName(String name);

    @Query("SELECT c from Customer c " +
            "JOIN FETCH c.segment " +
            "LEFT JOIN FETCH c.documents dc " +
            "LEFT JOIN FETCH dc.documentType " +
            "LEFT JOIN FETCH c.contacts ct " +
            "LEFT JOIN FETCH ct.contactType ")
    Page<Customer> findAllCustomers(Pageable pageable);
}