/**
Refer to
https://leetcode.com/problems/ambiguous-coordinates/
We had some 2-dimensional coordinates, like "(1, 3)" or "(2, 0.5)". Then, we removed all commas, decimal points, 
and spaces and ended up with the string s.

For example, "(1, 3)" becomes s = "(13)" and "(2, 0.5)" becomes s = "(205)".
Return a list of strings representing all possibilities for what our original coordinates could have been.

Our original representation never had extraneous zeroes, so we never started with numbers like "00", "0.0", "0.00", 
"1.0", "001", "00.01", or any other number that can be represented with fewer digits. Also, a decimal point within 
a number never occurs without at least one digit occurring before it, so we never started with numbers like ".1".

The final answer list can be returned in any order. All coordinates in the final answer have exactly one space 
between them (occurring after the comma.)

Example 1:
Input: s = "(123)"
Output: ["(1, 2.3)","(1, 23)","(1.2, 3)","(12, 3)"]

Example 2:
Input: s = "(0123)"
Output: ["(0, 1.23)","(0, 12.3)","(0, 123)","(0.1, 2.3)","(0.1, 23)","(0.12, 3)"]
Explanation: 0.0, 00, 0001 or 00.01 are not allowed.

Example 3:
Input: s = "(00011)"
Output: ["(0, 0.011)","(0.001, 1)"]

Example 4:
Input: s = "(100)"
Output: ["(10, 0)"]
Explanation: 1.0 is not allowed.

Constraints:
4 <= s.length <= 12
s[0] == '(' and s[s.length - 1] == ')'.
The rest of s are digits.
*/

// Solution 1: Handling heading and tailing 0s
// Refer to
// https://leetcode.com/problems/ambiguous-coordinates/discuss/123851/C%2B%2BJavaPython-Solution-with-Explanation
/**
We can split S to two parts for two coordinates.
Then we use sub function f to find all possible strings for each coordinate.

In sub functon f(S)
if S == "": return []
if S == "0": return [S]
if S == "0XXX0": return []
if S == "0XXX": return ["0.XXX"]
if S == "XXX0": return [S]
return [S, "X.XXX", "XX.XX", "XXX.X"...]

Then we add the product of two lists to result.

Time complexity
O(N^3) with N <= 10

Java:

    public List<String> ambiguousCoordinates(String S) {
        int n = S.length();
        List<String> res = new ArrayList();
        for (int i = 1; i < n - 2; ++i) {
            List<String> A = f(S.substring(1, i + 1)), B = f(S.substring(i + 1, n - 1));
            for (String a : A) for (String b : B) res.add("(" + a + ", " + b + ")");
        }
        return res;
    }
    public List<String> f(String S) {
        int n = S.length();
        List<String> res = new ArrayList();
        if (n == 0 || (n > 1 && S.charAt(0) == '0' && S.charAt(n - 1) == '0')) return res;
        if (n > 1 && S.charAt(0) == '0') {
            res.add("0." + S.substring(1));
            return res;
        }
        res.add(S);
        if (n == 1 || S.charAt(n - 1) == '0') return res;
        for (int i = 1; i < n; ++i) res.add(S.substring(0, i) + '.' + S.substring(i));
        return res;
    }
*/

// https://leetcode.com/problems/ambiguous-coordinates/discuss/123875/Really-clear-Java-code
/**
class Solution {
    public List<String> ambiguousCoordinates(String S) {
        S = S.substring(1, S.length() - 1);
        List<String> result = new LinkedList<>();
        for (int i = 1; i < S.length(); i++) {
            List<String> left = allowed(S.substring(0, i));
            List<String> right = allowed(S.substring(i));
            for (String ls : left) {
                for (String rs : right) {
                    result.add("(" + ls + ", " + rs + ")");
                }
            }
        }
        return result;
    }
    private List<String> allowed(String s) {
        int l = s.length();
        char[] cs = s.toCharArray();
        List<String> result = new LinkedList<>();
        if (cs[0] == '0' && cs[l - 1] == '0') { // "0xxxx0" Invalid unless a single "0"
            if (l == 1) {
                result.add("0");
            }
            return result;
        }
        if (cs[0] == '0') { // "0xxxxx" The only valid result is "0.xxxxx"
            result.add("0." + s.substring(1));
            return result;
        }
        if (cs[l - 1] == '0') { // "xxxxx0" The only valid result is itself
            result.add(s);
            return result;
        }
        result.add(s); // Add itself
        for (int i = 1; i < l; i++) { // "xxxx" -> "x.xxx", "xx.xx", "xxx.x"
            result.add(s.substring(0, i) + '.' + s.substring(i));
        }
        return result;
    }
}
*/
class Solution {
    public List<String> ambiguousCoordinates(String s) {
        String str = s.substring(1, s.length() - 1);
        List<String[]> list = new ArrayList<String[]>(); 
        for(int i = 1; i < str.length(); i++) {
            String s1 = str.substring(0, i);
            String s2 = str.substring(i);
            list.add(new String[] {s1, s2});
        }
        List<String> result = new ArrayList<String>();
        for(String[] a : list) {
            List<String> list1 = helper(a[0]);
            List<String> list2 = helper(a[1]);
            for(String s1 : list1) {
                for(String s2 : list2) {
                    result.add("(" + s1 + ", " + s2 + ")");
                }
            }
        }
        return result;
    }
    
    private List<String> helper(String s) {
        List<String> result = new ArrayList<String>();
        int n = s.length();
        // About "if S == “0XXX0”: return []". why not return "0.xxx0"? like 0.50
        // because "Our original representation never had extraneous zeroes"
        if(n > 1 && s.charAt(0) == '0' && s.charAt(n - 1) == '0') {
            return result;
        }
        // if S == "0XXX": return ["0.XXX"]
        if(n > 1 && s.charAt(0) == '0') {
            result.add("0." + s.substring(1));
            return result;
        }
        // No heading '0' case now, add itself without any dot '.' first
        result.add(s);
        // if S == "0": return [S] or if S == "XXX0": return [S]
        // why directly return S when S ending with 0 ?
        // because "Our original representation never had extraneous zeroes"
        if(n == 1 || s.charAt(n - 1) == '0') {
            return result;
        }
        // return [S, "X.XXX", "XX.XX", "XXX.X"...]
        for(int i = 1; i < n; i++) {
            result.add(s.substring(0, i) + "." + s.substring(i));
        }
        return result;
    }
}
