import java.util.HashMap;
import java.util.Map;

/**
 * Refer to
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/#/description
 * 
 * Solution
 * https://discuss.leetcode.com/topic/8232/11-line-simple-java-solution-o-n-with-explanation
 * The basic idea is, keep a hashmap which stores the characters 
 * in string as keys and their positions as values, and keep two 
 * pointers which define the max substring. move the right pointer 
 * to scan through the string , and meanwhile update the hashmap. 
 * If the character is already in the hashmap, then move the left 
 * pointer to the right of the same character last found. 
 * Note that the two pointers can only move forward.
 */
public class LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        if(s.length() == 0) {
            return 0;
        }
        int maxLen = 0;
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        int newStart = 0;
        for(int i = 0; i < s.length(); i++) {
            if(map.containsKey(s.charAt(i))) {
//                newStart = map.get(s.charAt(i)) + 1;
            	// Very important !!!
            	// Must compare the current newStart with updated next position of
            	// char which encounter again, and only keep the larger one
            	// e.g given "abba", the newStart first because of encounter
            	// 'b' again will assign as 2, but next time, when encounter 
            	// 'a' again, if not select the larger value between that two
            	// values, it will drop back to 1, which based only on next
            	// position of char 'a' which encounter again, but actually
            	// the larger newStart value should come from previous newStart
            	// value which encounter 'b' again.
            	newStart = Math.max(newStart, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            maxLen = Math.max(maxLen, i - newStart + 1);
        }
        return maxLen;
    }
    
    public static void main(String[] args) {
    	LongestSubstringWithoutRepeatingCharacters x = new LongestSubstringWithoutRepeatingCharacters();
    	String s = "abba";
    	int result = x.lengthOfLongestSubstring(s);
    	System.out.println(result);
    }
}

