https://leetcode.com/problems/perfect-rectangle/

Given an array rectangles where rectangles[i] = [xi, yi, ai, bi] represents an axis-aligned rectangle. The bottom-left point of the rectangle is (xi, yi) and the top-right point of it is (ai, bi).

Return true if all the rectangles together form an exact cover of a rectangular region.

Example 1:


```
Input: rectangles = [[1,1,3,3],[3,1,4,2],[3,2,4,4],[1,3,2,4],[2,3,3,4]]
Output: true
Explanation: All 5 rectangles together form an exact cover of a rectangular region.
```

Example 2:


```
Input: rectangles = [[1,1,2,3],[1,3,2,4],[3,1,4,2],[3,2,4,4]]
Output: false
Explanation: Because there is a gap between the two rectangular regions.
```

Example 3:


```
Input: rectangles = [[1,1,3,3],[3,1,4,2],[1,3,2,4],[2,2,4,4]]
Output: false
Explanation: Because two of the rectangles overlap with each other.
```
 
Constraints:
- 1 <= rectangles.length <= 2 * 104
- rectangles[i].length == 4
- -105 <= xi, yi, ai, bi <= 105
---
Attempt 1: 2023-03-05

Solution 1: Hash Table + two conditions to judge (30 min)
```
class Solution { 
    public boolean isRectangleCover(int[][] rectangles) { 
        String delimiter = "_"; 
        Set<String> corners_filter = new HashSet<String>(); 
        int area = 0; 
        // {x_out, y_out} -> outside rectangular region bottom left point 
        // {a_out, b_out} -> outside rectangular region top right point 
        int x_out = Integer.MAX_VALUE; 
        int y_out = Integer.MAX_VALUE; 
        int a_out = Integer.MIN_VALUE; 
        int b_out = Integer.MIN_VALUE; 
        for(int[] rectangle : rectangles) { 
            // Accumulate all rectangles area together  
            area += (rectangle[2] - rectangle[0]) * (rectangle[3] - rectangle[1]); 
            x_out = Math.min(x_out, rectangle[0]); 
            y_out = Math.min(y_out, rectangle[1]); 
            a_out = Math.max(a_out, rectangle[2]); 
            b_out = Math.max(b_out, rectangle[3]); 
            // Record all points 
            String bottom_left = rectangle[0] + delimiter + rectangle[1]; 
            String top_left = rectangle[0] + delimiter + rectangle[3]; 
            String bottom_right = rectangle[2] + delimiter + rectangle[1]; 
            String top_right = rectangle[2] + delimiter + rectangle[3]; 
            // Filter out all points, the corner filter should only remain 4 points 
            // and all other points should first add into filter then removed if 
            // encoutner again, which declare its any other point's number should 
            // only be even counter 
            if(!corners_filter.add(bottom_left)) { 
                corners_filter.remove(bottom_left); 
            } 
            if(!corners_filter.add(top_left)) { 
                corners_filter.remove(top_left); 
            } 
            if(!corners_filter.add(bottom_right)) { 
                corners_filter.remove(bottom_right); 
            } 
            if(!corners_filter.add(top_right)) { 
                corners_filter.remove(top_right); 
            } 
        } 
        // If corner filter only contains 4 points match the requirement, 
        // and these 4 points must be tag by combinations of x_out, y_out, 
        // a_out, b_out, otherwise not able to construct as rectangle 
        if(corners_filter.size() != 4 ||  
        !corners_filter.contains(x_out + delimiter + y_out) || 
        !corners_filter.contains(x_out + delimiter + b_out) || 
        !corners_filter.contains(a_out + delimiter + y_out) || 
        !corners_filter.contains(a_out + delimiter + b_out)) { 
            return false; 
        } 
        // 'area' must also match the requirement 
        return area == (a_out - x_out) * (b_out - y_out); 
    } 
}

Time Complexity:O(n)   
Space Complexity:O(n)
```

Refer to
https://walkccc.me/LeetCode/problems/0391/
The right answer must satisfy two conditions:
1. the large rectangle area should be equal to the sum of small rectangles
2. count of all the points should be even, and that of all the four corner points should be one
```
class Solution { 
  public boolean isRectangleCover(int[][] rectangles) { 
    int area = 0; 
    int x1 = Integer.MAX_VALUE; 
    int y1 = Integer.MAX_VALUE; 
    int x2 = Integer.MIN_VALUE; 
    int y2 = Integer.MIN_VALUE; 
    Set<String> corners = new HashSet<>(); 
    for (int[] r : rectangles) { 
      area += (r[2] - r[0]) * (r[3] - r[1]); 
      x1 = Math.min(x1, r[0]); 
      y1 = Math.min(y1, r[1]); 
      x2 = Math.max(x2, r[2]); 
      y2 = Math.max(y2, r[3]); 
      // Four points of current rectangle 
      String[] points = new String[] { 
        r[0] + " " + r[1], 
        r[0] + " " + r[3], 
        r[2] + " " + r[1], 
        r[2] + " " + r[3] 
      }; 
      for (final String point : points) 
        if (!corners.add(point)) 
          corners.remove(point); 
    } 
    if (corners.size() != 4) 
      return false; 
    if (!corners.contains(x1 + " " + y1) || 
        !corners.contains(x1 + " " + y2) || 
        !corners.contains(x2 + " " + y1) || 
        !corners.contains(x2 + " " + y2)) 
      return false; 
    return area == (x2 - x1) * (y2 - y1); 
  } 
}
```
- Time: O(n)
- Space: O(n)

Proof
https://leetcode.com/problems/perfect-rectangle/solutions/87181/really-easy-understanding-solution-o-n-java/comments/92034
Thanks for sharing this nice solution. For those who are concerned with the validity of the two conditions, here is a quick proof.

Proof to condition 1 is straightforward so I will focus on condition 2. First by observation we know it holds true for a perfect rectangle (consider how many small rectangles can overlap at a particular point: the number has to be even except for the four corner points of the prefect rectangle). The real question is whether it can also be true for some non-perfect rectangle.

Let's begin with the question: what will a non-perfect rectangle look like? Of course it can look rather complicated but here is a simple way to define it: any non-perfect rectangle can be obtained by a sequence of adding or removing rectangular parts from its perfect counterpart (the order for adding or removing does not matter here). If condition 1 is held true, the non-perfect rectangle must be built by both adding and removing small rectangles such that the total area of added rectangles compensates for those of removed.

Without loss of generality, let's focus on the first rectangle (denoted as FR) that is being removed (i.e., we have perfect rectangle before removing it). FR has four corner points. Before removing it, each corner points will appear even times (here I assume FR does not contain any corner points of the perfect rectangle. See my comment below for more details). After it's gone, all the four corner points will appear odd times. To make condition 2 valid again, for each of these four points, we have to either add or remove a rectangle such that each of them will once again appear even times. The key here is that the total number of rectangles added or removed is at least two, since the added or removed rectangles cannot overlap with FR, therefore each added or removed rectangle can contain at most two of the four corner points of FR.

So we end up with at least two extra rectangles (either added or removed), with a total of eight corner points. Four of those points coincide with the four corner points of FR. What about the other four points? For each of these points, if it belongs to a rectangle that is being removed, then before removing, it must appear even times and after removing, it will appear odd times. If it belongs to a rectangle that is being added, no matter it coincides with any point in the perfect rectangle or not, its number of appearance will always be odd (appear once if does not coincide with any point, odd if it does since the original number of appearance before adding is even). In either case (adding or removing rectangle), there is no way to keep the number of appearance of all points even, therefore condition 2 cannot be true for non-perfect rectangles.
