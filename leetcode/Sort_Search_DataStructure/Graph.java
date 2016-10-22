/******************************************************************************
 *  Compilation:  javac Graph.java        
 *  Execution:    java Graph input.txt
 *  Dependencies: Bag.java Stack.java In.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                http://algs4.cs.princeton.edu/41graph/mediumG.txt
 *                http://algs4.cs.princeton.edu/41graph/largeG.txt
 *
 *  A graph, implemented using an array of sets.
 *  Parallel edges and self-loops allowed.
 *
 *  % java Graph tinyG.txt
 *  13 vertices, 13 edges 
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
 *  % java Graph mediumG.txt
 *  250 vertices, 1273 edges 
 *  0: 225 222 211 209 204 202 191 176 163 160 149 114 97 80 68 59 58 49 44 24 15 
 *  1: 220 203 200 194 189 164 150 130 107 72 
 *  2: 141 110 108 86 79 51 42 18 14 
 *  ...
 *  
 ******************************************************************************/


/**
 *  The {@code Graph} class represents an undirected graph of vertices
 *  named 0 through <em>V</em> - 1.
 *  It supports the following two primary operations: add an edge to the graph,
 *  iterate over all of the vertices adjacent to a vertex. It also provides
 *  methods for returning the number of vertices <em>V</em> and the number
 *  of edges <em>E</em>. Parallel edges and self-loops are permitted.
 *  By convention, a self-loop <em>v</em>-<em>v</em> appears in the
 *  adjacency list of <em>v</em> twice and contributes two to the degree
 *  of <em>v</em>. 
 *  Refer to https://en.wikipedia.org/wiki/Loop_(graph_theory)
 *  A special case is a loop, which adds two to the degree[citation needed]. 
 *  This can be understood by letting each connection of the loop edge count as 
 *  its own adjacent vertex. In other words, a vertex with a loop "sees" itself 
 *  as an adjacent vertex from both ends of the edge thus adding two, not one, 
 *  to the degree
 *  <p>
 *  This implementation uses an adjacency-lists representation, which 
 *  is a vertex-indexed array of {@link Bag} objects.
 *  All operations take constant time (in the worst case) except
 *  iterating over the vertices adjacent to a given vertex, which takes
 *  time proportional to the number of such vertices.
 *  <p>
 */
public class Graph {
	// Define a new line separator
	private static final String NEWLINE = System.getProperty("line.separator");
	
	// Define number of Vertices
	private final int V;
	
	// Define number of Edges
	private int E;
	
	// adjacency-lists representation, a vertex-indexed array of Bag objects
	private Bag<Integer>[] adj;
    
    /**
     * Initializes an empty graph with {@code V} vertices and 0 edges.
     * param V the number of vertices
     *
     * @param  V number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
	public Graph(int V) {
    	if(V < 0) {
    		throw new IllegalArgumentException("Number of vertices must be nonnegative");
    	}
    	this.V = V;
    	this.E = 0;
    	// Refer to http://stackoverflow.com/questions/529085/how-to-create-a-generic-array-in-java
    	// E[] arr = (E[])new Object[INITIAL_ARRAY_LENGTH];
    	// E[] here is Bag<Integer>[]
    	// If not add @SuppressWarnings("unchecked") will have type safety: 
    	// Unchecked cast from Bag[] to Bag<Integer>[]
    	adj = (Bag<Integer>[]) new Bag[V];
    	for(int v = 0; v < V; v++) {
    		adj[v] = new Bag<Integer>();
    	}
    }
    
    /**  
     * Initializes a graph from an input stream.
     * Dependency: Constructor to initialize an empty graph with {@code V} vertices and 0 edges.
     * The format is the number of vertices <em>V</em>,
     * followed by the number of edges <em>E</em>,
     * followed by <em>E</em> pairs of vertices, with each entry separated by whitespace.
     * E.g tinyG.txt -> available on http://algs4.cs.princeton.edu/41graph/tinyG.txt
     * 13
     * 13
     * 0 5
     * 4 3
     * 0 1
     * 9 12
     * 6 4
     * 5 4
     * 0 2
     * 11 12
     * 9 10
     * 0 6
     * 7 8
     * 9 11
     * 5 3
     * 
     * 
     * @param  in the input stream
     * @throws IndexOutOfBoundsException if the endpoints of any edge are not in prescribed range
     * @throws IllegalArgumentException if the number of vertices or edges is negative
     */
    public Graph(In in) {
    	/**
    	 * What does “this()” method mean?
    	 * http://stackoverflow.com/questions/15867722/what-does-this-method-mean
    	 * This is constructor overloading:
			public class Diagraph {
			    public Diagraph(int n) {
			       // Constructor code
			    }
			
			    public Digraph(In in) {
			      this(in.readInt()); // Calls the constructor above. 
			      int E = in.readInt();
			      for (int i = 0; i < E; i++) {
			         int v = in.readInt();
			         int w = in.readInt();
			         addEdge(v, w); 
			      }
			   }
			}
		 *	You can tell this code is a constructor and not a method by the lack of a return type. 
		 *  This is pretty similar to calling super() in the first line of the constructor in order 
		 *  to initialize the extended class. You should call this() (or any other overloading of 
		 *  this()) in the first line of your constructor and thus avoid constructor code duplications.
    	 */
    	this(in.readInt());
    	int E = in.readInt();
    	if(E < 0) {
    		throw new IllegalArgumentException("Number of edges must be nonnegative");
    	}
    	for(int i = 0; i < E; i++) {
    		int v = in.readInt();
    		int w = in.readInt();
    		addEdge(v, w);
    	}
    }

    /**
     * Initializes a new graph that is a deep copy of {@code G}.
     *
     * @param  G the graph to copy
     */
    public Graph(Graph G) {
        this(G.V());
        this.E = G.E();
        for(int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
        	
        	// E.g when original input order as 6 -> 0 -> 1 -> 5 store on PrincetonStack,
        	// it will push 6 to the top of PrincetonStack as 1st item, then based on its
        	// push() method which add new item prior to old item, 0 will be new top of
        	// PrincetonStack as 0 -> 6, the rest can be done with same manner, so original
        	// input on PrincetonStack will be 5 -> 1 -> 0 -> 6, it satisfy LIFO.
            // Then we use PrincetonStack's iterator which behavior as LIFO to scan stack
        	// and use Bag's add method to add to copy of Graph adjacency list. The iterator
        	// will run over PrincetonStack as order as first select 5, then 1, then 0,
        	// then 6(which is LIFO order, opposite to bump-up order provide by java source
        	// stack). Now, simulate the process as 5 will be add onto copy of Graph adjacency
        	// list first, then 1 will be added prior to 5 based on add ahead logic of add()
        	// method of Bag class, the order in copy is 1 -> 5, and so on... Finally, 
        	// it will be in order as 6 -> 0 -> 1 -> 5, which is same as original order.
        	PrincetonStack<Integer> reverse = new PrincetonStack<Integer>();
        	
        	for(int w : G.adj[v]) {
        		reverse.push(w);
        	}
        	
        	for(int w : reverse) {
        		adj[v].add(w);
        	}
        }
    }
    
    
    /**
     * Adds the undirected edge v-w to this graph.
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     * @throws IndexOutOfBoundsException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(int v, int w) {
    	validateVertex(v);
    	validateVertex(w);
    	E++;
    	adj[v].add(w);
    	adj[w].add(v);
    }
    
    private void validateVertex(int v) {
    	if(v < 0 || v >= V) {
    		throw new IndexOutOfBoundsException("vertxt " + v + " is not between 0 and " + (V - 1));
    	}
    }
    
    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph
     */
    public int V() {
    	return V;
    }
    
    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */
    public int E() {
    	return E;
    }
    
    /**
     * Returns the degree of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the degree of vertex {@code v}
     * @throws IndexOutOfBoundsException unless {@code 0 <= v < V}
     */
    public int degree(int v) {
    	validateVertex(v);
    	return adj[v].size();
    }
    

    /**
     * Returns the vertices adjacent to vertex {@code v}.
     *
     * @param  v the vertex
     * @return the vertices adjacent to vertex {@code v}, as an iterable
     * @throws IndexOutOfBoundsException unless {@code 0 <= v < V}
     * 
     * For return type as {@code Interable<Integer>} but not {@code Bag<Integer>}
     * is based on flexible design rule. Which is always stick with Interface
     * return type rather than concrete Collection as return type.
     * e.g we always seen return type as {@code List<T>} but not {@code ArrayList<T>},
     * even when implement the detail use {@code ArrayList<T>}.
     * Refer to https://www.quora.com/Is-returning-an-iterable-better-than-a-list-or-set-in-Java
     * and http://stackoverflow.com/questions/23162559/iterable-as-a-return-type
     * 
     * Also based on below article
     * http://stackoverflow.com/questions/19474862/is-it-bad-practice-to-return-an-iterable-in-a-method
     * 
     * Returning an Iterable would be beneficial when we need to lazily load a collection that contains 
     * a lot of elements.
     * The following quote from Google Collections FAQ seems to support the idea of lazy loading:
     * Why so much emphasis on Iterators and Iterables?
     * In general, our methods do not require a Collection to be passed in when an Iterable or 
     * Iterator would suffice. This distinction is important to us, as sometimes at Google we 
     * work with very large quantities of data, which may be too large to fit in memory, 
     * but which can be traversed from beginning to end in the course of some computation. 
     * Such data structures can be implemented as collections, but most of their methods would 
     * have to either throw an exception, return a wrong answer, or perform abysmally. 
     * For these situations, Collection is a very poor fit; a square peg in a round hole.
     * An Iterator represents a one-way scrollable "stream" of elements, and an Iterable is anything 
     * which can spawn independent iterators. A Collection is much, much more than this, so we only 
     * require it when we need to.
     * 
     * Lazy loading
     * http://bravenewgeek.com/tag/lazy-loading/
     * http://stackoverflow.com/questions/2155788/most-of-the-iterators-and-iterables-methods-are-lazy-what-does-this-mean
     * https://en.wikipedia.org/wiki/Lazy_evaluation
     */
    public Iterable<Integer> adj(int v) {
    	validateVertex(v);
    	return adj[v];
    }
    

    /**
     * Returns a string representation of this graph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists
     */
    public String toString() {
    	StringBuilder s = new StringBuilder();
    	s.append(V + " vertices, " + E + " edges " + NEWLINE);
    	for(int v = 0; v < V; v++) {
    		s.append(v + ": ");
    		for(int w : adj[v]) {
    			s.append(w + " ");
    		}
    		s.append(NEWLINE);
    	}
    	
    	return s.toString();
    }
    
}
