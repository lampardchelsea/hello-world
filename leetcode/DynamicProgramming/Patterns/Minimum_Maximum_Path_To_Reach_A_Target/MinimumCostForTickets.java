/**
Refer to
https://leetcode.com/problems/minimum-cost-for-tickets/
You have planned some train traveling one year in advance. The days of the year in which you will travel are 
given as an integer array days. Each day is an integer from 1 to 365.

Train tickets are sold in three different ways:

a 1-day pass is sold for costs[0] dollars,
a 7-day pass is sold for costs[1] dollars, and
a 30-day pass is sold for costs[2] dollars.
The passes allow that many days of consecutive travel.

For example, if we get a 7-day pass on day 2, then we can travel for 7 days: 2, 3, 4, 5, 6, 7, and 8.
Return the minimum number of dollars you need to travel every day in the given list of days.

Example 1:
Input: days = [1,4,6,7,8,20], costs = [2,7,15]
Output: 11
Explanation: For example, here is one way to buy passes that lets you travel your travel plan:
On day 1, you bought a 1-day pass for costs[0] = $2, which covered day 1.
On day 3, you bought a 7-day pass for costs[1] = $7, which covered days 3, 4, ..., 9.
On day 20, you bought a 1-day pass for costs[0] = $2, which covered day 20.
In total, you spent $11 and covered all the days of your travel.

Example 2:
Input: days = [1,2,3,4,5,6,7,8,9,10,30,31], costs = [2,7,15]
Output: 17
Explanation: For example, here is one way to buy passes that lets you travel your travel plan:
On day 1, you bought a 30-day pass for costs[2] = $15 which covered days 1, 2, ..., 30.
On day 31, you bought a 1-day pass for costs[0] = $2 which covered day 31.
In total, you spent $17 and covered all the days of your travel.

Constraints:
1 <= days.length <= 365
1 <= days[i] <= 365
days is in strictly increasing order.
costs.length == 3
1 <= costs[i] <= 1000
*/

// Solution 1: Native DFS(TLE)
// Refer to
// https://leetcode.com/problems/minimum-cost-for-tickets/discuss/227321/Top-down-DP-Logical-Thinking
/**
Intuitively, we calculate total costs for each possible way of buying tickets and found the one with the minimum cost.
That causes TLE.
Why does it cause TLE? Because there are duplicate calculations.

Let's decompose the problem: minimum cost from day[i] for i is the next day to buy tickets
on day[i], we can buy 1-day pass, 7-day pass or 30-day pass.

If we buy 1-day pass, cost from day[i] = costs[0] + minimum cost from day[j] for j is the next day to buy tickets
If we buy 7-day pass, cost from day[i] = costs[1] + minimum cost from day[j] for j is the next day to buy tickets
If we buy 30-day pass, cost from day[i] = costs[2] + minimum cost from day[j] for j is the next day to buy tickets
minimum cost from day[i] is the minimum among 3 costs above
Optimal substructure is found. We could apply dynamic programing.

    private int[] memo;
    
    public int mincostTickets(int[] days, int[] costs) {
        memo = new int[days.length];
        return mincostTickets(0, days, costs);
    }
    
    private int mincostTickets(int dayIndex, int[] days, int[] costs) {
        if (dayIndex == days.length) {
            return 0;
        }
        
        if (memo[dayIndex] != 0) {
            return memo[dayIndex];
        }
        
        // Choose a pass, update dayIndex, and accumulate totalCost
        int totalCostDay = costs[0] + mincostTickets(getNextDayToBuy(dayIndex, days, 1), days, costs);   
        int totalCostWeek = costs[1] + mincostTickets(getNextDayToBuy(dayIndex, days, 7), days, costs);
        int totalCostMonth = costs[2] + mincostTickets(getNextDayToBuy(dayIndex, days, 30), days, costs);
        
        // Return the one with min totalCost
        memo[dayIndex] = Math.min(totalCostMonth, Math.min(totalCostDay, totalCostWeek));
        return memo[dayIndex];
    }
    
    private int getNextDayToBuy(int dayIndex, int[] days, int duration) {
        int endDay = days[dayIndex] + duration - 1;
        int newDayIndex = dayIndex;
		
        while (newDayIndex < days.length && days[newDayIndex] <= endDay) {
            newDayIndex++;
        }
        
        return newDayIndex;
    }
*/
class Solution {
    public int mincostTickets(int[] days, int[] costs) {
        return helper(0, days, costs);
    }
    
    private int helper(int dayIndex, int[] days, int[] costs) {
        if(dayIndex == days.length) {
            return 0;
        }
        int dayPassCost = helper(getNewDayIndex(dayIndex, days, 1), days, costs) + costs[0];
        int weekPassCost = helper(getNewDayIndex(dayIndex, days, 7), days, costs) + costs[1];
        int monthPassCost = helper(getNewDayIndex(dayIndex, days, 30), days, costs) + costs[2];
        return Math.min(dayPassCost, Math.min(weekPassCost, monthPassCost));
    }
    
    // Skip till newDayIndex day (in days array) as we are buying day/week/month pass
    private int getNewDayIndex(int dayIndex, int[] days, int duration) {
        int endDay = days[dayIndex] + duration - 1;
        int newDayIndex = dayIndex;
        while(newDayIndex < days.length && days[newDayIndex] <= endDay) {
            newDayIndex++;
        }
        return newDayIndex;
    }
}

// Solution 2: Top Down DP Memoization
// Refer to
// https://leetcode.com/problems/minimum-cost-for-tickets/discuss/227321/Top-down-DP-Logical-Thinking
class Solution {
    public int mincostTickets(int[] days, int[] costs) {
        int[] memo = new int[days.length];
        return helper(0, days, costs, memo);
    }
    
    private int helper(int dayIndex, int[] days, int[] costs, int[] memo) {
        if(dayIndex == days.length) {
            return 0;
        }
        if(memo[dayIndex] != 0) {
            return memo[dayIndex];
        }
        int dayPassCost = helper(getNewDayIndex(dayIndex, days, 1), days, costs, memo) + costs[0];
        int weekPassCost = helper(getNewDayIndex(dayIndex, days, 7), days, costs, memo) + costs[1];
        int monthPassCost = helper(getNewDayIndex(dayIndex, days, 30), days, costs, memo) + costs[2];
        memo[dayIndex] = Math.min(dayPassCost, Math.min(weekPassCost, monthPassCost));
        return memo[dayIndex];
    }
    
    private int getNewDayIndex(int dayIndex, int[] days, int duration) {
        int endDay = days[dayIndex] + duration - 1;
        int newDayIndex = dayIndex;
        while(newDayIndex < days.length && days[newDayIndex] <= endDay) {
            newDayIndex++;
        }
        return newDayIndex;
    }
}
