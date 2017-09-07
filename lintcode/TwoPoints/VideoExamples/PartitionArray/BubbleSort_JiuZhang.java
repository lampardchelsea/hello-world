/**
 * Refer to
 * http://www.lintcode.com/en/problem/sort-integers/
 * Given an integer array, sort it in ascending order. Use selection sort, bubble sort, 
   insertion sort or any O(n2) algorithm.

    Have you met this question in a real interview? Yes
    Example
    Given [3, 2, 1, 4, 5], return [1, 2, 3, 4, 5].
 *
 * Solution
 * http://www.jiuzhang.com/solution/sort-integers/
*/
public class Solution {
    /*
     * @param A: an integer array
     * @return: 
     */
    public void sortIntegers(int[] A) {
        if(A == null || A.length == 0) {
            return;
        }
        for(int i = 0; i < A.length; i++) {
            for(int j = i + 1; j < A.length; j++) {
                if(A[j] < A[i]) {
                    int temp = A[j];
                    A[j] = A[i];
                    A[i] = temp;
                }
            }
        }
    }
}
