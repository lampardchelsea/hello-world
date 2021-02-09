/**
Refer to
https://leetcode.com/problems/count-triplets-that-can-form-two-arrays-of-equal-xor/
Given an array of integers arr.

We want to select three indices i, j and k where (0 <= i < j <= k < arr.length).

Let's define a and b as follows:

a = arr[i] ^ arr[i + 1] ^ ... ^ arr[j - 1]
b = arr[j] ^ arr[j + 1] ^ ... ^ arr[k]
Note that ^ denotes the bitwise-xor operation.

Return the number of triplets (i, j and k) Where a == b.

Example 1:
Input: arr = [2,3,1,6,7]
Output: 4
Explanation: The triplets are (0,1,2), (0,2,2), (2,3,4) and (2,4,4)

Example 2:
Input: arr = [1,1,1,1,1]
Output: 10

Example 3:
Input: arr = [2,3]
Output: 0

Example 4:
Input: arr = [1,3,5,7,9]
Output: 3

Example 5:
Input: arr = [7,11,12,9,5,2,7,17,22]
Output: 8

Constraints:
1 <= arr.length <= 300
1 <= arr[i] <= 10^8
*/

// Solution 1: Use the Trick of XOR operation!!!
// Refer to
// https://leetcode.com/problems/count-triplets-that-can-form-two-arrays-of-equal-xor/discuss/623754/Java-Use-the-Trick-of-XOR-operation!!!
// https://leetcode.com/problems/count-triplets-that-can-form-two-arrays-of-equal-xor/discuss/623754/Java-Use-the-Trick-of-XOR-operation!!!/535680
// https://leetcode.com/problems/count-triplets-that-can-form-two-arrays-of-equal-xor/discuss/623754/Java-Use-the-Trick-of-XOR-operation!!!/738916
/**
Give the observation: For all pairs of i and k, where arr[i] ^ arr[i + 1] ^ ... ^ arr[k] = 0, 
then any j (i < j <= k) will be good to set as the answer (hint: proof by contradiction, if 
you cannot understand this trick immediately). 
So you just need to find all segments where XOR equals zero.

For the proof, just think about if a ==b then a ^ b = 0.
explaination
a==b
xor b to the both side
a^b=b^b
a^b=0
xor(a[i...k])=0 --> This is what we are going to use

    public int countTriplets(int[] arr) {
        int ans = 0;
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            int xor = arr[i];
            for (int j = i + 1; j < length; j++) {
                xor ^= arr[j];
                if (xor == 0) {
                    ans += (j - i);
                }
            }
        }
        return ans;
    }
Time complexity: O(N^2)
Space complexity: O(1)
*/
class Solution {
    public int countTriplets(int[] arr) {
        int count = 0;
        for(int i = 0; i < arr.length; i++) {
            int xor = arr[i];
            for(int k = i + 1; k < arr.length; k++) {
                xor ^= arr[k];
                if(xor == 0) {
                    count += k - i; // all j between i to k match require
                }
            }
        }
        return count;
    }
}

