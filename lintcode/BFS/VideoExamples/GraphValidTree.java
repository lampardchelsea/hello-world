
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Refer to
 * http://www.lintcode.com/en/problem/graph-valid-tree/
 * Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), 
 * write a function to check whether these edges make up a valid tree.
 * Notice
 * You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] 
 * is the same as [1, 0] and thus will not appear together in edges.
 * 
 * Have you met this question in a real interview?
 * Example
 * Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.
 * Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return false.
 * 
 * 
 * Solution
 * http://www.cnblogs.com/grandyang/p/5257919.html
 * 这道题给了我们一个无向图，让我们来判断其是否为一棵树，我们知道如果是树的话，所有的节点必须是连接的，也就是说必须是连通图，
 * 而且不能有环，所以我们的焦点就变成了验证是否是连通图和是否含有环。
 * 
 * 我们首先用DFS来做，根据pair来建立一个图的结构，用邻接链表来表示，还需要一个一位数组v来记录某个节点是否被访问过，然后
 * 我们用DFS来搜索节点0，遍历的思想是，当DFS到某个节点，先看当前节点是否被访问过，如果已经被访问过，说明环存在，直接返回
 * false，如果未被访问过，我们现在将其状态标记为已访问过，然后我们到邻接链表里去找跟其相邻的节点继续递归遍历，注意我们还
 * 需要一个变量pre来记录上一个节点，以免回到上一个节点，这样遍历结束后，我们就把和节点0相邻的节点都标记为true，然后我们
 * 在看v里面是否还有没被访问过的节点，如果有，则说明图不是完全连通的，返回false，反之返回true
 * 
 * 下面我们来看BFS的解法，思路很相近，需要用queue来辅助遍历，这里我们没有用一维向量来标记节点是否访问过，而是用了一个set，
 * 如果遍历到一个节点，在set中没有，则加入set，如果已经存在，则返回false，还有就是在遍历邻接链表的时候，遍历完成后需要将节点删掉
 * 
 * http://www.jiuzhang.com/solutions/graph-valid-tree/
 * 
 * 
 * https://discuss.leetcode.com/topic/21714/ac-java-graph-dfs-solution-with-adjacency-list
 * 
 * 
 * https://segmentfault.com/a/1190000003791051
 * 
 */
public class GraphValidTree {
	// Solution 1: BFS
    /**
     * @param n an integer
     * @param edges a list of undirected edges
     * @return true if it's a valid tree, or false
     */
    public boolean validTree(int n, int[][] edges) {
        // Basic check
        // If not satisfy definition of Graph: (1) no nodes
        // (2) edges number not match nodes - 1
        if(n == 0 || edges.length != n - 1) {
            return false;
        }
        
        // Initialize Graph with elementary data structure
        Map<Integer, HashSet<Integer>> graph = initializeGraph(n, edges);
        
        /**
         * Target: How to make sure a tree
         * Make sure there's no cycle (Use HashSet to check)
         * Make sure there's all vertices are connected
         */

        // Use BFS
        Queue<Integer> queue = new LinkedList<Integer>();
        Set<Integer> set = new HashSet<Integer>();
        // When we traverse a graph instead of traverse tree,
        // we must use set and queue synchronously to get rid
        // of circular cases
        queue.offer(0);
        set.add(0);
        while(!queue.isEmpty()) {
             int node = queue.poll();
             // No need for level traverse
             for(Integer neighbor : graph.get(node)) {
            	 // Use HashSet to judge if the node already enter
            	 // queue before, In case of same node repeatedly enter into queue,
            	 // This HashSet will used synchronously with queue
		         if(!set.add(neighbor)) {
		             continue;
		         }
                 queue.offer(neighbor);
             }
        }
        // Make sure there's all vertices are connected,
        // if not all vertices include on HashSet, that
        // means some vertex not connected
        return set.size() == n;
    }
    
    public Map<Integer, HashSet<Integer>> initializeGraph(int n, int[][] edges) {
        Map<Integer, HashSet<Integer>> graph = new HashMap<Integer, HashSet<Integer>>();
        // Initialize vertices (we can directly use i as key because given condition
        // declare n nodes labeled from 0 to n - 1)
        for(int i = 0; i < n; i++) {
            graph.put(i, new HashSet<Integer>());
        }
        // Initialize adjacency
        for(int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }        
        return graph;
    }
	
	// Solution 2: DFS Version 1
    // Refer to
    // https://discuss.leetcode.com/topic/21714/ac-java-graph-dfs-solution-with-adjacency-list/14
    // Solution 2 is very similar to Solution 1, just change its format to DFS
    public boolean validTree2(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Integer>());
        }

        // initialize adjacency list.
        for (int i = 0; i < edges.length; i++) {
            graph.get(edges[i][0]).add(edges[i][1]);
            graph.get(edges[i][1]).add(edges[i][0]);
        }
        Set<Integer> visited = new HashSet<>();
        visited.add(0);

        // do DFS from vertex 0, after one round DFS, if there is no loop and visited contains all the vertexs,
        // it is a tree.
        boolean res = helper(-1, 0, visited, graph);
        if (!res) return res;
        return visited.size() == n ? true : false;
    }

    public boolean helper(int parent, int vertex, Set<Integer> visited, List<List<Integer>> graph) {
        List<Integer> subs = graph.get(vertex);
        for (int v : subs) {
            // if v is vertex's parent, continue.
            if (v == parent) continue; 
            // if v is not vertex's parent, and v is visited. that presents there is a cycle in this graph.
            if(visited.contains(v)) return false;
            visited.add(v);
            boolean res = helper(vertex, v, visited, graph);
            if (!res) return false;
        }
        return true;
    }
	

	// Solution 3: DFS Version 2
    /**
     * @param n an integer
     * @param edges a list of undirected edges
     * @return true if it's a valid tree, or false
     */
    public boolean validTree3(int n, int[][] edges) {
        // Basic check
        // If not satisfy definition of Graph: (1) no nodes
        // (2) edges number not match nodes - 1
        if(n == 0 || edges.length != n - 1) {
            return false;
        }
        
        // Initialize Graph with elementary data structure
        List<List<Integer>> graph = initializeGraph3(n, edges);
        
        
        /**
         * Target: How to make sure a tree
         * Make sure there's no cycle (Use HashSet to check)
         * Make sure there's all vertices are connected
         */
         
        // Use DFS
        boolean[] visited = new boolean[n];
        // Check no cycle
        if(hasCycle3(graph, 0, visited, -1)) {
            return false;
        }
        // Check all nodes connected
        for(int i = 0; i < n; i++) {
            if(!visited[i]) {
                return false;
            }
        }
        return true;
    }
    
    // Check if an undirected graph has cycle started from vertex u
    public boolean hasCycle3(List<List<Integer>> graph, int u, boolean[] visited, int parent) {
        visited[u] = true;
        for(int i = 0; i < graph.get(u).size(); i++) {
            int v = graph.get(u).get(i);          
            // Case 1: Current node v already visited and current node not equal to its parent
            // Case 2: Current node v not visited but dfs start from current node v has cycle
            // Important: Case 1 and Case 2 checking order should NOT exchange, because in
            // Case 2 hasCycle() method will recursively calling and 'parent' will update,
            // when we check 'v != parent' in Case 1 on current recursion conflict on this update 
            //if((!visited[v] && hasCycle(graph, v, visited, u)) || (visited[v] && v != parent)) {
            if((visited[v] && v != parent) || (!visited[v] && hasCycle3(graph, v, visited, u))) {
                return true;
            }
        }
        return false;
    }
    
    public List<List<Integer>> initializeGraph3(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<List<Integer>>();
        // Initialize vertices (we can directly use i as key because given condition
        // declare n nodes labeled from 0 to n - 1)
        for(int i = 0; i < n; i++) {
            graph.add(i, new ArrayList<Integer>());
        }
        // Initialize adjacency
        for(int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }        
        return graph;
    }
	

	
    public static void main(String[] args) {
    	GraphValidTree g = new GraphValidTree();
    	int n = 5;
    	int[][] edges = {{0,1},{0,2},{0,3},{1,4}};
//    	int n = 5;
//    	int[][] edges = {{0,1},{1,2},{2,3},{1,3},{1,4}};
    	boolean result = g.validTree2(n, edges);
    	System.out.print(result);
    }
    
}
