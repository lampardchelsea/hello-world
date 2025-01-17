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

// Solution 2: Improvement of Solution 1(Most quickly solution) --> No need initial HashMap
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
public class Solution {
    public int longestPalindrome(String s) {
        Set<Character> set = new HashSet<Character>();
        int count = 0;
        int length = s.length();
        for(int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if(set.contains(c)) {
                set.remove(c);
                // count means how many pairs we have
                count++;
            } else {
                set.add(c);
            }
        }
        if(!set.isEmpty()) {
            return count * 2 + 1;
        } else {
            return count * 2;
        }
    }
}

























https://leetcode.com/problems/longest-palindrome/description/
Given a string s which consists of lowercase or uppercase letters, return the length of the longest palindrome
that can be built with those letters.
Letters are case sensitive, for example, "Aa" is not considered a palindrome.

Example 1:
Input: s = "abccccdd"
Output: 7
Explanation: One longest palindrome that can be built is "dccaccd", whose length is 7.

Example 2:
Input: s = "a"
Output: 1
Explanation: The longest palindrome that can be built is "a", whose length is 1.
 
Constraints:
- 1 <= s.length <= 2000
- s consists of lowercase and/or uppercase English letters only.
--------------------------------------------------------------------------------
Attempt 1: 2025-01-16
Solution 1: Hash Table (10 min)
class Solution {
    public int longestPalindrome(String s) {
        int oddCount = 0;
        Map<Character, Integer> freq = new HashMap<>();
        for(char c : s.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
            if(freq.get(c) % 2 == 1) {
                oddCount++;
            } else {
                oddCount--;
            }
        }
        if(oddCount > 1) {
            return s.length() - oddCount + 1;
        }
        return s.length();
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Refer to
https://leetcode.com/problems/longest-palindrome/solutions/3156147/c-easiest-beginner-friendly-sol-o-n-time-and-o-128-o-1-space/
1.Initialize two variables, oddCount to store the number of characters with an odd count of occurrences and an unordered map ump to store the count of each character in the string.
2.Iterate through the string and for each character ch, increment the count of that character in the unordered map.
3.If the count of the current character ch is odd, increment oddCount. If the count is even, decrement oddCount.
4.If oddCount is greater than 1, return s.length() - oddCount + 1, which is the maximum length of a palindrome that can be formed by using all but one character with an odd count of occurrences.
5.If oddCount is not greater than 1, return s.length(), which is the length of the original string, as all characters can be used to form a palindrome.
class Solution {
    public int longestPalindrome(String s) {
        int oddCount = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (char ch : s.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
            if (map.get(ch) % 2 == 1)
                oddCount++;
            else
                oddCount--;
        }
        if (oddCount > 1)
            return s.length() - oddCount + 1;
        return s.length();
    }
}

https://leetcode.com/problems/longest-palindrome/solutions/89604/simple-hashset-solution-java/
public int longestPalindrome(String s) {
    if(s==null || s.length()==0) return 0;
    HashSet<Character> hs = new HashSet<Character>();
    int count = 0;
    for(int i=0; i<s.length(); i++){
        if(hs.contains(s.charAt(i))){
            hs.remove(s.charAt(i));
            count++;
        }else{
            hs.add(s.charAt(i));
        }
    }
    if(!hs.isEmpty()) return count*2+1;
    return count*2;
}
