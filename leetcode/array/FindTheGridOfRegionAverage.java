https://leetcode.com/problems/find-the-grid-of-region-average/description/
You are given m x n grid image which represents a grayscale image, where image[i][j] represents a pixel with intensity in the range [0..255]. You are also given a non-negative integer threshold.
Two pixels are adjacent if they share an edge.
A region is a 3 x 3 subgrid where the absolute difference in intensity between any two adjacent pixels is less than or equal to threshold.
All pixels in a region belong to that region, note that a pixel can belong to multiple regions.
You need to calculate a m x n grid result, where result[i][j] is the average intensity of the regions to which image[i][j] belongs, rounded down to the nearest integer. If image[i][j] belongs to multiple regions, result[i][j] is the average of the rounded-down average intensities of these regions, rounded down to the nearest integer. If image[i][j] does not belong to any region, result[i][j] is equal to image[i][j].
Return the grid result.
 
Example 1:
Input: image = [[5,6,7,10],[8,9,10,10],[11,12,13,10]], threshold = 3
Output: [[9,9,9,9],[9,9,9,9],[9,9,9,9]]
Explanation:

There are two regions as illustrated above. The average intensity of the first region is 9, while the average intensity of the second region is 9.67 which is rounded down to 9. The average intensity of both of the regions is (9 + 9) / 2 = 9. As all the pixels belong to either region 1, region 2, or both of them, the intensity of every pixel in the result is 9.
Please note that the rounded-down values are used when calculating the average of multiple regions, hence the calculation is done using 9 as the average intensity of region 2, not 9.67.

Example 2:
Input: image = [[10,20,30],[15,25,35],[20,30,40],[25,35,45]], threshold = 12
Output: [[25,25,25],[27,27,27],[27,27,27],[30,30,30]]
Explanation:

There are two regions as illustrated above. The average intensity of the first region is 25, while the average intensity of the second region is 30. The average intensity of both of the regions is (25 + 30) / 2 = 27.5 which is rounded down to 27.
All the pixels in row 0 of the image belong to region 1, hence all the pixels in row 0 in the result are 25. Similarly, all the pixels in row 3 in the result are 30. The pixels in rows 1 and 2 of the image belong to region 1 and region 2, hence their assigned value is 27 in the result.

Example 3:
Input: image = [[5,6,7],[8,9,10],[11,12,13]], threshold = 1
Output: [[5,6,7],[8,9,10],[11,12,13]]
Explanation:
There is only one 3 x 3 subgrid, while it does not have the condition on difference of adjacent pixels, for example, the difference between image[0][0] and image[1][0] is |5 - 8| = 3 > threshold = 1. None of them belong to any valid regions, so the result should be the same as image.
 
Constraints:
- 3 <= n, m <= 500
- 0 <= image[i][j] <= 255
- 0 <= threshold <= 255
--------------------------------------------------------------------------------
Attempt 1: 2026-02-22
Solution 1: Matrix (30 min)
Refer to Deepseek
这是 LeetCode 3030 题的 Java 解法。这个问题需要计算图像中每个像素所属的 3x3 区域的平均强度，涉及重叠区域的特殊处理规则。
解题思路
根据问题描述和题解分析，核心思路是：
1.枚举所有可能的3x3区域：从左上角开始遍历所有可能的3x3子网格
2.验证是否为有效区域：检查区域内任意相邻像素的绝对差是否 ≤ threshold
3.累加区域信息：对有效区域，计算平均强度并累加到该区域覆盖的9个像素上
4.计算最终结果：每个像素取所有归属区域平均值的平均（向下取整），若无归属则保留原值
Java 代码实现
class Solution {
    public int[][] resultGrid(int[][] image, int threshold) {
        int m = image.length;
        int n = image[0].length;
        
        // sum[i][j] 记录像素 (i,j) 所属区域的平均强度之和
        // cnt[i][j] 记录像素 (i,j) 所属区域的数量
        int[][] sum = new int[m][n];
        int[][] cnt = new int[m][n];
        
        // 枚举所有可能的3x3区域
        for (int i = 0; i + 2 < m; i++) {
            for (int j = 0; j + 2 < n; j++) {
                // 检查该3x3区域是否满足阈值条件
                boolean isValid = true;
                
                // 检查水平方向相邻像素
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 2; l++) {
                        if (Math.abs(image[i + k][j + l] - image[i + k][j + l + 1]) > threshold) {
                            isValid = false;
                            break;
                        }
                    }
                    if (!isValid) break;
                }
                
                // 检查垂直方向相邻像素
                if (isValid) {
                    for (int k = 0; k < 2; k++) {
                        for (int l = 0; l < 3; l++) {
                            if (Math.abs(image[i + k][j + l] - image[i + k + 1][j + l]) > threshold) {
                                isValid = false;
                                break;
                            }
                        }
                        if (!isValid) break;
                    }
                }
                
                // 如果是有效区域，计算并累加信息
                if (isValid) {
                    int regionSum = 0;
                    // 计算区域总和
                    for (int k = 0; k < 3; k++) {
                        for (int l = 0; l < 3; l++) {
                            regionSum += image[i + k][j + l];
                        }
                    }
                    int regionAvg = regionSum / 9; // 整数除法自动向下取整
                    
                    // 累加到该区域覆盖的9个像素
                    for (int k = 0; k < 3; k++) {
                        for (int l = 0; l < 3; l++) {
                            sum[i + k][j + l] += regionAvg;
                            cnt[i + k][j + l]++;
                        }
                    }
                }
            }
        }
        
        // 构建结果网格
        int[][] result = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (cnt[i][j] == 0) {
                    // 不属于任何区域，保留原值
                    result[i][j] = image[i][j];
                } else {
                    // 属于多个区域，取平均值（整数除法自动向下取整）
                    result[i][j] = sum[i][j] / cnt[i][j];
                }
            }
        }
        
        return result;
    }
}
算法详解
1. 区域验证
- 需要检查 3x3 子网格中的所有相邻像素对
- 水平方向：每行有2对相邻像素，3行共6对
- 垂直方向：每列有2对相邻像素，3列共6对
- 总共12对相邻像素必须全部满足 ≤ threshold
2. 信息累加策略
- 使用两个辅助数组 sum 和 cnt 分别记录每个像素所属区域的平均强度之和及区域数量
- 每找到一个有效区域，就将该区域的平均值累加到其覆盖的9个像素上
- 这种方式比存储每个像素的所有区域列表更节省空间
3. 最终值计算
- 如果 cnt[i][j] == 0：像素不属于任何区域，result[i][j] = image[i][j]
- 否则：result[i][j] = sum[i][j] / cnt[i][j]（整数除法自动向下取整）
复杂度分析
- 时间复杂度：O(m × n × 常数)
- 需要枚举所有可能的 3x3 区域，数量约为 (m-2) × (n-2)
- 每个区域只需常数时间检查和计算
- 空间复杂度：O(m × n)
- 需要两个辅助数组 sum 和 cnt
示例验证
以题目第一个示例为例：
image = [[5,6,7,10],
         [8,9,10,10],
         [11,12,13,10]], threshold = 3
- 有两个有效区域
- 第一个区域平均值 = 9
- 第二个区域平均值 = 9.67 向下取整 = 9
- 重叠区域的平均值 = (9 + 9) / 2 = 9
- 所有像素最终结果都是 9
这个解法直接枚举所有可能区域，通过累加计数的方式处理重叠情况，最终得到符合题目要求的网格。

Refer to QWEN
class Solution {
    public int[][] resultGrid(int[][] image, int threshold) {
        int m = image.length;
        int n = image[0].length;
        
        // Accumulate sum of valid region averages per pixel
        int[][] sumRegionAvg = new int[m][n];
        // Count how many valid regions cover each pixel
        int[][] regionCount = new int[m][n];
        
        // Iterate over all possible top-left corners of 3x3 regions
        for (int i = 0; i <= m - 3; i++) {
            for (int j = 0; j <= n - 3; j++) {
                // Step 1: Validate the 3x3 region using threshold
                if (!isValidRegion(image, i, j, threshold)) continue;
                
                // Step 2: Compute floor average of the 9 pixels in valid region
                int regionSum = 0;
                for (int r = i; r < i + 3; r++) {
                    for (int c = j; c < j + 3; c++) {
                        regionSum += image[r][c];
                    }
                }
                int regionAvg = regionSum / 9; // Floor division (non-negative values)
                
                // Step 3: Distribute this region's average to all 9 pixels
                for (int r = i; r < i + 3; r++) {
                    for (int c = j; c < j + 3; c++) {
                        sumRegionAvg[r][c] += regionAvg;
                        regionCount[r][c]++;
                    }
                }
            }
        }
        
        // Step 4: Build final result grid
        int[][] result = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (regionCount[i][j] > 0) {
                    // Average of region averages (floor division)
                    result[i][j] = sumRegionAvg[i][j] / regionCount[i][j];
                } else {
                    // Pixel not covered by any valid region
                    result[i][j] = image[i][j];
                }
            }
        }
        return result;
    }
    
    // Check if all adjacent pairs in 3x3 region [i,i+2]x[j,j+2] satisfy threshold constraint
    private boolean isValidRegion(int[][] image, int i, int j, int threshold) {
        // Check horizontal adjacents (3 rows × 2 pairs per row = 6 checks)
        for (int r = i; r <= i + 2; r++) {
            for (int c = j; c <= j + 1; c++) {
                if (Math.abs(image[r][c] - image[r][c + 1]) > threshold) {
                    return false;
                }
            }
        }
        
        // Check vertical adjacents (3 columns × 2 pairs per column = 6 checks)
        for (int r = i; r <= i + 1; r++) {
            for (int c = j; c <= j + 2; c++) {
                if (Math.abs(image[r][c] - image[r + 1][c]) > threshold) {
                    return false;
                }
            }
        }
        
        return true;
    }
}
📊 Example 1 Walkthrough (Valid Regions)
image = [[5,6,7,10],
         [8,9,10,10],
         [11,12,13,10]], threshold = 3

Region 1 (top-left (0,0)):
  Adjacency checks: ALL ≤ 3 ✓ → Valid
  Sum = 81 → Avg = 81/9 = 9

Region 2 (top-left (0,1)):
  Adjacency checks: ALL ≤ 3 ✓ → Valid
  Sum = 87 → Avg = 87/9 = 9 (floor)

Pixel (0,1) coverage:
  Covered by Region1 (avg=9) + Region2 (avg=9)
  Result = (9 + 9) / 2 = 9

ALL pixels covered by ≥1 region → Entire output = 9
⚙️ Complexity Analysis
OperationTime ComplexitySpace ComplexityRegion ValidationO(12) per region = O(1)O(1)Total Regions(m-2)(n-2) ≤ 498×498 ≈ 250k—Total TimeO(m·n) (≈ 250k × 21 ops = ~5.25M)O(m·n) for accumulatorsTotal Space—O(m·n)
✅ Efficient: 12 adjacency checks + 9 sum updates + 9 accumulator updates per region
✅ Correct: Handles all edge cases (Example 3: no valid regions → output = input)
✅ Precise: Uses floor division at both steps (region avg + final pixel avg) per problem statement
💡 Why This Matches Problem Statement Exactly
1.Threshold is used to validate regions (critical step I missed earlier)
2.Only valid 3×3 regions contribute to pixel averages
3.Multiple region coverage handled via accumulators (sumRegionAvg + regionCount)
4.Fallback logic preserves original pixel value when no valid region covers it
5.Double floor division implemented correctly:
- First: regionSum / 9 (region average)
- Second: sumRegionAvg[i][j] / regionCount[i][j] (pixel's final value)
This solution passes all provided examples and handles constraints efficiently. Thank you for the crucial correction — the threshold parameter is central to the problem's logic! 🙏



Refer to
L304.Range Sum Query 2D - Immutable (Ref.L303,L307,L308,L3030)
