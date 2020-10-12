/**
 Refer to
 https://leetcode.com/problems/concatenated-words/
 Given a list of words (without duplicates), please write a program that returns all concatenated words in the given list of words.
A concatenated word is defined as a string that is comprised entirely of at least two shorter words in the given array.

Example:
Input: ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]

Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]

Explanation: "catsdogcats" can be concatenated by "cats", "dog" and "cats"; 
 "dogcatsdog" can be concatenated by "dog", "cats" and "dog"; 
"ratcatdogcat" can be concatenated by "rat", "cat", "dog" and "cat".
Note:
The number of elements of the given array will not exceed 10,000
The length sum of elements in the given array will not exceed 600,000.
All the input string will only include lower case letters.
The returned elements order does not matter.
*/

// Solution 1: Cache nonCombination
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DFS_BackTracking/DFS_Memoization/WordBreak.java
// https://leetcode.com/problems/concatenated-words/discuss/541520/Java-DFS-%2B-Memoization-Clean-code
class Solution {
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> result = new ArrayList<String>();
        Set<String> nonCombination = new HashSet<String>();
        Set<String> word_set = new HashSet<String>(Arrays.asList(words));
        for(String word : words) {
            if(helper(word, word_set, nonCombination)) {
                result.add(word);
            }
        }
        return result;
    }
    
    private boolean helper(String word, Set<String> word_set, Set<String> nonCombination) {
        if(nonCombination.contains(word)) {
            return false;
        }
        for(int i = 1; i < word.length(); i++) {
            if(word_set.contains(word.substring(0, i))) {
                String suffix = word.substring(i);
                if(word_set.contains(suffix) || helper(suffix, word_set, nonCombination)) {
                    // can treat concatenated word as a new word for quickly lookup later
                    //word_set.add(word);
                    return true;
                }
            }
        }
        nonCombination.add(word);
        return false;
    }
}

// Solution 2: 
