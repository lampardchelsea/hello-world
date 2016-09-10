/**
 * Given an array of integers and an integer k, find out whether there are two distinct indices i 
 * and j in the array such that nums[i] = nums[j] and the difference between i and j is at most k.
 * 
 * Analyze
 * The tricky part of this method is keep HashSet size as k, because if the distance two elements 
 * in array larger than k even they are same value make none sense, so if looply checking k elements
 * and find no same value elements, then when we check k + 1 element, we can remove the first
 * element, and set add method will return false if encounter same value put in, based on this
 * when i no larger than k and add method return false, that means we find a pair.
*/
public class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        int length = nums.length;
        Set<Integer> set = new HashSet<Integer>();
        
        for(int i = 0; i < length; i++) {
            if(i > k) {
                set.remove(nums[i - k - 1]);
            }
            
            if(!set.add(nums[i])) {
                return true;
            }
        }
        
        return false;
    }
}
