/**
 Refer to
 https://leetcode.com/problems/partition-to-k-equal-sum-subsets/
 Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into k non-empty subsets whose sums are all equal.

 Example 1:
 Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
 Output: True
 Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
 
 Note:
 1 <= k <= len(nums) <= 16.
 0 < nums[i] < 10000.
*/

// Solution 1: DFS + backtracking
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DFS_BackTracking/MatchsticksToSquare.java
// Runtime: 2173 ms, faster than 5.03% of Java online submissions for Partition to K Equal Sum Subsets.
// Memory Usage: 36.2 MB, less than 7.06% of Java online submissions for Partition to K Equal Sum Subsets.
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if(sum % k != 0) {
            return false;
        }
        return helper(0, nums, new int[k], sum / k);
    }
    
    private boolean helper(int index, int[] nums, int[] sums, int target) {
        if(index == nums.length) {
            int temp = sums[0];
            for(int i = 1; i < sums.length; i++) {
                if(sums[i] != temp) {
                    return false;
                }
            }
            return true;
        }
        for(int i = 0; i < sums.length; i++) {
            if(sums[i] + nums[index] <= target) {
                sums[i] += nums[index];
                if(helper(index + 1, nums, sums, target)) {
                    return true;
                }
                sums[i] -= nums[index];
            }
        }
        return false;
    }
}

// Solution 2: Optimization by sorting the array first
// Refer to
// 1. 473. Matchsticks to Square is special case for 698. Partition to K Equal Sum Subsets
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DFS_BackTracking/MatchsticksToSquare.java
/**
 Sorting the input array DESC will make the DFS process run much faster. Reason behind this is we always try 
 to put the next matchstick in the first subset. If there is no solution, trying a longer matchstick first 
 will get to negative conclusion earlier. Following is the updated code. Runtime is improved from more than 
 1000ms to around 40ms. A big improvement.
 We can either reverse array as one more function or we can traverse indexes from end to start index
 Here we choose the 2nd way since it will take even less time and boost the time from 2173ms to 22ms
*/
// 2. Java beat 100%
// https://leetcode.com/problems/partition-to-k-equal-sum-subsets/discuss/108741/Solution-with-Reference
// 3. We need to use if(index == -1) instead of (index == 0) for the terminate case
// https://leetcode.com/problems/matchsticks-to-square/discuss/95729/Java-DFS-Solution-with-Explanation/100200
// 4. 中文解释
// https://www.cnblogs.com/grandyang/p/7733098.html
/**
 下面这种方法也挺巧妙的，思路是建立长度为k的数组v，只有当v里面所有的数字都是target的时候，才能返回true。我们还需要给数组排个序，
 由于题目中限制了全是正数，所以数字累加只会增大不会减小，一旦累加超过了target，这个子集合是无法再变小的，所以就不能加入这个数。
 实际上相当于贪婪算法，由于题目中数组数字为正的限制，有解的话就可以用贪婪算法得到。我们用一个变量idx表示当前遍历的数字，排序后，
 我们从末尾大的数字开始累加，我们遍历数组v，当前位置加上nums[idx]，如果超过了target，我们掉过继续到下一个位置，否则就调用递归，
 此时的idx为idx-1，表示之前那个数字已经成功加入数组v了，我们尝试着加下一个数字。如果递归返回false了，我们就将nums[idx]从数组v
 中对应的位置减去，还原状态，然后继续下一个位置。如果某个递归中idx等于-1了，表明所有的数字已经遍历完了，此时我们检查数组v中k个
 数字是否都为target，是的话返回true，否则返回false
*/
// Runtime: 22 ms, faster than 30.64% of Java online submissions for Partition to K Equal Sum Subsets.
// Memory Usage: 36.4 MB, less than 7.06% of Java online submissions for Partition to K Equal Sum Subsets.
class Solution {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if(sum % k != 0) {
            return false;
        }
        Arrays.sort(nums);
        return helper(nums.length - 1, nums, new int[k], sum / k);
    }
    
    private boolean helper(int index, int[] nums, int[] sums, int target) {
        // Must index == -1 instead of index == 0, test case: [4,3,2,3,5,2,1] and k = 4
        // because for sums[i] += nums[index] also need to be checked when index = 0,
        // then pass index = 0 to next level as helper(index - 1, ....), the terminate 
        // checking condition should be if(index == -1) which will include previous
        // level index = 0
        if(index == -1) {
            int temp = sums[0];
            for(int i = 1; i < sums.length; i++) {
                if(sums[i] != temp) {
                    return false;
                }
            }
            return true;
        }
        for(int i = 0; i < sums.length; i++) {
            if(sums[i] + nums[index] <= target) {
                sums[i] += nums[index];
                if(helper(index - 1, nums, sums, target)) {
                    return true;
                }
                sums[i] -= nums[index];
            }
        }
        return false;
    }
}
