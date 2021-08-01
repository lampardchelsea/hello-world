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

// For Style 3 try to uniform the helper similar to Style 2 by removing 'step' inside helper method
// Refer to
// https://leetcode.com/problems/2-keys-keyboard/discuss/866150/C%2B%2B-Simple-Recursion-with-explanation-or-5-lines
/**
Intially I couldn't even think how this could be a DP problem. So classic way, Recursion -> DP with overlapping subproblems. 
But once I drew the recursion tree, got complete clarity and discovered the hidden variable i.e, the size of clipboard.

Simplied thinking :
On the screen, there is A. You need to generate 'n' A ie if n = 10, AAAAAAAAAA. You can either copy all the As on screen or 
just paste a combination of A that was last copied into clipboard.

Intuition :
Cost of copy all the As on Screen(basically double the number of A's or 2X) = 1 (cost of moving all A's to clipboard) + 1 
(cost of coping call the A's from clipboard to screen)
Cost of copy from Clipboard = 1 (cost of coping call the A's from clipboard to screen)

There are two choices ie two costs to pick from:

Copy existing a into clipboard and paste clipboard into Screen at cost = 2. If picked the screen char count doubles and the 
clipboard size is equal to current screen size.
Paste into Screen at cost = 1. Everytime copied from clipboard, screen gets increased by additional clipboard.length. 
Remember, size of clipboard depends on what was last copied.
So pick between the most optimal choice, here, minimum of both the choices.

Obviously there are overlapping subproblems in it. It is not the most optimal way to solve it. But because the constraint 
is <= 1000, the solution is a accepted.

// Elaborated code
class Solution {
public:
    int shortCopy(int n, int screen, int clip){
        if(screen == n)
            return 0;
        if(screen > n)
            return 1002;
	    // At max, we need 1000 operations in worst case.
		// So pick number greater than that.(not INT_MAX will overflow)
        return min(
            2 + shortCopy(n, 2*screen, screen),
            1 + shortCopy(n, screen + clip, clip)
        );
    }

    int minSteps(int n) {
        if(n == 1) return 0;
        return shortCopy(n, 1, 1) + 1;
		// Initial clipboard size is 1 because first pick is
		// always copying A into clipbored. Both the choices include
		// copying into the clipboard, so without coping first A, no operation can be made
    }
};
*/
class Solution {
    public int minSteps(int n) {
        if(n == 1) {
            return 0;
        }
        // Initial clipboard size is 1 because first pick is 
        // always copying A into clipbored. Both the choices include 
        // copying into the clipboard, so without coping first A, 
        // no operation can be made, and it need 1 step, so plus 1
        return helper(n, 1, 1) + 1;
    }
    
    private int helper(int n, int curAOnNotepad, int curACopied) {
        if(curAOnNotepad == n) {
            return 0;
        }
        if(curAOnNotepad > n) {
            return n + 1;
        }
        int steps = n;
        // Copy and paste
        steps = Math.min(steps, helper(n, curAOnNotepad * 2, curAOnNotepad) + 2);
        // Paste only
        steps = Math.min(steps, helper(n, curAOnNotepad + curACopied, curACopied) + 1);
        return steps;
    }
}


// Solution 2: Top Down DP Memoization (Only able to based on Solution 1 Style 2 because in 
// Style 3 curAOnNotepad * 2 will cause index out of boundary exception)
// Refer to
// https://leetcode.com/problems/2-keys-keyboard/discuss/521405/Java-Dp-Top-Down
class Solution {
    public int minSteps(int n) {
        Integer[][] memo = new Integer[n + 1][n + 1];
        return helper(n, 1, 0, memo);
    }
    
    private int helper(int n, int curAOnNotepad, int curACopied, Integer[][] memo) {
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
        // Must behind if(curAOnNotepad > n) check, otherwise out of boundary exception
        if(memo[curAOnNotepad][curACopied] != null) {
            return memo[curAOnNotepad][curACopied];
        }
        // Setup a maximum needed option (1 copy and n - 1 times paste need)
        int steps = n;
        // Avoid infinite copying when notepad(curAOnNotepad) and 
        // clipboard(curACopied) has same number of A
        if(curAOnNotepad != curACopied) {
            steps = Math.min(steps, helper(n, curAOnNotepad, curAOnNotepad, memo) + 1);
        }
        // Avoid infinite pasting by don't paste with nothing copied
        if(curACopied != 0) {
            steps = Math.min(steps, helper(n, curAOnNotepad + curACopied, curACopied, memo) + 1);
        }
        memo[curAOnNotepad][curACopied] = steps;
        return steps;
    }
}

// Solution 3: Bottom Up DP
// Refer to
// https://leetcode.com/problems/2-keys-keyboard/discuss/105899/Java-DP-Solution
// https://leetcode.com/problems/2-keys-keyboard/discuss/105899/Java-DP-Solution/120412
/**
Elegant solution.
Allow me to explain what is being done here.
As per the keyboard operations:
to get AA from A we need 2 additional steps (copy-all and then paste)
to get AAA from A we need 3 additional steps (copy-all, then paste, then again paste)

For generating AAAA we need 2 additional steps from AA.
however, to get AAAAAAAA, the most optimal way would be from AAAA, with 2 additional steps (copy-all then paste)
Essentially, we find the next smaller length sequence (than the one under consideration) which can be copied and 
then pasted over multiple times to generate the desired sequence. The moment we find a length that divides our 
required sequence length perfectly, then we don't need to check for any smaller length sequences.

// if sequence of length 'j' can be pasted multiple times to get length 'i' sequence
if (i % j == 0) {
    // we just need to paste sequence j (i/j - 1) times, hence additional (i/j) times since we need to copy it first as well.
    // we don't need checking any smaller length sequences 
    dp[i] = dp[j] + (i/j);
    break;
}
*/
class Solution {
    public int minSteps(int n) {
        int[] dp = new int[n + 1];
        for(int i = 2; i <= n; i++) {
            dp[i] = i;
            for(int j = i - 1; j > 1; j--) {
                // if sequence of length 'j' can be pasted multiple times 
                // to get length 'i' sequence
                if(i % j == 0) {
                    // we just need to paste sequence j (i/j - 1) times, 
                    // hence additional (i/j) times since we need to copy 
                    // it first as well. we don't need checking any smaller 
                    // length sequences 
                    dp[i] = dp[j] + (i / j);
                    break;
                }
            }
        }
        return dp[n];
    }
}
