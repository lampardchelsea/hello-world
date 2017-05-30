/**
 * Refer to
 * https://leetcode.com/problems/next-greater-element-iii/#/description
 * Given a positive 32-bit integer n, you need to find the smallest 32-bit integer which 
 * has exactly the same digits existing in the integer n and is greater in value than n. 
 * If no such positive 32-bit integer exists, you need to return -1.
    Example 1:
    Input: 12
    Output: 21

    Example 2:
    Input: 21
    Output: -1
 * 
 * Solution
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/array/NextPermutation.java
 * https://discuss.leetcode.com/topic/86049/simple-java-solution-4ms-with-explanation/4
*/
public class Solution {
    public int nextGreaterElement(int n) {
        //String s = String.valueOf(n);
        char[] nums = (n + "").toCharArray();
        int i = nums.length - 2;
        while(i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        // Important: Different than 'Next Permutation', need to exactly 
        // greater in value of original input, so, if i < 0 which means
        // current value already the largest one must return '-1' as not
        // able to find a larger one than current input
        if(i < 0) {
            return -1;
        } else {
            int j = nums.length - 1;
            while(j > i && nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1, nums.length - 1);
        // Don't forget the requirement here, when encounter
        // NumberFormatExpection, that means when Integer.valueOf(str)
        // try to convert, it doesn't work because of some issue,
        // For case here, may because of some value out of
        // 32-bit Integer limitation
        // Refer to
        // https://stackoverflow.com/questions/13935167/java-lang-numberformatexception-for-input-string
        // Integer.parseInt throws a NumberFormatException if the passed string is 
        // not a valid representation of an integer. here you are trying to pass 
        // 2463025552 which is out of integer range. use long instead
        //    long phone = Long.parseLong(s2[1].trim() )
        // But here we don't need to use long, just handle the exception by
        // setting up return as '-1'
        try {
            return Integer.valueOf(String.valueOf(nums));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    public void swap(char[] nums, int i, int j) {
        char tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    
    public void reverse(char[] nums, int left, int right) {
        while(left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }
    
}
