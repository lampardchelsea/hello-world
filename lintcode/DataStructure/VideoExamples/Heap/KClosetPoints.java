
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Refer to
 * https://yeqiuquan.blogspot.com/2017/03/lintcode-612-k-closest-points.html
 * Description
 * Given some points and a point origin in two dimensional space, find k points out 
 * of the some points which are nearest to origin.
 * Return these points sorted by distance, if they are same with distance, 
 * sorted by x-axis, otherwise sorted by y-axis.

	Example
	Given points = [[4,6],[4,7],[4,4],[2,5],[1,1]], origin = [0, 0], k = 3
	return [[1,1],[2,5],[4,4]]
 * 
 * Solution
 * https://yeqiuquan.blogspot.com/2017/03/lintcode-612-k-closest-points.html
 * 根据题意，我们维护一个大小为K的max-heap。一个一个把point放进去，如果容量超了，就把最大的踢掉。这样heap里永远是最小的K个。
 * （注意不是min-heap，自己举个例子就明白了。如果heap是[3, 4, 5]满了又来了2怎么办？当然是把5踢了，所以是max-heap。）
 * Comparator写的时候根据题意，如果距离相等，就比x轴，如果还相等，就比y轴。
 * 最后把max-heap里面这K个points倒出来就是最近的K个。
 * 另外算距离的时候不用开根，因为我们只比大小，所以勾方股方相加的数就够了可以比了
 */

/**
 * Definition for a point.
 * class Point {
 *     int x;
 *     int y;
 *     Point() { x = 0; y = 0; }
 *     Point(int a, int b) { x = a; y = b; }
 * }
 */
public class KClosetPoints {
	private class Point {
		int x;
		int y;
		public Point(int a, int b) {
			this.x = a;
			this.y = b;
		}
		
		public Point() {
			this.x = 0;
			this.y = 0;
		}
	}
	
    /**
     * @param points a list of points
     * @param origin a point
     * @param k an integer
     * @return the k closest points
     */
    
    public Point globalOrigin = null;
    public Point[] kClosest(Point[] points, Point origin, int k) {
    	globalOrigin = origin;
    	// Create max heap based on distance comparator
    	PriorityQueue<Point> pq = new PriorityQueue<Point>(k, new Comparator<Point>() {
    		public int compare(Point a, Point b) {
    			// Distance between a and origin
    			int distance_a = getDistance(a, globalOrigin);
    			// Distance between b and origin
    			int distance_b = getDistance(b, globalOrigin);
				// For max heap comparator must use (b - a) order,
				// similar min heap comparator use (a - b) order
    			int diff = distance_b - distance_a;
    			// If distance_a equals to distance_b, sorted by x-axis
    			if(diff == 0) {
    				diff = b.x - a.x;
    			}
    			// If using x-axis still the same value
    			if(diff == 0) {
    				diff = b.y - a.y;
    			}
    			return diff;
    		}
    	});
    	
    	for(Point p : points) {
    		pq.add(p);
    		if(pq.size() > k) {
    			pq.poll();
    		}
    	}
    	
    	Point[] result = new Point[k];
    	while(k - 1 >= 0) {
    		result[--k] = pq.poll();
    	}
    	return result;
    }
	
    private int getDistance(Point a, Point b) {
    	return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
    }
    
    public static void main(String[] args) {
    	KClosetPoints k = new KClosetPoints();
    	Point one = k.new Point(4,6);
    	Point two = k.new Point(4,7);
    	Point three = k.new Point(4,4);
    	Point four = k.new Point(2,5);
    	Point five = k.new Point(1,1);
    	Point[] points = {one, two, three, four, five};
    	Point origin = k.new Point(0,0);
    	int size = 3;
    	Point[] result = k.kClosest(points, origin, size);
    	for(Point p : result) {
    		System.out.println(p.x + " " + p.y);
    	}
    }
}
