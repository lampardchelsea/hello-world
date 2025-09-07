https://leetcode.com/problems/calculate-money-in-leetcode-bank/description/
Hercy wants to save money for his first car. He puts money in the Leetcode bank every day.
He starts by putting in $1 on Monday, the first day. Every day from Tuesday to Sunday, he will put in $1 more than the day before. On every subsequent Monday, he will put in $1 more than the previous Monday.
Given n, return the total amount of money he will have in the Leetcode bank at the end of the nth day.
 
Example 1:
Input: n = 4
Output: 10
Explanation: After the 4th day, the total is 1 + 2 + 3 + 4 = 10.

Example 2:
Input: n = 10
Output: 37
Explanation: After the 10th day, the total is (1 + 2 + 3 + 4 + 5 + 6 + 7) + (2 + 3 + 4) = 37. Notice that on the 2nd Monday, Hercy only puts in $2.

Example 3:
Input: n = 20
Output: 96
Explanation: After the 20th day, the total is (1 + 2 + 3 + 4 + 5 + 6 + 7) + (2 + 3 + 4 + 5 + 6 + 7 + 8) + (3 + 4 + 5 + 6 + 7 + 8) = 96.
 
Constraints:
- 1 <= n <= 1000
--------------------------------------------------------------------------------
Attempt 1: 2025-09-07
Solution 1: Math (10 min)
class Solution {
    public int totalMoney(int n) {
        int weekBase = 28;
        int weeks = n / 7;
        int days = n % 7;
        int result = 0;
        for(int i = 0; i < weeks; i++) {
            result += (weekBase + 7 * i);
        }
        for(int i = 0; i < days; i++) {
            result += (i + weeks + 1);
        }
        return result;
    }
}

Time Complexity: O(1)
Space Complexity: O(1)

Refer to Deepseek
I'll provide a Java solution for LeetCode 1716. "Calculate Money in Leetcode Bank", along with an explanation and complexity analysis.
Problem Description
Hercy wants to save money for his first car. He puts money in the Leetcode bank every day.
He starts by putting in $1 on Monday, the first day. Every day from Tuesday to Sunday, he will put in $1 more than the day before. On every subsequent Monday, he will put in $1 more than the previous Monday.
Given n, return the total amount of money he will have in the Leetcode bank at the end of the n-th day.
Java Solution
class Solution {
    public int totalMoney(int n) {
        int total = 0;
        int week = 0;
        
        for (int day = 0; day < n; day++) {
            if (day % 7 == 0) {
                week++; // Start of a new week, increment base amount
            }
            total += week + (day % 7);
        }
        
        return total;
    }
}
Explanation
1.Initialization:
- total accumulates the total money saved.
- week represents the base amount for the current week (starts at 1 for the first week).
2.Loop through each day:
- For each day day from 0 to n-1:

- If it's the start of a new week (i.e., day % 7 == 0), increment week (which acts as the base for that week).
- The amount for the current day is week + (day % 7). For example:

- Week 1: Monday (day0) = 1 + 0 = 1, Tuesday (day1) = 1 + 1 = 2, ..., Sunday (day6) = 1 + 6 = 7.
- Week 2: Monday (day7) = 2 + 0 = 2, Tuesday (day8) = 2 + 1 = 3, etc.
- Add the day's amount to total.
3.Return:
- After processing all n days, return total.
Example Walkthrough
Let n = 10:
- Day0 (Monday, week1): week=1, amount=1+0=1 → total=1
- Day1 (Tuesday): week=1, amount=1+1=2 → total=3
- Day2 (Wednesday): 1+2=3 → total=6
- Day3 (Thursday): 1+3=4 → total=10
- Day4 (Friday): 1+4=5 → total=15
- Day5 (Saturday): 1+5=6 → total=21
- Day6 (Sunday): 1+6=7 → total=28
- Day7 (Monday, week2): week=2, amount=2+0=2 → total=30
- Day8 (Tuesday): 2+1=3 → total=33
- Day9 (Wednesday): 2+2=4 → total=37
So, the total at the end of day 10 is 37.
Alternative Solution (Using Mathematical Formula)
We can also solve this mathematically by dividing the days into full weeks and remaining days:
class Solution {
    public int totalMoney(int n) {
        int fullWeeks = n / 7;
        int remainingDays = n % 7;
        
        // Total for full weeks: arithmetic series for each week
        // Week1: 28, Week2: 28 + 7, Week3: 28 + 14, ... so sum = 28 * fullWeeks + 7 * (0 + 1 + ... + (fullWeeks-1))
        int total = 28 * fullWeeks + 7 * (fullWeeks * (fullWeeks - 1)) / 2;
        
        // For remaining days: base = fullWeeks + 1, then add 0,1,2,... for each day
        total += (remainingDays * (2 * (fullWeeks + 1) + (remainingDays - 1))) / 2;
        
        return total;
    }
}
Mathematical Explanation:
- Full weeks:

- The first week sums to 28 (1+2+3+4+5+6+7).
- Each subsequent week adds 7 more than the previous week (since each day is increased by 1).
- So, the sum for k full weeks is: 28*k + 7*(0+1+2+...+(k-1)) = 28*k + 7*(k*(k-1)/2.
- Remaining days:

- The base for the next week is k+1.
- The sum for r days is: (k+1) + (k+2) + ... + (k+r) = r*(2*(k+1) + (r-1))/2.
Complexity Analysis
- Time Complexity: O(1) for the mathematical solution, O(n) for the iterative solution.
- Space Complexity: O(1) for both.
The iterative solution is straightforward and efficient for n up to 1000 (as per constraints). The mathematical solution is constant time and optimal.
You can choose either based on your preference! Let me know if you need further clarification.

Refer to
L2591.Distribute Money to Maximum Children (Ref.L1103,L1716,L2305)
