/**
 * Refer to
 * https://leetcode.com/problems/distribute-candies/#/description
 * Given an integer array with even length, where different numbers in this array represent different 
 * kinds of candies. Each number means one candy of the corresponding kind. You need to distribute 
 * these candies equally in number to brother and sister. Return the maximum number of kinds of 
 * candies the sister could gain.
  Example 1:
  Input: candies = [1,1,2,2,3,3]
  Output: 3
  Explanation:
  There are three different kinds of candies (1, 2 and 3), and two candies for each kind.
  Optimal distribution: The sister has candies [1,2,3] and the brother has candies [1,2,3], too. 
  The sister has three different kinds of candies. 

  Example 2:
  Input: candies = [1,1,2,3]
  Output: 2
  Explanation: For example, the sister has candies [2,3] and the brother has candies [1,1]. 
  The sister has two different kinds of candies, the brother has only one kind of candies. 

 * Note:
 * The length of the given array is in range [2, 10,000], and will be even.
 * The number in given array is in range [-100,000, 100,000].
 *
 * Solution
 * https://leetcode.com/articles/distribute-candies/
 * Approach #4 Using set [Accepted]
 * Algorithm
 * Another way to find the number of unique elements is to traverse over all the elements of the given 
 * candiescandiescandies array and keep on putting the elements in a set. By the property of a set, it 
 * will contain only unique elements. At the end, we can count the number of elements in the set, 
 * given by, say countcountcount. The value to be returned will again be given by min(count,n/2), 
 * as discussed in previous approaches. Here, nnn refers to the size of the candiescandiescandies array.
 * Complexity Analysis
 * Time complexity : O(n). The entire candiescandiescandies array is traversed only once. 
 * Here, n refers to the size of candiescandiescandies array.
 * Space complexity : O(n). setsetset will be of size n in the worst case.
*/
public class Solution {
    public int distributeCandies(int[] candies) {
        Set<Integer> set = new HashSet<Integer>();
        for(int n : candies) {
            set.add(n);
        }
        return Math.min(set.size(), candies.length / 2);
    }
}







