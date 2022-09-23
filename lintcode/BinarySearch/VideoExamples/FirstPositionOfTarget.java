/**
 * For a given sorted array (ascending order) and a target number, find the first index of 
 * this number in O(log n) time complexity.
 * If the target number does not exist in the array, return -1.
 * Have you met this question in a real interview?
 * Example
 * If the array is [1, 2, 3, 3, 4, 5, 10], for given target 3, return 2.
*/
class Solution {
    /**
     * @param nums: The integer array.
     * @param target: Target to find.
     * @return: The first position of target. Position starts from 0.
     */
    public int binarySearch(int[] nums, int target) {
        // Check on null and empty case
        if(nums == null || nums.length == 0) {
            return -1;
        }
        int start = 0;
        int end = nums.length - 1;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            if(nums[mid] == target) {
                // Not directly return here, we need to find first happening
                // of one value in array, always keep removing the second
                // half of current subset, its tendency is try to find most
                // previous one even we already find one item matching target
                // (Since there are duplicates, so even find a matching item
                // still need continuous process previous section, should
                // not return directly)
                end = mid;
            } else if(nums[mid] < target) {
                // For first position template, 'start = mid - 1' or
                // 'end = mid - 1' are both fine, but this is quite
                // different when come to last position template
                start = mid;
            } else if(nums[mid] > target) {
                end = mid;
            }
        }
        // Check on 'start' first for requirement
        // about find the first position 
        if(nums[start] == target) {
            return start;
        }
        if(nums[end] == target) {
            return end;
        }
        return -1;
    }
}



Attempt 1: 2022-09-23 

Solution 1: Modified Find Lower Boundary template solution (30min, requirement instead of finding a specific target we have to find minimum number on the array,  have to setup a auxiliary variable 'min' to track the minimum number during each shrink round, leverage the attribute of each round we can find a more concise monotonically increasing section)
```
class Solution { 
    public int findMin(int[] nums) { 
        int len = nums.length; 
        int lo = 0; 
        int hi = len - 1; 
        // 'min' used to record minimum value in a monotonically increasing section 
        int min = Integer.MAX_VALUE; 
        // Still use "Find Lower Boundary" template but a bit modification, 
        // since no specific target to search against like conventional template, 
        // we can only recursively shrink either left or right side to find a 
        // monotonically increasing section, but doesn't mean we find the global 
        // minimum number on the array, so we can leverage recursively finding 
        // monotonically increasing section this attribute to add a variable 'min' 
        // which records the minimum number on each round 
        while(lo <= hi) { 
            int mid = lo + (hi - lo) / 2; 
            // left side monotonically increasing, pivot on the right side 
            if(nums[mid] >= nums[lo]) { 
                // Since [lo, mid] is monotonically increasing, nums[lo] is 
                // minimum number on current section 
                min = Math.min(min, nums[lo]); 
                // Discard left side since pivot on the right side, the global 
                // minimum suppose on right side 
                lo = mid + 1; 
            // right side monotonically increasing, pivot on the left side 
            } else { 
                // Since [mid, hi] is monotonically increasing, nums[mid] is 
                // minimum number on current section 
                min = Math.min(min, nums[mid]); 
                // Discard right side since pivot on the left side, the global 
                // minimum suppose on left side 
                hi = mid - 1; 
            } 
        } 
        return min; 
    } 
}

Space Complexity: O(1)          
Time Complexity: O(logn)
```

Refer to
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

---

Solution 2: JiuZhang Template solution (180min, core concept: Things will be easy when only two elements left)
```
class Solution { 
    public int findMin(int[] nums) { 
        int lo = 0;  
        int hi = nums.length - 1;  
        int target = nums[hi]; 
        while(lo + 1 < hi) {  
            int mid = lo + (hi - lo) / 2;  
            if(nums[mid] == target) {  
                hi = mid;     
            } else if(nums[mid] < target) { 
                hi = mid;  
            } else if(nums[mid] > target) {  
                lo = mid;  
            } 
        } 
        if(nums[lo] <= target) {  
            return nums[lo];  
        } else {  
            return nums[hi]; 
        }  
    } 
}

Space Complexity: O(1)           
Time Complexity: O(logn)
```

Refer to JiuZhang Find Position Of Target (always 2 elements left) version
https://github.com/lampardchelsea/hello-world/blob/master/lintcode/BinarySearch/VideoExamples/FirstPositionOfTarget.java
```
/** 
 * For a given sorted array (ascending order) and a target number, find the first index of  
 * this number in O(log n) time complexity. 
 * If the target number does not exist in the array, return -1. 
 * Have you met this question in a real interview? 
 * Example 
 * If the array is [1, 2, 3, 3, 4, 5, 10], for given target 3, return 2. 
*/ 
class Solution { 
    /** 
     * @param nums: The integer array. 
     * @param target: Target to find. 
     * @return: The first position of target. Position starts from 0. 
     */ 
    public int binarySearch(int[] nums, int target) { 
        // Check on null and empty case 
        if(nums == null || nums.length == 0) { 
            return -1; 
        } 
        int start = 0; 
        int end = nums.length - 1; 
        while(start + 1 < end) { 
            int mid = start + (end - start) / 2; 
            if(nums[mid] == target) { 
                // Not directly return here, we need to find first happening 
                // of one value in array, always keep removing the second 
                // half of current subset, its tendency is try to find most 
                // previous one even we already find one item matching target 
                // (Since there are duplicates, so even find a matching item 
                // still need continuous process previous section, should 
                // not return directly) 
                end = mid; 
            } else if(nums[mid] < target) { 
                // For first position template, 'start = mid - 1' or 
                // 'end = mid - 1' are both fine, but this is quite 
                // different when come to last position template 
                start = mid; 
            } else if(nums[mid] > target) { 
                end = mid; 
            } 
        } 
        // Check on 'start' first for requirement 
        // about find the first position  
        if(nums[start] == target) { 
            return start; 
        } 
        if(nums[end] == target) { 
            return end; 
        } 
        return -1; 
    } 
}
```
https://github.com/lampardchelsea/hello-world/blob/master/lintcode/BinarySearch/VideoExamples/FindMinimumInRotatedSortedArray.java
```
/** 
 * Suppose a sorted array is rotated at some pivot unknown to you beforehand. 
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2). 
 * Find the minimum element. 
 * Notice 
 * You may assume no duplicate exists in the array. 
 * 
 * Solution 
 * Refer to 
 * http://blog.leanote.com/post/westcode/%5B%E5%88%B7%E9%A2%98%E7%AC%94%E8%AE%B0%5D-LintCode-159-Find-Minimum-in-Rotated-Sorted-Array 
 * 思路 
 * 这题的难点是找到要搜索的那个目标。 因为给的数据是一个 shifted 之后的 sorted array，那么我我们来分析一下可能出现的几种情况。 
    0 1 2 3 4 5 7 本身就是没有 shift 过的 sorted array 
    3 4 5 7 0 1 2 大概 shift 了一半的 array 
    1 2 3 4 5 7 0 只 shift 了一位，最小值在 array 最后 
 * 我们可以把 shift 之后的 aray 想象成这样一个图形，我们可以看到，shift 之后的 sorted array，前半部分 的最小值肯定是比后半 
 * 部分的最大值要大，即后半部分无论如何也不会大于前半部分的最小值。 
          7 
        5  
      4 
    3 
    --------------- 
                 2 
              1 
            0 
 * 那么我们可以发现我们要找的最小值，肯定是后半部分的第一个值。 那么我们需要找的值就是 第一个比 最后一个值（后半部分最大值） 要小的值。 
 * 再来看看前面几种情况，我们这个思路是否适用。 
    
   0 1 2 3 4 5 7 本身就是 sorted 的，那么第一个比 7 小的值就是0。 
   3 4 5 7 0 1 2 shift 过的 array，那么第一个比2小的值就是0 
   1 2 3 4 5 7 0 这个情况，我们的最小值同时也是最后一个值，那么我们发现这个思路就要稍微改一改，要包括等于最后一个值得情况。所以我们最终要找的值就是 
   第一个 小于或者等于 最后一个数的数 
   妈的怎么感觉这么别扭，中文语法学的不好？？？ 
 * The first element that's smaller or equal to the last element 
*/ 
public class Solution { 
    /** 
     * @param nums: a rotated sorted array 
     * @return: the minimum number in the array 
     */ 
    public int findMin(int[] nums) { 
        // Check null and empty case 
        if(nums == null || nums.length == 0) { 
            return -1; 
        } 
        int start = 0; 
        int end = nums.length - 1; 
        int target = nums[end]; 
        // Find the first item which no larger 
        // than the last item in given nums 
        while(start + 1 < end) { 
            int mid = start + (end - start) / 2; 
            if(nums[mid] == target) { 
                end = mid;    
            } else if(nums[mid] < target) { 
                // Different than template, because we 
                // are trying to find the first item 
                // <= target, when condition satisfy 
                // 'nums[mid] < target', which means 
                // candidates exist on left(inclusive)  
                // of current 'mid', we need to cut off 
                // second half and continue search first 
                // half, that's why use 'end = mid' not 
                // as normal as 'start = mid' in 
                // FirstPositionOfTarget.java, because 
                // in normal case we are finding 'target' 
                // itself, not the item exist in rnage  
                // before 'target' 
                end = mid; 
            } else if(nums[mid] > target) { 
                start = mid; 
            } 
        } 
        // Also need to change final check 
        // E.g 
        // If given {1, 2, 3}, final start = 0, end = 1, 
        // which will go into nums[0] <= 3 branch, 
        // return nums[start] = nums[0] = 1 
        // If given {4, 1, 2, 3}, final start = 0, end = 1, 
        // which will go into nums[0] > 3 branch, 
        // return nums[end] = nums[1] = 1 
        // More detail, why we can directly return 
        // nums[end] if 'nums[start] <= target' ? 
        // Because this 'nums[start]' must be the 
        // peak value after rotation, which the very 
        // previous item of the bottom value, like 4 
        // is the peak, and just before bottom as 1, 
        // what we need to do is return bottom one, 
        // which exactly the first item no larger than 
        // the last item as 3 here 
        if(nums[start] <= target) { 
            return nums[start]; 
        } else { 
            return nums[end]; 
        } 
    } 
}
```
https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/discuss/48484/A-concise-solution-with-proof-in-the-comment/219828
https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/discuss/48484/A-concise-solution-with-proof-in-the-comment/220700
```
public int findMin(int[] nums) { 
        if(nums == null || nums.length == 0){ 
            return -1; 
        } 
        int left = 0, right = nums.length - 1; 
        while(left < right - 1){   //leave the loop when only two elements left. 
            int mid = left + (right - left)/2; 
            if(nums[mid] > nums[right]){ 
                left = mid;  //I decide not to skip mid,  
            }else{ 
                right = mid;    
            } 
        } 
        return Math.min(nums[left], nums[right]);  //things will be easy when only two elements left 
    }
```
1.The range always narrows down.
2 When it comes to two items, the first is larger than the second. and the second is always the answer.
---

Solution 3:  No template (180min, too long to derive the details)
```
class Solution { 
    public int findMin(int[] nums) { 
        int lo = 0; 
        int hi = nums.length -1; 
        while(lo < hi) { 
            int mid = lo + (hi - lo) / 2; 
            if(nums[mid] < nums[hi]) { 
                hi = mid; 
            } else { 
                lo = mid+1; 
            } 
        } 
        return nums[lo]; 
    } 
}

Space Complexity: O(1)           
Time Complexity: O(logn)
```

Refer to
Q1: Why 'hi = mid' not 'hi = mid - 1' ?
https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/solution/252127
https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/solution/844973
```
    int findMin(vector<int>& nums) { 
        int lo = 0, hi = nums.size()-1;  
        while (lo < hi) { 
            int mid = lo + (hi-lo)/2; 
            if (nums[mid] < nums[hi]) { 
                // Case 1) Right side sorted, left side has pivot (minval), go left to find it 
                // Case 2) Both sides sorted, go left to find the smallest value (minval) 
                hi = mid; 
            } else { 
                // Case 1) Left side sorted, right side has pivot (minval), go right to find it 
                lo = mid+1; 
            } 
        }      
        return nums[lo]; 
    }

In another words for condition explain:
If nums[mid] < nums[hi], nums[mid] or some value before it could be our inflection point. Therefore, let hi = mid, including mid in our new search space. 
If nums[mid] >= nums[hi], nums[mid] cannot be our inflection point. Candidates are to the right of it. Let lo = mid + 1, exluding the mid from the search space.
```

Q2 : Why compare between nums[mid] < nums[hi] ? And nums[lo] < nums[mid] not work ?
https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/discuss/158940/Beat-100:-Very-Simple-(Python)-Very-Detailed-Explanation/740208
I think the intuition would be to consider the array as 2 parts and reduce our search space on the following intuition, If we consider the rotation, we see that the minimum element is pushed to the right with each rotation. The property of a sorted array (assuming sort is in ascending order) is that the first element(here the middle) in the array would be less than last element in the array(the right) and this property would be broken when the min element crosses the middle position, which can be identified by comparing the middle and the right most element. That said we can observe that unless the min element crosses the middle index, all elements starting from the middle element to the end would be in sorted order (strictly increasing) in which case we always move the search space to the left of the middle element.
