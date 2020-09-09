/**
 Refer to
 https://blog.csdn.net/xiyang0405/article/details/104747193
 https://www.cnblogs.com/Dylan-Java-NYC/p/12075993.html
 You are given some lists of regions where the first region of each list includes all other regions in that list.

Naturally, if a region X contains another region Y then X is bigger than Y. Also by definition a region X contains itself.

Given two regions region1, region2, find out the smallest region that contains both of them.

If you are given regions r1, r2 and r3 such that r1 includes r3, it is guaranteed there is no r2 such that r2 includes r3.

It's guaranteed the smallest region exists.

Example 1:

Input:
regions = [["Earth","North America","South America"],
["North America","United States","Canada"],
["United States","New York","Boston"],
["Canada","Ontario","Quebec"],
["South America","Brazil"]],
region1 = "Quebec",
region2 = "New York"
Output: "North America"
Constraints:

2 <= regions.length <= 10^4
region1 != region2
All strings consist of English letters and spaces with at most 20 letters.
*/

// Solution 1: 
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/LowestCommonAncestorOfABinaryTree.java
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/LowestCommonAncestorOfABinarySearchTree.java
// https://blog.csdn.net/xiyang0405/article/details/104747193
// https://www.cnblogs.com/Dylan-Java-NYC/p/12075993.html
/**
 With the regions list, we could construct partent HashMap with child pointing to parent.
 Maintain all the regions used while finding ancestor of region1.
 When finding ancestor of region2, return the first occurance of region that is in used, it would be smallest common region.
 Time Complexity: O(n). n = regions.size() * average length. h is height of parent tree.
 Space: O(n).
*/

// https://www.jianshu.com/p/7bb4936a1419
/**
 因为只要单向找，所以hashmap这个key value pair，这里是表示一个方向，所以建从孩子到parent的好用，就可以从input开始查了。
 这里用了一个set记录region1的path，region2就可以一路查上去了。
*/

