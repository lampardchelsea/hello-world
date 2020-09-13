/**
 Refer to
 https://leetcode.com/problems/minimum-time-to-collect-all-apples-in-a-tree/
 Given an undirected tree consisting of n vertices numbered from 0 to n-1, which has some apples in their vertices. 
 You spend 1 second to walk over one edge of the tree. Return the minimum time in seconds you have to spend in order 
 to collect all apples in the tree starting at vertex 0 and coming back to this vertex.
 
 The edges of the undirected tree are given in the array edges, where edges[i] = [fromi, toi] means that exists an 
 edge connecting the vertices fromi and toi. Additionally, there is a boolean array hasApple, where hasApple[i] = true 
 means that vertex i has an apple, otherwise, it does not have any apple.

 Example 1:
                   0
                1     2 (T)
              4   5 3   6 
             (T) (T)
             
 0 --> 1 --> 4 --> 1 --> 5 --> 1 --> 0 --> 2 --> 0
            (T)         (T)               (T)
            
 Input: n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], hasApple = [false,false,true,false,true,true,false]
 Output: 8 
 Explanation: The figure above represents the given tree where red vertices have an apple. One optimal path to collect all apples is shown by the green arrows.  

 Example 2:
                    0
                 1     2 (T)
               4   5 3   6
                  (T)
                  
 0 --> 1 --> 5 --> 1 --> 0 --> 2 --> 0             
            (T)               (T)
            
 Input: n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], hasApple = [false,false,true,false,false,true,false]
 Output: 6
 Explanation: The figure above represents the given tree where red vertices have an apple. One optimal path to collect all apples is shown by the green arrows.  

 Example 3:
 Input: n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], hasApple = [false,false,false,false,false,false,false]
 Output: 0
 
 Constraints:
 1 <= n <= 10^5
 edges.length == n-1
 edges[i].length == 2
 0 <= fromi, toi <= n-1
 fromi < toi
 hasApple.length == n
*/

// Solution 1: Build Tree + DFS
// Refer to
// https://leetcode.com/problems/minimum-time-to-collect-all-apples-in-a-tree/discuss/623673/Concise-explanation-with-a-Picture-for-Visualization
/**
 Key idea
 Whenever you are at a node, say p, you will collect all apples in p’s subtree before returning back to the original root. 
 This will avoid traveling the same path multiple times.
 Say, root is where we start, p is a node in the tree and p has two children - child1, child2 - and both of them have an apple each.
 root -> p -> child1 -> p -> child2 -> p -> root
 is always going to be better than
 root -> p -> child1 -> p -> root -> p -> child2 -> p -> root.

 So now it becomes a simple graph traversal problem, particularly DFS where we visit all children of the node first and then go back the other nodes.

 Algorithm
 Because you have the list of edges, construct the graph first to have a better representation of the graph.
 For each node, check if any of its children have apples and find the total cost in terms of time in seconds of collecting those.
 If there is at least one child with an apple, then we have to collect it. So we will also have to add the cost (time required) of reaching that node.
 return the total time.
*/

// https://leetcode.com/problems/minimum-time-to-collect-all-apples-in-a-tree/discuss/623686/Java-Detailed-Explanation-Build-Tree-%2B-DFS
/**
 [Updated] Still need bi-direction graph and use visited set. Example test case for that: (Thanks @hiepit and @notebook, my bad)
 4
 [[0,2],[0,3],[1,2]]
 [false,true,false,false]
 Expected Answer: 4

 Key Notes:
 You need to consume 2 seconds to simply collect an apple node (come and go)
 Consider a node:
 1.If none of descendant (including itself) has an apple, we don't need to waste time on this node
 2.If any of descendant has an apple (no matter if it-self has an apple or not), we need to consume 2 seconds on this node anyway
 3.Collect node 0 does not need to consume any time
 Then, we can have a helper dfs function meaning: time needs to waste on this node to collect all apples. (0 or > 0).
*/
class Solution {
    public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        for(int i = 0; i < n; i++) {
            map.put(i, new ArrayList<Integer>());
        }
        for(int[] edge : edges) {
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }
        return helper(0, map, new boolean[n], hasApple);
    }
    
    private int helper(int curNode, Map<Integer, List<Integer>> map, boolean[] visited, List<Boolean> hasApple) {
        if(visited[curNode]) {
            return 0;
        }
        visited[curNode] = true;
        int result = 0;
        for(int i = 0; i < map.get(curNode).size(); i++) {
            int nextNode = map.get(curNode).get(i);
            result += helper(nextNode, map, visited, hasApple);
        }
        // 1. curNode != 0 --> Collect node 0 does not need to consume any time
        // 2. If not node 0 and if any of descendant has an apple (no matter if 
        //    itself has an apple or not), we need to consume 2 seconds on this 
        //    node anyway
        if(curNode != 0 && (result > 0 || hasApple.get(curNode))) {
            result += 2;
        }
        return result;
    }
}

// Solution 2: Boolean DFS return
// Refer to
// https://leetcode.com/problems/minimum-time-to-collect-all-apples-in-a-tree/discuss/623735/Java-Easy-DFS
class Solution {
    int count = 0;
    public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        for(int i = 0; i < n; i++) {
            map.put(i, new ArrayList<Integer>());
        }
        for(int[] edge : edges) {
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }
        helper(0, map, new boolean[n], hasApple);
        return count;
    }
    
    private boolean helper(int curNode, Map<Integer, List<Integer>> map, boolean[] visited, List<Boolean> hasApple) {
        if(visited[curNode]) {
            return false;
        }
        visited[curNode] = true;
        boolean result = hasApple.get(curNode);
        for(int i = 0; i < map.get(curNode).size(); i++) {
            if(helper(map.get(curNode).get(i), map, visited, hasApple)) {
                count += 2;
                result = true;
            }
        }
        return result;
    }
}
