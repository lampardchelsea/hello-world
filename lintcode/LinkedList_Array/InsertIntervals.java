/**
 * Refer to
 * http://www.lintcode.com/en/problem/insert-interval/
 * Given a non-overlapping interval list which is sorted by start point.

    Insert a new interval into it, make sure the list is still in order and non-overlapping (merge intervals if necessary).

    Have you met this question in a real interview? Yes
    Example
    Insert [2, 5] into [[1,2], [5,9]], we get [[1,9]].

    Insert [3, 4] into [[1,2], [5,9]], we get [[1,2], [3,4], [5,9]].
 *
 *
 * Solution
 * http://www.jiuzhang.com/solutions/insert-interval/
*/
/**
 * Definition of Interval:
 * public classs Interval {
 *     int start, end;
 *     Interval(int start, int end) {
 *         this.start = start;
 *         this.end = end;
 *     }
 */

// Solution 1
// Just remember this intutive way is good, separate 3 cases based on overlapping or not
public class Solution {
    /*
     * @param intervals: Sorted interval list.
     * @param newInterval: new interval.
     * @return: A new interval list.
     */
    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        if(intervals == null || newInterval == null) {
            return intervals;
        }
        List<Interval> result = new ArrayList<Interval>();
        int insertPos = 0;
        for(Interval interval : intervals) {
            // If no overlapping and current interval before newInterval
            // add current interval and increase insert position for newInterval
            if(interval.end < newInterval.start) {
                result.add(interval);
                insertPos++;
            // If no overlapping and new interval before current interval
            // add current interval and no change on insert position for newInteval
            } else if(interval.start > newInterval.end) {
                result.add(interval);
            // If overlapping, update newInterval
            } else {
                newInterval.start = Math.min(interval.start, newInterval.start);
                newInterval.end = Math.max(interval.end, newInterval.end);
            }
        }
        result.add(insertPos, newInterval);
        return result;
    }
}


// Solution 2: Based on Merge Intervals 
// First insert new interval into original intervals without considering overlapping, then do the same as merge intervals
/**
 * Definition of Interval:
 * public classs Interval {
 *     int start, end;
 *     Interval(int start, int end) {
 *         this.start = start;
 *         this.end = end;
 *     }
 */


public class Solution {
    /*
     * @param intervals: Sorted interval list.
     * @param newInterval: new interval.
     * @return: A new interval list.
     */
    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        if(intervals == null || newInterval == null) {
            return intervals;
        }
        List<Interval> result = new ArrayList<Interval>();
        // First insert new interval into original intervals without considering overlapping
        int insertPos = 0;
        while(insertPos < intervals.size() && intervals.get(insertPos).start < newInterval.start) {
            insertPos++;
        }
        intervals.add(insertPos, newInterval);
        // Then do the same as merge intervals
        Interval last = null;
        for(Interval interval : intervals) {
            Interval cur = interval;
            if(last == null || last.end < cur.start) {
                last = cur;
                result.add(cur);
            } else {
                last.end = Math.max(cur.end, last.end);   
            }
        }
        return result;
    }
}
