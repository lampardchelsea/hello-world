L904/P2.4.Fruit Into Baskets (Same as L340)
https://leetcode.com/problems/fruit-into-baskets/

You are visiting a farm that has a single row of fruit trees arranged from left to right. The trees are represented by 
an integer array fruits where fruits[i] is the type of fruit the ith tree produces.

You want to collect as much fruit as possible. However, the owner has some strict rules that you must follow:

- You only have two baskets, and each basket can only hold a single type of fruit. There is no limit on the amount of fruit each basket can hold.
- Starting from any tree of your choice, you must pick exactly one fruit from every tree (including the start tree) 
    while moving to the right. The picked fruits must fit in one of your baskets.
- Once you reach a tree with fruit that cannot fit in your baskets, you must stop.
Given the integer array fruits, return the maximum number of fruits you can pick.

Example 1:
Input: fruits = [1,2,1]
Output: 3
Explanation: We can pick from all 3 trees.

Example 2:
Input: fruits = [0,1,2,2]
Output: 3
Explanation: We can pick from trees [1,2,2].
If we had started at the first tree, we would only pick from trees [0,1].

Example 3:
Input: fruits = [1,2,3,2,2]
Output: 4
Explanation: We can pick from trees [2,3,2,2].
If we had started at the first tree, we would only pick from trees [1,2].

Constraints:
- 1 <= fruits.length <= 105
- 0 <= fruits[i] < fruits.length


Attempt 1: 2022-09-10

Solution 1 (5min)

class Solution { 
    // Equal to find longest subarray contains at most two distinct number 
    public int totalFruit(int[] fruits) { 
        Map<Integer, Integer> freq = new HashMap<Integer, Integer>(); 
        int i = 0; 
        int len = fruits.length; 
        int maxLen = 0; 
        for(int j = 0; j < len; j++) { 
            freq.put(fruits[j], freq.getOrDefault(fruits[j], 0) + 1); 
            int size = freq.size(); 
            while(size > 2) { 
                freq.put(fruits[i], freq.get(fruits[i]) - 1); 
                if(freq.get(fruits[i]) == 0) { 
                    freq.remove(fruits[i]); 
                    size--; 
                } 
                i++; 
            } 
            maxLen = Math.max(maxLen, j - i + 1); 
        } 
        return maxLen; 
    } 
}

Space Complexity: O(n) 
Time Complexity: O(n)


Solution 2 (10 min)

class Solution {
    // Equal to find longest subarray contains at most two distinct number
    public int totalFruit(int[] fruits) {
        Map<Integer, Integer> index_map = new HashMap<Integer, Integer>();
        int i = 0;
        int len = fruits.length;
        int maxLen = 0;
        for(int j = 0; j < len; j++) {
            index_map.put(fruits[j], j);
            int size = index_map.size();
            if(size > 2) {
                // Shrink left end to skip all same value of fruits[i]
                // and reach to the very last one of fruits[i]
                while(index_map.get(fruits[i]) != i) {
                    i++;
                }
                // Since our purpose is removing fruits[i] totally from
                // index map, we can remove it first then update i to
                // next poistion which point to a new value differ than
                // fruits[i], and no need 'size--', since we remove key
                // fruits[i] already, in next for loop iteration, size
                // will auto re-calculate
                index_map.remove(fruits[i]);
                i++;
            }
            maxLen = Math.max(maxLen, j - i + 1);
        }
        return maxLen;
    }
}

Space Complexity: O(n) 
Time Complexity: O(n)
