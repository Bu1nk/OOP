package ru.nsu.abramkin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class IncidenceMatrixGraphTest {
    private IncidenceMatrixGraph graph;

    @BeforeEach
    void setUp() {
        graph = new IncidenceMatrixGraph(5, 5);
    }

    @Test
    void testAddVertex() {
        graph.addVertex();
        graph.addVertex();
        assertEquals(2, graph.vertexCount);
    }

    @Test
    void testRemoveVertex() {
        graph.addVertex();
        graph.addVertex();
        graph.removeVertex(1);
        assertEquals(1, graph.vertexCount);
    }

    @Test
    void testRemoveNonExistentVertex() {
        assertThrows(IllegalArgumentException.class,
                () -> graph.removeVertex(3));
    }

    @Test
    void testAddEdge() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        assertEquals(1, graph.edgeCount);
        assertEquals(List.of(1), graph.getNeighbors(0));
        assertEquals(List.of(0), graph.getNeighbors(1));
    }

    @Test
    void testRemoveEdge() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        assertEquals(0, graph.edgeCount);
        assertTrue(graph.getNeighbors(0).isEmpty());
        assertTrue(graph.getNeighbors(1).isEmpty());
    }

    @Test
    void testRemoveNonExistentEdge() {
        graph.addVertex();
        graph.addVertex();
        assertThrows(IllegalArgumentException.class,
                () -> graph.removeEdge(0, 1));
    }

    @Test
    void testGetNeighbors() {
        graph.addVertex();
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        assertEquals(List.of(1, 2), graph.getNeighbors(0));
        assertEquals(List.of(0), graph.getNeighbors(1));
    }

    @Test
    void testLoadFromFile() {
        graph.loadFromFile("testGraph.txt");
        assertEquals(3, graph.vertexCount);
        assertEquals(2, graph.edgeCount);
        assertEquals(List.of(1, 2), graph.getNeighbors(0));
    }

    @Test
    void testLoadFromNonExistentFile() {
        assertThrows(IllegalArgumentException.class,
                () -> graph.loadFromFile("nonexistentFile.txt"));
    }

    @Test
    void testEquals() {
        IncidenceMatrixGraph otherGraph = new IncidenceMatrixGraph(5, 5);
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        otherGraph.addVertex();
        otherGraph.addVertex();
        otherGraph.addEdge(0, 1);
        assertEquals(graph, otherGraph);
    }

    @Test
    public void testToString() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(2, 2);
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        graph.addEdge(1, 0);

        String expectedMatrix = "Incidence Matrix:\n1 -1 \n-1 1 \n";
        assertEquals(expectedMatrix, graph.toString());
    }
}
