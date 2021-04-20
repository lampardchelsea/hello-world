/**
Refer to
https://leetcode.com/problems/shortest-unsorted-continuous-subarray/
Given an integer array nums, you need to find one continuous subarray that if you only sort this subarray in ascending order, 
then the whole array will be sorted in ascending order.

Return the shortest such subarray and output its length.

Example 1:
Input: nums = [2,6,4,8,10,9,15]
Output: 5
Explanation: You need to sort [6, 4, 8, 10, 9] in ascending order to make the whole array sorted in ascending order.

Example 2:
Input: nums = [1,2,3,4]
Output: 0

Example 3:
Input: nums = [1]
Output: 0

Constraints:
1 <= nums.length <= 104
-105 <= nums[i] <= 105

Follow up: Can you solve it in O(n) time complexity?
*/

// Solution 1: Using sort
// Refer to
// https://leetcode.com/problems/shortest-unsorted-continuous-subarray/solution
/**
Algorithm
Another very simple idea is as follows. We can sort a copy of the given array numsnums, say given by nums_sorted. 
Then, if we compare the elements of numsnums and nums_sorted, we can determine the leftmost and rightmost elements 
which mismatch. The subarray lying between them is, then, the required shorted unsorted subarray.
public class Solution {
    public int findUnsortedSubarray(int[] nums) {
        int[] snums = nums.clone();
        Arrays.sort(snums);
        int start = snums.length, end = 0;
        for (int i = 0; i < snums.length; i++) {
            if (snums[i] != nums[i]) {
                start = Math.min(start, i);
                end = Math.max(end, i);
            }
        }
        return (end - start >= 0 ? end - start + 1 : 0);
    }
}
Complexity Analysis
Time complexity : O(nlogn). Sorting takes n\log nnlogn time.
Space complexity : O(n). We are making copy of original array.
*/
class Solution {
    public int findUnsortedSubarray(int[] nums) {
        int[] cnums = nums.clone();
        Arrays.sort(cnums);
        int start = nums.length;
        int end = 0;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] != cnums[i]) {
                start = Math.min(start, i);
                end = Math.max(end, i);
            }
        }
        return end - start >= 0 ? end - start + 1 : 0;
    }
}

// Solution 2: Stack
// Refer to
// https://leetcode.com/problems/shortest-unsorted-continuous-subarray/solution
/**
The idea behind this approach is also based on selective sorting. We need to determine the correct position of the minimum 
and the maximum element in the unsorted subarray to determine the boundaries of the required unsorted subarray.

To do so, in this implementation, we make use of a stack. We traverse over the nums array starting from the beginning. 
As we go on facing elements in ascending order(a rising slope), we keep on pushing the elements' indices over the stack. 
This is done because such elements are in the correct sorted order(as it seems till now). As soon as we encounter a falling slope, 
i.e. an element nums[j] which is smaller than the element on the top of the stack, we know that nums[j] 
isn't at its correct position.

In order to determine the correct position of nums[j], we keep on popping the elemnents from the top of the stack
until we reach the stage where the element(corresponding to the index) on the top of the stack is lesser than nums[j]. 
Let's say the popping stops when the index on stack's top is k. Now, nums[j] has found its correct position. 
It needs to lie at an index k + 1.

We follow the same process while traversing over the whole array, and determine the value of minimum such k. This marks the 
left boundary of the unsorted subarray.

Similarly, to find the right boundary of the unsorted subarray, we traverse over the nums array backwards. This time we keep 
on pushing the elements if we see a falling slope. As soon as we find a rising slope, we trace forwards now and determine the 
larger element's correct position. We do so for the complete array and thus, determine the right boundary.

We can look at the figure below for reference. We can observe that the slopes directly indicate the relative ordering. We can 
also observe that the point b needs to lie just after index 0 marking the left boundary and the point aa needs to lie just 
before index 7 marking the right boundary of the unsorted subarray.
*/






