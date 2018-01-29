/**
 * Refer to
 * https://leetcode.com/problems/data-stream-as-disjoint-intervals/description/
 * Given a data stream input of non-negative integers a1, a2, ..., an, ..., summarize the numbers seen so 
   far as a list of disjoint intervals.

    For example, suppose the integers from the data stream are 1, 3, 7, 2, 6, ..., then the summary will be:

    [1, 1]
    [1, 1], [3, 3]
    [1, 1], [3, 3], [7, 7]
    [1, 3], [7, 7]
    [1, 3], [6, 7]
    Follow up:
    What if there are lots of merges and the number of disjoint intervals are small compared to the data stream's size?
 * 
 * Solution
 * https://discuss.leetcode.com/topic/46887/java-solution-using-treemap-real-o-logn-per-adding
 * https://www.youtube.com/watch?v=mfBPUTW0cZY
*/
/**
 * Definition for an interval.
 * public class Interval {
 *     int start;
 *     int end;
 *     Interval() { start = 0; end = 0; }
 *     Interval(int s, int e) { start = s; end = e; }
 * }
 */
class SummaryRanges {    
    /** Initialize your data structure here. */
	TreeMap<Integer, Interval> tree;
	
	public SummaryRanges() {
		tree = new TreeMap<Integer, Interval>();
	} 
	
	public void addNum(int val) {
		if(tree.containsKey(val)) {
			return;
		}
		// the greatest key less than key, or null if there is no such key
		Integer lowerKey = tree.lowerKey(val);
		// the least key greater than key, or null if there is no such key
		Integer higherKey = tree.higherKey(val);
		if(lowerKey != null && higherKey != null && tree.get(lowerKey).end + 1 == val && tree.get(higherKey).start - 1 == val) {
			// E.g when we have [1,1], [3,3] and want to insert 2, merge [1,1],2,[3,3] as [1,3], and remove 3 -> [3,3]
			tree.get(lowerKey).end = tree.get(higherKey).end;
            tree.remove(higherKey);
		} else if(lowerKey != null && val <= tree.get(lowerKey).end + 1) {
			// E.g when we have [1,1] and want to insert 2, update 1 -> [1,1] to 1 -> [1,2]
			tree.get(lowerKey).end = Math.max(tree.get(lowerKey).end, val);
		} else if(higherKey != null && val == tree.get(higherKey).start - 1) {
			// E.g when we have [1,1] and want to insert 0, add 0 -> [0,1], remove 1 -> [1,1]
			tree.put(val, new Interval(val, tree.get(higherKey).end));
			tree.remove(higherKey);
		} else {
			// E.g when we have [1,1] and want to insert 3, add new interval as [3,3]
			tree.put(val, new Interval(val, val));
		}
  }
    
    public List<Interval> getIntervals() {
		return new ArrayList<Interval>(tree.values());
	}
}

/**
 * Your SummaryRanges object will be instantiated and called as such:
 * SummaryRanges obj = new SummaryRanges();
 * obj.addNum(val);
 * List<Interval> param_2 = obj.getIntervals();
 */
