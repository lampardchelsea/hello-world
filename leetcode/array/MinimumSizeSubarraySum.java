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
class Solution {
    /**
     Approach #4 Using 2 pointers [Accepted]
     Intuition
     Until now, we have kept the starting index of subarray fixed, and found the last position. 
     Instead, we could move the starting index of the current subarray as soon as we know that 
     no better could be done with this index as the starting index. We could keep 2 pointer,one 
     for the start and another for the end of the current subarray, and make optimal moves so 
     as to keep the sum greater than ss as well as maintain the lowest size possible.
     
     Algorithm
     Initialize left pointer to 0 and sum to 0
     Iterate over the nums:Add nums[i] to sum
     While sum is greater than or equal to s:
     Update ans=min(ans,i+1−left), where i+1−left is the size of current subarray
     It means that the first index can safely be incremented, since, the minimum subarray starting 
     with this index with sum≥s has been achieved
     Subtract nums[left] from sum and increment left
     
     Complexity analysis
     Time complexity: O(n). Single iteration of O(n). 
                      Each element can be visited atmost twice, once by the right pointer(i) and 
                      (atmost)once by the left pointer. --> This is very important: for loop and
                      inner while loop doesn't mean O(n^2), because the inner while loop not a
                      fully loop, because left pointer will never move backward, it only keep
                      increasing till end of nums array. So O(2n) actually still O(n) based on
                      each element only visited twice.
     Space complexity: O(1) extra space. Only constant space required for left, sum, ans and i.
    */
    public int minSubArrayLen(int s, int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int result = Integer.MAX_VALUE;
        int left = 0;
        int sum = 0;
        for(int i = 0; i < n; i++) {
            sum += nums[i];
            while(sum >= s) {
                // Update ans=min(ans,i+1−left), where i+1−left is the size of current subarray
                result = Math.min(result, i - left + 1);
                sum -= nums[left];
                // Move forward 1 position for left bound
                left++;
            }
        }
        return result == Integer.MAX_VALUE ? 0 : result;
    }
}


Attempt 1: 2022-09-07 (10min)

class Solution { 
    public int minSubArrayLen(int target, int[] nums) { 
        int minLen = Integer.MAX_VALUE; 
        int len = nums.length; 
        int i = 0; 
        int sum = 0; 
        for(int j = 0; j < len; j++) { 
            sum += nums[j]; 
            // Keep shrink left end 
            while(sum >= target) { 
                minLen = Math.min(minLen, j - i + 1); 
                sum -= nums[i]; 
                i++; 
            } 
        } 
        return minLen == Integer.MAX_VALUE ? 0 : minLen; 
    } 
}

Time Complexity: O(n)

https://leetcode.com/problems/minimum-size-subarray-sum/discuss/59078/Accepted-clean-Java-O(n)-solution-(two-pointers)/924580
For those trying to figure out how is it O(n):
Here we have defined 2 index i & j,
In case of O(n^2) for each outer loop, inner loop runs some n or m number of times to make it O(nm), that means, 
as soon as the outer loop finishes one iteration, inner loop resets itself.
In case of O(n2), as in this case, we are not resetting the inner inner variable i, it's just incrementing each time. 
It is like 2 loops one after another and both runs n number of time.
=======================================================================================================================    
    
Follow up: 
Time complexity O(n log(n))solution, Prefix Sum + Binary Search

Attempt 1:2022-09-09 (360min, too long for understanding binary search template)

class Solution { 
    public int minSubArrayLen(int s, int[] nums) { 
        int len = nums.length; 
        int[] preSum = new int[len + 1]; 
        //preSum[0] = 0; 
        for(int i = 1; i <= len; i++) { 
            preSum[i] = nums[i - 1] + preSum[i - 1]; 
        } 
        // 根据模板第一种情况，给定一个升序排列的数组，我们将preSum中满足 x ≥ target 的 
        // 第一个元素定义为「下界」在加入无法找到就返回-1这种情况以后类型更接近模板第三种情况： 
        // 查找指定值第一次出现的位置，修正为查找满足 x ≥ target 的第一个元素，如果因为下标 
        // 越界或仍在界内却依然 x < target, 则判定为不存在，返回-1。 
        // e.g 
        // nums = {2,3,1,2,4,3}, s = 7 
        // preSum = {0,2,5,6,8,12,15} 
        // i = 0, target = 7, binarySearch(0,6,7,preSum) -> low_bound = 4, minLen = 4(low_bound - i = 4 - 0 = 4, mapping nums subarray = {2,3,1,2}) 
        // i = 1, target = 9, binarySearch(1,6,9,preSum) -> low_bound = 5, minLen = 4(low_bound - i = 5 - 1 = 4, mapping nums subarray = {3,1,2,4}) 
        // i = 2, target = 12, binarySearch(2,6,12,preSum) -> low_bound = 5, minLen = 3(low_bound - i = 5 - 2 = 3, mapping nums subarray = {1,2,4}) 
        // i = 3, target = 13, binarySearch(3,6,13,preSum) -> low_bound = 6, minLen = 3(low_bound - i = 6 - 3 = 3, mapping nums subarray = {2,4,3}) 
        // i = 4, target = 15, binarySearch(4,6,15,preSum) -> low_bound = 6, minLen = 2(low_bound - i = 6 - 4 = 2, mapping nums subarray = {4,3}) ==> answer 
        // i = 5, target = 19, binarySearch(5,6,19,preSum) -> low_bound = NA, minLen = NA 
        int minLen = Integer.MAX_VALUE; 
        for(int i = 0; i <= len; i++) { 
            int target = preSum[i] + s; 
            int low_bound = binarySearch(i, len, target, preSum); 
            if(low_bound != -1) { 
                minLen = Math.min(minLen, low_bound - i); 
            } 
        } 
        return minLen == Integer.MAX_VALUE ? 0 : minLen; 
    } 
     
    // Binary Search template refer to 
    // https://imageslr.com/2020/03/15/binary-search.html 
    private int binarySearch(int lo, int hi, int target, int[] preSum) { 
        while(lo <= hi) { 
            int mid = lo + (hi - lo) / 2; 
            if(preSum[mid] >= target) { 
                hi = mid - 1; 
            } else { 
                lo = mid + 1; 
            } 
        } 
        // Important: In case we could not find target in preSum return -1 
        // Test out when i = 5, target = 19, binarySearch(5,6,19,preSum), 
        // 'lo' increase from 5 to 7 and index out of boundary, no valid  
        // range between 'lo' and 'hi' (actually 'lo' is already over 
        // 'hi') and still not able to find preSum[mid] >= target. 
        // we can ignore the second condition as 'preSum[lo] < target' since 
        // first condition as 'lo >= preSum.length' automatically means 'lo' 
        // go through all indexes and no 'preSum[lo] < target' found 
        // if(lo >= preSum.length || preSum[lo] < target) 
        if(lo >= preSum.length) { 
            return -1; 
        } 
        return lo; 
    } 
}

Space Complexity: O(n)
Time Complexity: O(nlogn)

Note: Why we can use binary search ?
https://leetcode.com/problems/minimum-size-subarray-sum/discuss/59103/Two-AC-solutions-in-Java-with-time-complexity-of-N-and-NLogN-with-explanation
As to NLogN solution, logN immediately reminds you of binary search. In this case, you cannot sort as the current order actually matters. 
How does one get an ordered array then? Since all elements are positive, the cumulative sum must be strictly increasing. 
Then, a subarray sum can expressed as the difference between two cumulative sum. Hence, given a start index for the cumulative sum array, 
the other end index can be searched using binary search.
