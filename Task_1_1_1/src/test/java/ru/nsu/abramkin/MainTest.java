package ru.nsu.abramkin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class MainTest {
    int arr[] = {12, 11, 19, 5, 6, 7};
    int sarr[] = {5, 6, 7, 11, 12, 19};

    @Test
    void SampleTest() {
        assertArrayEquals(sarr, Main.heapsort(arr));
    }

    int arr1[] = {12, 11, 11, 5, 6, 7};
    int sarr1[] = {5, 6, 7, 11, 11, 12};

    @Test
    void SameTest() {
        assertArrayEquals(sarr1, Main.heapsort(arr1));
    }

    int arr2[] = {1000, 1000, 1000, 1000, 1000, 1000};
    int sarr2[] = {1000, 1000, 1000, 1000, 1000, 1000};

    @Test
    void AllSameTest() {
        assertArrayEquals(sarr2, Main.heapsort(arr2));
    }

    int arr3[] = {1, 432, 7, 3774, 499, 858, 585, 3, 90, 9, 9, 8, 9, 342, 43, 238284, 43, 109, 347,
            56, 6, 1, 0, 546, 34, 54, 9669, 11111, 3438, 1, 432, 7, 3774, 499, 858, 585, 3, 90, 9,
            9, 8, 9, 342, 43, 238284, 43, 109, 347, 56, 45, 8787, 29, 52, 542, 908, 324, 1567,
            56, 6, 1, 0, 546, 34, 54, 9669, 11111, 3438, 548, 999, 1727, 2592, 54, 52};
    int sarr3[] = {0, 0, 1, 1, 1, 1, 3, 3, 6, 6, 7, 7, 8, 8, 9, 9, 9, 9, 9, 9, 29, 34,
            34, 43, 43, 43, 43, 45, 52, 52, 54, 54, 54, 56, 56, 56, 90, 90, 109, 109, 324,
            342, 342, 347, 347, 432, 432, 499, 499, 542, 546, 546, 548, 585, 585, 858, 858, 908,
            999, 1567, 1727, 2592, 3438, 3438, 3774, 3774, 8787, 9669, 9669, 11111, 11111, 238284, 238284};

    @Test
    void BigTest() {
        assertArrayEquals(sarr3, Main.heapsort(arr3));
    }

    int arr4[] = {-12, 11, 19, 5, -6, 7};
    int sarr4[] = {-12, -6, 5, 7, 11, 19};

    @Test
    void NegNumTest() {
        assertArrayEquals(sarr4, Main.heapsort(arr4));
    }
}