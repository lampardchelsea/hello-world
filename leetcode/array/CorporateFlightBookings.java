/**
Refer to
https://leetcode.com/problems/corporate-flight-bookings/
There are n flights, and they are labeled from 1 to n.

We have a list of flight bookings.  The i-th booking bookings[i] = [i, j, k] means that we booked k seats from flights labeled i to j inclusive.

Return an array answer of length n, representing the number of seats booked on each flight in order of their label.

Example 1:
Input: bookings = [[1,2,10],[2,3,20],[2,5,25]], n = 5
Output: [10,55,45,25,25]

Constraints:
1 <= bookings.length <= 20000
1 <= bookings[i][0] <= bookings[i][1] <= n <= 20000
1 <= bookings[i][2] <= 10000
*/

// Solution 1: Sweep Line
// Refer to
// https://leetcode.com/problems/corporate-flight-bookings/discuss/328856/JavaC%2B%2BPython-Sweep-Line
/**
Explanation
Set the change of seats for each day.
If booking = [i, j, k],
it needs k more seat on ith day,
and we don't need these seats on j+1th day.
We accumulate these changes then we have the result that we want.

Complexity
Time O(booking + N) for one pass on bookings
Space O(N) for the result
*/

// https://leetcode.com/problems/corporate-flight-bookings/discuss/328871/C%2B%2BJava-with-picture-O(n)
/**
Intuition
Since ranges are continuous, what if we add reservations to the first flight in the range, and remove them after the last 
flight in range? We can then use the running sum to update reservations for all flights.

This picture shows the logic for this test case: [[1,2,10],[2,3,20],[3,5,25]]. n = 5
-----------|------------------------|-----------------------------
Flights    | 1    2    3    4    5  | 
Reserve#1  | 10       -10           |
Reserve#2  |      20       -20      |
Reserve#3  |           25           |(out of boundary n no need -25)
-----------|------------------------|-----------------------------
All Reserve| 10   20   15  -20      |
           |     +10  +30  +45  +25 |
Result     | 10   30   45   25   25 |
-----------|------------------------|----------------------------

Java Solution
  public int[] corpFlightBookings(int[][] bookings, int n) {
    int[] res = new int[n];
    for (int[] v : bookings) {
      res[v[0] - 1] += v[2];
      if (v[1] < n) res[v[1]] -= v[2];
    }
    for (int i = 1; i < n; ++i) res[i] += res[i - 1];
    return res;
  }

Complexity Analysis
Runtime: O(n), where n is the number of flights (or bookings).
Memory: O(n)
*/

// https://leetcode.com/problems/corporate-flight-bookings/discuss/331192/Easy-Python-O(n)-Let's-step-through-the-algorithm
/**
The linear solution relies on a "mathematical trick" (derivative?), meaning we can find the final result by computing the 
cumulative sum so we don't need to compute every element within the range of seats.

Let's take this example:
[[1,2,10],[2,3,20],[2,5,25]], n = 5

The first booking is [1,2,10] where 1-2 is the range, and 10 the number of seats

If you look at the code below, it becomes obvious the result array is changed to:
[10,0,-10,0,0]

Note we only changed 2 cells. This would be a huge runtime-saver if the range was 1-2000.

(There's actually an extra 0 in the result array to stay within bounds, since we mark the end of the range with -seats)

We all know after the first loop, the result array must be [10,10,0,0,0]. This is exactly what we get if we take the cumulative sum for [10,0,-10,0,0].

This is just a simple demonstration of the cumulative sum. We actually only do this at the very end of the algorithm, when 
we have stepped through all the bookings and marked/updated the ranges appropriately.

If we do this for all the bookings, and then apply the cumulative sum, we get this:
[10, 45, -10, -20, 0, 25, -25] -> [10, 55, 45, 25, 25, 0]

The only tricky part is paying attention to the 0-indexed result array because we added 1 for padding to mark the end of the range.

So we return only the first n elements.

See the code below.
class Solution:
    def corpFlightBookings(self, bookings: List[List[int]], n: int) -> List[int]:
        result = [0] * (n+1)
        #mark the range with the deltas (head and tail)
        for booking in bookings:
            #start
            result[booking[0]-1] += booking[2]
            #end
            result[booking[1]] -= booking[2]
        #cumulative sum processing
        tmp = 0
        for i in range(n):
            tmp += result[i]
            result[i] = tmp
        return result[:n]
*/
class Solution {
    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] result = new int[n];
        for(int[] b : bookings) {
            result[b[0] - 1] += b[2];
            if(b[1] < n) {
                result[b[1]] -= b[2];
            }
        }
        for(int i = 1; i < n; i++) {
            result[i] += result[i - 1];
        }
        return result;
    }
}



































































Attempt 1: 2023-12-16
Solution 1: Line Sweep (10 min)

class Solution {
    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] delta = new int[n];
        Arrays.sort(bookings, (a, b) -> a[0] - b[0]);
        for(int i = 0; i < bookings.length; i++) {
            // '-1' to mapping 1-indexed 'start' flight number to 0-indexed array
            int start = bookings[i][0] - 1;
            // '+1' to make sure decrease after 'end' flight number only
            // '-1' to mapping 1-indexed 'end' flight number to 0-indexed array
            int end = bookings[i][1] + 1 - 1;
            delta[start] += bookings[i][2];
            // 'end' flight number edge case, when handling the last concatenate
            // flight we don't need to decrease
            // e.g flights = [[1,2,10],[2,3,20],[2,5,25]], n = 5, check out [2,5,25]
            if(end < n) {
                delta[end] -= bookings[i][2];
            }
        }
        int[] result = new int[n];
        int count = 0;
        for(int i = 0; i < delta.length; i++) {
            count += delta[i];
            result[i] = count;
        }
        return result;
    }
}

Time Complexity: O(N)
Space Complexity: O(N)
