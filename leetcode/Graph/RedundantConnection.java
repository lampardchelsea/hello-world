/**
 Refer to
 https://leetcode.com/problems/redundant-connection/
 In this problem, a tree is an undirected graph that is connected and has no cycles.

The given input is a graph that started as a tree with N nodes (with distinct values 1, 2, ..., N), with one 
additional edge added. The added edge has two different vertices chosen from 1 to N, and was not an edge 
that already existed.

The resulting graph is given as a 2D-array of edges. Each element of edges is a pair [u, v] with u < v, that 
represents an undirected edge connecting nodes u and v.

Return an edge that can be removed so that the resulting graph is a tree of N nodes. If there are multiple 
answers, return the answer that occurs last in the given 2D-array. The answer edge [u, v] should be in the 
same format, with u < v.

Example 1:
Input: [[1,2], [1,3], [2,3]]
Output: [2,3]
Explanation: The given undirected graph will be like this:
  1
 / \
2 - 3

Example 2:
Input: [[1,2], [2,3], [3,4], [1,4], [1,5]]
Output: [1,4]
Explanation: The given undirected graph will be like this:
5 - 1 - 2
    |   |
    4 - 3

Note:
The size of the input 2D-array will be between 3 and 1000.
Every integer represented in the 2D-array will be between 1 and N, where N is the size of the input array.

Update (2017-09-26):
We have overhauled the problem description + test cases and specified clearly the graph is an undirected graph. 
For the directed graph follow up please see Redundant Connection II). We apologize for any inconvenience caused.
*/

// Solution 1: Union Find
// Refer to
// https://leetcode.com/problems/redundant-connection/discuss/107984/10-line-Java-solution-Union-Find./222254
/**
 Why we use parent[i] = i ?
 In union-find algorithm, each group would have one root node which represent this group. We have to assign a 
 unique value for root node in the parent array to distinguish it from other non-root nodes. A normal non-root 
 node would be like parent[i] != i and parent[i] > 0. So to mark a node as root node we can assign parent[i] 
 as i or any value < 1 in this case. So these 2 lines initialize every node as root node. 
 After the initialization, we can combine them in the following part of the code.
*/

/**
 What is path compression ?
 The second optimization to naive method is Path Compression. The idea is to flatten the tree when find() is 
 called. When find() is called for an element x, root of the tree is returned. The find() operation traverses 
 up from x to find root. The idea of path compression is to make the found root as parent of x so that we don’t 
 have to traverse all intermediate nodes again. If x is root of a subtree, then path (to root) from all nodes 
 under x also compresses.
 
Let the subset {0, 1, .. 9} be represented as below and find() is called
for element 3.
              9
         /    |    \  
        4     5      6
     /     \        /  \
    0        3     7    8
            /  \
           1    2  

When find() is called for 3, we traverse up and find 9 as representative
of this subset. With path compression, we also make 3 as the child of 9 so 
that when find() is called next time for 1, 2 or 3, the path to root is reduced.

               9
         /    /  \    \
        4    5    6     3 
     /           /  \   /  \
    0           7    8  1   2           

The two techniques complement each other. The time complexity of each operation becomes even smaller 
than O(Logn). In fact, amortized time complexity effectively becomes small constant.
*/

/**
 An edge will connect two nodes into one connected component.

When we count an edge in, if two nodes have already been in the same connected component, the edge will result 
in a cycle. That is, the edge is redundant.

We can make use of Disjoint Sets (Union Find).
If we regard a node as an element, a connected component is actually a disjoint set.

For example,

Given edges [1, 2], [1, 3], [2, 3],
  1
 / \
2 - 3
Initially, there are 3 disjoint sets: 1, 2, 3.
Edge [1,2] connects 1 to 2, i.e., 1 and 2 are winthin the same connected component.
Edge [1,3] connects 1 to 3, i.e., 1 and 3 are winthin the same connected component.
Edge [2,3] connects 2 to 3, but 2 and 3 have been winthin the same connected component already, so [2, 3] is redundant.
*/
class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        // If set numNodes = edges.length * 2 then need mapping index = 0
        // as start by -1 from node number
        int numNodes = edges.length * 2 + 1;
        UnionFind uf = new UnionFind(numNodes);
        for(int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            if(uf.find(a) == uf.find(b)) {
                return edge;
            }
            uf.union(a, b);
        }
        return null;
    }
}

class UnionFind {
    int[] parent;
    int count;
    public UnionFind(int n) {
        parent = new int[n];
        for(int i = 0; i < n; i++) {
           parent[i] = i;    
        }
        count = n;
    }
    
    public int find(int x) {
        if(parent[x] == x) {
            return x;
        }
        return parent[x] = find(parent[x]);
    }
    
    public void union(int a, int b) {
        int src = find(a);
        int dst = find(b);
        if(src != dst) {
            parent[src] = dst;
            count--;
        }
    }
    
    public int get_count() {
        return count;
    }
}


