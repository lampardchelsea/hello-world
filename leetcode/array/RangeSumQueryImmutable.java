/**
 * Refer to
 * https://leetcode.com/problems/range-sum-query-immutable/
 *
 * 
 * Solution
 * 
 * 
*/

// Solution 1: Time Limited Exceeded
class NumArray {
    List<Integer> list;
    
    public NumArray(int[] nums) {
        list = new ArrayList<Integer>();
        for(int i = 0; i < nums.length; i++) {
            list.add(nums[i]);
        }
    }
    
    public int sumRange(int i, int j) {
        int sum = 0;
        for(int k = i; k <= j; k++) {
            sum += list.get(k);
        }
        return sum;
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(i,j);
 */
