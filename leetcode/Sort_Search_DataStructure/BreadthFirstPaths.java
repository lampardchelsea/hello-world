/******************************************************************************
 *  Compilation:  javac BreadthFirstPaths.java
 *  Execution:    java BreadthFirstPaths G s
 *  Dependencies: Graph.java Queue.java Stack.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/41graph/tinyCG.txt
 *                http://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                http://algs4.cs.princeton.edu/41graph/mediumG.txt
 *                http://algs4.cs.princeton.edu/41graph/largeG.txt
 *
 *  Run breadth first search on an undirected graph.
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
 *  %  java BreadthFirstPaths tinyCG.txt 0
 *  0 to 0 (0):  0
 *  0 to 1 (1):  0-1
 *  0 to 2 (1):  0-2
 *  0 to 3 (2):  0-2-3
 *  0 to 4 (2):  0-2-4
 *  0 to 5 (1):  0-5
 *
 *  %  java BreadthFirstPaths largeG.txt 0
 *  0 to 0 (0):  0
 *  0 to 1 (418):  0-932942-474885-82707-879889-971961-...
 *  0 to 2 (323):  0-460790-53370-594358-780059-287921-...
 *  0 to 3 (168):  0-713461-75230-953125-568284-350405-...
 *  0 to 4 (144):  0-460790-53370-310931-440226-380102-...
 *  0 to 5 (566):  0-932942-474885-82707-879889-971961-...
 *  0 to 6 (349):  0-932942-474885-82707-879889-971961-...
 *
 ******************************************************************************/


/**
 *  The {@code BreadthFirstPaths} class represents a data type for finding
 *  shortest paths (number of edges) from a source vertex <em>s</em>
 *  (or a set of source vertices)
 *  to every other vertex in an undirected graph.
 *  <p>
 *  This implementation uses breadth-first search.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  It uses extra space (not including the graph) proportional to <em>V</em>.
 *  <p>
 */
public class BreadthFirstPaths {
	private static final int INFINITY = Integer.MAX_VALUE;
	private boolean[] marked;
	private int[] edgeTo;
	private int[] distTo;
	
	/**
     * Computes the shortest path between the source vertex {@code s}
     * and every other vertex in the graph {@code G}.
     * @param G the graph
     * @param s the source vertex
     */
	public BreadthFirstPaths(Graph G, int s) {
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		distTo = new int[G.V()];
		bfs(G, s);
	}
	
	/**
	 * To implement this strategy, we maintain a queue of all vertices that have been marked but whose 
	 * adjacency lists have not been checked. We put the source vertex on the queue, then perform the 
	 * following steps until the queue is empty:
	 * (1) Remove the next vertex v from the queue.
	 * (2) Put onto the queue all unmarked vertices that are adjacent to v and mark them.
	 * @param G
	 * @param v
	 */
	// breadth-first search from a single source
	public void bfs(Graph G, int s) {
		PrincetonQueue<Integer> queue = new PrincetonQueue<Integer>();
		// Don't forget to set up distTo[v] as INFINITY
		for(int v = 0; v < G.V(); v++) {
			distTo[v] = INFINITY;
		}
		distTo[s] = 0;
		marked[s] = true;
		queue.enqueue(s);
		
		while(!queue.isEmpty()) {
			int v = queue.dequeue();
			for(int w : G.adj(v)) {
				if(!marked[w]) {
					queue.enqueue(w);
					marked[w] = true;
					edgeTo[w] = v;
					distTo[w] = distTo[v] + 1;
				}
			}
			
		}
	}
	
	/**
     * Computes the shortest path between any one of the source vertices in {@code sources}
     * and every other vertex in graph {@code G}.
     * @param G the graph
     * @param sources the source vertices
     */
	public BreadthFirstPaths(Graph G, Iterable<Integer> sources) {
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		distTo = new int[G.V()];
		// Don't forget to set up distTo[v] as INFINITY
		for(int v = 0; v < G.V(); v++) {
			distTo[v] = INFINITY;
		}
		bfs(G, sources);
	}
	
	// breadth-first search from multiple sources
	public void bfs(Graph G, Iterable<Integer> sources) {
		PrincetonQueue<Integer> queue = new PrincetonQueue<Integer>();
		// Only change is push all sources on queue instead of only
		// put one on queue first.
		for(int s : sources) {
			marked[s] = true;
			distTo[s] = 0;
			queue.enqueue(s);
		}
		
		while(!queue.isEmpty()) {
			int v = queue.dequeue();
			for(int w : G.adj(v)) {
				if(!marked[w]) {
					edgeTo[w] = v;
					distTo[w] = distTo[v] + 1;
					marked[w] = true;
					queue.enqueue(w);
				}
			}
		}
	}
	
	/**
     * Returns the number of edges in a shortest path between the source vertex {@code s}
     * (or sources) and vertex {@code v}?
     * @param v the vertex
     * @return the number of edges in a shortest path
     */
	public int distTo(int v) {
		return distTo[v];
	}
	
    /**
     * Is there a path between the source vertex {@code s} (or sources) and vertex {@code v}?
     * @param v the vertex
     * @return {@code true} if there is a path, and {@code false} otherwise
     */
	public boolean hasPathTo(int v) {
		return marked[v];
	}
	
	/**
     * Returns a shortest path between the source vertex {@code s} (or sources)
     * and {@code v}, or {@code null} if no such path.
     * @param v the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     */
	public Iterable<Integer> pathTo(int v) {
		PrincetonStack<Integer> path = new PrincetonStack<Integer>();
		int x;
		for(x = v; distTo(x) != 0; x = edgeTo[x]) {
			path.push(x);
		}
		path.push(x);
		
		return path;
	}
	
	public static void main(String[] args) {
		In in = new In(args[0]);
		Graph G = new Graph(in);
		int s = Integer.parseInt(args[1]);
		
		// The first step is create bfs object which computes the shortest path 
		// between the source vertex s and every other vertex in the graph G
		// Note: Not compute the path to specific vertex until now.
		BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);
		
		// The second step is when need to calculate path to specific vertex,
		// use pathTo() method to print out each edge which previously
		// calculated when generate bfs object in first step.
		for(int v = 0; v < G.V(); v++) {
			if(bfs.hasPathTo(v)) {
				StdOut.printf("%d to %d: ", s, v);
				for(int x : bfs.pathTo(v)) {
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

