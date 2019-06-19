/**
 Refer to
 https://leetcode.com/problems/remove-k-digits/
 Given a non-negative integer num represented as a string, remove k digits from the number so that the new number 
 is the smallest possible.

Note:
The length of num is less than 10002 and will be ≥ k.
The given num does not contain any leading zero.

Example 1:
Input: num = "1432219", k = 3
Output: "1219"
Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.

Example 2:
Input: num = "10200", k = 1
Output: "200"
Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.

Example 3:
Input: num = "10", k = 2
Output: "0"
Explanation: Remove all the digits from the number and it is left with nothing which is 0.
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/remove-k-digits/discuss/88708/Straightforward-Java-Solution-Using-Stack
class Solution {
    public String removeKdigits(String num, int k) {
        if(num == null || num.length() == 0) {
            return "";
        }
        if(num.length() == k) {
            return "0";
        }
        // Better than Stack<Integer> since no need calculate
        // num.charAt(i) - '0'
        Stack<Character> stack = new Stack<Character>();
        // The given num does not contain any leading zero
        // so we can directly put the 1st char on stack
        stack.push(num.charAt(0));
        int count = 0;
        for(int i = 1; i < num.length(); i++) {
            char curr = num.charAt(i);
            if(stack.size() == 1 && stack.peek() == '0') {
                stack.pop();
                // The tricky point is not including 'k--' in condition, 
                // since remove leading zeros not calculated into 'remove' 
                // operation count as example 2 mentioned
                //count++; 
            }
            while(!stack.isEmpty() && curr < stack.peek() && count < k) {
                stack.pop();
                count++;
            }
            stack.push(curr);
        }
        // Handle corner case as num = 112 and k = 1
        while(count < k) {
            stack.pop();
            count++;
        }
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()) {
            sb.insert(0, stack.pop());
        }          
        return sb.toString();
    }
}


// Solution 2:
// Refer to
// https://leetcode.com/problems/remove-k-digits/discuss/88737/Java-with-stack-get-rid-of-prefix-%220%22s-inside-the-loop.
