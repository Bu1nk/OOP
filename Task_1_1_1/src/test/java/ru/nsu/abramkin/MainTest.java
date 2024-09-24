package ru.nsu.abramkin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Тесты для проверки корректности работы алгоритма HeapSort.
 */
class MainTest {

    /**
     * Тест для сортировки массива в обратном порядке.
     * Ожидаемый результат: массив будет отсортирован по возрастанию.
     */
    @Test
    public void testSortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        Main.heapsort(arr);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, arr);
    }

    /**
     * Тест для сортировки уже отсортированного массива.
     * Ожидаемый результат: массив останется отсортированным.
     */
    @Test
    public void testReversedArray() {
        int[] arr = {5, 4, 3, 2, 1};
        Main.heapsort(arr);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, arr);
    }

    /**
     * Тест для сортировки случайного массива.
     * Ожидаемый результат: массив будет отсортирован по возрастанию.
     */
    @Test
    public void testUnsortedArray() {
        int[] arr = {3, 5, 1, 2, 4};
        Main.heapsort(arr);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, arr);
    }

    /**
     * Тест для сортировки массива с одним элементом.
     * Ожидаемый результат: массив останется неизменным.
     */
    @Test
    public void testArrayWithDuplicates() {
        int[] arr = {4, 1, 3, 2, 1};
        Main.heapsort(arr);
        assertArrayEquals(new int[] {1, 1, 2, 3, 4}, arr);
    }

    /**
     * Тест для сортировки пустого массива.
     * Ожидаемый результат: массив останется пустым.
     */
    @Test
    public void testEmptyArray() {
        int[] arr = {};
        Main.heapsort(arr);
        assertArrayEquals(new int[] {}, arr);
    }
}