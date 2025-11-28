https://leetcode.com/problems/maximum-running-time-of-n-computers/description/
You have n computers. You are given the integer n and a 0-indexed integer array batteries where the ith battery can run a computer for batteries[i] minutes. You are interested in running all n computers simultaneously using the given batteries.
Initially, you can insert at most one battery into each computer. After that and at any integer time moment, you can remove a battery from a computer and insert another battery any number of times. The inserted battery can be a totally new battery or a battery from another computer. You may assume that the removing and inserting processes take no time.
Note that the batteries cannot be recharged.
Return the maximum number of minutes you can run all the n computers simultaneously.
 
Example 1:

Input: n = 2, batteries = [3,3,3]
Output: 4
Explanation: 
Initially, insert battery 0 into the first computer and battery 1 into the second computer.
After two minutes, remove battery 1 from the second computer and insert battery 2 instead. Note that battery 1 can still run for one minute.
At the end of the third minute, battery 0 is drained, and you need to remove it from the first computer and insert battery 1 instead.
By the end of the fourth minute, battery 1 is also drained, and the first computer is no longer running.
We can run the two computers simultaneously for at most 4 minutes, so we return 4.

Example 2:

Input: n = 2, batteries = [1,1,1,1]
Output: 2
Explanation: 
Initially, insert battery 0 into the first computer and battery 2 into the second computer. 
After one minute, battery 0 and battery 2 are drained so you need to remove them and insert battery 1 into the first computer and battery 3 into the second computer. 
After another minute, battery 1 and battery 3 are also drained so the first and second computers are no longer running.We can run the two computers simultaneously for at most 2 minutes, so we return 2.
 
Constraints:
- 1 <= n <= batteries.length <= 10^5
- 1 <= batteries[i] <= 10^9
--------------------------------------------------------------------------------
Attempt 1: 2025-11-27
Solution 1: Binary Search (30 min)
class Solution {
    public long maxRunTime(int n, int[] batteries) {
        long sum = 0;
        for(int battery : batteries) {
            sum += battery;
        }
        long lo = 1;
        // Calculate the upper bound for binary search
        // Maximum possible time if all battery power is distributed evenly
        long hi = sum / n;
        // Binary search for the maximum running time
        while(lo <= hi) {
            long mid = lo + (hi - lo) / 2;
            if(canRun(n, batteries, mid)) {
                // Try for longer time
                lo = mid + 1;
            } else {
                // Reduce time
                hi = mid - 1;
            }
        }
        return lo - 1;
    }

    private boolean canRun(int n, int[] batteries, long maxRunningTime) {
        long totalPower = 0;
        // Calculate total available power for the given max running time
        for(int battery : batteries) {
            // Each battery can contribute at most 'maxRunningTime' units
            // (since it can only power one computer at a time)
            totalPower += Math.min(battery, maxRunningTime);
        }
        // Check if we have enough power to run all n computers for 'maxRunningTime' minutes
        return totalPower >= n * maxRunningTime;
    }
}

Time Complexity: O(m log(S/n)) where m = batteries.length, S = total battery sum
Space Complexity: O(1) - only a few variables

Refer to Deepseek
For LeetCode 2141 "Maximum Running Time of N Computers", this is a binary search + greedy problem. Here's the Java solution:
🎯 Binary Search Solution
class Solution {
    public long maxRunTime(int n, int[] batteries) {
        long left = 1;
        long right = 0;
        
        // Calculate the upper bound for binary search
        for (int battery : batteries) {
            right += battery;
        }
        right /= n;  // Maximum possible time if all battery power is distributed evenly
        
        // Binary search for the maximum running time
        while (left <= right) {
            long mid = left + (right - left) / 2;
            
            if (canRun(n, batteries, mid)) {
                left = mid + 1;  // Try for longer time
            } else {
                right = mid - 1;  // Reduce time
            }
        }
        
        return right;
    }
    
    private boolean canRun(int n, int[] batteries, long time) {
        long totalPower = 0;
        
        // Calculate total available power for the given time
        for (int battery : batteries) {
            // Each battery can contribute at most 'time' units
            // (since it can only power one computer at a time)
            totalPower += Math.min(battery, time);
        }
        
        // Check if we have enough power to run all n computers for 'time' minutes
        return totalPower >= n * time;
    }
}
🔍 How the Algorithm Works
Key Insight
We can use binary search to find the maximum time t such that:
- The total available battery power (capped at t per battery) is sufficient
- To run n computers for t minutes each: totalPower >= n * t
Binary Search Logic:
- Search space: [1, sum(batteries)/n]
- Check function: canRun(n, batteries, time)
- Result: The largest time where canRun returns true
💡 Alternative Greedy Solution
class Solution {
    public long maxRunTime(int n, int[] batteries) {
        Arrays.sort(batteries);
        long extra = 0;
        
        // Sum all batteries except the largest n batteries
        for (int i = 0; i < batteries.length - n; i++) {
            extra += batteries[i];
        }
        
        // The largest n batteries form our base
        int[] live = Arrays.copyOfRange(batteries, batteries.length - n, batteries.length);
        
        // Try to increase the running time by using extra power
        for (int i = 0; i < n - 1; i++) {
            // If the difference between current and next battery is too large
            // Use extra power to fill the gap
            long diff = (long)(live[i + 1] - live[i]) * (i + 1);
            
            if (extra >= diff) {
                extra -= diff;
                // All computers up to i+1 can run for live[i+1] minutes
            } else {
                // We can only increase up to a certain point
                return live[i] + extra / (i + 1);
            }
        }
        
        // If we reach here, all n largest batteries are balanced
        // Distribute remaining extra power evenly
        return live[n - 1] + extra / n;
    }
}
🧠 Step-by-Step Example
Example: n = 2, batteries = [3, 3, 3]
Binary Search Approach:
Search space: [1, (3+3+3)/2 = 4]

Check time=2:
  totalPower = min(3,2) + min(3,2) + min(3,2) = 2+2+2 = 6
  required = 2 * 2 = 4 → 6 >= 4 ✓

Check time=3:
  totalPower = min(3,3) + min(3,3) + min(3,3) = 3+3+3 = 9
  required = 2 * 3 = 6 → 9 >= 6 ✓

Check time=4:
  totalPower = min(3,4) + min(3,4) + min(3,4) = 3+3+3 = 9  
  required = 2 * 4 = 8 → 9 >= 8 ✓

But wait, time=4 is impossible! Let's see why...
Why Time=4 is Impossible:
We have 3 batteries: [3, 3, 3]
We need to run 2 computers for 4 minutes each = 8 total minutes

But each battery can only power one computer at a time.
The maximum we can get is 3+3+3 = 9 minutes, but distributed across 2 computers.

Actually, we CAN achieve 4 minutes:
Computer 1: battery1(3min) + battery3(1min) = 4min
Computer 2: battery2(3min) + battery3(2min) = 5min
Wait, this gives 4 and 5, but we need both to run 4 minutes.

Better distribution:
Computer 1: bat1(2min) + bat3(2min) = 4min
Computer 2: bat2(3min) + bat3(1min) = 4min
Yes, it works! So time=4 is achievable.
📊 Mathematical Foundation
The Check Function Logic:
private boolean canRun(int n, int[] batteries, long time) {
    long totalPower = 0;
    for (int battery : batteries) {
        totalPower += Math.min(battery, time);
    }
    return totalPower >= n * time;
}
Why this works:
- Each battery can contribute at most time units (it can't power a computer for longer than time)
- We need at least n * time total minutes of power
- If the sum of capped battery powers meets this requirement, it's possible
🚀 Optimized Binary Search
class Solution {
    public long maxRunTime(int n, int[] batteries) {
        long sum = 0;
        for (int battery : batteries) sum += battery;
        
        long left = 1, right = sum / n;
        
        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (canRun(n, batteries, mid)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return right;
    }
    
    private boolean canRun(int n, int[] batteries, long time) {
        long total = 0;
        for (int bat : batteries) {
            total += Math.min(bat, time);
            if (total >= n * time) return true;  // Early termination
        }
        return total >= n * time;
    }
}
💡 Key Insights
1.Battery Capping: A battery can't power a computer for longer than its capacity
2.Power Distribution: We can split battery power across multiple computers
3.Binary Search Applicability: The "can run for X minutes" property is monotonic
4.Efficient Checking: The greedy check runs in O(m) time where m = number of batteries
📊 Complexity Analysis
Binary Search Solution:
- Time: O(m log(S/n)) where m = batteries.length, S = total battery sum
- Space: O(1) - only a few variables
Greedy Solution:
- Time: O(m log m) for sorting
- Space: O(1) or O(n) depending on implementation
🎯 Recommendation
The binary search solution is preferred because:
- ✅ More intuitive: Easy to understand the "can we run for X minutes" check
- ✅ Efficient: Logarithmic time complexity
- ✅ Handles large constraints: Works well within problem limits
- ✅ No complex case analysis: Simple and robust
The key insight is recognizing that we can use binary search on the answer and verify feasibility with a greedy check!

Refer to
L453.Minimum Moves to Equal Array Elements (Ref.L462,L2137,L2491)
L2187.Minimum Time to Complete Trips (Ref.L410,L1011,L1283)
L2335.Minimum Amount of Time to Fill Cups (Ref.L1354)
L704.Binary Search
