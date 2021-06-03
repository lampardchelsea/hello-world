/**
Refer to
https://leetcode.com/problems/find-the-longest-substring-containing-vowels-in-even-counts/
Given the string s, return the size of the longest substring containing each vowel an even number of times. 
That is, 'a', 'e', 'i', 'o', and 'u' must appear an even number of times.

Example 1:
Input: s = "eleetminicoworoep"
Output: 13
Explanation: The longest substring is "leetminicowor" which contains two each of the vowels: e, i and o and zero of the vowels: a and u.

Example 2:
Input: s = "leetcodeisgreat"
Output: 5
Explanation: The longest substring is "leetc" which contains two e's.

Example 3:
Input: s = "bcbcbc"
Output: 6
Explanation: In this case, the given string "bcbcbc" is the longest because all vowels: a, e, i, o and u appear zero times.

Constraints:
1 <= s.length <= 5 x 10^5
s contains only lowercase English letters.
*/

// Solution 1: HashMap + Bitwise record state for "aeiou"
// Refer to
// https://leetcode.com/problems/find-the-longest-substring-containing-vowels-in-even-counts/discuss/531840/JavaC%2B%2BPython-One-Pass
/**
Explanation
cur records the count of "aeiou"
cur & 1 = the records of a % 2
cur & 2 = the records of e % 2
cur & 4 = the records of i % 2
cur & 8 = the records of o % 2
cur & 16 = the records of u % 2
seen note the index of first occurrence of cur

Note that we don't really need the exact count number,
we only need to know if it's odd or even.

If it's one of aeiou,
'aeiou'.find(c) can find the index of vowel,
cur ^= 1 << 'aeiou'.find(c) will toggle the count of vowel.

But for no vowel characters,
'aeiou'.find(c) will return -1,
that's reason that we do 1 << ('aeiou'.find(c) + 1) >> 1.

Hope this explain enough.

Complexity
Time O(N), Space O(1)

Java:

    public int findTheLongestSubstring(String s) {
        int res = 0 , cur = 0, n = s.length();
        HashMap<Integer, Integer> seen = new HashMap<>();
        seen.put(0, -1);
        for (int i = 0; i < n; ++i) {
            cur ^= 1 << ("aeiou".indexOf(s.charAt(i)) + 1 ) >> 1;
            seen.putIfAbsent(cur, i);
            res = Math.max(res, i - seen.get(cur));
        }
        return res;
    }
*/

// https://leetcode.com/problems/find-the-longest-substring-containing-vowels-in-even-counts/discuss/531840/JavaC++Python-One-Pass/467679
/**
Let me try to explain a bit more. Is the count of "aeiou" matters? No, indeed. Only the mod of count will contribute to the result. 
Consider at index i, we have below counting mods where '+' means even and '-' means odd.

a e i o u
+ - + - +

Then what we should do is just find another same mods pattern "+-+-+" with index j (j < i), then the sequence between (j, i] is 
guaranteed to have all vowels' counting even (while other patterns are definitely not qualified). To make the sequence longest, 
we should find the smallest j and that's why Map::putIfAbsent is used.

cur ^= 1 << ("aeiou".indexOf(s.charAt(i)) + 1 ) >> 1;

This concise code is just inversing a certain bit of cur:
                                       a       e       i       o       u       other
"aeiou".indexOf(s.charAt(i)) + 1       1       2       3       4       5       0
1 << tmp                               2       4       8      16      32       1
(1 << tmp) >> 1                        1       2       4       8      16       0

So cur ^= something should be cur ^= 1, cur ^= 2, cur ^= 4, and so on.
*/

// Good example step by step below:
/**
Example: leetcodeisgreat
=======================================================================
l => tmp3 = 00000000 ('l' is not vowel)
state ^ tmp3
00000000 state
00000000 tmp3
--------
00000000 => state = 0
seen => {0=-1} (first time seen state = 0, but since previously add as (0, -1) already no need to add into map again)
i - seen.get(state) = 0 - (-1) = 1, Math.max(res, 1) = 1
=======================================================================
l'e' => tmp3 = 00000010 ('e' is vowel)
00000000
00000010
--------
00000010 => state = 2
seen => {0=-1, 2=1} (first time seen state = 2, add into map to record)
i - seen.get(state) = 1 - 1 = 0, Math.max(res, 0) = 1
=======================================================================
le'e' => tmp3 = 00000010 ('e' is vowel)
00000010
00000010
--------
00000000 => state = 0
seen => {0=-1, 2=1} (second time seen state = 0, no need to add into map, and since we see the same state again, which means 
the substring between current index 'i' and first time seen state = 0 index 'seen.get(state)' is surely have even vowels)
i - seen.get(state) = 2 - (-1) = 3, Math.max(res, 3) = 3
=======================================================================
lee't' => tmp3 = 00000000 ('t' is not vowel)
00000000
00000000
--------
00000000 => state = 0
seen => {0=-1, 2=1} (third time seen state = 0, no need to add into map, and since we see the same state again, which means 
the substring between current index 'i' and first time seen state = 0 index 'seen.get(state)' is surely have even vowels)
i - seen.get(state) = 3 - (-1) = 4, Math.max(res, 4) = 4
=======================================================================
leet'c' => tmp3 = 00000000 ('c' is not vowel)
00000000
00000000
--------
00000000 => state = 0
seen => {0=-1, 2=1} (fourth time seen state = 0, no need to add into map, and since we see the same state again, which means 
the substring between current index 'i' and first time seen state = 0 index 'seen.get(state)' is surely have even vowels)
i - seen.get(state) = 4 - (-1) = 5, Math.max(res, 5) = 5
=======================================================================
leetc'o' => tmp3 = 00001000 ('o' is vowel)
00000000
00001000
--------
00001000 => state = 8
seen => {0=-1, 2=1, 8=5} (first time seen state = 8, add into map to record)
i - seen.get(state) = 5 - 5 = 0, Math.max(res, 0) = 5
=======================================================================
leetco'd' => tmp3 = 00000000 ('d' is not vowel)
00001000
00000000
--------
00001000 => state = 8
seen => {0=-1, 2=1, 8=5} (second time seen state = 8, no need to add into map, and since we see the same state again, which means 
the substring between current index 'i' and first time seen state = 8 index 'seen.get(state)' is surely have even vowels)
i - seen.get(state) = 6 - 5 = 1, Math.max(res, 1) = 5
=======================================================================
leetcod'e' => tmp3 = 00000010 ('e' is vowel)
00001000
00000010
--------
00001010 => state = 10
seen => {0=-1, 2=1, 8=5, 10=7} (first time seen state = 10, add into map to record)
i - seen.get(state) = 7 - 7 = 0, Math.max(res, 0) = 5
=======================================================================
leetcode'i' => tmp3 = 00000100 ('i' is vowel)
00001010
00000100
--------
00001110 => state = 14
seen => {0=-1, 2=1, 8=5, 10=7, 14=8} (first time seen state = 14, add into map to record)
i - seen.get(state) = 8 - 8 = 0, Math.max(res, 0) = 5
=======================================================================
leetcodei's' => tmp3 = 00000000 ('s' is not vowel)
00001110
00000000
--------
00001110 => state = 14
seen => {0=-1, 2=1, 8=5, 10=7, 14=8} (second time seen state = 14, no need to add into map, and since we see the same state again, 
which means the substring between current index 'i' and first time seen state = 14 index 'seen.get(state)' is surely have even vowels)
i - seen.get(state) = 9 - 8 = 1, Math.max(res, 1) = 5
=======================================================================
leetcodeis'g' => tmp3 = 00000000 ('g' is not vowel)
00001110
00000000
--------
00001110 => state = 14
seen => {0=-1, 2=1, 8=5, 10=7, 14=8} (third time seen state = 14, no need to add into map, and since we see the same state again, 
which means the substring between current index 'i' and first time seen state = 14 index 'seen.get(state)' is surely have even vowels)
i - seen.get(state) = 10 - 8 = 2, Math.max(res, 2) = 5
=======================================================================
leetcodeisgr'e' => tmp3 = 00000010 ('e' is vowel)
00001110
00000010
--------
00001100 => state = 12
seen => {0=-1, 2=1, 8=5, 10=7, 12=12, 14=8} (first time seen state = 12, add into map to record)
i - seen.get(state) = 12 - 12 = 0, Math.max(res, 0) = 5
=======================================================================
leetcodeisgre'a' => tmp3 = 00000001 ('e' is vowel)
00001100
00000001
--------
00001101 => state = 13
seen => {0=-1, 2=1, 8=5, 10=7, 12=12, 13=13, 14=8} (first time seen state = 13, add into map to record)
i - seen.get(state) = 13 - 13 = 0, Math.max(res, 0) = 5
=======================================================================
leetcodeisgrea't' => tmp3 = 00000000 ('t' is not vowel)
00001101
00000000
--------
00001101 => state = 13
seen => {0=-1, 2=1, 8=5, 10=7, 12=12, 13=13, 14=8} (second time seen state = 13, no need to add into map, and since we see the same state again, 
which means the substring between current index 'i' and first time seen state = 13 index 'seen.get(state)' is surely have even vowels)
i - seen.get(state) = 14 - 13 = 1, Math.max(res, 1) = 5
=======================================================================
Finally the res = 5 as maximum length

*/

class Solution {
    public int findTheLongestSubstring(String s) {
        int result = 0;
        int state = 0; 
        String vowels = "aeiou"; // 'state' records the count of "aeiou"
        Map<Integer, Integer> seen = new HashMap<Integer, Integer>(); // 'seen' note the index of first occurrence of 'state'
        seen.put(0, -1);
        for(int i = 0; i < s.length(); i++) {
            // state ^= 1 << (vowels.indexOf(s.charAt(i)) + 1) >> 1;
            int tmp = vowels.indexOf(s.charAt(i)) + 1; // Handle no vowels case
            int tmp2 = 1 << tmp;
            int tmp3 = tmp2 >> 1;
            state = state ^ tmp3; // Inversing a certain bit of 'state'
            seen.putIfAbsent(state, i); // Only record the first time position of 'state' value happening
            result = Math.max(result, i - seen.get(state));
        }
        return result;
    }
}
