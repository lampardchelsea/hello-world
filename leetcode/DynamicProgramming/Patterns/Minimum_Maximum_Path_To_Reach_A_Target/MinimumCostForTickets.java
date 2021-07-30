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

// Solution 3: Bottom Up DP
// Refer to
// https://leetcode.com/problems/minimum-cost-for-tickets/discuss/504403/DP-it's-not-easy-as-you-thought
/**
dp[n] is the minimum cost to cover all days from day 1 until day n.

If day n is not one of our days, then we don't have to worry. The cost is the same as the cost to cover all days to day n-1 => dp[n] = dp[n-1]
If day n is one of our days, then we will check if buying a pass before can help us minimize the cost.
For example, buying a week-pass a week ago can cost: dp[n-7] + costs[1]
buying a month-pass a month ago can cost: dp[n-30] + cost[2]
There is a confusion here what if n-7 or n-30 is not one of our days ?? It doesn't matter at all. What we care is we prefer not buy at day n, 
but rather buy at the moment before day n.

days: 1,2,3,x,x(n-7),x,x,x,x,9,x,11,12(n),13
In this example, n-7 is not our days, we would rather buy a week-pass from day 9, than buy a week-pass on day 12. Note that dp[3] = dp[n-7] = dp[9]

private int dp_bottom_up(int[] days, int[] costs) {
    Set<Integer> set = new HashSet<>();
    for (int day : days) set.add(day);

    int lastDay = days[days.length-1], dp[] = new int[lastDay+1];
    for (int i = 1; i <= lastDay; i++) {
        if (!set.contains(i)) {
            dp[i] = dp[i-1];
        }
        else {
            dp[i] = dp[i-1]+costs[0];

            int j = (i >= 7) ? i-7 : 0;
            dp[i] = Math.min(dp[i], dp[j] + costs[1]);

            j = (i >= 30) ? i-30 : 0;
            dp[i] = Math.min(dp[i], dp[j] + costs[2]);
        }
    }

    return dp[lastDay];
}
*/

// Refer to
// https://leetcode.com/problems/minimum-cost-for-tickets/discuss/227130/Java-DP-Solution-with-detailed-comment-and-explanation
/**
dp[i] means up to i-th day the minimum cost of the tickets. The size of the dp array depends on the last travel day, 
so we don't need an array length to be 365.

We do need a boolean array to mark the travel days, the reason is if it is not a travel day we don't need a ticket. 
However, if it is a travel day, we consider three scenarios (with three types of tickects):

If a 1-day ticket on day i, dp[i] = dp[i - 1] + cost[0]
If a 7-day ticket ending on day i, dp[i] = min(dp[i - 7], dp[i - 6] ... dp[i - 1]) + cost[1]
If a 30-day ticket ending on day i, dp[i] = min(dp[i - 30], dp[i - 29] ... dp[i - 1]) + cost[2]

But since the value of dp array is increasing, therefore:
For a 7-day ticket ending on day i, dp[i] = dp[i - 7] + cost[1]
For a 30-day ticket ending on day i, dp[i] = dp[i - 30] + cost[2]

 public int mincostTickets(int[] days, int[] costs) {
    // length up to the last travel + 1 day is good enough (no need for 365)
    int lastDay = days[days.length - 1]; 
    // dp[i] means up to i-th day the minimum cost of the tickets
    int[] dp = new int[lastDay + 1]; 
    boolean[] isTravelDays = new boolean[lastDay + 1];
    // mark the travel days
    for(int day : days) isTravelDays[day] = true;
    
    for(int i = 1; i <= lastDay; i++) {
        if(!isTravelDays[i]) { // no need to buy ticket if it is not a travel day
            dp[i] = dp[i - 1];
            continue;
        }
        // select which type of ticket to buy
        dp[i] = costs[0] + dp[i - 1]; // 1-day
        dp[i] = Math.min(costs[1] + dp[Math.max(i - 7, 0)], dp[i]); // 7-day
        dp[i] = Math.min(costs[2] + dp[Math.max(i - 30, 0)], dp[i]); // 30-day
    }
    return dp[lastDay];
}
*/
