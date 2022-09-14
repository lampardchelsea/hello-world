/**
 * Given a string s and a non-empty string p, find all the start indices of p's anagrams in s.
 * Strings consists of lowercase English letters only and the length of both strings s and p will not be larger than 20,100.
 * The order of output does not matter.
 * Example 1:
 * Input: s: "cbaebabacd" p: "abc"
 * Output: [0, 6]
 * Explanation:
 * The substring with start index = 0 is "cba", which is an anagram of "abc".
 * The substring with start index = 6 is "bac", which is an anagram of "abc".
 * 
 * Example 2:
 * Input: s: "abab" p: "ab"
 * Output: [0, 1, 2]
 * Explanation:
 * The substring with start index = 0 is "ab", which is an anagram of "ab".
 * The substring with start index = 1 is "ba", which is an anagram of "ab".
 * The substring with start index = 2 is "ab", which is an anagram of "ab".
*/
// Solution 1: Time Exceeded
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindAllAnagramsOfAString {
    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<Integer>();
        for(int i = 0; i <= s.length() - p.length(); i++) {
        	String temp = s.substring(i, i + p.length());
        	if(isAnagram(temp, p)) {
        		result.add(i);
        	}
        }
        return result;
    }
	
    public static boolean isAnagram(String temp, String p) {
    	char[] a = temp.toCharArray();
    	char[] b = p.toCharArray();
    	Arrays.sort(a);
    	Arrays.sort(b);
    	for(int i = 0; i < a.length; i++) {
    		if(a[i] != b[i]) {
    			return false;
    		}
    	}
    	return true;
    }
    
    
	public static void main(String[] args) {
		// s: "cbaebabacd" p: "abc"
		String s = "cbaebabacd";
		String p = "abc";
//		String s = "abab";
//		String p = "ab";
		List<Integer> result = findAnagrams(s, p);
		System.out.println(result);
	}
}


// Solution 2: Shortest/Concise JAVA O(n) Sliding Window Solution
// Refer to 
// https://discuss.leetcode.com/topic/30941/here-is-a-10-line-template-that-can-solve-most-substring-problems
// https://leetcode.com/problems/find-all-anagrams-in-a-string/ --> Top Solution
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindAllAnagramsOfAString {
    public static List<Integer> findAnagrams(String s, String p) {
    	List<Integer> list = new ArrayList<>();
        if (s == null || s.length() == 0 || p == null || p.length() == 0) return list;
        int[] hash = new int[256]; //character hash
        //record each character in p to hash
        for (char c : p.toCharArray()) {
            hash[c]++;
        }
        //two points, initialize count to p's length
        int left = 0, right = 0, count = p.length();
        while (right < s.length()) {
            //move right every time, if the character exists in p's hash, decrease the count
            //current hash value >= 1 means the character is existing in p
            if (hash[s.charAt(right)] >= 1) {
            	count--; 
            }
        	
            // Be careful, below two statements should NOT exchange order,
            // first minus current character in String s corresponding value 
            // in hash, then increase pointer for next character in String s.
            hash[s.charAt(right)]--;
            right++;
            
            /**
             * Note: The above code section can be write in simple line
             * if (hash[s.charAt(right++)]-- >= 1) count--;
             */
            
            //when the count is down to 0, means we found the right anagram
            //then add window's left to result list
            if (count == 0) {
            	list.add(left);
            }
            
            //if we find the window's size equals to p, then we have to move left (narrow the window) to find the new match window
            if (right - left == p.length()) {
        		// only increase the count if the character is in p
        		// hash[s.charAt(left)] >= 0 indicate it was original in the hash, cuz it won't go below 0
            	if(hash[s.charAt(left)] >= 0) {
            		count++;
            	}
            	// ++ to reset the hash because we kicked out the left
                // Be careful, below two statements should NOT exchange order,
                // first increase current character in String s corresponding value 
                // in hash, then increase pointer for next character in String s.
            	hash[s.charAt(left)]++;
            	left++;
            }
            
            /**
             * Note: The above code section can be write in simple line
             * if (right - left == p.length() && hash[s.charAt(left++)]++ >= 0) count++;
             */
            	
        }
        return list;
    }
    
	public static void main(String[] args) {
		// s: "cbaebabacd" p: "abc"
		String s = "cbaebabacd";
		String p = "abc";
//		String s = "abab";
//		String p = "ab";
		List<Integer> result = findAnagrams(s, p);
		System.out.println(result);
	}
}




Attempt 1: 2022-09-14 (5min)

class Solution { 
    public List<Integer> findAnagrams(String s, String p) { 
        List<Integer> result = new ArrayList<Integer>(); 
        // Create frequency table based on p 
        int[] freq = new int[26]; 
        int p_len = p.length(); 
        int s_len = s.length(); 
        for(int i = 0; i < p_len; i++) { 
            // Increase character count on frequency table 
            freq[p.charAt(i) - 'a']++; 
        } 
        // 'i' is left end pointer, 'j' is right end pointer  
        int i = 0; 
        for(int j = 0; j < s_len; j++) { 
            // Decrease character count on frequency table, a reverse   
            // operation rather than frequency table creation, because   
            // we want to identify if same character count has difference   
            // between s and p inside current sliding window  
            freq[s.charAt(j) - 'a']--; 
            // When a character count becomes negative means we find a difference  
            // between s and p, attempt to balance count again (make the character  
            // count = 0 again), only way is shinrking the left end pointer 'i'  
            while(freq[s.charAt(j) - 'a'] < 0) { 
                freq[s.charAt(i) - 'a']++; 
                i++; 
            } 
            // If substring length of sliding window identified by (j - i + 1)  
            // equal to string p length, we find a solution  
            if(j - i + 1 == p_len) { 
                result.add(i); 
            } 
        } 
        return result; 
    } 
}

Space Complexity: O(n)  
Time Complexity: O(n)

Refer to
https://leetcode.com/problems/permutation-in-string/discuss/102598/Sliding-Window-in-Java-very-similar-to-Find-All-Anagrams-in-a-String
https://leetcode.com/problems/permutation-in-string/discuss/102590/8-lines-slide-window-solution-in-Java/383847

public boolean checkInclusion(String s1, String s2) { 
    int[] counts = new int[26]; 
    for (char c: s1.toCharArray()) counts[c-'a']++; 
    int i = 0, j = 0; 
    while(j < s2.length()) { 
        char c = s2.charAt(j++); 
        counts[c-'a']--; 
        while(counts[c-'a'] < 0) { 
            char c2 = s2.charAt(i++); 
            counts[c2-'a']++; 
        } 
        if (j-i == s1.length()) return true; 
    } 
    return false; 
}
```

