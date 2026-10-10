package org.ehmsoft.prompts.desarrollo;

public class GeneratorCodePrompt {

    public static String build(String text){
        return """
                Eres un programador. Genera el código solicitado por el usuario. 
                No incluyas explicaciones ni sugerencias
                
                %s
                """.formatted(text);
    }
}
