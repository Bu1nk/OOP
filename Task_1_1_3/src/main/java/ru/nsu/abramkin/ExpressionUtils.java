package ru.nsu.abramkin;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс, который содержит функцию для парсинга переменных.
 */
public class ExpressionUtils {

    public static Map<String, Integer> parseVariables(String input) {
        Map<String, Integer> variables = new HashMap<>();
        if (input == null || input.isEmpty()) {
            return variables;
        }

        String[] assignments = input.split(";");
        for (String assignment : assignments) {
            String[] parts = assignment.split("=");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid variable assignment: " + assignment);
            }

            String name = parts[0].trim();
            String valueString = parts[1].trim();

            try {
                int value = Integer.parseInt(valueString);
                variables.put(name, value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid value for variable " + name + ": " + valueString);
            }
        }

        return variables;
    }
}
