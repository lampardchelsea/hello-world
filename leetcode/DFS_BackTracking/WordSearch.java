/**
 * Given a 2D board and a word, find if the word exists in the grid.
 * The word can be constructed from letters of sequentially adjacent cell, 
 * where "adjacent" cells are those horizontally or vertically neighboring. 
 * The same letter cell may not be used more than once.
 * For example,
 *   Given board =
 *
 *   [
 *     ['A','B','C','E'],
 *     ['S','F','C','S'],
 *     ['A','D','E','E']
 *   ]
 *   word = "ABCCED", -> returns true,
 *   word = "SEE", -> returns true,
 *   word = "ABCB", -> returns false.
*/

// Solution 1: 
// Refer to
// http://www.cnblogs.com/springfor/p/3883942.html
// 这道题分析看，就是一个词，在一行出现也是true，一列出现也是true，一行往下拐弯也是true，一行往上拐弯也是true，
// 一列往左拐弯也是true，一列往右拐弯也是true。所以是要考虑到所有可能性，基本思路是使用DFS来对一个起点字母上下
// 左右搜索，看是不是含有给定的Word。还要维护一个visited数组，表示从当前这个元素是否已经被访问过了，过了这一轮
// visited要回false，因为对于下一个元素，当前这个元素也应该是可以被访问的。
public class Solution {
    public boolean exist(char[][] board, String word) {
        int rows = board.length;
        int columns = board[0].length;
        
        boolean[][] visited = new boolean[rows][columns];
        
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                if(dfs(board, word, 0, i, j, visited)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public boolean dfs(char[][] board, String word, int index, int rowIndex, int columnIndex, boolean[][] visited) {
        // Base case, when index reach the end, and not return as false means we found the word in board
        if(index == word.length()) {
            return true;
        } 
        
        // Boundary check must before conditional check, otherwise will throw out ArrayOutOfBound exception
        if(rowIndex < 0 || rowIndex > board.length - 1 || columnIndex < 0 || columnIndex > board[0].length - 1) {
            return false;
        }
        
        // Condition to break out current level loop as this item on board is already visited
        if(visited[rowIndex][columnIndex]) {
            return false;
        }
        
        // Condition to break out current level loop as this item on board not match the required character
        if(board[rowIndex][columnIndex] != word.charAt(index)) {
            return false;
        }
        
        // Record current item in board has been visited before looply detect next character
        // in potential item
        visited[rowIndex][columnIndex] = true;
        
        // Detect potential item for next character(index + 1) on right/left/up/down four directions 
        boolean result = dfs(board, word, index + 1, rowIndex + 1, columnIndex, visited) ||
                         dfs(board, word, index + 1, rowIndex - 1, columnIndex, visited) ||
                         dfs(board, word, index + 1, rowIndex, columnIndex + 1, visited) ||
                         dfs(board, word, index + 1, rowIndex, columnIndex - 1, visited);
        
        // Restore the boolean tag for current item for next round detect
        visited[rowIndex][columnIndex] = false;
        
        return result;
    }
    
}


// Solution 2: 
// Refer to
// https://segmentfault.com/a/1190000003697153
// 基本思路很简单，对矩阵里每个点都进行一次深度优先搜索，看它能够产生一个路径和所给的字符串是一样的。
// 重点在如何优化搜索，避免不必要的计算。比如我们一个方向的搜索中已经发现了这个词，那我们就不用再搜索。
// 另外，为了避免循环搜索，我们还要将本轮深度优先搜索中搜索过的数字变一下，等递归回来之后再变回来。
// 实现这个特性最简单的方法就是异或上一个特定数，然后再异或回来。
public class Solution {
    public boolean exist(char[][] board, String word) {
       int rows = board.length;
       int columns = board[0].length;
       
       for(int i = 0; i < rows; i++) {
           for(int j = 0; j < columns; j++) {
               if(dfs(board, word, 0, i, j)) {
                   return true;
               }
           }
       }
       
       return false;
    }
    
    public boolean dfs(char[][] board, String word, int index, int rowIndex, int columnIndex) {
        if(index == word.length()) {
            return true;
        }
        
        if(rowIndex < 0 || rowIndex >= board.length || columnIndex < 0 || columnIndex >= board[0].length || board[rowIndex][columnIndex] != word.charAt(index)) {
            return false;
        }
        
        // The number used for XOR(exclusive or) can be randomly pick up, such as 255, 127, 63...
        // The big improvement of this way is no need to introduce additional O(n) space, such as
        // Solution 1 contain boolean[][] visited to record each on board item status
        board[rowIndex][columnIndex] ^= 255;
        boolean result = dfs(board, word, index + 1, rowIndex + 1, columnIndex) ||
                         dfs(board, word, index + 1, rowIndex - 1, columnIndex) ||
                         dfs(board, word, index + 1, rowIndex, columnIndex + 1) ||
                         dfs(board, word, index + 1, rowIndex, columnIndex - 1);
        board[rowIndex][columnIndex] ^= 255;
        return result;
    }    
}

// Re-work
// https://leetcode.com/problems/word-search/discuss/27658/Accepted-very-short-Java-solution.-No-additional-space./26671
// Time Complexity
// Refer to
// https://leetcode.com/problems/word-search/discuss/27658/Accepted-very-short-Java-solution.-No-additional-space./193004
// Since no one is talking about the complexity. I think space is O(L) where L is the length of the word; and time is O(M * N * 4^L) 
// where M*N is the size of the board and we have 4^L for each cell because of the recursion. Of course this would be an upper bound
class Solution {
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(helper(board, i, j, word, 0, visited)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    int[] dx = new int[] {0,0,1,-1};
    int[] dy = new int[] {1,-1,0,0};
    private boolean helper(char[][] board, int x, int y, String word, int index, boolean[][] visited) {
        if(index == word.length()) {
            return true;
        }
        if(x < 0 || x >= board.length || y < 0 || y >= board[0].length || board[x][y] != word.charAt(index) || visited[x][y]) {
            return false;
        }
        visited[x][y] = true;
        for(int i = 0; i < 4; i++) {
            int new_x = x + dx[i];
            int new_y = y + dy[i];
            if(helper(board, new_x, new_y, word, index + 1, visited)) {
                return true;
            }
        }
        visited[x][y] = false;
        return false;
    }
}














































































https://leetcode.com/problems/word-search/description/

Given an m x n grid of characters board and a string word, return true if word exists in the grid.

The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.

Example 1:


```
Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
Output: true
```

Example 2:


```
Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
Output: true
```

Example 3:


```
Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
Output: false
```

Constraints:
- m == board.length
- n = board[i].length
- 1 <= m, n <= 6
- 1 <= word.length <= 15
- board and word consists of only lowercase and uppercase English letters.
 
Follow up: Could you use search pruning to make your solution faster with a larger board?
---
Attempt 1: 2023-10-25

Solution 1: Backtracking (10 min)

Style 1: With extra space
```
class Solution {
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(helper(board, i, j, 0, word, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    int[] dx = new int[] {0,0,1,-1};
    int[] dy = new int[] {1,-1,0,0};
    private boolean helper(char[][] board, int x, int y, int index, String word, boolean[][] visited) {
        if(index == word.length()) {
            return true;
        }
        if(x < 0 || x >= board.length || y < 0 || y >= board[0].length 
        || visited[x][y] || word.charAt(index) != board[x][y]) {
            return false;
        }
        visited[x][y] = true;
        for(int k = 0; k < 4; k++) {
            int new_x = x + dx[k];
            int new_y = y + dy[k];
            if(helper(board, new_x, new_y, index + 1, word, visited)) {
                return true;
            }
        }
        // Important: Don't forget to restore the boolean flag
        // for next round detect
        // What is the reason behind marking "visited[i][j] = false;" at the end ?
        // If I understood correctly after you have tried all the neighbors 
        // of the board[i][j] and couldn't find the matches, then you have 
        // to search the word from another position. let's say that position 
        // is board[m][n]. so from board[m][n] perspective, it hasn't visited 
        // board[i][j], so you have to set visited[i][j] back to false to allow 
        // other calls use it.
        // Note: visited is a static variable which is common to all the instances 
        // (or objects) of the class because it is a class level variable.
        visited[x][y] = false;
        return false;
    }
}

Time Complexity: O(n * (4 ^ w)), where n is number of cells and w is word length. 
To avoid confusion: O(r * c * (4 ^ w)), where r x c are the dimensions of the board.
Space Complexity: O(n)
To avoid confusion: O(r * c), where r x c are the dimensions of the board.
------------------------------------------------------------------------------------
For each cell, we initially have 4 DIRS to go. And each way has additional 4 DIRS, which that means each word length will cost 4 times more.
Thus the total TC would be O(4 ^ L * m * n) where L is word.length().
```

Style 2: Without extra space
```
class Solution {
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(helper(board, i, j, 0, word)) {
                    return true;
                }
            }
        }
        return false;
    }

    int[] dx = new int[] {0,0,1,-1};
    int[] dy = new int[] {1,-1,0,0};
    private boolean helper(char[][] board, int x, int y, int index, String word) {
        if(index == word.length()) {
            return true;
        }
        if(x < 0 || x >= board.length || y < 0 || y >= board[0].length 
        || board[x][y] == '#' || word.charAt(index) != board[x][y]) {
            return false;
        }
        char tmp = board[x][y];
        board[x][y] = '#';
        for(int k = 0; k < 4; k++) {
            int new_x = x + dx[k];
            int new_y = y + dy[k];
            if(helper(board, new_x, new_y, index + 1, word)) {
                return true;
            }
        }
        board[x][y] = tmp;
        return false;
    }
}

Time Complexity: O(n * (4 ^ w)), where n is number of cells and w is word length. 
To avoid confusion: O(r * c * (4 ^ w)), where r x c are the dimensions of the board.
Space Complexity: O(n), 
To avoid confusion: O(r * c), where r x c are the dimensions of the board.
Because if consider recursion it will have additional space, since it's basically a DFS using recursion. The recursion will open space on call stack, which consumes additional space. The space is O(size of board), since at a moment, the worst DFS branch traversed entire board.
------------------------------------------------------------------------------------
For each cell, we initially have 4 DIRS to go. And each way has additional 4 DIRS, which that means each word length will cost 4 times more.
Thus the total TC would be O(4 ^ L * m * n) where L is word.length().
```

Refer to
https://leetcode.com/problems/word-search/solutions/27658/accepted-very-short-java-solution-no-additional-space/
Here accepted solution based on recursion. To save memory I decuded to apply bit mask for every visited cell. Please check board[y][x] ^= 256;
```
public boolean exist(char[][] board, String word) {
    char[] w = word.toCharArray();
    for (int y=0; y<board.length; y++) {
    	for (int x=0; x<board[y].length; x++) {
    		if (exist(board, y, x, w, 0)) return true;
    	}
    }
    return false;
}

private boolean exist(char[][] board, int y, int x, char[] word, int i) {
	if (i == word.length) return true;
	if (y<0 || x<0 || y == board.length || x == board[y].length) return false;
	if (board[y][x] != word[i]) return false;
	board[y][x] ^= 256;
	boolean exist = exist(board, y, x+1, word, i+1)
		|| exist(board, y, x-1, word, i+1)
		|| exist(board, y+1, x, word, i+1)
		|| exist(board, y-1, x, word, i+1);
	board[y][x] ^= 256;
	return exist;
}
```

---
What is the reason behind marking "visited[i][j] = false;" at the end ?

Refer to
https://leetcode.com/problems/word-search/solutions/27811/my-java-solution/comments/265883
If I understood correctly after you have tried all the neighbors of the board[i][j] and couldn't find the matches, then you have to search the word from another position. let's say that position is board[m][n]. so from board[m][n] perspective, it hasn't visited board[i][j], so you have to set visited[i][j] back to false to allow other calls use it.
Note: visited is a static variable which is common to all the instances (or objects) of the class because it is a class level variable.
