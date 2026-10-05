package org.ehmsoft.config;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Gestor de configuracion para la conexion con LLM.
 * Cargar propiedades desde el archivo application.properties.
 * */
public class Config {

    /** Almacen de propiedades cargadas desde el archivo de configuracion */
    private final Properties properties;

    /** Constructo que carga la configuracióm ddesde application.properties */
    public Config(){
        properties = new Properties();

        try (InputStream input =
                getClass()
                        .getClassLoader()
                        .getResourceAsStream("application.properties")
                ){
            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Error cargando configuracion",e);
        }
    }

    /**
     * Obtiene la URL del endpoint del LLM
     * */
    public String getUrl() {
        return properties.getProperty("ollama.url");
    }

    /** Obtiene el modelo de IA a utilizar */
    public String getModel(){
        return properties.getProperty("ollama.model");
    }
}
