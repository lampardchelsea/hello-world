import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * https://zhengyang2015.gitbooks.io/lintcode/find_peak_element_ii_390.html
 * There is an integer matrix which has the following features:

	The numbers in adjacent positions are different.
	The matrix has n rows and m columns.
	For all i < m, A[0][i] < A[1][i] && A[n - 2][i] > A[n - 1][i].
	For all j < n, A[j][0] < A[j][1] && A[j][m - 2] > A[j][m - 1].
	We define a position P is a peek 
	if A[j][i] > A[j+1][i] && A[j][i] > A[j-1][i] && A[j][i] > A[j][i+1] && A[j][i] > A[j][i-1].
	
	Find a peak element in this matrix. Return the index of the peak.
	
	Example
	Given a matrix:
	
	[
	  [1 ,2 ,3 ,6 ,5],
	  [16,41,23,22,6],
	  [15,17,24,21,7],
	  [14,18,19,20,10],
	  [13,14,11,10,9]
	]
	return index of 41 (which is [1,1]) or index of 24 (which is [2,2])
	
	Note
	The matrix may contains multiple peeks, find any of them.

	Challenge
	Solve it in O(n+m) time.
	If you come up with an algorithm that you thought it is O(n log m) or O(m log n), 
	can you prove it is actually O(n+m) or propose a similar but O(n+m) algorithm?
 * 
 * 
 * 
 * Solution
 * https://zhengyang2015.gitbooks.io/lintcode/find_peak_element_ii_390.html
 * http://blog.csdn.net/jmspan/article/details/51724388
 * http://www.tangjikai.com/algorithms/lintcode-390-find-peak-element-ii
 * http://courses.csail.mit.edu/6.006/spring11/lectures/lec02.pdf
 * 
 * Refer to FindPeakElement.java
 * https://github.com/lampardchelsea/hello-world/blob/master/lintcode/BinarySearch/VideoExamples/FindPeakElement.java
 */
public class FindPeakElementII {
	/**
     * @param A: An integer matrix
     * @return: The index of the peak
     */
	
	// Solution 1: 爬梯法/爬山法/夹逼法
	private int[] dx = {0, 1, -1, 0};
	private int[] dy = {-1, 0, 0, 1};
    public List<Integer> findPeakII(int[][] A) {
    	int i = 0, j = 0;
    	// Should not initial boolean[][] visited, 
    	// because certain position not a peak value
    	// in current around cases, maybe a peak
    	// value in next around cases, which require
    	// visit again
        while(true) {
        	int max = A[i][j];
        	// max_i, max_j always update to newest i, j
        	int max_i = i;
        	int max_j = j;
        	for(int k = 0; k < 4; k++) {
        		int next_i = i + dx[k];
        		int next_j = j + dy[k];
        		if(next_i >= 0 && next_i < A.length && next_j >= 0 && next_j < A[0].length) {
        			//max = Math.max(max, A[next_i][next_j]);
        			// Update max_i, max_j when new position value > previous max value
        			if(A[next_i][next_j] > max) {
        				max = A[next_i][next_j];
            			max_i = next_i;
            			max_j = next_j;
        			}
        		}
        	}
        	// Break out condition, if no more updates on max_i, max_j, which means
        	// no more bigger peak value found in 4 directions, so max_i will keep
        	// as i, same for max_j will keep as j, break out
        	if(max_i == i && max_j == j) {
        		List<Integer> result = new ArrayList<Integer>();
        		result.add(max_i);
        		result.add(max_j);
        		return result;
        	}
        	// Update i, j to current peak position i, j
        	i = max_i;
        	j = max_j;
        }
    }
    
    
    
    // Solution 2: Time Complexity O(mlogn) / Space O(1)
    // Refer to
    // http://www.tangjikai.com/algorithms/lintcode-390-find-peak-element-ii
    /**
     Solution 1: Binary search for row
		Steps:
		1. Get the maximum point in the center row, A[mid][col].
		2. 
		If A[mid][col] < A[mid + 1][col], cut off the top half(l = mid + 1)
		                     A[mid][col] < A[mid + 1][col]: the bottom half mush contains a peak element
		If A[mid][col] < A[mid - 1][col], cut off the top half(r = mid - 1)
		                     A[mid][col] < A[mid - 1][col]: the top half mush contains a peak element
		Else, return A[mid][col]
		                     A[mid][col] > A[mid-1][col] and > A[mid+1][col] and 
		                     it is the largest in row => peak element
     */
    public List<Integer> findPeakII_BinarySearch(int[][] A) {
    	List<Integer> result = new ArrayList<Integer>();
    	if(A == null || A.length == 0 || A[0].length == 0) {
    		return result;
    	}
    	int start = 1;
    	int end = A.length - 2;
    	while(start + 1 < end) {
    		int mid = start + (end - start) / 2;
    		int col = findColumn(mid, A);
    		if(A[mid][col] < A[mid - 1][col]) {
    			end = mid;
    		} else if(A[mid][col] < A[mid + 1][col]) {
    			start = mid;
    		} else {
    			result.add(mid);
    			result.add(col);
    			break;
    		}
    	}
    	return result;
    }
    
    private int findColumn(int row, int[][] A) {
    	int max_col = 0;
    	for(int i = 0; i < A[row].length; i++) {
    		if(A[row][i] > A[row][max_col]) {
    			max_col = i;
    		}
    	}
    	return max_col;
    }
    
    
    
    
    // Solution 3:Time Complexity O(m + n) / Space O(1)
    // This solution is continuously optimization of Solution 2, which use binary search
    // on both dimensions
    // Refer to
    // https://zhengyang2015.gitbooks.io/lintcode/find_peak_element_ii_390.html
    // 和在数组中find peak element一样，对行和列分别进行二分查找。
    // 先对行进行二分搜索，对搜到的那一行元素再进行二分搜索寻找peak element
    // 对找到的element看上下行的同列元素，若相同则返回，若比上小则在上半部分行继续进行搜索，若比下小则在下半部分的行继续进行搜索
    // 
    // http://www.tangjikai.com/algorithms/lintcode-390-find-peak-element-ii --> Solution 3
    // Step 1: binary search for row
    // Step 2: binary search for column
    // Step 3: repeat Step 1 & 2 until finding peak.
    /**
     * Complexity:
	   O(m+n) time
	   O(1) space
	   Let T(m,n) as time complexity of finding peak element in an m*n array.
	   T(m,n) = T(m/2,n)+cn
              = T(m/2,n/2)+cn+c(m/2)
              = T(m/4,n/2)+cn+c(m/2)+c(n/2)
              = T(m/4,n/4)+cn+c(m/2)+c(n/2)+c(m/4)
              = ...
              = T(1,1) + cn(1+1/2+1/4+...+1/n) + cm(1/2+1/4+...+1/m)
              = c(2n+m) 
              = O(m+n)
     */
    public List<Integer> findPeakII_BinarySearch_2(int[][] A) {
    	List<Integer> result = new ArrayList<Integer>();
    	if(A == null || A.length == 0 || A[0].length == 0) {
    		return result;
    	}
    	// As given condition, the first and last row no need to search
    	// start from second row to second last row
    	int start = 1;
    	int end = A.length - 2;
    	// This binary search don't have given specific target
    	// to approach, so continuous narrow range (cut non-possible
    	// half each loop)
    	while(start + 1 < end) {
    		int mid = start + (end - start) / 2;
    		int col = findPeak(mid, A);
    		// Continue on upper half, A[mid][col] < A[mid - 1][col] means
    		// lower half tend to contain peak
    		if(A[mid][col] < A[mid - 1][col]) {
    			end = mid;
    		// Continue on lower half, A[mid][col] < A[mid + 1][col] means
            // upper half tend to contain peak
    		} else if(A[mid][col] < A[mid + 1][col]) {
    			start = mid;
    		} else {
    			result.add(mid);
    			result.add(col);
    			break;
    		}
    	}
    	return result;
    }
    
    
    // Standard binary search template from JiuZhang
    // Find peak in a given matrix row, exactly same as FindPeakElement.java
    // https://github.com/lampardchelsea/hello-world/blob/master/lintcode/BinarySearch/VideoExamples/FindPeakElement.java
    private int findPeak(int row, int[][] A) {
    	// As given condition the first and last column no need to search
    	// start from second column and end with second last column
    	int start_col = 1;
    	int end_col = A[0].length - 2;
    	while(start_col + 1 < end_col) {
    		int mid_col = start_col + (end_col - start_col) / 2;
    		if(A[row][mid_col] < A[row][mid_col - 1]) {
    			end_col = mid_col; // Not include '-1'
    		} else if(A[row][mid_col] < A[row][mid_col + 1]) {
    			start_col = mid_col; // Not include '+1'
    		} else {
    			return mid_col;
    		}
    	}
    	// Finally if after while loop end still not encounter
    	// A[row][mid_col] >= A[row][mid_col - 1] && A[row][mid_col] >= A[row][mid_col + 1]
    	// then drop to below two candidates: start_col & end_col (as they are while loop
    	// end condition, just compare these two cases)
    	if(A[row][start_col] > A[row][end_col]) {
    		return start_col;
    	} else {
    		return end_col;
    	}
    }
    
    public static void main(String[] args) {
    	FindPeakElementII f = new FindPeakElementII();
    	int[][] A = {
    	             	  {1 ,2 ,3 ,6 ,5},
    	            	  {16,41,23,22,6},
    	            	  {15,17,24,21,7},
    	            	  {14,18,19,20,10},
    	            	  {13,14,11,10,9}
    	            	 };
//    	List<Integer> result = f.findPeakII(A);
    	List<Integer> result = f.findPeakII_BinarySearch(A);
//    	List<Integer> result = f.findPeakII_BinarySearch_2(A);
    	for(Integer i : result) {
    		System.out.print(i + " ");
    	}
    }
}
