import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5579271.html
 * Given n points on a 2D plane, find if there is such a line parallel to y-axis that 
 * reflect the given set of points.

	Example 1:
	Given points = [[1,1],[-1,1]], return true.
	
	Example 2:
	Given points = [[1,1],[-1,-1]], return false.
	
	Follow up:
	Could you do better than O(n2)?

 * Hint:
 * Find the smallest and largest x-value for all points.
 * If there is a line then it should be at y = (minX + maxX) / 2.
 * For each point, make sure that it has a reflected point in the opposite side.
 * 
 * Solution
 * https://discuss.leetcode.com/topic/47851/11ms-two-pass-hashset-based-java-solution
 * The idea is quite simple. If there exists a line reflecting the points, then each pair of 
 * symmetric points will have their x coordinates adding up to the same value, including the 
 * pair with the maximum and minimum x coordinates. So, in the first pass, I iterate through 
 * the array, adding each point to the hash set, and keeping record of the minimum and maximum 
 * x coordinates. Then, in the second pass, I check for every point to the left of the reflecting 
 * line, if its symmetric point is in the point set or not. If all points pass the test, 
 * then there exists a reflecting line. Otherwise, not.
 * By the way, here, to hash the content of an array, rather than the reference value, 
 * I use Arrays.hashCode(int[]) first, and then re-hash this hash code. You can also use 
 * Arrays.toString(int[]) to first convey the 2d array to a string, and then hash the string. 
 * But the second method is slower.
 * 
 * 
 * https://discuss.leetcode.com/topic/48172/simple-java-hashset-solution
 * The improvement on without using hashcode() to generate key, just adding additional char
 * variable between x-axis and y-axis
 * 
 * Note:
 * https://discuss.leetcode.com/topic/48172/simple-java-hashset-solution/13
 * Q: The test cases do not have duplicated points. If they have, your code cannot handle them.
 * A: The test cases do have duplicate points, e.g. [[-16,1],[16,1],[16,1]], and the result 
 *    should return true in this case.
 *    Duplicates didn't crash the code, since HashSet simply won't insert them.
 */
public class LineReflection {
	// Solution 1:
	public boolean isReflected(int[][] points) {
		Set<Integer> pointSet = new HashSet<Integer>();
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < points.length; i++) {
			max = Math.max(points[i][0], max);
			min = Math.min(points[i][0], min);
			pointSet.add(Arrays.hashCode(points[i]));
		}
		int sum = max + min;
		for(int i = 0; i < points.length; i++) {
			if(!pointSet.contains(Arrays.hashCode(new int[]{sum - points[i][0], points[i][1]}))) {
				return false;
			}
		}
		return true;
	}
	
	// Solution 2:
	public boolean isReflected2(int[][] points) {
		Set<String> pointSet = new HashSet<String>();
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for(int[] p : points) {
			max = Math.max(p[0], max);
			min = Math.min(p[0], min);
			String str = p[0] + "a" + p[1];
			pointSet.add(str);
		}
		int sum = max + min;
		for(int[] p : points) {
			// int[] arr = {sum - p[0], p[1]}
			String str = (sum - p[0]) + "a" + p[1];
			if(!pointSet.contains(str)) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		LineReflection l = new LineReflection();
		int[][] points = {{-16, 1}, {16, 1}, {16, 1}};
		boolean result = l.isReflected2(points);
		System.out.println(result);
	}
}
