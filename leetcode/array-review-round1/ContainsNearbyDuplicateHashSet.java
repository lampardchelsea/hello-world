public class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        int length = nums.length;
        boolean result = false;
        
        Set<Integer> set = new HashSet<Integer>();
        
        for(int i = 0; i < length; i++) {
            if(i > k) {
                set.remove(nums[i - k - 1]);
            }
            
            // set add method return false if set already
            // have this element
            if(!set.add(nums[i])) {
                result = true;
            }
        }
        
        return result;
    }
}
