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
