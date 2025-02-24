package ru.nsu.abramkin;

import java.util.concurrent.*;
import java.util.*;

public class Pizzeria {
    private final Queue<Order> orderQueue = new LinkedList<>();
    private final Storage storage;
    private final List<Baker> bakers = new ArrayList<>();
    private final List<Courier> couriers = new ArrayList<>();
    private final ExecutorService executorService;
    private final int workingTime;
    private boolean isOpen = true;

    public Pizzeria(int bakersCount, int couriersCount, int storageSize, int workingTime, int[] bakerSpeeds, int[] courierCapacities) {
        this.storage = new Storage(storageSize);
        this.workingTime = workingTime;
        this.executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < bakersCount; i++) {
            bakers.add(new Baker(i + 1, bakerSpeeds[i], orderQueue, storage));
        }
        for (int i = 0; i < couriersCount; i++) {
            couriers.add(new Courier(i + 1, courierCapacities[i], storage));
        }
    }

    public void start() {
        System.out.println("Пиццерия открыта!");

        for (Baker baker : bakers) {
            executorService.submit(baker);
        }
        for (Courier courier : couriers) {
            executorService.submit(courier);
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                close();
            }
        }, workingTime * 1000);
    }

    public synchronized void addOrder(Order order) {
        if (isOpen) {
            orderQueue.add(order);
            System.out.println("Заказ " + order.getId() + " добавлен в очередь.");
            notifyAll();
        }
    }

    public synchronized void close() {
        isOpen = false;
        System.out.println("Пиццерия закрывается! Завершаем заказы...");
        executorService.shutdown();
    }

    public static void main(String[] args) {
        ConfigLoader config = ConfigLoader.loadConfig("config.json");
        Pizzeria pizzeria = new Pizzeria(config.getBakersCount(), config.getCouriersCount(), config.getStorageSize(),
                config.getWorkingTime(), config.getBakerSpeeds(), config.getCourierCapacities());

        pizzeria.start();

        for (int i = 1; i <= 20; i++) {
            pizzeria.addOrder(new Order(i));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
