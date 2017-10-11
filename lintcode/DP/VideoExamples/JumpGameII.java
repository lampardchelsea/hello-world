/**
 * Refer to
 * http://www.lintcode.com/en/problem/jump-game-ii/
 * https://leetcode.com/problems/jump-game-ii/description/
 * Given an array of non-negative integers, you are initially positioned at the first 
   index of the array.

  Each element in the array represents your maximum jump length at that position.

  Your goal is to reach the last index in the minimum number of jumps.
  For example:
  Given array A = [2,3,1,1,4]

  The minimum number of jumps to reach the last index is 2. (Jump 1 step from index 0 to 1, 
  then 3 steps to the last index.)

  Note:
  You can assume that you can always reach the last index.
 * 
 * Solution
 * http://blog.unieagle.net/2012/09/29/leetcode%E9%A2%98%E7%9B%AE%EF%BC%9Ajump-game-ii%EF%BC%8C%E4%B8%80%E7%BB%B4%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92/
 * https://www.jiuzhang.com/solution/jump-game-ii/
 * https://segmentfault.com/a/1190000003488956
*/
class Solution {
    public int jump(int[] nums) {
        // state: f[x] means steps from position x to last 
        int n = nums.length;
        int[] f = new int[n];
        // initialize:
        f[n - 1] = 0;
        // function
        // moving forward to start position
        for(int i = n - 2; i >= 0; i--) {
            // Compare to Jump Game, use one more variable to
            // record minimum steps on status function
            int minSteps = n + 1;
            int furthestJump = Math.min(i + nums[i], n - 1);
            for(int j = i + 1; j <= furthestJump; j++) {
                if(minSteps > f[j] + 1) {
                    minSteps = f[j] + 1;
                }
            }
            f[i] = minSteps;
        }
        // answer
        return f[0];
    }
}
