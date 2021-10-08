import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Maze {
    public static int mazeBFS(int numRows, int numCols, List<List<Integer>> lot) {
        int[][] map = new int[numRows][numCols];
        boolean[][] visited = new boolean[numRows][numCols];
        for(int i = 0; i < lot.size(); i++) {
            List<Integer> row = lot.get(i);
            for(int j = 0;  j < row.size(); j++) {
                map[i][j] = row.get(j);
            }
        }
        int ans = 0;
        int[][] DIR = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] {0, 0});
        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int x = curr[0];
                int y = curr[1];
                if(x < 0 || y < 0 || x >= numRows || y >= numCols || map[x][y] == 0 || visited[x][y]) {
                    continue;
                }
                visited[x][y] = true;
                if (map[x][y] == 9) {
                    return ans;
                }
                for(int[] dir : DIR) {
                    int xx = x + dir[0];
                    int yy = y + dir[1];
                    queue.offer(new int[]{xx, yy});
                }
            }
            ans++;
        }
        return -1;
    }
}