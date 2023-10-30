/**
  Refer to
  https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/
  There are n cities numbered from 0 to n-1. Given the array edges where edges[i] = [fromi, toi, weighti] 
  represents a bidirectional and weighted edge between cities fromi and toi, and given the integer distanceThreshold.

Return the city with the smallest number of cities that are reachable through some path and whose distance 
is at most distanceThreshold, If there are multiple such cities, return the city with the greatest number.

Notice that the distance of a path connecting cities i and j is equal to the sum of the edges' weights along that path.

Example 1:
Input: n = 4, edges = [[0,1,3],[1,2,1],[1,3,4],[2,3,1]], distanceThreshold = 4
Output: 3
Explanation: The figure above describes the graph. 
The neighboring cities at a distanceThreshold = 4 for each city are:
City 0 -> [City 1, City 2] 
City 1 -> [City 0, City 2, City 3] 
City 2 -> [City 0, City 1, City 3] 
City 3 -> [City 1, City 2] 
Cities 0 and 3 have 2 neighboring cities at a distanceThreshold = 4, 
but we have to return city 3 since it has the greatest number.

Example 2:
Input: n = 5, edges = [[0,1,2],[0,4,8],[1,2,3],[1,4,2],[2,3,1],[3,4,1]], distanceThreshold = 2
Output: 0
Explanation: The figure above describes the graph. 
The neighboring cities at a distanceThreshold = 2 for each city are:
City 0 -> [City 1] 
City 1 -> [City 0, City 4] 
City 2 -> [City 3, City 4] 
City 3 -> [City 2, City 4]
City 4 -> [City 1, City 2, City 3] 
The city 0 has 1 neighboring city at a distanceThreshold = 2.
 
Constraints:
2 <= n <= 100
1 <= edges.length <= n * (n - 1) / 2
edges[i].length == 3
0 <= fromi < toi < n
1 <= weighti, distanceThreshold <= 10^4
All pairs (fromi, toi) are distinct.
*/

// Wrong Solution: DFS
// Refer to
// https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/discuss/490364/Why-cannot-I-use-DFS-for-this-problem
class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        Map<Integer, Map<Integer, Integer>> graph = new HashMap<Integer, Map<Integer, Integer>>();
        for(int[] edge : edges) {
            graph.putIfAbsent(edge[0], new HashMap<Integer, Integer>());
            graph.putIfAbsent(edge[1], new HashMap<Integer, Integer>());
            graph.get(edge[0]).put(edge[1], edge[2]);
            graph.get(edge[1]).put(edge[0], edge[2]);
        }
        int[] eachNodeNeighors = new int[n];
        // Initial as n since at most one node will have n - 1 neighbor nodes
        int min_neighbors = n;
        for(int i = 0; i < n; i++) {
            Set<Integer> visited = new HashSet<Integer>();
            helper(i, distanceThreshold, visited, graph);
            // Remove itself since it count self in DFS
            int neighbors = visited.size() - 1;
            eachNodeNeighors[i] = neighbors;
            if(neighbors < min_neighbors) {
                min_neighbors = neighbors;
            }
        }
        int result = 0;
        for(int i = 0; i < n; i++) {
            if(eachNodeNeighors[i] == min_neighbors) {
                result = i;
            }
        }
        return result;
    }
    
    private void helper(int currNode, int distance, Set<Integer> visited, Map<Integer, Map<Integer, Integer>> graph) {
        if(distance < 0) {
            return;
        }
        if(visited.contains(currNode)) {
            return;
        }
        visited.add(currNode);
        if(graph.containsKey(currNode)) {
            for(int next : graph.get(currNode).keySet()) {
                helper(next, distance - graph.get(currNode).get(next), visited, graph);
            }            
        }
    }
}

// The Reason of DFS Not Working (Explain Graph and Example)
// Refer to
// https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/discuss/490555/The-Reason-of-DFS-Not-Working-(Explain-Graph-and-Example)

// How to process it with DFS ?
// Refer to
// https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/discuss/490312/JavaC%2B%2BPython-Easy-Floyd-Algorithm
/**
 Just visit a node when processing it and unvisit it after done processing.
 This will allow updating a node's weight more than once without looping forever.
*/
// https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/discuss/538326/Simple-DFS-without-dijkstra
/**
 Return how many neighbor cities satisfy threashold
 (1) If this city is already visited, check the stored visited distance and current distance. If current distance is lower, ignore the current city and visit the current city's neighbours.
 (2) If true, Ignore the current city and visit the neighbours
 (3) If false, this the first time this city is visited. Add this city to the output.
*/
class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        Map<Integer, Map<Integer, Integer>> graph = new HashMap<Integer, Map<Integer, Integer>>();
        for(int[] edge : edges) {
            graph.putIfAbsent(edge[0], new HashMap<Integer, Integer>());
            graph.putIfAbsent(edge[1], new HashMap<Integer, Integer>());
            graph.get(edge[0]).put(edge[1], edge[2]);
            graph.get(edge[1]).put(edge[0], edge[2]);
        }
        // Initial as n since at most one node will have n - 1 neighbor nodes
        int min_neighbors = n;
        int[] counts = new int[n];
        for(int i = 0; i < n; i++) {
            Set<Integer> visited = new HashSet<Integer>();
            int[] storedVisitedDistance = new int[n];
            Arrays.fill(storedVisitedDistance, Integer.MAX_VALUE);
            helper(i, 0, distanceThreshold, visited, graph, storedVisitedDistance);
            for(int j = 0; j < n; j++) {
                counts[j] += storedVisitedDistance[j] != Integer.MAX_VALUE ? 1 : 0;
            }
        }
        int result = 0;
        for(int i = 0; i < n; i++) {
            if(counts[i] <= min_neighbors) {
                min_neighbors = counts[i];
                result = i;
            }
        }
        return result;
    }
    
    private void helper(int currNode, int currDistance, int distanceThreshold, Set<Integer> visited, Map<Integer, Map<Integer, Integer>> graph, int[] storedVisitedDistance) {
        // Only record smallest distance from source node to current node, if current path from source node to current
        // node has larger distance than stored one then ignore
        if(storedVisitedDistance[currNode] <= currDistance || currDistance > distanceThreshold) {
            return;
        }
        // Update distance from source node to current node
        storedVisitedDistance[currNode] = currDistance;
        if(graph.containsKey(currNode)) {
            for(int neighbor : graph.get(currNode).keySet()) {
                if(!visited.contains(neighbor)) {
                    // Add and then remove to allow re-visit the same node once again from other path
                    visited.add(neighbor);
                    helper(neighbor, currDistance + graph.get(currNode).get(neighbor), distanceThreshold, visited, graph, storedVisitedDistance);
                    visited.remove(neighbor);
                }
            }
        }
    }
}


// Solution 2: Dijkstra (BFS with PriorityQueue, MaxHeap)
// Refer to
// https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/discuss/490283/Java-PriorityQueue-%2B-BFS
/**
 Time Complexity: Dijkstra on one vertex is O(ElogV), so for all vertices is O(VElogV)
 The description says 1 <= edges.length <= n * (n - 1) / 2, so O(E) = O(V^2)
 Therefore, the final time complexity is O(V^3logV) which should be slower than Floyd Warshall (O(V^3))
*/

// https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/discuss/490586/Why-Dijkstra-over-BFS-Why-heap-over-FIFO
/**
 For this problem, why we have to use Dijkstra over BFS, that is, using heap over FIFO? 
 Knowing that bfs has better performance.
Because, we have to pop the shrotest distance first to include every loop. The reason is the 
visited node may block some countable path, if you dont do that.
For example,
[0, 2,5], [0,1,1], [1, 2, 1], [2,3,1].
If you count all the feasible nodes with distance 4 on node 0, you will be like to miss 3, 
since BFS will put neighbor 2 in visited list. then the feasible path [0, 1, 2, 3] with 
distance 3, will be early terminated at node [2], bacause it was visted.

During the contest, I was using BFS , which used a normal FIFO queue(see comented line), 
since every pop and poll only takes O(1), over Dijkstra, whe every pop and poll only takes O(lgk).
However, there was a corner case not passing:
5
[[0,1,2],[0,4,8],[1,2,3],[1,4,2],[2,3,1],[3,4,1]]
2

And build with maximum heap, not like NetworkDelayTime.java build with minimum heap and using Dijkstra
if using minimum heap, will fail with below test case
		int[][] edges = {{0,1,10}, {0,2,1}, {2,3,1}, {1,3,1}, {1,4,1}, {4,5,10}};
		int n = 6;
		int distanceThreshold = 20;
*/
class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        Map<Integer, Set<int[]>> g = new HashMap<>();
        for (int i = 0; i < n; i++) {
            g.put(i, new HashSet<>());
        }
        for (int[] e : edges) {
            if (e[2] > distanceThreshold) continue;
            g.get(e[0]).add(new int[]{e[1], e[2]});
            g.get(e[1]).add(new int[]{e[0], e[2]});
        }
        int min = n + 1;
        int res = -1;
        for (int i = 0; i < n; i++) {
            int count  = bfs(g, distanceThreshold, i);
            if (count <= min) {
                min = count;
                res = i;
            }
        }
        return res;
    }

    private int bfs(Map<Integer,Set<int[]>> g, int distanceThreshold, int i) {
        // Queue<int[]> q = new LinkedList<>(); 
        Queue<int[]> q = new PriorityQueue<>((a, b) -> (b[1] - a[1])); // have to be heap over FIFO, and it must be maximum heap
        q.add(new int[]{i, distanceThreshold});
        Set<Integer> visited = new HashSet<>();
        int count = -1;
        while (!q.isEmpty()) {
            int[] city = q.poll();
            if (visited.contains(city[0])) continue;
            visited.add(city[0]);
            count++;
            for (int[] neighbor : g.get(city[0])) {
                if (!visited.contains(neighbor[0]) && city[1] >= neighbor[1]) {
                    q.add(new int[]{neighbor[0], city[1] - neighbor[1]});
                }
            }
        }
        return count;
    }
}

























































https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/description/

There are n cities numbered from 0 to n-1. Given the array edges where edges[i] = [fromi, toi, weighti] represents a bidirectional and weighted edge between cities fromi and toi, and given the integer distanceThreshold.

Return the city with the smallest number of cities that are reachable through some path and whose distance is at most distanceThreshold, If there are multiple such cities, return the city with the greatest number.

Notice that the distance of a path connecting cities i and j is equal to the sum of the edges' weights along that path.

Example 1:


```
Input: n = 4, edges = [[0,1,3],[1,2,1],[1,3,4],[2,3,1]], distanceThreshold = 4
Output: 3
Explanation: The figure above describes the graph. 
The neighboring cities at a distanceThreshold = 4 for each city are:
City 0 -> [City 1, City 2] 
City 1 -> [City 0, City 2, City 3] 
City 2 -> [City 0, City 1, City 3] 
City 3 -> [City 1, City 2] 
Cities 0 and 3 have 2 neighboring cities at a distanceThreshold = 4, but we have to return city 3 since it has the greatest number.
```

Example 2:


```
Input: n = 5, edges = [[0,1,2],[0,4,8],[1,2,3],[1,4,2],[2,3,1],[3,4,1]], distanceThreshold = 2
Output: 0
Explanation: The figure above describes the graph. 
The neighboring cities at a distanceThreshold = 2 for each city are:
City 0 -> [City 1] 
City 1 -> [City 0, City 4] 
City 2 -> [City 3, City 4] 
City 3 -> [City 2, City 4]
City 4 -> [City 1, City 2, City 3] 
The city 0 has 1 neighboring city at a distanceThreshold = 2.
```

Constraints:
- 2 <= n <= 100
- 1 <= edges.length <= n * (n - 1) / 2
- edges[i].length == 3
- 0 <= fromi < toi < n
- 1 <= weighti, distanceThreshold <= 10^4
- All pairs (fromi, toi) are distinct.
---
Attempt 1: 2023-10-29

Solution 1: Dijkstra (120 min)

Style 1: MaxPQ
```
class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        Map<Integer, Set<int[]>> graph = new HashMap<>();
        for(int i = 0; i < n; i++) {
            graph.put(i, new HashSet<>());
        }
        for(int[] edge : edges) {
            if(edge[2] > distanceThreshold) {
                continue;
            }
            graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
            graph.get(edge[1]).add(new int[]{edge[0], edge[2]});
        }
        int min = n + 1;
        int result = -1;
        for(int i = 0; i < n; i++) {
            int count = bfs(i, graph, distanceThreshold);
            if(count <= min) {
                min = count;
                result = i;
            }
        }
        return result;
    }
 

    private int bfs(int i, Map<Integer, Set<int[]>> graph, int distanceThreshold) {
        int count = -1;
        Set<Integer> visited = new HashSet<>();
        Queue<int[]> maxPQ = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        maxPQ.offer(new int[]{i, distanceThreshold});
        while(!maxPQ.isEmpty()) {
            int[] cur = maxPQ.poll();
            if(visited.contains(cur[0])) {
                continue;
            }
            visited.add(cur[0]);
            count++;
            for(int[] neighbour : graph.get(cur[0])) {
                if(!visited.contains(neighbour[0]) && cur[1] >= neighbour[1]) {
                    maxPQ.offer(new int[]{neighbour[0], cur[1] - neighbour[1]});
                }
            }
        }
        return count;
    }
}


Dijkstra:
For each node A
Calculate the minimum distance from node A to every other node
Find how many nodes are reachable within the distanceThreshold
Output the last node with least reachability
Complexity 

|E| = (N * (N - 1)) / 2
|V| = N

Time:

Dijkstra has O(|E| * log(|V|)) and Dijkstra is applied to each node (i.e. |V| times).
O(|V| * |E| * log(|V|)) = O(N^3 * log(N))
Space:

O(|V| + |E|) for the graph adjacency list
O(|V|) for visited and distance arrays used by dijkstra, and O(N) for priority queue
O(|V| + |E|) = O(N^2)
```

---
The Reason of DFS Not Working (Explain Graph and Example)
Refer to
https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/solutions/490555/the-reason-of-dfs-not-working-explain-graph-and-example/
Many people solve this problem with a DFS algorithm and have a problem with some very big test cases. I found a short test case will fail the DFS solutions and explain the reason.

This test case will fail a DFS solution(I already contributed this test case):
```
6
{{0,1,1},{1,2,1},{2,3,1},{0,3,10},{3,4,1},{4,5,10}}
20
```
The graph looks like this, and The distanceThreshold = 20

If your DFS go though the 0->3 before the 0->1 the error will encounter. Note the visited list.

The next time your DFS goes 0->1 it will find the 3 already visited and the 5 will never visit. So that's why those case will have fewer city visit than expect.

By the way, this question is basiclly a trap. If you never heard of Floyd-Warshall algorithmor noticed those cases. A lot people will directly use DFS. This question's contest AC rate only: 0.259

在使用了max PriorityQueue以后下面我们测试以上给出的图例：
```
6
{{0,1,1},{1,2,1},{2,3,1},{0,3,10},{3,4,1},{4,5,10}}
20
```
我们可以发现基于BFS的Dijkstra可以成功回避从0 -> 3的路径错误路径选择，因为在每次首先选择当前节点到目标节点的最短路径最优先的情况下，当前节点0到目标节点3的最短路径是0 -> 1 -> 2 -> 3而非0 -> 3，而这一点可以通过引入PriorityQueue实现（根据题意这里选择了max PriorityQueue，因为比较的是基于给定的定值distanceThreshold，在走过每一段当前节点到目标节点的路径后还剩多少，剩的越多，当前路径消耗的越少，路径越优先，那么需要知道哪条路径剩的越多，我们可以用max PriorityQueue处理）
```
import java.util.*;

public class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        Map<Integer, Set<int[]>> g = new HashMap<>();
        for (int i = 0; i < n; i++) {
            g.put(i, new HashSet<>());
        }
        for (int[] e : edges) {
            if (e[2] > distanceThreshold) continue;
            g.get(e[0]).add(new int[]{e[1], e[2]});
            g.get(e[1]).add(new int[]{e[0], e[2]});
        }
        int min = n + 1;
        int res = -1;
        for (int i = 0; i < n; i++) {
            int count  = bfs(g, distanceThreshold, i);
            if (count <= min) {
                min = count;
                res = i;
            }
        }
        return res;
    }
 

    private int bfs(Map<Integer,Set<int[]>> g, int distanceThreshold, int i) {
        // Queue<int[]> q = new LinkedList<>();
        Queue<int[]> q = new PriorityQueue<>((a, b) -> (b[1] - a[1])); // have to be heap over FIFO, and it must be maximum heap
        q.add(new int[]{i, distanceThreshold});
        Set<Integer> visited = new HashSet<>();
        int count = -1;
        while (!q.isEmpty()) {
            int[] city = q.poll();
            if (visited.contains(city[0])) continue;
            visited.add(city[0]);
            count++;
            for (int[] neighbor : g.get(city[0])) {
                if (!visited.contains(neighbor[0]) && city[1] >= neighbor[1]) {
                    q.add(new int[]{neighbor[0], city[1] - neighbor[1]});
                }
            }
        }
        return count;
    }
 
    public static void main(String[] args) {

        Solution so = new Solution();
        int[][] edges = new int[][]{{0,1,1},{1,2,1},{2,3,1},{0,3,10},{3,4,1},{4,5,10}};
        int result = so.findTheCity(6, edges, 20);
        System.out.println(result);
    }
}
```

Refer to
https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/solutions/490586/why-dijkstra-over-bfs-why-heap-over-fifo/
Idea:
1. Record the edges and weights by map
2. Use Dijkstra algorithm to traverse every vertex
```
class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        Map<Integer, Map<Integer, Integer>> g = new HashMap<>();
        for (int i = 0; i < n; i++) {
            g.put(i, new HashMap<>());
        }
        for (int[] e : edges) {
            g.get(e[0]).put(e[1], e[2]);
            g.get(e[1]).put(e[0], e[2]);
        }
        int min = n + 1;
        int res = -1;
        for (int i = 0; i < n; i++) {
            Queue<int[]> q = new PriorityQueue<>((a, b)->(b[1] - a[1]));
            q.add(new int[]{i, distanceThreshold});
            Set<Integer> visited = new HashSet<>();
            int count = 0;
            while (!q.isEmpty()) {
                int[] city = q.poll();
                if (!visited.contains(city[0])) {
                    visited.add(city[0]);
                    count++;
                } else {
                    continue;
                }
                Map<Integer, Integer> m = g.get(city[0]);
                for (int neighbor : m.keySet()) {
                    if (!visited.contains(neighbor) && city[1] >= m.get(neighbor)) {
                        q.add(new int[]{neighbor, city[1] - m.get(neighbor)});
                    }
                }
            }
            if (count - 1 <= min) {
                min = count - 1;
                res = i;
            }
        }
        return res;
    }
}
```

Style 2: MinPQ
稍微改动一下MaxPQ就可以变成MinPQ的写法
```
class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        Map<Integer, Set<int[]>> graph = new HashMap<>();
        for(int i = 0; i < n; i++) {
            graph.put(i, new HashSet<>());
        }
        for(int[] edge : edges) {
            if(edge[2] > distanceThreshold) {
                continue;
            }
            graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
            graph.get(edge[1]).add(new int[]{edge[0], edge[2]});
        }
        int min = n + 1;
        int result = -1;
        for(int i = 0; i < n; i++) {
            int count = bfs(i, graph, distanceThreshold);
            if(count <= min) {
                min = count;
                result = i;
            }
        }
        return result;
    }
    private int bfs(int i, Map<Integer, Set<int[]>> graph, int distanceThreshold) {
        int count = -1;
        Set<Integer> visited = new HashSet<>();
        Queue<int[]> minPQ = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        minPQ.offer(new int[]{i, 0});
        while(!minPQ.isEmpty()) {
            int[] cur = minPQ.poll();
            if(visited.contains(cur[0])) {
                continue;
            }
            visited.add(cur[0]);
            count++;
            for(int[] neighbour : graph.get(cur[0])) {
                if(!visited.contains(neighbour[0]) && cur[1] + neighbour[1] <= distanceThreshold) {
                    minPQ.offer(new int[]{neighbour[0], cur[1] + neighbour[1]});
                }
            }
        }
        return count;
    }
}

Dijkstra:
For each node A
Calculate the minimum distance from node A to every other node
Find how many nodes are reachable within the distanceThreshold
Output the last node with least reachability
Complexity 

|E| = (N * (N - 1)) / 2
|V| = N

Time:

Dijkstra has O(|E| * log(|V|)) and Dijkstra is applied to each node (i.e. |V| times).
O(|V| * |E| * log(|V|)) = O(N^3 * log(N))
Space:

O(|V| + |E|) for the graph adjacency list
O(|V|) for visited and distance arrays used by dijkstra, and O(N) for priority queue
O(|V| + |E|) = O(N^2)
```
