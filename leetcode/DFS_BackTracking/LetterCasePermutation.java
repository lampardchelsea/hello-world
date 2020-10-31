/**
Refer to
https://leetcode.com/problems/letter-case-permutation/
Given a string S, we can transform every letter individually to be lowercase or uppercase to create another string.
Return a list of all possible strings we could create. You can return the output in any order.

Example 1:
Input: S = "a1b2"
Output: ["a1b2","a1B2","A1b2","A1B2"]

Example 2:
Input: S = "3z4"
Output: ["3z4","3Z4"]

Example 3:
Input: S = "12345"
Output: ["12345"]

Example 4:
Input: S = "0"
Output: ["0"]

Constraints:
S will be a string with length between 1 and 12.
S will consist only of letters or digits.
*/

// Solution 1: DFS
// Refer to
// https://leetcode.com/problems/letter-case-permutation/discuss/115485/Java-Easy-BFS-DFS-solution-with-explanation
// https://leetcode.com/problems/letter-case-permutation/discuss/255071/Java-detailed-explanation-of-DFSBacktracking-solution
/**
            a1b2   i=0, when it's at a, since it's a letter, we have two branches: a, A
         /        \
       a1b2       A1b2 i=1 when it's at 1, we only have 1 branch which is itself
        |          |   
       a1b2       A1b2 i=2 when it's at b, we have two branches: b, B
       /  \        / \
      a1b2 a1B2  A1b2 A1B2 i=3  when it's at 2, we only have one branch.
       |    |     |     |
      a1b2 a1B2  A1b2  A1B2 i=4 = S.length(). End recursion, add permutation to ans. 
      
      During this process, we are changing the S char array itself
*/
class Solution {
    public List<String> letterCasePermutation(String S) {
        List<String> result = new ArrayList<String>();
        helper(S.toCharArray(), result, 0);
        return result;
    }
    
    private void helper(char[] chars, List<String> result, int index) {
        if(index == chars.length) {
            result.add(new String(chars));
            return;
        }
        if(Character.isLetter(chars[index])) {
            chars[index] = Character.toUpperCase(chars[index]);
            helper(chars, result, index + 1);
            chars[index] = Character.toLowerCase(chars[index]);
            helper(chars, result, index + 1);
        } else {
            helper(chars, result, index + 1);
        }
    }
}

// Solution 2: BFS
// Refer to
// https://leetcode.com/problems/letter-case-permutation/discuss/115485/Java-Easy-BFS-DFS-solution-with-explanation
class Solution {
    public List<String> letterCasePermutation(String S) {
        List<String> result = new ArrayList<String>();
        Queue<String> q = new LinkedList<String>();
        q.offer(S);
        for(int i = 0; i < S.length(); i++) {
            if(Character.isLetter(S.charAt(i))) {
                int size = q.size();
                for(int j = 0; j < size; j++) {
                    String cur = q.poll();
                    char[] chars = cur.toCharArray();
                    chars[i] = Character.toUpperCase(chars[i]);
                    q.offer(new String(chars));
                    char[] chars2 = cur.toCharArray();
                    chars2[i] = Character.toLowerCase(chars2[i]);
                    q.offer(new String(chars2));
                }
            }
        }
        return new LinkedList<String>(q);
    }
}
