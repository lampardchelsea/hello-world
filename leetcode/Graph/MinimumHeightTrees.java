/**
 * Refer to
 * https://leetcode.com/problems/minimum-height-trees/description/
 * For a undirected graph with tree characteristics, we can choose any node as the root. 
   The result graph is then a rooted tree. Among all possible rooted trees, those with 
   minimum height are called minimum height trees (MHTs). Given such a graph, write a 
   function to find all the MHTs and return a list of their root labels.

    Format
    The graph contains n nodes which are labeled from 0 to n - 1. You will be given the 
    number n and a list of undirected edges (each edge is a pair of labels).

    You can assume that no duplicate edges will appear in edges. Since all edges are 
    undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.

    Example 1:
    Given n = 4, edges = [[1, 0], [1, 2], [1, 3]]

            0
            |
            1
           / \
          2   3
    return [1]

    Example 2:
    Given n = 6, edges = [[0, 3], [1, 3], [2, 3], [4, 3], [5, 4]]

         0  1  2
          \ | /
            3
            |
            4
            |
            5
    return [3, 4]

    Note:
    (1) According to the definition of tree on Wikipedia: “a tree is an undirected 
        graph in which any two vertices are connected by exactly one path. In other 
        words, any connected graph without simple cycles is a tree.”
    (2) The height of a rooted tree is the number of edges on the longest downward 
        path between the root and a leaf.
 *
 * Solution
 * http://www.cnblogs.com/grandyang/p/5000291.html
 * 这道题虽然是树的题目，但是跟其最接近的题目是Course Schedule 课程清单和Course Schedule II 
   课程清单之二。由于LeetCode中的树的题目主要都是针对于二叉树的，而这道题虽说是树但其实本质是
   想考察图的知识，这道题刚开始在拿到的时候，我最先想到的解法是遍历的点，以每个点都当做根节点，
   算出高度，然后找出最小的，但是一时半会又写不出程序来，于是上网看看大家的解法，发现大家推崇
   的方法是一个类似剥洋葱的方法，就是一层一层的褪去叶节点，最后剩下的一个或两个节点就是我们要求
   的最小高度树的根节点，这种思路非常的巧妙，而且实现起来也不难，跟之前那到课程清单的题一样，
   我们需要建立一个图g，是一个二维数组，其中g[i]是一个一维数组，保存了i节点可以到达的所有节点。
   我们开始将所有只有一个连接边的节点(叶节点)都存入到一个队列queue中，然后我们遍历每一个叶节点，
   通过图来找到和其相连的节点，并且在其相连节点的集合中将该叶节点删去，如果删完后此节点也也变成
   一个叶节点了，加入队列中，再下一轮删除。那么我们删到什么时候呢，当节点数小于等于2时候停止，
   此时剩下的一个或两个节点就是我们要求的最小高度树的根节点啦
 *
 *
 * https://discuss.leetcode.com/topic/30572/share-some-thoughts
 *  First let's review some statement for tree in graph theory:
    (1) A tree is an undirected graph in which any two vertices are
    connected by exactly one path.

    (2) Any connected graph who has n nodes with n-1 edges is a tree.

    (3) The degree of a vertex of a graph is the number of
    edges incident to the vertex.

    (4) A leaf is a vertex of degree 1. An internal vertex is a vertex of
    degree at least 2.

    (5) A path graph is a tree with two or more vertices that is not
    branched at all.

    (6) A tree is called a rooted tree if one vertex has been designated
    the root.

    (7) The height of a rooted tree is the number of edges on the longest
    downward path between root and a leaf.

    OK. Let's stop here and look at our problem.

    Our problem want us to find the minimum height trees and return their root labels. 
    First we can think about a simple case -- a path graph.

    For a path graph of n nodes, find the minimum height trees is trivial. Just designate 
    the middle point(s) as roots.

    Despite its triviality, let design a algorithm to find them.

    Suppose we don't know n, nor do we have random access of the nodes. We have to traversal. 
    It is very easy to get the idea of two pointers. One from each end and move at the same 
    speed. When they meet or they are one step away, (depends on the parity of n), we have 
    the roots we want.

    This gives us a lot of useful ideas to crack our real problem.

    For a tree we can do some thing similar. We start from every end, by end we mean vertex 
    of degree 1 (aka leaves). We let the pointers move the same speed. When two pointers meet, 
    we keep only one of them, until the last two pointers meet or one step away we then find the roots.

    It is easy to see that the last two pointers are from the two ends of the longest path in the graph.

    The actual implementation is similar to the BFS topological sort. Remove the leaves, update 
    the degrees of inner vertexes. Then remove the new leaves. Doing so level by level until 
    there are 2 or 1 nodes left. What's left is our answer!

    The time complexity and space complexity are both O(n).

    Note that for a tree we always have V = n, E = n-1.
*/
class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> list = new ArrayList<Integer>();
        if(n == 1) {
            list.add(0);
            return list;
        }
        
        // Build graph
        List<Set<Integer>> adj = new ArrayList<Set<Integer>>();
        for(int i = 0; i < n; i++) {
            adj.add(new HashSet<Integer>());
        }
        for(int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }
        
        // Find out all leaves
        List<Integer> leaves = new ArrayList<Integer>();
        for(int i = 0; i < n; i++) {
            if(adj.get(i).size() == 1) {
                leaves.add(i);
            }
        }
        
        // BFS to find final root
        while(n > 2) {
            // Get rid of all leaves node
            n -= leaves.size();
            List<Integer> newLeaves = new ArrayList<Integer>();
            for(int i : leaves) {
                /**
                  * Since current node is leave node, it only have 1 connection node
                  * e.g       4
                  *         / | \ 
                  *        1  2  3
                  * For nodes 1, 2, 3, they are all leave node, and connection node as
                  * only 4, to find node 1, 2, 3's connection node 4 is able to get by
                  * Set's iterator, as only 1 item 4 will be in 1, 2, 3's adjacence set
                  * so, we can say 
                  * adj.get(1).iterator().next() = 4
                  * adj.get(2).iterator().next() = 4
                  * adj.get(3).iterator().next() = 4
                  */
                int j = adj.get(i).iterator().next();
                adj.get(j).remove(i);
                if(adj.get(j).size() == 1) {
                    newLeaves.add(j);
                }
            }
            leaves = newLeaves;
        }
        return leaves;
    }
}


// Build graph with map to increase store and retrieve speed as O(1)
class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> result = new ArrayList<Integer>();
        if(n == 1) {
            result.add(0);
            return result;
        }
        // Initialize graph
        Map<Integer, Set<Integer>> map = new HashMap<Integer, Set<Integer>>();
        for(int i = 0; i < n; i++) {
            map.put(i, new HashSet<Integer>());
        }
        for(int[] edge : edges) {
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }
        // Find leaves
        List<Integer> leaves = new ArrayList<Integer>();
        for(Map.Entry<Integer, Set<Integer>> entry : map.entrySet()) {
            if(entry.getValue().size() == 1) {
                leaves.add(entry.getKey());
            }
        }
        // BFS find final root
        while(n > 2) {
            n -= leaves.size();
            List<Integer> newLeaves = new ArrayList<Integer>();
            for(int i = 0; i < leaves.size(); i++) {
                int leave = leaves.get(i);
                int newLeaveCandidate = map.get(leave).iterator().next();
                map.get(newLeaveCandidate).remove(leave);
                if(map.get(newLeaveCandidate).size() == 1) {
                    newLeaves.add(newLeaveCandidate);
                }
            }
            leaves = newLeaves;
        }
        return leaves;
    }
}


