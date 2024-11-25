package ru.nsu.abramkin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс для представления графа с использованием списка смежности.
 */
public class AdjacencyListGraph implements Graph {
    private List<List<Integer>> adjacencyList;
    int vertexCount;

    /**
     * Конструктор графа с начальной емкостью.
     */
    public AdjacencyListGraph() {
        adjacencyList = new ArrayList<>();
        vertexCount = 0;
    }

    /**
     * Добавляет новую вершину в граф.
     */
    @Override
    public void addVertex() {
        adjacencyList.add(new LinkedList<>());
        vertexCount++;
    }

    /**
     * Удаляет вершину из графа.
     *
     * @param v индекс вершины.
     * @throws IllegalArgumentException если вершина не существует.
     */
    @Override
    public void removeVertex(int v) {
        if (v >= vertexCount || v < 0) {
            throw new IllegalArgumentException(
                    String.format("Вершина с номером %d не существует", v));
        }

        adjacencyList.remove(v);
        vertexCount--;

        for (List<Integer> neighbors : adjacencyList) {
            neighbors.remove(Integer.valueOf(v));
        }

        for (List<Integer> neighbors : adjacencyList) {
            for (int i = 0; i < neighbors.size(); i++) {
                int neighbor = neighbors.get(i);
                if (neighbor > v) {
                    neighbors.set(i, neighbor - 1);
                }
            }
        }
    }

    /**
     * Добавляет ребро между двумя вершинами.
     *
     * @param v1 индекс первой вершины.
     * @param v2 индекс второй вершины.
     * @throws IllegalArgumentException если одна или обе вершины не существуют.
     */
    @Override
    public void addEdge(int v1, int v2) {
        if (v1 >= vertexCount || v2 >= vertexCount || v1 < 0 || v2 < 0) {
            throw new IllegalArgumentException(
                    String.format("Одна или обе вершины с номерами %d, %d не существуют",
                            v1, v2));
        }

        adjacencyList.get(v1).add(v2);
        adjacencyList.get(v2).add(v1); // Для неориентированного графа
    }

    /**
     * Удаляет ребро между двумя вершинами.
     *
     * @param v1 индекс первой вершины.
     * @param v2 индекс второй вершины.
     * @throws IllegalArgumentException если одна или обе вершины не существуют.
     */
    @Override
    public void removeEdge(int v1, int v2) {
        if (v1 >= vertexCount || v2 >= vertexCount || v1 < 0 || v2 < 0) {
            throw new IllegalArgumentException(
                    String.format("Одна или обе вершины с номерами %d, %d не существуют",
                            v1, v2));
        }

        adjacencyList.get(v1).remove(Integer.valueOf(v2));
        adjacencyList.get(v2).remove(Integer.valueOf(v1)); // Для неориентированного графа
    }

    /**
     * Возвращает список соседей для заданной вершины.
     *
     * @param v индекс вершины.
     * @return список индексов соседних вершин.
     * @throws IllegalArgumentException если вершина не существует.
     */
    @Override
    public List<Integer> getNeighbors(int v) {
        if (v >= vertexCount || v < 0) {
            throw new IllegalArgumentException(
                    String.format("Вершина с номером %d не существует", v));
        }
        return new ArrayList<>(adjacencyList.get(v));
    }

    /**
     * Загружает граф из файла.
     *
     * @param filename имя файла.
     * @throws IllegalArgumentException если файл не найден или содержит ошибки.
     */
    @Override
    public void loadFromFile(String filename) throws IOException{
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String[] firstLine = br.readLine().split(" ");
            int numVertices = Integer.parseInt(firstLine[0]);
            int numEdges = Integer.parseInt(firstLine[1]);

            // Убедимся, что граф из файла загружается с чистого состояния.
            adjacencyList.clear();
            vertexCount = 0;

            for (int i = 0; i < numVertices; i++) {
                addVertex();
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] edge = line.split(" ");
                int v1 = Integer.parseInt(edge[0]);
                int v2 = Integer.parseInt(edge[1]);
                addEdge(v1, v2);
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Ошибка формата файла графа: " + filename, e);
        }
    }


    /**
     * Проверяет равенство двух графов.
     *
     * @param o объект для сравнения.
     * @return true, если графы равны, иначе false.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AdjacencyListGraph)) {
            return false;
        }
        AdjacencyListGraph other = (AdjacencyListGraph) o;
        return adjacencyList.equals(other.adjacencyList);
    }

    /**
     * Преобразует граф в строку.
     *
     * @return строковое представление графа.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Adjacency List:\n");
        for (int i = 0; i < adjacencyList.size(); i++) {
            sb.append(i).append(": ").append(adjacencyList.get(i)).append("\n");
        }
        return sb.toString();
    }
}
