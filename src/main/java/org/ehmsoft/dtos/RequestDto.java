package org.ehmsoft.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Data Transfer Object  para peticiones a la API de LLM
 * */

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestDto(
        String model,
        String prompt,
        boolean stream
) {
}
