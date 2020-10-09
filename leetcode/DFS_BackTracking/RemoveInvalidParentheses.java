/**
 * Refer to
 * https://leetcode.com/problems/remove-invalid-parentheses/description/
 * Remove the minimum number of invalid parentheses in order to make the input string valid. 
   Return all possible results.

    Note: The input string may contain letters other than the parentheses ( and ).

    Examples:
    "()())()" -> ["()()()", "(())()"]
    "(a)())()" -> ["(a)()()", "(a())()"]
    ")(" -> [""]
 *
 * Solution
 * https://discuss.leetcode.com/topic/30743/easiest-9ms-java-solution?page=1
 * Here I share my DFS or backtracking solution. It's 10X faster than optimized BFS.

    Limit max removal rmL and rmR for backtracking boundary. Otherwise it will exhaust all possible 
    valid substrings, not shortest ones.
    Scan from left to right, avoiding invalid strs (on the fly) by checking num of open parens.
    If it's '(', either use it, or remove it.
    If it's '(', either use it, or remove it.
    Otherwise just append it.
    Lastly set StringBuilder to the last decision point.
    In each step, make sure:

    i does not exceed s.length().
    Max removal rmL rmR and num of open parens are non negative.
    De-duplicate by adding to a HashSet.
    Compared to 106 ms BFS (Queue & Set), it's faster and easier. Hope it helps! Thanks.
*/
class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> result = new ArrayList<String>();
        if(s == null) {
            return result;
        }
        if(s.length() == 0) {
            result.add("");
            return result;
        }
        char[] chars = s.toCharArray();
        // Limit max removal maxRemovableLeft and maxRemovableRight for backtracking boundary. 
        // Otherwise it will exhaust all possible valid substrings, not shortest ones
        // The openParenthesisPair argument is to verify that parentheses are matching, 
        // for example, "()", visit '(' to increment integer variable openParenthesisPair one, 
        // and visit ')' to decrement integer variable openParenthesisPair variable one
        int maxRemovableLeft = 0, maxRemovableRight = 0, openParenthesisPair = 0;
        for(int i = 0; i < chars.length; i++) {
            if(chars[i] == '(') {
                maxRemovableLeft++;
            } else if(chars[i] == ')') {
                if(maxRemovableLeft != 0) {
                    maxRemovableLeft--;
                } else {
                    maxRemovableRight++;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        // De-duplicate by adding to a HashSet
        Set<String> set = new HashSet<String>();
        dfs(chars, 0, set, sb, maxRemovableLeft, maxRemovableRight, 0);
        return new ArrayList<String>(set);
    }
    
    private void dfs(char[] chars, int index, Set<String> set, StringBuilder sb, int maxRemovableLeft, int maxRemovableRight, int openParenthesisPair) {
        // Max removal maxRemovableLeft maxRemovableRight and openParenthesisPair are non negative
        if(maxRemovableLeft < 0 || maxRemovableRight < 0 || openParenthesisPair < 0) {
            return;
        }
        // Base case: index does not exceed chars.length
        if(index == chars.length) {
            // Scan from left to right, avoiding invalid strs (on the fly) by checking num of openParenthesisPair == 0
            if(maxRemovableLeft == 0 && maxRemovableRight == 0 && openParenthesisPair == 0) {
                set.add(sb.toString());
            }
            return;
        }
        
        /**
         * Refer to
         * https://discuss.leetcode.com/topic/30743/easiest-9ms-java-solution/3?page=1
         * To understand it, first make sure you understand the idea of backtracking which is 
           extremely powerful. It's like going into a maze, and do a silly exhausted depth 
           first search. When you meet the dead end, you need to go back to the last successful 
           decision point, and try another direction.
           
           Back to this question. Based on 3 cases of current char: (, ) or others, we have 3 
           directions of at each maze point. Notice that no matter which direction we choose, 
           there is exactly one char added. So after going to the dead end and gathering some 
           chars, it will go backwards by removing one char at a time. (To make it clearer, 
           I've modified the last line)
           
           So this is what I found extremely useful pattern but some might not know:
           
           int len = sb.length();
           ... backtracking ...
           sb.setLength(len);
           
           It has at least two merits:
           (1) StringBuilder is much more efficient than String concat.
           (2) sb.setLength(len) method is naturally adapted to backtracking.
        */
        int len = sb.length(); // Prepare checkpoint for backtracking
        
        char c = chars[index]; 
        // If it's '(', either use it, or remove it
        if(c == '(') {
            /**
             * Note: The order to 'not use it' and 'use it' cannot revert
             * Refer to
             * https://discuss.leetcode.com/topic/30743/easiest-9ms-java-solution/29?page=2
             * The order of these 2 calls does matter.
               StringBuilder is mutable - when we use sb.append(c), we've altered sb's value 
               (its internal char array) by appending one more character to the end.
               
               For example, at the beginning, sb.toString() was "a(b)c" , and the current char was '(':
               If we do the 2 calls as in the order as in @yavinci's original code, we will get
               sb.toString().equals("a(b)c") => true               
               dfs(s, i + 1, res, sb, rmL - 1, rmR, open);	// not use (
               sb.toString().equals("a(b)c") => true
               dfs(s, i + 1, res, sb.append(c), rmL, rmR, open + 1); // use (
               
               On the other hand, if we switch the order of those 2 calls,
               sb.toString().equals("a(b)c") => true
               dfs(s, i + 1, res, sb.append(c), rmL, rmR, open + 1); // use (
               sb.toString().equals("a(b)c") => false, 
               because sb has been appended, and now it has value "a(b)c(". we'll pass a wrong value 
               to the next call.
               dfs(s, i + 1, res, sb, rmL - 1, rmR, open);	// not use (
               
               Note: To test it in easy way just use single "(" as input
            */
            // Not use it
            dfs(chars, index + 1, set, sb, maxRemovableLeft - 1, maxRemovableRight, openParenthesisPair);
            // Use it
            dfs(chars, index + 1, set, sb.append(c), maxRemovableLeft, maxRemovableRight, openParenthesisPair + 1);
        // If it's ')', either use it, or remove it
        } else if(c == ')') {
            // Not use it
            dfs(chars, index + 1, set, sb, maxRemovableLeft, maxRemovableRight - 1, openParenthesisPair);
            // Use it
            dfs(chars, index + 1, set, sb.append(c), maxRemovableLeft, maxRemovableRight, openParenthesisPair - 1);
        } else {
            // The input may contain other chars and must remain into final result, just append it
            dfs(chars, index + 1, set, sb.append(c), maxRemovableLeft, maxRemovableRight, openParenthesisPair);
        }
        
        sb.setLength(len); // Backtracking, lastly set StringBuilder to the last decision point.
    }
}

// Re-work
// BFS
// Refer to
// https://leetcode.com/problems/remove-invalid-parentheses/discuss/75032/Share-my-Java-BFS-solution
/**
The idea is straightforward, with the input string s, we generate all possible states by removing one ( or ), 
check if they are valid, if found valid ones on the current level, put them to the final result list and we 
are done, otherwise, add them to a queue and carry on to the next level.

The good thing of using BFS is that we can guarantee the number of parentheses that need to be removed is minimal, 
also no recursion call is needed in BFS.

Thanks to @peisi, we don't need stack to check valid parentheses.

Time complexity:
In BFS we handle the states level by level, in the worst case, we need to handle all the levels, we can analyze 
the time complexity level by level and add them up to get the final complexity.

On the first level, there's only one string which is the input string s, let's say the length of it is n, to check 
whether it's valid, we need O(n) time. On the second level, we remove one ( or ) from the first level, so there 
are C(n, n-1) new strings, each of them has n-1 characters, and for each string, we need to check whether it's 
valid or not, thus the total time complexity on this level is (n-1) x C(n, n-1). Come to the third level, total 
time complexity is (n-2) x C(n, n-2), so on and so forth...

Finally we have this formula:
T(n) = n x C(n, n) + (n-1) x C(n, n-1) + ... + 1 x C(n, 1) = n x 2^(n-1).
*/
class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> result = new ArrayList<String>();
        Set<String> visited = new HashSet<String>();
        Queue<String> q = new LinkedList<String>();
        q.offer(s);
        visited.add(s);
        // This ensures once we've found a valid parentheses pattern, 
        // we don't do any further bfs using items pending in the queue 
        // since any further bfs would only yield strings of smaller length. 
        // However the items already in queue need to be processed since 
        // there could be other solutions of the same length.
        boolean found = false;
        while(!q.isEmpty()) {
            String cur = q.poll();
            if(isValid(cur)) {
                result.add(cur);
                found = true;
            }
            // Use boolean flag 'found' to block further depth (smaller
            // length string) check, but keep check current depth by continue
            if(found) {
                continue;
            }
            for(int i = 0; i < cur.length(); i++) {
                // If current char is '(' or ')' try to remove
                if(cur.charAt(i) == '(' || cur.charAt(i) == ')') {
                    String next = cur.substring(0, i) + cur.substring(i + 1);
                    if(!visited.contains(next)) {
                        visited.add(next);
                        q.offer(next);
                    }
                }
            }
        }
        return result;
    }
    
    // No need to use stack to check valid parentheses pair or not
    private boolean isValid(String s) {
        int count = 0;
        for(int i = 0; i < s.length(); i++) {
            // When encounter open parenthesis increase count
            if(s.charAt(i) == '(') {
                count++;
            }
            if(s.charAt(i) == ')') {
                // In the case of valid parentheses, the count cannot be 0 
                // if a closing parenthesis is encountered, since 0 means
                // no open parenthesis
                if(count == 0) {
                    return false;
                }
                count--;
            }
        }
        return count == 0;
    }
}
