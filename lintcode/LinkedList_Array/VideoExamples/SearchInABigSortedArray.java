/**
 * Refer to
 * http://www.haobanfa.info/lintcode-447-search-big-sorted-array-%E5%8E%9F%E5%88%9Bjava%E5%8F%82%E8%80%83%E8%A7%A3%E7%AD%94/
 * Given a big sorted array with positive integers sorted by ascending order. 
   The array is so big so that you can not get the length of the whole array directly, 
   and you can only access the kth number by ArrayReader.get(k) (or ArrayReader->get(k) for C++). 
   Find the first index of a target number. Your algorithm should be in O(log k), 
   where k is the first index of the target number.

   Return -1, if the number doesn’t exist in the array.

   Notice
   If you accessed an inaccessible index (outside of the array), ArrayReader.get will return 2,147,483,647.
   Example
   Given [1, 3, 6, 9, 21, ...], and target = 3, return 1.
   Given [1, 3, 6, 9, 21, ...], and target = 4, return -1.
 *
 * Solution
 * http://www.haobanfa.info/lintcode-447-search-big-sorted-array-%E5%8E%9F%E5%88%9Bjava%E5%8F%82%E8%80%83%E8%A7%A3%E7%AD%94/
 * 题意是关于在一个极大已排序数列中查找给定的目标值target。如果找到目标值，则返回目标值在数列第一次出现的index位置，
   如果不在，则返回 －1。
   这套题是考察Binary Search二分搜索法的应用。这套题tricky的地方是在于不能简单用array.length来拿到array的长度，
   因而不能直接设置初始的end ＝ array.length。
   这套题关键在于逐步扩大数列的长度，而不是直接拿数列的长度。
   首先，我们设置一个index，通过ArrayReader.get(index)拿到该index上的值与target大小进行比较。
   如果ArrayReader.get(index) < target，则把index加倍。直到找到比target大的index位置。
   接下来即可用标准的Binary Search二分搜索法进行查找target是否在该数列中。初始时候，start = 0， end ＝ index – 1。
   不过需要注意，因为是需要查找给定的目标值target的第一次出现的位置。所以当reader.get(mid) == target 时，
   需要把end 设置为 mid。从而再循环查找是否还有更早出现的target位置。
 * 
 * http://xuyiruan.com/2016/06/28/Search-in-a-Big-Sorted-Array/
*/
