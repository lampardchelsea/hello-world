/**
 Refer to
 https://leetcode.com/problems/sum-of-distances-in-tree/
 An undirected, connected tree with N nodes labelled 0...N-1 and N-1 edges are given.
 The ith edge connects nodes edges[i][0] and edges[i][1] together.
 Return a list ans, where ans[i] is the sum of the distances between node i and all other nodes.
 Example 1:
 Input: N = 6, edges = [[0,1],[0,2],[2,3],[2,4],[2,5]]
 Output: [8,12,6,10,10,10]
 Explanation: 
 Here is a diagram of the given tree:
   0
  / \
 1   2
    /|\
   3 4 5
 We can see that dist(0,1) + dist(0,2) + dist(0,3) + dist(0,4) + dist(0,5)
 equals 1 + 1 + 2 + 2 + 2 = 8.  Hence, answer[0] = 8, and so on.
 Note: 1 <= N <= 10000
*/

// Solution 1: Brute force 1 pass DFS (TLE) --> Time Complexity: O(n^2)
// Refer to
// https://leetcode.com/problems/sum-of-distances-in-tree/discuss/466998/TLE-C%2B%2B-DFS-%2B-undirected-graph..Need-some-idea-to-fix-this-problem.-6469-accepted.
class Solution {
    public int[] sumOfDistancesInTree(int N, int[][] edges) {
        int[] result = new int[N];
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        for(int i = 0; i < N; i++) {
            map.put(i, new ArrayList<Integer>());
        }
        for(int[] edge : edges) {
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }
        for(int i = 0; i < N; i++) {
            result[i] = helper(i, map, 0, new boolean[N]);
        }
        return result;
    }
    
    private int helper(int i, Map<Integer, List<Integer>> map, int depth, boolean[] visited) {
        if(visited[i]) {
            return 0;
        }
        visited[i] = true;
        int sum = 0;
        sum += depth;
        for(int j : map.get(i)) {
            if(!visited[j]) {
                sum += helper(j, map, depth + 1, visited);
            }
        }
        return sum;
    }
}

// Solution 2: 2 PASS DFS --> Time Complexity O(N)
// Refer to
// https://leetcode.com/problems/sum-of-distances-in-tree/solution/
// https://leetcode.com/problems/sum-of-distances-in-tree/discuss/130583/C%2B%2BJavaPython-Pre-order-and-Post-order-DFS-O(N)
// https://www.cnblogs.com/grandyang/p/11520804.html
