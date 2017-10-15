/**
 * Refer to
 * https://leetcode.com/problems/dungeon-game/description/
 * The demons had captured the princess (P) and imprisoned her in the bottom-right corner of a dungeon. 
   The dungeon consists of M x N rooms laid out in a 2D grid. Our valiant knight (K) was initially 
   positioned in the top-left room and must fight his way through the dungeon to rescue the princess.
   
   The knight has an initial health point represented by a positive integer. If at any point his health 
   point drops to 0 or below, he dies immediately.
   Some of the rooms are guarded by demons, so the knight loses health (negative integers) upon entering 
   these rooms; other rooms are either empty (0's) or contain magic orbs that increase the knight's 
   health (positive integers).
   
   In order to reach the princess as quickly as possible, the knight decides to move only rightward 
   or downward in each step.
   
   Write a function to determine the knight's minimum initial health so that he is able to rescue the princess.
   For example, given the dungeon below, the initial health of the knight must be at least 7 if he follows 
   the optimal path RIGHT-> RIGHT -> DOWN -> DOWN.
   -2 (K)    -3         3
   -5        -10        1
   10         30       -5 (P)
   
   Notes:
   The knight's health has no upper bound. Any room can contain threats or power-ups, even the first room 
   the knight enters and the bottom-right room where the princess is imprisoned.
 *
 * Solution
 * https://segmentfault.com/a/1190000003884349 
 * 动态规划
   复杂度
   时间 O(MN) 空间 O(N) 递归栈
   思路
   骑士向右或者向下走，如果血量小于0就死掉了，这会使得计算变得很复杂。如果我们从后往前看，从最后一个格子逆推回去，
   就会简单很多。每个格子可以是它下方或者右方的格子逆推回来，那么要让其实的血量最少，我们则要保证逆推的每一步
   都处于活着的状态，且选择活着的状态中，血量较小的那一种。假设health[i][j]表示点i和j的血量，dungeon[i][j]表示
   走到i和j要扣除的血量。如果从下方逆推回上面，则血量为health[i][j] = health[i + 1][j] - dungeon[i][j]，
   但要考虑，如果该格子如果扣血扣太多的，则这样相减血量会成为负数，说明骑士就已经死了，这样的话我们要保证扣完血
   后骑士还活着，则该点的血量就应该为1。所以其实是health[i][j] = Math.max(health[i + 1][j] - dungeon[i][j], 1)。
   同理，如果从右边逆推回来，则health[i][j] = Math.max(health[i][j] - dungeon[i][j + 1], 1)。最后，
   我们在这两个逆推的值中，取较小的那个就行了。

    注意
    由于最下面一行和最右面一列比较特殊，只有一种逆推方法，所以我们要先单独处理一下。
    最右下角那个节点没有待逆推的节点，所以我们假设其逆推节点的血量为1。
 *
*/
class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        if(dungeon == null || dungeon.length == 0 || dungeon[0].length == 0) {
            return 0;
        }
        // state
        int m = dungeon.length;
        int n = dungeon[0].length;
        // dp[i][j] represent knight's health at position (i,j)
        // dungeon[i][j] represent decreased health at position (i,j)
        int[][] dp = new int[m][n];
        // intialize
        // princess position
        dp[m - 1][n - 1] = Math.max(1, 1 - dungeon[m - 1][n - 1]);
        // last row
        for(int i = n - 2; i >= 0; i--) {
            dp[m - 1][i] = Math.max(dp[m - 1][i + 1] - dungeon[m - 1][i], 1);
        }
        // last column
        for(int i = m - 2; i >= 0; i--) {
            dp[i][n - 1] = Math.max(dp[i + 1][n - 1] - dungeon[i][n - 1], 1);
        }
        // function
        // for every position reversely deduct from bottom-up and right-left
        for(int i = m - 2; i >= 0; i--) {
            for(int j = n - 2; j >= 0; j--) {
                // if both bottom-up and right-left case exist, try to find
                // minimum one between them as it require less health
                dp[i][j] = Math.max(Math.min(dp[i + 1][j] - dungeon[i][j], dp[i][j + 1] - dungeon[i][j]), 1);
            }
        }
        // answer
        return dp[0][0];
    }
}
