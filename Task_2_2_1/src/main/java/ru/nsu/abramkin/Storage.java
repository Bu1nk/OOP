package ru.nsu.abramkin;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Storage {
    private final Queue<Order> storage;
    private final int capacity;

    public Storage(int capacity) {
        this.capacity = capacity;
        this.storage = new LinkedList<>();
    }

    public synchronized void put(Order order) throws InterruptedException {
        while (storage.size() >= capacity) {
            wait();
        }
        storage.add(order);
        System.out.println("[Заказ " + order.id + "] Добавлен на склад");
        notifyAll();
    }

    public synchronized List<Order> take(int count) throws InterruptedException {
        while (storage.isEmpty()) {
            wait();
        }
        List<Order> orders = new LinkedList<>();
        for (int i = 0; i < count && !storage.isEmpty(); i++) {
            orders.add(storage.poll());
        }
        notifyAll();
        return orders;
    }
}
