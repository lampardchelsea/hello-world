/**
 * Refer to
 * https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/#/description
 * Given an array of integers that is already sorted in ascending order, find two numbers 
 * such that they add up to a specific target number.
 * The function twoSum should return indices of the two numbers such that they add up to the 
 * target, where index1 must be less than index2. Please note that your returned answers (both index1 and index2) are not zero-based.
 * You may assume that each input would have exactly one solution and you may not use the same element twice.
 * Input: numbers={2, 7, 11, 15}, target=9
 * Output: index1=1, index2=2 
 *
 * Solution:
 * https://discuss.leetcode.com/topic/6229/share-my-java-ac-solution
*/
public class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int lo = 0;
        int hi = numbers.length - 1;
        int[] result = new int[2];
        while(lo <= hi) {
            long temp = numbers[lo] + numbers[hi];
            if(temp > target) {
                hi--;
            } else if(temp < target) {
                lo++;
            } else {
                result[0] = lo + 1;
                result[1] = hi + 1;
                // Don't forget using break, test case = [2, 3, 4] and 6
                break;
            }
        }
        return result;
    }
}
