/**
 * Refer to
 * https://leetcode.com/problems/first-bad-version/#/description
 * You are a product manager and currently leading a team to develop a new product. 
 * Unfortunately, the latest version of your product fails the quality check. 
 * Since each version is developed based on the previous version, all the versions 
 * after a bad version are also bad.
 * Suppose you have n versions [1, 2, ..., n] and you want to find out the first 
 * bad one, which causes all the following ones to be bad.
 * You are given an API bool isBadVersion(version) which will return whether version 
 * is bad. Implement a function to find the first bad version. You should minimize the 
 * number of calls to the API. 
 * 
 * Solution
 * https://github.com/lampardchelsea/hello-world/blob/master/lintcode/BinarySearch/VideoExamples/FirstBadVersion.java
*/

/* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */

public class Solution extends VersionControl {
    public int firstBadVersion(int n) {
        int start = 1;
        int end = n;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            if(isBadVersion(mid)) {
                end = mid;   
            } else {
                start = mid;
            }
        }
        if(isBadVersion(start)) {
            return start;
        } else {
            return end;
        }
    }
}


// Solution 2: Anther template
// Refer to
// https://leetcode.com/discuss/general-discussion/786126/python-powerful-ultimate-binary-search-template-solved-many-problems
/**
278. First Bad Version [Easy]
You are a product manager and currently leading a team to develop a new product. Since each version is developed based on the 
previous version, all the versions after a bad version are also bad. Suppose you have n versions [1, 2, ..., n] and you want 
to find out the first bad one, which causes all the following ones to be bad. You are given an API bool isBadVersion(version) 
which will return whether version is bad.

Example:

Given n = 5, and version = 4 is the first bad version.

call isBadVersion(3) -> false
call isBadVersion(5) -> true
call isBadVersion(4) -> true

Then 4 is the first bad version. 
First, we initialize left = 1 and right = n to include all possible values. Then we notice that we don't even need to design the 
condition function. It's already given by the isBadVersion API. Finding the first bad version is equivalent to finding the minimal 
k satisfying isBadVersion(k) is True. Our template can fit in very nicely:

class Solution:
    def firstBadVersion(self, n) -> int:
        left, right = 1, n
        while left < right:
            mid = left + (right - left) // 2
            if isBadVersion(mid):
                right = mid
            else:
                left = mid + 1
        return left
*/
/* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */
public class Solution extends VersionControl {
    public int firstBadVersion(int n) {
        int lo = 1;
        int hi = n;
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if(isBadVersion(mid)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }
}






Attempt 1: 2022-10-01

Solution 1: Binary Search Find Target First Occurrence (template based on while(lo <= hi), refer to L704.Binary Search)
```
/* The isBadVersion API is defined in the parent class VersionControl. 
      boolean isBadVersion(int version); */ 
public class Solution extends VersionControl { 
    public int firstBadVersion(int n) { 
        int lo = 1; 
        int hi = n; 
        while(lo <= hi) { 
            int mid = lo + (hi - lo) / 2; 
            if(isBadVersion(mid)) { 
                hi = mid - 1; 
            } else { 
                lo = mid + 1; 
            } 
        } 
        // No check required for non-existing answer return -1 case, because the problem 
        // guaranteed there must be a 'T' exist   in the input, not all 'F'
        //if(!isBadVersion(lo)) { 
        //    return -1; 
        //} 
        return lo; 
    } 
}

Space Complexity: O(1)        
Time Complexity: O(logn)
```

Refer to
https://leetcode.com/problems/first-bad-version/discuss/606080/Java-Binary-Search-Clean-code-O(logN)
```
public class Solution extends VersionControl { 
    public int firstBadVersion(int n) { 
        int left = 1, right = n, ans = -1; 
        while (left <= right) { 
            int mid = left + (right - left) / 2; // to avoid overflow incase (left+right)>2147483647 
            if (isBadVersion(mid)) { 
                ans = mid; // record mid as current answer 
                right = mid - 1; // try to find smaller version in the left side 
            } else { 
                left = mid + 1; // try to find in the right side 
            } 
        } 
        return ans; 
    } 
}
```

How while(lo <= hi) work with hi = mid - 1 and lo = mid + 1 ?
```
Template:
while lo <= hi:  
  mid = (lo+hi)/2  
  if nums[mid] == target:  
    return mid  
  if nums[mid] > target:  
    hi = mid-1  
  else:  
    lo = mid+1  
return -1
=================================================================================
e.g  
isBadVersion result -> FFTTTT 
index start from 1  -> 123456 
Find left most 'T' 
while(lo <= hi) -> ending condition lo > hi, means no interval between ...hi|lo... 
Round 1: 
lo=1,hi=6 -> mid=3 
isBadVersion(3)=T -> hi=mid-1=2 (hi=mid-1 means skip 'mid') 
Round 2: 
lo=1,hi=2 -> mid=1 
isBadVersion(1)=F -> lo=mid+1=2 (lo=mid+1 means skip 'mid') 
Round 3: 
lo=2,hi=2 -> mid=2 
isBadVersion(2)=F -> lo=mid+1=3 (lo=mid+1 means skip 'mid')  
Now lo > hi while loop end, lo is the first position for isBadVersion return 'T', no check required for non-existing answer return -1 case, because the problem guaranteed there must be a 'T' exist   in the input, not all 'F', return lo for first position
```

Solution 2: Binary Search Find Target First Occurrence (template based on while(lo < hi), refer to )
```
/* The isBadVersion API is defined in the parent class VersionControl. 
      boolean isBadVersion(int version); */ 
public class Solution extends VersionControl { 
    public int firstBadVersion(int n) { 
        int lo = 1; 
        int hi = n; 
        while(lo < hi) { 
            int mid = lo + (hi - lo) / 2; 
            if(isBadVersion(mid)) { 
                hi = mid; 
            } else { 
                lo = mid + 1; 
            } 
        } 
        return lo; 
    } 
}

Space Complexity: O(1)         
Time Complexity: O(logn)
```

Refer to
https://leetcode.com/problems/first-bad-version/discuss/71386/An-clear-way-to-use-binary-search
```
public int firstBadVersionLeft(int n) { 
    int i = 1; 
    int j = n; 
    while (i < j) { 
        int mid = i + (j - i) / 2; 
	if (isBadVersion(mid)) { 
	    j = mid; 
	} else { 
	    i = mid + 1; 
        } 
    } 
    return i; 
}
```

How while(lo < hi) work with hi = mid and lo = mid + 1 ?
```
Template:
while lo <  hi:  
  mid = (lo+hi)/2  
  if function(mid):  
    hi = mid  
  else:  
    lo = mid+1  
return lo
=================================================================================
e.g  
isBadVersion result -> FFTTTT 
index start from 1  -> 123456 
Find left most 'T' 
while(lo < hi) -> ending condition lo == hi, means lo and hi overlap at same position ... hi ... 
                                                                                          lo 
Round 1: 
lo=1,hi=6 -> mid=3 
isBadVersion(3)=T -> hi=mid=3 (hi=mid means include 'mid') 
Round 2: 
lo=1,hi=3 -> mid=2 
isBadVersion(2)=F -> lo=mid+1=3 (lo=mid+1 means skip 'mid')  
Now lo == hi while loop end, and hi can equal to mid, which means include the final answer, return either hi or lo is fine
```

Conclusion
https://leetcode.com/problems/first-bad-version/discuss/71296/O(lgN)-simple-Java-solution/166412
I think when termination condition is left < right, you need to set hi = mid - 1, lo = mid; when it's left <= right, you need to set lo = mid + 1 & hi = mid - 1
