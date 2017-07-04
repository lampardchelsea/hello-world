import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Refer to
 * https://leetcode.com/problems/keyboard-row/#/description
 * Given a List of words, return the words that can be typed using letters of alphabet on only one 
 * row's of American keyboard like the image below. 
 * Example 1:
 * Input: ["Hello", "Alaska", "Dad", "Peace"]
 * Output: ["Alaska", "Dad"]
 * Note:
 * You may use one character in the keyboard more than once.
 * You may assume the input string will only contain letters of alphabet.
 * 
 * Solution
 * https://discuss.leetcode.com/topic/77773/short-easy-java-with-explanation
 *
 */
public class KeyboardRow {
    public String[] findWords(String[] words) {
        String[] threeRows = {"QWERTYUIOP", "ASDFGHJKL", "ZXCVBNM"};
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for(int i = 0; i < threeRows.length; i++) {
            for(int j = 0; j < threeRows[i].length(); j++) {
                map.put(threeRows[i].charAt(j), i);
            }
        }
        List<String> result = new ArrayList<String>();
        for(int i = 0; i < words.length; i++) {
            if(words[i] == "") {
                continue;
            }
            int row = map.get(words[i].toUpperCase().charAt(0));
            for(int j = 0; j < words[i].length(); j++) {
                if(map.get(words[i].toUpperCase().charAt(j)) != row) {
                    row = -1;
                    break;
                }
            }
            if(row != -1) {
                result.add(words[i]);
            }
        }
        return result.toArray(new String[0]);
    }
    
    public static void main(String[] args) {
    	
    }
}
