/**
 * Refer to
 * https://leetcode.com/problems/course-schedule-ii/description/
 * There are a total of n courses you have to take, labeled from 0 to n - 1.
 * Some courses may have prerequisites, for example to take course 0 you have to first 
 * take course 1, which is expressed as a pair: [0,1]
 * Given the total number of courses and a list of prerequisite pairs, return the ordering 
 * of courses you should take to finish all courses.
 * There may be multiple correct orders, you just need to return one of them. 
 * If it is impossible to finish all courses, return an empty array.

    For example:
    2, [[1,0]]
    There are a total of 2 courses to take. To take course 1 you should have finished course 0. 
    So the correct course order is [0,1]
    4, [[1,0],[2,0],[3,1],[3,2]]
    There are a total of 4 courses to take. To take course 3 you should have finished both 
    courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0. 
    So one correct course order is [0,1,2,3]. Another correct ordering is[0,2,1,3].

    Note:
    The input prerequisites is a graph represented by a list of edges, not adjacency matrices. 
    Read more about how a graph is represented.
    You may assume that there are no duplicate edges in the input prerequisites.
    click to show more hints.

 * Hints:
 * This problem is equivalent to finding the topological order in a directed graph. 
 * If a cycle exists, no topological ordering exists and therefore it will be impossible to take all courses.
 * Topological Sort via DFS - A great video tutorial (21 minutes) on Coursera explaining 
 * the basic concepts of Topological Sort.
 * Topological sort could also be done via BFS.
 *
 * Solution
 * https://discuss.leetcode.com/topic/27940/concise-java-solution-based-on-bfs-with-comments
*/
public class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // Do not include condition as 'prerequisites.length == 0',
        // because if given input as numCourses = 1, and prerequisites = [],
        // expected it should return [0]
        if(numCourses == 0) {
            return null;
        }
        List<Integer>[] allAjacencies = new List[numCourses];
        int[] indegree = new int[numCourses];
        // Use order and index to record the path
        int[] order = new int[numCourses];
        int index = 0;
        for(int[] pair : prerequisites) {
            List<Integer> list = allAjacencies[pair[1]];
            if(list == null) {
                list = new LinkedList<Integer>();
                allAjacencies[pair[1]] = list;
            }
            list.add(pair[0]);
            indegree[pair[0]]++;
        }
        Queue<Integer> queue = new LinkedList<Integer>();
        for(int i = 0; i < numCourses; i++) {
            if(indegree[i] == 0) {
                queue.offer(i);
                order[index++] = i;
            }
        }
        while(!queue.isEmpty()) {
            numCourses--;
            int node = queue.poll();
            List<Integer> adjacency = allAjacencies[node];
            if(adjacency == null) {
                continue;
            }
            for(int neighbor : adjacency) {
                indegree[neighbor]--;
                if(indegree[neighbor] == 0) {
                    order[index++] = neighbor;
                    queue.offer(neighbor);
                }
            }
        }
        
        // If find a path, which means numCourses drop to 0,
        // return order, otherwise return empty array
        return (numCourses == 0) ? order : new int[0];
    }
}
