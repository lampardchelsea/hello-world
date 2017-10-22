/**
 * Refer to
 * http://www.lintcode.com/en/problem/paint-fence/
 * There is a fence with n posts, each post can be painted with one of the k colors.
   You have to paint all the posts such that no more than two adjacent fence posts have the same color.
   Return the total number of ways you can paint the fence.
 *
 * Solution
 * https://segmentfault.com/a/1190000003790650
 * http://www.cnblogs.com/grandyang/p/5231220.html
 * https://discuss.leetcode.com/topic/23426/o-n-time-java-solution-o-1-space
*/
// Solution 1:
// Refer to
// https://segmentfault.com/a/1190000003790650
/**
 思路
 这种给定一个规则，计算有多少种结果的题目一般都是动态规划，因为我们可以从这个规则中得到递推式。
 根据题意，不能有超过连续两根柱子是一个颜色，也就意味着第三根柱子要么根第一个柱子不是一个颜色，
 要么跟第二根柱子不是一个颜色。如果不是同一个颜色，计算可能性的时候就要去掉之前的颜色，也就是
 k-1种可能性。假设dp[1]是第一根柱子及之前涂色的可能性数量，dp[2]是第二根柱子及之前涂色的可能
 性数量，则dp[3]=(k-1)*dp[1] + (k-1)*dp[2]。
 递推式有了，下面再讨论下base情况，所有柱子中第一根涂色的方式有k中，第二根涂色的方式则是k*k，
 因为第二根柱子可以和第一根一样。
*/
public class Solution {
    /*
     * @param n: non-negative integer, n posts
     * @param k: non-negative integer, k colors
     * @return: an integer, the total number of ways
     */
    public int numWays(int n, int k) {
        int[] dp = {0, k, k * k, 0};
        if(n <= 2) {
            return dp[n];
        }
        for(int i = 2; i < n; i++) {
            // 递推式：第三根柱子要么根第一个柱子不是一个颜色，要么跟第二根柱子不是一个颜色
            dp[3] = (k - 1) * (dp[1] + dp[2]);
            dp[1] = dp[2];
            dp[2] = dp[3];
        }
        return dp[3];
    }
}

// Solution 2
// Refer to
// https://discuss.leetcode.com/topic/24432/java-dp-solution
// https://discuss.leetcode.com/topic/24432/java-dp-solution/3
public class Solution {
    /*
     * @param n: non-negative integer, n posts
     * @param k: non-negative integer, k colors
     * @return: an integer, the total number of ways
     */
    public int numWays(int n, int k) {
        if(n == 0 || k == 0) {
            return 0;
        }
        if(n == 1) {
            return k;
        }
        // state
        // same[i] means the ith post has the same color with the (i-1)th post.
        int[] same = new int[n];
        // diff[i] means the ith post has a different color with the (i-1)th post.
        int[] diff = new int[n];
        // initialize
        same[0] = same[1] = k;
        diff[0] = k;
        diff[1] = k * (k - 1);
        // function
        for(int i = 2; i < n; i++) {
            // the i-th in same should be equal the previous one in diff since 
            // only two consectutive same are allowed
            same[i] = diff[i - 1];
            // the i-th in diff should be either different from its previous 
            // one or from the one before the previous one
            diff[i] = (k - 1) * (diff[i - 1] + same[i - 1]);
        }
        // answer
        return diff[n - 1] + same[n - 1];
    }
}






