package com.avanade.demo.application.dto;

import java.util.List;

public record CreateCustomerDTO(String name,
                                String segmentName,
                                List<CustomerDocumentDTO> documents,
                                List<CustomerContactDTO> contacts) {
}
