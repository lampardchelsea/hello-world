/**
Refer to
https://leetcode.com/problems/find-and-replace-in-string/
To some string s, we will perform some replacement operations that replace groups of letters with new ones (not necessarily the same size).

Each replacement operation has 3 parameters: a starting index i, a source word x and a target word y. 
The rule is that if x starts at position i in the original string S, then we will replace that occurrence of x with y. If not, we do nothing.

For example, if we have s = "abcd" and we have some replacement operation i = 2, x = "cd", y = "ffff", 
then because "cd" starts at position 2 in the original string s, we will replace it with "ffff".

Using another example on s = "abcd", if we have both the replacement operation i = 0, x = "ab", y = "eee", 
as well as another replacement operation i = 2, x = "ec", y = "ffff", this second operation does nothing 
because in the original string s[2] = 'c', which doesn't match x[0] = 'e'.

All these operations occur simultaneously. It's guaranteed that there won't be any overlap in replacement: 
for example, s = "abc", indexes = [0, 1], sources = ["ab","bc"] is not a valid test case.

Example 1:
Input: s = "abcd", indexes = [0, 2], sources = ["a", "cd"], targets = ["eee", "ffff"]
Output: "eeebffff"
Explanation:
"a" starts at index 0 in s, so it's replaced by "eee".
"cd" starts at index 2 in s, so it's replaced by "ffff".

Example 2:
Input: s = "abcd", indexes = [0, 2], sources = ["ab","ec"], targets = ["eee","ffff"]
Output: "eeecd"
Explanation:
"ab" starts at index 0 in s, so it's replaced by "eee".
"ec" doesn't starts at index 2 in the original s, so we do nothing.

Constraints:
0 <= s.length <= 1000
s consists of only lowercase English letters.
0 <= indexes.length <= 100
0 <= indexes[i] < s.length
sources.length == indexes.length
targets.length == indexes.length
1 <= sources[i].length, targets[i].length <= 50
sources[i] and targets[i] consist of only lowercase English letters.
*/

// Solution 1: Scan from left to right
// Refer to
// https://leetcode.com/problems/find-and-replace-in-string/discuss/455922/Java-or-Beats-100-or-Simple-one-pass-solution-left-to-right
/**
3 Steps:

Create an index map of indices in index. Sort the indexes array so we can process from left to right.
For each index, check for validity, and append a) substring from last processed till current index, and b) target string
Finally append the residual substring
class Solution {
    public String findReplaceString(String S, int[] indexes, String[] sources, String[] targets) {
        if (S.length()==0 || indexes.length==0) return S;
		
		// Step 1
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int i=0; i<indexes.length; i++) indexMap.put(indexes[i], i);
        Arrays.sort(indexes);
		
		// Step 2
		StringBuilder sb = new StringBuilder();
        int curr = 0;
		for (int index : indexes) {
            int i = indexMap.get(index);
            int next = index + sources[i].length();
            if (!S.substring(index, next).equals(sources[i])) continue;
            sb.append(S.substring(curr, index));
            sb.append(targets[i]);
            curr = next;
        }
		
		// Step 3
        sb.append(S.substring(curr));
        return sb.toString();
    }
}
*/
class Solution {
    public String findReplaceString(String s, int[] indexes, String[] sources, String[] targets) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < indexes.length; i++) {
            map.put(indexes[i], i);
        }
        Arrays.sort(indexes);
        StringBuilder sb = new StringBuilder();
        int curPos = 0;
        for(int index : indexes) {
            int i = map.get(index);
            int next = index + sources[i].length();
            if(!s.substring(index, next).equals(sources[i])) {
                continue;
            }
            sb.append(s.substring(curPos, index));
            sb.append(targets[i]);
            curPos = next;
        }
        sb.append(s.substring(curPos));
        return sb.toString();
    }
}
