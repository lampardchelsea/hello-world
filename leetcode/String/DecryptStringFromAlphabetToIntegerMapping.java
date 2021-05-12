/**
Refer to
https://leetcode.com/problems/decrypt-string-from-alphabet-to-integer-mapping/
Given a string s formed by digits ('0' - '9') and '#' . We want to map s to English lowercase characters as follows:

Characters ('a' to 'i') are represented by ('1' to '9') respectively.
Characters ('j' to 'z') are represented by ('10#' to '26#') respectively. 
Return the string formed after mapping.

It's guaranteed that a unique mapping will always exist.

Example 1:
Input: s = "10#11#12"
Output: "jkab"
Explanation: "j" -> "10#" , "k" -> "11#" , "a" -> "1" , "b" -> "2".

Example 2:
Input: s = "1326#"
Output: "acz"

Example 3:
Input: s = "25#"
Output: "y"

Example 4:
Input: s = "12345678910#11#12#13#14#15#16#17#18#19#20#21#22#23#24#25#26#"
Output: "abcdefghijklmnopqrstuvwxyz"

Constraints:
1 <= s.length <= 1000
s[i] only contains digits letters ('0'-'9') and '#' letter.
s will be valid string such that mapping is always possible
*/

// Solution: Check [i + 2]
class Solution {
    public String freqAlphabets(String s) {
        StringBuilder sb = new StringBuilder();
        char[] chars = new char[] {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        int n = s.length();
        int i = 0;
        for(i = 0; i < n - 2; i++) {
            if(s.charAt(i + 2) != '#') {
                sb.append(chars[s.charAt(i) - '1']);
            } else {
                String tmp = "" + s.charAt(i) + s.charAt(i + 1);
                sb.append(chars[Integer.valueOf(tmp) - 1]);
                i += 2;
            }
        }
        while(i < n) {
            sb.append(chars[s.charAt(i) - '1']);
            i++;
        }
        return sb.toString();
    }
}

// Better style
// Refer to
// https://leetcode.com/problems/decrypt-string-from-alphabet-to-integer-mapping/discuss/470686/Check-i-%2B-2
/**
We can simply check whehter '#' character appears at position i + 2 to determine which decription rule to apply.

string freqAlphabets(string s) {
  string res;
  for (int i = 0; i < s.size(); ++i) {
    if (i < s.size() - 2 && s[i + 2] == '#') {
      res += 'j' + (s[i] - '1') * 10 + s[i + 1] - '0';
      i += 2;
    }
    else res += 'a' + (s[i] - '1');
  }
  return res;
}
*/
class Solution {
    public String freqAlphabets(String s) {
        StringBuilder sb = new StringBuilder();
        int n = s.length();
        int i = 0;
        for(i = 0; i < n - 2; i++) {
            if(s.charAt(i + 2) != '#') {
                sb.append((char)('a' + s.charAt(i) - '1'));
            } else {
                int m = (s.charAt(i) - '0') * 10 + s.charAt(i + 1) - '0';
                sb.append((char)('j' + m - 10));
                i += 2;
            }
        }
        while(i < n) {
            sb.append((char)('a' + s.charAt(i) - '1'));
            i++;
        }
        return sb.toString();
    }
}

