package ru.nsu.abramkin;

/**
 * Represents an order.
 */
public class Order {
    final int id;
    String status;

    /**
     * Constructor for the Order class.
     *
     * @param id     Order ID.
     * @param status Order status.
     */
    public Order(int id, String status) {
        this.id = id;
        this.status = status;
    }

    /**
     * Sets a new status for the order.
     *
     * @param newStatus New order status.
     */
    public synchronized void setStatus(String newStatus) {
        this.status = newStatus;
    }

    /**
     * Gets the order ID.
     *
     * @return Order ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the order status.
     *
     * @return Order status.
     */
    public String getStatus() {
        return status;
    }
}
