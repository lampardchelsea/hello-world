/**
 * Write a function that takes a string as input and returns the string reversed.
 * Example:
 * Given s = "hello", return "olleh".
*/
// Solution 1:
// Refer to
// https://discuss.leetcode.com/topic/43296/java-simple-and-clean-with-explanations-6-solutions
public class Solution {
    public String reverseString(String s) {
        char[] word = s.toCharArray();
        int i = 0;
        int j = s.length() - 1;
        while(i < j) {
            char temp = word[i];
            word[i] = word[j];
            word[j] = temp;
            i++;
            j--;
        }
        return new String(word);
    }
}

// Solution 2: Recursive
public class Solution {
    public String reverseString(String s) {
        int length = s.length();
        if(length <= 1) {
          return s;  
        }
        String leftString = s.substring(0, length / 2);
        String rightString = s.substring(length / 2, length);
        return reverseString(rightString) + reverseString(leftString);
    }
}
