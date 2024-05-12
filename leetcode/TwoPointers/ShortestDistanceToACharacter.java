/**
Refer to
https://leetcode.com/problems/shortest-distance-to-a-character/
Given a string S and a character C, return an array of integers representing the shortest distance from the character C in the string.

Example 1:
Input: S = "loveleetcode", C = 'e'
Output: [3, 2, 1, 0, 1, 0, 0, 1, 2, 2, 1, 0]

Note:
S string length is in [1, 10000].
C is a single character, and guaranteed to be in string S.
All letters in S and C are lowercase.
*/

// Solution 1: Two Pointers + Two side scanning
// Refer to
// https://leetcode.com/problems/shortest-distance-to-a-character/solution/
/**
Approach #1: Min Array [Accepted]
Intuition
For each index S[i], let's try to find the distance to the next character C going left, and going right. 
The answer is the minimum of these two values.

Algorithm
When going left to right, we'll remember the index prev of the last character C we've seen. Then the answer is i - prev.
When going right to left, we'll remember the index prev of the last character C we've seen. Then the answer is prev - i.
We take the minimum of these two answers to create our final answer.
class Solution {
    public int[] shortestToChar(String S, char C) {
        int N = S.length();
        int[] ans = new int[N];
        int prev = Integer.MIN_VALUE / 2;

        for (int i = 0; i < N; ++i) {
            if (S.charAt(i) == C) prev = i;
            ans[i] = i - prev;
        }

        prev = Integer.MAX_VALUE / 2;
        for (int i = N-1; i >= 0; --i) {
            if (S.charAt(i) == C) prev = i;
            ans[i] = Math.min(ans[i], prev - i);
        }

        return ans;
    }
}
*/

// https://leetcode.com/problems/shortest-distance-to-a-character/discuss/125788/C%2B%2BJavaPython-2-Pass-with-Explanation
/**
Solution 1: Record the Position
Initial result array.
Loop twice on the string S.
First forward pass to find shortest distant to character on left.
Second backward pass to find shortest distant to character on right.

In python solution, I merged these two for statement.
We can do the same in C++/Java by:

for (int i = 0; i >= 0; res[n-1] == n ? ++i : --i)
But it will become less readable.

Time complexity O(N)
Space complexity O(N) for output

Java
    public int[] shortestToChar(String S, char C) {
        int n = S.length(), pos = -n, res[] = new int[n];
        for (int i = 0; i < n; ++i) {
            if (S.charAt(i) == C) pos = i;
            res[i] = i - pos;
        }
        for (int i = pos - 1; i >= 0; --i) {
            if (S.charAt(i) == C)  pos = i;
            res[i] = Math.min(res[i], pos - i);
        }
        return res;
    }
*/
class Solution {
    public int[] shortestToChar(String S, char C) {
        int n = S.length();
        int[] result = new int[n];
        int pos = Integer.MIN_VALUE / 2;
        for(int i = 0; i < n; i++) {
            if(S.charAt(i) == C) {
                pos = i;
            }
            result[i] = i - pos;
        }
        pos = Integer.MAX_VALUE / 2;
        for(int i = n - 1; i >= 0; i--) {
            if(S.charAt(i) == C) {
                pos = i;
            }
            result[i] = Math.min(result[i], pos - i);
        }
        return result;
    }
}















































https://leetcode.com/problems/shortest-distance-to-a-character/description/
Given a string s and a character c that occurs in s, return an array of integers answer where answer.length == s.length and answer[i] is the distance from index i to the closest occurrence of character c in s.
The distance between two indices i and j is abs(i - j), where abs is the absolute value function.

Example 1:
Input: s = "loveleetcode", c = "e"
Output: [3,2,1,0,1,0,0,1,2,2,1,0]
Explanation: The character 'e' appears at indices 3, 5, 6, and 11 (0-indexed).
The closest occurrence of 'e' for index 0 is at index 3, so the distance is abs(0 - 3) = 3.
The closest occurrence of 'e' for index 1 is at index 3, so the distance is abs(1 - 3) = 2.
For index 4, there is a tie between the 'e' at index 3 and the 'e' at index 5, but the distance is still the same: abs(4 - 3) == abs(4 - 5) = 1.
The closest occurrence of 'e' for index 8 is at index 6, so the distance is abs(8 - 6) = 2.

Example 2:
Input: s = "aaab", c = "b"
Output: [3,2,1,0]

Constraints:
- 1 <= s.length <= 10^4
- s[i] and c are lowercase English letters.
- It is guaranteed that c occurs at least once in s.
--------------------------------------------------------------------------------
Attempt 1: 2024-05-09
Solution 1: Two Passes (30 min)
class Solution {
    public int[] shortestToChar(String s, char c) {
        int len = s.length();
        int[] result = new int[len];
        // No need prev = Integer.MIN_VALUE / 2, because
        // the definition is 1 <= s.length <= 10^4
        int prev = -10001;
        for(int i = 0; i < len; i++) {
            if(s.charAt(i) == c) {
                prev = i;
            }
            result[i] = i - prev;
        }
        // No need prev = Integer.MAX_VALUE / 2, because
        // the definition is 1 <= s.length <= 10^4
        prev = 10001;
        for(int i = len - 1; i >= 0; i--) {
            if(s.charAt(i) == c) {
                prev = i;
            }
            result[i] = Math.min(result[i], prev - i);
        }
        return result;
    }
}

Time Complexity: O(N)
Space Complexity: O(N)

Refer to
https://algo.monster/liteproblems/821
Problem Description
In this problem, we are given a string s and a character c, with the requirement that c occurs at least once in s. We need to return an array of integers answer such that for every index i in the string s, answer[i] represents the shortest distance from the character at s[i] to the nearest occurrence of the character c. The distance between two indices is defined as the absolute difference between those indices.
Intuition
The intuition behind the solution is to iterate through the string s twice to find the distance to the nearest occurrence of character c. On the first pass, we move from left to right, keeping track of the most recent position of c (using a variable pre initialized with a value smaller than any valid index). For every character in s, we update ans[i] to be the distance from the current index i to the closest occurrence of c encountered so far.
The key point to notice is that this only accounts for the nearest occurrence of c that is either at the current index i or to its left since we have moved from left to right.
To find the overall nearest occurrence, we need to also check for the closest c to the right of our current position. Hence, we do a second pass from right to left. In this pass, we use a variable suf initialized with a value larger than any valid index to track the latest occurrence of c from the right. As we move leftward, we update ans[i] with the minimum of its current value and the distance to the nearest c from the right.
By comparing ans[i] obtained from the leftward and rightward passes for each index i, we ensure that the closest c in either direction is considered. This allows us to compute the shortest distance for each character in s to the nearest c.
By using two passes, we make the algorithm efficient with linear time complexity, since each pass processes every element exactly once.
Solution Approach
The solution approach involves a two-pass algorithm to compute the minimum distance to the closest occurrence of the character c for each index in the string s. Here is the step-by-step implementation explained:
Initialize the data structures and variables: We initialize an array ans with the same length as string s, filled with n, the length of s. This ensures that at the beginning, the minimum distances are set to the largest possible value. We also initialize two variables, pre and suf, to -inf and inf respectively. These will hold the indices of the most recent occurrence of character c from the left pass and the right pass, ensuring that they are initially set to invalid positions outside the range of valid indices.
First pass (left to right): We loop through s from left to right using i to track the current index. If we find an occurrence of c, we update pre to the current index i. For each index i, we compute the distance from i to pre and update ans[i] to be the minimum of its current value and i - pre.
This leftward pass sets ans[i] to the correct value if the nearest c is to the left or at index i.
Second pass (right to left): We perform a backward loop through s from the last index to the first, again tracking the current index with i. If s[i] is c, we update suf to i. Then for each index i, we calculate the distance to suf, and we update ans[i] to be the minimum of its current value and suf - i.
This rightward pass corrects ans[i] if the nearest occurrence of c is to the right of i.
By combining the results of both passes, ans[i] ends up with the minimum distance to the closest c from either direction. Since we are moving in both directions once, our time complexity remains O(n), where n is the length of s.
Return the result: Finally, after both passes are completed, we return the array ans which contains the minimum distance to the closest c for each index in the string s.
In terms of data structures, we only use a list to store the result. The algorithm heavily relies on the two-pointer technique, where the pointers (pre and suf) move through the string in opposite directions, updating the distances based on the latest seen occurrences of the target character c.
Example Walkthrough
Let's consider an example where the string s is "leetcode" and the character c is 'e'.
Step 1: Initialize the data structures and variables
We start by creating the ans array of the same length as s, which is 8 in this case. The ans array is initially filled with the largest possible minimum distances, which would be 8 (the length of s).
ans = [8, 8, 8, 8, 8, 8, 8, 8]
We'll have two variables pre = -inf and suf = inf to track the most recent occurrence of c from both ends.
Step 2: First pass (left to right)
Start from index i = 0, since s[0] != 'e', we skip updating pre and ans[i] = max(8, i - (-inf)), which remains 8.
Move to index i = 1, s[1] = 'e', we update pre = 1, and ans[1] = min(8, 1 - 1) = 0.
Continue to index i = 2, s[2] != 'e', so ans[2] = min(8, 2 - 1) = 1.
Repeat until the end of s, creating the array after the left to right pass:
ans = [8, 0, 1, 2, 0, 1, 2, 3]
Step 3: Second pass (right to left)
Start from the last index i = 7, s[7] != 'e', and update ans[7] = min(3, inf - 7) = 3.
Move to index i = 6, s[6] != 'e', and ans[6] = min(2, 7 - 6) = 1.
Proceed to index i = 4, s[4] = 'e', we update suf = 4, and ans[4] = min(0, 4 - 4) = 0.
Continue moving left and updating ans until i = 0, resulting in:
ans = [2, 0, 1, 0, 0, 1, 1, 3]
Now, ans[i] represents the shortest distance to the nearest e for every character.
Step 4: Return the result
Finally, we return the updated ans array:
ans = [2, 0, 1, 0, 0, 1, 1, 3]
This ans array provides the shortest distance from each character in s to the nearest occurrence of c, as desired. Each element in ans is computed by considering the closest occurrence of c from both directions in the string. The solution is efficient with a linear time complexity due to the two-pass algorithm.
Solution Implementation
class Solution {
    public int[] shortestToChar(String s, char targetChar) {
        // Get the length of the string to create and fill the output array
        int strLength = s.length();
        int[] shortestDistances = new int[strLength];

        // The variable 'inf' represents an effective infinity for our purposes
        final int inf = 1 << 30; // 2^30 is much greater than the maximum possible string length
        Arrays.fill(shortestDistances, inf); // Fill the array with 'infinite' distance initially

        // First pass: move from left to right,
        // updating the closest target character seen so far
        for (int i = 0, closestLeft = -inf; i < strLength; ++i) {
            // Update the position of the closest target character if found
            if (s.charAt(i) == targetChar) {
                closestLeft = i;
            }
            // Update the shortest distance for position i
            shortestDistances[i] = i - closestLeft;
        }

        // Second pass: move from right to left,
        // updating the closest target character seen so far
        for (int i = strLength - 1, closestRight = inf; i >= 0; --i) {
            // Update the position of the closest target character if found
            if (s.charAt(i) == targetChar) {
                closestRight = i;
            }
            // Update the shortest distance for position i only if it's smaller than the current value
            shortestDistances[i] = Math.min(shortestDistances[i], closestRight - i);
        }

        // Return the array of shortest distances to the target character
        return shortestDistances;
    }
}
Time and Space Complexity
The time complexity of the code is O(n), where n is the length of the string s. This is because the code processes each character in the string twice: once in the forward direction and once in the backward direction.
As for the space complexity, the space used by the program is also O(n). An additional array ans of size n is used to store the minimum distance to the character c for each position in the string.
In detail, the algorithm involves the following steps:
It initializes the ans array with n, where n is the size of the string s. This action itself has a space complexity of O(n).
It iterates through the string once from left to right (forward traversal) to calculate and update the minimum distance from each character to the previous occurrence of c. This has a time complexity of O(n) for the traversal.
It then iterates through the string again from right to left (backward traversal) to update the minimum distance based on the following occurrences of c. This backward traversal also takes O(n) time.
No additional data structures are used that are dependent on the size of the input, so the space complexity remains O(n).
Based on these operations, the total time complexity is O(n) (forward O(n) + backward O(n) is still O(n)), and the total space complexity remains O(n) given the above analysis.


Refer to
L845.Longest Mountain in Array (Ref.L821)
