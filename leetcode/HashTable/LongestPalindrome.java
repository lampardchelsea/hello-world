/**
 * Given a string which consists of lowercase or uppercase letters, find the length of the longest palindromes 
 * that can be built with those letters.
 * This is case sensitive, for example "Aa" is not considered a palindrome here.
 * Note:
 * Assume the length of given string will not exceed 1,010.
 * Example:
    Input: "abccccdd"
    Output: 7
 * Explanation:
 * One longest palindrome that can be built is "dccaccd", whose length is 7.
*/
// Refer to
// http://www.cnblogs.com/grandyang/p/5931874.html
// http://blog.csdn.net/mebiuw/article/details/52724207

// Wrong Solution:
// This solution has problem on calculating odd frequence character, should NOT
// only record the max odd frequence character, other odd numbers also
// need calculate into final palidrome string, only need to minus 1
// on each not max odd frequence character
import java.util.HashMap;
import java.util.Map;


public class LongestPalindrome {
    public static int longestPalindrome(String s) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(!map.containsKey(c)) {
                map.put(c, 1);
            } else {
                map.put(c, map.get(c) + 1);
            }
        }
        
        int evenNums = 0;
        int oddNums = 0;
        for(Integer i : map.values()) {
            if(i % 2 == 0) {
                evenNums += i;
            } else {
                if(oddNums < i) {
                    oddNums = i;
                }
            }
        }
        
        return oddNums + evenNums;
    }
    
    public static void main(String[] args) {
    	//String s = "bananas";
    	String s = "civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth";
    	int result = longestPalindrome(s);
    	System.out.println(result);
    }

}


// Solution 1:
// Refer to
// http://blog.csdn.net/mebiuw/article/details/52724207
// 这道题并不是求字符串里最长的回文串，而是说用这些字符可以构成多长的回文串。
// 所谓的回文串，就是要左右对称，所以除了中心的那个位置的字符可以出现奇数次以外，都要出现偶数次。
// 如此，方式就很简单了： 
// 1、统计所有字母的出现频率（分大小写） 
// 2、统计只出现奇数次数字母的个数
// 3、如果2中结果不为0，字符串的长度减去2中的字母个数+1
// 其中3的意思是，保留出现次数最多的那个奇数字母，剩下的需要全部减1变成偶数去构造
public class Solution {
    public int longestPalindrome(String s) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(!map.containsKey(c)) {
                map.put(c, 1);
            } else {
                map.put(c, map.get(c) + 1);
            }
        }
        
        int evenNums = 0;
        int howManyOddNums = 0;
        for(Integer i : map.values()) {
            if(i % 2 == 0) {
                evenNums += i;
            } else {
                howManyOddNums++;
            }
        }
        
        if(howManyOddNums == 0) {
            return evenNums;
        } else {
            // 如果字符串中字母出现次数为奇数的个数不为0，字符串的长度减去字母出现次数为奇数个数+1
            // +1和-howManyOddNums的意思：保留出现次数最多的那个奇数字母(+1)，剩下的需要
            // 全部减1(-howManyOddNums)变成偶数去构造
            return s.length() - howManyOddNums + 1;
        }

    }
}

// Solution 2: Improvement of Solution 1
// Refer to
// http://blog.csdn.net/mebiuw/article/details/52724207


