/**
Refer to
https://leetcode.com/problems/2-keys-keyboard/
There is only one character 'A' on the screen of a notepad. You can perform two operations on this notepad for each step:

Copy All: You can copy all the characters present on the screen (a partial copy is not allowed).
Paste: You can paste the characters which are copied last time.
Given an integer n, return the minimum number of operations to get the character 'A' exactly n times on the screen.

Example 1:
Input: n = 3
Output: 3
Explanation: Intitally, we have one character 'A'.
In step 1, we use Copy All operation.
In step 2, we use Paste operation to get 'AA'.
In step 3, we use Paste operation to get 'AAA'.

Example 2:
Input: n = 1
Output: 0

Constraints:
1 <= n <= 1000
*/

// Solution 1: Native DFS
// Style 1: Not very intuitive to understand, especially for Base case 3: Avoid a sequence with consecutive copies
// Refer to
// https://leetcode.com/problems/2-keys-keyboard/discuss/105979/Java-recursive-solution-with-explanation
class Solution {
    public int minSteps(int n) {
        // Special handle n == 1 no need any step
        return n == 1 ? 0 : 1 + helper(n, 1, 1);
    }
    
    private int helper(int n, int cur, int clipboard) {
        // Base case 1: If already reach target, no need more step
        if(cur == n) {
            return 0;
        }
        // Base case 2: Invalid solution will return negative, later 
        // logic able to identify based on negative value to find if 
        // go further dfs on that case or not
        if(cur > n) {
            return -1;
        }
        // Base case 3: Avoid a sequence with consecutive copies
        // Test out by: n == 3
        if(cur == clipboard) {	    
            int pasteCost = helper(n, cur + clipboard, clipboard);
            return pasteCost == -1 ? -1 : 1 + pasteCost;
        }
        // Just paste need 1 step and will change chars number on notepad
        // but not change chars number on clipboard
        int pasteCost = helper(n, cur + clipboard, clipboard);
        // Just copy need 1 step and will not change chars number on notepad
        // but change chars number on clipboard from clipboard to cur
        int copyCost = helper(n, cur, cur);
        // Not able to reach target in either way
        if(pasteCost == -1 && copyCost == -1) {
            return -1;
        // Only able to reach target as last step is paste
        } else if(copyCost == -1) {
            return pasteCost + 1;
        // Only able to reach target as last step is copy
        } else if(pasteCost == -1) {
            return copyCost + 1;
        // Either way able to reach target find the minimum one
        } else {
            return Math.min(pasteCost, copyCost) + 1;
        }
    }
}

// Style 2: Initialize current number of A copied to clipboard == 0 with two intuitive check
// 1. Avoid infinite copying when notepad(curAOnNotepad) and clipboard(curACopied) has same number of A
// 2. Avoid infinite pasting by don't paste with nothing copied
// Refer to
// https://leetcode.com/problems/2-keys-keyboard/discuss/462741/Java-or-Tidy-DFS-Solution
class Solution {
    public int minSteps(int n) {
        return helper(n, 1, 0);
    }
    
    private int helper(int n, int curAOnNotepad, int curACopied) {
        // If A on notepad equal to n no further step needed
        if(curAOnNotepad == n) {
            return 0;
        }
        // If A on notepad larger than n, invalid solution return 
        // potential maximum value (1 time copy and n time paste to
        // create n + 1's A [+ 1 is the original one A on notepad] 
        // on notepad which 1 more A than actual needed) in case of 
        // try to find minimum
        if(curAOnNotepad > n) {
            return n + 1;
        }
        // Setup a maximum needed option (1 copy and n - 1 times paste need)
        int steps = n;
        // Avoid infinite copying when notepad(curAOnNotepad) and 
        // clipboard(curACopied) has same number of A
        if(curAOnNotepad != curACopied) {
            steps = Math.min(steps, helper(n, curAOnNotepad, curAOnNotepad) + 1);
        }
        // Avoid infinite pasting by don't paste with nothing copied
        if(curACopied != 0) {
            steps = Math.min(steps, helper(n, curAOnNotepad + curACopied, curACopied) + 1);
        }   
        return steps;
    }
}

// Style 3: Initialize current number of A copied to clipboard == 1 and step == 1 without any additional check
// https://leetcode.com/problems/2-keys-keyboard/discuss/562030/C-recursive-DP-solution
/**
public class Solution {
    public int MinSteps(int n) {
        if (n == 1)
            return 0;
        return MinSteps(1, 1, 1, n);
    }
    
    private int MinSteps(int count, int copied, int step, int n) {
        if (count > n)
            return int.MaxValue;
        if (count == n)
            return step;
        return Math.Min(
            MinSteps(2*count, count, step+2, n),
            MinSteps(count + copied, copied, step+1, n)
        );
    }
}
*/
// https://leetcode.com/problems/2-keys-keyboard/discuss/460422/Java-recursive-DFS-solution
/**
class Solution {
    int steps = Integer.MAX_VALUE;
    public int minSteps(int n) {
        if (n == 1) return 0;
        minStepsUtil(n, 1, 1, 1);
        return steps;
    }
    
    public void minStepsUtil(int n, int stp, int curr, int prev) {
        if (curr > n) return;
        if (curr == n) {
            steps = Math.min(steps, stp);
            return;
        }
        
        minStepsUtil(n, stp+1, curr + prev, prev);
        minStepsUtil(n, stp+2, curr*2, curr);
    }
}
*/
class Solution {
    public int minSteps(int n) {
        if(n == 1) {
            return 0;
        }
        // Why initialize not as helper(n, 1, 0, 0) ?
        // Because if 'curACopied' (current number of A copied to clipboard) is 0,
        // it will cause infinite recursive call since (curAOnNotepad + curACopied)
        // will be (curAOnNotepad + 0) as no change and in DFS call will never end
        // So, let's start with copy one A into clipboard, which cost 1 copy step,
        // which also help to make 'Paste only' option in later DFS directly valid
        return helper(n, 1, 1, 1);
    }
    
    private int helper(int n, int curAOnNotepad, int curACopied, int step) {
        // If A on notepad equal to n no further step needed
        if(curAOnNotepad == n) {
            return step;
        }
        // If A on notepad larger than n, invalid solution return 
        // potential maximum value (1 time copy and n time paste to
        // create n + 1's A [+ 1 is the original one A on notepad] 
        // on notepad which 1 more A than actual needed) in case of 
        // try to find minimum
        if(curAOnNotepad > n) {
            return n + 1;
        }
        // Copy and paste
        int copyAndPaste = helper(n, curAOnNotepad * 2, curAOnNotepad, step + 2);
        // Paste only
        int paste = helper(n, curAOnNotepad + curACopied, curACopied, step + 1);
        return Math.min(copyAndPaste, paste);
    }
}
