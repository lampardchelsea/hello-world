/**
Refer to
https://leetcode.com/problems/can-make-arithmetic-progression-from-sequence/
Given an array of numbers arr. A sequence of numbers is called an arithmetic progression if the difference 
between any two consecutive elements is the same.

Return true if the array can be rearranged to form an arithmetic progression, otherwise, return false.

Example 1:
Input: arr = [3,5,1]
Output: true
Explanation: We can reorder the elements as [1,3,5] or [5,3,1] with differences 2 and -2 respectively, between each consecutive elements.

Example 2:
Input: arr = [1,2,4]
Output: false
Explanation: There is no way to reorder the elements to obtain an arithmetic progression.

Constraints:
2 <= arr.length <= 1000
-10^6 <= arr[i] <= 10^6
*/

// Solution 1: Brute Force sort with O(NlogN)
// Refer to
// https://leetcode.com/problems/can-make-arithmetic-progression-from-sequence/discuss/720253/JavaPython-3-O(n)-and-O(nlogn)-codes-w-brief-explanation-and-analysis.
/**
Method 2: Sort
    public boolean canMakeArithmeticProgression(int[] arr) {
        Arrays.sort(arr);
        for (int i = 2; i < arr.length; ++i) {
            if (arr[i - 1] - arr[i] != arr[i - 2] - arr[i - 1]) {
                return false;
            }
        }
        return true;
    }
Time: O(nlogn), space: O(1) if excluding space used during sort.
*/
// Style 1:
class Solution {
    public boolean canMakeArithmeticProgression(int[] arr) {
        Arrays.sort(arr);
        int prev = arr[0];
        int cur = arr[1];
        int diff = cur - prev;
        for(int i = 2; i < arr.length; i++) {
            int new_diff = arr[i] - cur;
            if(new_diff != diff) {
                return false;
            }
            prev = cur;
            cur = arr[i];
        }
        return true;
    }
}

// Style 2:
class Solution {
    public boolean canMakeArithmeticProgression(int[] arr) {
        Arrays.sort(arr);
        for(int i = 2; i < arr.length; i++) {
            if(arr[i] - arr[i - 1] != arr[i - 1] - arr[i- 2]) {
                return false;
            } 
        }
        return true;
    }
}

// Solution 2: HashMap
// Refer to
// https://leetcode.com/problems/can-make-arithmetic-progression-from-sequence/discuss/720253/JavaPython-3-O(n)-and-O(nlogn)-codes-w-brief-explanation-and-analysis.
/**
Method 1:
Find the max and min of arr and compute the average difference;
Put all numbers into a HashSet;
Start from the min, add the average difference to make the next number in the arithmetic sequence, check one by one 
if it is in the HashSet; if any one not in, return false; otherwise, return true.

Note:
There are n - 1 slots between n element of the array;
diff = max = min must be divisible by n - 1 for arr to be an arithmetic sequence;
After sorting arr, the adjacent elements difference must be diff / (n - 1), if it is an arithmetic sequence.
    public boolean canMakeArithmeticProgression(int[] arr) {
        Set<Integer> seen = new HashSet<>();
        int mi = Integer.MAX_VALUE, mx = Integer.MIN_VALUE, n = arr.length;
        for (int a : arr) {
            mi = Math.min(mi, a);
            mx = Math.max(mx, a);
            seen.add(a);
        }
        int diff = mx - mi;
        if (diff % (n - 1) != 0) {
            return false;
        }
        diff /= n - 1;
        while (--n > 0) {
            if (!seen.contains(mi)) {
                return false;
            }
            mi += diff;
        }
        return true;
    }
Time & space: O(n).
*/
class Solution {
    public boolean canMakeArithmeticProgression(int[] arr) {
        Set<Integer> set = new HashSet<Integer>();
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int n = arr.length;
        for(int i = 0; i < n; i++) {
            if(arr[i] < min) {
                min = arr[i];
            }
            if(arr[i] > max) {
                max = arr[i];
            }
            set.add(arr[i]);
        }
        int diff = max - min;
        if(diff % (n - 1) != 0) {
            return false;
        }
        diff /= n - 1;
        while(--n > 0) {
            if(!set.contains(min)) {
                return false;
            }
            min += diff;
        }
        return true;
    }
}
