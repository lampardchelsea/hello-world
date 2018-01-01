import java.util.HashMap;
import java.util.Map;

/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5226206.html
 * You are playing the following Flip Game with your friend: 
 * Given a string that contains only these two characters: + and -, you and your friend take 
 * turns to flip two consecutive "++" into "--". The game ends when a person can no longer 
 * make a move and therefore the other person will be the winner.
 * 
 * Write a function to determine if the starting player can guarantee a win.
 * 
 * For example, given s = "++++", return true. The starting player can guarantee a win by 
 * flipping the middle "++" to become "+--+".
 * 
 * Follow up:
 * Derive your algorithm's runtime complexity.
 * 
 * Solution
 * https://discuss.leetcode.com/topic/27250/share-my-java-backtracking-solution
 * https://discuss.leetcode.com/topic/27250/share-my-java-backtracking-solution/5
 */
public class FlipGameII {
    // Solution 1: Naive backtracking
	  // Refer to
	  // https://discuss.leetcode.com/topic/27250/share-my-java-backtracking-solution
	  // The idea is try to replace every "++" in the current string s to "--" and see 
	  // if the opponent can win or not, if the opponent cannot win, great, we win!
    public boolean canWin(String s) {
	    if(s == null || s.length() < 2) {
	    	return false;
	    }
	    for(int i = 0; i < s.length() - 1; i++) {
	    	if(s.charAt(i) == '+' && s.charAt(i + 1) == '+') {
	    		String opponent = s.substring(0, i) + "--" + s.substring(i + 2);
	    		System.out.println("i = " + i + " || " + opponent);
	    		// If the opponent cannot win, then we win
	    		if(!canWin(opponent)) {
	    			return true;
	    		}
	    	}
	    }
	    return false;
	}
	
	// Solution 2: Backtracking + Memorization
	// Refer to
    // https://discuss.leetcode.com/topic/27250/share-my-java-backtracking-solution/10
    // If we use HashMap to memorize both win string & lose string, we can further reduce time from 208ms to 18ms
    public boolean canWin2(String s) {
		if(s == null || s.length() < 2) {
			return false;
		}
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		return helper(s, map);
	}
	
    private boolean helper(String s, Map<String, Boolean> map) {
    	if(map.containsKey(s)) {
    		System.out.println("map already contains: " + s);
    		return map.get(s);
    	}
    	for(int i = 0; i < s.length() - 1; i++) {
    		if(s.charAt(i) == '+' && s.charAt(i + 1) == '+') {
    			String opponent = s.substring(0, i) + "--" + s.substring(i + 2);
    			System.out.println("i = " + i + " || " + opponent);
    			// If the opponent cannot win, then we win
    			if(!helper(opponent, map)) {
    				map.put(s, true);
    				return true;
    			}
    		}
    	}
    	map.put(s, false);
    	return false;
    } 
    
    
	public static void main(String[] args) {
		FlipGameII f = new FlipGameII();
		String s = "+++++++--++";
//		String s = "++++";
    	System.out.println("s:       " + s);
	    boolean result = f.canWin2(s);
		System.out.print(result);
	}



}
