/**
 * Refer to
 * http://www.lintcode.com/en/problem/search-in-rotated-sorted-array-ii/
 * Follow up for Search in Rotated Sorted Array:
 * What if duplicates are allowed?
 * Would this affect the run-time complexity? How and why?
 * Write a function to determine if a given target is in the array.
 * Have you met this question in a real interview?
 * Example
   Given [1, 1, 0, 1, 1, 1] and target = 0, return true.
   Given [1, 1, 1, 1, 1, 1] and target = 0, return false.
 *
 * Solution
 * https://www.kancloud.cn/kancloud/data-structure-and-algorithm-notes/72974
 * 仔细分析此题和之前一题的不同之处，前一题我们利用A[start] < A[mid]这一关键信息，而在此题中由于有重复元素的存在，
 * 在A[start] == A[mid]时无法确定有序数组，此时只能依次递增start/递减end以缩小搜索范围，时间复杂度最差变为O(n)。
 *
 * http://www.jiuzhang.com/solutions/search-in-rotated-sorted-array-ii/
 * 这个问题在面试中不会让实现完整程序
 * 只需要举出能够最坏情况的数据是 [1,1,1,1... 1] 里有一个0即可。
 * 在这种情况下是无法使用二分法的，复杂度是O(n) ??? (有误)
 ------------------------------------------------------------------------------------------------------------------
 * ------> 最坏情况下比如[1,1,1,... 1]里面没有0却要找一个0，时间复杂度依然是log(n)，结论是依然可以用二分法写，但是用for写也可以 
 ------------------------------------------------------------------------------------------------------------------
 * 因此写个for循环最坏也是O(n)，那就写个for循环就好了
 * 如果你觉得，不是每个情况都是最坏情况，你想用二分法解决不是最坏情况的情况，那你就写一个二分吧。
 * 反正面试考的不是你在这个题上会不会用二分法。这个题的考点是你想不想得到最坏情况。
     public boolean search(int[] A, int target) {
        for (int i = 0; i < A.length; i ++) {
            if (A[i] == target) {
                return true;
            }
        }
        return false;
    }

*/
public class Solution {
    /** 
     * param A : an integer ratated sorted array and duplicates are allowed
     * param target :  an integer to be search
     * return : a boolean 
     */
    public boolean search(int[] A, int target) {
        // Check null and empty case
        if(A == null || A.length == 0) {
            return false;
        }
        int start = 0;
        int end = A.length - 1;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            if(A[mid] == target) {
                return true;
            }
            // Case 1: Numbers between 'start' and 'mid' are sorted
            //         Move 'end' to 'mid' which cut second half
            // Note 1: Both 'A[mid] > A[start]' or 'A[mid] > A[end]' works,
            // as both represent 'mid' item happen in first rise zone
            // Note 2: Use 'else if' or 'if' here both works
            else if(A[mid] > A[start]) {
                if(A[start] <= target && target <= A[mid]) {
                    end = mid;
                } else {
                    start = mid;
                }
            // Case 2: Numbers between 'mid' and 'end' are sorted
            //         Move 'start' to 'mid' which cut first half
            } else if(A[mid] < A[start]) {
                if(A[mid] <= target && target <= A[end]) {
                    start = mid;
                } else {
                    end = mid;
                }
            // Case 3: Which is the tricky part, since we have duplicates
            //         when 'A[mid] == A[start]', we cannot directly return
            //         'mid' value, because you don't know if this is already
            //         the only one
            } else {
                start++;
                // If use A[mid] compare with 'end', change 'start++' to 'end--'
            }
        }
        if(A[start] == target || A[end] == target) {
            return true;
        }        
        return false;
    }
}
