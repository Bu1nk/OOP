package ru.nsu.abramkin;

import java.util.*;

public class Storage {
    private final int capacity;
    private final Queue<Order> storage = new LinkedList<>();

    public Storage(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void store(Order order) throws InterruptedException {
        while (storage.size() >= capacity) wait();
        storage.add(order);
        System.out.println(order.getId() + " Перемещена на склад");
        notifyAll();
    }

    public synchronized List<Order> take(int count) throws InterruptedException {
        while (storage.isEmpty()) wait();
        List<Order> batch = new ArrayList<>();
        for (int i = 0; i < count && !storage.isEmpty(); i++) {
            batch.add(storage.poll());
        }
        notifyAll();
        return batch;
    }
}
