package ru.nsu.abramkin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class MainTest {
    @Test
    public void testSortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        Main.heapsort(arr);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, arr);
    }

    @Test
    public void testReversedArray() {
        int[] arr = {5, 4, 3, 2, 1};
        Main.heapsort(arr);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, arr);
    }

    @Test
    public void testUnsortedArray() {
        int[] arr = {3, 5, 1, 2, 4};
        Main.heapsort(arr);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, arr);
    }

    @Test
    public void testArrayWithDuplicates() {
        int[] arr = {4, 1, 3, 2, 1};
        Main.heapsort(arr);
        assertArrayEquals(new int[] {1, 1, 2, 3, 4}, arr);
    }

    @Test
    public void testEmptyArray() {
        int[] arr = {};
        Main.heapsort(arr);
        assertArrayEquals(new int[] {}, arr);
    }
}