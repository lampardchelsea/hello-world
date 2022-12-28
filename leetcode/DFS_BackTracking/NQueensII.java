/**
 Refer to
 https://leetcode.com/problems/n-queens-ii/submissions/
 The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each other.

Given an integer n, return the number of distinct solutions to the n-queens puzzle.
Example:
Input: 4
Output: 2
Explanation: There are two distinct solutions to the 4-queens puzzle as shown below.
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
// https://leetcode.wang/leetCode-52-N-QueensII.html
/**
 既然不用返回所有解，那么我们就不需要 currentQueen 来保存当前已加入皇后的位置。只需要一个 bool 型数组，来标记列是否被占有就可以了。
 由于没有了 currentQueen，所有不能再用之前 isDiagonalAttack 判断对角线冲突的方法了。我们可以观察下，对角线元素的情况。
可以发现对于同一条副对角线，row + col 的值是相等的。
对于同一条主对角线，row - col 的值是相等的。
我们同样可以用一个 bool 型数组，来保存当前对角线是否有元素，把它们相加相减的值作为下标。
对于 row - col ，由于出现了负数，所以可以加 1 个 n，由 [ - 3, 3 ] 转换为 [ 1 , 7 ] 。

public int totalNQueens(int n) {
    List<Integer> ans = new ArrayList<>();
    boolean[] cols = new boolean[n]; // 列
    boolean[] d1 = new boolean[2 * n]; // 主对角线 
    boolean[] d2 = new boolean[2 * n]; // 副对角线
    return backtrack(0, cols, d1, d2, n, 0);
}

private int backtrack(int row, boolean[] cols, boolean[] d1, boolean[] d2, int n, int count) { 
    if (row == n) {
        count++;
    } else {
        for (int col = 0; col < n; col++) {
            int id1 = row - col + n; //主对角线加 n
            int id2 = row + col;
            if (cols[col] || d1[id1] || d2[id2])
                continue;
            cols[col] = true;
            d1[id1] = true;
            d2[id2] = true;
            count = backtrack(row + 1, cols, d1, d2, n, count);
            cols[col] = false;
            d1[id1] = false;
            d2[id2] = false;
        }

    }
    return count;
}
*/

// https://leetcode.com/problems/n-queens-ii/discuss/20048/Easiest-Java-Solution-(1ms-98.22)

class Solution {
    int count = 0;
    public int totalNQueens(int n) {
        boolean[] cols = new boolean[n];
        boolean[] diagonal = new boolean[2 * n];
        boolean[] paradiagonal = new boolean[2 * n];
        helper(cols, diagonal, paradiagonal, 0, n);
        return count;
    }
    
    private void helper(boolean[] cols, boolean[] diagonal, boolean[] paradiagonal, int rowIndex, int n) {
        if(rowIndex == n) {
            count++;
        }
        for(int i = 0; i < n; i++) {
            int diagonal_value = rowIndex - i + n;
            int paradiagonal_value = rowIndex + i;
            if(cols[i] || diagonal[diagonal_value] || paradiagonal[paradiagonal_value]) {
                continue;
            }
            cols[i] = true;
            diagonal[diagonal_value] = true;
            paradiagonal[paradiagonal_value] = true;
            helper(cols, diagonal, paradiagonal, rowIndex + 1, n);
            cols[i] = false;
            diagonal[diagonal_value] = false;
            paradiagonal[paradiagonal_value] = false;
        }
    }
}

// Re-work
class Solution {
    int count = 0;
    public int totalNQueens(int n) {
        boolean[] cols = new boolean[n];
        boolean[] main_diag = new boolean[2 * n];
        boolean[] sub_diag = new boolean[2 * n];
        helper(cols, main_diag, sub_diag, 0, n);
        return count;
    }
    
    private void helper(boolean[] cols, boolean[] main_diag, boolean[] sub_diag, int rowIndex, int n) {
        if(rowIndex == n) {
            count++;
        }
        for(int colIndex = 0; colIndex < n; colIndex++) {
            int main_diag_val = rowIndex - colIndex + n;
            int sub_diag_val = rowIndex + colIndex;
            if(cols[colIndex] || main_diag[main_diag_val] || sub_diag[sub_diag_val]) {
                continue;
            }
            cols[colIndex] = true;
            main_diag[main_diag_val] = true;
            sub_diag[sub_diag_val] = true;
            helper(cols, main_diag, sub_diag, rowIndex + 1, n);
            cols[colIndex] = false;
            main_diag[main_diag_val] = false;
            sub_diag[sub_diag_val] = false;            
        }
    }
}































https://leetcode.com/problems/n-queens-ii/

The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each other.

Given an integer n, return the number of distinct solutions to the n-queens puzzle.

Example 1:


```
Input: n = 4
Output: 2
Explanation: There are two distinct solutions to the 4-queens puzzle as shown.
```

Example 2:
```
Input: n = 1
Output: 1
```

Constraints:
- 1 <= n <= 9
---
Attempt 1: 2022-12-04

Solution 1:  Backtracking (10 min)

Style 1: Void helper with global variable
```
class Solution { 
    int count = 0; 
    public int totalNQueens(int n) { 
        char[][] board = new char[n][n]; 
        for(int i = 0; i < n; i++) { 
            for(int j = 0; j < n; j++) { 
                board[i][j] = '.'; 
            } 
        } 
        // Start from row = 0 and recursively assign each 'Q' to next row  
        helper(board, 0); 
        return count; 
    } 
    private void helper(char[][] board, int rowIndex) { 
        if(rowIndex == board.length) { 
            count++; 
            return; 
        } 
        for(int i = 0; i < board[0].length; i++) { 
            // Backtrack  
            board[rowIndex][i] = 'Q'; 
            if(isValid(board, rowIndex, i)) { 
                helper(board, rowIndex + 1); 
            } 
            board[rowIndex][i] = '.'; 
        } 
    } 
    private boolean isValid(char[][] board, int rowIndex, int colIndex) { 
        // only check rows above current row  
        for(int i = 0; i < rowIndex; i++) { 
            for(int j = 0; j < board[0].length; j++) { 
                // if 'Q' in the same col or the diagonal line, return false  
                if((j == colIndex || Math.abs(i - rowIndex) == Math.abs(j - colIndex)) && board[i][j] == 'Q') { 
                    return false; 
                } 
            } 
        } 
        return true; 
    } 
}

Time Complexity : O(N!), Since we have N choices in the first row, then N-1 choices in the second row and so on so the overall complexity become O(N!) 
Another saying for Time Complexity is O(N! * N), the additional N is coming from in isValid call the inner for loop consumes as N  
Space Complexity: O(N*N), Just the board and recursive stack space
```

Style 2: Return type helper without global variable
```
class Solution { 
    public int totalNQueens(int n) { 
        char[][] board = new char[n][n]; 
        for(int i = 0; i < n; i++) { 
            for(int j = 0; j < n; j++) { 
                board[i][j] = '.'; 
            } 
        } 
        // Start from row = 0 and recursively assign each 'Q' to next row  
        return helper(board, 0); 
    } 
    private int helper(char[][] board, int rowIndex) { 
        if(rowIndex == board.length) { 
            return 1; 
        } 
        int count = 0; 
        for(int i = 0; i < board[0].length; i++) { 
            // Backtrack  
            board[rowIndex][i] = 'Q'; 
            if(isValid(board, rowIndex, i)) { 
                count += helper(board, rowIndex + 1); 
            } 
            board[rowIndex][i] = '.'; 
        } 
        return count; 
    } 
    private boolean isValid(char[][] board, int rowIndex, int colIndex) { 
        // only check rows above current row  
        for(int i = 0; i < rowIndex; i++) { 
            for(int j = 0; j < board[0].length; j++) { 
                // if 'Q' in the same col or the diagonal line, return false  
                if((j == colIndex || Math.abs(i - rowIndex) == Math.abs(j - colIndex)) && board[i][j] == 'Q') { 
                    return false; 
                } 
            } 
        } 
        return true; 
    } 
}

Time Complexity : O(N!), Since we have N choices in the first row, then N-1 choices in the second row and so on so the overall complexity become O(N!)  
Another saying for Time Complexity is O(N! * N), the additional N is coming from in isValid call the inner for loop consumes as N   
Space Complexity: O(N*N), Just the board and recursive stack space
```

Refer to
https://leetcode.com/problems/n-queens-ii/solutions/1237811/short-easy-w-explanation-visualization-backtracking-explained/
```
int totalNQueens(int n) { 
	vector<vector<bool>> board(n, vector<bool>(n, false)); 
	return solve(board, 0); 
} 
bool check(vector<vector<bool>>& board, int row, int col) { 
	int n = size(board); 
	for(int i = 0; i <= row; i++) { 
		if(board[i][col]) return false; // checking if any queen already placed on same column previously 
		// checking if all diagonals are safe - 
		if(row - i >= 0 && col - i >= 0 && board[row - i][col - i]) return false; 
		if(row - i >= 0 && col + i <  n && board[row - i][col + i]) return false; 
	} 
	return true; 
}     
int solve(vector<vector<bool>>& board, int row) { 
	if(row == size(board)) return 1; 
	int count = 0; 
	for(int col = 0; col < size(board); col++)            
		if(check(board, row, col)){          // check if we can place at (row, col) 
			board[row][col] = true;          // place the queen at (row, col) 
			count += solve(board, row + 1);  // explore for the next row. The function will return 1 if all N queens get placed for current combination 
			board[row][col] = false;         // backtrack - remove previously placed queen and try for different columns 
		}                                 
	return count; 
}
```


