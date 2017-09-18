/**
 * Refer to
 * http://www.lintcode.com/en/problem/merge-intervals/
 * Given a collection of intervals, merge all overlapping intervals.

    Have you met this question in a real interview? Yes
    Example
    Given intervals => merged intervals:

    [                     [
      [1, 3],               [1, 6],
      [2, 6],      =>       [8, 10],
      [8, 10],              [15, 18]
      [15, 18]            ]
    ]
 * 
 * Solution
 * http://www.jiuzhang.com/solutions/merge-intervals/
*/


// Style 1: No need to separately handle last item not added into result
//          for loop start from 0
/**
 * Definition of Interval:
 * public class Interval {
 *     int start, end;
 *     Interval(int start, int end) {
 *         this.start = start;
 *         this.end = end;
 *     }
 */
public class Solution {
    /*
     * @param intervals: interval list.
     * @return: A new interval list.
     */
    public List<Interval> merge(List<Interval> intervals) {
        List<Interval> result = new ArrayList<Interval>();
        if(intervals == null || intervals.size() == 0) {
            return result;
        }
        Collections.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval a, Interval b) {
                return a.start - b.start;
            } 
        });
        Interval last = null;
        for(int i = 0; i < intervals.size(); i++) {
            Interval cur = intervals.get(i);
            if(last == null || last.end < cur.start) {
                last = cur;
                result.add(cur);
            } else {
                last.end = Math.max(last.end, cur.end);
            }
        }
        return result;
    }
}

// Style 2: For loop start from 1, need to handle last item not added into result issue
/**
 * Definition of Interval:
 * public class Interval {
 *     int start, end;
 *     Interval(int start, int end) {
 *         this.start = start;
 *         this.end = end;
 *     }
 */


public class Solution {
    /*
     * @param intervals: interval list.
     * @return: A new interval list.
     */
    public List<Interval> merge(List<Interval> intervals) {
        List<Interval> result = new ArrayList<Interval>();
        if(intervals == null || intervals.size() == 0) {
            return result;
        }
        Collections.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval a, Interval b) {
                return a.start - b.start;
            } 
        });
        Interval last = intervals.get(0);
        for(int i = 1; i < intervals.size(); i++) {
            Interval cur = intervals.get(i);
            if(cur.start <= last.end) {
			    // Continuously merge until cur.start > last.end
                last.end = Math.max(cur.end, last.end);
            } else {
			    // When encounter cur.start > last.end add last
				// section into result, also update last section
				// with current section
                result.add(last);
                last = cur;
            }
        }
        /**
         If forget about add 'last' will cause error as below,
         because this loop style will always miss the last
         updated 'last'
         Input:[[1,3],[2,6],[8,10],[15,18]]
         Output:[[1,6],[8,10]]
         Expected:[[1,6],[8,10],[15,18]]
        */
        result.add(last);
        return result;
    }
}



