/**
Refer to
https://leetcode.com/problems/backspace-string-compare/
Given two strings S and T, return if they are equal when both are typed into empty text editors. # means a backspace character.

Note that after backspacing an empty text, the text will continue empty.

Example 1:
Input: S = "ab#c", T = "ad#c"
Output: true
Explanation: Both S and T become "ac".

Example 2:
Input: S = "ab##", T = "c#d#"
Output: true
Explanation: Both S and T become "".

Example 3:
Input: S = "a##c", T = "#a#c"
Output: true
Explanation: Both S and T become "c".

Example 4:
Input: S = "a#c", T = "b"
Output: false
Explanation: S becomes "c" while T becomes "b".

Note:
1 <= S.length <= 200
1 <= T.length <= 200
S and T only contain lowercase letters and '#' characters.

Follow up:
Can you solve it in O(N) time and O(1) space?
*/

// Solution 1: Stack
class Solution {
    public boolean backspaceCompare(String S, String T) {
        Stack<Character> s1 = new Stack<Character>();
        Stack<Character> s2 = new Stack<Character>();
        char[] s_chars = S.toCharArray();
        char[] t_chars = T.toCharArray();
        for(char c : s_chars) {
            if(c == '#' && !s1.isEmpty()) {
                s1.pop();
            } else if(c != '#') {
                s1.push(c);
            }
        }
        for(char c : t_chars) {
            if(c == '#' && !s2.isEmpty()) {
                s2.pop();
            } else if(c != '#') {
                s2.push(c);
            }       
        }
        while(!s1.isEmpty() && !s2.isEmpty()) {
            char c1 = s1.pop();
            char c2 = s2.pop();
            if(c1 != c2) {
                return false;
            }
        }
        // Test out by:
        // Input S = "bxj##tw", T = "bxj###tw"
        // Output true 
        // Expected false
        if(!s1.isEmpty() || !s2.isEmpty()) {
            return false;
        }
        return true;
    }
}

// Follow up
// How to resovle the issue in O(N) time and O(1) space ?
// Solution 2: Two Pointers
// Refer to
// https://leetcode.com/problems/backspace-string-compare/discuss/135603/JavaC%2B%2BPython-O(N)-time-and-O(1)-space
/**
Intuition
The intuition and quick methode is to find the final text result.
You can just use a string if you don't care cost on string modification.
Or you can use a stack or string builder to do it in O(N).

Use stack to avoid string modification.
Time O(N) and space O(N).

    def backspaceCompare(self, S, T):
        def back(res, c):
            if c != '#': res.append(c)
            elif res: res.pop()
            return res
        return reduce(back, S, []) == reduce(back, T, [])

Follow up: O(1) Space
Can you do it in O(N) time and O(1) space?
I believe you have one difficulty here:
When we meet a char, we are not sure if it will be still there or be deleted.

However, we can do a back string compare (just like the title of problem).
If we do it backward, we meet a char and we can be sure this char won't be deleted.
If we meet a '#', it tell us we need to skip next lowercase char.

The idea is that, read next letter from end to start.
If we meet #, we increase the number we need to step back, until back = 0

Java:
    public boolean backspaceCompare(String S, String T) {
        int i = S.length() - 1, j = T.length() - 1, back;
        while (true) {
            back = 0;
            while (i >= 0 && (back > 0 || S.charAt(i) == '#')) {
                back += S.charAt(i) == '#' ? 1 : -1;
                i--;
            }
            back = 0;
            while (j >= 0 && (back > 0 || T.charAt(j) == '#')) {
                back += T.charAt(j) == '#' ? 1 : -1;
                j--;
            }
            if (i >= 0 && j >= 0 && S.charAt(i) == T.charAt(j)) {
                i--;
                j--;
            } else {
                break;
            }
        }
        return i == -1 && j == -1;
    }
*/
class Solution {
    public boolean backspaceCompare(String S, String T) {
        int i = S.length() - 1;
        int j = T.length() - 1;
        int countS = 0;
        int countT = 0;
        while(i >= 0 || j >= 0) {
            while(i >= 0 && (S.charAt(i) == '#' || countS > 0)) {
                if(S.charAt(i) == '#') {
                    countS++;
                } else {
                    countS--;
                }
                i--;
            }
            while(j >= 0 && (T.charAt(j) == '#' || countT > 0)) {
                if(T.charAt(j) == '#') {
                    countT++;
                } else {
                    countT--;
                }
                j--;
            }
            if(i >= 0 && j >= 0 && S.charAt(i) == T.charAt(j)) {
                i--;
                j--;
            } else {
                // Do not directly return true here
                // Test out by:
                // Input: S ="ab##", T = "c#d#"
                // Output: false
                // Expected: true
                break;
            }
        }
        return i == -1 && j == -1;
    }
}
