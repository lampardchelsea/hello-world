/**
 * Count the number of segments in a string, where a segment is defined to be a 
 * contiguous sequence of non-space characters.
 * Please note that the string does not contain any non-printable characters.
 * Example:
 * Input: "Hello, my name is John"
 * Output: 5
*/
// Wrong answer
// If input as "               " continuous space, it will treat as length > 0 branch
// and return wrong result, also it will not handle case like "     notspace     ",
// which start and end with space but string in mid case
public class Solution {
    public int countSegments(String s) {
        char[] chars = s.toCharArray();
        int length = chars.length;
        int count = 0;
        if(length > 0) {
            count = 1;
            for(int i = 0; i < length; i++) {
                if(chars[i] == ' ') {
                    count++;
                }
            }
        }
        return count;
    }
}

// Solution:
// Refer to
// https://discuss.leetcode.com/topic/70642/clean-java-solution-o-n
public class Solution {
    public int countSegments(String s) {
        int count = 0;
        char[] chars = s.toCharArray();
        int length = chars.length;
        for(int i = 0; i < length; i++) {
            if(chars[i] != ' ' && (i == 0 || chars[i - 1] == ' ')) {
                count++;
            }
        }
        return count;
    }
}

// Above equal to
public class Solution {
    public int countSegments(String s) {
        int count = 0;
        char[] chars = s.toCharArray();
        int length = chars.length;
        for(int i = 0; i < length; i++) {
            // When current character not equal to whitespace, only two
            // case can increase the count, (1) current character at
            // position 0 of string (2) current character not at position
            // 0 but the recent previous charcater is white space
            if(chars[i] != ' ') {
                if(i == 0) {
                    count++;
                }     
                if(i > 0 && chars[i - 1] == ' ') {
                    count++;
                }
            }
        }
        return count;
    }
}

