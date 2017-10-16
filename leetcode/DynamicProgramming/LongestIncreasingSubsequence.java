/**
 * Refer to
 * https://leetcode.com/problems/longest-increasing-subsequence/description/
 * http://www.lintcode.com/en/problem/longest-increasing-subsequence/
 * Given an unsorted array of integers, find the length of longest increasing subsequence.

    For example,
    Given [10, 9, 2, 5, 3, 7, 101, 18],
    The longest increasing subsequence is [2, 3, 7, 101], therefore the length is 4. 
    Note that there may be more than one LIS combination, it is only necessary for you to return the length.

    Your algorithm should run in O(n2) complexity.

    Follow up: Could you improve it to O(n log n) time complexity?
 *
 * Solution
 * https://www.jiuzhang.com/solution/longest-increasing-subsequence/
 * https://segmentfault.com/a/1190000003819886
 * 复杂度
   时间 O(N^2) 空间 O(N)
   思路
   由于这个最长上升序列不一定是连续的，对于每一个新加入的数，都有可能跟前面的序列构成一个较长的上升序列，
   或者跟后面的序列构成一个较长的上升序列。比如1,3,5,2,8,4,6，对于6来说，可以构成1,3,5,6，也可以构成
   2,4,6。因为前面那个序列长为4，后面的长为3，所以我们更愿意6组成那个长为4的序列，所以对于6来说，
   它组成序列的长度，实际上是之前最长一个升序序列长度加1，注意这个最长的序列的末尾是要小于6的，不然我们
   就把1,3,5,8,6这样的序列给算进来了。这样，我们的递推关系就隐约出来了，假设f[i]代表加入第i个数能构成
   的最长升序序列长度，我们就是要在f[0]到f[i-1]中找到一个最长的升序序列长度，又保证序列尾值nums[j]
   小于nums[i]，然后把这个长度加上1就行了。同时，我们还要及时更新最大长度
*/

class Solution {
    public int lengthOfLIS(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        // state: f[x] means add number at position x can build the LIS
        int n = nums.length;
        int[] f = new int[n];
        // initialize: every position can be a start point of LIS and
        // default length is 1 (itself for all positions)
        for(int i = 0; i < n; i++) {
            f[i] = 1;
        }
        int max = 0;
        // function: 
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < i; j++) {
                if(nums[j] < nums[i]) {
                    f[i] = Math.max(f[i], f[j] + 1);
                }
            }
            // Update global result: every position can be the end ponit of LIS
            max = Math.max(max, f[i]);
        }
        return max;
    }
}

// Solution 2: Improved By Binary Search
// Refer to
// http://www.cnblogs.com/grandyang/p/4938187.html
// https://segmentfault.com/a/1190000003819886
