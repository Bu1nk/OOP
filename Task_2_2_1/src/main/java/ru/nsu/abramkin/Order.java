package ru.nsu.abramkin;

public class Order {
    private static int idCounter = 1;
    int id;

    public Order() {
        this.id = idCounter++;
    }
}
