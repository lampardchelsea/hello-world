https://leetcode.com/problems/minimum-amount-of-time-to-fill-cups/description/
You have a water dispenser that can dispense cold, warm, and hot water. Every second, you can either fill up 2 cups with different types of water, or 1 cup of any type of water.
You are given a 0-indexed integer array amount of length 3 where amount[0], amount[1], and amount[2] denote the number of cold, warm, and hot water cups you need to fill respectively. Return the minimum number of seconds needed to fill up all the cups.
 
Example 1:
Input: amount = [1,4,2]
Output: 4
Explanation: One way to fill up the cups is:
Second 1: Fill up a cold cup and a warm cup.
Second 2: Fill up a warm cup and a hot cup.
Second 3: Fill up a warm cup and a hot cup.
Second 4: Fill up a warm cup.
It can be proven that 4 is the minimum number of seconds needed.

Example 2:
Input: amount = [5,4,4]
Output: 7
Explanation: One way to fill up the cups is:
Second 1: Fill up a cold cup, and a hot cup.
Second 2: Fill up a cold cup, and a warm cup.
Second 3: Fill up a cold cup, and a warm cup.
Second 4: Fill up a warm cup, and a hot cup.
Second 5: Fill up a cold cup, and a hot cup.
Second 6: Fill up a cold cup, and a warm cup.
Second 7: Fill up a hot cup.

Example 3:
Input: amount = [5,0,0]
Output: 5
Explanation: Every second, we fill up a cold cup.
 
Constraints:
- amount.length == 3
- 0 <= amount[i] <= 100
--------------------------------------------------------------------------------
Attempt 1: 2025-11-25
Solution 1: Heap (60 min)
class Solution {
    public int fillCups(int[] amount) {
        PriorityQueue<Integer> maxPQ = new PriorityQueue<>((a, b) -> b - a);
        for(int num : amount) {
            // Test out by [0, 0, 0]
            if(num > 0) {
                maxPQ.offer(num);
            }
        }
        int count = 0;
        while(!maxPQ.isEmpty()) {
            int first = maxPQ.poll();
            if(maxPQ.isEmpty()) {
                // Only one type left - must fill one by one
                count += first;
                break;
            }
            int second = maxPQ.poll();
            // Fill one cup of each of the two largest types
            count++;
            first--;
            second--;
            // Add back if still positive
            if(first > 0) {
                maxPQ.offer(first);
            }
            if(second > 0) {
                maxPQ.offer(second);
            }
        }
        return count;
    }
}

Time Complexity: O(n*logn)
Space Complexity: O(1)

Solution 2: Greedy + Math (60 min)
class Solution {
    public int fillCups(int[] amount) {
        int max = 0;
        int sum = 0;
        for(int num : amount) {
            max = Math.max(max, num);
            sum += num;
        }
        // The answer is the maximum between:
        // 1. The largest single cup type (we can only fill 2 cups at a time max)
        // 2. The ceiling of total cups divided by 2 (since we fill 2 cups per second)
        return Math.max(max, (sum + 1) / 2);
    }
}

Time Complexity: O(1)
Space Complexity: O(1)

Refer to Deepseek
For LeetCode 2335 "Minimum Amount of Time to Fill Cups", this is a greedy algorithm problem. Here are the Java solutions:
🎯 Optimal Greedy Solution
class Solution {
    public int fillCups(int[] amount) {
        int max = 0;
        int sum = 0;
        
        for (int cup : amount) {
            max = Math.max(max, cup);
            sum += cup;
        }
        
        // The answer is the maximum between:
        // 1. The largest single cup type (we can only fill 2 cups at a time max)
        // 2. The ceiling of total cups divided by 2 (since we fill 2 cups per second)
        return Math.max(max, (sum + 1) / 2);
    }
}

🔍 How the Algorithm Works
Key Insight
We can fill at most 2 cups per second. The minimum time is determined by:
1.The largest single type - if one type dominates, we need at least that many seconds
2.The total workload - we can fill at most 2 cups per second
Mathematical Formula:
min_time = max(largest_amount, ceil(total_amount / 2))

💡 Alternative Priority Queue Solution
class Solution {
    public int fillCups(int[] amount) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        for (int cup : amount) {
            if (cup > 0) {
                maxHeap.offer(cup);
            }
        }
        int time = 0;
        while (!maxHeap.isEmpty()) {
            int first = maxHeap.poll();
            if (maxHeap.isEmpty()) {
                time += first;
                break;
            }
            int second = maxHeap.poll();
            // Fill one cup of each of the two largest types
            time++;
            first--;
            second--;
            // Add back if still positive
            if (first > 0) maxHeap.offer(first);
            if (second > 0) maxHeap.offer(second);
        }
        return time;
    }
}
🧠 Step-by-Step Examples
Example 1: amount = [1, 4, 2]
Greedy calculation:
max = 4, sum = 7
result = max(4, ceil(7/2)) = max(4, 4) = 4

Step-by-step:
Second 1: Fill 2 types (4,2) → [1,3,1]  
Second 2: Fill 2 types (3,1) → [1,2,0]
Second 3: Fill 2 types (2,1) → [1,1,0]
Second 4: Fill 1 type (1) → [0,1,0]
Total: 4 seconds
Example 2: amount = [5, 4, 4]
Greedy calculation:
max = 5, sum = 13  
result = max(5, ceil(13/2)) = max(5, 7) = 7

Step-by-step: We need 7 seconds (cannot do better than 7)
Example 3: amount = [0, 0, 0]
Greedy calculation:
max = 0, sum = 0
result = max(0, ceil(0/2)) = 0
📊 Why the Greedy Formula Works
Case Analysis:
Case 1: One type dominates
[10, 2, 3] → max=10, sum=15 → max(10,8)=10
We need at least 10 seconds for the dominant type
Case 2: Balanced distribution
[3, 3, 3] → max=3, sum=9 → max(3,5)=5
We can fill 2 cups/second, so need ceil(9/2)=5 seconds
Case 3: Mixed scenario
[4, 5, 3] → max=5, sum=12 → max(5,6)=6
The total workload determines the time
🚀 Optimized One-Liner
class Solution {
    public int fillCups(int[] amount) {
        return Math.max(Math.max(amount[0], Math.max(amount[1], amount[2])), 
                       (amount[0] + amount[1] + amount[2] + 1) / 2);
    }
}
💡 Key Insights
1.Parallel Processing: We can fill 2 different cup types simultaneously
2.Bottleneck: Either the largest type or total workload limits us
3.Mathematical Bound: ceil(total/2) is the theoretical minimum
4.No Need for Simulation: The greedy formula gives the exact answer
📊 Complexity Analysis
Greedy Solution:
- Time: O(1) - constant time operations
- Space: O(1) - only a few variables
Heap Solution:
- Time: O(n log n) in worst case, but n=3 so effectively O(1)
- Space: O(1) - fixed size heap
🎯 Recommendation
The greedy mathematical solution is preferred because:
- ✅ Most efficient: O(1) time and space
- ✅ Simplest code: Easy to understand and implement
- ✅ No simulation needed: Direct mathematical answer
- ✅ Handles all edge cases: Including zeros and large numbers
The formula max(largest, ceil(total/2)) perfectly captures the problem's constraints!


Refer to
L1354.Construct Target Array With Multiple Sums (Ref.L2335)
L2141.Maximum Running Time of N Computers (Ref.L453,L2187,L2335)
