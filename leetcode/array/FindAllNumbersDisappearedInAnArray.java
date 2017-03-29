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
    
    public static void main(String[] args) {
    	int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};
    	FindAllNumbersDisappearedInAnArray f = new FindAllNumbersDisappearedInAnArray();
    	List<Integer> result = f.findDisappearedNumbers(nums);
    	for(Integer i : result) {
    		System.out.print(i + " ");
    	}
    }
}

