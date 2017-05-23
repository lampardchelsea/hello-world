import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/insert-interval/#/description
 * Given a set of non-overlapping intervals, insert a new interval into the intervals 
 * (merge if necessary).
 * You may assume that the intervals were initially sorted according to their start times.
 * Example 1:
 * Given intervals [1,3],[6,9], insert and merge [2,5] in as [1,5],[6,9].
 * Example 2:
 * Given [1,2],[3,5],[6,7],[8,10],[12,16], insert and merge [4,9] in as [1,2],[3,10],[12,16].
 * This is because the new interval [4,9] overlaps with [3,5],[6,7],[8,10]. 
 * 
 * Solution
 * https://segmentfault.com/a/1190000003768795
 * 排序法
 * 复杂度
 * 时间 O(NlogN) 空间 O(N)
 * 思路
 * 和Merge Interval的思路接近，这题中我们只有一个待合并的Interval，就是输入给出的。我们只要把所有和该Interval
 * 有重叠的合并到一起就行了。为了方便操作，对于那些没有重叠部分的Interval，我们可以把在待合并Interval之前的
 * Interval加入一个列表中，把之后的Interval加入另一个列表中。最后把前半部分的列表，合并后的大Interval和后半部分
 * 的列表连起来，就是结果了。
 * 注意
 * 因为待合并的Interval出现的位置不确定，判断重叠与否时既要判断起点，也要判断终点
 * 原列表为空时，直接加入待合并的Interval后返回
 */
public class InsertInterval {
	private class Interval {
		int start;
		int end;
		Interval() { 
			start = 0; 
			end = 0; 
		}
		Interval(int s, int e) { 
			start = s; 
			end = e; 
		}
		
		@Override
		public String toString() {
			return "[" + start + "," + end + "]";
		}
	}
	
    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        List<Interval> before = new ArrayList<Interval>();
        List<Interval> after = new ArrayList<Interval>();
        List<Interval> result = new ArrayList<Interval>();
        if(intervals.size() == 0) {
            result.add(newInterval);
            return result;
        }
        
        for(Interval itv : intervals) {
            if(newInterval.start > itv.end) {
                before.add(itv);
            }
            if((newInterval.end >= itv.start && newInterval.end <= itv.end) || 
            (newInterval.start >= itv.start && newInterval.start <= itv.end)) {
                newInterval.start = Math.min(newInterval.start, itv.start);
                newInterval.end = Math.max(newInterval.end, itv.end);
            }
            if(newInterval.end < itv.start) {
                after.add(itv);
            }
        }
        
        result.addAll(before);
        result.add(newInterval);
        result.addAll(after);
        return result;
    }
    
    public static void main(String[] args) {
    	InsertInterval m = new InsertInterval();
    	Interval one = m.new Interval(1, 3);
    	Interval two = m.new Interval(6, 9);
    	List<Interval> intervals = new ArrayList<Interval>();
    	// Adding order as [1,3],[6,9] and new interval is [2,5]
    	intervals.add(one);
    	intervals.add(two);
    	Interval newInterval = m.new Interval(2, 5);
    	List<Interval> result = m.insert(intervals, newInterval);
    	for(Interval i : result) {
    		System.out.println(i);
    	}
    }
}

