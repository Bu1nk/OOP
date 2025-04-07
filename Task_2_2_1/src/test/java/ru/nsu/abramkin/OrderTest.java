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
     * Additional test to verify the behavior when an order's status is set to a null value.
     * This ensures that the status can be updated to null without causing issues.
     */
    @Test
    void testSetNullStatus() {
        Order order = new Order(1, "Pending");
        order.setStatus(null);
        assertEquals(null, order.getStatus());
    }

    /**
     * Additional test to check if the Order ID is properly assigned.
     * This verifies that the ID is immutable after initialization.
     */
    @Test
    void testOrderIdImmutability() {
        Order order = new Order(1, "Pending");
        int id = order.getId();

        // Order ID should remain constant
        assertEquals(1, id);
        order.setStatus("Processing");
        assertEquals(1, order.getId());  // ID should remain unchanged
    }

    /**
     * Test method for setting a new status for an order.
     */
    @Test
    void testSetNewStatus() {
        Order order = new Order(1, "Pending");
        order.setStatus("Processing");
        assertEquals("Processing", order.getStatus());
    }
}
