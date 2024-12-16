https://leetcode.com/problems/count-subarrays-with-score-less-than-k/description/
The score of an array is defined as the product of its sum and its length.
- For example, the score of [1, 2, 3, 4, 5] is (1 + 2 + 3 + 4 + 5) * 5 = 75.
Given a positive integer array nums and an integer k, return the number of non-empty subarrays of nums whose score is strictly less than k.
A subarray is a contiguous sequence of elements within an array.

Example 1:
Input: nums = [2,1,4,3,5], k = 10
Output: 6
Explanation:
The 6 subarrays having scores less than 10 are:
- [2] with score 2 * 1 = 2.
- [1] with score 1 * 1 = 1.
- [4] with score 4 * 1 = 4.
- [3] with score 3 * 1 = 3. 
- [5] with score 5 * 1 = 5.
- [2,1] with score (2 + 1) * 2 = 6.
Note that subarrays such as [1,4] and [4,3,5] are not considered because their scores are 10 and 36 respectively, while we need scores strictly less than 10.

Example 2:
Input: nums = [1,1,1], k = 5
Output: 5
Explanation:
Every subarray except [1,1,1] has a score less than 5.
[1,1,1] has a score (1 + 1 + 1) * 3 = 9, which is greater than 5.Thus, there are 5 subarrays having scores less than 5.
 
Constraints:
- 1 <= nums.length <= 10^5
- 1 <= nums[i] <= 10^5
- 1 <= k <= 10^15
--------------------------------------------------------------------------------
Attempt 1: 2023-02-08
Solution 1:  Not fixed length Sliding Window (10 min)
class Solution {
    public long countSubarrays(int[] nums, long k) {
        long count = 0;
        int i = 0;
        long sum = 0;
        for(int j = 0; j < nums.length; j++) {
            sum += nums[j];
            while(sum * (j - i + 1) >= k) {
                sum -= nums[i];
                i++;
            }
            count += j - i + 1;
        }
        return count;
    }
}

Time Complexity: O(N)
Space Complexity: O(1)

We can use Sliding Window to expand on right and shrink on left is because the given nums[i] is positive only as 1 <= nums[i] <= 10^5, which means if expand any new element on right will monopoly increasing (subarray length increase 1 multiply increased sum), and shrink any old element on left will monopoly decreasing (subarray length decrease 1 multiply decreased sum), which match the usage scenario of Sliding Window.
Refer to
https://leetcode.com/problems/count-subarrays-with-score-less-than-k/solutions/2138778/sliding-window/
This problem is similar to L713.Subarray Product Less Than K (Ref.L325,L560,L2302).
We use a sliding window technique, tracking the sum of the subarray in the window.
The score of the subarray in the window is sum * (i - j + 1). We move the left side of the window, decreasing sum, if that score is equal or greater than k.
Note that element i forms i - j + 1 valid subarrays. This is because subarrays [j + 1, i], [j + 2, i] ... [i, i] are valid if subarray [j, i] is valid.
public long countSubarrays(int[] nums, long k) {
    long sum = 0, res = 0;
    for (int i = 0, j = 0; i < nums.length; ++i) {
        sum += nums[i];
        while (sum * (i - j + 1) >= k)
            sum -= nums[j++];
        res += i - j + 1;
    }
    return res;
}

--------------------------------------------------------------------------------
Refer to
https://algo.monster/liteproblems/2302
Problem Description
The problem provides us with an array of positive integers, nums, and a number k. Our task is to find the number of contiguous non-empty subarrays within nums where the score of the subarray is less than k. The score of a subarray is calculated by multiplying the sum of the elements in the subarray by the length of the subarray. A subarray is just a sequence of elements from the array that are adjacent to each other.
Intuition
To solve this problem, we need to find a way to efficiently calculate the score for all possible subarrays in nums and count how many of them have a score less than k. Doing this directly would require examining every subarray separately, which can be very inefficient for large arrays.
The intuition behind the solution is to use a sliding window approach that allows us to calculate the score of subarrays dynamically as we expand and contract the window. By keeping track of the sum of elements currently in the window, we can determine the score quickly for the current window size.
Here's the general idea:
- We start with a window at the beginning of the array.
- We expand the window to the right by adding elements until the score exceeds or equals k.
- When the score is equal to or greater than k, we shrink the window from the left by removing elements until the score is less than k again.
- For each window size, we count the number of valid subarrays that can be formed, which is equivalent to the number of elements we can add to the right of the current window while maintaining a score less than k.
This approach ensures we only calculate the score for relevant subarrays and prevents unnecessary recalculations, leading us to the solution in an efficient manner.
Solution Approach
The implementation of the solution uses a two-pointer (or sliding window) approach that keeps track of the current subarray being considered. These two pointers are denoted as i (the right pointer) and j (the left pointer), which represent the current bounds of the subarray.
Here is a step-by-step explanation of the algorithm:
1.Initialize ans to 0; this will count the number of valid subarrays. Also, initialize s to 0; this will hold the sum of the elements of the current subarray. Initialize the left pointer j to 0, which represents the start of the current subarray.
2.Iterate over each element v in nums using its index i, which acts as the end of the subarray. This loop will expand the window to the right.
- Add the value of the current element, v, to s, which maintains the sum of the subarray from index j to i.
3.While the score of the current subarray is greater than or equal to k (i.e., s * (i - j + 1) >= k), remove elements from the start of the subarray to reduce the score.
- Subtract nums[j] from s to reduce the sum. This corresponds to "removing" the element at the start of the subarray.
- Increment j to effectively shrink the window from the left.
4.At this point, the sum multiplied by the window length is less than k. Therefore, for the current end of the window (i), we can count the number of valid subarrays that end at i as i - j + 1, since we can form a valid subarray by starting from any element between j and i.
- Add i - j + 1 to ans, which accumulates the number of valid subarrays.
5.After the iteration is complete, ans will hold the total number of valid subarrays, and we return this value as the final answer.
By using this algorithm, we avoid explicitly calculating the score for every possible subarray, and we efficiently count the valid subarrays by maintaining an ongoing sum and adjusting the window size. The algorithm has an overall time complexity of O(n) because each element is added to s and removed from s at most once, keeping the number of operations linear with the size of the input array.
Example Walkthrough
Let's walk through the provided solution with an example. Suppose our array nums is [2, 1, 4, 1] and the number k is 8.
We will follow the steps described in the solution approach:
We initialize ans to 0, which will store the total count of valid subarrays, s to 0 for the sum of the current subarray elements, and the left pointer j to 0.
We begin iterating over nums with the right pointer i. For each element v in nums, we perform the following steps:
i = 0, v = 2: Add 2 to s. s becomes 2. (s * (i - j + 1)), which is (2 * (0 - 0 + 1)) = 2, is less than 8, so we add i - j + 1 = 0 - 0 + 1 = 1 to ans.
i = 1, v = 1: Add 1 to s. s becomes 3. (s * (i - j + 1)), which is (3 * (1 - 0 + 1)) = 6, is less than 8, so we add i - j + 1 = 1 - 0 + 1 = 2 to ans.
i = 2, v = 4: Add 4 to s. s becomes 7. (s * (i - j + 1)), which is (7 * (2 - 0 + 1)) = 21, is greater than 8, so we start shrinking the window from the left:
We subtract num[j] which is 2 from s and increment j. Now s is 5 and j is 1. The updated score is (5 * (2 - 1 + 1)) = 10, which is still greater than 8.
We again subtract num[j], now 1, from s and increment j. Now s is 4 and j is 2. The score is (4 * (2 - 2 + 1)) = 4, which is less than 8. Now we add i - j + 1 = 2 - 2 + 1 = 1 to ans.
i = 3, v = 1: Add 1 to s. s becomes 5. (s * (i - j + 1)), which is (5 * (3 - 2 + 1)) = 10, is again greater than 8. We need to shrink the window:
We do not need to remove any elements from the window since j is already at 2 and score 10 is from subarray [4, 1]. Now since we cannot shrink the window and the score is greater than k, we simply move on.
At the end of the iteration, we have the total number of valid subarrays which is the value of ans. By adding the counts at each step, ans = 1 + 2 + 1 = 4. Thus, [2], [2, 1], [1] and [1, 4] are valid subarrays whose scores are less than 8, and the final answer is 4.
Java Solution
class Solution {
    public long countSubarrays(int[] nums, long k) {
        long count = 0; // To store the number of subarrays
        long sum = 0; // Sum of the elements in the current subarray
        int start = 0; // Start index for the current subarray

        // Traverse through the array starting from the 0th element
        for (int end = 0; end < nums.length; ++end) {
            sum += nums[end]; // Add the current element to sum
          
            // Shrink the subarray from the left if the condition is violated
            // sum * length should be less than k
            while (sum * (end - start + 1) >= k) {
                sum -= nums[start]; // Removing the element from the start of subarray
                start++; // Increment the start index
            }
          
            // At this point, for each element nums[end], we find how many subarrays ending at 'end' are valid
            // The number of valid subarrays is given by the difference btw current end and the new start position
            count += end - start + 1;
        }
        return count; // Return the total count of valid subarrays
    }
}
Time and Space Complexity
Time Complexity
The given code uses a sliding window technique to count the number of subarrays whose elements product is less than k. In this code, two pointers (i and j) are used, which move forward through the array without stepping backwards. This results in each element being considered only once by each pointer, resulting in a linear traversal.
Thus, the time complexity of this algorithm is O(n), where n is the number of elements in the array nums. This is because both pointers i and j can only move from the start to the end of the array once, and the operations inside the for-loop and while-loop are all constant time operations.
Space Complexity
The space complexity is determined by the extra space used aside from the input. In this case, only a fixed number of variables (ans, s, j, i, v) are used. These do not depend on the size of the input array. Therefore, the space complexity of the code is O(1), which is constant space complexity since no additional space that grows with the input size is used.

Refer to
L713.Subarray Product Less Than K (Ref.L325,L560,L2302)
