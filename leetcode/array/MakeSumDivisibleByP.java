/**
Refer to
https://leetcode.com/problems/make-sum-divisible-by-p/
Given an array of positive integers nums, remove the smallest subarray (possibly empty) such that the sum of the remaining 
elements is divisible by p. It is not allowed to remove the whole array.

Return the length of the smallest subarray that you need to remove, or -1 if it's impossible.

A subarray is defined as a contiguous block of elements in the array.

Example 1:
Input: nums = [3,1,4,2], p = 6
Output: 1
Explanation: The sum of the elements in nums is 10, which is not divisible by 6. We can remove the subarray [4], and the sum of 
the remaining elements is 6, which is divisible by 6.

Example 2:
Input: nums = [6,3,5,2], p = 9
Output: 2
Explanation: We cannot remove a single element to get a sum divisible by 9. The best way is to remove the subarray [5,2], leaving us with [6,3] with sum 9.

Example 3:
Input: nums = [1,2,3], p = 3
Output: 0
Explanation: Here the sum is 6. which is already divisible by 3. Thus we do not need to remove anything.

Example 4:
Input: nums = [1,2,3], p = 7
Output: -1
Explanation: There is no way to remove a subarray in order to get a sum divisible by 7.

Example 5:
Input: nums = [1000000000,1000000000,1000000000], p = 3
Output: 0

Constraints:
1 <= nums.length <= 105
1 <= nums[i] <= 109
1 <= p <= 109
*/

// Solution 1: This is a variation of 974. Subarray Sums Divisible by K. AND 523. Continuous Subarray Sum store as <sum, index>
// Refer to
// https://leetcode.com/problems/make-sum-divisible-by-p/discuss/854174/C%2B%2BJava-O(n)
// https://leetcode.com/problems/make-sum-divisible-by-p/discuss/854174/C++Java-O(n)/704882
/**
We first compute modulo mod of the sum for the entire array. This modulo should be zero for the array to be divisible by p. 
To make it so, we need to remove a subarray, which sum modulo is a compliment comp to mod.
We use a hash map, where we track the running sum modulo, and the most recent position for that modulo. As we go through the array, 
we look up for a comp modulo in that hash map, and track the minimum window size.
public int minSubarray(int[] nums, int p) {
    int mod = 0, r_mod = 0, min_w = nums.length;
    for (var n : nums)
        mod = (mod + n) % p;
    if (mod == 0)
        return 0;
    Map<Integer, Integer> pos = new HashMap<>();
    pos.put(0, -1);
    for (int i = 0; i < nums.length; ++i) {
        r_mod = (r_mod + nums[i]) % p;
        int comp = (p - mod + r_mod) % p;
        if (pos.containsKey(comp))
            min_w = Math.min(min_w, i - pos.get(comp));
        pos.put(r_mod, i);
    }    
    return min_w >= nums.length ? -1 : min_w;
}

Proof for those who want a mathematical explanation:
Let pre[] be the prefix sum array,
then pre[i] is running prefix sum or prefix sum of i elements,
pre[j] is the prefix sum such that pre[i]-pre[j] is the subarray we need to remove to make pre[n] (sum of all elements) divisible by p

(pre[n] - (pre[i]-pre[j])) % p = 0 ... (remove a subarray to make pre[n] divisible by p)
=> pre[n] % p = (pre[i]-pre[j]) % p ... ((a-b)%m = a%m - b%m)
=> pre[j]%p = pre[i]%p - pre[n]%p ... (same property used above)
since RHS can be negative we make it positive modulus by adding p and taking modulus
=> pre[j]%p = (pre[i]%p - pre[n]%p + p) % p
In @votrubac code, pre[n]%p is mod, pre[i]%p is r_mod, pre[j]%p is comp
*/
class Solution {
    public int minSubarray(int[] nums, int p) {
        // Since we need to remove same modula subarray from original array,
        // first need to calculate original array modula by p
        int target_remainder = 0;
        for(int num : nums) {
            target_remainder = (target_remainder + num) % p;
        }
        // Test out by: nums = [1,2,3], p = 3 as subarray possibly as empty
        // since no need to remove any subarray when original array modula by p
        // already 
        if(target_remainder == 0) {
            return 0;
        }
        // Same as 523. Continuous Subarray Sum store as <sum, index>
        int minLen = nums.length;
        int sum = 0;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(0, -1);
        for(int i = 0; i < nums.length; i++) {
            sum = (sum + nums[i]) % p;
            // Inside original array nums(pre[n]) we can remove a subarray start from j(pre[j]) to i(pre[i])
			      // and remainder part of array nums(pre[n]) modula p is 0
            // i is current index, j is previous index for preSum has same modula result against p
            // (pre[n] - (pre[i] - pre[j])) % p == 0
            // --> pre[n]%p - (pre[i]%p - pre[j]%p) == 0
            // --> pre[j]%p == - pre[n]%p + pre[i]%p
            // --> pre[j]%p == p%p - pre[n]%p + pre[i]%p --> add 'p' to uniform positive modula since '- pre[n]%p + pre[i]%p' maybe negative
            // --> Now replace pre[j]%p as our index to looking for as 'temp', 
            //     replace pre[n]%p as previous calculated modula 'target_remainder',
            //     replace pre[i]%p as current calculated modula 'sum'
            int temp = (p - target_remainder + sum) % p;
            if(map.containsKey(temp)) {
                minLen = Math.min(minLen, i - map.get(temp));
            }
            map.put(sum, i);
        }
        return minLen == nums.length ? -1 : minLen;
    }
}
