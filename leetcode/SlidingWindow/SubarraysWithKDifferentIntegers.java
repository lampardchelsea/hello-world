/**
Refer to
https://leetcode.com/problems/subarrays-with-k-different-integers/
Given an array A of positive integers, call a (contiguous, not necessarily distinct) subarray of A good if the number of 
different integers in that subarray is exactly K.

(For example, [1,2,3,1,2] has 3 different integers: 1, 2, and 3.)

Return the number of good subarrays of A.

Example 1:
Input: A = [1,2,1,2,3], K = 2
Output: 7
Explanation: Subarrays formed with exactly 2 different integers: [1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2].

Example 2:
Input: A = [1,2,1,3,4], K = 3
Output: 3
Explanation: Subarrays formed with exactly 3 different integers: [1,2,1,3], [2,1,3], [1,3,4].

Note:
1 <= A.length <= 20000
1 <= A[i] <= A.length
1 <= K <= A.length
*/

// Solution 1: 
// Refer to
// https://leetcode.com/problems/subarrays-with-k-different-integers/discuss/523136/JavaC%2B%2BPython-Sliding-Window
/**
Intuition:
First you may have feeling of using sliding window.
Then this idea get stuck in the middle.

This problem will be a very typical sliding window,
if it asks the number of subarrays with at most K distinct elements.

Just need one more step to reach the folloing equation:
exactly(K) = atMost(K) - atMost(K-1)

Explanation
Write/copy a helper function of sliding window,
to get the number of subarrays with at most K distinct elements.
Done.

Complexity:
Time O(N) for two passes.
Space O(K) at most K elements in the counter
Of course, you can merge 2 for loops into one, if you like.

Java:
    public int subarraysWithKDistinct(int[] A, int K) {
        return atMostK(A, K) - atMostK(A, K - 1);
    }
    int atMostK(int[] A, int K) {
        int i = 0, res = 0;
        Map<Integer, Integer> count = new HashMap<>();
        for (int j = 0; j < A.length; ++j) {
            if (count.getOrDefault(A[j], 0) == 0) K--;
            count.put(A[j], count.getOrDefault(A[j], 0) + 1);
            while (K < 0) {
                count.put(A[i], count.get(A[i]) - 1);
                if (count.get(A[i]) == 0) K++;
                i++;
            }
            res += j - i + 1;
        }
        return res;
    }
*/

// Why ret += j - i + 1 ?
// Refer to
// https://leetcode.com/problems/subarrays-with-k-different-integers/discuss/523136/JavaC++Python-Sliding-Window/620828
/**
suppose initial window [a] then subarrays that ends with this element are [a]--> 1
now we expand our window [a,b] then subarrays that ends with this new element are [b], [a,b] -->2
now we expand our window [a,b,c] then subarrays that ends with this new element are [c], [b, c], [a,b,c] -->3
now we expand our window [a,b,c,d] and let suppose this is not valid window so we compress window from left side to make it valid window
[b,c,d] then subarrays that ends with this new element are [d], [c,d], [b,c,d] -->3

You can observe that we are only considering subarrays with new element in it which auto. eliminate the counting of duplicate 
subarrays that we already considered previously.
And surprisingly the number of sub-arrays with this new element in it is equal to the length of current window.
*/

// Why ret += j - i + 1 ?
// Refer to
// https://leetcode.com/problems/subarrays-with-k-different-integers/discuss/523136/JavaC++Python-Sliding-Window/631972
/**
Another way to understand this: Everytime before the res += j - i + 1, you contract the range by incrementing i until the 
invariant is satisfied that the range [i, j] has k distinct elements. Now, how many new ranges does this new element A[j] 
contribute? It contributes exactly the new ranges [i, j], [i+1, j], ... , [j-1, j], which amounts to j - i + 1 new ranges. 
You know those are valid because each subranges in [i, j] has less than or equal to k distinct elements by the invariant, 
and you know you are not double counting ranges because they all involve the new element A[j].
Notice that we do not account for subranges that end before j, because we've already accounted for them in a previous loop iteration.
*/
class Solution {
    public int subarraysWithKDistinct(int[] A, int K) {
        return helper(A, K) - helper(A, K - 1);
    }
    
    private int helper(int[] A, int K) {
        int count = 0;
        Map<Integer, Integer> freq = new HashMap<Integer, Integer>();
        int i = 0;
        for(int j = 0; j < A.length; j++) {
            freq.put(A[j], freq.getOrDefault(A[j], 0) + 1);
            while(freq.size() > K) {
                freq.put(A[i], freq.get(A[i]) - 1);
                if(freq.get(A[i]) == 0) {
                    freq.remove(A[i]);
                }
                i++;
            }
            count += j - i + 1;
        }
        return count;
    }
}
