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

// https://blog.csdn.net/lemonmillie/article/details/100045180
/**
 遍历一遍str1，就知道str1的每个字符应该映射到谁了
 但是1个字符不能映射到2个字符 同时 str1里的字符个数应该少于str2里的字符个数
 此外，如果str2有26个字符时，除非两个字符串相等，否则不可能转化成功，
 因为str1此时也得有26个字符，但是不管动了哪个字符，映射完之后就只有25个字符了，这时候无法再映射回26个字符
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




