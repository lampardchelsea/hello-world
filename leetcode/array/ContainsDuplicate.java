/**
 * Given an array of integers, find if the array contains any duplicates. 
 * Your function should return true if any value appears at least twice in the array, 
 * and it should return false if every element is distinct.
*/
public class Solution {
    public boolean containsDuplicate(int[] nums) {
        int length = nums.length;
        boolean result = false;
        Set<Integer> set = new HashSet<Integer>();
        
        for(int i = 0; i < length; i++) {
            if(!set.add(nums[i])) {
               result = true; 
            }
        }
        
        return result;
    }
}
