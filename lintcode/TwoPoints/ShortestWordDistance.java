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
 * https://discuss.leetcode.com/topic/20641/java-solution-using-minimum-difference-between-2-sorted-arrays
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
	// Refer to
	// https://segmentfault.com/a/1190000003906667
	// 复杂度
	// 时间 O(N) 空间 O(1)
	// 思路
	// 一个指针指向word1上次出现的位置，一个指针指向word2上次出现的位置。因为两个单词如果比较接近的话，
	// 肯定是相邻的word1和word2的位置之差，所以我们只要每次得到一个新位置和另一个单词的位置比较一下就行了。
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
		// Only when pointer a or b changed we update result, because if either a
		// or b or both keep on original position, the new calculated result will
		// surely >= previous calculated result
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
	
        // Solution 4: Java solution using minimum difference between 2 sorted arrays
	// Refer to
	// https://discuss.leetcode.com/topic/20641/java-solution-using-minimum-difference-between-2-sorted-arrays
	// The part of finding minimum difference between two sorted lists is interesting :-) 
	// It can be used to solve Shortest Word Distance II.
	public int shortestDistance4(String[] words, String word1, String word2) {
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();
		for(int i = 0; i < words.length; i++) {
			if(words[i].equals(word1)) {
				list1.add(i);
			} else if(words[i].equals(word2)) {
				list2.add(i);
			}
		}
		int result = words.length;
		int p1 = 0;
		int p2 = 0;
		while(p1 < list1.size() && p2 < list2.size()) {
			result = Math.min(result, Math.abs(list1.get(p1) - list2.get(p2)));
			if(list1.get(p1) < list2.get(p2)) {
				p1++;
			} else {
				p2++;
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		ShortestWordDistance s = new ShortestWordDistance();
		String[] words = {"practice", "makes", "perfect", "coding", "makes"};
		String word1 = "coding";
		//String word2 = "practice";
		String word2 = "makes";
		int result = s.shortestDistance3(words, word1, word2);
		System.out.println(result);
	}
}

