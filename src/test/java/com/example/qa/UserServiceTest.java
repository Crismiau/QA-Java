package com.example.qa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Mockito nos ayuda a simular o copiar una (fuente de datos, conexión de API, etc.) y con esa copia poder interactuar libremente
// para hacer pruebas unitarias.
// Esto es generalmente util cuando nuestra clase depende de una fuente de datos, alguna API O muy específico el caso de que si nuestra clase
// tiene comportamientos o funciones muy difíciles de reproducir..

    class UserServiceTest {

        // Aca especificamos a Junit que este metodo sera un codigo tipo test, para que lo pueda ejecutar como TEST y no como codigo normal
        @Test
        void register_existingEmail_returnsFalse() {

            // 1. Aqui creamos un mock (simulación o  copia) del repositorio de users (En un sistema real seria como una base de datos)
            UserRepository repo = mock(UserRepository.class);

            // 2. Configurar comportamiento del mock
            when(repo.existsByEmail("a@a.com")).thenReturn(true);

                    // con el when especifico una especie de condición:
                    // cuando se acceda al metodo del mock (repo) responde con una respuesta predeterminada:
                    // en esta caso cuando se acceda a la validacion del correo que retorne un true

                    // la logica aqui es que el correo que hay en el parametro yo lo voy a definir como que ya existe en el mock
                    // por ende entonces será false para el metodo de register, ya que el correo YA EXISTE.


            // 3. Luego probamos la clase con el mock
            UserService service = new UserService(repo); // instancio la clase userService para traerme el metodo de register.
            assertFalse(service.register("a@a.com"));
           verify(repo).existsByEmail("a@a.com");

           // tarea: hacer el sout para mostrar en consola que muestra verify y assertfalse.


            // Un caso de uso de este código es que si queremos conocer que usuarios ya estan registrados en nuestra página
            // devuelvo true si el usuario esta registrado y devolviendo false si no,
        }
    }
