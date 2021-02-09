/**
Refer to
https://leetcode.com/problems/find-kth-largest-xor-coordinate-value/
You are given a 2D matrix of size m x n, consisting of non-negative integers. You are also given an integer k.

The value of coordinate (a, b) of the matrix is the XOR of all matrix[i][j] where 0 <= i <= a < m and 0 <= j <= b < n (0-indexed).

Find the kth largest value (1-indexed) of all the coordinates of matrix.

Example 1:
Input: matrix = [[5,2],[1,6]], k = 1
Output: 7
Explanation: The value of coordinate (0,1) is 5 XOR 2 = 7, which is the largest value.

Example 2:
Input: matrix = [[5,2],[1,6]], k = 2
Output: 5
Explanation: The value of coordinate (0,0) is 5 = 5, which is the 2nd largest value.

Example 3:
Input: matrix = [[5,2],[1,6]], k = 3
Output: 4
Explanation: The value of coordinate (1,0) is 5 XOR 1 = 4, which is the 3rd largest value.

Example 4:
Input: matrix = [[5,2],[1,6]], k = 4
Output: 0
Explanation: The value of coordinate (1,1) is 5 XOR 2 XOR 1 XOR 6 = 0, which is the 4th largest value.

Constraints:
m == matrix.length
n == matrix[i].length
1 <= m, n <= 1000
0 <= matrix[i][j] <= 106
1 <= k <= m * n
*/

// Solution 1: Use a 2D prefix sum to precalculate the xor-sum of the upper left submatrix.
// Refer to
// https://leetcode.com/problems/find-kth-largest-xor-coordinate-value/discuss/1032010/Detailed-Explanation-or-C%2B%2B-Solution-or-Easy-Implementation
/**
Step 1: Change the matrix so that matrix[i][j] will contain the xor of all the numbers in the ith row upto jth column as shown below.

a  b  c       a  a^b  a^b^c
f  g  h  -->  f  f^g  f^g^h
k  l  m       k  k^l  k^l^m

Step 2: Change the above created matrix so that matrix[i][j] will contain the xor of all the numbers in the jth column upto ith row as shown below.

a  a^b  a^b^c       a       a^b          a^b^c
f  f^g  f^g^h  -->  a^f     a^b^f^g      a^b^c^f^g^h
k  k^l  k^l^m       a^f^k   a^b^f^g^k^l  a^b^c^f^g^h^k^l^m

Step 3: In the above created matrix matrix[i][j] contains the xor value of submatrix from (0,0) to (i,j). Hence we can use below two ways to find the kth largest value.
(Similar Problem - [215] Kth Larges`t Element in an Array)
       1.  Sort all the values and find the kth largest from that.
       2.  Use a priority queue of size k and insert only if the new element is greater than the top of the queue
I have used the second method.
Time Complexity : O(NMlogK)

Code:
int kthLargestValue(vector<vector<int>>& matrix, int k) {
        
        int i, j, n = matrix.size(), m = matrix[0].size();
        priority_queue< int, vector<int>, greater<int> >pq;
        
		// Step 1
        for(i = 0; i < n; i++)
        {
            for(j = 1; j < m; j++)
            {
                matrix[i][j] = matrix[i][j-1] ^ matrix[i][j];
            }
        }
        
		// Step 2
        for(i = 0; i < m; i++)
        {
            for(j = 1; j < n; j++)
            {
                matrix[j][i] = matrix[j-1][i] ^ matrix[j][i];
            }
        }
        
		// Step 3
        for(i = 0; i < n; i++)
        {
            for(j = 0; j < m; j++)
            {
                if(pq.size() < k)
                    pq.push(matrix[i][j]);
                else
                {
                   if(matrix[i][j] > pq.top())
                   {
                       pq.pop();
                       pq.push(matrix[i][j]);
                   }
                }
            }
        }
        
        return pq.top();
    }
*/
class Solution {
    public int kthLargestValue(int[][] matrix, int k) {
        int m = matrix.length;
        int n = matrix[0].length;
        for(int i = 0; i < m; i++) {
            for(int j = 1; j < n; j++) {
                matrix[i][j] ^= matrix[i][j - 1];
            }
        }
        for(int i = 1; i < m; i++) {
            for(int j = 0; j < n; j++) {
                matrix[i][j] ^= matrix[i - 1][j];
            }
        }
        int[] temp = new int[m * n];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                temp[i * n + j] = matrix[i][j];
            }
        }
        Arrays.sort(temp);
        return temp[m * n - k];
    }
}
