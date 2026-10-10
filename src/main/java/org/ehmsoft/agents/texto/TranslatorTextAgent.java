package org.ehmsoft.agents.texto;

import org.ehmsoft.agents.IAgent;
import org.ehmsoft.client.LlmClient;
import org.ehmsoft.prompts.texto.TranslatorTextPrompt;

public class TranslatorTextAgent implements IAgent {

    private final LlmClient llmClient;

    public TranslatorTextAgent(LlmClient llmClient) {
        this.llmClient = llmClient;
    }

    @Override
    public String execute(String text) throws Exception {
        return llmClient.generate(
                TranslatorTextPrompt.build(text)
        );
    }
}
