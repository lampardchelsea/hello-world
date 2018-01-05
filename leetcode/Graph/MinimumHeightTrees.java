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



