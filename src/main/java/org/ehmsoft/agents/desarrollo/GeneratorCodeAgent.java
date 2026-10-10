package org.ehmsoft.agents.desarrollo;

import org.ehmsoft.agents.IAgent;
import org.ehmsoft.client.LlmClient;
import org.ehmsoft.prompts.desarrollo.GeneratorCodePrompt;

public class GeneratorCodeAgent implements IAgent {

    private final LlmClient llmClient;

    public GeneratorCodeAgent(LlmClient llmClient) {
        this.llmClient = llmClient;
    }

    @Override
    public String execute(String text){
        return llmClient.generate(
                GeneratorCodePrompt.build(text)
        );
    }
}
