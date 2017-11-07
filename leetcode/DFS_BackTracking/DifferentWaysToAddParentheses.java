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
