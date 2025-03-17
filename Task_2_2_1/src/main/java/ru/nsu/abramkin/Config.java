package ru.nsu.abramkin;

import java.util.List;

public class Config {
    public int storageCapacity;
    public List<Integer> bakerSpeeds;
    public List<Integer> courierCapacities;

    public int getStorageCapacity() {
        return storageCapacity;
    }

    public List<Integer> getBakerSpeeds() {
        return bakerSpeeds;
    }

    public List<Integer> getCourierCapacities() {
        return courierCapacities;
    }
}
