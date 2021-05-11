/**
Refer to
https://helloacm.com/counting-substrings-with-only-one-distinct-letter-with-different-algorithms/
https://leetcode.jp/problemdetail.php?id=1180
Given a string S, return the number of substrings that have only one distinct letter.

Example 1:
Input: S = "aaaba"
Output: 8
Explanation: The substrings with one distinct letter are "aaa", "aa", "a", "b".
"aaa" occurs 1 time.
"aa" occurs 2 times.
"a" occurs 4 times.
"b" occurs 1 time.
So the answer is 1 + 2 + 4 + 1 = 8.

Example 2:
Input: S = "aaaaaaaaaa"
Output: 55

Constraints:
1 <= S.length <= 1000
S[i] consists of only lowercase English letters.
*/

// Solution 1: Two Pointers
class Solution {
    public int countLetters(String S) {
        int n = S.length();
        int result = 0;
        int i = 0;
        int j = 0;
        while(j < n) {
            while(j < n && S.charAt(i) == S.charAt(j)) {
                j++;
            }
            result += (j - i) * (j - i + 1) / 2;
            i = j;
        }
        return result;
    }
}

// Solution 2: Stack
class Solution {
    public int countLetters(String S) {
        Stack<Character> stack = new Stack<Character>();
        int result = 0;
        for(char c : S.toCharArray()) {
            if(stack.isEmpty()) {
                stack.push(c);
            } else if(stack.peek() == c) {
                stack.push(c);
            } else {
                int count = stack.size();
                result += count * (count + 1) / 2;
                stack.clear();
                stack.push(c);
            }
        }
        // Handle tailing section, e.g last 'a' in "aaaba" or "aa" in "aaabaa"
        int tmp = stack.size();
        result += tmp * (tmp + 1) / 2;
        return result;
    }
}
