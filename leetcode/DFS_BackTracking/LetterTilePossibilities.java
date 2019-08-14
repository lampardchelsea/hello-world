/**
 Refer to
 https://leetcode.com/problems/letter-tile-possibilities/
 You have a set of tiles, where each tile has one letter tiles[i] printed on it.  
 Return the number of possible non-empty sequences of letters you can make.

Example 1:
Input: "AAB"
Output: 8
Explanation: The possible sequences are "A", "B", "AA", "AB", "BA", "AAB", "ABA", "BAA".

Example 2:
Input: "AAABBC"
Output: 188

Note:
1 <= tiles.length <= 7
tiles consists of uppercase English letters.
*/

// To resolve this issue refer to 2 problems first.
// (1) Permutation Sequence
// https://leetcode.com/problems/letter-tile-possibilities/discuss/308212/Java-consider-all-permutations
// -> https://leetcode.com/problems/permutation-sequence/
// (2) Subsets II
// https://leetcode.com/problems/letter-tile-possibilities/discuss/308280/simple-java-version-with-backtracking
// -> https://leetcode.com/problems/subsets-ii/

// Solution 1: Concise java solution
// Refer to
// https://leetcode.com/problems/letter-tile-possibilities/discuss/308284/Concise-java-solution
/**
 Thoughts
input: AAB
count: A -> 2, B -> 1

For sequence of length 1:
We can pick either A, or B.
So we have "A", "B".

For sequence of length 2:
We build it based on "sequence of length 1"
For "A":
count: A -> 1, B -> 1
We can still pick either A, or B
So we have "AA", "AB"
For "B":
count: A -> 2, B -> 0
We can only pick A
So we have "BA"

For sequence of length 3: blablabla

Implementation
We don't need to keep track of the sequence. We only need count
If we implement the above idea by each level (Count all sequence of length 1, 
then count all sequence of length 2, etc), we have to remember previous sequence.
So we use recursion instead. Just remember to add the count back (arr[i]++).

Note:
Initially worry about how to insert an 'A' on 'AB', we have 3 candidate positions
as spaceholder present: _A_B_, it may difficult to think _AAB_ is difficult to approach
since need to insert between 'A' and 'B', but actually its not require to insert
because adding ahead is same AA_B_, in conclusion, we just need to remember previous
sequence, and appending new character on it will gain all combination.
e.g remember AA, AB and BA, all append 'A' will create as AAA, ABA and BAA, since initial
start different as 'A' and 'B', if we only have two choice as A and B, after append a new 
character 'A' will also include all choice.

freq[i]--; means we are using the i-th tile ('A'+i) as the current tile because there are still remaining ones.
result++; means with these current tiles (not necessarily all the tiles given) we already have a valid combination.
result += helper(freq); means if we still want to add more tiles to the existing combination, 
we simply do recursion with the tiles left;
freq[i]++; is backtracking because we have finished exploring the possibilities of using the i-th tile 
and need to restore it and continue to explore other possibilities.
*/
class Solution {
    public int numTilePossibilities(String tiles) {
        char[] chars = tiles.toCharArray();
        int[] freq = new int[26];
        for(int i = 0; i < chars.length; i++) {
            freq[chars[i] - 'A']++;
        }
        return helper(freq);
    }

    private int helper(int[] freq) {
        int result = 0;
        for(int i = 0; i < 26; i++) {
            if(freq[i] != 0) {
                result++;
                freq[i]--;
                result += helper(freq);
                freq[i]++;
            }
        }
        return result;
    }
}

// Solution 2: Traditional DFS + backtracking, similar to Subsets II + Permutation Sequence
// Refer to
// https://leetcode.com/problems/letter-tile-possibilities/discuss/308280/simple-java-version-with-backtracking
// Some tips need to careful
// (1) Need HashSet and visited to remove duplicates
// (2) Must use StringBuilder to implement DFS instead of String, since String is immutable,
//     even sb.substring(...) will not affect the target String which should pass to next
//     level, the target String not change and directly pass into next level.
class Solution {
    Set<String> set = new HashSet<String>();
    public int numTilePossibilities(String tiles) {
        char[] chars = tiles.toCharArray();
        boolean[] visited = new boolean[chars.length];
        StringBuilder sb = new StringBuilder();
        helper(chars, sb, visited);
        return set.size();
    }
    
    private void helper(char[] chars, StringBuilder sb, boolean[] visited) {
        if(sb.length() > 0) {
            set.add(sb.toString());
        }
        if(sb.length() == chars.length) {
            return;
        }
        for(int i = 0; i < chars.length; i++) {
            if(!visited[i]) {
                visited[i] = true;
                int len = sb.length();
                sb.append(chars[i]);
                helper(chars, sb, visited);
                sb.setLength(len);
                visited[i] = false;
            }
        }
    }
}

// Wrong answer as using string instead of StringBuilder since StringBuilder is a buffered object, string is immutable,
// when doing DFS, it cannot truncate and keeps going longer
class Solution {
    Set<String> set = new HashSet<String>();
    public int numTilePossibilities(String tiles) {
        char[] chars = tiles.toCharArray();
        boolean[] visited = new boolean[chars.length];
        helper(chars, "", visited);
        return set.size();
    }
    
    private void helper(char[] chars, String temp, boolean[] visited) {
        if(temp.length() > 0) {
            set.add(temp);
        }
        if(temp.length() == chars.length) {
            return;
        }
        for(int i = 0; i < chars.length; i++) {
            if(!visited[i]) {
                visited[i] = true;
                int len = temp.length();
                temp += chars[i];
                helper(chars, temp, visited);
                temp.substring(0, len + 1);
                visited[i] = false;
            }
        }
    }
}

