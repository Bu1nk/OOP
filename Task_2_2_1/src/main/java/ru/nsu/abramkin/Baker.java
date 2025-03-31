package ru.nsu.abramkin;

/**
 * Represents a baker. The baker takes orders from the queue, prepares them, and places them in the delivery queue.
 */
public class Baker implements Runnable {

    private final int id;
    private final int speed;
    private final QueueOrder<Order> orderQueue;
    private final QueueOrder<Order> deliveryOrderQueue;

    /**
     * Constructor for the Baker class.
     *
     * @param id                  Unique baker identifier.
     * @param speed               Order preparation speed (in seconds).
     * @param orderQueue          Queue of orders for preparation.
     * @param deliveryOrderQueue  Queue of orders for delivery.
     */
    public Baker(int id, int speed, QueueOrder<Order> orderQueue,
                 QueueOrder<Order> deliveryOrderQueue) {
        this.id = id;
        this.speed = speed;
        this.orderQueue = orderQueue;
        this.deliveryOrderQueue = deliveryOrderQueue;
    }

    /**
     * Main method of the baker's work. The baker takes orders from the queue, prepares them, and places them in the delivery queue.
     */
    @Override
    public void run() {
        try {
            Order curOrder;
            while (true) {
                curOrder = orderQueue.get();
                if (curOrder == null) {
                    System.out.println("Baker " + this.id + " has finished work");
                    return;
                }
                System.out.println(this.id + " [BAKING_STARTED]");
                Thread.sleep(speed * 1000L);
                deliveryOrderQueue.insert(curOrder);
                System.out.println(this.id + " [BAKING_FINISHED]");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
