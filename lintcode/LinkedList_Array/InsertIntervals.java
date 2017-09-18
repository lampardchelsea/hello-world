/**
 * Refer to
 * http://www.lintcode.com/en/problem/insert-interval/
 * Given a non-overlapping interval list which is sorted by start point.

    Insert a new interval into it, make sure the list is still in order and non-overlapping (merge intervals if necessary).

    Have you met this question in a real interview? Yes
    Example
    Insert [2, 5] into [[1,2], [5,9]], we get [[1,9]].

    Insert [3, 4] into [[1,2], [5,9]], we get [[1,2], [3,4], [5,9]].
 *
 *
 * Solution
 * http://www.cnblogs.com/grandyang/p/4367569.html
 * 这道题让我们在一系列非重叠的区间中插入一个新的区间，可能还需要和原有的区间合并，那么我们需要对给区间集
   一个一个的遍历比较，那么会有两种情况，重叠或是不重叠，不重叠的情况最好，直接将新区间插入到对应的位置即可，
   重叠的情况比较复杂，有时候会有多个重叠，我们需要更新新区间的范围以便包含所有重叠，而且最后处理的时候
   还需要删除原区间集中所有和新区间重叠的区间，然后插入新区间即可。具体思路如下：

- 对区间集中每个区间进行遍历

　　- 如果新区间的末尾小于当前区间的开头，则跳出循环

　　- 如果新区间的开头大于当前区间的末尾，不作处理

　　- 如果新区间和当前区间有重叠，则更新新区间的开头为两者最小值，新区间的末尾为两者最大值，重叠数加一

　　- 指针移向下一个区间

- 如果重叠数大于0，则删除掉所有的重叠区间

- 插入新区间到对应的位置 
 *
 * http://www.jiuzhang.com/solutions/insert-interval/
*/
