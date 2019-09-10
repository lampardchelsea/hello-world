/**
 Refer to
 http://leetcode.liangjiateng.cn/leetcode/the-maze-ii/description
 There is a ball in a maze with empty spaces and walls. The ball can go through empty spaces by rolling up, 
 down, left or right, but it won't stop rolling until hitting a wall. When the ball stops, it could choose 
 the next direction.

Given the ball's start position, the destination and the maze, find the shortest distance for the ball to 
stop at the destination. The distance is defined by the number of empty spaces traveled by the ball from 
the start position (excluded) to the destination (included). If the ball cannot stop at the destination, return -1.

The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space. You may assume 
that the borders of the maze are all walls. The start and destination coordinates are represented by row 
and column indexes.

Example 1:
Input 1: a maze represented by a 2D array
0 0 1 0 0
0 0 0 0 0
0 0 0 1 0
1 1 0 1 1
0 0 0 0 0
Input 2: start coordinate (rowStart, colStart) = (0, 4)
Input 3: destination coordinate (rowDest, colDest) = (4, 4)
Output: 12
Explanation: One shortest way is : left -> down -> left -> down -> right -> down -> right.
             The total distance is 1 + 1 + 3 + 1 + 2 + 2 + 2 = 12.
             
Example 2:
Input 1: a maze represented by a 2D array
0 0 1 0 0
0 0 0 0 0
0 0 0 1 0
1 1 0 1 1
0 0 0 0 0
Input 2: start coordinate (rowStart, colStart) = (0, 4)
Input 3: destination coordinate (rowDest, colDest) = (3, 2)
Output: -1
Explanation: There is no way for the ball to stop at the destination.

Note:
There is only one ball and one destination in the maze.
Both the ball and the destination exist on an empty space, and they will not be at the same position initially.
The given maze does not contain border (like the red rectangle in the example pictures), but you could assume the border of the maze are all walls.
The maze contains at least 2 empty spaces, and both the width and height of the maze won't exceed 100.
*/

// Solution 1: Dijkstra shortest path
// Refer to
// https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/
// https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-in-java-using-priorityqueue/
// https://massivealgorithms.blogspot.com/2019/02/leetcode-505-maze-ii.html
/**
 我们还可以使用迪杰克斯特拉算法Dijkstra Algorithm来做，LeetCode中能使用到此类高级算法的时候并不多，Network Delay Time 
 就是一次。该算法是主要是在有向权重图中计算单源最短路径，即单个点到任意点到最短路径。因为这里起点只有一个，所以适用，
 然后迷宫中的每个格子都可以看作是图中的一个结点，权重可以都看成是1，那么就可以当作是有向权重图来处理。Dijkstra算法的
 核心是松弛操作Relaxtion，当有对边 (u, v) 是结点u到结点v，如果 dist(v) > dist(u) + w(u, v)，那么 dist(v) 就可以被更新，
 这是所有这些的算法的核心操作。Dijkstra算法是以起点为中心，向外层层扩展，直到扩展到终点为止。根据这特性，用BFS来实现时
 再好不过了。为了加快运算速度，我们使用一个优先队列（最小堆）来代替普通的queue，这样我们就能尽量先更新离起点近的位置的dp值，
 优先队列里同时也存了该点到起点的距离，这个距离不一定是最短距离，可能还能松弛。但是如果其dp值已经小于优先队列中保存的距离，
 那么就不必更新其周围位置的距离了，因为优先队列中保存的这个距离值不是最短的，使用它来更新周围的dp值没有意义。这相当于利用
 了松弛操作来进行剪枝，大大提高了运算效率，之后就是类似于之前的BFS的操作了，遍历其周围的四个位置，尝试去更新其dp值。最后
 还是跟之前一样，如果遍历到了终点，就不要再排入队列了，因为已经得到需要的结果.
*/
class Solution {
    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        // base case
        if(Arrays.equals(start, destination)) {
            return 0;
        }
        return shortestPath(maze, start, destination);
    }
    
    int[] dx = new int[]{0,0,1,-1};
    int[] dy = new int[]{-1,-1,0,0};
    private int shortestPath(int[][] maze, int[] start, int[] destination) {
        // get the vertice has the minimum distance to start
        PriorityQueue<Node> minPQ = new PriorityQueue<Node>((a, b) -> a.distance - b.distance);
        minPQ.offer(new Node(start[0], start[1], 0));
        // visited 2d array contains information of node: distance to start point
        int[][] visited = new int[maze.length][maze[0].length];
        for(int[] arr : visited) {
            Arrays.fill(arr, Integer.MAX_VALUE);
        }
        while(!minPQ.isEmpty()) {
            Node cur = minPQ.pull();
            // Find the shortest path
            if(cur.x == destination[0] && cur.y == destination[1]) {
                return cur.distance;
            }
            for(int k = 0; k < 4; k++) {
                int x = cur.x;
                int y = cur.y;
                while(x >= 0 && x < maze.length && y >= 0 && y < maze[0].length && maze[x][y] != 1) {
                    x += dx[k];
                    y += dy[k];
                }
                int distance = cur.distance + Math.abs(x - cur.x) + Math.abs(y - cur.y);
                if(visited[x][y] > distance) {
                    minPQ.offer(new Node(cur.x, cur.y, distance));
                    visited[x][y] = distance;
                }
            }
            return -1;
        }
    }
    
    class Node {
        int x;
        int y;
        int distance;
        public Node(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance; // distance to start point
        }
    }
}    
