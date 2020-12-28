/**
Refer to
https://leetcode.com/problems/cells-with-odd-values-in-a-matrix/
Given n and m which are the dimensions of a matrix initialized by zeros and given an array indices where 
indices[i] = [ri, ci]. For each pair of [ri, ci] you have to increment all cells in row ri and column ci by 1.

Return the number of cells with odd values in the matrix after applying the increment to all indices.

Example 1:
0 0 0    -->   1 2 1   -->   1 3 1
0 0 0          0 1 0         1 3 1
Input: n = 2, m = 3, indices = [[0,1],[1,1]]
Output: 6
Explanation: Initial matrix = [[0,0,0],[0,0,0]].
After applying first increment it becomes [[1,2,1],[0,1,0]].
The final matrix will be [[1,3,1],[1,3,1]] which contains 6 odd numbers.

Example 2:
0 0   -->  0 1  --> 2 2
0 0        1 2      2 2
Input: n = 2, m = 2, indices = [[1,1],[0,0]]
Output: 0
Explanation: Final matrix = [[2,2],[2,2]]. There is no odd number in the final matrix.

Constraints:
1 <= n <= 50
1 <= m <= 50
1 <= indices.length <= 100
0 <= indices[i][0] < n
0 <= indices[i][1] < m
*/

// Solution 1: Brute Force
// Refer to
// https://leetcode.com/problems/cells-with-odd-values-in-a-matrix/discuss/428116/JavaScript-Easy-to-understand-3-solutions
/**
SOLUTION 1
It's a brute force way.
We do all operations from the problem description to the matrix according to data in indices
Traversal the matrix to find the result
const oddCells = (row, column, indices) => {
  const matrix = [];
  let ret = 0;
  for (let i = 0; i < row; ++i) matrix[i] = new Uint8Array(column);
  for (let i = 0; i < indices.length; ++i) {
    const [r, c] = indices[i];
    for (let j = 0; j < column; ++j) ++matrix[r][j];
    for (let j = 0; j < row; ++j) ++matrix[j][c];
  }
  for (let i = 0; i < row; ++i) {
    for (let j = 0; j < column; ++j) {
      if (matrix[i][j] % 2 === 1) ++ret;
    }
  }
  return ret;
};
Time complexity: O(indices.length * (row + column) + row * column)
Space complexity: O(row * column)
*/
class Solution {
    public int oddCells(int n, int m, int[][] indices) {
        int[][] matrix = new int[n][m];
        for(int[] indice : indices) {
            int x = indice[0];
            int y = indice[1];
            for(int i = 0; i < m; i++) {
                matrix[x][i]++;
            }
            for(int i = 0; i < n; i++) {
                matrix[i][y]++;
            }
        }
        int result = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(matrix[i][j] % 2 == 1) {
                    result++;
                }
            }
        }
        return result;
    }
}

// Solution 2: 
