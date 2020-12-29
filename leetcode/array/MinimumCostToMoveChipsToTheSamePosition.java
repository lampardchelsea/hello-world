/**
Refer to
https://leetcode.com/problems/minimum-cost-to-move-chips-to-the-same-position/
We have n chips, where the position of the ith chip is position[i].

We need to move all the chips to the same position. In one step, we can change the position of the ith chip from position[i] to:

position[i] + 2 or position[i] - 2 with cost = 0.
position[i] + 1 or position[i] - 1 with cost = 1.
Return the minimum cost needed to move all the chips to the same position.

Example 1:
Input: position = [1,2,3]
Output: 1
Explanation: First step: Move the chip at position 3 to position 1 with cost = 0.
Second step: Move the chip at position 2 to position 1 with cost = 1.
Total cost is 1.

Example 2:
Input: position = [2,2,2,3,3]
Output: 2
Explanation: We can move the two chips at position  3 to position 2. Each move has cost = 1. The total cost = 2.

Example 3:
Input: position = [1,1000000000]
Output: 1

Constraints:
1 <= position.length <= 100
1 <= position[i] <= 10^9
*/

// Solution 1: Count odd and even indices w/ brief explanation and analysis.
// What is i-th chip is at position chips[i] ?
// Refer to
// https://leetcode.com/problems/minimum-cost-to-move-chips-to-the-same-position/discuss/398329/Solved-Not-understanding-the-problem/358487
/**
Really appreciate johnmarkos' detailed explanation, I finally understand what the question is trying to ask about. 
I'm thinking may be a visual explanation would be more intuitive:

After figuring out the meaning of "i-th chip is at position chips[i]",
for [1, 2, 3], would look like:
position 1	position 2	position 3
chip 1	    chip 2	    chip 3

for [2, 2, 2, 3, 3], would look like:
position 2	position 3
chip 1	    chip 4
chip 2	    chip 5
chip 3	
*/
class Solution {
    public int minCostToMoveChips(int[] position) {
        int[] count = new int[2];
        for(int p : position) {
            count[p % 2]++;
        }
        return Math.min(count[0], count[1]);
    }
}
