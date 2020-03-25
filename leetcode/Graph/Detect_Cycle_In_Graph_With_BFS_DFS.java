/**
 Refer to
 1. Detect Cycle in a Directed Graph using BFS
 https://www.geeksforgeeks.org/detect-cycle-in-a-directed-graph-using-bfs/
 
 The idea is to simply use Kahn’s algorithm for Topological Sorting
 Steps involved in detecting cycle in a directed graph using BFS.
 Step-1: Compute in-degree (number of incoming edges) for each of the vertex present in the 
         graph and initialize the count of visited nodes as 0.
 Step-2: Pick all the vertices with in-degree as 0 and add them into a queue (Enqueue operation)
 Step-3: Remove a vertex from the queue (Dequeue operation) and then.
         Increment count of visited nodes by 1.
         Decrease in-degree by 1 for all its neighboring nodes.
         If in-degree of a neighboring nodes is reduced to zero, then add it to the queue. 
 Step 4: Repeat Step 3 until the queue is empty.
 Step 5: If count of visited nodes is not equal to the number of nodes in the graph has cycle, otherwise not.
 
 How to find in-degree of each node?
 There are 2 ways to calculate in-degree of every vertex:
 Take an in-degree array which will keep track of
 
 1) Traverse the array of edges and simply increase the counter of the destination node by 1.
    for each node in Nodes
        indegree[node] = 0;
    for each edge(src,dest) in Edges
        indegree[dest]++
  Time Complexity: O(V+E)
 
 2) Traverse the list for every node and then increment the in-degree of all the nodes connected to it by 1.
    for each node in Nodes
        If (list[node].size()!=0) then
        for each dest in list
            indegree[dest]++;
  Time Complexity: The outer for loop will be executed V number of times and the inner for loop will be 
  executed E number of times, Thus overall time complexity is O(V+E).

  The overall time complexity of the algorithm is O(V+E)
  // Java program to check if there is a cycle in 
  // directed graph using BFS. 

  class GFG {

      // Class to represent a graph
      static class Graph {
          int V; // No. of vertices'

          // Pointer to an array containing adjacency list
          Vector < Integer > [] adj;

          @SuppressWarnings("unchecked")
          Graph(int V) {
              // Constructor
              this.V = V;
              this.adj = new Vector[V];
              for (int i = 0; i < V; i++)
                  adj[i] = new Vector < > ();
          }

          // function to add an edge to graph
          void addEdge(int u, int v) {
              adj[u].add(v);
          }

          // Returns true if there is a cycle in the graph
          // else false.

          // This function returns true if there is a cycle
          // in directed graph, else returns false.
          boolean isCycle() {

              // Create a vector to store indegrees of all
              // vertices. Initialize all indegrees as 0.
              int[] in_degree = new int[this.V];
              Arrays.fill(in_degree, 0);

              // Traverse adjacency lists to fill indegrees of
              // vertices. This step takes O(V+E) time
              for (int u = 0; u < V; u++) {
                  for (int v: adj[u])
                      in_degree[v]++;
              }

              // Create an queue and enqueue all vertices with
              // indegree 0
              Queue < Integer > q = new LinkedList < Integer > ();
              for (int i = 0; i < V; i++)
                  if (in_degree[i] == 0)
                      q.add(i);

              // Initialize count of visited vertices
              int cnt = 0;

              // Create a vector to store result (A topological
              // ordering of the vertices)
              Vector < Integer > top_order = new Vector < > ();

              // One by one dequeue vertices from queue and enqueue
              // adjacents if indegree of adjacent becomes 0
              while (!q.isEmpty()) {

                  // Extract front of queue (or perform dequeue)
                  // and add it to topological order
                  int u = q.poll();
                  top_order.add(u);

                  // Iterate through all its neighbouring nodes
                  // of dequeued node u and decrease their in-degree
                  // by 1
                  for (int itr: adj[u])
                      if (--in_degree[itr] == 0)
                          q.add(itr);
                  cnt++;
              }

              // Check if there was a cycle
              if (cnt != this.V)
                  return true;
              else
                  return false;
          }
      }

      // Driver Code
      public static void main(String[] args) {

          // Create a graph given in the above diagram
          Graph g = new Graph(6);
          g.addEdge(0, 1);
          g.addEdge(1, 2);
          g.addEdge(2, 0);
          g.addEdge(3, 4);
          g.addEdge(4, 5);

          if (g.isCycle())
              System.out.println("Yes");
          else
              System.out.println("No");
      }
  }

  Actually BFS implement on this way is Toplogical Sort, examples on below problems:
  CourseSchedule.java
====================================================================================================  
 
 2. Detect cycle in an undirected graph using BFS
 https://www.geeksforgeeks.org/detect-cycle-in-an-undirected-graph-using-bfs/

// Java program to detect cycle in 
// an undirected graph using BFS. 
/**
 * In this article, BFS based solution is discussed. We do a BFS traversal of the given graph. 
 * For every visited vertex ‘v’, if there is an adjacent ‘u’ such that u is already visited 
 * and u is not parent of v, then there is a cycle in graph. If we don’t find such an adjacent 
 * for any vertex, we say that there is no cycle. The assumption of this approach is that 
 * there are no parallel edges between any two vertices.
class Cycle {
    public static void main(String arg[]) {
        int V = 4;
        ArrayList < Integer > adj[] = new ArrayList[V];
        for (int i = 0; i < 4; i++)
            adj[i] = new ArrayList < Integer > ();

        addEdge(adj, 0, 1);
        addEdge(adj, 1, 2);
        addEdge(adj, 2, 0);
        addEdge(adj, 2, 3);

        if (isCyclicDisconntected(adj, V))
            System.out.println("Yes");
        else
            System.out.println("No");
    }

    static void addEdge(ArrayList < Integer > adj[], int u, int v) {
        adj[u].add(v);
        adj[v].add(u);
    }

    static boolean isCyclicConntected(ArrayList < Integer > adj[], int s, int V, boolean visited[]) {
        // Set parent vertex for every vertex as -1.
        int parent[] = new int[V];
        Arrays.fill(parent, -1);

        // Create a queue for BFS
        Queue < Integer > q = new LinkedList < > ();

        // Mark the current node as
        // visited and enqueue it
        visited[s] = true;
        q.add(s);

        while (!q.isEmpty()) {
            // Dequeue a vertex from
            // queue and print it
            int u = q.poll();
            // Get all adjacent vertices
            // of the dequeued vertex u.
            // If a adjacent has not been
            // visited, then mark it visited
            // and enqueue it. We also mark parent
            // so that parent is not considered
            // for cycle.
            for (int i = 0; i < adj[u].size(); i++) {
                int v = adj[u].get(i);
                if (!visited[v]) {
                    visited[v] = true;
                    q.add(v);
                    parent[v] = u;
                } else if (parent[u] != v)
                    return true;
            }
        }
        return false;
    }

    static boolean isCyclicDisconntected(ArrayList < Integer > adj[], int V) {
        // Mark all the vertices as not visited
        boolean visited[] = new boolean[V];
        Arrays.fill(visited, false);
        for (int i = 0; i < V; i++)
            if (!visited[i] && isCyclicConntected(adj, i, V, visited))
                return true;
        return false;
    }
}

Time Complexity: The program does a simple BFS Traversal of graph and graph is represented 
using adjacency list. So the time complexity is O(V+E)
====================================================================================================

 3. Detect cycle in an undirected graph using DFS
 https://www.geeksforgeeks.org/detect-cycle-undirected-graph/
==================================================================================================== 
 
 4. Detect Cycle in a Directed Graph using DFS
 https://www.geeksforgeeks.org/detect-cycle-in-a-graph/
 class Graph {

    private final int V;
    private final List < List < Integer >> adj;

    public Graph(int V) {
        this.V = V;
        adj = new ArrayList < > (V);

        for (int i = 0; i < V; i++)
            adj.add(new LinkedList < > ());
    }

    // This function is a variation of DFSUtil() in
    // https://www.geeksforgeeks.org/archives/18212
    private boolean isCyclicUtil(int i, boolean[] visited, boolean[] recStack) {

        // Mark the current node as visited and
        // part of recursion stack
        if (recStack[i])
            return true;

        if (visited[i])
            return false;

        visited[i] = true;

        recStack[i] = true;
        List < Integer > children = adj.get(i);

        for (Integer c: children)
            if (isCyclicUtil(c, visited, recStack))
                return true;

        recStack[i] = false;

        return false;
    }

    private void addEdge(int source, int dest) {
        adj.get(source).add(dest);
    }

    // Returns true if the graph contains a
    // cycle, else false.
    // This function is a variation of DFS() in
    // https://www.geeksforgeeks.org/archives/18212
    private boolean isCyclic() {

        // Mark all the vertices as not visited and
        // not part of recursion stack
        boolean[] visited = new boolean[V];
        boolean[] recStack = new boolean[V];

        // Call the recursive helper function to
        // detect cycle in different DFS trees
        for (int i = 0; i < V; i++)
            if (isCyclicUtil(i, visited, recStack))
                return true;

        return false;
    }

    // Driver code
    public static void main(String[] args) {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);
        graph.addEdge(2, 3);
        graph.addEdge(3, 3);

        if (graph.isCyclic())
            System.out.println("Graph contains cycle");
        else
            System.out.println("Graph doesn't " + "contain cycle");
    }
}
 
 The DFS implement examples on below problems:
  CourseSchedule.java
====================================================================================================

 5. Why DFS and not BFS for finding cycle in graphs
 https://stackoverflow.com/questions/2869647/why-dfs-and-not-bfs-for-finding-cycle-in-graphs
*/
