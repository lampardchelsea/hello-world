/**
 * Refer to
 * https://leetcode.com/problems/range-sum-query-immutable/
 * 
 * 
 * Solution
 * https://leetcode.com/articles/range-sum-query-immutable/
 * https://discuss.leetcode.com/topic/29194/java-simple-o-n-init-and-o-1-query-solution
 * https://discuss.leetcode.com/topic/29322/java-solution-using-sum-array-built-in-constructor
*/

// Solution 1: Time Limited Exceeded
// Time Compelxity: O(n)
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

// Solution 2:
class NumArray {
    // Style 1:
    int[] sum;
    public NumArray(int[] nums) {
        for(int i = 1; i < nums.length; i++) {
            nums[i] += nums[i - 1];
        }
        sum = nums;
    }
    
    public int sumRange(int i, int j) {
        if(i == 0) {
            return sum[j];
        }
        return sum[j] - sum[i - 1];
    }
    
    // Style 2:
    // Refer to
    // https://discuss.leetcode.com/topic/29322/java-solution-using-sum-array-built-in-constructor
    int[] sum;
    public NumArray(int[] nums) {
        sum = new int[nums.length];
        if(nums.length > 0) {
            sum[0] = nums[0];    
        }
        for(int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
    }
    
    public int sumRange(int i, int j) {
        if(i == 0) {
            return sum[j];
        }
        return sum[j] - sum[i - 1];
    }
    
}


/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(i,j);
 */
