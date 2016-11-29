/**
 * The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each other.
 * Given an integer n, return all distinct solutions to the n-queens puzzle.
 * Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' 
 * both indicate a queen and an empty space respectively.
 * For example,
 * There exist two distinct solutions to the 4-queens puzzle:
  [
   [".Q..",  // Solution 1
    "...Q",
    "Q...",
    "..Q."],

   ["..Q.",  // Solution 2
    "Q...",
    "...Q",
    ".Q.."]
  ]
*/
// Solution 1:
// Refer to
// https://segmentfault.com/a/1190000003762668
// http://www.cnblogs.com/springfor/p/3870944.html
public class Solution {
    public List<List<String>> solveNQueens(int n) {
       List<List<String>> result = new LinkedList<List<String>>();
       int[] nqueens = new int[n];
       dfs(nqueens, result, n, 0);
       return result;
    }
    
    public void dfs(int[] nqueens, List<List<String>> result, int n, int rowIndex) {
        if(rowIndex == nqueens.length) {
            List<String> oneSolution = new LinkedList<String>();
            for(int columnIndex : nqueens) {
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < columnIndex; i++) {
                    sb.append(".");
                }
                sb.append("Q");
                for(int i = columnIndex + 1; i < n; i++) {
                    sb.append(".");
                }
                oneSolution.add(sb.toString());
            }
            result.add(oneSolution);
        } else {
            for(int candPos = 0; candPos < n; candPos++) {
                nqueens[rowIndex] = candPos;
                if(isValid(nqueens, rowIndex)) {
                    dfs(nqueens, result, n, rowIndex + 1);
                }
            }
        }
    }
    
    public boolean isValid(int[] nqueens, int rowIndex) {
        for(int idx = 0; idx < rowIndex; idx++) {
            if(nqueens[idx] == nqueens[rowIndex] || Math.abs(nqueens[idx] - nqueens[rowIndex]) == rowIndex - idx) {
                return false;
            }
        }
        return true;
    }
}



// Solution With Test And Debugging Message
