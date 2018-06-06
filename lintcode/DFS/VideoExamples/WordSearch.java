/**
 * Refer to
 * https://leetcode.com/problems/word-search/description/
 * Given a 2D board and a word, find if the word exists in the grid.

	The word can be constructed from letters of sequentially adjacent cell, 
	where "adjacent" cells are those horizontally or vertically neighboring. 
	The same letter cell may not be used more than once.
	
	For example,
	Given board =
	
	[
	  ['A','B','C','E'],
	  ['S','F','C','S'],
	  ['A','D','E','E']
	]
	word = "ABCCED", -> returns true,
	word = "SEE", -> returns true,
	word = "ABCB", -> returns false.
 * 
 * 
 * Solution
 * https://discuss.leetcode.com/topic/7907/accepted-very-short-java-solution-no-additional-space
 * https://discuss.leetcode.com/topic/7907/accepted-very-short-java-solution-no-additional-space/40?page=2
 */
// Solution with additional space
class Solution {
    int[] dx = {0,1,-1,0};
    int[] dy = {1,0,0,-1};
    public boolean exist(char[][] board, String word) {
        if(board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return false;
        }
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(helper(board, i, j, word, 0, visited)) {
                    return true;
                }        
            }
        }
        return false;
    }
    
    private boolean helper(char[][] board, int i, int j, String word, int index, boolean[][] visited) {
        if(index == word.length()) {
            return true;
        }
        if(i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
            return false;
        }
        if(!visited[i][j] && board[i][j] == word.charAt(index)) {
            visited[i][j] = true;
            for(int k = 0; k < 4; k++) {
                int next_x = i + dx[k];
                int next_y = j + dy[k];
                if(helper(board, next_x, next_y, word, index + 1, visited)) {
                    return true;
                }
            }
            visited[i][j] = false;
        }
        return false;
    }
}

// Solution without additional space required
class Solution {
    int[] dx = {0, 0, 1, -1};
    int[] dy = {1, -1, 0, 0};
    public boolean exist(char[][] board, String word) {
        if(board == null || board.length == 0) {
            return false;
        }
        if(board[0].length == 0) {
            return false;
        }
        int m = board.length;
        int n = board[0].length;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(exist(board, i, j, word, 0)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean exist(char[][] board, int i, int j, String word, int index) {
        // Base case
        if(index == word.length()) {
            return true;
        }
        if(i >= board.length || i < 0 || j >= board[0].length || j < 0) {
            return false;
        }
        if(board[i][j] != word.charAt(index)) {
            return false;
        }
        boolean exist = false;
        // Record original char
        char c = board[i][j];
        // Set up as invalid char
        board[i][j] = '#'; 
        // Following section will cause TLE
        // for(int k = 0; k < 4; k++) {
        //     int next_i = i + dx[k];
        //     int next_j = j + dy[k];
        //     if(exist(board, next_i, next_j, word, index + 1)) {
        //         exist = true;
        //     }
        // }
        // Refer to
        // https://discuss.leetcode.com/topic/7907/accepted-very-short-java-solution-no-additional-space/40?page=2
        // we can update to following style and when 'exist' is true, we directly return,
        // no need to loop all 4 directions.
        for(int k = 0; k < 4; k++) {
            int next_i = i + dx[k];
            int next_j = j + dy[k];
            exist |= exist(board, next_i, next_j, word, index + 1);
            if(exist) {
                return true;
            }
        }
        // Back tracking to original char
        board[i][j] = c;
        return exist;
    }
}
