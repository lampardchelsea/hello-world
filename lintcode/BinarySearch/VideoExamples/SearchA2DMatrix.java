/**
 * Refer to
 * http://www.lintcode.com/en/problem/search-a-2d-matrix/
 * Write an efficient algorithm that searches for a value in an m x n matrix.
 * This matrix has the following properties:
    Integers in each row are sorted from left to right.
    The first integer of each row is greater than the last integer of the previous row.
 * Have you met this question in a real interview?
    Example
    Consider the following matrix:
    [
        [1, 3, 5, 7],
        [10, 11, 16, 20],
        [23, 30, 34, 50]
    ]
 * Given target = 3, return true.
 *
 * Solution
 * Refer to
 * http://blog.leanote.com/post/westcode/%5B%E5%88%B7%E9%A2%98%E7%AC%94%E8%AE%B0%5D-LeetCode-240-Search-a-2D-Matrix-II
 * 思路
 * 这题的最初想法是用 binary search，这种方法肯定也是可行的，但是没有完全利用到这个 matrix 的两个特性。
 * 先说说 binary search
 * 这题说每行从右到左都是排好序的，那么我们就可以排除掉那些超出目标范围的行，然后对剩下的每行进行 binary search 就可以了。
 * Divide and Conqure
 * 这题 divide and conqure 的做法非常有意思，我是看了九章的小视频学会的，但是真是非常机智，我觉得临场写的话除非很擅长这种智力题不然是很难想到的。。
 * 方法的实现就是利用了这个 matrix 两个特性，即每行每列都是递增的。
 * 假设我们从最左下角的点开始看，这个点就是当前列的最大值，还有当前行的最小值，那么如果这个点比 target 要大，那么我们是不是就可以把整行都给排除了？
 * 如果这个点比 target 要小，我们可以把整个列都排除，所以我们就可以用一个 while 循环，来从左下角开始比较，直到我们目标找到或者
 * 整个 matrix 都排除完为止。
 * 复杂度分析
 * 时间 n: height, m: width
 * binary search: O(nlogm), O(log(nm))
    多次 binary search每次 search 是logm, 总共可能做 n 次 search
    单次 binary search search 总共n*m个 element
 * Divide & Conqure O(m+n)
 * 如果整个 matrix 都要排除，那么我们就是走完所有的 width 还有走完所有的 height，每次做 O(1)
 * 空间 O(1)
 * 没有使用额外空间
 * 总结
 * 这题一开始没想到 divide and conquere 做法，但是真是非常有意思。 还是需要多练习这种智力题，增加经验。
 * 
 * 
*/
public class Solution {
    /**
     * @param matrix, a list of lists of integers
     * @param target, an integer
     * @return a boolean, indicate whether matrix contains target
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        // Check null and empty case
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int height = matrix.length;
        int width = matrix[0].length;
        int start = 0;
        int end = height * width - 1;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            int rowNum = mid / width;
            int colNum = mid % width;
            if(matrix[rowNum][colNum] == target) {
                return true;
            } else if(matrix[rowNum][colNum] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }
        if(matrix[start / width][start % width] == target || 
           matrix[end / width][end % width] == target) {
           return true; 
        }
        return false;
    }

    public boolean searchMatrix_Divide_Conqure(int[][] matrix, int target) {
        // Check null and empty case
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int height = matrix.length;
        int width = matrix[0].length;
        int col = 0;
        int row = matrix.length - 1;
        // Divide and Conqure
        // 这题 divide and conqure的法非常有意思，我是看了九章的小视频学会的，但是真是非常机智，我觉得临场写的话除非
        // 很擅长这种智力题不然是很难想到的。。 方法的实现就是利用了这个 matrix 两个特性，即每行每列都是递增的。
        // 假设我们从最左下角的点开始看，这个点就是当前列的最大值，还有当前行的最小值，那么如果这个点比 
        // target要大，那么我们是不是就可以把整行都给排除了？
        // 如果这个点比 target 要小，我们可以把整个列都排除，所以我们就可以用一个 while 
        // 循环，来从左下角开始比较，直到我们目标找到或者整个 matrix 都排除完为止。
        while(col < width && row >= 0) {
            if(matrix[row][col] == target) {
                return true;
            } else if(matrix[row][col] < target) {
                col++;
            } else {
                row--;
            }
        }
        return false;
    }

    
}
