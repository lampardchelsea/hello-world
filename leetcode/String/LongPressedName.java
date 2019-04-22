/**
 Refer to
 https://leetcode.com/problems/long-pressed-name/
 Your friend is typing his name into a keyboard.  Sometimes, when typing a character c, 
 the key might get long pressed, and the character will be typed 1 or more times.

You examine the typed characters of the keyboard.  Return True if it is possible that it 
was your friends name, with some characters (possibly none) being long pressed.

Example 1:
Input: name = "alex", typed = "aaleex"
Output: true
Explanation: 'a' and 'e' in 'alex' were long pressed.

Example 2:
Input: name = "saeed", typed = "ssaaedd"
Output: false
Explanation: 'e' must have been pressed twice, but it wasn't in the typed output.

Example 3:
Input: name = "leelee", typed = "lleeelee"
Output: true

Example 4:
Input: name = "laiden", typed = "laiden"
Output: true
Explanation: It's not necessary to long press any character.
 
Note:
name.length <= 1000
typed.length <= 1000
The characters of name and typed are lowercase letters.
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/long-pressed-name/solution/
/**
 Approach 2: Two Pointer
 Intuition
As in Approach 1, we want to check the key and the count. We can do this on the fly.
Suppose we read through the characters name, and eventually it doesn't match typed.
There are some cases for when we are allowed to skip characters of typed. Let's use a 
tuple to denote the case (name, typed):
In a case like ('aab', 'aaaaab'), we can skip the 3rd, 4th, and 5th 'a' in typed because 
we have already processed an 'a' in this block.
In a case like ('a', 'b'), we can't skip the 1st 'b' in typed because we haven't processed 
anything in the current block yet.

Algorithm
This leads to the following algorithm:
For each character in name, if there's a mismatch with the next character in typed:
If it's the first character of the block in typed, the answer is False.
Else, discard all similar characers of typed coming up. The next (different) character coming must match.
Also, we'll keep track on the side of whether we are at the first character of the block.
*/
class Solution {
    public boolean isLongPressedName(String name, String typed) {
        for(int i = 0, j = 0; i < name.length(); i++, j++) {
            if(j == typed.length()) {
                return false;
            }
            // If mismatch
            if(typed.charAt(j) != name.charAt(i)) {
                // If its the first char of the block, return false
                if(j == 0 || typed.charAt(j - 1) != typed.charAt(j)) {
                    return false;
                }
                // Discard all similar chars
                char cur = typed.charAt(j);
                while(j < typed.length() && typed.charAt(j) == cur) {
                    j++;
                }
                // If next char isn't match, return false
                if(j == typed.length() || typed.charAt(j) != name.charAt(i)) {
                    return false;
                }
            }
        }
        return true;
    }
}
