/**
Refer to
https://leetcode.com/problems/detect-pattern-of-length-m-repeated-k-or-more-times/
Given an array of positive integers arr,  find a pattern of length m that is repeated k or more times.

A pattern is a subarray (consecutive sub-sequence) that consists of one or more values, repeated multiple times consecutively 
without overlapping. A pattern is defined by its length and the number of repetitions.

Return true if there exists a pattern of length m that is repeated k or more times, otherwise return false.

Example 1:
Input: arr = [1,2,4,4,4,4], m = 1, k = 3
Output: true
Explanation: The pattern (4) of length 1 is repeated 4 consecutive times. Notice that pattern can be repeated k or more times but not less.

Example 2:
Input: arr = [1,2,1,2,1,1,1,3], m = 2, k = 2
Output: true
Explanation: The pattern (1,2) of length 2 is repeated 2 consecutive times. Another valid pattern (2,1) is also repeated 2 times.

Example 3:
Input: arr = [1,2,1,2,1,3], m = 2, k = 3
Output: false
Explanation: The pattern (1,2) is of length 2 but is repeated only 2 times. There is no pattern of length 2 that is repeated 3 or more times.

Example 4:
Input: arr = [1,2,3,1,2], m = 2, k = 2
Output: false
Explanation: Notice that the pattern (1,2) exists twice but not consecutively, so it doesn't count.

Example 5:
Input: arr = [2,2,2,2], m = 2, k = 3
Output: false
Explanation: The only pattern of length 2 is (2,2) however it's repeated only twice. Notice that we do not count overlapping repetitions.

Constraints:
2 <= arr.length <= 100
1 <= arr[i] <= 100
1 <= m <= 100
2 <= k <= 100
*/

// Solution 1: Brute Force
// Refer to
// https://leetcode.com/problems/detect-pattern-of-length-m-repeated-k-or-more-times/discuss/819314/Java-Straightforward-Iteratively
// https://leetcode.com/problems/detect-pattern-of-length-m-repeated-k-or-more-times/discuss/819359/Java-Time-O(n)-Space-O(1)/677542
/**
E.g
m = 3   k = 3

... 1 2 3 1 2 3 1 2 3 ...
    i --- j
count = 1

... 1 2 3 1 2 3 1 2 3 ...
      i --- j
count = 2

... 1 2 3 1 2 3 1 2 3 ...
        i --- j
count = 3

... 1 2 3 1 2 3 1 2 3 ...
          i --- j
count = 4

... 1 2 3 1 2 3 1 2 3 ...
            i --- j
count = 5

... 1 2 3 1 2 3 1 2 3 ...
              i --- j
count = 6  ->  (k - 1) * m
*/
// Style 1: Boolean Flag
class Solution {
    public boolean containsPattern(int[] arr, int m, int k) {
        // All position could be possible initial start
        for(int i = 0; i <= arr.length - m * k; i++) {
            // Flag required to make sure all values between i to i + m - 1 has
            // k repeats, anyone of these values failed k repeats will cause fail
            // and break out to continue try next initial start
            boolean flag = true;
            for(int j = 0; j < m; j++) {
                for(int l = 1; l < k; l++) {
                    // Gap as l * m for each value
                    if(arr[i + j] != arr[i + j + l * m]) {
                        flag = false;
                        break;
                    }
                }
            }
            if(flag) {
                return true;
            }
        }
        return false;
    }
}

// Style 2: count flag
class Solution {
    public boolean containsPattern(int[] arr, int m, int k) {
        // All position could be possible initial start
        for(int i = 0; i <= arr.length - m * k; i++) {
            // count required to make sure all values between i to i + m - 1 has
            // k repeats, anyone of these values failed k repeats will cause fail
            // and break out to continue try next initial start
            int count = 0;
            for(int j = 0; j < m; j++) {
                for(int l = 1; l < k; l++) {
                    // Gap as l * m for each value
                    if(arr[i + j] != arr[i + j + l * m]) {
                        count = 0;
                        break;
                    } else {
                        count++;
                    }
                }
            }
            if(count == m * (k - 1)) {
                return true;
            }
        }
        return false;
    }
}

// Solution 2: Helper method to remove boolean flag
// Refer to
// https://leetcode.com/problems/detect-pattern-of-length-m-repeated-k-or-more-times/discuss/819397/Java-Iterative-Solution
class Solution {
    public boolean containsPattern(int[] arr, int m, int k) {
        // All position could be possible initial start
        for(int i = 0; i <= arr.length - m * k; i++) {
            if(check(arr, m, k, i)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean check(int[] arr, int m, int k, int i) {
        for(int j = 0; j < m; j++) {
            for(int l = 1; l < k; l++) {
                if(arr[i + j] != arr[i + j + l * m]) {
                    return false;
                }
            }
        }
        return true;
    }
}

// Solutoin 3: O(N) linear solution
// Refer to
// https://leetcode.com/problems/detect-pattern-of-length-m-repeated-k-or-more-times/discuss/819359/Java-Time-O(n)-Space-O(1)/677460
class Solution {
    public boolean containsPattern(int[] arr, int m, int k) {
        int count = 0;
        // All position could be possible initial start
        for(int i = 0; i < arr.length - m; i++) {
            if(arr[i] == arr[i + m]) {
                count++;
            } else {
                count = 0;
            }
            if(count == m * (k - 1)) {
                return true;
            }
        }
        return false;
    }
}
