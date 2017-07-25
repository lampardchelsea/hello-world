/**
 * Given a big sorted array with positive integers sorted by ascending order. 
 * The array is so big so that you can not get the length of the whole array directly, 
 * and you can only access the kth number by ArrayReader.get(k) (or ArrayReader->get(k) for C++).
 
    Find the first index of a target number. Your algorithm should be in O(log k), where
    k is the first index of the target number.

    Return -1, if the number doesn’t exist in the array.

    Notice
    If you accessed an inaccessible index (outside of the array),
    ArrayReader.get will return 2,147,483,647.

    Example
    Given [1, 3, 6, 9, 21, …], and target = 3, return 1.
    Given [1, 3, 6, 9, 21, …], and target = 4, return -1.
*
* Solution
* 思路
  这题可能如果二分不熟悉的话，一开始看有点懵逼，因为题目并没有把所有二分的条件都直接给出来，考的就是让你如果把二分的所有条件都找齐。

  二分的条件是什么呢？

  第一点 - 知道要找的 target，这一点题目已经给了
  第二点 - 二分实行的数值（candidates），这题也已经给了就是里面的这个 sorted big array
  第三点 - 二分法的边界，这一点才是题目没有给的。

  那么我们来看边界怎么找，左边界很明显就是第一个值，这里也就是1。

  重点是右边界，我们知道 binary search 的右边界是一个一定大于或者等于我们 target 的值
  那么我们就通过他给的 API 来找到一个一个大于或者等于 index 的值 n，然后我们再在 [1, n] 里面执行 binary search 来找到我们的 target 就可以了。

  举个粒子。 假如这个 big sorted array 是 1,2,3,4,5,6,7,8，要找的 target 是5.
  我们不知道他长这样，也不知道总共多少个值。那么我们设定一个右边界 variable end = 1 然后用一个 while 循环，倍增 end 直到 reader.get(end) >= 5 为止。

  这里我们就会每次乘2直到end = 8,那么我们的右边界就找到了。
 */
public int searchBigSortedArray(ArrayReader reader, int target) {
    
}
