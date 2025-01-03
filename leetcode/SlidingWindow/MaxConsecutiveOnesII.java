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



/**
Follow up:
What if the input numbers come in one by one as an infinite stream? In other words, you can't store all numbers 
coming from the stream as it's too large to hold in memory. Could you solve it efficiently?
https://discuss.leetcode.com/topic/75445/java-clean-solution-easily-extensible-to-flipping-k-zero-and-follow-up-handled

The idea is to keep a window [l, h] that contains at most k zero
The following solution does not handle follow-up, because nums[l] will need to access previous input stream
Time: O(n) Space: O(1)

    public int findMaxConsecutiveOnes(int[] nums) {
        int max = 0, zero = 0, k = 1; // flip at most k zero
        for (int l = 0, h = 0; h < nums.length; h++) {
            if (nums[h] == 0)                                           
                zero++;
            while (zero > k)
                if (nums[l++] == 0)
                    zero--;                                     
            max = Math.max(max, h - l + 1);
        }                                                               
        return max;             
    }

Now let's deal with follow-up, we need to store up to k indexes of zero within the window [l, h] so that we know where to 
move lnext when the window contains more than k zero. If the input stream is infinite, then the output could be extremely 
large because there could be super long consecutive ones. In that case we can use BigInteger for all indexes. 
For simplicity, here we will use int
Time: O(n) Space: O(k)

    public int findMaxConsecutiveOnes(int[] nums) {                 
        int max = 0, k = 1; // flip at most k zero
        Queue<Integer> zeroIndex = new LinkedList<>(); 
        for (int l = 0, h = 0; h < nums.length; h++) {
            if (nums[h] == 0)
                zeroIndex.offer(h);
            if (zeroIndex.size() > k)                                   
                l = zeroIndex.poll() + 1;
            max = Math.max(max, h - l + 1);
        }
        return max;                     
    }

Note that setting k = 0 will give a solution to the earlier version Max Consecutive Ones
For k = 1 we can apply the same idea to simplify the solution. Here q stores the index of zero within the window [l, h] 
so its role is similar to Queue in the above solution

    public int findMaxConsecutiveOnes(int[] nums) {
        int max = 0, q = -1;
        for (int l = 0, h = 0; h < nums.length; h++) {
            if (nums[h] == 0) {
                l = q + 1;
                q = h;
            }
            max = Math.max(max, h - l + 1);
        }                                                               
        return max;             
    }
 */


// Note: Why we can use only if condition to find maximum length of subarray ?
/**
Take example as: nums = [0,1,1,1,0,1,1,0,1]
If debug with console, we find in solution 1, when i = 8, j stop at 3, for solution 2, when i = 8, j stop at 5.
The thing is we don't actually need to while loop to find exactly previous 0 index, since we are trying to find
maximum length between i and j, if i keeps move forward (0 to length - 1) by 1 step each round, if we able to 
find maximum size sliding window, j should either not move forward (0 to length - 1) OR just keep the same pace
as i that move foward by 1 step each round, if we use while loop, even we find exactly previous 0 index that will
not help we find maximum size sliding window since j will move fast then i, in another word, when i just move 1
step, j may move n >= 1 steps to find previous 0 index, it will not help maximum (i - j).
Instead of moving j with while loop, just use if condition will guarantee we find maximum size sliding window since
j at most move 0 or 1 step each round when i must move 1 step each round, during this kind of iteration, if we are
lucky enough to remove the previous 0 when j move forward then we find the answer, we don't have to move j till
actual previous 0 index, such as here when i move up to 8, in if condition we only stop j at 3, but in while loop
you have to move j till 5 to find 2nd 0 in nums array and try to remove it.
*/














































































https://leetcode.ca/2017-03-31-487-Max-Consecutive-Ones-II/
Given a binary array nums, return the maximum number of consecutive 1's in the array if you can flip at most one 0.

Example 1:
Input: nums = [1,0,1,1,0]
Output: 4
Explanation: 
- If we flip the first zero, nums becomes [1,1,1,1,0] and we have 4 consecutive ones.
- If we flip the second zero, nums becomes [1,0,1,1,1] and we have 3 consecutive ones.
The max number of consecutive ones is 4.

Example 2:
Input: nums = [1,0,1,1,0,1]
Output: 4
Explanation: 
- If we flip the first zero, nums becomes [1,1,1,1,0,1] and we have 4 consecutive ones.
- If we flip the second zero, nums becomes [1,0,1,1,1,1] and we have 4 consecutive ones.
The max number of consecutive ones is 4.
 
Constraints:
- 1 <= nums.length <= 10^5
- nums[i] is either 0 or 1.
 
Follow up: What if the input numbers come in one by one as an infinite stream? In other words, you can't store all numbers coming from the stream as it's too large to hold in memory. Could you solve it efficiently?
--------------------------------------------------------------------------------
Attempt 1: 2024-12-27
Solution 1: Not fixed length Sliding Window (10 min)
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

Time Complexity: O(n)
Space Complexity: O(1)

Refer to chatGPT
public class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int left = 0, maxLength = 0;
        int zeroCount = 0;

        for (int right = 0; right < nums.length; right++) {
            // If the current number is 0, increase the zero count
            if (nums[right] == 0) {
                zeroCount++;
            }

            // If zeroCount exceeds 1, shrink the window from the left
            while (zeroCount > 1) {
                if (nums[left] == 0) {
                    zeroCount--;
                }
                left++;
            }

            // Update the maximum length
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }
}
Key Points in the Solution:
1.Two Pointers:
- Use left and right pointers to define the current sliding window.
- Expand the window by incrementing right and shrink it by incrementing left.
2.Zero Count:
- Track the number of 0s in the current window using zeroCount.
- If zeroCount exceeds 1 (because only one 0 can be flipped), move left to reduce the window size until zeroCount <= 1.
3.Update Maximum:
- Calculate the maximum length of the window whenever a valid window (with at most one 0) is maintained.
What about follow up ?
Follow up: What if the input numbers come in one by one as an infinite stream? In other words, you can't store all numbers coming from the stream as it's too large to hold in memory. Could you solve it efficiently?
Key Idea:
1.Sliding Window for Streams:
- Maintain a sliding window using two variables: 
- one to track the length of the current sequence of 1s before the last 0 (countBeforeZero) 
- another to track the length of the current sequence after the last 0 (countAfterZero).
- When a 0 is encountered, update the counts and keep the "window" moving forward.
2.Tracking the Result:
- At any point in the stream, the maximum consecutive 1s (with at most one flipped 0) is the sum of countBeforeZero + countAfterZero + 1 (if a 0 is flipped).
3.Space Efficiency:
- Instead of storing the entire stream, only keep two counters and a running maximum, making the solution highly space-efficient.
Java Solution for Infinite Stream:
public class Solution {
    public int findMaxConsecutiveOnesStream(int[] stream) {
        int countBeforeZero = 0; // Count of 1's before the last 0
        int countAfterZero = 0;  // Count of 1's after the last 0
        int maxLength = 0;       // Maximum length of consecutive 1's
        boolean seenZero = false; // Whether we've seen a 0 in the current window

        for (int num : stream) {
            if (num == 1) {
                countAfterZero++; // Extend the sequence after the last 0
            } else {
                // Shift the window: the current 'countAfterZero' becomes 'countBeforeZero'
                countBeforeZero = countAfterZero;
                countAfterZero = 0; // Reset the count for the new sequence
                seenZero = true;
            }

            // Calculate the maximum length
            if (seenZero) {
                maxLength = Math.max(maxLength, countBeforeZero + countAfterZero + 1); // Flip one 0
            } else {
                maxLength = Math.max(maxLength, countAfterZero); // No 0 to flip
            }
        }

        return maxLength;
    }
}
