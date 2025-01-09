package org.adp_implementatie;

import java.util.Arrays;

public class Graph {
    private int vertices;
    private Edge[] adjacencyList;

    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new Edge[vertices * 2];
    }

    static class Edge {
        int vertex;
        int weight;
        Edge next;

        Edge(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
            this.next = null;
        }
    }

    public void addVertex(int index) {
        if (index >= adjacencyList.length) {
            adjacencyList = copyArray(adjacencyList, adjacencyList.length * 2);
        }
        if (index >= vertices) {
            vertices = index + 1;
        }
    }

    public void removeVertex(int index) {
        adjacencyList[index] = null;
        if (vertices == index + 1) {
            for (int i = 0; i < adjacencyList.length; i++) {
                if (adjacencyList[i] != null) {
                    vertices = i + 1;
                }
            }
        }
    }

    public Edge[] copyArray(Edge[] original, int newLength) {
        if (original == null) {
            throw new NullPointerException("Original array cannot be null");
        }
        if (newLength < 0) {
            throw new NegativeArraySizeException("New length cannot be negative");
        }

        Edge[] newArray = new Edge[newLength];

        int lengthToCopy = (original.length < newLength) ? original.length : newLength;

        for (int i = 0; i < lengthToCopy; i++) {
            newArray[i] = original[i];
        }

        return newArray;
    }

    public void addEdge(int source, int destination, int weight, boolean bidirectional) {
        Edge newEdge = new Edge(destination, weight);
        newEdge.next = adjacencyList[source];
        adjacencyList[source] = newEdge;

        if (bidirectional) {
            newEdge = new Edge(source, weight);
            newEdge.next = adjacencyList[destination];
            adjacencyList[destination] = newEdge;
        }
    }

    public void addEdge(int source, int destination, boolean bidirectional) {
        Edge newEdge = new Edge(destination, 1);
        newEdge.next = adjacencyList[source];
        adjacencyList[source] = newEdge;

        if (bidirectional) {
            newEdge = new Edge(source, 1);
            newEdge.next = adjacencyList[destination];
            adjacencyList[destination] = newEdge;
        }
    }

    public void removeEdge(int source, int destination, boolean bidirectional) {
        Edge current = adjacencyList[source];
        while (current != null) {
            if (current.vertex == destination) {
                adjacencyList[source] = current.next;
                break;
            }
            if (current.next != null && current.next.vertex == destination) {
                current.next = current.next.next;
            }
            current = current.next;
        }

        if (bidirectional) {
            current = adjacencyList[destination];
            while (current != null) {
                if (current.vertex == source) {
                    adjacencyList[destination] = current.next;
                    break;
                }
                if (current.next != null && current.next.vertex == source) {
                    current.next = current.next.next;
                }
                current = current.next;
            }
        }
    }

    public void printGraph() {
        System.out.println("Graph Representation (Adjacency List):");
        for (int i = 0; i < vertices; i++) {
            System.out.print(i + ": ");
            Edge current = adjacencyList[i];
            while (current != null) {
                System.out.print("(" + current.vertex + ", " + current.weight + ") ");
                current = current.next;
            }
            System.out.println();
        }
    }
    public void calculateShortestPaths(int start) {
        int[] distances = new int[vertices * 2];
        for(int i = 0; i < vertices; i++) {
            if (adjacencyList[i] != null) {
                distances[i] = Integer.MAX_VALUE;
                distances[i + vertices] = -1;
            }
        }
        distances[start] = 0;

        distances = calculateDirectPaths(start, distances);

        System.out.println("Starting vertex: " + start);
        System.out.println("Distances: " + Arrays.toString(Arrays.copyOfRange(distances, 0, vertices)));
        System.out.println("Previous vertices: " + Arrays.toString(Arrays.copyOfRange(distances, vertices, vertices * 2)));
    }

    public int[] calculateDirectPaths(int start, int[] distances) {
        Edge currentEdge = adjacencyList[start];
        while (!(currentEdge == null)) {
            if (currentEdge.weight + distances[start] < distances[currentEdge.vertex]) {
                distances[currentEdge.vertex + vertices] = start;
                distances[currentEdge.vertex] = currentEdge.weight + distances[start];

                distances = calculateDirectPaths(currentEdge.vertex, distances);
            }
            currentEdge = currentEdge.next;
        }

        return distances;
    }

    public static void main(String[] args) {
        Graph unweightedGraph = new Graph(6);
        unweightedGraph.addEdge(0, 1, true);
        unweightedGraph.addEdge(0, 2, true);
        unweightedGraph.addEdge(3, 1, true);
        unweightedGraph.addEdge(2, 3, true);
        unweightedGraph.addEdge(3, 4, true);
        unweightedGraph.addEdge(4, 5, true);

        unweightedGraph.calculateShortestPaths(0);
        System.out.println();

        Graph graph = new Graph(6);
        graph.addEdge(0, 1, 4, true);
        graph.addEdge(0, 2, 3 ,true);
        graph.addEdge(3, 1, 2, true);
        graph.addEdge(2, 3, 5, true);
        graph.addEdge(3, 4, 1, true);
        graph.addEdge(4, 5, 6, true);

        graph.calculateShortestPaths(0);
        System.out.println();

        graph.printGraph();
        graph.removeEdge(2, 3, true);
        graph.printGraph();
        System.out.println();

        graph.printGraph();
        graph.addVertex(10);
        graph.addEdge(5, 10, 9, true);
        graph.printGraph();
        graph.removeVertex(10);
        graph.printGraph();
    }
}
