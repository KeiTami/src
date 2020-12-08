package group16.algorithms;

import group16.util.Util;

import java.util.ArrayList;

public class Backtracking {

    //Returns an integer value representing the number of colours
    public static int countColours(ArrayList<ArrayList<Integer>> graphConnections, int vertex) {
        int verticesCount = graphConnections.size(); //The amount of vertices there are
        int[] verticesColours = new int[verticesCount]; //Array used to contain the colors that had been assigned to each vertex
        boolean[] seen = new boolean[verticesCount]; //Array used to check which vertices have been already went through
        verticesColours = calculateColours(graphConnections, vertex, verticesColours, seen);
        return Util.getColourCount(verticesColours);
    }

    //Searches the graph Depth First by using recursion and returns an array that contains the colors that had been assigned to each vertex
    private static int[] calculateColours(ArrayList<ArrayList<Integer>> graphConnections, int vertex, int[] verticesColours, boolean[] seen) {
        seen[vertex] = true; //Marks that the vertex we're looking at now has been went through
        verticesColours[vertex] = getFirstValidColour(graphConnections, vertex, verticesColours);
        for (int i = 0; i < graphConnections.size(); i++) {
            if (Util.isAdjacent(graphConnections, vertex, i) && !seen[i]) {
                verticesColours = calculateColours(graphConnections, i, verticesColours, seen); //Repeats the method for every adjacent vertex
            }
        }
        return verticesColours;
    }

    //Goes through all of the colours, checks if they are valid, and returns the first valid colour that it finds
    private static int getFirstValidColour(ArrayList<ArrayList<Integer>> graphConnections, int vertex, int[] verticesColours) {
        for (int colour = 1; colour <= verticesColours.length; colour++) {
            if (isColourValid(graphConnections, vertex, verticesColours, colour)) {
                return colour;
            }
        }
        return -1;
    }

    //For the given vertex it checks in the adjacency matrix for all of it's adjacent vertices,
    // and checks if the given colour has already been assigned to the adjacent vertex or not
    public static boolean isColourValid(ArrayList<ArrayList<Integer>> graphConnections, int vertex, int[] verticesColours, int colour) {
        for (int i = 0; i < verticesColours.length; i++) {
            if (Util.isAdjacent(graphConnections, vertex, i) && colour == verticesColours[i]) {
                return false;
            }
        }
        return true;
    }
}
