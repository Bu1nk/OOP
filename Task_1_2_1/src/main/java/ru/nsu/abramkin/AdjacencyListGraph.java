package ru.nsu.abramkin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AdjacencyListGraph implements Graph {
    private List<List<Integer>> adjacencyList;
    int vertexCount;

    public AdjacencyListGraph() {
        adjacencyList = new ArrayList<>();
        vertexCount = 0;
    }

    @Override
    public void addVertex() {
        adjacencyList.add(new LinkedList<>());
        vertexCount++;
    }

    @Override
    public void removeVertex(int v) {
        if (v >= vertexCount || v < 0) {
            throw new IllegalArgumentException("Вершина не существует");
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

    @Override
    public void addEdge(int v1, int v2) {
        if (v1 >= vertexCount || v2 >= vertexCount || v1 < 0 || v2 < 0) {
            throw new IllegalArgumentException("Одна или обе вершины не существуют");
        }

        adjacencyList.get(v1).add(v2);
        adjacencyList.get(v2).add(v1); // Для неориентированного графа
    }

    @Override
    public void removeEdge(int v1, int v2) {
        if (v1 >= vertexCount || v2 >= vertexCount || v1 < 0 || v2 < 0) {
            throw new IllegalArgumentException("Одна или обе вершины не существуют");
        }

        adjacencyList.get(v1).remove(Integer.valueOf(v2));
        adjacencyList.get(v2).remove(Integer.valueOf(v1)); // Для неориентированного графа
    }

    @Override
    public List<Integer> getNeighbors(int v) {
        if (v >= vertexCount || v < 0) {
            throw new IllegalArgumentException("Вершина не существует");
        }
        return new ArrayList<>(adjacencyList.get(v));
    }

    @Override
    public void loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String[] firstLine = br.readLine().split(" ");
            int numVertices = Integer.parseInt(firstLine[0]);
            int numEdges = Integer.parseInt(firstLine[1]);

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AdjacencyListGraph)) {
            return false;
        }
        AdjacencyListGraph other = (AdjacencyListGraph) o;
        return adjacencyList.equals(other.adjacencyList);
    }

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
