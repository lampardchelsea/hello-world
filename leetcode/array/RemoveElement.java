public class Solution {
    public int removeElement(int[] nums, int val) {
        int length = nums.length;
        int i = 0;
        int j = 0;
        while(j < length) {
            if(nums[j] == val) {
                j++;
                continue;
            } else {
                nums[i] = nums[j];
                i++;
                j++;
            }
        }
        return i;
    }
}
