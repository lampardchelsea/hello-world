https://leetcode.com/problems/transpose-matrix/description/
Given a 2D integer array matrix, return the transpose of matrix.
The transpose of a matrix is the matrix flipped over its main diagonal, switching the matrix's row and column indices.


 
Example 1:
Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
Output: [[1,4,7],[2,5,8],[3,6,9]]

Example 2:
Input: matrix = [[1,2,3],[4,5,6]]
Output: [[1,4],[2,5],[3,6]]
 
Constraints:
- m == matrix.length
- n == matrix[i].length
- 1 <= m, n <= 1000
- 1 <= m * n <= 105
- -109 <= matrix[i][j] <= 109
--------------------------------------------------------------------------------
Attempt 1: 2026-07-20
Solution 1: Array + Matrix (10 min)
Refer to Deepseek
这道题 (LeetCode 867) 是一个非常基础的矩阵操作问题，难度为 Easy。
它的核心逻辑就是一句话：把矩阵的行变成列，把列变成行。对于原矩阵中的元素 matrix[i][j]，它在转置矩阵中的位置是 result[j][i]。
关键注意点：维度变化
如果原矩阵是 R 行 C 列，那么转置矩阵就是 C 行 R 列。
例如：2 x 3 的矩阵转置后会变成 3 x 2。不能直接在原数组上修改（除非是方阵且你想玩复杂的花样），直接开辟一个新数组是最安全、最标准的方法。
--------------------------------------------------------------------------------
Java 代码实现
class Solution {
    public int[][] transpose(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // 1. 创建一个新矩阵，行数 = 原列数，列数 = 原行数
        int[][] result = new int[cols][rows];
        
        // 2. 遍历原矩阵，交换行列索引
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        
        return result;
    }
}
示例验证
输入矩阵（2行3列）：
[1, 2, 3]
[4, 5, 6]
- i=0, j=0 -> result[0][0] = 1
- i=0, j=1 -> result[1][0] = 2
- i=0, j=2 -> result[2][0] = 3
- i=1, j=0 -> result[0][1] = 4
- ...输出矩阵（3行2列）：
[1, 4]
[2, 5]
[3, 6]
--------------------------------------------------------------------------------
复杂度分析
- 时间复杂度：O(R * C)，必须遍历矩阵中的每一个元素一次。
- 空间复杂度：O(R * C)，用于存储返回的结果矩阵（如果题目要求原地转置，复杂度才可能是 O(1)，但本题明确要求返回新矩阵，所以这是标准开销）。
--------------------------------------------------------------------------------
这道题在刷题体系中的位置
这道题是**“二维数组入门”的必做题。它不像之前的 2525（溢出陷阱）、2347（字符串匹配）或者 545（树边界），它纯粹考察你对数组下标映射**的熟练度。
常见坑点：
- 不小心写了 result[i][j] = matrix[i][j]，忘记交换 i 和 j。
- 初始化 result 时把行和列搞反了（写成 new int[rows][cols]），导致非方阵时数组越界。
只要记住 “原矩阵的横坐标，变成新矩阵的纵坐标”，这道题就能秒杀了。
