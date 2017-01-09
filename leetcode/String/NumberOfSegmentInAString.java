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
// and return wrong result
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
