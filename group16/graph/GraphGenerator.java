package group16.graph;

import java.util.ArrayList;

import static group16.util.Util.*;

public class GraphGenerator {
    public static Graph generateGraph(int verticesCount, int edgesCount) {
        Edge[] edges = new Edge[edgesCount];
        ArrayList<String> seenEdges = new ArrayList<>();
        for (int i = 0; i < edges.length; i++) {
            edges[i] = new Edge();
            while (edges[i].start == edges[i].end) {
                edges[i].start = random.nextInt(verticesCount) + 1;
                edges[i].end = random.nextInt(verticesCount) + 1;
            }
            if (isIn(edges[i], seenEdges)) {
                i--;
            } else {
                seenEdges.add(edges[i].start + " " + edges[i].end);
            }
        }
        return new Graph(verticesCount, edges);
    }

    private static boolean isIn(Edge edge, ArrayList<String> seenEdges) {
        for (String seenEdge : seenEdges) {
            String[] data = seenEdge.split(" ");
            int start = Integer.parseInt(data[0]);
            int end = Integer.parseInt(data[1]);
            if ((edge.start == start && edge.end == end) || (edge.start == end && edge.end == start)) {
                return true;
            }
        }
        return false;
    }
}