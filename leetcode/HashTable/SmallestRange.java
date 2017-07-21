import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/smallest-range/#/description
 * You have k lists of sorted integers in ascending order. Find the smallest range that includes 
 * at least one number from each of the k lists.
 * We define the range [a,b] is smaller than range [c,d] if b-a < d-c or a < c if b-a == d-c.

	Example 1:
	Input:[[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
	Output: [20,24]
	Explanation: 
	List 1: [4, 10, 15, 24,26], 24 is in range [20,24].
	List 2: [0, 9, 12, 20], 20 is in range [20,24].
	List 3: [5, 18, 22, 30], 22 is in range [20,24].

 * Note:
 * The given list may contain duplicates, so ascending order means >= here.
 * 1 <= k <= 3500
 * -105 <= value of elements <= 105.
 * For Java users, please note that the input type has been changed to List<List<Integer>>. 
 * And after you reset the code template, you'll see this point.
 * 
 * Solution
 * https://leetcode.com/articles/smallest-range/
 *
 */
public class SmallestRange {
	// Solution 1
	// Approach #1 Brute Force [Time Limit Exceeded]
	// The naive approach is to consider every pair of elements, nums[i][j] and nums[k][l] from amongst 
	// the given lists and consider the range formed by these elements. For every range currently 
	// considered, we can traverse over all the lists to find if at least one element from these lists 
	// can be included in the current range. If so, we store the end-points of the current range and 
	// compare it with the previous minimum range found, if any, satisfying the required criteria, to 
	// find the smaller range from among them. Once all the element pairs have been considered as the 
	// ranges, we can obtain the required minimum range.
	// Complexity Analysis
	// Time complexity : O(n3). Considering every possible range(element pair) requires O(n2) time. 
	//                   For each range considered, we need to traverse over all the elements of the 
	//                   given lists in the worst case requiring another O(n) time. Here, n refers to 
	//                   the total number of elements in the given lists.
    // Space complexity : O(1). Constant extra space is used.
	public int[] smallestRange(List<List<Integer>> nums) {
		int minX = 0;
		int minY = Integer.MAX_VALUE;
        /**
         * Example status change on a loop
         * Input: [[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
           ij = 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,...
           kl = 00, 01, 02, 03, 04, 10, 11, 12, 13, 20, 21,...
           min, max = (4,4),(4,10),(4,15),(4,24),(4,26),(0,4),(4,9),(4,12),(4,20),(4,5),(4,18),.. 
           minX, minY = (0,2147483647),(0,2147483647),(4,10),(4,10),(4,10),(4,10),(4,10),(4,10),(4,9),(4,9),(4,9),...
         */
		for(int i = 0; i < nums.size(); i++) {
			for(int j = 0; j < nums.get(i).size(); j++) {
				for(int k = i; k < nums.size(); k++) {
					// The assignment on 'l' here just to avoid re-check duplicates
					// cases when k == i, directly start check at index j item, no need
					// to start as index 0 item, but when k != i, must start as 0
                    // Use below statement is same effect				    
					// for(int l = 0; l < nums.get(k).size(); l++) {
					for(int l = (k == i ? j : 0); l < nums.get(k).size(); l++) {
                        // Find current range by comparing every possible combinations,
						// to get every possible combination, use (i,j) pair to locate
						// one range(i = start index, j = end index), and (k,l) pair 
						// to locate its counterpart, we need two pair because given
						// input equals to 2D Array
						int min = Math.min(nums.get(i).get(j), nums.get(k).get(l));
						int max = Math.max(nums.get(i).get(j), nums.get(k).get(l));
						// Below for loop used for circularly check whether existing 
						// at least one item in each list satisfy current range, 
						// if not in range move on to next item
						int n, m;
						for(m = 0; m < nums.size(); m++) {
							for(n = 0; n < nums.get(m).size(); n++) {
								if(nums.get(m).get(n) >= min && nums.get(m).get(n) <= max) {
									break;
								}
							}
							if(n == nums.get(m).size()) {
								break;
							}
						}
						// When reach the end, update the candidate range values
						if(m == nums.size()) {
							// This check will filter out all unnecessary cases
							// E.g if we already have pair as (min = 4, max = 10), the next pair
							// as (min = 4, max = 15) will omit
							if(minY - minX > max - min || (minY - minX == max - min && minX > min)) {
								minY = max;
								minX = min;
							}
						}
					}
				}
			}
		}
		return new int[] {minX, minY};
	}
	
	
	// Solution 2
	// Approach #2 Better Brute Force [Time Limit Exceeded]
	// In the last approach, we consider every possible range and then traverse over every list to 
	// check if at least one of the elements from these lists lies in the required range. Instead of 
	// doing this traversal for every range, we can make use of Binary Search to find the index of 
	// the element just larger than(or equal to) the lower limit of the range currently considered. 
	// If all the elements in the current list are lesser than this lower limit, we'll get the index 
	// as nums[k].length.length for the kth list being currently checked. 
	// In this case, none of the elements of the current list lies in the current range.
	// On the other hand, if all the elements in this list are larger than this lower limit, we'll get 
	// the index of the first element(minimum) in the current list. If this element happens to be larger 
	// than the upper limit of the range currently considered, then also, none of the elements of the 
	// current list lies within the current range.
	// Whenever a range is found which satisfies the required criteria, we can compare it with the minimum 
	// range found so far to determine the required minimum range.
	// Complexity Analysis
	// Time complexity : O(n2log(k)). The time required to consider every possible range is O(n2). 
	//                   For every range currently considered, a Binary Search requiring O(log(k)) time 
	//                   is required. Here, n refers to the total number of elements in the given lists 
	//                   and k refers to the average length of each list.
    // Space complexity : O(1). Constant extra space is used.
	public int[] smallestRange2(List<List<Integer>> nums) {
		int minX = 0;
		int minY = Integer.MAX_VALUE;
		/**
		 * Example status change on a loop
		 * Input: [[4,10,15,24,26], [0,9,12,20], [5,18,22,30]] 
		   ij = 00, 00, 00, 00, 00, 00, 00, 00, 00,...   
           kl = 00, 00, 01, 01, 01, 01, 02, 02, 02,... 
           min, max = (4,4),(4,4),(4,10),(4,10),(4,10),(4,10),(4,15),(4,15),(4,15),...
           minX, minY = (0,2147483647),(0,2147483647),(0,2147483647),(0,2147483647),(0,2147483647),(4,10),(4,10),(4,10),(4,10),...
           m = 0, 1, 0, 1, 2, 3, 0, 1, 2,...
           n = 0, 1, 0, 1, 0,  , 0, 1, 0,... 
           nums.get(m).get(n) = 4, 9, 4, 9, 5,  , 4, 9, 5,...
		 */
		for(int i = 0; i < nums.size(); i++) {
			for(int j = 0; j < nums.get(i).size(); j++) {
				for(int k = i; k < nums.size(); k++) {
					for(int l = (k == i ? j : 0); l < nums.get(k).size(); l++) {
						int min = Math.min(nums.get(i).get(j), nums.get(k).get(l));
						int max = Math.max(nums.get(i).get(j), nums.get(k).get(l));
						int n, m;
						for(m = 0; m < nums.size(); m++) {
							List<Integer> list = nums.get(m);
							Integer[] temp = list.toArray(new Integer[0]);
							// Binary search to find the item(return its position) equal or very next to 'min' value in current list 'temp'
							// Also, for binary search implementation
							// Refer to 'IsSubsequence.java'
							// https://github.com/lampardchelsea/hello-world/blob/8b4b53738efce1e76afd37b2eb341ff7f16347f8/leetcode/String/IsSubsequence.java
 						    n = Arrays.binarySearch(temp, min);
 						    if(n < 0) {
 						    	n = -1 - n;
 						    }
 						    // Write the customized binary search method
							//n = binarySearchHelper(min, list);
 						    // Target item nums.get(m).get(n) not exist in current range between [min, max], break out
 						    if(n == nums.get(m).size() || nums.get(m).get(n) < min || nums.get(m).get(n) > max) {
 						    	break;
 						    }
						}
						if(m == nums.size()) {
							if(minY - minX > max - min || (minY - minX == max - min && minX > min)) {
								minY = max;
								minX = min;
							}
						}
					}
				}
			}
		}
		return new int[] {minX, minY};
	}
	
    // As given conditions, list is already sorted into ascending order,
    // can directly used for binary search
    public int binarySearchHelper(int target, List<Integer> list) {
        int start = 0;
        int end = list.size() - 1;
        // The condition must including '='
        while(start <= end) {
            int mid = start + (end - start) / 2;
            if(list.get(mid) < target) {
                start = mid + 1;
            } else if(list.get(mid) > target) {
                end = mid - 1;
            } else {
                return mid;
            }
        }
        // If not found, return the very next position
        // Since 'start = mid + 1', it already represent
        // the very next position of final 'mid'
        return start;
    }
	
	public static void main(String[] args) {
		SmallestRange s = new SmallestRange();
		// [[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
		List<List<Integer>> nums = new ArrayList<List<Integer>>();
		List<Integer> one = new ArrayList<Integer>();
		one.add(4);
		one.add(10);
		one.add(15);
		one.add(24);
		one.add(26);
		List<Integer> two = new ArrayList<Integer>();
		two.add(0);
		two.add(9);
		two.add(12);
		two.add(20);
		List<Integer> three = new ArrayList<Integer>();
		three.add(5);
		three.add(18);
		three.add(22);
		three.add(30);
		nums.add(one);
		nums.add(two);
		nums.add(three);
		int[] result = s.smallestRange2(nums);
		for(int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}
		
	}
}
