/**
 Refer to
 https://leetcode.com/problems/find-common-characters/
 Given an array A of strings made only from lowercase letters, return a list of all characters that 
 show up in all strings within the list (including duplicates).  For example, if a character occurs 
 3 times in all strings but not 4 times, you need to include that character three times in the final answer.

You may return the answer in any order.
Example 1:
Input: ["bella","label","roller"]
Output: ["e","l","l"]

Example 2:
Input: ["cool","lock","cook"]
Output: ["c","o"]

Note:
1 <= A.length <= 100
1 <= A[i].length <= 100
A[i][j] is a lowercase letter
*/

// Wrong solution 1: 
// Missing considering even have same total number of certain character in given strings,
// if one string contains 1 more this character and the other string contains no such 
// character, it still error out.
//Input: ["acabcddd","bcbdbcbd","baddbadb","cbdddcac","aacbcccd","ccccddda","cababaab","addcaccd"]
//Output: ["a","b","c","c","d","d"]
//Expected: []
class Solution {
    public List<String> commonChars(String[] A) {
        List<String> result = new ArrayList<String>();
        int[] count = new int[26];
        for(int i = 0; i < A.length; i++) {    
            char[] chars = A[i].toCharArray();
            for(int j = 0; j < chars.length; j++) {
                count[chars[j] - 'a']++;
            }
        }
        for(int i = 0; i < 26; i++) {
            if(count[i] != 0 && count[i] / A.length >= 1) {
                for(int j = 0; j < count[i] / A.length; j++) {
                    result.add(String.valueOf((char)('a' + i)));
                }
            }
        }
        return result;
    }
}

// Wrong solution 2:
// Just include integer multiple times of certain characters for all given string together,
// we also need to consider cases like one string contains 2 that char, the other string
// contains 3 that char, 2 + 3 not match integer multiple times of certain characters here,
// but still need to count as 2 happening on result.
//Input: ["bella","label","roller"]
//Output: []
//Expected: ["e","l","l"]
class Solution {
    public List<String> commonChars(String[] A) {
        List<String> result = new ArrayList<String>();
        int[] count = new int[26];
        for(int i = 0; i < A.length; i++) {    
            char[] chars = A[i].toCharArray();
            for(int j = 0; j < chars.length; j++) {
                count[chars[j] - 'a']++;
            }
        }
        for(int i = 0; i < 26; i++) {
            if(count[i] != 0 && count[i] / A.length == 0) {
                for(int j = 0; j < count[i] / A.length; j++) {
                    result.add(String.valueOf((char)('a' + i)));
                }
            }
        }
        return result;
    }
}

// Solution with 2D matrix
class Solution {
    public List<String> commonChars(String[] A) {
        List<String> result = new ArrayList<String>();
        int len = A.length;
        int[][] count = new int[len][26];
        for(int i = 0; i < len; i++) {   
            char[] chars = A[i].toCharArray();
            for(int j = 0; j < chars.length; j++) {
                count[i][chars[j] - 'a']++;
            }
        }
        // Check as column by column, all cell value should
        // same and not be zero
        for(int j = 0; j < 26; j++) {
            // Initial assume present in all string as theoretical possible max value
            int minNumOfTheChar = len;
            for(int i = 0; i < len; i++) {
                if(count[i][j] < minNumOfTheChar) {
                    minNumOfTheChar = count[i][j];
                }
            }
            // Caution: minNumOfTheChar should not be 0, since it should present
            // in every string, if this number as 0 means at least one string
            // not contains this char, we should avoid this case, and if minNumOfTheChar
            // not equal to 0 means every string contains at least one of this char
            // and then we can find out the minimum numbers of this char among all string
			      // And if ignore the theoretical flow, just focus on the effect, we can comment
			      // out the if condition here, since when minNumOfTheChar equals 0, the for
			      // loop will skip it
            //if(minNumOfTheChar != 0) {
                for(int k = 0; k < minNumOfTheChar; k++) {
                    result.add(String.valueOf((char)('a' + j)));
                }
            //}
        }
        return result;
    }
}
