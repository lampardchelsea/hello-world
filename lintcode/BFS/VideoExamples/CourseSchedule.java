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

// Solution 2: 10ms (With adjacency list)
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
public class Solution {
    /**
     * @param numCourses a total of n courses
     * @param prerequisites a list of prerequisite pairs
     * @return true if can finish all courses or false
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
       // Initialize all nodes adjacency data structure
       List<Integer>[] allAdjacencies = new List[numCourses];
       int[] indegree = new int[numCourses];
       
       for(int[] pair : prerequisites) {
           List<Integer> list = allAdjacencies[pair[1]];
           if(list == null) {
               list = new LinkedList<Integer>();
               allAdjacencies[pair[1]] = list;
           }
           list.add(pair[0]);
           indegree[pair[0]]++;
       }
       
       Queue<Integer> queue = new LinkedList<Integer>();
       for(int i = 0; i < numCourses; i++) {
           if(indegree[i] == 0) {
               queue.offer(i);
           }
       }
       
       while(!queue.isEmpty()) {
           numCourses--;
           int node = queue.poll();
           List<Integer> adjacency = allAdjacencies[node];
           if(adjacency == null) {
               continue;
           }
           for(Integer neighbor : adjacency) {
               indegree[neighbor]--;
               if(indegree[neighbor] == 0) {
                   queue.offer(neighbor);
               }
           }
       }
       
       return numCourses == 0;
    }
}
