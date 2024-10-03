package ru.nsu.abramkin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Тесты для проверки корректности работы алгоритма HeapSort.
 */
class MainTest {

    /**
     * Тест для сортировки уже отсортированного массива.
     * Ожидаемый результат: массив останется отсортированным.
     */
    @Test
    public void testSortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        Heapsort.heapsort(arr);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, arr);
    }

    /**
     * Тест для сортировки массива в обратном порядке.
     * Ожидаемый результат: массив будет отсортирован по возрастанию.
     */
    @Test
    public void testReversedArray() {
        int[] arr = {5, 4, 3, 2, 1};
        Heapsort.heapsort(arr);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, arr);
    }

    /**
     * Тест для сортировки случайного массива.
     * Ожидаемый результат: массив будет отсортирован по возрастанию.
     */
    @Test
    public void testUnsortedArray() {
        int[] arr = {3, 5, 1, 2, 4};
        Heapsort.heapsort(arr);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, arr);
    }

    /**
     * Тест для сортировки массива с повторяющимся элементом.
     */
    @Test
    public void testArrayWithDuplicates() {
        int[] arr = {4, 1, 3, 2, 1};
        Heapsort.heapsort(arr);
        assertArrayEquals(new int[] {1, 1, 2, 3, 4}, arr);
    }

    /**
     * Тест для сортировки пустого массива.
     * Ожидаемый результат: массив останется пустым.
     */
    @Test
    public void testEmptyArray() {
        int[] arr = {};
        Heapsort.heapsort(arr);
        assertArrayEquals(new int[] {}, arr);
    }

    /**
     * Тест для сортировки массива с одним элементом.
     * Ожидаемый результат: массив останется неизменным.
     */
    @Test
    public void testSingleElementArray() {
        int[] arr = {42};
        Heapsort.heapsort(arr);
        assertArrayEquals(new int[] {42}, arr);
    }

    /**
     * Тест для сортировки массива с отрицательными числами.
     * Ожидаемый результат: массив будет отсортирован по возрастанию.
     */
    @Test
    public void testArrayWithNegativeNumbers() {
        int[] arr = {-1, -3, 4, -2, 0};
        Heapsort.heapsort(arr);
        assertArrayEquals(new int[] {-3, -2, -1, 0, 4}, arr);
    }

    /**
     * Тест для сортировки большого массива.
     * Ожидаемый результат: массив будет корректно отсортирован.
     */
    @Test
    public void testLargeArray() {
        int[] arr = new int[1000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1000 - i;
        }
        Heapsort.heapsort(arr);

        int[] expected = new int[1000];
        for (int i = 0; i < expected.length; i++) {
            expected[i] = i + 1;
        }
        assertArrayEquals(expected, arr);
    }
}
