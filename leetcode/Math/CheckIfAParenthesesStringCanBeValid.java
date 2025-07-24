https://leetcode.com/problems/check-if-a-parentheses-string-can-be-valid/description/
A parentheses string is a non-empty string consisting only of '(' and ')'. It is valid if any of the following conditions is true:
- It is ().
- It can be written as AB (A concatenated with B), where A and B are valid parentheses strings.
- It can be written as (A), where A is a valid parentheses string.
You are given a parentheses string s and a string locked, both of length n. locked is a binary string consisting only of '0's and '1's. For each index i of locked,
- If locked[i] is '1', you cannot change s[i].
- But if locked[i] is '0', you can change s[i] to either '(' or ')'.
Return true if you can make s a valid parentheses string. Otherwise, return false.
 
Example 1:

Input: s = "))()))", locked = "010100"
Output: true
Explanation: locked[1] == '1' and locked[3] == '1', so we cannot change s[1] or s[3].
We change s[0] and s[4] to '(' while leaving s[2] and s[5] unchanged to make s valid.

Example 2:
Input: s = "()()", locked = "0000"
Output: true
Explanation: We do not need to make any changes because s is already valid.

Example 3:
Input: s = ")", locked = "0"
Output: false
Explanation: locked permits us to change s[0]. Changing s[0] to either '(' or ')' will not make s valid.

Example 4:
Input: s = "(((())(((())", locked = "111111010111"
Output: true
Explanation: locked permits us to change s[6] and s[8]. We change s[6] and s[8] to ')' to make s valid.
 
Constraints:
- n == s.length == locked.length
- 1 <= n <= 105
- s[i] is either '(' or ')'.
- locked[i] is either '0' or '1'
--------------------------------------------------------------------------------
Attempt 1: 2025-07-22
Solution 1: Greedy + Math (60 min)
class Solution {
    public boolean canBeValid(String s, String locked) {
        int len = s.length();
        if(len % 2 != 0) {
            return false;
        }
        // Statement:
        // If at any point while traversing left-to-right, the number of ')' 
        // exceeds the number of '(', the string is invalid.
        // Proof:
        // - Let balance = count('(') - count(')').
        // - At any point, if balance < 0, it means there are more ')' than '('.
        // - In a valid string, every ')' must be preceded by a corresponding '('.
        // - If balance < 0, there exists a ')' without a matching '(' before it, 
        //   making the string invalid.
        int balance = 0;
        for(int i = 0; i < s.length(); i++) {
            if(locked.charAt(i) == '0' || s.charAt(i) == '(') {
                balance++;
            } else {
                balance--;
            }
            if(balance < 0) {
                return false;
            }
        }
        balance = 0;
        for(int i = s.length() - 1; i >= 0; i--) {
            if(locked.charAt(i) == '0' || s.charAt(i) == ')') {
                balance++;
            } else {
                balance--;
            }
            if(balance < 0) {
                return false;
            }
        }
        return true;
    }
}

Time Complexity: O(n)
Space Complexity: O(1)

Refer to Deepseek
Approach
The solution involves two passes over the string to ensure it can be balanced with the given constraints:
1.Initial Check: If the string length is odd, it's impossible to balance parentheses.
2.Left-to-Right Pass: Treat every unlocked character as '(' to prevent negative balance. If the balance becomes negative at any point, return false.
3.Right-to-Left Pass: Treat every unlocked character as ')' to prevent negative balance. If the balance becomes negative at any point, return false.
4.Final Check: If both passes complete without negative balances, return true.
Solution Code
class Solution {
    public boolean canBeValid(String s, String locked) {
        int n = s.length();
        if (n % 2 != 0) return false;
        int balance = 0;
        for (int i = 0; i < n; i++) {
            if (locked.charAt(i) == '0' || s.charAt(i) == '(') {
                balance++;
            } else {
                balance--;
            }
            if (balance < 0) return false;
        }
        balance = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (locked.charAt(i) == '0' || s.charAt(i) == ')') {
                balance++;
            } else {
                balance--;
            }
            if (balance < 0) return false;
        }
        return true;
    }
}
Explanation
1.Initial Check: The length of the string must be even; otherwise, it's impossible to form valid parentheses.
2.Left-to-Right Pass:
- Treat each character as '(' if it's unlocked or if it's a locked '('.
- For locked ')', treat as closing.
- Maintain a running balance. If the balance becomes negative, it means there are too many closing parentheses that cannot be compensated by unlocked characters, making the string invalid.
3.Right-to-Left Pass:
- Treat each character as ')' if it's unlocked or if it's a locked ')'.
- For locked '(', treat as opening.
- Maintain a running balance. If the balance becomes negative, it means there are too many opening parentheses that cannot be compensated by unlocked characters, making the string invalid.
4.Result: If both passes complete without negative balances, the string can be balanced by appropriately flipping unlocked characters.
This approach efficiently checks the feasibility of forming a valid parentheses string in O(n) time with O(1) space complexity.

But can you prove below:
So that every '(' bracket has it counterpart ')'.
String can be said invalid in terms of '(' , if number of ')' > number of '(' , at any point. And vice versa in terms of ')' as well.
Intuition :
- Idea :
- Idea is similar to other Balance Parentheses related problems. i.e we check the balance of ( and ) brackets.
- And whenever number of ) exceeds number of ( , we can say that it is unbalanced from start.
- And whenever number of ( exceeds number of ) , we can say that it is unbalanced from end ( this one is trivial).
- This can be done by incrementing when we see opening ( bracket and decrementing vice versa.
- Observations :
- We can get a valid Balanced Parentheses only when length of string is even.
- So that every ( bracket has it counterpart ).
- String can be said invalid in terms of ( , if
- number of ) > number of ( , at any point.
- and vice versa in terms of ) as well.
- Also in this problem, we are given that we can change bracket at index i
- iff locked[i] == '0' , i.e index i is unlocked
- Thus, we can say if at point we see balance < 0, but that index is unlocked,
- then we change character at that index, thus maintaining the balance.
- So we need to check if string is balanced in terms of opening '(' bracket from left -> right.
- As well as check if string is balanced in terms of closing ')' bracket from right -> left.
- If at any point we encounter negative balance i.e balance < 0 , simple return false as it is surely invalid.
Proof of the Observations in Parentheses Validation
1. Valid Parentheses Must Have Even Length
Statement:
A valid parentheses string must have an even length because every opening bracket '(' must have a corresponding closing bracket ')'.
Proof:
- Let n be the length of the string.
- For every '(', there must be exactly one ')'.
- Thus, the total number of brackets is 2k (where k is the number of pairs).
- Therefore, n must be even.
Conclusion:
If n is odd, the string cannot be balanced, and we immediately return false.
--------------------------------------------------------------------------------
2. Invalid if ')' > '(' at Any Point (Left-to-Right)
Statement:
If at any point while traversing left-to-right, the number of ')' exceeds the number of '(', the string is invalid.
Proof:
- Let balance = count('(') - count(')').
- At any point, if balance < 0, it means there are more ')' than '('.
- In a valid string, every ')' must be preceded by a corresponding '('.
- If balance < 0, there exists a ')' without a matching '(' before it, making the string invalid.
Example:
- String: "))(("
- At index 1, balance = -2 (invalid).
- This string can never be balanced because the first two ')' have no '(' before them.
Handling Unlocked Brackets:
- If a ')' causes balance < 0, but its position is unlocked (locked[i] = '0'), we can flip it to '(' to restore balance ≥ 0.
- Thus, we treat unlocked ')' as potential '(' to prevent balance < 0.
--------------------------------------------------------------------------------
3. Invalid if '(' > ')' at Any Point (Right-to-Left)
Statement:
If at any point while traversing right-to-left, the number of '(' exceeds the number of ')', the string is invalid.
Proof:
- Let balance = count(')') - count('(').
- At any point, if balance < 0, it means there are more '(' than ')'.
- In a valid string, every '(' must be followed by a corresponding ')'.
- If balance < 0, there exists a '(' without a matching ')' after it, making the string invalid.
Example:
- String: "(()))"
- At index 1 (right-to-left), balance = -1 (invalid).
- The last '(' has no matching ')' after it.
Handling Unlocked Brackets:
- If a '(' causes balance < 0, but its position is unlocked (locked[i] = '0'), we can flip it to ')' to restore balance ≥ 0.
- Thus, we treat unlocked '(' as potential ')' to prevent balance < 0.
--------------------------------------------------------------------------------
Why Two Passes Are Necessary
1.Left-to-Right Pass:
- Ensures no ')' appears before its matching '('.
- Unlocked ')' can be flipped to '(' to maintain balance ≥ 0.
2.Right-to-Left Pass:
- Ensures no '(' appears after its matching ')'.
- Unlocked '(' can be flipped to ')' to maintain balance ≥ 0.
Combined Guarantee:
- If both passes succeed, the string can be balanced by appropriately flipping unlocked brackets.
- If either pass fails, the string is impossible to balance.
--------------------------------------------------------------------------------
Formal Proof of Correctness
Theorem:
A string s can be balanced with unlocked brackets if and only if:
1.Its length is even.
2.The left-to-right pass ensures balance ≥ 0 (treating unlocked ')' as '(').
3.The right-to-left pass ensures balance ≥ 0 (treating unlocked '(' as ')').
Proof:
- Necessity: If the string is balanced, conditions (1), (2), and (3) must hold.
- Sufficiency: If conditions (1), (2), and (3) hold, we can construct a valid string by flipping unlocked brackets as needed during traversal.
Example:
- s = "())()(", locked = "010110"
- Left-to-right: Treat unlocked ')' at index 1 as '(' → balance ≥ 0.
- Right-to-left: Treat unlocked '(' at index 4 as ')' → balance ≥ 0.
- Final balanced string: "(())()".
--------------------------------------------------------------------------------
Key Takeaways
1.Even Length: Immediate check for impossibility.
2.Left-to-Right: Ensures no excess ')' (handled by flipping unlocked ')').
3.Right-to-Left: Ensures no excess '(' (handled by flipping unlocked '(').
4.Unlocked Brackets: Act as wildcards to restore balance when needed.
This approach guarantees correctness with O(n) time and O(1) space complexity. 🚀



Refer to
L301.Remove Invalid Parentheses (Ref.L22)
L1963.Minimum Number of Swaps to Make the String Balanced (Ref.L301,L2116)
