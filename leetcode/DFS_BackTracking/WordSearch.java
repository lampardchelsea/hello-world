/**
 * Given a 2D board and a word, find if the word exists in the grid.
 * The word can be constructed from letters of sequentially adjacent cell, 
 * where "adjacent" cells are those horizontally or vertically neighboring. 
 * The same letter cell may not be used more than once.
 * For example,
 *   Given board =
 *
 *   [
 *     ['A','B','C','E'],
 *     ['S','F','C','S'],
 *     ['A','D','E','E']
 *   ]
 *   word = "ABCCED", -> returns true,
 *   word = "SEE", -> returns true,
 *   word = "ABCB", -> returns false.
*/

// Solution 1: 
// Refer to
// http://www.cnblogs.com/springfor/p/3883942.html
// 这道题分析看，就是一个词，在一行出现也是true，一列出现也是true，一行往下拐弯也是true，一行往上拐弯也是true，
// 一列往左拐弯也是true，一列往右拐弯也是true。所以是要考虑到所有可能性，基本思路是使用DFS来对一个起点字母上下
// 左右搜索，看是不是含有给定的Word。还要维护一个visited数组，表示从当前这个元素是否已经被访问过了，过了这一轮
// visited要回false，因为对于下一个元素，当前这个元素也应该是可以被访问的。
public class Solution {
    public boolean exist(char[][] board, String word) {
        int rows = board.length;
        int columns = board[0].length;
        
        boolean[][] visited = new boolean[rows][columns];
        
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                if(dfs(board, word, 0, i, j, visited)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public boolean dfs(char[][] board, String word, int index, int rowIndex, int columnIndex, boolean[][] visited) {
        // Base case, when index reach the end, and not return as false means we found the word in board
        if(index == word.length()) {
            return true;
        } 
        
        // Boundary check must before conditional check, otherwise will throw out ArrayOutOfBound exception
        if(rowIndex < 0 || rowIndex > board.length - 1 || columnIndex < 0 || columnIndex > board[0].length - 1) {
            return false;
        }
        
        // Condition to break out current level loop as this item on board is already visited
        if(visited[rowIndex][columnIndex]) {
            return false;
        }
        
        // Condition to break out current level loop as this item on board not match the required character
        if(board[rowIndex][columnIndex] != word.charAt(index)) {
            return false;
        }
        
        // Record current item in board has been visited before looply detect next character
        // in potential item
        visited[rowIndex][columnIndex] = true;
        
        // Detect potential item for next character(index + 1) on right/left/up/down four directions 
        boolean result = dfs(board, word, index + 1, rowIndex + 1, columnIndex, visited) ||
                         dfs(board, word, index + 1, rowIndex - 1, columnIndex, visited) ||
                         dfs(board, word, index + 1, rowIndex, columnIndex + 1, visited) ||
                         dfs(board, word, index + 1, rowIndex, columnIndex - 1, visited);
        
        // Restore the boolean tag for current item for next round detect
        visited[rowIndex][columnIndex] = false;
        
        return result;
    }
    
}


// Solution 2: 
// Refer to
// https://segmentfault.com/a/1190000003697153
// 基本思路很简单，对矩阵里每个点都进行一次深度优先搜索，看它能够产生一个路径和所给的字符串是一样的。
// 重点在如何优化搜索，避免不必要的计算。比如我们一个方向的搜索中已经发现了这个词，那我们就不用再搜索。
// 另外，为了避免循环搜索，我们还要将本轮深度优先搜索中搜索过的数字变一下，等递归回来之后再变回来。
// 实现这个特性最简单的方法就是异或上一个特定数，然后再异或回来。
public class Solution {
    public boolean exist(char[][] board, String word) {
       int rows = board.length;
       int columns = board[0].length;
       
       for(int i = 0; i < rows; i++) {
           for(int j = 0; j < columns; j++) {
               if(dfs(board, word, 0, i, j)) {
                   return true;
               }
           }
       }
       
       return false;
    }
    
    public boolean dfs(char[][] board, String word, int index, int rowIndex, int columnIndex) {
        if(index == word.length()) {
            return true;
        }
        
        if(rowIndex < 0 || rowIndex >= board.length || columnIndex < 0 || columnIndex >= board[0].length || board[rowIndex][columnIndex] != word.charAt(index)) {
            return false;
        }
        
        // The number used for XOR(exclusive or) can be randomly pick up, such as 255, 127, 63...
        // The big improvement of this way is no need to introduce additional O(n) space, such as
        // Solution 1 contain boolean[][] visited to record each on board item status
        board[rowIndex][columnIndex] ^= 255;
        boolean result = dfs(board, word, index + 1, rowIndex + 1, columnIndex) ||
                         dfs(board, word, index + 1, rowIndex - 1, columnIndex) ||
                         dfs(board, word, index + 1, rowIndex, columnIndex + 1) ||
                         dfs(board, word, index + 1, rowIndex, columnIndex - 1);
        board[rowIndex][columnIndex] ^= 255;
        return result;
    }
    
}
