/**
Refer to
https://leetcode.com/problems/intersection-of-two-arrays-ii/
Given two arrays, write a function to compute their intersection.

Example 1:
Input: nums1 = [1,2,2,1], nums2 = [2,2]
Output: [2,2]

Example 2:
Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
Output: [4,9]

Note:
Each element in the result should appear as many times as it shows in both arrays.
The result can be in any order.

Follow up:
What if the given array is already sorted? How would you optimize your algorithm?
What if nums1's size is small compared to nums2's size? Which algorithm is better?
What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into the memory at once?
*/

// Solution 1: Two Pointers + Sort Array
// Refer to
// https://leetcode.com/problems/intersection-of-two-arrays-ii/discuss/282372/Java-solution-with-all-3-follow-up-questions
/**
The first question is relatively easy, create a hashmap base on number frequency of nums1(whichever one is longer). Then for every element of nums2, look upon the hashmap. If we found an intersection, deduct by 1 to avoid duplicate.

public int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i : nums1){
            int freq = map.getOrDefault(i, 0);
            map.put(i, freq + 1);
        }
        ArrayList<Integer> list = new ArrayList<>();
        for(int i : nums2){
            if(map.get(i) != null && map.get(i) > 0){
                list.add(i);
                map.put(i, map.get(i) - 1);
            }
        }
        int[] ret = new int[list.size()]; 
        for(int i = 0; i < list.size();i++){
            ret[i] = list.get(i);
        }
        return ret;
    }
This solution is O(N + M) time complexity, O(N) for iterate one of the array to create a hashmap and O(M) to 
iterate the other array. O(N) space to store such hashmap.

Follow Up:
What if the given array is already sorted? How would you optimize your algorithm?
Classic two pointer iteration, i points to nums1 and j points to nums2. Because a sorted array is in ascending order, 
so if nums1[i] > nums[j], we need to increment j, and vice versa. Only when nums1[i] == nums[j], we add it to the 
result array. Time Complexity O(max(N, M)). Worst case, for example, would be nums1 = {100}, and nums2 = {1, 2, ..., 100 }. 
We will always iterate the longest array. The example code is below(I sorted it so it could go through OJ):

public int[] intersect(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int n = nums1.length, m = nums2.length;
        int i = 0, j = 0;
        List<Integer> list = new ArrayList<>();
        while(i < n && j < m){
            int a = nums1[i], b= nums2[j];
            if(a == b){
                list.add(a);
                i++;
                j++;
            }else if(a < b){
                i++;
            }else{
                j++;
            }
        }
        int[] ret = new int[list.size()];
        for(int k = 0; k < list.size();k++) ret[k] = list.get(k);
        return ret;
    }
What if nums1's size is small compared to nums2's size? Which algorithm is better?
This one is a bit tricky. Let's say nums1 is K size. Then we should do binary search for every element in nums1. 
Each lookup is O(log N), and if we do K times, we have O(K log N).
If K this is small enough, O(K log N) < O(max(N, M)). Otherwise, we have to use the previous two pointers method.
let's say A = [1, 2, 2, 2, 2, 2, 2, 2, 1], B = [2, 2]. For each element in B, we start a binary search in A. 
To deal with duplicate entry, once you find an entry, all the duplicate element is around that that index, 
so you can do linear search scan afterward.

Time complexity, O(K(logN) + N). Plus N is worst case scenario which you have to linear scan every element in A. 
But on average, that shouldn't be the case. so I'd say the Time complexity is O(K(logN) + c), c (constant) is 
number of linear scan you did.

What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements 
into the memory at once?
This one is open-ended. But Map-Reduce I believe is a good answer.
*/
class Solution {
    public int[] intersect(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        List<Integer> list = new ArrayList<Integer>();
        int i = 0;
        int j = 0;
        while(i < nums1.length && j < nums2.length) {
            if(nums1[i] < nums2[j]) {
                i++;
            } else if(nums1[i] > nums2[j]) {
                j++;
            } else {
                list.add(nums1[i]);
                i++;
                j++;
            }
        }
        int[] result = new int[list.size()];
        int k = 0;
        for(int a : list) {
            result[k++] = a;
        }
        return result;
    }
}

// Solution 2: HashMap
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/lintcode/LinkedList_Array/VideoExamples/IntersectionOfTwoArraysII.java
class Solution {
    public int[] intersect(int[] nums1, int[] nums2) {
        List<Integer> list = new ArrayList<Integer>();
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int a : nums1) {
            map.put(a, map.getOrDefault(a, 0) + 1);
        }
        for(int a : nums2) {
            if(map.containsKey(a) && map.get(a) > 0) {
                list.add(a);
                map.put(a, map.get(a) - 1);
            }
        }
        int[] result = new int[list.size()];
        for(int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }
}


