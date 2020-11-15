/**
Refer to
https://leetcode.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/
Given an array of integers nums and an integer limit, return the size of the longest non-empty subarray such 
that the absolute difference between any two elements of this subarray is less than or equal to limit.

Example 1:
Input: nums = [8,2,4,7], limit = 4
Output: 2 
Explanation: All subarrays are: 
[8] with maximum absolute diff |8-8| = 0 <= 4.
[8,2] with maximum absolute diff |8-2| = 6 > 4. 
[8,2,4] with maximum absolute diff |8-2| = 6 > 4.
[8,2,4,7] with maximum absolute diff |8-2| = 6 > 4.
[2] with maximum absolute diff |2-2| = 0 <= 4.
[2,4] with maximum absolute diff |2-4| = 2 <= 4.
[2,4,7] with maximum absolute diff |2-7| = 5 > 4.
[4] with maximum absolute diff |4-4| = 0 <= 4.
[4,7] with maximum absolute diff |4-7| = 3 <= 4.
[7] with maximum absolute diff |7-7| = 0 <= 4. 
Therefore, the size of the longest subarray is 2.

Example 2:
Input: nums = [10,1,2,4,7,2], limit = 5
Output: 4 
Explanation: The subarray [2,4,7,2] is the longest since the maximum absolute diff is |2-7| = 5 <= 5.

Example 3:
Input: nums = [4,2,2,2,4,4,2,2], limit = 0
Output: 3

Constraints:
1 <= nums.length <= 10^5
1 <= nums[i] <= 10^9
0 <= limit <= 10^9
*/

// Solution 1: Not fixed length sliding window + TreeMap to get natural sort for getting maximum / minimum value in window easily
// Refer to
// https://leetcode.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/discuss/609771/JavaC%2B%2BPython-Deques-O(N)
/**
Use TreeMap
Use one tree map can easily get the maximum and the minimum at the same time.
In java, we can use TreeMap to count elements.
In cpp, it suports multi treeset, that's even better.
Time O(NogN)
Space O(N)
*/

// How to use TreeMap for get maximum / minimum value in window ?
// Refer to
// https://www.baeldung.com/java-treemap
/**
A TreeMap is always sorted based on keys. The sorting order follows the natural ordering of keys. You may also provide a custom 
Comparator to the TreeMap at the time of creation to let it sort the keys using the supplied Comparator.
By default, TreeMap sorts all its entries according to their natural ordering. For an integer, this would mean ascending order 
and for strings, alphabetical order.
@Test
public void givenTreeMap_whenOrdersEntriesNaturally_thenCorrect() {
    TreeMap<Integer, String> map = new TreeMap<>();
    map.put(3, "val");
    map.put(2, "val");
    map.put(1, "val");
    map.put(5, "val");
    map.put(4, "val"); 
    assertEquals("[1, 2, 3, 4, 5]", map.keySet().toString());
}

Notice that we placed the integer keys in a non-orderly manner but on retrieving the key set, we confirm that they are indeed 
maintained in ascending order. This is the natural ordering of integers.
Likewise, when we use strings, they will be sorted in their natural order, i.e. alphabetically:
@Test
public void givenTreeMap_whenOrdersEntriesNaturally_thenCorrect2() {
    TreeMap<String, String> map = new TreeMap<>();
    map.put("c", "val");
    map.put("b", "val");
    map.put("a", "val");
    map.put("e", "val");
    map.put("d", "val");
    assertEquals("[a, b, c, d, e]", map.keySet().toString());
}
TreeMap, unlike a hash map and linked hash map, does not employ the hashing principle anywhere since it does not use an array to 
store its entries.

We now know that TreeMap stores all its entries in sorted order. Because of this attribute of tree maps, we can perform queries 
like; find “largest”, find “smallest”, find all keys less than or greater than a certain value, etc.
The code below only covers a small percentage of these cases:
@Test
public void givenTreeMap_whenPerformsQueries_thenCorrect() {
    TreeMap<Integer, String> map = new TreeMap<>();
    map.put(3, "val");
    map.put(2, "val");
    map.put(1, "val");
    map.put(5, "val");
    map.put(4, "val");
        
    Integer highestKey = map.lastKey();
    Integer lowestKey = map.firstKey();
    Set<Integer> keysLessThan3 = map.headMap(3).keySet();
    Set<Integer> keysGreaterThanEqTo3 = map.tailMap(3).keySet();
 
    assertEquals(new Integer(5), highestKey);
    assertEquals(new Integer(1), lowestKey);
    assertEquals("[1, 2]", keysLessThan3.toString());
    assertEquals("[3, 4, 5]", keysGreaterThanEqTo3.toString());
}
*/
class Solution {
    public int longestSubarray(int[] nums, int limit) {
        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
        int i = 0;
        for(int j = 0; j < nums.length; j++) {
            map.put(nums[j], map.getOrDefault(nums[j], 0) + 1);
            // TreeMap last entry key will be maximum value in current window,
            // first entry key will be minimum value in current window
            if(map.lastEntry().getKey() - map.firstEntry().getKey() > limit) {
                map.put(nums[i], map.get(nums[i]) - 1);
                if(map.get(nums[i]) == 0) {
                    map.remove(nums[i]);
                }
                i++;
            }
        }
        return nums.length - i;
    }
}
