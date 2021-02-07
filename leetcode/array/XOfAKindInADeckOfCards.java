/**
Refer to
https://leetcode.com/problems/x-of-a-kind-in-a-deck-of-cards/
In a deck of cards, each card has an integer written on it.

Return true if and only if you can choose X >= 2 such that it is possible to split the entire deck into 1 or more groups of cards, where:

Each group has exactly X cards.
All the cards in each group have the same integer.

Example 1:
Input: deck = [1,2,3,4,4,3,2,1]
Output: true
Explanation: Possible partition [1,1],[2,2],[3,3],[4,4].

Example 2:
Input: deck = [1,1,1,2,2,2,3,3]
Output: false´
Explanation: No possible partition.

Example 3:
Input: deck = [1]
Output: false
Explanation: No possible partition.

Example 4:
Input: deck = [1,1]
Output: true
Explanation: Possible partition [1,1].

Example 5:
Input: deck = [1,1,2,2,2,2]
Output: true
Explanation: Possible partition [1,1],[2,2],[2,2].

Constraints:
1 <= deck.length <= 10^4
0 <= deck[i] < 10^4
*/

// Solution 1: Find greatest common divisor (GCD)
// Refer to
// https://leetcode.com/problems/x-of-a-kind-in-a-deck-of-cards/discuss/175845/C%2B%2BJavaPython-Greatest-Common-Divisor
class Solution {
    public boolean hasGroupsSizeX(int[] deck) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int d : deck) {
            map.put(d, map.getOrDefault(d, 0) + 1);
        }
        int result = 0;
        for(int d : map.values()) {
            result = gcd(d, result);
        }
        return result > 1;
    }
    
    private int gcd(int a, int b) {
        if(b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}
