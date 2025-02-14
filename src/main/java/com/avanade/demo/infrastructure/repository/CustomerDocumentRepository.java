package com.avanade.demo.infrastructure.repository;

import com.avanade.demo.core.domain.CustomerDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDocumentRepository extends JpaRepository<CustomerDocument, Long> {
}