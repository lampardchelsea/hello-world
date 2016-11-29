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
