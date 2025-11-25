https://leetcode.com/problems/construct-target-array-with-multiple-sums/description/
You are given an array target of n integers. From a starting array arr consisting of n 1's, you may perform the following procedure :
- let x be the sum of all elements currently in your array.
- choose index i, such that 0 <= i < n and set the value of arr at index i to x.
- You may repeat this procedure as many times as needed.
Return true if it is possible to construct the target array from arr, otherwise, return false.
 
Example 1:
Input: target = [9,3,5]
Output: true
Explanation: Start with arr = [1, 1, 1] 
[1, 1, 1], sum = 3 choose index 1
[1, 3, 1], sum = 5 choose index 2
[1, 3, 5], sum = 9 choose index 0
[9, 3, 5] Done

Example 2:
Input: target = [1,1,1,2]
Output: false
Explanation: Impossible to create target array from [1,1,1,1].

Example 3:
Input: target = [8,5]
Output: true
 
Constraints:
- n == target.length
- 1 <= n <= 5 * 10^4
- 1 <= target[i] <= 10^9
--------------------------------------------------------------------------------
Attempt 1: 2025-11-23
Solution 1: Heap + Math + Greedy (60 min)
class Solution {
    public boolean isPossible(int[] target) {
        PriorityQueue<Long> maxPQ = new PriorityQueue<>((a, b) -> Long.compare(b, a));
        long total = 0;
        for(long num : target) {
            total += num;
            maxPQ.offer((Long)num);
        }
        // Reverse the process until all elements become 1
        while(!maxPQ.isEmpty() && maxPQ.peek() > 1) {
            long curMax = maxPQ.poll();
            long sumOfOthers = total - curMax;
            // Test out: [2]
            // But if sumOfOthers < 1, it means sumOfOthers = 0
            // Then prev = curMax % 0 is invalid
            if(sumOfOthers < 1) {
                return false;
            }
            // Test out: [8, 5]
            // When sumOfOthers = 1, the recurrence becomes:
            // prev = curMax - 1
            // We can keep subtracting 1 until we reach 1
            // This is guaranteed to work because:
            // currentMax → currentMax - 1 → ... → 1
            if(sumOfOthers == 1) {
                return true;
            }
            // Replaces repeated subtraction, handling cases where 
            // the same index is updated multiple times.
            long prev = curMax % sumOfOthers;
            // If prev equals 0, it means:
            // currentMax is a multiple of sumOfOthers
            // But we need previousValue >= 1 (since we start from 1's)
            // A value of 0 means we can't reach back to the initial [1,1,...,1] state
            if(prev == 0 || prev == curMax) {
                return false;
            }
            // Update the heap and the total sum
            maxPQ.offer(prev);
            total = prev + sumOfOthers;
        }
        return true;
    }
}

Time Complexity: O(nlogn)
Space Complexity: O(n)

Refer to Deepseek
The solution to this problem involves working backwards from the target array, using a max-heap to always process the largest element. Here is the Java implementation:
class Solution {
    public boolean isPossible(int[] target) {
        // Use a max-heap to always access the largest element
        PriorityQueue<Long> maxHeap = new PriorityQueue<>((a, b) -> Long.compare(b, a));
        long totalSum = 0;

        // Initialize the heap and calculate the total sum
        for (int num : target) {
            totalSum += num;
            maxHeap.offer((long) num);
        }

        // Reverse the process until all elements become 1
        while (!maxHeap.isEmpty() && maxHeap.peek() > 1) {
            long currentMax = maxHeap.poll();
            long sumOfOthers = totalSum - currentMax;

            // Check for conditions that make transformation impossible
            if (sumOfOthers < 1) return false;
            if (sumOfOthers == 1) return true; // Handles edge case like [1, 1000000000]

            // Calculate the previous value before the last operation
            long previousValue = currentMax % sumOfOthers;

            // If the previous value is invalid, return false
            if (previousValue == 0 || previousValue == currentMax) {
                return false;
            }

            // Update the heap and the total sum
            maxHeap.offer(previousValue);
            totalSum = sumOfOthers + previousValue;
        }

        return true;
    }
}
🔍 How the Algorithm Works
The key is to reverse the process, starting from the target array and working back towards the initial array of all 1s. The table below breaks down the logic:
StepActionPurpose1Use a max-heap for the largest elementThe largest element must be the result of the most recent operation.2Calculate sumOfOthersThis helps find the element's value before the last operation.3Use modulo for optimizationReplaces repeated subtraction, handling cases where the same index is updated multiple times.4Check for sumOfOthers == 1Catches a valid edge case (e.g., [1, 1000000000]).5Check for invalid previousValueIf the result is 0 or unchanged, the transformation is impossible.
💡 Key Insights for Understanding
- Reverse Thinking: Trying to build the target array from all 1s is complex. It's more efficient to start from the target and repeatedly undo the last operation.
- Why the Largest Element?: In any valid state, the largest element must have been the one most recently replaced with the array's sum.
- Modulo is Key: The modulo operation efficiently reverses multiple operations on the same index at once. Without it, the algorithm could be too slow for large numbers.
📊 Complexity Analysis
- Time Complexity: The algorithm is efficient, operating in O(n log n) time for initial heap building. The while loop is optimized by the modulo operation.
- Space Complexity: O(n) is needed to store the elements in the priority queue.

I want to know background about below conditions, why and how we come up to these ideas ?
1.if (sumOfOthers < 1) return false; --> can test out by input = [2]
2.if (sumOfOthers == 1) return true; --> can test out by input = [8, 5]
3.if (previousValue == 0 || previousValue == currentMax) {return false;} --> first condition test out by input = [1, 1, 2], second condition test out by input = [1, 1, 1, 2]
🔍 Background Analysis of the Key Conditions
Let me explain the mathematical reasoning behind each condition and how we derive them:
🎯 Condition 1: if (sumOfOthers < 1) return false;
Mathematical Reasoning:
// The recurrence relation for the algorithm is:
previousValue = currentMax - sumOfOthers

// For this to be valid:
previousValue >= 1  (since we start from 1's)
=> currentMax - sumOfOthers >= 1
=> currentMax >= sumOfOthers + 1
=> sumOfOthers <= currentMax - 1

// But if sumOfOthers < 1, it means sumOfOthers = 0
// Then previousValue = currentMax - 0 = currentMax
// This creates an infinite loop!
Example: [2]
Initial: [2]
sumOfOthers = totalSum - currentMax = 2 - 2 = 0
previousValue = 2 - 0 = 2 (same as currentMax!) → Infinite loop
Why This Violates the Problem:
- We need to reduce numbers to 1, but with sumOfOthers = 0, we can't reduce
- The initial array must have at least two 1's to create any number > 1
- A single element array can only be [1]
🎯 Condition 2: if (sumOfOthers == 1) return true;
Mathematical Reasoning:
// When sumOfOthers = 1, the recurrence becomes:
previousValue = currentMax - 1

// We can keep subtracting 1 until we reach 1
// This is guaranteed to work because:
// currentMax → currentMax - 1 → ... → 1
Example: [8, 5]
Step-by-step reduction:
[8, 5] → sumOfOthers = 5, but let's see when sumOfOthers becomes 1:

[8, 5] → largest=8, sumOthers=5 → prev=8%5=3 → [3, 5]
[5, 3] → largest=5, sumOthers=3 → prev=5%3=2 → [3, 2]  
[3, 2] → largest=3, sumOthers=2 → prev=3%2=1 → [1, 2]
[2, 1] → largest=2, sumOthers=1 → THIS TRIGGERS CONDITION 2

At this point: [2, 1] with sumOfOthers=1
We can definitely reduce 2 to 1 by: 2 - 1 = 1 → [1, 1]
Why This Works:
- With sumOfOthers = 1, we can reduce any currentMax to 1 in exactly (currentMax - 1) steps
- Each step: newValue = currentValue - 1
🎯 Condition 3: if (previousValue == 0 || previousValue == currentMax)
Part 1: previousValue == 0
Mathematical Reasoning:
// previousValue = currentMax % sumOfOthers
// If this equals 0, it means:
currentMax is a multiple of sumOfOthers

// But we need previousValue >= 1 (since we start from 1's)
// A value of 0 means we can't reach back to the initial [1,1,...,1] state
Example: [1, 1, 2]
[1, 1, 2] → totalSum=4, largest=2, sumOthers=2
previousValue = 2 % 2 = 0

Why this fails:
We need to reduce [1,1,2] to [1,1,1]
But: 2 - (1+1) = 0, not 1!
We can never get back to 1 from 2 with sumOthers=2
Part 2: previousValue == currentMax
Mathematical Reasoning:
// previousValue = currentMax % sumOfOthers
// If previousValue == currentMax, it means:
currentMax < sumOfOthers

// But this violates the algorithm's assumption that:
// currentMax should be the LARGEST element
// If currentMax < sumOfOthers, our max-heap logic is broken
Example: [1, 1, 1, 2]
[1, 1, 1, 2] → totalSum=5, largest=2, sumOthers=3
previousValue = 2 % 3 = 2 (same as currentMax!)

Why this fails:
The largest element (2) is smaller than the sum of others (3)
This means 2 couldn't have been created by adding the sum of others
to a previous value (since that would make it larger than sum of others)
🧠 Derivation Process
Step 1: Understand the Reverse Process
Forward:  [1,1] → [1,2] → [3,2] → [3,5] → [8,5]
Backward: [8,5] → [3,5] → [3,2] → [1,2] → [1,1]
Step 2: Mathematical Formulation
// At each step:
totalSum = currentMax + sumOfOthers
previousValue = currentMax - sumOfOthers

// Using modulo for efficiency:
previousValue = currentMax % sumOfOthers
Step 3: Identify Invalid States
Through mathematical analysis and testing, we discover these impossible scenarios:
1.Division by zero: sumOfOthers = 0
2.Non-reducible multiples: currentMax % sumOfOthers = 0
3.Logic contradiction: currentMax < sumOfOthers
💡 Key Insights
The Algorithm Guarantees:
1.Monotonicity: Numbers only increase in forward process
2.Sum Preservation: newValue = oldValue + sumOfOthers
3.Minimum Value: All values start at and must be reducible to 1
The Conditions Ensure:
1.Progress: We always move toward smaller numbers
2.Termination: We eventually reach all 1's
3.Validity: Every step is mathematically possible
🎯 Summary
These conditions weren't arbitrary - they were derived mathematically by analyzing the reverse process and identifying states that violate the problem's fundamental constraints. Each condition prevents the algorithm from getting stuck in an infinite loop or pursuing an impossible path.
The conditions ensure that every step in the reverse process is valid, progressive, and terminating - the three essential properties of a correct algorithm!


Refer to
2335
1753
2141
L2448.Minimum Cost to Make Array Equal (Ref.L2968)
