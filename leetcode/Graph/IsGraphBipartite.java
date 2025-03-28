/**
 Refer to
 https://leetcode.com/problems/is-graph-bipartite/
 Given an undirected graph, return true if and only if it is bipartite.

Recall that a graph is bipartite if we can split it's set of nodes into two independent subsets A and B 
such that every edge in the graph has one node in A and another node in B.

The graph is given in the following form: graph[i] is a list of indexes j for which the edge between nodes 
i and j exists.  Each node is an integer between 0 and graph.length - 1.  There are no self edges or parallel 
edges: graph[i] does not contain i, and it doesn't contain any element twice.

Example 1:
Input: [[1,3], [0,2], [1,3], [0,2]]
Output: true
Explanation: 
The graph looks like this:
0----1
|    |
|    |
3----2
We can divide the vertices into two groups: {0, 2} and {1, 3}.

Example 2:
Input: [[1,2,3], [0,2], [0,1,3], [0,2]]
Output: false
Explanation: 
The graph looks like this:
0----1
| \  |
|  \ |
3----2
We cannot find a way to divide the set of nodes into two independent subsets.
 
Note:
graph will have length in range [1, 100].
graph[i] will contain integers in range [0, graph.length - 1].
graph[i] will not contain i or duplicate values.
The graph is undirected: if any element j is in graph[i], then i will be in graph[j].
*/

// Solution 1: DFS
// Refer to
// https://leetcode.com/problems/is-graph-bipartite/discuss/115487/Java-Clean-DFS-solution-with-Explanation
/**
 Our goal is trying to use two colors to color the graph and see if there are any adjacent nodes having the same color.
Initialize a color[] array for each node. Here are three states for colors[] array:
0: Haven't been colored yet.
1: Blue.
-1: Red.
For each node,
If it hasn't been colored, use a color to color it. Then use the other color to color all its adjacent nodes (DFS).
If it has been colored, check if the current color is the same as the color that is going to be used to color it. 
(Please forgive my english... Hope you can understand it.)
*/

// https://www.cnblogs.com/grandyang/p/8519566.html
/**
 原来输入数组中的 graph[i]，表示顶点i所有相邻的顶点，比如对于例子1来说，顶点0和顶点1，3相连，顶点1和顶点0，2相连，
 顶点2和结点1，3相连，顶点3和顶点0，2相连。这道题让我们验证给定的图是否是二分图，所谓二分图，就是可以将图中的所有
 顶点分成两个不相交的集合，使得同一个集合的顶点不相连。为了验证是否有这样的两个不相交的集合存在，我们采用一种很机智
 的染色法，大体上的思路是要将相连的两个顶点染成不同的颜色，一旦在染的过程中发现有两连的两个顶点已经被染成相同的颜色，
 说明不是二分图。这里我们使用两种颜色，分别用1和 -1 来表示，初始时每个顶点用0表示未染色，然后遍历每一个顶点，如果该
 顶点未被访问过，则调用递归函数，如果返回 false，那么说明不是二分图，则直接返回 false。如果循环退出后没有返回 false，
 则返回 true。在递归函数中，如果当前顶点已经染色，如果该顶点的颜色和将要染的颜色相同，则返回 true，否则返回 false。
 如果没被染色，则将当前顶点染色，然后再遍历与该顶点相连的所有的顶点，调用递归函数，如果返回 false 了，则当前递归函数
 的返回 false，循环结束返回 true
*/

// https://leetcode.com/problems/is-graph-bipartite/discuss/115492/JAVA-DFS-two-colorability-O(N)%3A-easy-clean-illustrated-informally-proved
/**
 Try to color all nodes with alternating colors. If we came back to an already-colored node with a different color, 
 then we return false. This is a typical application of DFS/BFS cycle detection described in Robert Sedgwick's 
 Algorithms (4th ed.) book.
 
 Slight explanation of correctness: why is it that we can be sure that as long as we have a cycle, where we try 
 to color one node with a different color the second time, we know for sure the graph is bad? This is because 
 the node just prior to the conflicting node must have the same color as the conflicting node itself. And as 
 long as such a cycle exists, there is no way you can bipartite-partition all nodes within this cycle because, 
 well, these two just won't do. What if there are no cycles? Well, cycle-free graphs are always bipartite.
 
 Multiple DFS is required since the nodes might not be fully connected. Each DFS will color one independent subgraph. 
 If one DFS discovers that one subgraph is okay, we can safely move on: it does not matter anymore because this 
 subgraph and its consisting nodes won't further affect any other independent subgraph.
*/

class Solution {
    public boolean isBipartite(int[][] graph) {
        int[] colors = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            // If node not paint on any color yet, but not able to paint
            // any color further is not a bipartite graph
            // Start paint with color = 1
            if (colors[i] == 0 && !helper(i, graph, colors, 1)) {
                return false;
            }
        }
        return true;
    }

    private boolean helper(int node, int[][] graph, int[] colors, int color) {
        // If node painted, check if it match the color try to paint on it
        // if not match means not bipartite graph
        if (colors[node] != 0) {
            return colors[node] == color;
        }
        colors[node] = color;
        for (int next: graph[node]) {
            // The adjacent nodes paint with different color as -1
            if (!helper(next, graph, colors, -color)) {
                return false;
            }
        }
        return true;
    }
}

// Solution 2: BFS
// Refer to
// https://leetcode.com/problems/is-graph-bipartite/discuss/115487/Java-Clean-DFS-solution-with-Explanation
// https://www.cnblogs.com/grandyang/p/8519566.html
/**
 我们再来看一种迭代的解法，整体思路还是一样的，还是遍历整个顶点，如果未被染色，则先染色为1，然后使用 BFS 进行遍历，
 将当前顶点放入队列 queue 中，然后 while 循环 queue 不为空，取出队首元素，遍历其所有相邻的顶点，如果相邻顶点未被染色，
 则染成和当前顶点相反的颜色，然后把相邻顶点加入 queue 中，否则如果当前顶点和相邻顶点颜色相同，直接返回 false，
 循环退出后返回 true
*/
class Solution {
    public boolean isBipartite(int[][] graph) {
        int[] colors = new int[graph.length];
        for(int i = 0; i < graph.length; i++) {
            // For each un-painted node start a BFS (similar like for
            // each un-painted node start a DFS)
            if(colors[i] == 0) {
                Queue<Integer> queue = new LinkedList<Integer>();
                queue.offer(i);
                colors[i] = 1;
                while(!queue.isEmpty()) {
                    int cur = queue.poll();
                    for(int next : graph[cur]) {
                        // If adjacent node of current node not painted
                        // paint it with different color than current node
                        // e.g adjacent node is 0, current node is 1, then
                        // paint adjacent node as -1
                        if(colors[next] == 0) {
                            queue.offer(next);
                            colors[next] = -colors[cur];
                        // If adjacent node already painted, but the color
                        // same as current node, then circle happen, return
                        // false, colors[next] == colors[cur] and
                        // colors[next] != -colors[cur] both works
                        } else if(colors[next] == colors[cur]) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}

