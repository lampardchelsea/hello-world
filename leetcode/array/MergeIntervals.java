import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/merge-intervals/#/description
 * Given a collection of intervals, merge all overlapping intervals.
 * For example,
 * Given [1,3],[2,6],[8,10],[15,18],
 * return [1,6],[8,10],[15,18]. 
 * 
 * Solution
 * http://stackoverflow.com/questions/44102578/mergesort-on-arraylist-only-work-with-arraylistcollection-extends-e-c-not-a
 * https://gist.github.com/louisbros/8514819
 * https://segmentfault.com/a/1190000003768795
 * 排序法
 * 复杂度
 * 时间 O(NlogN) 空间 O(N)
 * 思路
 * 首先根据Interval的起点，我们将其排序，这样能合并的Interval就一定是相邻的了：
 * [1,3] [5,6] [2,3] ---> [1,3] [2,3] [5,6]
 * 然后我们就顺序遍历这个列表，并记录一个当前待合并的Interval，如果遍历到的Interval和当前待合并的Interval有重复部分，
 * 我们就将两个合并，如果没有重复部分，就将待合并的Interval加入结果中，并用新的Interval更新这个待合并的Interval。
 * 因为数组已经排过序，前面的Interval的起点肯定小于后面Interval的起点，所以在判断是否有重叠部分时，只要考虑待合并的
 * Interval的终点和遍历到的Interval的起点的关系就行了。
 * 注意
 * (1) 判断重叠的边界时包含等于的情况
 * (2) 循环后还要把最后一个待合并的Interval也加入结果中
 * (3) 更新待合并Interval的边界时，要同时更新起点和终点
 */
public class MergeIntervals {
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
	
    public List<Interval> merge(List<Interval> intervals) {
        List<Interval> result = new ArrayList<Interval>();
        if(intervals.size() == 0) {
            return result;
        }
        sortHelper(intervals);
        // Given  [1,3],[2,6],[8,10],[15,18]
        // Return [1,6],[8,10],[15,18]
        // Set the pending merge interval as the first one 
        Interval current = intervals.get(0);
        for(Interval itv : intervals) {
        	// Important: Consider strictly equal case when judge on whether overlap
            if(current.end >= itv.start) {
                current.start = Math.min(current.start, itv.start);
                current.end = Math.max(current.end, itv.end);
            } else {
            	// If no overlap, add current interval onto result,
            	// then update current interval to next one
                result.add(current);
                current = itv;
            }
        }
        // Important: Don't miss add the pending merge interval 
        // onto result, e.g only given [1,3] can test out
        result.add(current);
        return result;
    }
    
    /**
     *  We can also use 'Collections.sort()' with 'Comparator' to
     *  sort the intervals, no need to use MergeSort
     *  Collections.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval i1, Interval i2){
                return i1.start - i2.start;
            }
        });
     */
       
    public void sortHelper(List<Interval> intervals) {
        int len = intervals.size();
        List<Interval> aux = new ArrayList<Interval>(intervals);
//      List<Interval> aux = new ArrayList<Interval>(len);
//      List<Interval> aux = new ArrayList<Interval>((Arrays.asList(new Interval(), new Interval(), new Interval(), new Interval())));
        sort(intervals, aux, 0, len - 1);
    }
    
    public void sort(List<Interval> intervals, List<Interval> aux, int lo, int hi) {
        if(hi <= lo) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(intervals, aux, lo, mid);
        sort(intervals, aux, mid + 1, hi);
        mergeHelper(intervals, aux, lo, mid, hi);
    }
    
    public void mergeHelper(List<Interval> intervals, List<Interval> aux, int lo, int mid, int hi) {
        for(int i = lo; i <= hi; i++) {
            aux.set(i, intervals.get(i));
        }
//    	aux.clear();
//    	for(int i = 0; i < lo; i++) {
//            aux.add(null);
//        }
//        for(int i = lo; i <= hi; i++) {
//            aux.add(intervals.get(i));
//        }
        int left = lo;
        int right = mid + 1;
        for(int k = lo; k <= hi; k++) {
            if(left > mid) {
                intervals.set(k, aux.get(right++));
            } else if(right > hi) {
                intervals.set(k, aux.get(left++));
            } else if(less(aux.get(right), aux.get(left))) {
                intervals.set(k, aux.get(right++));
            } else {
                intervals.set(k, aux.get(left++));
            }
        }
    }
    
    public boolean less(Interval a, Interval b) {
    	return a.start - b.start < 0;
    }
    
    public static void main(String[] args) {
    	MergeIntervals m = new MergeIntervals();
    	Interval one = m.new Interval(1, 3);
    	Interval two = m.new Interval(2, 6);
    	Interval three = m.new Interval(8, 10);
    	Interval four = m.new Interval(15, 18);
    	List<Interval> intervals = new ArrayList<Interval>();
    	// Adding order as [2,6],[1,3],[8,10],[15,18]
    	//intervals.add(two);
    	intervals.add(one);
    	//intervals.add(three);
    	//intervals.add(four);
    	//m.sortHelper(intervals);
    	// Expected sort result should be
    	// [1,3],[2,6],[8,10],[15,18]
//    	for(Interval i : intervals) {
//    		System.out.println(i);
//    	}
    	List<Interval> result = m.merge(intervals);
    	for(Interval i : result) {
    		System.out.println(i);
    	}
    }
    
}

