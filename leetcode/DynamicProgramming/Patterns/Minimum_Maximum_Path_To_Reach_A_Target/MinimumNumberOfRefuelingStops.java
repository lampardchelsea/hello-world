/**
Refer to
https://leetcode.com/problems/minimum-number-of-refueling-stops/
A car travels from a starting position to a destination which is target miles east of the starting position.

There are gas stations along the way. The gas stations are represented as an array stations where stations[i] = [positioni, fueli] 
indicates that the ith gas station is positioni miles east of the starting position and has fueli liters of gas.

The car starts with an infinite tank of gas, which initially has startFuel liters of fuel in it. It uses one liter of gas per one 
mile that it drives. When the car reaches a gas station, it may stop and refuel, transferring all the gas from the station into the car.

Return the minimum number of refueling stops the car must make in order to reach its destination. If it cannot reach the destination, return -1.

Note that if the car reaches a gas station with 0 fuel left, the car can still refuel there. If the car reaches the destination 
with 0 fuel left, it is still considered to have arrived.

Example 1:
Input: target = 1, startFuel = 1, stations = []
Output: 0
Explanation: We can reach the target without refueling.

Example 2:
Input: target = 100, startFuel = 1, stations = [[10,100]]
Output: -1
Explanation: We can not reach the target (or even the first gas station).

Example 3:
Input: target = 100, startFuel = 10, stations = [[10,60],[20,30],[30,30],[60,40]]
Output: 2
Explanation: We start with 10 liters of fuel.
We drive to position 10, expending 10 liters of fuel.  We refuel from 0 liters to 60 liters of gas.
Then, we drive from position 10 to position 60 (expending 50 liters of fuel),
and refuel from 10 liters to 50 liters of gas.  We then drive to and reach the target.
We made 2 refueling stops along the way, so we return 2.

Constraints:
1 <= target, startFuel <= 109
0 <= stations.length <= 500
0 <= positioni <= positioni+1 < target
1 <= fueli < 109
*/

// Solution 1: Native DFS with for loops subset implementation (TLE)
// Refer to
// https://leetcode.com/problems/minimum-number-of-refueling-stops/discuss/613853/Python-5-solutions-gradually-optimizing-from-Naive-DFS-to-O(n)-space-DP
/**
class Solution(object):
    def minRefuelStops(self, target, startFuel, stations):
        """
        :type target: int
        :type startFuel: int
        :type stations: List[List[int]]
        :rtype: int
        """
        # 1) Naive DFS
        self.full_target = target
        def dfs(curFuel, start, target):
            if curFuel >= target:
                return 0
            rst = sys.maxsize
            for i in xrange(start, len(stations)):
                dis, fuel = stations[i][0] - (self.full_target - target), stations[i][1]
                if curFuel - dis >= 0:
                    rst = min(rst, dfs(curFuel - dis + fuel, i + 1, target - dis) + 1)
            return rst
        stops = dfs(startFuel, 0, target)
        return stops if stops != sys.maxsize else -1
*/
class Solution {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        // Set maximum stops as (stations.length + 1) not Integer.MAX_VALUE
        // to avoid stackoverflow, plus one because need at least larger
        // than potential actual maximum stops which equals to total stations
        int stops = stations.length + 1;
        stops = helper(startFuel, 0, target, target, stations);
        return stops != stations.length + 1 ? stops : -1;
    }
    
    public int helper(int curFuel, int start, int remain, int original_target, int[][] stations) {
        // Base case: since current fuel more than remain distance, no more stops need
        if(curFuel >= remain) {
            return 0;
        }
        int min_stops = stations.length + 1;
        for(int i = start; i < stations.length; i++) {
            int passed_distance = original_target - remain;
            int distance_to_ith_station = stations[i][0] - passed_distance;
            int fuel = stations[i][1];
            if(curFuel - distance_to_ith_station >= 0) {
                min_stops = Math.min(min_stops, helper(curFuel - distance_to_ith_station + fuel, i + 1, remain - distance_to_ith_station, original_target, stations) + 1);
            }
        }
        return min_stops;
    }
}

// Solution 2: DFS with for loops subset implementation + memorization (Memory Limit Exceeded)
// Refer to
// https://leetcode.com/problems/minimum-number-of-refueling-stops/discuss/613853/Python-5-solutions-gradually-optimizing-from-Naive-DFS-to-O(n)-space-DP
/**
# 2) DFS with for loops subset implementation + memorization
        self.full_target = target
        mem = dict()
        def dfs(curFuel, start, target):
            if curFuel >= target:
                return 0
            
            if (curFuel, start, target) in mem:
                return mem[(curFuel, start, target)]
            
            rst = sys.maxsize
            for i in xrange(start, len(stations)):
                dis, fuel = stations[i][0] - (self.full_target - target), stations[i][1]
                if curFuel - dis >= 0:
                    rst = min(rst, dfs(curFuel - dis + fuel, i + 1, target - dis) + 1)
            mem[(curFuel, start, target)] = rst
            return mem[(curFuel, start, target)]
        
        stops = dfs(startFuel, 0, target)
        return stops if stops != sys.maxsize else -1
*/

// https://leetcode.com/problems/minimum-number-of-refueling-stops/discuss/149958/Does-anyone-have-a-Top-Down-DP-version-of-this
/**
I got the recursive solution, but I am getting memory limit exceeded whenever I try to memoize.

class Solution {
    
    int N;
    int [][] stations;
    Map<Integer, Integer> map = new HashMap<>();
    int [][] memo;
    
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        this.N = stations.length;
        this.stations = stations;
        for(int i = 0; i < stations.length; ++i){
            int [] s = stations[i];
            map.put(s[0], i);
        }
        map.put(0, -1);
        memo = new int[stations.length + 1][10000000 + 1];
        for(int [] arr : memo) Arrays.fill(arr, -1);
        
        int sol = helper(0, startFuel, target);
        return (sol >= N + 1) ? -1 : sol;
    }
    
    public int helper(int station, int fuel, int target){
        if(memo[station][fuel] != -1) return memo[station][fuel];
        if(station + fuel >= target) return 0;
        if(fuel <= 0) return N + 1;
        int cur = N + 1;
        for(int i = map.get(station) + 1; i < stations.length; ++i){
            int [] current = stations[i];
            if(fuel - (current[0] - station) < 0) continue;
            cur = Math.min(cur, 1 + helper(current[0], fuel - (current[0] - station) + current[1], target));
        }
        memo[station][fuel] = cur;
        return cur;
    }
}
This does not work I am getting memory and time limit exceeded. Does anyone have an idea how to fix this?
*/
// Style 1: memo[i][j] means minimum stops needed for i liters fuel at jth station
// Memory Limit Exceeded when input as [1, 1], 0 test case passed
class Solution {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        // Need to memoize tuple of (current fuel, position of station) 
        // since these two values keep changing during you recursive calls
        // 1 <= target, startFuel <= 10^9 --> need (target + 1)
        // 1 <= fueli < 10^9 --> need (10^9)
        // memo[i][j] means minimum stops needed for i liters fuel at jth station
        Integer[][] memo = new Integer[1000000000][target + 1];
        int stops = stations.length + 1;
        stops = helper(startFuel, 0, target, target, stations, memo);
        return stops != stations.length + 1 ? stops : -1;
    }
    
    public int helper(int curFuel, int start, int remain, int original_target, int[][] stations, Integer[][] memo) {
        if(curFuel >= remain) {
            return 0;
        }
        if(memo[curFuel][start] != null) {
            return memo[curFuel][start];
        } 
        int min_stops = stations.length + 1;
        for(int i = start; i < stations.length; i++) {
            int passed_distance = original_target - remain;
            int distance_to_ith_station = stations[i][0] - passed_distance;
            int fuel = stations[i][1];
            if(curFuel - distance_to_ith_station >= 0) {
                min_stops = Math.min(min_stops, helper(curFuel - distance_to_ith_station + fuel, i + 1, remain - distance_to_ith_station, original_target, stations, memo) + 1);
            }
        }
        memo[curFuel][start] = min_stops;
        return memo[curFuel][start];
    }
}

// Style 2: memo[i][j] means minimum stops needed for ith station with j miles remain
// Memory Limit Exceeded when input as [1000000000, 1000000000], 14/198 test case passed
class Solution {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        // Need to memoize tuple of (position of station, remain distance to target) 
        // since these two values keep changing during you recursive calls
        // 1 <= target, startFuel <= 10^9 --> need (target + 1)
        // remain is same as target range
        // memo[i][j] means minimum stops needed for ith station with j miles remain
        Integer[][] memo = new Integer[target + 1][target + 1];
        int stops = stations.length + 1;
        stops = helper(startFuel, 0, target, target, stations, memo);
        return stops != stations.length + 1 ? stops : -1;
    }
    
    public int helper(int curFuel, int start, int remain, int original_target, int[][] stations, Integer[][] memo) {
        if(curFuel >= remain) {
            return 0;
        }
        if(memo[start][remain] != null) {
            return memo[start][remain];
        } 
        int min_stops = stations.length + 1;
        for(int i = start; i < stations.length; i++) {
            int passed_distance = original_target - remain;
            int distance_to_ith_station = stations[i][0] - passed_distance;
            int fuel = stations[i][1];
            if(curFuel - distance_to_ith_station >= 0) {
                min_stops = Math.min(min_stops, helper(curFuel - distance_to_ith_station + fuel, i + 1, remain - distance_to_ith_station, original_target, stations, memo) + 1);
            }
        }
        memo[start][remain] = min_stops;
        return memo[start][remain];
    }
}

// Solution 3: DFS taken/not taken subset implementation / 0-1 Knapsack (TLE, 114 / 198 test cases passed)
// Refer to
// https://leetcode.com/problems/minimum-number-of-refueling-stops/discuss/613853/Python-5-solutions-gradually-optimizing-from-Naive-DFS-to-O(n)-space-DP
/**
# 3) DFS taken/not taken subset implementation + memorization
        self.full_target = target
        mem = dict()
        def dfs(curFuel, start, target):
            if curFuel >= target:
                return 0
            
            if start == len(stations):
                return sys.maxsize
            
            if (curFuel, start, target) in mem:
                return mem[(curFuel, start, target)]
            
            dis, fuel = stations[start][0] - (self.full_target - target), stations[start][1]
            taken, not_taken = sys.maxsize, sys.maxsize
            if curFuel - dis >= 0:
                taken = dfs(curFuel - dis + fuel, start + 1, target - dis) + 1
                not_taken = dfs(curFuel - dis, start + 1, target - dis)
                
            mem[(curFuel, start, target)] = min(taken, not_taken)
            return mem[(curFuel, start, target)]
        
        stops = dfs(startFuel, 0, target)
        return stops if stops != sys.maxsize else -1
*/
class Solution {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        int stops = stations.length + 1;
        stops = helper(startFuel, 0, target, target, stations);
        return stops != stations.length + 1 ? stops : -1;
    }
    
    public int helper(int curFuel, int start, int remain, int original_target, int[][] stations) {
        if(curFuel >= remain) {
            return 0;
        }
        if(start == stations.length) {
            return stations.length + 1;
        }
        int passed_distance = original_target - remain;
        int distance_to_ith_station = stations[start][0] - passed_distance;
        int fuel = stations[start][1];
        // The minimum stops needed if take the 'start' indexed gas station to refuel
        int taken = stations.length + 1;
        // The minimum stops needed if not take the 'start' indexed gas station to refuel
        int not_taken = stations.length + 1;
        if(curFuel - distance_to_ith_station >= 0) {
            taken = helper(curFuel - distance_to_ith_station + fuel, start + 1, remain - distance_to_ith_station, original_target, stations) + 1;
            not_taken = helper(curFuel - distance_to_ith_station, start + 1, remain - distance_to_ith_station, original_target, stations);
        }
        return Math.min(taken, not_taken);
    }
}

// Solution 4: DFS taken/not taken subset implementation + memorization / 0-1 Knapsack / Top Down DP Memoization (TLE 103/198)
// Refer to
// https://leetcode.com/problems/minimum-number-of-refueling-stops/discuss/613853/Python-5-solutions-gradually-optimizing-from-Naive-DFS-to-O(n)-space-DP
/**
# 3) DFS taken/not taken subset implementation + memorization
        self.full_target = target
        mem = dict()
        def dfs(curFuel, start, target):
            if curFuel >= target:
                return 0
            
            if start == len(stations):
                return sys.maxsize
            
            if (curFuel, start, target) in mem:
                return mem[(curFuel, start, target)]
            
            dis, fuel = stations[start][0] - (self.full_target - target), stations[start][1]
            taken, not_taken = sys.maxsize, sys.maxsize
            if curFuel - dis >= 0:
                taken = dfs(curFuel - dis + fuel, start + 1, target - dis) + 1
                not_taken = dfs(curFuel - dis, start + 1, target - dis)
                
            mem[(curFuel, start, target)] = min(taken, not_taken)
            return mem[(curFuel, start, target)]
        
        stops = dfs(startFuel, 0, target)
        return stops if stops != sys.maxsize else -1
*/
class Solution {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        // Use Map and key set up as 'curFuel + "_" + start' is because traditional
        // way to create memo is hard to setup dimension size, especially for current
        // fuel, the fuel as given condition is 1 <= fueli < 10^9, which means dimension
        // size at least 10^9, which is easy to get Memory Limit Exceeded
        // So store two recursively changing keys 'curFuel' and 'start' as a String
        // combination is most practical way, but even this way encounter TLE for 103/198
        Map<String, Integer> memo = new HashMap<String, Integer>();
        int stops = stations.length + 1;
        stops = helper(startFuel, 0, target, target, stations, memo);
        return stops != stations.length + 1 ? stops : -1;
    }
    
    public int helper(int curFuel, int start, int remain, int original_target, int[][] stations, Map<String, Integer> memo) {
        if(curFuel >= remain) {
            return 0;
        }
        if(start == stations.length) {
            return stations.length + 1;
        }
        String key = curFuel + "_" + start;
        if(memo.containsKey(key)) {
            return memo.get(key);
        }
        int passed_distance = original_target - remain;
        int distance_to_ith_station = stations[start][0] - passed_distance;
        int fuel = stations[start][1];
        int taken = stations.length + 1;
        int not_taken = stations.length + 1;
        if(curFuel - distance_to_ith_station >= 0) {
            taken = helper(curFuel - distance_to_ith_station + fuel, start + 1, remain - distance_to_ith_station, original_target, stations, memo) + 1;
            not_taken = helper(curFuel - distance_to_ith_station, start + 1, remain - distance_to_ith_station, original_target, stations, memo);
        }
        int result = Math.min(taken, not_taken);
        memo.put(key, result);
        return result;
    }
}

// Solution 5: Bottom Up DP: reversed 0-1 knapsack (2D-DP)
// Refer to
// https://leetcode.com/problems/minimum-number-of-refueling-stops/discuss/613853/Python-5-solutions-gradually-optimizing-from-Naive-DFS-to-O(n)-space-DP
/**
# 4) DP: reversed 0-1 knapsack. 
        # Original 0-1 knapsack: maximum value given # of bags limitation: dp[i][j] = bool -- former i bags, j VALUE can be constructed or not 
        # Reversed 0-1 knapsack: minimum # of bags used to reach a given value dp[i][j] = value -- former i bags, j # OF BAGS being picked, what is the maximum value
        
        if startFuel >= target:
            return 0
        
        n = len(stations)
        # dp[i][j]: in former i stations, pick j stations to fuel, how far it can mostly reach
        dp = [[0] * (n + 1) for _ in xrange(n + 1)]
        for i in range(n + 1):
            dp[i][0] = startFuel
            
        rst = sys.maxsize
        for i in range(1, n + 1):
            # for j in range(i, 0, -1): ... both works, as long as the i - 1 row has finished, updating i row from left to right/right to left doesn't matter 
            for j in range(1, i + 1):  # j <= i because in former i stations, at most i stations can be picked
                dp[i][j] = max(dp[i][j], dp[i - 1][j])
                if dp[i - 1][j - 1] >= stations[i - 1][0]:
                    dp[i][j] = max(dp[i][j], dp[i - 1][j - 1] + stations[i - 1][1])
                if dp[i][j] >= target:
                    rst = min(rst, j)
        return rst if rst != sys.maxsize else -1
*/
