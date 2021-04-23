/**
Refer to
https://www.lintcode.com/problem/pour-water/
Description
Given an elevation map, heights[i] representing the height of the terrain at that index. 
The width at each index is 1. After V units of water fall at index K, how much water is at each index?

Water first drops at index K, and then it flows according to the following rules:

First, the droplet can not move to higher level.
If the droplet would eventually fall by moving left, then move left.
Otherwise, if the droplet would eventually fall by moving right, then move right.
Otherwise, rise at it's current position.
Here, "eventually fall" means that the droplet will eventually be at a lower level if it moves in that direction. 
Also, "level" means the height of the terrain plus any water in that column.

Youcan assume there's infinitely high terrain on the two sides out of bounds of the array. Also, there could not 
be partial water being spread out evenly on more than 1 grid block - each unit of water has to be in exactly one block.

heights will have length in [1, 100][1,100] and contain integers in [0, 99][0,99].
V will be in range [0, 2000][0,2000].
K will be in range [0, heights.length - 1][0,heights.length−1].

Example

Example 1:
Input: heights = [2,1,1,2,1,2,2], V = 4, K = 3
Output: [2,2,2,3,2,2,2]
Explanation:
  #       #
  #       #
  ##  # ###
  #########
   0123456    <- index
   2112122    <- level(height)

  The first drop of water lands at index K = 3:

  #       #
  #   w   #
  ##  # ###
  #########
   0123456    

  When moving left or right, the water can only move to the same level or a lower level.
  (By level, we mean the total height of the terrain plus any water in that column.)
  Since moving left will eventually make it fall, it moves left.
  (A droplet "made to fall" means go to a lower height than it was at previously.)

  #       #
  #       #
  ## w# ###
  #########
   0123456    
   2122122

  Since moving left will not make it fall, it stays in place.  The next droplet falls:

  #       #
  #   w   #
  ## w# ###
  #########
   0123456  

  Since the new droplet moving left will eventually make it fall, it moves left.
  Notice that the droplet still preferred to move left,
  even though it could move right (and moving right makes it fall quicker.)

  #       #
  #  w    #
  ## w# ###
  #########
   0123456  

  #       #
  #       #
  ##ww# ###
  #########
   0123456  
   2222122

  After those steps, the third droplet falls.
  Since moving left would not eventually make it fall, it tries to move right.
  Since moving right would eventually make it fall, it moves right.

  #       #
  #   w   #
  ##ww# ###
  #########
   0123456  

  #       #
  #       #
  ##ww#w###
  #########
   0123456  
   2222222

  Finally, the fourth droplet falls.
  Since moving left would not eventually make it fall, it tries to move right.
  Since moving right would not eventually make it fall, it stays in place:

  #       #
  #   w   #
  ##ww#w###
  #########
   0123456  
   2223222

  The final answer is [2,2,2,3,2,2,2]:

      #    
  ####### 
  ####### 
  0123456 

Example 2:
Input: heights = [1,2,3,4], V = 2, K = 2
Output: [2,3,3,4]
Explanation:
  The last droplet settles at index 1, since moving further left would not cause it to eventually fall to a lower height.

Example 3:
Input: heights = [3,1,3], V = 5, K = 1
Output: [4,4,4]

Note:
heightswill have length in [1, 100] and contain integers in [0, 99].
V will be in range [0, 2000].
K will be in range [0, heights.length - 1].
*/

// Solution 1: Simulate
// Refer to
// https://aaronice.gitbook.io/lintcode/array/pour-water
/**
Solution & Analysis
题目很长，但是简而言之就是从一个点K倒水，水量为V，返回最终水倒完后的Array。
有一点需要注意的是水流动的方向顺序，先左，再右，如果左右都没有更低的地势，则留在那一点。且水不会平摊，也就是说会出现如下情况：
[2,1,1,2]
K = 2
V = 1

----

[2,1,2,2]
也就是水的最小单位为1，不会平摊。
开始想到用Trapping Rain Water的动态规划数组left[], right[]来记录左侧能填入的最深处，但是发现方法不好，因为倒水的过程实际在动态
改变left[], right[]，也就失去了先loop得到left[], right[]的价值。
直白解法就是模拟法：
对于每一滴水，往左找能达到的最低点，再往右找能达到的最低点。搜索过程中需要严格单调递减，如果相等则改变i，但是不调整最低点的index，
若>最低点的高度，则break。
直白解法 （4 ms, faster than 83.59%）
class Solution {
    public int[] pourWater(int[] heights, int V, int K) {
        // input validation
        if (heights == null || heights.length == 0 || V == 0) {
            return heights;
        }
        if (K < 0 || K > heights.length - 1) {
            return heights;
        }
        // assert K >= 0 && K < heights.length - 1 : "Invalid Input"; 

        int n = heights.length;

        int index;
        while (V > 0) {
            index = K; // reset for next pouring

            // find lowest / hole on the left
            for (int i = K - 1; i >= 0; i--) {
                if (heights[i] < heights[index]) {
                    index = i;
                } else if (heights[i] > heights[index]) {
                    break;
                }
            }

            if (index != K) {
                heights[index]++;
                V--;
                continue;
            }

            // find lowest on the right
            for (int i = K + 1; i < n; i++) {
                if (heights[i] < heights[index]) {
                    index = i;
                } else if (heights[i] > heights[index]) {
                    break;
                }
            }

            heights[index]++;
            V--;
        }

        return heights;
    }
}
// [2,1,1,2,1,2,2]
// V = 4, K = 3

LeetCode上一个解法，也是模拟法，但是很神奇很巧妙：
by @zxyperfect
Imagine water drop moves left, and then moves right, and then moves left to position K. The position it stops is where it will stay.
模拟水滴的动态过程：设一个指针curr，让水滴向左，向右，最后回到K，最终停下的位置就是加水位的位置。
class Solution {
    public int[] pourWater(int[] heights, int V, int K) {
        for(int i = 0; i < V; i++) {
            int cur = K;
            // Move left
            while(cur > 0 && heights[cur] >= heights[cur - 1]) {
                cur--;
            }
            // Move right
            while(cur < heights.length - 1 && heights[cur] >= heights[cur + 1]) {
                cur++;
            }
            // Move left to K
            while(cur > K && heights[cur] >= heights[cur - 1]) {
                cur--;
            }
            heights[cur]++;
        }

        return heights;
    }
}

PriorityQueue 解法
by @CoffeeMaker & @ShawnWangCMU
PriorityQueue记录左右最低点的index，并且在fill water drop的过程中可以动态地更新这个PQ。
class Solution {
    public int[] pourWater(int[] heights, int V, int K) {
        PriorityQueue<Integer> left = new PriorityQueue<>((a, b) -> (heights[a] == heights[b]) ? b - a : heights[a] - heights[b]);
        PriorityQueue<Integer> right = new PriorityQueue<>((a, b) -> (heights[a] == heights[b]) ? a - b : heights[a] - heights[b]);

        int i = K - 1, j = K + 1;
        for (int d = 0; d < V; d++) {
            while (i >= 0 && heights[i] <= heights[i + 1]) left.offer(i--);
            while (j < heights.length && heights[j] <= heights[j - 1]) right.offer(j++);
            int l = left.isEmpty() ? K : left.peek();
            int r = right.isEmpty() ? K : right.peek();
            if (heights[l] < heights[K]) {
                heights[l]++;
                left.poll();
                left.offer(l);
            } else if (heights[r] < heights[K]) {
                heights[r]++;
                right.poll();
                right.offer(r);
            } else {
                heights[K]++;
            }
        }

        return heights;
    }
}
*/
public class Solution {
    /**
     * @param heights: the height of the terrain
     * @param V: the units of water
     * @param K: the index
     * @return: how much water is at each index
     */
    public int[] pourWater(int[] heights, int V, int K) {
        // Write your code here
        while(V > 0) {
            // Reset for next pouring
            int index = K;
            // Find lowest / hole on the left
            for(int i = K - 1; i >= 0; i--) {
                if(heights[i] < heights[index]) {
                    index = i;
                // Be careful, strictly > and < relation here, not include
                // = since we want search for lower level position continue 
                // even encounter same level positions first
                } else if(heights[i] > heights[index]) {
                    break;
                }
            }
            // If find lower level poistion on left
            if(index != K) {
                heights[index]++;
                V--;
                continue;
            }
            // Find lowest on the right
            for(int i = K + 1; i < heights.length; i++) {
                if(heights[i] < heights[index]) {
                    index = i;
                } else if(heights[i] > heights[index]) {
                    break;
                }
            }
            heights[index]++;
            V--;
        }
        return heights;
    }
}
