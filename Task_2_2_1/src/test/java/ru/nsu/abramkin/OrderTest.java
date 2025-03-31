package ru.nsu.abramkin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class for the Order class.
 */
class OrderTest {

    /**
     * Test method for Order creation.
     */
    @Test
    void testOrderCreation() {
        Order order = new Order(1, "Pending");
        assertEquals(1, order.getId());
        assertEquals("Pending", order.getStatus());
    }

    /**
     * Test method for setting a new status for an order.
     */
    @Test
    void testSetNewStatus() {
        Order order = new Order(1, "Pending");
        order.setNewStatus("Processing");
        assertEquals("Processing", order.getStatus());
    }
}
