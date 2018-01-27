
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
int[][] board;
			
	/** Initialize your data structure here. */
	public TicTacToe(int n) {
        board = new int[n][n];
        for(int i = 0; i < n; i++) {
        	for(int j = 0; j < n; j++) {
        		board[i][j] = 0;
        	}
        }
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
        // Player 1 move will change board[row][col] from 0 to 1
		// Player 2 move will change board[row][col] from 0 to 2
		board[row][col] = player;
		// For each move check rows, cols, diagonals status
		int i = 0;
		int j = 0;
		int n = board.length;
		// Check all cols
		for(j = 1; j < n; j++) {
			if(board[row][j] != board[row][j - 1]) {
				break;
			}
		} 
		if(j == n) {
			return player;
		}
		// Check all rows
		for(i = 1; i < n; i++) {
			if(board[i][col] != board[i - 1][col]) {
				break;
			}
		}
		if(i == n) {
			return player;
		}
		// Check diagonal and anti-diagonal
		if(row == col) {
			for(i = 1; i < n; i++) {
				if(board[i][i] != board[i - 1][i - 1]) {
					break;
				}
			}
			if(i == n) {
				return player;
			}
		}
		if(row + col == n - 1) {
			for(i = 1; i < n; i++) {
				if(board[n - i - 1][i] != board[n - i][i - 1]) {
					break;
				}
			}
			if(i == n) {
				return player;
			}
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
