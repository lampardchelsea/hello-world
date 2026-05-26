https://leetcode.com/problems/add-two-integers/description/
Given two integers num1 and num2, return the sum of the two integers.
 
Example 1:
Input: num1 = 12, num2 = 5
Output: 17
Explanation: num1 is 12, num2 is 5, and their sum is 12 + 5 = 17, so 17 is returned.

Example 2:
Input: num1 = -10, num2 = 4
Output: -6
Explanation: num1 + num2 = -6, so -6 is returned.
 
Constraints:
- -100 <= num1, num2 <= 100
--------------------------------------------------------------------------------
Attempt 1: 2026-05-25
Solution 1: Math (10 min)
class Solution {
    public int sum(int num1, int num2) {
        return num1 + num2;
    }
}

Time Complexity: O(1)
Space Complexity: O(1)

Solution 2: Math (30 min)
class Solution {
    public int sum(int num1, int num2) {
        boolean negative = false;
        // Both non‑negative
        if(num1 >= 0 && num2 >= 0) {
            return addAbs(num1, num2, false);
        }
        // Both negative
        if(num1 < 0 && num2 < 0) {
            return addAbs(-num1, -num2, true);
        }
        int absNum1 = Math.abs(num1);
        int absNum2 = Math.abs(num2);
        if(absNum1 > absNum2) {
            // Result sign = sign of num1
            return subtractAbs(absNum1, absNum2, num1 < 0);
        } else if(absNum1 < absNum2) {
            // Result sign = sign of num2
            return subtractAbs(absNum2, absNum1, num2 < 0);
        } else {
            // Equal absolute values → result is 0
            return 0;
        }
    }

    private int addAbs(int a, int b, boolean neg) {
        int result = 0;
        int carry = 0;
        int place = 1; // 1, 10, 100...etc.
        while(a > 0 || b > 0 || carry > 0) {
            int digitalA = a % 10;
            int digitalB = b % 10;
            int sum = digitalA + digitalB + carry;
            int newDigital = sum % 10;
            carry = sum / 10;
            result += newDigital * place;
            place *= 10;
            a /= 10;
            b /= 10;
        }
        return neg ? -result : result;
    }

    private int subtractAbs(int a, int b, boolean neg) {
        int result = 0;
        int borrow = 0;
        int place = 1; // 1, 10, 100...etc.
        while(a > 0 || b > 0) {
            int digitalA = a % 10 - borrow;
            int digitalB = b % 10;
            if(digitalA < digitalB) {
                digitalA += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            int newDigital = digitalA - digitalB;
            result += newDigital * place;
            place *= 10;
            a /= 10;
            b /= 10;
        }
        return neg ? -result : result;
    }
}

Refer to Deepseek
Q1: Can you simulate the human way like add digital from least significant bit to most significant bit ?
Sure! Here's a Java solution that simulates human digit‑by‑digit addition for any integers (including negatives). It handles signs separately and adds the absolute values digit by digit, then applies the correct sign.
class Solution {
    public int sum(int num1, int num2) {
        // Base case: if both are non-negative or both negative, we add absolute values
        // If signs differ, we subtract the smaller absolute from the larger.
        
        // 1. Determine sign of result
        boolean resultNegative;
        int a = num1, b = num2;
        
        if (a >= 0 && b >= 0) {
            resultNegative = false;
            // Both positive → add absolute values (already positive)
        } else if (a < 0 && b < 0) {
            resultNegative = true;
            // Both negative → add absolute values, then make negative
            a = -a;
            b = -b;
        } else {
            // One positive, one negative
            // We need to subtract the smaller absolute from the larger
            int absA = Math.abs(a);
            int absB = Math.abs(b);
            if (absA > absB) {
                resultNegative = (a < 0); // sign of the larger absolute value
                a = absA;
                b = absB;
            } else if (absA < absB) {
                resultNegative = (b < 0);
                a = absB;
                b = absA;
            } else {
                // Absolute values equal → result is 0
                return 0;
            }
            // Now a is the larger absolute value, b is the smaller
            // We will compute a - b (digit-wise subtraction)
            return digitSubtract(a, b, resultNegative);
        }
        
        // 2. Both numbers have same sign (or both positive after abs conversion)
        // Perform digit-wise addition on a and b (both >= 0)
        return digitAdd(a, b, resultNegative);
    }
    
    private int digitAdd(int a, int b, boolean negative) {
        String s1 = Integer.toString(a);
        String s2 = Integer.toString(b);
        int maxLen = Math.max(s1.length(), s2.length());
        // Pad with leading zeros
        while (s1.length() < maxLen) s1 = "0" + s1;
        while (s2.length() < maxLen) s2 = "0" + s2;
        
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        for (int i = maxLen - 1; i >= 0; i--) {
            int d1 = s1.charAt(i) - '0';
            int d2 = s2.charAt(i) - '0';
            int sum = d1 + d2 + carry;
            sb.append(sum % 10);
            carry = sum / 10;
        }
        if (carry > 0) sb.append(carry);
        
        int result = Integer.parseInt(sb.reverse().toString());
        return negative ? -result : result;
    }
    
    private int digitSubtract(int larger, int smaller, boolean negative) {
        // larger and smaller are absolute values, larger >= smaller
        String s1 = Integer.toString(larger);
        String s2 = Integer.toString(smaller);
        int maxLen = Math.max(s1.length(), s2.length());
        while (s1.length() < maxLen) s1 = "0" + s1;
        while (s2.length() < maxLen) s2 = "0" + s2;
        
        StringBuilder sb = new StringBuilder();
        int borrow = 0;
        for (int i = maxLen - 1; i >= 0; i--) {
            int d1 = s1.charAt(i) - '0' - borrow;
            int d2 = s2.charAt(i) - '0';
            if (d1 < d2) {
                d1 += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            sb.append(d1 - d2);
        }
        // Remove leading zeros
        String resultStr = sb.reverse().toString().replaceFirst("^0+(?!$)", "");
        int result = resultStr.isEmpty() ? 0 : Integer.parseInt(resultStr);
        return negative ? -result : result;
    }
}
How it works step by step (example with negatives)
Example 1: num1 = 123, num2 = -45
- One positive, one negative.
- Absolute values: 123 and 45. Larger = 123, smaller = 45.
- Result sign = sign of larger = positive (since 123 > 45).
- Perform subtraction 123 - 45 digit‑wise (with borrow):
- Units: 3 - 5 → borrow, 13-5=8, borrow=1
- Tens: 2 - 1(borrow) = 1, then 1 - 4? No, borrow again: 11-4=7, borrow=1
- Hundreds: 1 - 1(borrow) = 0 → result "78"
- Final positive → 78.
Example 2: num1 = -123, num2 = 45
- Larger absolute = 123, smaller = 45, result negative (since negative number's absolute is larger).
- Compute 123 - 45 = 78, apply negative → -78.
Example 3: num1 = -123, num2 = -45
- Both negative: add absolute values 123+45 = 168, result negative → -168.
This matches human pencil‑and‑paper arithmetic.

Q2: Why you have to transfer into String and then process, is that possible to just keep the int way ?
You're right — converting to strings is convenient for digit extraction, but it's not necessary. You can absolutely do digit‑by‑digit addition using integer arithmetic (% 10 and / 10). This is more efficient and stays entirely within the int domain.
Here's a solution that simulates human column addition from least significant digit to most significant, handling negative numbers correctly, without using strings:
class Solution {
    public int sum(int num1, int num2) {
        // Handle the sign and use absolute values for digit operations
        int a = num1, b = num2;
        
        // Both non‑negative
        if (a >= 0 && b >= 0) {
            return addAbs(a, b, false);
        }
        // Both negative
        if (a < 0 && b < 0) {
            return addAbs(-a, -b, true);
        }
        // One positive, one negative
        int absA = Math.abs(a);
        int absB = Math.abs(b);
        if (absA > absB) {
            // Result sign = sign of a
            return subAbs(absA, absB, a < 0);
        } else if (absA < absB) {
            // Result sign = sign of b
            return subAbs(absB, absA, b < 0);
        } else {
            // Equal absolute values → result is 0
            return 0;
        }
    }
    
    // Add two positive integers digit by digit (like human addition)
    private int addAbs(int x, int y, boolean negativeResult) {
        int result = 0;
        int carry = 0;
        int place = 1; // 1, 10, 100, ...
        
        while (x > 0 || y > 0 || carry > 0) {
            int digitX = x % 10;
            int digitY = y % 10;
            int sum = digitX + digitY + carry;
            int newDigit = sum % 10;
            carry = sum / 10;
            
            result += newDigit * place;
            place *= 10;
            
            x /= 10;
            y /= 10;
        }
        
        return negativeResult ? -result : result;
    }
    
    // Subtract smaller from larger (both positive, larger >= smaller) digit by digit
    private int subAbs(int larger, int smaller, boolean negativeResult) {
        int result = 0;
        int borrow = 0;
        int place = 1;
        
        while (larger > 0 || smaller > 0) {
            int digitL = larger % 10 - borrow;
            int digitS = smaller % 10;
            
            if (digitL < digitS) {
                digitL += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            
            int newDigit = digitL - digitS;
            result += newDigit * place;
            place *= 10;
            
            larger /= 10;
            smaller /= 10;
        }
        
        // Remove any leading zeros (e.g., 100 - 99 = 001 → 1)
        // Since place value accumulates correctly, just return as is.
        // But if result is 0, return 0.
        return negativeResult ? -result : result;
    }
}
Why convert to strings in the first place?
- Readability – Extracting digits via charAt() is very clear, especially for beginners.
- No need to track place value – Building the result by appending to a StringBuilder and then reversing is straightforward.
- Avoids integer overflow of intermediate result – When using place‑value multiplication, result itself could overflow if the number has many digits (e.g., adding two large ints still fits in an int, but the temporary result might exceed int range during construction? Actually no, because the final value fits in an int, but intermediate steps could temporarily hold a number with more digits? Example: 999 + 999 = 1998, building 11 + 910 + 9100 + 81000? That's 1 + 90 + 900 + 8000 = 8991, which is not the correct result. Wait, careful.)
Actually, the place‑value method shown above works correctly only if you build from least significant digit and multiply by the correct place factor each time. For 999 + 999:
- units: 9+9=18 → digit 8, carry 1, place=1 → result += 8*1 = 8
- tens: 9+9+carry1=19 → digit 9, carry 1, place=10 → result += 9*10 = 90 → result=98
- hundreds: 9+9+carry1=19 → digit 9, carry 1, place=100 → result += 9*100 = 900 → result=998
- thousands: carry 1, place=1000 → result += 1*1000 = 1998 → correct. So it works.
But still, the string approach is simpler and less error‑prone for most programmers.
Conclusion
- Yes, you can keep it in the int world – just use % 10 and / 10 in a loop.
- Strings are used for clarity, not necessity.
- The integer‑only method is faster (no parsing overhead) but slightly more verbose.
The choice depends on whether you value readability (strings) or purity/efficiency (integer arithmetic). For LeetCode 2235, the one‑line return num1 + num2 is obviously the real answer — but this discussion is about simulating the human algorithm.


Refer to

