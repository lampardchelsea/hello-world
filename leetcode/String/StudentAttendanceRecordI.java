/**
 * Refer to
 * https://leetcode.com/problems/student-attendance-record-i/#/description
 * You are given a string representing an attendance record for a student. The record only contains the 
 * following three characters:
    'A' : Absent.
    'L' : Late.
    'P' : Present.
 * A student could be rewarded if his attendance record doesn't contain more than one 'A' (absent) 
 * or more than two continuous 'L' (late).
 * You need to return whether the student could be rewarded according to his attendance record.
    Example 1:
    Input: "PPALLP"
    Output: True

    Example 2:
    Input: "PPALLL"
    Output: False
 *   
 * Solution
 * https://discuss.leetcode.com/topic/86559/java-simple-without-regex-3-lines
 * https://discuss.leetcode.com/topic/86466/java-1-liner
*/
public class Solution {
    // Solution 1:
    public boolean checkRecord(String s) {
        if(s.contains("LLL")) {
            return false;
        }
        int countA = 0;
        int i = 0;
        while(i < s.length()) {
            if(s.charAt(i) == 'A') {
                countA++;
            }
            if(countA == 2) {
                return false;
            }
            i++;
        }
        return true;
    }
  
    // Solution 2:
    public boolean checkRecord(String s) {
        if(s.contains("LLL") || (s.indexOf('A') != s.lastIndexOf('A'))) {
            return false;
        }
        return true;
    }
    
    // Solution 3:
    public boolean checkRecord(String s) {
        return !s.matches(".*LLL.*|.*A.*A.*");
    }
}
