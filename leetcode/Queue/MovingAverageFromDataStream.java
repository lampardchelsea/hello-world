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







