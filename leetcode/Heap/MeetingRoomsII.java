
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Refer to
 * https://segmentfault.com/a/1190000003894670
 * Given an array of meeting time intervals consisting of start and end times 
 * [[s1,e1],[s2,e2],...] (si < ei), find the minimum number of conference rooms required.
 * For example, Given [[0, 30],[5, 10],[15, 20]], return 2.
 * 
 * 
 * Solution
 * https://discuss.leetcode.com/topic/20958/ac-java-solution-using-min-heap
 */
public class MeetingRoomsII {
	private class Interval {
		int start;
		int end;
		Interval() { start = 0; end = 0; }
		Interval(int s, int e) { start = s; end = e; }
	}
	
	public int minMeetingRooms(Interval[] intervals) {
		if(intervals == null || intervals.length == 0) {
			return 0;
		}
		// Sort intervals by start time
	    Arrays.sort(intervals, new Comparator<Interval>() {
	    	public int compare(Interval a, Interval b) {
	    		return a.start - b.start;
	    	}
	    });
	    // Use min heap to track minimum end time of merged intervals
	    // min heap size is required room numbers
	    PriorityQueue<Interval> minPQ = new PriorityQueue<Interval>(intervals.length, new Comparator<Interval>() {
	    	public int compare(Interval a, Interval b) {
	    		return a.end - b.end;
	    	}
	    });
	    // Initial min heap with first meeting, put it to a meeting room
	    minPQ.add(intervals[0]);
	    for(int i = 1; i < intervals.length; i++) {
	    	// Get the meeting room that finished earliest
	    	Interval interval = minPQ.poll();
	    	// If the current meeting starts right after 
	    	// there's no need for a new room, merge two
	    	// intervals
	    	if(intervals[i].start >= interval.end) {
	    		interval.end = intervals[i].end;
	        // Otherwise this meeting requires a new room
	    	} else {
	    		minPQ.add(intervals[i]);
	    	}
	    	// Don't forget to add back the merged interval to min heap
	    	minPQ.add(interval);
	    }
	    return minPQ.size();
	}
	
	public static void main(String[] args) {
		MeetingRoomsII m = new MeetingRoomsII();
		Interval one = m.new Interval(0,30);
		Interval two = m.new Interval(5,10);
		Interval three = m.new Interval(15,20);
		Interval[] intervals = {one, two, three};
		int result = m.minMeetingRooms(intervals);
		System.out.print(result);
	}
}
