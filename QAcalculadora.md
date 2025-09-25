Guía de Presentación: Testing Tipo QA con Java y IntelliJ IDEA
Este proyecto es una demostración práctica de cómo se aplica el testing de calidad (QA) a una aplicación de consola en Java, enfocada en la interacción del usuario. El objetivo es mostrar que las pruebas van más allá del código interno y pueden validar el flujo completo de la aplicación, incluyendo entradas y salidas.

1. Estructura del Proyecto con Maven
Utilizamos Maven para gestionar el proyecto. Maven nos proporciona una estructura de archivos estandarizada y un manejo eficiente de las dependencias.

pom.xml: El archivo de configuración principal de Maven, donde declaramos las librerías que necesitamos (como JUnit).

src/main/java: Contiene el código fuente de la aplicación, es decir, la calculadora.

src/test/java: Contiene las pruebas de calidad que se ejecutarán para validar el comportamiento de la aplicación.

2. Dependencias
Para el testing, utilizamos JUnit 5, el framework de pruebas más común en Java. Su dependencia se añade al archivo pom.xml.

<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.8.1</version>
    <scope>test</scope>
</dependency>

Instrucciones:

Abre el pom.xml.

Copia y pega la dependencia.

Haz clic en el botón de recarga de Maven en IntelliJ IDEA para descargar la librería.

3. Código de la Aplicación (CalculadoraCLI.java)
Esta clase contiene la lógica de la calculadora y la interacción con el usuario. Es el código que estamos probando.

Pide al usuario que ingrese dos números y una operación.

Maneja errores de entrada (letras en lugar de números).

Maneja errores de lógica (división por cero).

package com.tuempresa;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CalculadoraCLI {

    public static void main(String[] args) {
        CalculadoraCLI calculadora = new CalculadoraCLI();
        calculadora.iniciar();
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        double num1 = 0, num2 = 0;
        String operacion = "";
        boolean entradaValida = false;

        System.out.println("--- Calculadora de Consola ---");

        while (!entradaValida) {
            try {
                System.out.print("Ingresa el primer número: ");
                num1 = scanner.nextDouble();
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un número válido.");
                scanner.next(); // Limpia el buffer del scanner
            }
        }

        entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.print("Ingresa el segundo número: ");
                num2 = scanner.nextDouble();
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un número válido.");
                scanner.next();
            }
        }

        System.out.print("Ingresa la operación (+, -, *, /): ");
        operacion = scanner.next();

        double resultado;
        switch (operacion) {
            case "+":
                resultado = num1 + num2;
                System.out.println("Resultado: " + num1 + " + " + num2 + " = " + resultado);
                break;
            case "-":
                resultado = num1 - num2;
                System.out.println("Resultado: " + num1 + " - " + num2 + " = " + resultado);
                break;
            case "*":
                resultado = num1 * num2;
                System.out.println("Resultado: " + num1 + " * " + num2 + " = " + resultado);
                break;
            case "/":
                if (num2 == 0) {
                    System.out.println("Error: No se puede dividir por cero.");
                } else {
                    resultado = num1 / num2;
                    System.out.println("Resultado: " + num1 + " / " + num2 + " = " + resultado);
                }
                break;
            default:
                System.out.println("Error: Operación no válida.");
                break;
        }
        scanner.close();
    }
}

4. Código de las Pruebas (CalculadoraCLITest.java)
Esta clase contiene las pruebas de QA que validan el comportamiento de la CalculadoraCLI. La magia ocurre al simular la entrada del usuario y capturar la salida de la consola.

Conceptos Clave de Testing
AAA (Arrange-Act-Assert):

Arrange: Preparamos el escenario. Simulamos la entrada del usuario con una cadena de texto (ej: "10\n20\n+\n").

Act: Ejecutamos el método que queremos probar (calculadora.iniciar()).

Assert: Verificamos el resultado. Comprobamos que la salida capturada de la consola contenga el resultado o el mensaje de error esperado.

Anotaciones de JUnit:

@Test: Marca un método como una prueba ejecutable.

@DisplayName: Asigna un nombre legible al test para que sea más fácil de entender en los reportes de resultados.

@BeforeEach: Se ejecuta antes de cada prueba. Lo usamos para redirigir la salida de la consola.

@AfterEach: Se ejecuta después de cada prueba para restaurar el estado original de la consola.

package com.tuempresa;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CalculadoraCLITest {

    // Guarda los flujos originales de la consola
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    // Crea un stream para capturar la salida de la consola
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        // Redirige System.out para que podamos capturar lo que el programa imprime
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        // Restaura los flujos originales de la consola después de cada prueba
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Test de suma con entrada de usuario")
    void testSumaConEntradaUsuario() {
        String simulatedInput = "10\n20\n+\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        CalculadoraCLI calculadora = new CalculadoraCLI();
        calculadora.iniciar();
        String capturedOutput = outputStreamCaptor.toString();
        assertTrue(capturedOutput.contains("Resultado: 10.0 + 20.0 = 30.0"), "La salida no contiene el resultado de la suma esperado.");
    }

    @Test
    @DisplayName("Test de división por cero")
    void testDivisionPorCero() {
        String simulatedInput = "100\n0\n/\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        CalculadoraCLI calculadora = new CalculadoraCLI();
        calculadora.iniciar();
        String capturedOutput = outputStreamCaptor.toString();
        assertTrue(capturedOutput.contains("Error: No se puede dividir por cero."), "La salida no contiene el mensaje de error de división por cero.");
    }

    @Test
    @DisplayName("Test de error al ingresar el primer número")
    void testErrorPrimerNumero() {
        String simulatedInput = "hola\n10\n20\n+\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        CalculadoraCLI calculadora = new CalculadoraCLI();
        calculadora.iniciar();
        String capturedOutput = outputStreamCaptor.toString();
        assertTrue(capturedOutput.contains("Error: Debes ingresar un número válido."));
    }

    @Test
    @DisplayName("Test de error al ingresar el segundo número")
    void testErrorSegundoNumero() {
        String simulatedInput = "10\nadios\n20\n+\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        CalculadoraCLI calculadora = new CalculadoraCLI();
        calculadora.iniciar();
        String capturedOutput = outputStreamCaptor.toString();
        assertTrue(capturedOutput.contains("Error: Debes ingresar un número válido."));
    }

    @Test
    @DisplayName("Test de operación inválida")
    void testOperacionInvalida() {
        String simulatedInput = "5\n5\n%\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        CalculadoraCLI calculadora = new CalculadoraCLI();
        calculadora.iniciar();
        String capturedOutput = outputStreamCaptor.toString();
        assertTrue(capturedOutput.contains("Error: Operación no válida."), "La salida no contiene el mensaje de error de operación inválida.");
    }
}

5. Demostración Guiada
Sigue estos pasos para ejecutar el proyecto y entender el valor de las pruebas.

A. Ejecuta la Aplicación como Usuario
Abre el archivo CalculadoraCLI.java.

Busca el ícono de "play" (▶️) junto a la línea del main y haz clic. Selecciona Run 'CalculadoraCLI.main()'.

La consola de IntelliJ se activará y te pedirá los datos. Ingresa números y operadores manualmente.

B. Ejecuta las Pruebas de QA
Abre el archivo CalculadoraCLITest.java.

Haz clic en el ícono de "play" junto al nombre de la clase y selecciona Run 'CalculadoraCLITest'.

IntelliJ ejecutará todas las pruebas y te mostrará un reporte con los resultados. Si todas las pruebas tienen un ícono verde (✅), significa que todos los casos de prueba, tanto los exitosos como los que tienen errores, funcionan como se espera.

Opcional pero recomendado: Para ver cómo un test detecta un error, abre CalculadoraCLI.java y cambia la línea de la suma a una resta: resultado = num1 - num2;. Vuelve a ejecutar el test de suma y verás que fallará, demostrando cómo una prueba de QA puede proteger tu código.