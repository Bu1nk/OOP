package ru.nsu.abramkin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Класс для представления графа с использованием матрицы смежности.
 */
public class AdjacencyMatrixGraph implements Graph {
    private int[][] adjacencyMatrix;
    int vertexCount;

    /**
     * Конструктор графа с начальной емкостью.
     *
     * @param initialSize начальное количество вершин.
     */
    public AdjacencyMatrixGraph(int initialSize) {
        adjacencyMatrix = new int[initialSize][initialSize];
        vertexCount = 0;
    }

    /**
     * Проверяет, существует ли вершина в графе.
     *
     * @param vertex индекс вершины.
     * @return true, если вершина существует, иначе false.
     */
    public boolean hasVertex(int vertex) {
        return vertex >= 0 && vertex < vertexCount;
    }

    /**
     * Проверяет, существует ли ребро между двумя вершинами.
     *
     * @param v1 индекс первой вершины.
     * @param v2 индекс второй вершины.
     * @return true, если ребро существует, иначе false.
     * @throws IllegalArgumentException если одна или обе вершины не существуют.
     */
    public boolean hasEdge(int v1, int v2) {
        if (v1 >= vertexCount || v2 >= vertexCount || v1 < 0 || v2 < 0) {
            throw new IllegalArgumentException(
                    String.format("Одна или обе вершины с номерами %d, %d не существуют",
                            v1, v2));
        }
        return adjacencyMatrix[v1][v2] == 1;
    }

    /**
     * Добавляет новую вершину в граф.
     */
    @Override
    public void addVertex() {
        vertexCount++;

        if (vertexCount > adjacencyMatrix.length) {
            int newSize = adjacencyMatrix.length + 1;
            int[][] newMatrix = new int[newSize][newSize];

            for (int i = 0; i < adjacencyMatrix.length; i++) {
                System.arraycopy(
                        adjacencyMatrix[i],
                        0,
                        newMatrix[i],
                        0,
                        adjacencyMatrix[i].length);
            }
            adjacencyMatrix = newMatrix;
        }
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

        for (int i = v; i < vertexCount - 1; i++) {
            adjacencyMatrix[i] = adjacencyMatrix[i + 1];
        }

        for (int i = 0; i < vertexCount - 1; i++) {
            for (int j = v; j < vertexCount - 1; j++) {
                adjacencyMatrix[i][j] = adjacencyMatrix[i][j + 1];
            }
        }

        vertexCount--;
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
        adjacencyMatrix[v1][v2] = 1;
        adjacencyMatrix[v2][v1] = 1; // Для неориентированного графа
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
        adjacencyMatrix[v1][v2] = 0;
        adjacencyMatrix[v2][v1] = 0; // Для неориентированного графа
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

        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            if (adjacencyMatrix[v][i] == 1) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    /**
     * Загружает граф из файла.
     *
     * @param filename имя файла.
     * @throws IllegalArgumentException если файл не найден или содержит ошибки.
     */
    @Override
    public void loadFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            int vertCount = 0;

            if (!scanner.hasNextInt()) {
                throw new IllegalArgumentException(
                        "Impossible to know the matrix size");
            }
            vertCount = scanner.nextInt();
            int[][] newMatrix = new int[vertCount][vertCount];

            for (int i = 0; i < vertCount; i++) {
                for (int j = 0; j < vertCount; j++) {
                    if (!scanner.hasNextInt()) {
                        throw new IllegalArgumentException(
                                "Impossible to find out the matrix values");
                    }
                    int tmp = scanner.nextInt();
                    newMatrix[i][j] = tmp;
                }
            }
            adjacencyMatrix = newMatrix;
            vertexCount = vertCount;
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error reading file " + filename);
            throw new IllegalArgumentException("File not found: " + filename, e);
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
        if (!(o instanceof AdjacencyMatrixGraph)) {
            return false;
        }
        AdjacencyMatrixGraph other = (AdjacencyMatrixGraph) o;
        if (this.vertexCount != other.vertexCount) {
            return false;
        }
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (this.adjacencyMatrix[i][j] != other.adjacencyMatrix[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Преобразует граф в строку.
     *
     * @return строковое представление графа.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Adjacency Matrix:\n");
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                sb.append(adjacencyMatrix[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}


