/**
Refer to
https://leetcode.com/problems/longest-well-performing-interval/
We are given hours, a list of the number of hours worked per day for a given employee.

A day is considered to be a tiring day if and only if the number of hours worked is (strictly) greater than 8.

A well-performing interval is an interval of days for which the number of tiring days is strictly larger than the number of non-tiring days.

Return the length of the longest well-performing interval.

Example 1:
Input: hours = [9,9,6,0,6,6,9]
Output: 3
Explanation: The longest well-performing interval is [9,9,6].

Constraints:
1 <= hours.length <= 10000
0 <= hours[i] <= 16
*/

// Note: This question not suitable for Sliding Window, and similar to 525. Contiguous Array and 560. Subarray Sum Equals K
// Refer to
// Why 525 cannot be use Sliding Window to resolve ?
// https://leetcode.com/problems/longest-well-performing-interval/discuss/334565/JavaC++Python-O(N)-Solution-Life-needs-996-and-669/763532
// Well 525 isn't sliding window because the condition doesn't change monotonically as you expand/shrink the window (because of neg. numbers).
// It's actually very similar to this problem.

// 525. Contiguous Array
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/HashTable/ContiguousArray.java

// 560. Subarray Sum Equals K
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/array/SubarraySumEqualsK.java


// Solution 1: HashMap + Presum
// Refer to
// https://leetcode.com/problems/longest-well-performing-interval/discuss/334565/JavaC%2B%2BPython-O(N)-Solution-Life-needs-996-and-669
/**
Intuition
If working hour > 8 hours, yes it's tiring day.
But I doubt if 996 is a well-performing interval.
Life needs not only 996 but also 669.

Explanation
We starts with a score = 0,
If the working hour > 8, we plus 1 point.
Otherwise we minus 1 point.
We want find the maximum interval that have strict positive score.

After one day of work, if we find the total score > 0,
the whole interval has positive score,
so we set res = i + 1.

If the score is a new lowest score, we record the day by seen[cur] = i.
seen[score] means the first time we see the score is seen[score]th day.

We want a positive score, so we want to know the first occurrence of score - 1.
score - x also works, but it comes later than score - 1.
So the maximum interval is i - seen[score - 1]

Complexity
Time O(N) for one pass.
Space O(N) in worst case if no tiring day.

Java:

    public int longestWPI(int[] hours) {
        int res = 0, score = 0, n = hours.length;
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            score += hours[i] > 8 ? 1 : -1;
            if (score > 0) {
                res = i + 1;
            } else {
                seen.putIfAbsent(score, i);
                if (seen.containsKey(score - 1))
                    res = Math.max(res, i - seen.get(score - 1));
            }
        }
        return res;
    }

https://leetcode.com/problems/longest-well-performing-interval/discuss/334565/JavaC++Python-O(N)-Solution-Life-needs-996-and-669/309099
https://leetcode.com/problems/longest-well-performing-interval/discuss/334565/JavaC++Python-O(N)-Solution-Life-needs-996-and-669/305225
本质: 
1. 若到当前位置连续和sum是正数:整个数组就满足条件; 
2. 若sum<=0, 那么从头开始整个数组不满足条件; 此时希望找到最前面前缀和为sum-1对应的index i, 那么从i+1开始到当前位置就是longest subarray;此时子数组的元素和也就是1.
sum-x并不需要关心, 因为如果是最长子数组, 子数组的和一定是1, 所以只用关心sum-1.
3. 这道题目的本质是 先把数组换成由-1 和1 （-1代表没有超时，1代表超时） 转化为找一个maximum size subarray 让它的和大于 0， 
geeksforgeeks有(N logN)方法 当然这个考虑连续性，可以用哈希表找 score -1, 题目出的不错！
*/

// Refer to
// https://leetcode.com/problems/longest-well-performing-interval/discuss/334635/Java-HashMap-O(n)-solution-with-explanation-similar-to-lc525
/**
class Solution {
    public int longestWPI(int[] hours) {
        if (hours.length == 0) return 0;
        int maxLen = 0;
        Map<Integer, Integer> map = new HashMap();  // key is possible sum in hours array, value is index where that sum appears for the first time
        int sum = 0;  // sum at index i indicates the sum of hours[0:i] after transformation
		
        for (int i = 0; i < hours.length; i++) {
            sum += hours[i] > 8 ? 1 : -1;  // transform each hour to 1 or -1
            if (!map.containsKey(sum)) {
                map.put(sum, i);  // record where the sum appears for the first time
            }
			
            if (sum > 0) {  // in hours[0:i], more 1s than -1s
                maxLen = i + 1;
            } else if (map.containsKey(sum - 1)) {  // get the index j where sum of hours[0:j] is sum - 1, so that sum of hours[j+1:i] is 1
                maxLen = Math.max(maxLen, i - map.get(sum - 1));
            }            
            
        }
        
        return maxLen;
    }

Yes the algorithm makes sense and its actually clever. There are two things I want to clarify for anybody who wants.
https://leetcode.com/problems/longest-well-performing-interval/discuss/334635/Java-HashMap-O(n)-solution-with-explanation-similar-to-lc525/311518

a) Why should we consider map.get(sum-1) ? not sum - 2 or sum - 3..? because the interval would be the longest when the difference 
between number of tiring days and number of non-tiring days is 1 and not 2 or 3 because we might miss some non-tiring days if we 
consider 2, 3. etc. Get as many non-tiring days until you get the difference to 1 - this would ensure you get the longest interval.

b) Why should you put in the map only the first occurrence of sum? Because consider this use case...
N -> non-tiring
T -> tiring
NNNNTT
at the last T - > sum is -2, if you look for key -3 , you want to get the one at which sum is -3 at N and not at last-before T!
Again this makes sure that the interval is the longest.
}
*/
class Solution {
    public int longestWPI(int[] hours) {
        // Key is possible sum in hours array, value is index where that sum appears for the first time
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int max = 0;
        // sum at index i indicates the sum of hours[0:i] after transformation
        int sum = 0;
        for(int i = 0; i < hours.length; i++) {
            // Transform each hour to 1 or -1
            sum += hours[i] > 8 ? 1: -1;
            // Record where the sum appears for the first time
            if(!map.containsKey(sum)) {
                map.put(sum, i);
            }
            if(sum > 0) {
                // After one day of work, if we find the total sum > 0, the whole interval has positive 
                // sum (in hours[0:i], more 1s than -1s), so we set max = i + 1
                max = i + 1;
            } else if(map.containsKey(sum - 1)) {
                // Get the index j where sum of hours[0:j] is sum - 1, so that sum of hours[j+1:i] is 1
                max = Math.max(max, i - map.get(sum - 1));
            }
        }
        return max;
    }
}
