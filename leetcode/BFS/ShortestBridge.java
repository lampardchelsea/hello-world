/**
 Refer to
 https://leetcode.com/problems/shortest-bridge/
 In a given 2D binary array A, there are two islands.  (An island is a 4-directionally connected group of 1s not connected to any other 1s.)
 Now, we may change 0s to 1s so as to connect the two islands together to form 1 island.
 Return the smallest number of 0s that must be flipped.  (It is guaranteed that the answer is at least 1.)
 
 Example 1:
 Input: A = [[0,1],[1,0]]
 Output: 1
 
 Example 2:
 Input: A = [[0,1,0],[0,0,0],[0,0,1]]
 Output: 2

 Example 3:
 Input: A = [[1,1,1,1,1],[1,0,0,0,1],[1,0,1,0,1],[1,0,0,0,1],[1,1,1,1,1]]
 Output: 1

 Constraints:
 2 <= A.length == A[0].length <= 100
 A[i][j] == 0 or A[i][j] == 1
*/

// Solution 1: Java DFS find the island -> BFS expand the island
// Style 1: Use boolean flag with break out
// Refer to
// https://leetcode.com/problems/shortest-bridge/discuss/189321/Java-DFS-find-the-island-greater-BFS-expand-the-island
class Solution {
    int[] dx = new int[] {0,0,1,-1};
    int[] dy = new int[] {1,-1,0,0};
    // Use a boolean flag to control if we already found the initial
    // points of 1st island, if we found 1 initial point we should
    // break out both for loop to stop find another initial point
    // because we only need 1 initial point and DFS based on it to
    // find all other cells of 1st island, otherwise if you find another
    // initial point it will belong to 2nd island
    boolean found = false;
    public int shortestBridge(int[][] A) {
        // DFS to mark first island all '1' into '2'
        for(int i = 0; i < A.length; i++) {
            if(found) {
                break;
            }
            for(int j = 0; j < A[0].length; j++) {
                if(A[i][j] == 1) {
                    helper(A, i, j);
                    found = true;
                    break;
                }
            }
        }
        // Put all '2' into queue as candidate initial start points
        Queue<int[]> q = new LinkedList<int[]>();
        for(int i = 0; i < A.length; i++) {
            for(int j = 0; j < A[0].length; j++) {
                if(A[i][j] == 2) {
                    q.offer(new int[] {i, j});
                }
            }
        }
        int distance = 0;
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i = 0; i < size; i++) {
                int[] cur = q.poll();
                for(int j = 0; j < 4; j++) {
                    int new_x = cur[0] + dx[j];
                    int new_y = cur[1] + dy[j];
                    if(new_x >= 0 && new_x < A.length && new_y >= 0 && new_y < A[0].length) {
                        if(A[new_x][new_y] == 1) {
                            return distance;
                        } else if(A[new_x][new_y] == 0) {
                            // Update from 0 to 2 which means expand first island boundary
                            // which also avoid using visited to check
                            A[new_x][new_y] = 2;
                            q.offer(new int[] {new_x, new_y});
                        }
                    }
                }
            }
            distance++;
        }
        return distance;
    }
    
    // No need visited since update value from 1 to 2
    private void helper(int[][] A, int x, int y) {
        if(x >= 0 && x < A.length && y >= 0 && y < A[0].length && A[x][y] == 1) {
            A[x][y] = 2;
            for(int i = 0; i < 4; i++) {
                int new_x = x + dx[i];
                int new_y = y + dy[i];
                helper(A, new_x, new_y);
            }
        }
    }
}

// Style 2: Use boolean flag directly on condition of for loop
// Refer to
// https://leetcode.com/problems/shortest-bridge/discuss/189321/Java-DFS-find-the-island-greater-BFS-expand-the-island/258399
class Solution {
    int[] dx = new int[] {0,0,1,-1};
    int[] dy = new int[] {1,-1,0,0};
    // Use a boolean flag to control if we already found the initial
    // points of 1st island, if we found 1 initial point we should
    // break out both for loop to stop find another initial point
    // because we only need 1 initial point and DFS based on it to
    // find all other cells of 1st island, otherwise if you find another
    // initial point it will belong to 2nd island
    boolean found = false;
    public int shortestBridge(int[][] A) {
        // DFS to mark first island all '1' into '2'
        for(int i = 0; i < A.length && !found; i++) {
            for(int j = 0; j < A[0].length && !found; j++) {
                if(A[i][j] == 1) {
                    helper(A, i, j);
                    found = true;
                }
            }
        }
        // Put all '2' into queue as candidate initial start points
        Queue<int[]> q = new LinkedList<int[]>();
        for(int i = 0; i < A.length; i++) {
            for(int j = 0; j < A[0].length; j++) {
                if(A[i][j] == 2) {
                    q.offer(new int[] {i, j});
                }
            }
        }
        int distance = 0;
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i = 0; i < size; i++) {
                int[] cur = q.poll();
                for(int j = 0; j < 4; j++) {
                    int new_x = cur[0] + dx[j];
                    int new_y = cur[1] + dy[j];
                    if(new_x >= 0 && new_x < A.length && new_y >= 0 && new_y < A[0].length) {
                        if(A[new_x][new_y] == 1) {
                            return distance;
                        } else if(A[new_x][new_y] == 0) {
                            // Update from 0 to 2 which means expand first island boundary
                            // which also avoid using visited to check
                            A[new_x][new_y] = 2;
                            q.offer(new int[] {new_x, new_y});
                        }
                    }
                }
            }
            distance++;
        }
        return distance;
    }
    
    // No need visited since update value from 1 to 2
    private void helper(int[][] A, int x, int y) {
        if(x >= 0 && x < A.length && y >= 0 && y < A[0].length && A[x][y] == 1) {
            A[x][y] = 2;
            for(int i = 0; i < 4; i++) {
                int new_x = x + dx[i];
                int new_y = y + dy[i];
                helper(A, new_x, new_y);
            }
        }
    }
}
