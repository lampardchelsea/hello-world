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
 
 Complexity Analysis
 Time Complexity: O(N^2), where N is the number of events booked. For each new event, we process every 
 previous event to decide whether the new event can be booked. This leads to sum_k^N O(k) = O(N^2) complexity.
 Space Complexity: O(N), the size of the calendar.
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






















































































https://leetcode.com/problems/my-calendar-ii/description/

You are implementing a program to use as your calendar. We can add a new event if adding the event will not cause a triple booking.

A triple booking happens when three events have some non-empty intersection (i.e., some moment is common to all the three events.).

The event can be represented as a pair of integers start and end that represents a booking on the half-open interval [start, end), the range of real numbers x such that start <= x < end.

Implement the MyCalendarTwo class:
- MyCalendarTwo() Initializes the calendar object.
- boolean book(int start, int end) Returns true if the event can be added to the calendar successfully without causing a triple booking. Otherwise, return false and do not add the event to the calendar.
 
Example 1:
```
Input
["MyCalendarTwo", "book", "book", "book", "book", "book", "book"]
[[], [10, 20], [50, 60], [10, 40], [5, 15], [5, 10], [25, 55]]
Output
[null, true, true, true, false, true, true]

Explanation
MyCalendarTwo myCalendarTwo = new MyCalendarTwo();
myCalendarTwo.book(10, 20); // return True, The event can be booked. 
myCalendarTwo.book(50, 60); // return True, The event can be booked. 
myCalendarTwo.book(10, 40); // return True, The event can be double booked. 
myCalendarTwo.book(5, 15);  // return False, The event cannot be booked, because it would result in a triple booking.
myCalendarTwo.book(5, 10); // return True, The event can be booked, as it does not use time 10 which is already double booked.
myCalendarTwo.book(25, 55); // return True, The event can be booked, as the time in [25, 40) will be double booked with the third event, the time [40, 50) will be single booked, and the time [50, 55) will be double booked with the second event.

```
 
Constraints:
- 0 <= start < end <= 109
- At most 1000 calls will be made to book.
---
Attempt 1: 2023-11-30

Solution 1: Brute Force + Find double booking first (30 min)
```
class MyCalendarTwo {
    //        s1   e1
    // s2   e2       s2   e2
    // no conflict condition: e2 <= s1 or e1 <= s2
    // Based on De Morgan's law
    // conflict condition: e2 > s1 and e1 > s2
    List<int[]> list;
    List<int[]> doubleBooking;
    public MyCalendarTwo() {
        list = new ArrayList<>();
        doubleBooking = new ArrayList<>();
    }
    
    public boolean book(int start, int end) {
        // Detect triple booking based on double booking
        for(int[] range : doubleBooking) {
            if(end > range[0] && range[1] > start) {
                return false;
            }
        }
        // Build double booking
        for(int[] range : list) {
            if(end > range[0] && range[1] > start) {
                doubleBooking.add(new int[]{Math.max(range[0], start), Math.min(range[1], end)});
            }
        }
        list.add(new int[]{start, end});
        return true;
    }
}
/**
 * Your MyCalendarTwo object will be instantiated and called as such:
 * MyCalendarTwo obj = new MyCalendarTwo();
 * boolean param_1 = obj.book(start,end);
 */
```

Refer to
https://grandyang.com/leetcode/731/ 
这道题是 My Calendar I 的拓展，之前那道题说是不能有任何的重叠区间，而这道题说最多容忍两个重叠区域，注意是重叠区域，不是事件。比如事件 A，B，C 互不重叠，但是有一个事件D，和这三个事件都重叠，这样是可以的，因为重叠的区域最多只有两个。所以关键还是要知道具体的重叠区域，如果两个事件重叠，那么重叠区域就是它们的交集，求交集的方法是两个区间的起始时间中的较大值，到结束时间中的较小值。可以用一个 TreeSet 来专门存重叠区间，再用一个 TreeSet 来存完整的区间，那么思路就是，先遍历专门存重叠区间的 TreeSet，因为能在这里出现的区间，都已经是出现两次了，如果当前新的区间跟重叠区间有交集的话，说明此时三个事件重叠了，直接返回 false。如果当前区间跟重叠区间没有交集的话，则再来遍历完整区间的集合，如果有交集的话，那么应该算出重叠区间并且加入放重叠区间的 TreeSet 中。最后记得将新区间加入完整区间的 TreeSet 中。

Refer to
https://leetcode.com/problems/my-calendar-ii/editorial/
Intuition
Maintain a list of bookings and a list of double bookings. When booking a new event [start, end), if it conflicts with a double booking, it will have a triple booking and be invalid. Otherwise, parts that overlap the calendar will be a double booking.

Algorithm
Evidently, two events [s1, e1) and [s2, e2) do not conflict if and only if one of them starts after the other one ends: either e1 <= s2 OR e2 <= s1. By De Morgan's laws, this means the events conflict when s1 < e2 AND s2 < e1.

If our event conflicts with a double booking, it's invalid. Otherwise, we add conflicts with the calendar to our double bookings, and add the event to our calendar.
```
public class MyCalendarTwo {
    List<int[]> calendar;
    List<int[]> overlaps;
    MyCalendarTwo() {
        overlaps = new ArrayList();
        calendar = new ArrayList();
    }
    public boolean book(int start, int end) {
        for (int[] iv: overlaps) {
            if (iv[0] < end && start < iv[1]) return false;
        }
        for (int[] iv: calendar) {
            if (iv[0] < end && start < iv[1])
                overlaps.add(new int[]{Math.max(start, iv[0]), Math.min(end, iv[1])});
        }
        calendar.add(new int[]{start, end});
        return true;
    }
}
```
Complexity Analysis
- Time Complexity: O(N^2), where N is the number of events booked. For each new event, we process every previous event to decide whether the new event can be booked. This leads to 
  
   complexity.
- Space Complexity: O(N), the size of the calendar
---
Solution 2: Sweep Line + TreeMap (30 min)
注意：Sweep Line + TreeMap就是用自动排序的TreeMap结构代替了L253/P5.5.Meeting Rooms II中的开一个巨大数组包含所有可能的time slot的方式，其后的通过map.put()找delta，通过count += val找running sum都是必须的，所以time slot -> delta -> occupied(presum)三步走过程和L253一模一样，只是数据结构从单纯的array变成了TreeMap
```
class MyCalendarTwo {
    TreeMap<Integer, Integer> list;
    public MyCalendarTwo() {
        list = new TreeMap<>();
    }
    
    public boolean book(int start, int end) {
        // Increase the counter at the start time
        list.put(start, list.getOrDefault(start, 0) + 1);
        // Decrease the counter at the end time
        list.put(end, list.getOrDefault(end, 0) - 1);
        // This will track the number of ongoing events
        int count = 0;
        for(int val : list.values()) {
            count += val;
            // If at any point there are more than 2 active events, this booking overlaps with two other events
            if(count >= 3) {
                // The booking is not possible, so revert the changes
                list.put(start, list.get(start) - 1);
                list.put(end, list.get(end) + 1);
                if(list.get(start) == 0) {
                    list.remove(start);
                }
                return false;
            }
        }
        return true;
    }
}
/**
 * Your MyCalendarTwo object will be instantiated and called as such:
 * MyCalendarTwo obj = new MyCalendarTwo();
 * boolean param_1 = obj.book(start,end);
 */

Time Complexity: O(N^2), where N is the number of events booked. For each new event, we traverse list in O(N) time. 
Space Complexity: O(N), the size of list.
```

Refer to
https://leetcode.com/problems/my-calendar-ii/editorial/

Approach #2: Boundary Count [Accepted]

Intuition and Algorithm
When booking a new event [start, end), count delta[start]++ and delta[end]--. When processing the values of delta in sorted order of their keys, the running sum active is the number of events open at that time. If the sum is 3 or more, that time is (at least) triple booked.

A Python implementation was not included for this approach because there is no analog to TreeMap available.
```
class MyCalendarTwo {
    TreeMap<Integer, Integer> delta;
    public MyCalendarTwo() {
        delta = new TreeMap();
    }
    public boolean book(int start, int end) {
        delta.put(start, delta.getOrDefault(start, 0) + 1);
        delta.put(end, delta.getOrDefault(end, 0) - 1);
        int active = 0;
        for (int d: delta.values()) {
            active += d;
            if (active >= 3) {
                delta.put(start, delta.get(start) - 1);
                delta.put(end, delta.get(end) + 1);
                if (delta.get(start) == 0)
                    delta.remove(start);
                return false;
            }
        }
        return true;
    }
}
```
Complexity Analysis
- Time Complexity: O(N^2), where N is the number of events booked. For each new event, we traverse delta in O(N) time.
- Space Complexity: O(N), the size of delta.


Refer to
https://algo.monster/liteproblems/731

Problem Description

In this problem, you are tasked with creating a calendar system that can add new events without creating a situation wherein three events overlap in time—this is what is referred to as a "triple booking." Events are defined by their start and end times, with the start time being inclusive and the end time being exclusive, signified by the interval [start, end). The problem requires the implementation of a class, MyCalendarTwo, which provides two functionalities:
1. Initializing the calendar object.
2. Booking an event (specified by its start and end) if doing so does not result in any triple booking. It returns true if the event can be added without a triple booking, otherwise false.

The objective is to efficiently manage a calendar by keeping track of events while ensuring at most two events may overlap, but not three. This requires careful tracking of each event's start and end times.


Intuition

The intuition behind the solution comes from the need to manage the overlaps efficiently. Given that double bookings are allowed but not triple bookings, we need to track whenever an event starts and ends, and how this impacts the existing timeline of bookings. Here, we can use a data structure such as the SortedDict from the sortedcontainers module which keeps the keys sorted and allows us to efficiently determine starting and ending points of events.

The approach is to increment the count at the event start time and decrement it at the event end time. Every time we attempt to book an event, we update the timeline with the start and end times. After adding the event to the calendar, we iterate through all time points in our SortedDict and maintain a running sum that represents the current number of overlapping events. If, at any time, this sum exceeds 2, it means we are trying to create a triple booking, which is not allowed. At this point, we need to revert this booking by decrementing the count at the start time and incrementing at the end time, and return false since we cannot book the event. If we successfully iterate through the entire sorted dictionary without the sum exceeding 2, the event is successfully booked without causing a triple booking, so we return true.


Solution Approach

The solution uses a class MyCalendarTwo that maintains a SortedDict from the sortedcontainers module. This dictionary will keep track of how many events are starting or ending at any given time. This approach is akin to employing a sweep line algorithm commonly used in computational geometry. The key idea is to "sweep" across the calendar and keep a count of concurrent events.

When booking a new event, we apply these steps in the book method:
1. Increment the counter for the event's start time: self.sd[start] = self.sd.get(start, 0) + 1. This represents the beginning of an event.
2. Decrement the counter for the event's end time: self.sd[end] = self.sd.get(end, 0) - 1. This signals the end of an event.


After updating the counters, we need to check for triple bookings:
3. Iterate over all values in our sorted dictionary using self.sd.values(). We keep a running sum s that represents the current number of overlapping events.
   a. For each value v in the SortedDict, we add it to our running sum s += v.
   b. If at any point our sum exceeds 2 (if s > 2), it signifies that the attempted booking would result in a triple booking, thus we revert the changes done in step 1 and 2 by decrementing the start time counter and incrementing the end time counter:
```
self.sd[start] -= 1
self.sd[end] += 1
```
           c. Since adding the event leads to a triple booking, we return False.

4. If we complete the iteration without our running sum ever exceeding 2, it means we have successfully added the event without causing a triple booking, and we return True.


The SortedDict data structure allows efficient insertion, deletion, and iteration, which is crucial for the performance of this algorithm. By incrementing start times and decrementing end times, we smartly keep track of ongoing events, and by checking the running sum, we enforce the no-triple-booking rule.



Example Walkthrough

Let's go through an example to illustrate the solution approach using the MyCalendarTwo class.

First, we initialize the MyCalendarTwo object:
```
my_calendar = MyCalendarTwo()
```

Our SortedDict starts empty as no events have been booked yet.
Now, let's try booking our first event [10, 20):
```
result = my_calendar.book(10, 20)
```

- self.sd[10] becomes 1 because one event starts at time 10.
- self.sd[20] becomes -1 because one event ends at time 20.
- We iterate through the SortedDict, and the running sum s never exceeds 2, because we only have one event.
- The result is True, and the first event is successfully booked.
Now, suppose we book a second event [15, 25):
```
result = my_calendar.book(15, 25)
```

- self.sd[15] gets incremented to 1, and since there is already one event overlapping at this time, the running total of events is now 2 at time 15 (as earlier, the running sum was 1 at 10, and then it becomes 2 at 15).
- self.sd[25] becomes -1.
- We iterate through the SortedDict, which now looks like this: 10: 1, 15: 1, 20: -1, 25: -1, and the running sum s never exceeds 2.
- The result is True, and the second event is successfully booked.
Finally, let's try booking a third event [20, 30):
```
result = my_calendar.book(20, 30)
```

- self.sd[20] remains unchanged because when an event ends, another begins at the same time.
- self.sd[30] becomes -1.
- The sorted dictionary now is 10: 1, 15: 1, 20: -1, 25: -1, 30: -1, and while iterating, the running sum s does not exceed 2.
- The booking is successful as the sum s remains at most 2 at all points in time.
If we then attempt to book a fourth event [10, 15):
```
result = my_calendar.book(10, 15)
```

- self.sd[10] would be incremented, going from 1 to 2, as there is now a second event starting at time 10.
- self.sd[15] would be decremented, going from 1 to 0.
- During iteration, when we reach time 15, the running sum s would become 3 (1 for the first event starting at 10, and 2 for the second and fourth events overlapping between 10 and 15) which exceeds our limit of 2.
- This would result in a triple booking, so we revert the changes (self.sd[10] goes back to 1 and self.sd[15] back to 1), and the result is False.
The event [10, 15) cannot be booked without causing a triple booking, and therefore, our MyCalendarTwo correctly returns False.

```
import java.util.Map;
import java.util.TreeMap;
public class MyCalendarTwo {
  
    // Use TreeMap to automatically keep the keys sorted
    private Map<Integer, Integer> timeMap = new TreeMap<>();
    // Default constructor (not explicitly needed unless more constructors are provided)
    public MyCalendarTwo() {
    }
    // Function to book a new event from start to end time
    public boolean book(int start, int end) {
        // Increase the counter at the start time
        timeMap.put(start, timeMap.getOrDefault(start, 0) + 1);
        // Decrease the counter at the end time
        timeMap.put(end, timeMap.getOrDefault(end, 0) - 1);
        int activeEvents = 0; // This will track the number of ongoing events
        // Iterate through the values in TreeMap
        for (int eventsCount : timeMap.values()) {
            // Increment the count of active events
            activeEvents += eventsCount;
            // If at any point there are more than 2 active events, this booking overlaps with two other events
            if (activeEvents > 2) {
                // The booking is not possible, so revert the changes
                timeMap.put(start, timeMap.get(start) - 1);
                timeMap.put(end, timeMap.get(end) + 1);
                // Return false as the booking overlaps and cannot be accepted
                return false;
            }
        }
        // The booking does not overlap with two or more events, so return true
        return true;
    }
}
```
