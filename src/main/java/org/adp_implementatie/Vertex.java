package org.adp_implementatie;

import java.util.LinkedList;
import java.util.List;

public class Vertex {
    private String name;
    private List<Edge> adj; // Adjacent edges
// The following attributes are needed for the algorithm
    private double dist; // Cost
    private Vertex prev; // Previous vertex on shortest path
    public Vertex(String name) {
            this.name = name;
            this.adj = new LinkedList<>();
            this.prev = null;
            this.dist = Double.POSITIVE_INFINITY;

    }
}
