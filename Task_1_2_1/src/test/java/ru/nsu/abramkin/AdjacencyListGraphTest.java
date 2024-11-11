package ru.nsu.abramkin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AdjacencyListGraphTest {
    private AdjacencyListGraph graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyListGraph();
    }

    @Test
    void testAddVertex() {
        graph.addVertex();
        graph.addVertex();
        assertEquals(2, graph.vertexCount);
    }

    @Test
    void testAddEdge() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        assertEquals(List.of(1), graph.getNeighbors(0));
        assertEquals(List.of(0), graph.getNeighbors(1));
    }

    @Test
    void testRemoveEdge() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        assertTrue(graph.getNeighbors(0).isEmpty());
        assertTrue(graph.getNeighbors(1).isEmpty());
    }

    @Test
    void testRemoveVertex() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        graph.removeVertex(0);
        assertEquals(1, graph.vertexCount);
        assertTrue(graph.getNeighbors(0).isEmpty());
    }

    @Test
    void testGetNeighbors() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        assertEquals(List.of(1), graph.getNeighbors(0));
        assertEquals(List.of(0), graph.getNeighbors(1));
    }

    @Test
    void testLoadFromFile() {
        graph.loadFromFile("testGraphList.txt");

        assertEquals(3, graph.vertexCount);
        assertEquals(List.of(1), graph.getNeighbors(0));
        assertEquals(List.of(0, 2), graph.getNeighbors(1));
        assertEquals(List.of(1), graph.getNeighbors(2));
    }

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

    @Test
    void testToString() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        String expectedOutput = "Adjacency List:\n0: [1]\n1: [0]\n";
        assertEquals(expectedOutput, graph.toString());
    }
}
