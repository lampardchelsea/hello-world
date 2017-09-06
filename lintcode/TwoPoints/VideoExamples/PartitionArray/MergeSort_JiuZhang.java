/**
 * Refer to
 * http://www.lintcode.com/en/problem/sort-integers-ii/
 * Given an integer array, sort it in ascending order. Use quick sort, merge sort, 
   heap sort or any O(nlogn) algorithm.

    Have you met this question in a real interview? Yes
    Example
    Given [3, 2, 1, 4, 5], return [1, 2, 3, 4, 5].
 *
 * Solution
 * http://www.jiuzhang.com/solutions/merge-sort/
 * 
*/
public class Solution {
    /**
     * @param A an integer array
     * @return void
     */
    public void sortIntegers2(int[] A) {
        int[] temp = new int[A.length];
        mergeSort(A, 0, A.length - 1, temp);
    }
    
    private void mergeSort(int[] A, int start, int end, int[] temp) {
        // Don't forget the base case, same as Quick Sort
        if(start >= end) {
            return;
        }
        int mid = start + (end - start) / 2;
        mergeSort(A, start, mid, temp);
        mergeSort(A, mid + 1, end, temp);
        merge(A, start, mid, end, temp);
    }

    private void merge(int[] A, int start, int mid, int end, int[] temp) {
        int left = start;
        int right = mid + 1;
        int index = start;
        // Combination two conditions
        while(left <= mid && right <= end) {
            if(A[left] < A[right]) {
                temp[index++] = A[left++];
            } else {
                temp[index++] = A[right++];
            }
        }
        while(left <= mid) {
            temp[index++] = A[left++];
        }
        while(right <= end) {
            temp[index++] = A[right++];
        }
        for(index = start; index <= end; index++) {
            A[index] = temp[index];
        }
    }
}
