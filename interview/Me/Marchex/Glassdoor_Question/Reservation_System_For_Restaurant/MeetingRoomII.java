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
 * https://segmentfault.com/a/1190000003894670
 */
public class MeetingRoomsII {
	private class Interval {
		int start;
		int end;
		Interval() { start = 0; end = 0; }
		Interval(int s, int e) { start = s; end = e; }
	}
	
	// Style 1
	// Refer to
	// https://discuss.leetcode.com/topic/20958/ac-java-solution-using-min-heap
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
	
	// Style 2
	/**
	 * 复杂度
	 * 时间 O(NlogN) 空间 O(1)
	 * 思路
	 * 这题的思路和Rearrange array to certain distance很像，我们要用贪心法，即从第一个时间段开始，选择下一个
	 * 最近不冲突的时间段，再选择下一个最近不冲突的时间段，直到没有更多。然后如果有剩余时间段，开始为第二个房间安排，选择最早的
	 * 时间段，再选择下一个最近不冲突的时间段，直到没有更多，如果还有剩余时间段，则开辟第三个房间，以此类推。这里的技巧是我们不
	 * 一定要遍历这么多遍，我们实际上可以一次遍历的时候就记录下，比如第一个时间段我们放入房间1，然后第二个时间段，如果和房间1
	 * 的结束时间不冲突，就放入房间1，否则开辟一个房间2。然后第三个时间段，如果和房间1或者房间2的结束时间不冲突，就放入房间1
	 * 或者2，否则开辟一个房间3，依次类推，最后统计开辟了多少房间。对于每个房间，我们只要记录其结束时间就行了，这里我们查找不
	 * 冲突房间时，只要找结束时间最早的那个房间。
	 * 这里还有一个技巧，如果我们把这些房间当作List来管理，每次查询需要O(N)时间，如果我们用堆来管理，可以用logN时间找到
	 * 时间最早结束的房间。
	 */
	public int minMeetingRooms2(Interval[] intervals) {
		if(intervals == null || intervals.length == 0) {
			return 0;
		}
		Arrays.sort(intervals, new Comparator<Interval>() {
			public int compare(Interval a, Interval b) {
				return a.start - b.start;
			}
		});
		PriorityQueue<Integer> minPQ = new PriorityQueue<Integer>(intervals.length, new Comparator<Integer>() {
			public int compare(Integer a, Integer b) {
				return a - b;
			}
		});
		// 用堆来管理房间的结束时间
		minPQ.add(intervals[0].end);
		for(int i = 1; i < intervals.length; i++) {
			// 如果当前时间段的开始时间大于最早结束的时间，则可以更新这个最早的结束时间为
			// 当前时间段的结束时间，如果小于的话，就加入一个新的结束时间，表示新的房间
			if(minPQ.peek() <= intervals[i].start) {
				minPQ.poll();
			}
		    minPQ.add(intervals[i].end);
		}
		// 有多少结束时间就有多少房间
		return minPQ.size();
	}
	
	
	public static void main(String[] args) {
		MeetingRoomsII m = new MeetingRoomsII();
		Interval one = m.new Interval(0,30);
		Interval two = m.new Interval(5,10);
		Interval three = m.new Interval(15,20);
		Interval[] intervals = {one, two, three};
		int result = m.minMeetingRooms2(intervals);
		System.out.print(result);
	}
}