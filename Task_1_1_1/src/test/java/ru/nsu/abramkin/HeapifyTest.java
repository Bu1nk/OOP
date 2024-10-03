package ru.nsu.abramkin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Тесты для проверки корректности работы алгоритма heapify.
 */
class HeapifyTest {

    /**
     * Тест для heapify на неотсортированном участке массива.
     * Ожидаемый результат: подмассив должен быть преобразован в кучу.
     */
    @Test
    public void testHeapifySimple() {
        int[] arr = {4, 10, 3, 5, 1};
        Heapsort.heapify(arr, arr.length, 0);
        assertArrayEquals(new int[] {10, 5, 3, 4, 1}, arr);
    }

    /**
     * Тест для heapify на уже корректной куче.
     * Ожидаемый результат: массив не изменится.
     */
    @Test
    public void testHeapifyAlreadyHeap() {
        int[] arr = {10, 5, 3, 4, 1};
        Heapsort.heapify(arr, arr.length, 0);
        assertArrayEquals(new int[] {10, 5, 3, 4, 1}, arr);
    }

    /**
     * Тест для heapify на пустом массиве.
     * Ожидаемый результат: массив не изменится.
     */
    @Test
    public void testHeapifyEmptyArray() {
        int[] arr = {};
        Heapsort.heapify(arr, arr.length, 0);
        assertArrayEquals(new int[] {}, arr);
    }

    /**
     * Тест для heapify с одним элементом.
     * Ожидаемый результат: массив не изменится.
     */
    @Test
    public void testHeapifySingleElement() {
        int[] arr = {42};
        Heapsort.heapify(arr, arr.length, 0);
        assertArrayEquals(new int[] {42}, arr);
    }
}
