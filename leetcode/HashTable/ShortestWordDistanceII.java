/**
 * Linkedin phone interview question (by QiQi)
 *
 * Refer to
 * https://segmentfault.com/a/1190000003906667
 * This is a follow up of Shortest Word Distance. The only difference is now you are given the list of words 
   and your method will be called repeatedly many times with different parameters. How would you optimize it?

    Design a class which receives a list of words in the constructor, and implements a method that takes two words 
    word1 and word2 and return the shortest distance between these two words in the list.

    For example, Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
    Given word1 = “coding”, word2 = “practice”, return 3. 
    Given word1 = "makes", word2 = "coding", return 1.
    Note: You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
 *
 * 
 * Solution
 * https://www.youtube.com/watch?v=2MviYrr7a-k
 * https://discuss.leetcode.com/topic/20643/java-solution-using-hashmap/14
 * https://segmentfault.com/a/1190000003906667
 * https://discuss.leetcode.com/topic/20643/java-solution-using-hashmap
 * https://discuss.leetcode.com/topic/20641/java-solution-using-minimum-difference-between-2-sorted-arrays
*/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShortestDistanceWordII { 
	/**
	 * Refer to
	 * https://segmentfault.com/a/1190000003906667
	 * 哈希表法
	 * 复杂度
	 * 时间 O(N) 空间 O(N)
	 * 思路
	 * 因为会多次调用，我们不能每次调用的时候再把这两个单词的下标找出来。我们可以用一个哈希表，
	   在传入字符串数组时，就把每个单词的下标找出存入表中。这样当调用最短距离的方法时，我们只要
	   遍历两个单词的下标列表就行了。具体的比较方法，则类似merge two list，每次比较两个list
	   最小的两个值，得到一个差值。然后把较小的那个给去掉。因为我们遍历输入数组时是从前往后的，
	   所以下标列表也是有序的。
	*/
	Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
	public ShortestDistanceWordII(String[] words) {
		for(int i = 0; i < words.length; i++) {
			if(!map.containsKey(words[i])) {
				List<Integer> list = new ArrayList<Integer>();
				list.add(i);
				map.put(words[i], list);
			} else {
				map.get(words[i]).add(i);
			}			
		}
	}
	
	
	// Solution 1: HashMap with O(m * n) Time Complexity
	// https://www.youtube.com/watch?v=2MviYrr7a-k
	// https://discuss.leetcode.com/topic/20643/java-solution-using-hashmap/14
	public int shortest(String word1, String word2) {
	    int min = Integer.MAX_VALUE;
	    for(Integer num1: map.get(word1)) {
		for(Integer num2: map.get(word2)) {
		    min = Math.min(min, Math.abs(num1 - num2));
		}
	    }
	    return min;
	}
		
        // Solution 2: HashMap with O(m + n) Time Complexity
	// Refer to
	// https://segmentfault.com/a/1190000003906667
	// https://discuss.leetcode.com/topic/20643/java-solution-using-hashmap
	// https://discuss.leetcode.com/topic/20641/java-solution-using-minimum-difference-between-2-sorted-arrays
	public int shortest(String word1, String word2) {
		List<Integer> list1 = map.get(word1);
		List<Integer> list2 = map.get(word2);
		int p1 = 0;
		int p2 = 0;
		int result = Integer.MAX_VALUE;
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
		String[] strings = {"practice", "makes", "perfect", "coding", "makes"};
		ShortestDistanceWordII s = new ShortestDistanceWordII(strings);
		String word1 = "coding";
		//String word2 = "practice";
		String word2 = "makes";
		int result = s.shortest(word1, word2);
		System.out.println(result);
	}
}

