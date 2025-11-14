https://leetcode.com/problems/remove-colored-pieces-if-both-neighbors-are-the-same-color/description/
There are n pieces arranged in a line, and each piece is colored either by 'A' or by 'B'. You are given a string colors of length n where colors[i] is the color of the ith piece.
Alice and Bob are playing a game where they take alternating turns removing pieces from the line. In this game, Alice moves first.
- Alice is only allowed to remove a piece colored 'A' if both its neighbors are also colored 'A'. She is not allowed to remove pieces that are colored 'B'.
- Bob is only allowed to remove a piece colored 'B' if both its neighbors are also colored 'B'. He is not allowed to remove pieces that are colored 'A'.
- Alice and Bob cannot remove pieces from the edge of the line.
- If a player cannot make a move on their turn, that player loses and the other player wins.
Assuming Alice and Bob play optimally, return true if Alice wins, or return false if Bob wins.
 
Example 1:
Input: colors = "AAABABB"
Output: true
Explanation:
AAABABB -> AABABB
Alice moves first.
She removes the second 'A' from the left since that is the only 'A' whose neighbors are both 'A'.
Now it's Bob's turn.
Bob cannot make a move on his turn since there are no 'B's whose neighbors are both 'B'.
Thus, Alice wins, so return true.

Example 2:
Input: colors = "AA"
Output: false
Explanation:
Alice has her turn first.
There are only two 'A's and both are on the edge of the line, so she cannot move on her turn.
Thus, Bob wins, so return false.

Example 3:
Input: colors = "ABBBBBBBAAA"
Output: false
Explanation:
ABBBBBBBAAA -> ABBBBBBBAA
Alice moves first.
Her only option is to remove the second to last 'A' from the right.
ABBBBBBBAA -> ABBBBBBAA
Next is Bob's turn.
He has many options for which 'B' piece to remove. He can pick any.
On Alice's second turn, she has no more pieces that she can remove.
Thus, Bob wins, so return false.
 
Constraints:
- 1 <= colors.length <= 105
- colors consists of only the letters 'A' and 'B'
--------------------------------------------------------------------------------
Attempt 1: 2025-11-12
Solution 1: Greedy + Math (60 min)
class Solution {
    public boolean winnerOfGame(String colors) {
        int aliceMoves = 0;
        int bobMoves = 0;
        int n = colors.length();
        // Count consecutive 'A's and 'B's that can be removed
        for (int i = 1; i < n - 1; i++) {
            char current = colors.charAt(i);
            char prev = colors.charAt(i - 1);
            char next = colors.charAt(i + 1);
            if (current == 'A' && prev == 'A' && next == 'A') {
                aliceMoves++;
            } else if (current == 'B' && prev == 'B' && next == 'B') {
                bobMoves++;
            }
        }
        // Alice wins if she has more moves than Bob
        return aliceMoves > bobMoves;
    }
}

Time Complexity: O(n)
Space Complexity: O(1)

Refer to
https://leetcode.com/problems/remove-colored-pieces-if-both-neighbors-are-the-same-color/solutions/1524153/cpythonjava-count-aaa-and-bbb-by-lokeshs-2kdn/
Count "AAA" and "BBB" and compare them
You can only remove an A that is surrounded by two other A's.
So if you keep on removing valid A's, then the valid B's are not affected. Same goes for all valid B's.
You just count all the valid A's and valid B's. At each turn, Alice removes a valid A and the count of all valid A's (countA) decreases by one. Similarly after Bob's turn, countB decreases by one.
So if countA > countB, then a turn comes when Bob has no valid B's to remove. So he loses and Alice wins. You return TRUE.
Else if countA == countB, then at some point Alice has no valid A's to remove, So Bob wins and you return FALSE.
Else (countA < countB), then at some point Alice has no valid A's to remove. So Bob wins and you return FALSE.
In either case, if countA <= countB, then you return FALSE.
So just return countA > countB.
class Solution {
    public boolean winnerOfGame(String s) {
        int a = 0, b = 0;
        for(int i=1; i<s.length()-1; i++) {
            if(s.charAt(i) == s.charAt(i-1) && s.charAt(i) == s.charAt(i+1)) {
                if(s.charAt(i) == 'A')
                    a++;
                else
                    b++;
             }
        }                    
        return a>b;
    }
}

Refer to Kimi
Java Solution – LeetCode 2038
“Remove Colored Pieces if Both Neighbors are the Same Color”
Idea
- Every ‘A’ can be removed only if it is between two other **‘A’**s.
- Same for ‘B’.
- Count how many legal moves each color has; the player who has no move when it is their turn loses.
Algorithm
Single left-to-right pass:
For every inner index i, if colors[i-1] == colors[i] == colors[i+1]
⇒ increment the counter of that color.
Complexity
- Time: O(n)
- Space: O(1)
Code
class Solution {
    public boolean winnerOfGame(String colors) {
        int a = 0, b = 0;          // number of removable A/B
        int n = colors.length();
        for (int i = 1; i < n - 1; i++) {
            char c = colors.charAt(i);
            if (c == colors.charAt(i - 1) && c == colors.charAt(i + 1)) {
                if (c == 'A') a++;
                else          b++;
            }
        }
        return a > b;   // Alice wins iff she has more moves
    }
}
Short Explanation
Alice moves first.
If the amount of ‘AAA’ triplets (a) is strictly greater than the amount of ‘BBB’ triplets (b), Alice wins; otherwise Bob wins.
It's actually very simple, if you think about this fact: "There is no move a player can make that will unlock a move by another player". Because unlocking a move by another player would mean for example removing a B in between two As which is impossible. Therefore the number of moves of each player is determined by the initial number of moves they can make. Hence we have to count for AAA and BBB only.
Exactly — that observation is the key insight.
Because every valid move removes a piece whose two neighbors are the same color, the removed piece can never become a neighbor of any other piece afterward.
Hence:
- A move never creates a new triple (“AAA” or “BBB”).
- A move never destroys a triple of the opposite color.
Therefore the total number of moves each player can ever make is frozen at the very beginning—no future play can change those counts.
Since Alice moves first, she wins iff her initial move-count is strictly larger than Bob’s.

Refer to
L2419.Longest Subarray With Maximum Bitwise AND (Ref.L2038)
