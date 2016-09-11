public class Solution {
    public void rotate(int[] nums, int k) {
        int length = nums.length;
        
        for(int i = 0; i < k; i++) {
            int previous = nums[length - 1];
            for(int j = 0; j < length; j++) {
                int temp = nums[j];
                nums[j] = previous;
                previous = temp;
            }
        }
    }
}
