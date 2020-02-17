
import java.util.List;
import java.util.Queue;

/**
 * Refer to
 * https://leetcode.com/problems/course-schedule/description/
 * There are a total of n courses you have to take, labeled from 0 to n - 1.
 * Some courses may have prerequisites, for example to take course 0 you have 
 * to first take course 1, which is expressed as a pair: [0,1]
 * Given the total number of courses and a list of prerequisite pairs, is it 
 * possible for you to finish all courses?
 
	For example:	
	2, [[1,0]]
	There are a total of 2 courses to take. To take course 1 you should have 
	finished course 0. So it is possible.	
	2, [[1,0],[0,1]]
	There are a total of 2 courses to take. To take course 1 you should have 
	finished course 0, and to take course 0 you should also have finished 
	course 1. So it is impossible.
	
	Note:
	The input prerequisites is a graph represented by a list of edges, not adjacency 
	matrices. Read more about how a graph is represented.
	You may assume that there are no duplicate edges in the input prerequisites.
	click to show more hints.

 * Hints:
 * This problem is equivalent to finding if a cycle exists in a directed graph. 
 * If a cycle exists, no topological ordering exists and therefore it will be 
 * impossible to take all courses.
 * Topological Sort via DFS - A great video tutorial (21 minutes) on Coursera 
 * explaining the basic concepts of Topological Sort.
 * Topological sort could also be done via BFS.
 * 
 * 
 * Solution
 * https://discuss.leetcode.com/topic/13854/easy-bfs-topological-sort-java/11
 * https://discuss.leetcode.com/topic/13854/easy-bfs-topological-sort-java/6?page=1
 * https://leetcode.com/problems/course-schedule/discuss/58509/C%2B%2B-BFSDFS
 * This problem is equivalent to detecting a cycle in the directed graph represented by prerequisites. 
 * Both BFS and DFS can be used to solve it using the idea of topological sort. Since pair<int, int> 
 * is inconvenient for implementing graph algorithms, we first transform it to the adjacency-list 
 * representation. If course u is a prerequisite of course v, then the adjacency list of u will contain v.
 * 
 * BFS
 * BFS uses the indegrees of each node. We will first try to find a node with 0 indegree. If we fail 
 * to do so, there must be a cycle in the graph and we return false. Otherwise we set its indegree to 
 * be -1 to prevent from visiting it again and reduce the indegrees of its neighbors by 1. This process 
 * will be repeated for n (number of nodes) times.
 */
public class CourseSchedule {
    // Solution 1: 70ms (No adjacency list)
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // Find all nodes indegree
        // Initialize an indegree array, size based on 'numCourses',
        // we can do this based on: There are a total of n courses 
        // you have to take, labeled from 0 to n - 1.
        int[] indegree = new int[numCourses];
        for(int i = 0; i < prerequisites.length; i++) {
            // Definition of relationship between prerequisite node and
            // its neighbor:
            // E.g To take course 0 you have to first take 
            // course 1, which is expressed as a pair: [0,1]
            // based on this setting, we can analyze pair as
            // pair[0] -> neighbor of prerequisite node
            // pair[1] -> prerequisite node
            int[] pair = prerequisites[i];
            // Update item in indegree array at index of neighbor of prerequisite node
            // which is 'pair[0]'
            indegree[pair[0]]++;
        }
        // Find all start nodes based on indegree = 0, which means
        // no dependent on prerequisite node, also prepare a queue
        // to store these start nodes for later BFS
        Queue<Integer> queue = new LinkedList<Integer>();
        for(int i = 0; i < indegree.length; i++) {
            if(indegree[i] == 0) {
                queue.offer(i);
            }
        }
        // Topological Sort with BFS
        while(!queue.isEmpty()) {
            int startNode = queue.poll();
            numCourses--;
            // Update indegree for all neighbors of current node
            for(int[] pair : prerequisites) {
                // If neighbour of current node('pair[0]') equal to start node
                // we store on queue previously(since we only store
                // total of n courses labeled from 0 to n - 1,
                // this equation relation will be unique for each 
                // start node on queue), update current node('pair[1]')'s
                // indegree by minus 1
                if(pair[0] == startNode) {
                    indegree[pair[1]]--;
                    // Adding newly matched node onto queue
                    if(indegree[pair[1]] == 0) {
                        queue.offer(pair[1]);
                    }
                }
            }
        }
        // If finally numCourses drop to 0 means all
        // node can put on path
        return numCourses == 0;
    } 
    
    // Solution 2: 10ms (With adjacency list)
    // Refer to
    // https://blog.csdn.net/lisonglisonglisong/article/details/45543451
    /**
     * 为何第一个解法用了 70 ms, 第二个看上去没太大变化只用了 10ms，是哪里有个特别的优化吗 ? 
     * 首先定义 V 指节点数量，就是 numCourses。E 指边的数量，就是 prerequisites.length。
		第一个算法的时间复杂度是 O(VE)的。
		主逻辑一共三部分：
		1. for(int[] pair:prerequisites) - 复杂度 O(E)
		2. for(int i=0;i<indegree.length;i++) - 复杂度 O(V)
		3. while(!queue.isEmpty())
		    这一部分，对于每一个节点来说，都要再进行一个内层循环：for(int[] pair:prerequisites)
		    所以这一部分的复杂度是 O(VE)
		总复杂度是三者之和，因为 E 和 V 都是正数，所以三者之和就是 O(E + V + VE) = O(VE)。
		
		第二个算法的时间复杂度，就如注释写的，是 O(V + E) 的。
		第一部分是他写的“E part”。
		第二部分是 for (int i=0; i<numCourses; i++) ，这一部分单独的复杂度是 O(V)。
		第三部分是 while (!queue.isEmpty())
		    也许你会奇怪为什么他也是两层循环，但是复杂度不是 O(VE) 了呢？
		    原因在于，matrix 这个变量里，每条边只存在一个。而且访问过一条边之后，它所对应的节点就会从 Queue 里移除，不会再访问了。
		    所以，每一个节点会被访问有且只有一次，每一条边会被访问有且只有一次。
		    我觉得第三部分的复杂度应该是 O(max(V, E))。但这不影响三部分复杂度之和。
		根据 V 和 E 谁打谁小，总复杂度有两种情况：O(E + V + E) or O(E + V + V)，这都是 O(V + E) 。
		
		第一个算法在第三部分的内层循环上浪费了时间。
		所有 pair[0] != course 的点也会被循环一遍。
     */
    public boolean canFinish2(int numCourses, int[][] prerequisites) {
    	// Initialize data structure to express adjacency nodes of each node
    	// Same thing as 'neighbors' defined in TopologicalSort.java
    	// Refer to
    	// https://github.com/lampardchelsea/hello-world/blob/master/lintcode/BFS/VideoExamples/TopologicalSort.java
    	// class DirectedGraphNode {
    	//     int label;
    	//     ArrayList<DirectedGraphNode> neighbors;
    	//     DirectedGraphNode(int x) { label = x; neighbors = new ArrayList<DirectedGraphNode>(); }
    	// };
    	List<Integer>[] allAdjacents = new List[numCourses];
    	int[] indegree = new int[numCourses];
        
    	// Get all nodes indegree and initialize all adjacents of each node
    	// O(E)
    	for(int i = 0; i < prerequisites.length; i++) {
    		int[] pair = prerequisites[i];
            // pair[0] -> neighbor of current node
            // pair[1] -> current node
    		// Initialize adjacents for current node
    		List<Integer> list = allAdjacents[pair[1]];
    		if(list == null) {
    			list = new LinkedList<Integer>();
    			allAdjacents[pair[1]] = list;
    		}
    		list.add(pair[0]);
		// E.g the edge between as direction from pair[1] to pair[0], 
		// pair[1] -> pair[0], since current node is pair[1], 
		// so the indegree for its adjacent node as pair[0] plus 1
    		indegree[pair[0]]++;
    	} 
    	
    	// Get start nodes and add to queue (indegree = 0)
    	// O(E)
    	Queue<Integer> queue = new LinkedList<Integer>();
    	for(int i = 0; i < numCourses; i++) {
    		if(indegree[i] == 0) {
    			queue.offer(i);
    		}
    	}
    	
    	// Topological sort with BFS
    	// O(V + E)
    	while(!queue.isEmpty()) { // O(V)
    		numCourses--;
    		int node = queue.poll();
    		// Get node's adjacency
    		List<Integer> adjacents = allAdjacents[node];
    		// Important check on if adjacents of current node exist
    		// or not, if not exist skip
    		if(adjacents == null) {
    			continue;
    		}
    		// Update indegree by minus 1 for all adjacents of current node
    		for(Integer neighbor : adjacents) { // O(E)
    			indegree[neighbor]--;
    			if(indegree[neighbor] == 0) {
    				queue.offer(neighbor);
    			}
    		}
    	}
    	
    	return numCourses == 0;
    }
    
	
	public static void main(String[] args) {
		CourseSchedule c = new CourseSchedule();
		int numCourses = 2;
		int[][] prerequisites = {{1, 0}};
		boolean result = c.canFinish(numCourses, prerequisites);
		System.out.println(result);
	}
}

// Better explaination:
// https://segmentfault.com/a/1190000003814058
/**
 复杂度
 时间 O(N) 空间 O(N)

 思路
 先修课问题本质上是一个有向图，如果这个图无环，我们可以根据拓扑排序遍历到所有节点，如果有环则拓扑排序无法完成，
 遍历到的节点将少于总节点数，因为有的节点是孤岛。这题我们先根据边的关系，建一个图，并计算每个节点的入度，
 这里用的是数组来建图。然后从入度为0的节点，也就是入口开始广度优先搜索，按照拓扑排序的顺序遍历，最后看遍历过
 的节点数和总节点数的关系就行了。拓扑排序的使用方法参见外文字典。
*/
// 有个英文解释也很不错
// Refer to
// https://leetcode.com/problems/course-schedule/discuss/58799/C%2B%2B-dfs-(backtracking)-and-bfs-(indegree)-methods
/**
 Second method is bfs. First compute the indegree array (how many edges toward a node). Use a stack to push all 
 nodes with indegree 0. Start from a node on the stack and subtract by 1 of all the nodes on its adjacency list. 
 If after subtraction, the node has indegree 0, push it onto stack. Do this recursively for all nodes on stack 
 until no nodes are on stack (you pop a node after subtracting by 1 of all nodes on its adjacency list). If 
 there is at least one node that is never pushed on stack, the graph has a cycle. Too see why draw a digraph 
 with a cycle between two nodes (1->2, 2->1) and for all other nodes draw no cycle. Follow this algorithm you 
 will see the indegree array will be all 0 except for node 1 and 2, which both have indegree 1.
*/
public class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        ArrayList[] graph = new ArrayList[numCourses];
        int[] indegree = new int[numCourses];
        // 先初始化图，每个赋一个空列表
        for(int i = 0; i < numCourses; i++){
            graph[i] = new ArrayList<Integer>();
        }
        // 根据边建立图，并计算入度
        for(int i = 0; i < prerequisites.length; i++){
            graph[prerequisites[i][1]].add(prerequisites[i][0]);
            indegree[prerequisites[i][0]]++;
        }
        // 找到有向图的入口
        Queue<Integer> queue = new LinkedList<Integer>();
        for(int i = 0; i < indegree.length; i++){
            if(indegree[i] == 0){
                queue.add(i);
            }
        }
        // 按照拓扑排序的顺序，进行广度优先搜索
        int cnt = 0;
        while(!queue.isEmpty()){
            Integer curr = queue.poll();
            cnt++;
            ArrayList<Integer> nexts = graph[curr];
            for(int i = 0; i < nexts.size(); i++){
                int next = nexts.get(i);
                indegree[next]--;
                if(indegree[next] == 0){
                    queue.offer(next); 
                }
            }
        }
        return cnt == numCourses;
    }
}

// DFS soltuion:
// https://leetcode.com/problems/course-schedule/discuss/58524/Java-DFS-and-BFS-solution
// https://leetcode.com/problems/course-schedule/discuss/58524/Java-DFS-and-BFS-solution/60036
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if(numCourses == 0 || prerequisites == null || prerequisites.length == 0) {
            return true;
        }
        ArrayList[] graph = new ArrayList[numCourses];
        for(int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<Integer>();
        }
        for(int i = 0; i < prerequisites.length; i++) {
            graph[prerequisites[i][1]].add(prerequisites[i][0]);
        }
        boolean[] visited = new boolean[numCourses];
        for(int i = 0; i < numCourses; i++) {
            if(!helper(i, graph, visited)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean helper(int course, ArrayList[] graph, boolean[] visited) {
        if(visited[course]) {
            return false;
        } else {
            visited[course] = true;   
        }
        for(int i = 0; i < graph[course].size(); i++) {
            int neighbor = (int)graph[course].get(i);
            if(!helper(neighbor, graph, visited)) {
                return false;
            }
        }
        visited[course] = false;
        return true;
    }
}

// You can simply add a dp array in your existing code to prevent revisiting the nodes as well as to deal with cycles.
// The runtime will be greatly improved. (from 63ms to 10ms)
// https://leetcode.com/problems/course-schedule/discuss/58524/Java-DFS-and-BFS-solution/169446
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if(numCourses == 0 || prerequisites == null || prerequisites.length == 0) {
            return true;
        }
        ArrayList[] graph = new ArrayList[numCourses];
        for(int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<Integer>();
        }
        for(int i = 0; i < prerequisites.length; i++) {
            graph[prerequisites[i][1]].add(prerequisites[i][0]);
        }
        boolean[] visited = new boolean[numCourses];
        boolean[] dp = new boolean[numCourses];
        for(int i = 0; i < numCourses; i++) {
            if(!helper(i, graph, visited, dp)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean helper(int course, ArrayList[] graph, boolean[] visited, boolean[] dp) {
        if(visited[course]) {
            return dp[course];
        } else {
            visited[course] = true;   
        }
        for(int i = 0; i < graph[course].size(); i++) {
            int neighbor = (int)graph[course].get(i);
            if(!helper(neighbor, graph, visited, dp)) {
                dp[course] = false;
                return false;
            }
        }
        dp[course] = true;
        return true;
    }
}

// A more readable DFS + Backtracking + Memoization version (But not transform prerequisites into graph, no build graph)
// Runtime: 21 ms, faster than 83.69% of Java online submissions for Course Schedule.
// Memory Usage: 41.4 MB, less than 100% of Java online submissions for Course Schedule.
// Refer to
// https://leetcode.com/problems/course-schedule/discuss/58524/Java-DFS-and-BFS-solution/60015
// Explain refer to
// 
/*
* "dp" is to mark nodes in a "path". If a node is marked and you 
* see it again in a "path", the graph has a cycle.
* "visited" is to mark visited nodes in a graph. Once a node is flaged 
* it will not be used as a starting point to search for cycles 
* (i.e. it is for backtracking)
*/
public class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        boolean[] visited = new boolean[numCourses]; // history
        boolean[] dp = new boolean[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (!canFinishThisCourse(i, prerequisites, visited, dp)) { 
		return false; 
	    }
        }
        return true;
    }
    public boolean canFinishThisCourse(int course, int[][] prerequisites, boolean[] visited, boolean[] dp) {
        if (visited[course]) { 
            return true; 
	}
        if (dp[course]) { 
            return false; // find circle
	}
        // dfs backtracking
        dp[course] = true;
        for (int[] pair : prerequisites) {
            if (pair[0] == course) {
                if (!canFinishThisCourse(pair[1], prerequisites, visited, dp)) { 
                    return false; 
		}
            }
        }
        dp[course] = false;
        visited[course] = true;
        return true;
    }
}

// A more readable DFS + Backtracking + Memoization version (Transform prerequisites into graph and pass on DFS)
// Runtime: 2 ms, faster than 99.69% of Java online submissions for Course Schedule.
// Memory Usage: 41.7 MB, less than 98.46% of Java online submissions for Course Schedule.
// Refer to
// https://leetcode.com/problems/course-schedule/discuss/58524/Java-DFS-and-BFS-solution/60015
/*
* "dp" is to mark nodes in a "path". If a node is marked and you 
* see it again in a "path", the graph has a cycle.
* "visited" is to mark visited nodes in a graph. Once a node is flaged 
* it will not be used as a starting point to search for cycles 
* (i.e. it is for backtracking)
* "neighbor" is the adjacency list. The problem gives us the "edge list"
* it is better to convert it to adjacency list first
* I think both algorithms run in O(V+E) time and O(V) space I do not take the adjacency list into account)
*/
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        boolean[] visited = new boolean[numCourses]; // history
        boolean[] dp = new boolean[numCourses];
        // Build graph based on prerequisites
        ArrayList[] graph = new ArrayList[numCourses];
        for(int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<Integer>();
        }
        for(int i = 0; i < prerequisites.length; i++) {
            graph[prerequisites[i][1]].add(prerequisites[i][0]);
        }
        // Pass graph instead of prerequisites on DFS
        for(int i = 0; i < numCourses; i++) {
            if(!canFinishThisCourse(i, graph, visited, dp)) { 
		 return false; 
	    }
        }
        return true;
    }
    
    public boolean canFinishThisCourse(int course, ArrayList[] graph, boolean[] visited, boolean[] dp) {
        if(visited[course]) { 
            return true; 
	}
        if(dp[course]) { 
            return false; // find circle
	}
        // dfs backtracking
        dp[course] = true;
        for(int i = 0; i < graph[course].size(); i++) {
            int neighbor = (int)graph[course].get(i);
            if(!canFinishThisCourse(neighbor, graph, visited, dp)) { 
                return false;
            }
        }
        dp[course] = false;
        visited[course] = true;
        return true;
    }
}


// Best explaination video
// BFS refer to
// https://www.youtube.com/watch?v=u4v_kvOfumU&t=312s
// https://www.youtube.com/watch?v=0LjVxtLnNOk
// DFS refer to
// https://leetcode.com/problems/course-schedule/discuss/58799/C%2B%2B-dfs-(backtracking)-and-bfs-(indegree)-methods
