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
