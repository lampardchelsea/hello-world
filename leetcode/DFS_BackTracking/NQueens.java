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
import java.util.LinkedList;
import java.util.List;

/**
 * Refer to
 * https://segmentfault.com/a/1190000003762668
 * 
 * 暴力法
 * 复杂度
 * 时间 O(N^3) 空间 O(N)
 * 思路
 * 因为n皇后问题中，同一列不可能有两个皇后，所以我们可以用一个一维数组来表示二维棋盘上皇后的位置。
 * 一维数组中每一个值的下标代表着对应棋盘的行，每一个值则是那一列中皇后所在的列。这样我们可以只对
 * 一个一维数组进行深度优先搜索，来找出对于每一行，我们的皇后应该放在哪一列。在下一轮搜索之前，我们
 * 先检查一下新构成的数组是否是有效的，这样可以剪掉不必要的分支。检查的方法则是看之前排好的每一个
 * 皇后是否冲突。
 * 
	dfs(nqueens=[0,0,0,0], i=0)
	|  try to put queen at [0,0]
	|  this position for queen is currently OK
	|  dfs(nqueens=[0,0,0,0], i=1)
	|  |  try to put queen at [1,0]
	|  |  [1,0] conflict to column/diagonal queen at [0,0]
	|  |  try to put queen at [1,1]
	|  |  [1,1] conflict to column/diagonal queen at [0,1]
	|  |  try to put queen at [1,2]
	|  |  this position for queen is currently OK
	|  |  dfs(nqueens=[0,2,0,0], i=2)
	|  |  |  try to put queen at [2,0]
	|  |  |  [2,0] conflict to column/diagonal queen at [0,0]
	|  |  |  try to put queen at [2,1]
	|  |  |  [2,1] conflict to column/diagonal queen at [1,1]
	|  |  |  try to put queen at [2,2]
	|  |  |  [2,2] conflict to column/diagonal queen at [0,2]
	|  |  |  try to put queen at [2,3]
	|  |  |  [2,3] conflict to column/diagonal queen at [1,3]
	|  |  try to put queen at [1,3]
	|  |  this position for queen is currently OK
	|  |  dfs(nqueens=[0,3,3,0], i=2)
	|  |  |  try to put queen at [2,0]
	|  |  |  [2,0] conflict to column/diagonal queen at [0,0]
	|  |  |  try to put queen at [2,1]
	|  |  |  this position for queen is currently OK
	|  |  |  dfs(nqueens=[0,3,1,0], i=3)
	|  |  |  |  try to put queen at [3,0]
	|  |  |  |  [3,0] conflict to column/diagonal queen at [0,0]
	|  |  |  |  try to put queen at [3,1]
	|  |  |  |  [3,1] conflict to column/diagonal queen at [1,1]
	|  |  |  |  try to put queen at [3,2]
	|  |  |  |  [3,2] conflict to column/diagonal queen at [2,2]
	|  |  |  |  try to put queen at [3,3]
	|  |  |  |  [3,3] conflict to column/diagonal queen at [0,3]
	|  |  |  try to put queen at [2,2]
	|  |  |  [2,2] conflict to column/diagonal queen at [0,2]
	|  |  |  try to put queen at [2,3]
	|  |  |  [2,3] conflict to column/diagonal queen at [1,3]
	|  try to put queen at [0,1]
	|  this position for queen is currently OK
	|  dfs(nqueens=[1,3,3,3], i=1)
	|  |  try to put queen at [1,0]
	|  |  [1,0] conflict to column/diagonal queen at [0,0]
	|  |  try to put queen at [1,1]
	|  |  [1,1] conflict to column/diagonal queen at [0,1]
	|  |  try to put queen at [1,2]
	|  |  [1,2] conflict to column/diagonal queen at [0,2]
	|  |  try to put queen at [1,3]
	|  |  this position for queen is currently OK
	|  |  dfs(nqueens=[1,3,3,3], i=2)
	|  |  |  try to put queen at [2,0]
	|  |  |  this position for queen is currently OK
	|  |  |  dfs(nqueens=[1,3,0,3], i=3)
	|  |  |  |  try to put queen at [3,0]
	|  |  |  |  [3,0] conflict to column/diagonal queen at [2,0]
	|  |  |  |  try to put queen at [3,1]
	|  |  |  |  [3,1] conflict to column/diagonal queen at [0,1]
	|  |  |  |  try to put queen at [3,2]
	|  |  |  |  this position for queen is currently OK
	|  |  |  |  dfs(nqueens=[1,3,0,2], i=4)
	|  |  |  |  |  -------------------->Find a solution as:[.Q.., ...Q, Q..., ..Q.]
	|  |  |  |  try to put queen at [3,3]
	|  |  |  |  [3,3] conflict to column/diagonal queen at [1,3]
	|  |  |  try to put queen at [2,1]
	|  |  |  [2,1] conflict to column/diagonal queen at [0,1]
	|  |  |  try to put queen at [2,2]
	|  |  |  [2,2] conflict to column/diagonal queen at [1,2]
	|  |  |  try to put queen at [2,3]
	|  |  |  [2,3] conflict to column/diagonal queen at [0,3]
	|  try to put queen at [0,2]
	|  this position for queen is currently OK
	|  dfs(nqueens=[2,3,3,3], i=1)
	|  |  try to put queen at [1,0]
	|  |  this position for queen is currently OK
	|  |  dfs(nqueens=[2,0,3,3], i=2)
	|  |  |  try to put queen at [2,0]
	|  |  |  [2,0] conflict to column/diagonal queen at [0,0]
	|  |  |  try to put queen at [2,1]
	|  |  |  [2,1] conflict to column/diagonal queen at [1,1]
	|  |  |  try to put queen at [2,2]
	|  |  |  [2,2] conflict to column/diagonal queen at [0,2]
	|  |  |  try to put queen at [2,3]
	|  |  |  this position for queen is currently OK
	|  |  |  dfs(nqueens=[2,0,3,3], i=3)
	|  |  |  |  try to put queen at [3,0]
	|  |  |  |  [3,0] conflict to column/diagonal queen at [1,0]
	|  |  |  |  try to put queen at [3,1]
	|  |  |  |  this position for queen is currently OK
	|  |  |  |  dfs(nqueens=[2,0,3,1], i=4)
	|  |  |  |  |  -------------------->Find a solution as:[..Q., Q..., ...Q, .Q..]
	|  |  |  |  try to put queen at [3,2]
	|  |  |  |  [3,2] conflict to column/diagonal queen at [0,2]
	|  |  |  |  try to put queen at [3,3]
	|  |  |  |  [3,3] conflict to column/diagonal queen at [2,3]
	|  |  try to put queen at [1,1]
	|  |  [1,1] conflict to column/diagonal queen at [0,1]
	|  |  try to put queen at [1,2]
	|  |  [1,2] conflict to column/diagonal queen at [0,2]
	|  |  try to put queen at [1,3]
	|  |  [1,3] conflict to column/diagonal queen at [0,3]
	|  try to put queen at [0,3]
	|  this position for queen is currently OK
	|  dfs(nqueens=[3,3,3,3], i=1)
	|  |  try to put queen at [1,0]
	|  |  this position for queen is currently OK
	|  |  dfs(nqueens=[3,0,3,3], i=2)
	|  |  |  try to put queen at [2,0]
	|  |  |  [2,0] conflict to column/diagonal queen at [1,0]
	|  |  |  try to put queen at [2,1]
	|  |  |  [2,1] conflict to column/diagonal queen at [0,1]
	|  |  |  try to put queen at [2,2]
	|  |  |  this position for queen is currently OK
	|  |  |  dfs(nqueens=[3,0,2,3], i=3)
	|  |  |  |  try to put queen at [3,0]
	|  |  |  |  [3,0] conflict to column/diagonal queen at [0,0]
	|  |  |  |  try to put queen at [3,1]
	|  |  |  |  [3,1] conflict to column/diagonal queen at [2,1]
	|  |  |  |  try to put queen at [3,2]
	|  |  |  |  [3,2] conflict to column/diagonal queen at [1,2]
	|  |  |  |  try to put queen at [3,3]
	|  |  |  |  [3,3] conflict to column/diagonal queen at [0,3]
	|  |  |  try to put queen at [2,3]
	|  |  |  [2,3] conflict to column/diagonal queen at [0,3]
	|  |  try to put queen at [1,1]
	|  |  this position for queen is currently OK
	|  |  dfs(nqueens=[3,1,3,3], i=2)
	|  |  |  try to put queen at [2,0]
	|  |  |  [2,0] conflict to column/diagonal queen at [1,0]
	|  |  |  try to put queen at [2,1]
	|  |  |  [2,1] conflict to column/diagonal queen at [0,1]
	|  |  |  try to put queen at [2,2]
	|  |  |  [2,2] conflict to column/diagonal queen at [1,2]
	|  |  |  try to put queen at [2,3]
	|  |  |  [2,3] conflict to column/diagonal queen at [0,3]
	|  |  try to put queen at [1,2]
	|  |  [1,2] conflict to column/diagonal queen at [0,2]
	|  |  try to put queen at [1,3]
	|  |  [1,3] conflict to column/diagonal queen at [0,3]
	[[.Q.., ...Q, Q..., ..Q.], [..Q., Q..., ...Q, .Q..]]

 */
public class NQueens {
	static String indent = "";
    public List<List<String>> solveNQueens(int n) {
    	// Array nqueens index is queen row index value, value is queen column index value
    	// e.g nqueens[0] = 1 means one queen place at row 0 column 1,
    	//     nqueens[1] = 3 means another queen place at row 1 column 3...
        int[] nqueens = new int[n];
        List<List<String>> result = new LinkedList<List<String>>();
        dfs(nqueens, result, n, 0);
        return result;
    }
    
    public void dfs(int[] nqueens, List<List<String>> result, int n, int rowIndex) {
    	// For debug
    	enter(nqueens, rowIndex);
    	
    	// To build full nqueen solution need try dfs till the end row
    	if(rowIndex == nqueens.length) {
    		List<String> oneSolution = new LinkedList<String>();
    		for(int queenColumnIndex : nqueens) {
    			StringBuilder sb = new StringBuilder();
    			for(int x = 0; x < queenColumnIndex; x++) {
    				sb.append(".");
    			}
    			sb.append("Q");
    			for(int x = queenColumnIndex + 1; x < n; x++) {
    				sb.append(".");
    			}
    			oneSolution.add(sb.toString());
    		}
    		result.add(oneSolution);
    		// For debug
			String reason = "Find a solution as:" + oneSolution.toString();
			findOneSolution(reason);
    	} else {
    		for(int candPos = 0; candPos < n; candPos++) {
    			nqueens[rowIndex] = candPos;
    			// For debug
    			tryToPut(rowIndex, candPos);
    			if(isValid(nqueens, rowIndex)) {
//    				indent = indent.substring(3);
        	    	dfs(nqueens, result, n, rowIndex + 1);
        	    	// For debug: Note, must put behind dfs method
        	    	indent = indent.substring(3);
        	    }	
    		}
    	}
    }
	
	public boolean isValid(int[] nqueens, int rowIndex) {
		for(int idx = 0; idx < rowIndex; idx++) {
			// Check if already exist queen in same column or current position's diagonal
			if(nqueens[idx] == nqueens[rowIndex] || Math.abs(nqueens[idx] - nqueens[rowIndex]) == rowIndex - idx) {
				// For debug
				String reason = "[" + rowIndex + "," + nqueens[rowIndex] + "] conflict to column/diagonal queen at [" + idx + "," + nqueens[rowIndex] + "]";
				no(reason);
				return false;
			}
		}
		String reason = "this position for queen is currently OK";
		yes(reason);
		return true;
	}
    
//	static String indent = "";
	public void enter(int[] nqueens, int i) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int num : nqueens) {
			sb.append(num + ",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		String a = sb.toString();
		System.out.println(indent + "dfs(nqueens=" + a + "," + " i=" + i + ")");
		indent = indent + "|  ";
	}
		
	// As yes() and no() used in isValid() method which called by solveNQueens() method,
	// should NOT use indent = indent.substring(3); move this into solveNQueens() method
	public void no(String reason) {
		System.out.println(indent + reason);
	}
	
	public void yes(String reason) {
		System.out.println(indent + reason);
	}
	
	public void tryToPut(int i, int j) {
		System.out.println(indent + "try to put queen at [" + i + "," + j + "]");
	}
	
	public void findOneSolution(String reason) {
		System.out.println(indent + "-------------------->" + reason);
	}
	
    public static void main(String[] args) {
    	NQueens nqueens = new NQueens();
    	List<List<String>> result = nqueens.solveNQueens(4);
    	System.out.println(result.toString());
    }
}

// Solution 2:
// Refer to
// https://segmentfault.com/a/1190000003762668
/**
 * 集合法
 * 复杂度
 * 时间 O(N^2) 空间 O(N)
 * 思路
 * 该方法的思路和暴力法一样，区别在于，之前我们判断一个皇后是否冲突，是遍历一遍当前皇后排列的列表，看每一个皇后是否冲突。
 * 这里，我们用三个集合来保存之前皇后的信息，就可以O(1)时间判断出皇后是否冲突。三个集合分别是行集合，用于存放有哪些列被占了，
 * 主对角线集合，用于存放哪个右上到左下的对角线被占了，副对角线集合，用于存放哪个左上到右下的对角线被占了。如何唯一的判断某
 * 个点所在的主对角线和副对角线呢？我们发现，两个点的行号加列号的和相同，则两个点在同一条主对角线上。两个点的行号减列号的
 * 差相同，则两个点再同一条副对角线上。
 * 注意
 * 主对角线row + col，副对角线row - col
 */
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class NQueens2 {
	public List<List<String>> solveNQueens(int n) {
		List<List<String>> result = new LinkedList<List<String>>();
		int[] nqueens = new int[n];
		Set<Integer> columnSet = new HashSet<Integer>();
		Set<Integer> majorDiagonalSet = new HashSet<Integer>();
		Set<Integer> subDiagonalSet = new HashSet<Integer>();
		dfs(nqueens, result, n, 0, columnSet, majorDiagonalSet, subDiagonalSet);
		return result;
	}
	
	public void dfs(int[] nqueens, List<List<String>> result, int n, int rowIndex, 
			Set<Integer> columnSet, Set<Integer> majorDiagonalSet, Set<Integer> subDiagonalSet) {
		if(rowIndex == n) {
			List<String> oneSolution = new LinkedList<String>();
			for(int columnIndex : nqueens) {
				StringBuilder sb = new StringBuilder();
				for(int x = 0; x < columnIndex; x++) {
					sb.append(".");
				}
				sb.append("Q");
				for(int x = columnIndex + 1; x < n; x++) {
					sb.append(".");
				}
				oneSolution.add(sb.toString());
			} 
			result.add(oneSolution);
		} else {
			// Check for each row the column index should be what value
			// candPos is candidate position of column index on current row index
			for(int candPos = 0; candPos < n; candPos++) {
				//nqueens[rowIndex] = candPos;
				int forMajorDiagonal = rowIndex + candPos;
				int forSubDiagonal = rowIndex - candPos;
				if(columnSet.contains(candPos) || majorDiagonalSet.contains(forMajorDiagonal) 
						|| subDiagonalSet.contains(forSubDiagonal)) {
					continue;
				}
				nqueens[rowIndex] = candPos;
				columnSet.add(candPos);
				majorDiagonalSet.add(forMajorDiagonal);
				subDiagonalSet.add(forSubDiagonal);
				dfs(nqueens, result, n, rowIndex + 1, columnSet, majorDiagonalSet, subDiagonalSet);
				subDiagonalSet.remove(forSubDiagonal);
				majorDiagonalSet.remove(forMajorDiagonal);
				columnSet.remove(candPos);
				nqueens[rowIndex] = 0;
			}
		}
	}
	
	
	public static void main(String[] args) {
    	NQueens2 nqueens = new NQueens2();
    	List<List<String>> result = nqueens.solveNQueens(6);
    	System.out.println(result.toString());
	}
}


// New try
// Refer to
// https://leetcode.com/problems/n-queens/discuss/19805/My-easy-understanding-Java-Solution/150112
class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<List<String>>();
        char[][] board = new char[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }
        helper(board, result, 0);
        return result;
    }
    
    private void helper(char[][] board, List<List<String>> result, int rowIndex) {
        if(rowIndex == board.length) {
            List<String> list = new ArrayList<String>();
            for(int i = 0; i < board.length; i++) {
		// New String based on char array
                list.add(new String(board[i]));
            }
            result.add(list);
            return;
        }
        for(int i = 0; i < board[0].length; i++) {
            // Try
            board[rowIndex][i] = 'Q';
            // If possible
            if(isValid(board, rowIndex, i)) {
                helper(board, result, rowIndex + 1);
            }
            // Not try
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



































https://leetcode.com/problems/n-queens/

The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each other.

Given an integer n, return all distinct solutions to the n-queens puzzle. You may return the answer in any order.

Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space, respectively.

Example 1:


```
Input: n = 4
Output: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above
```

Example 2:
```
Input: n = 1
Output: [["Q"]]
```

Constraints:
- 1 <= n <= 9
---
Attempt 1: 2022-12-04

Solution 1:  Backtracking (10 min)
```
class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<List<String>>();
        char[][] board = new char[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }
        // Start from row = 0 and recursively assign each 'Q' to next row
        helper(result, board, 0);
        return result;
    }
    
    private void helper(List<List<String>> result, char[][] board, int rowIndex) {
        if(rowIndex == board.length) {
            List<String> list = new ArrayList<String>();
            for(int i = 0; i < board.length; i++) {
                list.add(new String(board[i]));
            }
            result.add(list);
            return;
        }
        for(int i = 0; i < board[0].length; i++) {
            // Backtrack
            board[rowIndex][i] = 'Q';
            if(isValid(board, rowIndex, i)) {
                helper(result, board, rowIndex + 1);
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

Refer to
Start from row = 0
https://leetcode.com/problems/n-queens/discuss/19805/My-easy-understanding-Java-Solution/150112
```
public class Solution {
  public List<List<String>> solveNQueens(int n) {
    List<List<String>> res = new LinkedList<>();
    if (n <= 0) { return res; } 
    // build chessboard @mat(=matrix)
    char[][] mat = new char[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        mat[i][j] = '.';
      }
    }
    helper(mat, 0, res);
    return res;
  }
  private void helper(char[][] mat, int row, List<List<String>> res) {
    // reach solution
    if (row == mat.length) {
      res.add(builder(mat));
      return;
    }
    for (int i = 0; i < mat.length; i++) {
      // try
      mat[row][i] = 'Q';
      // if possible
      if (isValid(mat, row, i)) {
        helper(mat, row + 1, res);
      }
      // un-try
      mat[row][i] = '.';
    }
    return;
  }
  private boolean isValid(char[][] mat, int x, int y) {
    // only check rows above current one
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < mat.length; j++) {
        // not need to check current position
        if (i == x && j == y) { 
          continue;
        }
        // if 'Q' in the same col or the diagonal line, return false
        if ((j == y || Math.abs(x - i) == Math.abs(y - j)) && mat[i][j] == 'Q') {
          return false;
        } 
      }
    }
    return true;
  }
  // build solution from temporary chessboard
  private List<String> builder(char[][] mat) {
    List<String> tmp = new LinkedList<>();
    for (int i = 0; i < mat.length; i++) {
      String t = new String(mat[i]);
      tmp.add(t);
    }
    return tmp;
  }
}
```

Refer to
Start from column = 0
https://leetcode.com/problems/n-queens/discuss/19805/My-easy-understanding-Java-Solution
```
public class Solution {
    public List<List<String>> solveNQueens(int n) {
        char[][] board = new char[n][n];
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                board[i][j] = '.';
        List<List<String>> res = new ArrayList<List<String>>();
        dfs(board, 0, res);
        return res;
    }
    
    private void dfs(char[][] board, int colIndex, List<List<String>> res) {
        if(colIndex == board.length) {
            res.add(construct(board));
            return;
        }
        
        for(int i = 0; i < board.length; i++) {
            if(validate(board, i, colIndex)) {
                board[i][colIndex] = 'Q';
                dfs(board, colIndex + 1, res);
                board[i][colIndex] = '.';
            }
        }
    }
    
    private boolean validate(char[][] board, int x, int y) {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < y; j++) {
                if(board[i][j] == 'Q' && (x + j == y + i || x + y == i + j || x == i))
                    return false;
            }
        }
        
        return true;
    }
    
    private List<String> construct(char[][] board) {
        List<String> res = new LinkedList<String>();
        for(int i = 0; i < board.length; i++) {
            String s = new String(board[i]);
            res.add(s);
        }
        return res;
    }
}
```

---
Solution 2:  Backtracking with Bit-manipulation (60 min)
```
class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<List<String>>();
        char[][] board = new char[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }
        // Start from row = 0 and recursively assign each 'Q' to next row
        helper(result, board, 0, 0, 0, 0);
        return result;
    }
    
    private void helper(List<List<String>> result, char[][] board, int row, int cols, int diags, int antiDiags) {
        if(row == board.length) {
            List<String> list = new ArrayList<String>();
            for(int i = 0; i < board.length; i++) {
                list.add(new String(board[i]));
            }
            result.add(list);
            return;
        }
        for(int col = 0; col < board[0].length; col++) {
            int curDiag = row - col + board.length;
            int curAntiDiag = row + col;
            // Check if the current Queen placement is valid with "&"
            if((cols & (1 << col)) != 0 || (diags & (1 << curDiag)) != 0 || (antiDiags & (1 << curAntiDiag)) != 0) {
                continue;
            }
            // Backtracking
            board[row][col] = 'Q';
            // Update given 'Q' at [row, col]'s other attribute as cols, diags and antiDiags with "|="  
            cols |= (1 << col);
            diags |= (1 << curDiag);
            antiDiags |= (1 << curAntiDiag);
            helper(result, board, row + 1, cols, diags, antiDiags);
            board[row][col] = '.';
            // Rollback given 'Q' at [row, col]'s other attribute as cols, diags and antiDiags with "^="  
            cols ^= (1 << col);
            diags ^= (1 << curDiag);
            antiDiags ^= (1 << curAntiDiag);
        }
    }
}

Time Complexity: O(N!) since we look for every valid board state.
Space Complexity: O(N^2) to build our board.
```

Refer to
https://leetcode.com/problems/n-queens/discuss/2107776/Explained-with-Diagrams-or-Backtracking-and-Bit-manipulation

Logic:

The best way to generate all valid Queen positions is through backtracking. First, we need to understand how a Queen moves and what constitutes a valid position. Queen's can move in literally any straight-lined direction.

It's possible to place a Queen down if and only if:
- There exists no Queen on the current row.
- There exists no Queen on the current column.
- There exists no Queen on the current diagonal.
- There exists no Queen on the current anti-diagonal.

Backtracking: Try a promising Queen position, see how it goes. If it fails, undo that Queen and try again somewhere else.

Hopefully it's now clear that we need some way to keep track of Queens on previous rows, columns and diagonals. We can take care of rows automatically by incrementing each queen placement by row. In other words, after each successful Queen placement, we move to the next row (since no two Queens can share the same row).

We can keep track of previous columns just by their column indexes. What about the diagonals? Well here's an interesting observation:

(the "+N" is to offset negative values. You will see why we do this in the next section). Now we have everything we need to keep track of previous Queens and start thinking about our algorithm! But just one more thing; let's keep track of "used" columns and diagonals using bit masks.
---

Why Integer Bit Masks?

We prefer integer bitmasks in this question for 2 main reasons:
1. Sets in Java are a slow data structure. So keeping track of visited columns and diagonals using integers is much quicker through bit manipulation!
2. It's slightly more space efficient since we're only storing three integers instead of arrays or other data structures.

Bit Manipulation Tricks

1. Check the ith bit: x & (1 << i), where 1 << i is shifting 1 to the left i number of times.
   
   For example, if we want to check if a Queen exists at column 5, we can check if (cols & (1 << 5) != 0). Basically, if we haven't seen a Queen at this column before, then the bitwise AND operation at that specific bit will be 0. Otherwise, it will be a non-zero value.
2. Set the ith bit: x |= (1 << i). This performs a bitwise OR operation on the ith bit. This will always set the ith bit to 1. For example, if the ith bit in cols is currently 1, then 1 | 1 = 1. If it's set to 0, then 0 | 0 is still = 1.
3. Flip the ith bit: x ^= (1 << i) To reverse the previous action, we use the inverse logic of an OR; a bitwise XOR (exclusive-OR). The basic idea of XOR is that if two bits are the same (0,0) and (1,1), you will get 0. However if the two bits are different (1,0) or (0,1), you get 1. This is the exact opposite of OR, hence the name.
---

Algorithm:

Awesome! Now we have everything we need. Here's how the backtracking algorithm will work:
1. Check if we've reached the end:
	- If row == N, we've filled in all our rows successfully which implies the current board state is a valid combination. Let's add it to our output list.
2. Loop through each column in the current row.
	3. If we can't add a Queen at this position, skip this col value.
	4. If we can, add a Queen at this position and adjust our bitmasks respectively.
	5. Continue to the next row (call backtrack for row+1).
	6. Undo our changes so we can try other col values.
---

Code:

If you have any questions, suggestions or improvements, feel free to let me know. Thank you for reading!
```
class Solution {
    private List<List<String>> res;
    private int N;

    public List<List<String>> solveNQueens(int n) {
        res = new ArrayList<>();
        N = n;
        char[][] emptyBoard = new char[N][N];
        for (char[] row: emptyBoard) Arrays.fill(row, '.');
        
        backtrack(emptyBoard, 0, 0, 0, 0);
        return res;
    }
    
    private void backtrack(char[][] board, int row, int cols, int diags, int antiDiags) {
        // if we've successfuly placed a Queen at all rows, we have a valid board state
        if (row == N) {
            res.add(toBoard(board));
            return;
        }
        
        for (int col=0; col<N; col++) {
            int currDiag = row-col+N;
            int currAntiDiag = row+col;
            
            // check if the current Queen placement is valid
            if ((cols & (1 << col)) != 0 || (diags & (1 << currDiag)) != 0 || (antiDiags & (1 << currAntiDiag)) != 0) continue;
            
            // if so, add changes
            board[row][col] = 'Q';
            cols |= (1 << col);
            diags |= (1 << currDiag);
            antiDiags |= (1 << currAntiDiag);
            
            // continue to the next row
            backtrack(board, row + 1, cols, diags, antiDiags);
            
            // undo changes and continue
            board[row][col] = '.';
            cols ^= (1 << col);
            diags ^= (1 << currDiag);
            antiDiags ^= (1 << currAntiDiag);
        }
    }
    
    private List<String> toBoard(char[][] board) {
        List<String> newBoard = new ArrayList<>();
        for (char[] row: board) newBoard.add(new String(row));
        return newBoard;
    }
}
```
Time Complexity : O(N!), Since we have N choices in the first row, then N-1 choices in the second row and so on so the overall complexity become O(N!)
Space Complexity: O(N*N), Just the board and recursive stack space
