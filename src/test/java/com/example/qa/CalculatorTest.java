package com.example.qa;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private Calculator calc;

    @BeforeEach
    void setUp() {
        calc = new Calculator();
    }

    @Test
    void add_twoNumbers_returnsSum() {
        assertEquals(6, calc.add(3, 3));
    }

    @ParameterizedTest
    @CsvSource({"1,2,3", "2,3,5", "10,5,15"})
    void parameterizedAdd(int a, int b, int expected) {
        assertEquals(expected, calc.add(a, b));
    }

    @Test
    void divide_byZero_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> calc.divide(1, 0));
    }
}
