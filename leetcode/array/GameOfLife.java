/**
 * Refer to
 * https://leetcode.com/problems/game-of-life/#/description
 * According to the Wikipedia's article: "The Game of Life, also known simply as Life, 
 * is a cellular automaton devised by the British mathematician John Horton Conway in 1970."
 * 
 * Given a board with m by n cells, each cell has an initial state live (1) or dead (0). 
 * Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using 
 * the following four rules (taken from the above Wikipedia article):

	Any live cell with fewer than two live neighbors dies, as if caused by under-population.
	Any live cell with two or three live neighbors lives on to the next generation.
	Any live cell with more than three live neighbors dies, as if by over-population..
	Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.

 * Write a function to compute the next state (after one update) of the board given its current state.
 * 
 * Follow up: 
 * Could you solve it in-place? Remember that the board needs to be updated at the same time: 
 * You cannot update some cells first and then use their updated values to update other cells.
 * In this question, we represent the board using a 2D array. In principle, the board is infinite, 
 * which would cause problems when the active area encroaches the border of the array. 
 * How would you address these problems?
 * 
 * Solution
 * Refer to
 * https://discuss.leetcode.com/topic/29054/easiest-java-solution-with-explanation
 * To solve it in place, we use 2 bits to store 2 states:

	[2nd bit, 1st bit] = [next state, current state]
	
	- 00  dead (next) <- dead (current)
	- 01  dead (next) <- live (current)  
	- 10  live (next) <- dead (current)  
	- 11  live (next) <- live (current) 
	In the beginning, every cell is either 00 or 01.
	Notice that 1st state is independent of 2nd state.
	Imagine all cells are instantly changing from the 1st to the 2nd state, at the same time.
	Let's count # of neighbors from 1st state and set 2nd state bit.
	Since every 2nd state is by default dead, no need to consider transition 01 -> 00.
	In the end, delete every cell's 1st state by doing >> 1.
	For each cell's 1st bit, check the 8 pixels around itself, and set the cell's 2nd bit.
	
	Transition 01 -> 11: when board == 1 and lives >= 2 && lives <= 3.
	Transition 00 -> 10: when board == 0 and lives == 3.
	
	To get the current state, simply do
	board[i][j] & 1
	
	To get the next state, simply do
	board[i][j] >> 1
 * 
 * Refer to
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/array/Document/%5BLeetcode%5D%20Game%20of%20Life%20%E7%94%9F%E5%91%BD%E6%B8%B8%E6%88%8F%20-%20Ethan%20Li%20%E7%9A%84%E6%8A%80%E6%9C%AF%E4%B8%93%E6%A0%8F%20-%20SegmentFault.pdf
 */
public class GameOfLife {
    public void gameOfLife(int[][] board) {
        int rows = board.length;
        int columns = board[0].length;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                int lives = liveNeighbours(board, i, j, rows, columns);
                // In the beginning, every 2nd bit is 0;
                // So we only need to care about when will the 2nd bit become 1.
                if(board[i][j] == 1 && (lives == 2 || lives == 3)) {
                    board[i][j] = 3; // Make the 2nd bit 1: 01 ---> 11
                }
                if(board[i][j] == 0 && lives == 3) {
                    board[i][j] = 2; // Make the 2nd bit 1: 00 ---> 10
                }
            }
        }
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                board[i][j] >>= 1; // Get the 2nd state.
            }
        }
    }
    
    public int liveNeighbours(int[][] board, int i, int j, int rows, int columns) {
        int lives = 0;
        for(int x = Math.max(i - 1, 0); x <= Math.min(i + 1, rows - 1); x++) {
            for(int y = Math.max(j - 1, 0); y <= Math.min(j + 1, columns - 1); y++) {
		// No need braces
		// Refer to
		// https://en.wikipedia.org/wiki/Order_of_operations
                lives += board[x][y] & 1;
            }
        }
        lives -= board[i][j] & 1;
        return lives;
    }
    
    public static void main(String[] args) {
    	
    }
}






































































































https://leetcode.com/problems/game-of-life/

According to Wikipedia's article: "The Game of Life, also known simply as Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970."

The board is made up of an m x n grid of cells, where each cell has an initial state: live (represented by a 1) or dead (represented by a 0). Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the following four rules (taken from the above Wikipedia article):
1. Any live cell with fewer than two live neighbors dies as if caused by under-population.
2. Any live cell with two or three live neighbors lives on to the next generation.
3. Any live cell with more than three live neighbors dies, as if by over-population.
4. Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.

The next state is created by applying the above rules simultaneously to every cell in the current state, where births and deaths occur simultaneously. Given the current state of the m x n grid board, return the next state.

Example 1:


```
Input: board = [[0,1,0],[0,0,1],[1,1,1],[0,0,0]]
Output: [[0,0,0],[1,0,1],[0,1,1],[0,1,0]]
```

Example 2:


```
Input: board = [[1,1],[1,0]]
Output: [[1,1],[1,1]]
```

Constraints:
- m == board.length
- n == board[i].length
- 1 <= m, n <= 25
- board[i][j] is 0 or 1.
 
Follow up:
- Could you solve it in-place? Remember that the board needs to be updated simultaneously: You cannot update some cells first and then use their updated values to update other cells.
- In this question, we represent the board using a 2D array. In principle, the board is infinite, which would cause problems when the active area encroaches upon the border of the array (i.e., live cells reach the border). How would you address these problems?
---
Attempt 1: 2023-10-15

Solution 1: Native fill new status on another board (10 min)
```
class Solution {
    public void gameOfLife(int[][] board) {
        int m = board.length;
        int n = board[0].length;
        int[][] tmp = new int[m][n];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                update(tmp, board, i, j, m, n);
            }
        }
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                board[i][j] = tmp[i][j];
            }
        }
    }
    int[] dx = new int[] {0,0,1,-1,1,1,-1,-1};
    int[] dy = new int[] {1,-1,0,0,1,-1,1,-1};
    private void update(int[][] tmp, int[][] board, int x, int y, int m, int n) {
        int one_count = 0;
        for(int k = 0; k < 8; k++) {
            int new_x = x + dx[k];
            int new_y = y + dy[k];
            if(new_x >= 0 && new_x < m && new_y >= 0 && new_y < n) {
                if(board[new_x][new_y] == 1) {
                    one_count++;
                }
            }
        }
        if(board[x][y] == 1) {
            if(one_count < 2 || one_count > 3) {
                tmp[x][y] = 0;
            } else {
                tmp[x][y] = 1;
            }
        } else {
            if(one_count == 3) {
                tmp[x][y] = 1;
            }
        }
    }
}

Time Complexity: O(N^3)
Space Complexity: O(N^2)
```

---
Solution 2: No extra space needed, in-place update in board (10 min)

Style 1: 用一个常用的方法，我们可以将需要改变的数字先转成另一个数字，最后再将其还原
```
class Solution {
    public void gameOfLife(int[][] board) {
        int m = board.length;
        int n = board[0].length;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                update(board, i, j, m, n);
            }
        }
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                // 遇见-1表示从0 -> 1，遇见-2表示从1 -> 0
                if(board[i][j] == -1) {
                    board[i][j] = 1;
                }
                if(board[i][j] == -2) {
                    board[i][j] = 0;
                }
            }
        }
    }

    int[] dx = new int[] {0,0,1,-1,1,1,-1,-1};
    int[] dy = new int[] {1,-1,0,0,1,-1,1,-1};
    private void update(int[][] board, int x, int y, int m, int n) {
        int one_count = 0;
        for(int k = 0; k < 8; k++) {
            int new_x = x + dx[k];
            int new_y = y + dy[k];
            if(new_x >= 0 && new_x < m && new_y >= 0 && new_y < n) {
                // 这样做的话，在记录周围八个位置有多少 1 的时候，除了 1 以外，还要记录 -2 的个数，因为 -2 代表以前这个位置是1
                if(board[new_x][new_y] == 1 || board[new_x][new_y] == -2) {
                    one_count++;
                }
            }
        }
        // 用一个常用的方法，我们可以将需要改变的数字先转成另一个数字，最后再将其还原。
        // 具体的讲，如果某个数字需要由 0 变成 1，我们先把它变成 -1。
        // 如果某个数字需要由 1 变成 0，我们先把它变成 -2。
        // 这样做的话，在记录周围八个位置有多少 1 的时候，除了 1 以外，还要记录 -2 的个数。
        // 最后再将所有的 -1 变成 1，-2 变成 0 即可。
        if(board[x][y] == 1) {
            if(one_count < 2 || one_count > 3) {
                // 1 -> 0, first try with 1 -> -2, as -2 means previously it is 1
                board[x][y] = -2;
            }
        } else {
            if(one_count == 3) {
                // 0 -> 1, first try with 0 -> -1
                board[x][y] = -1;
            }
        }
    }
}

Time Complexity: O(N^3)
Space Complexity: O(1)
```

Style 2:  Add one more bit to record status
```
class Solution {
    public void gameOfLife(int[][] board) {
        int m = board.length;
        int n = board[0].length;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                update(board, i, j, m, n);
            }
        }
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                // Get the 2nd state.
                board[i][j] >>= 1;
            }
        }
    }

    int[] dx = new int[] {0,0,1,-1,1,1,-1,-1};
    int[] dy = new int[] {1,-1,0,0,1,-1,1,-1};
    private void update(int[][] board, int x, int y, int m, int n) {
        int one_count = 0;
        for(int k = 0; k < 8; k++) {
            int new_x = x + dx[k];
            int new_y = y + dy[k];
            if(new_x >= 0 && new_x < m && new_y >= 0 && new_y < n) {
                // 这样做的话，在记录周围八个位置有多少 1(01) 的时候，除了 1(01) 以外，还要记录 3(11) 的个数，因为 3(11) 代表以前这个位置是1
                // Get the current state
                if((board[new_x][new_y] & 1) == 1) {
                    one_count++;
                }
            }
        }
        // 用一个常用的方法，我们可以将需要改变的数字先转成另一个数字，最后再将其还原
        // In the beginning, every 2nd bit is 0;
        // So we only need to care about when will the 2nd bit become 1.
        if(board[x][y] == 1) {
            if(one_count >= 2 && one_count <= 3) {
                // Make the 2nd bit 1: 01 ---> 11
                board[x][y] = 3;
            }
        } else {
            if(one_count == 3) {
                // Make the 2nd bit 1: 00 ---> 10
                board[x][y] = 2;
            }
        }
    }
}

Time Complexity: O(N^3)
Space Complexity: O(1)
```

Refer to
https://leetcode.com/problems/game-of-life/solutions/73223/easiest-java-solution-with-explanation/
To solve it in place, we use 2 bits to store 2 states:
```
[2nd bit, 1st bit] = [next state, current state]
- 00  dead (next) <- dead (current)
- 01  dead (next) <- live (current)  
- 10  live (next) <- dead (current)  
- 11  live (next) <- live (current) 
```
- In the beginning, every cell is either 00or 01.
- Notice that 1ststate is independent of 2ndstate.
- Imagine all cells are instantly changing from the 1stto the 2ndstate, at the same time.
- Let's count # of neighbors from 1ststate and set 2ndstate bit.
- Since every 2ndstate is by default dead, no need to consider transition 01 -> 00.
- In the end, delete every cell's 1ststate by doing >> 1.

For each cell's 1stbit, check the 8 pixels around itself, and set the cell's 2ndbit.
- Transition 01 -> 11: when board == 1and lives >= 2 && lives <= 3.
- Transition 00 -> 10: when board == 0and lives == 3.

To get the current state, simply do
```
board[i][j] & 1
```

To get the next state, simply do
```
board[i][j] >> 1
```

Java
```
public void gameOfLife(int[][] board) {
    if (board == null || board.length == 0) return;
    int m = board.length, n = board[0].length;

    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            int lives = liveNeighbors(board, m, n, i, j);

            // In the beginning, every 2nd bit is 0;
            // So we only need to care about when will the 2nd bit become 1.
            if (board[i][j] == 1 && lives >= 2 && lives <= 3) {  
                board[i][j] = 3; // Make the 2nd bit 1: 01 ---> 11
            }
            if (board[i][j] == 0 && lives == 3) {
                board[i][j] = 2; // Make the 2nd bit 1: 00 ---> 10
            }
        }
    }

    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            board[i][j] >>= 1;  // Get the 2nd state.
        }
    }
}

public int liveNeighbors(int[][] board, int m, int n, int i, int j) {
    int lives = 0;
    for (int x = Math.max(i - 1, 0); x <= Math.min(i + 1, m - 1); x++) {
        for (int y = Math.max(j - 1, 0); y <= Math.min(j + 1, n - 1); y++) {
            lives += board[x][y] & 1;
        }
    }
    lives -= board[i][j] & 1;
    return lives;
}
```

---

Refer to
https://leetcode.wang/leetcode-289-Game-of-Life.html

解法一

最直接的想法，就是再用一个等大的矩阵，然后一个一个判断原矩阵每个元素的下一状态，然后存到新矩阵，最后用新矩阵覆盖原矩阵即可。

上边的想法虽然可行，但需要额外空间，我们考虑不需要额外空间的想法。

用一个常用的方法，我们可以将需要改变的数字先转成另一个数字，最后再将其还原。

具体的讲，如果某个数字需要由 0 变成 1，我们先把它变成 -1。

如果某个数字需要由 1 变成 0，我们先把它变成 -2。

这样做的话，在记录周围八个位置有多少 1 的时候，除了 1 以外，还要记录 -2 的个数。

最后再将所有的 -1 变成 1，-2 变成 0 即可。

看下代码。

```
public void gameOfLife(int[][] board) {
    int rows = board.length;
    if (rows == 0) {
        return;
    }
    int cols = board[0].length;
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            //周围八个位置有多少个 1
            int count = getOnes(r, c, rows, cols, board);

            //当前是 0,  周围有 3 个 1, 说明 0 需要变成 1, 记成 -1
            if (board[r][c] == 0) {
                if (count == 3) {
                    board[r][c] = -1;
                }
            }
            //当前是 1
            if (board[r][c] == 1) {
                //当前 1 需要变成 0, 记成 -2
                if (count < 2 || count > 3) {
                    board[r][c] = -2;
                }
            }

        }
    }

    //将所有数字还原
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (board[r][c] == -1) {
                board[r][c] = 1;
            }
            if (board[r][c] == -2) {
                board[r][c] = 0;
            }
        }
    }

}
//需要统计周围八个位置 1 和 -2 的个数
private int getOnes(int r, int c, int rows, int cols, int[][] board) {
    int count = 0;
    // 上
    if (r - 1 >= 0 && (board[r - 1][c] == 1 || board[r - 1][c] == -2)) {
        count++;
    }
    // 下
    if (r + 1 < rows && (board[r + 1][c] == 1 || board[r + 1][c] == -2)) {
        count++;
    }
    // 左
    if (c - 1 >= 0 && (board[r][c - 1] == 1 || board[r][c - 1] == -2)) {
        count++;
    }
    // 右
    if (c + 1 < cols && (board[r][c + 1] == 1 || board[r][c + 1] == -2)) {
        count++;
    }
    // 左上
    if (c - 1 >= 0 && r - 1 >= 0 && (board[r - 1][c - 1] == 1 || board[r - 1][c - 1] == -2)) {
        count++;
    }
    // 左下
    if (c - 1 >= 0 && r + 1 < rows && (board[r + 1][c - 1] == 1 || board[r + 1][c - 1] == -2)) {
        count++;
    }
    // 右上
    if (c + 1 < cols && r - 1 >= 0 && (board[r - 1][c + 1] == 1 || board[r - 1][c + 1] == -2)) {
        count++;
    }
    // 右下
    if (c + 1 < cols && r + 1 < rows && (board[r + 1][c + 1] == 1 || board[r + 1][c + 1] == -2)) {
        count++;
    }
    return count;
}
```
上边就是我直接想到的了，下边分享一下别人的技巧，使得上边的代码简洁些，但时间复杂度不会变化。


一些优化

主要是两方面，一方面是考虑在记录 1 变成 0 和 0 变成 1 时候要变成的数字，另一方面就是统计周围八个位置 1 的个数时候的写法。

分享 @StefanPochmann-space-O(mn)-time) 的做法。

想法很简单，因为之前记录细胞生命是否活着的时候用的是 0 和 1，相当于只用了 1 个比特位来记录。把它们扩展一位，看成 00 和 01。

我们可以用新扩展的第二位来表示下次的状态，因为开始的时候倒数第二位默认是 0，所以在计算过程中我们只关心下一状态是 1 的时候，将自己本身的数（0 或者 1 ）通过和 2 进行异或即可。如果下一次状态是 0 就不需要管了。

这样做的好处就是在还原的时候，我们可以将其右移一位即可。

通过判断当前位置邻居中 1 的个数，然后通过下边的方式来更新
```
//count 代表当前位置邻居中 1 的个数
//count == 3 的时候下一状态是 1, 或者 count == 2, 并且当前是 1 的时候下一状态是 1
if(count == 3 || (board[r][c] == 1) && count == 2){
    board[r][c] |= 2; //2 的二进制是 10，相当于将第二位 置为 1
}


//和下边的是等价的
if(count == 3 || count + board[r][c] == 3){
    board[r][c] |= 2; //2 的二进制是 10，相当于将第二位 置为 1
}
```
还有就是在统计周围八个位置 1 的个数时候，可以通过下边的方式，确定开始遍历的行和列，然后开始遍历。
```
private int getOnes(int r, int c, int rows, int cols, int[][] board) {
    int count = 0;
    for (int i = Math.max(r - 1, 0); i <= Math.min(r + 1, rows - 1); i++) {
        for (int j = Math.max(c - 1, 0); j <= Math.min(c + 1, cols - 1); j++) {
            count += board[i][j] & 1;
        }
    }
    //如果原来是 1,需要减去
    count -= board[i][j] & 1;
    return count;
}
```
然后把代码综合起来。
```
public void gameOfLife(int[][] board) {
    int rows = board.length;
    if (rows == 0) {
        return;
    }
    int cols = board[0].length;
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            //周围八个位置有多少个 1
            int count = getOnes(r, c, rows, cols, board);

            //count == 3 的时候下一状态是 1, 或者 count == 2, 并且当前是 1 的时候下一状态是 1
            if(count == 3 || (board[r][c] == 1) && count == 2){
                board[r][c] |= 2; //2 的二进制是 10，相当于将第二位 置为 1
            }

        }
    }

    //将所有数字还原
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) { 
            //右移一位还原
            board[r][c] >>= 1;  
        }
    }

}
//需要统计周围八个位置 1 的个数
private int getOnes(int r, int c, int rows, int cols, int[][] board) {
    int count = 0;
    for (int i = Math.max(r - 1, 0); i <= Math.min(r + 1, rows - 1); i++) {
        for (int j = Math.max(c - 1, 0); j <= Math.min(c + 1, cols - 1); j++) {
            count += board[i][j] & 1;
        }
    }
    //如果原来是 1, 需要减去 1
    count -= board[r][c] & 1;
    return count;
}
```
当然如果对二进制操作不熟，也可以使用 这里 的代码。

把上边代码的这一部分。
```
//count == 3 的时候下一状态是 1, 或者 count == 2, 并且当前是 1 的时候下一状态是 1
if(count == 3 || (board[r][c] == 1) && count == 2){
    board[r][c] |= 2; //2 的二进制是 10，相当于将第二位 置为 1
}
```
按照文章开头的算法，更具体的分类。
```
if (board[r][c] == 1 && (count == 2 || count == 3) {  
    board[r][c] = 3; // Make the 2nd bit 1: 01 ---> 11
}
if (board[r][c] == 0 && count == 3) {
    board[r][c] = 2; // Make the 2nd bit 1: 00 ---> 10
}
```
还有 这里-space-O(mn)-time-Java-Solution) 的一种想法。

如果是 0 变成 1，将赋值为 3。如果是 1 变成 0 就赋值成 2 。

这样做的好处就是，在还原的时候通过对 2 求余即可。
```
 board[i][j] %=2;
```
最后还有一种求周围八个位置 1的个数的思路。

参考 这里-space-O(mn)-time)。我们可以先初始化一个数值对，然后通过和当前位置相加来得到相应的值。主要修改了 getOnes 函数。
```
public void gameOfLife(int[][] board) {
    int rows = board.length;
    if (rows == 0) {
        return;
    }
    int cols = board[0].length;
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            //周围八个位置有多少个 1
            int count = getOnes(r, c, rows, cols, board);

            //当前是 0,  周围有 3 个 1, 说明 0 需要变成 1, 记成 3
            if (board[r][c] == 0) {
                if (count == 3) {
                    board[r][c] = 3;
                }
            }
            //当前是 1
            if (board[r][c] == 1) {
                //当前 1 需要变成 0, 记成 2
                if (count < 2 || count > 3) {
                    board[r][c] = 2;
                }
            }

        }
    }

    //将所有数字还原
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) { 
            board[r][c] %= 2;  
        }
    }

}

//作为类的静态成员变量，仅初始化一次
static int  d[][] = {{1,-1},{1,0},{1,1},{0,-1},{0,1},{-1,-1},{-1,0},{-1,1}};
//需要统计周围八个位置 1 和 2 的个数
private int getOnes(int r, int c, int rows, int cols, int[][] board) {
    int count = 0; 
    for(int k = 0; k < 8; k++){
        int x = d[k][0] + r;
        int y = d[k][1] + c;
        if(x < 0 || x >= rows || y < 0 || y >= cols) {
            continue;
        }
        if(board[x][y] == 1 || board[x][y] == 2) {
            count++;
        }
    }
    return count;
}
```

扩展

题目中 Follow up 第二点指出，如果给定的 board 是无限的，我们该怎么处理呢？这是一个开放性的问题，讨论的点会有很多，每个人的想法可能都不一样，下边结合 官方 的讲解说一下。

首先在程序中，无限 board 肯定是不存在的，它只不过是一个很大很大的矩阵，大到无法直接将矩阵读到内存中。

第一个能想到的解决方案就是我们不需要直接将整个矩阵读到内存中，而是每次读出矩阵的三行，每次处理中间那行，然后把结果写入到文件。

第二个的话，如果是一个很大很大的矩阵，很有可能矩阵是一个稀疏矩阵，而我们只关心每个位置的八个邻居中 1 的个数，所以我们可以在内存中仅仅保存 1 的坐标。

如果题目给定的我们就是所有 1 的坐标，那么可以有下边的算法。

用一个 HashMap 去统计每个位置它的邻居的 1 的个数。只需要遍历所有 1 的坐标，然后将它八个邻居相应的 HashMap 的值进行加 1。

参考 ruben3 的 java 代码。
```
//live 保存了所有是 1 的坐标, Coord 是坐标类
private Set<Coord> gameOfLife(Set<Coord> live) {
    Map<Coord,Integer> neighbours = new HashMap<>();
    for (Coord cell : live) {
        for (int i = cell.i-1; i<cell.i+2; i++) {
            for (int j = cell.j-1; j<cell.j+2; j++) {
                if (i==cell.i && j==cell.j) continue;
                Coord c = new Coord(i,j);
                //将它的邻居进行加 1
                if (neighbours.containsKey(c)) {
                    neighbours.put(c, neighbours.get(c) + 1);
                } else {
                    neighbours.put(c, 1);
                }
            }
        }
    }
    Set<Coord> newLive = new HashSet<>();//下一个状态的所有 1 的坐标
    for (Map.Entry<Coord,Integer> cell : neighbours.entrySet())  {
        //当前位置周围有 3 个活细胞，或者有两个活细胞, 并且当前位置是一个活细胞
        if (cell.getValue() == 3 || cell.getValue() == 2 && live.contains(cell.getKey())) {
            newLive.add(cell.getKey());
        }
    }
    return newLive;
}

//相当于一个坐标类
private static class Coord {
    int i;
    int j;
    private Coord(int i, int j) {
        this.i = i;
        this.j = j;
    }
    public boolean equals(Object o) {
        return o instanceof Coord && ((Coord)o).i == i && ((Coord)o).j == j;
    }
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + i;
        hashCode = 31 * hashCode + j;
        return hashCode;
    }
}

//为了验证这个算法, 我们手动去求了 1 的所有坐标，并且调用上边的函数来验证我们的算法
public void gameOfLife(int[][] board) {
    Set<Coord> live = new HashSet<>();
    int m = board.length;
    int n = board[0].length;
    for (int i = 0; i<m; i++) {
        for (int j = 0; j<n; j++) {
            if (board[i][j] == 1) {
                live.add(new Coord(i,j));
            }
        }
    };
    live = gameOfLife(live);
    for (int i = 0; i<m; i++) {
        for (int j = 0; j<n; j++) {
            board[i][j] = live.contains(new Coord(i,j))?1:0;
        }
    };

}
```
上边的代码是基于假设题目给了我们所有 1 的坐标，但有可能题目仅仅给了我们一个矩阵 txt 文件，然后让我们自己去统计 1 的坐标。此时的话由于矩阵太大，我们就只能三行三行的分析了。

下边分享 beagle 的 python 代码，主要流程是从文件中去读取矩阵，每次读三行，然后统计这三行的 1 的所有坐标，然后再用上边的算法，最后将每行更新的结果打印出来。

但我觉得下边的代码细节上还有逻辑应该是有问题的，但整体思想可以供参考。
```
#Game of Life
from copy import deepcopy

#求出下一状态 1 的所有坐标, live 保存了所有 1 的坐标
def findLives(live):
    count = collections.Counter()
    #记录每个位置它的邻居中 1 的个数
    for i, j in live:
        for x in range(i-1, i+2):
            for y in range(j-1, j+2):
                if x == i and y == j: 
                    continue
                count[x, y] += 1
    result = {}
    for i, j in count:
        if count[i, j] == 3:
            result.add((i, j))
        elif count[i, j] == 2 and (i, j) in live:
            result.add((i, j))
    return result

#处理读出的三行或者两行(边界的情况)
def updateBoard(board):
    #统计 1 的所有坐标
    live = {(i, j) for i, row in enumerate(board) for j, v in enumerate(row) if v == 1}

    #得到下一状态的 1 的所有坐标
    live = findLives(live)
    for r, row in enumerate(board):
        for c, v in enumerate(row):
            board[r][c] = int((r, c) in live)
    for row in board:
        print(" ".join(row))

#假设矩阵在 input.txt 
with open("input.txt") as f1:
    prev = f1.readline()
    pointer = f1.readline()
    cur = next_ = None
    while pointer:
        if not cur:
            cur = pointer
            pointer = f1.readline()
            continue

        if next_ == None:
            next_ = pointer
            pointer = f1.readline()
        if prev == None:
            tmpBoard = [ cur, next_]
            nextStateBoard = updateBoard(tmpBoard)
        else:
            tmpBoard = [deepcopy(prev), cur, next_]
            nextStateBoard =  updateBoard(tmpBoard)

        prev = cur
        cur = next_
        next_ = None
```

总

看起来比较简单的一道题，但讨论了很多东西。

解法一的话只要想到先把值隐藏起来，然后还原，就比较好写了。然后再进一步，考虑要隐藏值的特点，可以简化代码。还有就是需要遍历周围坐标的时候，可以通过一系列的数值对来计算坐标，比单纯的一句句 if 会优雅很多。

解法二的角度很好，既然之前的值对后边的有影响，那么可以先去影响后边的值，然后再放心的更新自己的值。

最后的扩展，也是比较常见的处理较大矩阵时候的思路。主要就是两个，一个是通过稀疏矩阵，只保存关键坐标。另一个就是一部分一部分的处理矩阵。
