import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/minimum-time-difference/#/description
 * Given a list of 24-hour clock time points in "Hour:Minutes" format, find the minimum minutes 
 * difference between any two time points in the list.
   
   Example 1:
   Input: ["23:59","00:00"]
   Output: 1
	
 * Note:
 * The number of time points in the given list is at least 2 and won't exceed 20000.
 * The input time is legal and ranges from 00:00 to 23:59.
 * 
 * Solution
 * https://discuss.leetcode.com/topic/82582/java-sorting-with-a-sentinel-node
 * https://discuss.leetcode.com/topic/82573/verbose-java-solution-bucket
 */
public class MinimumTimeDifference {
	
	/**
	 * Wrong answer with wrongly parsing all "00" to "24" on hour
	 */
//	public int findMinDifference(List<String> timePoints) {
//        int[] array = new int[timePoints.size()];
//        for(int i = 0; i < array.length; i++) {
//            array[i] = time(timePoints.get(i));
//        }
//        Arrays.sort(array);
//        int min = Integer.MAX_VALUE;
//        for(int i = 1; i < array.length; i++) {
//             if(array[i] - array[i - 1] < min) {
//                 min = array[i] - array[i - 1];
//             }
//        }
//        return min;
//    }
//    
//    // Input:["12:12","00:13"]
//    // Output:721 --> from 12:12 to 00:13(24:13) 
//    // Expected:719 --> from 00:13 to 12:12
//    // So for any hour start with 00 must consider both situations
//    // now only consider as transfer 00 to 24, which result into 
//    // one kind of result
//    public int time(String timePoint) {
//        String timePointHour = timePoint.split(":")[0];
//        String timePointMin = timePoint.split(":")[1];
//        int timeHour = timePointHour.equals("00") ? 24 : Integer.parseInt(timePointHour);
//        //int timeMin = timePointMin.equals("00") ? 60 : Integer.parseInt(timePointMin);
//        int timeMin = Integer.parseInt(timePointMin);
//        int time = timeHour * 60 + timeMin;
//        return time;
//    }

	// Solution 1:
	public int findMinDifference(List<String> timePoints) {
		List<Time> times = new ArrayList<Time>();
		for(String s : timePoints) {
			int h = Integer.parseInt(s.split(":")[0]);
			int m = Integer.parseInt(s.split(":")[1]);
			times.add(new Time(h, m));
		}
		Collections.sort(times);
		// Very tricky part
		// Refer to
		// https://discuss.leetcode.com/topic/82582/java-sorting-with-a-sentinel-node/3
		// The idea is to duplicate the earliest moment in out input.
		// Let's say, we have a input [2:00, 7:30, 14:30, 23:00], the minimal difference 
		// should be 3 hours for 2:00 and 23:00, but if we don't process the array, 
		// it will give us 21 hours instead of 3 hours. 
		// After this step, it would become [2:00, 7:30, 14:30, 20:00, 26:00]. (26:00 is 
		// another way to represent the earliest moment 2:00), now we can handle this 
		// case successfully.
		// We should treat the time as a loop circle, last time point also can be previous
		// point of the first one, e.g 23:00 can also be previous than 1:00
		times.add(new Time(times.get(0).h + 24, times.get(0).m));
		int diffMin = Integer.MAX_VALUE;
		for(int i = 0; i < times.size() - 1; i++) {
			int diff = Math.abs(times.get(i).diff(times.get(i + 1)));
			if(diff < diffMin) {
				diffMin = diff;
			}
		}
		return diffMin;
	}
	
	private class Time implements Comparable<Time>{
		int h;
		int m;
		public Time(int h, int m) {
			this.h = h;
			this.m = m;
		}
		@Override
		public int compareTo(Time other) {
			if(this.h == other.h) {
				return this.m - other.m;
			}
			return this.h - other.h;
		}
		public int diff(Time other) {
			return (this.h - other.h) * 60 + this.m - other.m;
		}
	}
	
	
    // Solution 2:
	public int findMinDifference1(List<String> timePoints) {
		boolean[] mark = new boolean[60 * 24];
		for(String s : timePoints) {
			int h = Integer.parseInt(s.split(":")[0]);
			int m = Integer.parseInt(s.split(":")[1]);
			if(mark[h * 60 + m]) {
				return 0;
			}
			mark[h * 60 + m] = true;
		}
		int prev = 0;
		int first = Integer.MAX_VALUE;
		int last = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < 24 * 60; i++) {
			if(mark[i]) {
				if(first != Integer.MAX_VALUE) {
					min = Math.min(min, i - prev);
				} 
				first = Math.min(first, i);
				last = Math.max(last, i);
				prev = i;
			}
		}
		// Very tricky part
		// Refer to
		// https://discuss.leetcode.com/topic/82573/verbose-java-solution-bucket/5
		// It only calculates i - prev during the loop. At last, we need to take care 
		// the last and first time points. i.e. [00:00, 10:00, 23:00]. The smallest 
		// gap is between 23:00 and 00:00.
		min = Math.min(min, (24 * 60 - last + first));
		return min;
	}
	
	
    public static void main(String[] args) {
    	MinimumTimeDifference m = new MinimumTimeDifference();
    	List<String> timePoints = new ArrayList<String>();
    	timePoints.add("12:12");
    	timePoints.add("00:13");
    	int result = m.findMinDifference(timePoints);
    	System.out.println(result);
    }
    
}

