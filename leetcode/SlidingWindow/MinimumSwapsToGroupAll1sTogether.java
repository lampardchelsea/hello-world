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
// Refer to
// https://code.dennyzhang.com/minimum-swaps-to-group-all-1s-together
/**
Basic Ideas: fixed size sliding window
We know the size of the final group all 1's
The window can start from any position
Use a sliding window. And the find the minimum count of 0s in the sliding window.
Complexity: Time O(n), Space O(k)
*/




