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

// Why the time complexity is O(2^n)
// Refer to
// https://leetcode.com/problems/all-paths-from-source-to-target/discuss/118713/Java-DFS-Solution/198489
/**
 I think the time complexity is O(2^n).
Think about this worst case scenario:
Suppose we have n nodes, labeled 0 to n-1.
Think of it as an array: [0, 1, 2, 3, 4, 5, 6, ..., n-1]
For each pair of nodes i and j, if i < j, let there be an edge between node i and node j.
(So, there are O(n^2) edges, though this fact is not important.)
Now, we want to calculate how many paths there are from the 0th node to the (n-1)th node.
Well, notice that each path of length k corresponds to some choice of (k - 1) nodes between 0 and (n-1).
For example, here are two paths of length 2:
0->3->(n-1)
0->5->(n-1)
Here is a path of length 3:
0->1->5->(n-1)
How many paths of length k are there? The answer is (n-2 choose k-1) because we pick k - 1 nodes between 0 and (n - 1).
The total number of paths is the sum of (n-2 choose k-1) for k = 1, 2, ..., (n-1).
Using the binomial theorem, this sum is equal to 2^(n-2) which is O(2^n).
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


