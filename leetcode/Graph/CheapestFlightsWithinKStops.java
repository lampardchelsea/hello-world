/**
 Refer to
 https://leetcode.com/problems/cheapest-flights-within-k-stops/
 There are n cities connected by m flights. Each flight starts from city u and arrives at v with a price w.

Now given all the cities and flights, together with starting city src and the destination dst, your task is 
to find the cheapest price from src to dst with up to k stops. If there is no such route, output -1.

Example 1:
Input: 
n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
src = 0, dst = 2, k = 1
Output: 200
Explanation: 
The graph looks like this:
            0
          /   \
       100    500
       /         \
     1 --- 100 --- 2
The cheapest price from city 0 to city 2 with at most 1 stop costs 200, as marked red in the picture.

Example 2:
Input: 
n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
src = 0, dst = 2, k = 0
Output: 500
Explanation: 
The graph looks like this:
            0
          /   \
       100    500
       /         \
     1 --- 100 --- 2
The cheapest price from city 0 to city 2 with at most 0 stop costs 500, as marked blue in the picture.

Note:
The number of nodes n will be in range [1, 100], with nodes labeled from 0 to n - 1.
The size of flights will be in range [0, n * (n - 1) / 2].
The format of each flight will be (src, dst, price).
The price of each flight will be in the range [1, 10000].
k is in the range of [0, n - 1].
There will not be any duplicated flights or self cycles.
*/

// Solution 1: Looks like Dijkstra but update condition changed as actually PriorityQueue solution only
// Refer to
// https://leetcode.com/problems/cheapest-flights-within-k-stops/discuss/128776/5-ms-AC-Java-Solution-based-on-Dijkstra's-Algorithm
/**
 The key point for this problem using Dijkstra's Algorithm is to have two different weights on each edge, 
 because if only consider cost, intermediate cases with higher cost but fewer stops which can further become 
 the shortest valid solution will be discarded.
 I use a minHeap as the conventional Dijkstra's Algorithm does. The difference is that conventional Dijkstra's 
 Algorithm would remove higher cost with fewer stops cases from heap and only offer cases with lower cost into 
 heap, but I keep all valid intermediate cases in heap and offer higher cost but fewer stops cases into heap as well.
*/

// Update condition change point 1: Why we not update global costs array with int newCost = Math.min(costs[to], currCost + cost) ?
// Refer to
// https://leetcode.com/problems/cheapest-flights-within-k-stops/discuss/115541/JavaPython-Priority-Queue-Solution/222415
/**
The key difference with the classic Dijkstra algo is, we don't maintain the global optimal distance 
to each node, i.e. ignore below optimization:
alt ← dist[u] + length(u, v)
if alt < dist[v]:
Because there could be routes which their length is shorter but pass more stops, and those routes don't 
necessarily constitute the best route in the end. To deal with this, rather than maintain the optimal 
routes with 0..K stops for each node, the solution simply put all possible routes into the priority queue, 
so that all of them has a chance to be processed. IMO, this is the most brilliant part.
And the solution simply returns the first qualified route, it's easy to prove this must be the best route.
*/

// Refer to
// https://leetcode.com/problems/cheapest-flights-within-k-stops/discuss/115541/JavaPython-Priority-Queue-Solution/301827
// This is a pretty big modification of Dijkstras
// As a recap -
// Djikstras shortest path
// - Take priority queue, and put source in it
//   Take dist[] array, and init dist[src], dist[others] = +infinity
//      while queue
//          pop u
//          for each neighbor v of u
//              if dist[v] < cost from u->v + dist[u]
//                  dist[v] is updated to cost u->v + dist[u]
//                  push v onto q
// For proper Dijkstras, check LCM743 Network time delay
// Reason this problem is solved differently, is that usually for Dijkstra's,
// we have the relaxation step i.e dist[v] > dist[u] + cost u->v, then
// dist[v] is updated. This ensures that dist to v is shortest found so far.
// For this problem, we also need to consider number of stops needed to
// reach this v. If we exceed the number of stops while reaching v, then this
// shortest path is useless to us. So we don't need to just go one node back,
// but our whole path may be wrong, because we maintained path simply on basis
// of dist....
// So for this problem, we put priorityQueue<int[3]> where int[] = vertex, cost, stops
// We skip the relaxation step, and for each neighbor v of u, we add all of them
// to the Priority queue.

// Update condition change point 2: Why we also need to update when if(newStop < stops[to]) ?
// Refer to
// https://leetcode.com/problems/cheapest-flights-within-k-stops/discuss/115541/JavaPython-Priority-Queue-Solution/156055
/**
 Unlike Dijkstra which update only if the distance is shorter, a node should be updated here if (i) distance is shorter (ii) less number of moves
*/
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        Map<Integer, List<int[]>> graph = new HashMap<Integer, List<int[]>>();
        for(int i = 0; i < n; i++) {
            graph.put(i, new ArrayList<int[]>());
        }
        for(int[] flight : flights) {
            graph.get(flight[0]).add(new int[] {flight[1], flight[2]});
        }
        PriorityQueue<int[]> minPQ = new PriorityQueue<int[]>((a, b) -> Integer.compare(a[1], b[1]));
        // new int[] {node, distance, stops}
        minPQ.offer(new int[] {src, 0, 0});
        int[] costs = new int[n];
        Arrays.fill(costs, Integer.MAX_VALUE);
        costs[src] = 0;
        int[] stops = new int[n];
        Arrays.fill(stops, Integer.MAX_VALUE);
        stops[src] = 0;
        while(!minPQ.isEmpty()) {
            int[] curr = minPQ.poll();
            int currNode = curr[0];
            int currCost = curr[1];
            int currStop = curr[2];
            if(currNode == dst) {
                return currCost;
            }
            if(currStop == K + 1) {
                continue;
            }
            for(int[] neighbor : graph.get(currNode)) {
                int to = neighbor[0];
                int cost = neighbor[1];
                // Update condition change point 1
                //int newCost = Math.min(costs[to], currCost + cost);
                int newCost = currCost + cost;
                int newStop = currStop + 1;
                if(newCost < costs[to]) {
                    minPQ.offer(new int[] {to, newCost, newStop});
                    costs[to] = newCost;
                }
                // Update condition change point 2
                // Refer to
                // https://leetcode.com/problems/cheapest-flights-within-k-stops/discuss/115541/JavaPython-Priority-Queue-Solution/156055
                if(newStop < stops[to]) {
                    minPQ.offer(new int[] {to, newCost, newStop});
                    stops[to] = newStop;
                }
            }
        }
        return costs[dst] == Integer.MAX_VALUE ? -1 : costs[dst];
    }
}

// Solution 2: PriorityQueue
// Refer to
// https://leetcode.com/problems/cheapest-flights-within-k-stops/discuss/115541/JavaPython-Priority-Queue-Solution
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        Map<Integer, Map<Integer, Integer>> prices = new HashMap<>();
        for (int[] f : flights) {
            if (!prices.containsKey(f[0])) prices.put(f[0], new HashMap<>());
            prices.get(f[0]).put(f[1], f[2]);
        }
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> (Integer.compare(a[0], b[0])));
        pq.add(new int[] {0, src, k + 1});
        while (!pq.isEmpty()) {
            int[] top = pq.remove();
            int price = top[0];
            int city = top[1];
            int stops = top[2];
            if (city == dst) return price;
            if (stops > 0) {
                Map<Integer, Integer> adj = prices.getOrDefault(city, new HashMap<>());
                for (int a : adj.keySet()) {
                    pq.add(new int[] {price + adj.get(a), a, stops - 1});
                }
            }
        }
        return -1;
    }
}















































https://leetcode.com/problems/cheapest-flights-within-k-stops/

There are n cities connected by some number of flights. You are given an array flights where flights[i] = [fromi, toi, pricei] indicates that there is a flight from city fromi to city toi with cost pricei.

You are also given three integers src, dst, and k, return the cheapest price from src to dst with at most k stops. If there is no such route, return-1.

Example 1:


```
Input: n = 4, flights = [[0,1,100],[1,2,100],[2,0,100],[1,3,600],[2,3,200]], src = 0, dst = 3, k = 1
Output: 700
Explanation:
The graph is shown above.
The optimal path with at most 1 stop from city 0 to 3 is marked in red and has cost 100 + 600 = 700.
Note that the path through cities [0,1,2,3] is cheaper but is invalid because it uses 2 stops.
```

Example 2:


```
Input: n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 1
Output: 200
Explanation:
The graph is shown above.
The optimal path with at most 1 stop from city 0 to 2 is marked in red and has cost 100 + 100 = 200.
```

Example 3:


```
Input: n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 0
Output: 500
Explanation:
The graph is shown above.
The optimal path with no stops from city 0 to 2 is marked in red and has cost 500.
```
 
Constraints:
- 1 <= n <= 100
- 0 <= flights.length <= (n * (n - 1) / 2)
- flights[i].length == 3
- 0 <= fromi, toi < n
- fromi != toi
- 1 <= pricei <= 104
- There will not be any multiple flights between two cities.
- 0 <= src, dst, k < n
- src != dst
---
Attempt 1: 2022-11-26

Solution 1:  Find minimum distance in a Directed & Weighted Graph using BFS [Dijkstra's algorithm] (120min)
```
class Solution { 
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) { 
        // Build graph 
        Map<Integer, List<int[]>> graph = new HashMap<Integer, List<int[]>>(); 
        for(int[] flight : flights) { 
            graph.put(flight[0], new ArrayList<int[]>()); 
        } 
        for(int[] flight : flights) { 
            graph.get(flight[0]).add(new int[] {flight[1], flight[2]}); 
        } 
        // Dijkstra 
        int[] stops = new int[n]; 
        Arrays.fill(stops, Integer.MAX_VALUE); 
        PriorityQueue<int[]> minPQ = new PriorityQueue<int[]>((a, b) -> a[1] - b[1]); 
        // minPQ -> {src, price, steps} 
        minPQ.offer(new int[] {src, 0, 0}); 
        while(!minPQ.isEmpty()) { 
            int[] cur = minPQ.poll(); 
            int cur_src = cur[0]; 
            int cur_price = cur[1]; 
            int cur_steps = cur[2]; 
            // We have already encountered a path with a lower cost and fewer stops, 
            // or the number of stops exceeds the limit.    
            if(cur_steps > stops[cur_src] || cur_steps > k + 1) { 
                continue; 
            } 
            stops[cur_src] = cur_steps; 
            if(cur_src == dst) { 
                return cur_price; 
            } 
            if(!graph.containsKey(cur_src)) { 
                continue; 
            } 
            for(int[] neighbour : graph.get(cur_src)) { 
                minPQ.offer(new int[] {neighbour[0], cur_price + neighbour[1], cur_steps + 1}); 
            } 
        } 
        return -1; 
    } 
}
```

Refer to
https://leetcode.com/problems/cheapest-flights-within-k-stops/solution/

Approach 3: Dijkstra


Intuition

If you are new to Dijkstra's algorithm, please see our Leetcode Explore Card for more information on it!

Dijkstra's algorithm is used to find the shortest paths from a source node to all the other nodes in a weighted graph where the edge weights are positive numbers. It makes use of a priority queue (heap) to decide which edges to use.

Dijkstra's works by greedily choosing which node to investigate next. A priority queue is used to select the node that currently has the lowest price. In the previous two approaches, we used an array dist that made sure we only traversed an edge to node x if we could make an improvement on dist[x]. In this approach, we will instead use an array stops which tracks the minimum number of stops needed to reach each node instead of the minimum price. Then, we will only traverse an edge to a node x if x has not already been visited with fewer stops. Because we are greedily choosing the node with the lowest total price, the first time we reach dst, we will have the answer.

As per the problem, we also need to restrict the number of stops to k i.e., we can take at most k + 1 steps from the source node, so we will store the current number of stops along with each node since we aren't iterating level by level anymore.


Algorithm

1. Create an adjacency list where adj[X] contains all the neighbors of node X and the corresponding price it takes to move to a neighbor.
2. Initialize the stops array, storing the steps required to reach a node from the src node. We would initialize it with large values to indicate we've not reached any nodes yet.
3. Initialize a min-heap that stores a triplet {dist_from_src_node, node, number_of_stops_from_src_node}. Insert {0, src, 0} as the first triplet into the queue.
4. Perform Dijkstra's until the heap is empty:
	- Pop {dist, node, steps} from the heap
	- If steps > stops[node], then we already visited this node with fewer steps earlier, so ignore the current triplet and move on.
	- If steps > k + 1, then we have taken too many stops, so ignore the current triplet and move on.
	- Otherwise, check if we are at dst. If we are, then return dist as the answer.
	- If not, iterate over the neighbors of node and for each neighbor, push {dist + price, neighbor, steps + 1} onto the heap.
5. If we reach the end of the loop without returning the answer, it means we cannot reach the destination. Our answer would be -1 in this case.


Implementation

```
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        Map<Integer, List<int[]>> adj = new HashMap<>();
        for (int[] i : flights)
            adj.computeIfAbsent(i[0], value -> new ArrayList<>()).add(new int[] { i[1], i[2] });

        int[] stops = new int[n];
        Arrays.fill(stops, Integer.MAX_VALUE);
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        // {dist_from_src_node, node, number_of_stops_from_src_node}
        pq.offer(new int[] { 0, src, 0 });

        while (!pq.isEmpty()) {
            int[] temp = pq.poll();
            int dist = temp[0];
            int node = temp[1];
            int steps = temp[2];
            // We have already encountered a path with a lower cost and fewer stops,
            // or the number of stops exceeds the limit.
            if (steps > stops[node] || steps > k + 1)
                continue;
            stops[node] = steps;
            if (node == dst)
                return dist;
            if (!adj.containsKey(node))
                continue;
            for (int[] a : adj.get(node)) {
                pq.offer(new int[] { dist + a[1], a[0], steps + 1 });
            }
        }
        return -1;
    }
}
```

