/**
Refer to
https://leetcode.com/problems/positions-of-large-groups/
In a string s of lowercase letters, these letters form consecutive groups of the same character.

For example, a string like s = "abbxxxxzyy" has the groups "a", "bb", "xxxx", "z", and "yy".

A group is identified by an interval [start, end], where start and end denote the start and end indices (inclusive) of the group. 
In the above example, "xxxx" has the interval [3,6].

A group is considered large if it has 3 or more characters.

Return the intervals of every large group sorted in increasing order by start index.

Example 1:
Input: s = "abbxxxxzzy"
Output: [[3,6]]
Explanation: "xxxx" is the only large group with start index 3 and end index 6.

Example 2:
Input: s = "abc"
Output: []
Explanation: We have groups "a", "b", and "c", none of which are large groups.

Example 3:
Input: s = "abcdddeeeeaabbbcd"
Output: [[3,5],[6,9],[12,14]]
Explanation: The large groups are "ddd", "eeee", and "bbb".

Example 4:
Input: s = "aba"
Output: []

Constraints:
1 <= s.length <= 1000
s contains lower-case English letters only.
*/

// Solution 1: Native Written
class Solution {
    public List<List<Integer>> largeGroupPositions(String s) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        char[] chars = s.toCharArray();
        int prev = 0;
        int count = 1;
        for(int i = 1; i < chars.length; i++) {
            if(chars[prev] == chars[i]) {
                count++;
                // Test out by "aaa" as last digit handling
                if(count >= 3 && i == chars.length - 1) {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(prev);
                    list.add(i);
                    result.add(list);
                }
            } else {
                if(count >= 3) {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(prev);
                    list.add(i - 1);
                    result.add(list);
                }
                prev = i;
                count = 1;
            }
        }
        return result;
    }
}

// Solution 2: Promote Written
// Refer to
// https://leetcode.com/problems/positions-of-large-groups/discuss/128961/Java-Solution-Two-Pointers
class Solution {
    public List<List<Integer>> largeGroupPositions(String s) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        int prev = 0;
        for(int i = 1; i <= s.length(); i++) {
            if(i == s.length() || s.charAt(prev) != s.charAt(i)) {
                if(i - prev >= 3) {
                    result.add(Arrays.asList(prev, i - 1));
                }
                prev = i;
            }
        }
        return result;
    }
}
