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


