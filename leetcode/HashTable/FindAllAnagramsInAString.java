/**
 * Given a string s and a non-empty string p, find all the start indices of p's anagrams in s.
 * Strings consists of lowercase English letters only and the length of both strings s and p will not be larger than 20,100.
 * The order of output does not matter.
 * Example 1:
 * Input: s: "cbaebabacd" p: "abc"
 * Output: [0, 6]
 * Explanation:
 * The substring with start index = 0 is "cba", which is an anagram of "abc".
 * The substring with start index = 6 is "bac", which is an anagram of "abc".
 * 
 * Example 2:
 * Input: s: "abab" p: "ab"
 * Output: [0, 1, 2]
 * Explanation:
 * The substring with start index = 0 is "ab", which is an anagram of "ab".
 * The substring with start index = 1 is "ba", which is an anagram of "ab".
 * The substring with start index = 2 is "ab", which is an anagram of "ab".
*/
// Solution 1: Time Exceeded
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindAllAnagramsOfAString {
    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<Integer>();
        for(int i = 0; i <= s.length() - p.length(); i++) {
        	String temp = s.substring(i, i + p.length());
        	if(isAnagram(temp, p)) {
        		result.add(i);
        	}
        }
        return result;
    }
	
    public static boolean isAnagram(String temp, String p) {
    	char[] a = temp.toCharArray();
    	char[] b = p.toCharArray();
    	Arrays.sort(a);
    	Arrays.sort(b);
    	for(int i = 0; i < a.length; i++) {
    		if(a[i] != b[i]) {
    			return false;
    		}
    	}
    	return true;
    }
    
    
	public static void main(String[] args) {
		// s: "cbaebabacd" p: "abc"
		String s = "cbaebabacd";
		String p = "abc";
//		String s = "abab";
//		String p = "ab";
		List<Integer> result = findAnagrams(s, p);
		System.out.println(result);
	}
}
