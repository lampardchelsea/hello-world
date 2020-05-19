/**
 Refer to
 https://leetcode.com/problems/rectangle-area-ii/
 We are given a list of (axis-aligned) rectangles.  Each rectangle[i] = [x1, y1, x2, y2] , 
 where (x1, y1) are the coordinates of the bottom-left corner, and (x2, y2) are the coordinates 
 of the top-right corner of the ith rectangle.

 Find the total area covered by all rectangles in the plane.  Since the answer may be too large, 
 return it modulo 10^9 + 7.
 
 Example 1:
 Input: [[0,0,2,2],[1,0,2,3],[1,0,3,1]]
 Output: 6
 Explanation: As illustrated in the picture.

 Example 2:
 Input: [[0,0,1000000000,1000000000]]
 Output: 49
 Explanation: The answer is 10^18 modulo (10^9 + 7), which is (10^9)^2 = (-7)^2 = 49.
 
 Note:
 1 <= rectangles.length <= 200
 rectanges[i].length = 4
 0 <= rectangles[i][j] <= 10^9
 The total area covered by all rectangles will never exceed 2^63 - 1 and thus will fit in a 64-bit signed integer.
*/

// Solution 1: TreeMap
// Refer to
// https://leetcode.com/problems/rectangle-area-ii/discuss/137941/Java-TreeMap-solution-inspired-by-Skyline-and-Meeting-Room
// https://www.cnblogs.com/grandyang/p/11371256.html
/**
 这种解法更是利用了微积分的原理，把x轴长度为1当作一个步长，然后计算每一列有多少个连续的区间，每个连续区间又有多少个小正方形，
 题目中给的例子每一个列都只有一个连续区间，但事实上是可以有很多个的，只要算出了每一列 1x1 小正方形的个数，将所有列都累加起来，
 就是整个区域的面积。这里求每列上小正方形个数的方法非常的 tricky，博主也不知道该怎么讲解，大致就是要求同一列上每个连续区间中
 的小正方形个数，再累加起来。对于每个矩形起始的横坐标，映射较低的y值到1，较高的y值到 -1，对于结束位置的横坐标，刚好反过来一下，
 映射较低的y值到 -1，较高的y值到1。这种机制跟之前那道 [The Skyline Problem](http://www.cnblogs.com/grandyang/p/4534586.html) 
 有些异曲同工之妙，都还是为了计算高度差服务的。要搞清楚这道题的核心思想，不是一件容易的事，博主的建议是就拿题目中给的例子带入
 到下面的代码中，一步一步执行，并分析结果，是能够初步的了解解题思路的
*/
// https://leetcode.com/problems/rectangle-area-ii/discuss/137941/Java-TreeMap-solution-inspired-by-Skyline-and-Meeting-Room/147184
/**
 Collections.sort(data, (a,b)-> a.x-b.x); is enough, because you use TreeMap below, I've tried and lower the runtime.
 We can replace below with above one line
   Collections.sort(data, (a, b) -> {
        if (a.x == b.x) {
            return b.y - a.y;
        }
        return a.x - b.x;
    });
*/
class Solution {
    class Point {
        int x, y, val;
        Point(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }
    }
    
    public int rectangleArea(int[][] rectangles) {
        int M = 1000000007;
        List<Point> data = new ArrayList<>();
        for (int[] r : rectangles) {
            data.add(new Point(r[0], r[1], 1));
            data.add(new Point(r[0], r[3], -1));
            data.add(new Point(r[2], r[1], -1));
            data.add(new Point(r[2], r[3], 1));
        }
        Collections.sort(data, (a, b) -> a.x - b.x);
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int preX = -1;
        int preY = -1;
        int result = 0;
        for (int i = 0; i < data.size(); i++) {
            Point p = data.get(i);
            map.put(p.y, map.getOrDefault(p.y, 0) + p.val);
            if (i == data.size() - 1 || data.get(i + 1).x > p.x) {
                if (preX > -1) {
                    result += ((long)preY * (p.x - preX)) % M;
                    result %= M;
                }
                preY = calcY(map);
                preX = p.x;
            }
        }
        return result;
    }
    
    private int calcY(TreeMap<Integer, Integer> map) {
        int result = 0, pre = -1, count = 0;
        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            if (count > 0) {
                result += e.getKey() - pre;
            }
            count += e.getValue();
            pre = e.getKey();
        }
        return result;
    }
}

