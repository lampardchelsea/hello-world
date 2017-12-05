/**
 * Refer to
 * https://leetcode.com/problems/sudoku-solver/description/
 * 
 * Solution
 * https://discuss.leetcode.com/topic/11327/straight-forward-java-solution-using-backtracking
*/
class Solution {
    public void solveSudoku(char[][] board) {
        if(board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return;
        }
        helper(board);
    }
    
    private boolean helper(char[][] board) {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(board[i][j] == '.') {
                    for(char c = '1'; c <= '9'; c++) { //trial. Try 1 through 9
                        if(isValid(board, i, j, c)) {
                            board[i][j] = c; //Put c for this cell
                            if(helper(board)) {
                                return true;
                            } else {
                                board[i][j] = '.'; //If it's the solution return true
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean isValid(char[][] board, int row, int col, char c) {
        for(int i = 0; i < 9; i++) {
            if(board[row][i] != '.' && board[row][i] == c) { //check row
                return false;
            }
            if(board[i][col] != '.' && board[i][col] == c) { //check column
                return false;
            }
            if(board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] != '.' && board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c) {
                return false; //check 3*3 block
            }
        }
        return true;
    }
    
}
