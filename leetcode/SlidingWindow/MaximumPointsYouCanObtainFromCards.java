/**
Refer to
https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/
There are several cards arranged in a row, and each card has an associated number of points The points are given in the integer array cardPoints.
In one step, you can take one card from the beginning or from the end of the row. You have to take exactly k cards.
Your score is the sum of the points of the cards you have taken.
Given the integer array cardPoints and the integer k, return the maximum score you can obtain.

Example 1:
Input: cardPoints = [1,2,3,4,5,6,1], k = 3
Output: 12
Explanation: After the first step, your score will always be 1. However, choosing the rightmost card first will maximize 
your total score. The optimal strategy is to take the three cards on the right, giving a final score of 1 + 6 + 5 = 12.

Example 2:
Input: cardPoints = [2,2,2], k = 2
Output: 4
Explanation: Regardless of which two cards you take, your score will always be 4.

Example 3:
Input: cardPoints = [9,7,7,9,7,7,9], k = 7
Output: 55
Explanation: You have to take all the cards. Your score is the sum of points of all cards.

Example 4:
Input: cardPoints = [1,1000,1], k = 1
Output: 1
Explanation: You cannot take the card in the middle. Your best score is 1. 

Example 5:
Input: cardPoints = [1,79,80,1,1,1,200,1], k = 3
Output: 202

Constraints:
1 <= cardPoints.length <= 10^5
1 <= cardPoints[i] <= 10^4
1 <= k <= cardPoints.length
*/

// Solution 1: Fixed length sliding window
// Refer to
// https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/discuss/597763/Python3-Easy-Sliding-Window-O(n)%3A-Find-minimum-subarray
/**
Hint 1:
Let the sum of all points be total_pts. You need to remove a sub-array from cardPoints with length n - k.
Hint 2:
Keep a window of size n - k over the array. The answer is max(answer, total_pts - sumOfCurrentWindow)

Problem Translation: Find the smallest subarray sum of length len(cardPoints) - k
class Solution:
    def maxScore(self, cardPoints: List[int], k: int) -> int:
        size = len(cardPoints) - k
        minSubArraySum = float('inf')
        j = curr = 0
        
        for i, v in enumerate(cardPoints):
            curr += v
            if i - j + 1 > size:
                curr -= cardPoints[j]
                j += 1
            if i - j + 1 == size:    
                minSubArraySum = min(minSubArraySum, curr)
				
        return sum(cardPoints) - minSubArraySum
DP top down solution timed out for me.

class Solution:
    def maxScore(self, cardPoints: List[int], k: int) -> int:
        if k == len(cardPoints):
            return sum(cardPoints)
        @lru_cache(None)
        def dfs(i, j, k, res = 0):
            if k == 0:
                return 0
            res = max(cardPoints[i] + dfs(i + 1, j, k - 1), cardPoints[j] + dfs(i, j - 1, k - 1))
            return res   
			
        return dfs(0, len(cardPoints) - 1, k)
Please upvote if you think this was useful.
*/

// Template refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/SlidingWindow/FindKLengthSubstringsWithNoRepeatedCharacters.java
class Solution {
    // Find minimum sum of sub-array lenght with cardPoints.length - k
    public int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length;
        int totalSum = 0;
        int minSum = Integer.MAX_VALUE;
        int temp = 0;
        for(int i = 0; i < n; i++) {
            totalSum += cardPoints[i];
            temp += cardPoints[i];
            if(i >= n - k) {
                temp -= cardPoints[i - n + k];
            }
            if(i >= n - k - 1) {
                minSum = Math.min(temp, minSum);
            }
        }
        return totalSum - minSum;
    }
}
