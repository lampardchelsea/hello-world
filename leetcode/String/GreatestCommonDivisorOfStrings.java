/**
 Refer to
 https://leetcode.com/problems/greatest-common-divisor-of-strings/
 For strings S and T, we say "T divides S" if and only if S = T + ... + T  
 (T concatenated with itself 1 or more times)

Return the largest string X such that X divides str1 and X divides str2.

Example 1:
Input: str1 = "ABCABC", str2 = "ABC"
Output: "ABC"

Example 2:
Input: str1 = "ABABAB", str2 = "ABAB"
Output: "AB"

Example 3:
Input: str1 = "LEET", str2 = "CODE"
Output: ""

Note:
1 <= str1.length <= 1000
1 <= str2.length <= 1000
str1[i] and str2[i] are English uppercase letters.
*/
class Solution {
    public String gcdOfStrings(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        // String shortStr = (len1 < len2 ? str1 : str2);
        // String longStr = (len1 >= len2 ? str1 : str2);
        String shortStr = "";
        //String longStr = "";
        if(len1 < len2) {
            shortStr = str1;
            //longStr = str2;
        } else {
            shortStr = str2;
            //longStr = str1;
        }
        for(int i = shortStr.length(); i >= 0; i--) {
            String substr = shortStr.substring(0, i);
            if(repeatedToken(substr, str1) && repeatedToken(substr, str2)) {
                return substr;
            }
        }
        return "";
    }
    
    private boolean repeatedToken(String substr, String s) {
        int substrLen = substr.length();
        if(substrLen == 0) {
            return false;
        }
        int sLen = s.length();
        if(sLen % substrLen != 0) {
            return false;
        } else {
            int times = sLen / substrLen;
            for(int i = 0; i < times; i++) {
                String cur = s.substring(0 + substrLen * i, substrLen + substrLen * i);
                if(!cur.equals(substr)) {
                    return false;
                }
            }
        }
        return true;
    }
}
