/**
 Refer to
 https://leetcode.com/problems/can-make-palindrome-from-substring/
 Given a string s, we make queries on substrings of s.

For each query queries[i] = [left, right, k], we may rearrange the substring s[left], ..., s[right], 
and then choose up to k of them to replace with any lowercase English letter. 

If the substring is possible to be a palindrome string after the operations above, the result of the 
query is true. Otherwise, the result is false.

Return an array answer[], where answer[i] is the result of the i-th query queries[i].

Note that: Each letter is counted individually for replacement so if for example s[left..right] = "aaa", 
and k = 2, we can only replace two of the letters.  (Also, note that the initial string s is never 
modified by any query.)

Example :
Input: s = "abcda", queries = [[3,3,0],[1,2,0],[0,3,1],[0,3,2],[0,4,1]]
Output: [true,false,false,true,true]
Explanation:
queries[0] : substring = "d", is palidrome.
queries[1] : substring = "bc", is not palidrome.
queries[2] : substring = "abcd", is not palidrome after replacing only 1 character.
queries[3] : substring = "abcd", could be changed to "abba" which is palidrome. Also this can be changed to "baab" first rearrange it "bacd" then replace "cd" with "ab".
queries[4] : substring = "abcda", could be changed to "abcba" which is palidrome.

Constraints:
1 <= s.length, queries.length <= 10^5
0 <= queries[i][0] <= queries[i][1] < s.length
0 <= queries[i][2] <= s.length
s only contains lowercase English letters.
*/

// Solution: Presum + Freq table
// Refer to
// https://leetcode.com/problems/can-make-palindrome-from-substring/discuss/371849/JavaPython-3-3-codes-each:-prefix-sum-of-counting-characters-first-then-compare/334622
/**
 1.cnt数组类似于一个“前缀和”。cnt【i】【j】表示，从0开始，到当前位置i（不包括i），包含字符（‘a’ + j）的总个数。
 2.遍历字符串，更新这个“前缀和数组”。
 3.对于每一个操作q，可以得到substring（q【0】， q【1】 + 1），通过前面的“前缀和”数组，可以得到该substring的字母统计结果，即该substring包含多少个a，多少个b，以此类推。
 4.对于步骤三得到的结果，开始进行操作，对于每一个字母，查看其个数，如果是偶数个，则sum不变【理由：题目允许rearrange，比如abac，可以先rearrange成abca，则a这个字母是不需要进行替换的】；如果是奇数个，则sum值+1，表示这个落单的字母将来需要被替换掉。这一步遍历完成后，得到了需要replace的总的字符数量。
 5.按照要求，有sum个落单的，因此，只要把其中的一般替换成另一半，一定可以rearrange成一个palindrome。
*/
class Solution {
    /**
    e.g  abcda
         col = 0   1   2   3   4  ... 25
             -----------------------------------------
    row = 0  | 1 |   |   |   |   |...
             -----------------------------------------
    row = 1  | 1 | 1 |   |   |   |...
             -----------------------------------------
    row = 2  | 1 | 1 | 1 |   |   |...
             -----------------------------------------
    row = 3  | 1 | 1 | 1 | 1 |   |...
             -----------------------------------------
    row = 4  | 2 | 1 | 1 | 1 |   |...
             -----------------------------------------
    */
    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
        int[][] freq = new int[s.length()][26];
        // Initial row = 0 with first character in the string s
        freq[0][s.charAt(0) - 'a']++;
        for(int i = 1; i < s.length(); i++) {
            // Must use clone to copy previous row array to current row array
            // Refer to
            // https://stackoverflow.com/questions/5785745/make-copy-of-an-array
            freq[i] = freq[i - 1].clone();
            // Plus new char
            freq[i][s.charAt(i) - 'a']++;
        }
        List<Boolean> result = new ArrayList<Boolean>();
        for(int i = 0; i < queries.length; i++) {
            // Worst case is you have 26 odd number characters, but 
            // if K is >=13, so you can just switch 13 singles of 
            // each character, into the other 13, giving you 26 even 
            // numbered characters. Odd/even is all you care about 
            // in this problem because you can rearrange the letters.
            if(queries[i][2] >= 13) {
                result.add(true);
                continue;
            }
            int m = queries[i][0];
            int n = queries[i][1];
            // Only need to care about odd count character
            int count = 0;
            for(int j = 0; j < 26; j++) {
                // e.g [0,3,1], [0,3,2], [0,4,1], when m = 0 means
                // even not include row = 0
                if(m == 0) {
                    count += freq[n][j] % 2;
                } else {
                    count += (freq[n][j] - freq[m - 1][j]) % 2;
                }
            }
            // Only need half of them to replace to make as even number
            result.add(count / 2 <= queries[i][2]);
        }
        return result;
    }
}
