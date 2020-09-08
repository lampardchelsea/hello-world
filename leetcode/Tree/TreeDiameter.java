/**
 Refer to
 https://www.cnblogs.com/Dylan-Java-NYC/p/12365031.html
 Given an undirected tree, return its diameter: the number of edges in a longest path in that tree.

 The tree is given as an array of edges where edges[i] = [u, v] is a bidirectional edge between nodes u and v.  
 Each node has labels in the set {0, 1, ..., edges.length}.

 Example 1:
            0
         2     1

 Input: edges = [[0,1],[0,2]]
 Output: 2
 Explanation: 
 A longest path of the tree is the path 1 - 0 - 2.
 
 Example 2:
                 1
             0   2   4
                 3   5

 Input: edges = [[0,1],[1,2],[2,3],[1,4],[4,5]]
 Output: 4
 Explanation: 
 A longest path of the tree is the path 3 - 2 - 1 - 4 - 5.
 
 Constraints:
 0 <= edges.length < 10^4
 edges[i][0] != edges[i][1]
 0 <= edges[i][j] <= edges.length
 The given edges form an undirected tree.
*/

// Solution 1:
// Refer to
// https://www.cnblogs.com/Dylan-Java-NYC/p/12365031.html
/**
 If it is a tree, then its nodes n = edges.length + 1.
 Build the tree graph first. Then start DFS from node 0.
 DFS returns the deepest depth. DFS state needs current node, tree graph, and parent.
 For current node, for all its neighbors, as long as it is not parent, get the depth from it, pick 2 largest. 
 Use these 2 largest to update the diameter.
 And returns the largest depth + 1.
 Time Complexity: O(n). n = edges.length.
 Space: O(n).
*/

// https://blog.csdn.net/qq_17550379/article/details/102897279
/**
 这个问题和之前问题Leetcode 543：二叉树的直径（超详细的解法！！！）非常类似，所以这题我们依旧可以采用dfs来处理。
 和之前问题不同的地方是该问题中是图的遍历（之前问题是树的遍历），所以我们需要求出每个节点周围所有最长路径中最长的
 两个（相当于之前问题中的l和r），dfs的返回结果就是最长的那个路径加1（表示当前节点可以访问的最远距离
*/

// Also refer to below 2 problems to get final solution
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/ReorderRoutesToMakeAllPathsLeadToTheCityZero.java
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/DiameterOfBinaryTree.java
class Solution {
    int result = 0;
    public int treeDiameter(int[][] edges) {
        if(edges == null || edges.length == 0) {
            return 0;
        }
        int n = edges.length + 1;
        List<List<Integer>> adj_lists = new ArrayList<List<Integer>>;
        for(int i = 0; i < n; i++) {
            adj_lists.add(new ArrayList<Integer>());
        }
        for(int[] e : edges) {
            adj_lists[e[0]].add(e[1]);
            adj_lists[e[1]].add(e[0]);
        }
        dfs(adj_lists, -1, 0);
        return result;
    }

    private int dfs(List<List<Integer>> adj_lists, int parent, int root){
        int max1 = 0;
        int max2 = 0;
        for(int next : adj_lists.get(root)) {
            if(next != parent) {
                int depth = dfs(adj_lists, root, next);
                if(depth > max1) {
                    max2 = max1;
                    max1 = depth;
                } else if(depth > max2) {
                    max2 = depth;
                }
            }
        }
        result = Math.max(result, max1 + max2);
        return max1 + 1;
    }
}

