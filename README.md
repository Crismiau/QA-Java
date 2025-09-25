# 📘 Proyecto QA-Java con Maven y Pruebas Unitarias

## ✨ Requisitos Previos
- ☕ Java 17+ instalado  
- 🛠️ Maven (integrado en tu IDE o instalado en tu sistema)  
- 💻 IDE compatible con proyectos Maven (ejemplo: IntelliJ IDEA, Eclipse, VS Code)  
- 🌐 Git (para clonar y subir repositorios)  

## 📂 Estructura del Proyecto
El proyecto sigue la estructura estándar de Maven:

```
qa-playground/
 ├── .gitignore
 ├── pom.xml                       # Configuración de Maven
 └── src
     ├── main/java/com/example/qa/
     │     ├── Calculator.java
     │     ├── UserRepository.java
     │     └── UserService.java
     └── test/java/com/example/qa/
           ├── CalculatorTest.java
           └── UserServiceTest.java
```

## 🧩 Descripción de Clases

### 🔢 Calculator.java
Clase sencilla que expone operaciones aritméticas básicas.

```java
package com.example.qa;

public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }
}
```

### 🗂️ UserRepository.java
Interfaz que representa un repositorio de usuarios.

```java
package com.example.qa;

public interface UserRepository {
    boolean existsByEmail(String email);
}
```

### 👤 UserService.java
Servicio que depende del repositorio. Ejemplo de **inyección de dependencias**, ya que el repositorio se pasa por el constructor.

```java
package com.example.qa;

public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    // Simula un registro simple: devuelve false si ya existe
    public boolean register(String email) {
        if (repo.existsByEmail(email)) return false;
        // lógica de registro (persistencia simulada)
        return true;
    }
}
```

## 🧪 Pruebas Unitarias
Las pruebas se escriben con **JUnit 5** y se ejecutan con Maven (`mvn test`).  
Ejemplo de prueba para `Calculator`:

```java
package com.example.qa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private final Calculator calc = new Calculator();

    @Test
    void testAdd() {
        assertEquals(5, calc.add(2, 3));
    }

    @Test
    void testSubtract() {
        assertEquals(1, calc.subtract(3, 2));
    }
}
```

## 🧑‍🔬 Uso de Mockito
Para pruebas de `UserService`, usamos **Mockito** para simular `UserRepository`:

```java
package com.example.qa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Test
    void testRegisterNewUser() {
        UserRepository mockRepo = mock(UserRepository.class);
        when(mockRepo.existsByEmail("test@email.com")).thenReturn(false);

        UserService service = new UserService(mockRepo);
        assertTrue(service.register("test@email.com"));
    }

    @Test
    void testRegisterExistingUser() {
        UserRepository mockRepo = mock(UserRepository.class);
        when(mockRepo.existsByEmail("test@email.com")).thenReturn(true);

        UserService service = new UserService(mockRepo);
        assertFalse(service.register("test@email.com"));
    }
}
```

## ▶️ Cómo Ejecutar el Proyecto
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/qa-playground.git
   ```
2. Compilar y ejecutar las pruebas:
   ```bash
   mvn clean test
   ```

## 🚀 Futuras Mejoras
- Conexión con una base de datos real.  
- Integración con Spring Boot.  
- Más casos de prueba y ejemplos de integración.  

## 👨‍💻 Autor
Proyecto creado por **Cris y Juapa** con fines educativos en **Java y QA**.
