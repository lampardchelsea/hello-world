/**
Refer to
https://leetcode.com/problems/count-substrings-that-differ-by-one-character/
Given two strings s and t, find the number of ways you can choose a non-empty substring of s and replace a single 
character by a different character such that the resulting substring is a substring of t. In other words, find the 
number of substrings in s that differ from some substring in t by exactly one character.

For example, the underlined substrings in "computer" and "computation" only differ by the 'e'/'a', so this is a valid way.

Return the number of substrings that satisfy the condition above.

A substring is a contiguous sequence of characters within a string.

Example 1:
Input: s = "aba", t = "baba"
Output: 6
Explanation: The following are the pairs of substrings from s and t that differ by exactly 1 character:
("aba", "baba")
("aba", "baba")
("aba", "baba")
("aba", "baba")
("aba", "baba")
("aba", "baba")
The underlined portions are the substrings that are chosen from s and t.

Example 2:
Input: s = "ab", t = "bb"
Output: 3
Explanation: The following are the pairs of substrings from s and t that differ by 1 character:
("ab", "bb")
("ab", "bb")
("ab", "bb")
The underlined portions are the substrings that are chosen from s and t.

Example 3:
Input: s = "a", t = "a"
Output: 0

Example 4:
Input: s = "abe", t = "bbc"
Output: 10

Constraints:
1 <= s.length, t.length <= 100
s and t consist of lowercase English letters only.
*/

// Solution 1: Two Pointers Brute Force O(N^3)
// Refer to
// https://leetcode.com/problems/count-substrings-that-differ-by-one-character/discuss/917701/C%2B%2BJavaPython3-O(n-3)-greater-O(n-2)
/**
I misread the description and got wrong answer first time. Then I got TLE as I tried to use a map or a trie. I thought about 
using a rolling hash, but it sounded too complicated.

Finally, I looked at the constraint and thought a simple cubic solution should do. We analyze all pairs of starting points in our 
strings (which is O(n ^2)), and the trick is to count substrings in a linear time.

Update: we can also count substrings in O(1) time if we precompute. Check approach 3 below.

Approach 1: Compare and Expand
We compare strings starting from s[i] and t[j], and track the number of mismatched characters miss:

0: continue our comparison
1: we found a substring - increment res and continue our comparison
2: break from the loop.

Java
public int countSubstrings(String s, String t) {
    int res = 0;
    for (int i = 0; i < s.length(); ++i) {
        for (int j = 0; j < t.length(); ++j) {
            int miss = 0;
            for (int pos = 0; i + pos < s.length() && j + pos < t.length(); ++pos) {
                if (s.charAt(i + pos) != t.charAt(j + pos) && ++miss > 1)
                    break;
                res += miss;
            }
        }
    }
    return res;        
}
*/
class Solution {
    public int countSubstrings(String s, String t) {
        int result = 0;
        for(int i = 0; i < s.length(); i++) {
            for(int j = 0; j < t.length(); j++) {
                int count = 0;
                for(int k = 0; i + k < s.length() && j + k < t.length(); k++) {
                    if(s.charAt(i + k) != t.charAt(j + k)) {
                        count++;
                        if(count > 1) {
                            break;
                        }
                    }
                    result += count;
                }
            }
        }
        return result;
    }
}

// Solution 2: Same trick as 828. Count Unique Characters of All Substrings of a Given String
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/TwoPointers/CountUniqueCharactersOfAllSubstringsOfAGivenString.java
// https://leetcode.com/problems/count-substrings-that-differ-by-one-character/discuss/918183/Java-3ms-or-easy-to-understand-code
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/TwoPointers/Document/Count_Substrings_That_Differ_by_One_Character.docx
// https://leetcode.com/problems/count-substrings-that-differ-by-one-character/discuss/917701/C%2B%2BJavaPython3-O(n-3)-greater-O(n-2)
/**
Approach 2: Process Missmatches
Alternativelly, we can process all pair of missmatched characters s[i] and t[j]. Starting from the missmatch, we count matching 
characters to the left l and to the right r. The total number of substrings will be l * r.

C++

int countSubstrings(string &s, string &t) {
    int res = 0;
    for (int i = 0; i < s.size(); ++i)
        for (int j = 0; j < t.size(); ++j) {
            if (s[i] != t[j]) {
                int l = 1, r = 1;
                while (min(i - l, j - l) >= 0 && s[i - l] == t[j - l])
                    ++l;
                while (i + r < s.size() && j + r < t.size() && s[i + r] == t[j + r])
                    ++r;
                res += l * r;
            }
        }
    return res;
}
Complexity Analysis
Same as for approach above.
*/
class Solution {
    public int countSubstrings(String s, String t) {
        int count = 0;
        for(int i = 0; i < s.length(); i++) {
            for(int j = 0; j < t.length(); j++) {
                if(s.charAt(i) != t.charAt(j)) {
                    int l = 1;
                    int r = 1;
                    while(i - l >= 0 && j - l >= 0 && s.charAt(i - l) == t.charAt(j - l)) {
                        l++;
                    }
                    while(i + r < s.length() && j + r < t.length() && s.charAt(i + r) == t.charAt(j + r)) {
                        r++;
                    }
                    count += l * r;
                }
            }
        }
        return count;
    }
}

// Solution 3: DP
// Refer to
// https://leetcode.com/problems/count-substrings-that-differ-by-one-character/discuss/917701/C%2B%2BJavaPython3-O(n-3)-greater-O(n-2)
/**
Approach 3: Process Missmatches + DP
This is the same as approach 2 above, but we use DP to precompute sizes of a matching substring ending at position [i][j] (dpl), 
and starting from position [i][j] (dpr).
C++

int countSubstrings(string &s, string &t) {
    int res = 0;
    int dpl[101][101] = {}, dpr[101][101] = {};
    for (int i = 1; i <= s.size(); ++i)
        for (int j = 1; j <= t.size(); ++j)
            if (s[i - 1] == t[j - 1])
                dpl[i][j] = 1 + dpl[i - 1][j - 1];
    for (int i = s.size(); i > 0; --i)
        for (int j = t.size(); j > 0; --j)
            if (s[i - 1] == t[j - 1])
                dpr[i - 1][j - 1] = 1 + dpr[i][j];
    for (int i = 0; i < s.size(); ++i)
        for (int j = 0; j < t.size(); ++j)
            if (s[i] != t[j])
                res += (dpl[i][j] + 1) * (dpr[i + 1][j + 1] + 1);
    return res;
}
Complexity Analysis

Time: O(n ^ 2), we have two nested loops to precompute sizes, and count substrings.
Memory: O(n ^ 2) for the memoisation.
*/
