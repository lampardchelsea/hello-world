https://leetcode.com/problems/broken-calculator/description/
There is a broken calculator that has the integer startValue on its display initially. In one operation, you can:
- multiply the number on display by 2, or
- subtract 1 from the number on display.
Given two integers startValue and target, return the minimum number of operations needed to display target on the calculator.

Example 1:
Input: startValue = 2, target = 3
Output: 2
Explanation: Use double operation and then decrement operation {2 -> 4 -> 3}.

Example 2:
Input: startValue = 5, target = 8
Output: 2
Explanation: Use decrement and then double {5 -> 4 -> 8}.

Example 3:
Input: startValue = 3, target = 10
Output: 3
Explanation: Use double, decrement and double {3 -> 6 -> 5 -> 10}.
 
Constraints:
- 1 <= startValue, target <= 10^9
--------------------------------------------------------------------------------
Attempt 1: 2025-02-12
Solution 1: Greedy (10 min)
class Solution {
    public int brokenCalc(int startValue, int target) {
        // Instead of trying to transform startValue into target directly, 
        // it's often easier to work backward from target to startValue.
        int count = 0;
        while(target > startValue) {
            // If target is greater than startValue, the optimal way to 
            // reduce target is to divide it by 2 if it's even, or add 1 
            // if it's odd.
            if(target % 2 == 0) {
                target /= 2;
            } else {
                target += 1;
            }
            count++;
        }
        // If target is less than startValue, the only operation available 
        // is to subtract 1 repeatedly -> (startValue - target)
        return count + startValue - target;
    }
}

Time Complexity: O(log(target/startValue)), we do target/2 all the way until it's smaller than startValue, time complexity is O(log(target/startValue)).
Space Complexity: O(1)
Refer to Deepseek
To solve the LeetCode problem 991 "Broken Calculator", the goal is to determine the minimum number of operations required to transform a starting value X into a target value Y using only two operations:
1.Multiply X by 2.
2.Subtract 1 from X.
Approach
Instead of trying to transform X into Y directly, it's often easier to work backward from Y to X. This is because:
- If Y is greater than X, the optimal way to reduce Y is to divide it by 2 if it's even, or add 1 if it's odd.
- If Y is less than X, the only operation available is to subtract 1 repeatedly.
Steps:
1.Start from Y and work backward to X.
2.If Y is greater than X:
- If Y is even, divide it by 2.
- If Y is odd, add 1 to it.
3.Count the number of operations performed.
4.Once Y is less than or equal to X, the remaining operations are simply subtracting 1 (X - Y times).
Java Solution
class Solution {
    public int brokenCalc(int X, int Y) {
        int operations = 0;        
        while (Y > X) {
            if (Y % 2 == 0) {
                Y /= 2;
            } else {
                Y += 1;
            }
            operations++;
        }
        return operations + (X - Y);
    }
}
Explanation:
- We start with Y and repeatedly perform operations to reduce it to X.
- If Y is even, dividing by 2 is the most efficient way to reduce it.
- If Y is odd, adding 1 makes it even, allowing us to divide by 2 in the next step.
- Once Y is less than or equal to X, the remaining difference is handled by subtracting 1 repeatedly.
- The total number of operations is the sum of the operations performed in the loop and the remaining difference (X - Y).
Example:
- Input: X = 2, Y = 3
- Output: 2
- Operation 1: 3 is odd, so add 1 → 4
- Operation 2: 4 is even, so divide by 2 → 2
- Total operations: 2
This approach ensures that we find the minimum number of operations efficiently.

Refer to
L650.2 Keys Keyboard (Ref.L991,L2749)
L2749.Minimum Operations to Make the Integer Zero (Ref.L650,L991)
