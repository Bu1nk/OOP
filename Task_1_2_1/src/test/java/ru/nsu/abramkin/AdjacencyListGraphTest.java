package ru.nsu.abramkin;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса AdjacencyListGraph.
 */
class AdjacencyListGraphTest {
    private AdjacencyListGraph graph;

    /**
     * Устанавливает начальное состояние графа перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        graph = new AdjacencyListGraph();
    }

    /**
     * Тест добавления вершины в граф.
     */
    @Test
    void testAddVertex() {
        graph.addVertex();
        graph.addVertex();
        assertEquals(2, graph.vertexCount);
    }

    /**
     * Тест добавления ребра между вершинами.
     */
    @Test
    void testAddEdge() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        assertEquals(List.of(1), graph.getNeighbors(0));
        assertEquals(List.of(0), graph.getNeighbors(1));
    }

    /**
     * Тест удаления ребра между вершинами.
     */
    @Test
    void testRemoveEdge() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        assertTrue(graph.getNeighbors(0).isEmpty());
        assertTrue(graph.getNeighbors(1).isEmpty());
    }

    /**
     * Тест удаления вершины из графа.
     */
    @Test
    void testRemoveVertex() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        graph.removeVertex(0);
        assertEquals(1, graph.vertexCount);
        assertTrue(graph.getNeighbors(0).isEmpty());
    }

    /**
     * Тест получения списка соседей вершины.
     */
    @Test
    void testGetNeighbors() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        assertEquals(List.of(1), graph.getNeighbors(0));
        assertEquals(List.of(0), graph.getNeighbors(1));
    }

    /**
     * Тест загрузки графа из файла.
     */
    @Test
    void testLoadFromFile() {
        graph.loadFromFile("testGraphList.txt");

        assertEquals(3, graph.vertexCount);
        assertEquals(List.of(1), graph.getNeighbors(0));
        assertEquals(List.of(0, 2), graph.getNeighbors(1));
        assertEquals(List.of(1), graph.getNeighbors(2));
    }

    /**
     * Тест проверки равенства двух графов.
     */
    @Test
    void testEquals() {
        AdjacencyListGraph graph1 = new AdjacencyListGraph();
        AdjacencyListGraph graph2 = new AdjacencyListGraph();

        graph1.addVertex();
        graph1.addVertex();
        graph1.addEdge(0, 1);

        graph2.addVertex();
        graph2.addVertex();
        graph2.addEdge(0, 1);

        assertEquals(graph1, graph2);

        graph2.addVertex();
        assertNotEquals(graph1, graph2);
    }

    /**
     * Тест строкового представления графа.
     */
    @Test
    void testToString() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        String expectedOutput = "Adjacency List:\n0: [1]\n1: [0]\n";
        assertEquals(expectedOutput, graph.toString());
    }
}
