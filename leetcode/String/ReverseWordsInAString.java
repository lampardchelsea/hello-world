/**
 * Refer to
 * https://leetcode.com/problems/reverse-words-in-a-string/#/description
 *  Given an input string, reverse the string word by word.
	For example,
	Given s = "the sky is blue",
	return "blue is sky the".
	
	Update (2015-02-12):
	For C programmers: Try to solve it in-place in O(1) space.

	click to show clarification.
	Clarification:
	
    What constitutes a word?
    A sequence of non-space characters constitutes a word.
    Could the input string contain leading or trailing spaces?
    Yes. However, your reversed string should not contain leading or trailing spaces.
    How about multiple spaces between two words?
    Reduce them to a single space in the reversed string.

 * Solution
 * https://segmentfault.com/a/1190000003761552
 * 使用API
 * 复杂度
 * 时间 O(N) 空间 O(N)
 * 思路
 * 将单词根据空格split开来存入一个字符串数组，然后将该数组反转即可。
 * 注意
 * 先用trim()将前后无用的空格去掉
 * 用正则表达式" +"来匹配一个或多个空格
 * public class Solution {
	    public String reverseWords(String s) {
	        String[] words = s.trim().split(" +");
	        int len = words.length;
	        StringBuilder result = new StringBuilder();
	        for(int i = len -1; i>=0;i--){
	            result.append(words[i]);
	            if(i!=0) result.append(" ");
	        }
	        return result.toString();
	    }
	}
 * 
 * 
 * Splitting a string with multiple spaces
 * https://stackoverflow.com/questions/10079415/splitting-a-string-with-multiple-spaces
 * Since the argument to split() is a regular expression, you can look for 
 * one or more spaces (" +") instead of just one space (" ").
 * String[] array = s.split(" +");
 */
public class ReverseWordsInAString {
	public String reverseWords(String s) {
        if(s == null) {
            return null;
        }
        if(s.isEmpty()) {
            return "";
        }
        // Trim the leading and trailing spaces
        // and split by one or multiple spaces
        String[] tokens = s.trim().split(" +");
        int left = 0;
        int right = tokens.length - 1;
        while(left < right) {
            swap(tokens, left, right);
            left++;
            right--;
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < tokens.length; i++) {
            // For last section need to be careful,
            // should not append space after that
            if(i < tokens.length - 1) {
                sb.append(tokens[i]).append(" ");
            } else {
                sb.append(tokens[i]);
            }
        }
        return sb.toString();
    }
    
    public void swap(String[] tokens, int left, int right) {
        String tmp = tokens[left];
        tokens[left] = tokens[right];
        tokens[right] = tmp;
    }
    
    public static void main(String[] args) {
    	
    }
}

