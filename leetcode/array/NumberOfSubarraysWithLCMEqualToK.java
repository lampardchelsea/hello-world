https://leetcode.com/problems/number-of-subarrays-with-lcm-equal-to-k/description/
Given an integer array nums and an integer k, return the number of subarrays of nums where the least common multiple of the subarray's elements is k.
A subarray is a contiguous non-empty sequence of elements within an array.
The least common multiple of an array is the smallest positive integer that is divisible by all the array elements.
 
Example 1:
Input: nums = [3,6,2,7,1], k = 6
Output: 4
Explanation: The subarrays of nums where 6 is the least common multiple of all the subarray's elements are:
- [3,6,2,7,1]
- [3,6,2,7,1]
- [3,6,2,7,1]
- [3,6,2,7,1]

Example 2:
Input: nums = [3], k = 2
Output: 0
Explanation: There are no subarrays of nums where 2 is the least common multiple of all the subarray's elements.
 
Constraints:
- 1 <= nums.length <= 1000
- 1 <= nums[i], k <= 1000
--------------------------------------------------------------------------------
Attempt 1: 2024-01-27
Solution 1: Brute Force (10 min)
The question refer to L2447.Number of Subarrays With GCD Equal to K
class Solution {
    public int subarrayLCM(int[] nums, int k) {
        int count = 0;
        int n = nums.length;
        for(int i = 0; i < n; i++) {
            int lcmVal = 1;
            for(int j = i; j < n && k % nums[j] == 0; j++) {
                lcmVal = lcm(lcmVal, nums[j]);
                if(lcmVal == k) {
                    count++;
                }
            }
        }
        return count;
    }

    private int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    private int gcd(int a, int b) {
        if(b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}

Time Complexity: O(N^2)
Space Complexity: O(N)

What is Least Common Multiple (LCM) ?
Answer from chatGPT
The least common multiple (LCM) of two or more integers is the smallest positive integer that is divisible by each of the given integers without leaving a remainder. In other words, it is the smallest common multiple that all the numbers share.
For example, consider the numbers 4 and 5:
Multiples of 4: 4, 8, 12, 16, 20, ...
Multiples of 5: 5, 10, 15, 20, 25, ...
In this case, the least common multiple (LCM) of 4 and 5 is 20, as it is the smallest positive integer that is evenly divisible by both 4 and 5.
The LCM is often denoted by "lcm(a, b)" for two numbers a and b, and it can be extended to more than two numbers, for example, lcm(a, b, c).
The relationship between the greatest common divisor (GCD) and the LCM is given by the formula:
LCM(a, b) × GCD(a, b)=∣a × b∣
This formula shows that the product of the LCM and GCD of two numbers is equal to the absolute value of the product of the two numbers.

Refer to
https://leetcode.com/problems/number-of-subarrays-with-lcm-equal-to-k/solutions/2808830/simple-java-solution/
class Solution {
    public int subarrayLCM(int[] nums, int k) {
        int a=0,n=nums.length;
        for(int i=0; i<n; i++)
        {
            int c=1;
            for(int j=i;j<n;j++)
            {
                c=task(c, nums[j]);
                a+=(c==k)?1:0;
            }
        }
        return a;
    }
    public int gcd(int a, int b)
    {
        if(b==0)
            return a;
        return gcd(b,a%b);
    }
    public int task(int a, int b)
    {
        return (a*b)/gcd(a,b);
    }
}
