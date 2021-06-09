/**
Refer to
https://leetcode.com/problems/reorder-data-in-log-files/
You are given an array of logs. Each log is a space-delimited string of words, where the first word is the identifier.

There are two types of logs:

Letter-logs: All words (except the identifier) consist of lowercase English letters.
Digit-logs: All words (except the identifier) consist of digits.
Reorder these logs so that:

The letter-logs come before all digit-logs.
The letter-logs are sorted lexicographically by their contents. If their contents are the same, then sort them lexicographically by their identifiers.
The digit-logs maintain their relative ordering.
Return the final order of the logs.

Example 1:
Input: logs = ["dig1 8 1 5 1","let1 art can","dig2 3 6","let2 own kit dig","let3 art zero"]
Output: ["let1 art can","let3 art zero","let2 own kit dig","dig1 8 1 5 1","dig2 3 6"]
Explanation:
The letter-log contents are all different, so their ordering is "art can", "art zero", "own kit dig".
The digit-logs have a relative order of "dig1 8 1 5 1", "dig2 3 6".

Example 2:
Input: logs = ["a1 9 2 3 1","g1 act car","zo4 4 7","ab1 off key dog","a8 act zoo"]
Output: ["g1 act car","a8 act zoo","ab1 off key dog","a1 9 2 3 1","zo4 4 7"]

Constraints:
1 <= logs.length <= 100
3 <= logs[i].length <= 100
All the tokens of logs[i] are separated by a single space.
logs[i] is guaranteed to have an identifier and at least one word after the identifier.
*/

// Solution 1: Customize sort
// Refer to
// https://leetcode.com/problems/reorder-data-in-log-files/discuss/193872/Java-Nothing-Fancy-15-lines-2ms-all-clear./337530
class Solution {
    public String[] reorderLogFiles(String[] logs) {
        Arrays.sort(logs, (s1, s2) -> {
            String[] split1 = s1.split(" ", 2);
            String[] split2 = s2.split(" ", 2);
            boolean isDigit1 = Character.isDigit(split1[1].charAt(0));
            boolean isDigit2 = Character.isDigit(split2[1].charAt(0));
            if(!isDigit1 && !isDigit2) {
                // both letter logs
                // The letter-logs are sorted lexicographically by their contents. 
                // If their contents are the same, then sort them lexicographically 
                // by their identifiers.
                int comp = split1[1].compareTo(split2[1]);
                if(comp == 0) {
                    return split1[0].compareTo(split2[0]);
                } else {
                    return comp;
                }
            } else if(isDigit1 && isDigit2) {
                // both digit logs
                // The digit-logs maintain their relative ordering.
                return 0;
            } else if(isDigit1 && !isDigit2) {
                // first digit second letter bring letter to forward.
                // The letter-logs come before all digit-logs.
                return 1;
            } else {
                // first letter second digit keep letter as forward.
                return -1;
            }
        });
        return logs;
    }
}
