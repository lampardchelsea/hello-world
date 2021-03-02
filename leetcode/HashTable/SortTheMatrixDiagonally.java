/**
Refer to
https://leetcode.com/problems/sort-the-matrix-diagonally/
A matrix diagonal is a diagonal line of cells starting from some cell in either the topmost row or leftmost column 
and going in the bottom-right direction until reaching the matrix's end. For example, the matrix diagonal starting 
from mat[2][0], where mat is a 6 x 3 matrix, includes cells mat[2][0], mat[3][1], and mat[4][2].

Given an m x n matrix mat of integers, sort each matrix diagonal in ascending order and return the resulting matrix.

Example 1:
Input: mat = [[3,3,1,1],[2,2,1,2],[1,1,1,2]]
Output: [[1,1,1,1],[1,2,2,2],[1,2,3,3]]

Example 2:
Input: mat = [[11,25,66,1,69,7],[23,55,17,45,15,52],[75,31,36,44,58,8],[22,27,33,25,68,4],[84,28,14,11,5,50]]
Output: [[5,17,4,1,52,7],[11,11,25,45,8,69],[14,23,25,44,58,15],[22,27,31,36,50,66],[84,28,75,33,55,68]]

Constraints:
m == mat.length
n == mat[i].length
1 <= m, n <= 100
1 <= mat[i][j] <= 100
*/

// Solution 1: Use HashMap to identify each diagonal
// Each diagonal has different i - j, we can use i - j as key to identify each diagonal, and on same diagonal all elements have same i - j.
// Refer to
// https://leetcode.com/problems/sort-the-matrix-diagonally/discuss/489749/JavaPython-Straight-Forward
/**
Explanation
A[i][j] on the same diagonal have same value of i - j
For each diagonal,
put its elements together, sort, and set them back.

----------------------------------------------------------------
e.g
3 3 1 1
2 2 1 2
1 1 1 2
----------------------------------------------------------------
From top left to bottom right as a diagonal, we have 6 diagonals
same diagonal formula -> row index - col index
diag 1: 0 - 0, 1 - 1, 2 - 2 == key as 0
diag 2: 0 - 1, 1 - 2, 2 - 3 == key as -1
diag 3: 0 - 2, 1 - 3        == key as -2
diag 4: 0 - 3               == key as -3
diag 5: 1 - 0, 2 - 1        == key as 1
diag 6: 2 - 0               == key as 2
----------------------------------------------------------------

Complexity
Time O(MNlogD), where D is the length of diagonal with D = min(M,N).
Space O(MN)
*/
class Solution {
    // Each diagonal has different i - j, we can use i - j as key to
    // identify each diagonal, and on same diagonal all elements
    // have same i - j.
    public int[][] diagonalSort(int[][] mat) {
        int rows = mat.length;
        int cols = mat[0].length;
        Map<Integer, PriorityQueue<Integer>> map = new HashMap<Integer, PriorityQueue<Integer>>();
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                map.putIfAbsent(i - j, new PriorityQueue<Integer>());
                map.get(i - j).add(mat[i][j]);
            }
        }
        int[][] result = new int[rows][cols];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                result[i][j] = map.get(i - j).poll();
            }
        }
        return result;
    }
}

