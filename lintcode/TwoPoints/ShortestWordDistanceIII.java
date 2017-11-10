/**
 * Refer to
 * https://segmentfault.com/a/1190000003906667
 * This is a follow up of Shortest Word Distance. The only difference is now word1 could be the same as word2.

   Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.
   word1 and word2 may be the same and they represent two individual words in the list.
   For example, Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
   Given word1 = “makes”, word2 = “coding”, return 1. 
   Given word1 = "makes", word2 = "makes", return 3.
   Note: You may assume word1 and word2 are both in the list.
 * 
 * Solution
 * https://segmentfault.com/a/1190000003906667
 * https://discuss.leetcode.com/topic/20887/12-16-lines-java-c/8
*/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShortestWordDistanceIII {
	// Solution 1: HashMap
	public int shortestWordDistance(String[] words, String word1, String word2) {
		boolean isSame = word1.equals(word2);
		Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		for(int i = 0; i < words.length; i++) {
			if(!map.containsKey(words[i])) {
				List<Integer> list = new ArrayList<Integer>();
				list.add(i);
				map.put(words[i], list);
			} else {
				map.get(words[i]).add(i);
			}
		}
		int distance = words.length;
		if(isSame) {
			List<Integer> tmp = map.get(word1);
			int a1 = 0;
			int a2 = 1;
			if(tmp.size() >= 2) {
				while(a2 < tmp.size()) {
					distance = Math.min(distance, tmp.get(a2) - tmp.get(a1));
					a1++;
					a2++;
				}
			}
		} else {
			int a1 = 0;
			int a2 = 0;
			List<Integer> list1 = map.get(word1);
			List<Integer> list2 = map.get(word2);
			int size1 = list1.size();
			int size2 = list2.size();
			while(a1 < size1 && a2 < size2) {
				distance = Math.min(distance, Math.abs(list1.get(a1) - list2.get(a2)));
				if(list1.get(a1) < list2.get(a2)) {
					a1++;
				} else {
					a2++;
				}
			}
		}
		return distance;
	}
	
	// Solution 2: Two Points, Time Complexity O(n)
	public int shortestWordDistance2(String[] words, String word1, String word2) {	
		int p1 = -1;
		int p2 = -1;
		int distance = Integer.MAX_VALUE;
		boolean isSame = word1.equals(word2);
		for(int i = 0; i < words.length; i++) {
			if(words[i].equals(word1)) {
				p1 = i;
			} else if(words[i].equals(word2)) {
				p2 = i;
			} else {
				continue;
			}
			if(p1 >= 0 && p2 >= 0) {
				distance = Math.min(distance, Math.abs(p1 - p2));
			}
			if(isSame) {
				p2 = p1;
			}
		}
		return distance;
	}
	
	public static void main(String[] args) {
		String[] words = {"practice", "makes", "perfect", "running", "sitting", "makes", "coding", "mother", "makes"};
		String word1 = "makes";
		String word2 = "makes";
		ShortestWordDistanceIII s = new ShortestWordDistanceIII();
		int result = s.shortestWordDistance(words, word1, word2);
		System.out.println(result);
	}
}

