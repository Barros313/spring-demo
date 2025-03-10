package com.avanade.demo.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerDTO(Long id, String name, String segmentName, List<CustomerDocumentDTO> documents,
                          List<CustomerContactDTO> contacts) {
}