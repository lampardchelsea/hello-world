/**
 Refer to
 https://leetcode.com/problems/number-of-equivalent-domino-pairs/
 Given a list of dominoes, dominoes[i] = [a, b] is equivalent to dominoes[j] = [c, d] if and 
 only if either (a==c and b==d), or (a==d and b==c) - that is, one domino can be rotated to 
 be equal to another domino.

Return the number of pairs (i, j) for which 0 <= i < j < dominoes.length, and dominoes[i] is 
equivalent to dominoes[j].

Example 1:
Input: dominoes = [[1,2],[2,1],[3,4],[5,6]]
Output: 1

Constraints:
1 <= dominoes.length <= 40000
1 <= dominoes[i][j] <= 9
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/number-of-equivalent-domino-pairs/discuss/346231/Java-solution-beat-100-without-using-HashMap
class Solution {
    public int numEquivDominoPairs(int[][] dominoes) {
        int[] temp = new int[dominoes.length];
        for(int i = 0; i < dominoes.length; i++) {
            int a = Math.min(dominoes[i][0], dominoes[i][1]);
            int b = Math.max(dominoes[i][0], dominoes[i][1]);
            temp[i] = a * 10 + b;
        }       
        // Encoded range will between 11 to 99
        int[] index = new int[100];
        for(int i = 0; i < temp.length; i++) {
            index[temp[i]]++;
        }
        int count = 0;
        for(int i = 0; i < 100; i++) {
            // Return the number of pairs (i, j) for which 0 <= i < j < dominoes.length, 
            // and dominoes[i] is equivalent to dominoes[j].
            // e.g [[1,2],[1,2],[1,2],[1,1],[2,2]] -> 3 pairs (i = 0, j = 1), 
            // (i = 0, j = 2), (i = 1, j = 2)
            // e.g [[1,2],[2,1],[3,4],[5,6]] -> 1 pair (i = 0, j = 1)
            if(index[i] > 1) {
                count += (index[i] * (index[i] - 1)) / 2;
            }
        }
        return count;
    }
}
