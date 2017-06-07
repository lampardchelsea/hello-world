/**
 * Refer to
 * https://leetcode.com/problems/decode-ways/#/description
 *  A message containing letters from A-Z is being encoded to numbers using the following mapping:

	'A' -> 1
	'B' -> 2
	...
	'Z' -> 26

 * Given an encoded message containing digits, determine the total number of ways to decode it.
 * For example,
 * Given encoded message "12", it could be decoded as "AB" (1 2) or "L" (12).
 * The number of ways decoding "12" is 2. 
 * 
 * Solution
 * https://segmentfault.com/a/1190000003813921
 * 动态规划
 * 复杂度
 * 时间 O(N) 空间 O(N)
 * 思路
 * 解码是有规律的，所以我们可以尝试动态规划。假设数组dp[i]表示从头到字符串的第i位，一共有多少种解码方法的话，
 * 那么如果字符串的第i-1位和第i位能组成一个10到26的数字，说明我们是在第i-2位的解码方法上继续解码。如果字符
 * 串的第i-1位和第i位不能组成有效二位数字，而且第i位不是0的话，说明我们是在第i-1位的解码方法上继续解码。
 * 所以，如果两个条件都符合，则dp[i]=dp[i-1]+dp[i-2]，否则dp[i]=dp[i-1]。
 * 注意
 * 如果出现无法被两位数接纳的0，则无法解码，我们可以在一开始就判断，并将其初始化为0，这样后面的相加永远都是加0
 */
public class DecodeWays {
    public int numDecodings(String s) {
        if(s.length() == 0) return s.length();
        int[] dp = new int[s.length() + 1];
        // 初始化第一种解码方式
        dp[0] = 1;
        // 如果第一位是0，则无法解码
        dp[1] = s.charAt(0) == '0' ? 0 : 1;
        for(int i = 2; i <= s.length(); i++){
            // 如果字符串的第i-1位和第i位能组成一个10到26的数字，说明我们可以在第i-2位的解码方法上继续解码
            if(Integer.parseInt(s.substring(i-2, i)) <= 26 && Integer.parseInt(s.substring(i-2, i)) >= 10){
                dp[i] += dp[i - 2];
            }
            // 如果字符串的第i-1位和第i位不能组成有效二位数字，在第i-1位的解码方法上继续解码
            if(Integer.parseInt(s.substring(i-1, i)) != 0){
                dp[i] += dp[i - 1];
            }
        }
        return dp[s.length()];
    }
    
    public static void main(String[] args) {
    	
    }
}

