/**
Refer to
https://leetcode.com/problems/camelcase-matching/
A query word matches a given pattern if we can insert lowercase letters to the pattern word so that it equals the query. 
(We may insert each character at any position, and may insert 0 characters.)

Given a list of queries, and a pattern, return an answer list of booleans, where answer[i] is true if and only if queries[i] matches the pattern.

Example 1:
Input: queries = ["FooBar","FooBarTest","FootBall","FrameBuffer","ForceFeedBack"], pattern = "FB"
Output: [true,false,true,true,false]
Explanation: 
"FooBar" can be generated like this "F" + "oo" + "B" + "ar".
"FootBall" can be generated like this "F" + "oot" + "B" + "all".
"FrameBuffer" can be generated like this "F" + "rame" + "B" + "uffer".

Example 2:
Input: queries = ["FooBar","FooBarTest","FootBall","FrameBuffer","ForceFeedBack"], pattern = "FoBa"
Output: [true,false,true,false,false]
Explanation: 
"FooBar" can be generated like this "Fo" + "o" + "Ba" + "r".
"FootBall" can be generated like this "Fo" + "ot" + "Ba" + "ll".

Example 3:
Input: queries = ["FooBar","FooBarTest","FootBall","FrameBuffer","ForceFeedBack"], pattern = "FoBaT"
Output: [false,true,false,false,false]
Explanation: 
"FooBarTest" can be generated like this "Fo" + "o" + "Ba" + "r" + "T" + "est".

Note:
1 <= queries.length <= 100
1 <= queries[i].length <= 100
1 <= pattern.length <= 100
All strings consists only of lower and upper case English letters.
*/

// Solution 1: Two Pointers to find if one string is a subsequence of another
// Refer to
// https://leetcode.com/problems/camelcase-matching/discuss/270006/Java-Easy-Two-Pointers
/**
For each string, macth it with the pattern and pass the result.

The match process uses i for query pointer and j for pattern pointer, each iteration;

If current char query[i] matches pattern[j], increase pattern pointer;
if does not match and query[i] is lowercase, keep going;
if does not match and query[i] is captalized, we should return false.
If this pattern matches, j should equal length of pattern at the end.

Hope this helps.

class Solution {
    public List<Boolean> camelMatch(String[] queries, String pattern) {
        List<Boolean> res = new ArrayList<>();
        char[] patternArr = pattern.toCharArray();
        for (String query : queries) {
            boolean isMatch = match(query.toCharArray(), patternArr);
            res.add(isMatch);
        }
        return res;
    }
    
    private boolean match(char[] queryArr, char[] patternArr) {
        int j = 0;
        for (int i = 0; i < queryArr.length; i++) {
            if (j < patternArr.length && queryArr[i] == patternArr[j]) {
                j++;
            } else if (queryArr[i] >= 'A' && queryArr[i] <= 'Z') {
                return false;
            }
        }
        return j == patternArr.length;
    }
}
Updated naming convention and coding style per comments by destiny130@ . Thanks for your advice.
Possible improvement, special thanks to @Sithis:
Use new ArrayList<>(queries.length) to allocate capacity up-front. This can avoid resizing and copying as the size of the array grows.
queryArr[i] >= 'A' && queryArr[i] <= 'Z' can be replaced by built-in static method Character.isUpperCase().
*/

// Similar problem: 925. Long Pressed Name
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/TwoPointers/LongPressedName.java

class Solution {
    public List<Boolean> camelMatch(String[] queries, String pattern) {
        List<Boolean> result = new ArrayList<Boolean>();
        for(String query : queries) {
            result.add(isMatch(query, pattern));
        }
        return result;
    }
    
    private boolean isMatch(String query, String pattern) {
        int i = 0;
        for(int j = 0; j < query.length(); j++) {
            if(i < pattern.length() && query.charAt(j) == pattern.charAt(i)) {
                i++;
            } else if(Character.isUpperCase(query.charAt(j))) {
                return false;
            }
        }
        return i == pattern.length();
    }
}
