https://leetcode.com/problems/longest-ideal-subsequence/description/
You are given a string s consisting of lowercase letters and an integer k. We call a string t ideal if the following conditions are satisfied:
- t is a subsequence of the string s.
- The absolute difference in the alphabet order of every two adjacent letters in t is less than or equal to k.
Return the length of the longest ideal string.
A subsequence is a string that can be derived from another string by deleting some or no characters without changing the order of the remaining characters.
Note that the alphabet order is not cyclic. For example, the absolute difference in the alphabet order of 'a' and 'z' is 25, not 1.

Example 1:
Input: s = "acfgbd", k = 2
Output: 4
Explanation: The longest ideal string is "acbd". The length of this string is 4, so 4 is returned.Note that "acfgbd" is not ideal because 'c' and 'f' have a difference of 3 in alphabet order.

Example 2:
Input: s = "abcd", k = 3
Output: 4
Explanation: The longest ideal string is "abcd". The length of this string is 4, so 4 is returned.
 
Constraints:
- 1 <= s.length <= 10^5
- 0 <= k <= 25
- s consists of lowercase English letters.
--------------------------------------------------------------------------------
Attempt 1: 2024-09-19
Solution 1: Similar to Longest Increasing Subsequence + dp[i] means the length of the longest ideal substring ending at each character and used with map which keep track of the last index of each character that is part of the longest ideal string so far (1080 min)
class Solution {
    public int longestIdealString(String s, int k) {
        int lengthOfString = s.length(); // The length of the string s.
        int longestLength = 1; // Initialize the answer to 1 character as the minimum length of ideal string.
        int[] dynamicProgramming = new int[lengthOfString]; // Array to store the length of the longest ideal substring ending at each character.
        Arrays.fill(dynamicProgramming, 1); // At minimum, each character itself is an ideal string.

        // HashMap to keep track of the last index of each character that is part of the longest ideal string so far.
        Map<Character, Integer> lastSeenCharacterMap = new HashMap<>(26);

        // Place the first character in the map to start the process.
        lastSeenCharacterMap.put(s.charAt(0), 0);

        // Iterate through each character in the string starting from the second character.
        for (int i = 1; i < lengthOfString; ++i) {
            char currentChar = s.charAt(i); // The current character for which we are finding the ideal string.
            // The absolute difference in the alphabet order of every two adjacent letters in t is less than or equal to k.
            char start = (char)Math.max('a', currentChar - k);
            char end = (char)Math.min('z', currentChar + k);
            // Check closeness of every character in the alphabet to the current character within the limit k.
            for (char prevChar = start; prevChar <= end; ++prevChar) {
                // If the previous character has been seen and is part of an ideal string,
                // we update the DP table by taking the max of the current dp value or
                // the dp value of the previous character's last index plus one.
                if (lastSeenCharacterMap.containsKey(prevChar)) {
                    dynamicProgramming[i] = Math.max(dynamicProgramming[i], dynamicProgramming[lastSeenCharacterMap.get(prevChar)] + 1);
                }
            }

            // Update the last seen index of the current character to be the current index.
            lastSeenCharacterMap.put(currentChar, i);
            // Update longestLength to be the maximum of itself and the current length of the longest ideal string ending at i.
            longestLength = Math.max(longestLength, dynamicProgramming[i]);
        }

        // Return the length of the longest ideal string that can be formed from the input string.
        return longestLength;
    }
}

Refer to
https://algo.monster/liteproblems/2370
Problem Description
The LeetCode problem presented asks us to find the length of the longest ideal subsequence from a given string s. An ideal subsequence is one that satisfies two conditions. First, it must be a subsequence of s, which means it can be formed by removing some or no characters from s without changing the order of the remaining characters. Second, for every pair of adjacent letters in this subsequence, the absolute difference in their alphabetical order must be less than or equal to a given integer k.
To explain further, a subsequence is different from a substring since a substring is contiguous and appears in the same order within the original string, whereas a subsequence is not required to be contiguous but must appear in the same relative order.
The problem statement also clarifies that the alphabetical order difference isn't cyclic, meaning that the difference between a and z is considered to be 25, not 1.
Intuition
To solve this problem, we can use dynamic programming, which involves breaking the problem into smaller subproblems and solving each subproblem just once, storing the solution in a table for easy access when solving subsequent subproblems.
The intuition behind the approach is to consider each character in the string s one by one and determine the length of the longest ideal subsequence ending with that character. For each character, we need to find the previous character in the subsequence that can be connected to it (based on the condition about the alphabetical difference) for which the subsequence length is the longest possible (The thought similar to L300.Longest Increasing Subsequence).
We create a dictionary d that keeps track of the last occurrence index for each character that can be a candidate for forming a subsequence. We also maintain an array dp where dp[i] represents the length of the longest ideal subsequence ending at s[i].
For each character s[i] in the string, we iterate over every lowercase letter and check if it can form a sequence with s[i], satisfying the absolute difference condition. If it's a valid character, we update dp[i] to the maximum of its current value or 1 plus the length of the subsequence ending with the valid character found in our dictionary d. By doing this iteratively, we build upon the solutions of the subproblems until we reach the end of the string. Finally, the answer is the maximum value in the dp array, which represents the length of the longest ideal subsequence we can obtain from s.
Solution Approach
The implementation of the solution provided follows the dynamic programming approach to tackle the problem of finding the longest ideal subsequence.
Firstly, we initialize an array dp with all elements as 1, because the minimum length of an ideal subsequence that can end at any character is at least 1 – the character itself. We also create a dictionary d to keep track of the last index where each character occurred while checking the sequence.
The core of the solution code is a nested loop. The outer loop iterates through the string s, and the inner loop iterates through all lowercase ASCII letters to check whether they can form an ideal subsequence with the current character s[i]. Here's a breakdown of each element of the code:
- n = len(s): We determine the length of the given string s.
- dp = [1] * n: Creates a list dp with n elements, all initialized to 1.
- d = {s[0]: 0}: Initializes the dictionary d with the first character of the string s as the key and 0 as its index.
- for i in range(1, n): Outer loop to traverse the string s starting from index 1 since index 0 is used for initialization.
- a = ord(s[i]): Converts the current character s[i] to its ASCII value to facilitate comparison based on alphabetical order.
- Inside the outer loop is an inner loop: for b in ascii_lowercase:
- abs(a - ord(b)) > k: Checks if the absolute difference between the ASCII values of the characters is greater than k, which means they cannot form an ideal sequence.
- if b in d: If character b is within the difference k, and it's in dictionary d, then it's a candidate for creating or extending an ideal subsequence.
- dp[i] = max(dp[i], dp[d[b]] + 1): Here, the dynamic programming comes into play. We update the dp[i] with the maximum of its current value or the length of the ideal subsequence ending with b plus one (to include the current character s[i]).
- d[s[i]] = i: We update or add the current character and its index to dictionary d to keep track of the last position it was seen.
- return max(dp): After filling the dp table, the length of the longest ideal subsequence will be the maximum value in the dp list.
By keeping track of the ideal subsequences ending with different characters and building upon each other, this algorithm efficiently finds the longest one possible.
Example Walkthrough
Let's consider a string s = "abcd" and k = 2, which signifies the maximum allowed absolute difference in the alphabetical order.
To illustrate the solution approach, we'll walk through the example step-by-step:
1.Initialization: We have n = 4 since the length of s is 4. We create a dp list of length n initialized with all 1s because the minimum subsequence length ending at each character is 1 (the character itself). We also have a dictionary, d, which will keep track of the last seen index for each character.
2.Starting Point: With s[0] = 'a', we initialize d with {'a': 0}. Since 'a' is the first character, dp[0] = 1.
3.Iteration with i = 1 for s[i] = 'b':
- We check if 'a' can form an ideal subsequence with 'b'. Since abs(ord('a') - ord('b')) = 1 which is less than or equal to k, 'a' can form an ideal subsequence with 'b'.
- Thus, we update dp[1] with the maximum of dp[1] and dp['a' index] + 1. Since dp[0] = 1, this means dp[1] becomes 2.
- Update d to include 'b': 1.
4.Iteration with i = 2 for s[i] = 'c':
- We check 'a' and 'b' to form an ideal subsequence with 'c'. Both are within k distance.
- Since both 'a' and 'b' can precede 'c', we choose the one that provides the longest subsequence. dp[2] is updated to max(dp[2], dp['b' index] + 1), which is 3.
- Update d to include 'c': 2.
5.Iteration with i = 3 for s[i] = 'd':
- Repeat the above step for 'd', checking 'a', 'b', and 'c'. Again, all are within k distance.
- We again choose the best predecessor, which is 'c' in this case. We update dp[3] to max(dp[3], dp['c' index] + 1), which is 4.
- Update d to include 'd': 3.
6.Finish: We have processed all characters in s. The dp array is [1, 2, 3, 4], and thus the length of the longest ideal subsequence is max(dp) which is 4.
In this example, the longest ideal subsequence we can form from "abcd" with k = 2 is "abcd" itself, with the length of 4. However, for strings with repeating characters or larger alphabetic differences, we would see more variation in the dp updates based on which preceding characters provide the longer ideal subsequence according to the k condition.
Solution Implementation
class Solution {
    public int longestIdealString(String s, int k) {
        int lengthOfString = s.length(); // The length of the string s.
        int longestLength = 1; // Initialize the answer to 1 character as the minimum length of ideal string.
        int[] dynamicProgramming = new int[lengthOfString]; // Array to store the length of the longest ideal substring ending at each character.
        Arrays.fill(dynamicProgramming, 1); // At minimum, each character itself is an ideal string.

        // HashMap to keep track of the last index of each character that is part of the longest ideal string so far.
        Map<Character, Integer> lastSeenCharacterMap = new HashMap<>(26);

        // Place the first character in the map to start the process.
        lastSeenCharacterMap.put(s.charAt(0), 0);

        // Iterate through each character in the string starting from the second character.
        for (int i = 1; i < lengthOfString; ++i) {
            char currentChar = s.charAt(i); // The current character for which we are finding the ideal string.

            // Check closeness of every character in the alphabet to the current character within the limit k.
            for (char prevChar = 'a'; prevChar <= 'z'; ++prevChar) {
                // If the absolute difference in ASCII value is within the limit k, we proceed.
                if (Math.abs(currentChar - prevChar) > k) {
                    continue;
                }
                // If the previous character has been seen and is part of an ideal string,
                // we update the DP table by taking the max of the current dp value or
                // the dp value of the previous character's last index plus one.
                if (lastSeenCharacterMap.containsKey(prevChar)) {
                    dynamicProgramming[i] = Math.max(dynamicProgramming[i], dynamicProgramming[lastSeenCharacterMap.get(prevChar)] + 1);
                }
            }

            // Update the last seen index of the current character to be the current index.
            lastSeenCharacterMap.put(currentChar, i);
            // Update longestLength to be the maximum of itself and the current length of the longest ideal string ending at i.
            longestLength = Math.max(longestLength, dynamicProgramming[i]);
        }

        // Return the length of the longest ideal string that can be formed from the input string.
        return longestLength;
    }
}
If need to print longest ideal subsequence, how to modify above solution ?
Refer to chatGPT

class Solution {
    public String longestIdealString(String s, int k) {
        int lengthOfString = s.length(); // The length of the string s.
        int longestLength = 1; // Initialize the answer to 1 character as the minimum length of ideal string.
        int[] dynamicProgramming = new int[lengthOfString]; // Array to store the length of the longest ideal subsequence ending at each character.
        int[] parent = new int[lengthOfString]; // Array to store the index of the previous character in the subsequence.
        Arrays.fill(dynamicProgramming, 1); // At minimum, each character itself is an ideal string.
        Arrays.fill(parent, -1); // Initialize parent array with -1 (no parent).

        // HashMap to keep track of the last index of each character that is part of the longest ideal string so far.
        Map<Character, Integer> lastSeenCharacterMap = new HashMap<>(26);

        // Place the first character in the map to start the process.
        lastSeenCharacterMap.put(s.charAt(0), 0);

        // Variable to store the index of the character where the longest ideal subsequence ends.
        int endIndex = 0;

        // Iterate through each character in the string starting from the second character.
        for (int i = 1; i < lengthOfString; ++i) {
            char currentChar = s.charAt(i); // The current character for which we are finding the ideal string.

            // Check closeness of every character in the alphabet to the current character within the limit k.
            for (char prevChar = 'a'; prevChar <= 'z'; ++prevChar) {
                // If the absolute difference in ASCII value is within the limit k, we proceed.
                if (Math.abs(currentChar - prevChar) > k) {
                    continue;
                }
                // If the previous character has been seen and is part of an ideal string,
                // we update the DP table by taking the max of the current dp value or
                // the dp value of the previous character's last index plus one.
                if (lastSeenCharacterMap.containsKey(prevChar)) {
                    int prevIndex = lastSeenCharacterMap.get(prevChar);
                    if (dynamicProgramming[i] < dynamicProgramming[prevIndex] + 1) {
                        dynamicProgramming[i] = dynamicProgramming[prevIndex] + 1;
                        parent[i] = prevIndex; // Update the parent to reconstruct the sequence later.
                    }
                }
            }

            // Update the last seen index of the current character to be the current index.
            lastSeenCharacterMap.put(currentChar, i);
            // Update longestLength to be the maximum of itself and the current length of the longest ideal string ending at i.
            if (dynamicProgramming[i] > longestLength) {
                longestLength = dynamicProgramming[i];
                endIndex = i; // Update endIndex to mark where the longest subsequence ends.
            }
        }

        // Reconstruct the longest ideal subsequence using the parent array.
        StringBuilder longestIdealSubsequence = new StringBuilder();
        for (int i = endIndex; i != -1; i = parent[i]) {
            longestIdealSubsequence.append(s.charAt(i));
        }

        // The subsequence is constructed in reverse, so reverse it before returning.
        return longestIdealSubsequence.reverse().toString();
    }

}
Changes Made:
1.Parent Array: int[] parent is used to track the index of the previous character in the subsequence.
2.End Index: endIndex is used to store where the longest subsequence ends.
3.Reconstruction: After computing the DP array, the subsequence is reconstructed using the parent array.
This will now return and print the longest ideal subsequence instead of just its length.
--------------------------------------------------------------------------------
Solution 2: dp[c] meansthe length of the longest ideal subsequence ending with character c and NO need map
Style 1: Native DFS (720 min, TLE 23/85)
class Solution {
    public int longestIdealString(String s, int k) {
        int n = s.length();
        int result = 0;
        // We check the longest ideal subsequence ending with each character (a to z)
        for(int c = 0; c < 26; c++) {
            // Find the longest subsequence ending with each character
            result = Math.max(result, helper(s, k, n - 1, c));
        }
        return result;
    }

    // DFS function to recursively find the longest ideal subsequence ending 
    // at index i and character c
    private int helper(String s, int k, int i, int c) {
        // Base case: If we reach the start of the string, return 0 (no valid subsequence)
        if(i < 0) {
            return 0;
        }
        // Check if the current character match 'c'
        boolean match = c == (s.charAt(i) - 'a');
        // Recurse to check the longest subsequence without including the current character
        int result = helper(s, k, i - 1, c);
        // If the current character matches, we explore all valid previous characters within range k
        if(match) {
            for(int p = 0; p < 26; p++) {
                if(Math.abs(c - p) <= k) {
                    // Add 1 for the current matching character and explore previous characters
                    result = Math.max(result, helper(s, k, i - 1, p) + 1);
                }
            }
        }
        return result;
    }
}

Time Complexity: O(26 × 26^n)
Space Complexity: O(n)

Style 2: DFS + Memoization (10 min)
class Solution {
    public int longestIdealString(String s, int k) {
        int n = s.length();
        Integer[][] memo = new Integer[n][26];
        int result = 0;
        // We check the longest ideal subsequence ending with each character (a to z)
        for(int c = 0; c < 26; c++) {
            // Find the longest subsequence ending with each character
            result = Math.max(result, helper(s, k, n - 1, c, memo));
        }
        return result;
    }

    // DFS function to recursively find the longest ideal subsequence ending 
    // at index i and character c
    private int helper(String s, int k, int i, int c, Integer[][] memo) {
        // Base case: If we reach the start of the string, return 0 (no valid subsequence)
        if(i < 0) {
            return 0;
        }
        if(memo[i][c] != null) {
            return memo[i][c];
        }
        // Check if the current character match 'c'
        boolean match = c == (s.charAt(i) - 'a');
        // Recurse to check the longest subsequence without including the current character
        int result = helper(s, k, i - 1, c, memo);
        // If the current character matches, we explore all valid previous characters within range k
        if(match) {
            for(int p = 0; p < 26; p++) {
                if(Math.abs(c - p) <= k) {
                    // Add 1 for the current matching character and explore previous characters
                    result = Math.max(result, helper(s, k, i - 1, p, memo) + 1);
                }
            }
        }
        return memo[i][c] = result;
    }
}

Time Complexity: O(N * 26 * k), where N is the length of the string and k is the range of allowed characters.
Space Complexity: O(n)

Style 3: 2D DP (720 min, TLE 74/85)
class Solution {
    public int longestIdealString(String s, int k) {
        int result = 0;
        int n = s.length();
        // DP array to store the longest subsequence ending at each index with each character
        int[][] dp = new int[n][26];
        // Initialize the DP array for the first character
        dp[0][s.charAt(0) - 'a'] = 1;
        // The outer for loop just reverse the Native DFS reursion n - 1 -> 0 into 0 -> n - 1
        // Iterate over each character in the string
        for(int i = 1; i < n; i++) {
            int curChar = s.charAt(i) - 'a';
            // For each previous character within the allowed range [currChar - k, currChar + k]
            for(int prevChar = Math.max(0, curChar - k); prevChar <= Math.min(25, curChar + k); prevChar++) {
                for(int j = 0; j < i; j++) {
                    dp[i][curChar] = Math.max(dp[i][curChar], dp[j][prevChar] + 1);
                }
            }
            // Consider subsequences not including the current character
            // At minimum, the character itself forms a subsequence
            dp[i][curChar] = Math.max(dp[i][curChar], 1);
            // Update the global result with the longest subsequence found
            result = Math.max(result, dp[i][curChar]);
        }
        return result;
    }
}

Time Complexity: O(n^2 * k), where n is the length of the string and k is the range of allowed characters.
Space Complexity: O(n)

Style 4: 1D DP (1080min, too difficult to come up with the optimization idea)
class Solution {
    public int longestIdealString(String s, int k) {
        int result = 0;
        int n = s.length();
        int[] dp = new int[26];
        // The outer for loop just reverse the Native DFS reursion n - 1 -> 0 into 0 -> n - 1
        // Iterate over each character in the string
        for(int i = 0; i < n; i++) {
            int curChar = s.charAt(i) - 'a';
            // Find the maximum subsequence length for characters in the range [currChar - k, currChar + k]
            int curLongest = 0;
            // For each previous character within the allowed range [currChar - k, currChar + k]
            for(int prevChar = Math.max(0, curChar - k); prevChar <= Math.min(25, curChar + k); prevChar++) {
                curLongest = Math.max(curLongest, dp[prevChar]);
            }
            dp[curChar] = curLongest + 1;
            // Update the global result with the longest subsequence found
            result = Math.max(result, dp[curChar]);
        }
        return result;
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Refer to
https://leetcode.com/problems/longest-ideal-subsequence/editorial/
Approach 1: Recursive Dynamic Programming (Top Down)
Intuition
Due to the large length of s, checking every subsequence is not a feasible option. Instead, we should find some property that simplifies the construction of an ideal subsequence.
Consider building an ideal string as a subsequence of s by checking each letter from left to right. To keep track of what letters could be appended to some ideal string, we only need to access the last letter of the current ideal string.
For example, the same set of letters can be appended to the two strings "acdba" and "abbca" because both strings end with the letter "a". Any characters before the last letter won't affect future letter choices.
Therefore, we can maintain the longest possible subsequence of s created from the first i letters of s. Among all of these subsequences, we only need to track the last letter c of any ideal subsequence.
This motivates a dynamic programming (DP) approach. We can define dp[i][c] as the longest ideal subsequence ending with the letter c when considering only the first i letters in the input string s.
Since we need to perform difference calculations between characters, we will represent c as an integer from 0 to 25 that corresponds to each letter of the alphabet.
There are two types of transitions when calculating dp[i][c]:
1.Do not include si in an ideal subsequence. The length of the current longest subsequence stays the same, so dp[i][c] = dp[i - 1][c]
2.Include si in an ideal subsequence. Let c = si −′a′. This subsequence becomes one letter longer, so dp[i][c] = max(dp[i - 1][p]) + 1 for all characters p such that ∣c−p∣≤K. This simulates adding a new character to the longest previous subsequences that allow appending c.
For the base case of i=0 (the first letter), if c matches the first letter, then we can create an ideal sequence of length 1. Otherwise, c != s0, so it's impossible to create a non-empty ideal sequence with the first letter. We set dp[0][c] to 0 to indicate an empty sequence.
To retrieve the answer, we should consider the longest subsequences that consider all N letters and all 26 possible ending letters. These quantities are stored in the row dp[N - 1][c]. We calculate the possible ideal substring lengths for each c value, and the maximum is the result.
Algorithm
1.Initialize a dp table with N rows and 26 columns, and set the default values to −1.
2.Create the dfs method that passes i, c, dp, s, and k as parameters. Note that dp and s should be passed by reference. Steps 3-7 describe the implementation of the dfs method.
3.If dp[i][c] is not equal to −1, return the memoized value stored in dp[i][c].
4.Otherwise, set dp[i][c] to 1 if c == (s[i] - 'a'), and 0 otherwise.
5.If the current state is not a base case (i>0), check the option of not including si in this ideal subsequence.
6.If c == (s[i] - 'a'), check all transistions to previous letters p such that ∣c−p∣≤k.
7.Return dp[i][c] to end the recursive call.
8.Find the maximum of dp[N-1][c] for all c from 0 to 25, and return this value as the answer.
Implementation
class Solution {
    public int longestIdealString(String s, int k) {
        int N = s.length();

        // Initialize all dp values to -1 to indicate non-visited states
        int[][] dp = new int[N][26];
        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], -1);
        }

        // Find the maximum dp[N-1][c] and return the result
        int res = 0;
        for (int c = 0; c < 26; c++) {
            res = Math.max(res, dfs(N - 1, c, dp, s, k));
        }
        return res;
    }

    public int dfs(int i, int c, int[][] dp, String s, int k) {
        // Memoized value
        if (dp[i][c] != -1) {
            return dp[i][c];
        }
        // State is not visited yet
        dp[i][c] = 0;
        boolean match = c == (s.charAt(i) - 'a');
        if (match) {
            dp[i][c] = 1;
        }
        // Non base case handling
        if (i > 0) {
            dp[i][c] = dfs(i - 1, c, dp, s, k);
            if (match) {
                for (int p = 0; p < 26; p++) {
                    if (Math.abs(c - p) <= k) {
                        dp[i][c] = Math.max(dp[i][c], dfs(i - 1, p, dp, s, k) + 1);
                    }
                }
            }
        }
        return dp[i][c];
    }
}
Complexity Analysis
Let N be the length of s and L be the number of letters in the English alphabet, which is 26.
Time complexity: O(NL).
In the main function, we check each possible ending letter of some subsequence, calling dfs() L times. The dfs() function recursively calls itself, and the total number of dfs() calls that run prior to memoizing is bounded by N⋅L, so this step takes O(NL+L), which is essentially O(NL).
The loop inside the dfs() function makes up to 26 iterations. This loop is executed only if match is true, which is the case if c corresponds to the same ASCII value as the character s[i]. There is only one instance of c that fits this description for each distinct i, so this loop is executed at most once for each character in s. In other words, L transitions are executed only for N total states. Over the course of the whole search process, this loop executes up to O(NL) times.
Therefore, the total time complexity is O(NL+NL), or O(2NL), which we can simplify to O(NL). Note that L is 26, which is a constant, so we could simplify the time complexity to O(N).
Space complexity: O(NL).
The additional space complexity is O(NL), since the two-dimensional dp grid needs to be initialized for memoization. L is 26, which is a constant, so we could simplify the time complexity to O(N).

Approach 2: Iterative Dynamic Programming (Bottom Up, Space Optimized)
Intuition
Please read the above approach first, as this approach builds off of the previous approach. Top-down dynamic programming requires overhead for the call stack; let's use bottom-up dynamic programming to develop a more efficient solution.
If we examine the above approach, we can observe that dp[i] depends only on the previous row dp[i - 1] in the DP grid. When we transition to DP states ending at index i, we only need to check DP states ending at i−1.
By implementing this approach iteratively, we can store dp as an array that tracks only the previous row of DP values. We no longer need to memoize all DP states, so we can reduce the additional space complexity by a factor of N.
We can use two nested for loops to iterate through the dp values in order. The outer loop iterates over the current index i of s, and the inner loop iterates over every choice prev. Variable prev indicates the previous last letter of the subsequence. curr, which corresponds to the character s[i], is used to check if appending s[i] is valid.
Algorithm
Initialize the dp array of length 26 with all 0's.
Iterate through each letter in input string s and repeat steps 3-6 N times where N is the length of s.
Initialize a variable curr to the ASCII representation of si, and a variable best to 0.
Iterate through the possible candidates for the previous ending letter of some ideal subsequence, which are the letters in the alphabet that are at most K apart from si. Use best to track the maximum dp[prev].
Set dp[curr] = best + 1 to simulate appending letter curr = si − ′a′ to an ideal subsequence.
Update the result to the maximum between res and dp[curr].
Return the result.
Implementation
class Solution {
    public int longestIdealString(String s, int k) {
        int N = s.length();
        int[] dp = new int[26];

        int res = 0;
        // Updating dp with the i-th character
        for (int i = 0; i < N; i++) {
            int curr = s.charAt(i) - 'a';
            int best = 0;
            for (int prev = Math.max(0, curr - k); prev < Math.min(26, curr + k + 1); prev++) {
                best = Math.max(best, dp[prev]);
            }

            // Append s[i] to the previous longest ideal subsequence
            dp[curr] = best + 1;
            res = Math.max(res, dp[curr]);
        }

        return res;
    }
}
Complexity Analysis
Let N be the length of s and L be the number of letters in the English alphabet, which is 26.
Time complexity: O(NL).
The outer loop iterates through the characters in s, so it runs N times. The inner loop iterates up to L times for each character in s. Therefore, the time complexity is O(NL). Note that L is 26, which is a constant, so we could simplify the time complexity to O(N).
Space complexity: O(L)
We use a DP array of size L. L is 26, which is a constant, so we could simplify the time complexity to O(1).
--------------------------------------------------------------------------------
Refer to chatGPT
Native DFS (TLE 23/85)
class Solution {
    public int longestIdealString(String s, int k) {
        int N = s.length();
        int res = 0;
        // We check the longest ideal subsequence ending with each character (a to z)
        for (int c = 0; c < 26; c++) {
            res = Math.max(res, dfs(N - 1, c, s, k)); // Find the longest subsequence ending with each character
        }
        return res;
    }

    // DFS function to recursively find the longest ideal subsequence ending at index i and character c
    public int dfs(int i, int c, String s, int k) {
        // Base case: If we reach the start of the string, return 0 (no valid subsequence)
        if (i < 0) {
            return 0;
        }
        boolean match = c == (s.charAt(i) - 'a'); // Check if the current character matches 'c'
        int best = dfs(i - 1, c, s, k); // Recurse to check the best subsequence without including the current character
        if (match) {
            // If the current character matches, we explore all valid previous characters within range k
            for (int p = 0; p < 26; p++) {
                if (Math.abs(c - p) <= k) {
                    // Add 1 for the current matching character and explore previous characters
                    best = Math.max(best, dfs(i - 1, p, s, k) + 1);
                }
            }
        }
        return best; // Return the best subsequence length found
    }
}
The time complexity of the provided DFS solution is quite high due to its structure.
Let's break down the complexity:
1.Outer Loop:
- The outer loop runs 26 times (once for each character from 'a' to 'z').
- This loop iterates over each possible ending character for the ideal subsequence.
2.Recursive Helper Function:
- For each character, the helper function recursively computes the longest subsequence ending at each index i.
- The recursion explores two main cases:
i.Without including the current character (helper(s, k, i - 1, c)).
ii.Including the current character and exploring all possible valid previous characters (helper(s, k, i - 1, p) where |c - p| ≤ k).
3.For Each Recursive Call:
- The recursive function can make up to 26 recursive calls for each character p within the valid range of the character c (where |c - p| ≤ k).
4.Recursive Tree Depth:
- The depth of the recursive calls is proportional to the length of the string n, as the recursion is done from n - 1 to 0.
Total Time Complexity:
For each character in the outer loop, the helper function explores all possible previous characters, leading to 26 recursive calls at each step.
- For each of the 26 characters in the outer loop:
- At each index i of the string, the function makes up to 26 recursive calls.
- The depth of the recursion is n (the length of the string).
This gives a time complexity of O(26 × 26^n) in the worst case because each recursive call can spawn 26 further recursive calls. This exponential behavior makes the DFS approach extremely inefficient, especially for large inputs, leading to a time complexity of O(26^(n+1)).
Conclusion:
- The time complexity is exponential O(26^(n+1)), which explains why the DFS approach without memoization is highly inefficient for large input sizes.
The space complexity of the given DFS solution can be determined by considering the space used by:
1.Recursive Call Stack:
- The depth of the recursive calls depends on the length of the string n. In the worst case, the recursion goes as deep as n, which means the maximum recursion depth is n.
- Each recursive call stores variables like i, c, and possibly p. However, the dominant factor here is the recursion depth, which results in a stack space proportional to O(n).
2.Auxiliary Space:
- Other than the recursive stack, the algorithm uses only a few variables (s, k, i, c, etc.), all of which take constant space.
- There is no additional data structure (like arrays or hashmaps) being used, so no extra space is consumed other than the recursion stack.
Total Space Complexity:
- Recursive Call Stack: The depth of the recursion is O(n), where n is the length of the input string s.
- Auxiliary Space: Only constant space is used for variables, which is O(1).
Thus, the overall space complexity is: O(n)
This is due to the recursion stack depth, which can go as deep as the length of the string.
DFS + Memoization
To optimize the native DFS solution using memoization, we need to store previously computed results to avoid redundant calculations. Here's the memoized version of the native DFS solution:
Key Changes:
1.Memoization Table: We add a 2D memoization array memo[i][c], where i is the index in the string s, and c represents the current character (0-25 corresponding to 'a'-'z').
2.Base Case and Memo Lookup: Before making recursive calls, we check if the value has already been computed. If it has, we return the memoized result.
class Solution {
    public int longestIdealString(String s, int k) {
        int N = s.length();
        
        // Initialize memo table where -1 means not visited
        int[][] memo = new int[N][26];
        for (int i = 0; i < N; i++) {
            Arrays.fill(memo[i], -1);
        }

        int res = 0;
        
        // Find the longest ideal subsequence for each possible ending character ('a' to 'z')
        for (int c = 0; c < 26; c++) {
            res = Math.max(res, dfs(N - 1, c, s, k, memo));  // Compute the result recursively with memoization
        }
        
        return res;
    }

    // DFS function to find the longest subsequence ending at index i with character c
    private int dfs(int i, int c, String s, int k, int[][] memo) {
        // Base case: If we are out of bounds, return 0
        if (i < 0) {
            return 0;
        }
        
        // Check memoized result
        if (memo[i][c] != -1) {
            return memo[i][c];  // Return previously computed result
        }

        boolean match = c == (s.charAt(i) - 'a');  // Check if the current character matches 'c'
        int best = dfs(i - 1, c, s, k, memo);  // Find best without including the current character

        if (match) {
            // If current character matches, try all previous characters within the allowed range (k)
            for (int p = Math.max(0, c - k); p <= Math.min(25, c + k); p++) {
                best = Math.max(best, dfs(i - 1, p, s, k, memo) + 1);  // Add 1 for the current match
            }
        }

        // Store the result in the memo table before returning
        memo[i][c] = best;
        return best;
    }
}
Explanation:
1.Memoization Table:
- memo[i][c]: Stores the result of the longest ideal subsequence ending at index i with character c (where c is a value between 0 and 25, corresponding to 'a'-'z').
- Initially, all values in memo are set to -1, indicating they haven't been computed yet.
2.DFS Function:
- The function dfs(i, c, s, k, memo) computes the longest ideal subsequence ending at index i with character c.
- If the result for memo[i][c] has already been computed (i.e., memo[i][c] != -1), we return the memoized result.
- If the current character at index i matches the character c, we explore all valid previous characters within the allowed range (|c - p| <= k).
- The result is memoized in memo[i][c] before returning to avoid redundant recalculations.
3.Base Case:
- When i < 0, we've gone out of bounds, so we return 0 because no valid subsequence can exist.
Time Complexity:
- The solution explores each index and character once, and memoization ensures that no state is computed more than once. The time complexity is O(N * 26 * k), where N is the length of the string and k is the range of allowed characters.
Space Complexity:
- The space complexity is O(N * 26) due to the memoization table.
2D DP (TLE 73/85)
class Solution {
    public int longestIdealString(String s, int k) {
        int N = s.length();
        int[][] dp = new int[N][26]; // DP array to store the longest subsequence ending at each index with each character
        int res = 0;
        // Initialize the DP array for the first character
        dp[0][s.charAt(0) - 'a'] = 1;
        // Iterate over each character in the string
        for (int i = 1; i < N; i++) {
            int currChar = s.charAt(i) - 'a';
            // For each previous character within the allowed range [currChar - k, currChar + k]
            for (int prevChar = Math.max(0, currChar - k); prevChar <= Math.min(25, currChar + k); prevChar++) {
                // Find the best subsequence length from the previous index
                for (int j = 0; j < i; j++) {
                    dp[i][currChar] = Math.max(dp[i][currChar], dp[j][prevChar] + 1);
                }
            }
            // Consider subsequences not including the current character
            dp[i][currChar] = Math.max(dp[i][currChar], 1); // At minimum, the character itself forms a subsequence
            // Update the global result with the longest subsequence found
            res = Math.max(res, dp[i][currChar]);
        }
        return res;
    }
}
Explanation:
1.dp[i][currChar]: Stores the length of the longest ideal subsequence ending at index i with character currChar.
2.The inner loop over prevChar explores all valid previous characters within the range [currChar - k, currChar + k], and the loop over j checks all previous indices (j < i) to find the longest subsequence ending with a valid previous character.
3.Base Case: Every character forms a subsequence of at least length 1 on its own.
4.The time complexity of this solution is O(N^2 * k) due to the triple nested loops, which is why it could exceed time limits for large inputs.
Elaborate where the statement "the loop over j checks all previous indices (j < i) to find the longest subsequence ending with a valid previous character." logic mapping in native DFS solution ?
In the native DFS solution, the logic of checking all previous indices (j < i) to find the longest subsequence ending with a valid previous character is implicitly handled by the recursive function dfs(). Let’s break it down in detail:
// DFS function to recursively find the longest ideal subsequence ending at index i and character c
public int dfs(int i, int c, String s, int k) {
    // Base case: If we reach the start of the string, return 0 (no valid subsequence)
    if (i < 0) {
        return 0;
    }
    
    boolean match = c == (s.charAt(i) - 'a'); // Check if the current character matches 'c'
    
    // Recursively check the subsequence without including the current character
    int best = dfs(i - 1, c, s, k); 
    
    // If the current character matches, explore all valid previous characters within range k
    if (match) {
        for (int p = 0; p < 26; p++) {
            if (Math.abs(c - p) <= k) {
                // Add 1 for the current matching character and explore previous characters
                best = Math.max(best, dfs(i - 1, p, s, k) + 1);
            }
        }
    }
    
    return best; // Return the best subsequence length found
}

Recursive Nature of DFS:
1.Base Case:
- dfs(i, c) computes the longest subsequence ending at index i with character c.
- If i < 0, it means there's no valid subsequence, so we return 0.
2.Recurse Without Including Current Character (dfs(i - 1, c)):
- The DFS function recursively calls itself with the previous index i - 1 and the same character c. This explores the scenario where we skip the current character and find the best subsequence for previous indices up to i - 1 with character c.
- This part of the recursion in DFS corresponds to checking previous indices in the DP solution. Essentially, for each index i, it looks back at all previous indices (j < i) to find the longest subsequence ending with the same character c.
3.Matching Case:
- If the current character matches c (i.e., s.charAt(i) == c), we then explore all possible valid previous characters within a range of k (i.e., all characters p such that Math.abs(c - p) <= k).
- For each valid p, we recursively call dfs(i - 1, p, s, k) to find the best subsequence ending with character p up to index i - 1.
This recursive call:
best = Math.max(best, dfs(i - 1, p, s, k) + 1);
means:
- We are checking every possible previous character p (within the allowed range of difference k) to see if appending the current matching character c to a subsequence ending with p forms a longer subsequence.
How This Maps to the DP Solution:
- DFS Recursive Calls (dfs(i - 1, c) and dfs(i - 1, p)):
- In the DFS solution, dfs(i - 1, c) finds the best subsequence at index i - 1 with character c, and dfs(i - 1, p) finds the best subsequence at index i - 1 with any previous character p within the range.
- In the DP solution, this corresponds to the nested loops where we check all valid previous characters (prevChar) and look back at all previous indices (j < i) to find the longest subsequence:
for (int j = 0; j < i; j++) {  // Check all previous indices
    dp[i][currChar] = Math.max(dp[i][currChar], dp[j][prevChar] + 1);
}
- In DFS, this check is done recursively across all previous indices and characters, whereas in the DP solution, this is done explicitly using loops.
Key Differences:
- DFS is a top-down approach, where for each index i, the recursion explores all possible previous indices (i - 1, i - 2, ..., 0) and checks all valid characters p to find the longest subsequence.
- DP is a bottom-up approach, where for each index i, we iterate over all possible previous indices j and valid characters prevChar to find the longest subsequence iteratively.
In the DP solution:
- The logic of checking all previous indices j is explicitly written as a loop:
for (int j = 0; j < i; j++) {
    dp[i][currChar] = Math.max(dp[i][currChar], dp[j][prevChar] + 1);
}
- In DFS, the same logic is achieved through recursive calls to dfs(i - 1, p).
So, the loop over j in the DP solution corresponds to the recursive calls for previous indices in the DFS solution.
The 2D DP solution is exceeding the time limit because it involves three nested loops:
1.The outer loop runs for each character in the string s, which takes O(N) time.
2.The second loop iterates over all possible previous characters within the range [currChar - k, currChar + k], which takes O(k) time.
3.The innermost loop iterates over all previous positions j from 0 to i - 1, which results in a complexity of O(i), where i can be as large as N.
Time Complexity:
- The overall complexity is approximately O(N * k * N).
- For each position i, you are looping through i previous positions, which makes this a quadratic time solution.
- Given that N can be large (up to 10^5), the solution becomes too slow for large inputs, hence the Time Limit Exceeded (TLE) error.

Above 2D DP solution encounter Time Limit Exceed issue, can we optimize ?
Optimized Approach:
Instead of iterating over all previous indices j, we can eliminate the innermost loop by keeping track of the best subsequence ending with each character at any index using a 1D DP array. This way, you only need two loops: one to iterate over each character and another to iterate over possible character transitions within range k.
Yes, the 2D DP solution can be optimized to avoid the Time Limit Exceed (TLE) issue. The primary bottleneck in the current approach is the triple loop, where for each index i, you are looping over all previous indices j and for each character c. This leads to a time complexity of O(n^2 * 26), which can be inefficient for large input sizes.
Optimization Strategy
The key to optimizing this solution is to reduce the number of times we check all previous indices. Specifically, instead of maintaining a 2D DP table (dp[i][c]) where we store the longest ideal subsequence ending at index i with character c, we can switch to a 1D DP array that tracks the best subsequence that ends with each character across the entire string.
Key Idea:
Instead of maintaining dp[i][c] for each index i and character c, we can maintain dp[c] which tracks the longest ideal subsequence that ends with character c across all indices seen so far. This removes the need to look back at each previous index explicitly, reducing the complexity from O(n^2) to O(n * 26)
Optimized DP Approach:
1.Use a 1D DP array dp[26] where dp[c] stores the longest ideal subsequence ending with character c.
2.For each character s[i], update the corresponding dp[c] based on previous valid characters within the range [c - k, c + k].
3.At each step, update the result with the maximum subsequence length.
1D DP
class Solution {
    public int longestIdealString(String s, int k) {
        int[] dp = new int[26];  // DP array to store the longest ideal subsequence ending with each character 'a' to 'z'
        int res = 0;

        // Iterate over each character in the string
        for (int i = 0; i < s.length(); i++) {
            int currChar = s.charAt(i) - 'a';  // Convert character to integer (0-based for 'a' to 'z')
            
            // Find the best subsequence length for the current character based on valid previous characters
            int best = 0;
            for (int prevChar = Math.max(0, currChar - k); prevChar <= Math.min(25, currChar + k); prevChar++) {
                best = Math.max(best, dp[prevChar]);  // Find the best subsequence length among valid previous characters
            }
            
            // Update dp[currChar], add 1 for including the current character in the subsequence
            dp[currChar] = best + 1;

            // Update the global result
            res = Math.max(res, dp[currChar]);
        }
        
        return res;
    }
}
Explanation:
- 1D DP Array (dp[26]):
- dp[c] stores the length of the longest ideal subsequence that ends with character c.
- Iterating Over Each Character in the String:
- For each character s[i], calculate the longest subsequence that can end with s[i].
- Range of Valid Previous Characters:
- For each character s[i] (with value currChar), we check all possible previous characters within the range [currChar - k, currChar + k]. This range ensures that the absolute difference between adjacent characters is at most k.
- Best Subsequence for Current Character:
- The value of dp[currChar] is updated to the best subsequence length found in the range of previous characters, plus one for including the current character.
- Global Result:
- The global result res is updated with the maximum value of dp[currChar] at each step.
In the corrected approach, we do not need to track the index i directly. Instead, we can track the best possible subsequence for each character without worrying about the specific index. By focusing on the character transitions, we can directly update the DP for each character c and accumulate the subsequence length more efficiently.
Why This Approach Works:
- This approach avoids the need to look back at all previous indices for each character and reduces the complexity by focusing only on the character differences (handled in constant time per character due to the limited alphabet size).
Key Idea for improve 2D DP to 1D DP:
We don't need to track the subsequence at each index i, but rather we focus on tracking the best subsequence ending at each character 'a' to 'z'. We only need a 1D DP array where dp[c] stores the longest ideal subsequence ending with character c. The value dp[c] is updated by considering all possible valid characters prevChar that can transition into c based on the given constraint k.
Explanation:
1.DP Array:
- dp[c] tracks the longest ideal subsequence ending with character c (from 'a' to 'z').
- For each character s[i], we calculate its contribution by looking at all possible valid previous characters within range [currChar - k, currChar + k] and updating the DP array.
2.Transition:
- For each character s[i], we calculate the maximum possible subsequence length that can be extended from any valid previous character within range [-k, +k] relative to s[i].
- We update dp[currChar] as the maximum subsequence length from all valid previous characters plus 1 (to include the current character).
3.Result:
- The final result is the maximum value in the dp array, which gives the length of the longest ideal subsequence.
--------------------------------------------------------------------------------
Refer to
https://leetcode.com/problems/longest-ideal-subsequence/solutions/2390512/java-c-python-dp-solution/
Explanation
dp[c] means the length of the longest ideal subsequence
ending with character c.
Iterate the character i in string s,
c can be the next character for string ending from i - k to i + k.
So that dp[i] = max(dp[i-k], dp[i-k+1] ... , dp[i+k]) + 1.
return the max(dp) as result.
Complexity
Time O(n)
Space O(128)
    public int longestIdealString(String s, int k) {
        int res = 0, n = s.length(), dp[] = new int[150];
        for (int ci = 0; ci < n; ++ci) {
            int i = s.charAt(ci);
            for (int j = i - k; j <= i + k; ++j)
                dp[i] = Math.max(dp[i], dp[j]);
            res = Math.max(res, ++dp[i]);
        }
        return res;
    }

--------------------------------------------------------------------------------
The critical difference between Style 1 and Style 2 is the definition of dp
In Style 1, the dp[i] means the length of the longest ideal substring ending at each character, it constructs more based on each char in given string and deeply related to each char's index, and it has to be used with another map which keep track of the last index of each character that is part of the longest ideal string so far, the index of the char matters, especially the same char presents on different indexes matters.
In Style 2, the dp[c] means the length of the longest ideal subsequence ending with character c, it doesn't construct on given string's index related char, all same char c but presents on different indexes will be treated the same and stored or updated at the same slot in dp array as dp[c], no map which keep track of the last index of each character that is part of the longest ideal string required to be used, no more update index in map for same character required, update character c at dp[c] always, no matter on same character c's index in the given string



Refer to
L300.Longest Increasing Subsequence
