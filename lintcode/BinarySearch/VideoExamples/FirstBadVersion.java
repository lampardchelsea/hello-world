/**
 * The code base version is an integer start from 1 to n. One day, someone committed a 
 * bad version in the code case, so it caused this version and the following versions
 * are all failed in the unit tests. Find the first bad version.
 * You can call isBadVersion to help you determine which version is the first bad one. 
 * The details interface can be found in the code's annotation part.
 * Notice
 * Please read the annotation in code area to get the correct way to call isBadVersion 
 * in different language. For example, Java is SVNRepo.isBadVersion(v)
 * Have you met this question in a real interview?
    Example
    Given n = 5:
    isBadVersion(3) -> false
    isBadVersion(5) -> true
    isBadVersion(4) -> true
    Here we are 100% sure that the 4th version is the first bad version.
*/

/**
 * public class SVNRepo {
 *     public static boolean isBadVersion(int k);
 * }
 * you can use SVNRepo.isBadVersion(k) to judge whether 
 * the kth code version is bad or not.
*/
class Solution {
    /**
     * @param n: An integers.
     * @return: An integer which is the first bad version.
     */
    public int findFirstBadVersion(int n) {
        // write your code here
        int start = 1;
        int end = n;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            if(SVNRepo.isBadVersion(mid)) {
                end = mid;
            } else {
                start = mid;
            }
        }
        if(SVNRepo.isBadVersion(start)) {
            return start;
        }
        if(SVNRepo.isBadVersion(end)) {
            return end;
        }
        return -1;
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





