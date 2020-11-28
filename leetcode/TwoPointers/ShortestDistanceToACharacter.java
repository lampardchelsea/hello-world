/**
Refer to
https://leetcode.com/problems/shortest-distance-to-a-character/
Given a string S and a character C, return an array of integers representing the shortest distance from the character C in the string.

Example 1:
Input: S = "loveleetcode", C = 'e'
Output: [3, 2, 1, 0, 1, 0, 0, 1, 2, 2, 1, 0]

Note:
S string length is in [1, 10000].
C is a single character, and guaranteed to be in string S.
All letters in S and C are lowercase.
*/

// Solution 1: Two Pointers + Two side scanning
// Refer to
// https://leetcode.com/problems/shortest-distance-to-a-character/solution/
/**
Approach #1: Min Array [Accepted]
Intuition
For each index S[i], let's try to find the distance to the next character C going left, and going right. 
The answer is the minimum of these two values.

Algorithm
When going left to right, we'll remember the index prev of the last character C we've seen. Then the answer is i - prev.
When going right to left, we'll remember the index prev of the last character C we've seen. Then the answer is prev - i.
We take the minimum of these two answers to create our final answer.
class Solution {
    public int[] shortestToChar(String S, char C) {
        int N = S.length();
        int[] ans = new int[N];
        int prev = Integer.MIN_VALUE / 2;

        for (int i = 0; i < N; ++i) {
            if (S.charAt(i) == C) prev = i;
            ans[i] = i - prev;
        }

        prev = Integer.MAX_VALUE / 2;
        for (int i = N-1; i >= 0; --i) {
            if (S.charAt(i) == C) prev = i;
            ans[i] = Math.min(ans[i], prev - i);
        }

        return ans;
    }
}
*/

// https://leetcode.com/problems/shortest-distance-to-a-character/discuss/125788/C%2B%2BJavaPython-2-Pass-with-Explanation
/**
Solution 1: Record the Position
Initial result array.
Loop twice on the string S.
First forward pass to find shortest distant to character on left.
Second backward pass to find shortest distant to character on right.

In python solution, I merged these two for statement.
We can do the same in C++/Java by:

for (int i = 0; i >= 0; res[n-1] == n ? ++i : --i)
But it will become less readable.

Time complexity O(N)
Space complexity O(N) for output

Java
    public int[] shortestToChar(String S, char C) {
        int n = S.length(), pos = -n, res[] = new int[n];
        for (int i = 0; i < n; ++i) {
            if (S.charAt(i) == C) pos = i;
            res[i] = i - pos;
        }
        for (int i = pos - 1; i >= 0; --i) {
            if (S.charAt(i) == C)  pos = i;
            res[i] = Math.min(res[i], pos - i);
        }
        return res;
    }
*/
class Solution {
    public int[] shortestToChar(String S, char C) {
        int n = S.length();
        int[] result = new int[n];
        int pos = Integer.MIN_VALUE / 2;
        for(int i = 0; i < n; i++) {
            if(S.charAt(i) == C) {
                pos = i;
            }
            result[i] = i - pos;
        }
        pos = Integer.MAX_VALUE / 2;
        for(int i = n - 1; i >= 0; i--) {
            if(S.charAt(i) == C) {
                pos = i;
            }
            result[i] = Math.min(result[i], pos - i);
        }
        return result;
    }
}

