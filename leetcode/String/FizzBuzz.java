/**
 * Refer to
 * https://leetcode.com/problems/fizz-buzz/description/
   Write a program that outputs the string representation of numbers from 1 to n.

    But for multiples of three it should output “Fizz” instead of the number and 
    for the multiples of five output “Buzz”. For numbers which are multiples of 
    both three and five output “FizzBuzz”.

    Example:
    n = 15,
    
    Return:
    [
        "1",
        "2",
        "Fizz",
        "4",
        "Buzz",
        "Fizz",
        "7",
        "8",
        "Fizz",
        "Buzz",
        "11",
        "Fizz",
        "13",
        "14",
        "FizzBuzz"
    ]
    
 * Solution
 * https://leetcode.com/problems/fizz-buzz/discuss/89931/Java-4ms-solution-Not-using-%22%22-operation
*/
class Solution {
    public List<String> fizzBuzz(int n) {
        List<String> result = new ArrayList<String>();
        for(int i = 1; i <= n; i++) {
            if(i % 3 == 0 && i % 5 == 0) {
                result.add("FizzBuzz");
            } else if(i % 3 == 0) {
                result.add("Fizz");
            } else if(i % 5 == 0) {
                result.add("Buzz");
            } else {
                result.add("" + i);
            }
        }
        return result;
    }
}
















































https://leetcode.com/problems/fizz-buzz/description/
Given an integer n, return a string array answer (1-indexed) where:
- answer[i] == "FizzBuzz" if i is divisible by 3 and 5.
- answer[i] == "Fizz" if i is divisible by 3.
- answer[i] == "Buzz" if i is divisible by 5.
- answer[i] == i (as a string) if none of the above conditions are true.

Example 1:
Input: n = 3
Output: ["1","2","Fizz"]

Example 2:
Input: n = 5
Output: ["1","2","Fizz","4","Buzz"]

Example 3:
Input: n = 15
Output: ["1","2","Fizz","4","Buzz","Fizz","7","8","Fizz","Buzz","11","Fizz","13","14","FizzBuzz"]
 
Constraints:
- 1 <= n <= 10^4
--------------------------------------------------------------------------------
Attempt 1: 2024-05-29
Solution 1: Simple if - else (10 min)
class Solution {
    public List<String> fizzBuzz(int n) {
        List<String> result = new ArrayList<>();
        for(int i = 1; i <= n; i++) {
            if(i % 15 == 0) {
                result.add("FizzBuzz");
            } else if(i % 3 == 0) {
                result.add("Fizz");
            } else if(i % 5 == 0) {
                result.add("Buzz");
            } else {
                result.add(String.valueOf(i));
            }
        }
        return result;
    }
}

Time Complexity: O(N)
Space Complexity: O(N)

Solution 2: No % (10 min)
class Solution {
    public List<String> fizzBuzz(int n) {
        List<String> result = new ArrayList<>();
        int i = 1;
        int fizz = 0;
        int buzz = 0;
        while(i <= n) {
            fizz++;
            buzz++;
            if(fizz == 3 && buzz == 5) {
                result.add("FizzBuzz");
                fizz = 0;
                buzz = 0;
            } else if(fizz == 3) {
                result.add("Fizz");
                fizz = 0;
            } else if(buzz == 5) {
                result.add("Buzz");
                buzz = 0;
            } else {
                result.add(String.valueOf(i));
            }
            i++;
        }
        return result;
    }
}

Time Complexity: O(N)
Space Complexity: O(N)

Solution 3: Ternery Operator (10 min)
class Solution {
    public List<String> fizzBuzz(int n) {
        List<String> result = new ArrayList<>();
        for(int i = 1; i <= n; i++) {
            result.add(
                i % 15 == 0 ? "FizzBuzz" : 
                i % 5 == 0 ? "Buzz" : 
                i % 3 == 0 ? "Fizz" : 
                String.valueOf(i));
        }
        return result;
    }
}

Time Complexity: O(N)
Space Complexity: O(N)

Refer to
https://leetcode.com/problems/fizz-buzz/solutions/2628136/java-3-approaches-easy/
