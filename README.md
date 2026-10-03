# agentesia 🤖

Cliente en **Java** que consume la API local de **Ollama** para generar texto con modelos de lenguaje de forma sencilla.

> 📚 **Proyecto del taller de Agentes de IA** dictado por **Emilio Mayer** para la **Universidad Provincial de Córdoba (UPC)**.

---

## 📋 Descripción

`agentesia` es una aplicación de consola que envía un *prompt* a un modelo de lenguaje (LLM) ejecutado localmente a través de [Ollama](https://ollama.com) y muestra la respuesta generada. El proyecto está pensado como base para aprender a integrar modelos de IA en aplicaciones Java.

## ✨ Características

- Comunicación con la API de Ollama mediante `java.net.http.HttpClient` (incluido en el JDK).
- Construcción del *payload* JSON y parseo de la respuesta con **Jackson**.
- Ejecución sin *streaming* (`stream: false`): se recibe la respuesta completa de una sola vez.
- Fácil de extender para construir agentes más complejos.

## 🛠️ Tecnologías

| Tecnología | Versión / Detalle |
|---|---|
| Java | 21 |
| Maven | Gestión de dependencias y build |
| Jackson (`jackson-databind`) | 2.20.0 — procesamiento de JSON |
| Java HttpClient | API HTTP nativa del JDK |
| Ollama | Servidor local de modelos de IA |
| Modelo | `llama3.2:latest` |

## 📦 Requisitos previos

1. **JDK 21** o superior.
2. **Maven**.
3. **Ollama** instalado y ejecutándose. Sigue las instrucciones de instalación en [https://ollama.com](https://ollama.com).

## 🚀 Instalación

### 1. Instalar y arrancar Ollama

Descargá Ollama desde su página oficial y asegurate de que el servidor esté corriendo (por defecto escucha en `http://localhost:11434`).

### 2. Descargar el modelo

```bash
ollama pull llama3.2
```

Podés verificar que el modelo funciona con:

```bash
ollama run llama3.2
```

### 3. Clonar el proyecto

```bash
git clone <url-del-repositorio>
cd agentesia
```

## ▶️ Compilación y ejecución

Compilar el proyecto:

```bash
mvn compile
```

Ejecutar la aplicación (clase principal: `org.ehmsoft.Main`):

```bash
mvn exec:java -Dexec.mainClass="org.ehmsoft.Main"
```

> 💡 Si tu IDE es IntelliJ IDEA o Eclipse, también podés ejecutar directamente la clase `Main.java` desde el botón de *Run*.

**Salida esperada:** la consola imprimirá el texto generado por el modelo. Por ejemplo, con el prompt de ejemplo se obtiene un artículo breve de ~20 palabras para LinkedIn sobre IA.

## 📁 Estructura del proyecto

```
agenteesia/
├── pom.xml                  # Configuración de Maven (dependencias, Java 21)
├── README.md
├── .gitignore
└── src/
    └── main/
        └── java/
            └── org/
                └── ehmsoft/
                    ├── Main.java          # Punto de entrada: define el prompt e imprime la respuesta
                    └── OllamaClient.java  # Cliente HTTP contra la API de Ollama
```

## ⚙️ Cómo funciona

El flujo de ejecución es el siguiente:

1. **`Main.java`** define un *prompt* de ejemplo (`testPrompt`) y llama a `OllamaClient.generate(prompt)`.
2. **`OllamaClient.java`** construye el cuerpo de la petición:

   ```json
   {
     "model": "llama3.2:latest",
     "prompt": "...",
     "stream": false
   }
   ```

3. Serializa el mapa a JSON con **Jackson** y envía un **HTTP POST** a `http://localhost:11434/api/generate`.
4. Valida que el código de estado sea `200`.
5. Parsea la respuesta JSON y extrae el campo `response`, que contiene el texto generado por el modelo.

```java
// Fragmento simplificado de OllamaClient.java
Map<String, Object> payload = new HashMap<>();
payload.put("model", "llama3.2:latest");
payload.put("prompt", prompt);
payload.put("stream", false);

String json = mapper.writeValueAsString(payload);

HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:11434/api/generate"))
        .header("Content-type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(json))
        .build();

HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
```

## ✏️ Cómo cambiar el prompt

Editá el texto de `testPrompt` en `src/main/java/org/ehmsoft/Main.java`:

```java
String testPrompt = """
        Realiza un articulo de 20 palabras para linkedin sobre ia
        """;
```

Reemplazalo por cualquier instrucción que quieras darle al modelo y volvé a ejecutar la aplicación.

## 👨‍🏫 Acerca del taller

Este proyecto forma parte del **taller de Agentes de IA** impartido por **Emilio Mayer** en la **Universidad Provincial de Córdoba (UPC)**, con el objetivo de aprender a integrar modelos de lenguaje y agentes de inteligencia artificial en aplicaciones reales.

## 📄 Licencia

Proyecto con fines educativos. Consultá al autor para usos adicionales.