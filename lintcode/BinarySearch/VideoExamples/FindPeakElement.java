/**
 * Refer to
 * http://www.lintcode.com/en/problem/find-peak-element/
 * There is an integer array which has the following features:
    The numbers in adjacent positions are different.
    A[0] < A[1] && A[A.length - 2] > A[A.length - 1].
 * We define a position P is a peek if:
 *  A[P] > A[P-1] && A[P] > A[P+1]
 * Find a peak element in this array. Return the index of the peak.
 * Notice
 * The array may contains multiple peeks, find any of them.
*/
class Solution {
    /**
     * @param A: An integers array.
     * @return: return any of peek positions.
     */
    public int findPeak(int[] A) {
        // Check null and emtpy case
        if(A == null || A.length == 0) {
            return -1;
        }
        int start = 0;
        int end = A.length - 1;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            // No target to compare with, instead
            // we use two ajacent items to judge
            // if current 'mid' item in rise zone
            // or decline zone, and process evaluate
            // as how to cut half original array,
            // and make sure the left half contains
            // the peak
            // Also, the given condition as
            // A[0] < A[1] && A[A.length - 2] > A[A.length - 1]
            // means the given array initially rise and finally
            // decline, must have at least one peak
            
            // Base on A[0] < A[1] and A[mid] < A[mid - 1],
            // current position is decline zone, cut decline
            // zone as (mid, end] by setting 'end = mid'
            if(A[mid] < A[mid - 1]) {
                end = mid;
            // Similar, current position is rise zeon, cut
            // rise zone as [start, mid) by setting 'start = mid'
            } else if(A[mid] < A[mid + 1]) {
                start = mid;
            } else {
                // Either 'start = mid' or 'end = mid' is fine
                start = mid;
                // end = mid;
            }
        }
        // Finally only left 'start' and 'end'
        if(A[start] < A[end]) {
            return end;
        } 
        return start;
    }
}
