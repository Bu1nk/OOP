package ru.nsu.abramkin;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Pizzeria {
    private final List<Baker> bakers = new LinkedList<>();
    private final List<Courier> couriers = new LinkedList<>();
    private final Queue<Order> orderQueue = new LinkedList<>();
    private final Storage storage;

    public Pizzeria(Config config) {
        this.storage = new Storage(config.getStorageCapacity());

        int bakerId = 1;
        for (int speed : config.getBakerSpeeds()) {
            bakers.add(new Baker(bakerId++, speed, storage, orderQueue));
        }

        int courierId = 1;
        for (int cap : config.getCourierCapacities()) {
            couriers.add(new Courier(courierId++, cap, storage, orderQueue));
        }
    }

    public void startSimulation(int orderCount) {
        for (int i = 0; i < orderCount; i++) {
            orderQueue.add(new Order());
        }
        bakers.forEach(Thread::start);
        couriers.forEach(Thread::start);
        for (Baker baker : bakers) {
            try {
                baker.join();
            } catch (InterruptedException ignored) {}
        }
        for (Courier courier : couriers) {
            courier.interrupt();
        }
    }
}
