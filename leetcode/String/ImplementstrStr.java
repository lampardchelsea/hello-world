/**
 * Implement strStr().
 * Returns the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
*/
// Refer to
// http://www.cnblogs.com/springfor/p/3896469.html
// http://zjalgorithm.blogspot.com/2014/12/leetcode-in-java-implement-strstr.html
// https://segmentfault.com/a/1190000003707284
public class Solution {
    public int strStr(String haystack, String needle) {
        int nLen = needle.length();
        if(nLen == 0) {
            return 0;
        }
        int[] next = new int[nLen];
        for(int i = 0; i < nLen; i++) {
            next[i] = 0;
        }
        findNext(needle, next);
        return kmp(haystack, needle, next);
    }
    
    public void findNext(String needle, int[] next) {
        int nLen = next.length;
        int k = -1;
        int j = 0;
        next[0] = -1;
        while(j < nLen - 1) {
            if(k == -1 || needle.charAt(k) == needle.charAt(j)) {
                ++k;
                ++j;
                next[j] = k;
            } else {
                k = next[k];
            }
        }
    }
    
    public int kmp(String haystack, String needle, int[] next) {
        int hLen = haystack.length();
        int nLen = needle.length();
        int i = 0;
        int j = 0;
        while(i < hLen && j < nLen) {
            if(j == -1 || haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if(j == nLen) {
            return i - j;
        } else {
            return -1;
        }
    } 
}
