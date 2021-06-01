/**
Refer to
https://leetcode.com/problems/split-two-strings-to-make-palindrome/
You are given two strings a and b of the same length. Choose an index and split both strings at the same index, 
splitting a into two strings: aprefix and asuffix where a = aprefix + asuffix, and splitting b into two strings: 
bprefix and bsuffix where b = bprefix + bsuffix. Check if aprefix + bsuffix or bprefix + asuffix forms a palindrome.

When you split a string s into sprefix and ssuffix, either ssuffix or sprefix is allowed to be empty. For example, 
if s = "abc", then "" + "abc", "a" + "bc", "ab" + "c" , and "abc" + "" are valid splits.

Return true if it is possible to form a palindrome string, otherwise return false.

Notice that x + y denotes the concatenation of strings x and y.

Example 1:
Input: a = "x", b = "y"
Output: true
Explaination: If either a or b are palindromes the answer is true since you can split in the following way:
aprefix = "", asuffix = "x"
bprefix = "", bsuffix = "y"
Then, aprefix + bsuffix = "" + "y" = "y", which is a palindrome.

Example 2:
Input: a = "abdef", b = "fecab"
Output: true

Example 3:
Input: a = "ulacfd", b = "jizalu"
Output: true
Explaination: Split them at index 3:
aprefix = "ula", asuffix = "cfd"
bprefix = "jiz", bsuffix = "alu"
Then, aprefix + bsuffix = "ula" + "alu" = "ulaalu", which is a palindrome.

Example 4:
Input: a = "xbdef", b = "xecab"
Output: false

Constraints:
1 <= a.length, b.length <= 105
a.length == b.length
a and b consist of lowercase English letters
*/

// Solution 1: Scan till different from two strings, then scan till different on either of one string
// Refer to
// https://leetcode.com/problems/split-two-strings-to-make-palindrome/discuss/888985/Java-clean-O(n)-with-explanations
/**
class Solution {
    public boolean checkPalindromeFormation(String a, String b) {
        return validate(a,b) || validate(b,a);
    }
    
    private boolean validate(String a, String b) {
        int l = 0, r = a.length()-1;
        while (l < r) {
            if (a.charAt(l) != b.charAt(r)) 
                break; 
            l++;
            r--;
        }
        
        return validate(a, l, r) || validate(b, l, r);
    }
    
    private boolean validate(String a, int l, int r) {
        while(l < r) {
            if (a.charAt(l) != a.charAt(r))
                return false;
            l++;
            r--;
        }
        return true;
    }
}
Adding explanations
The basic idea is to use left pointer and right pointer to compare if the chars are equal

For example:

l
ulabbxyz
jizcdalu
       r

// Same, l++, r— 

 l
ulabbxyz
jizcdalu
      r
	   
// Same, l++, r— 

  l
ulabbxyz
jizcdalu
     r

// Same, l++, r— 

   l
ulabbxyz
jizcdalu
    r
	
// the trick is here, “b” and “d” are different. 
// That means we have two choice to make it possible
// Either cut at l in string a, or cut at r in string b. 

// try those 2 approaches
    lr
ula bb ... // <- need check if [l,r] in string a is palindrome
..... alu
// or 
ula .....
... cd alu  // <- need check if [l,r] in string b is palindrome
    lr

Thus, it's O(n) and it's ensure cutting at the same index
*/
class Solution {
    public boolean checkPalindromeFormation(String a, String b) {
        return validate(a, b) || validate(b, a);
    }
    
    private boolean validate(String a, String b) {
        int l = 0;
        int r = a.length() - 1;
        while(l < r) {
            if(a.charAt(l) != b.charAt(r)) {
                break;
            }
            l++;
            r--;
        }
        return validate(a, l, r) || validate(b, l, r);
    }
    
    private boolean validate(String s, int l, int r) {
        while(l < r) {
            if(s.charAt(l) != s.charAt(r)) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }
}
