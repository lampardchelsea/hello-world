https://leetcode.com/problems/minimize-the-difference-between-target-and-chosen-elements/description/
You are given an m x n integer matrix mat and an integer target.
Choose one integer from each row in the matrix such that the absolute difference between target and the sum of the chosen elements is minimized.
Return the minimum absolute difference.
The absolute difference between two numbers a and b is the absolute value of a - b.
 
Example 1:

Input: mat = [[1,2,3],[4,5,6],[7,8,9]], target = 13
Output: 0
Explanation: One possible choice is to:- Choose 1 from the first row.- Choose 5 from the second row.- Choose 7 from the third row.The sum of the chosen elements is 13, which equals the target, so the absolute difference is 0.

Example 2:

Input: mat = [[1],[2],[3]], target = 100
Output: 94
Explanation: The best possible choice is to:- Choose 1 from the first row.- Choose 2 from the second row.- Choose 3 from the third row.The sum of the chosen elements is 6, and the absolute difference is 94.

Example 3:

Input: mat = [[1,2,9,8,7]], target = 6
Output: 1
Explanation: The best choice is to choose 7 from the first row.The absolute difference is 1.
 
Constraints:
- m == mat.length
- n == mat[i].length
- 1 <= m, n <= 70
- 1 <= mat[i][j] <= 70
- 1 <= target <= 800
--------------------------------------------------------------------------------
Attempt 1: 2026-03-15
Solution 1: Backtracking
Style 1: Implicit Backtracking (30 min, TLE 14/81)
class Solution {
    int min = Integer.MAX_VALUE;
    public int minimizeTheDifference(int[][] mat, int target) {
        for(int i = 0; i < mat[0].length; i++) {
            helper(mat, target, 0, i, 0);
        }
        return min;
    }

    private void helper(int[][] mat, int target, int row, int col, int curSum) {
        if(row == mat.length) {
            min = Math.min(min, Math.abs(curSum - target));
            return;
        }
        for(int j = 0; j < mat[0].length; j++) {
            helper(mat, target, row + 1, j, curSum + mat[row][col]);
        }
    }
}

Time Complexity: O(n^m), m for rows, n for cols
Space Complexity: O(m), m for rows

Style 2: Implicit Backtracking without for loop in main method (30 min, TLE 14/81)
class Solution {
    int min = Integer.MAX_VALUE;
    public int minimizeTheDifference(int[][] mat, int target) {
        helper(mat, target, 0, 0);
        return min;
    }

    private void helper(int[][] mat, int target, int row, int curSum) {
        if(row == mat.length) {
            min = Math.min(min, Math.abs(curSum - target));
            return;
        }
        for(int j = 0; j < mat[0].length; j++) {
            helper(mat, target, row + 1, curSum + mat[row][j]);
        }
    }
}

Time Complexity: O(n^m), m for rows, n for cols
Space Complexity: O(m), m for rows

Style 3: Explicit Backtracking with object type as length = 1 array (30 min, TLE)
class Solution {
    int min = Integer.MAX_VALUE;
    public int minimizeTheDifference(int[][] mat, int target) {
        helper(mat, target, 0, new int[]{0});
        return min;
    }

    private void helper(int[][] mat, int target, int row, int[] curSum) {
        if(row == mat.length) {
            min = Math.min(min, Math.abs(curSum[0] - target));
            return;
        }
        for(int j = 0; j < mat[0].length; j++) {
            curSum[0] += mat[row][j];
            helper(mat, target, row + 1, curSum);
            curSum[0] -= mat[row][j];
        }
    }
}

Time Complexity: O(n^m), m for rows, n for cols
Space Complexity: O(m), m for rows

Style 4: Explicit Backtracking with global variable and no value pass in recursion method (30 min, TLE)
如果不使用值传递，而使用一个全局的 int 变量（例如 currentSum），在递归前加上 mat[row][j]，递归后减去。这就是显式回溯。
class Solution {
    int min = Integer.MAX_VALUE;
    int curSum = 0;
    public int minimizeTheDifference(int[][] mat, int target) {
        helper(mat, target, 0);
        return min;
    }

    private void helper(int[][] mat, int target, int row) {
        if(row == mat.length) {
            min = Math.min(min, Math.abs(curSum - target));
            return;
        }
        for(int j = 0; j < mat[0].length; j++) {
            curSum += mat[row][j];
            helper(mat, target, row + 1);
            curSum -= mat[row][j];
        }
    }
}

Time Complexity: O(n^m), m for rows, n for cols
Space Complexity: O(m), m for rows

Solution 2: Native DFS (30 min, TLE)
class Solution {
    public int minimizeTheDifference(int[][] mat, int target) {
        return helper(mat, target, 0, 0);
    }

    private int helper(int[][] mat, int target, int row, int curSum) {
        if(row == mat.length) {
            return Math.abs(curSum - target);
        }
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < mat[0].length; i++) {
            min = Math.min(min, helper(mat, target, row + 1, curSum + mat[row][i]));
        }
        return min;
    }
}

Time Complexity: O(n^m), m for rows, n for cols
Space Complexity: O(m), m for rows

Solution 3: DFS + Memoization (30 min)
class Solution {
    public int minimizeTheDifference(int[][] mat, int target) {
        int max = 0;
        for(int i = 0; i < mat.length; i++) {
            int rowMax = 0;
            for(int j = 0; j < mat[0].length; j++) {
                rowMax = Math.max(rowMax, mat[i][j]);
            }
            max += rowMax;
        }
        Integer[][] memo = new Integer[mat.length + 1][max + 1]; 
        return helper(mat, target, 0, 0, memo);
    }

    private int helper(int[][] mat, int target, int row, int curSum, Integer[][] memo) {
        if(row == mat.length) {
            return Math.abs(curSum - target);
        }
        if(memo[row][curSum] != null) {
            return memo[row][curSum];
        }
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < mat[0].length; i++) {
            min = Math.min(min, helper(mat, target, row + 1, curSum + mat[row][i], memo));
        }
        return memo[row][curSum] = min;
    }
}

时间复杂度：O(m × n × maxSum)
- `m` = 矩阵行数，`n` = 矩阵列数，`maxSum` = 每行最大值之和（即所有可能路径和的上限）。  
- 状态总数约为 `m × (maxSum + 1)`，每个状态需要遍历 `n` 个选择。  
- 每个状态仅计算一次，因此总计算量为 `m × (maxSum + 1) × n`。  
- 在题目给定约束（`m, n ≤ 70`，元素值 ≤ 70）下，`maxSum ≤ 70×70 = 4900`，因此时间约为 `70 × 4900 × 70 ≈ 24,010,000`，可接受。

空间复杂度：O(m × maxSum)
- 记忆化数组 `memo` 大小为 `(m+1) × (maxSum+1)`，用于存储已计算的子问题结果。  
- 递归调用栈深度为 `m`，忽略不计。  
- 总空间主要来自 `memo` 数组，即 `O(m × maxSum)`。

### 说明
- 此解法是自顶向下的动态规划，利用记忆化避免了重复计算，相比纯回溯（`O(n^m)`）大幅降低了复杂度。  
- 实际计算中，`maxSum` 可通过每行最大值求和得到，也可精确计算所有可能和的上界（如所有元素最大值的和），上述分析采用了上界估计。

Solution 4: 2D DP based on Solution 2: Native DFS (30 min)
Wrong solution debug process refer from Q4
Style 1: Add boundray check
class Solution {
    public int minimizeTheDifference(int[][] mat, int target) {
        int m = mat.length;
        int max = 0;
        for(int i = 0; i < m; i++) {
            int rowMax = 0;
            for(int val : mat[i]) {
                rowMax = Math.max(rowMax, val);
            }
            max += rowMax;
        }
        int[][] dp = new int[m + 1][max + 1];
        // Base case
        for(int curSum = 0; curSum <= max; curSum++) {
            dp[m][curSum] = Math.abs(curSum - target);
        }
        for(int i = m - 1; i >= 0; i--) {
            // 注意：这里遍历完整范围 [0, max]
            for(int curMax = max; curMax >= 0; curMax--) {
                int min = Integer.MAX_VALUE;
                for(int val : mat[i]) {
                    int nextMax = curMax + val;
                    // 就是这 3 行！防止访问 dp[i+1][>max]
                    if(nextMax > max) {
                        continue;
                    }
                    min = Math.min(min, dp[i + 1][nextMax]);
                }
                dp[i][curMax] = min;
            }
        }
        return dp[0][0];
    }
}

Time Complexity: O(n*m), m for rows, n for cols
Space Complexity: O(n*m)

Style 2: Correct preMax and maintain as array
class Solution {
    public int minimizeTheDifference(int[][] mat, int target) {
        int m = mat.length;
        int max = 0;
        int[] prevMax = new int[m + 1];
        for(int i = 0; i < m; i++) {
            int rowMax = 0;
            for(int j = 0; j < mat[0].length; j++) {
                rowMax = Math.max(rowMax, mat[i][j]);
            }
            prevMax[i + 1] = prevMax[i] + rowMax;
            max += rowMax;
        }
        int[][] dp = new int[m + 1][max + 1];
        // Base case
        for(int curSum = 0; curSum <= max; curSum++) {
            dp[m][curSum] = Math.abs(curSum - target);
        }
        for(int i = m - 1; i >= 0; i--) {
            int curPrevMax = prevMax[i]; // 之前所有行的最大和
            // 只需遍历 [0, curPrevMax]，因为这就是当前能达到的 curMax 范围
            for(int curMax = curPrevMax; curMax >= 0; curMax--) {
                int min = Integer.MAX_VALUE;
                for(int val : mat[i]) {
                    int nextMax = curMax + val;
                    min = Math.min(min, dp[i + 1][nextMax]);
                }
                dp[i][curMax] = min;
            }
        }
        return dp[0][0];
    }
}

Time Complexity: O(n*m), m for rows, n for cols
Space Complexity: O(n*m)

本题是同时能够通过 Backtracking 和 Standard DFS to Dynamic Programming 两种路线实现的最佳案例之一，代表了两种完全不同的思路，也体现了两种思路的巨大区别
Refer to Deepseek
Q1：For Leetcode 1981. Minimize the Difference Between Target and Chosen Elements, i attempt with below Native DFS solution but failed:
Input
mat = [[1,2,3],[4,5,6],[7,8,9]]
target = 13
Output = 3
Expected = 0
class Solution {
    public int minimizeTheDifference(int[][] mat, int target) {
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < mat[0].length; i++) {
            min = Math.min(min, Math.abs(helper(mat, 0, i) - target));
        }
        return min;
    }

    private int helper(int[][] mat, int row, int col) {
        if(row == mat.length - 1) {
            return mat[row][col];
        }
        int sum = 0;
        for(int j = 0; j < mat[0].length; j++) {
            sum = mat[row][col] + helper(mat, row + 1, j);
        }
        return sum;
    }
}
Then i realize i should use backtracking tactic, since i have to enumerate all possibilities, i come with below solution but still failed.
Input
mat = [[1,2,3],[4,5,6],[7,8,9]]
target = 13
Output = 4
Expected = 0
class Solution {
    public int minimizeTheDifference(int[][] mat, int target) {
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < mat[0].length; i++) {
            min = Math.min(min, Math.abs(helper(mat, 0, i, new int[]{0}) - target));
        }
        return min;
    }

    private int helper(int[][] mat, int row, int col, int[] curSum) {
        if(row == mat.length - 1) {
            return mat[row][col];
        }
        int sum = 0;
        for(int j = 0; j < mat[0].length; j++) {
            curSum[0] += mat[row][col];
            sum += curSum[0];
            helper(mat, row + 1, j, curSum);
            curSum[0] -= mat[row][col];
        }
        return sum;
    }
}
help me to analysis the issue and fix it, don't worry about input size and performance, i first only need a theoretical working Native DFS solution or Native Backtracking solution
我们分析你的两个DFS尝试。
第一个DFS代码：
- helper 返回从(row, col)开始到最后一行的“和”，但内部循环中 sum 被覆盖为 mat[row][col] + helper(...) 的累加，但这里每个递归调用都会产生一个值，然而你用一个 sum 变量累加所有分支，这显然不对，因为你要的是取某个分支的和，而不是所有分支的和。而且 helper 函数有返回值，你却忽略了递归调用返回的值，只累加了 sum，但 sum 会累加多个分支，这会导致错误。实际上，你需要的是：从当前行选 col 后，后续每行选一个数，得到的和，然后取其中绝对值最接近 target 的。所以应该枚举下一行所有可能，取每个递归返回值 + 当前值，然后找到最接近 target 的。
第二个DFS：
- 试图用回溯，但同样逻辑混乱。curSum[0] 是累计和，但你在循环内把它加上了当前值，然后 sum 累加 curSum[0]（这相当于每个分支都累加同一个累加和，错误）。而且返回 sum，这也不是我们要的。正确做法应该是：在每个递归调用中，记录当前累计和，当到达最后一行时，计算该累计和与 target 的差值，然后全局更新最小值。
所以，简单的朴素DFS应该是：枚举所有可能的路径，计算总和，然后更新最小绝对差。因为矩阵大小可能不大（题目中 m <= 70, n <= 70），但这里我们只求理论正确，不考虑性能。因此可以用回溯遍历所有组合。
思路：
- 使用一个递归函数，参数为当前行 row 和当前累计和 curSum。
- 当 row == m 时，更新答案 minDiff = min(minDiff, abs(curSum - target))。
- 否则，对于当前行的每个元素，递归调用下一行，curSum + mat[row][j]。
- 起始调用：dfs(0, 0)。
但注意：累计和可能会很大，但题目中 mat 元素 <= 10^4，m <= 70，所以总和最大 70*10^4 = 7e5，可以用整数。
下面给出修正的朴素DFS/回溯代码：
class Solution {
    private int minDiff = Integer.MAX_VALUE;
    private int target;
    private int[][] mat;
    private int m;
    private int n;

    public int minimizeTheDifference(int[][] mat, int target) {
        this.mat = mat;
        this.target = target;
        this.m = mat.length;
        this.n = mat[0].length;
        dfs(0, 0);
        return minDiff;
    }

    private void dfs(int row, int curSum) {
        if (row == m) {
            minDiff = Math.min(minDiff, Math.abs(curSum - target));
            return;
        }
        for (int j = 0; j < n; j++) {
            dfs(row + 1, curSum + mat[row][j]);
        }
    }
}
这个代码会枚举所有 m^n 种组合，对于小矩阵可以运行，但题目实际数据可能很大，但你说不需要考虑性能，只求理论正确。这样就能得到期望结果 0。
注意：上面的代码对于 mat = [[1,2,3],[4,5,6],[7,8,9]] 和 target=13，会找到路径 1+4+7=12（差1），1+4+8=13（差0），所以 minDiff 为 0。
因此你的错误在于没有正确枚举所有组合并计算每个组合的和与 target 的差值，而是错误地累加或返回了错误的值。
你的两个尝试都存在根本性错误：第一个版本错误地累加所有分支的和，第二个版本混淆了回溯的累计和与分支返回值。正确的方法是使用深度优先搜索枚举所有可能的路径，计算每条路径的总和，并更新与目标的最小绝对差值。
以下是修正后的朴素DFS/回溯代码（理论正确，不考虑性能）：
class Solution {
    private int minDiff = Integer.MAX_VALUE;
    private int target;
    private int[][] mat;
    private int m, n;

    public int minimizeTheDifference(int[][] mat, int target) {
        this.mat = mat;
        this.target = target;
        this.m = mat.length;
        this.n = mat[0].length;
        dfs(0, 0);
        return minDiff;
    }

    private void dfs(int row, int curSum) {
        if (row == m) {
            minDiff = Math.min(minDiff, Math.abs(curSum - target));
            return;
        }
        for (int j = 0; j < n; j++) {
            dfs(row + 1, curSum + mat[row][j]);
        }
    }
}
解释
- dfs(row, curSum) 表示当前已处理到第 row 行，累计和为 curSum。
- 当处理完最后一行（row == m）时，计算 |curSum - target| 并更新全局最小值。
- 否则，遍历当前行的所有列，递归调用下一行，并将当前元素加到累计和中。
测试
输入 mat = [[1,2,3],[4,5,6],[7,8,9]], target = 13，会枚举所有 3^3 = 27 种组合，找到 1+4+8=13，差值为 0，输出 0。
这个版本虽然简单，但枚举所有组合，在题目实际数据规模下会超时，仅用于理论验证。若需通过 LeetCode，还需用动态规划或剪枝优化。

Q2：I have below implicit backtracking solution, but error out:
class Solution {
    int min = Integer.MAX_VALUE;
    public int minimizeTheDifference(int[][] mat, int target) {
        for(int i = 0; i < mat[0].length; i++) {
            helper(mat, target, 0, i, 0);
        }
        return min;
    }

    private void helper(int[][] mat, int target, int row, int col, int curSum) {
        if(row == mat.length - 1) {
            min = Math.min(min, Math.abs(curSum - target));
            return;
        }
        for(int j = 0; j < mat[0].length; j++) {
            helper(mat, target, row + 1, j, curSum + mat[row][col]);
        }
    }
}
我们被要求分析给出的隐式回溯代码为什么输出错误。用户给出了代码和错误输出（预期0，实际4）。代码中递归终止条件有误，以及curSum累积方式不正确。需要分析并指出问题，并修正。
输入mat=[[1,2,3],[4,5,6],[7,8,9]], target=13。所有路径总和枚举后，有1+4+8=13等，但代码输出4，说明没有正确计算总和。
错误原因：
1.终止条件 if(row == mat.length - 1) 意味着当 row 等于最后一行索引时，此时还没有加上最后一行的值，就计算了 curSum 的差值，但 curSum 只包含了前几行的和，漏掉了最后一行的值。
2.在递归调用中，curSum + mat[row][col] 传递的是当前行的值，但 row 在调用时是 row+1，所以传入的 curSum 在下一层递归中会加上下一行的值，但最后一行没有被加上。
实际上正确做法应该是：当 row == mat.length 时（即已选择完所有行），再计算差值。且递归调用时应将当前行的值加入到curSum中。
修正：
- 递归基：if(row == mat.length)
- 递归调用：helper(mat, target, row + 1, j, curSum + mat[row][j]) （注意：当前行的索引是row，不是固定的col？原代码中col参数似乎无意义，应去掉col参数，或者用循环中的j代替。当前设计col参数是多余的，因为helper中row+1的col是下一行的列，但当前行curSum加的是mat[row][col]（col是外层循环的i），这会导致只考虑第一行固定的列，而后续行会遍历所有列，但第一行固定。实际上，更简单的写法是不传col参数，在递归中直接使用循环变量j作为当前行的选择，递归时传递curSum + mat[row][j]。原代码中的col参数未使用，且递归时传入的curSum中加的是固定col，这是严重错误。
应该重新设计：helper方法参数只需要(row, curSum)，在方法内部遍历当前行的列，递归调用下一行并加上当前元素。这样枚举所有组合。
同时，第一层循环也应去掉，直接调用helper(0,0)。因为helper内部会遍历当前行的所有列。
现在我们来分析输出4的原因：由于第一行只取了i=0（即1），后续行取所有组合，但curSum只加第一行的1，然后递归到第二行，在第二行调用时，curSum加的是mat[0][0]即1，第二行的递归中，当row==2（最后一行索引），此时curSum是前两行的和（1+某值），计算差值时没加第三行的值。所以实际得到的是前两行和与target的差值。例如1+4=5，差8；1+5=6，差7；1+6=7，差6；然后这些最小值是6？但最终输出4，可能是其他组合。不管怎样，错误明显。
我们将提供修正后的隐式回溯代码，并解释修改点。
回答时，我们需要清晰指出问题并给出修正版本。
你的隐式回溯代码存在两个关键错误，导致最终结果不正确：
1.递归终止条件错误
if(row == mat.length - 1) 表示只在处理完最后一行之前就计算差值，漏掉了最后一行的值。应该改为 if(row == mat.length)，即所有行都已选择完毕。
2.累加逻辑错误
在递归调用时，当前行的值应当来自当前行参数 row，而不是固定使用外层循环传入的 col。原代码中 col 参数是多余的，且递归时始终加的是第一行的固定列（外层循环的 i），导致枚举不完整。
修正后的隐式回溯代码
class Solution {
    int min = Integer.MAX_VALUE;

    public int minimizeTheDifference(int[][] mat, int target) {
        dfs(mat, target, 0, 0);  // 从第0行开始，当前和为0
        return min;
    }

    private void dfs(int[][] mat, int target, int row, int curSum) {
        if (row == mat.length) {               // 已选择完所有行
            min = Math.min(min, Math.abs(curSum - target));
            return;
        }
        for (int j = 0; j < mat[0].length; j++) {
            dfs(mat, target, row + 1, curSum + mat[row][j]); // 选择当前行的第j列
        }
    }
}
修改说明
- 终止条件：当 row == mat.length 时，表示已经处理完所有行，此时 curSum 是完整的路径和，可以计算与 target 的差值。
- 累加方式：在递归调用中，将当前行的值 mat[row][j] 加到 curSum 上，并传递给下一行。
- 移除多余的参数：原 col 参数已不需要，因为每一行的选择在循环内完成。
测试
对于输入 mat = [[1,2,3],[4,5,6],[7,8,9]]，target = 13，该代码会枚举所有 27 条路径，找到 1+4+8=13，差值为 0，输出 0，符合预期。
或者是下面这种写法
class Solution {
    int min = Integer.MAX_VALUE;
    public int minimizeTheDifference(int[][] mat, int target) {
        for(int i = 0; i < mat[0].length; i++) {
            helper(mat, target, 0, i, 0);
        }
        return min;
    }

    private void helper(int[][] mat, int target, int row, int col, int curSum) {
        if(row == mat.length) {
            min = Math.min(min, Math.abs(curSum - target));
            return;
        }
        for(int j = 0; j < mat[0].length; j++) {
            helper(mat, target, row + 1, j, curSum + mat[row][col]);
        }
    }
}
这两个代码在逻辑上等价，都能正确枚举所有路径并得到最小差值。之前的错误原因：
终止条件误写：检查实际代码中 if(row == mat.length) 是否写成了 if(row == mat.length - 1)？若漏掉最后一行，就会得到错误结果。
这段代码的时间复杂度是 O(n^m)，空间复杂度是 O(m)，其中 m 是行数，n 是列数。
详细分析
- 递归树：每层选择 n 种可能，共 m 层，总叶子节点数为 n^m。
- 每个叶子节点执行常数时间（计算绝对值并更新最小值），内部节点也有常数操作（循环调用）。
- 因此总操作数与叶子节点数成正比，时间复杂度为 O(n^m)。
- 递归栈深度为 m，每次递归调用占用常数栈空间，空间复杂度为 O(m)。
当 m 和 n 较大时（如本题限制 m ≤ 70，n ≤ 70），n^m 会爆炸式增长，无法通过实际测试，故需用动态规划优化。但作为理论回溯，上述分析正确。

Q3：Looks like its an implicit backtracking, since the parameter in recursion method is primitive 'int' and no need explicit backtracking ? And also the backtracking tactic always working with void return recursion method and a global variable style in case we want to enumerate all possibilities and find a min or max one out of them ?
我们被问到：看起来这是一种隐式回溯，因为递归方法的参数是基本类型'int'，不需要显式回溯？而且回溯策略通常使用void返回递归方法和全局变量风格，以便枚举所有可能性并从中找到最小值或最大值？
用户观察到了两个关键点：
1.递归参数是基本类型（int），所以不需要显式回溯（即无需在递归后恢复状态），因为每次调用传入的是值副本，不会影响其他分支。
2.回溯策略通常使用void返回递归方法和全局变量风格来枚举所有可能性并找出最值。
用户似乎在确认理解，可能想要更深层的解释。我们需要回应确认并进一步阐述。
问题来自前面的对话：用户之前尝试用DFS/回溯解决Leetcode 1981，但代码有问题。我们给出了正确的DFS代码，其中使用了void返回和全局minDiff变量，递归参数curSum是基本类型。用户注意到这是隐式回溯，因为curSum是值传递，无需显式恢复。用户还指出回溯策略常用void返回和全局变量来枚举所有可能并求最值。
我们应该肯定用户的观察，并补充说明：在需要枚举所有组合并寻找最优值的回溯问题中，确实常用void递归和全局变量记录最优解；而使用返回值（如返回所有组合的最大/最小值）也是常见模式，但有时需要处理状态恢复。同时解释为什么这里不需要显式回溯，因为状态通过值传递，不需要在递归返回后撤销修改。另外，可以讨论如果状态是可变对象（如数组、列表）时就需要显式回溯（如添加/删除元素）。
我们给出清晰、简洁的回应。
你的理解完全正确。
1. 为什么这里是隐式回溯？
递归方法 dfs(row, curSum) 中的参数 curSum 是基本类型 int，在 Java 中是按值传递。每次递归调用传入的是当前值的副本，修改这个副本不会影响外层调用中的 curSum 变量。因此，不需要在递归返回后显式地“撤销”对 curSum 的修改。这种“值传递”天然实现了回溯效果。
如果参数是可变对象（如 List<Integer>、数组），则需要在递归返回前还原状态，即显式回溯。
2. 为什么常用 void 返回 + 全局变量？
当我们需要枚举所有可能性并从中找出最优解时，使用 void 递归配合全局变量（如 minDiff）是一种简洁的模式：
- 递归函数负责遍历所有组合，在到达终点时更新全局最优解。
- 不需要通过返回值向上传递信息，因为全局变量已经记录了结果。
- 这种模式适合没有重叠子问题或无法提前剪枝的完全枚举。
另一种模式是让递归返回最优值（如 int dfs(...)），此时需要将子问题的最优值组合起来，适合动态规划或分治。但对于本题的纯粹枚举，void 递归 + 全局变量更直观。
3. 总结
- 你的观察很敏锐：基本类型参数天然避免了状态恢复，属于隐式回溯。
- void 递归 + 全局变量是回溯法枚举所有解并求最值的典型写法。
- 若需要显式回溯（如使用 List 记录当前路径），则需要在递归后手动移除添加的元素。

Q4：Can you explain more on this "另一种模式是让递归返回最优值（如 int dfs(...)），此时需要将子问题的最优值组合起来，适合动态规划或分治。" ?
我们被问到“另一种模式是让递归返回最优值（如 int dfs(...)），此时需要将子问题的最优值组合起来，适合动态规划或分治。” 需要更详细解释这种模式。
用户想了解这种返回最优值的递归模式与之前void+全局变量的区别，以及何时使用。我们将解释：
- 递归函数返回值表示从当前状态出发的最优结果。
- 通过子问题的返回值组合得到当前状态的最优解。
- 这种模式天然支持动态规划（如记忆化），也适用于分治（如归并排序）。
- 举例：最大路径和、最长递增子序列、最小编辑距离等。
- 对比：void+全局变量适合枚举所有解并记录极值，而返回值模式适合子问题结果可复用的场景，通常更高效。
我们将用简洁清晰的语言解释，并给出简单示例（如斐波那契数列的递归返回值 vs 枚举所有路径求最小值）。
当递归函数返回一个值时（例如 int dfs(...)），这种模式通常用于解决具有最优子结构的问题，即当前状态的最优解可以由子问题的最优解组合得到。这种模式非常适合动态规划（DP）和分治法，因为我们可以自顶向下地计算子问题的结果，并通过返回值将它们组合起来。
与 void + 全局变量的区别
- void + 全局变量：递归只负责探索所有可能，到达终点时更新全局最优。它不通过返回值传递信息，适合纯粹的枚举（如排列组合、N皇后、所有路径求和等），尤其是当子问题之间没有重叠或无法提前合并时。
- 返回最优值：递归函数返回从当前状态开始的最佳结果，调用者利用子结果构建当前结果。这种模式更容易应用记忆化（memoization），避免重复计算，通常效率更高。
举例说明
例1：斐波那契数列（分治/DP）
int fib(int n) {
    if (n <= 1) return n;
    return fib(n-1) + fib(n-2);  // 组合子问题的返回值
}
这里不需要全局变量，每个调用返回子问题的解，上一层直接使用。
例2：Leetcode 1981 如果用 DP 优化
原问题如果采用 DP，可以定义 dp[i][sum] 表示前 i 行能否达到和 sum，但若用递归返回值，可以设计函数 dfs(row, curSum) 返回从当前行开始能获得的最小差值，但这样会产生大量重复状态，所以通常会返回一个布尔值或集合并用记忆化。不过更典型的“返回最优值”例子如下：
例3：从矩阵左上角到右下角的最小路径和（Leetcode 64）
int minPathSum(int[][] grid, int i, int j) {
    if (i == m-1 && j == n-1) return grid[i][j];
    int right = (j+1 < n) ? minPathSum(grid, i, j+1) : Integer.MAX_VALUE;
    int down  = (i+1 < m) ? minPathSum(grid, i+1, j) : Integer.MAX_VALUE;
    return grid[i][j] + Math.min(right, down);
}
这里递归返回从 (i,j) 到终点的最小路径和，通过取子问题的最小值组合得到当前结果。如果加上记忆化，就变成了自顶向下的 DP。
例4：最长递增子序列（LIS）长度
int lis(int[] nums, int i, int prev) {
    if (i == nums.length) return 0;
    int take = (prev == -1 || nums[i] > nums[prev]) ? 1 + lis(nums, i+1, i) : 0;
    int skip = lis(nums, i+1, prev);
    return Math.max(take, skip);
}
同样，返回值表示从当前位置开始的最长递增子序列长度，通过比较取与不取子问题的结果得到。
关键点
- 返回值表示当前子问题的最优解，调用者通过组合（如求和、取最大值/最小值）得到上一层的最优解。
- 分治（如归并排序）也遵循这种模式：mergeSort(arr) 返回排序后的数组，然后合并左右结果。
- 动态规划正是利用这种模式，通过缓存子问题的返回值来避免重复计算。
什么时候用哪种模式？
- 如果问题需要枚举所有解并记录某个极值，且子问题之间没有重叠（或重叠很少），可以用 void + 全局变量直接暴力搜索（如回溯法）。
- 如果问题具有重叠子问题，或者能够通过子问题的结果直接推导出当前结果，优先考虑返回最优值的递归 + 记忆化（即 DP）。
在你的 minimizeTheDifference 问题中，虽然可以用 void + 全局变量枚举所有组合（因为 m, n 不大），但实际 LeetCode 上数据规模较大时，必须用 DP（返回布尔集合或可达和）来优化。这时的 DP 实现通常使用二维布尔数组或位集，而不是直接返回最优值，因为直接返回最优值无法处理“所有可能和”的集合。但模式上仍属于“子问题结果组合”的思想。

Q5：Show me detail solution about this:
例2：Leetcode 1981 如果用 DP 优化
原问题如果采用 DP，可以定义 dp[i][sum] 表示前 i 行能否达到和 sum，但若用递归返回值，可以设计函数 dfs(row, curSum) 返回从当前行开始能获得的最小差值，但这样会产生大量重复状态，所以通常会返回一个布尔值或集合并用记忆化
我们被要求展示 Leetcode 1981 的 DP 优化解决方案的细节，特别是基于递归返回值 + 记忆化的方式。题目是 Minimize the Difference Between Target and Chosen Elements，要求从每行选一个数，使得总和与 target 的绝对差最小。
我们可以设计一个递归函数 dfs(row, sum) 返回从第 row 行开始（已经选了前 row 行，当前累计和为 sum）能获得的最小差值。但这样参数 sum 的范围可能很大（最大 70*70 = 4900，实际可能更大），但可以接受。不过如果用递归返回值，我们需要组合子问题的结果：对于当前行，我们选择该行的某个元素 mat[row][col]，然后递归调用 dfs(row+1, sum + mat[row][col])，得到从下一行开始的最小差值。然后当前行的最小差值就是这些子结果的最小值。最后返回最小值。
但这样存在重复子问题：不同路径可能到达相同的 (row, sum)，因此我们可以用记忆化 memo[row][sum] 存储从该状态开始的最小差值（或直接存储布尔可达性）。由于我们只需要知道是否可达某些和，以及最终计算与 target 的差值，更常见的 DP 是使用集合（如布尔数组或位集）记录所有可能的和。但为了演示递归返回值 + 记忆化，我们可以这样做。
下面给出详细的 Java 实现，使用递归 + 记忆化，返回最小差值。注意，由于我们需要的是从当前行开始的最小差值，而差值依赖于 target 和后续的和，实际上我们可以从最后一行反向思考：从当前行开始，选择当前行的元素后，后续行的和可能取很多值，我们需要找到与 target 最接近的。但递归返回值需要知道当前累计和，才能计算最终差值。所以参数包含当前累计和。
实现步骤：
- 使用 memo 二维数组，初始化为 -1 表示未计算。
- 递归函数 int dfs(int row, int curSum)：
- 如果 row == m，返回 Math.abs(curSum - target)（因为已经选完所有行）。
- 如果 memo[row][curSum] != -1，直接返回。
- 否则，遍历当前行的所有元素，计算 int next = dfs(row+1, curSum + mat[row][col])，取最小值。
- 存入 memo 并返回。
但 curSum 范围需要确定。由于每行最大元素 70，最多 70 行，curSum 最大为 70*70 = 4900，所以可以开数组大小 m x (maxSum+1) 或使用 HashMap 节省空间。但为了简单，我们假设 maxSum 已知。
这种递归返回值的方法相当于自顶向下 DP，每个状态 (row, curSum) 只计算一次，时间复杂度 O(m * n * maxSum)，但 maxSum 可能较大。实际 LeetCode 中，m 和 n 最大 70，元素最大 70，所以 maxSum ≤ 4900，可行。
但需要注意的是，curSum 可能超过 maxSum 吗？不会，因为所有元素都是正数，我们只考虑选到最后一行的和。但中间状态可能超过 target 很多，为了减少状态，我们可以对 curSum 进行剪枝，但为了完整，我们保留所有状态。
下面给出完整代码：
class Solution {
    private int m, n, target;
    private int[][] mat;
    private int[][] memo;
    private static final int INF = Integer.MAX_VALUE;

    public int minimizeTheDifference(int[][] mat, int target) {
        this.mat = mat;
        this.target = target;
        this.m = mat.length;
        this.n = mat[0].length;
        // 最大可能和：每行最大元素之和（这里简单用 70*70=4900）
        int maxSum = 0;
        for (int[] row : mat) {
            int maxInRow = 0;
            for (int val : row) {
                maxInRow = Math.max(maxInRow, val);
            }
            maxSum += maxInRow;
        }
        memo = new int[m][maxSum + 1];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        // 初始 curSum = 0
        return dfs(0, 0);
    }

    private int dfs(int row, int curSum) {
        if (row == m) {
            return Math.abs(curSum - target);
        }
        if (memo[row][curSum] != -1) {
            return memo[row][curSum];
        }
        int best = INF;
        for (int col = 0; col < n; col++) {
            int nextSum = curSum + mat[row][col];
            // 如果 nextSum 已经超过最大可能和，可以继续，但为了数组不越界，可以限制
            // 但实际 nextSum 不会超过 maxSum，因为 curSum 已经保证了 <= maxSum
            int res = dfs(row + 1, nextSum);
            best = Math.min(best, res);
            // 可加入剪枝：如果 best == 0，直接返回
            if (best == 0) break;
        }
        memo[row][curSum] = best;
        return best;
    }
}
但这种递归返回值的方法，每个状态 (row, curSum) 都要遍历 n 个分支，且 curSum 可能很多，但 m=70, maxSum=4900, n=70，最坏状态数 70*4900 ≈ 343k，每个状态 O(n)，总操作约 24M，可以接受。
然而，这种方法会计算很多状态，实际上我们可以用更高效的方式：使用布尔数组记录可达和，然后从所有可能和中找最接近 target 的。这是更常见的 DP 优化（自底向上）。但用户要求展示“递归返回值”的 DP 优化，所以上述代码满足要求。
注意：上述代码中 maxSum 计算可能不精确，因为 curSum 可能超过按行最大元素和，但每行实际选的值可能不是最大，所以 curSum 的上限应该是所有元素的最大值之和，即每行最大元素的和。这个上限可以保守估计为 70*70 = 4900，但实际题目元素最大为 70，所以没问题。
如果希望更精确，可以在递归过程中限制 nextSum 不超过 target + 当前已得到的 best，但为了简洁，这里不添加剪枝。
如果使用记忆化，返回值就是最小差值，不需要全局变量。这就是“返回最优值”的递归模式。
在 LeetCode 1981 中，我们可以用递归返回值 + 记忆化的方式实现自顶向下的动态规划。核心思路是定义 dfs(row, curSum) 返回从第 row 行开始（已累计和为 curSum）时，所有可能最终总和与 target 的最小绝对差。通过记忆化避免重复计算，最终返回 dfs(0, 0)。
代码实现
import java.util.Arrays;

class Solution {
    private int m, n, target;
    private int[][] mat;
    private int[][] memo;
    private static final int INF = Integer.MAX_VALUE;

    public int minimizeTheDifference(int[][] mat, int target) {
        this.mat = mat;
        this.target = target;
        this.m = mat.length;
        this.n = mat[0].length;
        
        // 计算可能的最大总和（每行最大值之和）
        int maxSum = 0;
        for (int[] row : mat) {
            int maxInRow = 0;
            for (int val : row) {
                maxInRow = Math.max(maxInRow, val);
            }
            maxSum += maxInRow;
        }
        
        memo = new int[m][maxSum + 1];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        
        return dfs(0, 0);
    }

    private int dfs(int row, int curSum) {
        if (row == m) {
            return Math.abs(curSum - target);
        }
        if (memo[row][curSum] != -1) {
            return memo[row][curSum];
        }
        
        int best = INF;
        for (int col = 0; col < n; col++) {
            int nextSum = curSum + mat[row][col];
            // 可选的剪枝：如果 nextSum 已经超过某个上界，可继续（但数组已分配足够空间）
            int res = dfs(row + 1, nextSum);
            best = Math.min(best, res);
            if (best == 0) break; // 已达最优，提前结束
        }
        
        memo[row][curSum] = best;
        return best;
    }
}
关键点说明
- 递归返回值：dfs(row, curSum) 直接返回从当前状态开始的最小绝对差，调用方通过组合子结果（取最小值）得到自己的结果。
- 记忆化：用 memo[row][curSum] 存储已计算结果，避免重复遍历相同 (row, curSum) 的子问题。这体现了动态规划的自顶向下形式。
- 状态空间：curSum 最大为 maxSum（所有行最大元素之和），在题目约束下（m, n ≤ 70，元素 ≤ 70）状态数约 70×4900 ≈ 343k，每个状态需 O(n) 时间，总操作约 24M，可接受。
- 剪枝：当 best 已经为 0 时可直接返回，因为不可能更优。
这种“返回最优值”的模式与传统的 void + 全局变量回溯不同，它利用子问题的返回值直接构建当前最优解，更适合动态规划。虽然本题实际最优解常用布尔集合 DP（空间更小），但此方法在理论上是正确且可行的。

Q6：Based on given "修正后的朴素DFS/回溯代码", as its implicit backtracking solution, can you write an explicit backtracking solution ?
我们被要求基于之前给出的“修正后的朴素DFS/回溯代码”（即使用void + 全局变量，且参数为基本类型隐式回溯）写一个显式回溯（explicit backtracking）的版本。显式回溯通常意味着在递归函数中手动维护一个状态（比如当前路径和），在进入子递归前修改状态，返回后恢复原状态。参数可能是可变对象，或者使用基本类型但显式进行加减并恢复。
原隐式回溯代码（朴素DFS）：
class Solution {
    private int minDiff = Integer.MAX_VALUE;
    private int target;
    private int[][] mat;
    private int m, n;

    public int minimizeTheDifference(int[][] mat, int target) {
        this.mat = mat;
        this.target = target;
        this.m = mat.length;
        this.n = mat[0].length;
        dfs(0, 0);
        return minDiff;
    }

    private void dfs(int row, int curSum) {
        if (row == m) {
            minDiff = Math.min(minDiff, Math.abs(curSum - target));
            return;
        }
        for (int j = 0; j < n; j++) {
            dfs(row + 1, curSum + mat[row][j]);
        }
    }
}
在这个隐式回溯中，curSum 是基本类型，通过值传递，不需要显式恢复。为了显式回溯，我们可以：
- 将 curSum 作为可变状态（例如一个长度为1的数组或者一个整型字段）并在递归前后显式修改和恢复。
- 或者将 curSum 作为局部变量，但在循环中使用一个临时变量计算下一个和，然后显式恢复？实际上，因为 curSum 是基本类型，如果不使用值传递，而使用一个全局的 int 变量（例如 currentSum），在递归前加上 mat[row][j]，递归后减去。这就是显式回溯。
因此，显式回溯版本可以这样写：
class Solution {
    private int minDiff = Integer.MAX_VALUE;
    private int target;
    private int[][] mat;
    private int m, n;
    private int currentSum; // 全局状态，显式维护

    public int minimizeTheDifference(int[][] mat, int target) {
        this.mat = mat;
        this.target = target;
        this.m = mat.length;
        this.n = mat[0].length;
        this.currentSum = 0;
        dfs(0);
        return minDiff;
    }

    private void dfs(int row) {
        if (row == m) {
            minDiff = Math.min(minDiff, Math.abs(currentSum - target));
            return;
        }
        for (int j = 0; j < n; j++) {
            // 显式地修改当前和
            currentSum += mat[row][j];
            dfs(row + 1);
            // 显式地恢复当前和
            currentSum -= mat[row][j];
        }
    }
}
另一种写法：将 currentSum 作为参数传递，但在循环中显式更新然后恢复？如果参数是基本类型，其实还是隐式。所以显式回溯通常用一个可变的全局变量或传入一个可变对象（如数组）。上面的版本就是典型的显式回溯模式。
我可以在答案中解释两种方式的区别，并给出显式回溯的代码。
在之前给出的隐式回溯代码中，递归参数 curSum 是基本类型，通过值传递自动完成了状态恢复（无需手动还原）。现在我们来显式地维护一个可变的全局状态 currentSum，在进入递归前修改它，在返回后恢复它，这就是显式回溯。
--------------------------------------------------------------------------------
显式回溯版本
class Solution {
    private int minDiff = Integer.MAX_VALUE;
    private int target;
    private int[][] mat;
    private int m, n;
    private int currentSum; // 全局变量，显式维护当前和

    public int minimizeTheDifference(int[][] mat, int target) {
        this.mat = mat;
        this.target = target;
        this.m = mat.length;
        this.n = mat[0].length;
        this.currentSum = 0;
        dfs(0);
        return minDiff;
    }

    private void dfs(int row) {
        if (row == m) {
            minDiff = Math.min(minDiff, Math.abs(currentSum - target));
            return;
        }
        for (int j = 0; j < n; j++) {
            // 选择当前元素，显式修改状态
            currentSum += mat[row][j];
            // 递归到下一行
            dfs(row + 1);
            // 撤销选择，恢复状态（显式回溯）
            currentSum -= mat[row][j];
        }
    }
}
--------------------------------------------------------------------------------
隐式回溯 vs 显式回溯
对比点隐式回溯显式回溯状态存储通过递归参数（基本类型）传递通过全局变量或可变对象维护状态恢复由值传递自动完成，无需手动恢复需要手动在递归调用前后修改并还原代码风格简洁，不易出错稍微冗长，但更灵活（可以支持复杂状态如列表）适用场景状态是基本类型且无需复杂结构时状态是引用类型（如集合、数组），或需要在递归中共享时
两种方式都能正确枚举所有组合并得到最优解。在实际解题中，对于这种全枚举问题，隐式回溯已足够清晰；但当需要维护复杂状态（如路径记录、集合等）时，显式回溯更为常见。

Q7：I want the explicit backtracking solution has similar recursion method pattern as implicit backtracking solution such as "dfs(row, currentSum)" (currentSum should change from primitive type to object type) rather than "dfs(row)"
我们被要求提供一个显式回溯解决方案，该解决方案与隐式回溯解决方案具有类似的递归方法模式“dfs(row, currentSum)”，但这里的 currentSum 应改为对象类型（例如数组），以便可以显式地修改和回溯，而不是通过值传递自动恢复。隐式回溯中的 dfs(row, curSum) 使用基本类型 int，通过值传递，每次调用都是副本，所以不需要显式回溯。显式回溯中，我们希望使用一个可变对象（比如 int[] 数组）来传递当前和，这样在递归调用过程中我们可以修改该对象，然后在递归返回后恢复其值（回溯）。这种方式更接近于显式回溯的思想，同时保持了类似的方法签名。
因此，我们需要编写一个递归函数 dfs(int row, int[] curSum)，其中 curSum 是一个长度为 1 的数组，用于存储当前累积和。在递归调用前，我们修改 curSum[0] 加上当前元素；递归调用后，再减去（回溯）。在到达最后一行时，使用 curSum[0] 计算差值并更新全局最小值。
这样，递归方法签名与隐式版本类似，但 currentSum 变成了对象类型，从而需要显式回溯。
我们应提供完整的 Java 代码。
注意：原始隐式回溯的代码（修正后的朴素DFS/回溯代码）是：
class Solution {
    private int minDiff = Integer.MAX_VALUE;
    private int target;
    private int[][] mat;
    private int m, n;

    public int minimizeTheDifference(int[][] mat, int target) {
        this.mat = mat;
        this.target = target;
        this.m = mat.length;
        this.n = mat[0].length;
        dfs(0, 0);
        return minDiff;
    }

    private void dfs(int row, int curSum) {
        if (row == m) {
            minDiff = Math.min(minDiff, Math.abs(curSum - target));
            return;
        }
        for (int j = 0; j < n; j++) {
            dfs(row + 1, curSum + mat[row][j]);
        }
    }
}
现在要改成显式回溯，使用对象类型 currentSum（数组）作为参数，并在递归前后手动修改和恢复。我们还需要将 minDiff 作为类成员，但递归函数不再通过值传递 curSum，而是通过引用传递数组。
给出代码。
以下是显式回溯的版本，递归方法签名与隐式版本类似 dfs(row, curSum)，但 curSum 改为对象类型（长度为 1 的数组），需要手动回溯。
class Solution {
    private int minDiff = Integer.MAX_VALUE;
    private int target;
    private int[][] mat;
    private int m, n;

    public int minimizeTheDifference(int[][] mat, int target) {
        this.mat = mat;
        this.target = target;
        this.m = mat.length;
        this.n = mat[0].length;
        // 用长度为1的数组作为可变的当前和
        dfs(0, new int[]{0});
        return minDiff;
    }

    private void dfs(int row, int[] curSum) {
        if (row == m) {
            minDiff = Math.min(minDiff, Math.abs(curSum[0] - target));
            return;
        }
        for (int j = 0; j < n; j++) {
            // 选择当前元素，修改对象状态
            curSum[0] += mat[row][j];
            // 递归下一行
            dfs(row + 1, curSum);
            // 撤销选择，显式回溯
            curSum[0] -= mat[row][j];
        }
    }
}
说明
- curSum 是一个数组（对象），传递的是引用，因此在递归调用过程中所有层共享同一个数组实例。
- 每进入一个分支，我们修改 curSum[0]；返回后立即还原，保证了后续分支的独立性。
- 这种方法虽然使用对象传递，但通过手动回溯模拟了隐式版本的值传递效果，属于显式回溯的经典写法。
与隐式版本相比，显式版本稍显冗长，但可以更清晰地展示回溯过程，也便于扩展（如需要记录路径等其他可变状态时）。

Q8：Based on your top down memoization solution below, write up a bottom up Dynamic Programming solution
我们被要求基于给定的自顶向下记忆化DP代码，写出一个自底向上的动态规划解决方案。
首先，理解问题：给定矩阵mat (m行n列)，从每行选一个数，得到一个总和sum，求min |sum - target|。m, n <= 70，值 <= 70，所以最大总和 <= 70*70=4900。
自顶向下方案中，dfs(row, curSum)返回从第row行开始，当前累积和为curSum的情况下，后续能获得的最小绝对差。记忆化存储了(row, curSum)的结果。
自底向上方案通常使用一个布尔数组或一个集合来表示每一行可能的和，然后从最后一行向前推。但也可以使用DP表直接存储最小绝对差，但自底向上时，从最后一行开始，状态是(row, curSum)的最小绝对差，但curSum的范围是0..maxSum。我们可以逐行从下往上计算。
另一种常见DP：从第一行开始，维护一个布尔集合记录当前所有可能的和。然后对每一行扩展新的和。最后在最后一行的所有可能和中找最接近target的。这是更简洁的自底向上方法，不需要存储所有行，只需要上一行的可能和集合。
然而，题目要求的是“基于你的 top down memoization solution 写一个 bottom up Dynamic Programming solution”，所以我们应该保留DP表的形式，但迭代计算。
我们可以这样设计：
定义dp[row][sum] = 从第row行开始（包括第row行），当前累积和为sum（累积和是从第0行到第row-1行的和，所以dp[row][sum] 表示在已经选定了前row行的元素，累积和为sum时，后续行所能获得的最小绝对差）。但这样定义有点绕。更直接地，我们可以定义dp[i][s] 表示考虑前i行（0-indexed），能否达到和s。最后找到所有可能的s中|s-target|的最小值。这是布尔DP，而不是存储最小绝对差。
然而，给定的自顶向下代码返回的是最小绝对差，并且使用了记忆化。我们可以将自顶向下转换为自底向上，使用相同的状态定义，但计算顺序相反。
我们定义f[row][sum] = 从第row行开始，当前累积和为sum时，后续能获得的最小绝对差。那么：
基础：f[m][sum] = |sum - target| （当row == m时，没有后续行，直接计算绝对差）。
递推：f[row][sum] = min_{col in 0..n-1} f[row+1][sum + mat[row][col]]。
因为我们需要计算f[0][0]（从第0行开始，初始和0）。自底向上时，我们可以从row = m-1递减到0，计算每个sum对应的值。
注意sum的范围：0 到 maxSum（所有元素最大值之和）。maxSum可以预先计算为每行最大值之和，但为了简单，我们可以用更宽松的上限：70*70=4900。但也可以动态计算每行最大值之和，以减少内存。
在自底向上时，我们需要一个二维数组dp，维度是(m+1) x (maxSum+1)。对于每个row，我们计算dp[row][sum] = min over col of dp[row+1][sum + mat[row][col]]。这里dp[m][sum]已知为|sum-target|。
由于sum+mat[row][col]可能超过maxSum，但实际我们只需要考虑在合理范围内的sum。为了简化，我们可以限制sum的范围，并初始化dp[m][sum] = |sum-target|，对于所有0..maxSum。
最终答案是dp[0][0]。
注意：因为mat[row][col]可能较大，sum+mat[row][col]可能超过maxSum，但在我们计算时，如果超过maxSum，那么这个sum实际上无法通过后续行达到（因为后续行至少选非负数，和会更大），但这种情况在DP中我们可能不需要考虑，因为我们的maxSum是基于每行最大值的，但如果实际选的值更大，和可能超过maxSum。实际上，maxSum定义为每行最大值的和，所以任何一条路径的和都不会超过maxSum，因此sum+mat[row][col]一定≤maxSum。
所以我们可以安全地使用这个范围。
下面是自底向上的Java代码：
class Solution {
    public int minimizeTheDifference(int[][] mat, int target) {
        int m = mat.length;
        int n = mat[0].length;
        // 计算每行最大值，用于最大可能和
        int maxSum = 0;
        for (int[] row : mat) {
            int maxInRow = 0;
            for (int val : row) {
                maxInRow = Math.max(maxInRow, val);
            }
            maxSum += maxInRow;
        }

        // dp[row][sum] = 从第row行开始，当前累积和为sum时，后续能获得的最小绝对差
        // 使用 (m+1) x (maxSum+1) 大小
        int[][] dp = new int[m + 1][maxSum + 1];
        
        // 初始化最后一行（row = m）
        for (int sum = 0; sum <= maxSum; sum++) {
            dp[m][sum] = Math.abs(sum - target);
        }
        
        // 自底向上计算
        for (int row = m - 1; row >= 0; row--) {
            // 对于每个可能的累积和 sum
            for (int sum = 0; sum <= maxSum; sum++) {
                int best = Integer.MAX_VALUE;
                for (int col = 0; col < n; col++) {
                    int nextSum = sum + mat[row][col];
                    if (nextSum > maxSum) continue; // 理论上不会发生，但安全起见
                    best = Math.min(best, dp[row + 1][nextSum]);
                }
                dp[row][sum] = best;
            }
        }
        
        return dp[0][0];
    }
}
优化：可以只保留两行（滚动数组），因为递推只依赖下一行。但题目没有要求，此处给出完整二维数组版本。
测试：对于示例 mat = [[1,2,3],[4,5,6],[7,8,9]], target=13，最大和=3+6+9=18，计算后dp[0][0]应该为0，因为存在1+4+8=13。
但请注意：我们的maxSum是每行最大值的和，但实际可能有路径和超过这个值吗？不会，因为每行最大值之和是路径可能的最大和。但我们的dp数组大小正好覆盖了所有可能的和。所以是正确的。
然而，对于某些测试用例，如果某行的元素不是最大值，但最终和可能小于maxSum，dp[row][sum]对于某些sum可能从未被计算到，但dp[0][0]是会被计算的，因为从0开始，每步加的元素都是合法的，最终nextSum一定≤maxSum。
该自底向上DP的时间复杂度是O(m * maxSum * n)，空间O(m * maxSum)。maxSum ≤ 4900，m ≤ 70，n ≤ 70，所以大约 70 * 4900 * 70 ≈ 24,010,000，可以接受。
如果需要进一步优化，可以使用布尔集合DP（仅记录可达和），但题目要求基于给定自顶向下代码写自底向上，所以这个版本是合适的。
以下是基于给定自顶向下记忆化 DP 的自底向上实现，使用二维数组 dp[row][sum] 存储从第 row 行开始、当前累积和为 sum 时，后续能获得的最小绝对差值。
class Solution {
    public int minimizeTheDifference(int[][] mat, int target) {
        int m = mat.length;
        int n = mat[0].length;
        
        // 计算最大可能和（每行最大值之和）
        int maxSum = 0;
        for (int[] row : mat) {
            int maxInRow = 0;
            for (int val : row) {
                maxInRow = Math.max(maxInRow, val);
            }
            maxSum += maxInRow;
        }
        
        // dp[row][sum] 表示从第 row 行开始，当前累积和为 sum 时的最小绝对差
        int[][] dp = new int[m + 1][maxSum + 1];
        
        // 初始化最后一行（row = m，即没有后续行）
        for (int sum = 0; sum <= maxSum; sum++) {
            dp[m][sum] = Math.abs(sum - target);
        }
        
        // 自底向上计算
        for (int row = m - 1; row >= 0; row--) {
            for (int sum = 0; sum <= maxSum; sum++) {
                int best = Integer.MAX_VALUE;
                for (int col = 0; col < n; col++) {
                    int nextSum = sum + mat[row][col];
                    if (nextSum <= maxSum) { // 有效范围
                        best = Math.min(best, dp[row + 1][nextSum]);
                    }
                }
                dp[row][sum] = best;
            }
        }
        
        // 从第 0 行、累积和为 0 开始
        return dp[0][0];
    }
}
思路说明
- 状态定义：dp[row][sum] 表示处理完前 row 行后，当前累积和为 sum 时，剩余行所能产生的最小绝对差。
- 边界：dp[m][sum] = |sum - target|，表示已选完所有行，直接计算差值的绝对值。
- 转移方程：dp[row][sum] = min_{col} dp[row+1][sum + mat[row][col]]，即当前行选择某一列，将累积和增加该值，然后取后续行的最小值。
- 计算顺序：从最后一行 row = m-1 向上计算到 row = 0。
- 最终答案：dp[0][0]。
复杂度
- 时间复杂度：O(m * maxSum * n)，其中 maxSum ≤ 4900，在题目限制下可接受。
- 空间复杂度：O(m * maxSum)，若需优化可改为滚动数组。

Refer to Kimi
Q1：I have two wrong solutions showing above, what their problem ?
I'll analyze your two attempts and help you understand the issues, then provide a correct Native DFS/Backtracking solution.
Issue Analysis
First Attempt (Native DFS)
Bug 1: Wrong summation logic
for(int j = 0; j < mat[0].length; j++) {
    sum = mat[row][col] + helper(mat, row + 1, j);  // ❌ Overwrites sum, doesn't accumulate
}
You're overwriting sum in each iteration instead of finding the minimum. Also, you're not actually combining results from different paths properly.
Bug 2: Missing target comparison at intermediate steps
You only compare |sum - target| at the top level, but you need to explore all complete paths and find which complete sum is closest to target.
Second Attempt (Backtracking)
Bug 1: Confused state management
curSum[0] += mat[row][col];  // Adding same element multiple times in loop
sum += curSum[0];            // Meaningless accumulation
You're adding mat[row][col] to curSum[0] inside the loop, but mat[row][col] is fixed for this call - you're adding it mat[0].length times incorrectly.
Bug 2: Not tracking the actual path sum
You're not properly accumulating the sum along the path from root to leaf.
Bug 3: Return value is meaningless
The sum variable and return value don't represent the actual path sum.
Correct Native DFS/Backtracking Solution
The key insight: You must pick exactly one element from each row. So we need to try all combinations (one from row 0, one from row 1, ..., one from row n-1).
class Solution {
    private int minDiff = Integer.MAX_VALUE;
    
    public int minimizeTheDifference(int[][] mat, int target) {
        // Start DFS from row 0 with initial sum 0
        dfs(mat, 0, 0, target);
        return minDiff;
    }
    
    private void dfs(int[][] mat, int row, int currentSum, int target) {
        // Base case: processed all rows
        if (row == mat.length) {
            minDiff = Math.min(minDiff, Math.abs(currentSum - target));
            return;
        }
        
        // Try each element in current row
        for (int col = 0; col < mat[0].length; col++) {
            // Choose mat[row][col], add to sum, move to next row
            dfs(mat, row + 1, currentSum + mat[row][col], target);
        }
    }
}
How It Works
For mat = [[1,2,3],[4,5,6],[7,8,9]], target = 13:
The DFS explores all 3×3×3 = 27 paths:
- 1→4→7 = 12, |12-13| = 1
- 1→4→8 = 13, |13-13| = 0  ✓ Optimal!
- 1→4→9 = 14, |14-13| = 1
- ... and so on
The minimum difference is 0 (path 1→4→8 or 2→5→6, etc.).
Key Differences from Your Attempts
AspectYour AttemptsCorrect SolutionState passingTried to return values upPass sum down as parameterAccumulationConfused local/global variablesClean parameter passingBase caseOnly at leaf, not comparingCompare at complete pathLoop structureNested loops with wrong logicSingle loop, recurse to next row
This solution has O(n^m) time complexity where n = rows, m = cols - acceptable for understanding the algorithm, but will need memoization/DP optimization for larger inputs.

Q2：Looks like its an implicit backtracking, since the parameter in recursion method is primitive 'int' and no need explicit backtracking ? And also the backtracking tactic always working with void return recursion method and a global variable style in case we want to enumerate all possibilities and find a min or max one out of them ?
Excellent observations! You've grasped two very important concepts about backtracking. Let me confirm and expand on your understanding:
1. Implicit Backtracking with Primitive Parameters
You're absolutely correct!
dfs(mat, row + 1, currentSum + mat[row][col], target);
//                    ^^^^^^^^^^^^^^^^^^^^^^
//                    creates a NEW copy, no need to undo
When you pass currentSum + mat[row][col], Java creates a new primitive value for the next recursive call. The currentSum in the current stack frame remains unchanged. This is implicit backtracking - no explicit "undo" operation needed.
Contrast this with your second attempt using int[] curSum (reference type):
curSum[0] += mat[row][col];  // modifies shared object
helper(...);
curSum[0] -= mat[row][col];  // MUST undo (explicit backtracking)
TypeBehaviorBacktracking Styleint, boolean, etc.Pass-by-value (copy)Implicit - automaticint[], List, StringBuilderPass-by-reference (shared)Explicit - manual undo required
2. Void Return + Global/Instance Variable Pattern
Yes, this is a classic backtracking pattern! Especially for "find min/max/count/all solutions" problems.
Pattern Structure:
class Solution {
    // Global state to track the answer across all recursive paths
    private int bestResult = INITIAL_VALUE;  // or List<...> for all solutions
    
    public int solve(...) {
        backtrack(...initialState...);
        return bestResult;
    }
    
    private void backtrack(...) {
        if (baseCase) {
            bestResult = update(bestResult, currentSolution);
            return;
        }
        
        for (each choice) {
            // make choice (implicit or explicit)
            backtrack(...nextState...);
            // undo choice (if explicit backtracking needed)
        }
    }
}
Why Void + Global Variable?
AspectReasoningEnumerationWe need to visit ALL leaves to find min/max, not just return one valueAggregationMultiple paths contribute to one final answerState simplicityDon't need to return complex state up the call stackEarly pruningCan modify global best to prune future branches (optimization)
Alternative: Return Value Style (Less Common for Min/Max)
You could write it with returns, but it's clumsy for min/max:
// Possible, but messy for this use case
private int dfs(int[][] mat, int row, int currentSum, int target) {
    if (row == mat.length) {
        return Math.abs(currentSum - target);
    }
    
    int minDiff = Integer.MAX_VALUE;
    for (int col = 0; col < mat[0].length; col++) {
        int diff = dfs(mat, row + 1, currentSum + mat[row][col], target);
        minDiff = Math.min(minDiff, diff);
    }
    return minDiff;
}
This works but:
- Returns "best from this subtree" rather than global best
- Harder to add early termination optimizations
- Less intuitive when you need to collect all solutions
Summary Table
ScenarioRecommended PatternFind min/max/count across all pathsvoid + global variableCheck if any path exists (boolean)boolean return + early returnReturn all solutionsvoid + global ListNeed to use reference types (arrays, etc.)Explicit backtracking (undo after recurse)Primitive types onlyImplicit backtracking (no undo needed)
Your insight shows good algorithmic intuition! The "void + global" pattern with implicit backtracking is indeed the cleanest approach for this type of exhaustive enumeration problem.

Q3：But if follow Backtracking and go solution, we cannot optimize it with Dynamic Programming tactic, right ? But if we follow the "Alternative: Return Value Style", it can optimize with Dynamic Programming tactic, right ?
Absolutely correct! You've identified a crucial architectural difference. This is exactly why the return-value style is considered more "DP-friendly" while the void+global style is "enumeration-only."
The Core Issue: Caching Requires Return Values
Memoization/DP works by caching: state -> result
Void + Global Style (Cannot DP easily)
private void dfs(int row, int currentSum) {
    if (row == mat.length) {
        minDiff = Math.min(minDiff, Math.abs(currentSum - target)); // Side effect!
        return;
    }
    for (int col = 0; col < mat[0].length; col++) {
        dfs(row + 1, currentSum + mat[row][col]);
    }
}
Why it fails for DP:
- The function returns nothing (void)
- The "result" is scattered into a global variable through side effects
- If we call dfs(2, 15) twice, there's no return value to cache - we just know it might update minDiff
- We can't ask: "What's the best answer from state (row=2, sum=15)?"
Return-Value Style (DP-Ready)
private int dfs(int row, int currentSum) {
    if (row == mat.length) {
        return Math.abs(currentSum - target); // Returns result!
    }
    int res = Integer.MAX_VALUE;
    for (int col = 0; col < mat[0].length; col++) {
        res = Math.min(res, dfs(row + 1, currentSum + mat[row][col]));
    }
    return res;
}
Why it enables DP:
- Clear mapping: f(row, currentSum) = minimum achievable difference from this state
- We can cache: memo[row][currentSum] = result
- Overlapping subproblems are naturally exposed
Applying DP to LC 1981
Here's how you'd optimize the return-value version:
class Solution {
    private int[][] mat;
    private int target;
    private int n, m;
    private Map<String, Integer> memo = new HashMap<>(); // or int[][] memo
    
    public int minimizeTheDifference(int[][] mat, int target) {
        this.mat = mat;
        this.target = target;
        this.n = mat.length;
        this.m = mat[0].length;
        return dfs(0, 0);
    }
    
    private int dfs(int row, int currentSum) {
        // Base case
        if (row == n) {
            return Math.abs(currentSum - target);
        }
        
        // Check cache
        String key = row + "," + currentSum;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        
        int res = Integer.MAX_VALUE;
        for (int col = 0; col < m; col++) {
            // Pruning: if currentSum already too big, might skip or continue
            res = Math.min(res, dfs(row + 1, currentSum + mat[row][col]));
            
            // Early termination: if we found 0, can't get better
            if (res == 0) break;
        }
        
        // Store in cache
        memo.put(key, res);
        return res;
    }
}
The Pattern: Conversion for Optimization
StageStyleUse CaseBrute Forcevoid + globalEasy to write, enumerate allMemoizationReturn-valueCache overlapping subproblemsTabulationBottom-up DPConvert recursion to iteration
Key insight: To add DP to a backtracking solution, you often need to refactor from the "void+global" style to the "return-value" style first.
One Caveat
You can technically force DP onto void+global by passing the memo table and checking if we've already computed the best possible contribution from this state, but it's:
- Awkward: You need to track what the "best contribution from here" would be
- Error-prone: Side effects + caching = complexity
- Limited: Harder to prune or optimize
Conclusion: Your intuition is spot-on. If you anticipate needing DP/memoization, start with the return-value style. If you just need brute force enumeration, void+global is cleaner.

Q4：I have below wrong solution, the reason is i comment out 3 lines:
//if(nextSum > max) {
//    continue;
//}
Input:
mat = [[1,2,3],[4,5,6],[7,8,9]]
target = 13
Error:
java.lang.ArrayIndexOutOfBoundsException: Index 25 out of bounds for length 19
  at line 24, Solution.minimizeTheDifference
  at line 56, __DriverSolution__.__helper__
  at line 89, __Driver__.main
class Solution {
    public int minimizeTheDifference(int[][] mat, int target) {
        int max = 0;
        for(int i = 0; i < mat.length; i++) {
            int rowMax = 0;
            for(int j = 0; j < mat[0].length; j++) {
                rowMax = Math.max(rowMax, mat[i][j]);
            }
            max += rowMax;
        }
        int[][] dp = new int[mat.length + 1][max + 1];
        for(int i = 0; i <= max; i++) {
            dp[mat.length][i] = Math.abs(i - target);
        }
        for(int i = mat.length - 1; i >= 0; i--) {
            for(int curSum = max; curSum >= 0; curSum--) {
                int min = Integer.MAX_VALUE;
                for(int j = 0; j < mat[0].length; j++) {
                    int nextSum = curSum + mat[i][j];
                    //if(nextSum > max) {
                    //    continue;
                    //}
                    min = Math.min(min, dp[i + 1][nextSum]);
                }
                dp[i][curSum] = min;
            }
        }
        return dp[0][0];
    }
}
But i don't see its exactly mapping Native DFS method has such a limitation:
class Solution {
    public int minimizeTheDifference(int[][] mat, int target) {
        return helper(mat, target, 0, 0);
    }

    private int helper(int[][] mat, int target, int row, int curSum) {
        if(row == mat.length) {
            return Math.abs(curSum - target);
        }
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < mat[0].length; i++) {
            min = Math.min(min, helper(mat, target, row + 1, curSum + mat[row][i]));
        }
        return min;
    }
}
I don't know why when we move on to Dynamic Programming way, 'nextSum' has a chance to break the 'max' ?
Refer to Kimi
你的核心困惑是：为什么递归DFS不会越界，而DP会？
根本原因：访问方式不同
特性DFS 递归DP 数组存储方式栈帧传递参数 curSum预分配数组 dp[max+1]边界检查无（只要 curSum不超 Integer.MAX_VALUE）必须保证索引 0 <= nextSum <= max你的问题curSum可以任意增长（理论上）nextSum可能 > max，导致 ArrayIndexOutOfBoundsException
具体分析你的错误
你的 max 计算的是所有行最大值的总和（即理论上的最大可能和）。
// 你的代码逻辑
for(int curSum = max; curSum >= 0; curSum--) {  // curSum 可以取到 max
    for(int j = 0; j < mat[0].length; j++) {
        int nextSum = curSum + mat[i][j];  // 这里可能 > max！
        // 当 curSum = max 且 mat[i][j] > 0 时，nextSum = max + 正数 > max
        min = Math.min(min, dp[i + 1][nextSum]);  // 越界！
    }
}
为什么 curSum 会等于 max？
因为你在循环中让 curSum 从 max 遍历到 0，对于所有行 i 都这样做。但实际上：
- 当处理第 i 行时（从上往下），curSum 应该只表示前 i 行能形成的和
- 前 i 行的最大和 = 第0行max + 第1行max + ... + 第i-1行max
- 这个值 < 所有行的 max（除非 i 是最后一行）
所以当你在第0行（i=0）就让 curSum 取到 max（所有行的总和），这本身就是一个不可能达到的状态（因为第0行还没选呢，怎么可能有后面所有行的和？）。但代码不管这些，它尝试计算 nextSum = max + mat[0][j]，结果超过了数组边界。
为什么那三行注释能解决问题
if(nextSum > max) {
    continue;  // 跳过非法状态，防止数组越界
}
这行代码的作用是：当当前累积和已经太大，加上当前元素会超过理论最大和时，直接跳过。这对应着DFS中的"剪枝"——只不过DFS没有数组边界限制，所以不需要显式检查。
更本质的区别
- DFS：是"自顶向下"的探索，只有当递归深度到达最后一行时才计算结果。curSum 是真实构建出来的，它不可能超过 max（因为你最多选 m 个元素，每个不超过该行最大值），所以不会出问题。
- DP：是"自底向上"的填表，你试图用 dp[i][curSum] 表示状态。为了让代码简单，你把 curSum 的范围统一设为 [0, max]，这就导致在早期的行（i 较小）时，curSum 取大值是不合理的，会产生越界。
修复建议
除了加回那三行，你还可以调整DP的范围使其更精确（但代码会更复杂）：
// 更精确的范围控制（不需要那三行也能跑，但复杂度类似）
int prevMax = 0; // 前i-1行的最大和
for (int i = mat.length - 1; i >= 0; i--) {
    int rowMax = 0;
    for (int x : mat[i]) rowMax = Math.max(rowMax, x);
    
    // 当前行curSum的范围应该是 [0, prevMax]
    for (int curSum = prevMax; curSum >= 0; curSum--) {
        // ... 计算 nextSum = curSum + mat[i][j]
        // 现在 nextSum 最大 = prevMax + rowMax，不会越界
    }
    prevMax += rowMax;
}
结论：保留那三行 if(nextSum > max) continue; 是最简单正确的做法。它本质上是在DP中模拟了DFS的"只访问合法状态"的行为。

Q5：Full code if looks like below:
but error out on input as below:
Input
mat = [[1],[2],[3]]
target = 100
Output = 0
Expected = 94
class Solution {
    public int minimizeTheDifference(int[][] mat, int target) {
        // Calculate total maximum possible sum
        int max = 0;
        for (int[] row : mat) {
            int rowMax = 0;
            for (int val : row) rowMax = Math.max(rowMax, val);
            max += rowMax;
        }
        
        // dp[i][curSum] = min diff starting from row i with prefix sum = curSum
        int[][] dp = new int[mat.length + 1][max + 1];
        
        // Base case: after last row, difference is |curSum - target|
        for (int sum = 0; sum <= max; sum++) {
            dp[mat.length][sum] = Math.abs(sum - target);
        }
        
        // Backward DP with prevMax bounds control
        int prevMax = 0;  // Max sum achievable from rows [0, i-1]
        
        for (int i = mat.length - 1; i >= 0; i--) {
            // Find max value in current row
            int rowMax = 0;
            for (int val : mat[i]) {
                rowMax = Math.max(rowMax, val);
            }
            
            // Only iterate curSum over REACHABLE range [0, prevMax]
            for (int curSum = prevMax; curSum >= 0; curSum--) {
                int minDiff = Integer.MAX_VALUE;
                
                for (int val : mat[i]) {
                    int nextSum = curSum + val;
                    minDiff = Math.min(minDiff, dp[i + 1][nextSum]);
                }
                
                dp[i][curSum] = minDiff;
            }
            
            // Update prevMax for next iteration (row i-1)
            prevMax += rowMax;
        }
        
        return dp[0][0];  // Start from row 0 with prefix sum = 0
    }
}
问题的根源在于 prevMax 的初始化错误导致 DP 表访问了未计算的单元格（值为 0）。
错误分析
你的代码意图是用 prevMax 限制 curSum 的遍历范围，以减少计算量。但在倒序遍历（从最后一行往前）时：
- prevMax 应该表示当前行之前所有行（即 rows [0, i-1]）能达到的最大和
- 但你初始化为 0，导致第一轮（最后一行）只计算了 dp[m-1][0]，而 dp[m-1][1...] 都还是默认值 0
- 当处理倒数第二行时，你访问 dp[m-1][nextSum]（其中 nextSum 可能为 3, 4, 5...），读到未初始化的 0，导致结果错误
具体 trace 你的错误输入
mat = [[1],[2],[3]], target = 100
1.初始化：max = 6，dp[3][6] = |6-100| = 94（正确）
2.i=2（第三行 [3]）：prevMax = 0，只计算 dp[2][0] = dp[3][3] = 97，然后 prevMax 更新为 3
3.i=1（第二行 [2]）：curSum 遍历 [0,1,2,3]
- 当 curSum=1 时，nextSum = 1+2 = 3，访问 dp[2][3]
- 但 dp[2][3] 从未被计算过（默认为 0），所以 minDiff 变成 0
4.最终 dp[0][0] 继承了错误的 0
解决方案
方案 1：加回边界检查（推荐，最简单）
既然 prevMax 优化容易出错，且题目约束 max <= 70*70 = 4900 很小，直接遍历完整范围并跳过越界：
class Solution {
    public int minimizeTheDifference(int[][] mat, int target) {
        int max = 0;
        for (int[] row : mat) {
            int rowMax = 0;
            for (int val : row) rowMax = Math.max(rowMax, val);
            max += rowMax;
        }
        int[][] dp = new int[mat.length + 1][max + 1];
        // Base case
        for (int sum = 0; sum <= max; sum++) {
            dp[mat.length][sum] = Math.abs(sum - target);
        }
        // 倒序 DP
        for (int i = mat.length - 1; i >= 0; i--) {
            // 注意：这里遍历完整范围 [0, max]
            for (int curSum = max; curSum >= 0; curSum--) {
                int minDiff = Integer.MAX_VALUE;
                for (int val : mat[i]) {
                    int nextSum = curSum + val;
                    // 就是这 3 行！防止访问 dp[i+1][>max]
                    if (nextSum > max) {
                        continue;
                    }
                    minDiff = Math.min(minDiff, dp[i + 1][nextSum]);
                }
                dp[i][curSum] = minDiff;
            }
        }
        return dp[0][0];
    }
}
方案 2：修正 prevMax 逻辑（保持优化）
如果你坚持用 prevMax 优化，需要预先计算前缀和来正确初始化：
class Solution {
    public int minimizeTheDifference(int[][] mat, int target) {
        int m = mat.length;
        int max = 0;
        int[] prefixMax = new int[m + 1]; // prefixMax[i] = rows[0..i-1] 的最大和
        for (int i = 0; i < m; i++) {
            int rowMax = 0;
            for (int val : mat[i]) rowMax = Math.max(rowMax, val);
            max += rowMax;
            prefixMax[i + 1] = prefixMax[i] + rowMax;
        }
        int[][] dp = new int[m + 1][max + 1];
        for (int sum = 0; sum <= max; sum++) {
            dp[m][sum] = Math.abs(sum - target);
        }
        // 倒序遍历
        for (int i = m - 1; i >= 0; i--) {
            //int rowMax = prefixMax[i + 1] - prefixMax[i]; // 当前行最大值
            int curPrevMax = prefixMax[i]; // 之前所有行的最大和
            // 只需遍历 [0, curPrevMax]，因为这就是当前能达到的 curSum 范围
            for (int curSum = curPrevMax; curSum >= 0; curSum--) {
                int minDiff = Integer.MAX_VALUE;
                for (int val : mat[i]) {
                    int nextSum = curSum + val;
                    // nextSum 的最大值 = curPrevMax + rowMax = prefixMax[i+1]
                    // 而 dp[i+1] 在上一轮已经填到了 prefixMax[i+1]，所以安全
                    minDiff = Math.min(minDiff, dp[i + 1][nextSum]);
                }
                dp[i][curSum] = minDiff;
            }
        }
        return dp[0][0];
    }
}
关键区别：用 prefixMax[i] 代替手动维护的 prevMax，确保每次访问 dp[i+1][nextSum] 时，该位置已经被计算过（因为上一轮 i+1 填到了 prefixMax[i+1]）。
总结
- DFS 不越界：因为它是"探索式"的，curSum 是实实在在算出来的，自然不超过 max
- DP 会越界：因为你预分配了固定大小的数组，且倒序填表时如果没有正确限制范围，会访问到未初始化的格子
- 最简单修复：加回 if (nextSum > max) continue;，这是标准做法

追问：如果依然使用手动维护的 prevMax，该怎么写？
如果坚持手动维护 prevMax（即不用 prefixMax 数组），关键在于理解倒序 DP 中 prevMax 应该是递减的，而不是递增的。
正确写法
class Solution {
    public int minimizeTheDifference(int[][] mat, int target) {
        int m = mat.length;
        
        // 1. 先计算每行的最大值和总和
        int[] rowMax = new int[m];
        int totalMax = 0;
        for (int i = 0; i < m; i++) {
            int mx = 0;
            for (int val : mat[i]) mx = Math.max(mx, val);
            rowMax[i] = mx;
            totalMax += mx;
        }
        
        // 2. DP 数组，只开到 totalMax 即可
        int[][] dp = new int[m + 1][totalMax + 1];
        
        // 3. 初始化最后一行：dp[m][s] = |s - target|
        for (int s = 0; s <= totalMax; s++) {
            dp[m][s] = Math.abs(s - target);
        }
        
        // 4. 手动维护 prevMax：表示 dp[i+1] 当前的有效范围上限
        // 初始时 i=m-1，dp[m] 的范围是 [0, totalMax]
        int prevMax = totalMax;
        
        // 5. 倒序遍历每一行
        for (int i = m - 1; i >= 0; i--) {
            int currentRowMax = rowMax[i];
            
            // 关键：curSum 的范围是 [0, prevMax - currentRowMax]
            // 因为 nextSum = curSum + val，最大到 (prevMax - currentRowMax) + currentRowMax = prevMax
            // 正好在 dp[i+1] 的有效范围内，不会越界
            for (int curSum = prevMax - currentRowMax; curSum >= 0; curSum--) {
                int minDiff = Integer.MAX_VALUE;
                for (int val : mat[i]) {
                    int nextSum = curSum + val;
                    minDiff = Math.min(minDiff, dp[i + 1][nextSum]);
                }
                dp[i][curSum] = minDiff;
            }
            
            // 更新 prevMax：当前行处理完后，dp[i] 的范围上限是 prevMax - currentRowMax
            // 供下一轮（i-1）使用
            prevMax -= currentRowMax;
        }
        
        return dp[0][0];
    }
}
关键逻辑解释
步骤含义prevMax = totalMaxdp[m]覆盖 [0, totalMax]，因为最后一行前面所有行（0..m-1）的和最大为 totalMaxcurSum <= prevMax - rowMax[i]确保 nextSum = curSum + val <= (prevMax - rowMax[i]) + rowMax[i] = prevMax，即不超出 dp[i+1]的范围prevMax -= rowMax[i]处理完第 i行后，dp[i] 的有效范围上限就是 prevMax - rowMax[i]，这正好对应前 i 行的最大和
为什么你的写法错了
你的写法试图让 prevMax 递增（从 0 开始累加），这在正序 DP 中是对的，但在倒序 DP 中：
- 倒序时，第 i 行依赖第 i+1 行的结果
- 第 i+1 行只计算了 [0, prevMax] 的范围（较小）
- 第 i 行却需要访问 dp[i+1][nextSum]，其中 nextSum 可能达到 prevMax + rowMax[i]（较大）
- 导致访问越界或读到未初始化的值
因此倒序时必须让 prevMax 递减（从 totalMax 开始减），确保每次访问都在已计算的范围内。




Refer to
L64.Minimum Path Sum (Ref.L62,L63)
L112.P9.1.Path Sum (Ref.L257,L113)
L416.P16.2.Partition Equal Subset Sum
L1937.Maximum Number of Points with Cost (Ref.L64,L1981)
