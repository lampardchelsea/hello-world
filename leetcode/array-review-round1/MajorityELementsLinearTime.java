public class Solution {
    public int majorityElement(int[] nums) {
        int length = nums.length;
        int count = 0;
        int result = 0;
        for(int i = 0; i < length; i++) {
            if(count == 0 || nums[i] == result) {
                result = nums[i];
                count++;
            } else {
                count--;
            }
        }
        
        return result;
    }
}
