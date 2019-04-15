import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Refer to
 * https://codereview.stackexchange.com/questions/15911/tictactoe-logic-in-java
 * 
 * Running effect
 * Welcome to Tic Tac Toe
	 | | 
	-+-+-
	 | | 
	-+-+-
	 | | 
	Player X, it's your turn, Make your move!
	Enter the horizontal coordinate of the cell [1-3]: 1
	Enter the vertical coordinate of the cell [1-3]: 2
	 | | 
	-+-+-
	X| | 
	-+-+-
	 | | 
	Player O, it's your turn, Make your move!
	Enter the horizontal coordinate of the cell [1-3]: 1
	Enter the vertical coordinate of the cell [1-3]: 2
	Illegal move, try again
	Enter the horizontal coordinate of the cell [1-3]: 2
	Enter the vertical coordinate of the cell [1-3]: 1
	 |O| 
	-+-+-
	X| | 
	-+-+-
	 | | 
	Player X, it's your turn, Make your move!
	Enter the horizontal coordinate of the cell [1-3]: 1
	Enter the vertical coordinate of the cell [1-3]: 1
	X|O| 
	-+-+-
	X| | 
	-+-+-
	 | | 
	Player O, it's your turn, Make your move!
	Enter the horizontal coordinate of the cell [1-3]: 2
	Enter the vertical coordinate of the cell [1-3]: 2
	X|O| 
	-+-+-
	X|O| 
	-+-+-
	 | | 
	Player X, it's your turn, Make your move!
	Enter the horizontal coordinate of the cell [1-3]: 3
	Enter the vertical coordinate of the cell [1-3]: 2
	X|O| 
	-+-+-
	X|O|X
	-+-+-
	 | | 
	Player O, it's your turn, Make your move!
	Enter the horizontal coordinate of the cell [1-3]: 3
	Enter the vertical coordinate of the cell [1-3]: 3
	X|O| 
	-+-+-
	X|O|X
	-+-+-
	 | |O
	Player X, it's your turn, Make your move!
	Enter the horizontal coordinate of the cell [1-3]: 1
	Enter the vertical coordinate of the cell [1-3]: 3
	X|O| 
	-+-+-
	X|O|X
	-+-+-
	X| |O
	X won. Thank you for playing.
 */
 public class DesignTicTacToe {
 private static final Scanner in = new Scanner(System.in);
	private static final PrintStream out = System.out;
	private static final Game game = new DesignTicTacToe(). new Game();
	
	/**
	 * Tracking Game State
	 */
	private class Game {
		private final Board board = new Board();
		private Player currentPlayer = Player.X;
	
		/**
		 * The game has a Board and tracks the current Player (X has the first move; 
		 * Player is an enum). Scroll back up and look at our while loop in main. 
		 * So now it's clear: the game is only running if there isn't a winner yet 
		 * and the board is not yet full. Otherwise, it's ended.
		 */
		public boolean isRunning() {
			return !currentPlayer.isWinner && !board.isFull();
		}
		
		public Player getCurrentPlayer() {
		    return currentPlayer;	
		}
		
		/**
		 * A move is allowed if the game is running and the cell we are trying to put 
		 * an X or O in is empty.
		 */
		public boolean isMoveAllowed(Move move) {
			return isRunning() && board.isCellEmpty(move.X, move.Y);
		}
		
		/**
		 * You can only make a move if the game is still running. If we don't have a 
		 * winner after making the move, the next player's turn starts
		 */
		public void makeMove(Move move) {
			if(!isRunning()) {
				throw new IllegalStateException("Game has ended!");
			}
			board.setCell(move.X, move.Y, currentPlayer);
			if(!currentPlayer.isWinner) {
				currentPlayer = currentPlayer.getNextPlayer();
			}
		}
		
		/**
		 * When the game has finished, we can check the result
		 */
		public GameResult getResult() {
			if(isRunning()) {
				throw new IllegalStateException("Game is still running!");
			}
			return new GameResult(currentPlayer);
		}
		
		/**
		 * And to print the board to the console, toString is overridden 
		 * (so out.println(game); will print the game board in its current state):
		 */
		@Override
		public String toString() {
			return board.toString();
		}
	}
	
	/**
	 * The Tic Tac Toe board
	 */
	private class Board {
		private final int LENGTH = 3;
		/**
		 * Instead of your "two-dimensional" array of int, I've used the Player 
		 * enum to make the code more expressive (and to get rid of all the magic 
		 * numbers such as 500, 1000 and 50 that are so prevalent in your original 
		 * code). We keep count of the moves so far to easily tell if the board is 
		 * full without counting the full cells later.
		 */
		private final Player[][] cells = new Player[LENGTH][LENGTH];
		private int numberOfMoves = 0;
		public Board() {
			for(Player[] row : cells) {
				/**
				 * In the constructor, we fill all the rows with cells:
				 */
				Arrays.fill(row, Player.Blank);
			}
		}
		
		/**
		 * The following method is called when the player makes a move. It replaces your 
		 * three enormous if statements where the conditions spanned multiple lines. 
		 * Even in this refactored version, some complexity remains. Basically, the approach is:

			We can base our calculations on the current move,
			so we only need to examine the current column, row, diagonal and anti-diagonal.
			If any of those is long enough (3), we have a winner.
			If this makes no sense, draw the board on a piece of paper and go through it with a pencil.
		 */
		public void setCell(int x, int y, Player player) {
			cells[x][y] = player;
			numberOfMoves++;
			int row = 0;
			int column = 0;
			int diagonal = 0;
			int antiDiagonal = 0;
			for(int i = 0; i < LENGTH; i++) {
				if(cells[x][i] == player) {
					column++;
				}
				if(cells[i][y] == player) {
					row++;
				}
				if(cells[i][i] == player) {
					diagonal++;
				}
				if(cells[i][LENGTH - i - 1] == player) {
					antiDiagonal++;
				}
			}
			player.isWinner = isAnyLongEnough(row, column, diagonal, antiDiagonal);
		}
		
		private boolean isAnyLongEnough(int... combinationLengths) {
			/**
			 * The simplest way I found to find if any of the values is as long as LENGTH 
			 * is a binary search, which only works when lengths are sorted. You could use 
			 * a loop just as well.
			 */
			Arrays.sort(combinationLengths);
			return Arrays.binarySearch(combinationLengths, LENGTH) >= 0;
		}
		
		/**
		 * In game, we sometimes have to check if a cell is empty, so we check if it's on the 
		 * board and, if it is, whether it is also blank:
		 */
		private boolean isCellEmpty(int x, int y) {
			boolean isInsideBoard = x < LENGTH && y < LENGTH && x >= 0 && y >= 0;
			return isInsideBoard && cells[x][y] == Player.Blank;
		}
		
		/**
		 * After nine moves, the board is invariably full
		 */
		public boolean isFull() {
			return numberOfMoves == LENGTH * LENGTH;
		}
		
		/**
		 * Finally, we again override toString, giving us the ability to output the game board. 
		 * This is still a lot more complex than I would like. Ideally, this might be delegated 
		 * to a separate GameBoardFormatter class.
		 */
		@Override
		public String toString() {
	        final String horizontalLine = "-+-+-\n";
	        StringBuilder builder = new StringBuilder();
	        for (int row = 0; row < cells.length; row++) {
	            for (int column = 0; column < cells[row].length; column++) {
	                builder.append(cells[column][row]);
	                if (column < cells[row].length - 1)
	                    builder.append('|');
	            }
	            if (row < cells.length - 1)
	                builder.append('\n').append(horizontalLine);
	        }
	        return builder.toString();
		}
	}
	
	
	/**
	 * Helepr data structures
	 * Some Java experts and professionals will tell you that every field should 
	 * be protected by getters and setters. I disagree (and Robert C. Martin happens 
	 * to be of the same opinion): Some classes really have no significant state to 
	 * protect, so they should be classified as data structures without behaviour 
	 * and may have public fields. Others will disagree, and using getter-setter 
	 * methods is fine too
	 */
	private enum Player {
		/**
		 * Pretty straightforward: X and O will show up on the game board 
		 * correctly, so all we need to do is override toString of 
		 * Player.Blank to give us an empty space. Also, there's a 
		 * convenience method to give us the next player
		 */
		X, O, Blank {
			@Override // to give us a blank space on the board
			public String toString() {
				return " ";
			}
		};
		
		public boolean isWinner = false;
		
		public Player getNextPlayer() {
			return this == Player.X ? Player.O : Player.X;
		}
	}
	
	/**
	 * Formatting the game result
	 */
	private class GameResult {
		private final Player player;
		
		public GameResult(Player lastPlayer) {
			player = lastPlayer;
		}
		
		@Override
		public String toString() {
			String winner = player.isWinner ? player.toString() : "Nobody";
			return String.format("%s won. Thank you for playing.", winner);
		}
	}
	
	/**
	 * A data structure for Player moves
	 */
	private class Move {
		public final int X;
		public final int Y;
		
		public Move(int x, int y) {
			X = x;
			Y = y;
		}
	}
	
	
	/**
	 * The methods that are called from main all look pretty much how you expected them
	 * We simply ask the game for the current player and prompt them to make their move.
	 * Note the high level of these method calls... there's not much of an implementation 
	 * visible here, just a high level view of how the program flows. We'll keep asking 
	 * for a move until the user enters a valid one
	 */
	private static void informPlayerOfNextTurn() {
		String message = "Player %s, it's your turn, Make your move!\n";
		out.printf(message, game.getCurrentPlayer());
	}
	
	private static void makeNextMove() {
		Move move = askForMove();
		while(!game.isMoveAllowed(move)) {
			out.println("Illegal move, try again");
			move = askForMove();
		}
		game.makeMove(move);
	}
	
	/**
	 * Now, we delve a little deeper. Users aren't usually accustomed to the 
	 * programming convention of counting from zero, so we'll allow them to 
	 * enter their moves in familiar terms and subtract 1 to transform them 
	 * to an appropriate format for use with Java.
	 */
	private static Move askForMove() {
		int x = askForCoordinate("horizontal");
		int y = askForCoordinate("vertical");
		return new DesignTicTacToe().new Move(x - 1, y - 1);
	}
	
	/**
	 * Finally, we've arrived at the implementation level: We're using the Scanner 
	 * to gather some valid input.
	 */
	private static int askForCoordinate(String coordinate) {
		out.printf("Enter the %s coordinate of the cell [1-3]: ", coordinate);
		while(!in.hasNextInt()) {
			out.print("Invalid number, re-enter: ");
			in.next();
		}
		return in.nextInt();
	}
	
	
	/**
	 * A lot of effort went into transforming your original code into 
	 * this readable and expressive form. But where, you ask, has all 
	 * the code gone? Let's go step by step.
	 * Because I have split everything into separate methods that each 
	 * do one thing only, I've promoted the local variables to private 
	 * static final fields. There's your familiar Scanner, plus a 
	 * reference to System.out so I can use the short form out.println(...) 
	 * and drop the System (I find it more convenient, but go with what 
	 * you feel most comfortable with).
	 */
	public static void main(String[] args) {
		out.println("Welcome to Tic Tac Toe");
		out.println(game);
		while(game.isRunning()) {
			informPlayerOfNextTurn();
			makeNextMove();
			out.println(game);
		}
		out.println(game.getResult());
	}
 }
 
 
