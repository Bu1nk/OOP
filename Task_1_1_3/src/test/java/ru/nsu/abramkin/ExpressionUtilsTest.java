package ru.nsu.abramkin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Map;

/**
 * Тесты для проверки парсинга переменных.
 */
class ExpressionUtilsTest {

    /**
     * Тесты для простого случая (одна переменная).
     */
    @Test
    void testParseVariablesSingleVariable() {
        String input = "x = 10";
        Map<String, Integer> result = ExpressionUtils.parseVariables(input);
        assertEquals(1, result.size());
        assertEquals(10, result.get("x"));
    }

    /**
     * Тест для случая, когда переменных больше.
     */
    @Test
    void testParseVariablesMultipleVariables() {
        String input = "x = 10; y = 5";
        Map<String, Integer> result = ExpressionUtils.parseVariables(input);
        assertEquals(2, result.size());
        assertEquals(10, result.get("x"));
        assertEquals(5, result.get("y"));
    }

    /**
     * Тест для случая с лишними переменными.
     */
    @Test
    void testParseVariablesWithSpaces() {
        String input = "  x = 15  ;  y=  20  ";
        Map<String, Integer> result = ExpressionUtils.parseVariables(input);
        assertEquals(2, result.size());
        assertEquals(15, result.get("x"));
        assertEquals(20, result.get("y"));
    }

    /**
     * Тест для пустого случая.
     */
    @Test
    void testParseVariablesEmptyInput() {
        String input = "";
        Map<String, Integer> result = ExpressionUtils.parseVariables(input);
        assertTrue(result.isEmpty());
    }

    /**
     * Тест для случая с неправильным значением переменной.
     */
    @Test
    void testParseVariablesInvalidValue() {
        String input = "x = 10; y = abc"; // Некорректное значение переменной y
        assertThrows(IllegalArgumentException.class, () -> {
            ExpressionUtils.parseVariables(input);
        });
    }

    /**
     * Тест для случая без равно.
     */
    @Test
    void testParseVariablesInvalidFormat() {
        String input = "x 10";
        assertThrows(IllegalArgumentException.class, () -> {
            ExpressionUtils.parseVariables(input);
        });
    }
}
