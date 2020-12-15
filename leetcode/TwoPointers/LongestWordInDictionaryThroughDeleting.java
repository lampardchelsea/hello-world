/**
 * Refer to
 * https://leetcode.com/problems/longest-word-in-dictionary-through-deleting/description/
 * Given a string and a string dictionary, find the longest string in the dictionary that can be formed by deleting some characters of the given string. If there are more than one possible results, return the longest word with the smallest lexicographical order. If there is no possible result, return the empty string.

    Example 1:
    Input:
    s = "abpcplea", d = ["ale","apple","monkey","plea"]

    Output: 
    "apple"
    Example 2:
    Input:
    s = "abpcplea", d = ["a","b","c"]

    Output: 
    "a"
    Note:
    All the strings in the input will only contain lower-case letters.
    The size of the dictionary won't exceed 1,000.
    The length of all the strings in the input won't exceed 1,000.
 *
 *
 * Solution
 * https://leetcode.com/problems/longest-word-in-dictionary-through-deleting/discuss/99588/Short-Java-Solutions-Sorting-Dictionary-and-Without-Sorting/103703
*/
class Solution {
    public String findLongestWord(String s, List<String> d) {
        String result = "";
        if(s == null || s.length() == 0 || d == null || d.size() == 0) {
            return "";
        }
        for(String str : d) {
            if(isSubsequence(str, s)) {
                if(str.length() > result.length()) {
                    result = str;
                }
                if(str.length() == result.length() && str.compareTo(result) < 0) {
                    result = str;
                }
            }
        }
        return result;
    }
    
    private boolean isSubsequence(String str, String s) {
        int i = 0;
        int j = 0;
        while(i < str.length() && j < s.length()) {
            if(str.charAt(i) == s.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == str.length();
    }
}
