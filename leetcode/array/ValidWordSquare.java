
import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5991673.html
 * Given a sequence of words, check whether it forms a valid word square.

	A sequence of words forms a valid word square if the kth row and column read the exact same string, where 0 ≤k < max(numRows, numColumns).
	
	Note:
	
	The number of words given is at least 1 and does not exceed 500.
	Word length will be at least 1 and does not exceed 500.
	Each word contains only lowercase English alphabet a-z.
	 
	
	Example 1:
	
	Input:
	[
	  "abcd",
	  "bnrt",
	  "crmy",
	  "dtye"
	]
	
	Output:
	true
	
	Explanation:
	The first row and first column both read "abcd".
	The second row and second column both read "bnrt".
	The third row and third column both read "crmy".
	The fourth row and fourth column both read "dtye".
	
	Therefore, it is a valid word square.
	 
	
	Example 2:
	
	Input:
	[
	  "abcd",
	  "bnrt",
	  "crm",
	  "dt"
	]
	
	Output:
	true
	
	Explanation:
	The first row and first column both read "abcd".
	The second row and second column both read "bnrt".
	The third row and third column both read "crm".
	The fourth row and fourth column both read "dt".
	
	Therefore, it is a valid word square.
	 
	
	Example 3:
	
	Input:
	[
	  "ball",
	  "area",
	  "read",
	  "lady"
	]
	
	Output:
	false
	
	Explanation:
	The third row reads "read" while the third column reads "lead".
	
	Therefore, it is NOT a valid word square.
	
 * Solution
 * https://discuss.leetcode.com/topic/63387/java-ac-solution-easy-to-understand
 * http://www.cnblogs.com/grandyang/p/5991673.html
 * 这道题给了我们一个二位数组，每行每列都是一个单词，需要满足第k行的单词和第k列的单词要相等，这里不要求每一个单词的长度都一样，
 * 只要对应位置的单词一样即可。那么这里实际上也就是一个遍历二维数组，然后验证对应位上的字符是否相等的问题，由于各行的单词长度
 * 不一定相等，所以我们在找对应位置的字符时，要先判断是否越界，即对应位置是否有字符存在，遇到不符合要求的地方直接返回false，
 * 全部遍历结束后返回true
 *
 * Only three false conditions: too short, too long, letter not equal
 * https://discuss.leetcode.com/topic/66597/only-three-false-conditions-too-short-too-long-letter-not-equal 
 */
public class ValidWordSquare {
	public boolean validWordSquare(List<String> words) {
		if(words == null || words.size() == 0) {
			return true;
		}
		int n = words.size();
		for(int i = 0; i < n; i++) {
			String word = words.get(i);
			for(int j = 0; j < word.length(); j++) {
				// Too long, too short, letter not equal
				/**
				 * For 'too long' condition
				 * Similarly, if (j >= n) return false is for input cases such as ["abc","bde","cefg"]
				 * abc
				 * bde
				 * cefg
				 * when i is 2 and j is 3, n = 3, if try to apply with letter check with
				 * words.get(j).charAt(i) will throw exception as
				 * java.lang.IndexOutOfBoundsException: Index: 3, Size: 3
				 * because only 3 word, but try to get position at 3 which require 4 word.
				 * 
				 * For 'too short' condition
				 * Think about the example of ["ball","asee","let","lep"], which looks like the 
				 * following as a matrix:
				 * ball
				 * asee
				 * let
				 * lep
				 * when i is 3 and j is 2, words.get(i).charAt(j) will get p (at 4th row and 3th column). 
				 * However, the words.get(j).charAt(i) statement will throw a 
				 * java.lang.StringIndexOutOfBoundsException exception.
				 */
				if(words.get(j).length() <= i || words.get(j).charAt(i) != word.charAt(j)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		ValidWordSquare v = new ValidWordSquare();
		List<String> words = new ArrayList<String>();
		// Test for j >= n
		words.add("abc");
		words.add("bde");
		words.add("cefg");
		// Test for words.get(j).length() <= i
//		words.add("ball");
//		words.add("asee");
//		words.add("let");
//		words.add("lep");
		boolean result = v.validWordSquare(words);
		System.out.print(result);
	}
}
