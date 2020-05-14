/**
 Refer to
 https://leetcode.com/problems/my-calendar-ii/
 Implement a MyCalendarTwo class to store your events. A new event can be added if adding the event will not 
 cause a triple booking.
 Your class will have one method, book(int start, int end). Formally, this represents a booking on the half 
 open interval [start, end), the range of real numbers x such that start <= x < end.
 A triple booking happens when three events have some non-empty intersection (ie., there is some time that is 
 common to all 3 events.)
 For each call to the method MyCalendar.book, return true if the event can be added to the calendar successfully 
 without causing a triple booking. Otherwise, return false and do not add the event to the calendar.
 Your class will be called like this: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)
 
 Example 1:
 MyCalendar();
 MyCalendar.book(10, 20); // returns true
 MyCalendar.book(50, 60); // returns true
 MyCalendar.book(10, 40); // returns true
 MyCalendar.book(5, 15); // returns false
 MyCalendar.book(5, 10); // returns true
 MyCalendar.book(25, 55); // returns true
 
 Explanation: 
 The first two events can be booked.  The third event can be double booked.
 The fourth event (5, 15) can't be booked, because it would result in a triple booking.
 The fifth event (5, 10) can be booked, as it does not use time 10 which is already double booked.
 The sixth event (25, 55) can be booked, as the time in [25, 40) will be double booked with the third event;
 the time [40, 50) will be single booked, and the time [50, 55) will be double booked with the second event.
 
 Note:
 The number of calls to MyCalendar.book per test case will be at most 1000.
 In calls to MyCalendar.book(start, end), start and end are integers in the range [0, 10^9].
*/

// Solution 1: Brutal Force
// Refer to
// https://leetcode.com/problems/my-calendar-ii/discuss/109519/JavaC%2B%2B-Clean-Code-with-Explanation
/**
 The big idea is pretty simple:
 Each time of book, instead of fail a book when there is 1 or more overlap with existing books as in MyCalendar I, 
 we just want to make sure these overlaps does not overlap - having overlap is now ok, but overlapped period cannot 
 be overlapped again.
 So we just need to keep track of all the overlaps with any previous books
 
 MyCalendar I can be reused to track the overlaps during each book.
 
 How to calculate overlap of 2 intervals
 Assume a start earlier than b, (if not reverse), there could be 3 case, but in any case, an overlap(either positive 
 or negative) can always be represented as: (max(a0, b0), min(a1, b1))

 case 1: b ends before a ends:
 a: a0 |-------------| a1
 b:     b0 |-----| b1

 case 2: b ends after a ends:
 a: a0 |--------| a1
 b:     b0 |--------| b1

 case 3: b starts after a ends: (negative overlap)
 a: a0 |----| a1
 b:              b0 |----| b1
*/

// https://leetcode.com/problems/my-calendar-ii/solution/
/**
 Intuition
 Maintain a list of bookings and a list of double bookings. When booking a new event [start, end), 
 if it conflicts with a double booking, it will have a triple booking and be invalid. Otherwise, 
 parts that overlap the calendar will be a double booking.

 Algorithm
 Evidently, two events [s1, e1) and [s2, e2) do not conflict if and only if one of them starts after 
 the other one ends: either e1 <= s2 OR e2 <= s1. By De Morgan's laws, this means the events conflict 
 when s1 < e2 AND s2 < e1.
 
 If our event conflicts with a double booking, it's invalid. Otherwise, we add conflicts with the 
 calendar to our double bookings, and add the event to our calendar.
*/
class MyCalendarTwo {
    List<int[]> calendar;
    List<int[]> overlaps;
    
    public MyCalendarTwo() {
        calendar = new ArrayList<int[]>();
        overlaps = new ArrayList<int[]>();
    }
    
    public boolean book(int start, int end) {
        for(int[] overlap : overlaps) {
            if(overlap[1] > start && overlap[0] < end) {
                return false;
            }
        }
        for(int[] element : calendar) {
            if(element[1] > start && element[0] < end) {
                overlaps.add(new int[] {Math.max(start, element[0]), Math.min(end, element[1])});
            }
        }
        calendar.add(new int[] {start, end});
        return true;
    }
}

/**
 * Your MyCalendarTwo object will be instantiated and called as such:
 * MyCalendarTwo obj = new MyCalendarTwo();
 * boolean param_1 = obj.book(start,end);
 */
