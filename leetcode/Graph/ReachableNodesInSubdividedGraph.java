/**
 Refer to
 https://leetcode.com/problems/reachable-nodes-in-subdivided-graph/
 Starting with an undirected graph (the "original graph") with nodes from 0 to N-1, subdivisions are made to some of the edges.
 The graph is given as follows: edges[k] is a list of integer pairs (i, j, n) such that (i, j) is an edge of the original graph,
 and n is the total number of new nodes on that edge. 
 Then, the edge (i, j) is deleted from the original graph, n new nodes (x_1, x_2, ..., x_n) are added to the original graph,
 and n+1 new edges (i, x_1), (x_1, x_2), (x_2, x_3), ..., (x_{n-1}, x_n), (x_n, j) are added to the original graph.
 Now, you start at node 0 from the original graph, and in each move, you travel along one edge. 
 Return how many nodes you can reach in at most M moves.
 
 Example 1:
 Input: edges = [[0,1,10],[0,2,1],[1,2,2]], M = 6, N = 3
 Output: 13
 Explanation: 
 The nodes that are reachable in the final graph after M = 6 moves are indicated below.

 Example 2:
 Input: edges = [[0,1,4],[1,2,6],[0,2,8],[1,3,1]], M = 10, N = 4
 Output: 23
 
 Note:
 0 <= edges.length <= 10000
 0 <= edges[i][0] < edges[i][1] < N
 There does not exist any i != j for which edges[i][0] == edges[j][0] and edges[i][1] == edges[j][1].
 The original graph has no parallel edges.
 0 <= edges[i][2] <= 10000
 0 <= M <= 10^9
 1 <= N <= 3000
 A reachable node is a node that can be travelled to using at most M moves starting from node 0.
*/

// Solution 1: Dijkstra reverse with maxPQ
// Refer to
// https://leetcode.com/problems/reachable-nodes-in-subdivided-graph/discuss/157252/Logical-Thinking-with-Clear-Code
/**
 Logical Thinking
 The problem is to get maximum number of nodes I can reach from node 0 within M moves. If I figure out each node's 
 shortest distance to src, the one with distance <= M moves should be reachable. In this way, the problem can be 
 regarded as a single-source shortest-path problem, which can be solved by Dijkstra's Algorithm.
 Instead of maintaining a MinHeap which keeps track of shortest distances to the source, we maintain a MaxHeap that 
 keeps track of maximum moves remained for each node. Since for a node,
 
 moves remained + distance from current node to source = M
 
 The bigger moves remained is, the smaller the distance will be. Thus, the MaxHeap can also promise the shortest distance.
 We rebuild the graph to a weighted graph such that weight of an edge is total number of new nodes on that edge.
*/
// https://leetcode.com/problems/reachable-nodes-in-subdivided-graph/discuss/156777/Java-Dijkstra-Solution
// https://leetcode.com/problems/reachable-nodes-in-subdivided-graph/discuss/156777/Java-Dijkstra-Solution/162482
class Solution {
    public int reachableNodes(int[][] edges, int M, int N) {
        // Store edges into 2D graph, store how many nodes between each pair of nodes,
        // declare the value as 'cost', initialize all costs as -1
        int[][] graph = new int[N][N];
        // Seperate the conditions: edge with -1 cost (no new nodes) and not connected
        for (int[] tmp : graph) {
            Arrays.fill(tmp, -1);
        }
        // Since undirected graph requires mutual fill in, if an edge connect two nodes
        // connected, value n (edge[2]) is the total number of new nodes on that edge
        for (int[] edge: edges) { // e.g. graph[0][1] = 4
            graph[edge[0]][edge[1]] = edge[2];
            graph[edge[1]][edge[0]] = edge[2];
        }
        int result = 0;
        boolean[] visited = new boolean[N];
        // Build max pq
        PriorityQueue<int[]> pq_node_movesRemained = new PriorityQueue<>((a, b) -> (b[1] - a[1]));
        pq_node_movesRemained.add(new int[] {0, M});
        while (!pq_node_movesRemained.isEmpty()) {
            int[] tmp = pq_node_movesRemained.poll();
            int node = tmp[0];
            int movesRemained = tmp[1];
            // Check if current node visited before
            if (visited[node]) { 
                continue;
            }
            visited[node] = true;
            result++;
            for (int neighbor = 0; neighbor < N; neighbor++) {
                // cost = -1 means node and neighbor doesn't connected, 
                // the valid cost is at least 0
                if (graph[node][neighbor] != -1) {
                    if (!visited[neighbor] && movesRemained >= graph[node][neighbor] + 1) {
                        pq_node_movesRemained.add(new int[]{neighbor, movesRemained - graph[node][neighbor] - 1});
                    }
                    // 'movesCost' will record the new nodes we travelled
                    int movesCost = Math.min(movesRemained, graph[node][neighbor]);
                    // Update the remain new nodes from neighbor to current node
                    // this is important since based on an undirected graph, the 
                    // new nodes could be visted from both side
                    graph[neighbor][node] -= movesCost;
                    // Current round cost should be counted before the start of new round
                    result += movesCost;
                }
            }
        }
        return result;
    }
}
