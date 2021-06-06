/**
Refer to
https://leetcode.com/problems/groups-of-special-equivalent-strings/
You are given an array words of strings.

A move onto s consists of swapping any two even indexed characters of s, or any two odd indexed characters of s.

Two strings s and t are special-equivalent if after any number of moves onto s, s == t.

For example, s = "zzxy" and t = "xyzz" are special-equivalent because we may make the moves "zzxy" -> "xzzy" -> "xyzz" that swap s[0] and s[2], then s[1] and s[3].

Now, a group of special-equivalent strings from words is a non-empty subset of words such that:

Every pair of strings in the group are special equivalent, and;
The group is the largest size possible (ie., there isn't a string s not in the group such that s is special equivalent to every string in the group)
Return the number of groups of special-equivalent strings from words.

Example 1:
Input: words = ["abcd","cdab","cbad","xyzz","zzxy","zzyx"]
Output: 3
Explanation: 
One group is ["abcd", "cdab", "cbad"], since they are all pairwise special equivalent, and none of the other strings are all pairwise special equivalent to these.

The other two groups are ["xyzz", "zzxy"] and ["zzyx"].  Note that in particular, "zzxy" is not special equivalent to "zzyx".

Example 2:
Input: words = ["abc","acb","bac","bca","cab","cba"]
Output: 3

Note:
1 <= words.length <= 1000
1 <= words[i].length <= 20
All words[i] have the same length.
All words[i] consist of only lowercase letters.
*/

// Solution 1: Find odd and even position freq map and build string signature
// Refer to
// https://leetcode.com/problems/groups-of-special-equivalent-strings/discuss/163413/Java-Concise-Set-Solution
/**
For each String, we generate it's corresponding signature, and add it to the set.
In the end, we return the size of the set.

class Solution {
    public int numSpecialEquivGroups(String[] A) {
        Set<String> set= new HashSet<>();
        for (String s: A){
            int[] odd= new int[26];
            int[] even= new int[26];
            for (int i=0; i<s.length(); i++){
                if (i%2==1) odd[s.charAt(i)-'a']++;
                else even[s.charAt(i)-'a']++;
            }
            String sig= Arrays.toString(odd)+Arrays.toString(even);
            set.add(sig);
        }
        return set.size();
    }
}
*/
class Solution {
    public int numSpecialEquivGroups(String[] words) {
        Set<String> set = new HashSet<String>();
        for(String w : words) {
            int[] odd = new int[26];
            int[] even = new int[26];
            for(int i = 0; i < w.length(); i++) {
                char c = w.charAt(i);
                if(i % 2 == 1) {
                    odd[c - 'a']++;
                } else {
                    even[c - 'a']++;
                }
            }
            String signature = Arrays.toString(odd) + Arrays.toString(even);
            set.add(signature);
        }
        return set.size();
    }
}
