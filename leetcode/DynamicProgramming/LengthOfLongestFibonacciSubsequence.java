/**
Refer to
https://leetcode.com/problems/length-of-longest-fibonacci-subsequence/
A sequence X1, X2, ..., Xn is Fibonacci-like if:

n >= 3
Xi + Xi+1 = Xi+2 for all i + 2 <= n
Given a strictly increasing array arr of positive integers forming a sequence, return the length of the longest 
Fibonacci-like subsequence of arr. If one does not exist, return 0.

A subsequence is derived from another sequence arr by deleting any number of elements (including none) from arr, 
without changing the order of the remaining elements. For example, [3, 5, 8] is a subsequence of [3, 4, 5, 6, 7, 8].

Example 1:
Input: arr = [1,2,3,4,5,6,7,8]
Output: 5
Explanation: The longest subsequence that is fibonacci-like: [1,2,3,5,8].

Example 2:
Input: arr = [1,3,7,11,12,14,18]
Output: 3
Explanation: The longest subsequence that is fibonacci-like: [1,11,12], [3,11,14] or [7,11,18].

Constraints:
3 <= arr.length <= 1000
1 <= arr[i] < arr[i + 1] <= 109
*/

// Solution 1: 2D DP
// Style 1: dp[a, b] represents the length of fibo sequence ends up with (a, b)
// Refer to
// https://leetcode.com/problems/length-of-longest-fibonacci-subsequence/discuss/152343/C%2B%2BJavaPython-Check-Pair
/**
Solution 2
Another solution is kind of dp.
dp[a, b] represents the length of fibo sequence ends up with (a, b)
Then we have dp[a, b] = (dp[b - a, a] + 1 ) or 2
The complexity reduce to O(N^2).
In C++/Java, I use 2D dp and index as key.
In Python, I use value as key.

Time Complexity:
O(N^2)

Java
    public int lenLongestFibSubseq(int[] A) {
        int res = 0;
        int[][] dp = new int[A.length][A.length];
        Map<Integer, Integer> index = new HashMap<>();
        for (int j = 0; j < A.length; j++) {
            index.put(A[j], j);
            for (int i = 0; i < j; i++) {
                int k = index.getOrDefault(A[j] - A[i], -1);
                dp[i][j] = (A[j] - A[i] < A[i] && k >= 0) ? dp[k][i] + 1 : 2;
                res = Math.max(res, dp[i][j]);
            }
        }
        return res > 2 ? res : 0;
    }
*/
class Solution {
    public int lenLongestFibSubseq(int[] arr) {
        int result = 0;
        int n = arr.length;
        // dp[a, b] represents the length of fibo sequence ends up with (a, b)
        // Then we have dp[a, b] = (dp[b - a, a] + 1 ) or 2
        int[][] dp = new int[n][n];
        // <value, index>
        Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
        // j for later number, i for previous number
        for(int j = 0; j < n; j++) {
            indexMap.put(arr[j], j);
            for(int i = 0; i < j; i++) {
                int k = indexMap.getOrDefault(arr[j] - arr[i], -1);
                // For a moment I couldn't understand the following condition:
                // dp[i][j] = (arr[j] - arr[i] < arr[i] && ...)
                // Finally figured out by running a simple example:
                // A = {1, 2, 3}
                // when j = 2 and i = 0, arr[j] - arr[i] = 2, since arr[j] - arr[i] > arr[i] 
                // in this case, we can't expand the length.
                // If only keep condition as 'k > -1' will error out on test case:
                // [1,2,3,4,5,6,7,8], output = 4, expected = 5
                // The math behind arr[j] - arr[i] > arr[i] is actually because we define
                // dp[i][j] as last two numbers in sequence, and there relation should not
                // be arr[j] > 2 * arr[i] because arr[j] = arr[i] + arr[i - 1], and
                // arr[i] > arr[i - 1], so not able to double, this check is necessary
                // for construct a fibo sequence
                if(arr[j] - arr[i] < arr[i] && k > -1) {
                    dp[i][j] = dp[k][i] + 1;
                } else {
                    dp[i][j] = 2;
                }
                result = Math.max(result, dp[i][j]);
            }
        }
        return result > 2 ? result : 0;
    }
}

// Style 2: dp[a, b] represents the length of fibo sequence start with (a, b)
// Refer to
// https://leetcode.com/problems/length-of-longest-fibonacci-subsequence/discuss/152332/Java-clean-DP-O(n2)-time-O(n2)-space/377212
class Solution {
    public int lenLongestFibSubseq(int[] A) {
        int res = 0, len = A.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < A.length; i++) {
            map.put(A[i], i);
        }
        int[][] dp = new int[len][len];//dp[i][j]代表A[i],A[j]开头的fib seq的最大长度
        for (int i = len - 1; i > 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if (dp[j][i] == 0) {
                    dp[j][i] = 2;
                }
                int next = A[j] + A[i];
                if (map.containsKey(next)) {
                    dp[j][i] = dp[i][map.get(next)] + 1;
                    res = Math.max(res, dp[j][i]);
                }
            }
        }
        return res >= 3 ? res : 0;
    }
}
