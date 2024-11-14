package ru.nsu.abramkin;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IncidenceMatrixGraph implements Graph {
    private int[][] incidenceMatrix;
    int vertexCount;
    int edgeCount;

    public IncidenceMatrixGraph(int initialVertexSize, int initialEdgeSize) {
        incidenceMatrix = new int[initialVertexSize][initialEdgeSize];
        vertexCount = 0;
        edgeCount = 0;
    }

    @Override
    public void addVertex() {
        vertexCount++;
        if (vertexCount > incidenceMatrix.length) {
            int newSize = incidenceMatrix.length + 1;
            int[][] newMatrix = new int[newSize][incidenceMatrix[0].length];

            for (int i = 0; i < incidenceMatrix.length; i++) {
                System.arraycopy(
                        incidenceMatrix[i], 0,
                        newMatrix[i], 0,
                        incidenceMatrix[i].length);
            }
            incidenceMatrix = newMatrix;
        }
    }

    @Override
    public void removeVertex(int v) {
        if (v >= vertexCount || v < 0) {
            throw new IllegalArgumentException("Вершина не существует");
        }

        for (int i = v; i < vertexCount - 1; i++) {
            incidenceMatrix[i] = incidenceMatrix[i + 1];
        }

        vertexCount--;
    }

    @Override
    public void addEdge(int v1, int v2) {
        if (v1 >= vertexCount || v2 >= vertexCount || v1 < 0 || v2 < 0) {
            throw new IllegalArgumentException("Одна или обе вершины не существуют");
        }

        edgeCount++;

        if (edgeCount > incidenceMatrix[0].length) {
            int newSize = incidenceMatrix[0].length + 1;
            int[][] newMatrix = new int[incidenceMatrix.length][newSize];

            for (int i = 0; i < incidenceMatrix.length; i++) {
                System.arraycopy(incidenceMatrix[i], 0,
                        newMatrix[i], 0,
                        incidenceMatrix[i].length);
            }
            incidenceMatrix = newMatrix;
        }

        if (v1 == v2) {
            incidenceMatrix[v1][edgeCount - 1] = 2;
        } else {
            incidenceMatrix[v1][edgeCount - 1] = 1;
            incidenceMatrix[v2][edgeCount - 1] = -1;
        }
    }

    @Override
    public void removeEdge(int v1, int v2) {
        if (v1 >= vertexCount || v2 >= vertexCount || v1 < 0 || v2 < 0) {
            throw new IllegalArgumentException("Одна или обе вершины не существуют");
        }

        boolean edgeFound = false;

        for (int j = 0; j < edgeCount; j++) {
            if ((v1 == v2 && incidenceMatrix[v1][j] == 2) ||
                    (incidenceMatrix[v1][j] == 1 && incidenceMatrix[v2][j] == -1) ||
                    (incidenceMatrix[v1][j] == -1 && incidenceMatrix[v2][j] == 1)) {

                for (int i = 0; i < vertexCount; i++) {
                    incidenceMatrix[i][j] = 0;
                }
                edgeCount--;
                edgeFound = true;
                break;
            }
        }

        if (!edgeFound) {
            throw new IllegalArgumentException("Ребро не существует");
        }
    }

    @Override
    public List<Integer> getNeighbors(int v) {
        if (v >= vertexCount || v < 0) {
            throw new IllegalArgumentException("Вершина не существует");
        }

        List<Integer> neighbors = new ArrayList<>();
        for (int j = 0; j < edgeCount; j++) {
            if (incidenceMatrix[v][j] != 0) {
                for (int i = 0; i < vertexCount; i++) {
                    if (i != v && incidenceMatrix[i][j] != 0) {
                        neighbors.add(i);
                    } else if (i == v && incidenceMatrix[i][j] == 2) {
                        neighbors.add(i);
                    }
                }
            }
        }
        return neighbors;
    }

    @Override
    public void loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String[] firstLine = br.readLine().split(" ");
            if (firstLine.length < 2) {
                throw new IllegalArgumentException(
                        "File format error: expected two integers in the first line (vertices and edges count).");
            }

            int numVertices = Integer.parseInt(firstLine[0]);
            int numEdges = Integer.parseInt(firstLine[1]);

            for (int i = 0; i < numVertices; i++) {
                addVertex();
            }

            String line;
            int edgeCount = 0;
            while ((line = br.readLine()) != null) {
                String[] edge = line.trim().split(" ");
                if (edge.length != 2) {
                    throw new IllegalArgumentException(
                            "File format error: each edge line should contain exactly two integers.");
                }

                int v1 = Integer.parseInt(edge[0]);
                int v2 = Integer.parseInt(edge[1]);

                if (v1 >= numVertices || v2 >= numVertices || v1 < 0 || v2 < 0) {
                    throw new IllegalArgumentException(
                            "Invalid vertex index in file: vertex indices must be within [0, "
                                    + (numVertices - 1)
                                    + "].");
                }

                addEdge(v1, v2);
                edgeCount++;
            }

            if (edgeCount != numEdges) {
                throw new IllegalArgumentException(
                        "File format error: number of edges in file does not match specified edge count.");
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found: "
                    + filename, e);
        } catch (IOException e) {
            throw new RuntimeException(
                    "An error occurred while reading the file: "
                    + filename, e);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "File format error: expected integer values for vertices and edges.", e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IncidenceMatrixGraph)) {
            return false;
        }
        IncidenceMatrixGraph other = (IncidenceMatrixGraph) o;
        if (this.vertexCount != other.vertexCount || this.edgeCount != other.edgeCount) {
            return false;
        }
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < edgeCount; j++) {
                if (this.incidenceMatrix[i][j] != other.incidenceMatrix[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Incidence Matrix:\n");
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < edgeCount; j++) {
                sb.append(incidenceMatrix[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
