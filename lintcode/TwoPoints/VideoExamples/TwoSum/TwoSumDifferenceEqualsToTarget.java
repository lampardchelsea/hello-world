/**
 * Refer to
 * https://yeqiuquan.blogspot.com/2017/04/lintcode-610-two-sum-difference-equals.html
 * Description
   Given an array of integers, find two numbers that their difference equals to a target value.
   where index1 must be less than index2. Please note that your returned answers 
   (both index1 and index2) are NOT zero-based.
   Notice
   It's guaranteed there is only one available solution

   Example
   Given nums = [2, 7, 15, 24], target = 5
   return [1, 2] (7 - 2 = 5)
 *
 * Solution
 * https://yeqiuquan.blogspot.com/2017/04/lintcode-610-two-sum-difference-equals.html
 * 思路
 * 排序以后两个指针同时从左往右走。
*/
public class Solution {
    /*
     * @param nums an array of Integer
     * @param target an integer
     * @return [index1 + 1, index2 + 1] (index1 < index2)
     */
    private class Pair {
    	int index;
    	int value;
    	public Pair(int index, int value) {
    		this.index = index;
    		this.value = value;
    	}
    }
	
    public int[] twoSumDifferenceEqualsToTarget(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
        	return new int[0];
        }
        int[] result = new int[2];
        // Because we need to return original nums indexes, cannot sort
        // on original nums as that will change the original order, but
        // still need to sort nums based on value, so we need to create
        // private class Pair to implement just sort value and keep its
        // original index
        Pair[] pairs = new Pair[nums.length];
        for(int i = 0; i < nums.length; i++) {
        	pairs[i] = new Pair(i, nums[i]);
        }
        // Sort based on value, no change on original index
        Arrays.sort(pairs, new Comparator<Pair>() {
			@Override
			public int compare(Pair x, Pair y) {
				return x.value - y.value;
			}
        });
        int j = 0;
        for(int i = 0; i < pairs.length; i++) {
        	if(j == i) {
        		j++;
        	}
        	while(j < pairs.length && pairs[j].value - pairs[i].value < target) {
        		j++;
        	}
        	if(j < pairs.length && pairs[j].value - pairs[i].value == target) {
        		result[0] = Math.min(pairs[i].index, pairs[j].index) + 1;
        		result[1] = Math.max(pairs[i].index, pairs[j].index) + 1;
        		return result;
        	}
        }
        return result;
    }
}
