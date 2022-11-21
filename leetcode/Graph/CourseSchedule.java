
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
            int startCourse = queue.poll();
            numCourses--;
            // Update indegree for all neighbors of current node
            for(int[] pair : prerequisites) {
                // If any course's pre-course('pair[1]') equal to start 
		// course, we store on queue previously(since we only store
                // total of n courses labeled from 0 to n - 1,
                // this equation relation will be unique for each 
                // start node on queue), update current node('pair[0]')'s
                // indegree by minus 1
                if(pair[1] == startCourse) {
                    indegree[pair[0]]--;
                    // Adding newly matched node onto queue
                    if(indegree[pair[0]] == 0) {
                        queue.offer(pair[0]);
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
		if (list == null) {
		    list = new LinkedList < Integer > ();
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
		for(Integer neighbor: adjacents) { // O(E)
		    indegree[neighbor]--;
		    if (indegree[neighbor] == 0) {
			queue.offer(neighbor);
		    }
		}
	    }
	    // If all courses can be on top logical sort path, then there will
	    // be solution or no solution
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
 I think both algorithms run in O(V+E) time and O(V) space I do not take the adjacency list into account)
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

// DFS solution:
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

// DFS detect cycle
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
































https://leetcode.com/problems/course-schedule/

There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.

- For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.

Return true if you can finish all courses. Otherwise, return false.

Example 1:
```
Input: numCourses = 2, prerequisites = [[1,0]]
Output: true
Explanation: There are a total of 2 courses to take. 
To take course 1 you should have finished course 0. So it is possible.
```

Example 2:
```
Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take. 
To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.
```

Constraints:
- 1 <= numCourses <= 2000
- 0 <= prerequisites.length <= 5000
- prerequisites[i].length == 2
- 0 <= ai, bi < numCourses
- All the pairs prerequisites[i] are unique.
---
Attempt 1: 2022-11-20

Solution 1:  Detect Cycle in a Directed Graph using BFS [Topological Sort ] (10min)

Style 1 (Classic: Build graph + Get indegree based on graph + Topological Sort with BFS)
```
class Solution { 
    public boolean canFinish(int numCourses, int[][] prerequisites) { 
        // Build graph 
        Map<Integer, List<Integer>> graph = new HashMap<Integer, List<Integer>>(); 
        for(int i = 0; i < numCourses; i++) { 
            graph.put(i, new ArrayList<Integer>()); 
        } 
        for(int[] pre : prerequisites) { 
            graph.get(pre[1]).add(pre[0]); 
        } 
        // Get indegree based on graph 
        Map<Integer, Integer> indegree = new HashMap<Integer, Integer>(); 
        for(int node : graph.keySet()) { 
            indegree.put(node, 0); 
        } 
        for(int node : graph.keySet()) { 
            for(int neighbour : graph.get(node)) { 
                indegree.put(neighbour, indegree.get(neighbour) + 1); 
            } 
        } 
        // Topological Sort 
        Queue<Integer> q = new LinkedList<Integer>(); 
        for(int node : indegree.keySet()) { 
            if(indegree.get(node) == 0) { 
                q.offer(node); 
            } 
        } 
        // 'count' for recording number of courses can take 
        int count = 0; 
        while(!q.isEmpty()) { 
            int node = q.poll(); 
            count++; 
            for(int neighbour : graph.get(node)) { 
                indegree.put(neighbour, indegree.get(neighbour) - 1); 
                if(indegree.get(neighbour) == 0) { 
                    q.offer(neighbour); 
                } 
            } 
        } 
        return count == numCourses; 
    } 
}

Time Complexity: O(n+m) 
The overall time complexity of this basic algorithm is O(n+m). The O(n) comes from the number of times that the while loop (and initial for loop) is executed, and the O(m) from the nested for loop.  
Although there is no way to calculate how many times the inner loop will be executed on any one iteration of the outer loop, it will only be executed once for each successor of each member, which means that the total number of times that it will be executed is the total number of successors of all the members -- or the total number of relations. 
Space Complexity: O(n+m) 
Space complexity is also O(n+m). The O(n) component comes from the predecessor count information stored for each member, and the maximum length of the auxiliary queue. The O(m) comes from storing the successors for each member; once again, the total number of successors is the number of relations, so O(m).
```

Style 2 (More elegant)
```
class Solution { 
    public boolean canFinish(int numCourses, int[][] prerequisites) { 
        // Get indegree 
        int[] indegree = new int[numCourses]; 
        for(int[] pre : prerequisites) { 
            indegree[pre[0]]++; 
        } 
        // Topological Sort 
        Queue<Integer> q = new LinkedList<Integer>(); 
        for(int i = 0; i < numCourses; i++) { 
            if(indegree[i] == 0) { 
                q.offer(i); 
            } 
        } 
        while(!q.isEmpty()) { 
            int node = q.poll(); 
            numCourses--; 
            for(int[] pre : prerequisites) { 
                if(pre[1] == node) { 
                    indegree[pre[0]]--; 
                    if(indegree[pre[0]] == 0) { 
                        q.offer(pre[0]); 
                    } 
                } 
            } 
        } 
        return numCourses == 0; 
    } 
}

Time Complexity: O(n+m) 
The overall time complexity of this basic algorithm is O(n+m). The O(n) comes from the number of times that the while loop (and initial for loop) is executed, and the O(m) from the nested for loop.  
Although there is no way to calculate how many times the inner loop will be executed on any one iteration of the outer loop, it will only be executed once for each successor of each member, which means that the total number of times that it will be executed is the total number of successors of all the members -- or the total number of relations. 
Space Complexity: O(n+m) 
Space complexity is also O(n+m). The O(n) component comes from the predecessor count information stored for each member, and the maximum length of the auxiliary queue. The O(m) comes from storing the successors for each member; once again, the total number of successors is the number of relations, so O(m).
```

Solution 2:  Detect Cycle in a Directed Graph using DFS [Backtracking] (10min)

Style 1: DFS helper method name as 'hasCycle', when cycle detected return true
```
class Solution {  
    public boolean canFinish(int numCourses, int[][] prerequisites) {  
        boolean[] visited = new boolean[numCourses];  
        boolean[] recursionStack = new boolean[numCourses];  
        // Build graph  
        Map<Integer, List<Integer>> graph = new HashMap<Integer, List<Integer>>();  
        for(int i = 0; i < numCourses; i++) {  
            graph.put(i, new ArrayList<Integer>());  
        }  
        for(int[] pre : prerequisites) {  
            graph.get(pre[1]).add(pre[0]);  
        }  
        // DFS to find cycle based on each node  
        for(int i = 0; i < numCourses; i++) {  
            // Additional check 'visited' or not to promote 
            // efficiency on the fly since 'visited' will be 
            // update during DFS 
            if(!visited[i]) { 
                // If we have cycle then not able to finish  
                if(hasCycle(i, graph, visited, recursionStack)) {  
                    return false;  
                }                  
            } 
        }  
        return true;  
    }  
      
    private boolean hasCycle(int course, Map<Integer, List<Integer>> graph, boolean[] visited, boolean[] recursionStack) {  
        // If course detect on same path again then find cycle  
        if(recursionStack[course]) {  
            return true;  
        }  
        if(visited[course]) {  
            return false;  
        }  
        visited[course] = true;  
        // Backtrack the current path since switch to other path  
        recursionStack[course] = true;  
        for(int neighbour : graph.get(course)) {  
            if(hasCycle(neighbour, graph, visited, recursionStack)) {  
                return true;  
            }  
        }  
        recursionStack[course] = false;  
        return false;  
    }  
}

Time Complexity: O(n+m) 
Space Complexity: O(n+m)
```

Style 2: DFS helper method name as 'noCycle', when cycle detected return false, exactly reverse logic than Style 1
```
class Solution {  
    public boolean canFinish(int numCourses, int[][] prerequisites) {  
        boolean[] visited = new boolean[numCourses];  
        boolean[] recursionStack = new boolean[numCourses];  
        // Build graph  
        Map<Integer, List<Integer>> graph = new HashMap<Integer, List<Integer>>();  
        for(int i = 0; i < numCourses; i++) {  
            graph.put(i, new ArrayList<Integer>());  
        }  
        for(int[] pre : prerequisites) {  
            graph.get(pre[1]).add(pre[0]);  
        }  
        // DFS to find cycle based on each node  
        for(int i = 0; i < numCourses; i++) {  
            // Additional check 'visited' or not to promote 
            // efficiency on the fly since 'visited' will be 
            // update during DFS 
            if(!visited[i]) { 
                // If we have cycle then not able to finish  
                if(!noCycle(i, graph, visited, recursionStack)) {  
                    return false;  
                }                  
            } 
        }  
        return true;  
    }  
      
    private boolean noCycle(int course, Map<Integer, List<Integer>> graph, boolean[] visited, boolean[] recursionStack) {  
        // If course detect on same path again then find cycle  
        if(recursionStack[course]) {  
            return false;  
        }  
        if(visited[course]) {  
            return true;  
        }  
        visited[course] = true;  
        // Backtrack the current path since switch to other path  
        recursionStack[course] = true;  
        for(int neighbour : graph.get(course)) {  
            if(!noCycle(neighbour, graph, visited, recursionStack)) {  
                return false;  
            }  
        }  
        recursionStack[course] = false;  
        return true;  
    }  
}

Time Complexity: O(n+m) 
Space Complexity: O(n+m)
```

Graph Algorithm - Cycle Detection in Directed Graph using DFS
Refer to
https://dev.to/rohithv07/graph-algorithm-cycle-detection-in-directed-graph-using-dfs-4bl5

What is a Cycle

In graph theory, a path that starts from a given node and ends on the same node is a cycle.

Cycle Detection in an Directed Graph


A directed graph is a set of objects, otherwise called vertices or nodes, connected together and all the edges are directed from one vertex to another. A directed graph is an ordered pair G = (V, E) where, V is a set of elements known as vertices or nodes. E is a set of ordered pair of vertices called as edges or directed edges.

Cycle in a directed graph can be detected with the help of Depth-First Search algorithm.

DFS Algorithm for Cycle Detection in an Directed Graph
```
The dfs algorithm for cycle detection in undirected graph will not work here because we cannot say that directed graph is having a cycle, if we get to a node which is already marked as visited and previous node is different.
```

📌 Initialize a visited boolean array with all nodes unvisited, a boolean recursion stack with all nodes set to false.

A recursion stack is to track the nodes that are currently in recursion. We mark node as true if the node has further recursion calls and change it to false for no recursion calls.

📌 Run a loop from 0 to n - 1 as the graph may have different components.

📌 If the current node is not visited, call the dfs recursive function passing the current node, visited array, recursion stack array.
```
dfs(graph, node, visited, recursionStack)
```

📌 Inside the dfs function, check if the node is already in the recursion stack.

If it is already in the recursion stack, this means we are going to repeat the recursion call which results in a cycle. So we detect cycle in graph and return true.

📌 Check if the node is already visited. If yes, return false.

📌 Mark the node as visited and mark the node in recursion stack.

📌 Traverse through the children of the current node.

📌 Continue doing the recursion for all the children.

📌 If the recursion calls for the current node is over, reset the value to false in the recursion stack array.

📌 If we get out of the initial for loop and all the nodes are now visited, this means we have no cycle.


Example

1. A Directed Graph with No Cycle.

2. A Directed Graph with Cycle.


Time and Space Complexity

- We are traversing through all the nodes and edges. So time complexity will be O(V + E) where V = vertices or node, E = edges.
- We use a visited array, recursion stack array and an adjacency list for the graph. So the space complexity will be O(V) + O(V) + O(V + E) + extra space for the recursion calls.

  
Another different Coloring Algorithm for DFS

Refer to
https://leetcode.com/problems/course-schedule/discuss/58564/detailed-comment-on-dfs-solution-for-course-schedule-i-ii
For the DFS part, I can understand now. Thanks for sharing! For more details, please refer to classic CLRS.
The 0, 1, 2 in visited array corresponds to White, Gray, Black in CLRS. As CLRS says, The key idea is that when we first explore an edge, the color of vertex tells us something about the edge:
1. WHITE (vis[nb] == 0) indicates a tree edge,
2. GRAY (vis[nb] == 1) indicates a back edge, and
3. BLACK (vis[nb] == 2) indicates a forward or cross edge.
```
public boolean canFinish(int n, int[][] prereq) { 
        List<Integer>[] adj = new List[n]; 
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>(); 
        for (int[] e : prereq) adj[e[1]].add(e[0]); 
         
        int[] vis = new int[n];     // reuse so that each node visited once 
        for (int i = 0; i < n; i++) // must check every node. eg.[1,0],[0,1] 
            if (dfs(adj, i, vis)) return false; 
        return true; 
    } 
     
    // Check if back edge (directed cycle) exists. If not => DAG => able to topo sort 
    private boolean dfs(List<Integer>[] adj, int v, int[] vis) { 
        vis[v] = 1; 
        for (int nb : adj[v]) { 
            if (vis[nb] == 1) return true; // visited and nb is v's ancestor => back edge 
            if (vis[nb] == 0 && dfs(adj, nb, vis)) return true; // nb is not visited => tree edge 
            // else vis[nb]==2, nb is visited but not ancestor => forward or cross edge 
        } 
        vis[v] = 2; 
        return false; 
    }
```

The illustration below is an example. F for forward edge, C for cross edge, B for back edge.

Using same idea, we can solve Course Schedule II in DFS manner as well. The key note is Topological Sort order is the reverse order of their finish time.
```
public int[] findOrder(int n, int[][] prereq) { 
        List<Integer>[] adj = new List[n]; 
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>(); 
        for (int[] e : prereq) adj[e[1]].add(e[0]); 
         
        LinkedList<Integer> ret = new LinkedList<>(); 
        int[] vis = new int[n]; 
        for (int i = 0; i < n; i++) 
            if (vis[i] == 0 && dfs(ret, adj, i, vis)) return new int[0]; 
        return ret.stream().mapToInt(i -> i).toArray(); 
    } 
     
    private boolean dfs(LinkedList<Integer> ret, List<Integer>[] adj, int v, int[] vis) { 
        vis[v] = 1; 
        for (int nb : adj[v]) { 
            if (vis[nb] == 1) return true; 
            if (vis[nb] == 0 && dfs(ret, adj, nb, vis)) return true; 
        } 
        vis[v] = 2; 
        ret.addFirst(v); // Topo-sort order is the reverse order of their finish time 
        return false; 
    }
```

Another explain refer to
https://leetcode.com/problems/course-schedule/discuss/658275/C%2B%2B-DFS-Easiest-Solution-With-Explanation-(My-1st-approach)
We just have to find if our graph contains cycle or not because if graph contains cycle then all t nodes in cycle are interdependent and 1 course cannot be completed because its prerequisite is dependent on other course and it goes on .We used coloring algorithm to find if there is cycle in graph or not.

Coloring Algorithm
vis[id]=0 is used for node which is not yet visited
vis[id]=1 is used for the node which is visited and currently its child nodes are being visited
vis[id]=2 done when all the child nodes of a node ("id") are visited and the function returns to parent node of node ("id")
So at that time it is marked as 2 because this node does not require any further traversing.
```
bool iscycle(vector<int> adj[],vector<int> &vis,int id){ 
        if(vis[id]==1) 
            return true; 
        if(vis[id]==0){ 
            vis[id]=1; 
            for(auto edge : adj[id]){ 
                if(iscycle(adj,vis,edge)) 
                    return true; 
            } 
        } 
        vis[id] = 2; 
        return false; 
    } 
    bool canFinish(int n, vector<vector<int>>& pre) { 
        vector<int> adj[n]; 
        for(auto edge : pre) 
            adj[edge[1]].push_back(edge[0]); 
        vector<int> vis(n,0); 
         
        for(int i=0;i<n;i++){ 
            if(iscycle(adj,vis,i)) 
                return false; 
        } 
        return true; 
    }
```

