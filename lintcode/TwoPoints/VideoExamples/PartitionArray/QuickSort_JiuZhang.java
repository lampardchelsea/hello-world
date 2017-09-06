/**
 * Refer to
 * http://www.lintcode.com/en/problem/sort-integers-ii/
 * Given an integer array, sort it in ascending order. Use quick sort, 
   merge sort, heap sort or any O(nlogn) algorithm.
    Have you met this question in a real interview? Yes
    Example
    Given [3, 2, 1, 4, 5], return [1, 2, 3, 4, 5].
 *
 * Solution
 * https://www.jiuzhang.com/solutions/quick-sort/
*/
public class Solution {
    /**
     * @param A an integer array
     * @return void
     */
    public void sortIntegers2(int[] A) {
        quickSort(A, 0, A.length - 1);
    }
    
    private void quickSort(int[] A, int start, int end) {
        if(start >= end) {
            return;
        }
        // Initialize new reference as 'left' and 'right' for current 
        // and next 'quickSort' recursion
        int left = start;
        int right = end;
        // key point 1: pivot is the value, not the index
        // and only call finding 'pivot' once time when
        // calling quickSort
        int mid = left + (right - left) / 2;
        int pivot = A[mid];
        // key point 2: every time you compare left & right, it should be 
        // left <= right not left < right
        while(left <= right) {
            // key point 3: A[left] < pivot not A[left] <= pivot
            while(left <= right && A[left] < pivot) {
                left++;
            }
            // key point 3: A[right] > pivot not A[right] >= pivot
            while(left <= right && A[right] > pivot) {
                right--;
            }
            if(left <= right) {
                int temp = A[left];
                A[left] = A[right];
                A[right] = temp;
                left++;
                right--;
            }
        }
        // Start new recursion for quickSort based on updated new start / end
        // reference as 'left' and 'right', original 'start' and new updated 
        // end 'right' as a pair, original 'end' and new updated start 'left'
        // as a pair
        quickSort(A, start, right);
        quickSort(A, left, end);
    }
}
