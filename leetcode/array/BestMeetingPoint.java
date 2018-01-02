import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * https://segmentfault.com/a/1190000003894693
 * A group of two or more people wants to meet and minimize the total travel distance. 
 * You are given a 2D grid of values 0 or 1, where each 1 marks the home of someone in 
 * the group. The distance is calculated using Manhattan Distance, where 
 * distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|.

	For example, given three people living at (0,0), (0,4), and (2,2):
	
	1 - 0 - 0 - 0 - 1
	|   |   |   |   |
	0 - 0 - 0 - 0 - 0
	|   |   |   |   |
	0 - 0 - 1 - 0 - 0
	The point (0,2) is an ideal meeting point, as the total travel
	distance of 2+2+2=6 is minimal. So return 6.
 * 
 * 
 * Solution
 * https://segmentfault.com/a/1190000003894693
 * https://www.youtube.com/watch?v=vCRnwe0L0sg
 * https://discuss.leetcode.com/topic/46212/the-theory-behind-why-the-median-works
 *
 */

public class BestMeetingPoint {
    public int minTotalDistance(int[][] grid) {
	    List<Integer> iPos = new ArrayList<Integer>();
	    List<Integer> jPos = new ArrayList<Integer>();
	    // For x-axis in order(smaller index to large index) store
	    for(int i = 0; i < grid.length; i++) {
	    	for(int j = 0; j < grid[0].length; j++) {
	    		if(grid[i][j] == 1) {
	    			iPos.add(i);
	    		}
	    	}
	    }
	    // For y-axis in order(smaller index to large index) store
	    // Caution: Put j out side of i loop to make sure j in order
	    for(int j = 0; j < grid[0].length; j++) {
	    	for(int i = 0; i < grid.length; i++) {
	    		if(grid[i][j] == 1) {
	    			jPos.add(j);
	    		}
	    	}
	    }
	    
	    return min(iPos) + min(jPos);
	}
	
    // Calculate 
    private int min(List<Integer> list) {
    	int i = 0;
    	int j = list.size() - 1;
    	int sum = 0;
    	while(i < j) {
    		sum += list.get(j--) - list.get(i++);
    	}
    	return sum;
    }
    
    public static void main(String[] args) {
    	/**
    	 *  1 - 0 - 0 - 0 - 1
			|   |   |   |   |
			0 - 0 - 0 - 0 - 0
			|   |   |   |   |
			0 - 0 - 1 - 0 - 0
    	 */
    	BestMeetingPoint b = new BestMeetingPoint();
    	int[][] grid = new int[][]{{1,0,0,0,1}, {0,0,0,0,0}, {0,0,1,0,0}};
    	int result = b.minTotalDistance(grid);
    	System.out.println(result);
    }

}
