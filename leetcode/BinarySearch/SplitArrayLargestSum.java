/**
Refer to
https://leetcode.com/problems/split-array-largest-sum/
Given an array nums which consists of non-negative integers and an integer m, you can split the array into m non-empty continuous subarrays.

Write an algorithm to minimize the largest sum among these m subarrays.

Example 1:
Input: nums = [7,2,5,10,8], m = 2
Output: 18
Explanation:
There are four ways to split nums into two subarrays.
The best way is to split it into [7,2,5] and [10,8],
where the largest sum among the two subarrays is only 18.

Example 2:
Input: nums = [1,2,3,4,5], m = 2
Output: 9

Example 3:
Input: nums = [1,4,4], m = 3
Output: 4

Constraints:
1 <= nums.length <= 1000
0 <= nums[i] <= 106
1 <= m <= min(50, nums.length)
*/

// Solution 1:
// Refer to
// https://leetcode.com/discuss/general-discussion/786126/python-powerful-ultimate-binary-search-template-solved-many-problems
/**
410. Split Array Largest Sum [Hard]
Given an array which consists of non-negative integers and an integer m, you can split the array into m non-empty continuous subarrays. 
Write an algorithm to minimize the largest sum among these m subarrays.

Example:
Input:
nums = [7,2,5,10,8]
m = 2
Output:
18

Explanation:
There are four ways to split nums into two subarrays. The best way is to split it into [7,2,5] and [10,8], where the largest 
sum among the two subarrays is only 18.
If you take a close look, you would probably see how similar this problem is with LC 1011 above. Similarly, we can design a feasible function: 
given an input threshold, then decide if we can split the array into several subarrays such that every subarray-sum is less than or equal 
to threshold. In this way, we discover the monotonicity of the problem: if feasible(m) is True, then all inputs larger than m can satisfy 
feasible function. You can see that the solution code is exactly the same as LC 1011.

def splitArray(nums: List[int], m: int) -> int:        
    def feasible(threshold) -> bool:
        count = 1
        total = 0
        for num in nums:
            total += num
            if total > threshold:
                total = num
                count += 1
                if count > m:
                    return False
        return True

    left, right = max(nums), sum(nums)
    while left < right:
        mid = left + (right - left) // 2
        if feasible(mid):
            right = mid     
        else:
            left = mid + 1
    return left

But we probably would have doubts: It's true that left returned by our solution is the minimal value satisfying feasible, 
but how can we know that we can split the original array to actually get this subarray-sum? For example, let's say 
nums = [7,2,5,10,8] and m = 2. We have 4 different ways to split the array to get 4 different largest subarray-sum 
correspondingly: 25:[[7], [2,5,10,8]], 23:[[7,2], [5,10,8]], 18:[[7,2,5], [10,8]], 24:[[7,2,5,10], [8]]. Only 4 values. 
But our search space [max(nums), sum(nums)] = [10, 32] has much more that just 4 values. That is, no matter how we split 
the input array, we cannot get most of the values in our search space.

Let's say k is the minimal value satisfying feasible function. We can prove the correctness of our solution with proof by 
contradiction. Assume that no subarray's sum is equal to k, that is, every subarray sum is less than k. The variable total 
inside feasible function keeps track of the total weights of current load. If our assumption is correct, then total would 
always be less than k. As a result, feasible(k - 1) must be True, because total would at most be equal to k - 1 and would 
never trigger the if-clause if total > threshold, therefore feasible(k - 1) must have the same output as feasible(k), 
which is True. But we already know that k is the minimal value satisfying feasible function, so feasible(k - 1) has to be 
False, which is a contradiction. So our assumption is incorrect. Now we've proved that our algorithm is correct.
*/

class Solution {
    public int splitArray(int[] nums, int m) {
        int lo = 0;
        int hi = 0;
        for(int num : nums) {
            hi += num;
            lo = Math.max(lo, num);
        }
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            // If number of subarrays less or equal to m,
            // it is good to shrink upper boundary 'hi' to 'mid',
            // which also means 'mid' itself satisfy requirement
            // no need to set as 'mid - 1'
            if(countSubarrays(nums, mid) <= m) {
                hi = mid;
            // If number of subarrays more than m, the lower
            // boundary not able to satisfy requirement, must
            // increase 'lo' to 'mid + 1' (not 'mid')
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }
    
    private int countSubarrays(int[] nums, int limit) {
        int count = 1;
        int sum = 0;
        for(int num : nums) {
            sum += num;
            if(sum > limit) {
                sum = num;
                count++;
            }
        }
        return count;
    }
}






































































https://leetcode.com/problems/split-array-largest-sum/description/
Given an integer array nums and an integer k, split nums into k non-empty subarrays such that the largest sum of any subarray is minimized.
Return the minimized largest sum of the split.
A subarray is a contiguous part of the array.

Example 1:
Input: nums = [7,2,5,10,8], k = 2
Output: 18
Explanation: There are four ways to split nums into two subarrays.The best way is to split it into [7,2,5] and [10,8], where the largest sum among the two subarrays is only 18.

Example 2:
Input: nums = [1,2,3,4,5], k = 2
Output: 9
Explanation: There are four ways to split nums into two subarrays.The best way is to split it into [1,2,3] and [4,5], where the largest sum among the two subarrays is only 9.
 
Constraints:
- 1 <= nums.length <= 1000
- 0 <= nums[i] <= 10^6
- 1 <= k <= min(50, nums.length)
--------------------------------------------------------------------------------
Attempt 1: 2024-12-06
Solution 1: Binary Search + Greedy (30 min)
class Solution {
    public int splitArray(int[] nums, int k) {
        int max = 0;
        int sum = 0;
        for(int num : nums) {
            max = Math.max(max, num);
            sum += num;
        }
        // The answer is between maximum value of input array numbers 
        // and sum of those numbers. Use binary search to approach the 
        // correct answer. We have lo = max number of array; hi = sum of 
        // all numbers in the array; Every time we do mid = (lo + hi) / 2;
        int lo = max;
        int hi = sum;
        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            // Use greedy to narrow down left and right boundaries in binary search.
            if(canSplitArrayWithMidOrNot(nums, k, mid)) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    private boolean canSplitArrayWithMidOrNot(int[] nums, int k, int minSubarrayMaxSum) {
        int count = 1;
        long sum = 0;
        for(int num : nums) {
            sum += num;
            if(sum > minSubarrayMaxSum) {
                count++;
                sum = num;
                // If the number of subarrays exceeds k, return false
                if(count > k) {
                    return false;
                }
            }
        }
        return true;
    }
}

Time Complexity: O(nlogn)
Space Complexity: O(1)

Refer to
https://leetcode.com/problems/split-array-largest-sum/solutions/89817/clear-explanation-8ms-binary-search-java/
1.The answer is between maximum value of input array numbers and sum of those numbers.
2.Use binary search to approach the correct answer. We have l = max number of array; r = sum of all numbers in the array;Every time we do mid = (l + r) / 2;
3.Use greedy to narrow down left and right boundaries in binary search.
3.1 Cut the array from left.
3.2 Try our best to make sure that the sum of numbers between each two cuts (inclusive) is large enough but still less than mid.
3.3 We'll end up with two results: either we can divide the array into more than m subarrays or we cannot.
- If we can, it means that the mid value we pick is too small because we've already tried our best to make sure each part holds as many non-negative numbers as we can but we still have numbers left. So, it is impossible to cut the array into m parts and make sure each parts is no larger than mid. We should increase m. This leads to l = mid + 1;
- If we can't, it is either we successfully divide the array into m parts and the sum of each part is less than mid, or we used up all numbers before we reach m. Both of them mean that we should lower mid because we need to find the minimum one. This leads to r = mid - 1;
public class Solution {
    public int splitArray(int[] nums, int m) {
        int max = 0; long sum = 0;
        for (int num : nums) {
            max = Math.max(num, max);
            sum += num;
        }
        if (m == 1) return (int)sum;
        //binary search
        long l = max; long r = sum;
        while (l <= r) {
            long mid = (l + r)/ 2;
            if (valid(mid, nums, m)) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return (int)l;
    }
    public boolean valid(long target, int[] nums, int m) {
        int count = 1;
        long total = 0;
        for(int num : nums) {
            total += num;
            if (total > target) {
                total = num;
                count++;
                if (count > m) {
                    return false;
                }
            }
        }
        return true;
    }
}

Refer to chatGPT
Two Key Approaches:
1. Binary Search with Greedy Check (Efficient Solution)
Idea:
- The largest sum in the optimal split must lie between:
- max(nums): The largest single element (minimum possible largest sum).
- sum(nums): The sum of all elements (maximum possible largest sum).
- Use binary search to find the smallest largest sum, and use a greedy check to validate the current guess.
class Solution {
    public int splitArray(int[] nums, int m) {
        int max = 0;
        long sum = 0;

        // Determine the range for binary search
        for (int num : nums) {
            max = Math.max(max, num);
            sum += num;
        }

        // Binary search bounds
        long left = max, right = sum;
        while (left < right) {
            long mid = left + (right - left) / 2;

            // Check if it's possible to split with largest sum <= mid
            if (canSplit(nums, m, mid)) {
                right = mid; // Try for a smaller largest sum
            } else {
                left = mid + 1; // Increase the largest sum
            }
        }

        return (int) left; // The smallest possible largest sum
    }

    private boolean canSplit(int[] nums, int m, long maxSum) {
        int count = 1;
        long currentSum = 0;

        for (int num : nums) {
            if (currentSum + num > maxSum) {
                // Create a new subarray
                count++;
                currentSum = num;

                // If the number of subarrays exceeds m, return false
                if (count > m) {
                    return false;
                }
            } else {
                currentSum += num;
            }
        }

        return true;
    }
}
Explanation:
1.Binary Search Range:
- Start with the range [max(nums), sum(nums)].
- Narrow the range by checking if the current mid value is a valid maximum sum for splitting.
2.Greedy Check (canSplit):
- Traverse the array and keep adding to the current subarray sum.
- If adding a number exceeds maxSum, start a new subarray and increment the count.
3.Terminate:
- When the binary search completes, left represents the smallest valid largest sum.
Complexity:
- Time: O(nlog⁡(sum(nums)−max(nums))):
- Binary search runs O(log⁡(range)) times.
- Each canSplit check runs O(n)
- Space: O(1), as no additional space is used.
使用标准 Find Lower Boundary 的模版如下：
If you want to use the binary search template provided, you need to adjust the logic slightly because the left <= right condition allows the loop to terminate when left > right, and the smallest valid largest sum will be stored in left at the end of the loop. The key difference is that in this template, you adjust right to mid - 1 and keep track of the smallest possible valid solution.
Here's how your modified binary search solution should look:
class Solution {
    public int splitArray(int[] nums, int m) {
        int max = 0;
        long sum = 0;

        // Determine the range for binary search
        for (int num : nums) {
            max = Math.max(max, num);
            sum += num;
        }

        // Binary search bounds
        long left = max, right = sum;
        while (left <= right) {
            long mid = left + (right - left) / 2;

            // Check if it's possible to split with largest sum <= mid
            if (canSplit(nums, m, mid)) {
                right = mid - 1; // Try for a smaller largest sum
            } else {
                left = mid + 1; // Increase the largest sum
            }
        }

        return (int) left; // The smallest possible largest sum
    }

    private boolean canSplit(int[] nums, int m, long maxSum) {
        int count = 1;
        long currentSum = 0;

        for (int num : nums) {
            if (currentSum + num > maxSum) {
                // Start a new subarray
                count++;
                currentSum = num;

                // If the number of subarrays exceeds m, return false
                if (count > m) {
                    return false;
                }
            } else {
                currentSum += num;
            }
        }

        return true;
    }
}
Key Adjustments:
1.Loop Condition:
- The loop runs while left <= right instead of left < right.
2.Binary Search Updates:
- If canSplit(nums, m, mid) is true, adjust right = mid - 1 instead of right = mid.
- If canSplit(nums, m, mid) is false, adjust left = mid + 1.
3.Return Statement:
- After the loop, left holds the smallest valid largest sum because right will be one step below left when the loop exits.
Why It Works:
In this template, the binary search ensures the range [left, right] shrinks until it converges. At termination:
- left represents the smallest value for which canSplit returns true.
- The adjustments to right ensure that any invalid midpoint (canSplit = false) is excluded from consideration.
This is a direct adaptation of your template to this problem.

Refer to
L1011.Capacity To Ship Packages Within D Days (Ref.L410)
L704.Binary Search
