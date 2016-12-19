/**
 * Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.
 * Example:
 * Input: "babad"
 * Output: "bab"
 * Note: "aba" is also a valid answer.
 * Example:
 * Input: "cbbd"
 * Output: "bb"
*/
// Solution 1: Brute Force
/**
 * Refer to
 * http://flexaired.blogspot.com/2011/03/longest-palindrome-in-string.html
 * http://www.programmingsimplified.com/java/source-code/java-program-find-substrings-of-string
 * https://segmentfault.com/a/1190000002991199
 * 
 * Brute force Algorithm.
 * Have 2 for loops 
 * 1. for i = 0 to i less than array.length - 1 
 *    for j = 0 to j less than array.length - i
 * 2. This way you can get substring of every possible combination from the array
 * 3. Have a palindrome function which checks if a string is palindrome
 * 4. so for every substring (i, i + j + 1) call this function, if it is a palindrome store it in a string variable
 * 5. If you find next palindrome substring and if it is greater than the current one, replace it with current one.
 * 6. Finally your string variable will have the answer
 *
 * 暴力法 Brute Force
 * 复杂度
 * 时间 O(n^3) 空间 O(1)
 * 思路
 * 暴力法就是穷举所有子字符串的可能，然后依次按位判断其是否是回文，并更新结果。虽然其时间复杂度很高，但它对空间的要求很低。
 */
public class LongestPalidromeSubstring {
	public static String longestPalindrome(String s) {
		int length = s.length();
		int maxLength = 0;
		int start = 0;
		
		// i is start position of substring, can be 0 to length - 1
		for(int i = 0; i < length; i++) {
			// j is length of substring, can be 0 to length - i
			// actually need j <= length - i, but because of
			// isPalindrome requires s.charAt(i + j) in index 
			// boundary, we need a make up "=" situation
			// Note: If j start with 1, will miss input as "a",
			// expected return is "a", real return is empty.
			// Because j = 1 and j < length(1) - i(0) not possible
			// directly skip out for loop
			for(int j = 0; j < length - i; j++) {
				if(isPalindrome(s, i, i + j)) {
					if(j >= maxLength) {
						// +1 to make up cannot set as j <= length - i
						maxLength = j + 1;
						start = i;
					}
				}
			}
		}
        
        return s.substring(start, start + maxLength);
    }
	
	// The parameter right should NOT over limitation, so when assign
	// parameter, j < length - i (i + j < length) is a choice, 
	// otherwise if passing as j <= length - i (i + j <= length),
	// when s.charAt(i + j) will throw out ArrayIndexOutOfBound issue.
	public static boolean isPalindrome(String s, int left, int right) {
		while(left < right) {
			if(s.charAt(left) != s.charAt(right)) {
				return false;
			}
			left++;
			right--;
		}
		return true;
	}
	
	
	public static void main(String[] args) {
		// String s = "a";
		String s = "babad";
		// Time exceed
//		String s = "cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"
//				+ "ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"
//				+ "ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"
//				+ "ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"
//				+ "ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"
//				+ "ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"
//				+ "ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"
//				+ "ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"
//				+ "ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"
//				+ "ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"
//				+ "ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"
//				+ "cccccccccccccccccccccc";
		String result = longestPalindrome(s);
		System.out.println(result);
	}
}


// Solution 2: Dynamic Programming
// Refer to
// http://www.geeksforgeeks.org/longest-palindrome-substring-set-1/
// https://leetcode.com/articles/longest-palindromic-substring/#approach-2-brute-force-time-limit-exceeded
// https://segmentfault.com/a/1190000002991199






