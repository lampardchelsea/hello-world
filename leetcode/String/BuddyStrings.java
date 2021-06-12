/**
Refer to
https://leetcode.com/problems/buddy-strings/
Given two strings s and goal, return true if you can swap two letters in s so the result is equal to goal, otherwise, return false.

Swapping letters is defined as taking two indices i and j (0-indexed) such that i != j and swapping the characters at s[i] and s[j].

For example, swapping at indices 0 and 2 in "abcd" results in "cbad".

Example 1:
Input: s = "ab", goal = "ba"
Output: true
Explanation: You can swap s[0] = 'a' and s[1] = 'b' to get "ba", which is equal to goal.

Example 2:
Input: s = "ab", goal = "ab"
Output: false
Explanation: The only letters you can swap are s[0] = 'a' and s[1] = 'b', which results in "ba" != goal.

Example 3:
Input: s = "aa", goal = "aa"
Output: true
Explanation: You can swap s[0] = 'a' and s[1] = 'a' to get "aa", which is equal to goal.

Example 4:
Input: s = "aaaaaaabc", goal = "aaaaaaacb"
Output: true

Constraints:
1 <= s.length, goal.length <= 2 * 104
s and goal consist of lowercase letters.
*/

// Solution 1: Three cases
// Similar question: 1790. Check If One String Swap Can Make Strings Equal
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/String/CheckIfOneStringSwapCanMakeStringsEqual.java

// Refer to
// https://leetcode.com/problems/buddy-strings/discuss/141780/Easy-Understood
/**
1. If A.length() != B.length(): no possible swap
2. If A == B, we need swap two same characters. Check is duplicated char in A.
3. In other cases, we find index for A[i] != B[i]. There should be only 2 diffs and it's our one swap.

    public boolean buddyStrings(String A, String B) {
        if (A.length() != B.length()) return false;
        if (A.equals(B)) {
            Set<Character> s = new HashSet<Character>();
            for (char c : A.toCharArray()) s.add(c);
            return s.size() < A.length();
        }
        List<Integer> dif = new ArrayList<>();
        for (int i = 0; i < A.length(); ++i) if (A.charAt(i) != B.charAt(i)) dif.add(i);
        return dif.size() == 2 && A.charAt(dif.get(0)) == B.charAt(dif.get(1)) && A.charAt(dif.get(1)) == B.charAt(dif.get(0));
    }
*/
class Solution {
    public boolean buddyStrings(String a, String b) {
        // If A.length() != B.length(): no possible swap
        int len_a = a.length();
        int len_b = b.length();
        if(len_a != len_b) {
            return false;
        }
        // If A == B, we need swap two same characters. Check is duplicated char in A.
        // Test case: "aa" and "aa" OR "ab" and "ab"
        if(a.equals(b)) {
            Set<Character> set = new HashSet<Character>();
            for(char c : a.toCharArray()) {
                set.add(c);
            }
            return set.size() < a.length();
        }
        // In other cases, we find index for A[i] != B[i]. 
        // There should be only two diffs and it's our one swap.
        int dif1 = -1;
        int dif2 = -1;
        for(int i = 0; i < len_a; i++) {
            if(dif1 == -1 && a.charAt(i) != b.charAt(i)) {
                dif1 = i;
                continue;
            }
            if(dif1 != -1) {
                if(dif2 == -1 && a.charAt(i) != b.charAt(i)) {
                    if(a.charAt(i) == b.charAt(dif1) && a.charAt(dif1) == b.charAt(i)) {
                        dif2 = i;
                        continue;
                    } else {
                        return false;
                    }
                }
            }
            if(dif1 != -1 && dif2 != -1) {
                if(a.charAt(i) != b.charAt(i)) {
                    return false;
                }
            }
        }
        // Only find one different not able to switch
        if(dif1 != -1 && dif2 == -1) {
            return false;
        }
        return true;
    }
}
