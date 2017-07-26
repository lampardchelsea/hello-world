/**
 * Refer to
 * http://www.lintcode.com/en/problem/search-for-a-range/
 * Given a sorted array of n integers, find the starting and ending position of a given target value.
 * If the target is not found in the array, return [-1, -1].
 * Have you met this question in a real interview?
 * Example
 * Given [5, 7, 7, 8, 8, 10] and target value 8,
 * return [3, 4].
*/
public class Solution {
    /** 
     *@param A : an integer sorted array
     *@param target :  an integer to be inserted
     *return : a list of length 2, [index1, index2]
     */
    public int[] searchRange(int[] A, int target) {
        // Check null and empty case
        if(A == null || A.length == 0) {
            return new int[] {-1, -1};
        }

        int index_1 = -1;
        int index_2 = -1;
        
        // Find first position of target
        int start = 0;
        int end = A.length - 1;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            if(A[mid] == target) {
                end = mid;
            } else if(A[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }
        
        // We must use 'if-else' in this problem,
        // since we don't return 'start' or 'end'
        // directly as template, but we can only
        // assign 1 possible value to result, so
        // 'if-else' will enable atomic value assign
        if(A[start] == target) {
            index_1 = start;
        } else if(A[end] == target) {
            index_1 = end;
        } 
        
        // Find last position of target
        // Don't forget to re-assign values as
        // we start a new search
        start = 0;
        end = A.length - 1;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            if(A[mid] == target) {
                start = mid;
            } else if(A[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }
        
        if(A[end] == target) {
            index_2 = end;
        } else if(A[start] == target) {
            index_2 = start;
        }
        
        return new int[] {index_1, index_2};
    }
}
