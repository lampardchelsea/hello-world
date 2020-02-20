/**
 Refer to
 https://code.dennyzhang.com/connecting-cities-with-minimum-cost
 https://leetcode.com/discuss/interview-question/356981

 
 There are N cities numbered from 1 to N.

 You are given connections, where each connections[i] = [city1, city2, cost] represents the cost to connect 
 city1 and city2 together. (A connection is bidirectional: connecting city1 and city2 is the same as connecting 
 city2 and city1.)

 Return the minimum cost so that for every pair of cities, there exists a path of connections (possibly of 
 length 1) that connects those two cities together. The cost is the sum of the connection costs used. If the 
 task is impossible, return -1.

 Example 1:
 Connecting Cities With Minimum Cost
 Input: N = 3, connections = [[1,2,5],[1,3,6],[2,3,1]]
 Output: 6
 Explanation: 
 Choosing any 2 edges will connect all cities so we choose the minimum 2.

 Example 2:
 Connecting Cities With Minimum Cost
 Input: N = 4, connections = [[1,2,3],[3,4,4]]
 Output: -1
 Explanation: 
 There is no way to connect all cities even if all edges are used.
 
 Note:
 1 <= N <= 10000
 1 <= connections.length <= 10000
 1 <= connections[i][0], connections[i][1] <= N
 0 <= connections[i][2] <= 10^5
 connections[i][0] != connections[i][1]
*/

// Solution 1: Union Find (based on two different algorithm Prim and Kruskal)  
// Refer to
// https://www.quora.com/What-is-the-difference-in-Kruskals-and-Prims-algorithm
/**
 The basic difference is in which edge you choose to add next to the spanning tree in each step.

In Prim's, you always keep a connected component, starting with a single vertex. You look at all 
edges from the current component to other vertices and find the smallest among them. You then add 
the neighbouring vertex to the component, increasing its size by 1. In N-1 steps, every vertex 
would be merged to the current one if we have a connected graph.

In Kruskal's, you do not keep one connected component but a forest. At each stage, you look at the 
globally smallest edge that does not create a cycle in the current forest. Such an edge has to 
necessarily merge two trees in the current forest into one. Since you start with N single-vertex trees, 
in N-1 steps, they would all have merged into one if the graph was connected.
*/

// https://blog.csdn.net/fuxuemingzhu/article/details/101214765
/**
 题目大意
想象一下你是个城市基建规划者，地图上有 N 座城市，它们按以 1 到 N 的次序编号。
给你一些可连接的选项 conections，其中每个选项 conections[i] = [city1, city2, cost] 表示将城市 city1 和城市 city2 
连接所要的成本。（连接是双向的，也就是说城市 city1 和城市 city2 相连也同样意味着城市 city2 和城市 city1 相连）。
返回使得每对城市间都存在将它们连接在一起的连通路径（可能长度为 1 的）最小成本。该最小成本应该是所用全部连接代价的综合。
如果根据已知条件无法完成该项任务，则请你返回 -1。

解题方法
Kruskal算法
本题是标准的最小生成树问题，有Prim和Kruskal算法两个解法。

MST（Minimum Spanning Tree，最小生成树）问题有两种通用的解法，Prim算法就是其中之一，它是从点的方面考虑构建一颗MST，
大致思想是：设图G顶点集合为U，首先任意选择图G中的一点作为起始点a，将该点加入集合V，再从集合U-V中找到另一点b使得点b到V中
任意一点的权值最小，此时将b点也加入集合V；以此类推，现在的集合V={a，b}，再从集合U-V中找到另一点c使得点c到V中任意一点的
权值最小，此时将c点加入集合V，直至所有顶点全部被加入V，此时就构建出了一颗MST。因为有N个顶点，所以该MST就有N-1条边，
每一次向集合V中加入一个点，就意味着找到一条MST的边。

Kruskal算法是基于贪心的思想得到的。首先我们把所有的边按照权值先从小到大排列，接着按照顺序选取每条边，如果这条边的两个端点
不属于同一集合，那么就将它们合并，直到所有的点都属于同一个集合为止。至于怎么合并到一个集合，那么这里我们就可以用到一个工具
——-并查集。换而言之，Kruskal算法就是基于并查集的贪心算法。
*/


// Prim Algorithm
// https://blog.csdn.net/fuxuemingzhu/article/details/101214765
import java.util.Arrays;

public class Solution {
    public static int minimumCost(int N, int[][] connections) {
        int result = 0;
        // Sort cost by ascending order
        Arrays.sort(connections, (a, b) - > a[2] - b[2]);
        UnionFind uf = new UnionFind(N);
        for (int i = 0; i < connections.length; i++) {
            int[] connection = connections[i];
            // Decrease 1 in case of array index start from 0 but city number start from 1
            // Mapping index to number is important
            int a = connection[0] - 1;
            int b = connection[1] - 1;
            int cost = connection[2];
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
    
    public static void main(String[] args) {
        int N = 3;
        int[][] connections = {{1, 2, 5}, {1, 3, 6}, {2, 3, 1}};
        //int N = 4;
        //int[][] connections = {{1, 2, 3}, {3, 4, 4}};
        int result = minimumCost(N, connections);
        System.out.println(result);
    }
}

class UnionFind {
    private int[] parent;
    private int count;
    public UnionFind(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        count = n;
    }

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

// https://leetcode.com/discuss/interview-question/356981/Amazon-or-OA-2019-or-Min-Cost-to-Connect-All-Nodes/347846



// Kruskal Algorithm
// https://www.cnblogs.com/Dylan-Java-NYC/p/11280623.html

// https://code.dennyzhang.com/connecting-cities-with-minimum-cost
