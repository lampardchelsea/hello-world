/**
 * Refer to
 * https://leetcode.com/problems/partition-labels/description/
 * A string S of lowercase letters is given. We want to partition this string into as many parts 
   as possible so that each letter appears in at most one part, and return a list of integers 
   representing the size of these parts.

    Example 1:
    Input: S = "ababcbacadefegdehijhklij"
    Output: [9,7,8]
    Explanation:
    The partition is "ababcbaca", "defegde", "hijhklij".
    This is a partition so that each letter appears in at most one part.
    A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits S into less parts.
    Note:

    S will have length in range [1, 500].
    S will consist of lowercase letters ('a' to 'z') only.
 *
 *
 * Solution
 * https://www.youtube.com/watch?v=s-1W5FDJ0lw
 * https://leetcode.com/problems/partition-labels/discuss/113259/Java-2-pass-O(n)-time-O(1)-space-extending-end-pointer-solution
*/
// Solution 1: HashMap + 2 Pass
class Solution {
    public List<Integer> partitionLabels(String S) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        int len = S.length();
        for(int i = 0; i < len; i++) {
            map.put(S.charAt(i), i);
        }
        List<Integer> result = new ArrayList<Integer>();
        for(int i = 0; i < S.length(); i++) {
            int tokenCharsFinalPosi = map.get(S.charAt(i));
            for(int j = i; j <= tokenCharsFinalPosi; j++) {
            	tokenCharsFinalPosi = Math.max(tokenCharsFinalPosi, map.get(S.charAt(j)));
            }
            int sectionLen = tokenCharsFinalPosi - i + 1;
            result.add(sectionLen);
            i = tokenCharsFinalPosi;
        }
        return result;
    }
}

// Solution 2: Java 2 pass O(n) time O(1) space, extending end pointer solution






