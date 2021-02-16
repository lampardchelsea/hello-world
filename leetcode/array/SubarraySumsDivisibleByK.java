/**
Refer to
https://leetcode.com/problems/subarray-sums-divisible-by-k/
Given an array A of integers, return the number of (contiguous, non-empty) subarrays that have a sum divisible by K.

Example 1:
Input: A = [4,5,0,-2,-3,1], K = 5
Output: 7
Explanation: There are 7 subarrays with a sum divisible by K = 5:
[4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]

Note:
1 <= A.length <= 30000
-10000 <= A[i] <= 10000
2 <= K <= 10000
*/

// Solution 1: preSum + HashMap
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/array/SubarraySumEqualsK.java --> Exactly same way to handle the problem

// https://leetcode.com/problems/subarray-sums-divisible-by-k/discuss/413234/DETAILED-WHITEBOARD!-BEATS-100-(Do-you-really-want-to-understand-It)
---> // Save as document on https://github.com/lampardchelsea/hello-world/blob/master/leetcode/array/Document/Subarray_Sums_Divisible_by_K_preSum_HashMap_Explain.docx
// https://leetcode.com/problems/subarray-sums-divisible-by-k/discuss/217980/Java-O(N)-with-HashMap-and-prefix-Sum
/**
class Solution {
    public int subarraysDivByK(int[] A, int K) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int count = 0, sum = 0;
        for(int a : A) {
            sum = (sum + a) % K;
            if(sum < 0) sum += K;  // Because -1 % 5 = -1, but we need the positive mod 4
            count += map.getOrDefault(sum, 0);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
}

About the problems - sum of contiguous subarray , prefix sum is a common technique.
Another thing is if sum[0, i] % K == sum[0, j] % K, sum[i + 1, j] is divisible by by K.
So for current index j, we need to find out how many index i (i < j) exit that has the same mod of K.
Now it easy to come up with HashMap <mod, frequency>

Time Complexity: O(N)
Space Complexity: O(K)

As @davidluoyes Comments, we can just use array, which is faster than HashMap.
class Solution {
    public int subarraysDivByK(int[] A, int K) {
        int[] map = new int[K];
		map[0] = 1;
        int count = 0, sum = 0;
        for(int a : A) {
            sum = (sum + a) % K;
            if(sum < 0) sum += K;  // Because -1 % 5 = -1, but we need the positive mod 4
            count += map[sum];
            map[sum]++;
        }
        return count;
    }
}
*/

// https://leetcode.com/problems/continuous-subarray-sum/discuss/150330/Math-behind-the-solutions
/**
Haven't seen anyone post the math or theory behind the solutions yet, so I'm sharing mine. Let me know if there is any better one.
In short, start with mod =0, then we always do mod = (mod+nums[i])%k, if mod repeats, that means between these two mod = x occurences the sum is multiple of k.
Math: c = a % k, c = b % k, so we have a % k = b % k.
Where a is the mod at i and b is the mod at j and a <= b, i < j, because all nums are non-negative. And c is the mod that repeats.
Suppose b-a=d, then we have b % k = ((a+d) % k)%k = (a%k + d%k)%k
In order to make the equation valid: a % k = (a%k + d%k)%k
d%k has to be 0, so d, the different between b and a, is a multiple of k
Example:
[23, 2, 1, 6, 7] k=9
mod = 5, 7, 8, 5 <-- at here we found it
*/

class Solution {
    public int subarraysDivByK(int[] A, int K) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	// Need to save a preSum 0 frequency as 1
	// Consider this case. It's my first time meeting a remainder that equals zero. But wait, doesn't that mean that 
	// my subarray's sum is Divisible by K? If freq[0]!=1 then my counter wouldn't increment the first time I met a 
	// remainder=0. Therefore I need freq[0]=1 in order for my counter to consider the first subarray with a remainder 
	// that equals to zero.
        map.put(0, 1);
        int count = 0;
        int sum = 0;
        for(int a : A) {
	    // If sum A[i:j] = sum A[0:j] - sum[0:i-1] has the same remainder against K
	    // If subarray start at 0 and end at index i and j has same remainder against K, then subarray from index i to j
	    // must be multiply of K since subarray sum from i to j modula K is 0.
            sum = (sum + a) % K;
            // Always keep use positive reminder to align all scanning intermediate result
	    // E.g [-4,-1,5,5] and K = 2 --> you will encounter when scanning (-4 - 1) % 2 = -1
	    // but (-4 - 1 + 5 + 5) % 2 = 1, if we dont' convert -1 to (-1 + 2 = 1) then
	    // will calculate out wrong difference as 1 - (-1) = 2 not 0 and miss combination 
	    // as last two 5 as (5 + 5) which % 2 actually = 0.
            if(sum < 0) {
                sum += K;
            }
            if(map.containsKey(sum)) {
                count += map.get(sum);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
}
