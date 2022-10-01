import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/4sum/#/description
 * Given an array S of n integers, are there elements a, b, c, and d in S such that 
 * a + b + c + d = target? Find all unique quadruplets in the array which gives the sum of target.
 * Note: The solution set must not contain duplicate quadruplets.
 * For example, given array S = [1, 0, -1, 0, -2, 2], and target = 0.
	
	A solution set is:
	[
	  [-1,  0, 0, 1],
	  [-2, -1, 1, 2],
	  [-2,  0, 0, 2]
	]

 * 
 * Solution
 * https://segmentfault.com/a/1190000003740669
 * 双指针法
 * 复杂度
 * 时间 O(N^3) 空间 O(1)
 * 思路
 * 和3Sum的思路一样，在计算4Sum时我们可以先选一个数，然后在剩下的数中计算3Sum。
 * 而计算3Sum则同样是先选一个数，然后再剩下的数中计算2Sum。
 */
public class FourSum {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        for(int i = 0; i < nums.length - 3; i++) {
            if(i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            List<List<Integer>> res = threeSum(nums, i, target - nums[i]);
            result.addAll(res);
        }
        return result;
    }
    
    public List<List<Integer>> threeSum(int[] nums, int i, int newTarget) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        // Similar logic as when construct threeSum based on twoSum,
        // the start point of should be m = i + 1
        for(int m = i + 1; m < nums.length - 2; m++) {
            if(m > i + 1 && nums[m] == nums[m - 1]) {
                continue;    
            }
            // Pass both m and i into twoSum for adding both nums[m] and nums[i]
            // into one combination
            List<List<Integer>> res = twoSum(nums, m, i, newTarget - nums[m]);
            result.addAll(res);
        }
        return result;
    }
    
    public List<List<Integer>> twoSum(int[] nums, int m, int i, int newTarget) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        int left = m + 1;
        int right = nums.length - 1;
        while(left < right) {
            if(nums[left] + nums[right] == newTarget) {
                List<Integer> curr = new ArrayList<Integer>();
                // Adding both nums[i] and nums[m]
                curr.add(nums[i]);
                curr.add(nums[m]);
                curr.add(nums[left]);
                curr.add(nums[right]);
                result.add(curr);
                do {
                    left++;
                } while(left < nums.length && nums[left] == nums[left - 1]);
                do {
                    right--;
                } while(right >= 0 && nums[right] == nums[right + 1]);
            } else if(nums[left] + nums[right] > newTarget) {
                right--;
            } else {
                left++;
            }
        }
        return result;
    }
    
    public static void main(String[] args) {
    	
    }
    
}






Attempt 1: 2022-09-30

Solution 1:  Binary Search solution (30 min, too long for corner case added requires convert integer to long handling)
```
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
```

Refer to
Is it OK to compare an int and a long in Java ?
https://stackoverflow.com/questions/11143253/is-it-ok-to-compare-an-int-and-a-long-in-java
Q: Is it OK to compare an int and a long in Java...
```
long l = 800L
int i = 4

if (i < l) {
 // i is less than l
}
```
A: Yes, that's fine. The int will be implicitly converted to a long, which can always be done without any loss of information.

Solution 2:  Two Pointers solution (10 min)
```
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
```
