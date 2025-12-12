https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/description/
There are several cards arranged in a row, and each card has an associated number of points. The points are given in the integer array cardPoints.
In one step, you can take one card from the beginning or from the end of the row. You have to take exactly k cards.
Your score is the sum of the points of the cards you have taken.
Given the integer array cardPoints and the integer k, return the maximum score you can obtain.
 
Example 1:
Input: cardPoints = [1,2,3,4,5,6,1], k = 3
Output: 12
Explanation: After the first step, your score will always be 1. However, choosing the rightmost card first will maximize your total score. The optimal strategy is to take the three cards on the right, giving a final score of 1 + 6 + 5 = 12.

Example 2:
Input: cardPoints = [2,2,2], k = 2
Output: 4
Explanation: Regardless of which two cards you take, your score will always be 4.

Example 3:
Input: cardPoints = [9,7,7,9,7,7,9], k = 7
Output: 55
Explanation: You have to take all the cards. Your score is the sum of points of all cards.
 
Constraints:
- 1 <= cardPoints.length <= 10^5
- 1 <= cardPoints[i] <= 10^4
- 1 <= k <= cardPoints.length
--------------------------------------------------------------------------------
Attempt 1: 2024-05-04
Solution 1: Fixed length Sliding Window (10 min)
class Solution {
    public int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length;
        int total = 0;
        for(int cardPoint : cardPoints) {
            total += cardPoint;
        }
        int sum = 0;
        int i;
        for(i = 0; i < n - k; i++) {
            sum += cardPoints[i];
        }
        int curSum = sum;
        for(i = n - k; i < n; i++) {
            curSum += cardPoints[i] - cardPoints[i - n + k];
            sum = Math.min(sum, curSum);
        }
        return total - sum;
    }
}
Refer to
https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/solutions/2198002/c-solution-using-sliding-window/
Intuition
- The idea here is pretty tricky to guess...
- What we do is we initialize a window of size k...
- Now, since we can select either from the start or from the end, we only have access to either the first k items or last k items, and hence we are trying to limit our access using this window...
- So, we include all the elements from start in our window, till its full...
- The sum of elements at each instance in our window will be kept track of using another variable that will store our result.
- Now, we remove the last element from our window, and add the last unvisited element of our cardPoints array... Similarly we keep on removing 1 element from our window and start adding the last unvisited element of our cardPoints array...
- We keep doing this until we reach the start of our array, in this way we have covered all our possible picks...
Example

In our possible windows, we can see that we are covering all the possible picks in our given array using 3 cards...
class Solution {
    public:
    int maxScore(vector<int>& cardPoints, int k) {
        int res = 0;

        //First k elements in our window
        for(int i=0;i<k;i++) res+=cardPoints[i];

        int curr=res;
        for(int i=k-1;i>=0;i--) {
            //We remove the last visited element and add the non-visited element from the last
            curr-=cardPoints[i];
            curr+=cardPoints[cardPoints.size()-k+i];

            //We check the maximum value any possible combination can give
            res = max(res, curr);
        }

        return res;
    }
};

Refer to
https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/solutions/597763/python3-easy-sliding-window-o-n-find-minimum-subarray/
Problem Translation: Find the smallest subarray sum of length len(cardPoints) - k
class Solution {
    public int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length - k;
        int total = 0;
        int min = Integer.MAX_VALUE;
        int cur = 0;
        int l = 0;
        int r = 0;
        while (r < cardPoints.length) {
            total += cardPoints[r];
            cur += cardPoints[r];
            if (r - l + 1 == n) {
                min =Math.min(min, cur);
                cur -= cardPoints[l++];
            }
            r++;
        }
        
        return total - (min == Integer.MAX_VALUE ? 0 : min);
    }
}

Refer to Deepseek
For LeetCode 1423 (Maximum Points from Cards), the optimal solution uses a sliding window approach to find the minimum sum of the middle n-k cards, then subtracts this from the total sum. Here's the Java solution:
🏆 Optimal Sliding Window Solution
class Solution {
    public int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length;
        int totalSum = 0;
        
        // Calculate total sum of all cards
        for (int point : cardPoints) {
            totalSum += point;
        }
        
        // If we can take all cards
        if (k == n) return totalSum;
        
        // Window size = remaining cards in middle (n-k)
        int windowSize = n - k;
        int windowSum = 0;
        
        // Calculate sum of first window
        for (int i = 0; i < windowSize; i++) {
            windowSum += cardPoints[i];
        }
        
        int minWindowSum = windowSum;
        
        // Slide the window
        for (int i = windowSize; i < n; i++) {
            // Add new element, remove old element
            windowSum += cardPoints[i] - cardPoints[i - windowSize];
            minWindowSum = Math.min(minWindowSum, windowSum);
        }
        
        // Maximum points = total - minimum middle sum
        return totalSum - minWindowSum;
    }
}
🧠 How It Works: The Key Insight
Instead of trying all combinations of k cards from ends, think:
- We must take k cards total
- The remaining n-k cards will be in a contiguous middle segment
- Max points = Total sum - Min sum of middle segment
Visual Example: cardPoints = [1,2,3,4,5,6,1], k = 3
Total sum = 22
n-k = 4 (middle window size)

Find min sum of 4 consecutive cards:
[1,2,3,4] = 10
[2,3,4,5] = 14
[3,4,5,6] = 18
[4,5,6,1] = 16

Min = 10
Max points = 22 - 10 = 12
📊 Alternative Approaches
1. Two-Pointer Solution (Direct)
class Solution {
    public int maxScore(int[] cardPoints, int k) {
        int leftSum = 0, rightSum = 0;
        
        // Take all k cards from left initially
        for (int i = 0; i < k; i++) {
            leftSum += cardPoints[i];
        }
        
        int maxScore = leftSum;
        
        // Gradually replace left cards with right cards
        for (int i = 0; i < k; i++) {
            leftSum -= cardPoints[k - 1 - i];
            rightSum += cardPoints[cardPoints.length - 1 - i];
            maxScore = Math.max(maxScore, leftSum + rightSum);
        }
        
        return maxScore;
    }
}
2. Prefix Sum Solution
class Solution {
    public int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length;
        int[] prefixSum = new int[k + 1];
        int[] suffixSum = new int[k + 1];
        
        // Build prefix sums from left
        for (int i = 0; i < k; i++) {
            prefixSum[i + 1] = prefixSum[i] + cardPoints[i];
        }
        
        // Build suffix sums from right
        for (int i = 0; i < k; i++) {
            suffixSum[i + 1] = suffixSum[i] + cardPoints[n - 1 - i];
        }
        
        // Try all combinations: i from left, k-i from right
        int maxScore = 0;
        for (int i = 0; i <= k; i++) {
            maxScore = Math.max(maxScore, prefixSum[i] + suffixSum[k - i]);
        }
        
        return maxScore;
    }
}
📈 Complexity Analysis
MethodTimeSpaceApproachSliding WindowO(n)O(1)Find min middle sumTwo-PointerO(k)O(1)Direct end combinationsPrefix SumO(k)O(k)Precompute sums
Key Insight: All three are O(n) or O(k) time, but sliding window is most intuitive for this problem structure.
🔍 Step-by-Step Example
Input: cardPoints = [100,40,17,9,73,75], k = 3
Step 1: Total sum = 314
Step 2: n-k = 3 (window size)
Step 3: Find min sum of 3 consecutive cards:
  [100,40,17] = 157
  [40,17,9] = 66
  [17,9,73] = 99
  [9,73,75] = 157
  Min = 66
Step 4: Max points = 314 - 66 = 248
Verification: Taking cards [100, 73, 75] = 248 ✓
💡 Optimization Tips
1. Early Exit for Edge Cases
public int maxScore(int[] cardPoints, int k) {
    int n = cardPoints.length;
    if (k == 0) return 0;
    if (k == n) {
        int sum = 0;
        for (int point : cardPoints) sum += point;
        return sum;
    }
    // ... rest of sliding window logic
}
2. Combined Total and Window Calculation
public int maxScore(int[] cardPoints, int k) {
    int n = cardPoints.length;
    int total = 0, window = 0;
    
    // First window + total sum
    for (int i = 0; i < n; i++) {
        total += cardPoints[i];
        if (i < n - k) window += cardPoints[i];
    }
    
    int minWindow = window;
    for (int i = n - k; i < n; i++) {
        window += cardPoints[i] - cardPoints[i - (n - k)];
        minWindow = Math.min(minWindow, window);
    }
    
    return total - minWindow;
}
⚠️ Edge Cases
// Test cases:
1. k = 1 → Take max of first/last card
2. k = n → Take all cards
3. All cards same value → Any k cards give same score
4. Increasing array → Prefer right side cards
5. Decreasing array → Prefer left side cards
🎯 Why Sliding Window is Best
1.Intuitive: Transforms problem to "find min sum of middle n-k cards"
2.Efficient: Single pass O(n), constant space
3.No extra arrays: Works in-place
4.Easy to explain: Clear mathematical reasoning
🔄 Problem Transformation
Original: "Take k cards from ends"
Transformed: "Leave n-k consecutive cards in middle"
Result: maxScore = totalSum - minMiddleSum
This transformation is the key insight that makes the sliding window approach so elegant!
📝 Interview Strategy
1.Brute force mention: O(2ᵏ) trying all combinations (not implement)
2.Prefix sum approach: O(k) time, O(k) space
3.Optimal sliding window: O(n) time, O(1) space
4.Explain transformation: Taking from ends = leaving middle contiguous
The sliding window solution is optimal for both time and space complexity!


Refer to
L918.Maximum Sum Circular Subarray (Ref.L53,L1658)
L1658.Minimum Operations to Reduce X to Zero (Ref.L918,L1423)
L1770.Maximum Score from Performing Multiplication Operations (Ref.L1423)
L2091.Removing Minimum and Maximum From Array (Ref.L1423,L1647)
L2379.Minimum Recolors to Get K Consecutive Black Blocks (Ref.L2024)
L2931.Maximum Spending After Buying Items (Ref.L1423)
