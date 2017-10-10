/**
 * Refer to
 * https://leetcode.com/problems/jump-game/description/
 * http://www.lintcode.com/en/problem/jump-game/
 * Given an array of non-negative integers, you are initially positioned at the first index of the array.

    Each element in the array represents your maximum jump length at that position.
    Determine if you are able to reach the last index.

     Notice
    This problem have two method which is Greedy and Dynamic Programming.
    The time complexity of Greedy method is O(n).
    The time complexity of Dynamic Programming method is O(n^2).

    We manually set the small data set to allow you pass the test in both ways. 
    This is just to let you learn how to use this problem in dynamic programming ways. 
    If you finish it in dynamic programming ways, you can try greedy method to make 
    it accept again.

    Have you met this question in a real interview? Yes
    Example
    A = [2,3,1,1,4], return true.
    A = [3,2,1,0,4], return false.
 *
 * Solution
 * https://leetcode.com/articles/jump-game/
 * Top-down to bottom-up conversion is done by eliminating recursion. In practice, 
   this achieves better performance as we no longer have the method stack overhead 
   and might even benefit from some caching. More importantly, this step opens up 
   possibilities for future optimization. The recursion is usually eliminated by 
   trying to reverse the order of the steps from the top-down approach.
   
   The observation to make here is that we only ever jump to the right. This means 
   that if we start from the right of the array, every time we will query a position 
   to our right, that position has already be determined as being GOOD or BAD. This 
   means we don't need to recurse anymore, as we will always hit the memo table.
   
   Complexity Analysis
   Time complexity : O(n^2) 
   For every element in the array, say i, we are looking at the next nums[i] elements 
   to its right aiming to find a GOOD index. nums[i] can be at most nn, where nn is 
   the length of array nums.
   Space complexity : O(n). This comes from the usage of the memo table.
 *
 * https://www.jiuzhang.com/solution/jump-game/
*/
class Solution {
    public boolean canJump(int[] nums) {
        // state:
        int n = nums.length;
        boolean[] f = new boolean[n];
        // intialize
        // the last position is definitely able to reach
        f[n - 1] = true;
        // function
        // moveing forward to start position
        for(int i = n - 2; i >= 0; i--) {
            int furthestJump = Math.min(i + nums[i], n - 1);
            for(int j = i + 1; j <= furthestJump; j++) {
                if(f[j]) {
                    f[i] = true;
                    break;
                }
            }
        }
        // answer
        return f[0];
    }
}
