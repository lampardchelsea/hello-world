/**
 * Refer to
 * http://www.lintcode.com/en/problem/rotate-string/
 * Given a string and an offset, rotate string by offset. (rotate from left to right)

  Have you met this question in a real interview? Yes
  Example
  Given "abcdefg".

  offset=0 => "abcdefg"
  offset=1 => "gabcdef"
  offset=2 => "fgabcde"
  offset=3 => "efgabcd"
 *
 * Solution
 * http://www.jiuzhang.com/solution/rotate-string/
 * https://discuss.leetcode.com/topic/14341/easy-to-read-java-solution
*/
public class Solution {
    /**
     * @param str: an array of char
     * @param offset: an integer
     * @return: nothing
     */
    public void rotateString(char[] str, int offset) {
        if(str == null || str.length == 0) {
            return;
        }
        offset = offset % str.length;
        // Refer to
        // https://discuss.leetcode.com/topic/14341/easy-to-read-java-solution/20
        // 3 steps reverse
        // I am going to explain this with a simple example. 
        // Say with n = 7 and k = 3, the array = [1,2,3,4,5,6,7]
        // Reverse the whole array, so array = [7,6,5,4,3,2,1]
        // Reverse the first k elements, so array = [5,6,7,4,3,2,1]
        // Reverse [k+1, n] elements, so array = [5,6,7,1,2,3,4]
        reverse(str, 0, str.length - 1);
        reverse(str, 0, offset - 1);
        reverse(str, offset, str.length - 1);
    }
    
    private void reverse(char[] str, int start, int end) {
        while(start < end) {
            char temp = str[start];
            str[start] = str[end];
            str[end] = temp;
            start++;
            end--;
        }
    }
}
