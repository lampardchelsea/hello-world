/**
Refer to
https://leetcode.com/problems/tuple-with-same-product/
Given an array nums of distinct positive integers, return the number of tuples (a, b, c, d) such that a * b = c * d 
where a, b, c, and d are elements of nums, and a != b != c != d.

Example 1:
Input: nums = [2,3,4,6]
Output: 8
Explanation: There are 8 valid tuples:
(2,6,3,4) , (2,6,4,3) , (6,2,3,4) , (6,2,4,3)
(3,4,2,6) , (4,3,2,6) , (3,4,6,2) , (4,3,6,2)

Example 2:
Input: nums = [1,2,4,5,10]
Output: 16
Explanation: There are 16 valids tuples:
(1,10,2,5) , (1,10,5,2) , (10,1,2,5) , (10,1,5,2)
(2,5,1,10) , (2,5,10,1) , (5,2,1,10) , (5,2,10,1)
(2,10,4,5) , (2,10,5,4) , (10,2,4,5) , (10,2,4,5)
(4,5,2,10) , (4,5,10,2) , (5,4,2,10) , (5,4,10,2)

Example 3:
Input: nums = [2,3,4,6,8,12]
Output: 40

Example 4:
Input: nums = [2,3,5,7]
Output: 0

Constraints:
1 <= nums.length <= 1000
1 <= nums[i] <= 104
All elements in nums are distinct.
*/

// Solution 1: Freq map and find pair combinations
// Style 1:
// Refer to
// https://leetcode.com/problems/tuple-with-same-product/discuss/1020600/JAVA-Simple-Solution-with-HashMap-(7-lines)/821598
/**
public int tupleSameProduct(int[] nums) {
    int res = 0;
    Map<Integer, Integer> mulFreq = new HashMap<>();
    for (int i=0;i<nums.length;i++) {
        for (int j=i+1;j<nums.length;j++) {
            int prod = nums[i] * nums[j];
            int c = mulFreq.getOrDefault(prod, 0);
            res+=c; //Number of valid pair of pairs.
            mulFreq.put(prod,  c+ 1);
        }
    }
    return res*8; //Each tuple [a,b,c,d] can be arranged in 8 different ways matching the criteria.
}

Main idea :
For a valid tuple of length 4, [a,b,c,d] it can be arrange in 8 different ways.
Construct a frequency map, where
key is product of two distinct numbers in the array and
value is number of such pairs
If the number of such pairs is greater than one, those elements can be use to for a valid [a,b,c,d] tuple.
Increment the count, and in the final result multiply 8 becos each such count can have different ways.

Why * 8 on result ?
E.g
No of ways of arranging (a * b) = 2 {(a,b),(b,a)}
No of ways of arranging (c * d) = 2 {(c,d),(d,c)}
No of wasy of arranging (a * b) and (c * d) = 2 {(a,b)=(c,d), (c,d)=(a,b)}
Hence the total no of ways = 22*2 =8
*/
class Solution {
    public int tupleSameProduct(int[] nums) {
        int res = 0;
        Map<Integer, Integer> mulFreq = new HashMap<>();
        for (int i=0;i<nums.length;i++) {
            for (int j=i+1;j<nums.length;j++) {
                int prod = nums[i] * nums[j];
                int c = mulFreq.getOrDefault(prod, 0);
                res+=c; //Number of valid pair of pairs.
                mulFreq.put(prod,  c+ 1);
            }
        }
        return res*8; //Each tuple [a,b,c,d] can be arranged in 8 different ways matching the criteria.
    }
}

// Style 2:
/**
To avoid one kind of issue:

Input: nums = [2,3,4,6,8,12]
Expected Output: 40

the freq map as below:
{16=1, 32=1, 48=2, 96=1, 18=1, 36=1, 6=1, 8=1, 24=3, 72=1, 12=2}

if we only use freq >= 2 to judge, when 24 = 3 is the point, since we have 3 pair here. let's say pair a, pair b, pair c, 
the combination is 3 ways as (a,b) which means choose pair a and b together, same for (b,c) and (a,c), 
so we cannot easily use 3 - 1 = 2, the wrong way is directly minus 1 for all freq >= 2 as (2 - 1 + 3 - 1 + 2 - 1) * 8 = 32

So the actual pairs combination count should calculate as below:
48=2 --> 2 * (2 - 1) / 2 = 1
24=3 --> 3 * (3 - 1) / 2 = 3
12=2 --> 2 * (2 - 1) / 2 = 1
*/
class Solution {
    public int tupleSameProduct(int[] nums) {
        Map<Integer, Integer> freq = new HashMap<Integer, Integer>();
        for(int i = 0; i < nums.length; i++) {
            for(int j = i + 1; j < nums.length; j++) {
                int prod = nums[i] * nums[j];
                freq.put(prod, freq.getOrDefault(prod, 0) + 1);
            }
        }
        int count = 0;
        for(int n : freq.values()) {
            if(n >= 2) {
                count += 8 * n * (n - 1) / 2;
            }
        }
        return count;
    }
}
