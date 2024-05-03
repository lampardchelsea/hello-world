
https://leetcode.com/problems/4sum/
Given an array nums of n integers, return an array of all the unique quadruplets [nums[a], nums[b], nums[c], nums[d]] such that:
- 0 <= a, b, c, d < n
- a, b, c, and d are distinct.
- nums[a] + nums[b] + nums[c] + nums[d] == target
You may return the answer in any order.

Example 1:
Input: nums = [1,0,-1,0,-2,2], target = 0
Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]

Example 2:
Input: nums = [2,2,2,2,2], target = 8
Output: [[2,2,2,2]]

Constraints:
- 1 <= nums.length <= 200
- -10^9 <= nums[i] <= 10^9
- -10^9 <= target <= 10^9
--------------------------------------------------------------------------------
Attempt 1: 2022-09-30
Solution 1:  Binary Search solution (30 min, too long for corner case added requires convert integer to long handling)
class Solution { 
    public List<List<Integer>> fourSum(int[] nums, int target) { 
        Arrays.sort(nums); 
        List<List<Integer>> result = new ArrayList<List<Integer>>(); 
        int len = nums.length; 
        for(int i = 0; i < len - 3; i++) { 
            if(i > 0 && nums[i] == nums[i - 1]) { 
                continue; 
            } 
            // Have to convert integer to long for corner case            
            // Test case: 
            // nums=[1000000000,1000000000,1000000000,1000000000] 
            // target=-294967296 
            // Because if not convert to long 'twoSumTarget' will exceed Integer.MIN_VALUE range: 
            // twoSumTarget = -294967296 - 1000000000 - 1000000000 = -2294967296 
            // Integer.MIN_VALUE = -2147483648 
            // twoSumTarget < Integer.MIN_VALUE, NOT support in integer range, have to convert to long
            //int threeSumTarget = target - nums[i]; 
            long threeSumTarget = (long)target - nums[i]; 
            for(int j = i + 1; j < len - 2; j++) { 
                if(j > i + 1 && nums[j] == nums[j - 1]) { 
                    continue; 
                } 
                //int twoSumTarget = threeSumTarget - nums[j]; 
                long twoSumTarget = threeSumTarget - nums[j]; 
                for(int k = j + 1; k < len - 1; k++) { 
                    if(k > j + 1 && nums[k] == nums[k - 1]) { 
                        continue; 
                    } 
                    //int wanted = twoSumTarget - nums[k]; 
                    long wanted = twoSumTarget - nums[k]; 
                    int lo = k + 1; 
                    int hi = len - 1; 
                    while(lo <= hi) { 
                        int mid = lo + (hi - lo) / 2; 
                        // Refer to 
                        // Is it OK to compare an int and a long in Java ? 
                        // https://stackoverflow.com/questions/11143253/is-it-ok-to-compare-an-int-and-a-long-in-java 
                        // Yes, that's fine. The int will be implicitly converted to a long, 
                        // which can always be done without any loss of information 
                        // e.g here 'nums[mid]' is 'integer', but 'wanted' defined as 'long', 
                        // 'nums[mid]' will implicitly convert to long and compare with 'wanted' 
                        // but not vice versa, "nums[mid] == (int)wanted" won't work 
                        if(nums[mid] == wanted) { 
                            List<Integer> list = new ArrayList<Integer>(); 
                            list.add(nums[i]); 
                            list.add(nums[j]);  
                            list.add(nums[k]); 
                            list.add(nums[mid]); 
                            result.add(list); 
                            break; 
                        } else if(nums[mid] > wanted) { 
                            hi = mid - 1; 
                        } else { 
                            lo = mid + 1; 
                        } 
                    } 
                } 
            } 
        } 
        return result; 
    } 
}

Space Complexity: O(1)  
Time Complexity: O(n^3logn)  
Three for loop cost O(n^3), inside inner for loop binary search for target cost O(logn)

Refer to
Is it OK to compare an int and a long in Java ?
https://stackoverflow.com/questions/11143253/is-it-ok-to-compare-an-int-and-a-long-in-java
Q: Is it OK to compare an int and a long in Java...
long l = 800L
int i = 4

if (i < l) {
 // i is less than l
}
A: Yes, that's fine. The int will be implicitly converted to a long, which can always be done without any loss of information.

Solution 2:  Two Pointers solution (10 min)
class Solution { 
    public List<List<Integer>> fourSum(int[] nums, int target) { 
        Arrays.sort(nums); 
        List<List<Integer>> result = new ArrayList<List<Integer>>(); 
        int len = nums.length; 
        for(int i = 0; i < len - 3; i++) { 
            if(i > 0 && nums[i] == nums[i - 1]) { 
                continue; 
            } 
            long threeSumTarget = (long)target - nums[i]; 
            for(int j = i + 1; j < len - 2; j++) { 
                if(j > i + 1 && nums[j] == nums[j - 1]) { 
                    continue; 
                } 
                long twoSumTarget = threeSumTarget - nums[j]; 
                int lo = j + 1; 
                int hi = len - 1; 
                while(lo < hi) { 
                    long sum = nums[lo] + nums[hi]; 
                    if(sum == twoSumTarget) { 
                        result.add(Arrays.asList(nums[i], nums[j], nums[lo], nums[hi])); 
                        lo++; 
                        hi--; 
                        while(lo < hi && nums[lo] == nums[lo - 1]) { 
                            lo++; 
                        } 
                        while(lo < hi && nums[hi] == nums[hi + 1]) { 
                            hi--; 
                        } 
                    } else if(sum > twoSumTarget) { 
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



Time Complexity:O(n^k−1), or O(n^3) for 4Sum. We have k - 2 loops, and twoSum is O(n). 
Note that for k > 2, sorting the array does not change the overall time complexity. 
Space Complexity:O(n). We need O(k) space for the recursion. k can be the same as nn in the worst case for the generalized algorithm.
Note that, for the purpose of complexity analysis, we ignore the memory required for the output.
