/**
Refer to
https://leetcode.com/problems/3sum-with-multiplicity/
Given an integer array A, and an integer target, return the number of tuples i, j, k  such that i < j < k and A[i] + A[j] + A[k] == target.

As the answer can be very large, return it modulo 109 + 7.

Example 1:
Input: A = [1,1,2,2,3,3,4,4,5,5], target = 8
Output: 20
Explanation: 
Enumerating by the values (A[i], A[j], A[k]):
(1, 2, 5) occurs 8 times;
(1, 3, 4) occurs 8 times;
(2, 2, 4) occurs 2 times;
(2, 3, 3) occurs 2 times.

Example 2:
Input: A = [1,1,2,2,2,2], target = 5
Output: 12
Explanation: 
A[i] = 1, A[j] = A[k] = 2 occurs 12 times:
We choose one 1 from [1,1] in 2 ways,
and two 2s from [2,2,2,2] in 6 ways.

Constraints:
3 <= A.length <= 3000
0 <= A[i] <= 100
0 <= target <= 300
*/

// Solution 1: Same style as what we did for 15. 3 Sum
// Refer to
// https://leetcode.com/problems/3sum-with-multiplicity/discuss/181080/Java-sorting-solution-O(N2)
/**
The solution is similar to 3Sum problem (https://leetcode.com/submissions/detail/128547721/).
The key is how to skip duplicate numbers:
Case 1: A[l] == A[r], the total possible answer is num * (num-1) / 2, where num = r-l+1.
Case 2: A[l] != A[r], find how many duplicates in the left-hand side (which is cntL) and how many duplicates in the 
        right-hand side (which is cntR). Then, search for the next possible answers.

class Solution {
    public int threeSumMulti(int[] A, int target) {
        int mod = 1000000007;
        int ans = 0;
        Arrays.sort(A);
        for(int i = 0; i < A.length-2; i++) {
            long cnt = 0;
            int l = i+1, r = A.length-1;
            while(l < r) {
                if(A[i] + A[l] + A[r] > target) r--;
                else if(A[i] + A[l] + A[r] < target) l++;
                else {
                    if(A[l] != A[r]) {
                        long cntL = 1, cntR = 1;
                        while(l+1 < r && A[l] == A[l+1]) { cntL++; l++; }
                        while(l < r-1 && A[r] == A[r-1]) { cntR++; r--; }
                        cnt += (cntL * cntR) % mod;
                        l++;
                        r--;
                    } else {
                        long n = r-l+1;
                        cnt += (n * (n-1) / 2) % mod;
                        break;
                    }
                }
            }
            ans = (int) (ans + cnt) % mod;
        }
        return ans;
    }
}
Time complexity: O(N^2)
Space complexity: O(1)
*/
class Solution {
    public int threeSumMulti(int[] A, int target) {
        Arrays.sort(A);
        int mod = 1000000007;
        int result = 0;
        for(int i = 0; i < A.length - 2; i++) {
            int lo = i + 1;
            int hi = A.length - 1;
            long count = 0;
            while(lo < hi) {
                if(A[lo] + A[hi] == target - A[i]) {
                    // Count repeat elements from two directions, and
                    // their production means how many tuples result same
                    if(A[lo] != A[hi]) {
                        long countL = 1;
                        long countR = 1;
                        while(lo + 1 < hi && A[lo + 1] == A[lo]) {
                            countL++;
                            lo++;
                        }
                        while(hi - 1 > lo && A[hi - 1] == A[hi]) {
                            countR++;
                            hi--;
                        }
                        count += (countL * countR) % mod;
                        lo++;
                        hi--;                    
                    } else {
                        // Since we already sort the array if A[lo] == A[hi] means
                        // all elements between index lo and hi in A are same value
                        long n = hi - lo + 1;
                        count += (n * (n - 1) / 2) % mod;
                        // No need to continue current loop since all elements are same
                        break;
                    }

                } else if(A[lo] + A[hi] > target - A[i]) {
                    hi--;
                } else {
                    lo++;
                }
            }
            result = (int)(result + count) % mod;
        }
        return result;
    }
}


// Solution 2: Two Pointers + HashMap
// Refer to
// https://leetcode.com/problems/3sum-with-multiplicity/discuss/181128/10-lines-Super-Super-Easy-Java-Solution
/**
Think Outside of The Box!
Intuitively, you will try to solve it based on the solution of 3Sum.
But... Build a map for counting different sums of two numbers. The rest of things are straightfoward.

class Solution {
    public int threeSumMulti(int[] A, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        
        int res = 0;
        int mod = 1000000007;
        for (int i = 0; i < A.length; i++) {
            res = (res + map.getOrDefault(target - A[i], 0)) % mod;
            
            for (int j = 0; j < i; j++) {
                int temp = A[i] + A[j];
                map.put(temp, map.getOrDefault(temp, 0) + 1);
            }
        }
        return res;
    }
}
*/
