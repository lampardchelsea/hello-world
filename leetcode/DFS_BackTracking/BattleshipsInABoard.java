https://leetcode.com/problems/battleships-in-a-board/description/
Given an m x n matrix board where each cell is a battleship 'X' or empty '.', return the number of the battleships on board.
Battleships can only be placed horizontally or vertically on board. In other words, they can only be made of the shape 1 x k (1 row, k columns) or k x 1 (k rows, 1 column), where k can be of any size. At least one horizontal or vertical cell separates between two battleships (i.e., there are no adjacent battleships).
 
Example 1:

Input: board = [["X",".",".","X"],
                          [".",".",".","X"],
                          [".",".",".","X"]]
Output: 2

Example 2:
Input: board = [["."]]
Output: 0
 
Constraints:
- m == board.length
- n == board[i].length
- 1 <= m, n <= 200
- board[i][j] is either '.' or 'X'.
 
Follow up: Could you do it in one-pass, using only O(1) extra memory and without modifying the values board?
--------------------------------------------------------------------------------
Attempt 1: 2025-10-29
Solution 1: DFS (30min)
class Solution {
    public int countBattleships(char[][] board) {
        int count = 0;
        int m = board.length;
        int n = board[0].length;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(board[i][j] == 'X') {
                    count++;
                    helper(board, m, n, i, j);
                }
            }
        }
        return count;
    }

    private void helper(char[][] board, int m, int n, int i, int j) {
        if(i < 0 || i >= m || j < 0 || j >= n || board[i][j] != 'X') {
            return;
        }
        // Mark as visited, prevent re-visiting
        board[i][j] = '.';
        // Since ships are straight, we only need to check right and down,
        // because that's how the battleships organized (they can only be 
        // made of the shape 1 x k (1 row, k columns) or k x 1 (k rows, 
        // 1 column)), and since we continously mark 'X' to '.', its similar
        // like how 'L200. Number of islands' works, this strategy will only
        // impact connected cells(ships), and won't impact isoldated cells(ships). 
        helper(board, m, n, i + 1, j);
        helper(board, m, n, i, j + 1);
    }
}

Time Complexity: O(m * n)
Space Complexity: O(min(m, n)) recursion stack

Solution 2: Math (30min)
class Solution {
    public int countBattleships(char[][] board) {
        int count = 0;
        int m = board.length;
        int n = board[0].length;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                // Check if this is the first cell of a battleship
                // For horizontal ships: no 'X' to the left
                // For vertical ships: no 'X' above
                if(board[i][j] == 'X') {
                    if((i == 0 || board[i - 1][j] != 'X') 
                    && (j == 0 || board[i][j - 1] != 'X')) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}

Time Complexity: O(m * n)
Space Complexity: O(1)
Refer to Deepseek
For LeetCode 419 "Battleships in a Board", there's an elegant one-pass O(1) space solution that doesn't require modifying the board. Here are the Java solutions:
🎯 Most Efficient Solution (One-Pass, O(1) Space)
class Solution {
    public int countBattleships(char[][] board) {
        int count = 0;
        int m = board.length;
        int n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // If current cell is not 'X', skip
                if (board[i][j] != 'X') continue;
                
                // Check if this is the first cell of a battleship
                // For horizontal ships: no 'X' to the left
                // For vertical ships: no 'X' above
                if ((i == 0 || board[i-1][j] != 'X') && 
                    (j == 0 || board[i][j-1] != 'X')) {
                    count++;
                }
            }
        }
        return count;
    }
}
🔍 How the Efficient Solution Works
The key insight is that we only count the "first" cell of each battleship:
- For horizontal ships: Count only the leftmost 'X' (no 'X' to the left)
- For vertical ships: Count only the topmost 'X' (no 'X' above)
This works because battleships are straight lines (1×k or k×1) and don't touch each other.
📝 Example Walkthrough
Board:
X . . X
. . . X  
. . . X
. X . .
Counting:
- [0,0]: No left, no above → COUNT (vertical ship)
- [0,3]: No left, no above → COUNT (horizontal ship)
- [1,3]: Has above 'X' → skip
- [2,3]: Has above 'X' → skip
- [3,1]: No left, no above → COUNT (single cell ship)
Total: 3 battleships
🚢 Alternative DFS Solution
If you prefer a more explicit graph approach:
class Solution {
    public int countBattleships(char[][] board) {
        int count = 0;
        int m = board.length;
        int n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'X') {
                    count++;
                    dfs(board, i, j);
                }
            }
        }
        return count;
    }
    
    private void dfs(char[][] board, int i, int j) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != 'X') {
            return;
        }
        // Mark as visited
        board[i][j] = '.';
        // Since ships are straight, we only need to check right and down
        dfs(board, i, j + 1); // right
        dfs(board, i + 1, j); // down
    }
}
📊 Comparison of Approaches
ApproachTime ComplexitySpace ComplexityModifies BoardOne-Pass CountingO(m×n)O(1)NoDFSO(m×n)O(min(m,n)) recursion stackYes
💡 Key Points
- Battleship Rules: Ships are 1×k or k×1, don't touch diagonally
- Efficient Solution: Count only the "head" of each ship
- No Modification Needed: The one-pass solution preserves the original board
- Early Stop: Once we identify a ship's head, we don't need to process its body
The one-pass O(1) space solution is generally preferred for its elegance and efficiency!

Refer to
L200.Number of Islands (Ref.L419,L1568)
L695.Max Area of Island (Ref.L200,L1568)
L994.Rotting Oranges (Ref.286,2101)
