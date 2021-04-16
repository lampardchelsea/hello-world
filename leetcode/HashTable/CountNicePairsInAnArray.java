/**
Refer to
https://leetcode.com/problems/count-nice-pairs-in-an-array/
You are given an array nums that consists of non-negative integers. Let us define rev(x) as the reverse of 
the non-negative integer x. For example, rev(123) = 321, and rev(120) = 21. A pair of indices (i, j) is nice 
if it satisfies all of the following conditions:

0 <= i < j < nums.length
nums[i] + rev(nums[j]) == nums[j] + rev(nums[i])
Return the number of nice pairs of indices. Since that number can be too large, return it modulo 109 + 7.

Example 1:
Input: nums = [42,11,1,97]
Output: 2
Explanation: The two pairs are:
 - (0,3) : 42 + rev(97) = 42 + 79 = 121, 97 + rev(42) = 97 + 24 = 121.
 - (1,2) : 11 + rev(1) = 11 + 1 = 12, 1 + rev(11) = 1 + 11 = 12.

Example 2:
Input: nums = [13,10,35,24,76]
Output: 4

Constraints:
1 <= nums.length <= 105
0 <= nums[i] <= 109
*/

// Solution 1: num[i] - rev(num[i]) = num[j] - rev(num[j])
// Refer to
// https://leetcode.com/problems/count-nice-pairs-in-an-array/discuss/1140487/Count-Frequency-of-difference-of-number-and-its-reverse-or-Easy-Hashmap-Explained
/**
We are asked to find pair (i, j) such that i < j and num[i] + rev(num[j]) = num[j] + rev(num[i]). Obviously looking for 
each pair (i, j) won't work. So, we need to find a better time complexity than O(n^2)

The above equation can be modified as -

num[i] - rev(num[i]) = num[j] - rev(num[j])
We can see that we are required to find number of pairs whose difference between original number and reversed number is 
the same. So, we can just count the frequency of numbers having a particular n - rev(n) value and store them in a hashmap. 
Finally, we just have to count the pairs which can be formed which is freq[i] * (freq[i] - 1) / 2.

C++
int countNicePairs(vector<int>& nums) {
	long count = 0;
	unordered_map<int, long> mp;
	for(auto& num : nums) mp[num - rev(num)]++;        // counting frequency of each n - rev(n)
	for(auto& pair : mp)  // with each value, we can form n*(n-1)/2 pairs
		count = (count + (pair.second * (pair.second - 1)) / 2) % 1000000007; 
	// Infact, the above can be done in a single pass as well which I didn't realise at the first try -
	// for(auto& num : nums) count = (count + mp[num - reverseNum(num)]++) % 1000000007;
	return count;
}
int rev(int n){
	int revNum = 0;
	while(n) revNum = revNum * 10 + (n % 10), n /= 10;
	return revNum;
}

Time Complexity : O(N)
Space Complexity : O(N)

Fun Fact : All the numbers have the difference between the original number and its reverse as a multiple of 9.
*/

// Add mod and long to avoid stackoverflow for integer
// https://leetcode.com/problems/count-nice-pairs-in-an-array/discuss/1140487/Count-Frequency-of-difference-of-number-and-its-reverse-or-Easy-Hashmap-Explained/896131
/**
public int countNicePairs(int[] nums) {
        Map<Integer, Integer> map=new HashMap<>();
        for(int i=0;i<nums.length;i++) {
            int rev=Integer.parseInt(new StringBuilder().append(nums[i]).reverse().toString());
            int dif=nums[i]-rev;
            map.put(dif, map.getOrDefault(dif, 0)+1);
        }
        long res=0, m=1000000007;
        for(int ct : map.values()) {
            if(ct==1) continue;
            res=(res+(long)ct*(ct-1)/2)%m;
        }
        return (int)res;
    }
*/

// Style 1:
class Solution {
    public int countNicePairs(int[] nums) {
        // Transform each nums[i] into (nums[i] - rev(nums[i])). 
        // Then, count the number of (i, j) pairs that have equal values.
        for(int i = 0; i < nums.length; i++) {
            nums[i] -= reverseNum(nums[i]);
        }
        // Keep a map storing the frequencies of values that you have 
        // seen so far. For each i, check if nums[i] is in the map. 
        // If it is, then add that count to the overall count. 
        // Then, increment the frequency of nums[i].
        Map<Integer, Integer> freq = new HashMap<Integer, Integer>();
        for(int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        long count = 0;
        int mod = 1000000007;
        for(int c : freq.values()) {
            count = (count + (long)c * (c - 1) / 2) % mod;
        }
        return (int)count;
    }
    
    private int reverseNum(int num) {
        int result = 0;
        while(num > 0) {
            result = result * 10 + num % 10;
            num /= 10;
        }
        return result;
    }
}


// Style 2: TLE, no need to remove tailing 0
class Solution {
    public int countNicePairs(int[] nums) {
        // Transform each nums[i] into (nums[i] - rev(nums[i])). 
        // Then, count the number of (i, j) pairs that have equal values.
        for(int i = 0; i < nums.length; i++) {
            nums[i] -= reverseNum(nums[i]);
        }
        // Keep a map storing the frequencies of values that you have 
        // seen so far. For each i, check if nums[i] is in the map. 
        // If it is, then add that count to the overall count. 
        // Then, increment the frequency of nums[i].
        Map<Integer, Integer> freq = new HashMap<Integer, Integer>();
        for(int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        long count = 0;
        int mod = 1000000007;
        for(int c : freq.values()) {
            count = (count + (long)c * (c - 1) / 2) % mod;
        }
        return (int)count;
    }
    
    private int reverseNum(int num) {
        int result = 0;
        // Removing the tailing 0 --> redundant code for remove tailing
        while(num % 10 == 0) {
            num /= 10;
        }
        while(num > 0) {
            result = result * 10 + num % 10;
            num /= 10;
        }
        return result;
    }
}
