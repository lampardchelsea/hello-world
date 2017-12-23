/**
 * Refer to
 * https://leetcode.com/problems/add-digits/description/
 * Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.

    For example:

    Given num = 38, the process is like: 3 + 8 = 11, 1 + 1 = 2. Since 2 has only one digit, return it.

    Follow up:
    Could you do it without any loop/recursion in O(1) runtime?
 * 
 * Solution
 * https://stackoverflow.com/questions/4968323/java-parse-int-value-from-a-char
*/
class Solution {
    int result = 0;
    public int addDigits(int num) {
        helper(num);
        return result;
    }
    
    private void helper(int num) {
        String tmp = String.valueOf(num);
        if(tmp.length() == 1) {
            result = Integer.valueOf(tmp);
        } else {
            char[] chars = tmp.toCharArray();
            int val = 0;
            for(char c : chars) {
                val += (c - '0');
            }
            helper(val);   
        }
    }
}
