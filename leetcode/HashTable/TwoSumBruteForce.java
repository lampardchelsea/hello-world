/**
 * The brute force approach is simple. Loop through each element xx and find if 
 * there is another value that equals to target - x
 * 
 * Complexity Analysis
 * Time complexity : O(n^2)
 * For each element, we try to find its complement by looping through the rest of array which takes O(n) time. 
 * Therefore, the time complexity is O(n^2)
 * Space complexity : O(1)
 */
public class Solution {
    public int[] twoSum(int[] nums, int target) {
        int length = nums.length;
        int[] result = new int[2];
        
        for(int i = 0; i < length; i++) {
            for(int j = i + 1; j < length; j++) {
                if(nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        
        return result;
    }
}
