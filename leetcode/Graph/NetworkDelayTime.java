/**
 Refer to
 https://leetcode.com/problems/network-delay-time/
 There are N network nodes, labelled 1 to N.

Given times, a list of travel times as directed edges times[i] = (u, v, w), where u is the 
source node, v is the target node, and w is the time it takes for a signal to travel from source to target.

Now, we send a signal from a certain node K. How long will it take for all nodes to receive 
the signal? If it is impossible, return -1. 

Input: times = [[2,1,1],[2,3,1],[3,4,1]], N = 4, K = 2
Output: 2

Note:
N will be in the range [1, 100].
K will be in the range [1, N].
The length of times will be in the range [1, 6000].
All edges times[i] = (u, v, w) will have 1 <= u, v <= N and 0 <= w <= 100.
*/

// Wrong Solution: Try to use DFS + backtracking but failed
class Solution {
    public int networkDelayTime(int[][] times, int N, int K) {
        Set<Integer> set = new HashSet<Integer>();
        set.add(K);
        Map<Integer, List<Integer>> graph = new HashMap<Integer, List<Integer>>();
        for(int i = 1; i <= N; i++) {
            graph.put(i, new ArrayList<Integer>());
        }
        for(int[] time : times) {
            graph.get(time[0]).add(time[1]);
        }
        List<Integer> outDegreeZeroNodes = new ArrayList<Integer>();
        for(int i = 1; i <= N; i++) {
            if(graph.get(i).size() == 0) {
                outDegreeZeroNodes.add(i);
            }
        }
        Map<String, Integer> weightMap = new HashMap<String, Integer>();
        for(int[] time : times) {
            weightMap.put(time[0] + "_" + time[1], time[2]);
        }
        List<Integer> pathWeights = new ArrayList<Integer>();
        for(int i = 0; i < outDegreeZeroNodes.size(); i++) {
            int outDegreeZeroNode = outDegreeZeroNodes.get(i);
            pathWeights.add(i, 0);
            helper(K, outDegreeZeroNode, set, graph, weightMap, pathWeights, i);
        }
        int max = 0;
        for(int i = 0; i < pathWeights.size(); i++) {
            int pathWeight = pathWeights.get(i);
            if(pathWeight > max) {
                max = pathWeight;
            }
        }
        if(set.size() == N) {
            return max;
        } else {
            return -1;
        }
    }
    
    private void helper(int start, int dest, Set<Integer> set, Map<Integer, List<Integer>> graph, Map<String, Integer> weightMap, List<Integer> pathWeights, int recordIndexInPathWeightsArray) {
    	if(start == dest) {
            return;
        }
    	if(graph.get(start).size() == 0) {
            return;
        }
        for(int neighbor : graph.get(start)) {
            set.add(neighbor);
            int originalWeight = pathWeights.get(recordIndexInPathWeightsArray);
            int currSectionWeight = weightMap.get(start + "_" + neighbor);
            int newWeight = originalWeight + currSectionWeight;
            pathWeights.set(recordIndexInPathWeightsArray, newWeight);
            helper(neighbor, dest, set, graph, weightMap, pathWeights, recordIndexInPathWeightsArray);
            pathWeights.set(recordIndexInPathWeightsArray, originalWeight);
        }
    }
}

// Solution 1: Dijkstra / BFS classic version
// Refer to
// https://leetcode.com/problems/network-delay-time/discuss/210698/Java-Djikstrabfs-Concise-and-very-easy-to-understand
// https://leetcode.com/problems/network-delay-time/discuss/539965/Java-Clean-code-with-analysis
// https://www.cs.cornell.edu/courses/cs2112/2014fa/lectures/lecture.html?id=ssp
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Graph/Documents/Dijkstra_single-source_shortest_path_algorithm_CS%202112_ENGRD_2112_Cornell.pdf
// https://www.cs.princeton.edu/courses/archive/spring13/cos423/lectures/04GreedyAlgorithmsII.pdf
/**
 Dijkstra's algorithm: efficient implementation
 Implementation.
 ・Algorithm stores d(v) for each explored node v.
 ・Priority queue stores π (v) for each unexplored node v.
 ・Recall: d(u) = π (u) when u is deleted from priority queue.
   DIJKSTRA (V, E, s) 
   Create an empty priority queue.
   FOR EACH v ≠ s : d(v) ← ∞; d(s) ← 0.
   FOR EACH v ∈ V : insert v with key d(v) into priority queue.
   WHILE (the priority queue is not empty)
     u ← delete-min from priority queue.
     FOR EACH edge (u, v) ∈ E leaving u:
       IF d(v) > d(u) + ℓ(u, v)
         decrease-key of v to d(u) + ℓ(u, v) in priority queue.
         d(v) ← d(u) + ℓ(u, v). 

  Performance. Depends on PQ: n insert, n delete-min, m decrease-key.
  ・Array implementation optimal for dense graphs.
  ・Binary heap much faster for sparse graphs.
  ・4-way heap worth the trouble in performance-critical situations.
  ・Fibonacci/Brodal best in theory, but not worth implementing.
*/
public class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        // The test case based on https://www.cs.cornell.edu/courses/cs2112/2014fa/lectures/lecture.html?id=ssp
        int[][] times = {{1,2,15}, {1,3,14}, {1,4,9}, {4,5,23}, {3,2,5}, 
        		{3,5,17}, {2,6,20}, {3,6,30}, {5,6,3}, {6,7,16}, {2,7,37}, {5,7,20}};
        int K = 1;
        int N = 7;
        int result = s.networkDelayTime(times, N, K);
        System.out.println(result);
    }

    public int networkDelayTime(int[][] times, int N, int K) {
        if (N <= 0 || K < 0 || K > N || times == null || times.length == 0) {
            return 0;
        }
        Map < Integer, List < int[] >> graph = new HashMap < > ();
        for (int i = 1; i <= N; i++) {
            graph.put(i, new ArrayList < > ());
        }
        for (int[] time: times) {
            graph.get(time[0]).add(new int[] {time[1], time[2]});
        }
        PriorityQueue < int[] > minHeap = new PriorityQueue < int[] > (
            (a, b) - > Integer.compare(a[1], b[1]));
        minHeap.offer(new int[] {K, 0});
        int[] distance = new int[N + 1];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[K] = 0;
        distance[0] = 0; // As nodes are labels from 1 to N and the max time for network delay is the max of this array if successful
        Set < Integer > visited = new HashSet < > ();
        while (!minHeap.isEmpty()) {
            int[] current = minHeap.poll();
            int from = current[0];
            int dist = current[1];
            if (!visited.add(from)) continue; // Optimization
            for (int[] dest: graph.get(from)) {
                int to = dest[0];
                int newDist = Math.min(distance[to], dest[1] + dist);
                if (!visited.contains(to)) { // Check to avoid any cycle
                    distance[to] = newDist;
                    minHeap.offer(new int[] {to, newDist});
                }
            }
        }
        return visited.size() == N ? Arrays.stream(distance).max().getAsInt() : -1;
    }
}


// Solution 2: Djikstra / BFS
// Refer to
// https://leetcode.com/problems/network-delay-time/discuss/210698/Java-Djikstrabfs-Concise-and-very-easy-to-understand
/**
  I think bfs and djikstra are very similar problems. It's just that djikstra cost is different compared with bfs, 
  so use priorityQueue instead a Queue for a standard bfs search.
*/

// https://blog.csdn.net/qq_35644234/article/details/60870719
class Solution {
    public int networkDelayTime(int[][] times, int N, int K) {
        Map<Integer, Map<Integer, Integer>> map = new HashMap<Integer, Map<Integer, Integer>>();
        for(int[] time : times) {
            map.putIfAbsent(time[0], new HashMap<Integer, Integer>());
            map.get(time[0]).put(time[1], time[2]);
        }
        boolean[] visited = new boolean[N + 1];
        // element as {Node ID, distance}, pq order based on distance as minimum heap
        // Replace the normal queue used by BFS to PriorityQueue used by Dijkstra
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> (a[1] - b[1]));
        pq.add(new int[]{K, 0});
        int result = 0;
        while(!pq.isEmpty()) {
            int[] cur = pq.poll();
            int curNode = cur[0];
            int curDist = cur[1];
            // If not visited before, mark as visited
            if(!visited[curNode]) {
                visited[curNode] = true;
                N--;
                result = curDist;
                if(map.containsKey(curNode)) {
                  for(int neighbor : map.get(curNode).keySet()) {
                      pq.add(new int[]{neighbor, curDist + map.get(curNode).get(neighbor)});
                  }    
                }
            }
        }
        return N == 0 ? result : -1;
    }
}



