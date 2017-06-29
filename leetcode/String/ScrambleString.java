/**
 * Refer to
 * https://leetcode.com/problems/scramble-string/#/description
 *  Given a string s1, we may represent it as a binary tree by partitioning it to two non-empty substrings recursively.
	Below is one possible representation of s1 = "great":
	
	    great
	   /    \
	  gr    eat
	 / \    /  \
	g   r  e   at
	           / \
	          a   t
	
	To scramble the string, we may choose any non-leaf node and swap its two children.	
	For example, if we choose the node "gr" and swap its two children, it produces a scrambled string "rgeat".
	
	    rgeat
	   /    \
	  rg    eat
	 / \    /  \
	r   g  e   at
	           / \
	          a   t
	
	We say that "rgeat" is a scrambled string of "great".
	Similarly, if we continue to swap the children of nodes "eat" and "at", it produces a scrambled string "rgtae".
	
	    rgtae
	   /    \
	  rg    tae
	 / \    /  \
	r   g  ta  e
	       / \
	      t   a
	
	We say that "rgtae" is a scrambled string of "great".	
	Given two strings s1 and s2 of the same length, determine if s2 is a scrambled string of s1. 
 * 
 * 
 * 
 * Solution
 * http://www.cnblogs.com/grandyang/p/4318500.html
 * 这道题定义了一种爬行字符串，就是说假如把一个字符串当做一个二叉树的根，然后它的非空子字符串是它的子节点，然后交换某个子字符串
 * 的两个子节点，重新爬行回去形成一个新的字符串，这个新字符串和原来的字符串互为爬行字符串。这道题可以用递归Recursion或是
 * 动态规划Dynamic Programming来做，我们先来看递归的解法，参见网友uniEagle的博客，简单的说，就是s1和s2是scramble的话，
 * 那么必然存在一个在s1上的长度l1，将s1分成s11和s12两段，同样有s21和s22.那么要么s11和s21是scramble的并且s12和s22是
 * scramble的；要么s11和s22是scramble的并且s12和s21是scramble的。就拿题目中的例子 rgeat 和 great 来说，rgeat 可分
 * 成 rg 和 eat 两段， great 可分成 gr 和 eat 两段，rg 和 gr 是scrambled的， eat 和 eat 当然是scrambled
 * 
 * https://discuss.leetcode.com/topic/861/can-you-partition-a-string-at-any-index-at-any-time-in-producing-a-scramble
 * Q: The example shows the case where left child ALWAYS has equal or one-less characters than right child. 
 *    But since "abb" is a scramble of "bab", as suggested by a test case, strings are not always partitioned 
 *    in the way as the example implies. 
 *    However, if the answer is Yes, I think scrambles just become permutations. Isn't it?
 * A: This is very important to understand the difference to come up with a solution for this problem.
 *    you can not generate all the permutations of s1 with only branch and swap of the branches.
 *    Permutation is like a grammar which is able to generate all the combinations while the branch 
 *    and swap is like a push-down automata. The set of output strings will not be equal.
 * 
 * https://discuss.leetcode.com/topic/19158/accepted-java-solution
 * The 1st IF is to check the LEFT child of S1 is scramble of LEFT child of S2 AND RIGHT child of S1 is 
 * also a scramble of RIGHT child of s2. When this fails, it means the left and right substrings are swapped.
 * The 2nd IF statement check for the swap case with LEFT child of S1 and RIGHT child of S2 
 * AND RIGHT child of S1 and LEFT child of S2.
 */
public class ScrambleString {
    public boolean isScramble(String s1, String s2) {
        if(s1.equals(s2)) {
            return true;
        }
        
        // Add one more check to improve performance from 12ms to 6ms
        if(s1.length() != s2.length()) {
        	return false;
        }
        
        // Magic way on checking if two strings contain same charcaters
        int[] map = new int[26];
        char[] s1Chars = s1.toCharArray();
        char[] s2Chars = s2.toCharArray();
        // Given two strings have same length as condition
        for(int i = 0; i < s1.length(); i++) {
            map[s1Chars[i] - 'a']++;
            map[s2Chars[i] - 'a']--;
        }
        for(int i = 0; i < 26; i++) {
            if(map[i] != 0) {
                return false;
            }
        }
        
        // If contain same characters, then check on two possible scenarios
        for(int i = 1; i < s1.length(); i++) {
            // Case 1: (s11, s21) and (s12, s22), e.g s1 = ab, s2 = ab
            if(isScramble(s1.substring(0, i), s2.substring(0, i)) && isScramble(s1.substring(i), s2.substring(i))) {
                return true;
            }
            // Case 2: (s11, s22) and (s12, s21), e.g s1 = ab, s2 = ba
            if(isScramble(s1.substring(0, i), s2.substring(s2.length() - i)) && isScramble(s1.substring(i), s2.substring(0, s2.length() - i))) {
                return true;
            }
        }
        
        return false;
    }
    
    public static void main(String[] args) {
    	
    }
}

