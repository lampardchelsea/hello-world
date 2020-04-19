/**
 Refer to
 https://www.cnblogs.com/lz87/p/11280484.html
 There are N courses, labelled from 1 to N.

We are given relations[i] = [X, Y], representing a prerequisite relationship between course X and 
course Y: course X has to be studied before course Y.

In one semester you can study any number of courses as long as you have studied all the prerequisites 
for the course you are studying.

Return the minimum number of semesters needed to study all courses.  If there is no way to study all 
the courses, return -1.

Example 1:
Input: N = 3, relations = [[1,3],[2,3]]
Output: 2
Explanation: 
In the first semester, courses 1 and 2 are studied. In the second semester, course 3 is studied.

Example 2:
Input: N = 3, relations = [[1,2],[2,3],[3,1]]
Output: -1
Explanation: 
No course can be studied because they depend on each other.

Note:
1 <= N <= 5000
1 <= relations.length <= 5000
relations[i][0] != relations[i][1]
There are no repeated relations in the input.
*/

// Solution 1: Topological Sort (BFS)
// Refer to
// https://www.cnblogs.com/lz87/p/11280484.html
/**
Since we can take as many courses as we want, taking all courses that do not have outstanding 
prerequisites each semester must be one valid answer that gives a minimum number of semesters needed. 

1.  Construct a directed graph and in degree of each node(course).
2. Add all courses that do not have any prerequisites(in degree == 0) to a queue.
3. BFS on all neighboring nodes(courses) of the starting courses and decrease their indegrees by 1.  
If a neighboring course's indegree becomes 0, it means all its prerequisites are met, add it to 
queue as this course can be taken in the next semester. 
4. After all neighboring nodes of the courses that are taken in the current semester have been 
visited, increment semester count by 1. Repeat until the queue is empty.
5. When doing the BFS, keep a count of courses taken, compare this count with the total number 
of courses to determine if it is possible to study all courses.

Both the runtime and space are O(V+E).
*/
class Solution {
    public int minimumSemesters(int N, int[][] relations) {
        Map < Integer, Set < Integer >> graph = new HashMap < Integer, Set < Integer >> ();
        int[] indegree = new int[N + 1];
        for (int i = 1; i < N + 1; i++) {
            graph.put(i, new HashSet < Integer > ());
        }
        for (int[] relation: relations) {
            if (!graph.get(relation[0]).contains(relation[1])) {
                graph.get(relation[0]).add(relation[1]);
                indegree[relation[1]]++;
            }
        }
        int semester = 0;
        int courseAbleToTake = 0;
        Queue < Integer > queue = new LinkedList < Integer > ();
        for (int i = 1; i < N + 1; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            int size = queue.size();
            courseAbleToTake += size;
            for (int i = 0; i < size; i++) {
                int curr = queue.poll();
                for (int neighbor: graph.get(curr)) {
                    indegree[neighbor]--;
                    if (indegree[neighbor] == 0) {
                        queue.offer(neighbor);
                    }
                }
            }
            semester++;
        }
        return courseAbleToTake == N ? semester : -1;
    }
}

// Solution 2: Topological Sort (DFS)
// Refer to
// https://www.cnblogs.com/lz87/p/11280484.html
/**
 Because we have to first take all prerequisites of a course beforing taking it, we know the 
 following two property of this problem.
1. If there is a cycle, there is no way of studying all courses.
2. If there is no cycle, the minimum number of semesters needed to study all courses is determined 
by the longest acyclic path, i.e, we are looking for the longest acyclic path with each edge's 
weight being 1. This is exactly the same problem with Max path value in directed graph. The only 
difference is the dynamic programming state.
Both runtime and space are O(V+E).
*/

