package com.avanade.demo.infrastructure.adapter.output;

import com.avanade.demo.application.dto.AddCustomerDTO;
import com.avanade.demo.application.dto.CustomerContactDTO;
import com.avanade.demo.application.dto.CustomerDTO;
import com.avanade.demo.application.dto.CustomerDocumentDTO;
import com.avanade.demo.application.port.output.CustomerOutput;
import com.avanade.demo.domain.exception.EntityNotFoundException;
import com.avanade.demo.domain.model.*;
import com.avanade.demo.infrastructure.adapter.output.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CustomerPersistenceAdapter implements CustomerOutput {

    private final CustomerRepository customerRepository;
    private final SegmentRepository segmentRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final CustomerDocumentRepository customerDocumentRepository;
    private final CustomerContactTypeRepository customerContactTypeRepository;
    private final CustomerContactRepository customerContactRepository;

    public CustomerPersistenceAdapter(CustomerRepository customerRepository,
                                      SegmentRepository segmentRepository,
                                      DocumentTypeRepository documentTypeRepository,
                                      CustomerDocumentRepository customerDocumentRepository,
                                      CustomerContactTypeRepository customerContactTypeRepository,
                                      CustomerContactRepository customerContactRepository) {
        this.customerRepository = customerRepository;
        this.segmentRepository = segmentRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.customerDocumentRepository = customerDocumentRepository;
        this.customerContactTypeRepository = customerContactTypeRepository;
        this.customerContactRepository = customerContactRepository;
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id do cliente não pode ser nulo");
        }
        final Customer cust = customerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Cliente não encontrado"));

        return new CustomerDTO(cust.getId(), cust.getName(), cust.getSegment().getName(),
                Collections.emptyList(), Collections.emptyList());
    }

    @Override
    public CustomerDTO getCustomerByName(String name) {
        final Customer cust = customerRepository.findByName(name).orElseThrow(() ->
                new EntityNotFoundException("Cliente não encontrado"));

        return new CustomerDTO(cust.getId(), cust.getName(), cust.getSegment().getName(),
                Collections.emptyList(), Collections.emptyList());
    }

    @Override
    public List<CustomerDTO> getAllCustomers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("id").ascending());
        return customerRepository.findAll(pageable).stream().map(customer -> {
                List<CustomerDocumentDTO> docs = customer.getDocuments().stream().map(
                        document -> new CustomerDocumentDTO(document.getDocumentType().getName(),
                                document.getDocument())).toList();

                List<CustomerContactDTO> contacts = customer.getContacts().stream().map(
                        contact -> new CustomerContactDTO(contact.getCustomerContactType().getName(),
                                contact.getContactValue())).toList();

                return new CustomerDTO(customer.getId(), customer.getName(),
                        customer.getSegment().getName(), docs, contacts);
            }).toList();
    }

    @Transactional
    public void addCustomer(AddCustomerDTO addCustomerDTO) {
        Customer cust = new Customer();
        cust.setName(addCustomerDTO.name());
        Set<CustomerDocument> documents = new HashSet<>();
        Set<CustomerContact> contacts = new HashSet<>();

        Segment segment = segmentRepository.findByName(addCustomerDTO.segmentName()).orElseThrow(() ->
                new EntityNotFoundException("Segmento não encontrado"));
        cust.setSegment(segment);
        customerRepository.save(cust);

        for (CustomerDocumentDTO documentDTO: addCustomerDTO.documents()) {
            DocumentType documentType = documentTypeRepository.findByName(documentDTO.documentType()).orElseThrow(() ->
                    new EntityNotFoundException("Tipo de documento não encontrado"));

            CustomerDocument document = new CustomerDocument();
            document.setDocumentType(documentType);
            document.setDocument(documentDTO.documentNumber());
            document.setCustomer(cust);
            documents.add(document);
            customerDocumentRepository.save(document);
        }
        cust.setDocuments(documents);

        for (CustomerContactDTO contactDTO: addCustomerDTO.contacts()) {
            CustomerContactType contactType = customerContactTypeRepository.findByName(contactDTO.contactType()).orElseThrow(() ->
                    new EntityNotFoundException("Tipo de contato não encontrado"));

            CustomerContact contact = new CustomerContact();
            contact.setCustomerContactType(contactType);
            contact.setContactValue(contactDTO.contactValue());
            contact.setCustomer(cust);
            contacts.add(contact);
            customerContactRepository.save(contact);
        }
        cust.setContacts(contacts);
    }

}
