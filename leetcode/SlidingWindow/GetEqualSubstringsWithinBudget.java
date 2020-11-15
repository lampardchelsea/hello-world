/**
Refer to
https://leetcode.com/problems/get-equal-substrings-within-budget/
You are given two strings s and t of the same length. You want to change s to t. Changing the i-th character of s 
to i-th character of t costs |s[i] - t[i]| that is, the absolute difference between the ASCII values of the characters.

You are also given an integer maxCost.

Return the maximum length of a substring of s that can be changed to be the same as the corresponding substring of twith 
a cost less than or equal to maxCost.

If there is no substring from s that can be changed to its corresponding substring from t, return 0.

Example 1:
Input: s = "abcd", t = "bcdf", maxCost = 3
Output: 3
Explanation: "abc" of s can change to "bcd". That costs 3, so the maximum length is 3.

Example 2:
Input: s = "abcd", t = "cdef", maxCost = 3
Output: 1
Explanation: Each character in s costs 2 to change to charactor in t, so the maximum length is 1.

Example 3:
Input: s = "abcd", t = "acde", maxCost = 0
Output: 1
Explanation: You can't make any change, so the maximum length is 1.

Constraints:
1 <= s.length, t.length <= 10^5
0 <= maxCost <= 10^6
s and t only contain lower case English letters.
*/

// Solution 1: Not fixed length sliding window
// Refer to
// https://leetcode.com/problems/get-equal-substrings-within-budget/discuss/392837/JavaC%2B%2BPython-Sliding-Window
/**
Intuition
Change the input of string s and t into an array of difference.
Then it's a standard sliding window problem.

Complexity
Time O(N) for one pass
Space O(1)
Java:
    public int equalSubstring(String s, String t, int k) {
        int n = s.length(), i = 0, j;
        for (j = 0; j < n; ++j) {
            k -= Math.abs(s.charAt(j) - t.charAt(j));
            if (k < 0) {
                k += Math.abs(s.charAt(i) - t.charAt(i));
                ++i;
            }
        }
        return j - i;
    }
*/

// Refer to
// https://leetcode.com/problems/get-equal-substrings-within-budget/discuss/392833/C%2B%2B-Sliding-window-O(n)-and-Prefix-sum-O(nlogn)-implementations
/**
Observation
Since we need to find the maximum substring that can be replaced we can actually breakdown this problem to an array of integers that represent 
the replacement cost of s[i] to t[i] and then find the maximum length of continuous integers in that array whose sum <= maxCost.
eg:
s = "aabcd"
t = "zbdag"
The array to find the maximum length on comes out to[1,1,2,2,3].

This problem can now be solved in many ways, two of which are descibed below.
Somewhat similar to 209. Minimum Size Subarray Sum

Sliding window solution
In general a sliding window is when we keep increasing the size of the window by increasing the right end to fit our goal, 
if it increases the goal we reduce the window by sliding the left end until it again fits the goal, this ensures that the 
maximum window size is attained.

class Solution {
public:
    int equalSubstring(string s, string t, int maxCost) 
    {
        int start=0,end=0,sum=0;
        while(end<s.length())
        {
            sum+=abs(s[end]-t[end++]);
            if(sum>maxCost)
                sum-=abs(s[start]-t[start++]);
        }
        return end-start;
    }
};
Complexity
Time: O(n). Since each element is added and removed once at max.
Space: O(1). Since we get the elemets of sum array on the fly.
*/
class Solution {
    public int equalSubstring(String s, String t, int maxCost) {
        int maxLen = 0;
        int curCost = 0;
        int i = 0;
        for(int j = 0; j < s.length(); j++) {
            curCost += Math.abs(s.charAt(j) - t.charAt(j));
            if(curCost > maxCost) {
                curCost -= Math.abs(s.charAt(i) - t.charAt(i));
                i++;
            }
        }
        return t.length() - i;
    }
}
