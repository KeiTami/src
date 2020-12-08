package group16.algorithms;

import group16.util.Util;

import java.util.ArrayList;
import java.util.Arrays;

public class Greedy {

    //Returns an integer value representing the number of colours
    public static int countColours(ArrayList<ArrayList<Integer>> graphConnections) {
        return Util.getColourCount(calculateColours(graphConnections)) + 1;
    }

    //Goes through each vertex and assigns it the minimum possible colour,
    //and returns an array that contains the colors that had been assigned to each vertex
    private static int[] calculateColours(ArrayList<ArrayList<Integer>> graphConnections) {
        int verticesCount = graphConnections.size();
        int[] verticesColours = new int[verticesCount];
        Arrays.fill(verticesColours, -1);
        for (int vertex = 0; vertex < verticesCount; vertex++) {
            verticesColours[vertex] = getFirstValidColour(graphConnections, verticesColours, vertex);
        }
        return verticesColours;
    }

    //Goes through all of the colours, checks if they are valid, and returns the first valid colour that it finds
    private static int getFirstValidColour(ArrayList<ArrayList<Integer>> graphConnections, int[] verticesColours, int vertex) {
        boolean[] availableColours = getAvailableColours(graphConnections, verticesColours, vertex);
        for (int colour = 0; colour < availableColours.length; colour++) {
            if (availableColours[colour]) {
                return colour;
            }
        }
        return -1;
    }

    //All colours are by default marked as available(true),
    //then it checks for all adjacent vertices of our given vertex which colours are already taken and marks them as unavailable(false),
    //and it returns an array of colours with available ones marked true
    private static boolean[] getAvailableColours(ArrayList<ArrayList<Integer>> graphConnections, int[] verticesColours, int vertex) {
        boolean[] availableColours = new boolean[verticesColours.length];
        Arrays.fill(availableColours, true);
        for (int i = 0; i < graphConnections.size(); i++) {
            if (Util.isAdjacent(graphConnections, vertex, i) && verticesColours[i] != -1) {
                availableColours[verticesColours[i]] = false;
            }
        }
        return availableColours;
    }
}
