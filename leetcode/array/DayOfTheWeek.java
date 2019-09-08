/**
 Refer to
 https://leetcode.com/problems/day-of-the-week/
 Given a date, return the corresponding day of the week for that date.

The input is given as three integers representing the day, month and year respectively.

Return the answer as one of the following values {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"}.

Example 1:
Input: day = 31, month = 8, year = 2019
Output: "Saturday"

Example 2:
Input: day = 18, month = 7, year = 1999
Output: "Sunday"

Example 3:
Input: day = 15, month = 8, year = 1993
Output: "Sunday"

Constraints:
The given dates are valid dates between the years 1971 and 2100.
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/day-of-the-week/discuss/377384/JavaC++Python-Zeller-Formula/339135
// If we don't know this formula, we can still solve this problem based on 1971-1-1 is Friday.
class Solution {
    public String dayOfTheWeek(int day, int month, int year) {
        String[] week = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        int[] daysOfMonth = {31,28,31,30,31,30,31,31,30,31,30,31};
        int num = 0;
        for(int i = 1971;i<year;i++){
            if(i % 4 == 0) num += 366;
            else num += 365;
        }
        if(year % 4 == 0) daysOfMonth[1] = 29;
        for(int i = 0;i<month-1;i++){
            num += daysOfMonth[i];
        }
        num += day - 1;
        return week[(num + 5)%7];
    }
}


// Solution 2: Zeller-Formula
// Refer to
// https://www.hackerearth.com/blog/developers/how-to-find-the-day-of-a-week/
// https://leetcode.com/problems/day-of-the-week/discuss/377384/JavaC%2B%2BPython-Zeller-Formula
class Solution {
    public String dayOfTheWeek(int day, int month, int year) {
        int t[] = {0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4}; 
        year -= (month < 3) ? 1 : 0;
        int temp = (year + year / 4 - year / 100 + year / 400 + t[month - 1] + day) % 7;
        String[] week = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        return week[temp];
    }
}
