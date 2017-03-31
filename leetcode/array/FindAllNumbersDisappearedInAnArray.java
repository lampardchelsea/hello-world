import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/#/description
 * Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.
 * Find all the elements of [1, n] inclusive that do not appear in this array.
 * Could you do it without extra space and in O(n) runtime? You may assume the returned list does not count as extra space.
 * Example:
 * Input:
 * [4,3,2,7,8,2,3,1]
 * Output:
 * [5,6]
 */
public class FindAllNumbersDisappearedInAnArray {
	// Solution 1: Binary search for each value
    public List<Integer> findDisappearedNumbers(int[] nums) {
    	Arrays.sort(nums);
        List<Integer> result = new ArrayList<Integer>();
        int len = nums.length;
        // The boolean array records whether valToFind(= i + 1) 
        // found in given nums array
        boolean[] found = new boolean[len];
        for(int i = 0; i < len; i++) {
            int lo = 0;
            int hi = len - 1;
            int valToFind = i + 1;
            while(lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if(valToFind < nums[mid]) {
                    hi = mid - 1;
                } else if(valToFind > nums[mid]){
                    lo = mid + 1;
                } else {
                	// If found value in nums array, set found[i] to true
                	found[i] = true;
                	break;
                }
            }
            // If after binary search found[i] keep as false, it means
            // valToFind(= i + 1) missing in array.
            if(!found[i]) {
                result.add(valToFind);
            }
        }
        return result;
    }
    
    // Solution 2:
    // Refer to
    // https://discuss.leetcode.com/topic/65738/java-accepted-simple-solution/30
    /**
     * A more detailed explanation for those who might still be confused:
     * This solution is using the relation between array index ([0, n-1]) and the given value range [1,n].
     * Each time when a new value X is read, it changes the corresponding Xth number (value at index X-1) 
     * into negative, indicating value X is read for the first time.
     * For example. using the given test case [4,3,2,7,8,2,3,1], when it comes to i = 2 in the first loop, 
     * this solution marks the 2nd number (index = 1), indicating we've found number 2 for the first time.
     * When we encounter a redundant number Y, because we've marked the Yth position (index Y -1) when we 
     * saw Y for the first time, the if clause won't let us flip it again. This leaves the already marked 
     * Yth number (number at index Y-1) negative.
     * For example, in the given test case, when i = 5, val = |2| - 1 = 1, nums[1] = -3 < 0. No flip operation 
     * is needed because we've found value 2 before.
     * Looping through the 1st loop takes O(n) time, flipping signs won't take extra space.
     * The second loop checks the signs of the values at indices. If the sign at index P is negative, it means 
     * value P + 1 is in the array. e.g. nums[0] = -4, so value 0+1 = 1 is in the array. If the value at index 
     * Q is positive, then value Q + 1 is not in the array. e.g. nums[4] = 8 > 0, value 4 + 1 = 5, we add 5 
     * into the ret list.
     */
    public List<Integer> findDisappearedNumbers2(int[] nums) {
        List<Integer> result = new ArrayList<Integer>();
        for(int i = 0; i < nums.length; i++) {
        	// Must use abs, otherwise if encounter duplicate values
        	// which already set to negative value by previous
        	// operation, will calculate to negative index which is
        	// absolutely wrong
            int index =  Math.abs(nums[i]) - 1;
            //int index =  nums[i] - 1;
            if(nums[index] > 0) {
                nums[index] = -nums[index];
            }
        }
        for(int i = 0; i < nums.length; i++) {
        	if(nums[i] > 0) {
        		result.add(i + 1);
        	}
        }
        return result;
    }
    
    
    public static void main(String[] args) {
    	int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};
    	FindAllNumbersDisappearedInAnArray f = new FindAllNumbersDisappearedInAnArray();
    	List<Integer> result = f.findDisappearedNumbers2(nums);
    	for(Integer i : result) {
    		System.out.print(i + " ");
    	}
    }
}
