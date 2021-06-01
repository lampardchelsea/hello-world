/**
Refer to
https://leetcode.com/problems/repeated-string-match/
Given two strings a and b, return the minimum number of times you should repeat string a so that string b is a 
substring of it. If it is impossible for b​​​​​​ to be a substring of a after repeating it, return -1.

Notice: string "abc" repeated 0 times is "",  repeated 1 time is "abc" and repeated 2 times is "abcabc".

Example 1:
Input: a = "abcd", b = "cdabcdab"
Output: 3
Explanation: We return 3 because by repeating a three times "abcdabcdabcd", b is a substring of it.

Example 2:
Input: a = "a", b = "aa"
Output: 2

Example 3:
Input: a = "a", b = "a"
Output: 1

Example 4:
Input: a = "abc", b = "wxyz"
Output: -1

Constraints:
1 <= a.length <= 104
1 <= b.length <= 104
a and b consist of lower-case English letters.
*/

// Solution 1: Don't forget append one more a to handle rotation case
// Refer to
// https://leetcode.com/problems/repeated-string-match/discuss/108086/Java-Solution-Just-keep-building-(OJ-Missing-Test-Cases)
/**
Since LC has so many test cases missing, I wrote new code.
The idea is to keep string builder and appending until the length A is greater or equal to B.

 public int repeatedStringMatch(String A, String B) {

    int count = 0;
    StringBuilder sb = new StringBuilder();
    while (sb.length() < B.length()) {
        sb.append(A);
        count++;
    }
    if(sb.toString().contains(B)) return count;
    if(sb.append(A).toString().contains(B)) return ++count;
    return -1;
}
*/
class Solution {
    public int repeatedStringMatch(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        while(sb.length() < b.length()) {
            sb.append(a);
            count++;
        }
        if(sb.toString().contains(b)) {
            return count;
        }
        // To handle rotation case we need to try another one more a append
        // e.g a = "abcd", b = "cdabcdab"
        if(sb.append(a).toString().contains(b)) {
            return count + 1;
        }
        return -1;
    }
}


// Style 2: TLE
// Refer to
// https://leetcode.com/problems/repeated-string-match/discuss/108086/Java-Solution-Just-keep-building-(OJ-Missing-Test-Cases)/198856
/**
class Solution {
    public int repeatedStringMatch(String A, String B) {
        if(A==null || B==null)
            return -1;
     
        StringBuilder temp = new StringBuilder();
        int count = 0;
        while(temp.length()<B.length()){
            temp.append(A);
            count++;
        }
        if(isFound(temp, B))
            return count;
        if(isFound(temp.append(A), B))
            return count+1;
        return -1;
    }
    
    private boolean isFound(StringBuilder A, String B){
        for(int i=0; i<A.length() ; i++){
            int start = i;
            int j=0;
            while(start<A.length() && j<B.length() && A.charAt(start) == B.charAt(j)){
                start++;
                j++;
      
            }
            if(j==B.length())
                return true;
        }
        return false;
    }
}
*/
class Solution {
    public int repeatedStringMatch(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        while(sb.length() < b.length()) {
            sb.append(a);
            count++;
        }
        if(isFound(sb, b)) {
            return count;
        }
        // To handle rotation case we need to try another one more a append
        // e.g a = "abcd", b = "cdabcdab"
        if(isFound(sb.append(a), b)) {
            return count + 1;
        }
        return -1;
    }
    
    private boolean isFound(StringBuilder sb, String b) {
        for(int i = 0; i < sb.length(); i++) {
            int start = i;
            int j = 0;
            while(start < sb.length() && j < b.length() && sb.charAt(start) == b.charAt(j)) {
                start++;
                j++;
            }
            if(j == b.length()) {
                return true;
            }
        }
        return false;
    }
}
