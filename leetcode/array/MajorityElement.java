/**
* Given an array of size n, find the majority element. 
* The majority element is the element that appears more than ⌊ n/2 ⌋ times.
* You may assume that the array is non-empty and the majority element always exist in the array.
* 
* Analysis:
* As description, if an array element appears more than (int)Math.floor(nums.length / 2)
* times, it must also appear at the central of sorted array, because the consecutive
* length of this element must at least half length of the array, even start at the
* first or last element, its value should consecutive until the central of this array.
* We can set it as threshold, only need to return nums[threshold].
*/
public class Solution {
    public int majorityElement(int[] nums) {
        // Math.floor default return type is double, cast to integer
        int threshold = (int)Math.floor(nums.length / 2);
        // Sort the original array
        Arrays.sort(nums);
        // Return the central value
        return nums[threshold];
    }
}
