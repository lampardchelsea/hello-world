/**
 * Refer to
 * https://leetcode.com/problems/minimum-size-subarray-sum/#/description
 *  Given an array of n positive integers and a positive integer s, find the minimal length 
 *  of a contiguous subarray of which the sum ≥ s. If there isn't one, return 0 instead.
 *  For example, given the array [2,3,1,2,4,3] and s = 7,
 *  the subarray [4,3] has the minimal length under the problem constraint.
 *  click to show more practice.
 *  
 *  More practice:
 *  If you have figured out the O(n) solution, try coding another solution of which the time 
 *  complexity is O(n log n).
 * 
 * Solution
 * https://discuss.leetcode.com/topic/18583/accepted-clean-java-o-n-solution-two-pointers
 * https://leetcode.com/articles/minimum-size-subarray-sum/
 * http://likesky3.iteye.com/blog/2229475
 * http://www.cnblogs.com/grandyang/p/4501934.html
 * https://lefttree.gitbooks.io/leetcode-categories/twoPointer/minSizeSubarraySum.html
 */
// Solution 1:
class Solution {
    /**
     Approach #1 Brute force [Time Limit Exceeded]
     Intuition
     Do as directed in question. Find the sum for all the possible subarrays and update 
     the ans as and when we get a better subarray that fulfill the requirements sum ≥ s.
     
     Algorithm
     Initialize ans=INT_MAX
     Iterate the array from left to right using i:
     Iterate from the current element to the end of vector using j:
     Find the sum of elements from index i to j
     If sum is greater then s:
     Update ans=min(ans,(j−i+1))
     Start the next iith iteration, since, we got the smallest subarray with
     sum≥s starting from the current index.
     
     Complexity Analysis
     Time complexity: O(n^3). For each element of array, we find all the subarrays starting 
                              from that index which is O(n^2) Time complexity to find the 
                              sum of each subarray is O(n). Thus, the total time complexity: 
                              O(n^2 * n) = O(n^3)
     Space complexity: O(1) extra space.
    */
    public int minSubArrayLen(int s, int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int result = Integer.MAX_VALUE;
        for(int i = 0; i < nums.length; i++) {
            for(int j = i; j < nums.length; j++) {
                int sum = 0;
                for(int k = i; k <= j; k++) {
                    sum += nums[k];
                }
                if(sum >= s) {
                    result = Math.min(result, j - i + 1);
                    // Must break here to get the minimum length
                    // as from now on sum always >= s, and the
                    // first case means minimum length
                    break;
                }
            }
        }
        // Return 0 if not able to reach target >= s
        // e.g s = 3, nums = {1,1}
        return result == Integer.MAX_VALUE ? 0 : result;
    }
}


// Solution 2:
class Solution {
    /**
     Approach #2 A better brute force [Accepted]
     Intuition
     In Approach #1, you may notice that the sum is calculated for every surarray in O(n) time. 
     But, we could easily find the sum in O(1) time by storing the cumulative sum from the 
     beginning(Memoization). After we have stored the cumulative sum in sums, we could easily 
     find the sum of any subarray from i to j.
     
     Algorithm
     The algorithm is similar to Approach #1.
     The only difference is in the way of finding the sum of subarrays:
     Create a vector sums of size of nums
     Initialize sums[0]=nums[0]
     Iterate over the sums vector:
     Update sums[i]=sums[i−1]+nums[i]
     Sum of subarray from i to j is calculated as: sum=sums[j]−sums[i]+nums[i], 
     wherein sums[j]−sums[i] is the sum from (i+1)th element to the jth element.
     
     Complexity analysis
     Time complexity: O(n^2) Time complexity to find all the subarrays is O(n^2).
                             Sum of the subarrays is calculated in O(1) time.
                             Thus, the total time complexity: O(n^2 * 1)
     Space complexity: O(n) extra space. Additional O(n) space for sums vector than in Approach #1.
    */
    public int minSubArrayLen(int s, int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int[] preSum = new int[n];
        preSum[0] = nums[0];
        for(int i = 1; i < n; i++) {
            preSum[i] = preSum[i - 1] + nums[i];
        }
        int result = Integer.MAX_VALUE;
        for(int i = 0; i < n; i++) {
            for(int j = i; j < n; j++) {
                int sum = preSum[j] - preSum[i] + nums[i];
                if(sum >= s) {
                    // Find the smallest subarray with sum >= s starting with index i,
                    // hence break current loop and move to next start index i + 1
                    result = Math.min(result, j - i + 1);
                    break;
                }
            }
        }
        return result == Integer.MAX_VALUE ? 0 : result;
    }
}



// Solution 3:
class Solution {
    /**
      Approach #3 Using Binary search [Accepted]
      Intuition
      We could further improve the Approach #2 using the binary search. Notice that we find the 
      subarray with sum>=s starting with an index i in O(n) time. But, we could reduce the time 
      to O(log(n) using binary search. Note that in Approach #2, we search for subarray starting 
      with index i, until we find sum=sums[j]−sums[i]+nums[i] that is greater than s. So, instead 
      of iterating linearly to find the sum, we could use binary search to find the index that 
      is not lower than s−sums[i] in the sums, which can be done using lower_bound function in 
      C++ STL or could be implemented manually.
      
      Algorithm
      Create vector sums of size n+1 with: sums[0]=0, sums[i]=sums[i−1]+nums[i−1]
      Iterate from i=1 to n:
      Find the value to_find sum required for minimum subarray starting from index i to have 
      sum greater than s, that is: to_find=s+sums[i−1]
      Find the index in sums such that value at that index is not lower than the to_find value, 
      say bound
      If we find the to_find in sums, then: Size of current subarray is given by: bound−(sums.begin()+i−1)
      Compare ansans with the current subarray size and store minimum in ansans
      
      Complexity analysis
      Time complexity: O(nlog(n)).
      For each element in the vector, find the subarray starting from that index, and having sum 
      greater than ss using binary search. Hence, the time required is O(n) for iteration over the 
      vector and O(log(n)) for finding the subarray for each index using binary search.
      Therefore, total time complexity = O(n∗log(n))
      Space complexity: O(n). Additional O(n) space for sums vector
      
      Refer to
      http://likesky3.iteye.com/blog/2229475
      将问题转化为在递增数列中查找某个数。建立辅助数组sum[], sum[i]表示 num数组的前 i 个
      元素的加和。对于每个sum[i]，在 i 后面查找子数组右边界位置使得子数组的加和 >= s, 
      也就是在 i 位置后面寻找位置 j 满足 sum[j] >= sum[i] + s, 满足这个关系表明 num[i] 
      到 num[j - 1]这段子数组加和>=s。因为sum[]是递增数组，可使用二分法查找满足要求的下标。
      注意到实现中的binarySearchNotLess和经典的二分算法区别就在于while循环外面的return，
      这里是return left，如果sum数组中找不到target，会返回第一个比target大元素的下标，
      如果没有则返回sum.length + 1, 调用处据此判断某个元素后面是否有加和为s的子数组
    */
    public int minSubArrayLen(int s, int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        // size = n + 1 for easier calculate
        int[] preSum = new int[n + 1];
        // preSum[0] = 0 means it is the sum for first 0 elements
        preSum[0] = 0;
        // preSum[1] = A[0] means the sum for first 1 elements
        for(int i = 1; i < n + 1; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }
        int result = Integer.MAX_VALUE;
        for(int i = 0; i < n; i++) {
            // Try from preSum[0] to the end as preSum[n]
            int target = s + preSum[i];
            int right_bound = binarySearch(i, n, target, preSum);
            if(right_bound != preSum.length + 1) {
                result = Math.min(result, right_bound - i);
            }
        }
        return result == Integer.MAX_VALUE ? 0 : result;
    }
    
    private int binarySearch(int start, int end, int target, int[] preSum) {
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            // As try to find minimum length, cut second half
            // and keep on search in first half by end = mid
            if(preSum[mid] == target) {
                end = mid;
            } else if(preSum[mid] > target) {
                end = mid;
            } else {
                start = mid;
            }
        }
        if(preSum[start] >= target) {
            return start;
        } else if(preSum[end] >= target) {
            return end;
        } else {
            return preSum.length + 1;
        }
    }
}




// Solution 4: Two Points
public class MinimumSizeSubarraySum {
    public int minSubArrayLen(int s, int[] nums) {
        int len = nums.length;
        if(len == 0) return 0;
        int minWindow = Integer.MAX_VALUE;
        int left = 0;
        int right = 0;
        int sum = 0;
        while(right < nums.length) {
            sum += nums[right++];
            while(sum >= s) {
                minWindow = Math.min(minWindow, right - left);
                sum -= nums[left++];
            }
        }
        return minWindow == Integer.MAX_VALUE ? 0 : minWindow;
    }
    
    public static void main(String[] args) {
    	
    }
}
