package test;

import java.util.ArrayList;
import java.util.List;

public class EdgeWeightedDigraph {
	// Number of vertices in this digraph
    int V;
    // Number of edges in this digraph
    int E;
    // adjacency list for vertex v
    List<DirectedEdge>[] adj;
    // indegree[v] is indegree of vertex v
    int[] indegree;
    
    public EdgeWeightedDigraph(int V) {
        this.V = V;
        this.E = 0;
        this.indegree = new int[V];
        adj = (ArrayList<DirectedEdge>[])new ArrayList[V];
        for(int v = 0; v < V; v++) {
        	adj[v] = new ArrayList<DirectedEdge>();
        }
    }
    
    public int E() {
    	return E;
    }
    
    public int V() {
    	return V;
    }
    
    public void addEdge(DirectedEdge e) {
    	int v = e.from();
    	int w = e.to();
    	adj[v].add(e);
    	indegree[w]++;
    	E++;
    }
    
    // Returns the directed edges incident from vertex v
    public Iterable<DirectedEdge> adj(int v) {
    	return adj[v];
    }
    
    // Returns the number of directed edges incident from vertex v
    // known as outdegree of vertex
    public int outdegree(int v) {
    	return adj[v].size();
    }
    
    // Returns the number of directed edges incident to vertex v
    // known as indegree of vertex
    public int indegree(int v) {
    	return indegree[v];
    }
    
    // Returns all directed edges in this edge-weighted graph
    public Iterable<DirectedEdge> edges() {
    	List<DirectedEdge> list = new ArrayList<DirectedEdge>();
    	for(int v = 0; v < V; v++) {
    		for(DirectedEdge e : adj(v)) {
    			list.add(e);
    		}
    	}
    	return list;
    }
}
