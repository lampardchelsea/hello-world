/**
 * Refer to
 * https://leetcode.com/problems/rotate-image/#/description
 * You are given an n x n 2D matrix representing an image.
 * Rotate the image by 90 degrees (clockwise).
 * Follow up:
 * Could you do this in-place?
 * 
 * Solution
 * https://discuss.leetcode.com/topic/6796/a-common-method-to-rotate-the-image
 * 
 * here give a common method to solve the image rotation problems.
	
	 * clockwise rotate
	 * first reverse up to down, then swap the symmetry 
	 * 1 2 3     7 8 9     7 4 1
	 * 4 5 6  => 4 5 6  => 8 5 2
	 * 7 8 9     1 2 3     9 6 3
	 * 
	void rotate(vector<vector<int> > &matrix) {
	    reverse(matrix.begin(), matrix.end());
	    for (int i = 0; i < matrix.size(); ++i) {
	        for (int j = i + 1; j < matrix[i].size(); ++j)
	            swap(matrix[i][j], matrix[j][i]);
	    }
	}
	
	
	 * anticlockwise rotate
	 * first reverse left to right, then swap the symmetry
	 * 1 2 3     3 2 1     3 6 9
	 * 4 5 6  => 6 5 4  => 2 5 8
	 * 7 8 9     9 8 7     1 4 7
	 * 
	void anti_rotate(vector<vector<int> > &matrix) {
	    for (auto vi : matrix) reverse(vi.begin(), vi.end());
	    for (int i = 0; i < matrix.size(); ++i) {
	        for (int j = i + 1; j < matrix[i].size(); ++j)
	            swap(matrix[i][j], matrix[j][i]);
	    }
	}

 * 
 * 
 * https://discuss.leetcode.com/topic/6796/a-common-method-to-rotate-the-image/21
 * 
 */
public class RotateImage {
    public void rotate(int[][] matrix) {
        // First reverse up to down
        int start = 0;
        int end = matrix.length - 1;
        while(start < end) {
            int[] temp = matrix[start];
            matrix[start] = matrix[end];
            matrix[end] = temp;
            start++;
            end--;
        }
        // Then swap symmetry
        for(int i = 0; i < matrix.length; i++) {
        	// Since matrix is squal, no need to use matrix[i].length,
        	// we can directly use matrix.length
            for(int j = i + 1; j < matrix[i].length; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }
}

