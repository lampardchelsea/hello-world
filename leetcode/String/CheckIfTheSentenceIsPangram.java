/**
Refer to
https://leetcode.com/problems/check-if-the-sentence-is-pangram/
A pangram is a sentence where every letter of the English alphabet appears at least once.

Given a string sentence containing only lowercase English letters, return true if sentence is a pangram, or false otherwise.

Example 1:
Input: sentence = "thequickbrownfoxjumpsoverthelazydog"
Output: true
Explanation: sentence contains at least one of every letter of the English alphabet.

Example 2:
Input: sentence = "leetcode"
Output: false

Constraints:
1 <= sentence.length <= 1000
sentence consists of lowercase English letters.
*/
// Solution 1: Freq map
class Solution {
    public boolean checkIfPangram(String sentence) {
        int[] freq = new int[26];
        for(char c : sentence.toCharArray()) {
            freq[c - 'a']++;
        }
        for(int i = 0; i < 26; i++) {
            if(freq[i] < 1) {
                return false;
            }
        }
        return true;
    }
}

// Solution 2: Bitwise
// Style 1:
// Refer to
// https://leetcode.com/problems/check-if-the-sentence-is-pangram/discuss/1164135/Simple-solution-no-setmap
// https://leetcode.com/problems/check-if-the-sentence-is-pangram/discuss/1164135/Simple-solution-no-setmap/910583
/**
Explanation for beginners
Instead of using array or set, here the int bits are used to mark if the alphabet has already occurred or not.
say, when we encounter the characters a,b and j we will change the 1st, 2nd, 10th bits to 1

int seen = 0;
// we started with 0 as all bits are 0, denoting no alphabet has occurred till now
// then we iterate over all characters of input, and change them to integer value
'a' - 'a' = 97 - 97 (ASCII values) = 0
'b' - 'a' = 98 - 97 = 1
'c' - 'a' = 99 - 97 = 2
.
.
'z' - 'a' = 122 - 97 = 26

here << is bitwise left-shift operator which will shift the 1st bit of 1 to left by ci places.
1 << ci will denote , for,
a -> 00....00001
b -> 00....00010
c -> 00....00100
d -> 00....01000
and so on...


now you can see when we take OR with seen then the respective position bit will become 1
which is used to indicate this alphabet has occurred.


// iterate over each character and set the corresponding bit to 1 in seen variable
for(char c : sentence.toCharArray()) {
  int ci = c - 'a';
  seen = seen | (1 << ci);
}
let, seen was something like this, 000....010100
now we have encountered d
so we need to change 4th bit to 1, so taking OR with 1000 will do the work
now seen changes to 000....011100
it shows c, d, f have occurred

at the end if the number becomes 00..00111111....111
------------------------------------------------- ^ 26 ones ^
then it will mean all alphabets have occurred, at least once


Now,
1<<26 means to shit the 1st bit of 1 left side by 26 places, then it will denote 001000000...000 (26 zeroes after 1)
and (1<<26) - 1 will denote 000..01111..11( 26 consecutive 1s)
as 11111....111 + 1 = 100000...000 in binary

so if seen is equal to it, that means each alphabet occurred atleast once.


return seen == ((1 << 26) - 1);
// return true if all alphabet have occurred atleast once
It works here because we have 26 alphabets and we have 32 bits in the int
So it makes O(1) space complexity.
*/
class Solution {
    public boolean checkIfPangram(String sentence) {
        int seen = 0;
        for(char c : sentence.toCharArray()) {
            int ci = c - 'a';
            seen |= 1 << ci;
        }
        return seen == (1 << 26) - 1;
    }
}

// Style 2:
// https://leetcode.com/problems/check-if-the-sentence-is-pangram/discuss/1164135/Simple-solution-no-setmap/910621
/**
public boolean checkIfPangram(String s) {
	int max = (1 << 26) - 1, temp = 0;
        
    for(char c : s.toCharArray()){
        temp |= 1 << c - 'a';
        if(temp == max) return true;
    }           
    return false;
}
*/
class Solution {
    public boolean checkIfPangram(String sentence) {
        int max = (1 << 26) - 1;
        int tmp = 0;
        for(char c : sentence.toCharArray()) {
            tmp |= 1 << c - 'a';
            if(tmp == max) {
                return true;
            }
        }
        return false;
    }
}
