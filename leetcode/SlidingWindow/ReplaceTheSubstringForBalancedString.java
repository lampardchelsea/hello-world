/**
Refer to
https://leetcode.com/problems/replace-the-substring-for-balanced-string/
You are given a string containing only 4 kinds of characters 'Q', 'W', 'E' and 'R'.

A string is said to be balanced if each of its characters appears n/4 times where n is the length of the string.

Return the minimum length of the substring that can be replaced with any other string of the same length to make the original string s balanced.

Return 0 if the string is already balanced.

Example 1:
Input: s = "QWER"
Output: 0
Explanation: s is already balanced.

Example 2:
Input: s = "QQWE"
Output: 1
Explanation: We need to replace a 'Q' to 'R', so that "RQWE" (or "QRWE") is balanced.

Example 3:
Input: s = "QQQW"
Output: 2
Explanation: We can replace the first "QQ" to "ER". 

Example 4:
Input: s = "QQQQ"
Output: 3
Explanation: We can replace the last 3 'Q' to make s = "QWER".

Constraints:
1 <= s.length <= 10^5
s.length is a multiple of 4
s contains only 'Q', 'W', 'E' and 'R'.
*/

// Solution 1: Not fixed length sliding window
// Refer to
// https://leetcode.com/problems/replace-the-substring-for-balanced-string/discuss/408978/JavaC%2B%2BPython-Sliding-Window
/**
Intuition
We want a minimum length of substring,
which leads us to the solution of sliding window.
Specilly this time we don't care the count of elements inside the window,
we want to know the count outside the window.

Explanation
One pass the all frequency of "QWER".
Then slide the windon in the string s.

Imagine that we erase all character inside the window,
as we can modyfy it whatever we want,
and it will always increase the count outside the window.

So we can make the whole string balanced,
as long as max(count[Q],count[W],count[E],count[R]) <= n / 4.

Important
Does i <= j + 1 makes more sense than i <= n.
Strongly don't think, and i <= j + 1 makes no sense.

Answer the question first:
Why do we need such a condition in sliding window problem?

Actually, we never need this condition in sliding window solution
(Check all my other solutions link at the bottom).

Usually count the element inside sliding window,
and i won't be bigger than j because nothing left in the window.

The only reason that we need a condition is in order to prevent index out of range.
And how do we do that? Yes we use i < n

Does i <= j + 1 even work?
When will i even reach j + 1?
Does i <= j + 1 work better than i <= j?

Please upvote for this important tip.
Also let me know if there is any unclear, glad to hear different voices.
But please, have a try, and show the code if necessary.

Some people likes to criticize without even a try,
and solve the problem by talking.
Why talk to me? Talk to the white board.

Complexity
Time O(N), one pass for counting, one pass for sliding window
Space O(1)

Java
    public int balancedString(String s) {
        int[] count = new int[128];
        int n = s.length(), res = n, i = 0, k = n / 4;
        for (int j = 0; j < n; ++j) {
            ++count[s.charAt(j)];
        }
        for (int j = 0; j < n; ++j) {
            --count[s.charAt(j)];
            while (i < n && count['Q'] <= k && count['W'] <= k && count['E'] <= k && count['R'] <= k) {
                res = Math.min(res, j - i + 1);
                ++count[s.charAt(i++)];
            }
        }
        return res;
    }
*/
class Solution {
    public int balancedString(String s) {
        int n = s.length();
        int result = n;
        int k = n / 4;
        int i = 0;
        int[] count = new int[128];
        for(int j = 0; j < n; j++) {
            count[s.charAt(j)]++;
        }
        for(int j = 0; j < n; j++) {
            count[s.charAt(j)]--;
            while(i < n && count['Q'] <= k && count['W'] <= k && count['E'] <= k && count['R'] <= k) {
                result = Math.min(result, j - i + 1);
                count[s.charAt(i)]++;
                i++;
            }
        }
        return result;
    }
}
