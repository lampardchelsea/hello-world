/**
 Refer to
 https://www.cnblogs.com/Dylan-Java-NYC/p/12382223.html
 Given two strings str1 and str2 of the same length, determine whether you can transform str1 into str2 
 by doing zero or more conversions.

In one conversion you can convert all occurrences of one character in str1 to any other lowercase English character.

Return true if and only if you can transform str1 into str2.

Example 1:
Input: str1 = "aabcc", str2 = "ccdee"
Output: true
Explanation: Convert 'c' to 'e' then 'b' to 'd' then 'a' to 'c'. Note that the order of conversions matter.

Example 2:
Input: str1 = "leetcode", str2 = "codeleet"
Output: false
Explanation: There is no way to transform str1 to str2.

Note:
1 <= str1.length == str2.length <= 10^4
Both str1 and str2 contain only lowercase English letters.
*/

// Solution 1: HashMap
// Refer to
// https://www.cnblogs.com/Dylan-Java-NYC/p/12382223.html
/**
 If two string equals, then return true.
 If one character a is mapped to 2 different chars, then return false.
 The order matters, in example 1, a->c, c->e. need to perform c->e first. Otherwise, a->c, 
 becomes ccbcc, then c->e, it becomes eedee, which is not ccdee.
 Or we need a temp char g a->g->c, first have a->g ggbcc, then c->e, ggbee. Last we have g->c, then ccbee.
 Inorder to do this, we need one unused char in str2, which makes the size of str2 unique chars smaller than 26.
 Time Complexity: O(n). n = str1.length().
 Space: O(n).
*/

// https://www.twblogs.net/a/5d66d523bd9eee5327fec62c
/**
 遍歷一遍str1，就知道str1的每個字符應該映射到誰了
 但是1個字符不能映射到2個字符 同時 str1裏的字符個數應該少於str2裏的字符個數
 此外，如果str2有26個字符時，除非兩個字符串相等，否則不可能轉化成功，因爲str1此時也得有26個字符，但是不管動了哪個字符，
 映射完之後就只有25個字符了，這時候無法再映射回26個字符
*/

// https://www.cnblogs.com/slowbirdoflsh/p/11356674.html
class Solution {
    public boolean canConvert(String str1, String str2) {
        if (str1.equals(str2)) {
            return true;
        }

        int n = str1.length();
        HashMap < Character, Character > hm = new HashMap < > ();
        for (int i = 0; i < n; i++) {
            char c1 = str1.charAt(i);
            char c2 = str2.charAt(i);
            if (hm.containsKey(c1) && hm.get(c1) != c2) {
                return false;
            }

            hm.put(c1, c2);
        }

        return new HashSet < Character > (hm.values()).size() < 26;
    }
}




