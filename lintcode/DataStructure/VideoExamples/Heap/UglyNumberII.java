/**
 * Refer to
 * http://www.lintcode.com/en/problem/ugly-number-ii/#
 * Ugly number is a number that only have factors 2, 3 and 5.
    Design an algorithm to find the nth ugly number. The first 10 ugly numbers are 1, 2, 3, 4, 5, 6, 8, 9, 10, 12...

     Notice

    Note that 1 is typically treated as an ugly number.

    Have you met this question in a real interview? Yes
    Example
    If n=9, return 10.
 *
 * Solution
 * http://www.jiuzhang.com/solutions/ugly-number-ii/
*/
// Solution 1: Scan
public class Solution {
    /*
     * @param n: An integer
     * @return: the nth prime number as description.
     */
    public int nthUglyNumber(int n) {
        // Create ArrayList in case of 'get(index)' operation
        List<Integer> result = new ArrayList<Integer>();
        result.add(1);
        // p2, p3 & p5 share the same queue: result
        int p2 = 0;
        int p3 = 0;
        int p5 = 0;
        for(int i = 1; i < n; i++) {
            int lastNum = result.get(i - 1);
            while(result.get(p2) * 2 <= lastNum) {
                p2++;
            }
            while(result.get(p3) * 3 <= lastNum) {
                p3++;
            }
            while(result.get(p5) * 5 <= lastNum) {
                p5++;
            }
            int next = Math.min(result.get(p2) * 2, Math.min(result.get(p3) * 3, result.get(p5) * 5));
            result.add(next);
        }
        return result.get(n - 1);
    }
}

