package ru.nsu.abramkin;

import java.util.*;
import java.util.concurrent.*;

/**
 * Utility class for checking the presence of non-prime numbers in an array.
 */
public class PrimeChecker {
    /**
     * Checks if a number is prime.
     *
     * @param num the number to check
     * @return true if the number is prime, false otherwise
     */
    private static boolean isPrime(int num) {
        if (num < 2) return false;
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    /**
     * Checks if the array contains any non-prime numbers using a sequential approach.
     *
     * @param numbers the array of numbers to check
     * @return true if there is at least one non-prime number, false otherwise
     */
    public static boolean hasNonPrimeSequential(int[] numbers) {
        for (int num : numbers) {
            if (!isPrime(num)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the array contains any non-prime numbers using a parallel approach with threads.
     *
     * @param numbers the array of numbers to check
     * @param threadCount the number of threads to use
     * @return true if there is at least one non-prime number, false otherwise
     * @throws InterruptedException if the execution is interrupted
     */
    public static boolean hasNonPrimeParallelThreads(int[] numbers, int threadCount) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Future<Boolean>> results = new ArrayList<>();

        for (int num : numbers) {
            results.add(executor.submit(() -> !isPrime(num)));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        for (Future<Boolean> result : results) {
            try {
                if (result.get()) return true;
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Checks if the array contains any non-prime numbers using a parallel stream.
     *
     * @param numbers the array of numbers to check
     * @return true if there is at least one non-prime number, false otherwise
     */
    public static boolean hasNonPrimeParallelStream(int[] numbers) {
        return Arrays.stream(numbers).parallel().anyMatch(num -> !isPrime(num));
    }
}
