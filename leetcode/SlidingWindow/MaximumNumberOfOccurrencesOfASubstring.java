/**
Refer to
https://leetcode.com/problems/maximum-number-of-occurrences-of-a-substring/
Given a string s, return the maximum number of ocurrences of any substring under the following rules:

The number of unique characters in the substring must be less than or equal to maxLetters.
The substring size must be between minSize and maxSize inclusive.

Example 1:
Input: s = "aababcaab", maxLetters = 2, minSize = 3, maxSize = 4
Output: 2
Explanation: Substring "aab" has 2 ocurrences in the original string.
It satisfies the conditions, 2 unique letters and size 3 (between minSize and maxSize).

Example 2:
Input: s = "aaaa", maxLetters = 1, minSize = 3, maxSize = 3
Output: 2
Explanation: Substring "aaa" occur 2 times in the string. It can overlap.

Example 3:
Input: s = "aabcabcab", maxLetters = 2, minSize = 2, maxSize = 3
Output: 3

Example 4:
Input: s = "abcde", maxLetters = 2, minSize = 3, maxSize = 3
Output: 0

Constraints:
1 <= s.length <= 10^5
1 <= maxLetters <= 26
1 <= minSize <= maxSize <= min(26, s.length)
s only contains lowercase English letters.
*/

// Solution 1: Sliding window:
// Why we don't need to care about maxSize ?
// Refer to
// https://leetcode.com/problems/maximum-number-of-occurrences-of-a-substring/discuss/457577/C%2B%2B-Greedy-approach-%2B-Sliding-window-O(n).
/**
Observation
Let's assume we have a fixed size of window (minSize), we can easily use a sliding window of that size and check for the contraints within that window.
This can be done in O(n) using a hashmap to store the unique count of letters in the window.

The greedy part of the solution is that, we also notice that we only need minSize since if the minSize satisfies the constraints to have distinct 
letters <= maxLetters any substring greater than that size would only add at max a new distinct letter.
Which esentially means that since the substring of size greater than minSize starisfies the constraint of distinct letters <= maxLetters there will 
be a substring of this selected substring of size minSize that'll satisfy the same constraint and the frequency of this substring will be atleast 
as much as the parent substring.
We also know that number of shorter substrings are more than the longer ones , thus we only need to check for substrings of minSize satisfying the condition.

Eg: s=abcabc, minSize=3, maxSize=6, maxLetter=3
substrings satisfying the constraints are abc, abca ,abcab, abcabc, bca, bcab, bcabc, cab, cabc and abc again, we can see that all the substrings 
have a substring within it of size=minSize that satisfies all the conditions, like we have abc in first 4 substrings. Thus we only need to check 
for substrings of size=minSize that satisfy the condition to get our result.

The only problem is that this question ask for the max count of substrings. We can do this in two ways 1 we store the entire sub-string and it's 
count in a hashmap which is an O(n) operation (in this case O(1) since we can have at max 26 distinct characters) or we can use rolling hash during 
the sliding window to make it O(1).
*/

// Style 1: for loop
// Refer to
// https://leetcode.com/problems/maximum-number-of-occurrences-of-a-substring/discuss/457577/C++-Greedy-approach-+-Sliding-window-O(n)./411478
/**
public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
		
		int start = 0, res = 0;
		
		Map<Character, Integer> m = new HashMap<>(); //Stores count of letters in the window.
		Map<String, Integer> m2 = new HashMap<>(); //Stores count of occurence of that string.
		
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			m.put(c, m.getOrDefault(c, 0) + 1);
			if(i - start + 1 > minSize) {
				char c2 = s.charAt(start);
				m.put(c2, m.get(c2) - 1);
				if(m.get(c2) == 0) {
					m.remove(c2);
				}
				start++;
			}
			
			if(i - start + 1 == minSize && m.size() <= maxLetters) { //When the substring in the window matches the constraints.
				String str = s.substring(start, i + 1);
			    m2.put(str, m2.getOrDefault(str, 0) + 1);
				res = Math.max(res, m2.get(str));
			}
		}
		
		return res;			
	}
*/
class Solution {
    public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
        int max = 0;
        Map<String, Integer> map = new HashMap<String, Integer>();
        int[] freq = new int[26];
        int letters = 0;
        int j = 0;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // If no previous record then we get one new letter
            if(freq[c - 'a'] == 0) {
                letters++;
            }
            freq[c - 'a']++;
            if(i - j + 1 > minSize) {
                char c1 = s.charAt(j);
                if(freq[c1 - 'a'] == 1) {
                    letters--;
                }
                freq[c1 - 'a']--;
                j++;
            }
            if(i - j + 1 == minSize && letters <= maxLetters) {
                String str = s.substring(j, i + 1);
                map.put(str, map.getOrDefault(str, 0) + 1);
                max = Math.max(max, map.get(str));
            }
        }
        return max;
    }
}

// Solution 2: while loop and its equal for loop style
// Refer to
// https://leetcode.com/problems/maximum-number-of-occurrences-of-a-substring/discuss/457577/C++-Greedy-approach-+-Sliding-window-O(n)./411614
/**
class Solution {
    public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
        HashMap<String,Integer> map=new HashMap<>();
        int res=0;
        int[] ch=new int[128];
        int l=0, r=0, letter=0;
        while(r<s.length()){
            if(ch[s.charAt(r++)]++==0) letter++;
            while(letter>maxLetters || (r-l)>minSize){
                if(ch[s.charAt(l++)]--==1) letter--;
            }
            if((r-l)==minSize){
                String sb=s.substring(l, r);
                map.put(sb, map.getOrDefault(sb,0)+1);
                res=Math.max(res, map.get(sb));
            }
        }
        return res;
    }
}
*/
class Solution {
    public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
        int max = 0;
        Map<String, Integer> map = new HashMap<String, Integer>();
        int[] freq = new int[26];
        int letters = 0;
        int j = 0;
        for(int i = 0; i < s.length();) {
            char c = s.charAt(i);
            // If no previous record then we get one new letter
            if(freq[c - 'a'] == 0) {
                letters++;
            }
            freq[c - 'a']++;
            i++;
            while(letters > maxLetters || j < i - minSize) {
                char c1 = s.charAt(j);
                if(freq[c1 - 'a'] == 1) {
                    letters--;
                }
                freq[c1 - 'a']--;
                j++;
            }
            if(i - j == minSize) {
                String str = s.substring(j, i);
                map.put(str, map.getOrDefault(str, 0) + 1);
                max = Math.max(max, map.get(str));
            }
        }
        return max;
    }
}
