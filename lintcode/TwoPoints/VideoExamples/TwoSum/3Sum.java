/**
 * Refer to
 * http://www.lintcode.com/en/problem/3sum/
 * Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0? 
   Find all unique triplets in the array which gives the sum of zero.
   Notice
   Elements in a triplet (a,b,c) must be in non-descending order. (ie, a ≤ b ≤ c)
   The solution set must not contain duplicate triplets.
    Have you met this question in a real interview? Yes
    Example
    For example, given array S = {-1 0 1 2 -1 -4}, A solution set is:

    (-1, 0, 1)
    (-1, -1, 2)
 *
 *
 * Solution
 * http://www.jiuzhang.com/solutions/3sum/
 *
*/
public class Solution {
    /*
     * @param numbers: Give an array numbers of n integer
     * @return: Find all unique triplets in the array which gives the sum of zero.
     */
    public List<List<Integer>> threeSum(int[] numbers) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(numbers == null || numbers.length == 0) {
            return result;
        }
        // First need to sort as ascending order
        Arrays.sort(numbers);
        for(int i = 0; i < numbers.length - 2; i++) {
            // Check Point 1: skip duplicate triples with the same first numeber
            // E.g if without this check
            // Input
            // [1,0,-1,-1,-1,-1,0,1,1,1]
            // Output
            // [[-1,0,1],[-1,0,1],[-1,0,1],[-1,0,1],[-1,0,1],[-1,0,1],[-1,0,1],[-1,0,1]]
            // Expected
            // [[-1,0,1]]
            // After first sorted
            // [-1,-1,-1,-1,0,0,1,1,1,1]
            // With this check we will remove '-1' on index = 1,2,3 as duplicate as '-1'
            // on index = 0
            if(i > 0 && numbers[i] == numbers[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = numbers.length - 1;
            int target = -numbers[i];
            twoSum(numbers, left, right, target, result);
        }
        return result;
    }
    
    private void twoSum(int[] numbers, int left, int right, int target, List<List<Integer>> result) {
        while(left < right) {
            int temp = numbers[left] + numbers[right];
            if(temp == target) {
                List<Integer> list = new ArrayList<Integer>();
                list.add(-target);
                list.add(numbers[left]);
                list.add(numbers[right]);
                result.add(list);
                left++;
                right--;
                // Check Point 2: skip duplicate pairs with the same left
                // E.g if without this check (even with Check Point 1)
                // Input
                // [1,0,-1,-1,-1,-1,0,1,1,1]
                // After Check Point 1 =>
                // [-1,0,0,1,1,1,1]
                // Output
                // [[-1,0,1],[-1,0,1]]
                // Expected
                // [[-1,0,1]]
                // With this check we will remove combination including second '0' 
                // at index = 2, as same combination as [-1,0,1] which include 
                // first '0' at index = 1
                while(left < right && numbers[left] == numbers[left - 1]) {
                    left++;
                }
                // Check Point 3: skip duplicate pairs with the same right
                while(left < right && numbers[right] == numbers[right + 1]) {
                    right--;
                }
            } else if(temp < target) {
                left++;
            } else {
                right--;
            }
        } 
    }
}
