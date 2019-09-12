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
