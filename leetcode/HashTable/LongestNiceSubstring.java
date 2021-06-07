/**
Refer to
https://leetcode.com/problems/longest-nice-substring/
A string s is nice if, for every letter of the alphabet that s contains, it appears both in uppercase and lowercase. 
For example, "abABB" is nice because 'A' and 'a' appear, and 'B' and 'b' appear. However, "abA" is not because 'b' appears, but 'B' does not.

Given a string s, return the longest substring of s that is nice. If there are multiple, return the substring of the 
earliest occurrence. If there are none, return an empty string.

Example 1:
Input: s = "YazaAay"
Output: "aAa"
Explanation: "aAa" is a nice string because 'A/a' is the only letter of the alphabet in s, and both 'A' and 'a' appear.
"aAa" is the longest nice substring.

Example 2:
Input: s = "Bb"
Output: "Bb"
Explanation: "Bb" is a nice string because both 'B' and 'b' appear. The whole string is a substring.

Example 3:
Input: s = "c"
Output: ""
Explanation: There are no nice substrings.

Example 4:
Input: s = "dDzeE"
Output: "dD"
Explanation: Both "dD" and "eE" are the longest nice substrings.
As there are multiple longest nice substrings, return "dD" since it occurs earlier.

Constraints:
1 <= s.length <= 100
s consists of uppercase and lowercase English letters.
*/

// Solution 1: Brute Force
// Refer to
// https://leetcode.com/problems/longest-nice-substring/discuss/1075274/Java-Solution-with-Set-comment-explanation
/**
class Solution {
    public String longestNiceSubstring(String s) {
        String result = "";
        // take first index, go from 0 to length-1 of the string
		for (int i = 0;i<s.length(); i++){        
            // take second index, this should go up to the length of the string <=
			for (int j = i+1;j<=s.length(); j++){
                //get the substring for the index range from i to j
				String temp = s.substring(i, j);
                // if length of the substring should be greater than 1
				// if the length should be greater that the previous computed result
				// if the substring is valid Nice String
				// then update the result with the current substring from range i and j
				if (temp.length() > 1 && result.length() < temp.length() && checkNice(temp)) result = temp;
            }    
        }
        return result;
    }
    
	//validate Nice String check
    public boolean checkNice(String temp){
        //add substring to the set
		Set<Character> s = new HashSet<>();
        for (char ch : temp.toCharArray()) s.add(ch);
        
		// return false If you do not find both lower case and upper case in the sub string
		//for e.g 'aAa' substring added to set will have both a and A in the substring which is valid
		// 'azaA' substring will fail for 'z'
		// 'aaaaaaaa' will return "" as result
		//make sure that the substring contains both lower and upper case
        for (char ch : s)
            if (s.contains(Character.toUpperCase(ch)) != s.contains(Character.toLowerCase(ch))) return false;  
        return true;
    }
}
*/
class Solution {
    public String longestNiceSubstring(String s) {
        String result = "";
        int n = s.length();
        for(int i = 0; i <= n; i++) {
            for(int j = i + 1; j <= n; j++) {
                String str = s.substring(i, j);
                if(str.length() > 1 && str.length() > result.length() && checkNice(str)) {
                    result = str;
                }
            }
        }
        return result;
    }
    
    private boolean checkNice(String s) {
        Set<Character> set = new HashSet<Character>();
        for(char c : s.toCharArray()) {
            set.add(c);
        }
        for(char c : s.toCharArray()) {
            if(set.contains(Character.toUpperCase(c)) != set.contains(Character.toLowerCase(c))) {
                return false;
            }
        }
        return true;
    }
}
