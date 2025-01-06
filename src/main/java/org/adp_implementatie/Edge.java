package org.adp_implementatie;

public class Edge {
    private Vertex dest; // Second vertex in edge
    private double cost;
    public Edge(Vertex dest, double cost) {
        this.dest = dest;
        this.cost = cost;
    }
}
