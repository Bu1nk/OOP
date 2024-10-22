package ru.nsu.abramkin;

import java.util.Map;

/**
 * Интерфейс для всех математических выражений.
 * Описывает базовые операции, такие как вычисление значения выражения,
 * нахождение производной и представление выражения в виде строки.
 */
public interface Expression {

    /**
     * Вычисляет значение выражения при подстановке значений переменных.
     *
     * @param variables карта переменных, где ключ — это имя переменной, а значение — её числовое значение
     * @return вычисленное значение выражения
     * @throws IllegalArgumentException если одна из переменных не определена
     */
    int eval(Map<String, Integer> variables);

    /**
     * Возвращает производную выражения по указанной переменной.
     *
     * @param variable имя переменной, по которой нужно взять производную
     * @return новое выражение, представляющее собой производную исходного выражения
     */
    Expression derivative(String variable);

    /**
     * Возвращает строковое представление выражения в формате,
     * который отражает его математическую структуру.
     *
     * @return строка, представляющая выражение
     */
    @Override
    String toString();
}

/**
 * Класс для констант.
 */
class Number implements Expression {
    private final int value;

    public Number(int value) {
        this.value = value;
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return value;
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0); // Производная числа - 0
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}

/**
 * Класс для переменных.
 */
class Variable implements Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        } else {
            throw new IllegalArgumentException("Variable " + name + " is not defined.");
        }
    }

    @Override
    public Expression derivative(String variable) {
        return variable.equals(this.name) ? new Number(1) : new Number(0);
    }

    @Override
    public String toString() {
        return name;
    }
}

/**
 * Класс для сложения.
 */
class Add implements Expression {
    private final Expression left;
    private final Expression right;

    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables) + right.eval(variables);
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "+" + right.toString() + ")";
    }
}

/**
 * Класс для вычитания.
 */
class Sub implements Expression {
    private final Expression left;
    private final Expression right;

    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables) - right.eval(variables);
    }

    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "-" + right.toString() + ")";
    }
}

/**
 * Класс для умножения.
 */
class Mul implements Expression {
    private final Expression left;
    private final Expression right;

    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables) * right.eval(variables);
    }

    @Override
    public Expression derivative(String variable) {
        // (u*v)' = u'*v + u*v'
        return new Add(
                new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable))
        );
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "*" + right.toString() + ")";
    }
}

/**
 * Класс для деления.
 */
class Div implements Expression {
    private final Expression left;
    private final Expression right;

    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables) / right.eval(variables);
    }

    @Override
    public Expression derivative(String variable) {
        return new Div(
                new Sub(
                        new Mul(left.derivative(variable), right),
                        new Mul(left, right.derivative(variable))
                ),
                new Mul(right, right)
        );
    }

    @Override
    public String toString() {
        return "(" + left.toString() + "/" + right.toString() + ")";
    }
}
