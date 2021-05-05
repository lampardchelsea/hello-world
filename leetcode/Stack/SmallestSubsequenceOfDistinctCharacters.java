/**
Refer to
https://leetcode.com/problems/smallest-subsequence-of-distinct-characters/
Return the lexicographically smallest subsequence of s that contains all the distinct characters of s exactly once.

Note: This question is the same as 316: https://leetcode.com/problems/remove-duplicate-letters/

Example 1:
Input: s = "bcabc"
Output: "abc"

Example 2:
Input: s = "cbacdcbc"
Output: "acdb"

Constraints:
1 <= s.length <= 1000
s consists of lowercase English letters.
*/

// Solution 1: Stack
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Stack/RemoveDuplicateLetters.java
/**
    public String removeDuplicateLetters(String s) {
        // Build dictionary
        int[] dict = new int[26];
        char[] ch = s.toCharArray();
        for(char c : ch) {
            dict[c - 'a']++;
        }
        // Build stack for result
        Stack<Character> stack = new Stack<Character>();
        // Recording current character in stack or not
        boolean[] visited = new boolean[26];
        for(char c : ch) {
            int index = c - 'a';
            // Decrement number of characters remaining in the string to be analysed
            dict[index]--;
            // If character is already present in stack, dont bother
            if(visited[index]) {
                continue;
            }
            // If current character < peek character of stack and that peek character
            // still have additional supplyment in dictionary, we can pop out peek character
            // Another explaination:
            // if current character is smaller than last character in stack which occurs 
            // later in the string again
            // it can be removed and  added later e.g stack = bc remaining string abc then 
            // a can pop b and then c
            while(!stack.isEmpty() && (index < stack.peek() - 'a') && dict[stack.peek() - 'a'] > 0) {
            	// Reset the visited status since we will pop out the peek value
                visited[stack.peek() - 'a'] = false;
                stack.pop();
                // Or in simple we can write as
                // visited[stack.pop() - 'a'] = false;
            }
            stack.push(c);
            visited[index] = true;
        }
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()) {
            sb.insert(0, stack.pop());
        }
        return sb.toString();
	  }
*/
class Solution {
    public String smallestSubsequence(String s) {
        int[] freq = new int[26];
        boolean[] visited = new boolean[26];
        Stack<Character> stack = new Stack<Character>();
        char[] chars = s.toCharArray();
        for(char c : chars) {
            freq[c - 'a']++;
        }
        for(char c : chars) {
            freq[c - 'a']--;
            if(!visited[c - 'a']) {
                while(!stack.isEmpty() && stack.peek() > c && freq[stack.peek() - 'a'] > 0) {
                    visited[stack.peek() - 'a'] = false;
                    stack.pop();
                }
                stack.push(c);
                visited[c - 'a'] = true;
            }
        }
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()) {
            sb.insert(0, stack.pop());
        }
        return sb.toString();
    }
}
