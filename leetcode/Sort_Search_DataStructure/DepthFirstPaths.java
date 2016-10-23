
/******************************************************************************
 *  Compilation:  javac DepthFirstPaths.java
 *  Execution:    java DepthFirstPaths G s
 *  Dependencies: Graph.java Stack.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/41graph/tinyCG.txt
 *                http://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                http://algs4.cs.princeton.edu/41graph/mediumG.txt
 *                http://algs4.cs.princeton.edu/41graph/largeG.txt
 *
 *  Run depth first search on an undirected graph.
 *  Runs in O(E + V) time.
 *
 *  %  java Graph tinyCG.txt
 *  6 8
 *  0: 2 1 5 
 *  1: 0 2 
 *  2: 0 1 3 4 
 *  3: 5 4 2 
 *  4: 3 2 
 *  5: 3 0 
 *
 *  % java DepthFirstPaths tinyCG.txt 0
 *  0 to 0:  0
 *  0 to 1:  0-2-1
 *  0 to 2:  0-2
 *  0 to 3:  0-2-3
 *  0 to 4:  0-2-3-4
 *  0 to 5:  0-2-3-5
 *
 ******************************************************************************/

/**
 *  The {@code DepthFirstPaths} class represents a data type for finding
 *  paths from a source vertex <em>s</em> to every other vertex
 *  in an undirected graph.
 *  <p>
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  It uses extra space (not including the graph) proportional to <em>V</em>.
 *  <p>
 */
public class DepthFirstPaths {
	// marked[v] = is there an s-v path?
	private boolean[] marked;

	// edgeTo[v] = last edge on s-v path
	private int[] edgeTo;
	
	// source vertex
	private int s;
	
    /**
     * Computes a path between {@code s} and every other vertex in graph {@code G}.
     * @param G the graph
     * @param s the source vertex
     */
	public DepthFirstPaths(Graph G, int s) {
		this.s = s;
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		dfs(G, s);
	}
	
	// depth first search from v
	public void dfs(Graph G, int v) {
		marked[v] = true;
		for(int w : G.adj(v)) {
			if(!marked[w]) {
				/**
				 * It is easy to modify depth-first search to not only determine whether there exists 
				 * a path between two given vertices but to find such a path (if one exists).
				 * To accomplish this, we remember the edge v-w that takes us to each vertex w for 
				 * the first time by setting edgeTo[w] to v. In other words, v-w is the last edge 
				 * on the known path from s to w. The result of the search is a tree rooted at the 
				 * source; edgeTo[] is a parent-link representation of that tree.
				 */
				edgeTo[w] = v;
				dfs(G, w);
			}
		}
	}
	
    /**
     * Is there a path between the source vertex {@code s} and vertex {@code v}?
     * @param v the vertex
     * @return {@code true} if there is a path, {@code false} otherwise
     */
	public boolean hasPathTo(int v) {
		return marked[v];
	}
	
    /**
     * Returns a path between the source vertex {@code s} and vertex {@code v}, or
     * {@code null} if no such path.
     * @param v the vertex
     * @return the sequence of vertices on a path between the source vertex
     *   {@code s} and vertex {@code v}, as an Iterable
     */
	public Iterable<Integer> pathTo(int v) {
		if(!hasPathTo(v)) {
			return null;
		}
		
		PrincetonStack<Integer> path = new PrincetonStack<Integer>();
		
		/**
		 * The elegant design of for loop has special counter-control design,
		 * together with start(x = v) and stop(x != s) condition to control, 
		 * which is quite different than normal increment or decrement counter-control.
		 * As x = edgeTo[x] which update x from destination vertex(v) of current edge 
		 * (x = v) to the source vertex(edgeTo[x]) of current edge as a recall step, 
		 * based on definition of edgeTo[w] = v in dfs() method, which set up edgeTo
		 * array when dfs() method finish.
		 */
		for(int x = v; x != s; x = edgeTo[x]) {
			path.push(x);
		}
		
		// Finally need to put source vertex as last element on stack
		path.push(s);
		
		return path;
	}
	
	public static void main(String[] args) {
		In in = new In(args[0]);
		Graph G = new Graph(in);
		int s = Integer.parseInt(args[1]);
		DepthFirstPaths dfs = new DepthFirstPaths(G, s);
		
		for(int v = 0; v < G.V(); v++) {
			if(dfs.hasPathTo(v)) {
				StdOut.printf("%d to %d:  ", s, v);
				
				// Iterator of PrincetonStack has a natural of go through
				// stack with for each loop as LIFO order, will output
				// the reverse order of elements pushed onto PrincetonStack,
				// which means source vertex which put as last element will
				// print out as first one.
				for(int x : dfs.pathTo(v)) {
					if(x == s) {
						StdOut.print(x);
					} else {
						StdOut.print("-" + x);
					}
				}
				StdOut.println();
			} else {
				StdOut.printf("%d to %d:  not connected\n", s, v);
			}
		}
	}
	
}
