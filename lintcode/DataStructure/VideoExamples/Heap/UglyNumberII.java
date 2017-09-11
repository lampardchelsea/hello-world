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
// Solution 1: Scan (O(n) time)
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


// Solution 2: HashSet + PriorityQueue (O(nlogn) time)
public class Solution {
    /**
	 * For using Long instead of Integer, because in solution 2 is little different
	 * than solution 1, the 'num * primes[j]' in solution 2 will over Integer boundary
	 * in big n case, e.g when n = 1407, similar but different in solution 1 is not
	 * same case, because this 'num' in solution 2 is exactly same one that will 
	 * multiple 2/3/5, but in solution 1, this 'num' is not exactly same one, e.g
	 * because limitation of 'num' = result.get(p2/p3/p5) * primes[j] <= lastNum', here,
	 * p2/p3/p5 may different, and cause 'num' also different 
	 */
	public int nthUglyNumber3(int n) {
        Queue<Long> queue = new PriorityQueue<Long>();
        // Set is necessary to filter duplicate numbers
        // e.g given n = 9, expected num = 10, but return 9,
        //     because duplicate 6 adding onto queue based
        //     on first time 2 * 3 and later 3 * 2
        Set<Long> set = new HashSet<Long>();
        int[] primes = new int[3];
        primes[0] = 2;
        primes[1] = 3;
        primes[2] = 5;
        for(int i = 0; i < 3; i++) {
            queue.offer(Long.valueOf(primes[i]));
            set.add(Long.valueOf(primes[i]));
        }
        Long num = 1L;
        for(int i = 1; i < n; i++) {
            num = queue.poll();
            for(int j = 0; j < 3; j++) {
                if(!set.contains(num * primes[j])) {
                    queue.offer(num * primes[j]);
                    set.add(num * primes[j]);    
                }
            }
        }
        return num.intValue();
    }
}
