/**
 Refer to
 https://www.cnblogs.com/lz87/p/10354361.html
 In a directed graph, each node is assigned an uppercase letter. We define a path's value as the number 
 of most frequently-occurring letter along that path. For example, if a path in the graph goes through 
 "ABACA", the value of the path is 3, since there are 3 occurrences of 'A' on the path.

Given a graph with n nodes and m directed edges, return the largest value path of the graph. If the 
largest value is infinite, then return -1.

The graph is represented with a string and an edge list. The i-th character represents the uppercase 
letter of the i-th node. Each tuple in the edge list (i, j) means there is a directed edge from the 
i-th node to the j-th node. Self-edges are possible, as well as multi-edges.

For example, the following input graph:
ABACA
[(0, 1),
 (0, 2),
 (2, 3),
 (3, 4)]
 
Would have maximum value 3 using the path of vertices [0, 2, 3, 4], (A, A, C, A).

The following input graph:
A
[(0, 0)]
Should return null, since we have an infinite loop.
*/

// Solution 1: Topological Sort (DFS)
// Refer to
// https://leetcode.com/discuss/interview-question/277534/Google-Largest-Value-Path-in-a-Directed-graph/265117
/**
 The problem can be thought of as finding a simple path of maximal length in a graph. In general, the longest 
 path problem does not have optimal substructure, and is NP-Hard. Consider the following undirected graph:

 B+----+A
 +    + +
 +  +   +
 C+----+D
 +    +
 +  +
 E
 
 The longest path from A to E is A-B-C-D-E. This goes through B, but the longest path from B to E is not 
 B-C-D-E, it’s B-C-A-D-E. This problem does not have optimal substructure, which means that we cannot solve 
 it using dynamic programming.

 Fortunately, the longest path in a DAG does have optimal substructure, which allows us to solve for it using 
 dynamic programming. For a graph G = (V, E), the algorithm is shown below.

 for each vertex v ∈ V in linearized order
   do dist(v) = max(u,v) ∈ E {dist(u) + 1}

 If there are no edges into a vertex (ie., if in-degree[v] = 0), we take the maximum over an empty set, 
 so dist(v) is 0 as expected.And, we can compute this bottom-up for each vertex v ∈ V taken in a linearized order. 
 The final algorithm is simply taking the max of all dist(v).
 See https://blog.asarkar.org/assets/docs/algorithms-curated/Longest Path in a DAG - Khan.pdf

 Time complexity:
 Topological sort: O(V + E).
 
 For each vertex, we iterate over all its neighbors. Overall, we only process each edge in the graph once, 
 so for a sparse graph, that takes O(V + E) time.

 Finally, we backtrack from the node with the max distance, which in the worst case, may include all vertices 
 in the graph. Overall time complexity O(V + E) time. Space complexity: O(V + E), since we store every edge twice, 
 once for outgoing, and once for incoming.
*/

// https://www.cnblogs.com/lz87/p/10354361.html
/**
 The naive solution would be to try every single path from every vertex, counting up each path's value 
 and keep track of the maximum values we've seen. To do this, we can use DFS to try every path and return 
 INFINITY if we come across a cycle.  However, this would be terribly slow with a runtime of O(V * (V + E)) 
 since we need to perform an O(V + E) DFS V times.
 
 The reason that the above naive solution is inefficent is because it does not memoize any previously 
 calculated results,  for a path that has just been traversed, it will traverse all of its sub-paths. 
 This sounds like a good problem for dynamic programming.
 
 Since we're using the alphabet of upper case characters, we have a fixed number 26 of potential values 
 that contribute to the max value path.  Let's keep a matrix of size N by 26 T[i][j]. 
 
 T[i][j] is the max value that can be made using the ith node as path starting node and jth letter. We must 
 keep all of the path values using all possible letters because a local max path does not necessarily lead 
 to a global max path.  
 
 T[i][j] = max of T[neighbor][j] for all i's neighbors.  We also need to count the current node too, 
 so increment T[i][current_char] by one, where current_char is the current node's assigned letter. 
 This solution is O(V + E) in runtime as it never visits the same vertex or edge more than once. 
 The space used is O(V + E) for all the information book-keeping.
 
 The DFS implementation uses a tri-state to avoid visiting the same vertex more than once and detecting cycles.
*/
class Solution {
    public int maxGraphPath(String s, int[][] edges) {
        Map < Integer, List < Integer >> graph = new HashMap < Integer, List < Integer >> ();
        for (int i = 0; i < s.length(); i++) {
            graph.putIfAbsent(i, new ArrayList < Integer > ());
        }
        for (int i = 0; i < edges.length; i++) {
            graph.get(edges[i][0]).add(edges[i][1]);
        }
        boolean[] visited = new boolean[s.length()];
        boolean[] dp = new boolean[s.length()];
        int[][] maxPaths = new int[s.length()][26];
        for (int i = 0; i < s.length(); i++) {
            if (!helper(s, graph, visited, dp, maxPaths, i)) {
                return -1;
            }
        }
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < 26; j++) {
                if (result < maxPaths[i][j]) {
                    result = maxPaths[i][j];
                }
            }
        }
        return result;
    }

    private boolean helper(String s, Map < Integer, List < Integer >> graph, boolean[] visited, boolean[] dp, int[][] maxPaths, int i) {
        if (visited[i]) {
            return true;
        }
        if (dp[i]) {
            return false;
        }
        dp[i] = true;
        for (int neighbor: graph.get(i)) {
            if (!helper(s, graph, visited, dp, maxPaths, neighbor)) {
                return false;
            }
        }
        dp[i] = false;
        visited[i] = true;
        for (int neighbor: graph.get(i)) {
            for (int letter = 0; letter < 26; letter++) {
                maxPaths[i][letter] = Math.max(maxPaths[i][letter], maxPaths[neighbor][letter]);
            }
        }
        maxPaths[i][s.charAt(i) - 'A']++;
        return true;
    }
}
