/**
Refer to
https://leetcode.com/problems/check-if-a-string-can-break-another-string/
Given two strings: s1 and s2 with the same size, check if some permutation of string s1 can break some permutation 
of string s2 or vice-versa. In other words s2 can break s1 or vice-versa.

A string x can break string y (both of size n) if x[i] >= y[i] (in alphabetical order) for all i between 0 and n-1.

Example 1:
Input: s1 = "abc", s2 = "xya"
Output: true
Explanation: "ayx" is a permutation of s2="xya" which can break to string "abc" which is a permutation of s1="abc".

Example 2:
Input: s1 = "abe", s2 = "acd"
Output: false 
Explanation: All permutations for s1="abe" are: "abe", "aeb", "bae", "bea", "eab" and "eba" and all permutation for 
s2="acd" are: "acd", "adc", "cad", "cda", "dac" and "dca". However, there is not any permutation from s1 which can 
break some permutation from s2 and vice-versa.

Example 3:
Input: s1 = "leetcodee", s2 = "interview"
Output: true

Constraints:
s1.length == n
s2.length == n
1 <= n <= 10^5
All strings consist of lowercase English letters.
*/

// Solution 1: Brutal Force to find all permutation and compare (TLE)
// Refer to
// https://stackoverflow.com/questions/4240080/generating-all-permutations-of-a-given-string
/**
public static void permutation(String str) { 
    permutation("", str); 
}

private static void permutation(String prefix, String str) {
    int n = str.length();
    if (n == 0) System.out.println(prefix);
    else {
        for (int i = 0; i < n; i++)
            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
    }
}
*/
class Solution {
    public boolean checkIfCanBreak(String s1, String s2) {
        List<String> all_s1_permutation = new ArrayList<String>();
        List<String> all_s2_permutation = new ArrayList<String>();
        helper("", s1, all_s1_permutation);
        helper("", s2, all_s2_permutation);
        boolean s2_break_s1 = check(s1, all_s2_permutation);
        boolean s1_break_s2 = check(s2, all_s1_permutation);
        return s2_break_s1 || s1_break_s2;
    }
    
    private void helper(String prefix, String str, List<String> result) {
        int n = str.length();
        if(n == 0) {
            result.add(prefix);
        } else {
            for(int i = 0; i < n; i++) {
                helper(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), result);
            }
        }
    }
    
    private boolean check(String str, List<String> list) {
        for(String s : list) {
            int i;
            for(i = 0; i < s.length(); i++) {
                if(s.charAt(i) < str.charAt(i)) {
                	break;
                }
            }
            if(i == s.length()) {
                return true;
            }
        }
        return false;
    }
}

// Solution 2: Freq map + travel backwards to find potential character not able to cover
// Refer to
// https://leetcode.com/problems/check-if-a-string-can-break-another-string/discuss/1085537/O(n)-clean-with-explanation
/**
First piece of intuition comes from the problem statement - we are asked to find the answer over all possible permutions 
of both strings. Very often when we are asked find/do something related to all permutations of a string the solution involves 
counting the number of occurrences of each character. So this is the first thing we do: compute the character frequency 
maps of both strings.

Now lets say s1 = "abe" and s2 = "acd", so the frequencies are:

	 a b c d e ...
s1:  1 1 0 0 1
s2:  1 0 1 1 0

How do we check whether s1 breaks s2? Well, it would make sense for the largest character in s1 - e - to "cover" the largest 
character in s2 - d. After this pair of characters, the largest one in s1 is b and the largest one in s2 is c - and b < c! 
Meaning that s1 cannot break s2.

Now run the same logic on a more complex example:

	 a b c d e ...
s1:  1 1 0 0 5
s2:  1 0 2 4 0
The first 4 es cover the 4 ds, the remaining e covers one of the cs, but after that the largest remaining character in s1 is 
smaller that the respective character in s2, therefore there is no way s1 breaks s2.
The way we can translate this logic to code can be seen in the canBreak() function below: we iterate from z to a and keep track 
of how many characters from s1 we have seen minus how many chars from s2 we've seen. If at any point the difference is less than 
0, s1 cannot break s2.

The code:

  bool checkIfCanBreak(string s1, string s2) {
    auto freq1 = charFreq(s1);
    auto freq2 = charFreq(s2);
    return canBreak(freq1, freq2) || canBreak(freq2, freq1);
  }
  
  vector<int> charFreq(const string& s) {
    vector<int> freq(26);
    for (char c : s)
      freq[c - 'a']++;
    return freq;
  }
  
  bool canBreak(const vector<int>& strong, const vector<int>& weak) {
    int diff = 0;
    for (int i = 25; i >= 0; --i) {
      diff += strong[i] - weak[i];
      if (diff < 0)
        return false;
    }
    return true;
  }
*/
class Solution {
    public boolean checkIfCanBreak(String s1, String s2) {
        int[] freq1 = new int[26];
        int[] freq2 = new int[26];
        for(char c : s1.toCharArray()) {
            freq1[c - 'a']++;
        }
        for(char c : s2.toCharArray()) {
            freq2[c - 'a']++;
        }
        return check(freq1, freq2) || check(freq2, freq1);
    }
    
    private boolean check(int[] f1, int[] f2) {
        int diff = 0;
        for(int i = 25; i >= 0; i--) {
            diff += f1[i] - f2[i];
            if(diff < 0) {
                return false;
            }
        }
        return true;
    }
}
