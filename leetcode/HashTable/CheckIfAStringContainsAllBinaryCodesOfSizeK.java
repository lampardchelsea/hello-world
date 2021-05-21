/**
Refer to
https://leetcode.com/problems/check-if-a-string-contains-all-binary-codes-of-size-k/
Given a binary string s and an integer k.

Return true if every binary code of length k is a substring of s. Otherwise, return false.

Example 1:
Input: s = "00110110", k = 2
Output: true
Explanation: The binary codes of length 2 are "00", "01", "10" and "11". They can be all found as substrings at indicies 0, 1, 3 and 2 respectively.

Example 2:
Input: s = "00110", k = 2
Output: true

Example 3:
Input: s = "0110", k = 1
Output: true
Explanation: The binary codes of length 1 are "0" and "1", it is clear that both exist as a substring. 

Example 4:
Input: s = "0110", k = 2
Output: false
Explanation: The binary code "00" is of length 2 and doesn't exist in the array.

Example 5:
Input: s = "0000000001011100", k = 4
Output: false

Constraints:
1 <= s.length <= 5 * 105
s[i] is either '0' or '1'.
1 <= k <= 20
*/

// Solution 1: Using sliding window to traverse all possible binary codes of size k, put them into a set, then check if its size is 2 ^ k.
// Refer to
// https://leetcode.com/problems/check-if-a-string-contains-all-binary-codes-of-size-k/discuss/660546/JavaPython-3-4-and-1-liners-clean-codes-using-set-w-brief-explanation-and-analysis.
/**
Using sliding window to traverse all possible binary codes of size k, put them into a set, then check if its size is 2 ^ k.

    public boolean hasAllCodes(String s, int k) {
        Set<String> seen = new HashSet<>();
        for (int i = k; i <= s.length() && seen.size() < 1 << k; ++i) {
            seen.add(s.substring(i - k, i));
        }
        return seen.size() == 1 << k;
    }
*/

// Style 1: 133ms
class Solution {
    public boolean hasAllCodes(String s, int k) {
        Set<String> set = new HashSet<String>();
        for(int i = k; i <= s.length(); i++) {
            String a = s.substring(i - k, i);
            set.add(a);
        }
        return set.size() == 1 << k;
    }
}

// Style 2: Early terminate 98ms
class Solution {
    public boolean hasAllCodes(String s, int k) {
        int need = 1 << k;
        Set<String> set = new HashSet<String>();
        for(int i = k; i <= s.length(); i++) {
            String a = s.substring(i - k, i);
            if(!set.contains(a)) {
                set.add(a);
                need--;
                if(need == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
