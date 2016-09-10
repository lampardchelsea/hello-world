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
