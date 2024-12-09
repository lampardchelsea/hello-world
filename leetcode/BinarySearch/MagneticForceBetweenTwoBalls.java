/**
Refer to
https://leetcode.com/problems/magnetic-force-between-two-balls/
In universe Earth C-137, Rick discovered a special form of magnetic force between two balls if they are put 
in his new invented basket. Rick has n empty baskets, the ith basket is at position[i], Morty has m balls and 
needs to distribute the balls into the baskets such that the minimum magnetic force between any two balls is maximum.

Rick stated that magnetic force between two different balls at positions x and y is |x - y|.

Given the integer array position and the integer m. Return the required force.

Example 1:
Input: position = [1,2,3,4,7], m = 3
Output: 3
Explanation: Distributing the 3 balls into baskets 1, 4 and 7 will make the magnetic force between ball pairs 
[3, 3, 6]. The minimum magnetic force is 3. We cannot achieve a larger minimum magnetic force than 3.

Example 2:
Input: position = [5,4,3,2,1,1000000000], m = 2
Output: 999999999
Explanation: We can use baskets 1 and 1000000000.

Constraints:
n == position.length
2 <= n <= 10^5
1 <= position[i] <= 10^9
All integers in position are distinct.
2 <= m <= position.length
*/

// Solution 1: Binary Search to find last
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/BinarySearch/FindFirstAndLastPositionOfElementInSortedArray.java
/**
    public int findLast(int[] nums, int target) {
        // Check on null and empty case
        if(nums == null || nums.length == 0) {
            return -1;
        }
        int start = 0;
        int end = nums.length - 1;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            if(nums[mid] == target) {
                // Not directly return here, we need to find last happening
                // of one value in array, always keep removing the first
                // half of current subset, its tendency is try to find most
                // later one even we already find one item matching target
                // (Since there are duplicates, so even find a matching item
                // still need continuous process later section, should
                // not return directly)
                start = mid;
            } else if(nums[mid] < target) {
                // Use last position template should not write as 
                // 'start = mid + 1' or 'end = mid - 1',
                // because if we find the 'mid' position
                // item is the last one matching target, and we
                // minus 1, will cause time out exception
                // check lesson2_A for example
                start = mid + 1; // follow template we can use 'start = mid' here
            } else if(nums[mid] > target) {
                end = mid - 1; // follow template we can use 'end = mid' here
            }
        }
        // Check on 'end' first for requirement
        // about find the last position 
        if(nums[end] == target) {
            return end;
        }
        if(nums[start] == target) {
            return start;
        }
        return -1;
    }
*/

// https://leetcode.com/problems/magnetic-force-between-two-balls/discuss/794066/Simple-Explanation
/**
Let x be the required answer. Then, we know that we can also place all m balls even if minimum distance between any 
two were 1, 2, ... (x-1). But, we cannot place all of them if minimum distance between any two were (x+1), (x+2), ...

Search space becomes:
1, 2, 3, 4, 5, ... x, x+1, x+2, x+3 (Each number represents minimum distance between each ball)
T, T, T, T, T, ..... T, F, F, F .... (Can we place all m balls if minimum distance were above number? T = true, F = false)

Now, we need to find last occurrence of true in above array.

This is a general binary search structure. And turns the problem into:
https://leetcode.com/problems/first-bad-version/

class Solution {
    public int maxDistance(int[] position, int m) {
        Arrays.sort(position);

        int hi = 1000000000;
        int lo = 1;

        while (lo < hi) {
            int mi = (lo + hi + 1) / 2; // Read comments if you wanna know why +1 is done.
            if (check(position, mi, m)) {
                lo = mi;
            } else {
                hi = mi - 1;
            }
        }

        return lo;
    }

    private boolean check(int[] position, int minimumDistance, int m) {
        // Always place first object at position[0]
        int lastBallPosition = position[0];
        int ballsLeftToBePlaced = m - 1;
        for (int i = 1; i < position.length && ballsLeftToBePlaced != 0; ) {
            if (position[i] - lastBallPosition < minimumDistance) {
                // Try to place the next ball, since this ball isn't minimumDistance away from lastBall
                i++;
            } else {
                // Place the ball only if this ball is farther than the previous ball by minimumDistance
                lastBallPosition = position[i];
                ballsLeftToBePlaced--;
            }
        }
        return ballsLeftToBePlaced == 0;
    }
}
Complexity: O(n logn)
*/

// https://leetcode.com/problems/magnetic-force-between-two-balls/discuss/794066/Simple-Explanation/658189
/**
public int maxDistance(int[] position, int m) {
        Arrays.sort(position);
        int low = 1;
        int hi = position[position.length-1]; // since array is sorted then max possible distance can be first and last element
        while(low < hi) {
            int mid = low + (hi - low)/2;
            if(canPlace(position, m, mid)) {
                low = mid+1;
            } else {
                hi = mid;
            }
        }
        return low -1;
    }

    public boolean canPlace(int[] position, int noOfBalls, int minDistance) {
        int lastPos = position[0]; // first ball at 0 position - keep track of last position
        int idx=1;
        noOfBalls--; // we have placed first ball at 0 position
        while(idx < position.length && noOfBalls > 0) {
            // if minDistance between last position and position at idx is greater then or equal to minDistance
            // then place the ball else skip
            if(position[idx] - lastPos >= minDistance) {
                lastPos = position[idx];
                noOfBalls--;
            }
            idx++;
        }
        return noOfBalls == 0; // if all balls have been placed then return true else false
    }
*/

class Solution {
    public int maxDistance(int[] position, int m) {
        Arrays.sort(position);
        int lo = 1;
        int hi = position[position.length - 1];
        while(lo + 1 < hi) {
            int mid = lo + (hi - lo) / 2;
            if(checkIfAbleToPutAllBalls(position, mid, m)) {
                lo = mid;
            } else {
                hi = mid;
            }
        }
        return lo;
    }
    
    private boolean checkIfAbleToPutAllBalls(int[] position, int minimum, int m) {
        int prev_pos = position[0];
        int balls_left = m - 1;
        int i = 1;
        while(i < position.length && balls_left > 0) {
            if(position[i] - prev_pos >= minimum) {
                balls_left--;
                prev_pos = position[i];
            }
            i++;
        }
        return balls_left == 0;
    }
}
























































https://leetcode.com/problems/magnetic-force-between-two-balls/description/
In the universe Earth C-137, Rick discovered a special form of magnetic force between two balls if they are put in his new invented basket. Rick has n empty baskets, the ith basket is at position[i], Morty has m balls and needs to distribute the balls into the baskets such that the minimum magnetic force between any two balls is maximum.
Rick stated that magnetic force between two different balls at positions x and y is |x - y|.
Given the integer array position and the integer m. Return the required force.

Example 1:

Input: position = [1,2,3,4,7], m = 3
Output: 3
Explanation: Distributing the 3 balls into baskets 1, 4 and 7 will make the magnetic force between ball pairs [3, 3, 6]. The minimum magnetic force is 3. We cannot achieve a larger minimum magnetic force than 3.

Example 2:
Input: position = [5,4,3,2,1,1000000000], m = 2
Output: 999999999
Explanation: We can use baskets 1 and 1000000000.
 
Constraints:
- n == position.length
- 2 <= n <= 10^5
- 1 <= position[i] <= 10^9
- All integers in position are distinct.
- 2 <= m <= position.length
--------------------------------------------------------------------------------
Attempt 1: 2024-12-08
Solution 1: Sorting + Binary Search + Greedy (30 min)
Style 1: canDistributeBalls
class Solution {
    public int maxDistance(int[] position, int m) {
        int len = position.length;
        Arrays.sort(position);
        int lo = 1;
        int hi = position[len - 1] - position[0];
        // Find upper boundary (since we want to find maximum  
        // value of minimum magnetic force between any two balls)
        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            // If current maximum value minimum magnetic force between
            // any two balls able to distribute m balls, we can move 
            // forward left boundary 'lo' to 'mid + 1' to attempt a larger 
            // value, otherwise if cannot distribute m balls, we can move 
            // backward right boundary 'hi' to 'mid - 1' to attempt a 
            // smaller value
            if(canDistributeBalls(position, m, mid)) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return lo - 1;
    }

    private boolean canDistributeBalls(int[] position, int m, int maxMinMagForceBetweenTwoBalls) {
        // Place the first ball at position[0]
        int count = 1;
        int lastPos = position[0];
        // Iterate through the sorted positions, placing the next ball only 
        // if the distance from the last placed ball is at least minDist.
        for(int i = 1; i < position.length; i++) {
            if(position[i] - lastPos >= maxMinMagForceBetweenTwoBalls) {
                count++;
                // Update last position only when able to place a ball
                lastPos = position[i];
                // Successfully placed all balls
                if(count == m) {
                    return true;
                } 
            }
        }
        // Not enough balls placed
        return false;
    }
}

Time Complexity: O(nlogn)
Space Complexity: O(1)
Style 2: cannotDistributeBalls
class Solution {
    public int maxDistance(int[] position, int m) {
        int len = position.length;
        Arrays.sort(position);
        int lo = 1;
        int hi = position[len - 1] - position[0];
        // Find upper boundary (since we want to find maximum  
        // value of minimum magnetic force between any two balls)
        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            // If current maximum value minimum magnetic force between
            // any two balls not able to distribute m balls, we can move 
            // backward right boundary 'hi' to 'mid - 1' to attempt a 
            // smaller value, otherwise if can distribute m balls, we 
            // can move backward left boundary 'lo' to 'mid + 1' to 
            // attempt a larger value
            if(cannotDistributeBalls(position, m, mid)) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return lo - 1;
    }

    private boolean cannotDistributeBalls(int[] position, int m, int maxMinMagForceBetweenTwoBalls) {
        // Place the first ball at position[0]
        int count = 1;
        int lastPos = position[0];
        // Iterate through the sorted positions, placing the next ball only 
        // if the distance from the last placed ball is at least minDist.
        for(int i = 1; i < position.length; i++) {
            if(position[i] - lastPos >= maxMinMagForceBetweenTwoBalls) {
                count++;
                // Update last position only when able to place a ball
                lastPos = position[i];
            }
        }
        // If not able to place m balls return true
        return count < m;
    }
}

Time Complexity: O(nlogn)
Space Complexity: O(1)

Refer to chatGPT
This is a binary search on the answer problem. The task is to maximize the minimum magnetic force by strategically placing balls.
Key Idea:
1.Binary Search:
- The minimum distance is 1 (balls placed in adjacent positions).
- The maximum distance is max⁡(position)−min⁡(position) (balls placed at the extreme ends of the sorted positions).
- Use binary search to find the largest distance x that satisfies the condition.
2.Feasibility Check:
- For a given x, place the balls starting from the smallest position.
- If m balls can be placed such that the distance between any two consecutive balls is at least x, then x is feasible.

class Solution {
    public int maxDistance(int[] position, int m) {
        // Sort the positions
        Arrays.sort(position);

        // Binary search bounds
        int left = 1;
        int right = position[position.length - 1] - position[0];
        int result = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (canPlaceBalls(position, m, mid)) {
                result = mid; // Update result and try for a larger distance
                left = mid + 1;
            } else {
                right = mid - 1; // Decrease the distance
            }
        }

        return result;
    }

    private boolean canPlaceBalls(int[] position, int m, int minDist) {
        int count = 1; // Place the first ball
        int lastPlaced = position[0];

        for (int i = 1; i < position.length; i++) {
            if (position[i] - lastPlaced >= minDist) {
                count++;
                lastPlaced = position[i];
                if (count == m) {
                    return true; // Successfully placed all balls
                }
            }
        }

        return false; // Not enough balls placed
    }
}
Explanation:
1.Sorting:
- The position array is sorted to allow placing balls in increasing order of positions.
2.Binary Search:
- Start with left=1 and right=max(position) - min(position).
- Use the mid-point of the current range to test if it's feasible to place m balls.
3.canPlaceBalls Function:
- Place the first ball at the first position.
- Iterate through the sorted positions, placing the next ball only if the distance from the last placed ball is at least minDist.
- Return true if m balls are successfully placed, otherwise false.

Refer to
L410.Split Array Largest Sum (Ref.L1011,L704,L1482)
L1011.Capacity To Ship Packages Within D Days (Ref.L410,L1482)
L1283.Find the Smallest Divisor Given a Threshold (Ref.L410,L1011,L1482)
L1482.Minimum Number of Days to Make m Bouquets (Ref.L410,L1011)
L1802.Maximum Value at a Given Index in a Bounded Array (Ref.L410)
L2064.Minimized Maximum of Products Distributed to Any Store (Ref.L410)
L2226.Maximum Candies Allocated to K Children (Ref.L1802,L1552)
