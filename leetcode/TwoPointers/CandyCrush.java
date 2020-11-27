/**
Refer to
https://www.lintcode.com/problem/candy-crush/description
This question is about implementing a basic elimination algorithm for Candy Crush.

Given a 2D integer array board representing the grid of candy, different positive integers board[i][j] represent different 
types of candies. A value of board[i][j] = 0 represents that the cell at position (i, j) is empty. The given board represents 
the state of the game following the player's move. Now, you need to restore the board to a stable state by crushing candies 
according to the following rules:

If three or more candies of the same type are adjacent vertically or horizontally, "crush" them all at the same time - these positions become empty.
After crushing all candies simultaneously, if an empty space on the board has candies on top of itself, then these candies 
will drop until they hit a candy or bottom at the same time. (No new candies will drop outside the top boundary.)
After the above steps, there may exist more candies that can be crushed. If so, you need to repeat the above steps.
If there does not exist more candies that can be crushed (ie. the board is stable), then return the current board.
You need to perform the above rules until the board becomes stable, then return the current board.

Example
Example 1:
Input:
  [
    [110,5,112,113,114],
    [210,211,5,213,214],
    [310,311,3,313,314],
    [410,411,412,5,414],
    [5,1,512,3,3],
    [610,4,1,613,614],
    [710,1,2,713,714],
    [810,1,2,1,1],
    [1,1,2,2,2],
    [4,1,4,4,1014]
  ]
Output:
  [
    [0,0,0,0,0],
    [0,0,0,0,0],
    [0,0,0,0,0],
    [110,0,0,0,114],
    [210,0,0,0,214],
    [310,0,0,113,314],
    [410,0,0,213,414],
    [610,211,112,313,614],
    [710,311,412,613,714],
    [810,411,512,713,1014]
  ]
  
Explanation
Load the snapshot on document: https://github.com/lampardchelsea/hello-world/blob/master/leetcode/TwoPointers/Document/Candy_Crush_Explain.jpg

Example 2:
Input: [[1,3,5,5,2],[3,4,3,3,1],[3,2,4,5,2],[2,4,4,5,5],[1,4,4,1,1]]
Output: [[1,3,0,0,0],[3,4,0,5,2],[3,2,0,3,1],[2,4,0,5,2],[1,4,3,1,1]]

Notice
The length of board will be in the range [3, 50].
The length of board[i] will be in the range [3, 50].
Each board[i][j] will initially start as an integer in the range [1, 2000].
*/

// Solution 1: Two Pointers
// Refer to
// http://us.jiuzhang.com/problem/candy-crush/#tag-lang-java
/**
这道题目可能比较难写, 但是思路是很简单的: 只要局面不稳定, 那就消除, 下落.
代码的大题框架就是: 搜寻可以消除的块, 如果没有则局面稳定, 反之消除, 下落, 继续搜寻.
不过需要注意几个细节:
L型或者是十字型的可消除块要求的是行/列都需要至少3个相同的糖果
下落之后上方的空位补零
你不能只找到一个可以消除的块就立即消除然后下落, 而是找到当前局面中所有可以消除的块, 一次性消除, 然后下落, 这样才到达下一个局面, 再次判断.
我们可以这样实现: 因为给定的类型都是正数, 所以我们可以在一次搜寻中, 把所有可消除的块都转换成负数.
这样, 遍历整个地图完成一次搜寻后, 负数的块代表的就是可消除的, 置为0然后处理下落即可.
public class Solution {
    public int[][] candyCrush(int[][] board) {
        int N = board.length, M = board[0].length;
        boolean found = true;
        while (found) {
            found = false;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    int val = Math.abs(board[i][j]);
                    if (val == 0) {
                        continue;
                    }
                    if (j < M - 2 && Math.abs(board[i][j + 1]) == val && Math.abs(board[i][j + 2]) == val) {
                        found = true;
                        int ind = j;
                        while (ind < M && Math.abs(board[i][ind]) == val) {
                            board[i][ind++] = -val;
                        }
                    }
                    if (i < N - 2 && Math.abs(board[i + 1][j]) == val && Math.abs(board[i + 2][j]) == val) {
                        found = true;
                        int ind = i;
                        while (ind < N && Math.abs(board[ind][j]) == val) {
                            board[ind++][j] = -val;
                        }
                    }
                }
            }
            if (found) {
                for (int j = 0; j < M; j++) {
                    int storeInd = N - 1;
                    for (int i = N - 1; i >= 0; i--) {
                        if (board[i][j] > 0) {
                            board[storeInd--][j] = board[i][j];
                        }
                    }
                    for (int k = storeInd; k >= 0; k--) {
                        board[k][j] = 0;
                    }
                }
            }
        }
        return board;
    }
}
*/

// Refer to
// https://www.cnblogs.com/grandyang/p/7858414.html
/**
这道题就是糖果消消乐，博主刚开始做的时候，没有看清楚题意，以为就像游戏中的那样，每次只能点击一个地方，然后消除后糖果落下，
这样会导致一个问题，就是原本其他可以消除的地方在糖果落下后可能就没有了，所以博主在想点击的顺序肯定会影响最终的 stable 的状态，
可是题目怎么没有要求返回所剩糖果最少的状态？后来发现，其实这道题一次消除 table 中所有可消除的糖果，然后才下落，形成新的 table，
这样消除后得到的结果就是统一的了，这样也大大的降低了难度。下面就来看如何找到要消除的糖果，可能有人会觉得像之前的岛屿的题目一样
找连通区域，可是这道题的有限制条件，只有横向或竖向相同的糖果数达到三个才能消除，并不是所有的连通区域都能消除，所以找连通区域
不是一个好办法。最好的办法其实是每个糖果单独检查其是否能被消除，然后把所有能被删除的糖果都标记出来统一删除，然后在下落糖果，
然后再次查找，直到无法找出能够消除的糖果时达到稳定状态。好，用一个数组来保存可以被消除的糖果的位置坐标，判断某个位置上的糖果
能否被消除的方法就是检查其横向和纵向的最大相同糖果的个数，只要有一个方向达到三个了，当前糖果就可以被消除。所以对当前糖果的
上下左右四个方向进行查看，用四个变量 x0, x1, y0, y1，其中 x0 表示上方相同的糖果的最大位置，x1 表示下方相同糖果的最大位置，
y0 表示左边相同糖果的最大位置，y1 表示右边相同糖果的最大位置，均初始化为当前糖果的位置，然后使用 while 循环向每个方向遍历，
注意并不需要遍历到尽头，而是只要遍历三个糖果就行了，因为一旦查到了三个相同的，就说明当前的糖果已经可以消除了，没必要再往下查了。
查的过程还要注意处理越界情况，好，得到了上下左右的最大的位置，分别让相同方向的做差，如果水平和竖直方向任意一个大于3了，就说明
可以消除，将坐标加入数组 del 中。注意这里一定要大于3，是因为当发现不相等退出 while 循环时，坐标值已经改变了，所以已经多加了
或者减了一个，所以差值要大于3。遍历完成后，如果数组 del 为空，说明已经 stable 了，直接 break 掉，否则将要消除的糖果位置都
标记为0，然后进行下落处理。下落处理实际上是把数组中的0都移动到开头，那么就从数组的末尾开始遍历，用一个变量t先指向末尾，然后
然后当遇到非0的数，就将其和t位置上的数置换，然后t自减1，这样t一路减下来都是非0的数，而0都被置换到数组开头了，参见代码如下：
class Solution {
public:
    vector<vector<int>> candyCrush(vector<vector<int>>& board) {
        int m = board.size(), n = board[0].size();
        while (true) {
            vector<pair<int, int>> del;
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (board[i][j] == 0) continue;
                    int x0 = i, x1 = i, y0 = j, y1 = j;
                    while (x0 >= 0 && x0 > i - 3 && board[x0][j] == board[i][j]) --x0;
                    while (x1 < m && x1 < i + 3 && board[x1][j] == board[i][j]) ++x1;
                    while (y0 >= 0 && y0 > j - 3 && board[i][y0] == board[i][j]) --y0;
                    while (y1 < n && y1 < j + 3 && board[i][y1] == board[i][j]) ++y1;
                    if (x1 - x0 > 3 || y1 - y0 > 3) del.push_back({i, j});
                }
            }
            if (del.empty()) break;
            for (auto a : del) board[a.first][a.second] = 0;
            for (int j = 0; j < n; ++j) {
                int t = m - 1;
                for (int i = m - 1; i >= 0; --i) {
                    if (board[i][j]) swap(board[t--][j], board[i][j]);   
                }
            }
        }
        return board;
    }
};
*/
public class Solution {
    /**
     * @param board: a 2D integer array
     * @return: the current board
     */
    public int[][] candyCrush(int[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        boolean found = true;
        while(found) {
            found = false;
            for(int i = 0; i < rows; i++) {
                for(int j = 0; j < cols; j++) {
                    int val = Math.abs(board[i][j]);
                    if(val == 0) {
                        continue;
                    }
                    if(j < cols - 2 && Math.abs(board[i][j + 1]) == val && Math.abs(board[i][j + 2]) == val) {
                        found = true;
                        int idx = j;
                        while(idx < cols && Math.abs(board[i][idx]) == val) {
                            board[i][idx++] = -val;
                        }
                    }
                    if(i < rows - 2 && Math.abs(board[i + 1][j]) == val && Math.abs(board[i + 2][j]) == val) {
                        found = true;
                        int idx = i;
                        while(idx < rows && Math.abs(board[idx][j]) == val) {
                            board[idx++][j] = -val;
                        }
                    }
                }
            }
            if(found) {
                for(int j = 0; j < cols; j++) {
                    int storeIdx = rows - 1;
                    for(int i = rows - 1; i >= 0; i--) {
                        if(board[i][j] > 0) {
                           board[storeIdx--][j] = board[i][j];
                        }
                    }
                    for(int k = storeIdx; k >= 0; k--) {
                        board[k][j] = 0;
                    }
                }
            }
        }
        return board;
    }
}

