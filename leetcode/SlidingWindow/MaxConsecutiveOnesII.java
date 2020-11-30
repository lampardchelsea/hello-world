/**
Refer to
https://www.lintcode.com/problem/max-consecutive-ones-ii/description
Given a binary array, find the maximum number of consecutive 1s in this array if you can flip at most one 0.

Example 1:
	Input:  nums = [1,0,1,1,0]
	Output:  4
	
	Explanation:
	Flip the first zero will get the the maximum number of consecutive 1s.
	After flipping, the maximum number of consecutive 1s is 4.

Example 2:
	Input: nums = [1,0,1,0,1]
	Output:  3
	
	Explanation:
	Flip each zero will get the the maximum number of consecutive 1s.
	After flipping, the maximum number of consecutive 1s is 3.
	
Notice
The input array will only contain 0 and 1.
The length of input array is a positive integer and will not exceed 10,000.
*/

// Solution 1: Not fixed length sliding window
// Refer to
// https://www.cnblogs.com/grandyang/p/6376115.html
/**
这道题在之前那道题Max Consecutive Ones的基础上加了一个条件，说我们有一次将0翻转成1的机会，问此时最大连续1的个数，
再看看follow up中的说明，很明显是让我们只遍历一次数组，那我们想，肯定需要用一个变量cnt来记录连续1的个数吧，那么当
遇到了0的时候怎么处理呢，因为我们有一次0变1的机会，所以我们遇到0了还是要累加cnt，然后我们此时需要用另外一个变量cur
来保存当前cnt的值，然后cnt重置为0，以便于让cnt一直用来统计纯连续1的个数，然后我们每次都用用cnt+cur来更新结果res，
参见代码如下：
class Solution {
public:
    int findMaxConsecutiveOnes(vector<int>& nums) {
        int res = 0, cur = 0, cnt = 0;
        for (int num : nums) {
            ++cnt;
            if (num == 0) {
                cur = cnt;
                cnt = 0;
            } 
            res = max(res, cnt + cur);
        }
        return res;
    }
};
上面的方法有局限性，如果题目中说能翻转k次怎么办呢，我们最好用一个通解来处理这类问题。我们可以维护一个窗口[left,right]
来容纳至少k个0。我们遇到了0，就累加zero的个数，然后判断如果此时0的个数大于k，那么我们我们右移左边界left，如果移除掉的
nums[left]为0，那么我们zero自减1。如果不大于k，那么我们用窗口中数字的个数来更新res，参见代码如下：
class Solution {
public:
    int findMaxConsecutiveOnes(vector<int>& nums) {
        int res = 0, zero = 0, left = 0, k = 1;
        for (int right = 0; right < nums.size(); ++right) {
            if (nums[right] == 0) ++zero;
            while (zero > k) {
                if (nums[left++] == 0) --zero;
            }
            res = max(res, right - left + 1);
        }
        return res;
    }
};
*/
public class Solution {
    /**
     * @param nums: a list of integer
     * @return: return a integer, denote  the maximum number of consecutive 1s
     */
    public int findMaxConsecutiveOnes(int[] nums) {
        int i = 0;
        int zeroCount = 0;
        int maxLen = 0;
        for(int j = 0; j < nums.length; j++) {
            if(nums[j] == 0) {
                zeroCount++;
            }
            if(zeroCount > 1) {
                if(nums[i] == 0) {
                    zeroCount--;
                }
                i++;
            }
            maxLen = Math.max(maxLen, j - i + 1);
        }
        return maxLen;
    }
}
