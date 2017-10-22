/**
 * Refer to
 * http://www.lintcode.com/en/problem/coins-in-a-line/
 * There are n coins in a line. Two players take turns to take one or two coins from right side 
   until there are no more coins left. The player who take the last coin wins.
   Could you please decide the first play will win or lose?
   
   Example
    n = 1, return true.
    n = 2, return true.
    n = 3, return false.
    n = 4, return true.
    n = 5, return true.
 * 
 * Solution
 * https://aaronice.gitbooks.io/lintcode/content/dynamic_programming/coins_in_a_line.html
 * https://www.jiuzhang.com/solution/coins-in-a-line/
*/

// Solution 1:
/**
   Analysis
   Dynamic Programming

   这一个问题可以归类到博弈类问题，需要注意的是博弈有先后手。
   State:
   定义一个人的状态: dp[i], 现在还剩i个硬币，现在当前取硬币的人最后输赢状况
   Function:
   考虑两个人的状态做状态更新: dp[i] = (!dp[i-1]) || (!dp[i-2])
   Intialize:
   dp[0] = false
   dp[1] = true
   dp[2] = true
   Answer:
   dp[n]
   先思考最小状态 然后思考大的状态 -> 往小的递推，那么非常适合记忆化搜索 
   
   Algorithm with O(1) Time, O(1) Space
   其实此问题如果从另一个角度思考，就是从最后剩余1个或2个硬币时进行倒推，寻找规律：
   先手输：
   o o o | o o o
   先手胜：
   o | o o o
   制胜的方法就是一定在倒数第二个回合时，让对手面对3个硬币，这样因为自己可以拿1或者2个硬币，那么无论对手
   选1个或者2个，己方都可以拿到最后一个硬币。这个规律就是每次让对手都面对3的倍数个硬币，那么无论对方取1个
   或者2个，只需要取相应的硬币数，让剩下的硬币数目保持3X，这样就能够保证取胜。对于先手而言，如果自己第一轮
   面对的就是3的倍数个硬币，那么对手则可以使用同样的策略让自己一方每次面对3X个硬币。于是先手是否获胜的
   唯一要素就是初始硬币数目，在不为3的整数倍情况下，先手都可以获胜。这样的话，算法时间复杂度和空间复杂度
   都为O(1)。
*/


