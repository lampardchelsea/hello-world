/**
Refer to
https://leetcode.com/problems/check-if-string-is-transformable-with-substring-sort-operations/
Given two strings s and t, you want to transform string s into string t using the following operation any number of times:

Choose a non-empty substring in s and sort it in-place so the characters are in ascending order.
For example, applying the operation on the underlined substring in "14234" results in "12344".

Return true if it is possible to transform string s into string t. Otherwise, return false.

A substring is a contiguous sequence of characters within a string.

Example 1:
Input: s = "84532", t = "34852"
Output: true
Explanation: You can transform s into t using the following sort operations:
"84532" (from index 2 to 3) -> "84352"
"84352" (from index 0 to 2) -> "34852"

Example 2:
Input: s = "34521", t = "23415"
Output: true
Explanation: You can transform s into t using the following sort operations:
"34521" -> "23451"
"23451" -> "23415"

Example 3:
Input: s = "12345", t = "12435"
Output: false

Example 4:
Input: s = "1", t = "2"
Output: false

Constraints:
s.length == t.length
1 <= s.length <= 105
s and t only contain digits from '0' to '9'.
*/

// Solution 1: No smaller digit should happen on its left
// Refer to
// https://leetcode.com/problems/check-if-string-is-transformable-with-substring-sort-operations/discuss/843917/C%2B%2BJavaPython-O(n)
/**
Intuition
If ch[i] > ch[j], we can swap these characters. In other words, we can move a character freely to the left, until it hits a smaller character, e.g.:

"0231" > "0213" > "0123"

So, we do not need to sort anything, we just need to check if we can move required characters to the left to get the target string.

Note: we can also process the string right-to-left and move larger numbers right. 
In that case, we can just pop used indexes instead tracking them in a separate array pos. I though about it, but it appeared a bit 
harder to get right during the contest. If interested, check the solution by 0xFFFFFFFF in the comments below.

Algorithm
Collect indexes of all characters 0-9 of the source strings in idx. For each characters, we track which indexes we have used in pos.

For each character ch in the target string, check if we have it in idx. If so, verify that there are no smaller characters in front of it. 
To do that, we check the current idexes of all characters less than ch.

If the character can be moved, mark its index as used by advancing pos[ch].

public boolean isTransformable(String s, String t) {
    ArrayList<Integer> idx[] = new ArrayList[10];
    int pos[] = new int[10];
    for (int i = 0; i <= 9; ++i)
        idx[i] = new ArrayList<Integer>();
    for (int i = 0; i < s.length(); ++i)
        idx[s.charAt(i) - '0'].add(i);
    for (int i = 0; i < t.length(); ++i) {
        int d = t.charAt(i) - '0';
        if (pos[d] >= idx[d].size())
            return false;
        for (int j = 0; j < d; ++j)
            if (pos[j] < idx[j].size() && idx[j].get(pos[j]) < idx[d].get(pos[d]))
                return false;        
        ++pos[d];
    }
    return true;
}
*/

// https://leetcode.com/problems/check-if-string-is-transformable-with-substring-sort-operations/discuss/843917/C++JavaPython-O(n)/695347
/**
Hard qustions, brillant brain and smart soltion.
I have to admit that it took me almost a whole day to figure this solution out. And I also read the comments from Python [backward + forward pass with stack].

For those who still have doubts, I'd like to further explain the solution and hope this helps (not a native speaker). If this helps, please upvote.

My Understanding:

For every digit in target string t (let's say d in i-th position), we check if we can put the same digit (i.e. d) in s to the same position as t, i.e. i-th position.
If cnt[j] >= idx[j].size(), which means we have already arranged all digit j that string s has in its correction positions.
If the string t has more digit j than the source string s has, it needs return false;
cnt[j] < idx[j].size() && idx[j].get(cnt[j]) < idx[d].get(cnt[d]), check if there has smaller digit (let's say j) than d
and it's in front of d in source string s.
For example, s = "12345", t = "12435"
Firstly, the first two digits in t is "12", and we can arrange "12" in s with the same position without doubt.
Next, when we need to put '4' in t to the 2-th position in s. But in string s, there exists a '3' in front of '4', and '3' is smaller than '4',
and we cannot switch '3' and '4', therefore, we cannot put '4' in front of '3' in s, i.e. we cannot put '4' in 2-th position, and it needs to return false.
class Solution {
    public boolean isTransformable(String s, String t) {
        
        ArrayList<Integer>[] idx = new ArrayList[10];   // To store positions of every digit in s.
        // Initialize and store the positions of each digit.
        for(int i=0; i<10; i++) {
            idx[i] = new ArrayList<Integer>();
        }
        for(int i=0; i<s.length(); i++) {
            idx[s.charAt(i) - '0'].add(i);
        }
        
        int[] cnt = new int[10];        // To calculate the frequency of each digit in t.
        for(int i=0; i<t.length(); i++) {
            // The position of `d` in t is position i.
            // We need to check if we can put `d` in s to the position i of s.
            int d = t.charAt(i) - '0';  
            if(cnt[d] >= idx[d].size()) {   // If there has more d than s has, return false.
                return false;               // For example, there have 3 `5`s in string t,
            }                               // and only 2 `5`s in string s, it should return false.
            
            // Then we need to check if we can put digit `d` into the position i of s.
            for(int j=0; j<d; j++) {
                // cnt[j] < idx[j].size(), i.e. there still have extra smaller digit `j` in t we need to consider.
                // idx[j].get(cnt[j]) < idx[d].get(cnt[d]), i.e. (cnt[j])th `j` is in front of (cnt[d])th `d`,
                // and since `j < d`, we cannot swith `j` and `d`, i.e. the i-th position in s must left for digit `j`
                // and we cannot put `d` at position i in s. Therefore, we should return false.
                if(cnt[j] < idx[j].size() && idx[j].get(cnt[j]) < idx[d].get(cnt[d])) {
                    return false;
                }
            }
            cnt[d]++;
        }
        return true;
    }
}
*/
class Solution {
    // Every smaller digit's position in t is not earlier than that in s.
	public boolean isTransformable(String s, String t) {
	    ArrayList<Integer> idx[] = new ArrayList[10];
	    // To calculate the frequency of each digit in t.
	    int cnt[] = new int[10];
	    // Initialize and store the positions of every digit in s.
	    for(int i = 0; i <= 9; i++) {
	    	idx[i] = new ArrayList<Integer>();
	    }
	    for(int i = 0; i < s.length(); i++) {
	    	idx[s.charAt(i) - '0'].add(i);
	    } 
	    for(int i = 0; i < t.length(); i++) {
	        int d = t.charAt(i) - '0';
	        // If not equal count of digits during scanning process, e.g 
	        // if three '5's in t, but only two '5's in s, return false.
	        if(cnt[d] >= idx[d].size()) {
                return false;
            }
	        // Looping all prev smaller digit, make sure not moving smaller 
            // digit after dpos;
	        for(int j = 0; j < d; j++)	{
	            // cnt[j] < idx[j].size(), i.e. there still have extra smaller digit 'j' 
	            // in t we need to consider. idx[j].get(cnt[j]) < idx[d].get(cnt[d]), 
	            // i.e. (cnt[j])th 'j' is in front of (cnt[d])th 'd', and since 'j < d', 
	            // we cannot swith 'j' and 'd', i.e. the i-th position in s must left for 
	            // digit 'j', and we cannot put `d` at position i in s, return false
	            if(cnt[j] < idx[j].size() && idx[j].get(cnt[j]) < idx[d].get(cnt[d])) {
	                return false;
	            }
	        }
	        cnt[d]++;
	    }
	    return true;
	}
}
