/**
Refer to
https://leetcode.com/problems/number-of-different-integers-in-a-string/
You are given a string word that consists of digits and lowercase English letters.

You will replace every non-digit character with a space. For example, "a123bc34d8ef34" will become " 123  34 8  34". 
Notice that you are left with some integers that are separated by at least one space: "123", "34", "8", and "34".

Return the number of different integers after performing the replacement operations on word.

Two integers are considered different if their decimal representations without any leading zeros are different.

Example 1:
Input: word = "a123bc34d8ef34"
Output: 3
Explanation: The three different integers are "123", "34", and "8". Notice that "34" is only counted once.

Example 2:
Input: word = "leet1234code234"
Output: 2

Example 3:
Input: word = "a1b01c001"
Output: 1
Explanation: The three integers "1", "01", and "001" all represent the same integer because
the leading zeros are ignored when comparing their decimal values.

Constraints:
1 <= word.length <= 1000
word consists of digits and lowercase English letters.
*/

// Solution 1: Remove leading zeros but consider all digits zero must return as zero
// Refer to
// https://stackoverflow.com/a/7089841/6706875
class Solution {
    public int numDifferentIntegers(String word) {
        StringBuilder sb = new StringBuilder();
        for(char c : word.toCharArray()) {
            if(Character.isLetter(c)) {
                sb.append(" ");
            } else {
                sb.append(c);
            }
        }
        String[] strs = sb.toString().trim().split("\\s+");
        Set<String> set = new HashSet<String>();
        for(String s : strs) {
            // Check if empty string, test case: "u"
            if(!s.equals("")) {
                // Remove leading zeros, test case: "a1b01c001"
                set.add(removeLeadingZeros(s));
            }
        }
        return set.size();
    }

    private String removeLeadingZeros(String s) {
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) != '0') {
                return s.substring(i);
            }
        }
        // In case all digits are 0, must consider as 0
        return "0";
    }
}
































https://leetcode.com/problems/number-of-different-integers-in-a-string/description/
You are given a string word that consists of digits and lowercase English letters.
You will replace every non-digit character with a space. For example, "a123bc34d8ef34" will become " 123  34 8  34". Notice that you are left with some integers that are separated by at least one space: "123", "34", "8", and "34".
Return the number of different integers after performing the replacement operations on word.
Two integers are considered different if their decimal representations without any leading zeros are different.
 
Example 1:
Input: word = "a123bc34d8ef34"
Output: 3
Explanation: The three different integers are "123", "34", and "8". Notice that "34" is only counted once.

Example 2:
Input: word = "leet1234code234"
Output: 2

Example 3:
Input: word = "a1b01c001"
Output: 1
Explanation: The three integers "1", "01", and "001" all represent the same integer because
the leading zeros are ignored when comparing their decimal values.
 
Constraints:
- 1 <= word.length <= 1000
- word consists of digits and lowercase English letters.
--------------------------------------------------------------------------------
Attempt 1: 2025-11-15
Solution 1: Two Pointers (60 min)
class Solution {
    public int numDifferentIntegers(String word) {
        Set<String> set = new HashSet<>();
        int n = word.length();
        for(int i = 0; i < n; i++) {
            if(Character.isDigit(word.charAt(i))) {
                // 'i' used for marking the end index of current digits section
                // 'j' used for marking the start index of current digits section
                int j = i;
                // Skip leading zeros but keep at least one digit if all zeros
                while(i < n && word.charAt(i) == '0') {
                    // 'j' present for start index advances together to shrink
                    // till actual non-zero start digit
                    i++;
                    j++;
                }
                // 'i' present end index continue through current digit section
                // for remaining digits
                while(i < n && Character.isDigit(word.charAt(i))) {
                    i++;
                }
                // Retrieve current section number without leading zeros
                String num = word.substring(j, i);
                set.add(num);
                // Adjust for loop increment
                i--;
            }
        }
        return set.size();
    }
}

Time Complexity: O(n)
Space Complexity: O(1)

Refer to Kimi
The code is almost correct, but it fails in one corner case:
Issue 1 – “all-zeros” sequence becomes empty string
When the entire numeric run is zeros, the first while-loop moves j past the last zero, so
substring(j, i) becomes "" (empty string).
The HashSet then treats every such run as the same empty string, although they represent the integer 0.
Example
Input: "a000b0000c"
Expected unique integers: { "0" } → size 1
Your code adds "" twice → set size 1 (accidentally correct here), but for
Input: "a000b0c" it still adds "" twice, whereas the human-readable normalization should produce "0" once.
Fix – keep at least one zero
After skipping leading zeros, clamp j so it never exceeds the last character of the run.
// replace this line:
// String num = word.substring(j, i);

// with:
if (j == i) j = i - 1;          // guarantee at least one digit
String num = word.substring(j, i);
Now every all-zero block becomes "0" instead of "", and the set counts them correctly.
Optional style issues (no correctness impact)
1.Re-scanning the zeros twice is unnecessary; the collect-then-strip idiom is shorter and clearer.
2.Variable names i (loop index) and j (start) are swapped versus usual conventions; consider renaming for readability.
With the one-line clamp above, the code passes all LeetCode cases.
Final fix code
class Solution {
    public int numDifferentIntegers(String word) {
        Set<String> set = new HashSet<>();
        int n = word.length();
        for(int i = 0; i < n; i++) {
            if(Character.isDigit(word.charAt(i))) {
                int j = i;          // start index of current digits section
                // skip leading zeros
                while(i < n && word.charAt(i) == '0') {
                    i++;
                    j++;
                }
                // continue through remaining digits
                while(i < n && Character.isDigit(word.charAt(i))) {
                    i++;
                }
                // if we skipped all digits (all zeros), keep one zero
                if (j == i) j = i - 1;
                String num = word.substring(j, i);
                set.add(num);
                i--;                // adjust for outer loop increment
            }
        }
        return set.size();
    }
}

Refer to Deepseek
🔍 Analysis of Kimi's Fix
Let me analyze this proposed fix:
class Solution {
    public int numDifferentIntegers(String word) {
        Set<String> set = new HashSet<>();
        int n = word.length();
        for(int i = 0; i < n; i++) {
            if(Character.isDigit(word.charAt(i))) {
                int j = i;          // start index of current digits section
                // skip leading zeros
                while(i < n && word.charAt(i) == '0') {
                    i++;
                    j++;
                }
                // continue through remaining digits
                while(i < n && Character.isDigit(word.charAt(i))) {
                    i++;
                }
                // if we skipped all digits (all zeros), keep one zero
                if (j == i) j = i - 1;
                String num = word.substring(j, i);
                set.add(num);
                i--;                // adjust for outer loop increment
            }
        }
        return set.size();
    }
}
✅ What This Fix Gets Right:
The key improvement is this line:
if (j == i) j = i - 1;
This handles the all-zeros case by backing up j one position when we've skipped all digits.
🧪 Test Cases That Now Work:
- "0" → j=1, i=1 → j = i-1 = 0 → substring(0,1) = "0" ✓
- "000" → j=3, i=3 → j = 2 → substring(2,3) = "0" ✓
- "0a0" → First: "0", Second: "0" → set {"0"} ✓
🚨 Remaining Issues:
Issue 1: Single Character Edge Case
// Input: "0" (length=1)
// i=0, j=0
// First while: i=1, j=1 (i == n, loop exits)
// if (j == i) → 1 == 1 ✓ → j = 0
// substring(0,1) = "0" ✓

// But what if we're at the last character?
// The logic depends on the second while loop not running
Issue 2: Index Out of Bounds Risk
if (j == i) j = i - 1;
What if i = 0? Then j = -1 → substring(-1, i) would crash!
Example that could break:
// Actually this can't happen because:
// - We enter the loop only if Character.isDigit(word.charAt(i))
// - So i must be valid
// - The first while advances i, so i >= original i
// - So i-1 >= 0
This is actually safe due to the loop conditions.
Issue 3: Confusing Logic
The fix is clever but not intuitive:
- j and i advance together in first loop
- Then we detect j == i means we skipped everything
- So we back up j to get one character
📊 Comparison: Kimi's Fix vs My Original
AspectKimi's FixMy Original SolutionCorrectness✅ Handles all cases✅ Handles all casesReadability⚠️ Clever but confusing✅ Very clearMaintainability⚠️ Magic number -1✅ No magic numbersEdge Case Safety✅ Actually safe✅ SafeLogic Clarity⚠️ Why j = i - 1?✅ Clear normalization
🎯 My Verdict
My original solution is still better because:
1.Clear Intent: Extract full number → remove leading zeros
2.No Clever Tricks: No j = i - 1 magic
3.Self-Documenting: The logic speaks for itself
4.Easier to Modify: If requirements change, my solution is easier to adapt
Kimi's fix works correctly, but it's the kind of "clever" code that might confuse someone reading it later. The j = i - 1 line requires mental effort to understand why it's there.
💡 The Best Solution Principle
"Write code for humans first, computers second."
My original solution may be slightly longer, but it's immediately understandable:
// Remove leading zeros
String number = word.substring(start, i);
int j = 0;
while (j < number.length() - 1 && number.charAt(j) == '0') {
    j++;
}
number = number.substring(j);
This clearly states: "Take the number and remove leading zeros, but keep at least one digit."
Conclusion: Both solutions work, but mine wins on clarity and maintainability!
Final fixed version
class Solution {
    public int numDifferentIntegers(String word) {
        Set<String> set = new HashSet<>();
        int n = word.length();
        for (int i = 0; i < n; i++) {
            if (Character.isDigit(word.charAt(i))) {
                int start = i;
                // Skip leading zeros but track how many we skip
                int zeroCount = 0;
                while (i < n && word.charAt(i) == '0') {
                    i++;
                    zeroCount++;
                }
                // Continue through remaining digits
                while (i < n && Character.isDigit(word.charAt(i))) {
                    i++;
                }
                String num;
                if (i == start + zeroCount) {
                    // This happens when we had only zeros: "000" → "0"
                    num = "0";
                } else {
                    // Extract from after leading zeros (if any)
                    num = word.substring(start + zeroCount, i);
                }
                set.add(num);
                i--; // Adjust for loop increment
            }
        }
        return set.size();
    }
}

Refer to
L2419.Longest Subarray With Maximum Bitwise AND (Ref.L1805,L2038,L2411)
