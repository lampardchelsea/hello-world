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

// Style 2:
// Refer to
// https://leetcode.com/discuss/general-discussion/786126/python-powerful-ultimate-binary-search-template-solved-many-problems
/**
The above problems are quite easy to solve, because they already give us the array to be searched. We'd know that we should 
use binary search to solve them at first glance. However, more often are the situations where the search space and search 
target are not so readily available. Sometimes we won't even realize that the problem should be solved with binary search -- 
we might just turn to dynamic programming or DFS and get stuck for a very long time.

As for the question "When can we use binary search?", my answer is that, If we can discover some kind of monotonicity, 
for example, if condition(k) is True then condition(k + 1) is True, then we can consider binary search.

1011. Capacity To Ship Packages Within D Days [Medium]
A conveyor belt has packages that must be shipped from one port to another within D days. The i-th package on the conveyor 
belt has a weight of weights[i]. Each day, we load the ship with packages on the conveyor belt (in the order given by weights). 
We may not load more weight than the maximum weight capacity of the ship.

Return the least weight capacity of the ship that will result in all the packages on the conveyor belt being shipped within D days.

Example :
Input: weights = [1,2,3,4,5,6,7,8,9,10], D = 5
Output: 15

Explanation: 
A ship capacity of 15 is the minimum to ship all the packages in 5 days like this:
1st day: 1, 2, 3, 4, 5
2nd day: 6, 7
3rd day: 8
4th day: 9
5th day: 10

Note that the cargo must be shipped in the order given, so using a ship of capacity 14 and splitting the packages into parts 
like (2, 3, 4, 5), (1, 6, 7), (8), (9), (10) is not allowed. 
Binary search probably would not come to our mind when we first meet this problem. We might automatically treat weights as search 
space and then realize we've entered a dead end after wasting lots of time. In fact, we are looking for the minimal one among all 
feasible capacities. We dig out the monotonicity of this problem: if we can successfully ship all packages within D days with 
capacity m, then we can definitely ship them all with any capacity larger than m. Now we can design a condition function, let's 
call it feasible, given an input capacity, it returns whether it's possible to ship all packages within D days. This can run in 
a greedy way: if there's still room for the current package, we put this package onto the conveyor belt, otherwise we wait for 
the next day to place this package. If the total days needed exceeds D, we return False, otherwise we return True.

Next, we need to initialize our boundary correctly. Obviously capacity should be at least max(weights), otherwise the conveyor 
belt couldn't ship the heaviest package. On the other hand, capacity need not be more thansum(weights), because then we can ship 
all packages in just one day.

Now we've got all we need to apply our binary search template:

def shipWithinDays(weights: List[int], D: int) -> int:
    def feasible(capacity) -> bool:
        days = 1
        total = 0
        for weight in weights:
            total += weight
            if total > capacity:  # too heavy, wait for the next day
                total = weight
                days += 1
                if days > D:  # cannot ship within D days
                    return False
        return True

    left, right = max(weights), sum(weights)
    while left < right:
        mid = left + (right - left) // 2
        if feasible(mid):
            right = mid
        else:
            left = mid + 1
    return left
*/
class Solution {
    public int shipWithinDays(int[] weights, int D) {
        int lo = 0;
        int hi = 0;
        for(int weight : weights) {
            hi += weight;
            lo = Math.max(lo, weight);
        }
        while(lo < hi) { // quit loop when lo == hi
            int mid = lo + (hi - lo) / 2;
            // If total days equal or smaller than D means threshold = mid
            // still valid, we can continue shrink the upper bound 'hi',
            // 'mid' itself is also valid in this case
            if(totalDays(weights, mid) <= D) {
                hi = mid;
            } else {
            // If total days larger than D means threshold = mid not valid,
            // we have to increase lower bound 'lo' to 'mid + 1', '+1' means
            // 'mid' itself is also not valid in this case
                lo = mid + 1;
            }
        }
        // Return either 'lo' or 'hi' is same thing, since when lo == hi
        // we exist while loop
        return lo;
    }
    
    private int totalDays(int[] weights, int maxCapacity) {
        int total = 0;
        int count = 1;
        for(int weight : weights) {
            total += weight;
            if(total > maxCapacity) {
                total = weight;
                count++;
            }
        }
        return count;
    }
}

























































https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/description/
A conveyor belt has packages that must be shipped from one port to another within days days.
The ith package on the conveyor belt has a weight of weights[i]. Each day, we load the ship with packages on the conveyor belt (in the order given by weights). We may not load more weight than the maximum weight capacity of the ship.
Return the least weight capacity of the ship that will result in all the packages on the conveyor belt being shipped within days days.

Example 1:
Input: weights = [1,2,3,4,5,6,7,8,9,10], days = 5
Output: 15
Explanation: A ship capacity of 15 is the minimum to ship all the packages in 5 days like this:
1st day: 1, 2, 3, 4, 5
2nd day: 6, 7
3rd day: 8
4th day: 9
5th day: 10
Note that the cargo must be shipped in the order given, so using a ship of capacity 14 and splitting the packages into parts like (2, 3, 4, 5), (1, 6, 7), (8), (9), (10) is not allowed.

Example 2:
Input: weights = [3,2,2,4,1,4], days = 3
Output: 6
Explanation: A ship capacity of 6 is the minimum to ship all the packages in 3 days like this:
1st day: 3, 2
2nd day: 2, 4
3rd day: 1, 4

Example 3:
Input: weights = [1,2,3,1,1], days = 4
Output: 3
Explanation:
1st day: 1
2nd day: 2
3rd day: 3
4th day: 1, 1
 
Constraints:
- 1 <= days <= weights.length <= 5 * 10^4
- 1 <= weights[i] <= 500
--------------------------------------------------------------------------------
Attempt 1: 2024-12-06
Solution 1: Binary Search + Greedy (10 min, same as L410.Split Array Largest Sum)
class Solution {
    public int shipWithinDays(int[] weights, int days) {
        int max = 0;
        int sum = 0;
        for(int w : weights) {
            max = Math.max(max, w);
            sum += w;
        }
        int lo = max;
        int hi = sum;
        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if(findMinShipCapacity(weights, days, mid)) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    private boolean findMinShipCapacity(int[] weights, int days, int minShipCapacity) {
        int requireDays = 1;
        long sum = 0;
        for(int w : weights) {
            sum += w;
            if(sum > minShipCapacity) {
                requireDays++;
                sum = w;
                // If required days exceed determined days, return false
                if(requireDays > days) {
                    return false;
                }
            }
        }
        return true;
    }
}

Refer to
L410.Split Array Largest Sum (Ref.L1011,L704)
