/**
Refer to
https://leetcode.com/problems/pairs-of-songs-with-total-durations-divisible-by-60/
You are given a list of songs where the ith song has a duration of time[i] seconds.

Return the number of pairs of songs for which their total duration in seconds is divisible by 60. Formally, 
we want the number of indices i, j such that i < j with (time[i] + time[j]) % 60 == 0.

Example 1:
Input: time = [30,20,150,100,40]
Output: 3
Explanation: Three pairs have a total duration divisible by 60:
(time[0] = 30, time[2] = 150): total duration 180
(time[1] = 20, time[3] = 100): total duration 120
(time[1] = 20, time[4] = 40): total duration 60

Example 2:
Input: time = [60,60,60]
Output: 3
Explanation: All three pairs have a total duration of 120, which is divisible by 60.

Constraints:
1 <= time.length <= 6 * 104
1 <= time[i] <= 500
*/

// Solution 1: Handle module 0 and 30 separately and set frequency to 0 after calculate the combination
/**
Case 1:
Input: time = [30,20,150,100,40]
Output: 3
Explanation: Three pairs have a total duration divisible by 60:
(time[0] = 30, time[2] = 150): total duration 180
(time[1] = 20, time[3] = 100): total duration 120
(time[1] = 20, time[4] = 40): total duration 60

30 20 30 40 40

30 -> 2
20 -> 1
40 -> 2

for 30 it has combination as 2 * (2 - 1) / 2 = 1
for 20 + 40 has combination as 1 * 2 = 2

for 30 counted and remove key = 30 in map
for 20 + 40 counted and remove both key = 20 + 40 in map

-----------------------------------------------------------
Case 2:
Input: time = [60,60,60]
Output: 3
Explanation: All three pairs have a total duration of 120, which is divisible by 60.

0 0 0

0 -> 3

for 0 has combination as 3 * (3 - 1) / 2 = 3

-----------------------------------------------------------
Case 3:
Input: time = [15,63,451,213,37,209,343,319]
Null Pointer Exception check

Note: Why we have to set frequency to 0 after calculate the combination ?
Because we cannot remove key when traveling the map keyset, such as:
for(int k : map.keySet()) {
    ....
    map.remove(k);
}
You cannot do this, it will throw map keyset concurrentmodificationexception

Another example:
Refer to
https://stackoverflow.com/a/11723228/6706875
The problem is in these lines

for (BigDecimal bigDecimal : transactionLogMap.keySet()) {
    if(!inScopeActiveRegionIdSet.contains(bigDecimal)) {
        transactionLogMap.remove(bigDecimal);
    }
}

You are iterating through the transactionLogMap whilst also directly modifying the underlying Collection when 
you call transactionLogMap.remove, which is not allowed because the enhanced for loop cannot see those changes.

The correct solution is to use the Iterator:
Iterator<BigDecimal> it = transactionLogMap.keySet().iterator();//changed for syntax correctness
while (it.hasNext()) {
    BigDecimal bigDecimal = it.next();
    if(!inScopeActiveRegionIdSet.contains(bigDecimal)) {
        it.remove();
    }
}
*/
class Solution {
    public int numPairsDivisibleBy60(int[] time) {
        Map<Integer, Integer> freq = new HashMap<Integer, Integer>();
        for(int t : time) {
            freq.put(t % 60, freq.getOrDefault(t % 60, 0) + 1);
        }
        int count = 0;
        for(int k : freq.keySet()) {
            if(freq.get(k) != 0) {
                if(k == 0 || k == 30) {
                    int n = freq.get(k);
                    count += n * (n - 1) / 2;
                    freq.put(k, 0);
                } else {
                    int n = freq.get(k);
                    if(freq.containsKey(60 - k)) {
                        int m = freq.get(60 - k);
                        count += n * m;
                        freq.put(k, 0);
                        freq.put(60 - k, 0);
                    } else {
                        freq.put(k, 0);
                    }
                }
            }
        }
        return count;
    }
}

// Solution 2: O(n) code w/ comment, similar to Two Sum
// Refer to
// https://leetcode.com/problems/pairs-of-songs-with-total-durations-divisible-by-60/discuss/256726/JavaPython-3-O(n)-code-w-comment-similar-to-Two-Sum
/**
Let target in Two Sum be 60 and each item in time % 60, then the two problems are very similar to each other.

Explain the statement theOther = (60 - t % 60) % 60;

Let theOther be in the pair with t, then
(t + theOther) % 60 == 0

so we have
t % 60 + theOther % 60 = 0 or 60

then
theOther % 60 + t % 60 = 0 

or
theOther % 60 = 60 - t % 60

Note that it is possible that t % 60 == 0, which results 60 - t % 60 == 60,

therefore, we should have
theOther % 60 = (60 - t % 60) % 60

Let 0 <= theOther < 60, therefore thOther = theOther % 60.
use theOther to replace theOther % 60, we get

theOther = (60 - t % 60) % 60;
*/
class Solution {
    public int numPairsDivisibleBy60(int[] time) {
        Map<Integer, Integer> count = new HashMap<>();
        int ans = 0;
        for (int t : time) {
            int theOther = (60 - t % 60) % 60;
            ans += count.getOrDefault(theOther, 0); // in current HashMap, get the number of songs that if adding t equals to a multiple of 60.
            count.put(t % 60, 1 + count.getOrDefault(t % 60, 0)); // update the number of t % 60.
        }
        return ans;
    }
}

