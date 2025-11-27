https://leetcode.com/problems/maximum-score-from-removing-stones/description/
You are playing a solitaire game with three piles of stones of sizes a​​​​​​, b,​​​​​​ and c​​​​​​ respectively. Each turn you choose two different non-empty piles, take one stone from each, and add 1 point to your score. The game stops when there are fewer than two non-empty piles (meaning there are no more available moves).
Given three integers a​​​​​, b,​​​​​ and c​​​​​, return the maximum score you can get.
 
Example 1:
Input: a = 2, b = 4, c = 6
Output: 6
Explanation: The starting state is (2, 4, 6). One optimal set of moves is:
- Take from 1st and 3rd piles, state is now (1, 4, 5)
- Take from 1st and 3rd piles, state is now (0, 4, 4)
- Take from 2nd and 3rd piles, state is now (0, 3, 3)
- Take from 2nd and 3rd piles, state is now (0, 2, 2)
- Take from 2nd and 3rd piles, state is now (0, 1, 1)
- Take from 2nd and 3rd piles, state is now (0, 0, 0)
There are fewer than two non-empty piles, so the game ends. 
Total: 6 points.

Example 2:
Input: a = 4, b = 4, c = 6
Output: 7
Explanation: The starting state is (4, 4, 6). One optimal set of moves is:
- Take from 1st and 2nd piles, state is now (3, 3, 6)
- Take from 1st and 3rd piles, state is now (2, 3, 5)
- Take from 1st and 3rd piles, state is now (1, 3, 4)
- Take from 1st and 3rd piles, state is now (0, 3, 3)
- Take from 2nd and 3rd piles, state is now (0, 2, 2)
- Take from 2nd and 3rd piles, state is now (0, 1, 1)
- Take from 2nd and 3rd piles, state is now (0, 0, 0)
There are fewer than two non-empty piles, so the game ends. 
Total: 7 points.

Example 3:
Input: a = 1, b = 8, c = 8
Output: 8
Explanation: One optimal set of moves is to take from the 2nd and 3rd piles for 8 turns until they are empty.
After that, there are fewer than two non-empty piles, so the game ends.
 
Constraints:
- 1 <= a, b, c <= 10^5
--------------------------------------------------------------------------------
Attempt 1: 2025-11-25
Solution 1: Heap (30 min)
class Solution {
    public int maximumScore(int a, int b, int c) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((x, y) -> y - x);
        maxHeap.offer(a);
        maxHeap.offer(b);
        maxHeap.offer(c);
        
        int score = 0;
        
        while (true) {
            int first = maxHeap.poll();
            int second = maxHeap.poll();
            
            // If either pile is empty, we can't remove 2 stones
            if (first == 0 || second == 0) {
                break;
            }
            
            // Remove one stone from each of the two largest piles
            score++;
            maxHeap.offer(first - 1);
            maxHeap.offer(second - 1);
        }
        
        return score;
    }
}

Time Complexity: O(n*logn)
Space Complexity: O(n)

Solution 2: Greedy + Math (30 min)
class Solution {
    public int maximumScore(int a, int b, int c) {
        // Put the three piles into an array and sort
        int[] piles = {a, b, c};
        Arrays.sort(piles);
        
        // The three sorted values: piles[0] ≤ piles[1] ≤ piles[2]
        int min = piles[0];
        int mid = piles[1];
        int max = piles[2];
        
        // Key insight: 
        // If the largest pile is greater than the sum of the two smaller ones,
        // we're limited by the two smaller piles
        // Otherwise, we can use all stones optimally
        if (max >= min + mid) {
            return min + mid;
        } else {
            return (a + b + c) / 2;
        }
    }
}

Time Complexity: O(1)
Space Complexity: O(1)

Refer to Deepseek
For LeetCode 1753 "Maximum Score From Removing Stones", this is another greedy algorithm problem similar to the cup filling problem. Here are the Java solutions:
🎯 Optimal Greedy Solution
class Solution {
    public int maximumScore(int a, int b, int c) {
        // Put the three piles into an array and sort
        int[] piles = {a, b, c};
        Arrays.sort(piles);
        
        // The three sorted values: piles[0] ≤ piles[1] ≤ piles[2]
        int min = piles[0];
        int mid = piles[1];
        int max = piles[2];
        
        // Key insight: 
        // If the largest pile is greater than the sum of the two smaller ones,
        // we're limited by the two smaller piles
        // Otherwise, we can use all stones optimally
        if (max >= min + mid) {
            return min + mid;
        } else {
            return (a + b + c) / 2;
        }
    }
}
🔍 How the Algorithm Works
Key Insight
We can remove 2 stones per operation (one from each of two different piles). The maximum score is limited by:
1.The two smallest piles - if the largest pile dominates
2.Total stones divided by 2 - if stones are balanced
Mathematical Formula:
if (max >= min + mid):
    score = min + mid
else:
    score = floor((a + b + c) / 2)
💡 Alternative Priority Queue Solution
class Solution {
    public int maximumScore(int a, int b, int c) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((x, y) -> y - x);
        maxHeap.offer(a);
        maxHeap.offer(b);
        maxHeap.offer(c);
        
        int score = 0;
        
        while (true) {
            int first = maxHeap.poll();
            int second = maxHeap.poll();
            
            // If either pile is empty, we can't remove 2 stones
            if (first == 0 || second == 0) {
                break;
            }
            
            // Remove one stone from each of the two largest piles
            score++;
            maxHeap.offer(first - 1);
            maxHeap.offer(second - 1);
        }
        
        return score;
    }
}
🧠 Step-by-Step Examples
Example 1: a=2, b=4, c=6
Greedy calculation:
Sorted: [2,4,6]
max=6, min+mid=2+4=6
Since 6 >= 6 → score = 2 + 4 = 6

Step-by-step simulation:
(4,6) → (3,5) → (2,4) → (1,3) → (0,2) → (0,1)
We removed from (4,6), (3,5), (2,4), (1,3), (0,2), (0,1) = 6 operations
Example 2: a=4, b=4, c=6
Greedy calculation:
Sorted: [4,4,6]  
max=6, min+mid=4+4=8
Since 6 < 8 → score = (4+4+6)/2 = 14/2 = 7

Step-by-step: We can achieve 7 operations
Example 3: a=1, b=8, c=8
Greedy calculation:
Sorted: [1,8,8]
max=8, min+mid=1+8=9
Since 8 < 9 → score = (1+8+8)/2 = 17/2 = 8 (integer division)
📊 Why the Greedy Formula Works
Case Analysis:
Case 1: One pile dominates
[1, 2, 10] → max=10, min+mid=3 → 10 ≥ 3
Score = 1 + 2 = 3
We exhaust the two smaller piles with the large one
Case 2: Balanced piles
[3, 3, 3] → max=3, min+mid=6 → 3 < 6  
Score = (3+3+3)/2 = 9/2 = 4 (we can do 4 operations)
Case 3: Mixed scenario
[2, 4, 6] → max=6, min+mid=6 → 6 ≥ 6
Score = 2 + 4 = 6
🚀 Optimized Without Array
class Solution {
    public int maximumScore(int a, int b, int c) {
        int max = Math.max(a, Math.max(b, c));
        int total = a + b + c;
        
        if (max >= total - max) {
            return total - max;
        } else {
            return total / 2;
        }
    }
}
💡 Mathematical Proof
Intuition:
- Each operation removes 2 stones (one from each of two piles)
- Maximum possible operations = floor(total_stones / 2)
- But we're constrained by needing stones from different piles
The Two Cases:
1.Dominant pile case: max ≥ total - max
- The largest pile can pair with both smaller piles until they're exhausted
- Score = sum of two smaller piles
2.Balanced case: max < total - max
- We can optimally distribute operations to use almost all stones
- Score = floor(total / 2)
📊 Complexity Analysis
Greedy Solution:
- Time: O(1) - constant time operations
- Space: O(1) - only a few variables
Heap Solution:
- Time: O(n) where n is the total stones (but very efficient in practice)
- Space: O(1) - fixed size heap
🎯 Recommendation
The greedy mathematical solution is preferred because:
- ✅ Most efficient: O(1) time and space
- ✅ Simplest code: Easy to understand and implement
- ✅ No simulation needed: Direct mathematical answer
- ✅ Elegant formula: Captures the problem's essence perfectly
The key insight is recognizing when the largest pile dominates versus when stones are balanced enough to achieve the theoretical maximum of floor(total/2) operations!

Can you elaborate how it achieve 7 operations ?
Example 2: a=4, b=4, c=6

Greedy calculation:
Sorted: [4,4,6]  
max=6, min+mid=4+4=8
Since 6 < 8 → score = (4+4+6)/2 = 14/2 = 7

Step-by-step: We can achieve 7 operations
Let me show you exactly how we achieve 7 operations step-by-step for a=4, b=4, c=6:
🔍 Step-by-Step Operation Sequence
We start with: Pile A=4, Pile B=4, Pile C=6
Operation 1: Remove from B and C
A=4, B=3, C=5  (removed from B and C)
Operation 2: Remove from A and C
A=3, B=3, C=4  (removed from A and C)
Operation 3: Remove from A and C
A=2, B=3, C=3  (removed from A and C)
Operation 4: Remove from B and C
A=2, B=2, C=2  (removed from B and C)
Operation 5: Remove from A and B
A=1, B=1, C=2  (removed from A and B)
Operation 6: Remove from A and C
A=0, B=1, C=1  (removed from A and C)
Operation 7: Remove from B and C
A=0, B=0, C=0  (removed from B and C)
Total: 7 operations! ✅
🎯 Visual Representation
Step 0: [4, 4, 6]
Step 1: [4, 3, 5]  (-B,-C)
Step 2: [3, 3, 4]  (-A,-C)  
Step 3: [2, 3, 3]  (-A,-C)
Step 4: [2, 2, 2]  (-B,-C)
Step 5: [1, 1, 2]  (-A,-B)
Step 6: [0, 1, 1]  (-A,-C)
Step 7: [0, 0, 0]  (-B,-C)
🧠 Strategy Behind the Sequence
The key is to avoid letting any pile dominate and to keep all piles as balanced as possible:
Phase 1: Reduce the largest pile (Operations 1-4)
- Focus on reducing pile C (the largest) while maintaining balance
- Alternate between pairing C with A and C with B
- By operation 4, all piles are equal: [2, 2, 2]
Phase 2: Balanced reduction (Operations 5-7)
- With all piles equal, we can optimally use all remaining stones
- Each operation reduces two piles
- We finish with all piles empty simultaneously
💡 Why This Achieves Maximum
Mathematical Limit:
Total stones = 4 + 4 + 6 = 14
Theoretical maximum operations = floor(14 / 2) = 7
Feasibility Check:
- No pile is so large that it prevents us from pairing it with others
- We can distribute operations to use all stones
- The balanced approach ensures no pile is "stranded"
🔄 Alternative Valid Sequence
Here's another way to achieve 7 operations:
[4,4,6] → (A,C) → [3,4,5]
[3,4,5] → (B,C) → [3,3,4]  
[3,3,4] → (A,B) → [2,2,4]
[2,2,4] → (A,C) → [1,2,3]
[1,2,3] → (B,C) → [1,1,2]
[1,1,2] → (A,B) → [0,0,2]
[0,0,2] → (C,C) ❌ CAN'T DO THIS! Wait, this fails!

Actually, the second sequence fails at the end because we can't take from the same pile twice.
This shows why the first sequence is better - it maintains balance throughout.
🎯 Key Insight
The success of achieving 7 operations depends on maintaining balance among the piles. If we let one pile become too large relative to the others, we might not be able to use all stones optimally.
The greedy formula (a + b + c) / 2 works because when max < min + mid, we can always find a sequence that uses almost all stones through careful balancing!
This example perfectly illustrates why the mathematical solution gives us the correct maximum of 7 operations!

Refer to
L1354.Construct Target Array With Multiple Sums (Ref.L2335)
