import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Refer to
 * http://www.lintcode.com/en/problem/number-of-airplanes-in-the-sky/
 * Given an interval list which are flying and landing time of the flight. How many airplanes are on the sky at most?

	 Notice
	
	If landing and flying happens at the same time, we consider landing should happen at first.
	
	Have you met this question in a real interview? Yes
	Example
	For interval list
	
	[
	  [1,10],
	  [2,3],
	  [5,8],
	  [4,7]
	]
	Return 3
 * 
 * 
 * Solution
 * https://aaronice.gitbooks.io/lintcode/content/data_structure/number_of_airplanes_in_the_sky.html
 * http://www.jiuzhang.com/solutions/number-of-airplanes-in-the-sky/
 */
public class NumberOfAirplanesInTheSky {
	// Solution 1: HashMap
	/**
	 * HashMap - 空间换时间时间
	 * 航班起飞降落时间，直观的想法是，把一段段飞行时间看成线段，将它们都投射到时间坐标上，并进行叠加，最高的时间坐标点则是空中飞机
	 * 最多的时候。 用什么来表示时间坐标呢？可以利用HashMap，记录每一个时间段里的每一个飞行时间点，这样可以方便累加；（更简单的，
	 * 可以直接用一个Array，因为24小时的客观限制，int[24]。不过最好跟面试官明确一下时间坐标的可能范围，比如，是否会出现跨天
	 * 的时间段，[18, 6]，也就是18:00PM - 6:00AM +1，如果有这种情况，则需要考虑进行适当的处理。不过根据OJ给出的
	 * test cases，只会出现end>start的情况，并且不受24小时的时间限制，因此使用HashMap更为合适）
	 */
	private class Interval {
		int start, end;
		public Interval(int start, int end) {
		    this.start = start;
		    this.end = end;
		}
	}
	
	public int countOfAirplanes(List<Interval> airplanes) {
        if(airplanes == null || airplanes.size() == 0) {
            return 0;
        }
        int max = 0;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(Interval i : airplanes) {
            int start = i.start;
            int end = i.end;
            for(int k = start; k < end; k++) {
                if(!map.containsKey(k)) {
                    map.put(k, 1);
                } else {
                    map.put(k, map.get(k) + 1);
                }
                max = Math.max(max, map.get(k));
            }
        };
        return max;
	}
	
	
	
	// Solution 2: Sweep line
	/**
	 * Sweep Line - 扫描线法
	 * 可以对于各个飞行时间段按照start时间进行排序（附加start，end的flag，如果time相同时，end在start前）。那么遍历
	 * 这个排序过的链表时，也就是相当于在时间线上从前向后顺序移动，遇到start就+1，遇到end就-1，记录其中的最大值max即可。
	 */	
	private class Point {
        int time;
        int flag;
        public Point(int time, int flag) {
            this.time = time;
            this.flag = flag;
        }
    }
    
    public int countOfAirplanes_2(List<Interval> airplanes) {
    	if(airplanes == null || airplanes.size() == 0) {
    		return 0;
    	}
        List<Point> list = new ArrayList<Point>();
        for(Interval i : airplanes) {
            list.add(new Point(i.start, 1));
            list.add(new Point(i.end, 0));
        }
        Collections.sort(list, new Comparator<Point>() {
           public int compare(Point a, Point b) {
               // Caution: Must consider time equal case as below
               // E.g [[10,14],[10,15],[10,16],[1,10],[2,10],[3,10],[4,10]]
               //     output 7 exepected 4
               //return a.time - b.time;
               if(a.time == b.time) {
                   return a.flag - b.flag;
               }
               return a.time - b.time;
           } 
        });
        int count = 0;
        int result = 0;
        for(Point p : list) {
            if(p.flag == 1) {
                count++;
            } else {
                count--;
            }
            result = Math.max(result, count);
        }
        return result;
    }
	
}

