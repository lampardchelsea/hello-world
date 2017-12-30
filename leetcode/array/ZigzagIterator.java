/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5209621.html
 * Given two 1d vectors, implement an iterator to return their elements alternately.
    For example, given two 1d vectors:

    v1 = [1, 2]
    v2 = [3, 4, 5, 6]
    By calling next repeatedly until hasNext returns false, the order of elements 
    returned by next should be: [1, 3, 2, 4, 5, 6].

    Follow up: What if you are given k 1d vectors? How well can your code be extended to such cases?

    Clarification for the follow up question - Update (2015-09-18):
    The "Zigzag" order is not clearly defined and is ambiguous for k > 2 cases. If "Zigzag" 
    does not look right to you, replace "Zigzag" with "Cyclic". For example, given the following input:

    [1,2,3]
    [4,5,6,7]
    [8,9]
    It should return [1,4,8,2,5,9,3,6,7].
 * 
 *
 * Solution
 * https://segmentfault.com/a/1190000003786218
 * https://www.youtube.com/watch?v=NgZ9SafshhA
 * http://www.cnblogs.com/grandyang/p/5212785.html
*/
public class ZigzagIterator {
    // Solution 1: Switch two iterator each time
    // Refer to
    // https://www.youtube.com/watch?v=NgZ9SafshhA
    private Iterator<Integer> i, j, temp;
    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        i = v2.iterator();
        j = v1.iterator();
    }
 
    public int next() {
        if(j.hasNext()) {
            temp = j;
            j = i;
            i = temp;
        }
    }
    
    public boolean hasNext() {
        return i.hasNext() || j.hasNext();
    }
    
    
    // Solution 2: LinkedList + Iterator
    // Refer to
    // https://www.youtube.com/watch?v=NgZ9SafshhA
    // Note: This list contains not 'Integer', but 'Iterator'
    /**
     * E.g
     * v1 = [1,2]
     * v2 = [3,4,5,6]
     * list -> ((1,2),(3,4,5,6)) --> (1,2) and (3,4,5,6) represent 2 iterators, not these 6 elements
     * 
     *
     * 
    */
    LinkedList<Iterator> list;
    public ZigzagIterator2(List<Integer> v1, List<Integer> v2) {
        list = new LinkedList<Iterator>();
        if(!v1.isEmpty()) {
            list.add(v1.itertor());
        }
        if(!v2.isEmpty()) {
            list.add(v2.iterator());
        }
    }
    
    public int next2() {
        // Use LinkedList is for defaultly remove first item in list
        // e.g list -> ((1,2),(3,4,5,6)) --> first time list.remove() will remove iterator of (1,2)
        // and list becomes to ((3,4,5,6))
        Iterator poll = list.remove();
        // first time poll.next() will get 1 in (1,2)
        int result = (Integer)poll.next();
        // first time check poll.hasNext() will return true as after select out 1 (iterator default next() operation) 
        // and left (2) in (1,2)
        if(poll.hasNext()) {
            // first time list.add(poll) will add back iterator of (2) to list and list becomes to ((3,4,5,6),(2))
            // for next time calling next2() method, will start with list -> ((3,4,5,6),(2))
            list.add(poll);
        }
        return list;
    }
    
    public boolean hasNext2() {
        return !list.isEmpty();
    }
}








