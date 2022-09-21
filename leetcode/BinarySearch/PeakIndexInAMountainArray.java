/**
 * Refer to
 * https://leetcode.com/problems/peak-index-in-a-mountain-array/description/
 * Let's call an array A a mountain if the following properties hold:

    A.length >= 3
    There exists some 0 < i < A.length - 1 such that A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1]
    Given an array that is definitely a mountain, return any i such that 
    A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1].

    Example 1:

    Input: [0,1,0]
    Output: 1
    Example 2:

    Input: [0,2,1,0]
    Output: 1
    Note:

    3 <= A.length <= 10000
    0 <= A[i] <= 10^6
    A is a mountain, as defined above.
 * 
 * Solution
 * https://leetcode.com/problems/peak-index-in-a-mountain-array/discuss/139840/Java-O(n)-and-O(log(n))-code
*/
class Solution {
    public int peakIndexInMountainArray(int[] A) {
        if(A == null || A.length == 0) {
            return -1;
        }
        int start = 0;
        int end = A.length - 1;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            if(A[mid + 1] < A[mid]) {
                end = mid;
            } else if(A[mid - 1] < A[mid]) {
                start = mid;
            } else {
                start = mid;
            }
        }
        if(A[start] < A[end]) {
            return end;
        }
        return start;
    }
}



Attempt 1: 2022-09-20 

Solution 1: Binary Search solution (10min, even no specific target, but still able to use template similar to Find Target Occurrence, just change the condition as nums[mid] == target to nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1])

```
class Solution { 
    public int peakIndexInMountainArray(int[] arr) { 
        int len = arr.length; 
        if(len == 0) { 
            return 0; 
        } 
        if(arr[0] > arr[1]) { 
            return 0; 
        } 
        if(arr[len - 2] < arr[len - 1]) { 
            return arr[len - 1]; 
        } 
        int lo = 1; 
        int hi = len - 2; 
        while(lo <= hi) { 
            int mid = lo + (hi - lo) / 2; 
            if(arr[mid] > arr[mid - 1] && arr[mid] > arr[mid + 1]) { 
                return mid; 
            } else if(arr[mid] < arr[mid - 1]) { 
                hi = mid - 1; 
            } else { 
                lo = mid + 1; 
            } 
        } 
        return -1; 
    } 
}

Space Complexity: O(1)       
Time Complexity: O(logn)
```

Solution 2: Linear Scan solution (10 min)

```
class Solution { 
    public int peakIndexInMountainArray(int[] arr) { 
        int len = arr.length; 
        int result = 0; 
        for(int i = 0; i < len - 1; i++) { 
            if(arr[i] > arr[i + 1]) { 
                return i; 
            } 
        } 
        return len - 1; 
    } 
}

Space Complexity: O(1)      
Time Complexity: O(n)
```

