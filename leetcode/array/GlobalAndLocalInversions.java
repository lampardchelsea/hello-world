/**
Refer to
https://leetcode.com/problems/global-and-local-inversions/
You are given an integer array nums of length n which represents a permutation of all the integers in the range [0, n - 1].

The number of global inversions is the number of the different pairs (i, j) where:

0 <= i < j < n
nums[i] > nums[j]

The number of local inversions is the number of indices i where:
0 <= i < n - 1
nums[i] > nums[i + 1]

Return true if the number of global inversions is equal to the number of local inversions.

Example 1:
Input: nums = [1,0,2]
Output: true
Explanation: There is 1 global inversion and 1 local inversion.

Example 2:
Input: nums = [1,2,0]
Output: false
Explanation: There are 2 global inversions and 1 local inversion.

Constraints:
n == nums.length
1 <= n <= 5000
0 <= nums[i] < n
All the integers of nums are unique.
nums is a permutation of all the numbers in the range [0, n - 1]
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/global-and-local-inversions/discuss/113644/C%2B%2BJavaPython-Easy-and-Concise-Solution
/**
Explanation
All local inversions are global inversions.
If the number of global inversions is equal to the number of local inversions,
it means that all global inversions in permutations are local inversions.
It also means that we can not find A[i] > A[j] with i+2<=j.
In other words, max(A[i]) < A[i+2]

In this first solution, I traverse A and keep the current biggest number cmax.
Then I check the condition cmax < A[i+2]

Here come this solutions

Solution 1
public boolean isIdealPermutation(int[] A) {
        int cmax = 0;
        for (int i = 0; i < A.length - 2; ++i) {
            cmax = Math.max(cmax, A[i]);
            if (cmax > A[i + 2]) return false;
        }
        return true;
    }
*/
class Solution {
    public boolean isIdealPermutation(int[] A) {
        int cur_max = 0;
        for(int i = 0; i < A.length - 2; i++) {
            cur_max = Math.max(cur_max, A[i]);
            if(cur_max > A[i + 2]) {
                return false;
            }
        }
        return true;
    }
}
