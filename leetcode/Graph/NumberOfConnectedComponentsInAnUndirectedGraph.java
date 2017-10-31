
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5166356.html
 * Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge 
 * is a pair of nodes), write a function to find the number of connected components 
 * in an undirected graph.

	Example 1:
	
	     0          3
	
	     |          |
	
	     1 --- 2    4
	
	Given n = 5 and edges = [[0, 1], [1, 2], [3, 4]], return 2.
	
	Example 2:
	
	     0           4
	
	     |           |
	
	     1 --- 2 --- 3

    Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [3, 4]], return 1.

    Note:
    You can assume that no duplicate edges will appear in edges. Since all edges are 
    undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.
 * 
 * Solution
 * http://www.cnblogs.com/grandyang/p/5166356.html
 * https://discuss.leetcode.com/topic/32752/easiest-2ms-java-solution
 */
public class NumberOfConnectedComponentsInAnUndirectedGraph {
	// Solution 1: Union-Find
	// Refer to
	// https://discuss.leetcode.com/topic/32752/easiest-2ms-java-solution
	/**
	 * This is 1D version of Number of Islands II. For more explanations, 
	 * check out this 2D Solution.

		n points = n islands = n trees = n roots.
		With each edge added, check which island is e[0] or e[1] belonging to.
		If e[0] and e[1] are in same islands, do nothing.
		Otherwise, union two islands, and reduce islands count by 1.
		Bonus: path compression can reduce time by 50%.
	 */
	public int countComponents(int n, int[][] edges) {
        int[] father = new int[n + 1];
        for(int i = 1; i < n + 1; i++) {
        	father[i] = i;
        }
        for(int[] edge : edges) {
        	int root1 = find(edge[0], father);
        	int root2 = find(edge[1], father);
        	if(root1 != root2) {
        		father[root1] = root2;
        		n--;
        	}
        }
        return n;
	}
	
	private int find(int id, int[] father) {
		while(father[id] != id) {
			father[id] = father[father[id]];
			id = father[id];
		}
		return id;
	}
	
	// Solution 2: DFS
	// Refer to
	// https://discuss.leetcode.com/topic/34498/java-concise-dfs
	/**
	 * start dfsVisit with sources 0-n-1, count number of unvisited sources.
	 */
	public int countComponents2(int n, int[][] edges) {
		if(n <= 1) {
			return n;
		}
		Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		for(int i = 0; i < n; i++) {
			map.put(i, new ArrayList<Integer>());
		}
		for(int[] edge : edges) {
			map.get(edge[0]).add(edge[1]);
			map.get(edge[1]).add(edge[0]);
		}
		Set<Integer> visited = new HashSet<Integer>();
		int count = 0;
		for(int i = 0; i < n; i++) {
			if(visited.add(i)) {
				dfsVisit(i, map, visited);
				count++;
			}
		}
		return count;
	}
	
	private void dfsVisit(int i, Map<Integer, List<Integer>> map, Set<Integer> visited) {
		for(int j : map.get(i)) {
			if(visited.add(j)) {
				dfsVisit(j, map, visited);
			}
		}
	}
	
	public static void main(String[] args) {
		NumberOfConnectedComponentsInAnUndirectedGraph noc = new NumberOfConnectedComponentsInAnUndirectedGraph();
		int n = 5;
		int[][] edges = {{0,1},{1,2},{3,4}};
//		int n = 5;
//		int[][] edges = {{0,1},{1,2},{2,3},{3,4}};
//		int result = noc.countComponents(n, edges);
		int result = noc.countComponents2(n, edges);
		System.out.println(result);
	}
}
