/**
 * Refer to
 * https://leetcode.com/problems/add-strings/description/
 * Given two non-negative integers num1 and num2 represented as string, return the sum of num1 and num2.
    Note:

    The length of both num1 and num2 is < 5100.
    Both num1 and num2 contains only digits 0-9.
    Both num1 and num2 does not contain any leading zero.
    You must not use any built-in BigInteger library or convert the inputs to integer directly.
 *
 * Solution
 * https://leetcode.com/problems/add-two-numbers/discuss/1010/Is-this-Algorithm-optimal-or-what
 * https://leetcode.com/problems/add-strings/discuss/90436/Straightforward-Java-8-main-lines-25ms
*/
class Solution {
    public String addStrings(String num1, String num2) {
        int len1 = num1.length();
        int len2 = num2.length();
        int i = len1 - 1;
        int j = len2 - 1;
        int temp = 0;
        StringBuilder sb = new StringBuilder();
        while(i >= 0 || j >= 0) {
            temp = temp / 10;
            if(i >= 0) {
                temp += num1.charAt(i) - '0';
                i--;
            }
            if(j >= 0) {
                temp += num2.charAt(j) - '0';
                j--;
            }
            sb.insert(0, temp % 10);
        }
        if(temp / 10 == 1) {
            sb.insert(0, 1);
        }
        return sb.toString();
    }
}
