/**
 Refer to
 https://leetcode.com/problems/next-greater-element-i/
 Same as
 https://github.com/lampardchelsea/hello-world/blob/master/leetcode/array/DailyTemperatures.java
 You are given two arrays (without duplicates) nums1 and nums2 where nums1’s elements are subset 
 of nums2. Find all the next greater numbers for nums1's elements in the corresponding places of nums2.

The Next Greater Number of a number x in nums1 is the first greater number to its right in nums2. 
If it does not exist, output -1 for this number.

Example 1:
Input: nums1 = [4,1,2], nums2 = [1,3,4,2].
Output: [-1,3,-1]
Explanation:
    For number 4 in the first array, you cannot find the next greater number for it in the second array, so output -1.
    For number 1 in the first array, the next greater number for it in the second array is 3.
    For number 2 in the first array, there is no next greater number for it in the second array, so output -1.

Example 2:
Input: nums1 = [2,4], nums2 = [1,2,3,4].
Output: [3,-1]
Explanation:
    For number 2 in the first array, the next greater number for it in the second array is 3.
    For number 4 in the first array, there is no next greater number for it in the second array, so output -1.

Note:
All elements in nums1 and nums2 are unique.
The length of both nums1 and nums2 would not exceed 1000.
*/
// Solution 1: Native O(n^2) while loop check
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        if(nums1.length == 0 || nums2.length == 0 || nums2.length < nums1.length) {
            return new int[0];
        }
        int[] temp = new int[nums2.length];
        temp[nums2.length - 1] = -1;
        for(int i = 0; i <= nums2.length - 2; i++) {
            int j = i + 1;
            while(j < nums2.length && nums2[i] >= nums2[j]) {
                j++;
            }
            if(j == nums2.length) {
                temp[i] = -1;
            } else {
                temp[i] = nums2[j];
            }
        }
        int[] result = new int[nums1.length];
        for(int i = 0; i < nums1.length; i++) {
            int curr = nums1[i];
            for(int j = 0; j < nums2.length; j++) {
                if(nums2[j] == curr) {
                    result[i] = temp[j];
                }
            }
        }
        return result;
    }
}

// Solution 2: Elegant using stack to scan array with O(n) time
// Refer to
// https://leetcode.com/problems/next-greater-element-i/discuss/97595/Java-10-lines-linear-time-complexity-O(n)-with-explanation
/**
 Key observation:
Suppose we have a decreasing sequence followed by a greater number
For example [5, 4, 3, 2, 1, 6] then the greater number 6 is the next greater element 
for all previous numbers in the sequence
We use a stack to keep a decreasing sub-sequence, whenever we see a number x greater than stack.peek() 
we pop all elements less than x and for all the popped ones, their next greater element is x
For example [9, 8, 7, 3, 2, 1, 6]
The stack will first contain [9, 8, 7, 3, 2, 1] and then we see 6 which is greater than 1 so we pop 
1 2 3 whose next greater element should be 6
*/
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        // Map from x to next greater element of x
        Map<Integer, Integer> map = new HashMap<Integer, Integer>(); 
        Stack<Integer> stack = new Stack<Integer>();
        for(int num : nums2) {
            while(!stack.isEmpty() && stack.peek() < num) {
                map.put(stack.pop(), num);
            }
            stack.push(num);
        }
        for(int i = 0; i < nums1.length; i++) {
            nums1[i] = map.getOrDefault(nums1[i], -1);
        }
        return nums1;
    }
}
