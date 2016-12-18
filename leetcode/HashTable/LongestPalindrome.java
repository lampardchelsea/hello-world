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


public class LongestPalidrome {
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
