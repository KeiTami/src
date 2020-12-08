package group16.graph;

import java.util.ArrayList;

public class Graph {
    private int verticesCount;
    private final Edge[] edges;
    public final ArrayList<ArrayList<Integer>> connections = new ArrayList<>(verticesCount);

    public Graph (int verticesCount, Edge[] edges) {
        this.verticesCount = verticesCount;
        this.edges = edges;
        createGraph(edges);
    }

    public int getVerticesCount() {
        return verticesCount;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public void printGraph() {
        for (ArrayList<Integer> vertex : connections) {
            System.out.println(vertex);
        }
    }

    private void createGraph(Edge[] edges) {
        for (int i = 0; i < verticesCount; i++) {
            connections.add(new ArrayList<>());
        }
        for (Edge edge : edges) {
            addEdge(connections, edge);
        }
    }

    private void addEdge(ArrayList<ArrayList<Integer>> graph, Edge edge ) {
        graph.get(edge.start - 1).add(edge.end - 1);
        graph.get(edge.end - 1).add(edge.start - 1);
    }

}
