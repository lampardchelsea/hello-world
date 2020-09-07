/**
 Refer to
 https://leetcode.com/problems/reorder-routes-to-make-all-paths-lead-to-the-city-zero/
 There are n cities numbered from 0 to n-1 and n-1 roads such that there is only one way to travel between two different 
 cities (this network form a tree). Last year, The ministry of transport decided to orient the roads in one direction 
 because they are too narrow.
 
 Roads are represented by connections where connections[i] = [a, b] represents a road from city a to b.
 This year, there will be a big event in the capital (city 0), and many people want to travel to this city.
 Your task consists of reorienting some roads such that each city can visit the city 0. Return the minimum number of edges changed.
 It's guaranteed that each city can reach the city 0 after reorder.

 Example 1:
 
     0 --> 1 --> 3 <-- 2      ==>    0 <-- 1 <-- 3 <-- 2
       <-- 4 --> 5                     <-- 4 <-- 5

 Input: n = 6, connections = [[0,1],[1,3],[2,3],[4,0],[4,5]]
 Output: 3
 Explanation: Change the direction of edges show in red such that each node can reach the node 0 (capital).

 Example 2:
 
    0 <-- 1 --> 2 <-- 3 --> 4   ==>  0 <-- 1 <-- 2 <-- 3 <-- 4

 Input: n = 5, connections = [[1,0],[1,2],[3,2],[3,4]]
 Output: 2
 Explanation: Change the direction of edges show in red such that each node can reach the node 0 (capital).
 
 Example 3:
 Input: n = 3, connections = [[1,0],[2,0]]
 Output: 0

 Constraints:
 2 <= n <= 5 * 10^4
 connections.length == n-1
 connections[i].length == 2
 0 <= connections[i][0], connections[i][1] <= n-1
 connections[i][0] != connections[i][1]
*/

// Solution 1: Graph + Directed adjacent map traversal with DFS
// Refer to
// https://leetcode.com/problems/reorder-routes-to-make-all-paths-lead-to-the-city-zero/discuss/661672/C%2B%2BJava-Track-Direction
/**
Based on the problem description, we have a tree, and node zero is the root.

However, the direction can point either from a parent to a child (positive), or from a child to its parent (negative). 
To solve the problem, we traverse the tree and count edges that are directed from a parent to a child. Direction of 
those edges need to be changed to arrive at zero node.

In the code below, I am using the adjacency list, and the sign indicates the direction. If the index is positive - the direction 
is from a parent to a child and we need to change it (change += (to > 0)).

Note that we cannot detect the direction for zero (-0 == 0), but it does not matter as we start our traversal from zero.

Complexity Analysis
Time: O(n). We visit each node once.
Memory: O(n). We store n nodes in the adjacency list, with n - 1 edges in total.
*/
class Solution {
    public int minReorder(int n, int[][] connections) {
        List<List<Integer>> adj_lists = new ArrayList<List<Integer>>();
        for(int i = 0; i < n; i++) {
            adj_lists.add(new ArrayList<Integer>());
        }
        for(int[] c : connections) {
            adj_lists.get(c[0]).add(c[1]);
            adj_lists.get(c[1]).add(-c[0]);
        }
        boolean[] visited = new boolean[n];
        return helper(adj_lists, visited, 0);
    }
    
    private int helper(List<List<Integer>> adj_lists, boolean[] visited, int from) {
        int result = 0;
        visited[from] = true;
        for(int to : adj_lists.get(from)) {
            if(!visited[Math.abs(to)]) {
                if(to > 0) {
                    result += 1;
                }
                result += helper(adj_lists, visited, Math.abs(to));
            }
        }
        return result;
    }
}

// Solution 2: Get rid of 'visited'
// Refer to
// https://leetcode.com/problems/reorder-routes-to-make-all-paths-lead-to-the-city-zero/discuss/661672/C%2B%2BJava-Track-Direction
// https://leetcode.com/problems/reorder-routes-to-make-all-paths-lead-to-the-city-zero/discuss/661672/C++Java-Track-Direction/603863
/**
 Bonus: Minimalizm Version
 Instead of visited, we can just pass the previous index to prevent going back, as suggested by zed_b. 
 This is possible because every node has only one parent in the tree.
*/

