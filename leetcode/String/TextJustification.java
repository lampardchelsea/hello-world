
import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/text-justification/#/description
 *  Given an array of words and a length L, format the text such that each line has exactly L 
 *  characters and is fully (left and right) justified.
 *  You should pack your words in a greedy approach; that is, pack as many words as you can in each line. 
 *  Pad extra spaces ' ' when necessary so that each line has exactly L characters.
 *  Extra spaces between words should be distributed as evenly as possible. If the number of spaces on 
 *  a line do not divide evenly between words, the empty slots on the left will be assigned more spaces 
 *  than the slots on the right.
 *  For the last line of text, it should be left justified and no extra space is inserted between words.
	For example,
	words: ["This", "is", "an", "example", "of", "text", "justification."]
	L: 16.
 * Return the formatted lines as:
	[
	   "This    is    an",
	   "example  of text",
	   "justification.  "
	]

 * Note: Each word is guaranteed not to exceed L in length.
 * click to show corner cases.
 * Corner Cases:
 * A line other than the last line might contain only one word. What should you do in this case?
 * In this case, that line should be left-justified.
 *
 * Solution
 * https://discuss.leetcode.com/topic/9147/simple-java-solution/8
 * 
 */
public class TextJustification {
	public List<String> fullJustify(String[] words, int maxWidth) {
	    List<String> result = new ArrayList<String>();
	    int index = 0;
	    while(index < words.length) {
	    	// Iteratively adding word to build one line
	    	// 'count' for record current line length and compare with 'maxWidth'
	    	// 'last' for record add until which word for current line, next
	    	// outside while loop will continue build a new line based on this value
	    	int count = words[index].length();
	    	int last = index + 1;
	    	while(last < words.length) {
	    		// Plus 1 for the additional space between two words
	    		// if it is a perfect fit(no more additional spaces required)
	    		if(count + 1 + words[last].length() > maxWidth) {
	    			break;
	    		}
	    		count += 1 + words[last].length();
	    		last++;
	    	}
	    	// Build one line based on 'index' and 'last'
	    	StringBuilder sb = new StringBuilder();
	    	sb.append(words[index]);
	    	// The additional '-1' because when we break out in previous internal
	    	// while loop, 'last' actually larger than real tail index as 1
	    	// E.g As given example, 'last' = 3 when we break out as on "on" of
	    	// "This    is    an" as real tail index is 2
	    	int diff = last - index - 1;
	    	// If last line or number of words in the line is 1, left-justified
	    	if(last == words.length || diff == 0) {
	    		for(int i = index + 1; i < last; i++) {
	    			sb.append(" ");
	    			sb.append(words[i]);
	    		}
	    		for(int i = sb.length(); i < maxWidth; i++) {
	    			sb.append(" ");
	    		}
 	    	} else {
 	    		// Middle justified
 	    		int totalAdditionalSpaces = maxWidth - count;
 	    		int spaces = totalAdditionalSpaces / diff;
 	    		int remainedSpaces = totalAdditionalSpaces % diff;
 	    		// As required, if remainedSpaces exist, put onto left slots
 	    		for(int i = index + 1; i < last; i++) {
 	    			for(int k = spaces; k > 0; k--) {
 	    				sb.append(" ");
 	    			}
 	    			if(remainedSpaces > 0) {
 	    				sb.append(" ");
 	    				remainedSpaces--;
 	    			}
 	    			sb.append(" ");
 	    			sb.append(words[i]);
 	    		}
 	    	}
	    	result.add(sb.toString());
	    	index = last;
	    }
	    return result;
	}
	
	public static void main(String[] args) {
		
	}
}
