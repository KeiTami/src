package group16.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GraphReader {
    public final static boolean DEBUG = false;
    public final static String COMMENT = "//";

    public static Graph readGraph(String inputFile) {

        boolean[] seen = null;
        int verticesCount = -1;  //! verticesCount is the number of vertices in the group16.graph
        int edgesCount;  //! edgesCount is the number of edges in the group16.graph
        Edge[] edges = null; //! edges will contain the edges of the group16.graph

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(inputFile));
            String record;
            //! The first few lines of the file are allowed to be comments, staring with a // symbol.
            //! These comments are only allowed at the top of the file.

            //! -----------------------------------------
            while ((record = buffer.readLine()) != null) {
                if (record.startsWith("//")) continue;
                break; // Saw a line that did not start with a comment -- time to start reading the data in!
            }
            verticesCount = getVerticesCount(record);
            seen = new boolean[verticesCount + 1];
            record = buffer.readLine();

            edgesCount = getEdgesCount(record);
            edges = new Edge[edgesCount];
            edges = getEdges(edges, buffer, seen);
            checkSurplus(buffer);
        } catch (IOException ex) {
            // catch possible io errors from readLine()
            System.out.println("Error! Problem reading file " + inputFile);
            System.exit(0);
        }

        checkUnseenVertices(seen, verticesCount);
        return new Graph(verticesCount, edges);
    }

    private static void checkSurplus(BufferedReader buffer) throws IOException {
        String surplus = buffer.readLine();
        if (surplus != null && surplus.length() >= 2 && DEBUG)
            System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '" + surplus + "'");
    }

    private static void checkUnseenVertices(boolean[] seen, int verticesCount) {
        for (int x = 1; x <= verticesCount; x++) {
            if (!seen[x] && DEBUG)
                System.out.println(COMMENT + " Warning: vertex " + x + " didn't appear in any edge : it will be considered a disconnected vertex on its own.");
        }
    }

    private static Edge[] getEdges(Edge[] edges, BufferedReader buffer, boolean[] seen) throws IOException {
        int edgesCount = edges.length;
        String record;
        for (int edgeIndex = 0; edgeIndex < edgesCount; edgeIndex++) {
            if (DEBUG)
                System.out.println(COMMENT + " Reading edge " + (edgeIndex + 1));

            record = buffer.readLine();
            String[] data = record.split(" ");
            checkLine(record, data);
            edges[edgeIndex] = new Edge();

            edges[edgeIndex].start = Integer.parseInt(data[0]);
            edges[edgeIndex].end = Integer.parseInt(data[1]);

            seen[edges[edgeIndex].start] = true;
            seen[edges[edgeIndex].end] = true;

            if (DEBUG)
                System.out.println(COMMENT + " group16.graph.Edge: " + edges[edgeIndex].start + " " + edges[edgeIndex].end);
        }
        return edges;
    }

    private static void checkLine(String record, String[] data) {
        if (data.length != 2) {
            System.out.println("Error! Malformed edge line: " + record);
            System.exit(0);
        }
    }

    private static int getEdgesCount(String record) {
        int edgesCount = -1;
        if (record.startsWith("EDGES = ")) {
            edgesCount = Integer.parseInt(record.substring(8));
            if (DEBUG) System.out.println(COMMENT + " Expected number of edges = " + edgesCount);
        }
        return edgesCount;
    }

    private static int getVerticesCount(String record) {
        int verticesCount = -1;
        if (record != null && record.startsWith("VERTICES = ")) {
            verticesCount = Integer.parseInt(record.substring(11));
            if (DEBUG) System.out.println(COMMENT + " Number of vertices = " + verticesCount);
        }
        return verticesCount;
    }
}
