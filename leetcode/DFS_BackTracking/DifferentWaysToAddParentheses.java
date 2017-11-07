/**
 * Refer to
 * https://leetcode.com/problems/different-ways-to-add-parentheses/description/ 
 * Given a string of numbers and operators, return all possible results from computing all the 
   different possible ways to group numbers and operators. The valid operators are +, - and *.

    Example 1
    Input: "2-1-1".

    ((2-1)-1) = 0
    (2-(1-1)) = 2
    Output: [0, 2]


    Example 2
    Input: "2*3-4*5"

    (2*(3-(4*5))) = -34
    ((2*3)-(4*5)) = -14
    ((2*(3-4))*5) = -10
    (2*((3-4)*5)) = -10
    (((2*3)-4)*5) = 10
    Output: [-34, -14, -10, -10, 10]
 *
 * Solution
 * https://discuss.leetcode.com/topic/19901/a-recursive-java-solution-284-ms?page=1
 * https://discuss.leetcode.com/topic/19906/c-4ms-recursive-dp-solution-with-brief-explanation
 * https://discuss.leetcode.com/topic/25490/share-a-clean-and-short-java-solution/2
 * https://www.youtube.com/watch?v=gxYV8eZY0eQ
 * https://en.wikipedia.org/wiki/Cartesian_product
*/
// Solution 1: DFS (Divide and Conquer)
class Solution {
    public List<Integer> diffWaysToCompute(String input) {
        List<Integer> result = new ArrayList<Integer>();
        if(input == null || input.length() == 0) {
            return result;
        }
        for(int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if(c == '+' || c == '*' || c == '-') {
                String part1 = input.substring(0, i);
                String part2 = input.substring(i + 1);
                List<Integer> part1Result = diffWaysToCompute(part1);
                List<Integer> part2Result = diffWaysToCompute(part2);
                // Use basic rule as 'Cartesian Product'
                // Refer to
                // https://www.youtube.com/watch?v=gxYV8eZY0eQ
                // https://en.wikipedia.org/wiki/Cartesian_product
                for(int m : part1Result) {
                    for(int n : part2Result) {
                        int temp = 0;
                        if(c == '+') {
                            temp = m + n;
                        } else if(c == '-') {
                            temp = m - n;
                        } else if(c == '*') {
                            temp = m * n;
                        }
                        result.add(temp);
                    }
                }
            }
        }
        // Note: Critical terminate condition as store only numeric number
        // present in current level 'result' (recursively start from deepest
        // level as single number) for upper level compute
        if(result.size() == 0) {
            result.add(Integer.valueOf(input));
        }
        return result;
    }
}
