package org.adp_implementatie;

class Graph {
    private final int vertices; // Number of vertices
    private Edge[] adjacencyList; // Array of adjacency lists for the graph

    // Constructor to initialize the graph
    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new Edge[vertices];
    }

    // Inner class to represent an adjacency list node
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
    public void addEdge(int source, int destination, int weight) {
        // Add edge from source to destination
        Edge newEdge = new Edge(destination, weight);
        newEdge.next = adjacencyList[source];
        adjacencyList[source] = newEdge;

        // Add edge from destination to source (for undirected graph)
        newEdge = new Edge(source, weight);
        newEdge.next = adjacencyList[destination];
        adjacencyList[destination] = newEdge;
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

    // Depth-First Search (DFS)
    public void dfs(int startEdge) {
        boolean[] visited = new boolean[vertices];
        System.out.println("DFS Traversal:");
        dfsHelper(startEdge, visited);
        System.out.println();
    }

    private void dfsHelper(int edge, boolean[] visited) {
        if (visited[edge]) return;

        visited[edge] = true;
        System.out.print(edge + " ");

        Edge current = adjacencyList[edge];
        while (current != null) {
            dfsHelper(current.vertex, visited);
            current = current.next;
        }
    }

    // Breadth-First Search (BFS)
    public void bfs(int startEdge) {
        boolean[] visited = new boolean[vertices];
        int[] queue = new int[vertices]; // Simple array for queue implementation
        int front = 0, rear = 0;

        visited[startEdge] = true;
        queue[rear++] = startEdge;

        System.out.println("BFS Traversal:");
        while (front < rear) {
            int currentEdge = queue[front++];
            System.out.print(currentEdge + " ");

            Edge current = adjacencyList[currentEdge];
            while (current != null) {
                if (!visited[current.vertex]) {
                    visited[current.vertex] = true;
                    queue[rear++] = current.vertex;
                }
                current = current.next;
            }
        }
        System.out.println();
    }

    // Main method to test the implementation
    public static void main(String[] args) {
        Graph graph = new Graph(6);

        // Add edges with weights
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 3, 2);
        graph.addEdge(2, 3, 5);
        graph.addEdge(3, 4, 1);
        graph.addEdge(4, 5, 6);

        // Print the graph
        graph.printGraph();

        // Perform DFS
        graph.dfs(0);

        // Perform BFS
        graph.bfs(0);
    }
}
