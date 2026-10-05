package org.ehmsoft.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.ehmsoft.config.Config;
import org.ehmsoft.dtos.RequestDto;
import org.ehmsoft.dtos.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class LlmClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(LlmClient.class);

    // Configuracion del LLM cargada desde application.properties
    private static final Config config = new Config();

    // Cliente HTTP
    private static final HttpClient client = HttpClient.newHttpClient();

    // Mapper de Jackson para JSON
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String generate(String prompt){
        try {
            /** Logger para registro de eventos y errores */
            LOGGER.info("Enviando prompt al LLM: {}", config.getModel());

            // 1. cuerpo de la peticion
            RequestDto requestBody =
                    new RequestDto(
                            config.getModel(),
                            prompt,
                            false
                    );

            // 2. Serializar peticion a json
            String json = mapper.writeValueAsString(requestBody);

            // 3. Construir la peticion HTTP POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(config.getUrl()))
                    .header("Content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            // 4. Enviar la peticion y recibir la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 5. Verificar que la respuesta sea exitosa
            LOGGER.info("Respuesta recibida. Status: {}", response.statusCode());

            // 6. deserializar la respuesta JSON a DTO
            ResponseDto result = mapper.readValue(response.body(), ResponseDto.class);

            // Validar que la respuesta no sea nula o vacía
            if (result == null
                    || result.response() == null
                    || result.response().isBlank()) {

                LOGGER.error("El LLM devolvió una respuesta vacía");

                throw new RuntimeException(
                        "Respuesta vacía recibida desde el LLM"
                );
            }

            // 7. Extraemos el texto generado
            return result.response();

        }catch(Exception e){
            return "Error: " + e.getMessage();
        }
    }
}
