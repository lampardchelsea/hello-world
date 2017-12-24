import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Refer to
 * https://leetcode.com/articles/palindrome-permutation/
 * Given a string, determine if a permutation of the string could form a palindrome.

	For example,
	"code" -> False, "aab" -> True, "carerac" -> True.
 * 
 * Solution
 * https://leetcode.com/articles/palindrome-permutation/
 * https://www.youtube.com/watch?v=4TjNTeX4yyw
 *
 */

public class PalindromePermutation {
// Solution 1: HashMap
	/**
	 * Complexity Analysis
	 * Time complexity : O(n). We traverse over the given string s with n characters once. 
	 *                         We also traverse over the map which can grow up to a size of n 
	 *                         in case all characters in s are distinct.
	 * Space complexity : O(n). The hashmap can grow up to a size of n, in case all the 
	 *                          characters in s are distinct.
	 */
	public boolean canPermutePalindrome(String s) {
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(map.containsKey(c)) {
				map.put(c, map.get(c) + 1);
			} else {
				map.put(c, 1);
			}
		}
		
		int oddCount = 0;
		for(Map.Entry<Character, Integer> entry : map.entrySet()) {
			if(entry.getValue() % 2 == 1) {
				oddCount++;
			}
			if(oddCount > 1) {
				return false;
			}
		}
		
		return true;
	}
	
	
	// Solution 2: Set
	/**
	 *  Complexity Analysis
	 *  Time complexity : O(n). We traverse over the string s of length n once only.
	 *  Space complexity : O(n). The set can grow up to a maximum size of n in case of all distinct elements.
	 */
	public boolean canPermutePalindrome2(String s) {
		Set<Character> set = new HashSet<Character>();
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(set.contains(c)) {
				set.remove(c);
			} else {
				set.add(c);
			}
		}
		return set.size() <= 1;
	}
	
	
	// Solution 3: Array
	/**
	 * Complexity Analysis
	 * Time complexity : O(n). We traverse once over the string s of length n. 
	 *                   Then, we traverse over the map of length 128(constant).
	 * Space complexity : O(1). Constant extra space is used for map of size 128.
	 * 
	 */
	public boolean canPermutePalindrome3(String s) {
		// Each index of this mapmap corresponds to one of the 128 ASCII characters possible
		int[] map = new int[128];
		for(int i = 0; i < s.length(); i++) {
			map[s.charAt(i)]++;
		}
		int count = 0;
		for(int j = 0; j < map.length && count <= 1; j++) {
			count += map[j] % 2;
		}
		return count <= 1;
	}	
	
	
	public static void main(String[] args) {
		PalindromePermutation p = new PalindromePermutation();
		String s = "abb";
		boolean result = p.canPermutePalindrome3(s);
		System.out.println(result);
	}

}
