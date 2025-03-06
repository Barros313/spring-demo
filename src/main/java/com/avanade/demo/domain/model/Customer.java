package com.avanade.demo.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "Nome é obrigatório")
    private String name;

    @ManyToOne
    @JoinColumn(name = "segment_id", nullable = false)
    private Segment segment;

    @OneToMany
    @JoinColumn(name = "customer_id")
    private Set<CustomerDocument> documents;

    @OneToMany
    @JoinColumn(name = "customer_id")
    private Set<CustomerContact> contacts;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public Set<CustomerDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<CustomerDocument> documents) {
        this.documents = documents;
    }

    public Set<CustomerContact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<CustomerContact> contacts) {
        this.contacts = contacts;
    }
}