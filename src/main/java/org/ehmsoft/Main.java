package org.ehmsoft;

import static org.ehmsoft.OllamaClient.generate;

public class Main {
    public static void main(String[] args) {
        // Prompt de ejemplo
        String testPrompt = """
                Realiza un articulo de 20 palabras para linkedin sobre ia
                """;

        // Enviar el prompt a ollama y mostrar la respuesta
        System.out.println(generate(testPrompt));
    }
}