/**
 * Refer to
 * https://leetcode.com/problems/minimum-index-sum-of-two-lists/#/description
 * Suppose Andy and Doris want to choose a restaurant for dinner, and they both have 
 * a list of favorite restaurants represented by strings.
 * You need to help them find out their common interest with the least list index sum. 
 * If there is a choice tie between answers, output all of them with no order requirement. 
 * You could assume there always exists an answer.
    Example 1:
    Input:
    ["Shogun", "Tapioca Express", "Burger King", "KFC"]
    ["Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"]
    Output: ["Shogun"]
    Explanation: The only restaurant they both like is "Shogun".

    Example 2:
    Input:
    ["Shogun", "Tapioca Express", "Burger King", "KFC"]
    ["KFC", "Shogun", "Burger King"]
    Output: ["Shogun"]
    Explanation: The restaurant they both like and have the least index sum is "Shogun" with index sum 1 (0+1).

 * Note:
    The length of both lists will be in the range of [1, 1000].
    The length of strings in both lists will be in the range of [1, 30].
    The index is starting from 0 to the list length minus 1.
    No duplicates in both lists.
 * 
 * Solution 1 (HashMap)
 * https://leetcode.com/articles/minimum-index-sum-of-two-lists/
 * Approach #3 Using HashMap (linear) [Accepted]
 * We make use of a HashMap to solve the given problem in a different way in this approach. 
 * Firstly, we traverse over the whole list1 and create an entry for each element 
 * of list1 in a HashMap map, of the form (list[i],i). 
 * Here, iii refers to the index of the ith element, and list[i] is the ith element itself. 
 * Thus, we create a mapping from the elements of list1 to their indices.
 * Now, we traverse over list2. For every element ,list2[j], of list2 encountered, we check 
 * if the same element already exists as a key in the map. If so, it means that the element 
 * exists in both list1list1list1 and list2list2list2. Thus, we find out the sum of indices 
 * corresponding to this element in the two lists, given by sum=map.get(list[j])+jsum = 
 * map.get(list[j]) + jsum=map.get(list[j])+j. If this sum is lesser than the minimum 
 * sum obtained till now, we update the resultant list to be returned, resresres, with the 
 * ement list2[j] as the only entry in it.
 * If the sum is equal to the minimum sum obtained till now, we put an extra entry corresponding 
 * to the element list2[j] in the resresres list.
 * Complexity Analysis
 *
 * Time complexity : O(l1+l2). Every item of list2 is checked in a map of list1. l1 and l2
 * are the lengths of list1 and list2 respectively.
 * Space complexity : O(l1∗x). hashmap size grows upto l1, where x refers to average string length.
 *
 * Solution 2 (No HashMap)
 * Approach #2 Without Using HashMap [Accepted]
 * Algorithm
 * Another method could be to traverse over the various sumsumsum(index sum) values and determine 
 * if any such string exists in list1 and list2 such that the sum of its indices 
 * in the two lists equals sumsumsum.
 * Now, we know that the value of index sum, sum could range from 0 to m+n−1. 
 * Here, m and n refer to the length of lists list1 and list2 respectively. Thus, we choose every 
 * value of sum in ascending order. For every sum chosen, we iterate over list1. Suppose, currently 
 * the string at ith index in list1 is being considered. Now, in order for the index sum sum to be 
 * the one corresponding to matching strings in list1 and list2, the string at index j in list2 
 * should match the string at index iii in list1, such that sum=i+j.
 * Or, stating in other terms, the string at index j in list2 should be equal to the string at index 
 * i in list1, such that j=sum−i. Thus, for a particular sumand i(from list1), we can directly determine 
 * that we need to check the element at index j=sum−i in list2, instead of traversing over the whole list2.
 * Doing such checks/comparisons, iterate over all the indices of list1 for every sumsumsum value chosen. \
 * Whenver a match occurs between list1 and list2, we put the matching string in a list resresres.
 * We do the same process of checking the strings for all the values of sumin ascending order. After 
 * completing every iteration over list1 for a particular sum, we check if the resresres list is empty or 
 * not. If it is empty, we need to continue the process with the next sum value considered. If not, 
 * the current resresres gives the required list with minimum index sum. This is because we are already 
 * considering the index sum values in ascending order. So, the first list to be found is the required 
 * resultant list.
*/
public class Solution1 {
    public String[] findRestaurant(String[] list1, String[] list2) {
        List<String> result = new ArrayList<String>();
        if(list1 == null || list2 == null) {
            return result.toArray(new String[0]);
        }
        Map<String, Integer> map = new HashMap<String, Integer>();
        for(int i = 0; i < list1.length; i++) {
            map.put(list1[i], i);
        }
        int min = Integer.MAX_VALUE;
        int sum = 0;
        // Additional i <= min control will help on decrease for loop
        // times, if not add, execution time is 36ms, if add, 23ms
        for(int i = 0; i < list2.length && i <= min; i++) {
            if(map.containsKey(list2[i])) {
                sum = i + map.get(list2[i]);
                if(sum < min) {
                    result.clear();
                    result.add(list2[i]);
                    min = sum;
                } else if(sum == min) {
                    result.add(list2[i]);
                }
            }
        }
        return result.toArray(new String[result.size()]);
    }
}


public class Solution2 {
    public String[] findRestaurant(String[] list1, String[] list2) {
        List<String> result = new ArrayList<String>();
        if(list1 == null || list2 == null) {
            return result.toArray(new String[0]);
        }
        int pre_sum = Integer.MAX_VALUE;
        for(int sum = 0; sum < list1.length + list2.length - 1; sum++) {
            for(int i = 0; i <= sum; i++) {
                if(i < list1.length && sum - i < list2.length && list1[i].equals(list2[sum - i]) && sum <= pre_sum) {
                    pre_sum = sum;
                    result.add(list1[i]);
                }
                // Should not break out directly but adding a condition as 'sum <= pre_sum' is fine
                // to collect all same 'sum' combintaions
                // if(result.size() > 0) {
                //     break;
                // }
            }
        }
        return result.toArray(new String[result.size()]);
    }
}



