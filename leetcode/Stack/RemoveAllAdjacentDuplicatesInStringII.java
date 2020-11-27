/**
Refer to
https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/
Given a string s, a k duplicate removal consists of choosing k adjacent and equal letters from s and removing them causing 
the left and the right side of the deleted substring to concatenate together.

We repeatedly make k duplicate removals on s until we no longer can.

Return the final string after all such duplicate removals have been made.

It is guaranteed that the answer is unique.

Example 1:
Input: s = "abcd", k = 2
Output: "abcd"
Explanation: There's nothing to delete.

Example 2:
Input: s = "deeedbbcccbdaa", k = 3
Output: "aa"
Explanation: 
First delete "eee" and "ccc", get "ddbbbdaa"
Then delete "bbb", get "dddaa"
Finally delete "ddd", get "aa"

Example 3:
Input: s = "pbbcggttciiippooaais", k = 2
Output: "ps"

Constraints:
1 <= s.length <= 10^5
2 <= k <= 10^4
s only contains lower case English letters.
*/

// Solution 1: Stack
// Refer to
// https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/discuss/392933/JavaC%2B%2BPython-Two-Pointers-and-Stack-Solution
/**
Intuition
If you read my post last month about 1047. Remove All Adjacent Duplicates In String
you cannot solve this problem easier.
Solution 2: Stack
Save the character c and its count to the stack.
If the next character c is same as the last one, increment the count.
Otherwise push a pair (c, 1) into the stack.
I used a dummy element ('#', 0) to avoid empty stack.
Java
By @motorix
We can use StringBuilder as a stack.
    public String removeDuplicates(String s, int k) {
        int[] count = new int[s.length()];
        StringBuilder sb = new StringBuilder();
        for(char c : s.toCharArray()) {
            sb.append(c);
            int last = sb.length()-1;
            count[last] = 1 + (last > 0 && sb.charAt(last) == sb.charAt(last-1) ? count[last-1] : 0);
            if(count[last] >= k) sb.delete(sb.length()-k, sb.length());
        }
        return sb.toString();
    }
*/

// Style 1: Actual stack used
// Refer to
// https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/discuss/392933/JavaC++Python-Two-Pointers-and-Stack-Solution/354981
class Solution {
    public String removeDuplicates(String s, int k) {
        Stack<Node> stack = new Stack<Node>();
        char[] chars = s.toCharArray();
        for(char c : chars) {
            // Compare previous char with current char and increase count
            if(!stack.isEmpty() && stack.peek().c == c) {
                stack.peek().count++;
            // If new char set count as 1
            } else {
                stack.push(new Node(c, 1));
            }
            // If count reach to k then remove char
            if(stack.peek().count == k) {
                stack.pop();
            }
        }
        StringBuilder sb = new StringBuilder();
        for(Node node : stack) {
            for(int i = 0; i < node.count; i++) {
                sb.append(node.c);
            }
        }
        return sb.toString();
    }
}

class Node {
    char c;
    int count;
    public Node(char c, int count) {
        this.c = c;
        this.count = count;
    }
}

// Style 2: No need actual stack but use StringBuilder
// Refer to
// https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/discuss/392933/JavaC%2B%2BPython-Two-Pointers-and-Stack-Solution
/**
Solution 2: Stack
Save the character c and its count to the stack.
If the next character c is same as the last one, increment the count.
Otherwise push a pair (c, 1) into the stack.
I used a dummy element ('#', 0) to avoid empty stack.

Java
By @motorix
We can use StringBuilder as a stack.

    public String removeDuplicates(String s, int k) {
        int[] count = new int[s.length()];
        StringBuilder sb = new StringBuilder();
        for(char c : s.toCharArray()) {
            sb.append(c);
            int last = sb.length()-1;
            count[last] = 1 + (last > 0 && sb.charAt(last) == sb.charAt(last-1) ? count[last-1] : 0);
            if(count[last] >= k) sb.delete(sb.length()-k, sb.length());
        }
        return sb.toString();
    }

Complexity
Time O(N) for one pass
Space O(N)
*/
// Refer to
// https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/discuss/392933/JavaC++Python-Two-Pointers-and-Stack-Solution/773393
class Solution {
    public String removeDuplicates(String s, int k) {
        StringBuilder sb = new StringBuilder(s);
        // Array to hold the count of character at particular index
        int[] count = new int[sb.length()];
        for(int i = 0; i < sb.length(); i++) {
            // For first character and when previous character is not equal to 
            // current character then start count again for current character
            if(i == 0 || sb.charAt(i) != sb.charAt(i - 1)) {
                count[i] = 1;
            // When character are equal increase the count by 1 of previous 
            // count as they are same characters
            } else {
                count[i] = count[i - 1] + 1;
                // IF current count == k, time to delete in buffer
                if(count[i] == k) {
                    sb.delete(i - k + 1, i + 1);
                    // Change the i position after deleting
                    i = i - k;
                }
            }
        }
        return sb.toString();
    }
}


// Solution 2: Two Pointers --> We even don't need StringBuilder to simulate stack
// Refer to
// https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/discuss/392933/JavaC%2B%2BPython-Two-Pointers-and-Stack-Solution
/**
Intuition
If you read my post last month about 1047. Remove All Adjacent Duplicates In String
you cannot solve this problem easier.

Solution 1: Two Pointers
java
    public String removeDuplicates(String s, int k) {
        int i = 0, n = s.length(), count[] = new int[n];
        char[] stack = s.toCharArray();
        for (int j = 0; j < n; ++j, ++i) {
            stack[i] = stack[j];
            count[i] = i > 0 && stack[i - 1] == stack[j] ? count[i - 1] + 1 : 1;
            if (count[i] == k) i -= k;
        }
        return new String(stack, 0, i);
    }
*/
class Solution {
    public String removeDuplicates(String s, int k) {
        int n = s.length();
        int[] count = new int[n];
        char[] chars = s.toCharArray();
        int i = 0;
        for(int j = 0; j < n; j++) {
            chars[i] = chars[j];
            if(i > 0 && chars[i] == chars[i - 1]) {
                count[i] = count[i - 1] + 1;
            } else {
                count[i] = 1;
            }
            if(count[i] == k) {
                i -= k;
            }
            i++;
        }
        return new String(chars, 0, i);
    }
}
