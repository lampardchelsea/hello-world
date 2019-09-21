/**
 Refer to
 https://leetcode.com/problems/binary-watch/
 A binary watch has 4 LEDs on the top which represent the hours (0-11), 
 and the 6 LEDs on the bottom represent the minutes (0-59).
 Each LED represents a zero or one, with the least significant bit on the right.
 For example, the above binary watch reads "3:25".

Given a non-negative integer n which represents the number of LEDs that are currently on, 
return all possible times the watch could represent.

Example:
Input: n = 1
Return: ["1:00", "2:00", "4:00", "8:00", "0:01", "0:02", "0:04", "0:08", "0:16", "0:32"]
Note:
The order of output does not matter.
The hour must not contain a leading zero, for example "01:00" is not valid, it should be "1:00".
The minute must be consist of two digits and may contain a leading zero, for example "10:2" is not valid, it should be "10:02".
*/

// Solution 1: 3ms Java Solution Using Backtracking and Idea of "Permutation and Combination"
// Refer to
// https://leetcode.com/problems/binary-watch/discuss/88456/3ms-Java-Solution-Using-Backtracking-and-Idea-of-%22Permutation-and-Combination%22
class Solution {
    public List<String> readBinaryWatch(int num) {
        List<String> result = new ArrayList<String>();
        int[] nums1 = new int[]{8,4,2,1};
        int[] nums2 = new int[]{32,16,8,4,2,1};
        for(int i = 0; i <= num; i++) {
            List<Integer> list1 = new ArrayList<Integer>();
            helper(list1, nums1, i, 0, 0);
            List<Integer> list2 = new ArrayList<Integer>();
            helper(list2, nums2, num - i, 0, 0);
            for(int j = 0; j < list1.size(); j++) {
                int num1 = list1.get(j);
                // Limitation of hour < 12
                if(num1 >= 12) {
                    continue;
                }
                for(int k = 0; k < list2.size(); k++) {
                    int num2 = list2.get(k);
                    // Limitation of minute < 60
                    if(num2 >= 60) {
                        continue;
                    }
                    result.add(num1 + ":" + (num2 < 10 ? "0" + num2 : num2));
                }
            }
        }
        return result;
    }
    
    // Use combination (not permutation (no visited boolean array)) to find sum, since any
    // order of same number combination will generate same result
    private void helper(List<Integer> list, int[] nums, int count, int index, int sum) {
        if(count == 0) {
            list.add(sum);
            return;
        }
        for(int i = index; i < nums.length; i++) {
            sum += nums[i];
            helper(list, nums, count - 1, i + 1, sum);
            sum -= nums[i];
        }
    }
}
