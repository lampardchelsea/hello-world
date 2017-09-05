/**
 * Refer to
 * http://www.lintcode.com/en/problem/two-sum/
 * Given an array of integers, find two numbers such that they add up to a specific target number.

   The function twoSum should return indices of the two numbers such that they add up to the target, 
   where index1 must be less than index2. Please note that your returned answers (both index1 and index2) 
   are NOT zero-based.
   
    Notice
    You may assume that each input would have exactly one solution
    Have you met this question in a real interview? Yes
    Example
    numbers=[2, 7, 11, 15], target=9

    return [1, 2]

 * Solution
 * https://discuss.leetcode.com/topic/2447/accepted-java-o-n-solution/3?page=1
 * 
*/
public class Solution {
    /*
     * @param numbers: An array of Integer
     * @param target: target = numbers[index1] + numbers[index2]
     * @return: [index1 + 1, index2 + 1] (index1 < index2)
     */
    public int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int[] result = new int[2];
        for(int i = 0; i < numbers.length; i++) {
            if(map.containsKey(target - numbers[i])) {
                // Important: As description as "where index1 must be less than index2"
                // we must set result[0] and result[1] as below, cannot reverse
                // E.g numbers = {2,7,11,15}, target = 9,
                // you will put 2 onto map first as {2 = 0}, then continue next loop,
                // check (9 - 7 = 2) if exist or not, actually 7 not yet put on map,
                // but already find its counterpart 2 on the map at index 0,
                // and as requirement result[0] must < result[1]
                // so return not zero based result[0] = 1, result[1] = 2
                result[0] = map.get(target - numbers[i]) + 1;
                result[1] = i + 1;
            }
            map.put(numbers[i], i);
        }
        return result;
    }
}
