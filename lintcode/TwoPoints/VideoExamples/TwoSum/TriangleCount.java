/**
 * Refer to
 * http://www.lintcode.com/en/problem/triangle-count/
 * Given an array of integers, how many three numbers can be found in the array, 
   so that we can build an triangle whose three edges length is the three numbers that we find?

    Have you met this question in a real interview? Yes
    Example
    Given array S = [3,4,6,7], return 3. They are:

    [3,4,6]
    [3,6,7]
    [4,6,7]
    Given array S = [4,4,4,4], return 4. They are:

    [4(1),4(2),4(3)]
    [4(1),4(2),4(4)]
    [4(1),4(3),4(4)]
    [4(2),4(3),4(4)]
 *
 * Solution
 * http://www.cnblogs.com/Dylan-Java-NYC/p/6362616.html
 * 
 * 
*/
public class Solution {
    /*
     * @param S: A list of integers
     * @return: An integer
     */
    public int triangleCount(int[] S) {
        if(S == null || S.length < 3) {
            return 0;
        }
        Arrays.sort(S);
        // Condition
        // If 3 edges are a, b, c
        // when a < b < c and a + b > c, they can build a triangle
        // to satisfy a < b < c, need to sort given S first then
        // then preivous two edges can be represent by entry before
        // the third edge, e.g start ... end | i, so maximum value
        // of end is (i - 1)
        int count = 0;
        for(int i = 2; i < S.length; i++) {
            int start = 0;
            int end = i - 1;
            while(start < end) {
                if(S[start] + S[end] > S[i]) {
                    count += end - start;
                    end--;
                } else {
                    start++;
                }
            }
        }
        return count;
    }
}
