/**
 Refer to
 https://leetcode.com/problems/next-greater-element-ii/
 Given a circular array (the next element of the last element is the first element of the array), 
 print the Next Greater Number for every element. The Next Greater Number of a number x is the 
 first greater number to its traversing-order next in the array, which means you could search 
 circularly to find its next greater number. If it doesn't exist, output -1 for this number.

Example 1:
Input: [1,2,1]
Output: [2,-1,2]
Explanation: The first 1's next greater number is 2; 
The number 2 can't find next greater number; 
The second 1's next greater number needs to search circularly, which is also 2.
Note: The length of given array won't exceed 10000.
*/
// Solution 1: Brutal force
// Refer to
// https://leetcode.com/problems/next-greater-element-ii/solution/
/**
 Approach #1 Brute Force (using Double Length Array) [Time Limit Exceeded]
In this method, we make use of an array doublenumsdoublenums which is formed by concatenating 
two copies of the given numsnums array one after the other. Now, when we need to find out the 
next greater element for nums[i], we can simply scan all the elements doublenums[j], such that 
i < j < length(doublenums). The first element found satisfying the given condition is the required 
result for nums[i]. If no such element is found, we put a -1 at the appropriate position in the 
resres array.
Complexity Analysis
Time complexity : O(n^2). 
The complete doublenumsdoublenums array(of size 2n) is scanned for all the elements of nums in the worst case.
Space complexity : O(n). doublenumsdoublenums array of size 2n is used. resres array of size n is used.
*/
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        int[] result = new int[nums.length];
        int[] doublenums = new int[nums.length * 2];
        for(int i = 0; i < nums.length; i++) {
            doublenums[i] = nums[i];
            doublenums[i + nums.length] = nums[i];
        }
        for(int i = 0; i < nums.length; i++) {
            result[i] = -1;
            for(int j = i + 1; j < doublenums.length; j++) {
                if(doublenums[j] > doublenums[i]) {
                    result[i] = doublenums[j];
                    break;
                }
            }
        }
        return result;
    }
}

// Solution 2:
// https://leetcode.com/problems/next-greater-element-ii/solution/
/**
 Approach #2 Better Brute Force [Accepted]
 Instead of making a double length copy of numsnums array , we can traverse circularly 
 in the numsnums array by making use of the modulus % operator. For every element 
 nums[i], we start searching in the nums array(of length n) from the index (i+1) and 
 look at the next(cicularly) n−1 elements. For nums[i] we do so by scanning over nums[j], 
 such that (i+1), and we look for the first greater element found. 
 If no such element is found, we put a -1 at the appropriate position in the 
 resres array.
 Complexity Analysis
 Time complexity : O(n^2). The complete numsnums array of size nn is scanned for all the elements of numsnums in the worst case.
 Space complexity : O(n). resres array of size n is used.
*/
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        int[] result = new int[nums.length];
        for(int i = 0; i < nums.length; i++) {
            result[i] = -1;
            for(int j = 1; j < nums.length; j++) {
                if(nums[(i + j) % nums.length] > nums[i]) {
                    result[i] = nums[(i + j) % nums.length];
                    break;
                }
            }
        }
        return result;
    }
}

// Solution 3:
// Refer to
// https://leetcode.com/problems/next-greater-element-ii/discuss/98270/JavaC%2B%2BPython-Loop-Twice
/**
  Similar to 496.Next Greater Element I, but not need to use HashMap like Next Greater Element I,
  because this happen in the same array itself, just need to handle circular as nums[i % len],
  in Next Greater Element I we use HashMap to locate nums2's index associate to nums1's value
*/
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        int len = nums.length;
        int[] result = new int[len];
        for(int i = 0; i < len; i++) {
            result[i] = -1;
        }
        Stack<Integer> stack = new Stack<Integer>();
        for(int i = 0; i < len * 2; i++) {
            while(!stack.isEmpty() && nums[stack.peek()] < nums[i % len]) {
                result[stack.pop()] = nums[i % len];
            }
            stack.push(i % len);
        }
        return result;
    }
}
