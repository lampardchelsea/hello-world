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

/**
 Refer to
 http://blog.csdn.net/qq508618087/article/details/52584133
 思路：其基本思想是利用栈尽量维持一个递增的序列，也就是说将字符串中字符依次入栈，如果当前字符串比栈顶元素小，
 并且还可以继续删除元素，那么就将栈顶元素删掉，这样可以保证将当前元素加进去一定可以得到一个较小的序列．
 也可以算是一个贪心思想．最后我们只取前len-k个元素构成一个序列即可，如果这样得到的是一个空串那就手动返回０．
 还有一个需要注意的是字符串首字符不为０
*/

// Solution 1: Remove leading 0 after all operations
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
            while(!stack.isEmpty() && curr < stack.peek() && count < k) {
                stack.pop();
                count++;
            }
            // ??? This way not work ??? Instead remove the initial 0 after all
            // Because second condition as '(stack.size() == 1 && stack.peek() == '0')'
            // should not count into remove operation since 0200 need auto remove
            // leading 0
            // while((!stack.isEmpty() && curr < stack.peek() && count < k)
            //     || (stack.size() == 1 && stack.peek() == '0')) {
            //     stack.pop();
            //     count++;
            // }
            // Whenever meet a digit which is less than the previous digit, 
            // discard the previous one
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
        // Remove all the 0 at the head
        while(sb.length() > 1 && sb.charAt(0) == '0') {
            sb.deleteCharAt(0);
        }            
        return sb.toString();
    }
}

// Solution 2: Java with stack, get rid of prefix "0"s inside the loop
// Refer to
// https://leetcode.com/problems/remove-k-digits/discuss/88737/Java-with-stack-get-rid-of-prefix-%220%22s-inside-the-loop.
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
