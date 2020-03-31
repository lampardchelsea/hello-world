/**
 Refer to
 https://leetcode.com/problems/all-paths-from-source-to-target/
 Given a directed, acyclic graph of N nodes.  Find all possible paths from node 0 to node N-1, 
 and return them in any order.
 The graph is given as follows:  the nodes are 0, 1, ..., graph.length - 1.  graph[i] is a 
 list of all nodes j for which the edge (i, j) exists.

Example:
Input: [[1,2], [3], [3], []] 
Output: [[0,1,3],[0,2,3]] 
Explanation: The graph looks like this:
0--->1
|    |
v    v
2--->3
There are two paths: 0 -> 1 -> 3 and 0 -> 2 -> 3.

Note:
The number of nodes in the graph will be in the range [2, 15].
You can print different paths in any order, but you should keep the order of nodes inside one path.
*/

// Solution 1: DFS + backtracking
// Refer to
// https://leetcode.com/problems/all-paths-from-source-to-target/discuss/297408/Java-DFS-solution-Easy-to-understand-and-Explanation
/**
 For this quesiton we can generalize it to:

Find all paths from a vertex u to vertex v.
There will be an exponetial number of paths - O(2^n) paths

To generate all of these paths you need to use Backtracking:
1. Go through every vertex's childern (This is essentially picking a path)
2. Then dfs() on that path to find if any of those childern lead to your target vertex
3. If they, do then add them to your answer list

Time Complexity: O(2^n), because there will be 2^n number of paths
Space Complexity: O(2^n), because you will need to return 2^n number of paths
*/
class Solution {
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<Integer> path = new ArrayList<Integer>();
        path.add(0);
        int n = graph.length;
        helper(graph, 0, n - 1, result, path);
        return result;
    }
    
    private void helper(int[][] graph, int source, int target, List<List<Integer>> result, List<Integer> path) {
        if(source == target) {
            result.add(new ArrayList<Integer>(path));
            return;
        }
        for(int neighbor : graph[source]) {
            path.add(neighbor);
            helper(graph, neighbor, target, result, path);
            path.remove(path.size() - 1);
        }
    }
}


