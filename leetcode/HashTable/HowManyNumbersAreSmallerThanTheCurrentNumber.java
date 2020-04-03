/**
 Refer to
 https://leetcode.com/problems/how-many-numbers-are-smaller-than-the-current-number/
 Given the array nums, for each nums[i] find out how many numbers in the array are smaller than it. 
 That is, for each nums[i] you have to count the number of valid j's such that j != i and nums[j] < nums[i].

Return the answer in an array.

Example 1:
Input: nums = [8,1,2,2,3]
Output: [4,0,1,1,3]
Explanation: 
For nums[0]=8 there exist four smaller numbers than it (1, 2, 2 and 3). 
For nums[1]=1 does not exist any smaller number than it.
For nums[2]=2 there exist one smaller number than it (1). 
For nums[3]=2 there exist one smaller number than it (1). 
For nums[4]=3 there exist three smaller numbers than it (1, 2 and 2).

Example 2:
Input: nums = [6,5,4,8]
Output: [2,1,0,3]

Example 3:
Input: nums = [7,7,7,7]
Output: [0,0,0,0]

Constraints:
2 <= nums.length <= 500
0 <= nums[i] <= 100
*/

// Solution 1: HashMap
// Refer to
// https://leetcode.com/problems/how-many-numbers-are-smaller-than-the-current-number/discuss/535421/Java-Clean-HashMap-solution-with-explanation
/**
 So the idea is:
Let's use this input for illustration: [8,1,2,2,3]
Create a copy of the input array. copy = [8,1,2,2,3]
Sort the copy array. copy = [1,2,2,3,8]
Fill the map: number => count (where count is an index in sorted array, 
so first number with index 0 has 0 numbers less than it, index 1 has 1 number less, etc). 
We update only first time we enocunter the number so that way we skip duplicates.
map[1]=>0
map[2]=>1
map[3]=>3
map[8]=>4
We re-use our copy array to get our result, we iterate over original array, and get counts from the map.
[4,0,1,1,3]
*/
class Solution {
    public int[] smallerNumbersThanCurrent(int[] nums) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int[] copy = nums.clone();
        Arrays.sort(copy);
        for(int i = 0; i < nums.length; i++) {
            map.putIfAbsent(copy[i], i);
        }
        for(int i = 0; i < nums.length; i++) {
            copy[i] = map.get(nums[i]);
        }
        return copy;
    }
}

