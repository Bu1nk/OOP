package ru.nsu.abramkin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PrimeChecker class.
 */
public class PrimeCheckerTest {
    /**
     * Tests sequential method with different cases.
     */
    @Test
    void testHasNonPrimeSequential() {
        assertTrue(PrimeChecker.hasNonPrimeSequential(new int[]{4, 7, 9}));
        assertFalse(PrimeChecker.hasNonPrimeSequential(new int[]{2, 3, 5, 7, 11}));
        assertTrue(PrimeChecker.hasNonPrimeSequential(new int[]{15, 17, 19, 23}));
        assertFalse(PrimeChecker.hasNonPrimeSequential(new int[]{99999941, 99999959, 99999971}));
        assertTrue(PrimeChecker.hasNonPrimeSequential(new int[]{1, 2, 3, 4, 5}));
        assertFalse(PrimeChecker.hasNonPrimeSequential(new int[]{}));
        assertFalse(PrimeChecker.hasNonPrimeSequential(new int[]{17}));
        assertTrue(PrimeChecker.hasNonPrimeSequential(new int[]{10}));
    }

    /**
     * Tests parallel threads method with different cases.
     * @throws InterruptedException if execution is interrupted
     */
    @Test
    void testHasNonPrimeParallelThreads() throws InterruptedException {
        assertTrue(PrimeChecker.hasNonPrimeParallelThreads(new int[]{4, 7, 9}, 4));
        assertFalse(PrimeChecker.hasNonPrimeParallelThreads(new int[]{2, 3, 5, 7, 11}, 4));
        assertTrue(PrimeChecker.hasNonPrimeParallelThreads(new int[]{15, 17, 19, 23}, 4));
        assertFalse(PrimeChecker.hasNonPrimeParallelThreads(new int[]{99999941, 99999959, 99999971}, 4));
        assertTrue(PrimeChecker.hasNonPrimeParallelThreads(new int[]{1, 2, 3, 4, 5}, 4));
        assertFalse(PrimeChecker.hasNonPrimeParallelThreads(new int[]{}, 4));
        assertFalse(PrimeChecker.hasNonPrimeParallelThreads(new int[]{17}, 4));
        assertTrue(PrimeChecker.hasNonPrimeParallelThreads(new int[]{10}, 4));
    }

    /**
     * Tests parallel stream method with different cases.
     */
    @Test
    void testHasNonPrimeParallelStream() {
        assertTrue(PrimeChecker.hasNonPrimeParallelStream(new int[]{4, 7, 9}));
        assertFalse(PrimeChecker.hasNonPrimeParallelStream(new int[]{2, 3, 5, 7, 11}));
        assertTrue(PrimeChecker.hasNonPrimeParallelStream(new int[]{15, 17, 19, 23}));
        assertFalse(PrimeChecker.hasNonPrimeParallelStream(new int[]{99999941, 99999959, 99999971}));
        assertTrue(PrimeChecker.hasNonPrimeParallelStream(new int[]{1, 2, 3, 4, 5}));
        assertFalse(PrimeChecker.hasNonPrimeParallelStream(new int[]{}));
        assertFalse(PrimeChecker.hasNonPrimeParallelStream(new int[]{17}));
        assertTrue(PrimeChecker.hasNonPrimeParallelStream(new int[]{10}));
    }
}
