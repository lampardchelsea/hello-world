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
 *
 * https://leetcode.com/articles/longest-substring-without-repeating-characters/
 */

// Solution 1:
class Solution {
    /**
     Approach #1 Brute Force [Time Limit Exceeded]
     Intuition
     Check all the substring one by one to see if it has no duplicate character.
     
     Algorithm
     Suppose we have a function boolean allUnique(String substring) which will return 
     true if the characters in the substring are all unique, otherwise false. We can 
     iterate through all the possible substrings of the given string s and call the 
     function allUnique. If it turns out to be true, then we update our answer of the 
     maximum length of substring without duplicate characters.
     
     Now let's fill the missing parts:
     To enumerate all substrings of a given string, we enumerate the start and end 
     indices of them. Suppose the start and end indices are i and j, respectively. 
     Then we have n0 ≤ i < j ≤ n (here end index j is exclusive by convention). 
     Thus, using two nested loops with i from 0 to n−1 and j from i+1 to n, we can 
     enumerate all the substrings of s.
     
     To check if one string has duplicate characters, we can use a set. We iterate 
     through all the characters in the string and put them into the set one by one. 
     Before putting one character, we check if the set already contains it. If so, 
     we return false. After the loop, we return true.
     
     Complexity Analysis
     Time complexity : O(n^3)
     Space complexity : O(min(n,m)). We need O(k) space for checking a substring 
                        has no duplicate characters, where k is the size of the Set. 
                        The size of the Set is upper bounded by the size of the 
                        string n and the size of the charset/alphabet m.
    */
    public int lengthOfLongestSubstring(String s) {
        if(s == null || s.length() == 0) {
            return 0;
        }
        int result = 0;
        int n = s.length();
        for(int i = 0; i < n; i++) {
            // Note: The char at index 'j' exclusive in substring
            // based on String.substring() method, and substring length
            // is (j - i), not (j - i + 1) as char at 'j' not include
            for(int j = 1; j <= n; j++) {
                if(allUnique(s, i, j)) {
                    result = Math.max(result, j - i);
                }
            }
        }
        return result;
    }
    
    private boolean allUnique(String s, int start, int end) {
        Set<Character> set = new HashSet<Character>();
        for(int i = start; i < end; i++) {
            Character c = s.charAt(i);
            if(set.contains(c)) {
                return false;
            }
            set.add(c);
        }
        return true;
    }
}



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

