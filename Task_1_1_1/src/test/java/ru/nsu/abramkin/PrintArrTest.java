package ru.nsu.abramkin;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты для проверки корректности работы алгоритма printArray.
 */
class PrintArrayTest {

    /**
     * Тест для printArray с непустым массивом.
     * Ожидаемый результат: строка "[1, 2, 3, 4, 5]".
     */
    @Test
    public void testPrintArray() {
        int[] arr = {1, 2, 3, 4, 5};
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        Main.printArray(arr);

        System.setOut(originalOut);
        assertEquals(
                "[1, 2, 3, 4, 5]\n",
                outContent.toString().replace(
                        "\r\n",
                        "\n"
                )
        );  // Убираем различия в переводах строк
    }

    /**
     * Тест для printArray с пустым массивом.
     * Ожидаемый результат: строка "[]".
     */
    @Test
    public void testPrintEmptyArray() {
        int[] arr = {};
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        Main.printArray(arr);

        System.setOut(originalOut);
        assertEquals("[]\n", outContent.toString().replace(
                "\r\n",
                "\n"
        ));
    }

    /**
     * Тест для printArray с одним элементом.
     * Ожидаемый результат: строка "[42]".
     */
    @Test
    public void testPrintSingleElementArray() {
        int[] arr = {42};
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        Main.printArray(arr);

        System.setOut(originalOut);
        assertEquals("[42]\n", outContent.toString().replace(
                "\r\n",
                "\n"
        ));
    }
}
