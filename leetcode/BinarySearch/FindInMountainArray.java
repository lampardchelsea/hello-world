https://leetcode.com/problems/find-in-mountain-array/

(This problem is an interactive problem.)

You may recall that an array arr is a mountain array if and only if:

- arr.length >= 3
- There exists some i with 0 < i < arr.length - 1 such that:
	- arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
	- arr[i] > arr[i + 1] > ... > arr[arr.length - 1]
Given a mountain array mountainArr, return the minimum index such that mountainArr.get(index) == target. If such an index does not exist, return -1.

You cannot access the mountain array directly. You may only access the array using a MountainArray interface:

- MountainArray.get(k) returns the element of the array at index k (0-indexed).
- MountainArray.length() returns the length of the array.
Submissions making more than 100 calls to MountainArray.get will be judged Wrong Answer. Also, any solutions that attempt to circumvent the judge will result in disqualification.

Example 1:
```
Input: array = [1,2,3,4,5,3,1], target = 3
Output: 2
Explanation: 3 exists in the array, at index=2 and index=5. Return the minimum index, which is 2.
```

Example 2:
```
Input: array = [0,1,2,4,2,1], target = 3
Output: -1
Explanation: 3 does not exist in the array, so we return -1.
```
 
Constraints:
- 3 <= mountain_arr.length() <= 104
- 0 <= target <= 109
- 0 <= mountain_arr.get(index) <= 109
---
Attempt 1: 2022-09-21 

Wrong solution (Similar as L162, even no specific target, but still able to use template similar to Find Target Occurrence, just change the condition as nums[mid] == target to nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1], but error out "You made too many calls to MountainArray.get()."), because we call too many times of mountainArr.get() during find peak element
```
        // Since given array length >= 3, no need check corner case when length = 1 or 2  
        // Find peak element (Refer to L162)  
        // Set 'lo' = 1 and 'hi' = array length - 2, to avoid index out of boundary  
        // when calling mountainArr.get(mid - 1) and mountainArr.get(mid + 1)  
        int lo = 1;  
        int hi = mountainArr.length() - 2;  
        int peak = 0;  
        while(lo <= hi) {  
            int mid = lo + (hi - lo) / 2;  
            if(mountainArr.get(mid) > mountainArr.get(mid - 1) && mountainArr.get(mid) > mountainArr.get(mid + 1)) {  
                peak = mid;  
                break;  
            } else if(mountainArr.get(mid) < mountainArr.get(mid - 1)) {  
                hi = mid - 1;  
            } else {  
                lo = mid + 1;  
            }  
        }
```

Failure as 77/78, last test case not able to pass
```
/** 
 * // This is MountainArray's API interface. 
 * // You should not implement it, or speculate about its implementation 
 * interface MountainArray { 
 *     public int get(int index) {} 
 *     public int length() {} 
 * } 
 */ 
class Solution { 
    public int findInMountainArray(int target, MountainArray mountainArr) { 
        // Since given array length >= 3, no need check corner case when length = 1 or 2 
        // Find peak element (Refer to L162) 
        // Set 'lo' = 1 and 'hi' = array length - 2, to avoid index out of boundary 
        // when calling mountainArr.get(mid - 1) and mountainArr.get(mid + 1) 
        int lo = 1; 
        int hi = mountainArr.length() - 2; 
        int peak = 0; 
        while(lo <= hi) { 
            int mid = lo + (hi - lo) / 2; 
            if(mountainArr.get(mid) > mountainArr.get(mid - 1) && mountainArr.get(mid) > mountainArr.get(mid + 1)) { 
                peak = mid; 
                break; 
            } else if(mountainArr.get(mid) < mountainArr.get(mid - 1)) { 
                hi = mid - 1; 
            } else { 
                lo = mid + 1; 
            } 
        } 
         
        // Find target on left monotonic increasing subarray (Refer to L704) 
        // If not able to find target and return directly, then move on to 
        // right monotonic decreasing subarray, no need to return -1 now 
        lo = 0; 
        hi = peak; 
        while(lo <= hi) { 
            int mid = lo + (hi - lo) / 2; 
            if(mountainArr.get(mid) == target) { 
                return mid; 
            } else if(mountainArr.get(mid) > target) { 
                hi = mid - 1; 
            } else { 
                lo = mid + 1; 
            } 
        } 
         
        // Find target on right monotonic decreasing subarray 
        lo = peak; 
        hi = mountainArr.length() - 1; 
        while(lo <= hi) { 
            int mid = lo + (hi - lo) / 2; 
            if(mountainArr.get(mid) == target) { 
                return mid; 
            } else if(mountainArr.get(mid) > target) { 
                lo = mid + 1; 
            } else { 
                hi = mid - 1; 
            } 
        } 
         
        // If not able to find in either side return -1 
        return -1; 
    } 
}
```

Require a new way to find peak element with less call of mountainArr.get() method

Two Times Binary Search (360min, too long to figure out how to find peak element with less call of mountainArr.get() method)

Template refer to
https://leetcode.com/problems/find-peak-element/discuss/788474/General-Binary-Search-Thought-Process-%3A-4-Templates
```
class Solution: 
    def findPeakElement(self, nums: List[int]) -> int: 
        left =0 
        right = len(nums)-1 
        while left < right: 
            mid = left + (right - left ) //2 
            if nums[mid] > nums[mid+1]: # True Condition # Dec function # go left # Find First True i.e first elem where this condition will be True 
                right = mid # include mid # mid is potential solution  
            else: 
                left = mid +1 
        return left
```

Summary of 2 most frequently used binary search templates
https://leetcode.com/problems/find-in-mountain-array/discuss/317607/JavaC++Python-Triple-Binary-Search/294169
One is return index during the search:
```
while lo <= hi: 
  mid = (lo+hi)/2 
  if nums[mid] == target: 
    return mid 
  if nums[mid] > target: 
    hi = mid-1 
  else: 
    lo = mid+1 
return -1
```
Another more frequently used binary search template is for searching lowest element satisfy function(i) == True (the array should satisfy function(x) == False for 0 to i-1, and function(x) == True for i to n-1, and it is up to the question to define the function, like in the find peak element problem, function(x) can be nums[x] < nums[x+1] ), there are 2 ways to write it:
```
while lo <= hi: 
  mid = (lo+hi)/2 
  if function(mid): 
    hi = mid-1 
  else: 
    lo = mid+1 
return lo
```
or
```
while lo <  hi: 
  mid = (lo+hi)/2 
  if function(mid): 
    hi = mid 
  else: 
    lo = mid+1 
return lo
```
No matter which one you use, just be careful about updating the hi and lo, which could easily lead to infinite loop. Some binary question is searching a floating number and normally the question will give you a precision, in which case you don't need to worry too much about the infinite loop but your while condition will become something like "while lo+1e-7<hi"

Implementation for L1095
```
        int lo = 0; 
        int hi = mountainArr.length() - 1; 
        // Find the peak index 
        while (lo < hi) { 
            int mid = lo + (hi - lo) / 2; 
            if (mountainArr.get(mid) > mountainArr.get(mid + 1)) { 
                // include mid since mid is potential solution of peak
                hi = mid; 
            } else {  
                lo = mid + 1; 
            } 
        }
```

Full code
https://leetcode.com/problems/find-in-mountain-array/discuss/378052/Binary-Search-Thinking-Process
If we divide A in half, one subarray is in the increasing / decreasing order, the other is a mountain array that has the peak index. That helps abandon one subarray. Then in the mountain subarray, we keep dividing...Until the searching space is exhausted, we can find the peak index. The loop invariant is the peak index is in [left, right].
The peak index divides A in half, one subarray is in the increasing order, the other is in the decreasing order. We aim to find the index of target in A if target exists. We can do Binary Search on two subarrays separately. The loop invariant is the target index is in [left, right].

```
/** 
 * // This is MountainArray's API interface. 
 * // You should not implement it, or speculate about its implementation 
 * interface MountainArray { 
 *     public int get(int index) {} 
 *     public int length() {} 
 * } 
 */ 
class Solution { 
    public int findInMountainArray(int target, MountainArray mountainArr) {         
        int lo = 0; 
        int hi = mountainArr.length() - 1; 
        // Find the peak index 
        while (lo < hi) { 
            int mid = lo + (hi - lo) / 2; 
            if (mountainArr.get(mid) > mountainArr.get(mid + 1)) { 
                hi = mid; 
            } else {  
                lo = mid + 1; 
            } 
        } 
         
        int peak = lo; 
         
        // Find target on left monotonic increasing subarray (Refer to L704) 
        // If not able to find target and return directly, then move on to 
        // right monotonic decreasing subarray, no need to return -1 now 
        lo = 0; 
        hi = peak; 
        while(lo <= hi) { 
            int mid = lo + (hi - lo) / 2; 
            if(mountainArr.get(mid) == target) { 
                return mid; 
            } else if(mountainArr.get(mid) > target) { 
                hi = mid - 1; 
            } else { 
                lo = mid + 1; 
            } 
        } 
         
        // Find target on right monotonic decreasing subarray 
        lo = peak; 
        hi = mountainArr.length() - 1; 
        while(lo <= hi) { 
            int mid = lo + (hi - lo) / 2; 
            if(mountainArr.get(mid) == target) { 
                return mid; 
            } else if(mountainArr.get(mid) > target) { 
                lo = mid + 1; 
            } else { 
                hi = mid - 1; 
            } 
        } 
         
        // If not able to find in either side return -1 
        return -1; 
    } 
}

Space Complexity: O(1)       
Time Complexity: O(logn)
```
