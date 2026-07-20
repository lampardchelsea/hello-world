/**
Refer to
https://leetcode.com/problems/find-winner-on-a-tic-tac-toe-game/
Tic-tac-toe is played by two players A and B on a 3 x 3 grid.

Here are the rules of Tic-Tac-Toe:

Players take turns placing characters into empty squares (" ").
The first player A always places "X" characters, while the second player B always places "O" characters.
"X" and "O" characters are always placed into empty squares, never on filled ones.
The game ends when there are 3 of the same (non-empty) character filling any row, column, or diagonal.
The game also ends if all squares are non-empty.
No more moves can be played if the game is over.
Given an array moves where each element is another array of size 2 corresponding to the row and column of the grid 
where they mark their respective character in the order in which A and B play.

Return the winner of the game if it exists (A or B), in case the game ends in a draw return "Draw", if there are 
still movements to play return "Pending".

You can assume that moves is valid (It follows the rules of Tic-Tac-Toe), the grid is initially empty and A will play first.

Example 1:
Input: moves = [[0,0],[2,0],[1,1],[2,1],[2,2]]
Output: "A"
Explanation: "A" wins, he always plays first.
"X  "    "X  "    "X  "    "X  "    "X  "
"   " -> "   " -> " X " -> " X " -> " X "
"   "    "O  "    "O  "    "OO "    "OOX"

Example 2:
Input: moves = [[0,0],[1,1],[0,1],[0,2],[1,0],[2,0]]
Output: "B"
Explanation: "B" wins.
"X  "    "X  "    "XX "    "XXO"    "XXO"    "XXO"
"   " -> " O " -> " O " -> " O " -> "XO " -> "XO " 
"   "    "   "    "   "    "   "    "   "    "O  "

Example 3:
Input: moves = [[0,0],[1,1],[2,0],[1,0],[1,2],[2,1],[0,1],[0,2],[2,2]]
Output: "Draw"
Explanation: The game ends in a draw since there are no moves to make.
"XXO"
"OOX"
"XOX"

Example 4:
Input: moves = [[0,0],[1,1]]
Output: "Pending"
Explanation: The game has not finished yet.
"X  "
" O "
"   "

Constraints:
1 <= moves.length <= 9
moves[i].length == 2
0 <= moves[i][j] <= 2
There are no repeated elements on moves.
moves follow the rules of tic tac toe.
*/

// Solution 1: Beautiful orgnization of take turn ship
// Refer to
// https://leetcode.com/problems/find-winner-on-a-tic-tac-toe-game/discuss/441319/JavaPython-3-Check-rows-columns-and-two-diagonals-w-brief-explanation-and-analysis./610247
class Solution {
    public String tictactoe(int[][] moves) {
        int n = 3;
        int[] rows = new int[n];
        int[] cols = new int[n];
        int diag = 0;
        int sub_diag = 0;
        int player = 1; // 1 for 'A', -1 for 'B'
        for(int[] move : moves) {
            rows[move[0]] += player;
            cols[move[1]] += player;
            if(move[0] == move[1]) {
                diag += player;
            }
            if(move[0] + move[1] == n - 1) {
                sub_diag += player;
            }
            if(Math.abs(rows[move[0]]) == n || Math.abs(cols[move[1]]) == n || Math.abs(diag) == n || Math.abs(sub_diag) == n) {
                return player == 1 ? "A" : "B";
            }
            player *= -1; // Now turn to next round
        }
        return moves.length < 9 ? "Pending" : "Draw";
    }
}




























































https://leetcode.com/problems/find-winner-on-a-tic-tac-toe-game/description/
Tic-tac-toe is played by two players A and B on a 3 x 3 grid. The rules of Tic-Tac-Toe are:
- Players take turns placing characters into empty squares ' '.
- The first player A always places 'X' characters, while the second player B always places 'O' characters.
- 'X' and 'O' characters are always placed into empty squares, never on filled ones.
- The game ends when there are three of the same (non-empty) character filling any row, column, or diagonal.
- The game also ends if all squares are non-empty.
- No more moves can be played if the game is over.
Given a 2D integer array moves where moves[i] = [rowi, coli] indicates that the ith move will be played on grid[rowi][coli]. return the winner of the game if it exists (A or B). In case the game ends in a draw return "Draw". If there are still movements to play return "Pending".
You can assume that moves is valid (i.e., it follows the rules of Tic-Tac-Toe), the grid is initially empty, and A will play first.
 
Example 1:

Input: moves = [[0,0],[2,0],[1,1],[2,1],[2,2]]
Output: "A"
Explanation: A wins, they always play first.

Example 2:

Input: moves = [[0,0],[1,1],[0,1],[0,2],[1,0],[2,0]]
Output: "B"
Explanation: B wins.

Example 3:

Input: moves = [[0,0],[1,1],[2,0],[1,0],[1,2],[2,1],[0,1],[0,2],[2,2]]
Output: "Draw"
Explanation: The game ends in a draw since there are no moves to make.
 
Constraints:
- 1 <= moves.length <= 9
- moves[i].length == 2
- 0 <= rowi, coli <= 2
- There are no repeated elements on moves.
- moves follow the rules of tic tac toe.
--------------------------------------------------------------------------------
Attempt 1: 2026-07-19
Solution 1: Array + Matrix (10 min)
class Solution {
    public String tictactoe(int[][] moves) {
        char[][] board = new char[3][3];
        char player = 'A';
        for(int[] move : moves) {
            int r = move[0];
            int c = move[1];
            board[r][c] = player;
            if(checkWin(board, r, c, player)) {
                return String.valueOf(player);
            }
            player = (player == 'A') ? 'B' : 'A';
        }
        if(moves.length == 9) {
            return "Draw";
        } else {
            return "Pending";
        }
    }

    private boolean checkWin(char[][] board, int row, int col, char player) {
        // Check row
        if (board[row][0] == player && board[row][1] == player && board[row][2] == player) return true;
        // Check col
        if (board[0][col] == player && board[1][col] == player && board[2][col] == player) return true;
        // Check diagonal (main)
        if (row == col && board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        // Check anti-diagonal
        if (row + col == 2 && board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        return false;
    }
}

Time Complexity: O(n^2)
Space Complexity: O(n^2)

Solution 2: Array + Math (30 min)
class Solution {
    public String tictactoe(int[][] moves) {
        int[] rows = new int[3];
        int[] cols = new int[3];
        int diag = 0;
        int antiDiag = 0;
        for(int i = 0; i < moves.length; i++) {
            int r = moves[i][0];
            int c = moves[i][1];
            // 1 for A, -1 for B
            int val = (i % 2 == 0) ? 1 : -1; 
            rows[r] += val;
            cols[c] += val;
            if(r == c) {
                diag += val;
            }
            if(r + c == 2) {
                antiDiag += val;
            }
            if(Math.abs(rows[r]) == 3 || Math.abs(cols[c]) == 3 || 
            Math.abs(diag) == 3 || Math.abs(antiDiag) == 3) {
                return (val == 1) ? "A" : "B";
            }
        }
        return (moves.length == 9) ? "Draw" : "Pending";
    }
}

Time Complexity: O(n)
Space Complexity: O(1)

Refer to Deepseek
1.Analyze the Request:
- User is asking for the Java solution to Leetcode 1275: "Find Winner on a Tic Tac Toe Game".
- Previous context: Solved many Leetcode problems (312, 1438, 1425, 2294, 2444, 2779, 545). The assistant used a pattern of explaining the core idea, providing Java code, complexity analysis, and often comparing to similar problems to build a mental model.
- This problem (1275) is categorized as Easy/Medium. The constraint is that the board is exactly a 3x3 Tic Tac Toe, and we are given a list of moves in order. We need to return the winner ("A", "B", or "Draw"/"Pending" depending on if board is full).
2.Brainstorm Solutions for 1275:
- Approach 1 (Straightforward): Simulate the board using a 2D char/int array. Populate it based on moves. After placing a move, check if that player just won (check rows, cols, diagonals). If won, return that player. If all moves are processed and no one won, return "Draw" if total moves == 9, else "Pending".
- Approach 2 (Bitmask): Use bitmasks (since it's 3x3, 9 bits) for A and B. Check 8 winning conditions (3 rows, 3 cols, 2 diags) using bitmask masks.
- Approach 3 (Check only last move): Since only the last placed piece can trigger a win, we can just check the rows/cols/diags containing that last move. This is efficient and easy.
- Approach 4 (Brute force over all winning conditions): Store all 8 winning coordinate combos. Iterate through them for A and B.
3.Select the best approach for explanation:
- The simplest, most readable, and bug-free approach is simulating the board with a 2D array (char[][] or int[][]).
- After each move, check the rows, columns, and diagonals for a win. Since the board is tiny (3x3), this is perfectly fine.
- Let's use char board, 'A' and 'B'.
4.Draft the Code (Java):
class Solution {
    public String tictactoe(int[][] moves) {
        char[][] board = new char[3][3];
        char player = 'A';
        
        for (int[] move : moves) {
            int r = move[0], c = move[1];
            board[r][c] = player;
            
            if (checkWin(board, r, c, player)) {
                return String.valueOf(player);
            }
            
            player = (player == 'A') ? 'B' : 'A';
        }
        
        // No winner yet
        if (moves.length == 9) {
            return "Draw";
        } else {
            return "Pending";
        }
    }
    
    private boolean checkWin(char[][] board, int row, int col, char player) {
        // Check row
        if (board[row][0] == player && board[row][1] == player && board[row][2] == player) return true;
        // Check col
        if (board[0][col] == player && board[1][col] == player && board[2][col] == player) return true;
        // Check diagonal (main)
        if (row == col && board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        // Check anti-diagonal
        if (row + col == 2 && board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        
        return false;
    }
}
5.Alternative Approach (using winning patterns without simulation):Actually, a highly cited elegant solution uses a int[] rows, int[] cols, int diag, int antiDiag and increments/decrements with +1 for A and -1 for B. If any absolute value reaches 3, that player wins. This is incredibly efficient and concise. Let's show this as the primary or secondary solution because it demonstrates great algorithmic thinking.Let's explain the +/- 1 trick.int[] rows = new int[3]; int[] cols = new int[3]; int diag = 0, anti = 0;For move i:int val = (i % 2 == 0) ? 1 : -1; // A gets 1, B gets -1.Update rows[r] += val; cols[c] += val;If r == c, diag += val;If r + c == 2, anti += val;If Math.abs(rows[r]) == 3 or Math.abs(cols[c]) == 3 or Math.abs(diag) == 3 or Math.abs(anti) == 3, then A wins if val == 1, B wins if val == -1. Actually, we can just check the absolute value, but need to return the correct player. If val == 1 (A), else B.
6.Let's write the clean solution:I'll present the bitmask/summing strategy (rows/cols/diag counter) first as it's O(1) time and space (apart from moves array) and most elegant, followed by the board simulation for readability if desired.Wait, the user asked "java solution ?" – I'll provide the most elegant one. Let's use the rows/cols/diag/anti sum approach.
Check winner logic:
class Solution {
    public String tictactoe(int[][] moves) {
        int[] rows = new int[3];
        int[] cols = new int[3];
        int diag = 0, antiDiag = 0;
        
        for (int i = 0; i < moves.length; i++) {
            int r = moves[i][0], c = moves[i][1];
            int val = (i % 2 == 0) ? 1 : -1; // 1 for A, -1 for B
            
            rows[r] += val;
            cols[c] += val;
            if (r == c) diag += val;
            if (r + c == 2) antiDiag += val;
            
            if (Math.abs(rows[r]) == 3 || Math.abs(cols[c]) == 3 || 
                Math.abs(diag) == 3 || Math.abs(antiDiag) == 3) {
                return (val == 1) ? "A" : "B";
            }
        }
        
        return (moves.length == 9) ? "Draw" : "Pending";
    }
}
7.Explaining the logic:
- Tic Tac Toe is 3x3. A win is a row, col, or diagonal summing to 3 of the same player.
- Assign +1 to Player A and -1 to Player B.
- If any row/col/diag sums to +3 -> A wins. If -3 -> B wins. Since we check immediately after placing, abs() == 3 is universally applicable, then return based on val.
- If no winner after 9 moves, it's a "Draw". Else "Pending".
8.Review against previous response style:
- Core Idea section.
- Java Code block.
- Complexity analysis.
- Comparison to previous problems (optional but asked for pattern recognition). Let's tie it back to how this relates to "simulation" vs "complex algorithms".
9.Final Polish:
- Ensure the code compiles.
- Mention that the board is fixed at 3x3, so we don't need to iterate over 8 patterns every time, we just track the impacted row/col/diag.
- Let's address the edge case: moves is given, and it's guaranteed to be valid (no over-writing).

这道题 (LeetCode 1275) 和之前那些复杂的 DP、滑动窗口或树遍历截然不同，它是一道纯粹的模拟 (Simulation) 题。因为棋盘只有 3x3，所以我们甚至不需要真的创建一个二维数组。
这里有一种极其优雅、高效的解法，叫做 “差值统计法 (Difference Counter)”，把代码压缩到极致。
--------------------------------------------------------------------------------
核心思路：差值统计法
1.赋值：A 下的棋记为 +1，B 下的棋记为 -1。
2.统计：我们不需要看整个棋盘，只需要追踪 3行、3列、2条对角线 的累加和。
- 每当 A 在 (r, c) 落子，rows[r] += 1, cols[c] += 1，如果在对角线上，对应 diag += 1。
- 每当 B 在 (r, c) 落子，rows[r] += -1, cols[c] += -1，如果在对角线上，对应 diag += -1。
3.判定胜负：
- 如果某一行、列或对角线的绝对值达到了 3，说明这一整条线全被同一个玩家占满了。
- 因为 A 是 +1，如果和为 3，赢家是 A；如果和为 -3，赢家是 B。
Java 代码实现 (极致优雅版)
class Solution {
    public String tictactoe(int[][] moves) {
        int[] rows = new int[3];
        int[] cols = new int[3];
        int diag = 0;      // 主对角线 (0,0) (1,1) (2,2)
        int antiDiag = 0;  // 副对角线 (0,2) (1,1) (2,0)

        // 遍历每一步落子
        for (int i = 0; i < moves.length; i++) {
            int r = moves[i][0];
            int c = moves[i][1];
            
            // A 的步数是偶数位 (0, 2, 4...)，赋值为 1；B 为奇数位，赋值为 -1
            int val = (i % 2 == 0) ? 1 : -1; 
            
            // 更新对应的行、列、对角线状态
            rows[r] += val;
            cols[c] += val;
            if (r == c) diag += val;
            if (r + c == 2) antiDiag += val;
            
            // 检查当前玩家是否已经赢了（绝对值等于 3）
            if (Math.abs(rows[r]) == 3 || 
                Math.abs(cols[c]) == 3 || 
                Math.abs(diag) == 3 || 
                Math.abs(antiDiag) == 3) {
                // val == 1 说明是 A，否则是 B
                return val == 1 ? "A" : "B";
            }
        }
        
        // 没人赢：检查棋盘是否下满 (9步)
        return moves.length == 9 ? "Draw" : "Pending";
    }
}
复杂度分析
- 时间复杂度：O(n)，其中 n 是 moves 的长度（最多 9）。我们只遍历了落子记录一次。
- 空间复杂度：O(1)，只使用了固定大小的数组和变量。
为什么这种方法比“建二维数组”更优？
如果建立 char[3][3] 棋盘，每次落子后检查胜负，你需要扫描行、列、对角线，最坏情况需要检查 8 条线，每次检查 3 个格子，总共 24 次操作。
而差值统计法只更新当前这一步影响到的行、列、对角线（最多 4 个变量），将胜负判定压缩到了 O(1) 的常数时间，代码也更加干净。
总结：对于棋盘类问题，如果规模极小（如 3x3），用纯粹的数学计算（+1/-1 累加）比创建数据结构要高效得多。这也是 LeetCode 简单题考察的核心——你能不能从简单的场景中抽象出最精简的代码逻辑。

Refer to QWEN
LeetCode 1275. Find Winner on a Tic Tac Toe Game is a fun and straightforward simulation problem. While you could create a 3x3 character grid and check for wins, there is a much more elegant and efficient mathematical approach.
The Core Concept: The Scoring Trick
Instead of simulating the board, we can use a running sum for every row, column, and diagonal.
- Assign Player A a value of 1.
- Assign Player B a value of -1.
Whenever a player makes a move, we add their value to the corresponding row, column, and diagonal.
Because the board is exactly 3x3, a player wins if and only if any row, column, or diagonal sum reaches exactly 3 or -3.
This completely eliminates the need to write messy if-else blocks to check for three-in-a-rows!
Java Solution
class Solution {
    public String tictactoe(int[][] moves) {
        // Keep track of the sum for each row and column
        int[] rows = new int[3];
        int[] cols = new int[3];
        
        // Keep track of the sum for the two diagonals
        int diag1 = 0; // Top-left to bottom-right
        int diag2 = 0; // Top-right to bottom-left
        
        for (int i = 0; i < moves.length; i++) {
            int r = moves[i][0];
            int c = moves[i][1];
            
            // Player A goes first (even indices), Player B goes second (odd indices)
            int player = (i % 2 == 0) ? 1 : -1;
            
            // Update the running sums
            rows[r] += player;
            cols[c] += player;
            
            // A cell (r, c) is on the main diagonal if r == c
            if (r == c) {
                diag1 += player;
            }
            // A cell (r, c) is on the anti-diagonal if r + c == 2 (for a 3x3 grid)
            if (r + c == 2) {
                diag2 += player;
            }
            
            // Check if the current player has won
            if (Math.abs(rows[r]) == 3 || Math.abs(cols[c]) == 3 || 
                Math.abs(diag1) == 3 || Math.abs(diag2) == 3) {
                return player == 1 ? "A" : "B";
            }
        }
        
        // If no one won, check if the board is full (Draw) or has empty spaces (Pending)
        return moves.length == 9 ? "Draw" : "Pending";
    }
}
Step-by-Step Explanation
1.Initialization: We create arrays of size 3 for rows and cols, and two integers for the diagonals. All start at 0.
2.Determine the Player: Since Player A always goes first, any move at an even index (0, 2, 4...) is Player A (+1), and any move at an odd index (1, 3, 5...) is Player B (-1).
3.Updating Sums: For a move at (r, c), we add the player value to rows[r] and cols[c].
4.Diagonal Logic:
- The main diagonal (top-left to bottom-right) consists of cells where the row index equals the column index: (0,0), (1,1), (2,2). So, r == c.
- The anti-diagonal (top-right to bottom-left) consists of cells where the indices sum to 2 (since max index is 2): (0,2), (1,1), (2,0). So, r + c == 2.
5.Checking for a Win: Because a line requires exactly 3 marks, we just check if the absolute value of any updated sum is 3. If it is, the current player wins. We return "A" if player == 1, else "B".
6.Draw vs. Pending: If the loop finishes without returning a winner, the game hasn't ended in a win. If exactly 9 moves were made, the board is full, so it's a "Draw". Otherwise, there are empty squares left, so it's "Pending".
Complexity Analysis
- Time Complexity: $\mathcal{O}(N)$, where $N$ is the number of moves (at most 9). We process each move in $\mathcal{O}(1)$ constant time. Therefore, the time complexity is effectively $\mathcal{O}(1)$.
- Space Complexity: $\mathcal{O}(1)$. We only use a few fixed-size arrays and integer variables, regardless of the input size.


Refer to Kimi
Here is the clean Java solution for Leetcode 1275. Find Winner on a Tic Tac Toe Game.
Key Insight
Instead of simulating the board, we can use bitmasking to track each player's moves. A 3×3 board has 9 cells, so we can represent each player's occupied cells with a 9-bit integer. After each move, we check if any of the 8 winning patterns matches.
Java Solution
class Solution {
    // All 8 winning patterns for Tic Tac Toe
    // Represented as bitmasks (9 bits, row-major: 0-8)
    private static final int[] WIN_PATTERNS = {
        0b111000000, // Row 0: cells 0,1,2
        0b000111000, // Row 1: cells 3,4,5
        0b000000111, // Row 2: cells 6,7,8
        0b100100100, // Col 0: cells 0,3,6
        0b010010010, // Col 1: cells 1,4,7
        0b001001001, // Col 2: cells 2,5,8
        0b100010001, // Diagonal: cells 0,4,8
        0b001010100  // Anti-diagonal: cells 2,4,6
    };
    
    public String tictactoe(int[][] moves) {
        int playerA = 0; // 'A' moves first
        int playerB = 0; // 'B' moves second
        
        for (int i = 0; i < moves.length; i++) {
            int row = moves[i][0];
            int col = moves[i][1];
            int pos = row * 3 + col; // Convert to 0-8 index
            
            if (i % 2 == 0) {
                // A's turn (0th, 2nd, 4th... move)
                playerA |= (1 << pos);
                if (isWin(playerA)) {
                    return "A";
                }
            } else {
                // B's turn
                playerB |= (1 << pos);
                if (isWin(playerB)) {
                    return "B";
                }
            }
        }
        
        // No winner
        return moves.length == 9 ? "Draw" : "Pending";
    }
    
    private boolean isWin(int bitmask) {
        for (int pattern : WIN_PATTERNS) {
            if ((bitmask & pattern) == pattern) {
                return true; // All 3 cells in this pattern are occupied
            }
        }
        return false;
    }
}
How it works
StepExplanationConvert move to bitrow * 3 + col gives position 0-8Track with bitmaskplayerA |= (1 << pos) sets the bit for that cellCheck winFor each of 8 patterns, check if (bitmask & pattern) == pattern — meaning all 3 bits are set
Example Walkthrough
moves = [[0,0],[2,0],[1,1],[2,1],[2,2]]
MovePlayerPositionBitmaskBinary[0,0]A01 << 0 = 1000000001[2,0]B61 << 6 = 64010000000[1,1]A41 | 16 = 17000010001[2,1]B764 | 128 = 192110000000[2,2]A817 | 256 = 273100010001
Check A's bitmask 273 against patterns:
- 0b100010001 (diagonal 0,4,8) → (273 & 273) == 273 ✓ A wins!
Complexity
Time$O(n)$ where $n$ = moves.length (≤ 9), each check is $O(1)$ with 8 patternsSpace$O(1)$ — just two integers
Alternative: Simple Board Simulation
If you prefer readability over bitmask elegance:
class Solution {
    public String tictactoe(int[][] moves) {
        char[][] board = new char[3][3];
        
        for (int i = 0; i < moves.length; i++) {
            char c = (i % 2 == 0) ? 'X' : 'O';
            board[moves[i][0]][moves[i][1]] = c;
        }
        
        // Check rows, cols, diagonals for winner
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0] == 'X' ? "A" : "B";
            }
            if (board[0][i] != 0 && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return board[0][i] == 'X' ? "A" : "B";
            }
        }
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0] == 'X' ? "A" : "B";
        }
        if (board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2] == 'X' ? "A" : "B";
        }
        
        return moves.length == 9 ? "Draw" : "Pending";
    }
}
Both are valid; the bitmask version is more compact and slightly faster, while the board version is more intuitive.


Refer to
L2525.Categorize Box According to Criteria (Ref.L1275)
