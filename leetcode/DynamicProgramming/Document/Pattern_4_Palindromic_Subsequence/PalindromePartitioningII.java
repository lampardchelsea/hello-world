https://leetcode.com/problems/palindrome-partitioning-ii/

Given a string s, partition s such that every 

substring of the partition is a palindrome.

Return the minimum cuts needed for a palindrome partitioning of s.

Example 1:
```
Input: s = "aab"
Output: 1
Explanation: The palindrome partitioning ["aa","b"] could be produced using 1 cut.
```

Example 2:
```
Input: s = "a"
Output: 0
```

Example 3:
```
Input: s = "ab"
Output: 1
```

Constraints:
- 1 <= s.length <= 2000
- s consists of lowercase English letters only.
---
Attempt 1: 2023-01-11

Solution 1: Native DFS (30 min, TLE)

Style 1: No 'startIndex' in recursion call and truncate on original input String (more intuitive and readable)
```
class Solution { 
    public int minCut(String s) { 
        return helper(s); 
    } 

    private int helper(String s) { 
        // Base case: No cut needed 
        // If remain substring s is empty means we go over whole string, no remain 
        // If remain substring s is already a palindrome 
        if(s.length() == 0 || isPalindrome(s, 0, s.length() - 1)) { 
            return 0; 
        } 
        // If remain substring not a palindrome, continue attempt to cut out a  
        // palindrome first 
        // Initial as maximum cut counts 
        int minCut = s.length() - 1; 
        for(int i = 1; i <= s.length(); i++) { 
            String next = s.substring(i); 
            // If we able to cut out a palindrome(index 0 to i - 1) based on remain  
            // substring s, we take it as one cut and recursively process the remaining 
            // (index i to the end) 
            if(isPalindrome(s, 0, i - 1)) { 
                minCut = Math.min(minCut, 1 + helper(next)); 
            } 
        } 
        return minCut; 
    } 

    private boolean isPalindrome(String s, int start, int end) {  
        while(start < end) {  
            if(s.charAt(start) != s.charAt(end)) {  
                return false;  
            }  
            start++;  
            end--;  
        }  
        return true;  
    } 
}
```

Style 2: Have 'startIndex' and no truncate on original input String in recursion call
```
class Solution { 
    public int minCut(String s) { 
        return helper(s, 0); 
    }

    private int helper(String s, int startIndex) { 
        // Base case: No cut needed 
        // If current substring s is empty means we go over whole string, no remain 
        // If current substring s is already a palindrome 
        if(s.length() == 0 || isPalindrome(s, startIndex, s.length() - 1)) { 
            return 0; 
        } 
        // If current substring not a palindrome, continue attempt to cut out a  
        // palindrome first 
        // Initial as maximum cut counts 
        int minCut = s.length() - 1; 
        for(int i = startIndex; i < s.length(); i++) { 
            // If we able to cut out a palindrome(index startIndex to i) based on  
            // s, we take it as one cut and recursively process the remaining 
            // (index i + 1 to the end) 
            if(isPalindrome(s, startIndex, i)) { 
                minCut = Math.min(minCut, 1 + helper(s, i + 1)); 
            } 
        } 
        return minCut; 
    }

    private boolean isPalindrome(String s, int start, int end) {  
        while(start < end) {  
            if(s.charAt(start) != s.charAt(end)) {  
                return false;  
            }  
            start++;  
            end--;  
        }  
        return true;  
    } 
}
```

Solution 2:  Top Down DP (10 min)
```
class Solution { 
    public int minCut(String s) { 
        Integer[] memo = new Integer[s.length()]; 
        return helper(s, 0, memo); 
    }

    private int helper(String s, int startIndex, Integer[] memo) { 
        // Base case: No cut needed 
        // If current substring s is empty means we go over whole string, no remain 
        // If current substring s is already a palindrome 
        if(s.length() == 0 || isPalindrome(s, startIndex, s.length() - 1)) { 
            return 0; 
        } 
        if(memo[startIndex] != null) { 
            return memo[startIndex]; 
        } 
        // If current substring not a palindrome, continue attempt to cut out a  
        // palindrome first 
        // Initial as maximum cut counts 
        int minCut = s.length() - 1; 
        for(int i = startIndex; i < s.length(); i++) { 
            // If we able to cut out a palindrome(index startIndex to i) based on  
            // s, we take it as one cut and recursively process the remaining 
            // (index i + 1 to the end) 
            if(isPalindrome(s, startIndex, i)) { 
                minCut = Math.min(minCut, 1 + helper(s, i + 1, memo)); 
            } 
        } 
        memo[startIndex] = minCut; 
        return minCut; 
    }

    private boolean isPalindrome(String s, int start, int end) {  
        while(start < end) {  
            if(s.charAt(start) != s.charAt(end)) {  
                return false;  
            }  
            start++;  
            end--;  
        }  
        return true;  
    } 
}
```

Refer to
https://leetcode.com/problems/palindrome-partitioning-ii/solutions/590653/from-brute-force-to-top-down-dp

Brute Force

We might try all the substring combinations of the given string. To achieve this, we might start processing from the beginning of the string and keep adding one character at a time. If we get a palindrome, we take it as one cut and recursively process the remaining.
```
class Solution { 
    public int minCut(String s) { 
        return minCutFrom(s, 0, s.length() - 1); 
    } 
     
    private int minCutFrom(String s, int start, int end) { 
        if (start == end || isPalindrome(s, start, end)) { 
            return 0; 
        } 
        int minCut = s.length() - 1; 
        for (int i = start; i <= end; i++) { 
            if (isPalindrome(s, start, i)) { 
                minCut = Math.min(minCut, 1 + minCutFrom(s, i + 1, end)); 
            } 
        } 
        return minCut; 
    } 
     
    private boolean isPalindrome(String s, int x, int y) { 
        while (x < y) { 
            if (s.charAt(x++) != s.charAt(y--)) 
                return false; 
        } 
        return true; 
    } 
}
```


Top-down DP

We might memoize both functions minCut() and isPalindrome().Two indexes are changing in both functions; therefore, we might build a two-dimensional array for each.
```
class Solution { 
    private Integer dp[][]; 
    private Boolean dpIsPalindrome[][]; 
    public int minCut(String s) { 
        dp = new Integer[s.length()][s.length()]; 
        dpIsPalindrome = new Boolean[s.length()][s.length()]; 
        return minCutFrom(s, 0, s.length() - 1); 
    } 
     
    private int minCutFrom(String s, int start, int end) { 
        if (start == end || isPalindrome(s, start, end)) { 
            return 0; 
        } 
         
        if (dp[start][end] != null) 
            return dp[start][end]; 
         
        int minCut = s.length() - 1; 
        for (int i = start; i <= end; i++) { 
            if (isPalindrome(s, start, i)) { 
                minCut = Math.min(minCut, 1 + minCutFrom(s, i + 1, end)); 
            } 
        } 
        return dp[start][end] = minCut; 
    } 
     
    private boolean isPalindrome(String s, int start, int end) { 
        if (start >= end) 
            return true; 
         
        if (dpIsPalindrome[start][end] != null) 
            return dpIsPalindrome[start][end]; 
         
        return dpIsPalindrome[start][end] = (s.charAt(start) == s.charAt(end)) 
            && isPalindrome(s, start + 1, end - 1); 
    } 
}
```

---
Solution 3: Bottom Up DP (60 min)
```
class Solution { 
    public int minCut(String s) { 
        int len = s.length(); 
        // cut[n] means for s[0:n] how many cuts needed (cut[0] actually not used) 
        int[] cut = new int[len + 1]; 
        // no more than i-1 cut needed for string s[:i] 
        // initialize with max cut value needed for that position 
        for(int i = 0; i <= len; i++) { 
            cut[i] = i - 1; 
        } 
        // i is the center of the palindrome 
        // j is the radias  
        for(int i = 0; i < len; i++) { 
            // odd length cases 
            // j as radius, i-j>=0 and i+j<n, and the string at position i-j and i+j  
            // should equal to be a palindrome 
            // for string from index 0 to i+j (included), the cut value is cut[i+j+1]  
            // (s[0:i+j+1]) is equal to the cut value of string s[i-j+1] + 1 
            for(int j = 0; i - j >= 0 && i + j < len && s.charAt(i - j) == s.charAt(i + j); j++) { 
                cut[i + j + 1] = Math.min(cut[i + j + 1], 1 + cut[i - j]); 
            } 
            // even length cases 
            // radius is j-1 at left side so i>=j-1 
            // i belongs to the left part of palindrome 
            // i+1+j-1 = i+j < n 
            for(int j = 1; i - j + 1 >= 0 && i + j < len && s.charAt(i - j + 1) == s.charAt(i + j); j++) { 
                cut[i + j + 1] = Math.min(cut[i + j + 1], 1 + cut[i - j + 1]); 
            } 
        } 
        return cut[len]; 
    } 
}
```

Refer to
https://leetcode.com/problems/palindrome-partitioning-ii/solutions/42198/my-solution-does-not-need-a-table-for-palindrome-is-it-right-it-uses-only-o-n-space/comments/40522
The definition of 'cut' array is the minimum number of cuts of a sub string. More specifically, cut[n + 1] stores the cut number of string s[0, n].
Here is the basic idea of the solution:
1. Initialize the 'cut' array: For a string with n characters s[0, n], it needs at most n-1 cut. Therefore, the 'cut' array is initialized as cut[i] = i-1
2. Use two variables in two loops to represent a palindrome: The external loop variable 'i' represents the center of the palindrome. The internal loop variable 'j' represents the 'radius' of the palindrome. Apparently, j <= i is a must. This palindrome can then be represented as s[i-j, i+j]. If this string is indeed a palindrome, then one possible value of cut[i+j] is cut[i-j] + 1, where cut[i-j] corresponds to s[0, i-j] and 1 correspond to the palindrome s[i-j, i+j];
When the loops finish, you'll get the answer at cut[s.length]

https://leetcode.com/problems/palindrome-partitioning-ii/solutions/42198/my-solution-does-not-need-a-table-for-palindrome-is-it-right-it-uses-only-o-n-space/comments/741404
```
def minCut(self, s: str) -> int: 
        """turns out a DP problem""" 
        n = len(s) 
        cut = [0]*(n+1) # cut[n] means for s[0:n] how many cuts needed (cut[0] actually not used) 
        # no more than i-1 cut needed for string s[:i] 
        for i in range(n+1): 
            cut[i] = i-1 # initialize with max cut value needed for that position 
        # i is the center of the palindrome 
        # j is the radias  
        for i in range(n): 
            # odd length cases 
            # j as radius, i-j>=0 and i+j<n, and the string at position i-j and i+j should equal to be a palindrome 
            j = 0 
            # for string from index 0 to i+j (included), the cut value is cut[i+j+1] (s[0:i+j+1]) is equal to the cut value of string s[i-j+1] + 1 
            while j<=i and i+j<n and s[i-j]==s[i+j]: 
                cut[i+j+1] = min(cut[i+j+1], 1+cut[i-j]) 
                j+=1 
             
            # even length 
            # radius is j-1 at left side so i>=j-1 
            # i belongs to the left part of palindrome 
            # i+1+j-1 = i+j < n 
            j = 1 
            while j<=i+1 and i+j <n and s[i-j+1]==s[i+j]: 
                cut[i+j+1] = min(cut[i+j+1], 1+cut[i-j+1]) 
                j+=1 
        return cut[n]
```
