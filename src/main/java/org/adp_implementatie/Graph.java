package org.adp_implementatie;

import java.util.Arrays;

class Graph {
    private final int vertices; // Number of vertices
    private Edge[] adjacencyList; // Array of adjacency lists for the graph

    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new Edge[vertices];
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

    // Method to add a weighted edge to the graph
    public void addEdge(int source, int destination, int weight, boolean bidirectional) {
        // Add edge from source to destination
        Edge newEdge = new Edge(destination, weight);
        newEdge.next = adjacencyList[source];
        adjacencyList[source] = newEdge;

        // Add edge from destination to source (for undirected graph)
        if (bidirectional) {
            newEdge = new Edge(source, weight);
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

    // Method to display the graph
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
            distances[i] = Integer.MAX_VALUE;
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
        unweightedGraph.addEdge(0, 1, 1, true);
        unweightedGraph.addEdge(0, 2, 1 ,true);
        unweightedGraph.addEdge(3, 1, 1, true);
        unweightedGraph.addEdge(2, 3, 1, true);
        unweightedGraph.addEdge(3, 4, 1, true);
        unweightedGraph.addEdge(4, 5, 1, true);

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
    }
}
