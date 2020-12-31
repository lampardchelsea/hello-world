/**
Refer to
https://leetcode.com/problems/sort-array-by-increasing-frequency/
Given an array of integers nums, sort the array in increasing order based on the frequency of the values. 
If multiple values have the same frequency, sort them in decreasing order.

Return the sorted array.

Example 1:
Input: nums = [1,1,2,2,2,3]
Output: [3,1,1,2,2,2]
Explanation: '3' has a frequency of 1, '1' has a frequency of 2, and '2' has a frequency of 3.

Example 2:
Input: nums = [2,3,1,3,2]
Output: [1,3,3,2,2]
Explanation: '2' and '3' both have a frequency of 2, so they are sorted in decreasing order.

Example 3:
Input: nums = [-1,1,-6,4,5,-6,1,4,1]
Output: [5,-1,4,4,-6,-6,1,1,1]

Constraints:
1 <= nums.length <= 100
-100 <= nums[i] <= 100
*/

// Solution 1: Heap with customize sort + HashMap
// Refer to
// https://leetcode.com/problems/sort-array-by-increasing-frequency/discuss/917826/javaPython-3-Sort-frequency-followed-by-value./770054
/**
public int[] frequencySort(int[] nums) {
    int n = nums.length, res[] = new int[n];
        
    Map<Integer, Integer> map = new HashMap<>();
    PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> map.get(a) != map.get(b) ? map.get(b) - map.get(a) : a - b);
    //heap will remove elements with higher frequency first
    //if two or more elements have same frequency then smaller element will get removed first
     
    for(int e : nums)
        map.merge(e, 1, Integer::sum);
      
    pq.addAll(map.keySet());
        
    while(n > 0){
        int curr = pq.poll();
        int freq = map.get(curr);
        while(freq-- > 0)
            res[--n] = curr;
    }
    return res;
}
*/
class Solution {
    public int[] frequencySort(int[] nums) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        PriorityQueue<Integer> minPQ = new PriorityQueue<Integer>((a, b) -> (map.get(a) != map.get(b) ? map.get(a) - map.get(b) : b - a));
        for(int key : map.keySet()) {
            minPQ.offer(key);
        }
        int[] result = new int[nums.length];
        int idx = 0;
        while(!minPQ.isEmpty()) {
            int key = minPQ.poll();
            int freq = map.get(key);
            for(int i = 0; i < freq; i++) {
                result[idx + i] = key;
            }
            idx += freq;
        }
        return result;
    }
}
