import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/largest-divisible-subset/description/
 * Given a set of distinct positive integers, find the largest subset such that every 
 * pair (Si, Sj) of elements in this subset satisfies: Si % Sj = 0 or Sj % Si = 0.

	If there are multiple solutions, return any subset is fine.
	
	Example 1:
	
	nums: [1,2,3]
	
	Result: [1,2] (of course, [1,3] will also be ok)
	Example 2:
	
	nums: [1,2,4,8]
	
	Result: [1,2,4,8]
 * 
 * Solution
 * http://www.cnblogs.com/grandyang/p/5625209.html
 * 这道题给了我们一个数组，让我们求这样一个子集合，集合中的任意两个数相互取余均为0，而且提示中说明了要使用DP来解。那么我们考虑，
 * 较小数对较大数取余一定不为0，那么问题就变成了看较大数能不能整除这个较小数。那么如果数组是无序的，处理起来就比较麻烦，所以我们
 * 首先可以先给数组排序，这样我们每次就只要看后面的数字能否整除前面的数字。定义一个动态数组dp，其中dp[i]表示到数字nums[i]
 * 位置最大可整除的子集合的长度，还需要一个一维数组pre，来保存上一个能整除的数字的位置，两个整型变量mx和mx_idx分别表示
 * 最大子集合的长度和起始数字的位置，我们可以从后往前遍历数组，对于某个数字再遍历到末尾，在这个过程中，如果nums[j]能整除
 * nums[i], 且dp[i] < dp[j] + 1的话，更新dp[i]和pre[i]，如果dp[i]大于mx了，还要更新mx和mx_idx，
 * 最后循环结束后，我们来填res数字，根据parent数组来找到每一个数字
 * 
 * 
 * http://blog.csdn.net/yeqiuzs/article/details/51773890
 * https://discuss.leetcode.com/topic/49652/classic-dp-solution-similar-to-lis-o-n-2
 * https://discuss.leetcode.com/topic/49456/c-solution-with-explanations
 */
public class LargestDivisibleSubset {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        List<Integer> result = new ArrayList<Integer>();
        if(nums == null || nums.length == 0) {
            return result;
        }
        
        // sort array
        // 那么我们考虑，较小数对较大数取余一定不为0，那么问题就变成了看较大数能不能整除这个较小数。那么如果数组是无序的，
        // 处理起来就比较麻烦，所以我们首先可以先给数组排序，这样我们每次就只要看后面的数字能否整除前面的数字
        Arrays.sort(nums);
        
        // state:
        // Refer to
        // http://blog.csdn.net/yeqiuzs/article/details/51773890
        // dp[i] to record maximum size subset(including element on index = i) 
        // when index = i (记录从0~i包括nums[i]的最大subset的size)
        // pre[i] to record previous element index that can be divided
        // (记录到当前构成subset的元素的前一个元素的下标)
        // dp数组表示从0~i包括第i个元素最大的divisible subset size 
        // pre数组用来标记 状态转移过程中的方向，用于回溯最大值时的解集。 
        // dp[i]=max{dp[i],dp[j]+1}
        int n = nums.length;
        int[] dp = new int[n];
        int[] pre = new int[n];
        
        // initialize
        for(int i = 0; i < n; i++) {
            dp[i] = 1;
            pre[i] = -1;
        }
        int maxLen = 0;  // global variable to store size of maximum subset
        int maxIdx = -1;  // maximum index of maximum subset
            
        // function
        for(int i = 0; i < n; i++) {
            for(int j = i - 1; j >= 0; j--) {
                if(nums[i] % nums[j] == 0) {
                    if(dp[i] < dp[j] + 1) {
                        dp[i] = dp[j] + 1;
                        // record the previous element index(position = j) which as 
                        // divisor of current element(position = i)
                        // e.g nums[1,2,3,4] -> when i = 1, pre[i] = j = 0, 
                        // when i = 2, pre[i] = j = 0, when i = 3, pre[i] = j = 1
                        // so, the pre array looks as [-1,0,0,1]
                        pre[i] = j;
                    }
                }
            }
            // update size of maximum subset and its last element index
            if(dp[i] > maxLen) {
            	maxLen = dp[i];
                maxIdx = i;
            }
        }
        
        // Merge this section into function
        // for(int i = 0; i < nums.length; i++) {//找到最大的子集size 和它最后元素的下标
        //     if (dp[i] > max) {
        //         max = dp[i];
        //         maxIdx = i;
        //     }
        // }
        
        // answer        
        while(maxIdx != -1) {
            result.add(nums[maxIdx]);
            // backtracking all positions stored in pre array
            // internally we can back tracking because dynamic programming is actually
            // memorization traverse search, very like DFS
            maxIdx = pre[maxIdx];
        }
        
        // Another style
        // Refer to
        // http://blog.csdn.net/yeqiuzs/article/details/51773890
        // for (int i = maxIdx; i >= 0;) { //回溯解集
        //     res.addFirst(nums[i]);
        //     i = pre[i];
        // }
        
        return result;
    }
    
    public static void main(String[] args) {
    	LargestDivisibleSubset l = new LargestDivisibleSubset();
    	int[] nums = {1,2,3,4};
    	List<Integer> result = l.largestDivisibleSubset(nums);
    	for(Integer i : result) {
    		System.out.print(i + " ");
    	}
    }
}
