package ru.nsu.abramkin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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
        if (num < 2) {
            return false;
        }
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the array contains any non-prime numbers using a sequential approach.
     *
     * @param numbers the array of numbers to check
     * @return true if there is at least one non-prime number, false otherwise
     */
    public static boolean hasCompositeSequential(int[] numbers) {
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
    public static boolean hasCompositeParallelThreads(int[] numbers, int threadCount)
            throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        int chunkSize = (int) Math.ceil((double) numbers.length / threadCount);
        List<Future<Boolean>> results = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            final int start = i * chunkSize;
            final int end = Math.min(start + chunkSize, numbers.length);

            if (start >= end) break; // Если больше нечего обрабатывать, выходим

            results.add(executor.submit(() -> {
                for (int j = start; j < end; j++) {
                    if (!isPrime(numbers[j])) {
                        return true;
                    }
                }
                return false;
            }));
        }

        executor.shutdown();

        // Ожидаем завершения всех задач
        executor.awaitTermination(1, TimeUnit.MINUTES);

        for (Future<Boolean> result : results) {
            try {
                if (result.get()) {
                    return true; // Если хотя бы одна часть содержит составное число
                }
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
    public static boolean hasCompositeParallelStream(int[] numbers) {
        return Arrays.stream(numbers)
                .parallel()
                .anyMatch(num -> !isPrime(num));
    }
}
