/**
 * Refer to
 * Suppose a sorted array is rotated at some pivot unknown to you beforehand.
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * You are given a target value to search. If found in the array return its index, otherwise return -1.
 * You may assume no duplicate exists in the array.
 * Have you met this question in a real interview?
 * Example
   For [4, 5, 1, 2, 3] and target=1, return 2.
   For [4, 5, 1, 2, 3] and target=0, return -1.
 *
 * Solution
 * Refer to
 * http://www.jiuzhang.com/solutions/search-in-rotated-sorted-array/
*/

public class SearchInRotatedSortedArray {
    public int search(int[] A, int target) {
    	// Check null and empty case
        if(A == null || A.length == 0) {
            return -1;
        }
        int start = 0;
        int end = A.length - 1;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            if(A[mid] == target) {
                return mid;
            }
            // We need to separate into 2 cases
            // 1. 'mid' item happen in first rise zone (e.g 4 -- 7)
            // 2. 'mid' item happen in second rise zone (e.g 0 -- 2)
            // First rise zone
            if(A[mid] > A[start]) {
                if(A[start] <= target && target <= A[mid]) {
                    end = mid;
                } else {
                    start = mid;
                }
            // Second rise zone
            } else {
                if(A[mid] <= target && target <= A[end]) {
                    start = mid;
                } else {
                    end = mid;
                }
            }
        }
        if(A[start] == target) {
            return start;
        }
        if(A[end] == target) {
            return end;
        }
        return -1;
    }
    
    public static void main(String[] args) {
    	//int[] A = {1001,10001,10007,1,10,101,201};
    	int[] A = {0,1,2,-10,-9,-8,-7,-6,-5,-4,-3,-2,-1};
    	//int target = 10001;
    	int target = -9;
    	SearchInRotatedSortedArray s = new SearchInRotatedSortedArray();
    	int result = s.search(A, target);
    	System.out.println(result);
    }
    
}

