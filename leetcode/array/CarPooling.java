/**
 Refer to
 https://leetcode.com/problems/car-pooling/
 You are driving a vehicle that has capacity empty seats initially available for passengers.  The vehicle only drives east (ie. it cannot turn around and drive west.)

Given a list of trips, trip[i] = [num_passengers, start_location, end_location] contains information 
about the i-th trip: the number of passengers that must be picked up, and the locations to pick them 
up and drop them off.  The locations are given as the number of kilometers due east from your 
vehicle's initial location.

Return true if and only if it is possible to pick up and drop off all passengers for all the given trips. 

Example 1:
Input: trips = [[2,1,5],[3,3,7]], capacity = 4
Output: false

Example 2:
Input: trips = [[2,1,5],[3,3,7]], capacity = 5
Output: true

Example 3:
Input: trips = [[2,1,5],[3,5,7]], capacity = 3
Output: true

Example 4:
Input: trips = [[3,2,7],[3,7,9],[8,3,9]], capacity = 11
Output: true

Constraints:
trips.length <= 1000
trips[i].length == 3
1 <= trips[i][0] <= 100
0 <= trips[i][1] < trips[i][2] <= 1000
1 <= capacity <= 100000
*/

// Solution 1: Less memory required but more for loop
// Refer to
// https://leetcode.com/problems/car-pooling/discuss/345993/Java-clean-code-with-no-additional-array-required
class Solution {
    public boolean carPooling(int[][] trips, int capacity) {
        if(trips == null || trips.length == 0) {
            return false;
        }
        int maxEnd = 0;
        for(int i = 0; i < trips.length; i++) {
            if(trips[i][2] > maxEnd) {
                maxEnd = trips[i][2];
            }
        }
        int result = 0;
        for(int i = 0; i <= maxEnd; i++) {
            for(int j = 0; j < trips.length; j++) {
                if(trips[j][1] == i) {
                    result += trips[j][0];
                }
                if(trips[j][2] == i) {
                    result -= trips[j][0];
                }                
            }
            if(result > capacity) {
                return false;
            }
        }
        return true;
    }
}

// Solution 2: More memory required but less less for loop
// Refer to
// https://leetcode.com/problems/car-pooling/discuss/345993/Java-clean-code-with-no-additional-array-required
class Solution {
    public boolean carPooling(int[][] trips, int capacity) {
        int maxDist = 1000;
        int[] starts = new int[maxDist + 1];
        int[] ends = new int[maxDist + 1];
        for(int[] trip : trips) {
            starts[trip[1]] += trip[0];
            ends[trip[2]] += trip[0];
        }
        int total = 0;
        for(int i = 0; i <= maxDist; i++) {
            total += starts[i] - ends[i];
            if(total > capacity) {
                return false;
            }
        }
        return true;
    }
}
