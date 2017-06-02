/**
 * Refer to
 * https://leetcode.com/problems/longest-uncommon-subsequence-ii/#/description
 *  Given a list of strings, you need to find the longest uncommon subsequence among them. 
 * The longest uncommon subsequence is defined as the longest subsequence of one of 
 * these strings and this subsequence should not be any subsequence of the other strings.
 * A subsequence is a sequence that can be derived from one sequence by deleting some 
 * characters without changing the order of the remaining elements. Trivially, any string 
 * is a subsequence of itself and an empty string is a subsequence of any string.
 * The input will be a list of strings, and the output needs to be the length of the longest 
 * uncommon subsequence. If the longest uncommon subsequence doesn't exist, return -1.
  Example 1:
  Input: "aba", "cdc", "eae"
  Output: 3
 * 
 * Solution
 * https://discuss.leetcode.com/topic/85027/java-hashing-solution
 * We simply maintain a map of all subsequence frequencies and get the subsequence with 
 * frequency 1 that has longest length.
 * NOTE: This solution does not take advantage of the fact that the optimal length subsequence 
 * (if it exists) is always going to be the length of some string in the array. Thus, the time 
 * complexity of this solution is non-optimal. 
 * See https://discuss.leetcode.com/topic/85044/python-simple-explanation for optimal solution.
*/

public class Solution {
    public int findLUSlength(String[] strs) {
        // Store all string's(in strs) possible subsequence and its frequence into map
        Map<String, Integer> frequenceMap = new HashMap<String, Integer>();
        for(int i = 0; i < strs.length; i++) {
            for(String subseq : getSubseqs(strs[i])) {
                if(!frequenceMap.containsKey(subseq)) {
                    frequenceMap.put(subseq, 1);
                } else {
                    frequenceMap.put(subseq, frequenceMap.get(subseq) + 1);
                }
            }
        }
        // Find all frequence = 1 subsequence in map then find longest one
        // Set default value as '-1' based on requirement if uncommon
        // subsequence doesn't exist
        int longest = -1;
        for(Map.Entry<String, Integer> entry : frequenceMap.entrySet()) {
            if(entry.getValue() == 1) {
                longest = Math.max(longest, entry.getKey().length());
            }
        }
        return longest;
    }
    
    // Method to get all possible subsequence of a string
    public Set<String> getSubseqs(String s) {
        Set<String> res = new HashSet<String>();
        // Base case
        if(s.length() == 0) {
            res.add("");
            return res;
        }
        // Get all subsequences besides first character in string
        Set<String> subRes = getSubseqs(s.substring(1));
        res.addAll(subRes);
        // Get all subsequences include first character in string
        for(String seq : subRes) {
            res.add(s.charAt(0) + seq);
        }
        return res;
    }
}
