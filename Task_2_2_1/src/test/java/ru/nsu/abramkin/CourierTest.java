package ru.nsu.abramkin;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Thread.sleep(2000); // Give courier time to deliver

        assertEquals(0, deliveryQueue.getSize());
        courierThread.interrupt();
    }
}
