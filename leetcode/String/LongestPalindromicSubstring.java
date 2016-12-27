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


// Solution 2: Dynamic Programming (Longest Palindrome Substring != Longest Palindrome Subsequence(LPS))
// Original LPS problem (Check Leetcode/Dynamic Programming)
// https://www.quora.com/What-is-a-dynamic-programming-algorithm-to-find-the-longest-palindrome-that-is-a-subsequence-of-a-given-input-string-without-reversing-the-string
// http://www.geeksforgeeks.org/dynamic-programming-set-12-longest-palindromic-subsequence/
/**
// Below solution has some issue
// Refer to
// https://segmentfault.com/a/1190000002991199
public class Solution {
	public static String longestPalindrome(String s) {
		int maxLength = 0;
		int start = 0;
		int length = s.length();
		// 1st dimension is start position of longest palindrome
		// 2nd dimension is end position of longest palindrome
		boolean[][] dp = new boolean[length][length];
		
		// i is length of candidate substring
		for(int i = 0; i < length; i++) {
			// j is start position of candidate substring
			for(int j = 0; j < length - i; j++) {
				if(i == 0 || i == 1) {
					// When substring length is 0 or 1, it must be palindrome
					dp[j][j + i] = true;
				} else if(s.charAt(j) == s.charAt(j + i)) {
					// When left and right terminal positions equals, it depends
					// on whether shorter substring is centrosymmetric
					dp[j][j + i] = dp[j + 1][j + i - 1];
				} else {
					// When left and right terminal posistions not equals,
					// it directly cause current substring not palindrome
					dp[j][j + i] = false;
				}
				if(dp[j][j + i] && i >= maxLength) {
					maxLength = i + 1;
					start = j;
				}
			}
		}
		
		return s.substring(start, start + maxLength);
    }
}
*/

// Right DP solution
// Refer to
// http://articles.leetcode.com/longest-palindromic-substring-part-i/
/**
Dynamic programming solution, O(N2) time and O(N2) space:
To improve over the brute force solution from a DP approach, first think how we can avoid unnecessary 
re-computation in validating palindromes. Consider the case “ababa”. If we already knew that “bab” is 
a palindrome, it is obvious that “ababa” must be a palindrome since the two left and right end letters 
are the same.

Stated more formally below:
Define P[ i, j ] ← true iff the substring Si … Sj is a palindrome, otherwise false.
Therefore,

P[ i, j ] ← ( P[ i+1, j-1 ] and Si = Sj )
The base cases are:

P[ i, i ] ← true
P[ i, i+1 ] ← ( Si = Si+1 )
This yields a straight forward DP solution, which we first initialize the one and two letters palindromes, 
and work our way up finding all three letters palindromes, and so on… 

This gives us a run time complexity of O(N2) and uses O(N2) space to store the table.
*/
public class Solution {
	public static String longestPalindrome(String s) {
        int length = s.length();
        int maxLength = 1;
        int longestBegin = 0;
        boolean[][] table = new boolean[1000][1000];
        
        // All single character (substring length = 1) are
        // naturally palindrome
        for(int i = 0; i < length; i++) {
            table[i][i] = true;
        }
        
        // Be careful on boundary conditions, if missing "-1" will
        // show error as java.lang.StringIndexOutOfBoundsException: 
        // String index out of range: 5 when input "babad", this
        // is because we assume current substring length is 2, 
        // need to make sure s.charAt(i + 1) in boundary
        for(int i = 0; i < length - 1; i++) {
            if(s.charAt(i) == s.charAt(i + 1)) {
                table[i][i + 1] = true;
                maxLength = 2;
                longestBegin = i;
            }
        }
        
        // Be careful on boundary conditions, len can equal to length
        // if missing "=", error will show as e.g input "CCC", expect
        // output "CCC", error output "CC"
        for(int len = 3; len <= length; len++) {
            for(int i = 0; i < length - len + 1; i++) {
                int j = i + len - 1;
                if(s.charAt(i) == s.charAt(j) && table[i + 1][j - 1]) {
                    table[i][j] = true;
                    maxLength = len;
                    longestBegin = i;
                }
            }
        }
        
        return s.substring(longestBegin, longestBegin + maxLength);
    }
}


