package com.avanade.demo.infrastructure.adapter.output.repository;

import com.avanade.demo.domain.model.Customer;
import com.avanade.demo.domain.model.CustomerCompactView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByName(String name);

    @Query("select c from Customer c " +
            "join fetch c.segment " +
            "left join fetch c.documents d " +
            "left join fetch d.documentType " +
            "left join fetch c.contacts ct " +
            "left join fetch ct.customerContactType")
    Page<Customer> findAllCustomers(Pageable pageable);

    @Query("select new com.avanade.demo.domain.model.CustomerCompactView(c.name, s.name) " +
            "from Customer c join c.segment s " +
            "where s.name = :segmentName")
    Page<CustomerCompactView> findAllCustomersBySegment(String segmentName, Pageable pageable);
}