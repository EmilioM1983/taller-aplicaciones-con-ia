package org.ehmsoft;

import org.ehmsoft.client.LlmClient;

public class Main {
    public static void main(String[] args) {
        // Instanciar por constructor
        LlmClient client = new LlmClient();

        // Prompt de ejemplo
        String testPrompt = """
                Realiza un articulo de 20 palabras para linkedin sobre ia
                """;

        // Enviar el prompt a ollama y mostrar la respuesta
        System.out.println(client.generate(testPrompt));
    }
}