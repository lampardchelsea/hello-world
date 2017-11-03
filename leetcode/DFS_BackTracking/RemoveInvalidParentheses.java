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
