/**
 Refer to
 https://leetcode.com/problems/shortest-path-with-alternating-colors/
 Consider a directed graph, with nodes labelled 0, 1, ..., n-1.  In this graph, each edge 
 is either red or blue, and there could be self-edges or parallel edges.

Each [i, j] in red_edges denotes a red directed edge from node i to node j.  Similarly, 
each [i, j] in blue_edges denotes a blue directed edge from node i to node j.

Return an array answer of length n, where each answer[X] is the length of the shortest path from 
node 0 to node X such that the edge colors alternate along the path (or -1 if such a path doesn't exist).

Example 1:
Input: n = 3, red_edges = [[0,1],[1,2]], blue_edges = []
Output: [0,1,-1]

Example 2:
Input: n = 3, red_edges = [[0,1]], blue_edges = [[2,1]]
Output: [0,1,-1]

Example 3:
Input: n = 3, red_edges = [[1,0]], blue_edges = [[2,1]]
Output: [0,-1,-1]

Example 4:
Input: n = 3, red_edges = [[0,1]], blue_edges = [[1,2]]
Output: [0,1,2]

Example 5:
Input: n = 3, red_edges = [[0,1],[0,2]], blue_edges = [[1,0]]
Output: [0,1,1]

Constraints:
1 <= n <= 100
red_edges.length <= 400
blue_edges.length <= 400
red_edges[i].length == blue_edges[i].length == 2
0 <= red_edges[i][j], blue_edges[i][j] < n
*/

// Solution 1: BFS
// Refer to
// https://leetcode.com/problems/shortest-path-with-alternating-colors/discuss/340258/Java-BFS-Solution-with-Video-Explanation
class Solution {
    public int[] shortestAlternatingPaths(int n, int[][] red_edges, int[][] blue_edges) {
        int[][] graph = new int[n][n];
        buildGraph(graph, n, red_edges, blue_edges);
        Queue<int[]> queue = new LinkedList<int[]>();
        // element {position, color}, 1 is red, -1 is blue, 0 is both, -n is not connected
        // Start with two possibilities, node at 0 colored by either red or blue
        queue.offer(new int[]{0, 1});
        queue.offer(new int[]{0, -1});
        int len = 0;
        int[] result = new int[n];
        Arrays.fill(result, Integer.MAX_VALUE);
        result[0] = 0;
        // Recording visited nodes and important thing is also need recording visited
        // by which color, because 1 node may either visited by red or blue path, and
        // these 2 possible paths should not conflict
        Set<String> visited = new HashSet<String>();
        while(!queue.isEmpty()) {
            // To calculate path length (same wave node has same distance
            // from node 0), treat as level traverse
            int size = queue.size();
            len++;
            for(int i = 0; i < size; i++) {
                int[] cur = queue.poll();
                int node = cur[0];
                int color = cur[1];
                int oppoColor = -color;
                // Scan all nodes to find which connection between current node and
                // node j match opposite color or have both colors
                for(int j = 0; j < n; j++) {
                    // Either opposite color or 0(both color) is target
                    if(graph[node][j] == oppoColor || graph[node][j] == 0) {
                        // If the node j already visited before in BFS means the distance
                        // between that node and current node must be smaller, because BFS
                        // theoratically is always able to find smaller distance first
                        if(!visited.contains(j + "" + oppoColor)) {
                            visited.add(j + "" + oppoColor);
                            queue.offer(new int[]{j, oppoColor});
                            result[j] = Math.min(result[j], len);
                        }
                    }
                }
            }
        }
        for(int i = 0; i < n; i++) {
            if(result[i] == Integer.MAX_VALUE) {
                result[i] = -1;
            }
        }
        return result;
    }
    
    private void buildGraph(int[][] graph, int n, int[][] red_edges, int[][] blue_edges) {
        // Initialize all node color as -n, means two nodes not connected by edge
        for(int i = 0; i < n; i++) {
            Arrays.fill(graph[i], -n);
        }
        // Set nodes connected only by red edge as 1
        for(int[] red_edge : red_edges) {
            int from = red_edge[0];
            int to = red_edge[1];
            graph[from][to] = 1;
        }
        // Set nodes connected only by blue edge as -1
        // Set nodes connected both by red / blue edge as 0 since parallel edges exist
        for(int[] blue_edge : blue_edges) {
            int from = blue_edge[0];
            int to = blue_edge[1];
            if(graph[from][to] == 1) {
                graph[from][to] = 0;
            } else {
                graph[from][to] = -1;
            }
        }
    }
}


