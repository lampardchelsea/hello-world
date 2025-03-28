/**
Refer to
https://leetcode.com/problems/slowest-key/
A newly designed keypad was tested, where a tester pressed a sequence of n keys, one at a time.

You are given a string keysPressed of length n, where keysPressed[i] was the ith key pressed in the testing sequence, 
and a sorted list releaseTimes, where releaseTimes[i] was the time the ith key was released. Both arrays are 0-indexed. 
The 0th key was pressed at the time 0, and every subsequent key was pressed at the exact time the previous key was released.

The tester wants to know the key of the keypress that had the longest duration. The ith keypress had a duration of 
releaseTimes[i] - releaseTimes[i - 1], and the 0th keypress had a duration of releaseTimes[0].

Note that the same key could have been pressed multiple times during the test, and these multiple presses of the same key 
may not have had the same duration.

Return the key of the keypress that had the longest duration. If there are multiple such keypresses, return the lexicographically 
largest key of the keypresses.

Example 1:
Input: releaseTimes = [9,29,49,50], keysPressed = "cbcd"
Output: "c"
Explanation: The keypresses were as follows:
Keypress for 'c' had a duration of 9 (pressed at time 0 and released at time 9).
Keypress for 'b' had a duration of 29 - 9 = 20 (pressed at time 9 right after the release of the previous character and released at time 29).
Keypress for 'c' had a duration of 49 - 29 = 20 (pressed at time 29 right after the release of the previous character and released at time 49).
Keypress for 'd' had a duration of 50 - 49 = 1 (pressed at time 49 right after the release of the previous character and released at time 50).
The longest of these was the keypress for 'b' and the second keypress for 'c', both with duration 20.
'c' is lexicographically larger than 'b', so the answer is 'c'.

Example 2:
Input: releaseTimes = [12,23,36,46,62], keysPressed = "spuda"
Output: "a"
Explanation: The keypresses were as follows:
Keypress for 's' had a duration of 12.
Keypress for 'p' had a duration of 23 - 12 = 11.
Keypress for 'u' had a duration of 36 - 23 = 13.
Keypress for 'd' had a duration of 46 - 36 = 10.
Keypress for 'a' had a duration of 62 - 46 = 16.
The longest of these was the keypress for 'a' with duration 16.

Constraints:
releaseTimes.length == n
keysPressed.length == n
2 <= n <= 1000
1 <= releaseTimes[i] <= 109
releaseTimes[i] < releaseTimes[i+1]
keysPressed contains only lowercase English letters.
*/

// Solution 1: HashMap
class Solution {
    public char slowestKey(int[] releaseTimes, String keysPressed) {
        char[] chars = keysPressed.toCharArray();
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for(int i = 0; i < chars.length; i++) {
            map.put(chars[i], 0);
        }
        int prev = 0;
        for(int i = 0; i < releaseTimes.length; i++) {
            int curr = releaseTimes[i];
            if(curr - prev > map.get(chars[i])) {
                map.put(chars[i], curr - prev);
            }
            prev = curr;
        }
        char result = 'a';
        int max = 0;
        for(char c = 'a'; c <= 'z'; c++) {
            if(map.get(c) != null && map.get(c) >= max) {
                max = map.get(c);
                result = c;
            }
        }
        return result;
    }
}

// Solution 2: Without HashMap
// Refer to
// https://leetcode.com/problems/slowest-key/discuss/909070/JAVA-Solution-O(n)-Explained
/**
Initialize the maxDifference and bestChar with the first character.
Start iterating from 2nd character to nth character.
Find the difference between every adjacent character (i and i-1th character)
If the current difference is greater than maxDifference calculated so far. 
Or the difference is same but the current character is greater than bestChar, update the difference and bestChar.
Return the bestChar.
*/
class Solution {
    public char slowestKey(int[] releaseTimes, String keysPressed) {
        char cur_char = keysPressed.charAt(0);
        int max_diff = releaseTimes[0];
        for(int i = 1; i < releaseTimes.length; i++) {
            int diff = releaseTimes[i] - releaseTimes[i - 1];
            if(diff > max_diff || (diff == max_diff && keysPressed.charAt(i) > cur_char)) {
                cur_char = keysPressed.charAt(i);
                max_diff = diff;
            }
        }
        return cur_char;
    }
}
