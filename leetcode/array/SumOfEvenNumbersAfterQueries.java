/**
Refer to
https://leetcode.com/problems/sum-of-even-numbers-after-queries/
We have an array A of integers, and an array queries of queries.

For the i-th query val = queries[i][0], index = queries[i][1], we add val to A[index].  
Then, the answer to the i-th query is the sum of the even values of A.

(Here, the given index = queries[i][1] is a 0-based index, and each query permanently modifies the array A.)

Return the answer to all queries.  Your answer array should have answer[i] as the answer to the i-th query.

Example 1:
Input: A = [1,2,3,4], queries = [[1,0],[-3,1],[-4,0],[2,3]]
Output: [8,6,2,4]
Explanation: 
At the beginning, the array is [1,2,3,4].
After adding 1 to A[0], the array is [2,2,3,4], and the sum of even values is 2 + 2 + 4 = 8.
After adding -3 to A[1], the array is [2,-1,3,4], and the sum of even values is 2 + 4 = 6.
After adding -4 to A[0], the array is [-2,-1,3,4], and the sum of even values is -2 + 4 = 2.
After adding 2 to A[3], the array is [-2,-1,3,6], and the sum of even values is -2 + 6 = 4.

Note:
1 <= A.length <= 10000
-10000 <= A[i] <= 10000
1 <= queries.length <= 10000
-10000 <= queries[i][0] <= 10000
0 <= queries[i][1] < A.length
*/

// Solution 1: Native Solution (TLE 56/58)
class Solution {
    public int[] sumEvenAfterQueries(int[] A, int[][] queries) {
        int len = queries.length;
        int[] result = new int[len];
        for(int i = 0; i < len; i++) {
            A[queries[i][1]] += queries[i][0];
            for(int j = 0; j < A.length; j++) {
                if(A[j] % 2 == 0) {
                    result[i] += A[j];
                }
            }
        }
        return result;
    }
}

// Solution 2: [Java/Python 3] odd / even analysis, time O(max(m, n))
// Refer to
// https://leetcode.com/problems/sum-of-even-numbers-after-queries/discuss/231099/JavaPython-3-odd-even-analysis-time-O(max(m-n))
/**
Track sum of all even #s.
There are 4 cases for odd / even property of A[k] and queries[i][0], where k = queries[i][1]:
1). even and odd, lose an even item in A; sum need to deduct A[k];
2). even and even, get a bigger even item in A; sum need to add queries[i][0](same as deduct A[k] first then add both);
3). odd and odd, get a bigger even item in A; sum need to add both;
4). odd and even, no influence on even items in A;
*/
class Solution {
    public int[] sumEvenAfterQueries(int[] A, int[][] queries) {
        int[] result = new int[queries.length];
        int even_sum = 0;
        for(int i = 0; i < A.length; i++) {
            if(A[i] % 2 == 0) {
                even_sum += A[i];
            }
        }
        for(int i = 0; i < queries.length; i++) {
            int idx = queries[i][1];
            if(A[idx] % 2 == 0) {
                even_sum -= A[idx];
            }
            A[idx] += queries[i][0];
            if(A[idx] % 2 == 0) {
                even_sum += A[idx];
            }
            result[i] = even_sum;
        }
        return result;
    }
}
