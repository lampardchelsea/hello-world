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










































https://leetcode.com/problems/minimum-time-difference/description/
Given a list of 24-hour clock time points in "HH:MM" format, return the minimum minutes difference between any two time-points in the list.
 
Example 1:
Input: timePoints = ["23:59","00:00"]
Output: 1

Example 2:
Input: timePoints = ["00:00","23:59","00:00"]
Output: 0
 
Constraints:
- 2 <= timePoints.length <= 2 * 10^4
- timePoints[i] is in the format "HH:MM".
--------------------------------------------------------------------------------
Attempt 1: 2025-11-28
Solution 1: String + Math (10 min)
class Solution {
    public int findMinDifference(List<String> timePoints) {
        // Step 1: Convert all time points to minutes
        int n = timePoints.size();
        int[] minutes = new int[n];
        for(int i = 0; i < n; i++) {
            minutes[i] = convert(timePoints.get(i));
        }
        Arrays.sort(minutes);
        // Step 3: Find minimum difference between consecutive times
        int minDiff = Integer.MAX_VALUE;
        for(int i = 1; i < n; i++) {
            minDiff = Math.min(minDiff, minutes[i] - minutes[i - 1]);
        }
        // Step 4: Check the circular difference (last and first)
        // The circular difference is: (1440 - last) + first
        int circularDiff = minutes[0] + 1440 - minutes[n - 1];
        minDiff = Math.min(minDiff, circularDiff);
        return minDiff;
    }

    private int convert(String timePoint) {
        int h = Integer.parseInt(timePoint.substring(0, 2));
        int m = Integer.parseInt(timePoint.substring(3, 5));
        return 60 * h + m;
    }
}

Time Complexity: O(n*log n) for sorting
Space Complexity: O(n) for the minutes array

Solution 2: Bucket Sort (10 min)
class Solution {
    public int findMinDifference(List<String> timePoints) {
        // Boolean array to mark which minutes exist
        boolean[] timeExists = new boolean[1440];
        
        for (String time : timePoints) {
            int minutes = convertToMinutes(time);
            // If duplicate time found, minimum difference is 0
            if (timeExists[minutes]) {
                return 0;
            }
            timeExists[minutes] = true;
        }
        
        // Find first and last time, and calculate differences
        Integer first = null, prev = null;
        int minDiff = Integer.MAX_VALUE;
        
        for (int i = 0; i < 1440; i++) {
            if (timeExists[i]) {
                if (first == null) {
                    first = i;
                    prev = i;
                } else {
                    minDiff = Math.min(minDiff, i - prev);
                    prev = i;
                }
            }
        }
        
        // Check circular difference
        minDiff = Math.min(minDiff, 1440 - prev + first);
        
        return minDiff;
    }
    
    private int convertToMinutes(String time) {
        return Integer.parseInt(time.substring(0, 2)) * 60 + 
               Integer.parseInt(time.substring(3));
    }
}

Time Complexity: O(n + 1440) = O(n) since 1440 is constant
Space Complexity: O(1440) = O(1) constant space

Refer to Deepseek
For LeetCode 539 "Minimum Time Difference", this problem requires finding the minimum time difference between any two time points in a list. Here's the Java solution:
🎯 Optimal Sorting Solution
class Solution {
    public int findMinDifference(List<String> timePoints) {
        // Step 1: Convert all time points to minutes
        int n = timePoints.size();
        int[] minutes = new int[n];
        
        for (int i = 0; i < n; i++) {
            minutes[i] = convertToMinutes(timePoints.get(i));
        }
        
        // Step 2: Sort the minutes array
        Arrays.sort(minutes);
        
        // Step 3: Find minimum difference between consecutive times
        int minDiff = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            minDiff = Math.min(minDiff, minutes[i] - minutes[i - 1]);
        }
        
        // Step 4: Check the circular difference (last and first)
        // The circular difference is: (1440 - last) + first
        int circularDiff = 1440 - minutes[n - 1] + minutes[0];
        minDiff = Math.min(minDiff, circularDiff);
        
        return minDiff;
    }
    
    private int convertToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }
}
🔍 How the Algorithm Works
Key Insight
Time is circular (23:59 → 00:00), so we need to consider:
1.Linear differences between consecutive sorted times
2.Circular difference between the last and first time
Step-by-Step Example: ["23:59", "00:00"]
Convert to minutes: [1439, 0]
Sort: [0, 1439]
Linear difference: 1439 - 0 = 1439
Circular difference: (1440 - 1439) + 0 = 1
Minimum = min(1439, 1) = 1
💡 Alternative with Bucket Sort
Since there are only 1440 possible minute values (24×60), we can use boolean array:
class Solution {
    public int findMinDifference(List<String> timePoints) {
        // Boolean array to mark which minutes exist
        boolean[] timeExists = new boolean[1440];
        
        for (String time : timePoints) {
            int minutes = convertToMinutes(time);
            // If duplicate time found, minimum difference is 0
            if (timeExists[minutes]) {
                return 0;
            }
            timeExists[minutes] = true;
        }
        
        // Find first and last time, and calculate differences
        Integer first = null, prev = null;
        int minDiff = Integer.MAX_VALUE;
        
        for (int i = 0; i < 1440; i++) {
            if (timeExists[i]) {
                if (first == null) {
                    first = i;
                    prev = i;
                } else {
                    minDiff = Math.min(minDiff, i - prev);
                    prev = i;
                }
            }
        }
        
        // Check circular difference
        minDiff = Math.min(minDiff, 1440 - prev + first);
        
        return minDiff;
    }
    
    private int convertToMinutes(String time) {
        return Integer.parseInt(time.substring(0, 2)) * 60 + 
               Integer.parseInt(time.substring(3));
    }
}
🧠 More Examples
Example 1: ["23:59", "00:00", "01:00"]
Convert: [1439, 0, 60]
Sort: [0, 60, 1439]

Differences:
0 → 60 = 60
60 → 1439 = 1379

Circular: 1440 - 1439 + 0 = 1

Minimum = min(60, 1379, 1) = 1
Example 2: ["01:00", "13:00", "12:30"]
Convert: [60, 780, 750]  
Sort: [60, 750, 780]

Differences:
60 → 750 = 690
750 → 780 = 30

Circular: 1440 - 780 + 60 = 720

Minimum = min(690, 30, 720) = 30
🚀 Optimized Conversion
Using substring for faster conversion:
class Solution {
    public int findMinDifference(List<String> timePoints) {
        int n = timePoints.size();
        int[] minutes = new int[n];
        
        for (int i = 0; i < n; i++) {
            String time = timePoints.get(i);
            minutes[i] = ((time.charAt(0) - '0') * 10 + (time.charAt(1) - '0')) * 60 +
                         ((time.charAt(3) - '0') * 10 + (time.charAt(4) - '0'));
        }
        
        Arrays.sort(minutes);
        
        int minDiff = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++) {
            minDiff = Math.min(minDiff, minutes[i] - minutes[i - 1]);
        }
        
        // Circular difference
        minDiff = Math.min(minDiff, 1440 - minutes[n - 1] + minutes[0]);
        
        return minDiff;
    }
}
💡 Key Insights
1.Circular Nature: Time wraps around at midnight (23:59 → 00:00)
2.Sorting: Makes consecutive comparisons efficient
3.Duplicate Detection: If any time appears twice, answer is 0
4.Efficient Conversion: Convert to minutes for easy comparison
📊 Complexity Analysis
Sorting Solution:
- Time: O(n log n) for sorting
- Space: O(n) for the minutes array
Bucket Sort Solution:
- Time: O(n + 1440) = O(n) since 1440 is constant
- Space: O(1440) = O(1) constant space
🎯 Recommendation
The sorting solution is generally preferred because:
- ✅ Simple and intuitive: Easy to understand and implement
- ✅ Efficient enough: For n ≤ 200, O(n log n) is very fast
- ✅ No large constant: Unlike bucket sort with 1440 elements
The bucket sort solution is better when:
- You expect many duplicate times (early return with 0)
- You're concerned about worst-case O(n log n) performance
The key is remembering to check both linear differences and the circular difference between the last and first time points!
