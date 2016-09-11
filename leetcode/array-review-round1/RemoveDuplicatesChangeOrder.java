public class Solution {
    public int removeDuplicates(int[] nums) {
        int length = nums.length;
        int i = 0;
        int j;
        
        while(i < length) {
            j = i + 1;
            while(j < length) {
                if(nums[i] == nums[j]) {
                    nums[j] = nums[length - 1];
                    length--;
                } else {
                    j++;
                }
            }
            i++;
        }
        
        return length;
    }
}
