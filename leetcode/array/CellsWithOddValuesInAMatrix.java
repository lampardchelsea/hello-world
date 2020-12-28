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
// Refer to
// https://leetcode.com/problems/cells-with-odd-values-in-a-matrix/discuss/426647/C%2B%2B-0ms-9.2MB.-Faster-than-100-and-uses-lesser-memory-than-100-submissions
/**
This can be done in O(m+n) time and O(m+n) space
Firstly notice that the order in which we add 1 does not matter. You could add 1 to all rows and then proceed to adding 1 to all columns.

Now suppose you add 1 to the same row twice, the resultant elements in that row are even. So applying the operation 
to the same row/col is as good as not applying anything there.

Applying operation to a same row/col any odd number of times is as good as applying the operation only once.

So now suppose there are r such rows where you apply the operation odd number of times. And c such cols where you apply operation odd number of times.

Then
All m elements in each row are odd => r*m
All n elements in each row are odd => c*n
You double count r*c elements => -1 * r * c
Also, these r*c elements are even => -1 * r * c
So total odd elements is r*m + c*n - 2*r*c

class Solution {
public:
    int oddCells(int n, int m, vector<vector<int>>& indices) {
        vector<bool> N(n, false);
        vector<bool> M(m, false);
        
        for(int i=0; i<indices.size(); i++) {
            N[indices[i][0]] = N[indices[i][0]]^true;
            M[indices[i][1]] = M[indices[i][1]]^true;
        }
        
        int r = 0;
        int c = 0;
        
        for(int i=0; i<n; i++) {
            if(N[i])
                r++;
        }
        
        for(int j=0; j<m; j++) {
            if(M[j])
                c++;
        }
        
        return  r*m + c*n - 2*r*c;
    }
};
*/

// Refer to
// https://leetcode.com/problems/cells-with-odd-values-in-a-matrix/discuss/425100/JavaPython-3-2-methods%3A-time-O(m-*-n-%2B-L)-and-O(L)-codes-w-comment-and-analysis.
/**
Method 2:
We actually only care about the number of rows and columns with odd times of increments respectively.

Count the rows and columns that appear odd times;
Compute the number of cells in aforementioned rows and columns respectively, then both deduct the cells located on odd rows 
and odd columns (they become evens, because odd + odd results even).
Note: Easier alternative way for 2 is odd_cols * even_rows + even_cols * odd_rows -- credit to @lenchen1112.
    public int oddCells(int n, int m, int[][] indices) {
        boolean[] oddRows = new boolean[n], oddCols = new boolean[m];
        int cntRow = 0, cntCol = 0;
        for (int[] idx : indices) {
            oddRows[idx[0]] ^= true;
            oddCols[idx[1]] ^= true;
        }
        for (boolean r : oddRows)
            cntRow += r ? 1 : 0;
        for (boolean c : oddCols)
            cntCol += c ? 1 : 0;
        // return m * cntRow + n * cntCol - 2 * cntRow * cntCol;
        return (m - cntCol) * cntRow + (n - cntRow) * cntCol;
    }
Analysis:
Time: O(L + m + n), space: O(m + n), where L = indices.length.
*/
class Solution {
    public int oddCells(int n, int m, int[][] indices) {
        boolean[] oddRows = new boolean[n];
        boolean[] oddCols = new boolean[m];
        int cntRow = 0;
        int cntCol = 0;
        // XOR - Sets each bit to 1 if only one of the two bits is 1
        for(int[] idx : indices) {
            oddRows[idx[0]] ^= true;
            oddCols[idx[1]] ^= true;
        }
        for(boolean r : oddRows) {
            cntRow += r ? 1 : 0;
        }
        for(boolean c : oddCols) {
           cntCol += c ? 1 : 0; 
        }
        // return (m - cntCol) * cntRow + (n - cntRow) * cntCol;
        return m * cntRow + n * cntCol - 2 * cntRow * cntCol;
    }
}
