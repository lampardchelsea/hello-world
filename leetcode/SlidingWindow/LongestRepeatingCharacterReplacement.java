/**
Refer to
https://leetcode.com/problems/longest-repeating-character-replacement/
Given a string s that consists of only uppercase English letters, you can perform at most k operations on that string.
In one operation, you can choose any character of the string and change it to any other uppercase English character.
Find the length of the longest sub-string containing all repeating letters you can get after performing the above operations.

Note:
Both the string's length and k will not exceed 104.

Example 1:
Input:
s = "ABAB", k = 2
Output:
4
Explanation:
Replace the two 'A's with two 'B's or vice versa.
 
Example 2:
Input:
s = "AABABBA", k = 1
Output:
4
Explanation:
Replace the one 'A' in the middle with 'B' and form "AABBBBA".
The substring "BBBB" has the longest repeating letters, which is 4.
*/

// Solution 1: Not fixed length window using two pointers + criteria
// Refer to
// https://leetcode.com/problems/longest-repeating-character-replacement/discuss/91271/Java-12-lines-O(n)-sliding-window-solution-with-explanation
/**
public int characterReplacement(String s, int k) {
        int len = s.length();
        int[] count = new int[26];
        int start = 0, maxCount = 0, maxLength = 0;
        for (int end = 0; end < len; end++) {
            maxCount = Math.max(maxCount, ++count[s.charAt(end) - 'A']);
            while (end - start + 1 - maxCount > k) {
                count[s.charAt(start) - 'A']--;
                start++;
            }
            maxLength = Math.max(maxLength, end - start + 1);
        }
        return maxLength;
    }
There's no edge case for this question. The initial step is to extend the window to its limit, that is, the longest 
we can get to with maximum number of modifications. Until then the variable start will remain at 0.

Then as end increase, the whole substring from 0 to end will violate the rule, so we need to update start accordingly 
(slide the window). We move start to the right until the whole string satisfy the constraint again. Then each time we 
reach such situation, we update our max length.
*/


// Why we don't update "maxRepeat" ?
// Refer to
// https://leetcode.com/problems/longest-repeating-character-replacement/discuss/91271/Java-12-lines-O(n)-sliding-window-solution-with-explanation/95833
// https://leetcode.com/problems/longest-repeating-character-replacement/discuss/91271/Java-12-lines-O(n)-sliding-window-solution-with-explanation/95822
/**
This solution is great, best so far. However, it requires a bit more explanation.

Since we are only interested in the longest valid substring, our sliding windows need not shrink, even if a window may 
cover an invalid substring. We either grow the window by appending one char on the right, or shift the whole window to 
the right by one. And we only grow the window when the count of the new char exceeds the historical max count (from a 
previous window that covers a valid substring).

That is, we do not need the accurate max count of the current window; we only care if the max count exceeds the historical 
max count; and that can only happen because of the new char.

Here's my implementation that's a bit shorter

public int characterReplacement(String s, int k)
{
    int[] count = new int[128];
    int max=0;
    int start=0;
    for(int end=0; end<s.length(); end++)
    {
        max = Math.max(max, ++count[s.charAt(end)]);
        if(max+k<=end-start)
            count[s.charAt(start++)]--;
    }
    return s.length()-start;
}

Basics:
We use a window.
1.The window starts at the left side of the string and the length of the window is initialized to zero.
2.The window only stays the same or grows in length as the algorithm proceeds.
3.The algorithm grows the window to the maximum valid length according to the rules of the game.
4.The algorithm returns the length of the window upon completion.

Setup:
1.The length of the window is defined by the following equation: end - start + 1.
2.The values of both start and end are subject to change during the execution of the algorithm.
3.The value of end starts at 0 and gets incremented by 1 with each execution of the loop.
4.But unless a certain condition is met, the value of start will also gets incremented with each execution of the loop, 
  keeping the length of the window unchanged.
5.That condition is met when the number of the most commonly occuring character in the window + k is at least as large 
  as the length of the window (the value of k determines how many of the less commonly occurring characters there can be). 
  This condition would be required to create a string containing all the same characters from the characters contained 
  within the window.

Execution:
1.Right in the beginning, the length of the window is going to be able to grow to at least the value of k.
2.After that initial growth, the algorithm becomes a search to find the window that contains the greatest number of 
  reoccurring characters.
3.Whenever including the character at end results in an increase in the greatest number of reoccurring characters ever 
  encountered in a window tested so far, the window grows by one (by not incrementing start).
4.When determining whether or not including another character at the end of the window results in the increase described 
  above, only the occurrence of the newly included character in the window and the running all-time max need to be taken 
  into account (after all, only the frequency of the newly included character is increasing).
5.Even if/when the value of start is incremented (i.e. the left side of the window is moved to the right), the all-time 
  max doesn't need to be reset to reflect what's currently in the window because 1) at this point in the algorithm, the 
  all-time maximum number of reoccurring characters in a window is what we're using to determine the all-time longest 
  window; and 2) we only care about positive developments in our search (i.e. we find a window that contains an even greater 
  number of reoccurring characters than any other window we have tested so far and therefore is longer than any window we 
  have tested so far). The algorithm becomes a search for the max and we only need to set the max when we have a new max.
*/


// Why we have "j - i + 1 - maxRepeat > k" ?
// Refer to
// https://leetcode.com/problems/longest-repeating-character-replacement/discuss/91271/Java-12-lines-O(n)-sliding-window-solution-with-explanation/137008
/**
In case anyone is confused by this solution, here's another way of explaining it:

end-start+1 = size of the current window
maxCount = largest count of a single, unique character in the current window

The main equation is: end-start+1-maxCount

When end-start+1-maxCount == 0, then then the window is filled with only one character
When end-start+1-maxCount > 0, then we have characters in the window that are NOT the character that occurs the most. 
end-start+1-maxCount is equal to exactly the # of characters that are NOT the character that occurs the most in that window. 
Example: For a window "xxxyz", end-start+1-maxCount would equal 2. (maxCount is 3 and there are 2 characters here, "y" and "z" 
that are not "x" in the window.)

We are allowed to have at most k replacements in the window, so when end-start+1-maxCount > k, then there are more characters 
in the window than we can replace, and we need to shrink the window.

If we have window with "xxxy" and k = 1, that's fine because end-start+1-maxCount = 1, which is not > k. maxLength gets updated to 4.

But if we then find a "z" after, like "xxxyz", then we need to shrink the window because now end-start+1-maxCount = 2, and 2 > 1. 
The window becomes "xxyz".

maxCount may be invalid at some points, but this doesn't matter, because it was valid earlier in the string, and all that matters is 
finding the max window that occurred anywhere in the string. Additionally, it will expand if and only if enough repeating characters 
appear in the window to make it expand. So whenever it expands, it's a valid expansion.

Hope that helps.
P.S. Yes, as several other people mentioned already, the while should be replaced with if. The time complexity is exactly the same, 
because the while-loop only runs once anyway.
*/

// Style 1: Separately use 2 statements to increase / decrease i and j where i means start index and j means end index
// Runtime: 6 ms, faster than 54.12% of Java online submissions for Longest Repeating Character Replacement.
// Memory Usage: 39 MB, less than 70.42% of Java online submissions for Longest Repeating Character Replacement.
class Solution {
    public int characterReplacement(String s, int k) {
        int maxLen = 0;
        int maxRepeat = 0;
        int i = 0;
        int[] freq = new int[26];
        for(int j = 0; j < s.length(); j++) {
            freq[s.charAt(j) - 'A']++;
            maxRepeat = Math.max(maxRepeat, freq[s.charAt(j) - 'A']);
            if(j - i + 1 - maxRepeat > k) {
                freq[s.charAt(i) - 'A']--;
                i++;
            }
            maxLen = Math.max(maxLen, j - i + 1);
        }
        return maxLen;
    }
}


// Style 2: Instead of separately use 2 statements to increase / decrease i and j where i means start index and j means end index,
// we merge into one line statement, we promote to 5ms now
// Runtime: 5 ms, faster than 68.92% of Java online submissions for Longest Repeating Character Replacement.
// Memory Usage: 39.2 MB, less than 38.09% of Java online submissions for Longest Repeating Character Replacement.
class Solution {
    public int characterReplacement(String s, int k) {
        int maxLen = 0;
        int maxRepeat = 0;
        int i = 0;
        int[] freq = new int[26];
        for(int j = 0; j < s.length(); j++) {
            maxRepeat = Math.max(maxRepeat, ++freq[s.charAt(j) - 'A']);
            if(j - i + 1 - maxRepeat > k) {
                freq[s.charAt(i++) - 'A']--;
            }
            maxLen = Math.max(maxLen, j - i + 1);
        }
        return maxLen;
    }
}

// Style 3: Instead of looply calculate the maxLen, we just need s.length() - i where i means the final position of start index
// to calculate the final window length, we further promote to 4ms now.
// Runtime: 4 ms, faster than 93.59% of Java online submissions for Longest Repeating Character Replacement.
// Memory Usage: 39.3 MB, less than 39.09% of Java online submissions for Longest Repeating Character Replacement.
class Solution {
    public int characterReplacement(String s, int k) {
        int maxRepeat = 0;
        int i = 0;
        int[] freq = new int[26];
        for(int j = 0; j < s.length(); j++) {
            maxRepeat = Math.max(maxRepeat, ++freq[s.charAt(j) - 'A']);
            if(j - i + 1 - maxRepeat > k) {
                freq[s.charAt(i++) - 'A']--;
            }
        }
        return s.length() - i;
    }
}


