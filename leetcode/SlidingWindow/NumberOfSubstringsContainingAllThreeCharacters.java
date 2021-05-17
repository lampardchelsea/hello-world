/**
Refer to
https://leetcode.com/problems/number-of-substrings-containing-all-three-characters/
Given a string s consisting only of characters a, b and c.

Return the number of substrings containing at least one occurrence of all these characters a, b and c.

Example 1:
Input: s = "abcabc"
Output: 10
Explanation: The substrings containing at least one occurrence of the characters a, b and c are "abc", "abca", 
"abcab", "abcabc", "bca", "bcab", "bcabc", "cab", "cabc" and "abc" (again). 

Example 2:
Input: s = "aaacb"
Output: 3
Explanation: The substrings containing at least one occurrence of the characters a, b and c are "aaacb", "aacb" and "acb". 

Example 3:
Input: s = "abc"
Output: 1

Constraints:
3 <= s.length <= 5 x 10^4
s only consists of a, b or c characters.
*/

// Solution 1: Not fixed length sliding window
// Style 1: 
// Refer to
// https://leetcode.com/problems/number-of-substrings-containing-all-three-characters/discuss/516973/JavaPython-3-Sliding-Window-O(n)-O(1)-code-w-explanation-and-analysis.
/**
1. Maintain a sliding window (lo, hi], where lower bound exclusively and upper bound inclusively;
2. Traverse string s, use upper bound hi to count the number of the 3 characters, a, b, & c; 
   once the sliding window includes all of the 3, we find s.length() - hi substrings (lo, hi], (lo, hi + 1], ..., (lo, s.length() - 1];
3. Increase the lower bound lo by 1 (denote it as lo'), decrease the count accordingly, if the sliding window still includes 
   all of the 3 characters, we count in substrings (lo', hi], (lo', hi + 1], ..., (lo', s.length() - 1];
4. Repeat 3 till the sliding window short of at least 1 of the 3 characters, go to step 2;
5. Repeat 2 - 4 till the end of the string s.
    public int numberOfSubstrings(String s) {
        int[] count = new int[3];
        int ans = 0;
        for (int lo = -1, hi = 0; hi < s.length(); ++hi) {
            ++count[s.charAt(hi) - 'a'];
            while (count[0] > 0 && count[1] > 0 && count[2] > 0) {
                ans += s.length() - hi; // number of valid substrings all start from lo + 1, but end at hi, hi + 1, ..., s.length() - 1, respectively.
                --count[s.charAt(++lo) - 'a'];
            }
        } 
        return ans;        
    }
*/
class Solution {
    public int numberOfSubstrings(String s) {
        int count = 0;
        int n = s.length();
        int[] freq = new int[3];
        int j = 0;
        for(int i = 0; i < n; i++) {
            freq[s.charAt(i) - 'a']++;
            while(freq[0] > 0 && freq[1] > 0 && freq[2] > 0) {
                count += n - i;
                freq[s.charAt(j) - 'a']--;
                // j keep increase till no 3 chars anymore, and no need draw back j
                // when i increase to next position, because all those possibilities
                // are calculated in 'count += n - i' already
                j++;
            }
        }
        return count;
    }
}


// Style 2:
// Refer to
// https://leetcode.com/problems/number-of-substrings-containing-all-three-characters/discuss/516977/JavaC%2B%2BPython-Easy-and-Concise
// https://leetcode.com/problems/number-of-substrings-containing-all-three-characters/discuss/516977/JavaC++Python-Easy-and-Concise/756006
/**
Solution 1: Sliding Window
Find many similar sliding window problem at the end of post.

Time O(N)
Space O(1)

    public int numberOfSubstrings(String s) {
        int count[] = {0, 0, 0}, res = 0 , i = 0, n = s.length();
        for (int j = 0; j < n; ++j) {
            ++count[s.charAt(j) - 'a'];
            while (count[0] > 0 && count[1] > 0 && count[2] > 0)
                --count[s.charAt(i++) - 'a'];
            res += i;
        }
        return res;
    }
 
For people who can't understand res+=i
res += i
This is the best line ever
a a a b b c c a b c
when all a, b, c > 0 for first time at j = 5 the n after while loop i will be at i = 3, 
we will add 3 to result because there would be three substrings from three a's.
Then a,b,c > 0 at j = 7 ,then we will move i until i = 5 then we will add 5 to result 
because there could be 5 substrings starting from 0 to second b.
And similarly we proceed....
*/
class Solution {
    public int numberOfSubstrings(String s) {
        int count = 0;
        int n = s.length();
        int[] freq = new int[3];
        int j = 0;
        for(int i = 0; i < n; i++) {
            freq[s.charAt(i) - 'a']++;
            while(freq[0] > 0 && freq[1] > 0 && freq[2] > 0) {
                freq[s.charAt(j) - 'a']--;
                j++;
            }
            /**
            res += i, this is the best line ever
            e.g "a a a b b c c a b c"
            when all a, b, c > 0 for first time at j = 5 the n after while loop i 
            will be at i = 3, we will add 3 to result because there would be three
            substrings from three a's.
            Then a,b,c > 0 at j = 7 ,then we will move i until i = 5 then we will 
            add 5 to result because there could be 5 substrings starting from 0 
            to second b. And similarly we proceed....
            */
            count += j;
        }
        return count;
    }
}
