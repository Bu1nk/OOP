package ru.nsu.abramkin.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameConfigControllerTest {

    @Test
    void testValidConfig() {
        assertTrue(GameConfigController.isValidConfig(10, 10, 3, 20));
    }

    @Test
    void testTooFewRows() {
        assertFalse(GameConfigController.isValidConfig(4, 10, 3, 10));
    }

    @Test
    void testTooManyColumns() {
        assertFalse(GameConfigController.isValidConfig(10, 31, 3, 10));
    }

    @Test
    void testInvalidFood() {
        assertFalse(GameConfigController.isValidConfig(10, 10, 0, 10));
        assertFalse(GameConfigController.isValidConfig(10, 10, 17, 10));
    }

    @Test
    void testInvalidWinLength() {
        assertFalse(GameConfigController.isValidConfig(10, 10, 3, 0));
        assertFalse(GameConfigController.isValidConfig(10, 10, 3, 101));
    }

    @Test
    void testEdgeValues() {
        assertTrue(GameConfigController.isValidConfig(5, 5, 1, 2));
        assertTrue(GameConfigController.isValidConfig(30, 30, 16, 900));
    }

    @Test
    void testNegativeValues() {
        assertFalse(GameConfigController.isValidConfig(-10, 10, 3, 20));
        assertFalse(GameConfigController.isValidConfig(10, -10, 3, 20));
        assertFalse(GameConfigController.isValidConfig(10, 10, -1, 20));
        assertFalse(GameConfigController.isValidConfig(10, 10, 3, -5));
    }

    @Test
    void testWinLengthTooLargeForField() {
        assertFalse(GameConfigController.isValidConfig(5, 5, 3, 26));
    }

    @Test
    void testTooMuchFoodForFieldSize() {
        assertFalse(GameConfigController.isValidConfig(3, 3, 10, 5));
    }

    @Test
    void testFoodAndWinLengthTooBigTogether() {
        assertTrue(GameConfigController.isValidConfig(5, 5, 10, 20));
    }
}
