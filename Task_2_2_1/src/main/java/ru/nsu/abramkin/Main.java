package ru.nsu.abramkin;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Config config = mapper.readValue(new File("config.json"), Config.class);

        Pizzeria pizzeria = new Pizzeria(config);
        pizzeria.startSimulation(20);
    }
}
