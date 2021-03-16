/**
Refer to
https://leetcode.com/problems/partition-array-into-disjoint-intervals/
Given an array A, partition it into two (contiguous) subarrays left and right so that:

Every element in left is less than or equal to every element in right.
left and right are non-empty.
left has the smallest possible size.
Return the length of left after such a partitioning.  It is guaranteed that such a partitioning exists.

Example 1:
Input: [5,0,3,8,6]
Output: 3
Explanation: left = [5,0,3], right = [8,6]

Example 2:
Input: [1,1,1,0,6,12]
Output: 4
Explanation: left = [1,1,1,0], right = [6,12]

Note:
2 <= A.length <= 30000
0 <= A[i] <= 10^6
It is guaranteed there is at least one way to partition A as described.
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/partition-array-into-disjoint-intervals/solution/
/**
Approach 1: Next Array
Intuition
Instead of checking whether all(L <= R for L in left for R in right), let's check whether max(left) <= min(right).

Algorithm
Let's try to find max(left) for subarrays left = A[:1], left = A[:2], left = A[:3], ... etc. Specifically, maxleft[i] 
will be the maximum of subarray A[:i]. They are related to each other: max(A[:4]) = max(max(A[:3]), A[3]), 
so maxleft[4] = max(maxleft[3], A[3]).

Similarly, min(right) for every possible right can be found in linear time.

After we have a way to query max(left) and min(right) quickly, the solution is straightforward.
class Solution {
    public int partitionDisjoint(int[] A) {
        int N = A.length;
        int[] maxleft = new int[N];
        int[] minright = new int[N];

        int m = A[0];
        for (int i = 0; i < N; ++i) {
            m = Math.max(m, A[i]);
            maxleft[i] = m;
        }

        m = A[N-1];
        for (int i = N-1; i >= 0; --i) {
            m = Math.min(m, A[i]);
            minright[i] = m;
        }

        for (int i = 1; i < N; ++i)
            if (maxleft[i-1] <= minright[i])
                return i;

        throw null;
    }
}
*/
class Solution {
    public int partitionDisjoint(int[] A) {
        int n = A.length;
        int[] maxLeft = new int[n];
        int[] minRight = new int[n];
        int max = A[0];
        for(int i = 0; i < n; i++) {
            max = Math.max(max, A[i]);
            maxLeft[i] = max;
        }
        int min = A[n - 1];
        for(int i = n - 1; i >= 0; i--) {
            min = Math.min(min, A[i]);
            minRight[i] = min;
        }
        for(int i = 1; i <= n - 1; i++) {
            if(maxLeft[i - 1] <= minRight[i]) {
                return i;
            }
        }
        return -1;
    }
}




