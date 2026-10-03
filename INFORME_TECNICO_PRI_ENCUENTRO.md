# Informe Técnico - Análisis Arquitectónico

## Resumen Ejecutivo

El proyecto **Agentesia** cumple con su funcionalidad básica de interactuar con la API de Ollama. Sin embargo, presenta deficiencias significativas en términos de arquitectura, ingeniería de software y buenas prácticas que limitan su mantenibilidad, escalabilidad y testeabilidad.

---

## Análisis de la Arquitectura Actual

### Estructura del Código

```
com.ehmsoft.agentesia.agentesia/
├── Main.java           # Clase principal con lógica de negocio
└── OllamaClient.java   # Cliente HTTP estático
```

### Problemas Identificados

#### 1. **Acoplamiento Fuerte (Tight Coupling)**

**Problema:**
- `Main.java` importa y utiliza directamente `OllamaClient.generate()` como método estático
- No existe inyección de dependencias ni interfaces que permitan desacoplar componentes

**Impacto:**
- Imposibilidad de cambiar la implementación sin modificar el código cliente
- Dificultad para realizar testing unitario (no se puede mockear)
- Imposibilidad de usar diferentes implementaciones de clientes (ej: OpenAI, Anthropic)

**Código problemático:**
```java
import static com.ehmsoft.agentesia.agentesia.OllamaClient.generate;
// ...
System.out.println(generate(testPrompt));
```

#### 2. **Uso Excesivo de Métodos Estáticos**

**Problema:**
- `OllamaClient.generate()` es un método estático
- `HttpClient`, `ObjectMapper` son estáticos

**Impacto:**
- No se puede inyectar diferentes configuraciones
- Dificulta el testing (no se pueden reemplazar con mocks)
- Viola el principio de inversión de dependencias (DIP)
- Estado compartido global sin control

#### 3. **Hardcoding de Configuración**

**Problema:**
```java
private static final String OLLAMA_URL = "http://localhost:11434/api/generate";
// ...
payload.put("model", "deepseek-r1:8b");
payload.put("stream", false);
```

**Impacto:**
- No se puede cambiar el modelo sin recompilar
- No se puede apuntar a diferentes entornos (dev/staging/prod)
- La URL está fija, no soporta configuración externa

#### 4. **Manejo de Errores Deficiente**

**Problema:**
```java
} catch (Exception e) {
    return "Error: " + e.getMessage();
}
```

**Impacto:**
- Pérdida del stack trace completo
- No hay logging estructurado

#### 5. **Falta de Separación de Responsabilidades (SRP)**

**Problema:**
- `OllamaClient` mezcla:
  - Construcción de HTTP requests
  - Serialización JSON
  - Ejecución de HTTP calls
  - Parsing de respuestas
  - Manejo de errores

**Impacto:**
- Clase con múltiples responsabilidades
- Difícil de testear y mantener
- Cambios en un aspecto afectan toda la clase

#### 6. **Falta de Configuración Externa**

**Problema:**
- No hay archivos de configuración (application.properties, .env, etc.)
- No hay soporte para variables de entorno

**Impacto:**
- Configuración rígida y no portable

#### 7. **Diseño No Extensible**

**Problema:**
- No hay interfaces ni abstracciones
- Todo está acoplado a Ollama específicamente
- No se puede agregar soporte para otros LLM providers

**Impacto:**
- Violación del principio Open/Closed (OCP)
- Cualquier cambio requiere modificar código existente

#### 8. **Manejo de Recursos**

**Problema:**
```java
private static final HttpClient client = HttpClient.newHttpClient();
```

**Impacto:**
- HttpClient se crea una sola vez (ok), pero no se puede configurar

#### 9. **Falta de Logging**

**Problema:**
- No hay logs de requests/responses

**Impacto:**
- No hay visibilidad del comportamiento del sistema

---

## Comparación: Fin vs Medio

### ✅ El Fin (Funcionalidad)
El proyecto **CUMPLE** con su objetivo:
- Se conecta a Ollama ✓
- Envía prompts ✓
- Recibe respuestas ✓
- Muestra el resultado ✓

### ❌ El Medio (Arquitectura e Ingeniería)
El proyecto **NO CUMPLE** con estándares de ingeniería:

| Aspecto | Estado Actual | Estado Esperado |
|---------|--------------|-----------------|
| **Patrones de Diseño** | Ninguno | Repository, Factory, Strategy |
| **Inyección de Dependencias** | No | Spring/CDI/Manual DI |
| **Configuración** | Hardcodeada | Externa (properties/env) |
| **Manejo de Errores** | Básico | Excepciones tipadas + Recovery |
| **Logging** | No existe | SLF4J/Logback estructurado |
| **Abstracciones** | No hay interfaces | Interfaces + Implementaciones |
| **Principios SOLID** | No aplicados | Aplicados consistentemente |

---

## Problemas de Ingeniería Específicos

### 1. **Violación de Principios SOLID**

- **S** - Single Responsibility: `OllamaClient` tiene múltiples responsabilidades
- **O** - Open/Closed: No se puede extender sin modificar
- **L** - Liskov Substitution: No hay jerarquías que aplicar
- **I** - Interface Segregation: No hay interfaces
- **D** - Dependency Inversion: Depende de implementaciones concretas

### 2. **Code Smells Identificados**

- **Hardcoded Configuration**: Valores fijos en código
- **Static Methods**: Métodos estáticos everywhere
- **God Class**: `OllamaClient` hace demasiado
- **Magic Strings**: URLs y nombres de modelo hardcodeados
- **Error Swallowing**: Catch genérico que oculta errores
- **No Logging**: Ausencia total de logs

### 3. **Problemas de Mantenibilidad**

- Sin tests = refactorización peligrosa
- Sin configuración externa = cambios requieren recompilación
- Sin abstracciones = imposible cambiar de proveedor LLM
- Sin logging = imposible debugear en producción

---

## Métricas de Calidad

| Métrica | Actual |
|---------|--------|
| Cobertura de Tests | 0% |
| Acoplamiento | Alto |
| Cohesión | Baja | Alta |
| Complejidad Ciclomática | Media-Alta |
| Principios SOLID | 0/5 |
| Patrones de Diseño | 0 |

---

## Conclusión

### El Proyecto Cumple el Fin
La aplicación funciona y genera respuestas desde Ollama correctamente.

### El Medio No es el Correcto
La implementación actual:
- **No es mantenible**: Sin tests, sin logging, sin configuración externa
- **No es escalable**: Hardcoded, sin abstracciones, sin DI
- **No es testeable**: Métodos estáticos, sin mocks
- **No es profesional**: Viola principios SOLID, sin patrones de diseño
- **No es portable**: Configuración hardcodeada

### Recomendación Final

Para un proyecto académico inicial, la implementación actual es aceptable como **primer borrador**. Sin embargo, para cualquier uso real o producción, se requiere una refactorización completa aplicando:

1. ✅ Principios SOLID
2. ✅ Patrones de Diseño (Repository, Factory, Strategy)
3. ✅ Inyección de Dependencias
4. ✅ Configuración Externa
5. ✅ Testing (Unit + Integration)
6. ✅ Logging estructurado
7. ✅ Manejo de errores robusto
8. ✅ Abstracciones e interfaces

---

## Referencias

- [Clean Code - Robert C. Martin](https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882)
- [Design Patterns - Gang of Four](https://en.wikipedia.org/wiki/Design_Patterns)
- [SOLID Principles](https://en.wikipedia.org/wiki/SOLID)
- [Spring Framework Best Practices](https://spring.io/guides)

---
**Fecha**: 2026-04-07
**Autor**: Emilio Mayer (Desarrollador de software)
*Informe generado para proyecto académico - UPC*
