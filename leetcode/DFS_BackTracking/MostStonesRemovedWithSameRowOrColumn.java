/**
 Refer to
 https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/
 On a 2D plane, we place stones at some integer coordinate points.  Each coordinate point may 
 have at most one stone.
Now, a move consists of removing a stone that shares a column or row with another stone on the grid.
What is the largest possible number of moves we can make?

Example 1:
Input: stones = [[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]
Output: 5

Example 2:
Input: stones = [[0,0],[0,2],[1,1],[2,0],[2,2]]
Output: 3

Example 3:
Input: stones = [[0,0]]
Output: 0

Note:
1 <= stones.length <= 1000
0 <= stones[i][j] < 10000
*/
// Explain:
// Refer to
// https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/discuss/200034/Someone-please-help-I-just-don't-get-this/203867
/**
  The input "stones" is a list of the form [[1,2],[3,5],[2,0],[1,5]...]. Each element of this list, 
  for instance '[1,2]' is the position of a stone, so the x-coordinate is '1' and the y-coordinate 
  is 2. On each "move" you are allowed to remove an element from this list if and only if that 
  element has a common x or y coordinate with another element. For instance, the first stone sitting 
  at [1,2] may be removed since there is another stone at [1,5] sharing a common x coordinate. 
  You can remove either one from the list, but for the optimal strategy you have to worry about 
  the order of removing stones. Ideally, you want to remove as many stones as possible.
*/
// Solution 1: DFS
// Refer to
// https://blog.csdn.net/sc19951007/article/details/85362404
/**
 这道题和LC200–最大连通区域题有些类似，我们可以把在同一行/列的石块看成connected，每个connected区域保留一个石块，
 这样的话最后用total stone - number of connected area即为最后结果。那么怎么计算 number of connected area呢？
 我写了两种方法：DFS和UnionFind供参考
*/
// https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/discuss/197668/Count-the-Number-of-Islands-O(N)
/**
 Problem:
we can remove a stone if and only if,
there is another stone in the same column OR row.
We try to remove as many as stones as possible.

Find more details in chinese on the jianshu

One sentence to solve:
Connected stones can be reduced to 1 stone,
the maximum stones can be removed = stones number - islands number.
so just count the number of "islands".

1. Connected stones
Two stones are connected if they are in the same row or same col.
Connected stones will build a connected graph.
It's obvious that in one connected graph,
we can't remove all stones.

We have to have one stone left.
An intuition is that, in the best strategy, we can remove until 1 stone.

I guess you may reach this step when solving the problem.
But the important question is, how?

2. A failed strategy
Try to remove the least degree stone
Like a tree, we try to remove leaves first.
Some new leaf generated.
We continue this process until the root node left.

However, there can be no leaf.
When you try to remove the least in-degree stone,
it won't work on this "8" like graph:
[[1, 1, 0, 0, 0],
[1, 1, 0, 0, 0],
[0, 1, 1, 0, 0],
[0, 0, 1, 1, 1],
[0, 0, 0, 1, 1]]

The stone in the center has least degree = 2.
But if you remove this stone first,
the whole connected stones split into 2 parts,
and you will finish with 2 stones left.

3. A good strategy
In fact, the proof is really straightforward.
You probably apply a DFS, from one stone to next connected stone.
You can remove stones in reversed order.
In this way, all stones can be removed but the stone that you start your DFS.

One more step of explanation:
In the view of DFS, a graph is explored in the structure of a tree.
As we discussed previously,
a tree can be removed in topological order,
from leaves to root.

4. Count the number of islands
We call a connected graph as an island.
One island must have at least one stone left.
The maximum stones can be removed = stones number - islands number

The whole problem is transferred to:
What is the number of islands?

You can show all your skills on a DFS implementation,
and solve this problem as a normal one.

5. Unify index
Struggle between rows and cols?
You may duplicate your codes when you try to the same thing on rows and cols.
In fact, no logical difference between col index and rows index.

An easy trick is that, add 10000 to col index.
So we use 0 ~ 9999 for row index and 10000 ~ 19999 for col.

6. Search on the index, not the points
When we search on points,
we alternately change our view on a row and on a col.

We think:
a row index, connect two stones on this row
a col index, connect two stones on this col.

In another view：
A stone, connect a row index and col.

Have this idea in mind, the solution can be much simpler.
The number of islands of points,
is the same as the number of islands of indexes.

7. Union-Find
I use union find to solve this problem.
As I mentioned, the elements are not the points, but the indexes.

for each point, union two indexes.
return points number - union number
Copy a template of union-find,
write 2 lines above,
you can solve this problem in several minutes.

Complexity
union and find functions have worst case O(N), amortize O(1)
The whole union-find solution with path compression,
has O(N) Time, O(N) Space

If you have any doubts on time complexity,
please refer to wikipedia first.
*/
class Solution {
    public int removeStones(int[][] stones) {
        if(stones == null || stones.length == 0 
           || stones[0] == null || stones[0].length == 0) {
            return 0;
        }
        int numOfIslands = 0;
        Set<int[]> visited = new HashSet<int[]>();
        for(int[] stone : stones) {
            if(!visited.contains(stone)) {
                helper(stone, visited, stones);
                numOfIslands++;
            }
        }
        return stones.length - numOfIslands;
    }
    
    private void helper(int[] stone, Set<int[]> visited, int[][] stones) {
        visited.add(stone);
        for(int[] stone2 : stones) {
            if(!visited.contains(stone2)) {
                if(stone[0] == stone2[0] || stone[1] == stone2[1]) {
                    helper(stone2, visited, stones);
                }
            }
        }
    }
}

// Solution 2: Union-Found
// Refer to
// https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/discuss/197693/Java-Union-Find
class Solution {
    int count = 0;
    public int removeStones(int[][] stones) {
        Map<String, String> parent = new HashMap<>();
        count = stones.length;
        // init Union Find
        for (int[] stone : stones) {
            String s = stone[0] + " " + stone[1];
            parent.put(s, s);
        }
        for (int[] s1 : stones) {
            String ss1 = s1[0] + " " + s1[1];
            for (int[] s2 : stones) {
                if (s1[0] == s2[0] || s1[1] == s2[1]) { // in the same column or row
                    String ss2 = s2[0] + " " + s2[1];
                    union(parent, ss1, ss2);
                }
            }
        }
        return stones.length - count;
    }
    
    public void union(Map<String, String> parent, String s1, String s2) {
        String src = find(parent, s1);
        String dst = find(parent, s2);
        if(!src.equals(dst)) {
            parent.put(src, dst);
            count--;
        }
    }
    
    // find method style 1
    // Runtime: 115 ms, faster than 14.42% of Java online submissions for Most Stones Removed with Same Row or Column.
    // Memory Usage: 42.9 MB, less than 66.39% of Java online submissions for Most Stones Removed with Same Row or Column
    public String find(Map<String, String> parent, String s) {
        if(!parent.get(s).equals(s)) {
            parent.put(s, find(parent, parent.get(s)));
        }
        //return s;
        return parent.get(s);
    }
       // find method style 2
       // without path compression will be
       // Runtime: 175 ms, faster than 7.15% of Java online submissions for Most Stones Removed with Same Row or Column.
       // Memory Usage: 48 MB, less than 21.45% of Java online submissions for Most Stones Removed with Same Row or Column.
       // with path compression will be
       // Runtime: 136 ms, faster than 9.75% of Java online submissions for Most Stones Removed with Same Row or Column.
       // Memory Usage: 42.5 MB, less than 68.82% of Java online submissions for Most Stones Removed with Same Row or Column.
//     public String find(Map<String, String> parent, String s) {
//         if(parent.get(s).equals(s)) {
//             return s;
//         }
//         // return find(parent, parent.get(s)); -> without path compression like s = find(...) will cost more memory and time
//         return s = find(parent, parent.get(s));
//     }
}
