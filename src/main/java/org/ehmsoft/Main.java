package org.ehmsoft;

import org.ehmsoft.client.LlmClient;
import org.ehmsoft.ui.AgentUI;
import org.ehmsoft.ui.Console;

public class Main {
    public static void main(String[] args) throws Exception {
        // Console console = new Console();

        // console.menu();

        javax.swing.SwingUtilities.invokeLater(AgentUI::new);
    }
}