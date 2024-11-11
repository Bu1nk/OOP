package ru.nsu.abramkin;

import java.util.List;

public interface Graph {
    void addVertex(); // Добавление новой вершины без указания индекса
    void removeVertex(int v); // Удаление вершины по индексу
    void addEdge(int v1, int v2); // Добавление ребра
    void removeEdge(int v1, int v2); // Удаление ребра
    List<Integer> getNeighbors(int v); // Получение списка соседей вершины
    void loadFromFile(String filename); // Чтение графа из файла
    boolean equals(Object o); // Сравнение графов
    String toString(); // Преобразование графа в строку
}
