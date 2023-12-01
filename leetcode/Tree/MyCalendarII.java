/**
 Refer to
 https://leetcode.com/problems/my-calendar-ii/
 Implement a MyCalendarTwo class to store your events. A new event can be added if adding the event will not 
 cause a triple booking.
 Your class will have one method, book(int start, int end). Formally, this represents a booking on the half 
 open interval [start, end), the range of real numbers x such that start <= x < end.
 A triple booking happens when three events have some non-empty intersection (ie., there is some time that is 
 common to all 3 events.)
 For each call to the method MyCalendar.book, return true if the event can be added to the calendar successfully 
 without causing a triple booking. Otherwise, return false and do not add the event to the calendar.
 Your class will be called like this: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)
 
 Example 1:
 MyCalendar();
 MyCalendar.book(10, 20); // returns true
 MyCalendar.book(50, 60); // returns true
 MyCalendar.book(10, 40); // returns true
 MyCalendar.book(5, 15); // returns false
 MyCalendar.book(5, 10); // returns true
 MyCalendar.book(25, 55); // returns true
 
 Explanation: 
 The first two events can be booked.  The third event can be double booked.
 The fourth event (5, 15) can't be booked, because it would result in a triple booking.
 The fifth event (5, 10) can be booked, as it does not use time 10 which is already double booked.
 The sixth event (25, 55) can be booked, as the time in [25, 40) will be double booked with the third event;
 the time [40, 50) will be single booked, and the time [50, 55) will be double booked with the second event.
 
 Note:
 The number of calls to MyCalendar.book per test case will be at most 1000.
 In calls to MyCalendar.book(start, end), start and end are integers in the range [0, 10^9].
*/

// Solution 1: Brutal Force
// Refer to
// Style 1
// https://leetcode.com/problems/my-calendar-ii/discuss/109519/JavaC%2B%2B-Clean-Code-with-Explanation
/**
 The big idea is pretty simple:
 Each time of book, instead of fail a book when there is 1 or more overlap with existing books as in MyCalendar I, 
 we just want to make sure these overlaps does not overlap - having overlap is now ok, but overlapped period cannot 
 be overlapped again.
 So we just need to keep track of all the overlaps with any previous books
 
 MyCalendar I can be reused to track the overlaps during each book.
 
 How to calculate overlap of 2 intervals
 Assume a start earlier than b, (if not reverse), there could be 3 case, but in any case, an overlap(either positive 
 or negative) can always be represented as: (max(a0, b0), min(a1, b1))

 case 1: b ends before a ends:
 a: a0 |-------------| a1
 b:     b0 |-----| b1

 case 2: b ends after a ends:
 a: a0 |--------| a1
 b:     b0 |--------| b1

 case 3: b starts after a ends: (negative overlap)
 a: a0 |----| a1
 b:              b0 |----| b1
*/
class MyCalendarTwo {
    private List<int[]> books = new ArrayList<int[]>();
    public boolean book(int start, int end) {
        MyCalendar overlaps = new MyCalendar();
        for(int[] book : books) {
            if(book[1] > start && book[0] < end) { // overlap exist
                if(!overlaps.book(Math.max(book[0], start), Math.min(book[1], end))) {
                    return false; // overlaps overlapped
                }
            }
        }
        books.add(new int[] {start, end});
        return true;
    }
    
    private static class MyCalendar {
        List<int[]> books = new ArrayList<int[]>();
        public boolean book(int start, int end) {
            for(int[] book : books) {
                if(book[1] > start && book[0] < end) {
                    return false;
                }
            }
            books.add(new int[] {start, end});
            return true;
        }        
    }
}

/**
 * Your MyCalendarTwo object will be instantiated and called as such:
 * MyCalendarTwo obj = new MyCalendarTwo();
 * boolean param_1 = obj.book(start,end);
 */

// Style 2
// https://leetcode.com/problems/my-calendar-ii/solution/
/**
 Intuition
 Maintain a list of bookings and a list of double bookings. When booking a new event [start, end), 
 if it conflicts with a double booking, it will have a triple booking and be invalid. Otherwise, 
 parts that overlap the calendar will be a double booking.

 Algorithm
 Evidently, two events [s1, e1) and [s2, e2) do not conflict if and only if one of them starts after 
 the other one ends: either e1 <= s2 OR e2 <= s1. By De Morgan's laws, this means the events conflict 
 when s1 < e2 AND s2 < e1.
 
 If our event conflicts with a double booking, it's invalid. Otherwise, we add conflicts with the 
 calendar to our double bookings, and add the event to our calendar.
 
 Complexity Analysis
 Time Complexity: O(N^2), where N is the number of events booked. For each new event, we process every 
 previous event to decide whether the new event can be booked. This leads to sum_k^N O(k) = O(N^2) complexity.
 Space Complexity: O(N), the size of the calendar.
*/
class MyCalendarTwo {
    List<int[]> calendar;
    List<int[]> overlaps;
    
    public MyCalendarTwo() {
        calendar = new ArrayList<int[]>();
        overlaps = new ArrayList<int[]>();
    }
    
    public boolean book(int start, int end) {
        for(int[] overlap : overlaps) {
            if(overlap[1] > start && overlap[0] < end) {
                return false;
            }
        }
        for(int[] element : calendar) {
            if(element[1] > start && element[0] < end) {
                overlaps.add(new int[] {Math.max(start, element[0]), Math.min(end, element[1])});
            }
        }
        calendar.add(new int[] {start, end});
        return true;
    }
}




/**
 * Your MyCalendarTwo object will be instantiated and called as such:
 * MyCalendarTwo obj = new MyCalendarTwo();
 * boolean param_1 = obj.book(start,end);
 */
 
 // Solution 2: Segment Tree
 // Refer to
 // https://leetcode.com/problems/my-calendar-ii/discuss/232261/Simple-and-Optimal-Segment-Tree-Solution
 class MyCalendarTwo {    
    private SegmentTreeNode root;
    
    public MyCalendarTwo() {
        
    }
    
    public boolean book(int start, int end) {
        if(query(root, start, end) >= 2) {
            return false;
        }
        root = insert(root, start, end);
        return true;
    }
    
    // new_node_end is exclusive.
    private int query(SegmentTreeNode node, int new_node_start, int new_node_end) {
        if(new_node_start >= new_node_end || node == null) {
            return 0;
        }
        if(node.start >= new_node_end) {
            return query(node.left, new_node_start, new_node_end);
        } else if(node.end <= new_node_start) {
            return query(node.right, new_node_start, new_node_end);
        }
        return Math.max(node.overlap_count, Math.max(query(node.left, new_node_start, new_node_end), query(node.right, new_node_start, new_node_end)));
    }
    
    private SegmentTreeNode insert(SegmentTreeNode node, int new_node_start, int new_node_end) {
        if(new_node_start >= new_node_end) {
            return node;
        }
        if(node == null) {
            // If recursively till end and not interrupted means we found 
            // an available interval, then create a node, but with one 
            // more property 'overlap_count = 1', for value initial as 1 
            // is for block potential triple booking, we can imagine as:
            // first we put a new node in an interval, 'overlap_count = 1',
            // then we put another node has overlap to previous new node,
            // increasing that previous new node 'overlap_count' to 2,
            // now if you try to put another node has overlap to both nodes
            // by 'query' method, it will block
            return new SegmentTreeNode(new_node_start, new_node_end, 1);
        }
        if(node.end <= new_node_start) {
            node.right = insert(node.right, new_node_start, new_node_end);
        } else if(node.start >= new_node_end) {
            node.left = insert(node.left, new_node_start, new_node_end);
        } else {
            int a = Math.min(new_node_start, node.start);
            int b = Math.max(new_node_start, node.start);
            int c = Math.min(new_node_end, node.end);
            int d = Math.max(new_node_end, node.end);
            node.start = b;
            node.end = c;
            node.overlap_count += 1;
            node.left = insert(node.left, a, b);
            node.right = insert(node.right, c, d);
        }
        return node;
    }
}

class SegmentTreeNode {
    int start;
    int end;
    int overlap_count;
    SegmentTreeNode left;
    SegmentTreeNode right;
    public SegmentTreeNode(int start, int end, int overlap_count) {
        this.start = start;
        this.end = end;
        this.overlap_count = overlap_count;
    }
}

/**
 * Your MyCalendarTwo object will be instantiated and called as such:
 * MyCalendarTwo obj = new MyCalendarTwo();
 * boolean param_1 = obj.book(start,end);
 */
 
 // Solution 3: Segement Tree + Lazy propagation
 // Refer to
 // https://leetcode.com/problems/my-calendar-ii/discuss/109528/nlogd-Java-solution-using-segment-tree-with-lazy-propagation-(for-the-general-case-of-K-booking)
 /**
Segment trees have typical applications for these type of problems but I'm surprised that no one has brought it up. 
So here I will introduce the general structures for writing segment tree related codes.

Step I -- Define the segment tree node

A segment tree node typically contains four pieces of information:

The segment this tree node represents: [l, r] (both inclusive)

The property fields associated with this segment: for this problem, this will be the maximum integer k such that there 
exists a k-booking within the segment [l, r].

The lazy fields corresponding to each property field above: this is required for efficient range updating, and can be 
dropped if there is no range updating (see this article and this Quora post for more detailed explanation).

The pointer to the left and right subtrees: left and right.

Here is what our segment tree node looks like:

private static class SegmentTreeNode {
    int l, r;
    int k, lazy;
    SegmentTreeNode left, right;
        
    SegmentTreeNode(int l, int r, int k) {
        this.l = l;
        this.r = r;
        this.k = k;
        this.lazy = 0;
        this.left = this.right = null;
    }
}
Step II -- Write the query and update functions

Given a range [i, j], corresponding to an event of range [i, j+1), the query function should return the maximum 
integer k such that there exists a k-booking within the range [i, j], so that we can use the information to 
determine whether this event can be booked or not.

If this event can be booked, the update function then will update all segments within the range [i, j] accordingly 
(in this case, increase the k value of all segments within the range [i, j] by 1).

A. -- The general structure of the query function:

Check if the query range is invalid or out of range with respect to current segment -- if so, simply return 0.

Check if the query range covers current segment -- if so, simply return the k value of current segment node.

Normalize the segment tree node -- the node may have been marked as lazy from previous steps, so we need to remove 
the laziness in order to see the most recent values.

Recurse to the left and right subtrees -- simply call the query function on the two child nodes of current segment 
node with the same query range [i, j].

Return the combined results of the left and right subtrees -- in this case, it will be the larger one of the two, 
since we need the maximum integer k.

Here is what it looks like:

private int query(SegmentTreeNode node, int i, int j) {    
    if (i > j || node == null || i > node.r || j < node.l) return 0;
    
    if (i <= node.l && node.r <= j) return node.k;
	
    normalize(node);

    return Math.max(query(node.left, i, j), query(node.right, i, j));
}
B. -- The general structure of the update function (very similar to query):

Check if the query range is invalid or out of range with respect to current segment -- if so, simply return.

Check if the query range covers current segment -- if so, update the property and lazy fields of current segment node, then return.

Normalize the segment tree node -- the node may have been marked as lazy from previous steps, so we need to remove 
the laziness in order to avoid overwriting prior updates.

Recurse to the left and right subtrees -- simply call the update function on the two child nodes of current segment 
node with the same query range [i, j].

Propagate the results of the left and right subtrees back to the parent node -- in this case, the k value of the 
parent node will be set to the larger one of the two subtree nodes.

Here is what it looks like:

private void update(SegmentTreeNode node, int i, int j, int val) {        
    if (i > j || node == null || i > node.r || j < node.l) return;
    
    if (i <= node.l && node.r <= j) {
		node.k += val;
        node.lazy += val;
        return;
    }
	
	normalize(node);

    update(node.left, i, j, val);
    update(node.right, i, j, val);
    
    node.k = Math.max(node.left.k, node.right.k);
}
C. -- The general structure of the normalize function:

Push down the laziness to the child nodes
1a. First make sure current segment node is not a leaf node (a leaf node has l == r)
1b. If the two child nodes are null, we initialize them with the value of current node
1c. Otherwise we simply update their propery and lazy fields (by adding the lazy field of current node).

Reset the laziness of current segment node so as to normalize it (by resetting its lazy field to 0).

Here is what it looks like:

private void normalize(SegmentTreeNode node) {
    if (node.l < node.r) {
        if (node.left == null || node.right == null) {
            int m = node.l + (node.r - node.l) / 2;
            node.left = new SegmentTreeNode(node.l, m, node.k);
            node.right = new SegmentTreeNode(m + 1, node.r, node.k);
        
        } else if (node.lazy > 0) {
            node.left.k += node.lazy;
			node.left.lazy += node.lazy;

            node.right.k += node.lazy;
            node.right.lazy += node.lazy;
        }
    }
    
    node.lazy = 0;
}
Step III -- Initialize the root node and write the book function

The root node should at least cover the maximum range allowed for all events, which for this problem is [0, 10^9]. 
Its k value will be set to 0 since at the beginning no events are booked.

The book function will first query the k value within the range of the event to be added. If k >= 2, then there 
exists at least one double booking within that range, and adding the event would cause a triple booking, therefore 
the event will be dropped and the function return false. Otherwise, it will add the event to the calendar by 
updating accordingly the segments within the range of the added event and return true.

Here is the root node and the book function:

SegmentTreeNode root;

public MyCalendarTwo() {
    root = new SegmentTreeNode(0, 1_000_000_000, 0);
}

public boolean book(int start, int end) {
    int k = query(root, start, end - 1);
    if (k >= 2) return false;  // For the general case of `K`-booking, replace `2` with `K-1`
    
    update(root, start, end - 1, 1);
    return true;
}
Step IV -- complexity analyses

Time complexity: for each call of the function book, we need to do at most one query and one update, both of 
which can be done in logd time, where d is the maximum range allowed for all events (in this case d = 10^9). 
Therefore, for n calls of the book function, the total time complexity will be O(nlogd).

Space complexity: in the worst case, the segment tree can be a full binary tree with 2d nodes. However, this 
is very unlikely as it would require a total of d calls of the book function, each with an event of range 1. 
For n calls of the book function, the average space cost is roughly O(nlogd).

Step V -- Generalization to arbitrary K-booking

The generalization to K-booking is trivial. The only modification we need to do is to replace the number to 
which we compare the k value in the book function to K-1. Everything else will remain the same.

For "My Calendar I", this number is 1; for "My Calendar II", this number is 2. For "My Calendar III", however, 
the events can always be added to the calendar so we don't even need the query function. The maximum value of K 
such that there exists a K-booking in the calendar is given, by definition, the k field of the root node after updating.

Lastly, for you convenience, here is the complete solution for "My Calendar II" by putting everything together:

private static class SegmentTreeNode {
    int l, r;
    int k, lazy;
    SegmentTreeNode left, right;
        
    SegmentTreeNode(int l, int r, int k) {
        this.l = l;
        this.r = r;
        this.k = k;
        this.lazy = 0;
        this.left = this.right = null;
    }
}

private int query(SegmentTreeNode node, int i, int j) {    
    if (i > j || node == null || i > node.r || j < node.l) return 0;
    
    if (i <= node.l && node.r <= j) return node.k;
   
	normalize(node);

    return Math.max(query(node.left, i, j), query(node.right, i, j));
}

private void update(SegmentTreeNode node, int i, int j, int val) {
    if (i > j || node == null || i > node.r || j < node.l) return;
    
    if (i <= node.l && node.r <= j) {
		node.k += val;
        node.lazy += val;
        return;
    }
	
    normalize(node);
	
    update(node.left, i, j, val);
    update(node.right, i, j, val);
    
    node.k = Math.max(node.left.k, node.right.k);
}

private void normalize(SegmentTreeNode node) {
    if (node.l < node.r) {
        if (node.left == null || node.right == null) {
            int m = node.l + (node.r - node.l) / 2;
            node.left = new SegmentTreeNode(node.l, m, node.k);
            node.right = new SegmentTreeNode(m + 1, node.r, node.k);
        
        } else if (node.lazy > 0) {
            node.left.k += node.lazy;
			node.left.lazy += node.lazy;
			
			node.right.k += node.lazy;
            node.right.lazy += node.lazy;
        }
    }
    
    node.lazy = 0;
}


SegmentTreeNode root;

public MyCalendarTwo() {
    root = new SegmentTreeNode(0, 1_000_000_000, 0);
}

public boolean book(int start, int end) {
    int k = query(root, start, end - 1);
    if (k >= 2) return false;
    
    update(root, start, end - 1, 1);
    return true;
}
 */

// Solution 4: Timeline
// Refer to
// https://leetcode.com/problems/my-calendar-ii/discuss/109550/Simple-AC-by-TreeMap
// https://www.cnblogs.com/grandyang/p/7968035.html
class MyCalendarTwo {
    private TreeMap<Integer, Integer> timeline = new TreeMap<Integer, Integer>();
    
    public MyCalendarTwo() {
        
    }
    
    public boolean book(int start, int end) {
        // 1 new event will start at [s]
        timeline.put(start, timeline.getOrDefault(start, 0) + 1);
        // 1 new event will end at [e]
        timeline.put(end, timeline.getOrDefault(end, 0) - 1);
        int ongoing = 0;
        for(int v : timeline.values()) {
            ongoing += v;
            if(ongoing > 2) {
                timeline.put(start, timeline.get(start) - 1);
                if(timeline.get(start) == 0) {
                    timeline.remove(start);
                }
                timeline.put(end, timeline.get(end) + 1);
                if(timeline.get(end) == 0) {
                    timeline.remove(end);
                }
                return false;
            }
        }
        return true;
    }
}

/**
 * Your MyCalendarTwo object will be instantiated and called as such:
 * MyCalendarTwo obj = new MyCalendarTwo();
 * boolean param_1 = obj.book(start,end);
 */




// Follow up questions:
// Refer to
// https://leetcode.com/problems/my-calendar-ii/discuss/619695/Follow-up-questions%3A-A-new-event-can-be-added-if-it-will-not-cause-a-K-booking.
/**
 One follow up question is
 A new event can be added if it will not cause a K-booking.
 A K-booking happens when K events have some non-empty intersection (ie., there is some time that is common to all K events.)
*/
class MyCalendarTwo {
    List<List<int[]>> overlaps;
    int k = 2;   // For triple-booking   
    public MyCalendarTwo() {
        overlaps = new ArrayList<>();
        for(int i = 0; i < k; i++) {
            overlaps.add(new ArrayList<>());
        }
    }
    
    public boolean book(int start, int end) {     
        for(int i = 0; i < k; i++) {
            // Find overlap in the i-th overlaps
            for(int[] o : overlaps.get(i)) {
                if(i == 0) {
                    if(Math.max(o[0], start) < Math.min(o[1], end)) {
                        return false;
                    }
                } else {
                    if(Math.max(o[0], start) < Math.min(o[1], end)) {
                        overlaps.get(i-1).add(new int[] {Math.max(o[0], start), Math.min(o[1], end)});
                    }  
                }
            }
        }
        // Add interval into calendar: O(1)
        overlaps.get(k-1).add(new int[]{ start, end });
        return true;
    }
}










































































https://leetcode.com/problems/my-calendar-ii/description/

You are implementing a program to use as your calendar. We can add a new event if adding the event will not cause a triple booking.

A triple booking happens when three events have some non-empty intersection (i.e., some moment is common to all the three events.).

The event can be represented as a pair of integers start and end that represents a booking on the half-open interval [start, end), the range of real numbers x such that start <= x < end.

Implement the MyCalendarTwo class:
- MyCalendarTwo() Initializes the calendar object.
- boolean book(int start, int end) Returns true if the event can be added to the calendar successfully without causing a triple booking. Otherwise, return false and do not add the event to the calendar.
 
Example 1:
```
Input
["MyCalendarTwo", "book", "book", "book", "book", "book", "book"]
[[], [10, 20], [50, 60], [10, 40], [5, 15], [5, 10], [25, 55]]
Output
[null, true, true, true, false, true, true]

Explanation
MyCalendarTwo myCalendarTwo = new MyCalendarTwo();
myCalendarTwo.book(10, 20); // return True, The event can be booked. 
myCalendarTwo.book(50, 60); // return True, The event can be booked. 
myCalendarTwo.book(10, 40); // return True, The event can be double booked. 
myCalendarTwo.book(5, 15);  // return False, The event cannot be booked, because it would result in a triple booking.
myCalendarTwo.book(5, 10); // return True, The event can be booked, as it does not use time 10 which is already double booked.
myCalendarTwo.book(25, 55); // return True, The event can be booked, as the time in [25, 40) will be double booked with the third event, the time [40, 50) will be single booked, and the time [50, 55) will be double booked with the second event.

```
 
Constraints:
- 0 <= start < end <= 109
- At most 1000 calls will be made to book.
---
Attempt 1: 2023-11-30

Solution 1: Brute Force + Find double booking first (30 min)
```
class MyCalendarTwo {
    //        s1   e1
    // s2   e2       s2   e2
    // no conflict condition: e2 <= s1 or e1 <= s2
    // Based on De Morgan's law
    // conflict condition: e2 > s1 and e1 > s2
    List<int[]> list;
    List<int[]> doubleBooking;
    public MyCalendarTwo() {
        list = new ArrayList<>();
        doubleBooking = new ArrayList<>();
    }
    
    public boolean book(int start, int end) {
        // Detect triple booking based on double booking
        for(int[] range : doubleBooking) {
            if(end > range[0] && range[1] > start) {
                return false;
            }
        }
        // Build double booking
        for(int[] range : list) {
            if(end > range[0] && range[1] > start) {
                doubleBooking.add(new int[]{Math.max(range[0], start), Math.min(range[1], end)});
            }
        }
        list.add(new int[]{start, end});
        return true;
    }
}
/**
 * Your MyCalendarTwo object will be instantiated and called as such:
 * MyCalendarTwo obj = new MyCalendarTwo();
 * boolean param_1 = obj.book(start,end);
 */
```

Refer to
https://grandyang.com/leetcode/731/ 
这道题是 My Calendar I 的拓展，之前那道题说是不能有任何的重叠区间，而这道题说最多容忍两个重叠区域，注意是重叠区域，不是事件。比如事件 A，B，C 互不重叠，但是有一个事件D，和这三个事件都重叠，这样是可以的，因为重叠的区域最多只有两个。所以关键还是要知道具体的重叠区域，如果两个事件重叠，那么重叠区域就是它们的交集，求交集的方法是两个区间的起始时间中的较大值，到结束时间中的较小值。可以用一个 TreeSet 来专门存重叠区间，再用一个 TreeSet 来存完整的区间，那么思路就是，先遍历专门存重叠区间的 TreeSet，因为能在这里出现的区间，都已经是出现两次了，如果当前新的区间跟重叠区间有交集的话，说明此时三个事件重叠了，直接返回 false。如果当前区间跟重叠区间没有交集的话，则再来遍历完整区间的集合，如果有交集的话，那么应该算出重叠区间并且加入放重叠区间的 TreeSet 中。最后记得将新区间加入完整区间的 TreeSet 中。

Refer to
https://leetcode.com/problems/my-calendar-ii/editorial/
Intuition
Maintain a list of bookings and a list of double bookings. When booking a new event [start, end), if it conflicts with a double booking, it will have a triple booking and be invalid. Otherwise, parts that overlap the calendar will be a double booking.

Algorithm
Evidently, two events [s1, e1) and [s2, e2) do not conflict if and only if one of them starts after the other one ends: either e1 <= s2 OR e2 <= s1. By De Morgan's laws, this means the events conflict when s1 < e2 AND s2 < e1.

If our event conflicts with a double booking, it's invalid. Otherwise, we add conflicts with the calendar to our double bookings, and add the event to our calendar.
```
public class MyCalendarTwo {
    List<int[]> calendar;
    List<int[]> overlaps;
    MyCalendarTwo() {
        overlaps = new ArrayList();
        calendar = new ArrayList();
    }
    public boolean book(int start, int end) {
        for (int[] iv: overlaps) {
            if (iv[0] < end && start < iv[1]) return false;
        }
        for (int[] iv: calendar) {
            if (iv[0] < end && start < iv[1])
                overlaps.add(new int[]{Math.max(start, iv[0]), Math.min(end, iv[1])});
        }
        calendar.add(new int[]{start, end});
        return true;
    }
}
```
Complexity Analysis
- Time Complexity: O(N^2), where N is the number of events booked. For each new event, we process every previous event to decide whether the new event can be booked. This leads to 
  
   complexity.
- Space Complexity: O(N), the size of the calendar
---
Solution 2: Sweep Line + TreeMap (30 min)
```
class MyCalendarTwo {
    TreeMap<Integer, Integer> list;
    public MyCalendarTwo() {
        list = new TreeMap<>();
    }
    
    public boolean book(int start, int end) {
        // Increase the counter at the start time
        list.put(start, list.getOrDefault(start, 0) + 1);
        // Decrease the counter at the end time
        list.put(end, list.getOrDefault(end, 0) - 1);
        // This will track the number of ongoing events
        int count = 0;
        for(int val : list.values()) {
            count += val;
            // If at any point there are more than 2 active events, this booking overlaps with two other events
            if(count >= 3) {
                // The booking is not possible, so revert the changes
                list.put(start, list.get(start) - 1);
                list.put(end, list.get(end) + 1);
                if(list.get(start) == 0) {
                    list.remove(start);
                }
                return false;
            }
        }
        return true;
    }
}
/**
 * Your MyCalendarTwo object will be instantiated and called as such:
 * MyCalendarTwo obj = new MyCalendarTwo();
 * boolean param_1 = obj.book(start,end);
 */

Time Complexity: O(N^2), where N is the number of events booked. For each new event, we traverse list in O(N) time. 
Space Complexity: O(N), the size of list.
```

Refer to
https://leetcode.com/problems/my-calendar-ii/editorial/

Approach #2: Boundary Count [Accepted]

Intuition and Algorithm
When booking a new event [start, end), count delta[start]++ and delta[end]--. When processing the values of delta in sorted order of their keys, the running sum active is the number of events open at that time. If the sum is 3 or more, that time is (at least) triple booked.

A Python implementation was not included for this approach because there is no analog to TreeMap available.
```
class MyCalendarTwo {
    TreeMap<Integer, Integer> delta;
    public MyCalendarTwo() {
        delta = new TreeMap();
    }
    public boolean book(int start, int end) {
        delta.put(start, delta.getOrDefault(start, 0) + 1);
        delta.put(end, delta.getOrDefault(end, 0) - 1);
        int active = 0;
        for (int d: delta.values()) {
            active += d;
            if (active >= 3) {
                delta.put(start, delta.get(start) - 1);
                delta.put(end, delta.get(end) + 1);
                if (delta.get(start) == 0)
                    delta.remove(start);
                return false;
            }
        }
        return true;
    }
}
```
Complexity Analysis
- Time Complexity: O(N^2), where N is the number of events booked. For each new event, we traverse delta in O(N) time.
- Space Complexity: O(N), the size of delta.


Refer to
https://algo.monster/liteproblems/731

Problem Description

In this problem, you are tasked with creating a calendar system that can add new events without creating a situation wherein three events overlap in time—this is what is referred to as a "triple booking." Events are defined by their start and end times, with the start time being inclusive and the end time being exclusive, signified by the interval [start, end). The problem requires the implementation of a class, MyCalendarTwo, which provides two functionalities:
1. Initializing the calendar object.
2. Booking an event (specified by its start and end) if doing so does not result in any triple booking. It returns true if the event can be added without a triple booking, otherwise false.

The objective is to efficiently manage a calendar by keeping track of events while ensuring at most two events may overlap, but not three. This requires careful tracking of each event's start and end times.


Intuition

The intuition behind the solution comes from the need to manage the overlaps efficiently. Given that double bookings are allowed but not triple bookings, we need to track whenever an event starts and ends, and how this impacts the existing timeline of bookings. Here, we can use a data structure such as the SortedDict from the sortedcontainers module which keeps the keys sorted and allows us to efficiently determine starting and ending points of events.

The approach is to increment the count at the event start time and decrement it at the event end time. Every time we attempt to book an event, we update the timeline with the start and end times. After adding the event to the calendar, we iterate through all time points in our SortedDict and maintain a running sum that represents the current number of overlapping events. If, at any time, this sum exceeds 2, it means we are trying to create a triple booking, which is not allowed. At this point, we need to revert this booking by decrementing the count at the start time and incrementing at the end time, and return false since we cannot book the event. If we successfully iterate through the entire sorted dictionary without the sum exceeding 2, the event is successfully booked without causing a triple booking, so we return true.


Solution Approach

The solution uses a class MyCalendarTwo that maintains a SortedDict from the sortedcontainers module. This dictionary will keep track of how many events are starting or ending at any given time. This approach is akin to employing a sweep line algorithm commonly used in computational geometry. The key idea is to "sweep" across the calendar and keep a count of concurrent events.

When booking a new event, we apply these steps in the book method:
1. Increment the counter for the event's start time: self.sd[start] = self.sd.get(start, 0) + 1. This represents the beginning of an event.
2. Decrement the counter for the event's end time: self.sd[end] = self.sd.get(end, 0) - 1. This signals the end of an event.


After updating the counters, we need to check for triple bookings:
3. Iterate over all values in our sorted dictionary using self.sd.values(). We keep a running sum s that represents the current number of overlapping events.
   a. For each value v in the SortedDict, we add it to our running sum s += v.
   b. If at any point our sum exceeds 2 (if s > 2), it signifies that the attempted booking would result in a triple booking, thus we revert the changes done in step 1 and 2 by decrementing the start time counter and incrementing the end time counter:
```
self.sd[start] -= 1
self.sd[end] += 1
```
           c. Since adding the event leads to a triple booking, we return False.

4. If we complete the iteration without our running sum ever exceeding 2, it means we have successfully added the event without causing a triple booking, and we return True.


The SortedDict data structure allows efficient insertion, deletion, and iteration, which is crucial for the performance of this algorithm. By incrementing start times and decrementing end times, we smartly keep track of ongoing events, and by checking the running sum, we enforce the no-triple-booking rule.



Example Walkthrough

Let's go through an example to illustrate the solution approach using the MyCalendarTwo class.

First, we initialize the MyCalendarTwo object:
```
my_calendar = MyCalendarTwo()
```

Our SortedDict starts empty as no events have been booked yet.
Now, let's try booking our first event [10, 20):
```
result = my_calendar.book(10, 20)
```

- self.sd[10] becomes 1 because one event starts at time 10.
- self.sd[20] becomes -1 because one event ends at time 20.
- We iterate through the SortedDict, and the running sum s never exceeds 2, because we only have one event.
- The result is True, and the first event is successfully booked.
Now, suppose we book a second event [15, 25):
```
result = my_calendar.book(15, 25)
```

- self.sd[15] gets incremented to 1, and since there is already one event overlapping at this time, the running total of events is now 2 at time 15 (as earlier, the running sum was 1 at 10, and then it becomes 2 at 15).
- self.sd[25] becomes -1.
- We iterate through the SortedDict, which now looks like this: 10: 1, 15: 1, 20: -1, 25: -1, and the running sum s never exceeds 2.
- The result is True, and the second event is successfully booked.
Finally, let's try booking a third event [20, 30):
```
result = my_calendar.book(20, 30)
```

- self.sd[20] remains unchanged because when an event ends, another begins at the same time.
- self.sd[30] becomes -1.
- The sorted dictionary now is 10: 1, 15: 1, 20: -1, 25: -1, 30: -1, and while iterating, the running sum s does not exceed 2.
- The booking is successful as the sum s remains at most 2 at all points in time.
If we then attempt to book a fourth event [10, 15):
```
result = my_calendar.book(10, 15)
```

- self.sd[10] would be incremented, going from 1 to 2, as there is now a second event starting at time 10.
- self.sd[15] would be decremented, going from 1 to 0.
- During iteration, when we reach time 15, the running sum s would become 3 (1 for the first event starting at 10, and 2 for the second and fourth events overlapping between 10 and 15) which exceeds our limit of 2.
- This would result in a triple booking, so we revert the changes (self.sd[10] goes back to 1 and self.sd[15] back to 1), and the result is False.
The event [10, 15) cannot be booked without causing a triple booking, and therefore, our MyCalendarTwo correctly returns False.

```
import java.util.Map;
import java.util.TreeMap;
public class MyCalendarTwo {
  
    // Use TreeMap to automatically keep the keys sorted
    private Map<Integer, Integer> timeMap = new TreeMap<>();
    // Default constructor (not explicitly needed unless more constructors are provided)
    public MyCalendarTwo() {
    }
    // Function to book a new event from start to end time
    public boolean book(int start, int end) {
        // Increase the counter at the start time
        timeMap.put(start, timeMap.getOrDefault(start, 0) + 1);
        // Decrease the counter at the end time
        timeMap.put(end, timeMap.getOrDefault(end, 0) - 1);
        int activeEvents = 0; // This will track the number of ongoing events
        // Iterate through the values in TreeMap
        for (int eventsCount : timeMap.values()) {
            // Increment the count of active events
            activeEvents += eventsCount;
            // If at any point there are more than 2 active events, this booking overlaps with two other events
            if (activeEvents > 2) {
                // The booking is not possible, so revert the changes
                timeMap.put(start, timeMap.get(start) - 1);
                timeMap.put(end, timeMap.get(end) + 1);
                // Return false as the booking overlaps and cannot be accepted
                return false;
            }
        }
        // The booking does not overlap with two or more events, so return true
        return true;
    }
}
```
