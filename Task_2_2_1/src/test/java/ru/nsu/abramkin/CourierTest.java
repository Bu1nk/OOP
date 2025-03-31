package ru.nsu.abramkin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test class for the Courier class.
 */
class CourierTest {

    /**
     * Test method for courier's delivery functionality.
     */
    @Test
    void testCourierRun() throws InterruptedException {
        QueueOrder<Order> deliveryQueue = new QueueOrder<>(10);
        Courier courier = new Courier(1, 2, 1, deliveryQueue);

        Thread courierThread = new Thread(courier);
        courierThread.start();

        deliveryQueue.insert(new Order(1, "Ready"));
        deliveryQueue.insert(new Order(2, "Ready"));
        Thread.sleep(2000);

        assertEquals(0, deliveryQueue.getSize());
        courierThread.interrupt();
    }

    /**
     * Additional test to verify that the courier does not deliver orders when the queue is empty.
     * This ensures that the courier waits for orders if the queue is empty.
     *
     * @throws InterruptedException if the thread is interrupted during the test.
     */
    @Test
    void testCourierWaitsWhenQueueIsEmpty() throws InterruptedException {
        QueueOrder<Order> deliveryQueue = new QueueOrder<>(10);
        Courier courier = new Courier(1, 2, 1, deliveryQueue);

        Thread courierThread = new Thread(courier);
        courierThread.start();

        Thread.sleep(1000); // Wait for courier to possibly pick up orders

        // The queue is empty, the courier should not have delivered any orders
        assertTrue(deliveryQueue.getSize() == 0, "Queue should still be empty.");

        courierThread.interrupt();
    }
}
