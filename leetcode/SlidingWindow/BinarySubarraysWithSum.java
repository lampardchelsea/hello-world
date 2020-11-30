/**
Refer to
https://leetcode.com/problems/binary-subarrays-with-sum/
In an array A of 0s and 1s, how many non-empty subarrays have sum S?

Example 1:
Input: A = [1,0,1,0,1], S = 2
Output: 4
Explanation: 
The 4 subarrays are bolded below:
[1,0,1,0,1]
[1,0,1,0,1]
[1,0,1,0,1]
[1,0,1,0,1]

Note:
A.length <= 30000
0 <= S <= A.length
A[i] is either 0 or 1.
*/

// Solution 1: Not fixed length sliding window + Template with Subarrays With K Different Integers
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/SlidingWindow/SubarraysWithKDifferentIntegers.java
// https://leetcode.com/problems/binary-subarrays-with-sum/discuss/186683/C++JavaPython-Sliding-Window-O(1)-Space/500171
class Solution {
    public int numSubarraysWithSum(int[] A, int S) {
        return helper(A, S) - helper(A, S - 1);
    }
    
    private int helper(int[] A, int S) {
        int i = 0;
        int count = 0;
        int sum = 0;
        for(int j = 0; j < A.length; j++) {
            sum += A[j];
            // i < j will error out on below test case:
            // Input [0,0,0,0,0] and 0
            // expected: 15, error output: 10
            while(sum > S && i <= j) {
                sum -= A[i];
                i++;
            }
            count += j - i + 1;
        }
        return count;
    }
}

// Solution 2: Prefix Sum
// Refer to
// https://leetcode.com/problems/binary-subarrays-with-sum/solution/
/**
Approach 2: Prefix Sums
Intuition
Let P[i] = A[0] + A[1] + ... + A[i-1]. Then P[j+1] - P[i] = A[i] + A[i+1] + ... + A[j], the sum of the subarray [i, j].
Hence, we are looking for the number of i < j with P[j] - P[i] = S.

Algorithm
For each j, let's count the number of i with P[j] = P[i] + S. This is analogous to counting the number of subarrays ending in j with sum S.
It comes down to counting how many P[i] + S we've seen before. We can keep this count on the side to help us find the final answer.

class Solution {
    public int numSubarraysWithSum(int[] A, int S) {
        int N = A.length;
        int[] P = new int[N + 1];
        for (int i = 0; i < N; ++i)
            P[i+1] = P[i] + A[i];

        Map<Integer, Integer> count = new HashMap();
        int ans = 0;
        for (int x: P) {
            ans += count.getOrDefault(x, 0);
            count.put(x+S, count.getOrDefault(x+S, 0) + 1);
        }

        return ans;
    }
}
*/
