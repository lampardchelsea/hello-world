import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5315227.html
 * Given a string s, return all the palindromic permutations (without duplicates) of it. 
 * Return an empty list if no palindromic permutation could be form.

	For example:
	Given s = "aabb", return ["abba", "baab"].
	Given s = "abc", return [].
	
	Hint:
	If a palindromic permutation exists, we just need to generate the first half of the string.
	To generate all distinct permutations of a (half of) string, use a similar approach from: 
	Permutations II or Next Permutation.
 * 
 * 
 * Solution
 * https://discuss.leetcode.com/topic/28020/short-backtracking-solution-in-java-3-ms
 * http://www.cnblogs.com/grandyang/p/5315227.html
 * 这道题是之前那道Palindrome Permutation的拓展，那道题只是让判断存不存在回文全排列，而这题让我们
 * 返回所有的回文全排列，此题给了我们充分的提示：如果回文全排列存在，我们只需要生成前半段字符串即可，
 * 后面的直接根据前半段得到。那么我们再进一步思考，由于回文字符串有奇偶两种情况，偶数回文串例如abba，
 * 可以平均分成前后半段，而奇数回文串例如abcba，需要分成前中后三段，需要注意的是中间部分只能是一个字符，
 * 那么我们可以分析得出，如果一个字符串的回文字符串要存在，那么奇数个的字符只能有0个或1个，其余的必须
 * 是偶数个，所以我们可以用哈希表来记录所有字符的出现个数，然后我们找出出现奇数次数的字符加入mid中，
 * 如果有两个或两个以上的奇数个数的字符，那么返回空集，我们对于每个字符，不管其奇偶，都将其个数除以2的
 * 个数的字符加入t中，这样做的原因是如果是偶数个，那么将其一般加入t中，如果是奇数，如果有1个，那么除以
 * 2是0，不会有字符加入t，如果是3个，那么除以2是1，取一个加入t。等我们获得了t之后，t是就是前半段字符，
 * 我们对其做全排列，每得到一个全排列，我们加上mid和该全排列的逆序列就是一种所求的回文字符串，这样
 * 我们就可以得到所有的回文全排列了
 * 
 * 
 * https://leetcode.com/articles/palindrome-permutation-ii/
 * https://www.youtube.com/watch?v=tV-n9qZNiW0
 * https://discuss.leetcode.com/topic/28020/short-backtracking-solution-in-java-3-ms/8
 * 
 * Note: Difference between Solution 1 and Solution 2
 * https://stackoverflow.com/questions/8798403/string-is-immutable-what-exactly-is-the-meaning
 * https://stackoverflow.com/questions/2971315/string-stringbuffer-and-stringbuilder
 */
public class PalindromePermutationII {
// Solution 1: int[] map + Backtrack
	// Refer to
	// https://discuss.leetcode.com/topic/28020/short-backtracking-solution-in-java-3-ms
	List<String> result = new ArrayList<String>();
	public List<String> generatePalindromes(String s) {	
		// Scan string to get character frequencies map
		int[] map = new int[256];
		for(int i = 0; i < s.length(); i++) {
			map[s.charAt(i)]++;
		}
		int count = 0;
		for(int j = 0; j < map.length && count <= 1; j++) {
			count += map[j] % 2;
		}
		if(count > 1) {
			return result;
		}
		
		// Find the mid string and calculate half
		// string length
		String mid = "";
		int length = 0;
		for(int i = 0; i < map.length; i++) {
			if(map[i] > 0) {
				// Char with odd count will be in the middle
				if(map[i] % 2 == 1) {
					mid = "" + (char)i;
					// Decrease 1 count and remind counts will
					// cut into half
					map[i]--;
				}
				// Cut in half since we only generate half string
				map[i] /= 2;
				length += map[i];
			}
		}
		
		helper("", length, mid, map);
		return result;
	}
	
	private void helper(String s, int length, String mid, int[] map) {
		// Base case
		if(s.length() == length) {
	        StringBuilder sb = new StringBuilder(s).reverse();
	        result.add(s + mid + sb.toString());
	        return;
		}
		// Why don't need backtrack the string ? -> Because string is immutable ?
		// Backtracking Style 1: Modify String s
		for(int i = 0; i < 256; i++) {
			if(map[i] > 0) {
				/**
				 * Why no need for below two statements ?
				 * Compare with Solution 2, we will get some basic idea:
				 * String VS StringBuilder
				 * 
				 * Every time we modify a String will create a new String object
				 * So, when pass into DFS method, even we modify String in the 
				 * current level, the original passed in String object will keep
				 * as it is, that's why we don't need to implement backtracking
				 * rule as same as on Solution 1, but Solution 2 need, as StringBuilder
				 * pass into DFS method will always keep same object, if don't
				 * apply backtracking on that, the passed in StringBuilder object will
				 * be modified
				 * 
				 * Note: In common case, we prefer Solution 2 as String is special
				 * immutable object (declared as final class), StringBuilder like
				 * objects are more popular
				 * 
				 * Refer to
				 * String is immutable. What exactly is the meaning?
				 * https://stackoverflow.com/questions/8798403/string-is-immutable-what-exactly-is-the-meaning
				 * 
				 * String, StringBuffer, and StringBuilder
				 * https://stackoverflow.com/questions/2971315/string-stringbuffer-and-stringbuilder
				 * 
				 */
		        s += (char)i;  // This is not necessary
		        map[i]--;
		        helper(s, length, mid, map);
		        s = s.substring(0, s.length() - 1);  // This is not necessary
		        map[i]++;
			}
		}
		// Backtracking Style 2: Not modify String s
		for(int i = 0; i < 256; i++) {
			if(map[i] > 0) {
				// Refer to
				// Why we don't need the duplicate check like we did for Permutation II 
				// here? How to ensure there is no duplicate string s?
				// https://discuss.leetcode.com/topic/28020/short-backtracking-solution-in-java-3-ms/8
				// the total number of map[i] (i from 0 to 256) is fixed, so one element only used once, 
				// this avoids duplicate like [a,a,a] if the map indicates a string [a,c(1),c(2)]. 
				// Also in each stack of recursion this for loop goes from 0 to 255, and the map is 
				// updated each time, this avoids duplicates like [a, c(1), c(2)] and [a, c(2), c(1)].
		        map[i]--;
			    helper(s + (char)i, length, mid, map);
		        map[i]++;
			}
		}
	}
	
// Below is the wrong way since it fixed the "mid" string as original retrieved ones
// e.g it could be test out by "aabbccc" -> below wrong answer will only result out as "abcccba" and "bacccab",
// but problem is "ccc" could be devide into "c" and "cc", and merge "cc" with "abba" will provide more result
// So only pick out the possible unique odd count character, the left part should also calculate as half string
// used for calculating permutation (not combination), style refer to 47.Permutations II
// Solution 2: HashMap + Backtrack
// Refer to
// https://www.youtube.com/watch?v=tV-n9qZNiW0
public class Solution {
    /**
     * @param s: the given string
     * @return: all the palindromic permutations (without duplicates) of it
     */
    List<String> result = new ArrayList<String>();
    public List<String> generatePalindromes(String s) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(!map.containsKey(c)) {
                map.put(c, 1);
            } else {
                map.put(c, map.get(c) + 1);
            }
        }
        int oddCount = 0;
        String mid = "";
        String half = "";
        for(Character c : map.keySet()) {
            int num = map.get(c);
            if(num % 2 == 1) {
                oddCount++;
                while(num > 0) {
                    mid += c;
                    num--;
                }
            } else {
                num /= 2;
                while(num > 0) {
                    half += c;
                    num--;
                }
            }
            if(oddCount > 1) {
                return result;
            }
        }
        char[] chars = half.toCharArray();
        Arrays.sort(chars);
        boolean[] visited = new boolean[chars.length];
        helper(chars, new StringBuilder(), mid, visited);
        return result;
    }
    
    // Refer to 47.Permutations II
    private void helper(char[] chars, StringBuilder sb, String mid, boolean[] visited) {
        if(sb.length() == chars.length) {
            result.add(sb.toString() + mid + sb.reverse().toString());
            sb.reverse();
            return;
        }
        for(int i = 0; i < chars.length; i++) {
            if(visited[i] || (i > 0 && !visited[i - 1] && chars[i] == chars[i - 1])) {
                continue;
            }
            int len = sb.length();
            sb.append(chars[i]);
            visited[i] = true;
            helper(chars, sb, mid, visited);
            sb.setLength(len);
            visited[i] = false;
        }
    }
}
	
	
// Correct way
public class Solution {
    /**
     * @param s: the given string
     * @return: all the palindromic permutations (without duplicates) of it
     */
    List<String> result = new ArrayList<String>();
    public List<String> generatePalindromes(String s) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(!map.containsKey(c)) {
                map.put(c, 1);
            } else {
                map.put(c, map.get(c) + 1);
            }
        }
        int oddCount = 0;
        String mid = "";
        // e.g "ccc" after pick out 1 'c' to form 'mid' will left 2 'c'
        String mid_remain = ""; 
        String half = "";
        for(Character c : map.keySet()) {
            int num = map.get(c);
            if(num % 2 == 1) {
                oddCount++;
                mid += c;
                int mid_remain_count = (num - 1) / 2;
                while(mid_remain_count > 0) {
                    mid_remain += c;
                    mid_remain_count--;
                }
            } else {
                num /= 2;
                while(num > 0) {
                    half += c;
                    num--;
                }
            }
            if(oddCount > 1) {
                return result;
            }
        }
        String final_half = half + mid_remain;
        char[] chars = final_half.toCharArray();
        Arrays.sort(chars);
        boolean[] visited = new boolean[chars.length];
        helper(chars, new StringBuilder(), mid, visited);
        return result;
    }
    
    // Refer to 47.Permutations II
    private void helper(char[] chars, StringBuilder sb, String mid, boolean[] visited) {
        if(sb.length() == chars.length) {
            result.add(sb.toString() + mid + sb.reverse().toString());
            sb.reverse();
            return;
        }
        for(int i = 0; i < chars.length; i++) {
            if(visited[i] || (i > 0 && !visited[i - 1] && chars[i] == chars[i - 1])) {
                continue;
            }
            int len = sb.length();
            sb.append(chars[i]);
            visited[i] = true;
            helper(chars, sb, mid, visited);
            sb.setLength(len);
            visited[i] = false;
        }
    }
}	
}
