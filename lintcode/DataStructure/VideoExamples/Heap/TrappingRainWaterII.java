/**
 * Refer to
 * https://leetcode.com/problems/trapping-rain-water-ii/description/
 * http://www.lintcode.com/en/problem/trapping-rain-water-ii/
 *
 * Solution
 * https://aaronice.gitbooks.io/lintcode/content/heap/trapping_rain_water_ii.html
 * 本题是Trapping Rain Water的follow up，I中是循环两遍记录每个位置左右两侧的最高水柱，
   而II在二维的灌水情境中，则需要从外围向内包围查找，记录最小的柱高，也就是木桶原理，最矮的柱子决定了灌水的高度。
   从最外围一圈向内部遍历，记录包围“墙”的最小柱高，可以利用min-heap（PriorityQueue）
   记录遍历过的点visited[][]
   对于min-heap的堆顶元素，假设高度h，查找其周围4个方向上未曾访问过的点
   如果比h高，则说明不能装水，但是提高了“围墙”最低高度，因此将其加入min-heap中，设置元素被访问
   如果比h矮，则说明可以向其中灌水，且灌水高度就是h - h'，其中h'是当前访问的柱子高度，同样的，要将其加入min heap中，
   （且该元素高度记为灌水后的高度，也就是h，可以设想为一个虚拟的水位高度），设置元素被访问
   此外，为了方便，可以定义一个Cell类，包含其坐标x,y，以及高度h，并定义其Comparator规则（也可以在初始化PriorityQueue的时候定义）。
 * 
 * http://www.cnblogs.com/grandyang/p/5928987.html
 * https://discuss.leetcode.com/topic/60418/java-solution-using-priorityqueue
 * https://github.com/lampardchelsea/hello-world/blob/master/lintcode/DataStructure/VideoExamples/Heap/Document/%5BLeetCode%5D%20Trapping%20Rain%20Water%20II%20%E6%94%B6%E9%9B%86%E9%9B%A8%E6%B0%B4%E4%B9%8B%E4%BA%8C%20-%20Grandyang%20-%20%E5%8D%9A%E5%AE%A2%E5%9B%AD.pdf
*/
public class Solution {
    public int trapRainWater(int[][] heightMap) {
        if(heightMap == null || heightMap.length == 0 || heightMap[0].length == 0) {
            return 0;
        }
        int sum = 0;
        int rows = heightMap.length;
        int columns = heightMap[0].length;
        boolean[][] visited = new boolean[rows][columns];
        // Create min heap
        PriorityQueue<Tuple> pq = new PriorityQueue<Tuple>(rows * columns, new Comparator<Tuple>() {
            public int compare(Tuple a, Tuple b) {
                return a.height - b.height;
            }
        });
        // Add first and last column onto pq
        for(int i = 0; i < rows; i++) {
            pq.add(new Tuple(i, 0, heightMap[i][0]));
            pq.add(new Tuple(i, columns - 1, heightMap[i][columns - 1]));
            visited[i][0] = true;
            visited[i][columns - 1] = true;
        }
        // Add first and last row onto pq
        for(int i = 0; i < columns; i++) {
            pq.add(new Tuple(0, i, heightMap[0][i]));
            pq.add(new Tuple(rows - 1, i, heightMap[rows - 1][i]));
            visited[0][i] = true;
            visited[rows - 1][i] = true;
        }
        // Magic direction array
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, -1, 0, 1};
        while(!pq.isEmpty()) {
            Tuple cur = pq.poll();
            // Check adjacency tuples and if not visited added onto pq
            for(int i = 0; i < 4; i++) {
                int next_x = cur.x + dx[i];
                int next_y = cur.y + dy[i];
                if(next_x >= 0 && next_x < rows && next_y >= 0 && next_y < columns && !visited[next_x][next_y]) {
                    int next_height = heightMap[next_x][next_y];
                    // Set up next position height based on max value between current position and next position
                    pq.add(new Tuple(next_x, next_y, Math.max(cur.height, next_height)));
                    visited[next_x][next_y] = true;
                    // Only fill water on next position when current position has larger height
                    sum += Math.max(0, cur.height - next_height);
                }
            }
        }
        return sum;
    }
    
}

class Tuple {
    int x;
    int y;
    int height;
    public Tuple(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
    }
}
