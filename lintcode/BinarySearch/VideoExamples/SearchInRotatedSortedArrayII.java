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
 * 在这种情况下是无法使用二分法的，复杂度是O(n) ??? (这里说的无法使用并不完全恰当，实际情况是依然可以使用二分法，二分法的局限在于对
 * 有序数组进行搜索，但是问题在于像[1,1,1,1... 1] 这样每一项都相等的数组由于总是进入A[mid] == A[start]的分支，所以只能index++，
 * 这样一来就有问题了，在稳定使用二分法的最坏情况下比如[1,1,1,... 1]里面没有0却要找一个0，时间复杂度依然是O(logn)， 但这个
 * A[mid] == A[start]的分支却不是二分法，无法一次砍掉一半，所以导致了时间复杂度在此情况的最坏情况下变为O(n))
 ------------------------------------------------------------------------------------------------------------------
 * ------> ，结论是依然可以用二分法
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
                // Important: Worst case of this branch, [1,1,1,.....1] all
                // elements are same, only can increase/decline by 1 each
                // time, not like binary search cut half, so time complexity
                // not stable as O(logn) but may max to O(n)
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





Attempt 1:2022-09-15 (180min, too long to come up with how to break out the deadlock when duplicates happen and cause target happen on both left, right interval)

class Solution { 
    public boolean search(int[] nums, int target) { 
        int lo = 0;  
        int hi = nums.length - 1;  
        while(lo <= hi) {  
            int mid = lo + (hi - lo) / 2;  
            if(nums[mid] == target) {  
                return true;  
            } 
            // A bit change by separating mus[mid] >= nums[lo] into two cases: 
            // Case 1.mus[mid] > nums[lo] and Case 2.mus[mid] == nums[lo] 
            // Case 1:remained original work 
            // Case 2:specifically for dupllicate elements 
            // The duplicate numbers impact which side (left or right interval) we should 
            // continue search, because if no duplicate numbers, when deal with nums[mid]  
            // and 'target', the comparison will only result into one side, because all  
            // the values are unique, 'target' will only occur once at either left or  
            // right interval, but if have duplicate numbers, 'target' may have multiple 
            // occurrences, e.g in [1,1,3] and [1,3,1,1], in both cases we have  
            // nums[mid] == nums[lo], but target = 3 can happen on both left interval  
            // (e.g [1,3,1,1]) and right interval (e.g [1,1,3]), to break the deadlock  
            // there is a simple way, just looply increase the 'lo' pointer ('lo++') until 
            // nums[mid] != nums[lo], the purpose behind, because to break the deadlock  
            // and find a potential binary search applicable interval, we have no option  
            // but to move to next search space iteratively 
             
            // If left interval of mid index as nums[lo, mid) is monotonically increasing,   
            // means the pivot point is on the right interval of mid index as mid(mid, hi] 
            if(nums[mid] > nums[lo]) {  
                // If 'target' is on left monotonic interval, remove right interval by  
                // using 'hi = mid - 1', otherwise 'target' is on right interval, even   
                // right interval is not monotonically increasing interval for now,   
                // we still should remove left interval by using 'lo = mid + 1' because   
                // left interval won't involve in further calculation since 'target' on   
                // right interval, the further calculation will recursively use   
                // 'nums[mid] >= nums[lo]' to find monotonic sub-interval based on 
                // current right interval 
                if(nums[mid] >= target && target >= nums[lo]) {  
                    hi = mid - 1; 
                } else {  
                    lo = mid + 1; 
                }  
            // If right interval of mid index as nums(mid, hi] is monotonically increasing,  
            // means the pivot point is on the left interval of mid index as mid[lo, mid)  
            } else if(nums[mid] < nums[lo]) {  
                if(nums[mid] <= target && target <= nums[hi]) {  
                    lo = mid + 1;  
                } else {  
                    hi = mid - 1;  
                }  
            } else { 
                // Duplicate caused deadlock break out by increase lower boundary iteratively 
                // to find a potential new interval applicable for binary search, O(N) time  
                // complexity in this case 
                lo++; 
            } 
        }  
        return false; 
    } 
}

Space Complexity: O(1)  
Time Complexity: O(N) worst case, O(logN) best case, where N is the length of the input array.
Worst case: This happens when all the elements are the same and we search for some different element. At each step, we will only be able to reduce the search space by 1 since arr[mid] equals arr[start] and it's not possible to decide the relative position of target from arr[mid]. Example: [1, 1, 1, 1, 1, 1, 1], target = 2. 
Best case: This happens when all the elements are distinct. At each step, we will be able to divide our search space into half just like a normal binary search.
This also answers the following follow-up question: 
Would this (having duplicate elements) affect the run-time complexity? How and why? 
As we can see, by having duplicate elements in the array, we often miss the opportunity to apply binary search in certain search spaces. Hence, we get O(N) worst case (with duplicates) vs O(logN) best case complexity (without duplicates).
