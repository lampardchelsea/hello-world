/**
 * Given an array of integers, every element appears twice except for one. Find that single one.
 * Note:
 * Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
*/
public class Solution {
    public int singleNumber(int[] nums) {
        int length = nums.length;
        int result = 0;
        Set<Integer> set = new HashSet<Integer>();
        
        for(int i = 0; i < length; i++) {
            if(!set.add(nums[i])) {
                set.remove(nums[i]);
            }
        }
        
        Iterator<Integer> it = set.iterator();
        if(it.hasNext()) {
            result = it.next();
        }
        
        return result;
    }
}
