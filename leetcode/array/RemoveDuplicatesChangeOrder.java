/**
 * Given a sorted array, remove the duplicates in place such that each element appear only once 
 * and return the new length.
 * Do not allocate extra space for another array, you must do this in place with constant memory.
 * For example,
 * Given input array nums = [1,1,2],
 * Your function should return length = 2, with the first two elements of nums being 1 and 2 
 * respectively. It doesn't matter what you leave beyond the new length.
 * 
 * But this solution which use exchange element violate the law as "with the first two elements 
 * of nums being 1 and 2 respectively", because the order already changed, even it will successfully
 * remove the duplicates.
*/
public class Solution {
  public int removeDuplicates(int[] nums) {
    int length = nums.length;
    int i = 0;
    int j;
    while(i < length) {
      // Cannot set j = 1 initially, only need to set i = 0 initially, and
      // j's value should based on i's change, the relation is j = i + 1,
      // each time reset j's value in outside while loop to (i + 1).
      // This case can be find by input as [1,2,2]
    	j = i + 1;
      while(j < length) {
        if(nums[i] == nums[j]) {
          // In if condition exchange the current element with the last one,
          // and decrease the length to discard the last one, which means
          // remove the duplicate element.
          nums[j] = nums[length - 1];
          length--;
        } else {
          // Only after check the exchanged element not duplicate with current
          // element we can increase the index, otherwise keep same to compare
          // e.g input as [1,1,1]
          j++;   
        }
      }
      i++;
    }
    return length;
  }
}
