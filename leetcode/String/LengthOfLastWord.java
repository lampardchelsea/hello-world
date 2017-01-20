/**
 * Refer to
 * https://discuss.leetcode.com/topic/6055/a-single-line-of-code-in-java/2
*/
public class Solution {
    public int lengthOfLastWord(String s) {
        int i = s.length() - 1;
        char[] chars = s.toCharArray();
        int count = 0;
        while(i >= 0) {
            // Special handling for position 0, e.g if input as
            // "a ", when index = 0, we cannot directly minus 1,
            // otherwise will cause IndexOutOfBound, for this
            // case, we need shut down on index = 0, if this
            // character is ' ', then terminate loop directly,
            // if not ' ', add 1 for length then terminate
            if(i == 0) {
                if(chars[i] != ' ') {
                    count++;
                }
                break;
            } else {
                if(chars[i] == ' ') {
                    i--;
                    //continue;
                } else {
                    i--;
                    count++;
                    // As i-- happen before if condition, no need to
                    // use chars[i--] to compare, also its safe as
                    // we handle i == 0 case separately, in else
                    // branch, i end at index 1, so even i--, i still
                    // keep non negative as 0, OR we can remove previous
                    // i-- and change chars[i] to chars[--i], which also
                    // make sure i minus 1 happen before compare with ' '
                    if(chars[i] == ' ') {
                        break;
                    }
                }
            }
        }
        return count;
    }
}
