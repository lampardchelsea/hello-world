/**
 Refer to
 https://leetcode.com/discuss/interview-question/356981
 Given an undirected graph with n nodes labeled 1..n. Some of the nodes are already connected. The i-th edge connects 
 nodes edges[i][0] and edges[i][1] together. Your task is to augment this set of edges with additional edges to connect 
 all the nodes. Find the minimum cost to add new edges between the nodes such that all the nodes are accessible from each other.

Input:
n, an int representing the total number of nodes.
edges, a list of integer pair representing the nodes already connected by an edge.
newEdges, a list where each element is a triplet representing the pair of nodes between which an edge can be added and 
the cost of addition, respectively (e.g. [1, 2, 5] means to add an edge between node 1 and 2, the cost would be 5).

Example 1:
Input: n = 6, edges = [[1, 4], [4, 5], [2, 3]], newEdges = [[1, 2, 5], [1, 3, 10], [1, 6, 2], [5, 6, 5]]
Output: 7
Explanation:
There are 3 connected components [1, 4, 5], [2, 3] and [6].
We can connect these components into a single component by connecting node 1 to node 2 and node 1 to node 6 at a minimum cost of 5 + 2 = 7.
Solution

Related problems:
Min Cost to Repair Edges
https://leetcode.com/problems/connecting-cities-with-minimum-cost (premium)
*/

// Solution 1:
// Refer to
// Prime style
// https://leetcode.com/discuss/interview-question/356981/Amazon-or-OA-2019-or-Min-Cost-to-Connect-All-Nodes/323252
import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        int n = 6;
        //int[][] edges = {{1, 4}, {4, 5}, {2, 3}};
        //int[][] newEdges = {{1, 2, 5}, {1, 3, 10}, {1, 6, 2}, {5, 6, 5}};
        int result = minCosttoConnectAllNodes(n, edges, newEdges);
        System.out.println(result);
    }

    public static int minCosttoConnectAllNodes(int n, int[][] edges, int[][] newEdges) {
        int result = 0;
        // Sort cost by ascending order
        Arrays.sort(newEdges, (a, b) - > a[2] - b[2]);
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            int a = edge[0] - 1;
            int b = edge[1] - 1;
            if (uf.find(a) != uf.find(b)) {
                uf.union(a, b);
            }
        }

        for (int i = 0; i < newEdges.length; i++) {
            int[] newEdge = newEdges[i];
            // Decrease 1 in case of array index start from 0 but city number start from 1
            // Mapping index to number is important
            int a = newEdge[0] - 1;
            int b = newEdge[1] - 1;
            int cost = newEdge[2];
            if (uf.find(a) != uf.find(b)) {
                uf.union(a, b);
                result += cost;
            }
            if (uf.get_count() == 1) {
                return result;
            }
        }
        return -1;
    }
}

class UnionFind {
    private int[] parent;
    // Adding one more array as size to monitor each component size
    private int[] size;
    private int count;
    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = i;
        }
        count = n;
    }

    // Kruskal different than Prim part is happening on union strategy
    public void union(int a, int b) {
        int src = find(a);
        int dst = find(b);
        if (src != dst) {
            parent[src] = dst;
            count--;
        }
    }

    public int find(int x) {
        if (parent[x] == x) {
            return x;
        }
        return parent[x] = find(parent[x]);
    }

    public int get_count() {
        return count;
    }
}


// Kruskal style
// https://leetcode.com/discuss/interview-question/356981/Amazon-or-OA-2019-or-Min-Cost-to-Connect-All-Nodes/347846
import java.util.Arrays;

public class Solution {
    public static int minCosttoConnectAllNodes(int n, int[][] edges, int[][] newEdges) {
        int result = 0;
        // Sort cost by ascending order
        Arrays.sort(newEdges, (a, b) - > a[2] - b[2]);
        UnionFind uf = new UnionFind(n);
        
        // Union the already existing nodes on edges, the count will decreasing
        // when invoke uf.union method
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            int a = edge[0] - 1;
            int b = edge[1] - 1;
            if (uf.find(a) != uf.find(b)) {
                uf.union(a, b);
            }
        }

        for (int i = 0; i < newEdges.length; i++) {
            int[] newEdge = newEdges[i];
            // Decrease 1 in case of array index start from 0 but city number start from 1
            // Mapping index to number is important
            int a = newEdge[0] - 1;
            int b = newEdge[1] - 1;
            int cost = newEdge[2];
            if (uf.find(a) != uf.find(b)) {
                uf.union(a, b);
                result += cost;
            }
            if (uf.get_count() == 1) {
                return result;
            }
        }
        return -1;
    }
}

class UnionFind {
    private int[] parent;
    // Adding one more array as size to monitor each component size
    private int[] size;
    private int count;
    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = i;
        }
        count = n;
    }

    // Kruskal different than Prim part is happening on union strategy
    public void union(int a, int b) {
        int src = find(a);
        int dst = find(b);
        if (size[src] > size[dst]) {
            parent[dst] = src;
            size[src] += size[dst];
        } else {
            parent[src] = dst;
            size[dst] += size[src];
        }
        count--;
    }

    public int find(int x) {
        if (parent[x] == x) {
            return x;
        }
        return parent[x] = find(parent[x]);
    }

    public int get_count() {
        return count;
    }
    
    public static void main(String[] args) {
        int n = 6;
        int[][] edges = {{1, 4}, {4, 5}, {2, 3}};
        int[][] newEdges = {{1, 2, 5}, {1, 3, 10}, {1, 6, 2}, {5, 6, 5}};
        int result = minCosttoConnectAllNodes(n, edges, newEdges);
        System.out.println(result);
    }
}
