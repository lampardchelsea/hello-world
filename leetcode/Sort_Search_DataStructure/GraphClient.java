/******************************************************************************
 *  Compilation:  javac GraphClient.java
 *  Execution:    java GraphClient graph.txt
 *  Dependencies: Graph.java
 *
 *  Typical graph-processing code.
 *
 *  % java GraphClient tinyG.txt
 *  13 13
 *  0: 6 2 1 5 
 *  1: 0 
 *  2: 0 
 *  3: 5 4 
 *  4: 5 6 3 
 *  5: 3 4 0 
 *  6: 0 4 
 *  7: 8 
 *  8: 7 
 *  9: 11 10 12 
 *  10: 9 
 *  11: 9 12 
 *  12: 11 9 
 *
 *  vertex of maximum degree = 4
 *  average degree           = 2
 *  number of self loops     = 0
 *  
 *  If add a self loop vertex in tinyG.txt will be
 *  14 14
 *  0: 6 2 1 5 
 *  1: 0 
 *  2: 0 
 *  3: 5 4 
 *  4: 5 6 3 
 *  5: 3 4 0 
 *  6: 0 4 
 *  7: 8 
 *  8: 7 
 *  9: 11 10 12 
 *  10: 9 
 *  11: 9 12 
 *  12: 11 9
 *  13: 13 13 -> This is self loop vertex
 *  
 *  vertex of maximum degree = 4
 *  average degree           = 2
 *  number of self loops     = 1
 *  
 ******************************************************************************/

public class GraphClient {	
	// maximum degree in a Graph
	public static int maxDegree(Graph G) {
		int max = 0;
		for(int v = 0; v < G.V(); v++) {
			int degree = G.degree(v);
			if(degree >= max) {
				max = degree;
			}
		}
		
		return max;
	}
	
	// average degree in a Graph
	public static int avgDegree(Graph G) {
		// each edge incident on two vertices
		return (int)2 * G.E() / G.V();
	}
	

	// number of self-loops
	public static int numberOfSelfLoops(Graph G) {
		// A self-loop is an edge that connects a vertex to itself
		int count = 0;
		for(int v = 0; v < G.V(); v++) {
			for(int w : G.adj(v)) {
				if(w == v) {
					count++;
				} 
			}
		}
		
		// self loop appears in adjacency list twice
		// Refer to https://en.wikipedia.org/wiki/Loop_(graph_theory)
		// A special case is a loop, which adds two to the degree[citation needed]. 
		// This can be understood by letting each connection of the loop edge count as 
		// its own adjacent vertex. In other words, a vertex with a loop "sees" itself 
		// as an adjacent vertex from both ends of the edge thus adding two, not one, 
		// to the degree.
		return count / 2;
	}

    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        StdOut.println(G);

        StdOut.println("vertex of maximum degree = " + maxDegree(G));
        StdOut.println("average degree           = " + avgDegree(G));
        StdOut.println("number of self loops     = " + numberOfSelfLoops(G));
    }

}
