package ru.nsu.abramkin;

import java.util.Queue;

public class Baker extends Thread {
    private final int id;
    private final int speed;
    private final Queue<Order> orderQueue;
    private final Storage storage;

    public Baker(int id, int speed, Queue<Order> orderQueue, Storage storage) {
        this.id = id;
        this.speed = speed;
        this.orderQueue = orderQueue;
        this.storage = storage;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Order order;
            synchronized (orderQueue) {
                if (orderQueue.isEmpty()) continue;
                order = orderQueue.poll();
            }
            try {
                System.out.println(order.getId() + " Готовится пекарем " + id);
                Thread.sleep(speed * 1000);
                storage.store(order);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
