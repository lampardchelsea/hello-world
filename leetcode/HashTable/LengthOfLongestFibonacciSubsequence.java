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

// Solution 1: HashMap
// Refer to
// https://leetcode.com/problems/length-of-longest-fibonacci-subsequence/discuss/152343/C%2B%2BJavaPython-Check-Pair
/**
Solution 1
Save array A to a hash set s.
Start from base (A[i], A[j]) as the first two element in the sequence,
we try to find the Fibonacci like subsequence as long as possible,

Initial (a, b) = (A[i], A[j])
While the set s contains a + b, we update (a, b) = (b, a + b).
In the end we update the longest length we find.

Time Complexity:
O(N^2logM), where M is the max(A).

Quote from @renato4:
Just clarifying a little bit more.
Since the values grow exponentially,
the amount of numbers needed to accommodate a sequence
that ends in a number M is at most log(M).
*/
class Solution {
    public int lenLongestFibSubseq(int[] arr) {
        int n = arr.length;
        Set<Integer> set = new HashSet<Integer>();
        for(int a : arr) {
            set.add(a);
        }
        int max_len = 2;
        for(int i = 0; i < n; i++) {
            for(int j = i + 1; j < n; j++) {
                int cur_len = 2;
                int a = arr[i];
                int b = arr[j];
                while(set.contains(a + b)) {
                    b = a + b;
                    a = b - a;
                    cur_len++;
                }
                max_len = Math.max(max_len, cur_len);
            }
        }
        return max_len > 2 ? max_len : 0;
    }
}
