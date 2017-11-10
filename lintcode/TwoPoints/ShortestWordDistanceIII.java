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
*/

public class ShortestWordDistanceIII {
	public int shortestWordDistance(String[] words, String word1, String word2) {
		// Solution 1: HashMap 
		
		// Solution 2: Two Points
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
		String[] words = {"practice", "makes", "perfect", "running", "sitting", "makes", "coding", "makes"};
		String word1 = "makes";
		String word2 = "makes";
		ShortestWordDistanceIII s = new ShortestWordDistanceIII();
		int result = s.shortestWordDistance(words, word1, word2);
		System.out.println(result);
	}
}
