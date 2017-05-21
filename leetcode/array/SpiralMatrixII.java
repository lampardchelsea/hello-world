/**
 * Refer to
 * https://leetcode.com/problems/spiral-matrix-ii/#/description
 * Given an integer n, generate a square matrix filled with elements from 1 to n2 in spiral order.
	For example,
	Given n = 3,
	You should return the following matrix:
	
	[
	 [ 1, 2, 3 ],
	 [ 8, 9, 4 ],
	 [ 7, 6, 5 ]
	]
 *
 * Solution
 * https://segmentfault.com/a/1190000003817711
 * 顺序添加法
 * 复杂度
 * 时间 O(NM) 空间 O(1)
 * 思路
 * 本题就是按照螺旋的顺序把数字依次塞进去，我们可以维护上下左右边界的四个变量，一圈一圈往里面添加。
 * 最后要注意的是，如果n是奇数，要把中心那个点算上。
 */
public class SpiralMatrixII {
	public int[][] generateMatrix(int n) {
        int[][] result = new int[n][n];
        int left = 0, right = n - 1, bottom = n - 1, top = 0;
        int num = 1;
        while(left < right && top < bottom) {
        	// Add first row of current round
        	for(int i = left; i < right; i++) {
        		result[top][i] = num++;
        	}
        	// Add last column of current round
        	for(int i = top; i < bottom; i++) {
        		result[i][right] = num++;
         	}
        	// Add last row of current round
        	for(int i = right; i > left; i--) {
        		result[bottom][i] = num++;
        	}
        	// Add first column of current round
        	for(int i = bottom; i > top; i--) {
        		result[i][left] = num++;
        	}
        	// Prepare for possible second round
        	top++;
        	bottom--;
        	left++;
        	right--;
        }
        // Important: If given odd number, then add center point
        if(n % 2 == 1) {
        	result[n / 2][n / 2] = num;
        }
        return result;
    }
	 
	public static void main(String[] args) {
		int n = 3;
		SpiralMatrixII s = new SpiralMatrixII();
		int[][] result = s.generateMatrix(n);
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				System.out.println(result[i][j]);
			}
		}
	}
}
