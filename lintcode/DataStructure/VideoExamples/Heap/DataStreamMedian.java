
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Refer to
 * http://www.lintcode.com/en/problem/data-stream-median/
 * https://leetcode.com/problems/find-median-from-data-stream/description/
 * Notice
	LeetCode中与LintCode里稍有不同，在于对于Median的定义：
	LeetCode要求是如果是even number，就计算中间两个数字的平均值，也就是 (maxHeap.peek() + (minHeap.peek()))/2
	而LintCode的要求则是当even number时取[N/2]那一个，也就是说不论是否even number，需要返回的都是 maxHeap.peek()
	延伸思考
	
	这里建立Heap依然需要知道即将传入的nums[]的长度，如果对于一个未知长度的nums[]这里应当如何处理呢？
 * 
 * Solution
 * http://www.jiuzhang.com/solutions/median-in-data-stream/
 * 
 * https://aaronice.gitbooks.io/lintcode/content/data_structure/data_stream_median.html
 * 寻找中位数median，这里有一种很巧妙的思路，需要利用Heap的半排序特性，是指root node的值是min （min heap）或者max（max heap）。
 * 利用两个heap，一个minHeap，一个maxHeap，将nums[]的数顺序存入时，则可以分别存入maxHeap和minHeap，
 * 并保持这两个heap的size相同，保持两者size相同的操作通过minHeap.offer(maxHeap.poll());和maxHeap.offer(minHeap.poll());
 * 
 * https://segmentfault.com/a/1190000003709954
 * 最大最小堆
 * 复杂度
 * 时间 O(NlogN) 空间 O(N)
 * 思路
 * 维护一个最大堆，一个最小堆。最大堆存的是到目前为止较小的那一半数，最小堆存的是到目前为止较大的那一半数，这样中位数只有可能是堆顶或
 * 者堆顶两个数的均值。而维护两个堆的技巧在于判断堆顶数和新来的数的大小关系，还有两个堆的大小关系。我们将新数加入堆后，要保证两个堆的
 * 大小之差不超过1。先判断堆顶数和新数的大小关系，有如下三种情况：最小堆堆顶小于新数时，说明新数在所有数的上半部分。最小堆堆顶大于
 * 新数，但最大堆堆顶小于新数时，说明新数将处在最小堆堆顶或最大堆堆顶，也就是一半的位置。最大堆堆顶大于新数时，说明新数将处在所有数的
 * 下半部分。再判断两个堆的大小关系，如果新数不在中间，那目标堆不大于另一个堆时，将新数加入目标堆，否则将目标堆的堆顶加入另一个堆，
 * 再把新数加入目标堆。如果新数在中间，那加到大小较小的那个堆就行了（一样大的话随便，代码中是加入最大堆）。这样，每次新加进来一个数
 * 以后，如果两个堆一样大，则中位数是两个堆顶的均值，否则中位数是较大的那个堆的堆顶。
 * 注意
 * Java中实现最大堆是在初始化优先队列时加入一个自定义的Comparator，默认初始堆大小是11。Comparator实现compare方法时，
 * 用arg1 - arg0来表示大的值在前面
 *
 */
public class DataStreamMedian {
	/*
     * @param nums: A list of integers
     * @return: the median of numbers
     */
    public int[] medianII(int[] nums) {
        if(nums == null || nums.length == 0) {
            return new int[0];
        }
        int[] result = new int[nums.length];
        // minPQ store larger half of input data stream
        // maxPQ store smaller half of input data stream
        // so we can compute median by using root of both PQ
        PriorityQueue<Integer> minPQ = new PriorityQueue<Integer>();
        PriorityQueue<Integer> maxPQ = new PriorityQueue<Integer>(nums.length, new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return b - a;
            } 
        });
        result[0] = nums[0];
        // Initially add the first number onto maxPQ
        maxPQ.add(nums[0]);
        for(int i = 1; i < nums.length; i++) {
            int x = maxPQ.peek();
            // If this number smaller than maxPQ root(top element)
            // put new number onto maxPQ, otherwise add onto minPQ
            if(nums[i] < x) {
                maxPQ.add(nums[i]);
            } else {
                minPQ.add(nums[i]);
            }
            // Make sure minPQ and maxPQ size difference <= 1
            if(maxPQ.size() > minPQ.size() + 1) {
                minPQ.add(maxPQ.poll());
            // Caution: use '>=' instead of '>' to make sure handle size
            // difference equals 1 situation
            } else if(minPQ.size() >= maxPQ.size() + 1) {
                maxPQ.add(minPQ.poll());
            }
            // Refer to
            // https://aaronice.gitbooks.io/lintcode/content/data_structure/data_stream_median.html
            // LeetCode中与LintCode里稍有不同，在于对于Median的定义：
            // LeetCode要求是如果是even number，就计算中间两个数字的平均值，也就是 (maxHeap.peek() + (minHeap.peek()))/2
            // 而LintCode的要求则是当even number时取[N/2]那一个，也就是说不论是否even number，需要返回的都是 maxHeap.peek()
            result[i] = maxPQ.peek();
        }
        return result;
    }
}



// Also about Leetcode version we have
// Refer to
// https://segmentfault.com/a/1190000003709954
class MedianFinder {
    
    PriorityQueue<Integer> maxheap;
    PriorityQueue<Integer> minheap;
    
    public MedianFinder(){
        // 新建最大堆
        maxheap = new PriorityQueue<Integer>(11, new Comparator<Integer>(){
            public int compare(Integer i1, Integer i2){
                return i2 - i1;
            }
        });
        // 新建最小堆
        minheap = new PriorityQueue<Integer>();
    }

    // Adds a number into the data structure.
    public void addNum(int num) {
        // 如果最大堆为空，或者该数小于最大堆堆顶，则加入最大堆
        if(maxheap.size() == 0 || num <= maxheap.peek()){
            // 如果最大堆大小超过最小堆，则要平衡一下
            if(maxheap.size() > minheap.size()){
                minheap.offer(maxheap.poll());
            }
            maxheap.offer(num);
        // 数字大于最小堆堆顶，加入最小堆的情况
        } else if (minheap.size() == 0 || num > minheap.peek()){
            if(minheap.size() > maxheap.size()){
                maxheap.offer(minheap.poll());
            }
            minheap.offer(num);
        // 数字在两个堆顶之间的情况
        } else {
            if(maxheap.size() <= minheap.size()){
                maxheap.offer(num);
            } else {
                minheap.offer(num);
            }
        }
    }

    // Returns the median of current data stream
    public double findMedian() {
        // 返回大小较大的那个堆堆顶，如果大小一样说明是偶数个，则返回堆顶均值
        if(maxheap.size() > minheap.size()){
            return maxheap.peek();
        } else if (maxheap.size() < minheap.size()){
            return minheap.peek();
        } else if (maxheap.isEmpty() && minheap.isEmpty()){
            return 0;
        } else {
            return (maxheap.peek() + minheap.peek()) / 2.0;
        }
    }
};




