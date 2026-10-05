package org.ehmsoft.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Data Transfer Object para respuesta de la API de LLM
 * */

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseDto(
        String response
) {
}
