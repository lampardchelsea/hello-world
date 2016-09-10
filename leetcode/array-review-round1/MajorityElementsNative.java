/**
 * Compare with original one in array directory, though both the same way to calculate
 * longest consecutive value length, but we already sort the array, the "consecutive"
 * flag is not necessary.
 * 
 * public class Solution {
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
*/
public class Solution {
    public int majorityElement(int[] nums) {
        int length = nums.length;
        int count = 0;
        int threshold = (int)Math.floor(length / 2);
        Arrays.sort(nums);
        
        int previous = 0;
        for(int i = 0; i < length; i++) {
            if(nums[i] == previous) {
                count++;
            } else {
                previous = nums[i];
            }
            
            if(count >= threshold) {
                break;
            }
        }
        
        return previous;
    }
}
