/**
 * Refer to
 * http://www.cnblogs.com/aprilyang/p/6701586.html
 * Given an array of integers, find how many unique pairs in the array such that their 
   sum is equal to a specific target number. Please return the number of pairs.
    Example
    Given nums = [1,1,2,45,46,46], target = 47
    return 2

    1 + 46 = 47
    2 + 45 = 47
 *
 * Solution
 * http://www.jiuzhang.com/solutions/two-sum-unique-pairs
 * 
*/
public class TwoSum {
    public int twoSumUniquePairs(int[] nums, int target) {
    	if(nums == null || nums.length < 2) {
            return 0;
        }
    	Arrays.sort(nums);
    	int start = 0;
    	int end = nums.length - 1;
    	int count = 0;
    	while(start < end) {
    		if(nums[start] + nums[end] == target) {
    		    start++;
    		    end--;
    		    count++;
    		    while(start < end && nums[start] == nums[start - 1]) {
    		    	start++;
    		    }
    		    while(start < end && nums[end] == nums[end + 1]) {
    		    	end--;
    		    }
    		} else if(nums[start] + nums[end] < target) {
    			start++;
    		} else {
    			end--;
    		}
    	}
    	return count;
    }
   
    public static void main(String[] args) {
    	TwoSum t = new TwoSum();
    	int[] numbers = {1,1,2,45,45,46};
    	int target = 47;
    	int result = t.twoSumUniquePairs(numbers, target);
    	System.out.println(result);
    }
}
