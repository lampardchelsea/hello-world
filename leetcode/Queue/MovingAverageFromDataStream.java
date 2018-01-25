/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5450001.html
 * Given a stream of integers and a window size, calculate the moving average of all integers in the sliding window.

    For example,
    MovingAverage m = new MovingAverage(3);
    m.next(1) = 1
    m.next(10) = (1 + 10) / 2
    m.next(3) = (1 + 10 + 3) / 3
    m.next(5) = (10 + 3 + 5) / 3
 *
 *
 * Solution
 * https://www.youtube.com/watch?v=tEGSN17jP4A
 * https://discuss.leetcode.com/topic/44251/java-easy-to-understand-solution
 * https://discuss.leetcode.com/topic/44108/java-o-1-time-solution
*/
// Solution 1: Queue
// Refer to
// https://discuss.leetcode.com/topic/44251/java-easy-to-understand-solution
// https://www.youtube.com/watch?v=tEGSN17jP4A
// Time Complexity: O(n)
/**
 这道题定义了一个MovingAverage类，里面可以存固定个数字，然后我们每次读入一个数字，如果加上这个
 数字后总个数大于限制的个数，那么我们移除最早进入的数字，然后返回更新后的平均数，这种先进先出的
 特性最适合使用队列queue来做，而且我们还需要一个double型的变量sum来记录当前所有数字之和，这样
 有新数字进入后，如果没有超出限制个数，则sum加上这个数字，如果超出了，那么sum先减去最早的数字，
 再加上这个数字，然后返回sum除以queue的个数即可
*/
import java.util.LinkedList;
import java.util.Queue;
public class MovingAverage {
	Queue<Integer> queue;
	double prevSum = 0.0;
	int maxSize;
	
	public MovingAverage(int size) {
        queue = new LinkedList<Integer>();
        this.maxSize = size;
	}

	public double next(int val) {
		if(queue.size() == maxSize) {
		    prevSum -= queue.remove();
		}
		prevSum += val;
		queue.offer(val);
		return prevSum / queue.size();
	}
	
	public static void main(String[] args) {
		MovingAverage m = new MovingAverage(3);
		m.next(10);
		m.next(3);
		m.next(5);
		double result = m.next(1);
		System.out.print(result);
	}
}


// Solution 2: Sliding Window
// Refer to
// https://discuss.leetcode.com/topic/44108/java-o-1-time-solution
// Time Complexity: O(1)
public class MovingAverage {
//	Queue<Integer> queue;
//	double prevSum = 0.0;
//	int maxSize;
//	
//	public MovingAverage(int size) {
//        queue = new LinkedList<Integer>();
//        this.maxSize = size;
//	}
//
//	public double next(int val) {
//		if(queue.size() == maxSize) {
//		    prevSum -= queue.remove();
//		}
//		prevSum += val;
//		queue.offer(val);
//		return prevSum / queue.size();
//	}
	
	int[] window;
	int n;
	int insert;
	long sum;
	
	public MovingAverage(int size) {
        window = new int[size];
        insert = 0;
        sum = 0;
	}

	public double next(int val) {
	if(n < window.length) {
		n++;
	}
	sum -= window[insert];
	sum += val;
	window[insert] = val;
	insert = (insert + 1) % window.length;
	return (double)sum / n;
	}
		
	public static void main(String[] args) {
		MovingAverage m = new MovingAverage(3);
		m.next(10);
		m.next(3);
		m.next(5);
		double result = m.next(1);
		System.out.print(result);
	}
}






