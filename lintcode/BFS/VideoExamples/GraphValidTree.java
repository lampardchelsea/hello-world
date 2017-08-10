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
 * 杩欓亾棰樼粰浜嗘垜浠竴涓棤鍚戝浘锛岃鎴戜滑鏉ュ垽鏂叾鏄惁涓轰竴妫垫爲锛屾垜浠煡閬撳鏋滄槸鏍戠殑璇濓紝鎵�湁鐨勮妭鐐瑰繀椤绘槸杩炴帴鐨勶紝涔熷氨鏄蹇呴』鏄繛閫氬浘锛�
 * 鑰屼笖涓嶈兘鏈夌幆锛屾墍浠ユ垜浠殑鐒︾偣灏卞彉鎴愪簡楠岃瘉鏄惁鏄繛閫氬浘鍜屾槸鍚﹀惈鏈夌幆銆�
 * 
 * 鎴戜滑棣栧厛鐢―FS鏉ュ仛锛屾牴鎹畃air鏉ュ缓绔嬩竴涓浘鐨勭粨鏋勶紝鐢ㄩ偦鎺ラ摼琛ㄦ潵琛ㄧず锛岃繕闇�涓�釜涓�綅鏁扮粍v鏉ヨ褰曟煇涓妭鐐规槸鍚﹁璁块棶杩囷紝鐒跺悗
 * 鎴戜滑鐢―FS鏉ユ悳绱㈣妭鐐�锛岄亶鍘嗙殑鎬濇兂鏄紝褰揇FS鍒版煇涓妭鐐癸紝鍏堢湅褰撳墠鑺傜偣鏄惁琚闂繃锛屽鏋滃凡缁忚璁块棶杩囷紝璇存槑鐜瓨鍦紝鐩存帴杩斿洖
 * false锛屽鏋滄湭琚闂繃锛屾垜浠幇鍦ㄥ皢鍏剁姸鎬佹爣璁颁负宸茶闂繃锛岀劧鍚庢垜浠埌閭绘帴閾捐〃閲屽幓鎵捐窡鍏剁浉閭荤殑鑺傜偣缁х画閫掑綊閬嶅巻锛屾敞鎰忔垜浠繕
 * 闇�涓�釜鍙橀噺pre鏉ヨ褰曚笂涓�釜鑺傜偣锛屼互鍏嶅洖鍒颁笂涓�釜鑺傜偣锛岃繖鏍烽亶鍘嗙粨鏉熷悗锛屾垜浠氨鎶婂拰鑺傜偣0鐩搁偦鐨勮妭鐐归兘鏍囪涓簍rue锛岀劧鍚庢垜浠�
 * 鍦ㄧ湅v閲岄潰鏄惁杩樻湁娌¤璁块棶杩囩殑鑺傜偣锛屽鏋滄湁锛屽垯璇存槑鍥句笉鏄畬鍏ㄨ繛閫氱殑锛岃繑鍥瀎alse锛屽弽涔嬭繑鍥瀟rue
 * 
 * 涓嬮潰鎴戜滑鏉ョ湅BFS鐨勮В娉曪紝鎬濊矾寰堢浉杩戯紝闇�鐢╭ueue鏉ヨ緟鍔╅亶鍘嗭紝杩欓噷鎴戜滑娌℃湁鐢ㄤ竴缁村悜閲忔潵鏍囪鑺傜偣鏄惁璁块棶杩囷紝鑰屾槸鐢ㄤ簡涓�釜set锛�
 * 濡傛灉閬嶅巻鍒颁竴涓妭鐐癸紝鍦╯et涓病鏈夛紝鍒欏姞鍏et锛屽鏋滃凡缁忓瓨鍦紝鍒欒繑鍥瀎alse锛岃繕鏈夊氨鏄湪閬嶅巻閭绘帴閾捐〃鐨勬椂鍊欙紝閬嶅巻瀹屾垚鍚庨渶瑕佸皢鑺傜偣鍒犳帀
 * 
 * http://www.jiuzhang.com/solutions/graph-valid-tree/
 * 
 * 
 * https://discuss.leetcode.com/topic/21714/ac-java-graph-dfs-solution-with-adjacency-list
 * 
 * 
 * https://segmentfault.com/a/1190000003791051
 * http://blog.csdn.net/dm_vincent/article/details/7655764
 * 并查集
 * 复杂度
 * 时间 O(N^M) 空间 O(1)
 * 思路
 * 判断输入的边是否能构成一个树，我们需要确定两件事：
 * 这些边是否构成环路，如果有环则不能构成树
 * 这些边是否能将所有节点连通，如果有不能连通的节点则不能构成树
 * 因为不需要知道具体的树长什么样子，只要知道连通的关系，所以并查集相比深度优先搜索是更好的方法。我们定义一个并查集的数据结构，并提供标准的四个接口：

	union 将两个节点放入一个集合中
	find 找到该节点所属的集合编号
	areConnected 判断两个节点是否是一个集合
	count 返回该并查集中有多少个独立的集合

 * 具体并查集的原理，参见这篇文章。简单来讲，就是先构建一个数组，节点0到节点n-1，刚开始都各自独立的属于自己的集合。这时集合的编号是节点号。然后，
 * 每次union操作时，我们把整个并查集中，所有和第一个节点所属集合号相同的节点的集合号，都改成第二个节点的集合号。这样就将一个集合的节点归属到
 * 同一个集合号下了。我们遍历一遍输入，把所有边加入我们的并查集中，加的同时判断是否有环路。最后如果并查集中只有一个集合，则说明可以构建树。
 * 注意
 * 因为要判断是否会产生环路，union方法要返回一个boolean，如果两个节点本来就在一个集合中，就返回假，说明有环路
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
	

    // Solution 4: Union-Find
    // Refer to
    // https://segmentfault.com/a/1190000003791051
    // http://blog.csdn.net/dm_vincent/article/details/7655764
    public boolean validTree4(int n, int[][] edges) {
    	if(n == 0 || edges.length != n - 1) {
    		return false;
    	}
    	UnionFind uf = new UnionFind(n);
    	for(int i = 0; i < edges.length; i++) {
    		// 如果两个节点已经在同一集合中，说明新的边将产生环路
    		if(!uf.union(edges[i][0], edges[i][1])) {
    			return false;
    		}
    	}
    	return uf.count() == 1;
    }
    
    private class UnionFind {
    	int[] ids;
    	int setCount;
    	public UnionFind(int size) {
    		ids = new int[size];
    		//初始化并查集，每个节点对应自己的集合号
    		for(int i = 0; i < size; i++) {
    			ids[i] = i;
    		}
    		setCount = size;
    	}
    	
    	public boolean union(int m, int n) {
    		int src = find(m);
    		int dst = find(n);
    		//如果两个节点不在同一集合中，将两个集合合并为一个
    		if(src != dst) {
    			//注意：必须把所有等于src集合的转化为dst集合
    			for(int i = 0; i < ids.length; i++) {
    			    if(ids[i] == src) {
    			    	ids[i] = dst;
    			    }
    			}
    			// 合并完集合后，集合数减一
    			setCount--;
    			return true;
    		} else {
    			return false;
    		}
    	}
    	
    	public int find(int x) {
    		return ids[x];
    	}
    	
    	public boolean isConnected(int m, int n) {
    		return find(m) == find(n);
    	}
    	
    	public int count() {
    		return setCount;
    	}
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
