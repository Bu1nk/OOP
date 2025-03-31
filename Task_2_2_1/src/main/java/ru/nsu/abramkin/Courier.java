package ru.nsu.abramkin;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a courier. The courier picks up ready orders from the queue and delivers them.
 */
public class Courier implements Runnable {
    private final int id;
    private final int cap;
    private final int speed;
    private final QueueOrder<Order> orderQueue;

    /**
     * Constructor for the Courier class.
     *
     * @param id                  Unique courier identifier.
     * @param cap                 Courier capacity (maximum orders per delivery).
     * @param speed               Delivery speed (in seconds).
     * @param deliveryOrderQueue  Queue of orders for delivery.
     */
    public Courier(int id, int cap, int speed, QueueOrder<Order> deliveryOrderQueue) {
        this.id = id;
        this.cap = cap;
        this.speed = speed;
        this.orderQueue = deliveryOrderQueue;
    }

    /**
     * Main method of the courier's work. The courier picks up orders from the queue and delivers them.
     */
    @Override
    public void run() {
        try {
            int size = 0;
            List<Integer> orders = new ArrayList<>();
            while (!orderQueue.isClosed() || (orderQueue.isClosed() && orderQueue.getSize() != 0)
                    || (orderQueue.isClosed() && size != 0)) {
                Order order = orderQueue.see();
                if (size == 0 && order == null) {
                    continue;
                } else if ((order == null && size > 0) || size == this.cap) {
                    System.out.println(this.id + " [DELIVERY_STARTED] " + orders);
                    size = 0;
                    Thread.sleep(this.speed * 1000L);
                    System.out.println(this.id + " [DELIVERY_FINISHED] " + orders);
                    orders.clear();
                    continue;
                }
                order = orderQueue.get();
                orders.add(order.getId());
                size++;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
