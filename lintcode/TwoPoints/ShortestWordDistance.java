/**
 * Refer to
 * https://segmentfault.com/a/1190000003906667
 * Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.
    For example, Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
    Given word1 = “coding”, word2 = “practice”, return 3. Given word1 = "makes", word2 = "coding", return 1.
    Note: You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
 *
 * Solution
 * https://www.youtube.com/watch?v=N0j3Sc_3wB4
 * https://segmentfault.com/a/1190000003906667
 * https://discuss.leetcode.com/topic/20668/ac-java-clean-solution/11?page=1
*/

package test;

public class ShortestWordDistance {
	// Solution 1: 
	// Time Complexity: O(n^2)
	public int shortestDistance(String[] words, String word1, String word2) {
		if(words == null || words.length == 0) {
			return 0;
		}
		int result = words.length;
		for(int i = 0; i < words.length; i++) {
			for(int j = 0; j < words.length; j++) {
				if(words[i].equals(word1) && words[j].equals(word2)) {
					result = Math.min(result, Math.abs(i - j));
				}
			}
		}
		return result;
	}
	
	// Solution 2:
	// Time Complexity: O(n)
	public int shortestDistance2(String[] words, String word1, String word2) {
		if(words == null || words.length == 0) {
			return 0;
		}
		int result = words.length;
		// Initial pointer a, b as -1 based on words may not contain word1 or word2
		int a = -1;
		int b = -1;
		for(int i = 0; i < words.length; i++) {
			if(words[i].equals(word1)) {
				a = i;
			} else if(words[i].equals(word2)) {
				b = i;
			}
			// If word1 and word2 both exist in words, then recursively compute
			// its minimum position difference
			if(a != -1 && b != -1) {
				result = Math.min(result, Math.abs(a - b));	
			}
		}
		return result;
	}
	
	// Solution 3: Optimization on Solution 2
	// Refer to
	// https://discuss.leetcode.com/topic/20668/ac-java-clean-solution/11?page=1
	// add a boolean to check if position has changed to save some extra time
	public int shortestDistance3(String[] words, String word1, String word2) {
		if(words == null || words.length == 0) {
			return 0;
		}
		int result = words.length;
		// Initial pointer a, b as -1 based on words may not contain word1 or word2
		int a = -1;
		int b = -1;
		boolean changed = false;
		for(int i = 0; i < words.length; i++) {
			if(words[i].equals(word1)) {
				a = i;
				changed = true;
			} else if(words[i].equals(word2)) {
				b = i;
				changed = true;
			}
			// If word1 and word2 both exist in words, then recursively compute
			// its minimum position difference
			if(changed && a != -1 && b != -1) {
				result = Math.min(result, Math.abs(a - b));
				changed = false;
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		ShortestWordDistance s = new ShortestWordDistance();
		String[] words = {"practice", "makes", "perfect", "coding", "makes"};
		String word1 = "coding";
		String word2 = "practice";
		//String word2 = "makes";
		int result = s.shortestDistance3(words, word1, word2);
		System.out.println(result);
	}
}

