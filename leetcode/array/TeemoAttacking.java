/**
 * Refer to
 * https://leetcode.com/problems/teemo-attacking/description/
 * In LOL world, there is a hero called Teemo and his attacking can make his enemy Ashe be in poisoned condition. 
   Now, given the Teemo's attacking ascending time series towards Ashe and the poisoning time duration per Teemo's 
   attacking, you need to output the total time that Ashe is in poisoned condition.

    You may assume that Teemo attacks at the very beginning of a specific time point, and makes Ashe be in poisoned 
    condition immediately.

    Example 1:
    Input: [1,4], 2
    Output: 4
    Explanation: At time point 1, Teemo starts attacking Ashe and makes Ashe be poisoned immediately. 
    This poisoned status will last 2 seconds until the end of time point 2. 
    And at time point 4, Teemo attacks Ashe again, and causes Ashe to be in poisoned status for another 2 seconds. 
    So you finally need to output 4.
    Example 2:
    Input: [1,2], 2
    Output: 3
    Explanation: At time point 1, Teemo starts attacking Ashe and makes Ashe be poisoned. 
    This poisoned status will last 2 seconds until the end of time point 2. 
    However, at the beginning of time point 2, Teemo attacks Ashe again who is already in poisoned status. 
    Since the poisoned status won't add up together, though the second poisoning attack will still work at time point 2, 
    it will stop at the end of time point 3. 
    So you finally need to output 3.
    Note:
    You may assume the length of given time series array won't exceed 10000.
    You may assume the numbers in the Teemo's attacking time series and his poisoning time duration per attacking are 
    non-negative integers, which won't exceed 10,000,000.
 *
 *
 * Solution
 * https://leetcode.com/problems/teemo-attacking/discuss/97465/O(n)-Java-Solution-using-same-idea-of-merge-intervals/101991
*/
// Solution 1: Intuitive Solution
class Solution {
    public int findPoisonedDuration(int[] timeSeries, int duration) {
        if (timeSeries == null || timeSeries.length == 0 || duration == 0) {
            return 0;
        }
        /**
        [1,4], 2 --> (1,3) + (4,6) = 4
             s         e    s'        e' 
        |----|----|----|----|----|----|----
        0    1    2    3    4    5    6
        
        [1,2], 2 --> (1,4) = 3
             s    s'   e    e'  
        |----|----|----|----|----|----|----
        0    1    2    3    4    5    6
        
        Refer to
        https://leetcode.com/problems/teemo-attacking/discuss/97465/O(n)-Java-Solution-using-same-idea-of-merge-intervals/101991
        Sharing my solution, looking at the problem in a more intuitive perspective:
        (1)the previous duration has ended already: only count in the duration
        (2)the previous duration has not ended: refresh the duration. As with the previous duration, 
           we only count in as long as the interval between these two attacks
        */
        int result = 0;
        int len = timeSeries.length;
        for(int i = 1; i < len; i++) {
            if(timeSeries[i] - timeSeries[i - 1] >= duration) {
                result += duration;
            } else {
                result += timeSeries[i] - timeSeries[i - 1];
            }
        }
        result += duration;
        return result;
    }
}
