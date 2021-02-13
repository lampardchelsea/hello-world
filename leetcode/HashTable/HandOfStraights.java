/**
Refer to
https://leetcode.com/problems/hand-of-straights/
Alice has a hand of cards, given as an array of integers.

Now she wants to rearrange the cards into groups so that each group is size W, and consists of W consecutive cards.

Return true if and only if she can.

Note: This question is the same as 1296: https://leetcode.com/problems/divide-array-in-sets-of-k-consecutive-numbers/

Example 1:
Input: hand = [1,2,3,6,2,3,4,7,8], W = 3
Output: true
Explanation: Alice's hand can be rearranged as [1,2,3],[2,3,4],[6,7,8]

Example 2:
Input: hand = [1,2,3,4,5], W = 4
Output: false
Explanation: Alice's hand can't be rearranged into groups of 4.

Constraints:
1 <= hand.length <= 10000
0 <= hand[i] <= 10^9
1 <= W <= hand.length
*/

// Solution 1: TreeMap + Greedy 
// same as 1296: Divide Array in Sets of K Consecutive Numbers
// https://leetcode.com/problems/divide-array-in-sets-of-k-consecutive-numbers/
class Solution {
    public boolean isNStraightHand(int[] hand, int W) {
        if(hand.length % W != 0) {
            return false;
        }
        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
        for(int a : hand) {
            map.put(a, map.getOrDefault(a, 0) + 1);
        }
        while(map.size() > 0) {
            int min = map.firstKey();
            int minFreq = map.get(min);
            for(int i = 0; i < W; i++) {
                if(map.containsKey(min + i) && map.get(min + i) >= minFreq) {
                    map.put(min + i, map.get(min + i) - minFreq);
                    if(map.get(min + i) == 0) {
                        map.remove(min + i);
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}



