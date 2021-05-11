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
// Refer to
// https://helloacm.com/counting-substrings-with-only-one-distinct-letter-with-different-algorithms/
/**
O(N) WITH MATH FORMULAS: COMBINATIONS
Given n-size string with unique letter only, there are n*(n+1)/2 different substrings. For example:
“a”: 1 = 1 “a”
“aa”: 3 = 2 “a” + 1 + “aa”
“aaa”: 6 = 3 “a”, + 2 “aa”, + 1 “aaa”
class Solution {
public:
    int countLetters(string S) {
        int ans = 0;
        int i = 0, j = 0, l = S.size();
        while (i < l) {
            while ((j < l) && (S[j] == S[i])) j ++;
            int n = j - i;
            ans += (n * (n + 1) / 2);
            i = j;
        }
        return ans;
    }
};
*/
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

// Solution 3: One Pointer
// Refer to
// https://alex8080.com/2019/10/30/1180-Count-Substrings-with-Only-One-Distinct-Letter/
class Solution {
    public int countLetters(String S) {
        if (S == null || S.length() == 0) return 0;
        int count = 1;
        int sum = 0;
        for (int i = 0; i < S.length() - 1; i++) {
            if (S.charAt(i) != S.charAt(i+1)) {
                sum += count * (count + 1) / 2;
                count = 1;
            } else { 
                count++;
            }
        }
        sum += count * (count + 1) / 2; // add last substring
        return sum;
    }
}
