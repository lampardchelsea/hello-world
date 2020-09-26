/**
 Refer to
 https://leetcode.com/problems/minesweeper/
 Let's play the minesweeper game (Wikipedia, online game)!

You are given a 2D char matrix representing the game board. 'M' represents an unrevealed mine, 'E' represents an unrevealed empty square, 
'B' represents a revealed blank square that has no adjacent (above, below, left, right, and all 4 diagonals) mines, digit ('1' to '8') 
represents how many mines are adjacent to this revealed square, and finally 'X' represents a revealed mine.

Now given the next click position (row and column indices) among all the unrevealed squares ('M' or 'E'), return the board after revealing 
this position according to the following rules:

If a mine ('M') is revealed, then the game is over - change it to 'X'.
If an empty square ('E') with no adjacent mines is revealed, then change it to revealed blank ('B') and all of its adjacent unrevealed 
squares should be revealed recursively.
If an empty square ('E') with at least one adjacent mine is revealed, then change it to a digit ('1' to '8') representing the number 
of adjacent mines.
Return the board when no more squares will be revealed.

Example 1:
Input: 
[['E', 'E', 'E', 'E', 'E'],
 ['E', 'E', 'M', 'E', 'E'],
 ['E', 'E', 'E', 'E', 'E'],
 ['E', 'E', 'E', 'E', 'E']]
Click : [3,0]
Output: 
[['B', '1', 'E', '1', 'B'],
 ['B', '1', 'M', '1', 'B'],
 ['B', '1', '1', '1', 'B'],
 ['B', 'B', 'B', 'B', 'B']]

Explanation:

Example 2:
Input: 
[['B', '1', 'E', '1', 'B'],
 ['B', '1', 'M', '1', 'B'],
 ['B', '1', '1', '1', 'B'],
 ['B', 'B', 'B', 'B', 'B']]
Click : [1,2]
Output: 
[['B', '1', 'E', '1', 'B'],
 ['B', '1', 'X', '1', 'B'],
 ['B', '1', '1', '1', 'B'],
 ['B', 'B', 'B', 'B', 'B']]

Explanation:

Note:
The range of the input matrix's height and width is [1,50].
The click position will only be an unrevealed square ('M' or 'E'), which also means the input board contains at least one clickable square.
The input board won't be a stage when game is over (some mines have been revealed).
For simplicity, not mentioned rules should be ignored in this problem. For example, you don't need to reveal all the unrevealed mines 
when the game is over, consider any cases that you will win the game or flag any squares.
*/

// Solution 1: DFS
// Refer to
// https://leetcode.com/problems/minesweeper/discuss/99841/Straight-forward-Java-solution
// https://leetcode.com/problems/minesweeper/discuss/99826/Java-Solution-DFS-%2B-BFS
/**
This is a typical Search problem, either by using DFS or BFS. Search rules:
1.If click on a mine ('M'), mark it as 'X', stop further search.
2.If click on an empty cell ('E'), depends on how many surrounding mine:
  2.1 Has surrounding mine(s), mark it with number of surrounding mine(s), stop further search.
  2.2 No surrounding mine, mark it as 'B', continue search its 8 neighbors.

Why no need a 'visited' check in Minesweeper ?
And we don't need to initial a 'boolean[][] visited' to avoid infinite loop on DFS because
in each step we either update a cell 'E' to 'B' or digit number or update a cell 'M' to 'X',
and in the DFS check, we have "board[x][y] != 'E'" to block any possibility to step into a 
cell twice to update it.
*/
class Solution {
    public char[][] updateBoard(char[][] board, int[] click) {
        int x = click[0];
        int y = click[1];
        if(board[x][y] == 'M') {
            board[x][y] = 'X';
            return board;
        }
        helper(board, x, y);
        return board;
    }
    
    int[] dx = {1,1,0,-1,-1,-1,0,1};
    int[] dy = {0,-1,-1,-1,0,1,1,1};
    private void helper(char[][] board, int x, int y) {
        if(x < 0 || x >= board.length || y < 0 || y >= board[0].length || board[x][y] != 'E') {
            return;
        }
        int num = getSurroundMinesNum(board, x, y);
        if(num == 0) {
            board[x][y] = 'B';
            for(int i = 0; i < 8; i++) {
                int new_x = x + dx[i];
                int new_y = y + dy[i];
                helper(board, new_x, new_y);
            }
        } else {
            board[x][y] = (char)(num + '0');
        }
    }
    
    private int getSurroundMinesNum(char[][] board, int x, int y) {
        int count = 0;
        for(int i = 0; i < 8; i++) {
            int new_x = x + dx[i];
            int new_y = y + dy[i];
            if(new_x >= 0 && new_x < board.length && new_y >= 0 && new_y < board[0].length && board[new_x][new_y] == 'M') {
               count++;
            }
        }
        return count;
    }
}

// Solution 2: BFS
// Refer to
// https://leetcode.com/problems/minesweeper/discuss/99826/Java-Solution-DFS-+-BFS/135989
/**
A minor improvement - You don't need to check the case 'board[r][c] == 'X'', when you count the the mines adjacent to a cell.
Also the check for 'mines' inside the while loop is not required as well, since you will never 'reach' a mine during traversal.
*/
// https://leetcode.com/problems/minesweeper/discuss/99841/Straight-forward-Java-solution/104052
/**
  if (num == 0) {
      board[curX][curY] = 'B';
      for (int i = -1; i < 2; i++) {
          for (int j = -1; j < 2; j++) {
              if (i == 0 && j == 0) continue;
              int newX = curX + i, newY = curY + j;
              if (newX < 0 || newY < 0 || newX >= board.length || newY >= board[0].length || board[newX][newY] != 'E') continue;
              queue.offer(newX * n + newY);
              board[newX][newY] = 'B'; // Avoid being added again
          }
      }
  } else {
      board[curX][curY] = (char)(num + '0');
  }
*/
class Solution {
    int[] dx = {1,1,0,-1,-1,-1,0,1};
    int[] dy = {0,-1,-1,-1,0,1,1,1};
    public char[][] updateBoard(char[][] board, int[] click) {
        int x = click[0];
        int y = click[1];
        if(board[x][y] == 'M') {
            board[x][y] = 'X';
            return board;
        }
        Queue<int[]> q = new LinkedList<int[]>();
        q.offer(new int[] {x, y});
        while(!q.isEmpty()) {
            int[] curr = q.poll();
            /**
              Refer to
              https://leetcode.com/problems/minesweeper/discuss/99826/Java-Solution-DFS-+-BFS/135989
              A minor improvement - You don't need to check the case 'board[r][c] == 'X'', 
              when you count the the mines adjacent to a cell.
              Also the check for 'mines' inside the while loop is not required as well, 
              since you will never reach a 'mine' during traversal.
            */
            //if(board[curr[0]][curr[1]] == 'M') {
            //    board[curr[0]][curr[1]] = 'X';
            //    return board;
            //}
            int num = getSurroundMinesNum(board, curr[0], curr[1]);
            if(num == 0) {
                board[curr[0]][curr[1]] = 'B';
                for(int i = 0; i < 8; i++) {
                    int new_x = curr[0] + dx[i];
                    int new_y = curr[1] + dy[i];
                    if(new_x >= 0 && new_x < board.length && new_y >= 0 && new_y < board[0].length && board[new_x][new_y] == 'E') {
                        q.offer(new int[] {new_x, new_y});
                        // Don't forget to update 'E' to 'B', otherwise infinite loop
                        // In DFS we actually do the same way to avoid using 'visited'
                        board[new_x][new_y] = 'B';
                    }
                }
            } else {
                board[curr[0]][curr[1]] = (char)(num + '0');
            }
        }
        return board;
    }
        
    private int getSurroundMinesNum(char[][] board, int x, int y) {
        int count = 0;
        for(int i = 0; i < 8; i++) {
            int new_x = x + dx[i];
            int new_y = y + dy[i];
            if(new_x >= 0 && new_x < board.length && new_y >= 0 && new_y < board[0].length && board[new_x][new_y] == 'M') {
               count++;
            }
        }
        return count;
    }
}
