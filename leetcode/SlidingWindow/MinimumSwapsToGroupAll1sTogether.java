/**
Refer to
https://code.dennyzhang.com/minimum-swaps-to-group-all-1s-together
Given a binary array data, return the minimum number of swaps required to group all 1’s present in the array together in any place in the array.

Example 1:
Input: [1,0,1,0,1]
Output: 1
Explanation: 
There are 3 ways to group all 1's together:
[1,1,1,0,0] using 1 swap.
[0,1,1,1,0] using 2 swaps.
[0,0,1,1,1] using 1 swap.
The minimum is 1.

Example 2:
Input: [0,0,0,1,0]
Output: 0
Explanation: 
Since there is only one 1 in the array, no swaps needed.

Example 3:
Input: [1,0,1,0,1,0,0,1,1,0,1]
Output: 3
Explanation: 
One possible solution that uses 3 swaps is [0,0,0,0,0,1,1,1,1,1,1].

Note:
1 <= data.length <= 10^5
0 <= data[i] <= 1
*/

// Solution 1: Fixed length sliding window
// Template same as Find K-Length Substrings With No Repeated Characters
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/SlidingWindow/FindKLengthSubstringsWithNoRepeatedCharacters.java

// Refer to
// https://code.dennyzhang.com/minimum-swaps-to-group-all-1s-together
/**
Basic Ideas: fixed size sliding window
We know the size of the final group all 1's
The window can start from any position
Use a sliding window. And the find the minimum count of 0s in the sliding window.
Complexity: Time O(n), Space O(k)
func minSwaps(data []int) int {
    k:=0
    for _, v := range data {
        if v == 1 {  k++ }
    }
    res := 1<<31-1
    count := 0
    // sliding window. left: i-k+1, right: i
    for i:=0; i<len(data); i++ {
        // move right pointer: add a new element
        if data[i] == 0 { count++ }
        // move left pointer: remove the old element
        if i-k >= 0 {
            if data[i-k] == 0 { count-- }
        }
        // collect result
        if i-k+1 >= 0  {
            if count < res { res = count }
        }
    }
    return res
}
*/
class Solution {
    public int minSwaps(int[] data) {
        // Sliding window size K will be number of 1's in the given array
        int K = 0;
        for(int i = 0; i < data.length; i++) {
            if(data[i] == 1) {
                K++;
            }
        }
        int minSwap = Integer.MAX_VALUE;
        int curSwap = 0;
        // Create sliding window as right boundary pointer index = i and since fixed length
        // window, left boundary pointer automatically obtained as index = i - K + 1
        for(int i = 0; i < data.length; i++) {
            // If data[i] is 0 and still in sliding window, swap count increase 1
            if(data[i] == 0) {
                curSwap++;
            }
            // If move out of sliding window and data[i - K] is 0, swap count decrease 1,
            // since we move position index = i - K element out of sliding window
            if(i >= K) {
                if(data[i - K] == 0) {
                    curSwap--;
                }
            }
            // Since i > K means at least we have got 2 candiates to compare, then 
            // find the minimum swap required count by comparison
            if(i > K) {
                minSwap = Math.min(minSwap, curSwap);
            }
        }
        return minSwap;
    }
}
