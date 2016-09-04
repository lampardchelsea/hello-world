/**
 * Given an array of size n, find the majority element. The majority element is the element that 
 * appears more than ⌊ n/2 ⌋ times. You may assume that the array is non-empty and the majority
 * element always exist in the array.
 * 
 * If not observe condition as this element appears more than half times and recognize it must
 * exist on the central of sorted array. We can scan once to find the longest consecutive substrings
*/
public class Solution {
    public int majorityElement(int[] nums) {
        int threshold = (int)Math.floor(nums.length / 2);
        boolean consecutive = false;
        int count = 0;
        
        Arrays.sort(nums);
        
        // Record previous array element value
        int previous = nums[0];
        int result = previous;
                
        for(int i = 1; i < nums.length; i++) {
            if(previous != nums[i]) {
                // Replace the record element with current value 
                previous = nums[i];
                count = 0;
                consecutive = false;
            } else {
                count++;
                consecutive = true;
            }
            
            if(consecutive && count >= threshold) {
                result = nums[i];
            }
        }
        
        return result;
    }
}
