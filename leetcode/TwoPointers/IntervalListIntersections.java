/**
Refer to
https://leetcode.com/problems/interval-list-intersections/
Given two lists of closed intervals, each list of intervals is pairwise disjoint and in sorted order.

Return the intersection of these two interval lists.

(Formally, a closed interval [a, b] (with a <= b) denotes the set of real numbers x with a <= x <= b.  
The intersection of two closed intervals is a set of real numbers that is either empty, or can be represented 
as a closed interval.  For example, the intersection of [1, 3] and [2, 4] is [2, 3].)

Example 1:
Input: A = [[0,2],[5,10],[13,23],[24,25]], B = [[1,5],[8,12],[15,24],[25,26]]
Output: [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]

Note:
0 <= A.length < 1000
0 <= B.length < 1000
0 <= A[i].start, A[i].end, B[i].start, B[i].end < 10^9
*/

// Solution 1: Two Pointers
// Refer to
// https://leetcode.com/problems/interval-list-intersections/discuss/647482/Python-Two-Pointer-Approach-%2B-Thinking-Process-Diagrams
/**
Detail refer to
https://github.com/lampardchelsea/hello-world/blob/master/leetcode/TwoPointers/Document/Interval_List_Intersections_Two_Points_Thinking_Process.docx
E.g
A = [[0,2],[5,10],[13,23],[24,25]]
B = [[1,5],[8,12],[15,24],[25,26]]
output = [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]

Compare interval range end between first element in array A and B
A[0][1] = 2 < B[0][1] = 5	
output[0][0] = Math.max(A[0][0], B[0][0]) -> Math.max(0, 1) -> 1
output[0][1] = Math.min(A[0][1], B[0][1]) -> Math.min(2, 5) -> 2

Since range end of first element in array A smaller than first element in array B,
that essentially means we have exhausted the range of first element in array A and
we should move on to next element in array A for a new range
A[0] -> A[1]
output[1][0] = Math.max(A[1][0], B[0][0]) -> Math.max(5, 1) -> 5
output[1][1] = Math.min(A[1][1], B[0][1]) -> Math.min(10, 5) -> 5

Now range end of second element in array A larger than first element range end in
array B, that essentially means we have exhausted the range of first element in array
B and we should move on to next element in array B for a new range
B[0] -> B[1]
output[2][0] = Math.max(A[1][0], B[1][0]) -> Math.max(5, 8) -> 8
output[2][1] = Math.min(A[1][1], B[1][1]) -> Math.min(10, 12) -> 10

Repeat process until exhausted both A and B
*/

// https://leetcode.com/problems/interval-list-intersections/solution/
/**
Approach 1: Merge Intervals
Intuition
In an interval [a, b], call b the "endpoint".
Among the given intervals, consider the interval A[0] with the smallest endpoint. (Without loss of generality, this interval occurs in array A.)
Then, among the intervals in array B, A[0] can only intersect one such interval in array B. (If two intervals in B intersect A[0], then they 
both share the endpoint of A[0] -- but intervals in B are disjoint, which is a contradiction.)

Algorithm
If A[0] has the smallest endpoint, it can only intersect B[0]. After, we can discard A[0] since it cannot intersect anything else.
Similarly, if B[0] has the smallest endpoint, it can only intersect A[0], and we can discard B[0] after since it cannot intersect anything else.
We use two pointers, i and j, to virtually manage "discarding" A[0] or B[0] repeatedly.

class Solution {
  public int[][] intervalIntersection(int[][] A, int[][] B) {
    List<int[]> ans = new ArrayList();
    int i = 0, j = 0;

    while (i < A.length && j < B.length) {
      // Let's check if A[i] intersects B[j].
      // lo - the startpoint of the intersection
      // hi - the endpoint of the intersection
      int lo = Math.max(A[i][0], B[j][0]);
      int hi = Math.min(A[i][1], B[j][1]);
      if (lo <= hi)
        ans.add(new int[]{lo, hi});

      // Remove the interval with the smallest endpoint
      if (A[i][1] < B[j][1])
        i++;
      else
        j++;
    }

    return ans.toArray(new int[ans.size()][]);
  }
}

Complexity Analysis
Time Complexity: O(M + N), where M, N are the lengths of A and B respectively.
Space Complexity: O(M + N), the maximum size of the answer.
*/
class Solution {
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        List<int[]> result = new ArrayList<int[]>();
        int i = 0;
        int j = 0;
        while(i < A.length && j < B.length) {
            int lo = Math.max(A[i][0], B[j][0]);
            int hi = Math.min(A[i][1], B[j][1]);
            // When lo > hi means no intersection between
            // e.g [1,2] and [3,4] -> lo = 3, hi = 2
            if(lo <= hi) {
                result.add(new int[] {lo, hi});
            }
            if(A[i][1] < B[j][1]) {
                i++;
            } else {
                j++;
            }
        }
        return result.toArray(new int[result.size()][]);
    }
}

















































https://leetcode.com/problems/interval-list-intersections/

You are given two lists of closed intervals, firstList and secondList, where firstList[i] = [starti, endi] and secondList[j] = [startj, endj]. Each list of intervals is pairwise disjoint and in sorted order.

Return the intersection of these two interval lists.

A closed interval [a, b] (with a <= b) denotes the set of real numbers x with a <= x <= b.

The intersection of two closed intervals is a set of real numbers that are either empty or represented as a closed interval. For example, the intersection of [1, 3] and [2, 4] is [2, 3].

Example 1:


```
Input: firstList = [[0,2],[5,10],[13,23],[24,25]], secondList = [[1,5],[8,12],[15,24],[25,26]]
Output: [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]
```

Example 2:
```
Input: firstList = [[1,3],[5,9]], secondList = []
Output: []
```
 
Constraints:
- 0 <= firstList.length, secondList.length <= 1000
- firstList.length + secondList.length >= 1
- 0 <= starti < endi <= 109
- endi < starti+1
- 0 <= startj < endj <= 109
- endj < startj+1
---
Attempt 1: 2023-03-01

Solution 1: Two Pointers (60 min)
```
class Solution { 
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) { 
        List<int[]> tmp = new ArrayList<int[]>(); 
        int i = 0; 
        int j = 0; 
        while(i < firstList.length && j < secondList.length) { 
            // First check "criss-crossing" intersection 
            int[] a = firstList[i]; 
            int[] b = secondList[j]; 
            if(a[0] <= b[1] && b[0] <= a[1]) { 
                tmp.add(new int[]{Math.max(a[0], b[0]), Math.min(a[1], b[1])}); 
            } 
            // Exhausted this range in firstList, point to next range in firstList, 
            // otherwise exhausted this range in secondList, point to next range in secondList 
            if(a[1] <= b[1]) { 
                i++; 
            } else { 
                j++; 
            } 
        } 
        int[][] result = new int[tmp.size()][]; 
        for(int k = 0; k < tmp.size(); k++) { 
            result[k] = tmp.get(k); 
        } 
        return result; 
    } 
}
```

[Python] Two Pointer Approach + Thinking Process Diagrams
Refer to
https://leetcode.com/problems/interval-list-intersections/solutions/647482/python-two-pointer-approach-thinking-process-diagrams/
I love this problem. Even though it is a two pointer problem, making everything concise is quite hard!

Let's break down this problem into two parts:

First part - Finding overlapping range
Let's see possible types of overlaps

What's the condition for two ranges to have an overlapping range? Check out the figures below. The way I think is: if we can have a criss-crossing lock condition satisfied, we have essentially found an overlapping range.

After we have made sure that there is an overlapping range, we need to figure out the start and end of the overlapping range. I think of this as trying to squeeze the overlapping range as tight as possible (pushing as far right as possible for start and pushing as far left as possible for end)

Second part - Incrementing pointers
The idea behind is to increment the pointer based on the end values of two ranges. Let's say the current range in A has end value smaller than to equal to end value of the current range in B, that essentially means that you have exhausted that range in A and you should move on to the next range. Let's try to visually think about this. When you are going through the images, keep track of end values of the ranges and how the little pointer triangles progress.




Python
```
1. class Solution: 
2.     def intervalIntersection(self, A: List[List[int]], B: List[List[int]]) -> List[List[int]]: 
3.         i = 0 
4.         j = 0 
5.          
6.         result = [] 
7.         while i < len(A) and j < len(B): 
8.             a_start, a_end = A[i] 
9.             b_start, b_end = B[j] 
10.             if a_start <= b_end and b_start <= a_end:                       # Criss-cross lock 
11.                 result.append([max(a_start, b_start), min(a_end, b_end)])   # Squeezing 
12.                  
13.             if a_end <= b_end:         # Exhausted this range in A 
14.                 i += 1               # Point to next range in A 
15.             else:                      # Exhausted this range in B 
16.                 j += 1               # Point to next range in B 
17.         return result
```
Java
https://leetcode.com/problems/interval-list-intersections/solutions/647482/python-two-pointer-approach-thinking-process-diagrams/comments/764904
```
public int[][] intervalIntersection(int[][] A, int[][] B) { 
        int i = 0, j = 0; 
        int m = A.length, n = B.length; 
        List<int[]> resultList = new ArrayList<>(); 
        while (i < m && j < n) { 
           int[] aVal = A[i], bVal = B[j]; 
            if (aVal[0] <= bVal[1] && bVal[0] <= aVal[1]) { 
                // First check "criss cross" intersection 
                resultList.add(new int[]{Math.max(aVal[0], bVal[0]), Math.min(aVal[1], bVal[1])}); 
            } 
            if (aVal[1] <= bVal[1]) ++i; //Next A 
            else ++j; // Next B 
        } 
        int[][] result = new int[resultList.size()][]; 
        for (int idx = 0; idx < resultList.size(); idx++) { 
            result[idx] = resultList.get(idx); 
        } 
        return result; 
    }

Complexity - Time - O(min(m,n)) - Space O(k) where k is the number of intersections
```


