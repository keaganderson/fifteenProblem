# Fifteen Sliding Problem
An Artificial Intelligence that solves Sliding Problems of size 16 using a choice of various search algorithms.

# Algorithms
There is an option of Breadth First Search (BFS), Depth First Search (DFS), Greedy Breadth First Search (GBFS), Depth Limited Search (DLS), and AStar

*Depth Limited Search takes a limit parameter

*Greedy and Astar takes a heuristic parameter

# Instructions

Run the sliding program with the puzzle as the first parameter in parethesis and the search algorithm as the second parameter. Then an optional parameter as the third if the search algorithm requires it.

Ex. 1: "123456789ABC DFE" BFS

Ex. 2: "123456789ABC DFE" GBFS h1

Ex. 3: "123456789ABC DFE" DLS 3

# Solution

The algorithm will solve the puzzle and give statistics based on how the search algorithm performed to solve it.

It will print a string of:
1. Current steps to solve the puzzle
2. Total puzzle states explored to find the solution
3. Total puzzle states expanded to find the solution
4. Max solutions on the stack at one time
