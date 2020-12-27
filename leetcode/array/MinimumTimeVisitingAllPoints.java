/**
Refer to
https://leetcode.com/problems/minimum-time-visiting-all-points/
On a 2D plane, there are n points with integer coordinates points[i] = [xi, yi]. Return the minimum time in seconds 
to visit all the points in the order given by points.

You can move according to these rules:

In 1 second, you can either:
move vertically by one unit,
move horizontally by one unit, or
move diagonally sqrt(2) units (in other words, move one unit vertically then one unit horizontally in 1 second).
You have to visit the points in the same order as they appear in the array.
You are allowed to pass through points that appear later in the order, but these do not count as visits.
 

Example 1:
Input: points = [[1,1],[3,4],[-1,0]]
Output: 7
Explanation: One optimal path is [1,1] -> [2,2] -> [3,3] -> [3,4] -> [2,3] -> [1,2] -> [0,1] -> [-1,0]   
Time from [1,1] to [3,4] = 3 seconds 
Time from [3,4] to [-1,0] = 4 seconds
Total time = 7 seconds

Example 2:
Input: points = [[3,2],[-2,2]]
Output: 5

Constraints:
points.length == n
1 <= n <= 100
points[i].length == 2
-1000 <= points[i][0], points[i][1] <= 1000
*/

// Solution 1: Intuitive calculate distance section by section
// Refer to
// https://leetcode.com/problems/minimum-time-visiting-all-points/discuss/436250/JavaPython-3-6-liner-and-1-liner-w-brief-explanation-and-analysis./571749
/**
Genius solution. I really loved this solution. Howeer, I found the explanation too terse and the intuition didn't make sense to me. 
I found a stackoverflow answer (https://stackoverflow.com/questions/59077134/minimum-time-visiting-all-points-understanding) to 
help a lot from a guy called Klaycon on stackoverflow who explained as follows:

"Think about it: If the next node is +10x and -5y away, it's going to take exactly 10 steps, because you can only move 1 x at a 
time and the difference in y is made up by diagonal moves during the process of overcoming the difference in x."

So the next node is max(abs(dx),abs(dy)) away.

This was way better than my initial solution of counting x_steps + y_steps + diagonal_steps. :S
*/
class Solution {
    public int minTimeToVisitAllPoints(int[][] points) {
        int result = 0;
        for(int i = 1; i < points.length; i++) {
            int x1 = points[i - 1][0];
            int y1 = points[i - 1][1];
            int x2 = points[i][0];
            int y2 = points[i][1];
            int x_delta = Math.abs(x1 - x2);
            int y_delta = Math.abs(y1 - y2);
            int diagonal_step = Math.min(x_delta, y_delta);
            int x_step = x_delta - diagonal_step;
            int y_step = y_delta - diagonal_step;
            result += diagonal_step + x_step + y_step;
        }
        return result;
    }
}


// Solution 2: Calculate minimum distance between two adjacent points
// Refer to
// https://leetcode.com/problems/minimum-time-visiting-all-points/discuss/436250/JavaPython-3-6-liner-and-1-liner-w-brief-explanation-and-analysis.
/**
Proof: the time cost to travel between 2 neighboring points equals the larger value between the absolute values of the difference 
of respective x and y coordinates of the 2 points.

a) Consider 2 points (x1, y1) and (x2, y2), let dx = |x1 - x2| and dy = |y1 - y2|; According to the constraints of the problem, 
each step at most moves 1 unit along x and/or y coordinate. Therefore, min time >= max(dx, dy);
b) On the other hand, each step can move 1 unit along x/y coordinate to cover the distance dx/dy, whichever is greater; 
Therefore, min time <= max(dx, dy);Combine the above a) and b), we have max(dx, dy) <= min time <= max(dx, dy) to complete the proof.

End of Proof

Q & A:
Q: Can you use an example to show max(dx, dy) <= min time <= max(dx, dy)?
A: Let us use the example 2 in problem description: points = [[3,2],[-2,2]].
dx = |3 - (-2)| = 5, dy = |2 - 2| = 0.
a) Since each step at most moves 1 unit along x and/or y coordinate, min time >= max(dx, dy) = 5;
b) On the other hand, each step can move 1 unit along both x and y coordinate, min time <= max(dx, dy) = 5;
Combine a) and b), min time = max(dx, dy) = 5.
End of Q & A

Traverse the input array, for each pair of neighboring points, comparing the absolute difference in x and y coordinates, 
the larger value is the minimum time need to travel between them;
Sum these time.

Java
    public int minTimeToVisitAllPoints(int[][] points) {
        int ans = 0;
        for (int i = 1; i < points.length; ++i) {
            int[] cur = points[i], prev = points[i - 1];
            ans += Math.max(Math.abs(cur[0] - prev[0]), Math.abs(cur[1] - prev[1]));
        }
        return ans;        
    }
*/
class Solution {
    public int minTimeToVisitAllPoints(int[][] points) {
        int result = 0;
        for(int i = 1; i < points.length; i++) {
            int[] cur = points[i];
            int[] prev = points[i - 1];
            result += Math.max(Math.abs(prev[0] - cur[0]), Math.abs(prev[1] - cur[1]));
        }
        return result;
    }
}
