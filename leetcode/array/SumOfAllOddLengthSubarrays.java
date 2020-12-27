/**
Refer to
https://leetcode.com/problems/sum-of-all-odd-length-subarrays/
Given an array of positive integers arr, calculate the sum of all possible odd-length subarrays.

A subarray is a contiguous subsequence of the array.

Return the sum of all odd-length subarrays of arr.

Example 1:
Input: arr = [1,4,2,5,3]
Output: 58
Explanation: The odd-length subarrays of arr and their sums are:
[1] = 1
[4] = 4
[2] = 2
[5] = 5
[3] = 3
[1,4,2] = 7
[4,2,5] = 11
[2,5,3] = 10
[1,4,2,5,3] = 15
If we add all these together we get 1 + 4 + 2 + 5 + 3 + 7 + 11 + 10 + 15 = 58

Example 2:
Input: arr = [1,2]
Output: 3
Explanation: There are only 2 subarrays of odd length, [1] and [2]. Their sum is 3.

Example 3:
Input: arr = [10,11,12]
Output: 66

Constraints:
1 <= arr.length <= 100
1 <= arr[i] <= 1000
*/

// Solution 1: Brute Force O(N^3)
// Refer to
// https://helloacm.com/algorithms-to-sum-of-all-odd-length-subarrays/
/**
Since the given input array size of maximum 100, we can bruteforce. We can bruteforce every pair of (i, j) and compute 
the sum of subarray from index i to j – when the sub array are length of odd numbers.
*/
class Solution {
    public int sumOddLengthSubarrays(int[] arr) {
        int n = arr.length;
        int sum = 0;
        for(int i = 0; i < n; i++) {
            for(int j = i; j < n; j++) {
                if((j - i + 1) % 2 == 1) {
                    for(int k = i; k <= j; k++) {
                        sum += arr[k];
                    }
                }
            }
        }
        return sum;
    }
}

// Solution 2: PreSum O(N^2)
// Style 1:
// Refer to
// https://helloacm.com/algorithms-to-sum-of-all-odd-length-subarrays/
/**
The time complexity is O(N^3) as we are using another inner loop to compute the sum. This can be improved by using an 
accumulated sum, thus improving the solution to O(N^2).
*/
class Solution {
    public int sumOddLengthSubarrays(int[] arr) {
        int n = arr.length;
        int result = 0;
        for(int i = 0; i < n; i++) {
            int sum = 0;
            for(int j = i; j < n; j++) {
                sum += arr[j];
                if((j - i + 1) % 2 == 1) {
                    result += sum;
                }
            }
        }
        return result;
    }
}

// Style 2:
// Refer to
// https://leetcode.com/problems/sum-of-all-odd-length-subarrays/discuss/854154/JavaPython-3-Prefix-Sum.
class Solution {
    public int sumOddLengthSubarrays(int[] arr) {
        int n = arr.length;
        int[] preSum = new int[n + 1];
        for(int i = 0; i < n; i++) {
            preSum[i + 1] = preSum[i] + arr[i]; 
        }
        int result = 0;
        for(int i = 0; i < n; i++) {
            for(int j = i + 1; j <= n; j += 2) {
                result += preSum[j] - preSum[i];
            }
        }
        return result;
    }
}

// Solution 3: 
// Refer to
// https://helloacm.com/algorithms-to-sum-of-all-odd-length-subarrays/
/**
O(N) LINEAR ALGORITHM TO COMPUTE THE SUM OF SUBARRAY OF ODD LENGTH
We can sum up the contribution of A[i] – then it will be a linear solution.
*/

// https://leetcode.com/problems/sum-of-all-odd-length-subarrays/discuss/854184/JavaC%2B%2BPython-O(N)-Time-O(1)-Space
/**
Intuition
Hmmm, totally not an easy problem.
That where it's misleading:
It lets brute force get accepted,
and mark it as easy.

Solution 1: Brute Force
Enumerate all possible odd length of subarray.
Time O(n^3)
Space O(1)

Solution 2: Consider the contribution of A[i]
Also suggested by @mayank12559 and @simtully.

Consider the subarray that contains A[i],
we can take 0,1,2..,i elements on the left,
from A[0] to A[i],
we have i + 1 choices.

we can take 0,1,2..,n-1-i elements on the right,
from A[i] to A[n-1],
we have n - i choices.

In total, there are (i + 1) * (n - i) subarrays, that contains A[i].
And there are ((i + 1) * (n - i) + 1) / 2 subarrays with odd length, that contains A[i].
A[i] will be counted ((i + 1) * (n - i) + 1) / 2 times.

Example of array [1,2,3,4,5]
1 2 3 4 5 subarray length 1
1 2 X X X subarray length 2
X 2 3 X X subarray length 2
X X 3 4 X subarray length 2
X X X 4 5 subarray length 2
1 2 3 X X subarray length 3
X 2 3 4 X subarray length 3
X X 3 4 5 subarray length 3
1 2 3 4 X subarray length 4
X 2 3 4 5 subarray length 4
1 2 3 4 5 subarray length 5

5 8 9 8 5 total times each index was added.
3 4 5 4 3 total times in odd length array with (x + 1) / 2
2 4 4 4 2 total times in even length array with x / 2

Complexity
Time O(N)
Space O(1)

Java
    public int sumOddLengthSubarrays(int[] A) {
        int res = 0, n = A.length;
        for (int i = 0; i < n; ++i) {
            res += ((i + 1) * (n - i) + 1) / 2 * A[i];
        }
        return res;
    }
*/
