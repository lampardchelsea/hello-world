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

// Solution 1: Native DFS (TLE)
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

// Solution 2: 
