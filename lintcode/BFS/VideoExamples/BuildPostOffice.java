import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/**
 * Refer to
 * https://segmentfault.com/a/1190000006970751
 * Given a 2D grid, each cell is either an house 1 or empty 0 (the number zero, one), 
 * find the place to build a post office, the distance that post office to all the 
 * house sum is smallest. Return the smallest distance. Return -1 if it is not possible.
	Notice
	You can pass through house and empty.
	You only build post office on an empty.
	Example
	Given a grid:
	0 1 0 0 
	1 0 1 1 
	0 1 0 0
 * return 6. (Placing a post office at (1,1), the distance that post office to 
 * all the house sum is smallest.)
 * 
 * Solution
 * https://zhengyang2015.gitbooks.io/lintcode/build_post_office_573.html
 * 这道题可以用dp来做，但是会超时。首先扫描一遍将所有house的点记录下来，然后遍历图中所有0的点，计算每个0的点到这些house的距离和，
 * 选最小的那个即可。这种情况下可以优化到O(k * n ^ 2)，但是如果数据量很大还是过不了。
 * 因此需要减少搜索的点。想到的方法是在所有房子围成的形状的重心位置附近建邮局则到所有房子的距离之和最短（怎么证明？）。因此步骤如下：
 * 首先找到所有房子的重心。找所有房子x值的median和y值的median（如果是奇数个就是排序后取中间值，如果是偶数则取中间两个数再取
 * 平均值）即为重心。
 * 然后用bfs来搜索。将重心加入queue中，然后开始一圈一圈（将出队的每个点周围八个点加入队中）向外找，用的是和逐层遍历二叉树的类似
 * 的方法（即在每一层开始的时候记录一下本层的点的个数，然后一次出队这么多点即可将本层的点全部出队）。每一圈结束时，返回该圈上的点作
 * 为post office能取的最小值，如果存在则停止搜索。即如果存在可以作为post office的点，则外圈点到各个房子的距离一定不会比
 * 内圈点更优。
 */
public class BuildPostOffice {
    private class Coordinate {
        int x;
        int y;
        boolean visited;
        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
            this.visited = false;
        }
    }

    public int shortestDistance(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return -1;
        }
        // Magic numbers
        int[] directionX = {0, 0, -1, 1, -1, 1, -1, 1};
        int[] directionY = {-1, 1, 0, 0, -1, -1, 1, 1};
        int m = grid.length;
        int n = grid[0].length;

        List < Coordinate > houses = new ArrayList < Coordinate > ();
        List < Integer > xArr = new ArrayList < Integer > ();
        List < Integer > yArr = new ArrayList < Integer > ();
        // Find house positions
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    houses.add(new Coordinate(i, j));
                    xArr.add(i);
                    yArr.add(j);
                }
            }
        }
        // No empty space
        if (houses.size() == m * n) {
            return -1;
        }
        // If no house
        if (houses.size() == 0) {
            return 0;
        }
        // Find median of house positions
        int xMedian = getMedian(xArr);
        int yMedian = getMedian(yArr);
        // BFS
        Queue < Coordinate > queue = new LinkedList < Coordinate > ();
        Coordinate median = new Coordinate(xMedian, yMedian);
        queue.add(median);
        int minDistance = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            // Need level traverse as outside level is surely 
            // bad than inside level positions
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Coordinate coor = queue.poll();
                coor.visited = true;
                if (grid[coor.x][coor.y] == 0) {
                    minDistance = Math.min(minDistance, search(houses, coor));
                }
                // Check all its 8 directions adjacent for smaller path sum
                for (int j = 0; j < 8; j++) {
                    Coordinate adj = new Coordinate(coor.x + directionX[j], coor.y + directionY[j]);
                    if (!inBound(adj, grid) || adj.visited) {
                        continue;
                    }
                    queue.add(adj);
                }
            }
            if (minDistance != Integer.MAX_VALUE) {
                return minDistance;
            }
        }
        return -1;
    }

    private boolean inBound(Coordinate coor, int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        return coor.x >= 0 && coor.x < m && coor.y >= 0 && coor.y < n;
    }

    private int search(List < Coordinate > houses, Coordinate coor) {
        int sum = 0;
        for (Coordinate house: houses) {
            sum += Math.abs(coor.x - house.x) + Math.abs(coor.y - house.y);
        }
        return sum;
    }

    private int getMedian(List < Integer > list) {
        Collections.sort(list);
        int median = list.get(list.size() / 2);
        if (list.size() % 2 == 0) {
            median = (median + list.get(list.size() / 2 - 1)) / 2;
        }
        return median;
    }

    public static void main(String[] args) {
        BuildPostOffice b = new BuildPostOffice();
        int[][] grid = {{0, 1, 0, 0}, {1, 0, 1, 1}, {0, 1, 0, 0}};
        int result = b.shortestDistance(grid);
        System.out.println(result);
    }
}
