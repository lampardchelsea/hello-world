/**
 * Refer to
 * https://leetcode.com/problems/surrounded-regions/description/
 * Given a 2D board containing 'X' and 'O' (the letter O), capture all regions surrounded by 'X'.

    A region is captured by flipping all 'O's into 'X's in that surrounded region.

    For example,
    X X X X
    X O O X
    X X O X
    X O X X
    After running your function, the board should be:

    X X X X
    X X X X
    X X X X
    X O X X

 *
 * Solution
 * https://www.programcreek.com/2014/04/leetcode-surrounded-regions-java/
*/
// Be careful must use upper case 'O'
class Solution {
    /**
     First, check the four border of the matrix. 
     If there is a element is ’O’, alter it and all its 
     neighbor ‘O’ elements to ‘1’. Then ,alter all the ‘O’ to ‘X’
     At last,alter all the ‘1’ to ‘O’
     For example:
     X X X X           X X X X             X X X X
     X X O X  ->       X X O X    ->       X X X X
     X O X X           X 1 X X             X O X X
     X O X X           X 1 X X             X O X X
    */
    int[] dx = new int[]{0,1,-1,0};
    int[] dy = new int[]{1,0,0,-1};
    public void solve(char[][] board) {
        if(board == null || board.length == 0 || board[0].length == 0) {
            return;
        }
        int rows = board.length; // rows number
        int cols = board[0].length; // columns number

        // check first and last row if 'O' exist or its adjacency also 'O'
        for(int i = 0; i < cols; i++) {
            if(board[0][i] == 'O') {
                dfs(board, 0, i);
            }
            if(board[rows - 1][i] == 'O') {
                dfs(board, rows - 1, i);
            }
        }
        // check first and last column if 'O' exist or its adjacency also 'O'
        for(int i = 0; i < rows; i++) {
            if(board[i][0] == 'O') {
                dfs(board, i, 0);
            }
            if(board[i][cols - 1] == 'O') {
                dfs(board, i, cols - 1);
            }
        }
        // after dfs search boarded 'O' and its adjacent cells 'O' to '1'
        // check if still left 'O', if exist then set it to 'x',
        // then revert all '1' back to 'O'
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if(board[i][j] == '1') {
                    board[i][j] = 'O';
                }
            }
        }
    }
    
    // dfs to set current cell and its adjacent cell to '1' if it is 'O'
    private void dfs(char[][] board, int row, int col) {
        // Base condition to terminate dfs (If cell not 'o', directly stop search)
        if(row < 0 || row >= board.length || col < 0 || col >= board[0].length || board[row][col] != 'O') {
            return;
        }
        board[row][col] = '1';
        for(int i = 0; i < 4; i++) {
            dfs(board, row + dx[i], col + dy[i]);
        }
    }

}
