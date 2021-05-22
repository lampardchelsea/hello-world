/**
Refer to
https://leetcode.com/problems/minimum-deletions-to-make-string-balanced/
You are given a string s consisting only of characters 'a' and 'b'

You can delete any number of characters in s to make s balanced. s is balanced if there is no pair of indices (i,j) such that i < j and s[i] = 'b' and s[j]= 'a'.

Return the minimum number of deletions needed to make s balanced.

Example 1:
Input: s = "aababbab"
Output: 2
Explanation: You can either:
Delete the characters at 0-indexed positions 2 and 6 ("aababbab" -> "aaabbb"), or
Delete the characters at 0-indexed positions 3 and 6 ("aababbab" -> "aabbbb").

Example 2:
Input: s = "bbaaaaabb"
Output: 2
Explanation: The only solution is to delete the first two characters.

Constraints:
1 <= s.length <= 105
s[i] is 'a' or 'b'
*/

// Solution 1: DP, exactly same as 926. Flip String to Monotone Increasing
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DynamicProgramming/FlipStringToMonotoneIncreasing.java
/**
This is a typical case of DP.
Let's see the sub-question of DP first.
Suppose that you have a string s, and the solution to the mono increase question is already solved. That is, for string s, counter_flip 
flips are required for the string, and there were counter_one '1's in the original string s.
Let's see the next step of DP.
Within the string s, a new incoming character, say ch, is appended to the original string. The question is that, how should counter_flip 
be updated, based on the sub-question? We should discuss it case by case.
When '1' comes, no more flip should be applied, since '1' is appended to the tail of the original string.
When '0' comes, things become a little bit complicated. There are two options for us: flip the newly appended '0' to '1', after counter_flip 
flips for the original string; or flip counter_one '1' in the original string to '0'. Hence, the result of the next step of DP, in the '0' 
case, is std::min(counter_flip + 1, counter_one);.
Based on these analysis, the solution comes.
class Solution {
public:
    int minFlipsMonoIncr(const std::string& S, int counter_one  = 0, int counter_flip = 0) {
        for (auto ch : S) counter_flip = std::min(counter_one += ch - '0', counter_flip + '1' - ch);
        return counter_flip;
    }
};
If you find the above snippet of code is somewhat difficult to understand, try the below one.
class Solution {
public:
    int minFlipsMonoIncr(const std::string& S, int counter_one  = 0, int counter_flip = 0) {
        for (auto ch : S) {
            if (ch == '1') {
                ++counter_one;
            } else {
                ++counter_flip;
            }
            counter_flip = std::min(counter_one, counter_flip);
        }
        return counter_flip;
    }
};
*/

// https://leetcode.com/problems/minimum-deletions-to-make-string-balanced/discuss/935701/DP-solution-beats-100-with-explanation
/**
class Solution {
    public int minimumDeletions(String s) {
        int l = s.length();
		//dp stores number of chars to remove to make s.substring(0, i) valid
        int[] dp = new int[l + 1];
        int bcount = 0;
        for (int i = 0; i < l; i++) {
            if (s.charAt(i) == 'a') {
                //case 1: keep current a. ==> prev chars must be a...a
                //so need to remove all 'b' chars before i, which is bcount
                
                //case 2: remove current a ==> prev chars must be a...ab...b
                //so need to remove current a and whatever makes substring before current i valid which is dp[i];
                dp[i + 1] = Math.min(dp[i] + 1, bcount);
            } else {
                //since it is always valid to append 'b' if substring before current i is valid, so just copy whatever makes substring before i valid which is dp[i];
                dp[i + 1] = dp[i];
                bcount++;
            }
        }
        return dp[l];
    }
}
*/
class Solution {
    public int minimumDeletions(String s) {
        int count_b = 0;
        int count_flip = 0;
        for(char c : s.toCharArray()) {
            if(c == 'b') {
                count_b++;
            } else {
                count_flip++;
            }
            count_flip = Math.min(count_b, count_flip);
        }
        return count_flip;
    }
}
