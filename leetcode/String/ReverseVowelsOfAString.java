/**
 * Write a function that takes a string as input and reverse only the vowels of a string.
 * Example 1:
 * Given s = "hello", return "holle".
 * Example 2:
 * Given s = "leetcode", return "leotcede".
 * Note:
 * The vowels does not include the letter "y".
 * 
 * 这道题让我们翻转字符串中的元音字母，元音字母有五个a,e,i,o,u，需要注意的是大写的也算，所以总共有十个字母。
 * 我们写一个isVowel的函数来判断当前字符是否为元音字母，如果两边都是元音字母，那么我们交换，如果左边的不是，
 * 向右移动一位，如果右边的不是，则向左移动一位
*/

// Refer to
// http://www.cnblogs.com/grandyang/p/5426682.html
public class Solution {
    public String reverseVowels(String s) {
        int length = s.length();
        int i = 0;
        int j = length - 1;
        char[] chars = s.toCharArray();
        while(i < j) {
            if(isVowel(chars[i]) && isVowel(chars[j])) {
                char temp = chars[i];
                chars[i] = chars[j];
                chars[j] = temp;
                i++;
                j--;
            } else if(isVowel(chars[i]) && !isVowel(chars[j])) {
                j--;
            } else if(!isVowel(chars[i]) && isVowel(chars[j])) {
                i++;
            } else {
                i++;
                j--;
            }
        }
        return new String(chars);
    }
    
    public boolean isVowel(char c) {
        if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
            return true;
        }
        return false;
    }
}
