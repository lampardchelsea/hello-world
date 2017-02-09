/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/4741934.html
 * 这道题让我们检测一个数是否为丑陋数，所谓丑陋数就是其质数因子只能是2,3,5。
 * 那么最直接的办法就是不停的除以这些质数，如果剩余的数字是1的话就是丑陋数了
*/
public class Solution {
    public boolean isUgly(int num) {
        while(num >= 2) {
            if(num % 2 == 0) {
                num = num / 2;
            } else if(num % 3 == 0) {
                num = num / 3;
            } else if(num % 5 == 0) {
                num = num / 5;
            } else {
                return false;
            }
        }
        return num == 1;
    }
}
