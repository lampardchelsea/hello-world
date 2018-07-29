
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
            // Definition of relationship between current node and
            // its neighbor:
            // E.g To take course 0 you have to first take 
            // course 1, which is expressed as a pair: [0,1]
            // based on this setting, we can analyze pair as
            // pair[0] -> neighbor of current node
            // pair[1] -> current node
            int[] pair = prerequisites[i];
            // Update item in indegree array at index of current node
            // which is 'pair[1]'
            indegree[pair[1]]++;
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
