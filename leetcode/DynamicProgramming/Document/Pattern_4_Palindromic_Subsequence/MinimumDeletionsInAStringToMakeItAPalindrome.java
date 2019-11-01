/**
 Refer to
 https://www.techiedelight.com/find-minimum-number-deletions-convert-string-into-palindrome/
 Given a string A, compute the minimum number of characters you need to delete to make 
 resulting string a palindrome.
 
Examples:
Input : baca
Output : 1

Input : geek
Output : 2
*/
// Solution 1: Native DFS
// Refer to
// https://www.techiedelight.com/find-minimum-number-deletions-convert-string-into-palindrome/
/**
  The idea is to use recursion to solve this problem, the idea is compare the last character of
  the string X[i...j] with its first character, there are two possibilities -
  1. If the character of the string is same as the first character, no deletion is 
     needed and we recur for the remaining substring X[i + 1, j - 1]
  2. If last character of string is different from the first character, then we return 
     one plus maximum of the two values we get by
     (1) delete the last character and recusing for the remaining substring X[i...j - 1]
     (2) delete the first character and recusing for the remaining substring X[i + 1...j]
     This yeilds the below recursive relation:
     T[i...j] = | T[i + 1...j - 1]                       (if X[i] == X[j])
                | 1 + max(T[i + 1...j], T[i...j - 1])    (if X[i] 1= X[j])
*/
class Solution {
    public int minDeletions(String s) {
        return helper(s, 0, s.length() - 1);
    }

    public int helper(String s, int i, int j) {
        // Base condition
        if(i >= j) {
            return 0;
        }
        // If last character of the String is same as the first character
        // no need to remove anything character, just checking next level
        if(s.charAt(i) == s.charAt(j)) {
            return helper(s, i + 1, j - 1);
        }
        return 1 + Math.min(helper(s, i, j - 1), helper(s, i + 1, j));
    }
}
