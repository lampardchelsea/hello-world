/**
 Refer to
 https://github.com/azl397985856/leetcode/blob/master/problems/1168.optimize-water-distribution-in-a-village-en.md
 There are n houses in a village. We want to supply water for all the houses by building wells and laying pipes.

For each house i, we can either build a well inside it directly with cost wells[i], or pipe in water from 
another well to it. The costs to lay pipes between houses are given by the array pipes, where each 
pipes[i] = [house1, house2, cost] represents the cost to connect house1 and house2 together using a pipe. 
Connections are bidirectional.

Find the minimum total cost to supply water to all houses.

Example 1:
Input: n = 3, wells = [1,2,2], pipes = [[1,2,1],[2,3,1]]
Output: 3
Explanation: 
The image shows the costs of connecting houses using pipes.
The best strategy is to build a well in the first house with cost 1 and connect the other houses to it with 
cost 2 so the total cost is 3.

Constraints:
1 <= n <= 10000
wells.length == n
0 <= wells[i] <= 10^5
1 <= pipes.length <= 10000
1 <= pipes[i][0], pipes[i][1] <= n
0 <= pipes[i][2] <= 10^5
pipes[i][0] != pipes[i][1]
*/

// Solution 1: Kruskal (Minimum Spanning Tree) algorithm
// Refer to
// https://github.com/azl397985856/leetcode/blob/master/problems/1168.optimize-water-distribution-in-a-village-en.md
/**
 From example graph, we can see that this is Shortest path problem/Minimum spanning tree problem. In this problem, 
 in a graph, view cities as nodes, pipe connects two cities as edges with cost. here, wells costs, it is self 
 connected edge, we can add extra node as root node 0, and connect all 0 and i with costs wells[i]. So that we 
 can have one graph/tree, and how to get minimun spanning trees / shortest path problem in a graph. Please see 
 below detailed steps for analysis.

Analysis Steps：
1.Create POJO EdgeCost(node1, node2, cost) - node1, node2, and cost of connect node1 and node2
2.Assume on root node 0，build graph with node 0 and all nodes(cities)
3.Connect all nodes with0，[0,i] - i is nodes range from [1,n]，0-1 meaning node 0 and node 1 connect edge ，value 
is node i's cost wells[i];
4.Turn cities into nodes, wells' costs and pipes' into costs into edges value which connected into two cities.
5.Sort all edges (from min to max)
6.Scan all edges, check whether 2 nodes connected or not:（Union-Find），
if already connected, continue check next edge
if not yet connected, +costs, connect 2 nodes
7.If all nodes already connected, get minimum costs, return result
8.(#7 Optimization) for each union, total nodes number n-1, if n==0, then meaning all nodes already connected, 
can terminate early.
Here use weighted-Union-find to check whether 2 nodes connceted or not, and union not connected nodes.
*/

// LeetCode 1168. Optimize Water Distribution in a Village 中文解释 Chinese Version
// https://www.youtube.com/watch?v=JxaO5n_530c
// Using minimum heap to implement 

// http://bookshadow.com/weblog/2019/08/25/leetcode-optimize-water-distribution-in-a-village/
/**
 题目大意：
 有n个村庄，给定数组wells和数组pipies。wells[i]表示在第i个村庄打井的花费，pipes的每个元素三元组(i, j, k) 
 表示村庄i到j铺设管道的花费为k。管道为双向连通。求让每个村庄都有水的最小花费。

 解题思路：
 Kruskal（最小生成树）
 Kruskal的基本思路是贪心（Greedy）。利用边集求最小生成树。首先对边集edges排序，然后遍历edges，利用并查集
 (Union-Find Set)对边的端点进行合并。若两端点不在同一个集合中，则将两端点进行merge，并记录权重。
 本题在应用Kruskal之前需要稍作转换：构建虚拟节点n+1，将wells数组转化为每一个村庄1~n与n+1的边。
*/

// Detect Cycle using Union Find ?
// https://www.youtube.com/watch?time_continue=162&v=mHz-mx-8lJ8&feature=emb_logo
// For each edge, make subsets using both the vertices of the edge, if both of the vertices are in the same subsets, then a cycle find.

// Kruskal template
// https://www.cs.princeton.edu/courses/archive/spring13/cos423/lectures/04GreedyAlgorithmsII.pdf
/**
 KRUSKAL (V, E, c) 
 SORT m edges by weight so that c(e1) ≤ c(e2) ≤ … ≤ c(em)
 S ← φ
 FOREACH v ∈ V: MAKESET(v).
 FOR i = 1 TO m
   (u, v) ← ei
   IF FINDSET(u) ≠ FINDSET(v) <- are u and v in same component?
     S ← S ∪ { ei }
     UNION(u, v) <- make u and v in same component
 RETURN S
*/

class Solution {
    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        List < EdgeCost > costs = new ArrayList < EdgeCost > ();
        for (int i = 1; i <= n; i++) {
            costs.add(new EdgeCost(0, i, wells[i - 1]));
        }
        for (int[] pipe: pipes) {
            costs.add(new EdgeCost(pipe[0], pipe[1], pipe[2]));
        }
        Collections.sort(costs);
        int minCosts = 0;
        UnionFind uf = new UnionFind(n);
        for (EdgeCost edge: costs) {
            int root1 = uf.find(edge.node1);
            int root2 = uf.find(edge.node2);
            // If same root means two nodes in same subset,
            // if connect them will create cycle, so ignore
            if (root1 == root2) {
                continue;
            }
            minCosts += edge.cost;
            uf.union(edge.node1, edge.node2);
            // For each union we connect one node
            n--;
            // If all nodes already connected, terminate early
            if (n == 0) {
                return minCosts;
            }
        }
        return minCosts;
    }

    /**
	public static void main(String[] args) {
		Solution s = new Solution();
		//int[][] edges = {{0,1,3}, {1,2,1}, {1,3,4}, {2,3,1}};
		//int[][] edges = {{0,2,5}, {0,1,1}, {1,2,1}, {2,3,1}};
		//int n = 4;
		//int distanceThreshold = 4;
		int[] wells = {1,2,2,3,2};
		int[][] pipes = {{1,2,1}, {2,3,1}, {4,5,7}};
		int n = 5;
		int result = s.minCostToSupplyWater(n, wells, pipes);
		System.out.println(result);
	}
    */
}

class EdgeCost implements Comparable < EdgeCost > {
    int node1;
    int node2;
    int cost;

    public EdgeCost(int node1, int node2, int cost) {
        this.node1 = node1;
        this.node2 = node2;
        this.cost = cost;
    }

    @Override
    public int compareTo(EdgeCost o) {
        return this.cost - o.cost;
    }

}

class UnionFind {
    int[] parent;
    int count;

    public UnionFind(int n) {
        parent = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
        }
        count = n + 1;
    }

    public int find(int x) {
        if (parent[x] == x) {
            return x;
        }
        return parent[x] = find(parent[x]);
    }

    public void union(int a, int b) {
        int src = find(a);
        int dst = find(b);
        if (src != dst) {
            parent[src] = dst;
            count--;
        }
    }

    public int get_count() {
        return count;
    }

}


