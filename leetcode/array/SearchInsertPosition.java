/**
 * Refer to
 * https://leetcode.com/problems/search-insert-position/#/description
 * Given a sorted array and a target value, return the index if the target is found. 
 * If not, return the index where it would be if it were inserted in order.
 * You may assume no duplicates in the array.
    Here are few examples.
    [1,3,5,6], 5 → 2
    [1,3,5,6], 2 → 1
    [1,3,5,6], 7 → 4
    [1,3,5,6], 0 → 0
  * 
  * Solution
  * https://discuss.leetcode.com/topic/15955/c-o-logn-binary-search-that-handles-duplicate/2
*/
public class Solution {
    public int searchInsert(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;
        // Invariant: the desired index is between [low, high+1]
        while(lo <= hi) {
            int mid = lo + (hi - lo)/2;
            if(target > nums[mid]) {
                lo = mid + 1;
            } else if(target < nums[mid]) {
                hi = mid - 1;
            } else {
                return mid;
            }
        }
        // Refer to
        // https://discuss.leetcode.com/topic/15955/c-o-logn-binary-search-that-handles-duplicate/2
        // (1) At this point, low > high. That is, low >= high+1
        // (2) From the invariant, we know that the index is between [low, high+1], so low <= high+1. 
        //     Follwing from (1), now we know low == high+1.
        // (3) Following from (2), the index is between [low, high+1] = [low, low], which means that 
        //     low is the desired index. Therefore, we return low as the answer. You can also return 
        //     high+1 as the result, since low == high+1
        return lo;
    }
}
