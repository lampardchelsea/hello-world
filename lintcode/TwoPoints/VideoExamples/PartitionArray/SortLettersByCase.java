/**
 * Refer to
 * http://www.lintcode.com/en/problem/sort-letters-by-case/
 * Given a string which contains only letters. Sort it by lower case first and upper case second.

     Notice

    It's NOT necessary to keep the original order of lower-case letters and upper case letters.

    Have you met this question in a real interview? Yes
    Example
    For "abAcD", a reasonable answer is "acbAD"
 *
 * Solution
 * http://www.jiuzhang.com/solutions/sort-letters-by-case/
 * 
*/
public class Solution {
    /** 
     *@param chars: The letter array you should sort by Case
     *@return: void
     */
    public void sortLetters(char[] chars) {
        if(chars == null || chars.length == 0) {
            return;
        }
        int left = 0;
        int right = chars.length - 1;
        while(left <= right) {
            while(left <= right && Character.isLowerCase(chars[left])) {
                left++;
            }
            while(left <= right && Character.isUpperCase(chars[right])) {
                right--;
            }
            if(left <= right) {
                char temp = chars[left];
                chars[left] = chars[right];
                chars[right] = temp;
                left++;
                right--;
            }
        }
    }
}
