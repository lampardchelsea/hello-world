import java.util.ArrayList;
import java.util.List;

/**
 * You are playing the following Flip Game with your friend: 
 * Given a string that contains only these two characters: + and -, you and your friend 
 * take turns to flip two consecutive "++" into "--". The game ends when a person can no 
 * longer make a move and therefore the other person will be the winner.
 * Write a function to compute all possible states of the string after one valid move.
 * 
 * For example, given s = "++++", after one move, it may become one of the following states:

	[
	  "--++",
	  "+--+",
	  "++--"
	]
 
 * If there is no valid move, return an empty list [].
*/

// Refer to
// http://www.cnblogs.com/grandyang/p/5224896.html
// http://www.programcreek.com/2014/04/leetcode-flip-game-java/
public class FlipGame {
	public List<String> generatePossibleNextMoves(String s) {
		List<String> result = new ArrayList<String>();
		char[] c = s.toCharArray();
		int i = 0;
		for(; i < s.length() - 1; i++) {
			if(c[i] == '+' && c[i + 1] == '+') {
				// Change current '++' to '--'
				c[i] = '-';
				c[i + 1] = '-';
				// Add changed string into result
				result.add(new String(c));
				// Because only collect result after 1 change
				// need to change back to '++' and move to
				// next step
				c[i] = '+';
				c[i + 1] = '+';
 			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		FlipGame fg = new FlipGame();
		String s = "++--++";
		List<String> result = fg.generatePossibleNextMoves(s);
		System.out.println(result.toString());
	}
}

