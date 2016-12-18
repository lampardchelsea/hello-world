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

// Solution 2: Improvement of Solution 1 --> No need initial HashMap
// Refer to
// http://blog.csdn.net/mebiuw/article/details/52724207
public class Solution {
    public int longestPalindrome(String s) {
        char[] chars = s.toCharArray();
        int length = chars.length;
        int[] lowerCount = new int[26];
        int[] upperCount = new int[26];
        
        for(int i = 0; i < length; i++) {
            if(chars[i] < 'a') {
                upperCount[chars[i] - 'A']++;
            } else {
                lowerCount[chars[i] - 'a']++;
            }
        }
        
        int odds = 0;
        for(int i = 0; i < 26; i++) {
            if(lowerCount[i] % 2 == 1) {
                odds++;
            }
            if(upperCount[i] % 2 == 1) {
                odds++;
            }
        }
        
        if(odds == 0) {
            return length;
        } else {
            return length - odds + 1;
        }
    }
}


// Solution 3: 
// Refer to
// http://www.cnblogs.com/grandyang/p/5931874.html
// 上面那种方法是通过哈希表来建立字符串和其出现次数的映射，这里我们可以换一种思路，来找出搜有奇数个的字符，
// 我们采用的方法是使用一个set集合，如果遍历到的字符不在set中，那么就将其加入set，如果已经在set里了，
// 就将其从set中删去，这样遍历完成后set中就是所有出现个数是奇数个的字符了，那么我们最后只要用s的长度减去0
// 和set长度减一之间的较大值即可，为啥这样呢，我们想，如果没有出现个数是奇数个的字符，那么t的长度就是0，
// 减1成了-1，那么s的长度只要减去0即可；如果有奇数个的字符，那么字符个数减1，就是不能组成回文串的字符，
// 因为回文串最多允许一个不成对出现的字符











