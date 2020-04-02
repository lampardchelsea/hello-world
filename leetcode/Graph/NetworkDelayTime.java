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

// Solution 1: Djikstra/bfs
// Refer to
// https://leetcode.com/problems/network-delay-time/discuss/210698/Java-Djikstrabfs-Concise-and-very-easy-to-understand
/**
  I think bfs and djikstra are very similar problems. It's just that djikstra cost is different compared with bfs, 
  so use priorityQueue instead a Queue for a standard bfs search.
*/

// https://blog.csdn.net/qq_35644234/article/details/60870719




