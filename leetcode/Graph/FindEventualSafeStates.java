/**
 Refer to
 https://leetcode.com/problems/find-eventual-safe-states/
 In a directed graph, we start at some node and every turn, walk along a directed edge of the graph.  
 If we reach a node that is terminal (that is, it has no outgoing directed edges), we stop.

Now, say our starting node is eventually safe if and only if we must eventually walk to a terminal node.  
More specifically, there exists a natural number K so that for any choice of where to walk, we must 
have stopped at a terminal node in less than K steps.

Which nodes are eventually safe?  Return them as an array in sorted order.

The directed graph has N nodes with labels 0, 1, ..., N-1, where N is the length of graph.  The graph 
is given in the following form: graph[i] is a list of labels j such that (i, j) is a directed edge of the graph.

Example:
Input: graph = [[1,2],[2,3],[5],[0],[5],[],[]]
Output: [2,4,5,6]
Here is a diagram of the above graph.

Illustration of graph

Note:
graph will have length at most 10000.
The number of edges in the graph will not exceed 32000.
Each graph[i] will be a sorted list of different integers, chosen within the range [0, graph.length - 1].
*/

// Understanding the problem
// Refer to
// https://leetcode.com/problems/find-eventual-safe-states/discuss/244269/cut-the-crap-in-problem-description
/**
 'Now, say our starting node is eventually safe if and only if we must eventually walk to a terminal node. 
 More specifically, there exists a natural number K so that for any choice of where to walk, we must have 
 stopped at a terminal node in less than K steps.'
 ain't it just saying 'A node is evetually safe if all path from the node ends at a terminal node'
*/

// Who is the center ? who is the neighbor ?
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Graph/CourseSchedule.java
/**
 For Course Schedule, we need to find initial indegree = 0 course, and from defition we know for a[0, 1], 
 all present as 1 -> 0
 course 1 is pre-requisite of course 0, so indegree for course 1 is 0, for course 0 is 1, that's why we 
 have indegree[0]++
 to implement toplogical sort, we should define initial indegree = 0 course which is course 1 as center
 and course 0 as its neighbor, then when remove course 1 and remove relation between course 1 and 0, the
 next will able to remove course 0 next.
 ----------------------
 course 1 -> course 0
 indegree[course 0]++
 course 1 indegree = 0 -> center -> what we want as initial toplogical start point
 course 0 indegree = 1 -> neighbor
 so course 0 is neighbor of course 1
 ----------------------
 // 根据边建立图，并计算入度
 for(int i = 0; i < prerequisites.length; i++){
     graph[prerequisites[i][1]].add(prerequisites[i][0]); --> prerequisites[i][1] as course 1 and prerequisites[i][0] as course 0
     indegree[prerequisites[i][0]]++;
 }
 ======================================================================================================
 For Find Eventual Safe States, we need to find initial outdegree = 0 state, and from definition we know
 for (state 0 -> state 1) means state 1 has outdegree 0 and state 0 has outdegree 1. In case of implementing
 toplogical sort start from outdegree = 0 state, we need state 1 as center, and state 0 as its neighbor
 to build graph, then we able to remove state 1 first and remove state 0 next.
 ----------------------
 state 0 -> state 1
 outdegree[state 0]++
 state 1 outdegree = 0 -> center ->  what we want as initial toplogical start point
 state 0 indegree = 1 -> neighbor
 so state 0 is neighbor of state 1
 ----------------------
 for(int i = 0; i < graph.length; i++) {
     for(int element : graph[i]) {
         neighbors.putIfAbsent(element, new HashSet<Integer>());
         neighbors.get(element).add(i);  --> element could be state 1 as center and i as state 0 as neighbor which has outdegree > 0
         out_degree[i]++;
     }
 }
*/


// Solution 1: Tological Sort
// Refer to
// https://leetcode.com/problems/find-eventual-safe-states/discuss/120633/Java-Solution-(DFS-andand-Topological-Sort)
/**
 Using degree array to record the out-degree, neighbors map to record In-neighbors(for example 0->1, 2->1, map(1) = [0, 2]).
 Add the node whose out-degree is 0 into queue and result Set, then process each node in the queue, if the out-degree 
 of one node becomes 0, add it to queue until queue is empty.
*/
// https://www.cnblogs.com/grandyang/p/9319966.html
/**
 这道题给了我们一个有向图，然后定义了一种最终安全状态的结点，就是说该结点要在自然数K步内停止，所谓停止的意思，就是再没有向外的边，
 即没有出度，像上面例子中的结点5和6就是出度为0，因为graph[5]和graph[6]均为空。那么我们分析题目中的例子，除了没有出度的结点5和6之外，
 结点2和4也是安全状态结点，为啥呢，我们发现结点2和4都只能到达结点5，而结点5本身就是安全状态点，所以2和4也就是安全状态点了，
 所以我们可以得出的结论是，若某结点唯一能到达的结点是安全状态结点的话，那么该结点也同样是安全状态结点。那么我们就可以从没有出度的
 安全状态往回推，比如结点5，往回推可以到达结点4和2，先看结点4，此时我们先回推到结点4，然后将这条边断开，那么此时结点4出度为0，
 则标记结点4也为安全状态结点，同理，回推到结点2，断开边，此时结点2虽然入度仍为2，但是出度为0了，标记结点2也为安全状态结点。

分析到这里，思路应该比较明朗了，由于我们需要回推边，所以需要建立逆向边，用一个集合数组来存，由于题目要求返回的结点有序，我们可以利用
集合TreeSet的自动排序的特性，由于需要断开边，为了不修改输入数据，所以我们干脆再建一个顺向边得了，即跟输入数据相同。还需要一个safe数组，
布尔型的，来标记哪些结点是安全状态结点。在遍历结点的时候，直接先将出度为0的安全状态结点找出来，排入一个队列queue中，方便后续的处理。
后续的处理就有些类似BFS的操作了，我们循环非空queue，取出队首元素，标记safe中该结点为安全状态结点，然后遍历其逆向边的结点，即可以到达
当前队首结点的所有结点，我们在正向边集合中删除对应的边，如果此时结点出度为0了，将其加入队列queue中等待下一步处理，这样while循环退出后，
所有的安全状态结点都已经标记好了，我们直接遍历safe数组，将其存入结果res中即可
*/
// Runtime: 59 ms, faster than 24.16% of Java online submissions for Find Eventual Safe States.
// Memory Usage: 56.1 MB, less than 100.00% of Java online submissions for Find Eventual Safe States.
class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        List<Integer> result = new ArrayList<Integer>();
        int[] out_degree = new int[graph.length];
        Map<Integer, Set<Integer>> neighbors = new HashMap<Integer, Set<Integer>>();
        for(int i = 0; i < graph.length; i++) {
            for(int element : graph[i]) {
                neighbors.putIfAbsent(element, new HashSet<Integer>());
                neighbors.get(element).add(i);
                out_degree[i]++;
            }
        }
        Queue<Integer> queue = new LinkedList<Integer>();
        for(int i = 0; i < graph.length; i++) {
            if(out_degree[i] == 0) {
                queue.offer(i);
                result.add(i);
            }
        }
        while(!queue.isEmpty()) {
            int cur = queue.poll();
            if(neighbors.containsKey(cur)) {
                Set<Integer> set = neighbors.get(cur);
                for(int a : set){
                    out_degree[a]--;
                    if(out_degree[a] == 0) {
                        result.add(a);
                        queue.offer(a);
                    }
                }
            }
        }
        Collections.sort(result);
        return result;
    }
}

// DFS detect cycle
// Solution 2: DFS
// https://leetcode.com/problems/find-eventual-safe-states/discuss/138233/Java-find-cycle-method-23ms
// https://leetcode.com/problems/course-schedule/discuss/58524/Java-DFS-and-BFS-solution/60015
/**
 * "currentVisited" is to mark nodes in a "path". If a node is marked and you 
 * see it again in a "path", the graph has a cycle.
 * "globalVisited" is to mark visited nodes in a graph. Once a node is flaged 
 * it will not be used as a starting point to search for cycles 
 * (i.e. it is for backtracking)
*/
class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        List<Integer> result = new ArrayList<Integer>();
        boolean[] globalVisited = new boolean[graph.length];
        boolean[] currentVisited = new boolean[graph.length];
        for(int i = 0; i < graph.length; i++) {
            if(helper(i, graph, globalVisited, currentVisited)) {
                result.add(i);
            }
        }
        Collections.sort(result);
        return result;
    }
    
    private boolean helper(int state, int[][] graph, boolean[] globalVisited, boolean[] currentVisited) {
        if(globalVisited[state]) {
            return true;
        }
        if(currentVisited[state]) {
            return false; // find circle
        }
        currentVisited[state] = true;
        for(int next : graph[state]) {
            if(!helper(next, graph, globalVisited, currentVisited)) {
                return false;
            }
        }
        currentVisited[state] = false;
        globalVisited[state] = true;
        return true;
    }
}



// Solution 3: DFS with 3 status
// Refer to
// https://leetcode.com/problems/find-eventual-safe-states/discuss/120633/Java-Solution-(DFS-andand-Topological-Sort)
// https://www.cnblogs.com/grandyang/p/9319966.html
/**
 我们再来看一种DFS遍历有向图的解法。仔细分析题目中的例子，不难发现，之所以某些结点不是安全状态，因为有环的存在，而环经过的所有结点，
 一定不是安全状态结点，所以我们可以通过DFS遍历有向图来找出环即可。在大多数的算法中，经典的DFS遍历法对于结点都有三种状态标记，white，
 gray，和black，其中white表示结点还未遍历，gray表示正在遍历邻结点，black表示已经结束该结点的遍历。那么我们可以对每个结点都调用递归函数，
 在递归函数中，如果当前结点不是white，表示该结点已经访问过了，那么如果当前结点是black，直接返回true，如果是gray，直接返回false，
 因为遇到gray的结点，表示一定有环存在。否则我们给结点标记gray，然后开始遍历所有邻接结点，如果某个邻结点是black，直接跳过该结点。
 如果某个邻结点是gray，或者对该邻结点调用递归返回false了，说明当前结点是环结点，返回false。如果循环结束了，当前结点标记为black，
 并且返回true
*/

// when color[i] = 1 means node i is visiting.
// when color[i] = 0 means node i is not visited.
// when color[i] = 2 means node i has been already visited.
// when color[i] = 1 and it is visited again, it is not safe, otherwise it is safe.
class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
       int N = graph.length;
       int[] color = new int[N];
       List<Integer> res = new ArrayList<>();
       for (int i = 0; i < N; i++) {
           if (dfs(i, color, graph))
               res.add(i);
       }
       return res;
   }
    
   private boolean dfs(int i, int[] color, int[][] graph) {
       if (color[i] > 0) {
           return color[i] == 2;
       }
       
       color[i] = 1;
       for (int neighbor : graph[i]) {
           if (color[neighbor] == 2) continue;
           
           if (color[neighbor] == 1 || !dfs(neighbor, color, graph)) 
               return false;
       }
       color[i] = 2;
       return true;
   }
}





