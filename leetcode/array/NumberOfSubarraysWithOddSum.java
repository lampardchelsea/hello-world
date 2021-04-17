/**
Refer to
https://leetcode.com/problems/number-of-sub-arrays-with-odd-sum/
Given an array of integers arr. Return the number of sub-arrays with odd sum.

As the answer may grow large, the answer must be computed modulo 10^9 + 7.

Example 1:
Input: arr = [1,3,5]
Output: 4
Explanation: All sub-arrays are [[1],[1,3],[1,3,5],[3],[3,5],[5]]
All sub-arrays sum are [1,4,9,3,8,5].
Odd sums are [1,9,3,5] so the answer is 4.

Example 2:
Input: arr = [2,4,6]
Output: 0
Explanation: All sub-arrays are [[2],[2,4],[2,4,6],[4],[4,6],[6]]
All sub-arrays sum are [2,6,12,4,10,6].
All sub-arrays have even sum and the answer is 0.

Example 3:
Input: arr = [1,2,3,4,5,6,7]
Output: 16

Example 4:
Input: arr = [100,100,99,99]
Output: 4

Example 5:
Input: arr = [7]
Output: 1

Constraints:
1 <= arr.length <= 10^5
1 <= arr[i] <= 100
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/number-of-sub-arrays-with-odd-sum/discuss/754706/C%2B%2BJava-O(n)
/**
Update: added simplified solution below.

If we know the number of even and odd subarrays that end at the previous element, we can figure out how many even and odd subarrays we have for element n:

If n is even, we increase the number of even subarrays; the number of odd subarrays does not change.
If n is odd, the number of odd subarrays is the previous number of even subarrays + 1. The number of even subarrays is the previous number of odd subarrays.

Looking at this example:
Array: [1, 1, 2, 1]  Total
Odd:    1  1  1  3     6
Even:   0  1  2  1

public int numOfSubarrays(int[] arr) {
    int odd = 0, even = 0, sum = 0;
    for (int n : arr) {
        if (n % 2 == 1) {
            int temp = odd;
            odd = even + 1;
            even = temp;
        }
        else
            ++even;
        sum = (sum + odd) % 1000000007;
    }
    return sum;
}

Simplified Solution
Since odd + even equals the number of elements so far, we can simplify our solution by only tracking odd.

public int numOfSubarrays(int[] arr) {
    int sum = 0;
    for (int i = 0, odd = 0; i < arr.length; ++i) {
        if (arr[i] % 2 == 1)
            odd = (i - odd) + 1;
        sum = (sum + odd)  % 1000000007;
    }
    return sum;
}

Complexity Analysis
Time: O(n)
Memory: O(1)
*/

class Solution {
    public int numOfSubarrays(int[] arr) {
        int mod = (int)1e9 + 7;
        int odd = 0;
        int even = 0;
        int sum = 0;
        for(int a : arr) {
            if(a % 2 == 0) {
                even++;
            } else {
                int tmp = odd;
                odd = even + 1;
                even = tmp;
            }
            sum = (sum + odd) % mod;
        }
        return sum;
    }
}
