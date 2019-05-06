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

