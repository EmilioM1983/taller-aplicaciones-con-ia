package org.ehmsoft.ui;

import org.ehmsoft.agents.IAgent;
import org.ehmsoft.agents.desarrollo.GeneratorCodeAgent;
import org.ehmsoft.agents.texto.TranslatorTextAgent;
import org.ehmsoft.client.LlmClient;
import org.ehmsoft.factory.AgentFactory;

import java.util.Scanner;

public class Console {

    private Scanner scanner = new Scanner(System.in);

    private String prompt;

    private int option;

    public Console(){

    }

    public void menu() throws Exception{
        //GeneratorCodeAgent agent = new GeneratorCodeAgent(new LlmClient());

        //Solicitar al usuario las especificaciones del codigo
        //System.out.println("Ingrese las especificaciones del código que desea generar: ");
        //prompt = scanner.nextLine();

        //Ejecutar el agente y mostrar el resultado
        //System.out.println(agent.execute(prompt));

        while (option!=3){
            System.out.println("Seleccione una opcion");
            System.out.println("1. Generador de codigo");
            System.out.println("2. Traductor al castellano");
            System.out.println("3. Salir");

            option = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (option) {
                    case 1:{
                        System.out.println("Ingrese las especificacion del código que desea generar");
                        prompt = scanner.nextLine();
                        IAgent agent = AgentFactory.create(option);
                        System.out.println(agent.execute(prompt));
                        break;
                    }
                    case 2:{
                        System.out.println("Ingrese el texto a traducir");
                        prompt = scanner.nextLine();
                        IAgent agent = AgentFactory.create(option);
                        System.out.println(agent.execute(prompt));
                        break;
                    }
                    case 3:
                        System.out.println("Saliendo...");

                        break;

                    default:
                        System.out.println("Opcion invalida. KIntente nuevamente");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un numero valido");
            }

        }
    }
}
