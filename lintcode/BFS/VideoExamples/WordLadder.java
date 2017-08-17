import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Refer to
 * http://www.lintcode.com/en/problem/word-ladder/
 * Given two words (start and end), and a dictionary, find the length of shortest 
 * transformation sequence from start to end, such that:
	Only one letter can be changed at a time
	Each intermediate word must exist in the dictionary
	 Notice
	
	Return 0 if there is no such transformation sequence.
	All words have the same length.
	All words contain only lowercase alphabetic characters.
	Have you met this question in a real interview? Yes
	Example
	Given:
	start = "hit"
	end = "cog"
	dict = ["hot","dot","dog","lot","log"]
	As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
	return its length 5.
 * 
 * Solution
 * http://www.jiuzhang.com/solutions/word-ladder/
 * 
 */
public class WordLadder {
	public int ladderLength(String start, String end, List<String> wordList) {
		Set<String> dict = new HashSet<String>();
		for(String word : wordList) {
			dict.add(word);
		}
		if(start.equals(end)) {
			return 1;
		}
		// HashSet used to avoid duplicate transformation into already
		// existing steps
		Set<String> hash = new HashSet<String>();
		Queue<String> queue = new LinkedList<String>();
		hash.add(start);
		queue.offer(start);
		int length = 1;
		while(!queue.isEmpty()) {
			length++;
			// Use level traverse to calculate how many steps we need,
			// one level means one step
			int size = queue.size();
			for(int i = 0; i < size; i++) {
				String word = queue.poll();
				for(String nextWord : getNextWords(word, dict)) {
					// If already existing in previous step
					// which record by HashSet
					if(hash.contains(nextWord)) {
						continue;
					}
					if(nextWord.equals(end)) {
						return length;
					}
				    hash.add(nextWord);
				    queue.offer(nextWord);
				}
			}
		}
		return 0;
	}
	
	private String replace(String s, int index, char c) {
		char[] chars = s.toCharArray();
		chars[index] = c;
		return new String(chars);
	}
	
	private List<String> getNextWords(String word, Set<String> dict) {
		List<String> nextWords = new ArrayList<String>();
		// The best strategy here is not change one character of one word
		// in 'dict' and compare with given 'word', e.g if we have 10k words
		// in 'dict' and every word length is L, it will cost O(10Lk) time
		// complexity, the better way is just change 1 char 1 time in a
		// word and search it with HashSet, e.g if we still have 10k words
		// in a HashSet, then only need 25('a' -- 'z') * L^2 = O(25L^2) time
		// complexity, if L = 10, it apparently O(2.5k) < O(100k)
		for(char c = 'a'; c <= 'z'; c++) { // O(25)
			for(int i = 0; i < word.length(); i++) { // O(L)
				if(c == word.charAt(i)) {
					continue;
				}
				String nextWord = replace(word, i, c);
				if(dict.contains(nextWord)) {
					nextWords.add(nextWord);
				}
			}
		}
		return nextWords;
	}

}

