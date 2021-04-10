/**
Refer to
https://leetcode.com/problems/koko-eating-bananas/
Koko loves to eat bananas. There are n piles of bananas, the ith pile has piles[i] bananas. 
The guards have gone and will come back in h hours.

Koko can decide her bananas-per-hour eating speed of k. Each hour, she chooses some pile of bananas 
and eats k bananas from that pile. If the pile has less than k bananas, she eats all of them instead 
and will not eat any more bananas during this hour.

Koko likes to eat slowly but still wants to finish eating all the bananas before the guards return.

Return the minimum integer k such that she can eat all the bananas within h hours.

Example 1:
Input: piles = [3,6,7,11], h = 8
Output: 4

Example 2:
Input: piles = [30,11,23,4,20], h = 5
Output: 30

Example 3:
Input: piles = [30,11,23,4,20], h = 6
Output: 23

Constraints:
1 <= piles.length <= 104
piles.length <= h <= 109
1 <= piles[i] <= 109
*/

// Solution 1:
// Refer to
// https://leetcode.com/discuss/general-discussion/786126/python-powerful-ultimate-binary-search-template-solved-many-problems
/**
875. Koko Eating Bananas [Medium]
Koko loves to eat bananas. There are N piles of bananas, the i-th pile has piles[i] bananas. The guards have gone and will come 
back in H hours. Koko can decide her bananas-per-hour eating speed of K. Each hour, she chooses some pile of bananas, and eats K 
bananas from that pile. If the pile has less than K bananas, she eats all of them instead, and won't eat any more bananas during this hour.

Koko likes to eat slowly, but still wants to finish eating all the bananas before the guards come back. Return the minimum 
integer K such that she can eat all the bananas within H hours.

Example :

Input: piles = [3,6,7,11], H = 8
Output: 4
Input: piles = [30,11,23,4,20], H = 5
Output: 30
Input: piles = [30,11,23,4,20], H = 6
Output: 23
Very similar to LC 1011 and LC 410 mentioned above. Let's design a feasible function, given an input speed, determine whether 
Koko can finish all bananas within H hours with hourly eating speed speed. Obviously, the lower bound of the search space is 1, 
and upper bound is max(piles), because Koko can only choose one pile of bananas to eat every hour.

def minEatingSpeed(piles: List[int], H: int) -> int:
    def feasible(speed) -> bool:
        # return sum(math.ceil(pile / speed) for pile in piles) <= H  # slower        
        return sum((pile - 1) // speed + 1 for pile in piles) <= H  # faster

    left, right = 1, max(piles)
    while left < right:
        mid = left  + (right - left) // 2
        if feasible(mid):
            right = mid
        else:
            left = mid + 1
    return left
*/

// How to round up the result of integer division?
// https://stackoverflow.com/questions/17944/how-to-round-up-the-result-of-integer-division
/**
int pageCount = (records + recordsPerPage - 1) / recordsPerPage;
*/
class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int lo = 1;
        int hi = 0;
        for(int pile : piles) {
            hi = Math.max(hi, pile);
        }
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if(totalHours(piles, mid) <= h) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }
    
    private int totalHours(int[] piles, int k) {
        int count = 0;
        for(int pile : piles) {
            count += (pile - 1) / k + 1;
        }
        return count;
    }
}
