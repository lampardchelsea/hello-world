https://leetcode.com/problems/special-binary-string/description/
Special binary strings are binary strings with the following two properties:
- The number of 0's is equal to the number of 1's.
- Every prefix of the binary string has at least as many 1's as 0's.
You are given a special binary string s.
A move consists of choosing two consecutive, non-empty, special substrings of s, and swapping them. Two strings are consecutive if the last character of the first string is exactly one index before the first character of the second string.
Return the lexicographically largest resulting string possible after applying the mentioned operations on the string.
 
Example 1:
Input: s = "11011000"
Output: "11100100"
Explanation: The strings "10" [occuring at s[1]] and "1100" [at s[3]] are swapped.
This is the lexicographically largest string possible after some number of swaps.

Example 2:
Input: s = "10"
Output: "10"
 
Constraints:
- 1 <= s.length <= 50
- s[i] is either '0' or '1'.
- s is a special binary string.
--------------------------------------------------------------------------------
Attempt 1: 2026-01-07
Solution 1: DFS + Greedy + Math (180 min)
class Solution {
    public String makeLargestSpecial(String s) {
        return helper(s, 0, s.length() - 1);
    }

    private String helper(String s, int i, int j) {
        // Base case: empty string or single "10"
        if(i > j) {
            return "";
        }
        // Only possible special string of length 2
        if(i == j - 1) {
            return "10";
        }
        List<String> specialStrings = new ArrayList<>();
        int start = i;
        int count = 0;
        // Find all special substrings within [i, j]
        for(int k = i; k <= j; k++) {
            if(s.charAt(k) == '1') {
                count++;
            } else {
                count--;
            }
            // When count becomes 0, we have a complete special substring
            if(count == 0) {
                // Divide and Conquer
                // Current special substring is from start to k
                // Strip the outer '1' and '0' and process the inside
                String inside = helper(s, start + 1, k - 1);
                String processed = "1" + inside + "0";
                specialStrings.add(processed);
                // Update start for next potential substring
                start = k + 1;
            }
        }
        // Sort in descending order for lexicographically largest
        Collections.sort(specialStrings, Collections.reverseOrder());
        // Combine all processed substrings
        StringBuilder sb = new StringBuilder();
        for(String str : specialStrings) {
            sb.append(str);
        }
        return sb.toString();
    }
}

Time Complexity: O(n*logn)
Space Complexity: O(n)

Refer to QWEN
Looking at LeetCode 761. Special Binary String, this is a challenging problem that requires understanding the recursive structure of special binary strings and applying a greedy sorting strategy.
Problem Understanding
A special binary string has these properties:
- Equal number of 0s and 1s
- Every prefix has at least as many 1s as 0s
- Can be thought of as valid parentheses where 1 = '(' and 0 = ')'
The goal is to rearrange the string to make it lexicographically largest by swapping special substrings.
Key Insights
1.Special substrings are like valid parentheses groups - they cannot be broken down further
2.Recursive structure: Any special string can be decomposed into special substrings
3.Greedy approach: To maximize lexicographically, we should sort special substrings in descending order
4.Processing pattern:
- Strip the outermost 1 and 0 from each special substring
- Recursively make each inner part largest
- Sort the results in descending order
- Reconstruct with outer 1 and 0
Solution Approach
import java.util.*;

class Solution {
    public String makeLargestSpecial(String s) {
        if (s.length() <= 2) {
            return s; // Base case: "" or "10"
        }
        
        List<String> specialSubstrings = new ArrayList<>();
        int count = 0;
        int start = 0;
        
        // Split the string into special substrings
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                count++;
            } else {
                count--;
            }
            
            // When count becomes 0, we have a complete special substring
            if (count == 0) {
                // Extract the special substring from start to i (inclusive)
                String substring = s.substring(start, i + 1);
                // Strip the outer '1' and '0', process the inside recursively
                String processedInside = makeLargestSpecial(substring.substring(1, substring.length() - 1));
                // Reconstruct with outer '1' and '0'
                specialSubstrings.add("1" + processedInside + "0");
                // Update start for next substring
                start = i + 1;
            }
        }
        
        // Sort in descending order to get lexicographically largest arrangement
        Collections.sort(specialSubstrings, Collections.reverseOrder());
        
        // Combine all special substrings
        StringBuilder result = new StringBuilder();
        for (String substr : specialSubstrings) {
            result.append(substr);
        }
        
        return result.toString();
    }
}

How It Works
Step-by-step Example
For input "11011000":
1.First decomposition:
- At i=5: count becomes 0, substring = "110110"
- At i=7: count becomes 0, substring = "00" (but wait, this isn't right)
Actually, let's trace correctly:
- s = "11011000"
- i=0: count=1
- i=1: count=2
- i=2: count=1
- i=3: count=2
- i=4: count=3
- i=5: count=2
- i=6: count=1
- i=7: count=0 → entire string is one special substring
1.Strip outer: "11011000" → inner = "101100"
2.Recurse on inner "101100":
- i=1: count=0 → first substring "10"
- i=5: count=0 → second substring "1100"
- Process "10" → base case → "10"
- Process "1100" → strip to "10" → base case → "10" → reconstruct "1100"
- Sort ["10", "1100"] in descending order → ["1100", "10"]
- Combine → "110010"
3.Reconstruct outer: "1" + "110010" + "0" = "11100100"
Why This Works
1.Valid decomposition: When count hits 0, we have a minimal special substring that cannot be split further
2.Recursive optimization: Each special substring's interior is independently optimizable
3.Greedy sorting: Lexicographically largest arrangement comes from sorting special substrings in descending order
4.Preservation of validity: Adding outer 1 and 0 maintains the special property
Time & Space Complexity
- Time: O(n² log n) in worst case
- Each level of recursion processes O(n) characters
- Sorting at each level takes O(k log k) where k is number of substrings
- Depth can be O(n) in worst case (nested structure)
- Space: O(n²) for recursion stack and substring storage
Key Edge Cases Handled
- Empty string → returns empty string
- Single special substring "10" → returns "10"
- Multiple independent special substrings → sorts them optimally
- Nested special substrings → recursively optimizes inner parts
This solution elegantly combines string parsing, recursion, and greedy sorting to solve the problem optimally.

Q1: Can you elaborate below statement ? i don't understand what's the meaning
Every prefix of the binary string has at least as many 1's as 0's.
Great question! Let me break down this statement with clear examples.
What is a "prefix"?
A prefix of a string is any substring that starts from the beginning of the string.
For example, if we have string "1100", its prefixes are:
- "1" (first 1 character)
- "11" (first 2 characters)
- "110" (first 3 characters)
- "1100" (first 4 characters, which is the whole string)
The Rule: "At least as many 1's as 0's"
This means that for every single prefix of the string, when you count the number of 1's and 0's in that prefix, the count of 1's should be greater than or equal to the count of 0's.
Valid Example: "1100"
Let's check each prefix:
- Prefix "1": 1's = 1, 0's = 0 → 1 ≥ 0 ✓
- Prefix "11": 1's = 2, 0's = 0 → 2 ≥ 0 ✓
- Prefix "110": 1's = 2, 0's = 1 → 2 ≥ 1 ✓
- Prefix "1100": 1's = 2, 0's = 2 → 2 ≥ 2 ✓
Result: "1100" is valid! ✅
Invalid Example: "1001"
Let's check each prefix:
- Prefix "1": 1's = 1, 0's = 0 → 1 ≥ 0 ✓
- Prefix "10": 1's = 1, 0's = 1 → 1 ≥ 1 ✓
- Prefix "100": 1's = 1, 0's = 2 → 1 ≥ 2 ❌ VIOLATION!
- Prefix "1001": 1's = 2, 0's = 2 → 2 ≥ 2 ✓
Result: "1001" is invalid because the prefix "100" has more 0's than 1's! ❌
Another Invalid Example: "0110"
- Prefix "0": 1's = 0, 0's = 1 → 0 ≥ 1 ❌ VIOLATION immediately!
Visual Understanding: Think of it as a Balance
Imagine you're walking along the string:
- Every time you see a '1', you go up (+1)
- Every time you see a '0', you go down (-1)
The rule means you should never go below ground level (0) at any point!
For "1100": Up(1) → Up(2) → Down(1) → Down(0) → Never below 0 ✓
For "1001": Up(1) → Down(0) → Down(-1) → Up(0) → Went below 0 at third step ❌
Connection to Parentheses
This is exactly like valid parentheses matching:
- '1' = opening parenthesis '('
- '0' = closing parenthesis ')'
You can never close more parentheses than you've opened! That's why every prefix must have at least as many 1's (opens) as 0's (closes).
This rule ensures the binary string represents a valid structure that can be properly nested and balanced.

Q2: If i don't want to pass the variable as substring of original string, instead i want to keep the original string as one parameter, plus parameters such as indexes, and i would like a formal native DFS pattern such as:
public String makeLargestSpecial(String s) {
    return dfs(s, other parameters like index)
}

private String dfs(String s, int other parameters like index) {
    return .....
}
You can definitely implement the solution using a native DFS pattern with indices instead of creating substrings. Here's how to do it:
class Solution {
    public String makeLargestSpecial(String s) {
        return dfs(s, 0, s.length() - 1);
    }
    
    private String dfs(String s, int start, int end) {
        // Base case: empty string or single "10"
        if (start > end) {
            return "";
        }
        if (start == end - 1) {
            return "10"; // Only possible special string of length 2
        }
        
        List<String> specialSubstrings = new ArrayList<>();
        int count = 0;
        int substringStart = start;
        
        // Find all special substrings within [start, end]
        for (int i = start; i <= end; i++) {
            if (s.charAt(i) == '1') {
                count++;
            } else {
                count--;
            }
            
            // When count becomes 0, we have a complete special substring
            if (count == 0) {
                // Current special substring is from substringStart to i
                // Strip the outer '1' and '0' and process the inside
                String inner = dfs(s, substringStart + 1, i - 1);
                String processed = "1" + inner + "0";
                specialSubstrings.add(processed);
                // Update start for next potential substring
                substringStart = i + 1;
            }
        }
        
        // Sort in descending order for lexicographically largest
        Collections.sort(specialSubstrings, Collections.reverseOrder());
        
        // Combine all processed substrings
        StringBuilder result = new StringBuilder();
        for (String substr : specialSubstrings) {
            result.append(substr);
        }
        
        return result.toString();
    }
}
How This Works:
Parameters:
- s: the original string (never modified)
- start: starting index of current segment to process
- end: ending index of current segment to process
Key Differences from Substring Approach:
1.No substring creation: We work directly with indices [start, end]
2.Native DFS pattern: Each recursive call processes a specific range of the original string
3.Memory efficient: Avoids creating multiple string objects during recursion
Logic Flow:
1.Base cases: Handle empty segments and minimal "10" case
2.Decomposition: Scan from start to end, using count to find complete special substrings
3.Recursive processing: For each special substring found at [substringStart, i], recursively process the inner part [substringStart+1, i-1]
4.Reconstruction: Wrap the processed inner result with "1" and "0"
5.Sorting and combining: Sort all special substrings and concatenate
This approach maintains the same algorithmic logic as the substring version but uses the formal DFS pattern you requested, working entirely with indices on the original string. The time and space complexity remain the same, but it's more memory-efficient as it avoids intermediate string allocations.

Q3: I see below two lines, is this kind of backtracking ?
String inner = dfs(s, substringStart + 1, i - 1);
String processed = "1" + inner + "0";
No, this is not backtracking. This is divide-and-conquer with recursion.
Let me explain the key differences:
What Backtracking Looks Like
Backtracking involves:
1.Making a choice (modifying state)
2.Recursing with that choice
3.Undoing the choice (restoring state) to try other options
Example of backtracking:
// Try adding a character
path.add(char);
backtrack(...);
path.remove(path.size()-1); // UNDO the choice - this is backtracking!
What Your Code Does
Your code is pure divide-and-conquer:
1.Divide: Split the problem into independent subproblems
- Original problem: [substringStart, i]
- Subproblem: [substringStart+1, i-1] (the inner part)
2.Conquer: Solve each subproblem recursively
- String inner = dfs(s, substringStart + 1, i - 1);
3.Combine: Use the subproblem solutions to build the current solution
- String processed = "1" + inner + "0";
Why No Backtracking Is Needed
- No state modification: You're not modifying any shared state or data structure
- Independent subproblems: Each special substring is processed independently
- No "undo" required: You're not making temporary choices that need to be reversed
- Pure function: Each recursive call returns a result that's used immediately
The Pattern You're Using
This follows the classic recursive decomposition pattern:
solve(problem):
    if base_case: return base_result
    subproblems = decompose(problem)
    subresults = []
    for subproblem in subproblems:
        subresult = solve(subproblem)  // Recursive call
        subresults.append(subresult)
    return combine(subresults)
In your case:
- Problem: Make largest special string for range [start, end]
- Decomposition: Split into special substrings [substringStart, i]
- Subproblem: Make largest special string for inner range [substringStart+1, i-1]
- Combine: Wrap with "1"/"0" and sort all results
So while both backtracking and your approach use recursion, backtracking is about exploring and undoing choices, while your approach is about breaking down and rebuilding solutions.

Refer to Deepseek
Here's the Java solution for LeetCode 761 "Special Binary String" with a recursive approach:
class Solution {
    public String makeLargestSpecial(String s) {
        // Step 1: Base case - empty string
        if (s.isEmpty()) return "";
        
        // Step 2: Split into special substrings
        List<String> specialSubstrings = new ArrayList<>();
        int count = 0;
        int start = 0;
        
        for (int i = 0; i < s.length(); i++) {
            count += (s.charAt(i) == '1') ? 1 : -1;
            
            // When count becomes 0, we found a special substring
            if (count == 0) {
                // Extract the substring (excluding outer 1 and 0)
                String inner = s.substring(start + 1, i);
                // Recursively process the inner part
                String processedInner = makeLargestSpecial(inner);
                // Add back the outer 1 and 0
                specialSubstrings.add("1" + processedInner + "0");
                start = i + 1; // Move to next substring
            }
        }
        
        // Step 3: Sort in descending order and concatenate
        Collections.sort(specialSubstrings, Collections.reverseOrder());
        
        StringBuilder result = new StringBuilder();
        for (String sub : specialSubstrings) {
            result.append(sub);
        }
        
        return result.toString();
    }
}
🔍 Understanding the Problem
Special Binary String:
1.Contains equal number of '1's and '0's
2.Every prefix has at least as many '1's as '0's
Operations allowed:
- Swap two contiguous special binary substrings
- Can do this any number of times
- Goal: Get lexicographically largest string
🎯 How the Solution Works
Step-by-Step Example:
Input: "11011000"

1. First level parsing:

   - Whole string is special (count balance)
   - Remove outer '1' and '0': get "101100"
   
2. Process "101100":

   - Split into special substrings: "10" and "1100"
   - For "10": inner = "" → returns "10"
   - For "1100": inner = "10" → process "10" → returns "10"
                   → becomes "1" + "10" + "0" = "1100"
   
3. Sort ["10", "1100"] → ["1100", "10"]
4. Concatenate → "110010"

   
5. Add back outer: "1" + "110010" + "0" = "11100100"
Visualization:
Original: 1(101100)0
          ↓
Process inner: "101100"
Split: "10" and "1100"
        ↓
"10" → "1()0" → "10"
"1100" → "1(10)0" → "1100"

Sort: "1100" > "10" → "110010"

Final: "1(110010)0" = "11100100"
💡 Key Insights
1.Special Strings always start with '1' and end with '0'
2.Balancing point (where count of 1's and 0's equal) defines substrings
3.Recursive structure: Inner part of a special string is also special
4.Swapping allowed: We can reorder special substrings at any level
📊 Complexity Analysis
OperationTime ComplexitySpace ComplexitySplittingO(n)O(h) recursionSortingO(k log k) per levelO(n) for storageTotalO(n²) worst-caseO(n)
🔧 Testing with Examples
// Test cases
public void test() {
    Solution sol = new Solution();
    
    // Example 1
    System.out.println(sol.makeLargestSpecial("11011000")); 
    // Output: "11100100"
    
    // Example 2
    System.out.println(sol.makeLargestSpecial("1010")); 
    // Output: "1010"
    
    // Example 3
    System.out.println(sol.makeLargestSpecial("111000")); 
    // Output: "111000"
    
    // Example 4
    System.out.println(sol.makeLargestSpecial("1100")); 
    // Output: "1100"
}
🎯 Alternative Approach: Iterative with Stack
class Solution {
    public String makeLargestSpecial(String s) {
        Stack<String> stack = new Stack<>();
        int count = 0;
        StringBuilder current = new StringBuilder();
        
        for (char c : s.toCharArray()) {
            current.append(c);
            count += (c == '1') ? 1 : -1;
            
            if (count == 0) {
                // Found a special substring
                String inner = current.substring(1, current.length() - 1);
                String processed = makeLargestSpecial(inner);
                stack.push("1" + processed + "0");
                current = new StringBuilder();
            }
        }
        
        // Sort and concatenate
        Collections.sort(stack, Collections.reverseOrder());
        return String.join("", stack);
    }
}
💡 Key Points to Remember
1.Always start with '1' and end with '0' for special strings
2.Count balance (increment for '1', decrement for '0') helps find boundaries
3.Recursive processing of inner strings is crucial
4.Sorting in descending order gives lexicographically largest result
5.Reconstruction with outer '1' and '0' maintains special property
🎯 Why This Algorithm Works
The algorithm effectively:
1.Decomposes the string into its smallest special components
2.Recursively sorts each component's inner parts
3.Recombines in descending order to maximize lexicographic order
4.Preserves the special binary string property at each step
This solution runs in O(n²) time in worst case but handles the problem constraints efficiently.

How to avoid substring ?
Here's the DFS pattern solution with indices to avoid substring creation:
class Solution {
    public String makeLargestSpecial(String s) {
        return dfs(s, 0, s.length());
    }
    
    private String dfs(String s, int left, int right) {
        // Base case: empty substring
        if (left >= right) return "";
        
        List<String> specialSubstrings = new ArrayList<>();
        int count = 0;
        int start = left;
        
        for (int i = left; i < right; i++) {
            char c = s.charAt(i);
            count += (c == '1') ? 1 : -1;
            
            // Found a special substring: s[start...i]
            if (count == 0) {
                // Process the inner part (without outer '1' and '0')
                String inner = dfs(s, start + 1, i);
                // Reconstruct with outer '1' and '0'
                specialSubstrings.add("1" + inner + "0");
                // Move to next potential special substring
                start = i + 1;
            }
        }
        
        // Sort in descending order and concatenate
        Collections.sort(specialSubstrings, Collections.reverseOrder());
        
        // Build result without creating intermediate strings unnecessarily
        if (specialSubstrings.size() == 1) {
            return specialSubstrings.get(0);
        }
        
        StringBuilder result = new StringBuilder();
        for (String sub : specialSubstrings) {
            result.append(sub);
        }
        return result.toString();
    }
}
🎯 Alternative: Pass Index as Parameter Array (Mutable)
class Solution {
    public String makeLargestSpecial(String s) {
        int[] index = {0};
        return dfs(s, index);
    }
    
    private String dfs(String s, int[] idx) {
        List<String> substrings = new ArrayList<>();
        
        while (idx[0] < s.length() && s.charAt(idx[0]) != '0') {
            // Move past the starting '1'
            idx[0]++;
            // Recursively process inner part
            String inner = dfs(s, idx);
            // Move past the ending '0'
            idx[0]++;
            substrings.add("1" + inner + "0");
        }
        
        // Sort in descending order
        Collections.sort(substrings, Collections.reverseOrder());
        
        // Concatenate
        StringBuilder sb = new StringBuilder();
        for (String sub : substrings) {
            sb.append(sub);
        }
        return sb.toString();
    }
}
🎯 Alternative: Classic DFS with Balanced Count Tracking
class Solution {
    public String makeLargestSpecial(String s) {
        return dfs(s.toCharArray(), 0, s.length() - 1);
    }
    
    private String dfs(char[] chars, int left, int right) {
        if (left > right) return "";
        
        List<String> subs = new ArrayList<>();
        int balance = 0;
        int start = left;
        
        for (int i = left; i <= right; i++) {
            balance += (chars[i] == '1') ? 1 : -1;
            
            if (balance == 0) {
                // Found a balanced substring
                String inner = dfs(chars, start + 1, i - 1);
                subs.add("1" + inner + "0");
                start = i + 1;
            }
        }
        
        // Sort descending
        subs.sort((a, b) -> b.compareTo(a));
        
        // Efficient concatenation
        if (subs.isEmpty()) return "";
        if (subs.size() == 1) return subs.get(0);
        
        StringBuilder sb = new StringBuilder();
        for (String sub : subs) sb.append(sub);
        return sb.toString();
    }
}
🔍 Why Pass Original String + Indices?
Memory Efficiency:
// BAD: Creates many substrings (O(n²) space in worst case)
String left = s.substring(0, mid);  // NEW string
String right = s.substring(mid);    // NEW string

// GOOD: Pass indices (O(n) space)
process(s, 0, mid);    // NO new string
process(s, mid, end);  // NO new string
Performance Comparison:
Input: "1" repeated 1000 times + "0" repeated 1000 times

Substring approach:
- Creates ~500 substrings at first level
- Each creates more substrings recursively
- Total: O(n²) string copies

Index approach:
- Always works with original char array
- No string copies until final result
- Total: O(n) memory usage
🎯 Optimized DFS with StringBuilder
class Solution {
    public String makeLargestSpecial(String s) {
        char[] chars = s.toCharArray();
        return dfs(chars, 0, chars.length - 1, new StringBuilder()).toString();
    }
    
    private StringBuilder dfs(char[] chars, int left, int right, StringBuilder sb) {
        if (left > right) return sb;
        
        List<StringBuilder> subs = new ArrayList<>();
        int balance = 0;
        int start = left;
        
        for (int i = left; i <= right; i++) {
            balance += (chars[i] == '1') ? 1 : -1;
            
            if (balance == 0) {
                StringBuilder inner = new StringBuilder();
                dfs(chars, start + 1, i - 1, inner);
                StringBuilder full = new StringBuilder();
                full.append('1').append(inner).append('0');
                subs.add(full);
                start = i + 1;
            }
        }
        
        // Sort by string value descending
        subs.sort((a, b) -> b.toString().compareTo(a.toString()));
        
        for (StringBuilder sub : subs) {
            sb.append(sub);
        }
        return sb;
    }
}
📊 Complexity Analysis
ApproachTime ComplexitySpace ComplexityString CopiesSubstring DFSO(n²) worst-caseO(n²)ManyIndex DFSO(n log n) averageO(n)FewStringBuilderO(n log n)O(n)Minimal
💡 Key Design Decisions
1.Parameter Choice: (String s, int left, int right) vs (char[] chars, int left, int right)
- String: Simpler, but still uses charAt()
- char[]: Faster array access, but requires conversion
2.Base Case: left >= right vs left > right
- Use >= for exclusive right, > for inclusive right
- Consistent indexing is crucial
3.Result Building:
- Collect substrings → sort → concatenate
- Alternative: Build during DFS if order is known
🎯 Why This Pattern is Better
1.Memory Efficient: No substring copying
2.Clean Recursion: Classic DFS pattern with indices
3.Easier Debugging: Can add print statements with indices
4.Flexible: Can be adapted to other tree/string parsing problems
This index-based DFS pattern is a fundamental technique for:
- Tree construction from strings
- Expression parsing
- Balanced parenthesis problems
- Any recursive string processing
The key insight is that indices define the working window within the original string, avoiding expensive substring operations while maintaining clean recursive structure.



Refer to
L678.Valid Parenthesis String (Ref.L761,L2116)
L2116.Check if a Parentheses String Can Be Valid (Ref.L301,L678,L1963)
