
/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5467118.html
 * Design a Tic-tac-toe game that is played between two players on a n x n grid.

	You may assume the following rules:
	
	A move is guaranteed to be valid and is placed on an empty block.
	Once a winning condition is reached, no more moves is allowed.
	A player who succeeds in placing n of their marks in a horizontal, vertical, or diagonal row wins the game.
	Example:
	Given n = 3, assume that player 1 is "X" and player 2 is "O" in the board.
	
	TicTacToe toe = new TicTacToe(3);
	
	toe.move(0, 0, 1); -> Returns 0 (no one wins)
	|X| | |
	| | | | // Player 1 makes a move at (0, 0).
	| | | |
	
	toe.move(0, 2, 2); -> Returns 0 (no one wins)
	|X| |O|
	| | | | // Player 2 makes a move at (0, 2).
	| | | |
	
	toe.move(2, 2, 1); -> Returns 0 (no one wins)
	|X| |O|
	| | | | // Player 1 makes a move at (2, 2).
	| | |X|
	
	toe.move(1, 1, 2); -> Returns 0 (no one wins)
	|X| |O|
	| |O| | // Player 2 makes a move at (1, 1).
	| | |X|
	
	toe.move(2, 0, 1); -> Returns 0 (no one wins)
	|X| |O|
	| |O| | // Player 1 makes a move at (2, 0).
	|X| |X|
	
	toe.move(1, 0, 2); -> Returns 0 (no one wins)
	|X| |O|
	|O|O| | // Player 2 makes a move at (1, 0).
	|X| |X|
	
	toe.move(2, 1, 1); -> Returns 1 (player 1 wins)
	|X| |O|
	|O|O| | // Player 1 makes a move at (2, 1).
	|X|X|X|
	
	Follow up:
	Could you do better than O(n2) per move() operation?
	
	Hint:
	Could you trade extra space such that move() operation can be done in O(1)?
	You need two arrays: int rows[n], int cols[n], plus two variables: diagonal, anti_diagonal.
 * 
 * 
 * Solution
 * http://www.cnblogs.com/grandyang/p/5467118.html
 * https://www.youtube.com/watch?v=tpfmHHdfmoI
 * http://www.cs.toronto.edu/~hojjat/108w07/lectures/mar16/TicTacToe.java
 * https://codereview.stackexchange.com/questions/15911/tictactoe-logic-in-java
 */
// Solution 1:
/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5467118.html
 * 我们首先来O(n2)的解法，这种方法的思路很straightforward，就是建立一个nxn大小的board，
 * 其中0表示该位置没有棋子，1表示玩家1放的子，2表示玩家2。那么棋盘上每增加一个子，我们都
 * 扫描当前行列，对角线，和逆对角线，看看是否有三子相连的情况，有的话则返回对应的玩家，没有则返回0
 */
public class TicTacToe {
// Solution 1:
	/**
	 * Refer to
	 * http://www.cnblogs.com/grandyang/p/5467118.html
	 * 我们首先来O(n2)的解法，这种方法的思路很straightforward，就是建立一个nxn大小的board，
	 * 其中0表示该位置没有棋子，1表示玩家1放的子，2表示玩家2。那么棋盘上每增加一个子，我们都
	 * 扫描当前行列，对角线，和逆对角线，看看是否有三子相连的情况，有的话则返回对应的玩家，没有则返回0
	 */
//	int[][] board;
//			
//	/** Initialize your data structure here. */
//	public TicTacToe(int n) {
//        board = new int[n][n];
//        for(int i = 0; i < n; i++) {
//        	for(int j = 0; j < n; j++) {
//        		board[i][j] = 0;
//        	}
//        }
//	}
//
//	/** Player {player} makes a move at ({row}, {col}).
//	    @param row The row of the board.
//	    @param col The column of the board.
//	    @param player The player, can be either 1 or 2.
//	    @return The current winning condition, can be either:
//	            0: No one wins.
//	            1: Player 1 wins.
//	            2: Player 2 wins. */
//	public int move(int row, int col, int player) {
//        // Player 1 move will change board[row][col] from 0 to 1
//		// Player 2 move will change board[row][col] from 0 to 2
//		board[row][col] = player;
//		// For each move check rows, cols, diagonals status
//		int i = 0;
//		int j = 0;
//		int n = board.length;
//		// Check all cols
//		for(j = 1; j < n; j++) {
//			if(board[row][j] != board[row][j - 1]) {
//				break;
//			}
//		} 
//		if(j == n) {
//			return player;
//		}
//		// Check all rows
//		for(i = 1; i < n; i++) {
//			if(board[i][col] != board[i - 1][col]) {
//				break;
//			}
//		}
//		if(i == n) {
//			return player;
//		}
//		// Check diagonal and anti-diagonal
//		if(row == col) {
//			for(i = 1; i < n; i++) {
//				if(board[i][i] != board[i - 1][i - 1]) {
//					break;
//				}
//			}
//			if(i == n) {
//				return player;
//			}
//		}
//		if(row + col == n - 1) {
//			for(i = 1; i < n; i++) {
//				if(board[n - i - 1][i] != board[n - i][i - 1]) {
//					break;
//				}
//			}
//			if(i == n) {
//				return player;
//			}
//		}
//		return 0;
//	}
	
	// Solution 2: Time Complexity: O(n)
	// Refer to
	// http://www.cnblogs.com/grandyang/p/5467118.html
	/**
	 * Follow up中让我们用更高效的方法，那么根据提示中的，我们建立一个大小为n的一维数组rows和cols，
	 * 还有变量对角线diag和逆对角线rev_diag，这种方法的思路是，如果玩家1在第一行某一列放了一个子，
	 * 那么rows[0]自增1，如果玩家2在第一行某一列放了一个子，则rows[0]自减1，那么只有当rows[0]等于
	 * n或者-n的时候，表示第一行的子都是一个玩家放的，则游戏结束返回该玩家即可，其他各行各列，对角线
	 * 和逆对角线都是这种思路
	 */
	// Refer to
	// https://discuss.leetcode.com/topic/44548/java-o-1-solution-easy-to-understand
	/**
	 * Initially, I had not read the Hint in the question and came up with an O(n) solution. 
	 * After reading the extremely helpful hint; a much easier approach became apparent. 
	 * The key observation is that in order to win Tic-Tac-Toe you must have the entire 
	 * row or column. Thus, we don't need to keep track of an entire n^2 board. We only 
	 * need to keep a count for each row and column. If at any time a row or column matches 
	 * the size of the board then that player has won.
	 * To keep track of which player, I add one for Player1 and -1 for Player2. There are 
	 * two additional variables to keep track of the count of the diagonals. Each time a 
	 * player places a piece we just need to check the count of that row, column, diagonal 
	 * and anti-diagonal.
	 * Also see a very similar answer that I believe had beaten me to the punch. We came up 
	 * with our solutions independently but they are very similar in principle.
	 */
	/** Initialize your data structure here. */
	int[] rows;
	int[] cols;
	int diagonal;
	int antidiagonal;
	public TicTacToe(int n) {
        rows = new int[n];
        cols = new int[n];
	}

	/** Player {player} makes a move at ({row}, {col}).
	    @param row The row of the board.
	    @param col The column of the board.
	    @param player The player, can be either 1 or 2.
	    @return The current winning condition, can be either:
	            0: No one wins.
	            1: Player 1 wins.
	            2: Player 2 wins. */
	public int move(int row, int col, int player) {
        int toAdd = player == 1 ? 1 : -1;
        rows[row] += toAdd;
        cols[col] += toAdd;
        if(row == col) {
        	diagonal += toAdd;
        }
        if(row + col == cols.length - 1) {
        	antidiagonal += toAdd;
        }
        int size = rows.length;
        if(Math.abs(rows[row]) == size || Math.abs(cols[col]) == size
        		|| Math.abs(diagonal) == size || Math.abs(antidiagonal) == size) {
        	return player;
        }
        return 0;
	}
	
	public static void main(String[] args) {
		TicTacToe t = new TicTacToe(3);
		int a = t.move(0, 0, 1);
		int b = t.move(0, 2, 2);
		int c = t.move(2, 2, 1);
		int d = t.move(1, 1, 2);
		int e = t.move(2, 0, 1);
		int f = t.move(1, 0, 2);
		int g = t.move(2, 1, 1);
		System.out.print(a + " " + b + " " + c + " " + d + " " + e + " " + f + " " + g);
	}
}
