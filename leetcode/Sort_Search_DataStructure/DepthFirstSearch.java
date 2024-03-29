
/******************************************************************************
 *  Compilation:  javac DepthFirstSearch.java
 *  Execution:    java DepthFirstSearch filename.txt s
 *  Dependencies: Graph.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                http://algs4.cs.princeton.edu/41graph/mediumG.txt
 *
 *  Run depth first search on an undirected graph.
 *  Runs in O(E + V) time.
 *
 *  % java DepthFirstSearch tinyG.txt 0
 *  0 1 2 3 4 5 6 
 *  NOT connected
 *
 *  % java DepthFirstSearch tinyG.txt 9
 *  9 10 11 12 
 *  NOT connected
 *
 ******************************************************************************/

/**
 *  The {@code DepthFirstSearch} class represents a data type for 
 *  determining the vertices connected to a given source vertex <em>s</em>
 *  in an undirected graph. For versions that find the paths, see
 *  {@link DepthFirstPaths} and {@link BreadthFirstPaths}.
 *  <p>
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>
 *  (in the worst case),
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  It uses extra space (not including the graph) proportional to <em>V</em>.
 *  <p>
 */
public class DepthFirstSearch {
	// marked[v] = is there an s-v path?
	private boolean[] marked;
	
	// number of vertices connected to s
	private int count;
	
    /**
     * Computes the vertices in graph {@code G} that are
     * connected to the source vertex {@code s}.
     * @param G the graph
     * @param s the source vertex
     */
	public DepthFirstSearch(Graph G, int s) {
		marked = new boolean[G.V()];
		dfs(G, s);
	}
	
	// depth first search from v
	public void dfs(Graph G, int v) {
		count++;
		marked[v] = true;
		for(int w : G.adj(v)) {
			if(!marked[w]) {
				dfs(G, w);
			}
		}
	}
	
    /**
     * Is there a path between the source vertex {@code s} and vertex {@code v}?
     * @param v the vertex
     * @return {@code true} if there is a path, {@code false} otherwise
     */
	public boolean marked(int v) {
		return marked[v];
	}
	
    /**
     * Returns the number of vertices connected to the source vertex {@code s}.
     * @return the number of vertices connected to the source vertex {@code s}
     */
    public int count() {
        return count;
    }
	
    /**
     * Unit tests the {@code DepthFirstSearch} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
    	In in = new In(args[0]);
    	Graph G = new Graph(in);
    	int s = Integer.parseInt(args[1]);
    	DepthFirstSearch search = new DepthFirstSearch(G, s);
    	for(int v = 0; v < G.V(); v++) {
    		if(search.marked(v)) {
    			StdOut.print(v + " ");
    		}
    	}
    	
    	StdOut.println();
    	
    	// A graph is connected if there is a path from every vertex to every other vertex.
    	if(search.count() != G.V()) {
    		StdOut.println("Graph NOT connected");
    	} else {
    		StdOut.println("Graph Connected");
    	}
    }
}
