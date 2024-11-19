package ru.nsu.abramkin;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    /**
     * Тест проверяет корректное представление сложного выражения с несколькими операциями в виде строки.
     */
    @Test
    void testComplexExpressionToString() {
        Expression e = new Add(
                new Number(3),
                new Sub(
                        new Mul(new Variable("x"), new Number(5)),
                        new Div(new Variable("y"), new Number(2))
                )
        );
        assertEquals("(3+((x*5)-(y/2)))", e.toString());
    }
    /**
     * Тест вычисляет сложное выражение с несколькими переменными и проверяет результат вычисления.
     */
    @Test
    void testComplexExpressionEval() {
        Expression e = new Add(
                new Number(3),
                new Sub(
                        new Mul(new Variable("x"), new Number(5)),
                        new Div(new Variable("y"), new Number(2))
                )
        );

        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 4);
        variables.put("y", 8);

        int result = e.eval(variables);
        assertEquals(19, result);
    }

    /**
     * Тест проверяет результат для других значений тех же переменных.
     */
    @Test
    void testComplexExpressionEvalWithDifferentValues() {
        Expression e = new Add(
                new Number(3),
                new Sub(
                        new Mul(new Variable("x"), new Number(5)),
                        new Div(new Variable("y"), new Number(2))
                )
        );

        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 2);
        variables.put("y", 6);

        int result = e.eval(variables);
        assertEquals(10, result);
    }

    /**
     * Тест проверяет правильность символического дифференцирования сложного выражения по переменной x.
     */
    @Test
    void testDerivativeOfComplexExpression() {
        Expression e = new Add(
                new Number(3),
                new Sub(
                        new Mul(new Variable("x"), new Number(5)),
                        new Div(new Variable("y"), new Number(2))
                )
        );

        Expression de = e.derivative("x");

        assertEquals("(0+(((1*5)+(x*0))-(((0*2)-(y*0))/(2*2))))", de.toString());
    }

    /**
     * Тест проверяет правильность дифференцирования по переменной y.
     */
    @Test
    void testDerivativeOfComplexExpressionWithRespectToY() {
        Expression e = new Add(
                new Number(3),
                new Sub(
                        new Mul(new Variable("x"), new Number(5)),
                        new Div(new Variable("y"), new Number(2))
                )
        );

        Expression de = e.derivative("y");

        assertEquals("(0+(((0*5)+(x*0))-(((1*2)-(y*0))/(2*2))))", de.toString());
    }

    /**
     * Тест проверяет правильность представления вложенных выражений.
     */
    @Test
    void testNestedExpressions() {
        Expression e = new Add(
                new Mul(new Variable("x"), new Add(new Variable("y"), new Number(2))),
                new Sub(new Variable("z"), new Number(4))
        );

        assertEquals("((x*(y+2))+(z-4))", e.toString());
    }

    /**
     * Тест вычисляет вложенное выражение с несколькими переменными и проверяет результат.
     */
    @Test
    void testNestedExpressionEval() {
        Expression e = new Add(
                new Mul(new Variable("x"), new Add(new Variable("y"), new Number(2))),
                new Sub(new Variable("z"), new Number(4))
        );

        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 3);
        variables.put("y", 4);
        variables.put("z", 8);

        int result = e.eval(variables);
        assertEquals(22, result);
    }

    /**
     * Тест проверяет, что попытка вычисления выражения с недостающей переменной вызывает ошибку.
     */
    @Test
    void testEvalMissingVariable() {
        Expression e = new Add(new Number(3), new Mul(new Variable("x"), new Number(5)));

        Map<String, Integer> variables = new HashMap<>();
        variables.put("y", 5);

        assertThrows(IllegalArgumentException.class, () -> {
            e.eval(variables);
        });
    }

    /**
     * Тест проверяет правильность вычисления выражений с нулевыми значениями переменных.
     */
    @Test
    void testEvalWithZeroValues() {
        Expression e = new Add(new Number(3), new Mul(new Variable("x"), new Variable("y")));

        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 0);
        variables.put("y", 5);

        int result = e.eval(variables);
        assertEquals(3, result);
    }
}
