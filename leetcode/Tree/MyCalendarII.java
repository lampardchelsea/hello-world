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
