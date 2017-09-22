/**
 * Refer to
 * http://www.lintcode.com/en/problem/sqrtx/
 * Implement int sqrt(int x).

  Compute and return the square root of x.

  Have you met this question in a real interview? Yes
  Example
  sqrt(3) = 1

  sqrt(4) = 2

  sqrt(5) = 2

  sqrt(10) = 3
 *
 * Solution
 * https://aaronice.gitbooks.io/lintcode/content/number/sqrt_x.html
 *
 * The template below
    int start = 1, end = max // 1. 找到可行解范围
    while(start + 1 < end) {
        int mid = start + (end - start) / 2; // 2.猜答案
      if(check(mid)) {  // 3.检验答案
          start = mid; // 4. 调查搜索范围 
      } else {
          end = mid; // 4. 调查搜索范围
      }
    }
*/

public class Sqrtx {
	public int mySqrt(int x) {
        if(x == 0) {
            return 0;
        }
        // Caution: Using 'long' instead of 'int'
        // E.g 
        // Input: 2147395599
        // Output: 2147395598
        // Expected: 46339
        // When given this case, mid * mid over integer range −32,768 to 32,767
        long start = 1, end = x;
        while(start + 1 < end) {
            long mid = start + (end - start) / 2;
            if(mid * mid < x) {
                start = mid;
            } else if(mid * mid > x){
                end = mid;
            } else {
            	// When mid * mid == x, must assign to start not end, why ?
            	// Because if evolution a number only take its floor value (not ceiling)
            	// E.g Evolution 90 => 9
            	// Refer to
            	// http://www.cnblogs.com/yuzhangcmu/p/4198959.html
            	// 其实这里有一个非常trick地地方：就是当循环终止的时候，l一定是偏小，r一定是偏大（实际的值是介于l和r之间的）：
            	// 比如以下的例子，90开根号是9.48 按照开方向下取整的原则, 我们应该返回L.
            	// 以下展示了在循环过程中，L,R两个变量的变化过程
                start = mid;
            }
        }
        if(start * start <= x) {
            return (int)start;
        } else {
            return (int)end;
        }
    }
	
	public static void main(String[] args) {
		Sqrtx s = new Sqrtx();
		int x = 2147395599;
		int result = s.mySqrt(x);
		System.out.print(result);
	}
}
