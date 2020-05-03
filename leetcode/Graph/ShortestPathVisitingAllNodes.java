/**
 Refer to
 https://leetcode.com/problems/shortest-path-visiting-all-nodes/
 An undirected, connected graph of N nodes (labeled 0, 1, 2, ..., N-1) is given as graph.
 graph.length = N, and j != i is in the list graph[i] exactly once, if and only if nodes i and j are connected.
 Return the length of the shortest path that visits every node. You may start and stop at any node, 
 you may revisit nodes multiple times, and you may reuse edges.

 Example 1:
 Input: [[1,2,3],[0],[0],[0]]
 Output: 4
 Explanation: One possible path is [1,0,2,0,3]
 
 Example 2:
 Input: [[1],[0,2,4],[1,3,4],[2],[1,2]]
 Output: 4
 Explanation: One possible path is [0,1,4,2,3]
 
 Note:
 1 <= graph.length <= 12
 0 <= graph[i].length < graph.length
*/

// Solution 1: BFS
// Refer to
// https://leetcode.com/problems/shortest-path-visiting-all-nodes/discuss/135809/Fast-BFS-Solution-(46ms)-Clear-Detailed-Explanation-Included
/**
 Idea is to use BFS to traverse the graph. Since all edges are weighted 1, we can use a Queue (instead of 
 a PriorityQueue sorted by cost). Since all edges are weighted 1, then closer nodes will always be evaluated 
 before farther ones.
 
 In order to represent a path, I used a combination of 3 variables:
 1.int bitMask: mask of all the nodes we visited so far: 0 -> not visited, 1 -> visited (Originally this was 
 Set<Integer>of all nodes we visited so far, but since the Solution TLE and N <= 12, it turns out we can 
 use a bitMask and 32 bits total in an Integer can cover all the possibilities. This is a small speed up optimization.)
 2.int curr: current node we are on
 3.int cost: the total cost in the path.
 
 Each path taken will have a unique combination of these 3 variables.

 We initialize our queue to contain N possible paths each starting from [0,N-1]. This is because we can 
 start at any of N possible Nodes.
 
 At each step, we remove element from the queue and see if we have covered all 12 nodes in our bitMask. 
 
 If we cover all nodes, we return the cost of the path immediately. Since we are using BFS, this is 
 guranteed to be path with the lowest cost.
 
 Otherwise, we get all the neighbors of current node, and for each neighbor, set the Node to visited in bitMask, 
 and then add it back into the queue.
 
 In order to prevent duplicate paths from being visited, we use a Set<Tuple> to store the Set<Path> that we have 
 visited before. Since we don't really need the cost here, I set cost to 0 for elements stored in Set. You could
 also set the actual cost value here, it wouldn't make a difference :)
*/

// https://leetcode.com/problems/shortest-path-visiting-all-nodes/discuss/135809/Fast-BFS-Solution-(46ms)-Clear-Detailed-Explanation-Included/237938
/**
 I think we could get rid of the cost attribute just by looping the queue by levels. It will cause less confusion
*/

// What is 1 << 0?
// https://stackoverflow.com/questions/18215681/what-is-1-0
class Solution {
    public int shortestPathLength(int[][] graph) {
        int n = graph.length;
        // e.g n = 4, fullMask = 00010000 - 1 = 00001111
        int fullMask = (1 << n) - 1;
        Set<String> visited = new HashSet<String>();
        Queue<Node> queue = new LinkedList<Node>();
        for(int i = 0; i < n; i++) {
            // e.g i = 0, node mask = 00000001
            //     i = 1, node mask = 00000010
            //     i = 2, node mask = 00000100
            //     i = 3, node mask = 00001000
            Node node = new Node(i, 1 << i);
            queue.offer(node);
            visited.add(node.toString());
        }
        int level = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int i = 0; i < size; i++) {
                Node node = queue.poll();
                if(node.mask == fullMask) {
                    return level;
                }
                for(int next : graph[node.id]) {
                    // For 'node.mask | (1 << next)' is following the same
                    // style to encode the visited status for each node into
                    // bitmask by (1 << next), after encode then use 'OR' logic
                    // to reserve both different visited nodes as 'next' and
                    // 'node' status into 'nextNode' bitmask
                    // e.g For node = 0, next = 1, we have node.mask = 00000001
                    // which we get previously by 1 << 0, next.mask we get here
                    // as 1 << 1 = 00000010, then 00000001 | 00000010 = 00000011
                    // so, on 'nextNode' the bitmask is 3 which store node = 0
                    // and node = 1 both status as 'visited = 1' on certain bits
                    Node nextNode = new Node(next, node.mask | (1 << next));
                    if(visited.contains(nextNode.toString())) {
                        continue;
                    }
                    queue.offer(nextNode);
                    visited.add(nextNode.toString());
                }
            }
            level++;
        }
        return level;
    }
}

class Node {
    int id;
    int mask;
    public Node(int id, int mask) {
        this.id = id;
        this.mask = mask;
    }
    public String toString() {
        return id + " " + mask;
    }
}





