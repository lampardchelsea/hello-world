import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Refer to
 * https://discuss.leetcode.com/topic/40657/150-ms-45-lines-java-solution
 * https://leetcode.com/problems/palindrome-pairs/#/description
 *  Given a list of unique words, find all pairs of distinct indices (i, j) in the 
 *  given list, so that the concatenation of the two words, i.e. words[i] + words[j] is a palindrome.
	Example 1:
	Given words = ["bat", "tab", "cat"]
	Return [[0, 1], [1, 0]]
	The palindromes are ["battab", "tabbat"]
	
	Example 2:
	Given words = ["abcd", "dcba", "lls", "s", "sssll"]
	Return [[0, 1], [1, 0], [3, 2], [2, 4]]
	The palindromes are ["dcbaabcd", "abcddcba", "slls", "llssssll"]
 * 
 * Solution 1
 * http://blog.csdn.net/qq508618087/article/details/51443809
 * 思路: 将所有的单词逆序加入hash表中, 然后再遍历一遍数组, 然后会有两种情况
 * 1. 将单词的前一部分如果可以在hash表中找到匹配说明这部分是可以回文的, 如果这个单词剩下的部分也是回文, 那么这两个单词就可以配成回文. 
 *    例如aabbcc和bbaa, 其中bbaa在hash表中是以逆序存在的, 即aabb, 那么当我们遍历到aabbcc的时候其前半部分aabb可以在hash表中查到, 
 *    并且剩余部分cc是回文, 因此他们可以构成回文
 * 2. 如果单词的后一部分可以在hash表中查到, 并且其前一部分是回文, 他们也可以构成匹配. 例如aabb和ccbbaa, 其中aabb在hash表中是
 *    以bbaa存在的. 当我们遍历到ccbbaa的时候, 其后一部分bbaa可以在hash表中查到存在, 并且其前一部分cc是回文, 因此他们也可以构成回文.
 * 基本的思路就是这样, 但是有一些特殊的case,
 * 1. 要防止与其本身进行匹配
 * 2. 当存在空串时, 就会复杂一些, 比如["a", ""], 这种情况应该输出[[0, 1] [1, 0]]. 因此空串也应该在作为其前缀和后缀进行检测. 
 *    但是这种情况又会引起另外的问题, 例如aabb和bbaa, 当我们将单词划分为左右两部分并且其前半部分为空串或这后半部分为空串时都会匹配, 
 *    也就是遍历到aabb时会产出两个匹配, 即aabb作为前缀, 空串作为后缀 和空串作为前缀, aabb作为后缀时. 而遍历到单词bbaa时又会重复匹配一次, 
 *    因此我们需要引入另外一个条件, 即在分别判断将当前单词的前一部分作为前缀和后一部分作为后缀的时候, 其前缀和后缀不能同时等于单词本身.
 *
 */
public class PalindromePairs {
	public List<List<Integer>> palindromePairs(String[] words) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		if(words == null || words.length < 2) {
			return result;
		}
		// Insert all words onto map and record its index in array
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(int i = 0; i < words.length; i++) {
			map.put(words[i], i);
		}
		for(int i = 0; i < words.length; i++) {
			// Must '<=', otherwise will miss special case as empty 
			// string "" happen in words
			for(int j = 0; j <= words[i].length(); j++) {
				String str1 = words[i].substring(0, j);
				String str2 = words[i].substring(j);
				if(isPalindrome(str1)) {
					String str2rvs = reverseString(str2);
					if(map.containsKey(str2rvs) && map.get(str2rvs) != i) {
						List<Integer> list = new ArrayList<Integer>();
						list.add(map.get(str2rvs));
						list.add(i);
						result.add(list);
					}
				}
				if(isPalindrome(str2)) {
					String str1rvs = reverseString(str1);
					// Since we now use <= in for (int j = 0; j <= words[i].length(); j++) instead of <. 
					// There may be duplicates in the output (consider test case ["abcd", "dcba"]). 
					// Therefore put a str2.length() != 0 to avoid duplicates. (which means str1 and
					// str2 should not same as original string at same time)
					if(map.containsKey(str1rvs) && map.get(str1rvs) != i && str2.length() != 0) {
						List<Integer> list = new ArrayList<Integer>();
						// Also, the position adding into list order should not same ?
						// Refer to
						// https://discuss.leetcode.com/topic/40657/150-ms-45-lines-java-solution/22?page=2
						// otherwise will given same order combination in result
						// E.g if given ["a", ""], result will [[0, 1], [0, 1]], but what we expected
						// is [[0, 1], [1, 0]]
						list.add(i);
						list.add(map.get(str1rvs));
						//list.add(i);
						result.add(list);
					}
				}
			}
		}
		return result;
	}

	public String reverseString(String str) {
	    char[] in = str.toCharArray();
	    int begin=0;
	    int end=in.length-1;
	    char temp;
	    while(end>begin){
	        temp = in[begin];
	        in[begin]=in[end];
	        in[end] = temp;
	        end--;
	        begin++;
	    }
	    return new String(in);
	}
	
	private boolean isPalindrome(String str) {
	    int left = 0;
	    int right = str.length() - 1;
	    while (left <= right) {
	        if (str.charAt(left++) !=  str.charAt(right--)) return false;
	    }
	    return true;
	}

	public static void main(String[] args) {
		PalindromePairs p = new PalindromePairs();
		String[] words = {"a", ""};
		List<List<Integer>> result = p.palindromePairs(words);
		for(List<Integer> a : result) {
			System.out.println("------------");
			for(Integer b : a) {
				System.out.println(b);
			}
		}
	}
}











































































https://leetcode.com/problems/palindrome-pairs/

You are given a 0-indexed array of unique strings words.

A palindrome pair is a pair of integers (i, j) such that:

- 0 <= i, j < words.length,
- i != j, and
- words[i] + words[j] (the concatenation of the two strings) is a
  palindrome
  .
Return an array of all the palindrome pairs of words.

Example 1:
```
Input: words = ["abcd","dcba","lls","s","sssll"]
Output: [[0,1],[1,0],[3,2],[2,4]]
Explanation: The palindromes are ["abcddcba","dcbaabcd","slls","llssssll"]
```

Example 2:
```
Input: words = ["bat","tab","cat"]
Output: [[0,1],[1,0]]
Explanation: The palindromes are ["battab","tabbat"]
```

Example 3:
```
Input: words = ["a",""]
Output: [[0,1],[1,0]]
Explanation: The palindromes are ["a","a"]
```

Constraints:
- 1 <= words.length <= 5000
- 0 <= words[i].length <= 300
- words[i] consists of lowercase English letters.
---
Attempt 1: 2023-06-28

Solution 1: Hash Table (60 min)
```
class Solution {
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        // {key, val} -> {word, index}
        Map<String, Integer> map = new HashMap<String, Integer>();
        for(int i = 0; i < words.length; i++) {
            map.put(words[i], i);
        }


        // Special cases: "" can be combine with any palindrome string
        if(map.containsKey("")) {
            int emptyStrIndex = map.get("");
            for(int i = 0; i < words.length; i++) {
                if(i != emptyStrIndex && isPalindrome(words[i])) {
                    result.add(Arrays.asList(emptyStrIndex, i));
                    result.add(Arrays.asList(i, emptyStrIndex));
                }
            }
        }


        // Find all string and reverse string pairs
        for(int i = 0; i < words.length; i++) {
            String reverse_str = reverse(words[i]);
            if(map.containsKey(reverse_str)) {
                int found = map.get(reverse_str);
                // If "found == i" means current word itself is a palindrome,
                // but without compensate word, we cannot use it as a pair
                if(found != i) {
                    // We don't have to add reversed index pair{found, i} again
                    // because when traversal for loop of index till 'found',
                    // we will automatically add pair{found, i}
                    // e.g  
                    // Input: words = ["abcd","dcba","lls","s","sssll"]
                    // Output: [0,1],[1,0]
                    // when i = 0, we get found = 1, we add pair {0, 1} first
                    // when i = 1, we get found = 0, we add pair {1, 0} then
                    // no need to add {1, 0} when only traverse till index = 0
                    result.add(Arrays.asList(i, found));
                }
            }
        }


        // Find the pair s1, s2 cut cases, 'cut' start from 1
        for(int i = 0; i < words.length; i++) {
            String word = words[i];
            for(int cut = 1; cut < word.length(); cut++) {
                // Case1 : s1[0:cut) is palindrome and s1[cut:] = reverse(s2) => (s2, s1)
                // e.g
                // Input: words = ["abcd","dcba","lls","s","sssll"]
                // Output: [3,2] -> s2("s") + s1("lls") = "slls"
                //         [2,4] -> s2("lls") + s1("sssll") = "llssssll"
                if(isPalindrome(word.substring(0, cut))) {
                    String reverse_cut = reverse(word.substring(cut));
                    if(map.containsKey(reverse_cut)) {
                        int found = map.get(reverse_cut);
                        if(found != i) {
                            // s2 index is 'found' must before s1 index 'i'
                            result.add(Arrays.asList(found, i));
                        }
                    }
                }
                // Case2 : s1[cut:] is palindrome and s1[0:cut) = reverse(s2) => (s1, s2)
                // Input: words = ["esll","se","sssll"]
                // Output: [0,1] -> s1("esll") + s2("se") = "esllse"
                if(isPalindrome(word.substring(cut))) {
                    String reverse_cut = reverse(word.substring(0, cut));
                    if(map.containsKey(reverse_cut)) {
                        int found = map.get(reverse_cut);
                        if(found != i) {
                            // s2 index is 'found' must after s1 index 'i'
                            result.add(Arrays.asList(i, found));
                        }
                    }
                }
            }
        }
        return result;
    }



    private String reverse(String s) {
        StringBuilder sb = new StringBuilder(s);
        return sb.reverse().toString();
    }



    private boolean isPalindrome(String s) {
        int i = 0;
        int j = s.length() - 1;
        while(i < j) {
            if(s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}
```

Refer to
https://leetcode.com/problems/palindrome-pairs/solutions/79210/the-easy-to-unserstand-java-solution/
There are several cases to be considered that isPalindrome(s1 + s2):
Case1: If s1 is a blank string, then for any string that is palindrome s2, s1+s2 and s2+s1 are palindrome.
Case 2: If s2 is the reversing string of s1, then s1+s2 and s2+s1 are palindrome.
Case 3: If s1[0:cut] is palindrome and there exists s2 is the reversing string of s1[cut+1:] , then s2+s1 is palindrome.
Case 4: Similiar to case3. If s1[cut+1: ] is palindrome and there exists s2 is the reversing string of s1[0:cut] , then s1+s2 is palindrome.

To make the search faster, build a HashMap to store the String-idx pairs.

My code:
```
public class Solution {
public List<List<Integer>> palindromePairs(String[] words) {
    List<List<Integer>> res = new ArrayList<List<Integer>>();
    if(words == null || words.length == 0){
        return res;
    }
    //build the map save the key-val pairs: String - idx
    HashMap<String, Integer> map = new HashMap<>();
    for(int i = 0; i < words.length; i++){
        map.put(words[i], i);
    }
    
    //special cases: "" can be combine with any palindrome string
    if(map.containsKey("")){
        int blankIdx = map.get("");
        for(int i = 0; i < words.length; i++){
            if(isPalindrome(words[i])){
                if(i == blankIdx) continue;
                res.add(Arrays.asList(blankIdx, i));
                res.add(Arrays.asList(i, blankIdx));
            }
        }
    }
    
    //find all string and reverse string pairs
    for(int i = 0; i < words.length; i++){
        String cur_r = reverseStr(words[i]);
        if(map.containsKey(cur_r)){
            int found = map.get(cur_r);
            if(found == i) continue;
            res.add(Arrays.asList(i, found));
        }
    }
    
    //find the pair s1, s2 that 
    //case1 : s1[0:cut] is palindrome and s1[cut+1:] = reverse(s2) => (s2, s1)
    //case2 : s1[cut+1:] is palindrome and s1[0:cut] = reverse(s2) => (s1, s2)
    for(int i = 0; i < words.length; i++){
        String cur = words[i];
        for(int cut = 1; cut < cur.length(); cut++){
            if(isPalindrome(cur.substring(0, cut))){
                String cut_r = reverseStr(cur.substring(cut));
                if(map.containsKey(cut_r)){
                    int found = map.get(cut_r);
                    if(found == i) continue;
                    res.add(Arrays.asList(found, i));
                }
            }
            if(isPalindrome(cur.substring(cut))){
                String cut_r = reverseStr(cur.substring(0, cut));
                if(map.containsKey(cut_r)){
                    int found = map.get(cut_r);
                    if(found == i) continue;
                    res.add(Arrays.asList(i, found));
                }
            }
        }
    }
    
    return res;
}

public String reverseStr(String str){
    StringBuilder sb= new StringBuilder(str);
    return sb.reverse().toString();
}

public boolean isPalindrome(String s){
    int i = 0;
    int j = s.length() - 1;
    while(i <= j){
        if(s.charAt(i) != s.charAt(j)){
            return false;
        }
        i++;
        j--;
    }
    return true;
}
```
