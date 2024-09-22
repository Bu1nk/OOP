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
}