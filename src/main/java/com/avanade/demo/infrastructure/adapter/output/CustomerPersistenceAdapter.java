package com.avanade.demo.infrastructure.adapter.output;

import com.avanade.demo.application.dto.CreateCustomerDTO;
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
    private final ContactTypeRepository contactTypeRepository;
    private final CustomerContactRepository customerContactRepository;

    public CustomerPersistenceAdapter(CustomerRepository customerRepository,
                                      SegmentRepository segmentRepository,
                                      DocumentTypeRepository documentTypeRepository,
                                      CustomerDocumentRepository customerDocumentRepository, ContactTypeRepository contactTypeRepository, CustomerContactRepository customerContactRepository) {
        this.customerRepository = customerRepository;
        this.segmentRepository = segmentRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.customerDocumentRepository = customerDocumentRepository;
        this.contactTypeRepository = contactTypeRepository;
        this.customerContactRepository = customerContactRepository;
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        final Customer cust = customerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Client not found"));

        return new CustomerDTO(cust.getId(), cust.getName(), cust.getSegment().getName(),
                Collections.emptyList(), Collections.emptyList());
    }

    @Override
    public CustomerDTO getCustomerByName(String name) {
        final Customer cust = customerRepository.findByName(name).orElseThrow(() ->
                new EntityNotFoundException("Client not found"));

        return new CustomerDTO(cust.getId(), cust.getName(), cust.getSegment().getName(),
                Collections.emptyList(), Collections.emptyList());
    }

    @Transactional
    public void createCustomer(CreateCustomerDTO createCustomerDTO) {
        Customer customer = new Customer();

        customer.setName(createCustomerDTO.name());
        Set<CustomerDocument> documents = new HashSet<>();
        Set<CustomerContact> contacts = new HashSet<>();

        Segment segment = segmentRepository.findByName(createCustomerDTO.segmentName())
                .orElseThrow(() -> new EntityNotFoundException("Segment not found"));

        customer.setSegment(segment);

        customerRepository.save(customer);

        for (CustomerDocumentDTO documentDTO : createCustomerDTO.documents()) {
            DocumentType documentType = documentTypeRepository.findByName(documentDTO.documentType())
                    .orElseThrow(() -> new EntityNotFoundException("Document type not found"));

            CustomerDocument document = new CustomerDocument();
            document.setDocumentType(documentType);
            document.setDocument(documentDTO.documentNumber());
            document.setCustomer(customer);

            documents.add(document);

            customerDocumentRepository.save(document);
        }

        customer.setDocuments(documents);

        for (CustomerContactDTO contactDTO : createCustomerDTO.contacts()) {
            ContactType contactType = contactTypeRepository.findByName(contactDTO.contactType())
                    .orElseThrow(() -> new EntityNotFoundException("Contact type not found"));

            CustomerContact contact = new CustomerContact();
            contact.setContactType(contactType);
            contact.setContactValue(contactDTO.contactValue());
            contact.setCustomer(customer);

            contacts.add(contact);

            customerContactRepository.save(contact);
        }

        customer.setContacts(contacts);
    }

    @Override
    public List<CustomerDTO> getAllCustomers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("id").ascending());

        return customerRepository.findAllCustomers(pageable).stream().map(customer -> {
            List<CustomerDocumentDTO> documentDTOs = customer.getDocuments().stream().map(document -> new CustomerDocumentDTO(
                    document.getDocumentType().getName(),
                    document.getDocument()
            )).toList();

            List<CustomerContactDTO> contactDTOs = customer.getContacts().stream().map(
                    contact -> new CustomerContactDTO(
                            contact.getContactType().getName(),
                            contact.getContactValue()
                    )
            ).toList();

            return new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getSegment().getName(),
                    documentDTOs,
                    contactDTOs);
        }).toList();
    }
}
