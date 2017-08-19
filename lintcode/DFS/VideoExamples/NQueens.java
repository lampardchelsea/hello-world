/**
 * Refer to
 * https://leetcode.com/problems/n-queens/description/
 *
 * Solution
 * http://www.jiuzhang.com/solutions/n-queens/
*/
class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<List<String>>();
        if(n <= 0) {
            return result;
        }
        List<Integer> cols = new ArrayList<Integer>();
        helper(result, cols, n);
        return result;
    }
    
    /*
     * results store all of the chessboards
     * cols store the column indices for each row
     * we can do same for rows store row indices
     * for each column
     */
    private void helper(List<List<String>> result, List<Integer> cols, int n) {
        if(cols.size() == n) {
           result.add(drawChessBoard(cols));  
        }
        for(int i = 0; i < n; i++) {
            // Check if current column index is valid in cols
            if(!isValid(cols, i)) {
                continue;
            }
            cols.add(i);
            helper(result, cols, n);
            cols.remove(cols.size() - 1);
        }
    }
    
    private List<String> drawChessBoard(List<Integer> cols) {
        List<String> chessboard = new ArrayList<>();
        for (int i = 0; i < cols.size(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < cols.size(); j++) {
                // Because cols stores all column indexes in each row,
                // so mask each position at cols.get(i) as 'Q', other
                // positions leave as '.'
                sb.append(j == cols.get(i) ? 'Q' : '.');
            }
            chessboard.add(sb.toString());
        }
        return chessboard;
    }
    
    private boolean isValid(List<Integer> cols, int columnNumber) {
        int existRows = cols.size();
        for(int i = 0; i < existRows; i++) {
            // Check if same column number happen on other row
            if(cols.get(i) == columnNumber) {
                return false;
            }
            // Check diagonal case
            if(Math.abs(cols.get(i) - columnNumber) == existRows - i) {
                return false;
            }
        }
        return true;
    }
}
