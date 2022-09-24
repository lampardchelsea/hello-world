/**
 * Refer to
 * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/#/description
 * Follow up for "Find Minimum in Rotated Sorted Array":
 * What if duplicates are allowed?
 * Would this affect the run-time complexity? How and why?
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * Find the minimum element.
 * The array may contain duplicates.
 * 
 * Solution
 * https://segmentfault.com/a/1190000003488815
 * 二分递归法
 * 复杂度
 * 时间 O(N) 空间 O(N) 递归栈空间
 * 思路
 * 如果有重复的话，一旦中间和右边是相等的，就无法确定是否在左半边还是右半边，这时候我们必须对两边都递归求解。
 * 如果nums[max]大于等于nums[mid]，则右边有可能有，如果nums[max]小于等于nums[mid]，则左边有可能有。
 * 注意
 * 要先将左和右的最小值初始化最大整数，然后求解后，最后返回其中较小的那个
 * 
 * https://leetcode.com/submissions/detail/100222655/
 */
public class FindMinimumInRotatedSortedArrayII {
    public int findMin(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }
    
    public int helper(int[] nums, int min, int max) {
        if(min == max) {
            return nums[min];
        }
        //int leftMin = 0, rightMin = 0;
        // E.g If randomly set up as 0, 0 and use {1, 3} to test, 
        // it will return as 0, which suppose to return 1
        // Important: To find the minimum result on each side, must
        // initialize two variables as Integer.MAX_VALUE.
        // 先将右边和左边可能找到的值都初始化为最大
        int rightMin = Integer.MAX_VALUE, leftMin = Integer.MAX_VALUE;
        int mid = min + (max - min)/2;
        if(nums[mid] >= nums[max]) {
            rightMin = helper(nums, mid + 1, max);
        } 
        if(nums[mid] <= nums[max]){
            leftMin = helper(nums, min, mid);
        }
        return Math.min(leftMin, rightMin);
    }
    
    public static void main(String[] args) {
    	
    }
}



Attempt 1: 2022-09-23 

Solution 1: Modified Find Lower Boundary template solution (10min, requirement instead of finding a specific target we have to find minimum number on the array,  have to setup a auxiliary variable 'min' to track the minimum number during each shrink round, leverage the attribute of each round we can find a more concise monotonically increasing section, refer to L153/P12.10.Find Minimum in Rotated Sorted Array, also use the tech how to remove duplicate elements by shrink pointer as 'lo++' or 'hi--', refer to L81/P12.9.Search in Rotated Sorted Array II)
```
class Solution { 
    public int findMin(int[] nums) { 
        int len = nums.length; 
        int lo = 0; 
        int hi = len - 1; 
        int min = Integer.MAX_VALUE; 
        while(lo <= hi) { 
            int mid = lo + (hi - lo) / 2; 
            // left side is monotonically increasing, pivot on right side 
            // Note: not include nums[mid] == nums[lo] here, reason same as  
            // L81 have to breakout duplicates when nums[mid] == nums[lo] separately 
            if(nums[mid] > nums[lo]) { 
                min = Math.min(min, nums[lo]); 
                lo = mid + 1; 
            // right side is monotonically increasing, pivot on left side 
            } else if(nums[mid] < nums[lo]) { 
                min = Math.min(min, nums[mid]); 
                hi = mid - 1; 
            } else { 
                // If no 'min = Math.min(min, nums[lo])' update here, test failure on 
                // Input: [1] 
                // Output: 2147483647 
                // Expected: 1 
                // -------------------------- 
                // If no 'min = Math.min(min, nums[lo])' in if(nums[mid] == nums[lo]) branch 
                // e.g 
                // nums={1}, len=1, lo=0, hi=1-1=0, min=INT_MAX 
                // -------------------------- 
                // Round 1:  
                // lo=0 <= hi=0 -> mid=0 
                // nums[mid]=nums[0] == nums[lo]=nums[0] -> lo++=1 
                // lo=1 > hi=0 while loop end -> min=INT_MAX didn't change as wrong answer 
                // -------------------------- 
                // If have 'min = Math.min(min, nums[lo])' in if(nums[mid] == nums[lo]) branch 
                // Still use same example 
                // e.g 
                // nums={1}, len=1, lo=0, hi=1-1=0, min=INT_MAX 
                // -------------------------- 
                // Round 1: 
                // lo=0 <= hi=0 -> mid=0 
                // nums[mid]=nums[0] == nums[lo]=nums[0]  
                // -> min = Math.min(min, nums[lo]) -> min=nums[lo]=1 then lo++=1 
                // lo=1 > hi=0 while loop end -> min=1 match the answer 
                min = Math.min(min, nums[lo]);  
                lo++; 
            } 
        } 
        return min; 
    } 
}

Space Complexity: O(1)           
Time Complexity: O(logn)
```

Refer to 
Template from L153/P12.10.Find Minimum in Rotated Sorted Array
https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/discuss/48748/Very-Simple-Java-Binary-Search/446597
```
class Solution {  
    public int findMin(int[] nums) {  
        int start = 0, end = nums.length-1;  
        int smallest = Integer.MAX_VALUE;  
        while(start<=end){  
            int mid = (start+end)/2;  
            if(nums[mid] >= nums[start]){  
                smallest = Math.min(nums[start],smallest);  
                start = mid+1;  
            }else{  
                smallest = Math.min(nums[mid],smallest);  
                end = mid - 1;  
            }  
        }  
        return smallest;  
    }  
}
```

Template from L81/P12.9.Search in Rotated Sorted Array II
https://www.cnblogs.com/grandyang/p/4325840.html
这道是之前那道 Search in Rotated Sorted Array 的延伸，现在数组中允许出现重复数字，这个也会影响我们选择哪半边继续搜索，由于之前那道题不存在相同值，我们在比较中间值和最右值时就完全符合之前所说的规律：如果中间的数小于最右边的数，则右半段是有序的，若中间数大于最右边数，则左半段是有序的。而如果可以有重复值，就会出现来面两种情况，[3 1 1] 和 [1 1 3 1]，对于这两种情况中间值等于最右值时，目标值3既可以在左边又可以在右边，那怎么办么，对于这种情况其实处理非常简单，只要把最右值向左一位即可继续循环，如果还相同则继续移，直到移到不同值为止，然后其他部分还采用 Search in Rotated Sorted Array 中的方法，可以得到代码如下
```
class Solution { 
public: 
    bool search(vector<int>& nums, int target) { 
        int n = nums.size(), left = 0, right = n - 1; 
        while (left <= right) { 
            int mid = (left + right) / 2; 
            if (nums[mid] == target) return true; 
            if (nums[mid] < nums[right]) { 
                if (nums[mid] < target && nums[right] >= target) left = mid + 1; 
                else right = mid - 1; 
            } else if (nums[mid] > nums[right]){ 
                if (nums[left] <= target && nums[mid] > target) right = mid - 1; 
                else left = mid + 1; 
            } else --right; // or ++left;
        } 
        return false; 
    } 
};
```

---
Solution 2: JiuZhang Template solution (10min, core concept: Things will be easy when only two elements left)
```
class Solution { 
    public int findMin(int[] nums) { 
        int len = nums.length; 
        int lo = 0; 
        int hi = len - 1; 
        while(lo + 1 < hi) { 
            int mid = lo + (hi - lo) / 2; 
            if(nums[mid] > nums[hi]) { 
                lo = mid; 
            } else if(nums[mid] < nums[hi]) { 
                hi = mid; 
            } else { 
                hi--; 
            } 
        } 
        return Math.min(nums[lo], nums[hi]); 
    } 
}

Space Complexity: O(1)            
Time Complexity: O(logn)
```

Refer to
https://aaronice.gitbook.io/lintcode/binary-search/find-minimum-in-rotated-sorted-array-ii
作为Find Minimum in Rotated Sorted Array 的follow-up，差别在于是否有重复。跟Search in Rotated Sorted Array II 类似，处理nums[mid] == nums[right]的情况即可，移动搜索边界right--

```
class Solution {
    public int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int left = 0, right = nums.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            // Must compare with 'right'
            if (nums[mid] > nums[right]) {
                left = mid;
            } else if (nums[mid] < nums[right]) {
                right = mid;
            } else {
                right--; // lo++ won't work, e.g [1,3,3], expect output = 1, actual output = 3
            }
        }
        return Math.min(nums[left], nums[right]);
    }
}
```

---
Solution 3:  No template (180min, too long to derive the details)

Same style as L153 Solution 3
```
class Solution { 
    public int findMin(int[] nums) { 
        int len = nums.length; 
        int lo = 0; 
        int hi = len - 1; 
        while(lo < hi) { 
            int mid = lo + (hi - lo) / 2; 
            if(nums[mid] < nums[hi]) { 
                hi = mid; 
            } else if(nums[mid] > nums[hi]) { 
                lo = mid + 1; 
            } else { 
                hi--; 
            } 
        } 
        return nums[lo]; 
    } 
}

Space Complexity: O(1)           
Time Complexity: O(logn)
```

