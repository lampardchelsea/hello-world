/**
 Refer to
 https://leetcode.com/problems/sliding-puzzle/
 On a 2x3 board, there are 5 tiles represented by the integers 1 through 5, and an empty square represented by 0.

A move consists of choosing 0 and a 4-directionally adjacent number and swapping it.

The state of the board is solved if and only if the board is [[1,2,3],[4,5,0]].

Given a puzzle board, return the least number of moves required so that the state of the board is solved. If it is 
impossible for the state of the board to be solved, return -1.

Examples:
Input: board = [[1,2,3],[4,0,5]]
Output: 1
Explanation: Swap the 0 and the 5 in one move.

Input: board = [[1,2,3],[5,4,0]]
Output: -1
Explanation: No number of moves will make the board solved.

Input: board = [[4,1,2],[5,0,3]]
Output: 5
Explanation: 5 is the smallest number of moves that solves the board.
An example path:
After move 0: [[4,1,2],[5,0,3]]
After move 1: [[4,1,2],[0,5,3]]
After move 2: [[0,1,2],[4,5,3]]
After move 3: [[1,0,2],[4,5,3]]
After move 4: [[1,2,0],[4,5,3]]
After move 5: [[1,2,3],[4,5,0]]
Input: board = [[3,2,4],[1,5,0]]
Output: 14

Note:
board will be a 2 x 3 array as described above.
board[i][j] will be a permutation of [0, 1, 2, 3, 4, 5].
*/

// Solution 1: BFS with each time switch 0 to another position
// Refer to
// https://leetcode.com/problems/sliding-puzzle/discuss/146652/Java-8ms-BFS-with-algorithm-explained
/**
 Consider each state in the board as a graph node, we just need to find out the min distance between start node and final target 
 node "123450". Since it's a single point to single point questions, Dijkstra is not needed here. We can simply use BFS, and also 
 count the level we passed. Every time we swap 0 position in the String to find the next state. Use a hashTable to store the 
 visited states.
 public int slidingPuzzle(int[][] board) {
        String target = "123450";
        String start = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                start += board[i][j];
            }
        }
        HashSet<String> visited = new HashSet<>();
        // all the positions 0 can be swapped to
        int[][] dirs = new int[][] { { 1, 3 }, { 0, 2, 4 },
                { 1, 5 }, { 0, 4 }, { 1, 3, 5 }, { 2, 4 } };
        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        visited.add(start);
        int res = 0;
        while (!queue.isEmpty()) {
            // level count, has to use size control here, otherwise not needed
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String cur = queue.poll();
                if (cur.equals(target)) {
                    return res;
                }
                int zero = cur.indexOf('0');
                // swap if possible
                for (int dir : dirs[zero]) {
                    String next = swap(cur, zero, dir);
                    if (visited.contains(next)) {
                        continue;
                    }
                    visited.add(next);
                    queue.offer(next);

                }
            }
            res++;
        }
        return -1;
    }

    private String swap(String str, int i, int j) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(i, str.charAt(j));
        sb.setCharAt(j, str.charAt(i));
        return sb.toString();
    }
*/
class Solution {
    /**
        index switch table
        from index 0 -> 1 3
        from index 1 -> 0 2 4
        from index 2 -> 1 5
        from index 3 -> 0 4
        from index 4 -> 1 3 5
        from index 5 -> 2 4
    */
    public int slidingPuzzle(int[][] board) {
        String initial = "" + board[0][0] + board[0][1] + board[0][2] + board[1][0] + board[1][1] + board[1][2];
        if(initial.equals("123450")) {
            return 0;
        }
        Map<Integer, int[]> exchangePositions = new HashMap<Integer, int[]>();
        exchangePositions.put(0, new int[] {1, 3});
        exchangePositions.put(1, new int[] {0, 2, 4});
        exchangePositions.put(2, new int[] {1, 5});
        exchangePositions.put(3, new int[] {0, 4});
        exchangePositions.put(4, new int[] {1, 3, 5});
        exchangePositions.put(5, new int[] {2, 4});
        int step = 0;
        Set<String> visited = new HashSet<String>();
        Queue<String> q = new LinkedList<String>();
        q.offer(initial);
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i = 0; i < size; i++) {
                String curr = q.poll();
                if(curr.equals("123450")) {
                    return step;
                }
                int zeroPos = 0;
                for(int j = 0; j < 6; j++) {
                    if(curr.charAt(j) == '0') {
                        zeroPos = j;
                    }
                }
                int[] exchangePos = exchangePositions.get(zeroPos);
                for(int j = 0; j < exchangePos.length; j++) {
                    char[] chars = curr.toCharArray();
                    char temp = chars[exchangePos[j]];
                    chars[exchangePos[j]] = '0';
                    chars[zeroPos] = temp;
                    String next = new String(chars);
                    if(!visited.contains(next)) {
                        q.offer(next);
                        visited.add(next);
                    }
                }
            }
            step++;
        }
        return -1;
    }
}
