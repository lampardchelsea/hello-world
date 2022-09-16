https://leetcode.com/problems/search-in-a-sorted-array-of-unknown-size/description/

Given an integer array sorted in ascending order, write a function to searchtargetinnums. Iftargetexists, then return its index, 
otherwise return-1.However, the array size is unknown to you. You may only access the array using anArrayReader interface, 
where ArrayReader.get(k)returns the element of the array at indexk (0-indexed).

You may assume all integers in the array are less than 10000, and if you access the array out of bounds,ArrayReader.getwill 
return2147483647.

Example 1:
```
Input: array = [-1,0,3,5,9,12], target = 9
Output: 4
Explanation: 9 exists in nums and its index is 4
```

Example 2:
```
Input: array = [-1,0,3,5,9,12], target = 2
Output: -1
Explanation: 2 does not exist in nums so return -1
```

Note:
1. You may assume that all elements in the array are unique.
2. The value of each element in the array will be in the range [-9999, 9999].
---
Attempt 1: 2022-09-16 (10min, problem convert to use given reader to find target occurrence by using template)

```
class Solution { 
    public int search(ArrayReader reader, int target) { 
        int lo = 0;
        int hi = Integer.MAX_VALUE;
        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            // Only able to find nums[mid] value by using Arrayreader
            int val = reader.get(mid);
            if(val == target) {
                return mid; 
            } else if(val > target) {
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

Refer to:
https://just4once.gitbooks.io/leetcode-notes/content/leetcode/binary-search/702-search-in-a-sorted-array-of-unknown-size.html

Thought Process

1. Binary Search
	1. We set our search index to be from 0 to 2147483647, named lo and hi
	2. Our search ends when we hit our target or lo > hi
	3. Time complexity O(1) from 32 times or search
	4. Space complexity O(1)

https://github.com/grandyang/leetcode/issues/702
这道题给了我们一个未知大小的数组，让我们在其中搜索数字。给了我们一个ArrayReader的类，我们可以通过get函数来获得数组中的数字，如果越界了的话，
会返回整型数最大值。既然是有序数组，又要搜索，那么二分搜索法肯定是不二之选，问题是需要知道数组的首尾两端的位置，才能进行二分搜索，而这道题
刚好就是大小未知的数组。所以博主的第一个想法就是先用二分搜索法来求出数组的大小，然后再用一个二分搜索来查找数字，这种方法是可以通过OJ的。
但其实我们是不用先来确定数组的大小的，而是可以直接进行搜索数字，我们实际上是假设数组就有整型最大值个数字，在多余的位置上相当于都填上了
整型最大值，那么这也是一个有序的数组，我们可以直接用一个二分搜索法进行查找即可
