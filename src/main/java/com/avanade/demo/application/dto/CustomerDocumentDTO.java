package com.avanade.demo.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CustomerDocumentDTO(String documentType, String documentNumber) {
}
