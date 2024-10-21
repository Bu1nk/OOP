package ru.nsu.abramkin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Map;
import java.util.HashMap;

/**
 * Тесты для проверки корректности работы иерархии Exprassion.
 */
class ExpressionTest {

    /**
     * Тест для простого случая.
     */
    @Test
    void testExpressionToString() {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        assertEquals("(3+(2*x))", e.toString());
    }

    /**
     * Тест для de константы.
     */
    @Test
    void testDerivativeConstant() {
        Expression e = new Number(5);
        Expression de = e.derivative("x");
        assertEquals("0", de.toString());
    }

    /**
     * Тест de переменной.
     */
    @Test
    void testDerivativeVariable() {
        Expression e = new Variable("x");
        Expression de = e.derivative("x");
        assertEquals("1", de.toString());
    }

    /**
     * Тест de для переменной, которой нет в выражении.
     */
    @Test
    void testDerivativeWithRespectToDifferentVariable() {
        Expression e = new Variable("x");
        Expression de = e.derivative("y");
        assertEquals("0", de.toString());
    }

    /**
     * Тест de для сложения.
     */
    @Test
    void testDerivativeAddition() {
        Expression e = new Add(new Number(3), new Variable("x"));
        Expression de = e.derivative("x");
        assertEquals("(0+1)", de.toString());
    }

    /**
     * Тест для de умножения.
     */
    @Test
    void testDerivativeMultiplication() {
        Expression e = new Mul(new Number(2), new Variable("x"));
        Expression de = e.derivative("x");
        assertEquals("((0*x)+(2*1))", de.toString());
    }

    /**
     * Тест для простого случая подстановки.
     */
    @Test
    void testEvalSimpleExpression() {
        Expression e = new Add(new Number(3), new Variable("x"));
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 10);
        int result = e.eval(variables);
        assertEquals(13, result);
    }

    /**
     * Тест для проверки составного случая подстановки.
     */
    @Test
    void testEvalComplexExpression() {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 5);
        int result = e.eval(variables);
        assertEquals(13, result); // 3 + (2 * 5) = 13
    }

    /**
     * Тест для некорректного случая.
     */
    @Test
    void testEvalWithUndefinedVariable() {
        Expression e = new Add(new Number(3), new Variable("x"));
        Map<String, Integer> variables = new HashMap<>();
        assertThrows(IllegalArgumentException.class, () -> {
            e.eval(variables); // переменная x не определена
        });
    }
}
