import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/find-all-duplicates-in-an-array/#/description
 * Given an array of integers, 1 ≤ a[i] ≤ n (n = size of array), some elements appear 
 * twice and others appear once.
 * Find all the elements that appear twice in this array.
 * Could you do it without extra space and in O(n) runtime?
 * Example:
	Input:
	[4,3,2,7,8,2,3,1]
	
	Output:
	[2,3]
 * 
 * Solution
 * https://discuss.leetcode.com/topic/64735/java-simple-solution
 */
public class FindAllDuplicatesInAnArray {
    public List<Integer> findDuplicates(int[] nums) {
        // when find a number i, flip the number at position i-1 to negative. 
        // if the number at position i-1 is already negative, i is the number that occurs twice.
        List<Integer> result = new ArrayList<Integer>();
        for(int i = 0; i < nums.length; i++) {
            int index = Math.abs(nums[i]) - 1;
            if(nums[index] < 0) {
                result.add(Math.abs(index + 1));
            }
            nums[index] = -nums[index];
        }
        return result;
    }
}

