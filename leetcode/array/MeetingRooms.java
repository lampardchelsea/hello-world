import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Refer to
 * https://segmentfault.com/a/1190000003894670
 * Meeting Rooms
 * Given an array of meeting time intervals consisting of start and end times 
 * [[s1,e1],[s2,e2],...] (si < ei), determine if a person could attend all meetings.
 * For example, Given [[0, 30],[5, 10],[15, 20]], return false.
 * 
 * 
 * Solution
 * https://segmentfault.com/a/1190000003894670\
 * https://discuss.leetcode.com/topic/20959/ac-clean-java-solution/2
 */
public class MeetingRooms {
	private class Interval {
		int start;
		int end;
		Interval() { start = 0; end = 0; }
		Interval(int s, int e) { start = s; end = e; }
	}
	
	// Solution 1: Intuitive way
	public boolean canAttendMeetings(Interval[] intervals) {
		if(intervals == null || intervals.length == 0) {
			return false;
		}
		Arrays.sort(intervals, new Comparator<Interval>() {
			public int compare(Interval a, Interval b) {
				return a.start - b.start;
			}
		});
		for(int i = 1; i < intervals.length; i++) {
			if(intervals[i].start < intervals[i - 1].end) {
				return false;
			}
		}
		return true;
	}
	
	
	// Solution 2: 
	// Refer to
	// https://discuss.leetcode.com/topic/20959/ac-clean-java-solution/2
	/**
	 * In the above implementation, you use the sort method to get all objects 
	 * sorted - O(nlogn) time complexity.
	 * After sorting you go through each object comparing it with the previous 
	 * object to find if there is an overlap - O(n) time complexity.
	 * What if you can find if there is an overlap while you sort and raise a flag?
	 */
	public boolean canAttendMeetings2(Interval[] intervals) {
		if(intervals == null || intervals.length == 0) {
			return false;
		}
		try {
			Arrays.sort(intervals, new IntervalComparator());	
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	private class IntervalComparator implements Comparator<Interval> {
		public int compare(Interval a, Interval b) {
			if(a.start < b.start && a.end <= b.end) {
				return -1;
			} else if(a.start > b.start && a.start >= b.end) {
				return 1;
			}
			throw new RuntimeException();
		}		
	}
	
	public static void main(String[] args) {
		MeetingRooms m = new MeetingRooms();
		Interval one = m.new Interval(0,30);
		Interval two = m.new Interval(5,10);
		Interval three = m.new Interval(15,20);
		Interval[] intervals = {one, two, three};
		boolean result = m.canAttendMeetings2(intervals);
		System.out.print(result);
	}
}

