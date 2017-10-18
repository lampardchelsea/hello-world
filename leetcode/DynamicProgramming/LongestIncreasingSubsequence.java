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
/**
 我们来看一种思路更清晰的二分查找法，跟上面那种方法很类似，思路是先建立一个空的dp数组，然后开始遍历原数组，
 对于每一个遍历到的数字，我们用二分查找法在dp数组找第一个不小于它的数字，如果这个数字不存在，那么直接在dp
 数组后面加上遍历到的数字，如果存在，则将这个数字更新为当前遍历到的数字，最后返回dp数字的长度即可，注意的是，
 跟上面的方法一样，特别注意的是dp数组的值可能不是一个真实的LIS 
*/

// https://segmentfault.com/a/1190000003819886
// https://leetcode.com/problems/longest-increasing-subsequence/solution/
/**
 Approach #4 Dynamic Programming with Binary Search[Accepted]:
 Algorithm
 In this approach, we scan the array from left to right. We also make use of a dp array initialized 
 with all 0's. This dp array is meant to store the increasing subsequence formed by including the 
 currently encountered element. While traversing the nums array, we keep on filling the dp array 
 with the elements encountered so far. For the element corresponding to the jth index (nums[j]), we 
 determine its correct position in the dp array(say ith index) by making use of Binary Search(which 
 can be used since the dp array is storing increasing subsequence) and also insert it at the correct 
 position. An important point to be noted is that for Binary Search, we consider only that portion of 
 the dp array in which we have made the updations by inserting some elements at their correct 
 positions(which remains always sorted). Thus, only the elements upto the ith index in the dp array 
 can determine the position of the current element in it. Since, the element enters its correct 
 position(i) in an ascending order in the dp array, the subsequence formed so far in it is surely 
 an increasing subsequence. Whenever this position index ii becomes equal to the length of the LIS 
 formed so far(len), it means, we need to update the len as len = len + 1.
 Note: dp array does not result in longest increasing subsequence, but length of dpdp array will 
 give you length of LIS.
 
 Consider the example:
 input: [0, 8, 4, 12, 2]
 dp: [0]
 dp: [0, 8]
 dp: [0, 4]
 dp: [0, 4, 12]
 dp: [0 , 2, 12] which is not the longest increasing subsequence, but length of dp array results in 
                 length of Longest Increasing Subsequence.
 
 Note: Arrays.binarySearch() method returns index of the search key, if it is contained in the array, 
 else it returns (-(insertion point) - 1). The insertion point is the point at which the key would be 
 inserted into the array: the index of the first element greater than the key, or a.length if all 
 elements in the array are less than the specified key.
 
 Complexity Analysis
 Time complexity : O(nlog(n)). Binary search takes log(n) time and it is called n times.
 Space complexity : O(n). dp array of size nn is used.
*/
class Solution {
    public int lengthOfLIS(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        // state
        int m = nums.length;
        int[] dp = new int[m];
        // initialize
        int size = 0;
        dp[0] = nums[0];
        // Our strategy determined by the following conditions
        // (1) If nums[i] is smallest among all end
        // candidates of active lists, we will start
        // new active list of length 1.
        // (2) If nums[i] is largest among all end candidates of
        // active lists, we will clone the largest active
        // list, and extend it by nums[i].
        // (3) If nums[i] is in between, we will find a list with
        // largest end element that is smaller than nums[i].
        // Clone and extend this list by nums[i]. We will discard all
        // other lists of same length as that of this modified list."
        for(int i = 1; i < m; i++) {
            if(nums[i] < dp[0]) {
                dp[0] = nums[i];
            } else if(nums[i] > dp[size]) {
                size++;
                dp[size] = nums[i];
            } else {
                dp[index(dp, 0, size, nums[i])] = nums[i];
            }
        }
        return size + 1;
    }
    
    public int index(int[] dp, int start, int end, int target) {
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (dp[mid] == target) {
                return mid;
            } else if (dp[mid] < target) { 
                start = mid;
            } else {
                end = mid;
            }
        }
        if (dp[start] >= target) {
            return start;
        } else {
            return end;
        }
    }
}
