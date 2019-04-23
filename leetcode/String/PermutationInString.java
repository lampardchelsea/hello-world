/**
 Refer to
 https://leetcode.com/problems/permutation-in-string/
 Given two strings s1 and s2, write a function to return true if s2 contains the permutation of s1. 
 In other words, one of the first string's permutations is the substring of the second string.

Example 1:
Input: s1 = "ab" s2 = "eidbaooo"
Output: True
Explanation: s2 contains one permutation of s1 ("ba").

Example 2:
Input:s1= "ab" s2 = "eidboaoo"
Output: False

Note:
The input strings only contain lower case letters.
The length of both given strings is in range [1, 10,000].
*/
// Solution 1: Time Limit Exceeded
// Refer to Permutations II
// https://github.com/lampardchelsea/hello-world/blob/b1b6c794809ddbde034d049f01ca63b17e6928be/lintcode/DFS/VideoExamples/PermutationsII.java
// https://leetcode.com/problems/permutation-in-string/solution/
// Time Complexity: O(n!) We match all the permutations of the short string s1, of length s1, with s2. 
//                  Here, n refers to the length of s1.
// Space Complexity: O(n^2) The depth of the recursion tree is n(n refers to the length of the short string s1). 
//                   Every node of the recursion tree contains a string of max. length n.
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        List<String> allPermutations = findAllPermutations(s1);
        for(String s : allPermutations) {
            if(s2.contains(s)) {
                return true;
            }
        }
        return false;
    }
    
    private List<String> findAllPermutations(String s) {
        List<String> result = new ArrayList<String>();
        if(s == null || s.length() == 0) {
            return result;
        }
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        boolean[] visited = new boolean[s.length()];
        helper(result, visited, chars, "");
        return result;
    }
    
    private void helper(List<String> result, boolean[] visited, char[] chars, String str) {
        if(str.length() == chars.length) {
            result.add(str);
        }
        for(int i = 0; i < chars.length; i++) {
            if(visited[i] || (i > 0 && !visited[i - 1] && chars[i - 1] == chars[i])) {
                continue;
            }
            visited[i] = true;
            str += chars[i];
            helper(result, visited, chars, str);
            str = str.substring(0, str.length() - 1);
            visited[i] = false;
        }
    }
}

// Solution 2:
// Refer to
// https://leetcode.com/problems/permutation-in-string/solution/
/**
Approach #4 Using Array [Accepted]
Algorithm
Instead of making use of a special HashMap datastructure just to store the frequency of occurence 
of characters, we can use a simpler array data structure to store the frequencies. Given strings 
contains only lowercase alphabets ('a' to 'z'). So we need to take an array of size 26.The rest 
of the process remains the same as the last approach.
Complexity Analysis:
Time Complexity: O(l1 + 26 * l1 * (l2 - l1)), where l1 is the length of string l1 and l2 is the
                 length of string l2
Space Complexity: O(1)
*/
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        int[] counter1 = new int[26];
        char[] chars1 = s1.toCharArray();
        for(int i = 0; i < chars1.length; i++) {
            counter1[chars1[i] - 'a']++;
        }
        for(int i = 0; i <= s2.length() - s1.length(); i++) {
            int[] counter2 = new int[26];
            String subStr = s2.substring(i, i + s1.length());
            char[] chars2 = subStr.toCharArray();
            for(int j = 0; j < chars2.length; j++) {
                counter2[chars2[j] - 'a']++;
            }
            if(matches(counter1, counter2)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean matches(int[] counter1, int[] counter2) {
        for(int i = 0; i < 26; i++) {
            if(counter1[i] != counter2[i]) {
                return false;
            }
        }
        return true;
    }
}

// Solution 3: Sliding Window
// Refer to
// https://leetcode.com/problems/permutation-in-string/discuss/102588/Java-Solution-Sliding-Window
/**
 How do we know string p is a permutation of string s? Easy, each character in p is in s too. 
 So we can abstract all permutation strings of s to a map (Character -> Count). i.e. abba -> {a:2, b:2}. 
 Since there are only 26 lower case letters in this problem, we can just use an array to represent the map.
How do we know string s2 contains a permutation of s1? We just need to create a sliding window with length 
of s1, move from beginning to the end of s2. When a character moves in from right of the window, we 
subtract 1 to that character count from the map. When a character moves out from left of the window, 
we add 1 to that character count. So once we see all zeros in the map, meaning equal numbers of every 
characters between s1 and the substring in the sliding window, we know the answer is true.
*/
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if(s1.length() > s2.length()) {
            return false;
        }
        int[] count = new int[26];
        for(int i = 0; i < s1.length(); i++) {
            count[s1.charAt(i) - 'a']++;
            count[s2.charAt(i) - 'a']--;
        }
        if(allZero(count)) {
            return true;
        }
        for(int i = s1.length(); i < s2.length(); i++) {
            count[s2.charAt(i) - 'a']--;
            count[s2.charAt(i - s1.length()) - 'a']++;
            if(allZero(count)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean allZero(int[] count) {
        for(int i = 0; i < 26; i++) {
            if(count[i] != 0) {
                return false;
            }
        }
        return true;
    }
}
