https://leetcode.com/problems/next-greater-element-iii/description/
Given a positive integer n, find the smallest integer which has exactly the same digits existing in the integer n and is greater in value than n. If no such positive integer exists, return -1.
Note that the returned integer should fit in 32-bit integer, if there is a valid answer but it does not fit in 32-bit integer, return -1.
 
Example 1:
Input: n = 12
Output: 21

Example 2:
Input: n = 21
Output: -1
 
Constraints:
- 1 <= n <= 2^31 - 1
--------------------------------------------------------------------------------
Attempt 1: 2025-11-09
Solution 1: Two Pointers (30 min)
class Solution {
    public int nextGreaterElement(int n) {
        char[] digits = Integer.toString(n).toCharArray();
        int len = digits.length;
        // Step 1: Find the first digit that is smaller than its next digit (from right)
        int i = len - 2;
        while(i >= 0 && digits[i] >= digits[i + 1]) {
            i--;
        }
        // If no such digit found, the number is in descending order, no greater number
        if(i < 0) {
            return -1;
        }
        // Step 2: Find the smallest digit on right side of digits[i] that is greater than digits[i]
        int j = len - 1;
        while(j > i && digits[j] <= digits[i]) {
            j--;
        }
        // Step 3: Swap digits[i] and digits[j]
        swap(digits, i, j);
        // Step 4: Reverse the digits from i+1 to end to get the smallest possible number
        reverse(digits, i + 1, len - 1);
        // Step 5: Convert back to long to check for 32-bit integer overflow
        long result = Long.parseLong(new String(digits));
        return result > Integer.MAX_VALUE ? -1 : (int) result;
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

Refer to Deepseek
For LeetCode 556 "Next Greater Element III", this problem is about finding the next permutation of a number that fits in 32-bit integer range. Here's the Java solution:
🎯 Efficient Solution (Next Permutation Approach)
class Solution {
    public int nextGreaterElement(int n) {
        char[] digits = Integer.toString(n).toCharArray();
        int len = digits.length;
        
        // Step 1: Find the first digit that is smaller than its next digit (from right)
        int i = len - 2;
        while (i >= 0 && digits[i] >= digits[i + 1]) {
            i--;
        }
        
        // If no such digit found, the number is in descending order, no greater number
        if (i < 0) {
            return -1;
        }
        
        // Step 2: Find the smallest digit on right side of digits[i] that is greater than digits[i]
        int j = len - 1;
        while (j > i && digits[j] <= digits[i]) {
            j--;
        }
        
        // Step 3: Swap digits[i] and digits[j]
        swap(digits, i, j);
        
        // Step 4: Reverse the digits from i+1 to end to get the smallest possible number
        reverse(digits, i + 1, len - 1);
        
        // Step 5: Convert back to long to check for 32-bit integer overflow
        try {
            long result = Long.parseLong(new String(digits));
            return result > Integer.MAX_VALUE ? -1 : (int) result;
        } catch (NumberFormatException e) {
            return -1;
        }
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
Step-by-Step Example: n = 12443322
Step 1: Find the pivot
Digits: [1, 2, 4, 4, 3, 3, 2, 2]
         ↑  ↑  ↑  ↑  ↑  ↑  ↑  ↑
         0  1  2  3  4  5  6  7
         
i = 5 → digits[5]=3 >= digits[6]=2 ✓
i = 4 → digits[4]=3 >= digits[5]=3 ✓  
i = 3 → digits[3]=4 >= digits[4]=3 ✓
i = 2 → digits[2]=4 >= digits[3]=4 ✓
i = 1 → digits[1]=2 < digits[2]=4 ✗ → FOUND! i=1
Step 2: Find swap candidate
Find j where digits[j] > digits[1]=2
j=7 → digits[7]=2 <= 2 ✗
j=6 → digits[6]=2 <= 2 ✗
j=5 → digits[5]=3 > 2 ✓ → FOUND! j=5
Step 3: Swap
Swap digits[1] and digits[5]:
[1, 2, 4, 4, 3, 3, 2, 2] → [1, 3, 4, 4, 3, 2, 2, 2]

Step 4: Reverse suffix
Reverse from index 2 to 7:
[1, 3, 4, 4, 3, 2, 2, 2] → [1, 3, 2, 2, 3, 4, 4]
Result: 13223444
💡 Alternative Solution (Using Integer Array)
class Solution {
    public int nextGreaterElement(int n) {
        // Convert to array of digits
        List<Integer> digits = new ArrayList<>();
        int temp = n;
        while (temp > 0) {
            digits.add(0, temp % 10);
            temp /= 10;
        }
        // Apply next permutation algorithm
        int size = digits.size();
        int i = size - 2;
        while (i >= 0 && digits.get(i) >= digits.get(i + 1)) {
            i--;
        }
        if (i < 0) return -1;
        int j = size - 1;
        while (j > i && digits.get(j) <= digits.get(i)) {
            j--;
        }
        // Swap
        Collections.swap(digits, i, j);
        // Reverse suffix
        reverse(digits, i + 1, size - 1);
        // Convert back to number
        long result = 0;
        for (int digit : digits) {
            result = result * 10 + digit;
        }
        return result > Integer.MAX_VALUE ? -1 : (int) result;
    }
    
    private void reverse(List<Integer> list, int start, int end) {
        while (start < end) {
            Collections.swap(list, start, end);
            start++;
            end--;
        }
    }
}
🚨 Important Edge Cases
1.No greater number: 321 → -1
2.Single digit: 1 → -1
3.All same digits: 111 → -1
4.32-bit overflow: 1999999999 → -1 (result would be > 2³¹-1)
5.Simple case: 12 → 21
📊 Complexity Analysis
- Time Complexity: O(d) where d is number of digits
- Space Complexity: O(d) for storing digits
- Key Insight: This is essentially the "next permutation" algorithm applied to digits
🎯 Key Points
1.Find the pivot: Rightmost digit smaller than its next digit
2.Find swap candidate: Smallest digit on right that's larger than pivot
3.Swap and reverse: Swap pivot with candidate, then reverse the suffix
4.Overflow check: Use long to handle 32-bit integer boundaries
This solution efficiently finds the next greater number using the classic next permutation algorithm!

Refer to
L31.Next Permutation (Ref.L556)
L496.Next Greater Element I (Ref.L739)
L503.Next Greater Element II (Ref.L496)
L1842.Next Palindrome Using Same Digits (Ref.L31,L556)
