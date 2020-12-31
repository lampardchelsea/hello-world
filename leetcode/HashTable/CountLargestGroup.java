/**
Refer to
https://leetcode.com/problems/count-largest-group/
Given an integer n. Each number from 1 to n is grouped according to the sum of its digits. 

Return how many groups have the largest size.

Example 1:
Input: n = 13
Output: 4
Explanation: There are 9 groups in total, they are grouped according sum of its digits of numbers from 1 to 13:
[1,10], [2,11], [3,12], [4,13], [5], [6], [7], [8], [9]. There are 4 groups with largest size.

Example 2:
Input: n = 2
Output: 2
Explanation: There are 2 groups [1], [2] of size 1.

Example 3:
Input: n = 15
Output: 6

Example 4:
Input: n = 24
Output: 5

Constraints:
1 <= n <= 10^4
*/

// Solution 1: Native HashMap
class Solution {
    public int countLargestGroup(int n) {
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        for(int i = 1; i <= n; i++) {
            int val = i;
            int sum = 0;
            while(val > 0) {
                sum += val % 10;
                val /= 10;
            }
            map.putIfAbsent(sum, new ArrayList<Integer>());
            map.get(sum).add(i);
        }
        int max_size = 0;
        for(Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            if(max_size < entry.getValue().size()) {
                max_size = entry.getValue().size();
            }
        }
        int count = 0;
        for(Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            if(entry.getValue().size() == max_size) {
                count++;
            }
        }
        return count;
    }
}
