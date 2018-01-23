/**
 * Refer to
 * https://leetcode.com/problems/integer-break/description/
 * Given a positive integer n, break it into the sum of at least two positive integers and 
   maximize the product of those integers. Return the maximum product you can get.

  For example, given n = 2, return 1 (2 = 1 + 1); given n = 10, return 36 (10 = 3 + 3 + 4).

  Note: You may assume that n is not less than 2 and not larger than 58.
 *
 *
 * Solution
 * https://discuss.leetcode.com/topic/42978/java-dp-solution/14
*/
// Solution 1: DP
class Solution {
    public int integerBreak(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for(int i = 2; i <= n; i++) {
            int max = 1;
            for(int j = 1; j < i; j++) {
                int factor1 = Math.max(j, dp[j]);
                int factor2 = Math.max(i - j, dp[i - j]);
                max = Math.max(max, factor1 * factor2);
            }
            dp[i] = max;
        }
        return dp[n];
    }
}

// Solution 2: Math
// Refer to
// https://discuss.leetcode.com/topic/43055/why-factor-2-or-3-the-math-behind-this-problem/10
/**
 If an optimal product contains a factor f >= 4, then you can replace it with factors 2 and 
 f-2 without losing optimality, as 2*(f-2) = 2f-4 >= f. So you never need a factor greater 
 than or equal to 4, meaning you only need factors 1, 2 and 3 (and 1 is of course wasteful 
 and you'd only use it for n=2 and n=3, where it's needed).
 For the rest I agree, 3*3 is simply better than 2*2*2, so you'd never use 2 more than twice.
*/
// Refer to
// https://discuss.leetcode.com/topic/45341/a-simple-explanation-of-the-math-part-and-a-o-n-solution
/**
 The first thing we should consider is : What is the max product if we break a number N into two factors?
  I use a function to express this product: f=x(N-x)
  When x=N/2, we get the maximum of this function.
  However, factors should be integers. Thus the maximum is (N/2) * (N/2) when N is even or (N-1)/2 * (N+1)/2 when N is odd.
  When the maximum of f is larger than N, we should do the break.
  (N/2) * (N/2) >= N, then N >= 4
  (N-1)/2 * (N+1)/2 >= N, then N >= 5
  
  These two expressions mean that factors should be less than 4, otherwise we can do the break and get a 
  better product. The factors in last result should be 1, 2 or 3. Obviously, 1 should be abandoned. 
  Thus, the factors of the perfect product should be 2 or 3.

  The reason why we should use 3 as many as possible is
  For 6, 3 * 3 > 2 * 2 * 2. Thus, the optimal product should contain no more than three 2.
  Below is my accepted, O(N) solution.
*/

// Refer to
// www.cnblogs.com/grandyang/p/5411919.html
/**
   这道题给了我们一个正整数n，让我们拆分成至少两个正整数之和，使其乘积最大，题目提示中让我们用O(n)来解题，
   而且告诉我们找7到10之间的规律，那么我们一点一点的来分析：

    正整数从1开始，但是1不能拆分成两个正整数之和，所以不能当输出。

    那么2只能拆成1+1，所以乘积也为1。

    数字3可以拆分成2+1或1+1+1，显然第一种拆分方法乘积大为2。

    数字4拆成2+2，乘积最大，为4。

    数字5拆成3+2，乘积最大，为6。

    数字6拆成3+3，乘积最大，为9。

    数字7拆为3+4，乘积最大，为12。

    数字8拆为3+3+2，乘积最大，为18。

    数字9拆为3+3+3，乘积最大，为27。

    数字10拆为3+3+4，乘积最大，为36。

    ....

    那么通过观察上面的规律，我们可以看出从5开始，数字都需要先拆出所有的3，一直拆到剩下一个数为2或者4，
    因为剩4就不用再拆了，拆成两个2和不拆没有意义，而且4不能拆出一个3剩一个1，这样会比拆成2+2的乘积小。
    那么这样我们就可以写代码了，先预处理n为2和3的情况，然后先将结果res初始化为1，然后当n大于4开始循环，
    我们结果自乘3，n自减3，根据之前的分析，当跳出循环时，n只能是2或者4，再乘以res返回即可
*/
class Solution {
    public int integerBreak(int n) {
        if(n == 2) {
            return 1;
        }
        if(n == 3) {
            return 2;
        }
        int product = 1;
        while(n > 4) {
            product *= 3;
            n -= 3;
        }
        // When jump out the loop, the left n will be only
        // be 2 or 4 (directly multiple is fine)
        product *= n;
        return product;
    }
}


