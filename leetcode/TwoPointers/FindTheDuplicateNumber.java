/**
Refer to
https://leetcode.com/problems/find-the-duplicate-number/
Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.

There is only one duplicate number in nums, return this duplicate number.

Follow-ups:
How can we prove that at least one duplicate number must exist in nums? 
Can you solve the problem without modifying the array nums?
Can you solve the problem using only constant, O(1) extra space?
Can you solve the problem with runtime complexity less than O(n2)?

Example 1:
Input: nums = [1,3,4,2,2]
Output: 2

Example 2:
Input: nums = [3,1,3,4,2]
Output: 3

Example 3:
Input: nums = [1,1]
Output: 1

Example 4:
Input: nums = [1,1,2]
Output: 1

Constraints:
2 <= n <= 3 * 104
nums.length == n + 1
1 <= nums[i] <= n
All the integers in nums appear only once except for precisely one integer which appears two or more times.
*/

// Refer to
// https://leetcode.com/problems/find-the-duplicate-number/solution/
/**
Note
The first two approaches mentioned do not satisfy the constraints given in the prompt, but they are solutions that you might be 
likely to come up with during a technical interview. As an interviewer, I personally would not expect someone to come up with 
the cycle detection solution unless they have heard it before.

Proof
Proving that at least one duplicate must exist in nums is simple application of the pigeonhole principle. Here, each number in 
nums is a "pigeon" and each distinct number that can appear in nums is a "pigeonhole". Because there are n+1 numbers are n distinct 
possible numbers, the pigeonhole principle implies that at least one of the numbers is duplicated.
**/

/**
Approach 1: Sorting
Intuition
If the numbers are sorted, then any duplicate numbers will be adjacent in the sorted array.
Algorithm
Given the intuition, the algorithm follows fairly simply. First, we sort the array, and then we compare each element to the previous 
element. Because there is exactly one duplicated element in the array, we know that the array is of at least length 2, and we can 
return the duplicate element as soon as we find it.
class Solution {
    public int findDuplicate(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i-1]) {
                return nums[i];
            }
        }
        return -1;
    }
}
Complexity Analysis
Time complexity : O(nlgn)
The sort invocation costs O(nlgn) time in Python and Java, so it dominates the subsequent linear scan.
Space complexity : O(1) or O(n)
Here, we sort nums in place, so the memory footprint is constant. If we cannot modify the input array, then we must allocate linear 
space for a copy of nums and sort that instead.
*/
class Solution {
    public int findDuplicate(int[] nums) {
        Arrays.sort(nums);
        for(int i = 1; i < nums.length; i++) {
            if(nums[i] == nums[i - 1]) {
                return nums[i];
            }
        }
        return -1;
    }
}

/**
Approach 2: Set
Intuition
If we store each element as we iterate over the array, we can simply check each element as we iterate over the array.
Algorithm
In order to achieve linear time complexity, we need to be able to insert elements into a data structure (and look them up) 
in constant time. A Set satisfies these constraints nicely, so we iterate over the array and insert each element into seen. 
Before inserting it, we check whether it is already there. If it is, then we found our duplicate, so we return it.
*/
class Solution {
    public int findDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<Integer>();
        for (int num : nums) {
            if (seen.contains(num)) {
                return num;
            }
            seen.add(num);
        }
        return -1;
    }
}

