package ru.nsu.abramkin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Test class for the QueueOrder class.
 */
class OrderQueueTest {

    /**
     * Test method for inserting and getting elements from the queue.
     */
    @Test
    void testInsertAndGet() throws InterruptedException {
        OrderQueue<Integer> queue = new OrderQueue<>(5);
        queue.insert(1);
        queue.insert(2);
        assertEquals(1, queue.get());
        assertEquals(2, queue.get());
    }

    /**
     * Additional test to verify that the queue can still retrieve items when closed, if they were added before closure.
     *
     * @throws InterruptedException if the thread is interrupted during execution.
     */
    @Test
    void testGetFromClosedQueue() throws InterruptedException {
        OrderQueue<Integer> queue = new OrderQueue<>(2);
        queue.insert(1);
        queue.close();

        assertEquals(1, queue.get());
    }

    /**
     * Test method to verify the queue capacity.
     */
    @Test
    void testQueueCapacity() throws InterruptedException {
        OrderQueue<Integer> queue = new OrderQueue<>(2);
        queue.insert(1);
        queue.insert(2);
        Thread thread = new Thread(() -> {
            try {
                queue.insert(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(100); // Give the thread time to start
        assertEquals(2, queue.getSize());
    }

    /**
     * Test method for closing the queue.
     */
    @Test
    void testCloseQueue() throws InterruptedException {
        OrderQueue<Integer> queue = new OrderQueue<>(2);
        queue.close();
        assertNull(queue.get());
    }
}
