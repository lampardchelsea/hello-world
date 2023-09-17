/**
 * Refer to
 * https://leetcode.com/problems/missing-number/#/description
 * Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.
 * For example,
 * Given nums = [0, 1, 3] return 2.
 * Note:
 * Your algorithm should run in linear runtime complexity. Could you implement it using only constant extra space complexity?
*/
// Solution 1: Sum
// Refer to
// https://discuss.leetcode.com/topic/23427/3-different-ideas-xor-sum-binary-search-java-code
public class Solution {
    public int missingNumber(int[] nums) {
        int sum = 0;
        int len = nums.length;
        for(int i = 0; i < len; i++) {
            sum += nums[i];
        }
        // Because the original (0, 1, 2... n) array need to added
        // based on (0 + n) * (n + 1) / 2, and n = nums.length = len
        int a = (len + 1) * len / 2;
        return a - sum;
    } 
}


// Solution 2: Binary Search
// Refer to
// https://discuss.leetcode.com/topic/23427/3-different-ideas-xor-sum-binary-search-java-code/26
public class Solution {
    public int missingNumber(int[] nums) {
        // Refer to
        // Will Arrays.sort() increase time complexity and space time complexity?
        // http://stackoverflow.com/questions/22571586/will-arrays-sort-increase-time-complexity-and-space-time-complexity
        Arrays.sort(nums);
        int len = nums.length;
        int lo = 0;
        int hi = len - 1;
        while(lo <= hi) {
            /**
             * Refer to
             * https://discuss.leetcode.com/topic/23427/3-different-ideas-xor-sum-binary-search-java-code/33
             * If the nums is already sorted, we can use binary search to find out the missing element.
             * And the time complexity is O(logN), better than the XOR solution O(N).
             * The logic behind the binary search solution is:
             * If nums[index] > index, it means that something is missing on the left of the element.
             * Therefore, we can discard the elements on the right, and focus on searching on the right 
             * half of the array. 
             */
            int mid = lo + (hi - lo)/2;
            if(nums[mid] > mid) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }
}

// Solution 3: XOR
public class Solution {
    public int missingNumber(int[] nums) {
        int res = nums.length;
        for(int i=0; i < nums.length; i++){
            res ^= i;
            res ^= nums[i];
        }
        return res;
    }
}













































































https://leetcode.com/problems/missing-number/description/

Given an array nums containing n distinct numbers in the range [0, n], return the only number in the range that is missing from the array.

Example 1:
```
Input: nums = [3,0,1]
Output: 2
Explanation: n = 3 since there are 3 numbers, so all numbers are in the range [0,3]. 2 is the missing number in the range since it does not appear in nums.
```

Example 2:
```
Input: nums = [0,1]
Output: 2
Explanation: n = 2 since there are 2 numbers, so all numbers are in the range [0,2]. 2 is the missing number in the range since it does not appear in nums.
```

Example 3:
```
Input: nums = [9,6,4,2,3,5,7,0,1]
Output: 8
Explanation: n = 9 since there are 9 numbers, so all numbers are in the range [0,9]. 8 is the missing number in the range since it does not appear in nums.
```

Constraints:
- n == nums.length
- 1 <= n <= 104
- 0 <= nums[i] <= n
- All the numbers of nums are unique.

Follow up: Could you implement a solution using only O(1) extra space complexity and O(n) runtime complexity?
---
Attempt 1: 2023-09-17

Solution 1:  XOR (10 min)
```
class Solution {
    public int missingNumber(int[] nums) {
        int n = nums.length;
        // 我们可以把下标当成上边所说的完整的序列。因为下标没有 n，
        // 所以初始化 result = n。然后把两个序列的数字依次异或即可
        int result = n;
        for(int i = 0; i < n; i++) {
            result ^= nums[i] ^ i;
        }
        return result;
    }
}
```

Refer to
https://leetcode.wang/leetcode-268-Missing-Number.html

解法三

又到了神奇的异或的方法了，这里 的解法。

136 题 详细的介绍了异或的一个性质，a ⊕ a = 0，也就是相同数字异或等于 0。

这道题的话，相当于我们有两个序列。

一个完整的序列， 0 到 n。

一个是 0 到 n 中缺少了一个数字的序列。

把这两个序列合在一起，其实就变成了136 题 的题干——所有数字都出现了两次，只有一个数字出现了一次，找出这个数字。

假如合起来的数字序列是 a b a b c c d ，d 出现了一次，也就是我们缺失的数字。

如果我们把给定的数字相互异或会发生什么呢？因为异或满足交换律和结合律，所以结果如下。
```
  a ⊕ b ⊕ a ⊕ b ⊕ c ⊕ c ⊕ d
= ( a ⊕ a ) ⊕ ( b ⊕ b ) ⊕ ( c ⊕ c ) ⊕ d
= 0 ⊕ 0 ⊕ 0 ⊕ d
= d
```
这样我们就找了缺失的数字了。

代码的话，我们可以把下标当成上边所说的完整的序列。因为下标没有 n，所以初始化 result = n。

然后把两个序列的数字依次异或即可。
```
public int missingNumber(int[] nums) {
    int result = nums.length;
    for (int i = 0; i < nums.length; i++) {
        result = result ^ nums[i] ^ i;
    }
    return result;
}
```

Refer to
https://leetcode.com/problems/missing-number/solutions/69791/4-line-simple-java-bit-manipulate-solution-with-explaination/
The basic idea is to use XOR operation. We all know that a^b^b =a, which means two xor operations with the same number will eliminate the number and reveal the original number.
In this solution, I apply XOR operation to both the index and value of the array. In a complete array with no missing numbers, the index and value should be perfectly corresponding( nums[index] = index), so in a missing array, what left finally is the missing number.
```
public int missingNumber(int[] nums) {
    int xor = 0, i = 0;
	for (i = 0; i < nums.length; i++) {
		xor = xor ^ i ^ nums[i];
	}
	return xor ^ i;
}
```

Refer to
https://grandyang.com/leetcode/268/
这题还有一种解法，使用位操作Bit Manipulation来解的，用到了异或操作的特性，相似的题目有Single Number 单独的数字, Single Number II 单独的数字之二和Single Number III 单独的数字之三。那么思路是既然0到n之间少了一个数，我们将这个少了一个数的数组合0到n之间完整的数组异或一下，那么相同的数字都变为0了，剩下的就是少了的那个数字了，参加代码如下：
```
    class Solution {
        public:
        int missingNumber(vector<int>& nums) {
            int res = 0;
            for (int i = 0; i < nums.size(); ++i) {
                res ^= (i + 1) ^ nums[i];
            }
            return res;
        }
    };
```
