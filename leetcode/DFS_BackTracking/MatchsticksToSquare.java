/**
 Refer to
 https://leetcode.com/problems/matchsticks-to-square/
 Remember the story of Little Match Girl? By now, you know exactly what matchsticks the little match girl has, 
 please find out a way you can make one square by using up all those matchsticks. You should not break any stick, 
 but you can link them up, and each matchstick must be used exactly one time.

Your input will be several matchsticks the girl has, represented with their stick length. Your output will either 
be true or false, to represent whether you could make one square using all the matchsticks the little match girl has.

Example 1:
Input: [1,1,2,2,2]
Output: true

Explanation: You can form a square with length 2, one side of the square came two sticks with length 1.
Example 2:
Input: [3,3,3,3,4]
Output: false

Explanation: You cannot find a way to form a square with all the matchsticks.
Note:
The length sum of the given matchsticks is in the range of 0 to 10^9.
The length of the given matchstick array will not exceed 15.
*/

// Solution 1: DFS + backtracking
// Refer to
// https://leetcode.com/problems/matchsticks-to-square/discuss/95729/Java-DFS-Solution-with-Explanation
/**
 According to https://en.wikipedia.org/wiki/Partition_problem, the partition problem (or number partitioning) 
 is the task of deciding whether a given multiset S of positive integers can be partitioned into two subsets 
 S1 and S2 such that the sum of the numbers in S1 equals the sum of the numbers in S2. The partition problem 
 is NP-complete.

When I trying to think how to apply dynamic programming solution of above problem to this one (difference is 
divid S into 4 subsets), I took another look at the constraints of the problem:
The length sum of the given matchsticks is in the range of 0 to 10^9.
The length of the given matchstick array will not exceed 15.

Sounds like the input will not be very large... Then why not just do DFS? In fact, DFS solution passed judges.
Anyone solved this problem by using DP? Please let me know :)
*/
// Runtime: 1095 ms, faster than 13.53% of Java online submissions for Matchsticks to Square.
// Memory Usage: 36.5 MB, less than 10.47% of Java online submissions for Matchsticks to Square.
class Solution {
    public boolean makesquare(int[] nums) {
        if(nums == null || nums.length == 0) {
            return false;
        }
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if(sum % 4 != 0) {
            return false;
        }
        return helper(0, nums, sum / 4, new int[4]);
    }
    
    private boolean helper(int index, int[] nums, int target, int[] sums) {
        if(index == nums.length) {
            if(sums[0] == target && sums[1] == target && sums[2] == target) {
                return true;
            }
            return false;
        }
        for(int i = 0; i < 4; i++) {
            if(sums[i] + nums[index] <= target) {
                sums[i] += nums[index];
                if(helper(index + 1, nums, target, sums)) {
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
// https://leetcode.com/problems/matchsticks-to-square/discuss/95729/Java-DFS-Solution-with-Explanation
/**
 Sorting the input array DESC will make the DFS process run much faster. Reason behind this is we always try 
 to put the next matchstick in the first subset. If there is no solution, trying a longer matchstick first 
 will get to negative conclusion earlier. Following is the updated code. Runtime is improved from more than 
 1000ms to around 40ms. A big improvement.
 We can either reverse array as one more function or we can traverse indexes from end to start index
 Here we choose the 2nd way since it will take even less time and boost the time from 1095ms to 21ms
*/
// Runtime: 21 ms, faster than 68.12% of Java online submissions for Matchsticks to Square.
// Memory Usage: 36.4 MB, less than 10.47% of Java online submissions for Matchsticks to Square.
class Solution {
    public boolean makesquare(int[] nums) {
        if(nums == null || nums.length == 0) {
            return false;
        }
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if(sum % 4 != 0) {
            return false;
        }
        // We are sorting the array first
        Arrays.sort(nums);
        // Traverse index from end to start
        return helper(nums.length - 1, nums, sum / 4, new int[4]);
    }
    
    private boolean helper(int index, int[] nums, int target, int[] sums) {
        // Must index == -1 instead of index == 0, test case: [4,3,2,3,5,2,1] and k = 4
        // because for sums[i] += nums[index] also need to be checked when index = 0,
        // then pass index = 0 to next level as helper(index - 1, ....), the terminate 
        // checking condition should be if(index == -1) which will include previous
        // level index = 0
        if(index == -1) {
            if(sums[0] == target && sums[1] == target && sums[2] == target) {
                return true;
            }
            return false;
        }
        for(int i = 0; i < 4; i++) {
            if(sums[i] + nums[index] <= target) {
                sums[i] += nums[index];
                if(helper(index - 1, nums, target, sums)) {
                    return true;
                }
                sums[i] -= nums[index];
            }
        }
        return false;
    }
}
