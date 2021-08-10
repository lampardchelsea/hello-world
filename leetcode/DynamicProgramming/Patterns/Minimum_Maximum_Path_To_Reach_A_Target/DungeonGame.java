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
*/

// Solution 1: Native DFS (TLE)
// Refer to
// https://leetcode.com/problems/dungeon-game/discuss/745340/post-Dedicated-to-beginners-of-DP-or-have-no-clue-how-to-start
/**
before starting with DP one must master the art of recursion
most of the post shows you the optimal solution, briefly explains how it works, but doesn't tell you how to arive at that solution. 
it goes as "give man a fish, he can eat it whole day, tell then how to catch one, he can have it whole life".
here is how you should approach these recurrsive/Dp problems. hopefully you will learn something.

Step 1: Understand the problem
here we have been given a matrix we need to start from top right and find a way to get to bottom right, we need the min cost that is required to do this

Step 2: Logic building and deriving recurence relation

1st sub step : asking questions

At any point if our health gets zero of below we dies, athem so : we need 1 + (-mat[i][j]) for our health to be one.
What if we get some health if we arrive at some cell ? my guess is we still need 1 health in first case to arrive at that cell - 
cases like these need to be figure out by yourself.
at any cell what health do we need ? - since we can go only down and right therefore min health required will be minimun health 
required if we go right or down, ( futher explained in arriving at recurrance relation heading )
for brief answers/explanation for above point 1 and 2 , assume a 1D matrix this is what is ment by 1st and 2nd point.

[[-10]]                     : ans  = 1 + (-(-10)) = 11 (explanation to first point mentioned)
[[10]]                      : ans  = 1 as we still need 1 health at first place to get there (explanation to second point mentioned)
[[-2,-3,3,-5,-10]]          : ans = 1 + (-(-17)) = 18 same as 1st case
[[2,3,3,5,10]]              : ans = 1 same as 2nd test case, explanation to second point mentioned to asking question

2nd sub step : Ariving at recurence relation
recurrence relation is pretty straight forward at any cell ,if we are at any particular cell we must ask should we go right or down ? 
if we know the answer for min health req if we go right vs we go down, then we can easily choose

Step 3: code recursive solution - (TLE)
int getVal(vector<vector<int>> &mat, int i=0, int j=0)
    {
        int n = mat.size();
        int m = mat[0].size();
        // Base case : we have crossed the matrix, ie. out of bound
        /// if current row crosses then my row is below the princess or 
        /// if current column crosses then my column is ahead the column of princess
        /// and beacause we can go only down and right so we wont be able reach princess
        if(i == n || j == m)    return 1e9+5; 
        
		// Base Case : we have reached our destination ie. last cell
        /// we reached princess , cheers return this cost;
        if(i == n-1 and j == m-1)   
            return (mat[i][j] <= 0) ? -mat[i][j] + 1 : 1;
        
        /// now we must try all possible paths , we ask our right and and down cell
        int IfWeGoRight = getVal(mat , i , j+1);
        int IfWeGoDown = getVal(mat , i+1 , j);
        
        /// min of either values and then cost of this cell
        int minHealthRequired =  min(IfWeGoRight , IfWeGoDown) -mat[i][j];
        
        /// point 2 as explained 
        return ( minHealthRequired <= 0 ) ? 1 : minHealthRequired;      
    }
    
    int calculateMinimumHP(vector<vector<int>>& dungeon) {
        return getVal(dungeon);     
    }
*/
class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        return helper(dungeon, 0, 0);
    }
    
    private int helper(int[][] dungeon, int x, int y) {
        // Base case 1: Invalid
        if(x >= dungeon.length || y >= dungeon[0].length) {
            return Integer.MAX_VALUE;
        }
        // Base case 2: Since only go downward and rightward, 
        // no need check upward, leftward
        // At any point if our health gets zero of below we dies, 
        // athem so : we need 1 + (-mat[i][j]) for our health to be one.
        if(x == dungeon.length - 1 && y == dungeon[0].length - 1) {
            return dungeon[x][y] <= 0 ? 1 + (-dungeon[x][y]) : 1;
        }
        int rightward = helper(dungeon, x + 1, y);
        int downward = helper(dungeon, x, y + 1);
        // Minus 'dungeon[x][y]' means consider current cell value
        // as cost from whole health you should have
        // e.g
        // -2  -3  3
        // -5 -10  1
        // 10  30 -5
        // now we stand at right bottom cell as -5, which means in DFS
        // we are at base case 2 and return should be -5 <= 0 --> 
        // -(-5) + 1 = 6, which means 'Math.min(rightward, downward)' = 6, 
        // now we return to one upper level in DFS, and in downward option, 
        // how to handle cell as 1 to calculate the minimum health you 
        // initially should obtain? add it or minus it? If go over cell 1
        // actually means you obtain 1 more health, reversely means you 
        // can have 1 less health initially, the target is to calculate how
        // much health we initially should have, so actually relation for
        // cell 1 is minus it to reflect you can have 1 less health initially
        // so, if go over cell 1 in reversely 2nd level in DFS, the return
        // will be 'int min = Math.min(rightward, downward) - dungeon[x][y]' 
        // = 6 - 1 = 5 --> so min(5) <= 0 ? 1 : min(5) = 5
        int min = Math.min(rightward, downward) - dungeon[x][y];
        // Why when min <= 0, we still need 1 health return ?
        // What if we get some health if we arrive at some cell ? 
        // my guess is we still need 1 health in first case to arrive at that cell
        return min <= 0 ? 1 : min;
    }
}

// Solution 2: Top Down DP Memoization (2D-DP)
// Refer to
// https://leetcode.com/problems/dungeon-game/discuss/745340/post-Dedicated-to-beginners-of-DP-or-have-no-clue-how-to-start
/**
Now you know how to solve this recurssively, lets now observe time complexity ... ... yep its exponential ! . 
but luckily we are doing same task over and over again. ( for example we are asking 6 times the last cell its 
cost in 3X3 matrix), we can overcome this task by storing the values of cost at each cell ( aka memoization ).

Step 4: memoization-top-down
lets maintain a DP matrix which will store the calculated values for its cell. next time if we arrive at this cell we 
will return this calculated value to save recurrsive calls.

int getVal(vector<vector<int>> &mat, vector<vector<int>> &dp , int i=0, int j=0)
    {
        int n = mat.size();
        int m = mat[0].size();    
        
        if(i == n || j == m)    return 1e9+5; 
        
        if(i == n-1 and j == m-1)
            return (mat[i][j] <= 0) ? -mat[i][j] + 1 : 1;
        
        /// if we know the answer for this cell then no need to recalculate those, simply return those values 
        if( dp[i][j] != 1e9+5 )
            return dp[i][j];
        
        int IfWeGoRight = getVal(mat , dp , i , j+1);
        int IfWeGoDown = getVal(mat , dp , i+1 , j);
        
        int minHealthRequired =  min(IfWeGoRight , IfWeGoDown) -mat[i][j];
        
        /// before returning the values, we must store the answers for this cell which we hacve calculated
        /// in next recurssive call this value will be used to save some computation, aka repetative work which we are doing.
        dp[i][j] = ( minHealthRequired <= 0 ) ? 1 : minHealthRequired;      
        return dp[i][j];
    }
    
    int calculateMinimumHP(vector<vector<int>>& dungeon) {
        
        int n = dungeon.size();
        int m = dungeon[0].size();
        
        vector<vector<int>> dp(n , vector<int>(m , 1e9+5));
        
        return getVal(dungeon, dp);     
    }
*/
class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        int rows = dungeon.length;
        int cols = dungeon[0].length;
        Integer[][] memo = new Integer[rows][cols];
        return helper(dungeon, 0, 0, memo);
    }
    
    private int helper(int[][] dungeon, int x, int y, Integer[][] memo) {
        if(x >= dungeon.length || y >= dungeon[0].length) {
            return Integer.MAX_VALUE;
        }
        if(x == dungeon.length - 1 && y == dungeon[0].length - 1) {
            return dungeon[x][y] <= 0 ? 1 + (-dungeon[x][y]) : 1;
        }
        if(memo[x][y] != null) {
            return memo[x][y];
        }
        int rightward = helper(dungeon, x + 1, y, memo);
        int downward = helper(dungeon, x, y + 1, memo);
        int min = Math.min(rightward, downward) - dungeon[x][y];
        memo[x][y] = (min <= 0) ? 1 : min;
        return memo[x][y];
    }
}

// Solution 3: Bottom Up DP (2D-DP)
// Refer to
// https://leetcode.com/problems/dungeon-game/discuss/745340/post-Dedicated-to-beginners-of-DP-or-have-no-clue-how-to-start
/**
so now you know the recursive solution , you have also tried memoization, so try coming up with bottomUp solution yourself, 
HINT : as you might have observed final destination is the last cell, so why dont we start with the bottom cell itself. 
then work all the way up to first cell.

Step 5: You know the base casses , you know the sub problems so try coming up with bottom up solution yourself ( hint in above paragraph )

int calculateMinimumHP(vector<vector<int> > &dungeon) {

        int n = dungeon.size();
        int m = dungeon[0].size();

        vector<vector<int> > dp(n + 1, vector<int>(m + 1, 1e9+5));
        dp[n][m - 1] = 1;
        dp[n - 1][m] = 1;
        
        for (int i = n - 1; i >= 0; i--) 
        {
            for (int j = m - 1; j >= 0; j--) 
            {
                int need = min(dp[i + 1][j], dp[i][j + 1]) - dungeon[i][j];                
                // store this value
                dp[i][j] = need <= 0 ? 1 : need;
            }
        }
        return dp[0][0];
    }
*/

// Refer to
// https://segmentfault.com/a/1190000003884349 
/**
   动态规划
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
*/
