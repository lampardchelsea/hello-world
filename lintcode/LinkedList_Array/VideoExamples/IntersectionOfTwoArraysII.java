/**
 * Refer to
 * https://leetcode.com/problems/intersection-of-two-arrays-ii/description/
 * Given two arrays, write a function to compute their intersection.
    Example:
    Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2, 2].

    Note:
    Each element in the result should appear as many times as it shows in both arrays.
    The result can be in any order.
    Follow up:
    What if the given array is already sorted? How would you optimize your algorithm?
    What if nums1's size is small compared to nums2's size? Which algorithm is better?
    What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements 
    into the memory at once? 
 *
 * Solution
 * http://www.jiuzhang.com/solutions/intersection-of-two-arrays-ii/
 * https://discuss.leetcode.com/topic/45992/solution-to-3rd-follow-up-question
 * https://discuss.leetcode.com/topic/68883/solution-with-three-follow-ups
 * Compared to 349. Intersection of Two Arrays, which uses a hash to flag if element exists, 
   we turn to use a hash to count how many elements exist.

      def intersect(nums1, nums2)
        hash = nums1.reduce(Hash.new(0)) {|ha, num| ha[num] += 1; ha }

        nums2.reduce([]) do |ar, num|
          if hash[num] > 0
            hash[num] -= 1
            ar << num
          else
            ar
          end
        end
      end
    
    Q. What if the given array is already sorted? How would you optimize your algorithm?
       If both arrays are sorted, I would use two pointers to iterate, which somehow resembles the merge process in merge sort.
    
    Q. What if nums1's size is small compared to nums2's size? Which algorithm is better?
       Suppose lengths of two arrays are N and M, the time complexity of my solution is O(N+M) and the space 
       complexity if O(N) considering the hash. So it's better to use the smaller array to construct the counter hash.
       Well, as we are calculating the intersection of two arrays, the order of array doesn't matter. 
       We are totally free to swap to arrays.

    Q. What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into 
       the memory at once?
       Divide and conquer. Repeat the process frequently: Slice nums2 to fit into memory, process (calculate intersections), 
       and write partial results to memory.
       
       Another version
       If only nums2 cannot fit in memory, put all elements of nums1 into a HashMap, read chunks of array that fit into the 
       memory, and record the intersections.
       If both nums1 and nums2 are so huge that neither fit into the memory, sort them individually (external sort), then 
       read 2 elements from each array at a time in memory, record intersections.
 * 
*/
public class Solution {
    /**
     * @param nums1 an integer array
     * @param nums2 an integer array
     * @return an integer array
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int a : nums1) {
            if(!map.containsKey(a)) {
                map.put(a, 1);
            } else {
                map.put(a, map.get(a) + 1);
            }
        }
        
        List<Integer> list = new ArrayList<Integer>();
        for(int b : nums2) {
            if(map.containsKey(b) && map.get(b) > 0) {
                list.add(b);
                map.put(b, map.get(b) - 1);
            }
        }
        
        int[] result = new int[list.size()];
        int i = 0;
        for(int c : list) {
            result[i++] = c;
        }
        return result;
    }
}
