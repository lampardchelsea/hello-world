/**
Refer to
https://leetcode.com/problems/check-if-array-pairs-are-divisible-by-k/
Given an array of integers arr of even length n and an integer k.

We want to divide the array into exactly n / 2 pairs such that the sum of each pair is divisible by k.

Return True If you can find a way to do that or False otherwise.

Example 1:
Input: arr = [1,2,3,4,5,10,6,7,8,9], k = 5
Output: true
Explanation: Pairs are (1,9),(2,8),(3,7),(4,6) and (5,10).

Example 2:
Input: arr = [1,2,3,4,5,6], k = 7
Output: true
Explanation: Pairs are (1,6),(2,5) and(3,4).

Example 3:
Input: arr = [1,2,3,4,5,6], k = 10
Output: false
Explanation: You can try all possible pairs to see that there is no way to divide arr into 3 pairs each with sum divisible by 10.

Example 4:
Input: arr = [-10,10], k = 2
Output: true

Example 5:
Input: arr = [-1,1,-2,2,-3,3,-4,4], k = 3
Output: true

Constraints:
arr.length == n
1 <= n <= 105
n is even.
-109 <= arr[i] <= 109
1 <= k <= 105
*/

// Mod in Java produces negative numbers ?
// https://stackoverflow.com/questions/5385024/mod-in-java-produces-negative-numbers
/**
Q:When I calculate int i = -1 % 2 I get -1 in Java. In Python, I get 1 as the result of -1 % 2. 
  What do I have to do to get the same behavior in Java with the modulo function?

A:
The problem here is that in Python the % operator returns the modulus and in Java it returns the remainder. 
These functions give the same values for positive arguments, but the modulus always returns positive results 
for negative input, whereas the remainder may give negative results. There's some more information about it 
in this question.

You can find the positive value by doing this:

int i = (((-1 % 2) + 2) % 2)
or this:

int i = -1 % 2;
if (i<0) i += 2;
(obviously -1 or 2 can be whatever you want the numerator or denominator to be)
*/

// Solution 1: HashMap + Complementary Remainder
// Style 1
// Refer to
// https://leetcode.com/problems/check-if-array-pairs-are-divisible-by-k/discuss/709691/Java-7ms-Simple-Solution
/**
Idea :
Given 2 nums 'a' and 'b':
If a % k == x and b % k == k - x :
then (a + b) is divisible by k

Proof :

 a % k == x
 b % k == k - x
 (a + b) % k = ((a + b)%k)%k = (a%k + b%k)%k = (x + k - x)%k = k%k = 0 
 Hence, (a + b) % k == 0 and (a + b) is divisible by k.
OR
by @Marthevin

a%k = x             =>       a = nk+x
b%k = k-x           =>       b = mk+k-x
a+b = nk+mk+k+x-x   =>       a+b = (m+n+1)k    => (a+b) % k = 0
Approach :

Keep count of remainders of all elements of arr
frequency[0] keeps all elements divisible by k, and a divisible of k can only form a group with other divisible of k. 
Hence, total number of such divisibles must be even.
for every element with remainder of i (i != 0) there should be a element with remainder k-i.
Hence, frequency[i] should be equal to frequency[k-i]
class Solution {
    public boolean canArrange(int[] arr, int k) {
        int[] frequency = new int[k];
        for(int num : arr){
            num %= k;
            if(num < 0) num += k;
            frequency[num]++;
        }
        if(frequency[0]%2 != 0) return false;
        
        for(int i = 1; i <= k/2; i++)
            if(frequency[i] != frequency[k-i]) return false;
			
        return true;
    }
}
*/
class Solution {
    public boolean canArrange(int[] arr, int k) {
        int[] freq = new int[k];
        for(int a : arr) {
            a %= k;
            if(a < 0) {
                a += k;
            }
            freq[a]++;
        }
        // Each index of frequency array represents the one of the 
        // possible remainders between the range [0, k - 1]. So, 0th 
        // index of frequency array represents all those elements 
        // which when divided by k give 0 as remainder. 
        // frequency[0] keeps all elements divisible by k, and a 
        // divisible of k can only form a group with other divisible 
        // of k. Hence, total number of such divisibles must be even.
        if(freq[0] % 2 != 0) {
            return false;
        }
        // Cannot start from i = 0, since k - 0 will out of index
        for(int i = 1; i <= k / 2; i++) {
            if(freq[i] != freq[k - i]) {
                return false;
            }
        }
        return true;
    }
}

// Style 2:
// Refer to
// https://leetcode.com/problems/check-if-array-pairs-are-divisible-by-k/discuss/709379/Easy-Java-solution-with-explanation-similar-to-Two-Sum
/**
This problem is similiar to Two Sum. We just need to keep a map to record remiander's frequency:

class Solution {
    public boolean canArrange(int[] arr, int k) {
        Map<Integer, Integer> remainderFreqMap = new HashMap<>();
        int count = 0; // available pair count
        
        for (int num: arr) {
            int remainder = (num % k + k) % k; // if n % k < 0, add k to make it positive
            int complementaryRemainder = (k - remainder) % k;
            if (remainderFreqMap.containsKey(complementaryRemainder) && remainderFreqMap.get(complementaryRemainder) > 0) {
                remainderFreqMap.put(complementaryRemainder, remainderFreqMap.get(complementaryRemainder) - 1);
                count++;
            } else {
                remainderFreqMap.put(remainder, remainderFreqMap.getOrDefault(remainder, 0) + 1);
            }
        }
        
        return count == arr.length / 2;
    }
}
*/
class Solution {
    public boolean canArrange(int[] arr, int k) {
        int count = 0;
        Map<Integer, Integer> freq = new HashMap<Integer, Integer>();
        for(int a : arr) {
            int remainder = (a % k + k) % k;
            // Don't use k - remainder only, must plus % k, consider {5, 10} and k = 5,
            // if no % k, the count will not decrease after processing 10, instead, the
            // count will increase from 1 to 2 since it think remainder are different
            // for 5 and 10, but actually they both as 0 against k = 5.
            int complementaryRemainder = (k - remainder) % k;
            if(freq.containsKey(complementaryRemainder) && freq.get(complementaryRemainder) > 0) {
                freq.put(complementaryRemainder, freq.get(complementaryRemainder) - 1);
                count++; // Find one pair
            } else {
                freq.put(remainder, freq.getOrDefault(remainder, 0) + 1);
            }
        }
        return count == arr.length / 2;
    }
}
