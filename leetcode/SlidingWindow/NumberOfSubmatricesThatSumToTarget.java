/**
Refer to
https://leetcode.com/problems/number-of-submatrices-that-sum-to-target/
Given a matrix and a target, return the number of non-empty submatrices that sum to target.

A submatrix x1, y1, x2, y2 is the set of all cells matrix[x][y] with x1 <= x <= x2 and y1 <= y <= y2.

Two submatrices (x1, y1, x2, y2) and (x1', y1', x2', y2') are different if they have some coordinate that is different: for example, if x1 != x1'.

Example 1:
0 1 0
1 1 1
0 1 0
Input: matrix = [[0,1,0],[1,1,1],[0,1,0]], target = 0
Output: 4
Explanation: The four 1x1 submatrices that only contain 0.

Example 2:
Input: matrix = [[1,-1],[-1,1]], target = 0
Output: 5
Explanation: The two 1x2 submatrices, plus the two 2x1 submatrices, plus the 2x2 submatrix.

Example 3:
Input: matrix = [[904]], target = 0
Output: 0

Constraints:
1 <= matrix.length <= 100
1 <= matrix[0].length <= 100
-1000 <= matrix[i] <= 1000
-10^8 <= target <= 10^8
*/

// Solution 1: 560. Subarray Sum Equals K + Not fixed sliding window implement by two pointers to control columns range
// Refer to
// https://leetcode.com/problems/number-of-submatrices-that-sum-to-target/discuss/303750/JavaC%2B%2BPython-Find-the-Subarray-with-Target-Sum
/**
Intuition
Preaquis: 560. Subarray Sum Equals K
Find the Subarray with Target Sum in linear time.

Explanation
For each row, calculate the prefix sum.
For each pair of columns,
calculate the accumulated sum of rows.
Now this problem is same to, "Find the Subarray with Target Sum".

Complexity
Time O(MN^2)
Space O(N)

Java
    public int numSubmatrixSumTarget(int[][] A, int target) {
        int res = 0, m = A.length, n = A[0].length;
        for (int i = 0; i < m; i++)
            for (int j = 1; j < n; j++)
                A[i][j] += A[i][j - 1];
        Map<Integer, Integer> counter = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                counter.clear();
                counter.put(0, 1);
                int cur = 0;
                for (int k = 0; k < m; k++) {
                    cur += A[k][j] - (i > 0 ? A[k][i - 1] : 0);
                    res += counter.getOrDefault(cur - target, 0);
                    counter.put(cur, counter.getOrDefault(cur, 0) + 1);
                }
            }
        }
        return res;
    }
    
Note: 560. Subarray Sum Equals K
https://github.com/lampardchelsea/hello-world/blob/master/leetcode/array/SubarraySumEqualsK.java
Approach #4 Using hashmap [Accepted]
Algorithm
The idea behind this approach is as follows: If the cumulative sum(represented by sum[i] for sum upto ith index) 
upto two indices is the same, the sum of the elements lying in between those indices is zero. Extending the same 
thought further, if the cumulative sum upto two indices, say i and j is at a difference of k 
i.e. if sum[i] - sum[j] = k, the sum of elements lying between indices i and j is k.
Based on these thoughts, we make use of a hashmap which is used to store the cumulative sum upto all the 
indices possible along with the number of times the same sum occurs. We store the data in the form: 
(sum_i, no. of occurences of sum_i) We traverse over the array nums and keep on finding the cumulative sum. 
Every time we encounter a new sum, we make a new entry in the hashmap corresponding to that sum. If the same 
sum occurs again, we increment the count corresponding to that sum in the hashmap. 
Further, for every sum encountered, we also determine the number of times the sum k has occured already, 
since it will determine the number of times a subarray with sum k has occured upto the current index. 
We increment the count by the same amount.
After the complete array has been traversed, the count gives the required result.
Complexity Analysis
Time complexity : O(n) The entire nums array is traversed only once.
Space complexity : O(n) Hashmap map can contain upto nn distinct entries in the worst case.

class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        int sum = 0;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(0, 1);
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if(map.containsKey(sum - k)) {
                count += map.get(sum - k);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
}
*/

class Solution {
    public int numSubmatrixSumTarget(int[][] matrix, int target) {
        // Create pre-sum matrix in 2D array row by row, for each row
        // the logic exactly same as 560. Subarray Sum Equals K
        int m = matrix.length;
        int n = matrix[0].length;
        for(int i = 0; i < m; i++) {
            for(int j = 1; j < n; j++) {
                matrix[i][j] = matrix[i][j - 1] + matrix[i][j];
            }
        }
        int result = 0;
        Map<Integer, Integer> counter = new HashMap<>();
        // Outside two for loops with i, j to control columns range as sliding window
        // i range from 0 to n - 1, j range from i to n - 1, both for columns
        for(int i = 0; i < n; i++) {
            for(int j = i; j < n; j++) {
                counter.clear();
                counter.put(0, 1);
                int cur = 0;
                // Inside for loop with k to go through all rows to sum up as 1D array,
                // exactly same idea as 560. Subarray Sum Equals K
                for(int k = 0; k < m; k++) {
                    // For first column element not able to substract previous column element
                    if(i == 0) {
                        cur += matrix[k][j];
                    } else {
                        cur += matrix[k][j] - matrix[k][i - 1];
                    }
                    result += counter.getOrDefault(cur - target, 0);
                    counter.put(cur, counter.getOrDefault(cur, 0) + 1);
                }
            }
        }
        return result;
    }
}
