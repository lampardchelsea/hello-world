/**
 Refer to
 https://leetcode.com/problems/redundant-connection-ii/
 In this problem, a rooted tree is a directed graph such that, there is exactly one node (the root) for which all other nodes are descendants of this node, plus every node has exactly one parent, except for the root node which has no parents.

The given input is a directed graph that started as a rooted tree with N nodes (with distinct values 1, 2, ..., N), with one additional directed edge added. The added edge has two different vertices chosen from 1 to N, and was not an edge that already existed.

The resulting graph is given as a 2D-array of edges. Each element of edges is a pair [u, v] that represents a directed edge connecting nodes u and v, where u is a parent of child v.

Return an edge that can be removed so that the resulting graph is a rooted tree of N nodes. If there are multiple answers, return the answer that occurs last in the given 2D-array.

Example 1:
Input: [[1,2], [1,3], [2,3]]
Output: [2,3]
Explanation: The given directed graph will be like this:
  1
 / \
v   v
2-->3
Example 2:
Input: [[1,2], [2,3], [3,4], [4,1], [1,5]]
Output: [4,1]
Explanation: The given directed graph will be like this:
5 <- 1 -> 2
     ^    |
     |    v
     4 <- 3
Note:
The size of the input 2D-array will be between 3 and 1000.
Every integer represented in the 2D-array will be between 1 and N, where N is the size of the input array.
*/

// Solution 1: UnionFind
// Refer to
// https://leetcode.com/problems/redundant-connection-ii/discuss/108045/C++Java-Union-Find-with-explanation-O(n)/213568
class Solution {
    Map<Integer, Integer> incoming = new HashMap<Integer, Integer>();
    public int[] findRedundantDirectedConnection(int[][] edges) {
        // Count incoming edges for all nodes
        int nodeWithTwoIncomingEdges = -1;
        for(int[] edge : edges) {
            incoming.put(edge[1], incoming.getOrDefault(edge[1], 0) + 1);
            if(incoming.get(edge[1]) == 2) {
                nodeWithTwoIncomingEdges = edge[1];
            }
        }
        if(nodeWithTwoIncomingEdges == -1) {
            // If there are no nodes with 2 incoming edges -> just find a cycle
            return findRedundantConnection(edges, -1);
        } else {
            // If there is a node with 2 incoming edges -> skip them one by one
            // and try to build a graph, if we manage to build a graph without
            // a cycle - the skipped node is what we're looking for
            for(int i = edges.length - 1; i >= 0; i--) {
                if(edges[i][1] == nodeWithTwoIncomingEdges) {
                    int[] result = findRedundantConnection(edges, i);
                    if(result == null) {
                        return edges[i];
                    }
                }
            }
        }
        return null;
    }
    
    // 'Redundant Connection' solution is extended to skip a node.
    private int[] findRedundantConnection(int[][] edges, int skip) {
        UnionFind uf = new UnionFind();
        for(int i = 0; i < edges.length; i++) {
            // Skip the node
            if(i == skip) {
                continue;
            }
            if(!uf.union(edges[i][0], edges[i][1])) {
                return edges[i];
            }
        }
        return null;
    }
}

// Because we don't build parent with continuous number as need to skip
// one number, the UnionFind template has a little difference
class UnionFind {
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    public int find(int x){
        if(!map.containsKey(x)) {
            map.put(x, x);
        }
        if(map.get(x) == x) {
            return x;
        }
        int parent = find(map.get(x));
        map.put(x, parent);
        return parent;
    }

    public boolean union(int x, int y){
        int src = find(x);
        int dst = find(y);
        if(src == dst) {
            return false;
        }
        map.put(src, dst);
        return true;
    }     
}
