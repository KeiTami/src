package group16.util;

import java.util.ArrayList;
import java.util.Random;

public class Util {
    public static Random random = new Random();

    public static boolean isAdjacent(ArrayList<ArrayList<Integer>> graph, int vertex, int index) {
        for (Integer elem : graph.get(vertex)) {
            if (elem == index) {
                return true;
            }
        }
        return false;
    }
    //Goes through a one dimensional array to return its biggest value (colour)
    public static int getColourCount(int[] verticesColours) {
        int max = verticesColours[0];
        for (int colour : verticesColours) {
            if (colour > max) {
                max = colour;
            }
        }
        return max;
    }
}
