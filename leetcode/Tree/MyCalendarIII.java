/**
 Refer to
 https://leetcode.com/problems/my-calendar-iii/
 Implement a MyCalendarThree class to store your events. A new event can always be added.
 
 Your class will have one method, book(int start, int end). Formally, this represents a booking on the half 
 open interval [start, end), the range of real numbers x such that start <= x < end.

 A K-booking happens when K events have some non-empty intersection (ie., there is some time that is common to all K events.)

 For each call to the method MyCalendar.book, return an integer K representing the largest integer such that 
 there exists a K-booking in the calendar.

 Your class will be called like this: MyCalendarThree cal = new MyCalendarThree(); MyCalendarThree.book(start, end)

 Example 1:
 MyCalendarThree();
 MyCalendarThree.book(10, 20); // returns 1
 MyCalendarThree.book(50, 60); // returns 1
 MyCalendarThree.book(10, 40); // returns 2
 MyCalendarThree.book(5, 15); // returns 3
 MyCalendarThree.book(5, 10); // returns 3
 MyCalendarThree.book(25, 55); // returns 3
 
 Explanation: 
 The first two events can be booked and are disjoint, so the maximum K-booking is a 1-booking.
 The third event [10, 40) intersects the first event, and the maximum K-booking is a 2-booking.
 The remaining events cause the maximum K-booking to be only a 3-booking.
 Note that the last event locally causes a 2-booking, but the answer is still 3 because
 eg. [10, 20), [10, 40), and [5, 15) are still triple booked.

 Note:
 The number of calls to MyCalendarThree.book per test case will be at most 400.
 In calls to MyCalendarThree.book(start, end), start and end are integers in the range [0, 10^9].
*/

// Solution 1: Timeline
// Refer to
// https://leetcode.com/problems/my-calendar-iii/discuss/109556/JavaC%2B%2B-Clean-Code
/**
 Summarize
 This is to find the maximum number of concurrent ongoing event at any time.
 We can log the start & end of each event on the timeline, each start add a new ongoing event at that time, 
 each end terminate an ongoing event. Then we can scan the timeline to figure out the maximum number of 
 ongoing event at any time.
 The most intuitive data structure for timeline would be array, but the time spot we have could be very sparse, 
 so we can use sorted map to simulate the time line to save space.
*/

// https://www.cnblogs.com/grandyang/p/7968035.html
/**
 下面这种方法相当的巧妙，建立一个时间点和次数之间的映射，规定遇到起始时间点，次数加1，遇到结束时间点，次数减1。
 那么首先更改新的起始时间 start 和结束时间 end 的映射，start 对应值增1，end 对应值减1。然后定义一个变量 cnt，
 来统计当前的次数。使用 TreeMap 具有自动排序的功能，所以遍历的时候就是按时间顺序的，最先遍历到的一定是一个起始
 时间，所以加上其映射值，一定是个正数。如果此时只有一个区间，就是刚加进来的区间的话，那么首先肯定遍历到 start，
 那么 cnt 此时加1，然后就会遍历到 end，那么此时 cnt 减1，最后下来 cnt 为0，没有重叠。还是用具体数字来说吧，
 现在假设 TreeMap 中已经加入了一个区间 [3, 5) 了，就有下面的映射：

 3 -> 1
 5 -> -1

 假如此时要加入的区间为 [3, 8) 的话，则先对3和8分别加1减1，此时的映射为：

 3 -> 2
 5 -> -1
 8 -> -1

 最先遍历到3，cnt 为2，没有超过3，此时有两个事件有重叠，是允许的。然后遍历5和8，分别减去1，最终又变成0了，
 始终 cnt 没有超过2，所以是符合题意的。如果此时再加入一个新的区间 [1, 4)，则先对1和4分别加1减1，那么此时的映射为：

 1 -> 1
 3 -> 2
 4 -> -1
 5 -> -1
 8 -> -1

 先遍历到1，cnt为1，然后遍历到3，此时 cnt 为3了，那么就知道有三个事件有重叠区间了，所以这个新区间是不能加入的，
 需要还原其 start 和 end 做的操作，把 start 的映射值减1，end 的映射值加1，然后返回 false。否则没有三个事件
 有共同重叠区间的话，返回 true 即可
*/
class MyCalendarThree {
    private TreeMap<Integer, Integer> timeline = new TreeMap<Integer, Integer>();
    
    public MyCalendarThree() {
            
    }
    
    public int book(int start, int end) {
        // 1 new event will start at [s]
        timeline.put(start, timeline.getOrDefault(start, 0) + 1);
        // 1 new event will end at [e]
        timeline.put(end, timeline.getOrDefault(end, 0) - 1);
        int ongoing = 0;
        int k = 0;
        for(int v : timeline.values()) {
            k = Math.max(k, ongoing += v);
        }
        return k;
    }
}

/**
 * Your MyCalendarThree object will be instantiated and called as such:
 * MyCalendarThree obj = new MyCalendarThree();
 * int param_1 = obj.book(start,end);
 */
