package ru.nsu.abramkin;

import java.util.List;
import java.util.Queue;

public class Courier extends Thread {
    private final int courierId;
    private final int capacity;
    private final Storage storage;
    private final Queue<Order> orderQueue;

    public Courier(int courierId, int capacity, Storage storage, Queue<Order> orderQueue) {
        this.courierId = courierId;
        this.capacity = capacity;
        this.storage = storage;
        this.orderQueue = orderQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Order order;
                synchronized (orderQueue) {
                    if (orderQueue.isEmpty()) break;
                    order = orderQueue.poll();
                }
                List<Order> orders = storage.take(capacity);
                System.out.println("[Заказ " + order.id + "] Курьер " + courierId + " доставляет " + orders.size() + " пицц");
                Thread.sleep(3000);
                if (orders.isEmpty()) break;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
