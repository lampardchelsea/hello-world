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
  
  Actually BFS implement on this way is Toplogical Sort, examples on below problems:
  CourseSchedule.java
  
 
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
 
 3. Detect cycle in an undirected graph
 https://www.geeksforgeeks.org/detect-cycle-undirected-graph/
 
 4. Detect Cycle in a Directed Graph
 https://www.geeksforgeeks.org/detect-cycle-in-a-graph/
 
 5. Why DFS and not BFS for finding cycle in graphs
 https://stackoverflow.com/questions/2869647/why-dfs-and-not-bfs-for-finding-cycle-in-graphs
*/
