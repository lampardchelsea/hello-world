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
 *  
 *  If not adding distTo() related part(including comment out check() method), also
 *  need to change pathTo() method for loop end condition as same as DFS
 *  pathTo(v) --> pathTo(v, s)
 *  for(x = v; distTo(x) != 0; x = edgeTo(x))
 *  for(x = v; x != s; x = edgeTo(x)){...}
 *  The background mechanism for end condition is same, because x != s equally to
 *  distTo[x] != 0, both of these two methods works as define end condition as
 *  x not step back to source vertex.
 *  
 *  As check, the final result is same as adding distTo().
 */
public class BreadthFirstPaths {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;  // marked[v] = is there an s-v path
    private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
//    private int[] distTo;      // distTo[v] = number of edges shortest s-v path

    /**
     * Computes the shortest path between the source vertex {@code s}
     * and every other vertex in the graph {@code G}.
     * @param G the graph
     * @param s the source vertex
     */
    public BreadthFirstPaths(Graph G, int s) {
        marked = new boolean[G.V()];
        //distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        bfs(G, s);

//        assert check(G, s);
    }

    /**
     * Computes the shortest path between any one of the source vertices in {@code sources}
     * and every other vertex in graph {@code G}.
     * @param G the graph
     * @param sources the source vertices
     */
    public BreadthFirstPaths(Graph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
//        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
//        for (int v = 0; v < G.V(); v++)
//            distTo[v] = INFINITY;
        bfs(G, sources);
    }


    // breadth-first search from a single source
    private void bfs(Graph G, int s) {
        Queue<Integer> q = new Queue<Integer>();
//        for (int v = 0; v < G.V(); v++)
//            distTo[v] = INFINITY;
//        distTo[s] = 0;
        marked[s] = true;
        q.enqueue(s);

        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
//                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

    // breadth-first search from multiple sources
    private void bfs(Graph G, Iterable<Integer> sources) {
        Queue<Integer> q = new Queue<Integer>();
        for (int s : sources) {
            marked[s] = true;
//            distTo[s] = 0;
            q.enqueue(s);
        }
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
//                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;                    
                    q.enqueue(w);
                }
            }
        }
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
     * Returns the number of edges in a shortest path between the source vertex {@code s}
     * (or sources) and vertex {@code v}?
     * @param v the vertex
     * @return the number of edges in a shortest path
     */
//    public int distTo(int v) {
//        return distTo[v];
//    }

    /**
     * Returns a shortest path between the source vertex {@code s} (or sources)
     * and {@code v}, or {@code null} if no such path.
     * @param v the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     */
    public Iterable<Integer> pathTo(int v, int s) {
        if (!hasPathTo(v)) return null;
        PrincetonStack<Integer> path = new PrincetonStack<Integer>();
        int x;
//        for (x = v; distTo[x] != 0; x = edgeTo[x])
        for (x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(x);
        return path;
    }


//    // check optimality conditions for single source
//    private boolean check(Graph G, int s) {
//
//        // check that the distance of s = 0
//        if (distTo[s] != 0) {
//            StdOut.println("distance of source " + s + " to itself = " + distTo[s]);
//            return false;
//        }
//
//        // check that for each edge v-w dist[w] <= dist[v] + 1
//        // provided v is reachable from s
//        for (int v = 0; v < G.V(); v++) {
//            for (int w : G.adj(v)) {
//                if (hasPathTo(v) != hasPathTo(w)) {
//                    StdOut.println("edge " + v + "-" + w);
//                    StdOut.println("hasPathTo(" + v + ") = " + hasPathTo(v));
//                    StdOut.println("hasPathTo(" + w + ") = " + hasPathTo(w));
//                    return false;
//                }
//                if (hasPathTo(v) && (distTo[w] > distTo[v] + 1)) {
//                    StdOut.println("edge " + v + "-" + w);
//                    StdOut.println("distTo[" + v + "] = " + distTo[v]);
//                    StdOut.println("distTo[" + w + "] = " + distTo[w]);
//                    return false;
//                }
//            }
//        }
//
//        // check that v = edgeTo[w] satisfies distTo[w] = distTo[v] + 1
//        // provided v is reachable from s
//        for (int w = 0; w < G.V(); w++) {
//            if (!hasPathTo(w) || w == s) continue;
//            int v = edgeTo[w];
//            if (distTo[w] != distTo[v] + 1) {
//                StdOut.println("shortest path edge " + v + "-" + w);
//                StdOut.println("distTo[" + v + "] = " + distTo[v]);
//                StdOut.println("distTo[" + w + "] = " + distTo[w]);
//                return false;
//            }
//        }
//
//        return true;
//    }

    /**
     * Unit tests the {@code BreadthFirstPaths} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        // StdOut.println(G);

        int s = Integer.parseInt(args[1]);
        BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (bfs.hasPathTo(v)) {
//                StdOut.printf("%d to %d (%d):  ", s, v, bfs.distTo(v));
            	StdOut.printf("%d to %d:  ", s, v);
                for (int x : bfs.pathTo(v, s)) {
                    if (x == s) StdOut.print(x);
                    else        StdOut.print("-" + x);
                }
                StdOut.println();
            }

            else {
//                StdOut.printf("%d to %d (-):  not connected\n", s, v);
            	StdOut.printf("%d to %d:  not connected\n", s, v);
            }

        }

    }


}
