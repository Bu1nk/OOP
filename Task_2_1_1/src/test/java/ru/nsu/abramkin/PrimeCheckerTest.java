package ru.nsu.abramkin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the PrimeChecker class.
 */
public class PrimeCheckerTest {
    /**
     * Tests the sequential method with different cases.
     */
    @Test
    void testHasCompositeSequential() {
        assertTrue(PrimeChecker.hasCompositeSequential(new int[]{4, 7, 9}));
        assertFalse(PrimeChecker.hasCompositeSequential(new int[]{2, 3, 5, 7, 11}));
        assertTrue(PrimeChecker.hasCompositeSequential(new int[]{15, 17, 19, 23}));
        assertFalse(PrimeChecker.hasCompositeSequential(new int[]{99999941, 99999959, 99999971}));
        assertTrue(PrimeChecker.hasCompositeSequential(new int[]{1, 2, 3, 4, 5}));
        assertFalse(PrimeChecker.hasCompositeSequential(new int[]{}));
        assertFalse(PrimeChecker.hasCompositeSequential(new int[]{17}));
        assertTrue(PrimeChecker.hasCompositeSequential(new int[]{10}));
    }

    /**
     * Tests the parallel threads method with different cases.
     *
     * @throws InterruptedException if execution is interrupted
     */
    @Test
    void testHasCompositeParallelThreads() throws InterruptedException {
        assertTrue(PrimeChecker.hasCompositeParallelThreads(new int[]{4, 7, 9}, 4));
        assertFalse(PrimeChecker.hasCompositeParallelThreads(new int[]{2, 3, 5, 7, 11}, 4));
        assertTrue(PrimeChecker.hasCompositeParallelThreads(new int[]{15, 17, 19, 23}, 4));
        assertFalse(PrimeChecker.hasCompositeParallelThreads(
                new int[]{99999941, 99999959, 99999971},
                4));
        assertTrue(PrimeChecker.hasCompositeParallelThreads(new int[]{1, 2, 3, 4, 5}, 4));
        assertFalse(PrimeChecker.hasCompositeParallelThreads(new int[]{}, 4));
        assertFalse(PrimeChecker.hasCompositeParallelThreads(new int[]{17}, 4));
        assertTrue(PrimeChecker.hasCompositeParallelThreads(new int[]{10}, 4));
    }

    /**
     * Tests the parallel stream method with different cases.
     */
    @Test
    void testHasCompositeParallelStream() {
        assertTrue(PrimeChecker.hasCompositeParallelStream(new int[]{4, 7, 9}));
        assertFalse(PrimeChecker.hasCompositeParallelStream(new int[]{2, 3, 5, 7, 11}));
        assertTrue(PrimeChecker.hasCompositeParallelStream(new int[]{15, 17, 19, 23}));
        assertFalse(PrimeChecker.hasCompositeParallelStream(
                new int[]{99999941, 99999959, 99999971}));
        assertTrue(PrimeChecker.hasCompositeParallelStream(new int[]{1, 2, 3, 4, 5}));
        assertFalse(PrimeChecker.hasCompositeParallelStream(new int[]{}));
        assertFalse(PrimeChecker.hasCompositeParallelStream(new int[]{17}));
        assertTrue(PrimeChecker.hasCompositeParallelStream(new int[]{10}));
    }
}
