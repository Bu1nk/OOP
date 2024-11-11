package ru.nsu.abramkin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AdjacencyMatrixGraphTest {
    private AdjacencyMatrixGraph graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyMatrixGraph(3);
    }

    @Test
    void testAddVertex() {
        graph.addVertex();
        assertEquals(1, graph.vertexCount);
    }

    @Test
    void testAddEdge() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        assertTrue(graph.hasEdge(0, 1));
        assertTrue(graph.hasEdge(1, 0));
    }

    @Test
    void testRemoveEdge() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        assertFalse(graph.hasEdge(0, 1));
        assertFalse(graph.hasEdge(1, 0));
    }

    @Test
    void testRemoveVertex() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);

        graph.removeVertex(1);

        assertEquals(1, graph.vertexCount);
        assertThrows(IllegalArgumentException.class,
                () -> graph.hasEdge(0, 1));
    }

    @Test
    void testGetNeighbors() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertEquals(1, neighbors.size());
        assertEquals(1, neighbors.get(0));
    }

    @Test
    void testHasVertex() {
        graph.addVertex();
        assertTrue(graph.hasVertex(0));
        assertFalse(graph.hasVertex(2));
    }

    @Test
    void testHasEdge() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        assertTrue(graph.hasEdge(0, 1));
        assertThrows(IllegalArgumentException.class,
                () -> graph.hasEdge(0, 2));
    }

    @Test
    void testEquals() {
        AdjacencyMatrixGraph graph2 = new AdjacencyMatrixGraph(3);
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        graph2.addVertex();
        graph2.addVertex();
        graph2.addEdge(0, 1);
        assertTrue(graph.equals(graph2));
    }

    @Test
    void testLoadFromFile() throws IOException {
        File tempFile = File.createTempFile("graph", ".txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("3\n0 1 0\n1 0 1\n0 1 0\n");
        }

        graph.loadFromFile(tempFile.getPath());
        assertEquals(3, graph.vertexCount);
        assertTrue(graph.hasEdge(0, 1));
        assertTrue(graph.hasEdge(1, 2));
    }

    @Test
    void testLoadFromFileInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> graph.loadFromFile("invalidFile.txt"));
    }

    @Test
    void testToString() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        String expectedOutput = "Adjacency Matrix:\n0 1 \n1 0 \n";
        assertEquals(expectedOutput, graph.toString());
    }
}
