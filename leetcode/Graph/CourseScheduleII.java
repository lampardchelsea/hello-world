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

// Topological Sort solution
// Refer to
// Use the same strategy as Course Schedule, just adding an ArrayList to store the course visiting sequence on path
// https://segmentfault.com/a/1190000003814058
// Runtime: 3 ms, faster than 97.37% of Java online submissions for Course Schedule II.
// Memory Usage: 42.8 MB, less than 96.34% of Java online submissions for Course Schedule II.
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        if(numCourses == 0 || prerequisites == null) {
            return new int[0];
        }
        int[] indegree = new int[numCourses];
        ArrayList[] graph = new ArrayList[numCourses];
        for(int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<Integer>();
        }
        for(int i = 0; i < prerequisites.length; i++) {
            graph[prerequisites[i][1]].add(prerequisites[i][0]);
            indegree[prerequisites[i][0]]++;
        }
        Queue<Integer> queue = new LinkedList<Integer>();
        for(int i = 0; i < numCourses; i++) {
            if(indegree[i] == 0) {
                queue.offer(i);
            }
        }
        // Adding path to record BFS visiting course sequence
        List<Integer> path = new ArrayList<Integer>();
        int count = 0;
        while(!queue.isEmpty()) {
            int course = queue.poll();
            count++;
            // Add current course on path
            path.add(course);
            ArrayList<Integer> neighbors = graph[course];
            for(int i = 0; i < neighbors.size(); i++) {
                int neighbor = neighbors.get(i);
                indegree[neighbor]--;
                if(indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        // Convert result into array
        int[] result = new int[path.size()];
        for(int i = 0; i < result.length; i++) {
            result[i] = path.get(i);
        }
        if(count == numCourses) {
            return result;
        } else {
            return new int[0];
        }
    }
}

// DFS solution
// Refer to
// https://leetcode.com/problems/course-schedule-ii/discuss/383621/Better-than-97-93-DFS-JAVA
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        if(numCourses == 0 || prerequisites == null) {
            return new int[0];
        }
        // mark node visited after visiting all its neighbors, if any.
        boolean[] visited = new boolean[numCourses]; 
        // keep track of elements in dfs recusion, to detect loop. 
        // Note that visited array alone cannot suffice since we mark 
        // a node visited only after visiting all its neighbors.
        boolean[] dp = new boolean[numCourses];
        ArrayList[] graph = new ArrayList[numCourses];
        for(int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<Integer>();
        }
        for(int i = 0; i < prerequisites.length; i++) {
            graph[prerequisites[i][1]].add(prerequisites[i][0]);
        }
        // Adding path to record DFS visiting course sequence
        List<Integer> path = new ArrayList<Integer>();
        for(int i = 0; i < numCourses; i++) {
            if(!canFinishThisCourse(path, i, graph, visited, dp)) {
                return new int[0];
            }
        }
        // Important !!! Need to revese the path, since the last node add first
        // as recursive to the deepest level first, and 'path.add(course);' 
        // happen there, or use stack instead of ArrayList to store
        // https://efficientcodeblog.wordpress.com/2017/11/28/topological-sort-dfs-bfs-and-dag/
        /**
        DFS Implementation  Mechanism for Topological Sort:
        While doing a DFS we will search for the sink vertices, and as we get a sink 
        vertex we will disconnect that vertex from the graph and will then backtrack 
        and search for the next sink vertex along the search path, until all the vertices 
        in the graph are visited. This way we will get all the vertices in totally 
        reverse order of that of what a topological ordering should be. So we would need 
        to reverse the order to get the topological sort.
        */
        Collections.reverse(path);
        int[] result = new int[path.size()];
        for(int i = 0; i < path.size(); i++) {
            result[i] = path.get(i);
        }
        return result;
    }
    
    private boolean canFinishThisCourse(List<Integer> path, int course, ArrayList[] graph, boolean[] visited, boolean[] dp) {
        // Why return true ?
        // Refer to
        // https://leetcode.com/problems/course-schedule-ii/discuss/59317/Two-AC-solution-in-Java-using-BFS-and-DFS-with-explanation/60618
        // which means the subtree whose root is i has been dfs traversed and all the nodes in 
        // subtree has been put in the result(if we request), so we do not need to traverse it again
        if(visited[course]) {
            return true;
        }
        // loop detected
        if(dp[course]) {
            return false;
        }
        // dfs backtracking
        dp[course] = true;
        for(int i = 0; i < graph[course].size(); i++) {
            int neighbor = (int)graph[course].get(i);
            if(!canFinishThisCourse(path, neighbor, graph, visited, dp)) {
                return false;
            }
        }
        dp[course] = false;
        // since all neighbors are now visited for n, mark true.
        visited[course] = true;
        // add to final result.
        path.add(course);
        // since no loop detected, return true
        return true;
    }
}

// Also refer to 
// https://leetcode.com/problems/course-schedule-ii/discuss/59317/Two-AC-solution-in-Java-using-BFS-and-DFS-with-explanation































https://leetcode.com/problems/course-schedule-ii/

There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.

- For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.

Return the ordering of courses you should take to finish all courses. If there are many valid answers, return any of them. If it is impossible to finish all courses, return an empty array.

Example 1:
```
Input: numCourses = 2, prerequisites = [[1,0]]
Output: [0,1]
Explanation: There are a total of 2 courses to take. To take course 1 you should have finished course 0. So the correct course order is [0,1].
```

Example 2:
```
Input: numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
Output: [0,2,1,3]
Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0.
So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3].
```

Example 3:
```
Input: numCourses = 1, prerequisites = []
Output: [0]
```

Constraints:
- 1 <= numCourses <= 2000
- 0 <= prerequisites.length <= numCourses * (numCourses - 1)
- prerequisites[i].length == 2
- 0 <= ai, bi < numCourses
- ai != bi
- All the pairs [ai, bi] are distinct.
---
Attempt 1: 2022-11-20

Solution 1:  Detect Cycle in a Directed Graph using BFS [Topological Sort ] (10min)
```
class Solution { 
    public int[] findOrder(int numCourses, int[][] prerequisites) { 
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
        int[] result = new int[numCourses]; 
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
            result[count] = node; 
            count++;  
            for(int neighbour : graph.get(node)) {  
                indegree.put(neighbour, indegree.get(neighbour) - 1);  
                if(indegree.get(neighbour) == 0) {  
                    q.offer(neighbour);  
                }  
            }  
        }  
        if(count == numCourses) { 
            return result; 
        } else { 
            return new int[]{}; 
        } 
    } 
}

Time Complexity: O(n+m) 
The overall time complexity of this basic algorithm is O(n+m). The O(n) comes from the number of times that the while loop (and initial for loop) is executed, and the O(m) from the nested for loop.  
Although there is no way to calculate how many times the inner loop will be executed on any one iteration of the outer loop, it will only be executed once for each successor of each member, which means that the total number of times that it will be executed is the total number of successors of all the members -- or the total number of relations. 
Space Complexity: O(n+m) 
Space complexity is also O(n+m). The O(n) component comes from the predecessor count information stored for each member, and the maximum length of the auxiliary queue. The O(m) comes from storing the successors for each member; once again, the total number of successors is the number of relations, so O(m).
```

Solution 2:  Detect Cycle in a Directed Graph using DFS [Backtracking] (10min)
```
class Solution { 
    public int[] findOrder(int numCourses, int[][] prerequisites) { 
        List<Integer> tmp = new ArrayList<Integer>(); 
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
                if(hasCycle(i, graph, visited, recursionStack, tmp)) {   
                    return new int[]{};   
                }                   
            }  
        }   
        int[] result = new int[numCourses]; 
        for(int i = 0; i < numCourses; i++) { 
            result[i] = tmp.get(i); 
        } 
        return result; 
    } 
     
    private boolean hasCycle(int course, Map<Integer, List<Integer>> graph, boolean[] visited, boolean[] recursionStack, List<Integer> tmp) {   
        // If course detect on same path again then find cycle   
        if(recursionStack[course]) {   
            return true;   
        }   
        if(visited[course]) {   
            return false;   
        }      
        // Don't add 'course' before 'no cycle' confirmed 
        // Test out: 
        // Input: 4, [[1,0],[2,0],[3,1],[3,2]] 
        // Output: [0,1,3,2] 
        // Expected: [0,2,1,3] 
        //tmp.add(course); 
        visited[course] = true;   
        // Backtrack the current path since switch to other path   
        recursionStack[course] = true;   
        for(int neighbour : graph.get(course)) {   
            if(hasCycle(neighbour, graph, visited, recursionStack, tmp)) {   
                return true;   
            }   
        } 
        recursionStack[course] = false;   
        // The only difference than L207.Course Schedule which record path 
        // 1. Must put 'course' into path reversely, always insert at index = 0 
        // 2. Only add 'course' after 'no cycle' confirmed 
        tmp.add(0, course); 
        return false;   
    }    
}

Time Complexity: O(n+m) 
The overall time complexity of this basic algorithm is O(n+m). The O(n) comes from the number of times that the while loop (and initial for loop) is executed, and the O(m) from the nested for loop.  
Although there is no way to calculate how many times the inner loop will be executed on any one iteration of the outer loop, it will only be executed once for each successor of each member, which means that the total number of times that it will be executed is the total number of successors of all the members -- or the total number of relations. 
Space Complexity: O(n+m) 
Space complexity is also O(n+m). The O(n) component comes from the predecessor count information stored for each member, and the maximum length of the auxiliary queue. The O(m) comes from storing the successors for each member; once again, the total number of successors is the number of relations, so O(m).
```

Refer to
https://leetcode.com/problems/course-schedule-ii/discuss/59317/Two-AC-solution-in-Java-using-BFS-and-DFS-with-explanation/60618
```
public class Solution { 
    public int[] findOrder(int numCourses, int[][] prerequisites) { 
        //prepare 
        List<List<Integer>> graph = new ArrayList<>(); 
        for(int i = 0; i < numCourses; i++){ 
            graph.add(new ArrayList<>()); 
        } 
        for(int[] pair : prerequisites){ 
            int prev = pair[1]; 
            int next = pair[0]; 
            graph.get(prev).add(next); 
        } 
        Map<Integer, Integer> visited = new HashMap<>(); 
        //initail visited 
        for(int i = 0; i < numCourses; i++){ 
            visited.put(i, 0);//0 -> unvisited, 1 -> visiting, 2 -> visited 
        } 
        List<Integer> res = new ArrayList<>(); 
        for(int i = 0; i < numCourses; i++){ 
            if(!topoSort(res, graph, visited, i)) return new int[0]; 
        } 
        int[] result = new int[numCourses]; 
        for(int i = 0; i < numCourses; i++){ 
            result[i] = res.get(numCourses - i - 1); 
        } 
        return result; 
    } 
    //the return value of this function only contains the ifCycle info and does not interfere dfs process. if there is Cycle, then return false 
    private boolean topoSort(List<Integer> res, List<List<Integer>> graph, Map<Integer, Integer> visited, int i){ 
        int visit = visited.get(i); 
        if(visit == 2){//when visit = 2, which means the subtree whose root is i has been dfs traversed and all the nodes in subtree has been put in the result(if we request), so we do not need to traverse it again 
            return true; 
        }if(visit == 1){ 
            return false; 
        } 
        visited.put(i, 1); 
        for(int j : graph.get(i)){ 
            if(!topoSort(res, graph, visited, j)) return false; 
        } 
        visited.put(i, 2); 
        res.add(i);//the only difference with traversing a graph 
        return true; 
    } 
}
```
