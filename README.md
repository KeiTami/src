Group 16 
Project 1-1 Phase 1:
group16.graph.Graph Colouring/Chromatic Numbers


The project is split into 4 different java classes:
  group16.algorithms.Backtracking.java
  group16.algorithms.Greedy.java
  Main.java
  group16.util.Util.java
  
group16.algorithms.Backtracking Algorithm:
group16.algorithms.Backtracking.java calculates the exact chromatic number using the backtracking algorithm.

group16.algorithms.Greedy Algorithm:
group16.algorithms.Greedy.java contains the code for calculating an upper-bound for a given group16.graph.

group16.util.Util:
Contains methods which are used by both group16.algorithms.Greedy and group16.algorithms.Backtracking
  
Main:
Main.java has been modified to accomodate for the two different group16.algorithms.


To run Main.java you need to follow these steps:

1. Download group16.algorithms.Backtracking.java, group16.algorithms.Greedy.java, Main.java
  and group16.util.Util.java
2. Open your command prompt
3. Go to the folder containing all the files
4. Compile Main (javac Main.java)
5. Run it (java Main) with an appropriate text file that contains a group16.graph (ex: graph01_2020.txt)
6. A prompt will appear asking which algorithm to use
7. The program will output the result of the chosen algorithm along with the amount of time taken to complete the task.
