package ru.nsu.abramkin;

import java.util.Queue;

public class Baker extends Thread {
    private final int bakerId;
    private final int speed;
    private final Storage storage;
    private final Queue<Order> orderQueue;

    public Baker(int bakerId, int speed, Storage storage, Queue<Order> orderQueue) {
        this.bakerId = bakerId;
        this.speed = speed;
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
                System.out.println("[Заказ " + order.id + "] Готовится пекарем " + bakerId);
                Thread.sleep(speed * 1000L);
                storage.put(order);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
