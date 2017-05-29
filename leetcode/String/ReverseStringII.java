/**
 * Refer to
 * https://leetcode.com/problems/reverse-string-ii/#/description
 * Given a string and an integer k, you need to reverse the first k characters for 
 * every 2k characters counting from the start of the string. If there are less than 
 * k characters left, reverse all of them. If there are less than 2k but greater 
 * than or equal to k characters, then reverse the first k characters and left the 
 * other as original.
 * Example:
   Input: s = "abcdefg", k = 2
   Output: "bacdfeg"
 * Restrictions:
 * The string consists of lower English letters only.
 * Length of the given string and k will in the range [1, 10000]
 *
 * Solution
 * https://discuss.leetcode.com/topic/82626/java-concise-solution
 * Using int j = Math.min(i + k - 1, n - 1) and just swap the specific positions of array is 
 * very tricky which can avoid many end conditions determination
*/

public class Solution {
    // Solution 1:
    public String reverseStr(String s, int k) {
        int m = s.length() / (2 * k);
        int n = s.length() % (2 * k);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < m; i++) {
            String reversedPart = reverseFirstK(s.substring(2 * k * i, 2 * k * i + k));
            sb.append(reversedPart);
            sb.append(s.substring(2 * k * i + k, 2 * k * i + 2 * k));
        }
        if(n >= k) {
            // Important: Should not depend on m as m maybe 0, but here require m >= 1 !!!
            // String reversedPart = reverseFirstK(s.substring(2 * k * (m - 1), 2 * k * (m - 1) + k));
            String reversedPart = reverseFirstK(s.substring(s.length() - n, s.length() - n + k));
            sb.append(reversedPart);
            // sb.append(s.substring(2 * k * (m - 1) + k, 2 * k * (m - 1) + n));
            sb.append(s.substring(s.length() - n + k, s.length()));
        } else {
            String reversedPart = reverseFirstK(s.substring(s.length() - n, s.length()));
            sb.append(reversedPart);
        }
        return sb.toString();
    }
    
    public String reverseFirstK(String tmp) {
        int left = 0;
        int right = tmp.length() - 1;
        char[] array = tmp.toCharArray();
        while(left < right) {
            swap(array, left, right);
            left++;
            right--;
        }
        return String.valueOf(array);
    }
    
    public void swap(char[] array, int left, int right) {
        char tmp = array[left];
        array[left] = array[right];
        array[right] = tmp;
    }
    
}
