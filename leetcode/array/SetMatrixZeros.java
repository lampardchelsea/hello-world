/**
 * Refer to
 * https://leetcode.com/problems/set-matrix-zeroes/#/description
 *  Given a m x n matrix, if an element is 0, set its entire row and column to 0. 
 *  Do it in place.
 *  
 *  click to show follow up.
 *  Follow up:
 *  Did you use extra space?
 *  A straight forward solution using O(mn) space is probably a bad idea.
 *  A simple improvement uses O(m + n) space, but still not the best solution.
 *  Could you devise a constant space solution?
 * 
 * Solution
 * https://discuss.leetcode.com/topic/15193/my-ac-java-o-1-solution-easy-to-read
 * https://segmentfault.com/a/1190000003747491
 * 新建矩阵法
 * 复杂度
 * 时间 O(NM) 空间 O(NM)
 * 思路
 * 最简单的方法就是建一个同样大小的矩阵，在原矩阵中遇到一个0，就将新矩阵的行和列设为0
 * 首行首列暂存法
 * 复杂度
 * 时间 O(NM) 空间 O(1)
 * 思路
 * 实际上，我们只需要直到哪些行，哪些列需要被置0就行了，最简单的方法就是建两个大小分别为M和N的数组，
 * 来记录哪些行哪些列应该被置0。那有没有可能不用额外空间呢？我们其实可以借用原矩阵的首行和首列来
 * 存储这个信息。这个方法的缺点在于，如果我们直接将0存入首行或首列来表示相应行和列要置0的话，我们
 * 很难判断首行或者首列自己是不是该置0。这里我们用两个boolean变量记录下首行和首列原本有没有0，
 * 然后在其他位置置完0后，再单独根据boolean变量来处理首行和首列，就避免了干扰的问题。
 */
public class SetMatrixZeroes {
    public void setZeroes(int[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        // Two boolean flags for whether first row & column
        // contains 0 or not, as we actually use first row
        // & column to record whether that row or column 
        // need to set to 0
        boolean firstRowZero = false;
        boolean firstColZero = false;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                if(i != 0 && j != 0 && matrix[i][j] == 0) {
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                } else if(matrix[i][j] == 0) {
                    if(i == 0) {
                        firstRowZero = true;
                    }
                    if(j == 0) {
                        firstColZero = true;
                    }
                }
            }
        }
        // Set all items besides first row and column to 0
        for(int i = 1; i < rows; i++) {
            for(int j = 1; j < columns; j++) {
                if(matrix[0][j] == 0 || matrix[i][0] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        // If necessary, set first row to 0
        if(firstRowZero) {
            for(int i = 0; i < columns; i++) {
                matrix[0][i] = 0;
            }    
        }
        // If necessary, set first column to 0
        if(firstColZero) {
            for(int i = 0; i < rows; i++) {
                matrix[i][0] = 0;
            }
        }
    }
    
    public static void main(String[] args) {
    	
    }
}

