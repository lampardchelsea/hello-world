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
































































































https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/description/
You are given a string s and an integer k, a k duplicate removal consists of choosing k adjacent and equal letters from s and removing them, causing the left and the right side of the deleted substring to concatenate together.
We repeatedly make k duplicate removals on s until we no longer can.
Return the final string after all such duplicate removals have been made. It is guaranteed that the answer is unique.

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
- 1 <= s.length <= 10^5
- 2 <= k <= 10^4
- s only contains lowercase English letters.
--------------------------------------------------------------------------------
Attempt 1: 2024-11-27
Solution 1: Stack (30min)
class Solution {
    public String removeDuplicates(String s, int k) {
        // {char's int value, count}
        Stack<int[]> stack = new Stack<>();
        int len = s.length();
        for(int i = 0; i < len; i++) {
            int index = s.charAt(i) - 'a';
            // If stack is not empty and the character on the top of the stack is the same as the current one,
            // increase the count, otherwise push a new pair to the stack.
            if(!stack.isEmpty() && stack.peek()[0] == index) {
                // Increment the count and use modulo operation to reset to 0 if it hits the 'k'.
                stack.peek()[1] = (stack.peek()[1] + 1) % k;
                // If the count becomes 0 after reaching k, pop the element from the stack.
                if(stack.peek()[1] == 0) {
                    stack.pop();
                }
            } else {
                // If stack is empty or the top element is different, push the new character and count (1).
                stack.push(new int[] {index, 1});                
            }
        }
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()) {
            int[] a = stack.pop();
            char c = (char) (a[0] + 'a');
            for(int i = 0; i < a[1]; i++) {
                sb.append(c);
            }
        }
        return sb.reverse().toString();
        // Another style by using for loop, no need pop out, no need reverse()
        //for(int[] a : stack) {
        //    char c = (char) (a[0] + 'a');
        //    for(int i = 0; i < a[1]; i++) {
        //        sb.append(c);
        //    }
        //}
        //return sb.toString();
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Refer to
https://algo.monster/liteproblems/1209
Problem Description
The problem provides us with a string s and an integer k. The task is to repetitively remove k adjacent, identical characters from the string until no further such group of k identical characters exists. After each removal, the left and right parts of the string are concatenated together. This process of removal is known as a k duplicate removal. The final string obtained after performing as many k duplicate removals as possible is the output. It is assured that the outcome obtained is unique for the given input string and integer k.
Intuition
The solution employs a stack data structure to track groups of identical characters. The stack helps us efficiently manage the groups and their counts without repeatedly scanning the whole string, which would be less efficient.
Here's the step-by-step intuition behind the solution:
1.Iterate over each character in the input string.
2.For each character c, check if the top element of the stack is the same character. If it is, we increment the count associated with that character at the top of the stack.
3.If the count of the top element becomes equal to k after incrementing, it means we have found k adjacent identical characters, and we can remove them from the stack (mimicking the removal from the string).
4.If the stack's top element is not c, or if the stack is empty, we push the new character c onto the stack with an initial count of one, as it starts a new potential group.
5.After the iteration is complete, we are left with a stack that contains characters and their respective counts that are less than k. The final answer is constructed by repeating the characters in the stack according to their remaining counts.
6.We return the reconstructed string as the result.
This method leverages the stack's Last-In-First-Out (LIFO) property, allowing us to process characters in a way that mimics the described k duplicate removal process. In essence, we maintain a running "active" portion of the string, with counts reflecting how many consecutive occurrences of each character there are. When any count reaches k, we've identified a 'completed' group which we can remove, emulating the removal of k duplicates in the string itself.
Solution Approach
The implementation uses a stack which is one of the most suited data structures for problems involving successive pairs or groups of elements with some common characteristics, like consecutive duplicate characters in this case.
Here is how the code works:
1.Initializing the Stack: We begin by initializing an empty list "stk" which will serve as our stack. The stack will store sublists where each sublist contains a character and a count of how many times it has occurred consecutively.
2.Iterating Over the String: We iterate over each character "c" in the string "s":
- Top of the Stack Comparison: We look at the top element of the stack (if it isn't empty) to see if c is the same as the character at the top of the stack (stk[-1][0] represents the character at the top of the stack).
- Incrementing Count: If the top element is the same as c, we increment the count (stk[-1][1]) and check if this count has reached k. If the count is k, this signifies a k duplicate removal, and therefore we pop the top of the stack.
- Pushing New Characters: If the stack is empty or c is different from the top character of the stack, we push a new sublist onto the stack with c and the count 1.
3.Reconstructing the String: After the iteration, the stack contains characters that did not qualify for k duplicate removal, with their counts being less than k. Now, to reconstruct the final string, we multiply (*) each character c by its count v and join ("".join()) the multiplied strings to form the answer.
The mathematical formula for the reconstruction process is given by:
ans = "".join([c * v for c, v in stk])
Here is the breakdown using the code:
- if stk and stk[-1][0] == c: This checks if the stack is not empty and whether the top element on the stack is the same as the current character.
- stk[-1][1] = (stk[-1][1] + 1) % k: Here, we increment the count and use the modulus operator to reset it to 0 if it reaches k (since k % k is 0).
- if stk[-1][1] == 0: stk.pop(): If after incrementing, the count becomes 0, meaning we have k duplicates, we remove (pop) the top element from the stack.
- else: stk.append([c, 1]): In case the character c is different or the stack is empty, we append c along with the initial count 1 as a new group in the stack.
- ans = [c * v for c, v in stk]: We build the answer list by multiplying the character c by its count v.
- return "".join(ans): Join all strings in the ans list to get the final answer.
The code takes advantage of Python's built-in list operations to simulate stack behavior efficiently, making the solution succinct and highly readable.
Example Walkthrough
Let's take a small example and walk through the solution approach to illustrate how it works. Assume s = "aabbbacc" and k = 3. Here's how the algorithm would process this:
1.Initializing the Stack: We start with an empty stack stk.
2.Iterating Over the String:
- We begin to iterate over s. First character is 'a'. The stack is empty, so we push ['a', 1].
- Next character is 'a'. The top of the stack is 'a', we increment the count: ['a', 2].
- We move to the next character 'b'. Stack's top is 'a', which is different, so we push ['b', 1].
- Next is another 'b'. The top is 'b' with count 1. Increment count: ['b', 2].
- Another 'b' comes. Increment count: ['b', 3]. But now we've hit k duplicates, so we remove this from the stack.
- The next character is 'a'. Top of the stack is 'a' with count 2. Increment the count and now we have 'a' with count 3, which meets the removal condition, and thus it is removed from the stack as well.
- We then see 'c'. The stack is empty now, so we push ['c', 1].
- Another 'c' comes up. Top is 'c' with count 1. Increment count: ['c', 2].
- At the end of this process, our stack stk is [ ['c', 2] ] since we only have two 'c' characters, which is less than the k duplicate threshold.
3.Reconstructing the String:
We can't remove any more elements, so it's time to reconstruct the string from the stack items. We multiply each character by its count: 'c' * 2 which yields 'cc'.
Hence, the final string after performing k duplicate removals is "cc".
Here is the resultant stack operations visualized at each step:
InputStackOperation'a'[('a', 1)]Push 'a''a'[('a', 2)]Increment count of 'a''b'[('a', 2), ('b', 1)]Push 'b''b'[('a', 2), ('b', 2)]Increment count of 'b''b'[('a', 2)]Increment count of 'b' and remove 'b' as count==k'a'[]Increment count of 'a' and remove 'a' as count==k'c'[('c', 1)]Push 'c''c'[('c', 2)]Increment count of 'c'
And the final string obtained by concatenating characters based on their count in the stack is 'cc'.
Solution Implementation
import java.util.Deque;
import java.util.ArrayDeque;

class Solution {
    public String removeDuplicates(String s, int k) {
        // Initialize a stack to keep track of characters and their counts.
        Deque<int[]> stack = new ArrayDeque<>();
      
        // Loop through each character of the string.
        for (int i = 0; i < s.length(); ++i) {
            // Convert the character to an index (0 for 'a', 1 for 'b', etc.).
            int index = s.charAt(i) - 'a';
          
            // If stack is not empty and the character on the top of the stack is the same as the current one,
            // increase the count, otherwise push a new pair to the stack.
            if (!stack.isEmpty() && stack.peek()[0] == index) {
                // Increment the count and use modulo operation to reset to 0 if it hits the `k`.
                stack.peek()[1] = (stack.peek()[1] + 1) % k;
              
                // If the count becomes 0 after reaching k, pop the element from the stack.
                if (stack.peek()[1] == 0) {
                    stack.pop();
                }
            } else {
                // If stack is empty or the top element is different, push the new character and count (1).
                stack.push(new int[] {index, 1});
            }
        }
      
        // Initialize a StringBuilder to collect the result.
        StringBuilder result = new StringBuilder();
      
        // Build the result string by iterating over the stack in LIFO order.
        for (var element : stack) {
            // Retrieve the character from the integer index.
            char c = (char) (element[0] + 'a');
            // Append the character element[1] (count) times.
            for (int i = 0; i < element[1]; ++i) {
                result.append(c);
            }
        }
      
        // The characters were added in reverse order, so reverse the whole string to get the correct order.
        result.reverse();
      
        // Return the resultant string.
        return result.toString();
    }
}
Time and Space Complexity
Time Complexity
The time complexity of the code can be analyzed by looking at the operations within the main loop that iterates over each character in the input string s. Here's the breakdown:
For each character c in s, the code performs a constant-time check to see if the stack stk is not empty and if the top element's character matches c.
If there's a match, it updates the count of that character on the stack, which is also a constant-time operation.
If the count equals k, it pops the element from the stack. Popping from the stack takes amortized constant time.
If there is no match or the stack is empty, it pushes the current character with count 1 onto the stack. This is a constant-time operation.
Since each character in the string is processed once and each operation is constant time or amortized constant time, the overall time complexity is O(n), where n is the length of string s.
Space Complexity
The space complexity of the code is determined by the additional space used by the stack stk:
In the worst case, if there are no k consecutive characters that are the same, the stack will contain all distinct characters of s, which would take O(n) space.
In the best case, if all characters are removed as duplicates, the stack will be empty, so no extra space is used beyond the input string.
Thus, the space complexity of the algorithm is O(n), where n is the length of string s, representing the maximum stack size that may be needed in the worst case.

--------------------------------------------------------------------------------
Solution 2: Two Pointers (60min)
class Solution {
    public String removeDuplicates(String s, int k) {
        int len = s.length();
        int[] count = new int[len];
        char[] result = s.toCharArray();
        int i = 0;
        for(int j = 0; j < len; j++, i++) {
            result[i] = result[j];
            count[i] = (i > 0 && result[i - 1] == result[j] ? count[i - 1] + 1 : 1);
            if(count[i] == k) {
                i -= k;
            }
        }
        return new String(result, 0, i);
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Refer to
https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/solutions/392933/java-c-python-two-pointers-and-stack-solution/
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


Refer to
L1047.Remove All Adjacent Duplicates In String (Ref.L1209)
