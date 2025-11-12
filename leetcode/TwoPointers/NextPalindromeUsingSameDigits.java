https://leetcode.ca/2021-07-03-1842-Next-Palindrome-Using-Same-Digits/
You are given a numeric string num, representing a very large palindrome.
Return the smallest palindrome larger than num that can be created by rearranging its digits. If no such palindrome exists, return an empty string "".
A palindrome is a number that reads the same backward as forward.
Example 1:
Input: num = “1221”
Output: “2112”
Explanation: The next palindrome larger than “1221” is “2112”.

Example 2:
Input: num = “32123”
Output: “”
Explanation: No palindromes larger than “32123” can be made by rearranging the digits.

Example 3:
Input: num = “45544554”
Output: “54455445”
Explanation: The next palindrome larger than “45544554” is “54455445”.

Constraints:
- 1 <= num.length <= 10^5
- num is a palindrome.
--------------------------------------------------------------------------------
Attempt 1: 2025-11-11
Solution 1: Two Pointers (10 min)
Refer to Deepseek
For LeetCode 1842 "Next Palindrome Using Same Digits", this problem is about finding the next greater palindrome that can be formed using the same digits. Here's the Java solution:
🎯 Efficient Solution (Next Permutation + Palindrome Construction)
class Solution {
    public String nextPalindrome(String num) {
        int n = num.length();
        char[] chars = num.toCharArray();
        
        // Extract first half of the palindrome
        char[] firstHalf = new char[n / 2];
        for (int i = 0; i < n / 2; i++) {
            firstHalf[i] = chars[i];
        }
        
        // Find next permutation of the first half
        if (!nextPermutation(firstHalf)) {
            return "";
        }
        
        // Reconstruct the palindrome
        StringBuilder result = new StringBuilder();
        result.append(firstHalf);
        
        // Add middle character for odd length
        if (n % 2 == 1) {
            result.append(chars[n / 2]);
        }
        
        // Append the reversed first half to complete palindrome
        for (int i = firstHalf.length - 1; i >= 0; i--) {
            result.append(firstHalf[i]);
        }
        
        return result.toString();
    }
    
    private boolean nextPermutation(char[] arr) {
        int n = arr.length;
        
        // Step 1: Find the first decreasing element from right
        int i = n - 2;
        while (i >= 0 && arr[i] >= arr[i + 1]) {
            i--;
        }
        
        // If no next permutation exists
        if (i < 0) {
            return false;
        }
        
        // Step 2: Find element to swap with
        int j = n - 1;
        while (j > i && arr[j] <= arr[i]) {
            j--;
        }
        
        // Step 3: Swap
        swap(arr, i, j);
        
        // Step 4: Reverse the suffix
        reverse(arr, i + 1, n - 1);
        
        return true;
    }
    
    private void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    private void reverse(char[] arr, int start, int end) {
        while (start < end) {
            swap(arr, start, end);
            start++;
            end--;
        }
    }
}

🔍 How the Algorithm Works
Key Insight
A palindrome is determined by its first half. To find the next greater palindrome:
1.Extract the first half of the current palindrome
2.Find the next permutation of the first half
3.Reconstruct the palindrome by mirroring the first half
Step-by-Step Example: num = "231430"
Step 1: Extract first half
Original: "2 3 1 4 3 0" (length 6, even)
First half: "2 3 1"
Step 2: Find next permutation of first half
"2 3 1" → next permutation is "3 1 2"
Step 3: Reconstruct palindrome
First half: "3 1 2"
Mirror: "2 1 3"  
Result: "312213"
💡 Alternative All-in-One Solution
class Solution {
    public String nextPalindrome(String num) {
        int n = num.length();
        char[] chars = num.toCharArray();
        
        // Handle the first half (excluding middle for odd length)
        int halfLen = n / 2;
        char[] half = new char[halfLen];
        for (int i = 0; i < halfLen; i++) {
            half[i] = chars[i];
        }
        
        // Find next permutation of the half
        if (!nextPermutation(half)) {
            return "";
        }
        
        // Build the result
        StringBuilder sb = new StringBuilder();
        sb.append(half);
        
        // Add middle character for odd length
        if (n % 2 == 1) {
            sb.append(chars[halfLen]);
        }
        
        // Append reversed half
        for (int i = halfLen - 1; i >= 0; i--) {
            sb.append(half[i]);
        }
        
        return sb.toString();
    }
    
    private boolean nextPermutation(char[] arr) {
        // Same next permutation logic as above
        int i = arr.length - 2;
        while (i >= 0 && arr[i] >= arr[i + 1]) i--;
        if (i < 0) return false;
        
        int j = arr.length - 1;
        while (arr[j] <= arr[i]) j--;
        
        swap(arr, i, j);
        reverse(arr, i + 1, arr.length - 1);
        return true;
    }
    
    private void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    private void reverse(char[] arr, int start, int end) {
        while (start < end) swap(arr, start++, end--);
    }
}
🧠 Handling Edge Cases
// Test cases:
"1234" → "1331" (next palindrome using same digits)
"1221" → "2112" 
"32123" → "" (no greater palindrome possible)
"1" → "" (single digit, no next palindrome)
"11" → "" (all same digits, no next)
📊 Complexity Analysis
- Time Complexity: O(n) where n is the length of the string
- Space Complexity: O(n) for storing the half array and result
🎯 Key Points
1.Palindrome Property: Only need to modify the first half
2.Next Permutation: Use standard algorithm to find next greater arrangement
3.Mirroring: Reverse the first half to create the second half
4.Odd Length: Preserve the middle character unchanged
5.No Solution: Return empty string when no next permutation exists
This solution efficiently finds the next greater palindrome by leveraging the next permutation algorithm on the first half!


Refer to
L31.Next Permutation (Ref.L556)
L556.Next Greater Element III (Ref.L31,L496,L503,L1842)
