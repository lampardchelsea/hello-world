/**
Refer to
https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/
A conveyor belt has packages that must be shipped from one port to another within D days.

The ith package on the conveyor belt has a weight of weights[i]. Each day, we load the ship with packages on the 
conveyor belt (in the order given by weights). We may not load more weight than the maximum weight capacity of the ship.

Return the least weight capacity of the ship that will result in all the packages on the conveyor belt being shipped within D days.

Example 1:
Input: weights = [1,2,3,4,5,6,7,8,9,10], D = 5
Output: 15
Explanation: A ship capacity of 15 is the minimum to ship all the packages in 5 days like this:
1st day: 1, 2, 3, 4, 5
2nd day: 6, 7
3rd day: 8
4th day: 9
5th day: 10

Note that the cargo must be shipped in the order given, so using a ship of capacity 14 and splitting the packages 
into parts like (2, 3, 4, 5), (1, 6, 7), (8), (9), (10) is not allowed.

Example 2:
Input: weights = [3,2,2,4,1,4], D = 3
Output: 6
Explanation: A ship capacity of 6 is the minimum to ship all the packages in 3 days like this:
1st day: 3, 2
2nd day: 2, 4
3rd day: 1, 4

Example 3:
Input: weights = [1,2,3,1,1], D = 4
Output: 3
Explanation:
1st day: 1
2nd day: 2
3rd day: 3
4th day: 1, 1

Constraints:
1 <= D <= weights.length <= 5 * 104
1 <= weights[i] <= 500
*/

// Solution 1: Binary Search
// Refer to
// https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/discuss/256765/Python-Binary-search-with-detailed-explanation
/**
The intuition for this problem, stems from the fact that

a) Without considering the limiting limiting days D, if we are to solve, the answer is simply max(a)
b) If max(a) is the answer, we can still spend O(n) time and greedily find out how many partitions it will result in.

[1,2,3,4,5,6,7,8,9,10], D = 5

For this example, assuming the answer is max(a) = 10, disregarding D,
we can get the following number of days:
[1,2,3,4] [5] [6] [7] [8] [9] [10]

So by minimizing the cacpacity shipped on a day, we end up with 7 days, by greedily chosing the packages for a day limited by 10.

To get to exactly D days and minimize the max sum of any partition, we do binary search in the sum space which is bounded by [max(a), sum(a)]

Binary Search Update:
One thing to note in Binary Search for this problem, is even if we end up finding a weight, that gets us to D partitions, 
we still want to continue the space on the minimum side, because, there could be a better minimum sum that still passes <= D paritions.

In the code, this is achieved by:

if res <= d:
     hi = mid
With this check in place, when we narrow down on one element, lo == hi, we will end up with exactly the minimum sum that leads to <= D partitions.

def shipWithinDays(self, a: List[int], d: int) -> int:
        lo, hi = max(a), sum(a)   
        while lo < hi:
            mid = (lo + hi) // 2
            tot, res = 0, 1
            for wt in a:
                if tot + wt > mid:
                    res += 1
                    tot = wt
                else:
                    tot += wt
            if res <= d:
                hi = mid
            else:
                lo = mid+1
        return lo
*/

// https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/discuss/256729/JavaC++Python-Binary-Search/249951
/**
This is basically the same problem as FairWorkload from TopCoder.
Time complexity: O(n * logSIZE), where SIZE is the size of the search space (sum of weights - max weight).
Space complexity: O(1).

In function getRequiredDays, why initialize requiredDays as 1 instead of 0?
Because the loop doesn't take into account the last partition (i.e. doesn't do requiredDay++ for the last day)

public int shipWithinDays(int[] weights, int d) {
    int lo = getMax(weights);
    int hi = getSum(weights);
    while (lo < hi) {
        int capacity = (lo + hi) >>> 1; // avoid overflow. same as (lo + hi) / 2
        int requiredDays = getRequiredDays(weights, capacity);

        if (requiredDays > d) {
            lo = capacity + 1;
        } else {
            hi = capacity;
        }
    }
    return lo;
}

private int getRequiredDays(int[] weights, int maxCapacity) {
    int requiredDays = 1;
    int capacity = 0;
    for (int weight : weights) {
        capacity += weight;
        if (capacity > maxCapacity) {
            requiredDays++;
            capacity = weight;
        }
    }
    return requiredDays;
}

private int getSum(int[] arr) {
    int sum = 0;
    for (int val : arr) {
        sum += val;
    }
    return sum;
}

private int getMax(int[] arr) {
    int max = Integer.MIN_VALUE;
    for (int val : arr) {
        max = Math.max(max, val);
    }
    return max;
}
*/

class Solution {
    public int shipWithinDays(int[] weights, int D) {
        int lo = 0;
        int hi = 0;
        for(int weight : weights) {
            hi += weight;
            if(weight > lo) {
                lo = weight;
            } 
        }
        while(lo < hi) {
            int capacity = lo + (hi - lo) / 2;
            int requiredDays = findDays(weights, capacity);
            if(requiredDays > D) {
                lo = capacity + 1;
            } else {
                hi = capacity;
            }
        }
        return lo;
    }
    
    private int findDays(int[] weights, int maxCapacity) {
        int days = 1;
        int capacity = 0;
        for(int weight : weights) {
            capacity += weight;
            if(capacity > maxCapacity) {
                days++;
                capacity = weight;
            }
        }
        return days;
    }
}
