package ru.nsu.abramkin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса IncidenceMatrixGraph.
 */
class IncidenceMatrixGraphTest {
    private IncidenceMatrixGraph graph;

    /**
     * Устанавливает начальное состояние графа перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        graph = new IncidenceMatrixGraph(5, 5);
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
     * Тест удаления вершины из графа.
     */
    @Test
    void testRemoveVertex() {
        graph.addVertex();
        graph.addVertex();
        graph.removeVertex(1);
        assertEquals(1, graph.vertexCount);
    }

    /**
     * Тест удаления несуществующей вершины.
     */
    @Test
    void testRemoveNonExistentVertex() {
        assertThrows(IllegalArgumentException.class,
                () -> graph.removeVertex(3));
    }

    /**
     * Тест добавления ребра между вершинами.
     */
    @Test
    void testAddEdge() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        assertEquals(1, graph.edgeCount);
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
        assertEquals(0, graph.edgeCount);
        assertTrue(graph.getNeighbors(0).isEmpty());
        assertTrue(graph.getNeighbors(1).isEmpty());
    }

    /**
     * Тест удаления несуществующего ребра.
     */
    @Test
    void testRemoveNonExistentEdge() {
        graph.addVertex();
        graph.addVertex();
        assertThrows(IllegalArgumentException.class,
                () -> graph.removeEdge(0, 1));
    }

    /**
     * Тест получения списка соседей вершины.
     */
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

    /**
     * Тест загрузки графа из файла.
     */
    @Test
    void testLoadFromFile() {
        graph.loadFromFile("testGraph.txt");
        assertEquals(3, graph.vertexCount);
        assertEquals(2, graph.edgeCount);
        assertEquals(List.of(1, 2), graph.getNeighbors(0));
    }

    /**
     * Тест загрузки графа из несуществующего файла.
     */
    @Test
    void testLoadFromNonExistentFile() {
        assertThrows(IllegalArgumentException.class,
                () -> graph.loadFromFile("nonexistentFile.txt"));
    }

    /**
     * Тест проверки равенства двух графов.
     */
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

    /**
     * Тест строкового представления графа.
     */
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

    /**
     * Тест добавления нескольких вершин и ребер.
     */
    @Test
    void testAddMultipleVerticesAndEdges() {
        for (int i = 0; i < 10; i++) {
            graph.addVertex();
        }
        for (int i = 0; i < 5; i++) {
            graph.addEdge(i, i + 1);
        }
        assertEquals(10, graph.vertexCount);
        assertEquals(5, graph.edgeCount);
    }

    /**
     * Тест повторного добавления ребра после удаления.
     */
    @Test
    void testReAddEdgeAfterRemoval() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        assertEquals(0, graph.edgeCount);
        graph.addEdge(0, 1);
        assertEquals(1, graph.edgeCount);
        assertEquals(List.of(1), graph.getNeighbors(0));
    }

    /**
     * Тест добавления вершины после удаления всех ребер.
     */
    @Test
    void testAddVertexAfterRemovingAllEdges() {
        graph.addVertex();
        graph.addVertex();
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        graph.addVertex();
        assertEquals(3, graph.vertexCount);
        assertEquals(0, graph.edgeCount);
    }

    /**
     * Тест добавления самопетли (ребра, соединяющего вершину саму с собой).
     */
    @Test
    void testAddLoopEdge() {
        graph.addVertex();
        graph.addEdge(0, 0);
        assertEquals(1, graph.edgeCount);
        assertEquals(List.of(0), graph.getNeighbors(0));
    }

    /**
     * Тест двойного удаления вершины.
     */
    @Test
    void testRemoveVertexTwice() {
        graph.addVertex();
        graph.addVertex();
        graph.removeVertex(1);
        assertThrows(IllegalArgumentException.class,
                () -> graph.removeVertex(1));
    }

    /**
     * Тест загрузки пустого файла.
     */
    @Test
    void testLoadEmptyFile() {
        assertThrows(IllegalArgumentException.class,
                () -> graph.loadFromFile("emptyFile.txt"));
    }

    /**
     * Тест добавления ребра с отрицательным индексом.
     */
    @Test
    void testAddEdgeWithNegativeIndex() {
        graph.addVertex();
        graph.addVertex();
        assertThrows(IllegalArgumentException.class,
                () -> graph.addEdge(-1, 1));
        assertThrows(IllegalArgumentException.class,
                () -> graph.addEdge(0, -1));
    }

    /**
     * Тест удаления самопетли.
     */
    @Test
    void testRemoveLoopEdge() {
        graph.addVertex();
        graph.addEdge(0, 0);
        graph.removeEdge(0, 0);
        assertEquals(0, graph.edgeCount);
        assertTrue(graph.getNeighbors(0).isEmpty());
    }
}
