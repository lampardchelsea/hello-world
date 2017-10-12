public class Solution {
    /*
     * @param nums: a set of distinct positive integers
     * @return: the largest subset 
     */
    public List<Integer> largestDivisibleSubset(int[] nums) {
        List<Integer> result = new ArrayList<Integer>();
        if(nums == null || nums.length == 0) {
            return result;
        }
        // sort array
        Arrays.sort(nums);
        // state
        int n = nums.length;
        int[] dp = new int[n];
        int[] pre = new int[n];
        // intialize
        for(int i = 0; i < n; i++) {
            dp[i] = 1;
            pre[i] = -1;
        }
        int maxLen = 0;
        int maxIdx = -1;
        // function
        for(int i = 0; i < n; i++) {
            for(int j = i - 1; j >= 0; j--) {
                if(nums[i] % nums[j] == 0) {
                    if(dp[i] < dp[j] + 1) {
                        dp[i] = dp[j] + 1;
                        pre[i] = j;
                    }
                }
            }
            if(dp[i] > maxLen) {
                maxLen = dp[i];
                maxIdx = i;
            }
        }
        // answer
        while(maxIdx != -1) {
            result.add(nums[maxIdx]);
            maxIdx = pre[maxIdx];
        }
        return result;
    }
}
