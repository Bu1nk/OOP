package ru.nsu.abramkin;

import java.util.List;

public class Courier extends Thread {
    private final int id;
    private final int capacity;
    private final Storage storage;

    public Courier(int id, int capacity, Storage storage) {
        this.id = id;
        this.capacity = capacity;
        this.storage = storage;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                List<Order> orders = storage.take(capacity);
                System.out.println("Курьер " + id + " забрал " + orders.size() + " пицц");
                Thread.sleep(5000);
                orders.forEach(o -> System.out.println(o.getId() + " Доставлена курьером " + id));
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
