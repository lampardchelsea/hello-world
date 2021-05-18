/**
Refer to
https://leetcode.com/problems/print-words-vertically/
Given a string s. Return all the words vertically in the same order in which they appear in s.
Words are returned as a list of strings, complete with spaces when is necessary. (Trailing spaces are not allowed).
Each word would be put on only one column and that in one column there will be only one word.

Example 1:
Input: s = "HOW ARE YOU"
Output: ["HAY","ORO","WEU"]
Explanation: Each word is printed vertically. 
 "HAY"
 "ORO"
 "WEU"
 
Example 2:
Input: s = "TO BE OR NOT TO BE"
Output: ["TBONTB","OEROOE","   T"]
Explanation: Trailing spaces is not allowed. 
"TBONTB"
"OEROOE"
"   T"

Example 3:
Input: s = "CONTEST IS COMING"
Output: ["CIC","OSO","N M","T I","E N","S G","T"]

Constraints:
1 <= s.length <= 200
s contains only upper case English letters.
It's guaranteed that there is only one space between 2 words.
*/

// Solution 1: Using '#' to mask non-exist position and then remove in final build
class Solution {
    public List<String> printVertically(String s) {
        // How many tailing spaces for current string needed 
        // based on the max length of string after it
        String[] strs = s.split("\\s+");
        int n = strs.length;
        int maxLen = 0;
        int[] all_len = new int[n];
        for(int i = 0; i < n; i++) {
            all_len[i] = strs[i].length();
            maxLen = Math.max(maxLen, all_len[i]);
        }
        int[] tailing_spaces_needed = new int[n];
        for(int i = 0; i < n; i++) {
            int curLen = all_len[i];
            int maxLenAfter = 0;
            for(int j = i + 1; j < n; j++) {
                maxLenAfter = Math.max(maxLenAfter, all_len[j]);
            }
            tailing_spaces_needed[i] = curLen > maxLenAfter ? 0 : maxLenAfter - curLen;
        }
        for(int i = 0; i < n; i++) {
            StringBuilder sb = new StringBuilder(strs[i]);
            int spaces = tailing_spaces_needed[i];
            for(int j = 0; j < spaces; j++) {
                sb.append(" ");
            }
            // Most tricky part: since we have to scan veritically will encounter
            // char not exist on certain poisition, make up non-exist position 
            // with other letter, and when we build final string just skip them 
            for(int j = 0; j < maxLen - strs[i].length() - spaces; j++) {
                sb.append("#");
            }
            strs[i] = sb.toString();
        }
        List<String> result = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < maxLen; i++) {
            for(int j = 0; j < strs.length; j++) {
                char c = strs[j].charAt(i);
                if(c != '#') {
                    sb.append(c);
                }
            }
            result.add(sb.toString());
            sb.setLength(0);
        }
        return result;
    }
}

// Solution 2: Remove the trailing space for each string.
// Refer to
// https://leetcode.com/problems/print-words-vertically/discuss/484322/JavaPython-3-Straight-forward-codes-w-brief-explanation-and-analysis.
/**
1.Find the size of the longest word;
2.Iterate chars of all words at index 0, 1, ..., max - 1, pack with space if the index if out of the range of current word;
3.Remove the trailing space for each string.

    public List<String> printVertically(String s) {
        String[] words = s.split(" ");
        int mx = 0;
        for (int i = 0; i < words.length; ++i)
            mx = Math.max(mx, words[i].length());
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < mx; ++i) {
            StringBuilder sb = new StringBuilder();
            for (String word : words)
                sb.append(i < word.length() ? word.charAt(i) : " ");
            while (sb.charAt(sb.length() - 1) == ' ')
                sb.deleteCharAt(sb.length() - 1); // remove trailing space.
            ans.add(sb.toString());
        }
        return ans;
    }

The above strip trailing space operation can be done by using stripTrailing() method: - credit to @KenpachiZaraki1

That is, we can rewrite
            while (sb.charAt(sb.length() - 1) == ' ')
                sb.deleteCharAt(sb.length() - 1); // remove trailing space.
            ans.add(sb.toString());
to
            ans.add(sb.toString().stripTrailing());
*/
class Solution {
    public List<String> printVertically(String s) {
        String[] words = s.split(" ");
        int mx = 0;
        for (int i = 0; i < words.length; ++i)
            mx = Math.max(mx, words[i].length());
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < mx; ++i) {
            StringBuilder sb = new StringBuilder();
            for (String word : words)
                sb.append(i < word.length() ? word.charAt(i) : " ");
            while (sb.charAt(sb.length() - 1) == ' ')
                sb.deleteCharAt(sb.length() - 1); // remove trailing space.
            ans.add(sb.toString());
        }
        return ans;
    }
}


