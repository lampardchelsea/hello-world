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
 *
 */
public class CourseSchedule {
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
                // If current node('pair[0]') equal to start node
                // we store on queue previously(since we only store
                // total of n courses labeled from 0 to n - 1,
                // this equation relation will be unique for each 
                // start node on queue), update its neighbor('pair[1]')'s
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
	
	public static void main(String[] args) {
		CourseSchedule c = new CourseSchedule();
		int numCourses = 2;
		int[][] prerequisites = {{1, 0}};
		boolean result = c.canFinish(numCourses, prerequisites);
		System.out.println(result);
	}
}
