/**
Refer to
https://leetcode.com/problems/3sum/
Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? 
Find all unique triplets in the array which gives the sum of zero.

Notice that the solution set must not contain duplicate triplets.

Example 1:
Input: nums = [-1,0,1,2,-1,-4]
Output: [[-1,-1,2],[-1,0,1]]

Example 2:
Input: nums = []
Output: []

Example 3:
Input: nums = [0]
Output: []

Constraints:
0 <= nums.length <= 3000
-105 <= nums[i] <= 105
*/

// Solution 1: Two Pointers
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/lintcode/TwoPoints/VideoExamples/TwoSum/3Sum.java
/**
public class Solution {
    public List<List<Integer>> threeSum(int[] numbers) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(numbers == null || numbers.length < 3) {
            return result;
        }
        // First need to sort as ascending order
        Arrays.sort(numbers);
        for(int i = 0; i < numbers.length - 2; i++) {
            // Check Point 1: skip duplicate triples with the same first numeber
            // E.g if without this check
            // Input
            // [1,0,-1,-1,-1,-1,0,1,1,1]
            // Output
            // [[-1,0,1],[-1,0,1],[-1,0,1],[-1,0,1],[-1,0,1],[-1,0,1],[-1,0,1],[-1,0,1]]
            // Expected
            // [[-1,0,1]]
            // After first sorted
            // [-1,-1,-1,-1,0,0,1,1,1,1]
            // With this check we will remove '-1' on index = 1,2,3 as duplicate as '-1'
            // on index = 0
            if(i > 0 && numbers[i] == numbers[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = numbers.length - 1;
            int target = -numbers[i];
            twoSum(numbers, left, right, target, result);
        }
        return result;
    }
    
    private void twoSum(int[] numbers, int left, int right, int target, List<List<Integer>> result) {
        while(left < right) {
            int temp = numbers[left] + numbers[right];
            if(temp == target) {
                List<Integer> list = new ArrayList<Integer>();
                list.add(-target);
                list.add(numbers[left]);
                list.add(numbers[right]);
                result.add(list);
                left++;
                right--;
                // Check Point 2: skip duplicate pairs with the same left
                // E.g if without this check (even with Check Point 1)
                // Input
                // [1,0,-1,-1,-1,-1,0,1,1,1]
                // After Check Point 1 =>
                // [-1,0,0,1,1,1,1]
                // Output
                // [[-1,0,1],[-1,0,1]]
                // Expected
                // [[-1,0,1]]
                // With this check we will remove combination including second '0' 
                // at index = 2, as same combination as [-1,0,1] which include 
                // first '0' at index = 1
                while(left < right && numbers[left] == numbers[left - 1]) {
                    left++;
                }
                // Check Point 3: skip duplicate pairs with the same right
                while(left < right && numbers[right] == numbers[right + 1]) {
                    right--;
                }
            } else if(temp < target) {
                left++;
            } else {
                right--;
            }
        } 
    }
     
    public static void main(String[] args) {
    	ThreeSum t = new ThreeSum();
    	int[] numbers = {1,0,-1,-1,-1,-1,0,1,1,1};
    	List<List<Integer>> result = t.threeSum(numbers);
    	for(List<Integer> a : result) {
    		System.out.println("----------");
    		for(Integer b : a) {
    			System.out.print(b);
    		}
    	}
    }
}
*/

// Refer to
// https://leetcode.com/problems/3sum/discuss/7380/Concise-O(N2)-Java-solution
/**
The idea is to sort an input array and then run through all indices of a possible first element of a triplet. 
For each possible first element we make a standard bi-directional 2Sum sweep of the remaining part of the array. 
Also we want to skip equal elements to avoid duplicates in the answer without making a set or smth like that.

public List<List<Integer>> threeSum(int[] num) {
    Arrays.sort(num);
    List<List<Integer>> res = new LinkedList<>(); 
    for (int i = 0; i < num.length-2; i++) {
        if (i == 0 || (i > 0 && num[i] != num[i-1])) {
            int lo = i+1, hi = num.length-1, sum = 0 - num[i];
            while (lo < hi) {
                if (num[lo] + num[hi] == sum) {
                    res.add(Arrays.asList(num[i], num[lo], num[hi]));
                    while (lo < hi && num[lo] == num[lo+1]) lo++;
                    while (lo < hi && num[hi] == num[hi-1]) hi--;
                    lo++; hi--;
                } else if (num[lo] + num[hi] < sum) lo++;
                else hi--;
           }
        }
    }
    return res;
}
*/
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        Arrays.sort(nums);
        for(int i = 0; i < nums.length; i++) {
            if(i == 0 || i > 0 && nums[i - 1] != nums[i]) {
                int lo = i + 1;
                int hi = nums.length - 1;
                int target = -nums[i];
                while(lo < hi) {
                    if(nums[lo] + nums[hi] == target) {
                        result.add(Arrays.asList(nums[i], nums[lo], nums[hi]));
                        while(lo < hi && nums[lo] == nums[lo + 1]) {
                            lo++;
                        }
                        while(lo < hi && nums[hi] == nums[hi - 1]) {
                            hi--;
                        }
                        lo++;
                        hi--;
                    } else if(nums[lo] + nums[hi] > target) {
                        hi--;
                    } else {
                        lo++;
                    }
                }
            }
        }
        return result;
    }
}
