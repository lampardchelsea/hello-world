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
