package org.ehmsoft.factory;

import org.ehmsoft.agents.IAgent;
import org.ehmsoft.agents.desarrollo.GeneratorCodeAgent;
import org.ehmsoft.agents.texto.TranslatorTextAgent;
import org.ehmsoft.client.LlmClient;

public class AgentFactory {
    private static final LlmClient CLIENT = new LlmClient();

    public static IAgent create(int option){

        return switch (option){
            case 1 -> new GeneratorCodeAgent(CLIENT);

            case 2 -> new TranslatorTextAgent(CLIENT);

            default -> throw new IllegalArgumentException("Agente no valido");
        };
    }
}
