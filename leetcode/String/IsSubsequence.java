import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Refer to
 * https://leetcode.com/problems/is-subsequence/#/description
 *  Given a string s and a string t, check if s is subsequence of t.
 *  You may assume that there is only lower case English letters in both s and t. 
 *  t is potentially a very long (length ~= 500,000) string, and s is a short string (<=100).
 *  A subsequence of a string is a new string which is formed from the original string by 
 *  deleting some (can be none) of the characters without disturbing the relative positions 
 *  of the remaining characters. (ie, "ace" is a subsequence of "abcde" while "aec" is not).
 *  
	Example 1:
	s = "abc", t = "ahbgdc"
	Return true.
	
	Example 2:
	s = "axc", t = "ahbgdc"
	Return false.

 * Follow up:
 * If there are lots of incoming S, say S1, S2, ... , Sk where k >= 1B, and you want to check one 
 * by one to see if T has its subsequence. In this scenario, how would you change your code?
 * 
 * 
 * Solution
 * https://discuss.leetcode.com/topic/57147/straight-forward-java-simple-solution
 * 
 * https://discuss.leetcode.com/topic/58367/binary-search-solution-for-follow-up-with-detailed-comments
 * 
 * https://discuss.leetcode.com/topic/67167/java-code-for-the-follow-up-question
 * 
 */
public class IsSubsequence {
	// Solution 1: Two Pointers
    public boolean isSubsequence(String s, String t) {
        int i = 0;
        int j = 0;
        int count = 0;
        while(i < s.length() && j < t.length()) {
            if(s.charAt(i) == t.charAt(j)) {
                count++;
                i++;
            }
            j++;
        }
        if(count == s.length()) {
            return true;
        }
        return false;
    }
    
    // Solution 2: Map and Binary Search
    /**
     * Follow-up
     * If we check each sk in this way, then it would be O(kn) time where k is the number 
     * of s and t is the length of t. 
     * This is inefficient. 
     * Since there is a lot of s, it would be reasonable to preprocess t to generate something 
     * that is easy to search for if a character of s is in t. 
     * Sounds like a HashMap, which is super suitable for search for existing stuff. 
     */
    public boolean isSubsequence2(String s, String t) {
    	if(s == null || t == null) {
    		return false;
    	}
    	// Construct the map based on t, each entry represent certain char and its indexes
        // Refer to
    	// https://discuss.leetcode.com/topic/58367/binary-search-solution-for-follow-up-with-detailed-comments
    	// There is another improvement point as we can use ASCII array such as int[] table = new int[256]
    	// instead of map
    	Map<Character, List<Integer>> map = new HashMap<Character, List<Integer>>();
    	for(int i = 0; i < t.length(); i++) {
    		char curr = t.charAt(i);
    		if(!map.containsKey(curr)) {
    		    map.put(curr, new ArrayList<Integer>());
    		}
    		map.get(curr).add(i);
    	}
    	/**
    	 * Refer to
    	 * https://discuss.leetcode.com/topic/58367/binary-search-solution-for-follow-up-with-detailed-comments/5
    	 * The prev variable is an index where previous character was picked from the sequence. 
    	 * And for the next character to be picked, you have to select it only after this index 
    	 * is the string 'T'.
			For instance, if S = "abcd" and T = "abdced".
			The index list mapping looks like,
			a -> 0
			b -> 1
			c -> 3
			d -> 2,5
			e -> 4
         * After you pick a, and b, c will be picked, and index is 3. Now if you have to pick d, 
         * you can't pick index 2 because c was picked at 3, so you have to binary search for index 
         * which comes after 3. So it returns 5.
    	 */
    	int prev = -1;
    	for(int i = 0; i < s.length(); i++) {
    		char c = s.charAt(i);
    		if(map.get(c) == null) {
    			return false;
    		} else {
    			List<Integer> list = map.get(c);
    			prev = binarySearchHelper(prev, list, 0, list.size() - 1);
    			if(prev == -1) {
    				return false;
    			}
    			prev++;
    		}
    	}
    	return true;
    }
    
    // Refer to
    // https://discuss.leetcode.com/topic/67167/java-code-for-the-follow-up-question/2
    // Q: From what I understand, your helper function binarySearch finds the first element of list 
    //    that is greater than or equal to the index that is passed in. Is my understanding correct?
    // 
    // https://discuss.leetcode.com/topic/67167/java-code-for-the-follow-up-question/3
    // A: Yes. You are right. Since we always need to move forward in t regarding the index, thus we 
    //    have to find out the very first character in t that is the same with the current character 
    //    we are considering in s. Then we set our prev (which is the start index of substring in t) 
    //    to prev++.
    public int binarySearchHelper(int index, List<Integer> list, int start, int end) {
    	while(start <= end) {
    		int mid = start + (end - start) / 2;
    		if(list.get(mid) < index) {
    			start = mid + 1;
    		} else {
    			end = mid - 1;
    		}
    	}
    	// start == list.size() means search to the end of range, because real range on list is 
    	// [0, list.size() - 1], e.g if s = "abb", t = "abcd", the 2nd 'b' in s will encounter
    	// case as start == list.size()
    	// if not out of range, then get the correspond value(very first
    	// character's index after prev)
    	return start == list.size() ? -1 : list.get(start);
    }
    
    public static void main(String[] args) {
    	IsSubsequence a = new IsSubsequence();
    	String s = "abb";
    	String t = "abcd";
    	boolean result = a.isSubsequence2(s, t);
    	System.out.println(result);
    }
}

