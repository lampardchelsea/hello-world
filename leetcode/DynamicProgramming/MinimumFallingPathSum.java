https://leetcode.com/problems/minimum-falling-path-sum/description/
Given an n x n array of integers matrix, return the minimum sum of any falling path through matrix.
A falling path starts at any element in the first row and chooses the element in the next row that is either directly below or diagonally left/right. Specifically, the next element from position (row, col) will be (row + 1, col - 1), (row + 1, col), or (row + 1, col + 1).
 
Example 1:

Input: matrix = [[2,1,3],[6,5,4],[7,8,9]]Output: 13Explanation: There are two falling paths with a minimum sum as shown.
Example 2:

Input: matrix = [[-19,57],[-40,-5]]Output: -59Explanation: The falling path with a minimum sum is shown.
 
Constraints:
- n == matrix.length == matrix[i].length
- 1 <= n <= 100
- -100 <= matrix[i][j] <= 100
--------------------------------------------------------------------------------
Attempt 1: 2026-04-03
Solution 1: Native DFS (60 min)
Wrong Solution
Input
matrix = [[2,1,3],[6,5,4],[7,8,9]]
Output = -2147483647
Expected = 13
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < matrix[0].length; i++) {
            min = Math.min(min, helper(matrix, 0, i));
        }
        return min;
    }

    private int helper(int[][] matrix, int row, int col) {
        if(col < 0 || col > matrix[0].length - 1) {
            return Integer.MAX_VALUE;
        }
        if(row == matrix.length - 1) {
            return matrix[row][col];
        }
        int result = Integer.MAX_VALUE;
        for(int i = -1; i < 2; i++) {
            result = Math.min(result, matrix[row][col] + helper(matrix, row + 1, col + i));
        }
        return result;
    }
}

Correct Solution (TLE 38/52)
Style 1
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < matrix[0].length; i++) {
            min = Math.min(min, helper(matrix, 0, i));
        }
        return min;
    }

    private int helper(int[][] matrix, int row, int col) {
        if(row == matrix.length - 1) {
            return matrix[row][col];
        }
        int result = Integer.MAX_VALUE;
        if(col == 0) {
            for(int i = 0; i < 2; i++) {
                result = Math.min(result, matrix[row][col] + helper(matrix, row + 1, col + i));
            }
        } else if(col == matrix[0].length - 1) {
            for(int i = -1; i < 1; i++) {
                result = Math.min(result, matrix[row][col] + helper(matrix, row + 1, col + i));
            }
        } else {
            for(int i = -1; i < 2; i++) {
                result = Math.min(result, matrix[row][col] + helper(matrix, row + 1, col + i));
            }
        }
        return result;
    }
}
Style 2
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < matrix[0].length; i++) {
            min = Math.min(min, helper(matrix, 0, i));
        }
        return min;
    }
    private int helper(int[][] matrix, int row, int col) {
        if (row == matrix.length - 1) {
            return matrix[row][col];
        }
        int result = Integer.MAX_VALUE;
        for (int d = -1; d <= 1; d++) {
            int newCol = col + d;
            if (newCol >= 0 && newCol < matrix[0].length) {
                result = Math.min(result, matrix[row][col] + helper(matrix, row + 1, newCol));
            }
        }
        return result;
    }
}

Time Complexity: O(n × 3ᵐ)
Space Complexity: O(m)
该解法的**时间复杂度**为 **O(n × 3ᵐ)**，**空间复杂度**为 **O(m)**，其中 `m` 是矩阵的行数，`n` 是列数。

### 详细分析
- **递归树**：从第一行的每个列开始（共 `n` 个起点），每一层（行）最多有 3 个分支（向左、向下、向右），
但边界处分支减少。总路径数约为 `n × 3^(m-1)`。
- **每个节点** 执行常数操作，递归调用次数等于所有路径上的节点总数。每条路径长度为 `m`，因此总调用次数约
为 `m × n × 3^(m-1)`，量级为 `O(n × 3ᵐ)`。
- **空间复杂度**：递归调用栈的最大深度为 `m`（行数），每层常数空间，因此为 `O(m)`。

### 注意
- 该解法未使用记忆化，对于较大的 `m`（如 `m = 100`）会指数爆炸，在实际 LeetCode 题目中会超时。通常需
使用动态规划（`O(m × n)` 时间，`O(n)` 空间）来优化。

Solution 2: DFS + Memoization (10 min)
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int min = Integer.MAX_VALUE;
        int m = matrix.length;
        int n = matrix[0].length;
        Integer[][] memo = new Integer[m][n];
        for (int i = 0; i < n; i++) {
            min = Math.min(min, helper(matrix, 0, i, memo));
        }
        return min;
    }

    private int helper(int[][] matrix, int row, int col, Integer[][] memo) {
        if (row == matrix.length - 1) {
            return matrix[row][col];
        }
        if(memo[row][col] != null) {
            return memo[row][col];
        }
        int result = Integer.MAX_VALUE;
        for (int d = -1; d <= 1; d++) {
            int newCol = col + d;
            if (newCol >= 0 && newCol < matrix[0].length) {
                result = Math.min(result, matrix[row][col] + helper(matrix, row + 1, newCol, memo));
            }
        }
        return memo[row][col] = result;
    }
}

Time Complexity: O(m * n)
Space Complexity: O(m * n)

该带记忆化的 DFS 解法（自顶向下动态规划）的时间复杂度为 **O(m × n)**，
空间复杂度为 **O(m × n)**，其中 `m` 是行数，`n` 是列数。

### 详细分析
- **状态总数**：`m × n` 个格子，每个格子对应一个状态 `(row, col)`。
- **每个状态的计算**：最多检查 3 个方向（`d = -1, 0, 1`），每个方向的有效性检查为 O(1)，
且由于使用了记忆化，每个状态只会被计算一次。因此总时间复杂度为 **O(m × n × 3)**，即 **O(m × n)**。
- **空间复杂度**：
  - 记忆化数组 `memo` 占用 `O(m × n)`。
  - 递归调用栈深度为 `O(m)`，可忽略。
  因此总空间复杂度为 **O(m × n)**。

该解法是 LeetCode 931 的有效解法，能够通过所有测试用例。

Solution 3: 2D DP (10 min, very similar to L1937.Maximum Number of Points with Cost (Ref.L64,L1981) 3D DP solution)
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        // Base case
        for(int col = 0; col < n; col++) {
            dp[m - 1][col] = matrix[m - 1][col];
        }
        // Start from second last row
        for(int row = m - 2; row >= 0; row--) {
            for(int col = 0; col < n; col++) {
                int result = Integer.MAX_VALUE;
                for(int d = -1; d <= 1; d++) {
                    int newCol = col + d;
                    if(newCol >= 0 && newCol < n) {
                        result = Math.min(result, matrix[row][col] + dp[row + 1][newCol]);
                    }
                    dp[row][col] = result;
                }
            }
        }
        // The answer is from first row
        int min = Integer.MAX_VALUE;
        for(int col = 0; col < n; col++) {
            min = Math.min(min, dp[0][col]);
        }
        return min;
    }
}

Time Complexity: O(m * n)
Space Complexity: O(m * n)

Refer to Deepseek
我们分析第一个错误代码：在helper中，如果col越界返回Integer.MAX_VALUE，但递归调用时没有检查越界，导致可能加上了MAX_VALUE导致溢出或错误。实际上，在递归调用前，应该先检查下一行col+i是否越界，而不是在进入helper时返回MAX_VALUE。因为当前行的值加上MAX_VALUE会溢出变成负数。所以第一个代码输出-2147483647是因为溢出了。
第二个代码试图通过边界判断来避免越界，但仍有问题：在col==0时，循环for(int i=0;i<2;i++)包括i=0和1，即向右和正下，缺少向左（i=-1）但左边越界，正确。但注意：helper递归调用时，如果下一行col+i越界，不会调用，因为循环只生成有效索引。但这里有个细节：在col==0时，只考虑了i=0和1，但i=1可能越界？如果co
l==0且n=1，则col+1=1越界？实际上矩阵至少1列，但若n=1，col==0且n-1=0，那么col==0且col==matrix[0].length-1，应该走else if，但这里顺序先判断col==0，会进入第一个分支，而循环i=0,1，其中i=1会导致col+1=1越界，但helper函数开头没有越界检查，会直接访问matrix[row+1][1]导致数组越界异常。所以需要处理边界情况。
更简单且正确的做法是：在helper中先检查越界，返回一个很大的值（如Integer.MAX_VALUE），但要注意避免加法溢出。正确方式：在递归调用时，先判断下一位置是否越界，若越界则跳过，不调用。这样就不需要helper内部检查越界。或者保留helper内部越界检查，但在加法时，如果helper返回MAX_VALUE，那么加上当前值会溢出，所以应该先判断helper返回值是否为MAX_VALUE，若是则跳过。
更好的方法是：在递归调用前检查col+i是否在范围内，若在则调用，否则跳过。这样helper就不需要处理越界情况，因为调用方已经保证了索引有效。
下面给出修正后的正确DFS（朴素递归，未加记忆化）：
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < matrix[0].length; i++) {
            min = Math.min(min, helper(matrix, 0, i));
        }
        return min;
    }
    private int helper(int[][] matrix, int row, int col) {
        if (row == matrix.length - 1) {
            return matrix[row][col];
        }
        int result = Integer.MAX_VALUE;
        for (int d = -1; d <= 1; d++) {
            int newCol = col + d;
            if (newCol >= 0 && newCol < matrix[0].length) {
                result = Math.min(result, matrix[row][col] + helper(matrix, row + 1, newCol));
            }
        }
        return result;
    }
}
基于你提供的原生 DFS 解法，我们可以将其转化为自底向上的 2D DP 解法。DFS 的递归关系为：从 (row, col) 出发的最小路径和等于 matrix[row][col] 加上下一行三个相邻列（col-1, col, col+1）的最小路径和中的最小值。因此，定义 dp[row][col] 表示从第 row 行第 col 列到达最后一行的最小路径和，则有状态转移方程：
- 最后一行：dp[m-1][col] = matrix[m-1][col]
- 对于 row = m-2 到 0：dp[row][col] = matrix[row][col] + min(dp[row+1][col-1], dp[row+1][col], dp[row+1][col+1])，其中需处理边界（当 col-1 或 col+1 越界时忽略）。
最终答案取 min(dp[0][col])。
下面是完整的 2D DP 代码：
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m][n];
        // 初始化最后一行
        for (int j = 0; j < n; j++) {
            dp[m - 1][j] = matrix[m - 1][j];
        }
        // 从倒数第二行向上递推
        for (int i = m - 2; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                int minNext = dp[i + 1][j]; // 正下方
                if (j > 0) {
                    minNext = Math.min(minNext, dp[i + 1][j - 1]);
                }
                if (j < n - 1) {
                    minNext = Math.min(minNext, dp[i + 1][j + 1]);
                }
                dp[i][j] = matrix[i][j] + minNext;
            }
        }
        // 找第一行的最小值
        int ans = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            ans = Math.min(ans, dp[0][j]);
        }
        return ans;
    }
}

该解法的时间复杂度为 O(m × n)，空间复杂度为 O(m × n)。如果需要优化空间，可以使用滚动数组将空间降到 O(n)。

Refer to
L1937.Maximum Number of Points with Cost (Ref.L64,L1981)
