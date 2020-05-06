/**
 Refer to
 https://leetcode.com/problems/similar-string-groups/
 Two strings X and Y are similar if we can swap two letters (in different positions) of X, so that it equals Y. 
 Also two strings X and Y are similar if they are equal.
 For example, "tars" and "rats" are similar (swapping at positions 0 and 2), and "rats" and "arts" are similar, 
 but "star" is not similar to "tars", "rats", or "arts".
 Together, these form two connected groups by similarity: {"tars", "rats", "arts"} and {"star"}.  Notice that 
 "tars" and "arts" are in the same group even though they are not similar.  Formally, each group is such that 
 a word is in the group if and only if it is similar to at least one other word in the group.
 We are given a list A of strings.  Every string in A is an anagram of every other string in A.  
 How many groups are there?
 
 Example 1:
 Input: A = ["tars","rats","arts","star"]
 Output: 2
 
 Constraints:
 1 <= A.length <= 2000
 1 <= A[i].length <= 1000
 A.length * A[i].length <= 20000
 All words in A consist of lowercase letters only.
 All words in A have the same length and are anagrams of each other.
 The judging time limit has been increased for this question.
*/

// Solution 1: DFS
// Refer to
// https://leetcode.com/problems/similar-string-groups/discuss/132318/Simple-Java-Solution-using-DFS
// https://leetcode.com/problems/similar-string-groups/discuss/132318/Simple-Java-Solution-using-DFS/297615
class Solution {
    public int numSimilarGroups(String[] A) {
        if(A.length < 2) {
            return A.length;
        }
        int result = 0;
        for(int i = 0; i < A.length; i++) {
            if(A[i] == null) {
                continue;
            }
            String str = A[i];
            A[i] = null;
            result++;
            helper(A, str);
        }
        return result;
    }
    
    private void helper(String[] arr, String s) {
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == null) {
                continue;
            }
            // Check if both string str and arr[i] belong in same group
            if(isSimilar(arr[i], s)) {
                String str = arr[i];
                arr[i] = null;
                helper(arr, str);
            }
        }
    }
    
    private boolean isSimilar(String a, String b) {
        int count = 0;
        int i = 0;
        while(count <= 2 && i < a.length()) {
            if(a.charAt(i) != b.charAt(i)) {
                count++;
            }
            i++;
        }
        // To consider the case that two strings are exactly same must add || count == 0
        return count == 2 || count == 0;
    }
}
