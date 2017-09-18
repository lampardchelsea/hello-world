Heap
A heap is a specialized tree-based data structure that satisfies the heap property: if P is a parent node of C, then the key (the value) of node P is greater than the key of node C. A heap can be classified further as either a "max heap" or a "min heap"
A heap is a useful data structure when you need to remove the object with the highest (or lowest) priority.
https://www.youtube.com/watch?v=uZj0hetLFHU

Heap 在工作中很少用到，但在面试时是非常有用的工具。

1. Priority Queue
this implementation provides O(log(n)) time for the enqueuing and dequeuing methods (offer, poll, remove() and add); linear time for the remove(Object) and contains(Object) methods
这是 Heap 最主要的用处。

2. TreeMap
This implementation provides guaranteed log(n) time cost for the containsKey, get, put and remove operations.
如果你想把 key-value pairs 放到 Priority Queue 里，就用 TreeMap。

我只想到了一道题用到了 TreeMap：
218. The Skyline Problem
https://leetcode.com/problems/the-skyline-problem/description/
但其实很多题目都需要把 heap 当作 Priority Queue 来节约时间的。

勘误：
今天早上我又研究了一下，发现我昨天讲错了。
TreeMap 不是 Heap 的 key-value 实现形式。
TreeMap 是一个 Balanced-Binary Search Tree，和 Heap 是两种结构，应该算作 Tree。
等稍后我讲 Tree 的时候再回过头来细说它。

所以 Heap 最后就只剩下 PriorityQueue 一个 class 需要掌握。

抱歉，学艺不精，
QiQi
